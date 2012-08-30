package com.glaf.base.modules.sys.action;

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.core.cache.CacheFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.glaf.base.callback.CallbackProperties;
import com.glaf.base.callback.LoginCallback;
import com.glaf.base.context.ContextFactory;

import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.utils.ContextUtil;
import com.glaf.base.utils.ClassUtil;
import com.glaf.base.utils.RequestUtil;

public class AuthorizeBean {
	private static final Log logger = LogFactory.getLog(AuthorizeBean.class);
	protected static String configurationResource = "/conf/spring/spring-config.xml";

	protected static org.springframework.context.ApplicationContext ctx;

	public static void main(String[] args) {
		ctx = new ClassPathXmlApplicationContext(configurationResource);
		com.glaf.base.context.ContextFactory.setContext(ctx);
		AuthorizeBean bean = new AuthorizeBean();
		SysUser user = bean.getAuthorizeService().login("root");
		System.out.println(bean.getSysApplicationService().getMenu(3, user));
	}

	private SysApplicationService sysApplicationService;

	private AuthorizeService authorizeService;

	public AuthorizeBean() {
		authorizeService = ContextFactory.getBean("authorizeProxy");
		sysApplicationService = ContextFactory.getBean("sysApplicationProxy");
	}

	public AuthorizeService getAuthorizeService() {
		return authorizeService;
	}

	public SysApplicationService getSysApplicationService() {
		return sysApplicationService;
	}

	public SysUser getUser(String account) {
		logger.debug("#account=" + account);
		if (account != null) {
			String cacheKey = "cache_sysuser_" + account;
			if (CacheFactory.get(cacheKey) != null) {
				logger.debug("get user from cache");
				return (SysUser) CacheFactory.get(cacheKey);
			}
			SysUser bean = authorizeService.login(account);
			CacheFactory.put(cacheKey, bean);
			return bean;
		}
		return null;
	}

	public String getMenus(SysUser bean) {
		String menus = sysApplicationService.getMenu(3, bean);
		return menus;
	}

	/**
	 * ��¼
	 * 
	 * @param request
	 */
	public SysUser login(String account, HttpServletRequest request) {

		logger.debug(account + " start login........................");

		// �û���½������ϵͳ�û�����
		SysUser bean = authorizeService.login(account);
		if (bean != null) {

			// ��¼�ɹ����޸����һ�ε�¼ʱ��

			Properties props = CallbackProperties.getProperties();
			if (props != null && props.keys().hasMoreElements()) {
				Enumeration<?> e = props.keys();
				while (e.hasMoreElements()) {
					String className = (String) e.nextElement();
					try {
						Object obj = ClassUtil.instantiateObject(className);
						if (obj instanceof LoginCallback) {
							LoginCallback callback = (LoginCallback) obj;
							callback.afterLogin(bean, request, null);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error(ex);
					}
				}
			}

			String menus = sysApplicationService.getMenu(3, bean);
			bean.setMenus(menus);

			ContextUtil.put(bean.getAccount(), bean);// ����ȫ�ֱ���

			RequestUtil.setLoginUser(request, bean);

			// ����session������ת����̨��ҳ��
			request.getSession().setAttribute(SysConstants.MENU, menus);

		}
		return bean;
	}

	public void setAuthorizeService(AuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
	}

	public void setSysApplicationService(
			SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
	}

}
