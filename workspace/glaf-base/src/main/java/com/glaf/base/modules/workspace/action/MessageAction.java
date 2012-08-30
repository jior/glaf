package com.glaf.base.modules.workspace.action;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springframework.web.struts.DispatchActionSupport;

import com.glaf.base.modules.Constants;

import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.workspace.actionform.MessageForm;
import com.glaf.base.modules.workspace.model.Message;
import com.glaf.base.modules.workspace.service.MessageService;
import com.glaf.base.utils.PageResult;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;
import com.glaf.base.utils.WebUtil;

public class MessageAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(MessageAction.class);

	private MessageService messageService;

	private SysUserService sysUserService;

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
		logger.info("setsysUserService");
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
		logger.info("setMessageService");
	}

	/**
	 * ��ʾ�ռ����б�
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showReceiveList(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String flag = ParamUtil.getParameter(request, "flag", null);

		SysUser user = RequestUtil.getLoginUser(request);
		long userId = user == null ? 0L : user.getId();

		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size",
				Constants.PAGE_SIZE);

		PageResult pager = messageService.getReceiveList(userId,
				WebUtil.getQueryMap(request), pageNo, pageSize);
		request.setAttribute("pager", pager);
		request.setAttribute("flag", flag);

		// ��ʾ�б�ҳ��
		return mapping.findForward("showReceiveList");
	}

	/**
	 * ��ʾ�������б�
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showSendedList(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String flag = ParamUtil.getParameter(request, "flag", null);

		SysUser user = RequestUtil.getLoginUser(request);
		long userId = user == null ? 0L : user.getId();

		int pageNo = ParamUtil.getIntParameter(request, "page_no", 1);
		int pageSize = ParamUtil.getIntParameter(request, "page_size",
				Constants.PAGE_SIZE);

		PageResult pager = messageService.getSendedList(userId,
				WebUtil.getQueryMap(request), pageNo, pageSize);
		request.setAttribute("pager", pager);
		request.setAttribute("flag", flag);

		// ��ʾ�б�ҳ��
		return mapping.findForward("showSendedList");
	}

	/**
	 * ��ʾ������Ϣҳ��
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepareSend(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		long id = ParamUtil.getLongParameter(request, "id", 0);
		Message bean = messageService.find(id);
		request.setAttribute("bean", bean);

		return mapping.findForward("prepareSend");
	}

	/**
	 * ������Ϣ
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveSend(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUser user = RequestUtil.getLoginUser(request);
		int sysType = ParamUtil.getIntParameter(request, "sysType", 1);// 0��Ϊϵͳ����
																		// 1��Ϊϵͳ��Ϣ
		String recverIds = ParamUtil.getParameter(request, "recverIds");
		// �û�����
		int recverType = ParamUtil.getIntParameter(request, "recverType", 0);

		MessageForm formBean = new MessageForm();
		WebUtil.copyProperties(formBean, actionForm);

		Message bean = new Message();
		bean.setSysType(sysType);
		bean.setTitle(formBean.getTitle());
		bean.setContent(formBean.getContent());
		bean.setSender(user);
		bean.setCategory(0);// �ռ���
		bean.setReaded(0);
		bean.setCreateDate(new Date());

		int type = 1;// �û���Ϣ
		if (bean.getSender().getId() == 0) {
			type = 0;// ϵͳ��Ϣ
		}
		bean.setType(type);

		boolean ret = false;
		if (isTokenValid(request)) {// ��ֹ���ظ��ύ
			if (recverType == 0) {
				ret = messageService
						.saveSendMessage(bean, recverIds.split(","));
			}
			if (recverType == 1) {
				ret = messageService.saveSendMessageToDept(bean,
						recverIds.split(","));
			}
			if (recverType == 2) {
				List userList = sysUserService.getSupplierUser(recverIds);
				if (userList != null) {
					Iterator iter = userList.iterator();
					StringBuffer sb = new StringBuffer();
					while (iter.hasNext()) {
						SysUser user_sp = (SysUser) iter.next();
						if (user_sp.getAccountType() == 1) {

							sb.append(user_sp.getId() + ",");
						}
						String userIds = sb.toString();
						ret = messageService.saveSendMessage(bean,
								userIds.split(","));
					}
				}
			}
		}
		saveToken(request);

		ActionMessages messages = new ActionMessages();
		if (ret) {// ����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.send_success"));
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.send_failure"));
		}
		addMessages(request, messages);
		// ��ʾ�ύ��ҳ��
		return mapping.findForward("show_msg");
	}

	/**
	 * ����ɾ����Ϣ
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchDelete(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SysUser user = RequestUtil.getLoginUser(request);

		boolean ret = true;
		if (isTokenValid(request)) {// ��ֹ���ظ��ύ
			int[] id = ParamUtil.getIntParameterValues(request, "id");
			if (id != null) {
				for (int i = 0; i < id.length; i++) {
					Message bean = messageService.find((long) id[i]);
					// �ж��Ƿ����Լ�����Ϣ
					if (bean == null
							|| bean.getRecver().getId() != user.getId()) {
						ret = false;
					} else {
						if (!messageService.delete(bean)) {
							ret = false;
						}
					}
				}
			}
		}
		saveToken(request);

		ActionMessages messages = new ActionMessages();
		if (ret) {// ɾ���ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.delete_success"));
		} else {// ɾ��ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.delete_failure"));
		}
		addMessages(request, messages);
		// ��ʾɾ����ҳ��
		return mapping.findForward("show_msg");
	}

	/**
	 * ��ʾ��Ϣ��Ϣ
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showMessage(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		long id = ParamUtil.getLongParameter(request, "id", 0);

		Message bean = messageService.updateReadMessage(id);
		request.setAttribute("bean", bean);

		// ��ʾ��Ϣҳ��
		return mapping.findForward("showMessage");
	}

	/**
	 * ����email
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveEmail(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysUser user = RequestUtil.getLoginUser(request);
		String sendEmail = user.getEmail();

		int recverType = ParamUtil.getIntParameter(request, "recverType", 0);
		String recverIds = ParamUtil.getParameter(request, "recverIds");
		String recverName = ParamUtil.getParameter(request, "recverName");
		MessageForm formBean = new MessageForm();
		WebUtil.copyProperties(formBean, actionForm);
		String title = formBean.getTitle();
		String content = formBean.getContent();

		if (recverType == 0 || recverType == 2) {
			String toEmail = ParamUtil.getParameter(request, "toEmail");
			String[] email = toEmail.split(",");
			for (int i = 0; i < email.length; i++) {
				// EMail.send(sendEmail,email[i],title,content,null);
			}
		}
		// ����Ⱥ��
		if (recverType == 1) {
			System.out.println("string to int" + Integer.parseInt(recverIds));
			List list = sysUserService.getSysUserList(Integer
					.parseInt(recverIds));
			// System.out.println("list.size"+list.size());
			if (list != null) {
				Iterator iter = list.iterator();
				while (iter.hasNext()) {
					SysUser sysUser = (SysUser) iter.next();
					String email = sysUser.getEmail();
					// EMail.send(sendEmail, email, title, content, null);
				}
			}

		}
		// ������Ϣ���뷢����
		Message bean = new Message();
		bean.setTitle(formBean.getTitle());
		bean.setContent(formBean.getContent());
		bean.setSender(user);
		bean.setRecver(user);
		bean.setRecverList(recverName);
		bean.setCategory(1);// ������
		bean.setReaded(0);
		bean.setCreateDate(new Date());

		int type = 1;// �û���Ϣ
		if (bean.getSender().getId() == 0) {
			type = 0;// ϵͳ��Ϣ
		}
		bean.setType(type);

		boolean ret = false;
		if (isTokenValid(request)) {// ��ֹ���ظ��ύ
			ret = messageService.saveOrUpdate(bean);
		}
		saveToken(request);

		ActionMessages messages = new ActionMessages();
		if (ret) {// ����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.send_success"));
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.send_failure"));
		}
		addMessages(request, messages);
		// ��ʾ�ύ��ҳ��
		return mapping.findForward("show_msg");
	}

	/**
	 * ����ϵͳ��Ϣ��Email
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveBoth(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUser user = RequestUtil.getLoginUser(request);
		String sendEmail = user.getEmail();

		String recverIds = ParamUtil.getParameter(request, "recverIds");
		// �û�����
		int recverType = ParamUtil.getIntParameter(request, "recverType", 0);

		int sysType = ParamUtil.getIntParameter(request, "sysType", 1);// 0��Ϊϵͳ����
																		// 1��Ϊϵͳ��Ϣ

		MessageForm formBean = new MessageForm();
		WebUtil.copyProperties(formBean, actionForm);
		String title = formBean.getTitle();
		String content = formBean.getContent();
		if (recverType == 0 || recverType == 2) {
			String toEmail = ParamUtil.getParameter(request, "toEmail");
			String[] email = toEmail.split(",");

			for (int i = 0; i < email.length; i++) {
				// EMail.send(sendEmail,email[i],title,content,null);
			}
		}
		if (recverType == 1) {
			List list = sysUserService.getSysUserList(Integer
					.parseInt(recverIds));
			if (list != null) {
				Iterator iter = list.iterator();
				while (iter.hasNext()) {
					SysUser sysUser = (SysUser) iter.next();
					String email = sysUser.getEmail();
					// EMail.send(sendEmail, email, title, content, null);
				}
			}
		}
		Message bean = new Message();
		bean.setSysType(sysType);
		bean.setTitle(formBean.getTitle());
		bean.setContent(formBean.getContent());
		bean.setSender(user);
		bean.setCategory(0);// �ռ���
		bean.setReaded(0);
		bean.setCreateDate(new Date());

		int type = 1;// �û���Ϣ
		if (bean.getSender().getId() == 0) {
			type = 0;// ϵͳ��Ϣ
		}
		bean.setType(type);

		boolean ret = false;
		if (isTokenValid(request)) {// ��ֹ���ظ��ύ
			if (recverType == 0) {
				ret = messageService
						.saveSendMessage(bean, recverIds.split(","));
			}
			if (recverType == 1) {
				ret = messageService.saveSendMessageToDept(bean,
						recverIds.split(","));
			}
			if (recverType == 2) {
				List userList = sysUserService.getSupplierUser(recverIds);
				if (userList != null) {
					Iterator iter = userList.iterator();
					StringBuffer sb = new StringBuffer();
					while (iter.hasNext()) {
						SysUser user_sp = (SysUser) iter.next();
						if (user_sp.getAccountType() == 1) {

							sb.append(user_sp.getId() + ",");
						}
						String userIds = sb.toString();
						ret = messageService.saveSendMessage(bean,
								userIds.split(","));
					}
				}
			}
		}
		saveToken(request);

		ActionMessages messages = new ActionMessages();
		if (ret) {// ����ɹ�
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.send_success"));
		} else {// ����ʧ��
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.send_failure"));
		}
		addMessages(request, messages);
		// ��ʾ�ύ��ҳ��
		return mapping.findForward("show_msg");
	}

}
