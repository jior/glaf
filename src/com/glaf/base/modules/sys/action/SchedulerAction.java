package com.glaf.base.modules.sys.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.jpage.util.Tools;
import org.springframework.web.struts.DispatchActionSupport;
 
import com.glaf.base.modules.sys.actionform.SchedulerForm;
import com.glaf.base.modules.sys.model.Scheduler;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SchedulerService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;

public class SchedulerAction extends DispatchActionSupport {
	protected final static Log logger = LogFactory
			.getLog(SchedulerAction.class);

	protected SchedulerService schedulerService;

	public SchedulerAction() {

	}

	public ActionForward locked(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String taskId = request.getParameter("taskId");
		int locked = 0;
		Scheduler scheduler = null;
		if (StringUtils.isNotEmpty(taskId)) {
			scheduler = schedulerService.getSchedulerById(taskId);
			if (scheduler != null) {
				schedulerService.locked(taskId, locked);
				if (scheduler.getLocked() == 1) {
					schedulerService.stop(taskId);
				}
			}
		}
		return this.showList(mapping, form, request, response);
	}

	@SuppressWarnings("rawtypes")
	public ActionForward saveModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Scheduler scheduler = new Scheduler();
		SchedulerForm schedulerForm = (SchedulerForm) form;
		Map params = Tools.getParameterMap(request);
		logger.debug(params);
		PropertyUtils.copyProperties(scheduler, schedulerForm);
		Date startDate = ParamUtil.getDate(params, "startDate");
		Date endDate = ParamUtil.getDate(params, "endDate");
		scheduler.setStartDate(startDate);
		scheduler.setEndDate(endDate);
		if (StringUtils.isNotEmpty(scheduler.getJobClass())) {
			try {
				Class<?> clazz = Class.forName(scheduler.getJobClass());
				Object object = clazz.newInstance();
				if (!(object instanceof org.quartz.Job)) {

				}
			} catch (Exception ex) {
				logger.debug(ex);
			}
		}

		SysUser user = RequestUtil.getLoginUser(request);
		String actorId = user.getAccount();
		scheduler.setCreateBy(actorId);
		schedulerService.save(scheduler);

		return this.showList(mapping, form, request, response);
	}

	public void setSchedulerService(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}

	public ActionForward showList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUser user = RequestUtil.getLoginUser(request);
		String actorId = user.getAccount();
		List<Scheduler> list = schedulerService.getUserSchedulers(actorId);
		request.setAttribute("schedulers", list);
		return mapping.findForward("show_list");
	}

	public ActionForward showModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		saveToken(request);
		String taskId = request.getParameter("taskId");
		Scheduler scheduler = null;
		if (StringUtils.isNotEmpty(taskId)) {
			scheduler = schedulerService.getSchedulerById(taskId);
		}
		request.setAttribute("scheduler", scheduler);

		return mapping.findForward("show_modify");
	}

	public ActionForward startup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String taskId = request.getParameter("taskId");
		String startup = request.getParameter("startup");
		Scheduler scheduler = null;
		if (StringUtils.isNotEmpty(taskId)) {
			scheduler = schedulerService.getSchedulerById(taskId);
			if (scheduler != null) {
				if (StringUtils.equals(startup, "1")) {
					schedulerService.restart(taskId);
				} else {
					schedulerService.stop(taskId);
				}
			}
		}
		return this.showList(mapping, form, request, response);
	}

}
