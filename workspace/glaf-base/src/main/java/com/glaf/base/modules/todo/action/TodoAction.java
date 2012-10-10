package com.glaf.base.modules.todo.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.springframework.web.struts.DispatchActionSupport;

import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.todo.TodoXlsReader;
import com.glaf.base.modules.todo.form.TodoForm;
import com.glaf.base.modules.todo.model.ToDo;
import com.glaf.base.modules.todo.service.TodoService;
import com.glaf.base.utils.RequestUtil;

public class TodoAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(TodoAction.class);

	private TodoService todoService;

	/**
	 * 显示TODO列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUser user = RequestUtil.getLoginUser(request);
		if (user.isSystemAdmin()) {
			String id = request.getParameter("id");
			String enableFlag = request.getParameter("enableFlag");
			String limitDay = request.getParameter("limitDay");
			String xa = request.getParameter("xa");
			String xb = request.getParameter("xb");
			ToDo todo = todoService.getToDo(Long.valueOf(id));
			todo.setTitle(request.getParameter("title"));
			todo.setContent(request.getParameter("content"));
			try {
				todo.setEnableFlag(new Integer(enableFlag).intValue());
				todo.setLimitDay(new Integer(limitDay).intValue());
				todo.setXa(new Integer(xa).intValue());
				todo.setXb(new Integer(xb).intValue());
				todoService.update(todo);
			} catch (Exception ex) {
				logger.error(ex);
			}
		}

		return this.showList(mapping, actionForm, request, response);
	}

	public void setTodoService(TodoService todoService) {
		this.todoService = todoService;
		logger.info("setTodoService");
	}

	/**
	 * 显示TODO列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showList(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List rows = todoService.getAllToDoList();
		request.setAttribute("rows", rows);
		return mapping.findForward("show_list");
	}

	/**
	 * 显示TODO列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showModify(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("show_modify");
	}

	/**
	 * 上传文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("show_upload");
	}

	/**
	 * 上传文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TodoForm todoForm = (TodoForm) form;
		TodoXlsReader reader = new TodoXlsReader();
		List<ToDo> todos = reader.readXls(todoForm.getFile().getInputStream());
		if (todos != null && !todos.isEmpty()) {
			logger.debug("import size:"+todos.size());
			todoService.saveAll(todos);
		}
		return this.showList(mapping, form, request, response);
	}
}
