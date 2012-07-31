package com.glaf.base.modules.sys.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.jpage.util.DigestUtil;
import org.springframework.web.struts.DispatchActionSupport;

import com.glaf.base.listener.UserOnlineListener;
import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.utils.ContextUtil;
import com.glaf.base.utils.ParamUtil;

public class AuthorizeAction extends DispatchActionSupport {
	private Log logger = LogFactory.getLog(AuthorizeAction.class);

	private SysApplicationService sysApplicationService;

	private AuthorizeService authorizeService;

	private SysTreeService sysTreeService;

	private SysUserService sysUserService;

	public SysUserService getSysUserService() {
		return sysUserService;
	}

	/**
	 * ��¼
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		saveToken(request);

		ActionMessages messages = new ActionMessages();
		// ��ȡ����
		String account = ParamUtil.getParameter(request, "account");
		String password = ParamUtil.getParameter(request, "password");
		String pwd = DigestUtil.digestString(password, "MD5");

		logger.debug(account + " start login........................");

		// �û���½������ϵͳ�û�����
		SysUser bean = authorizeService.login(account, pwd);
		if (bean == null) {
			// �û�����Ϊ�ջ�ʧЧ����ʾ������Ϣ
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"authorize.login_failure"));
			addMessages(request, messages);
			return mapping.findForward("show_login");
		} else {
			String loginIp = UserOnlineListener.findUser(bean.getId());
			logger.info("login ip:" + loginIp);
			if (loginIp != null && !loginIp.equals(request.getRemoteAddr())) {// �û���������������½
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"authorize.login_failure2"));
				addMessages(request, messages);
				return mapping.findForward("show_login");
			}

			// ��¼�ɹ����޸����һ�ε�¼ʱ��
			Map map = new HashMap();
			map.put("lastLoginIP", request.getRemoteAddr());
			map.put("lastLoginTime", new Date());
			map.put("account", bean.getAccount());
			// sysUserService.updatexy();

			ContextUtil.put(bean.getAccount(), bean);// ����ȫ�ֱ���

			org.jpage.actor.User user = new org.jpage.actor.User();
			user.setActorId(bean.getAccount().toLowerCase());
			user.setActorType(0);
			user.setMail(bean.getEmail());
			user.setMobile(bean.getMobile());
			if (StringUtils.equals(bean.getAccount(), "root")) {
				user.setAdmin(true);
			}

			request.getSession().setAttribute(
					org.jpage.util.Constant.LOGIN_USER, user);
			request.getSession().setAttribute(
					org.jpage.util.Constant.LOGIN_USER_USERNAME,
					user.getActorId());

			// ����session������ת����̨��ҳ��
			request.getSession().setAttribute(SysConstants.LOGIN, bean);
			request.getSession().setAttribute(SysConstants.MENU,
					sysApplicationService.getMenu(3, bean));

			if (bean.getAccountType() == 1) {// ��Ӧ���û�
				return mapping.findForward("show_sp_frame");
			} else {
				return mapping.findForward("show_frame");
			}
		}
	}

	/**
	 * �ǳ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// �ǳ�ϵͳ�����session����
		request.getSession().invalidate();
		return mapping.findForward("show_login");
	}

	/**
	 * ׼����¼
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward prepareLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// ��ʾ��½ҳ��
		return mapping.findForward("show_login");
	}

	public void setAuthorizeService(AuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
		logger.info("setAuthorizeService");
	}

	public void setSysApplicationService(
			SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
		logger.info("setSysApplicationService");
	}

	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
		logger.info("setSysTreeService");
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	/**
	 * ��ʾ�˵�
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showMenu(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUser user = (SysUser) request.getSession().getAttribute(
				SysConstants.LOGIN);
		SysTree parent = sysTreeService.getSysTreeByCode(Constants.TREE_APP);
		List list = sysApplicationService
				.getAccessAppList(parent.getId(), user);
		request.setAttribute("list", list);
		return mapping.findForward("show_menu");
	}

	/**
	 * ��ʾ�Ӳ˵�
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showSubMenu(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysUser user = (SysUser) request.getSession().getAttribute(
				SysConstants.LOGIN);
		long parent = ParamUtil.getIntParameter(request, "parent", 0);
		List list = sysApplicationService.getAccessAppList(parent, user);
		request.setAttribute("list", list);
		return mapping.findForward("show_submenu");
	}
}
