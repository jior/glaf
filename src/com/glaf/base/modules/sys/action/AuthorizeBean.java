package com.glaf.base.modules.sys.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.context.ContextFactory;

import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.utils.ContextUtil;
import com.glaf.base.utils.RequestUtil;

public class AuthorizeBean {
	private static final Log logger = LogFactory.getLog(AuthorizeBean.class);

	private SysApplicationService sysApplicationService;

	private AuthorizeService authorizeService;

	public AuthorizeBean() {
		authorizeService = ContextFactory.getBean("authorizeService");
		sysApplicationService = ContextFactory.getBean("sysApplicationService");
	}

	/**
	 * ��¼
	 * 
	 * @param request
	 */
	public void login(String account, HttpServletRequest request) {

		logger.debug(account + " start login........................");

		// �û���½������ϵͳ�û�����
		SysUser bean = authorizeService.login(account);
		if (bean != null) {

			// ��¼�ɹ����޸����һ�ε�¼ʱ��
			Map map = new HashMap();
			map.put("lastLoginIP", request.getRemoteAddr());
			map.put("lastLoginTime", new Date());
			map.put("account", bean.getAccount());
			// sysUserService.updatexy();

			ContextUtil.put(bean.getAccount(), bean);// ����ȫ�ֱ���

			RequestUtil.setLoginUser(request, bean);

			// ����session������ת����̨��ҳ��
			request.getSession().setAttribute(SysConstants.MENU,
					sysApplicationService.getMenu(3, bean));

		}
	}

}
