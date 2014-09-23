package com.glaf.form.web.springmvc;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.glaf.core.base.DataFile;
import com.glaf.core.base.DataModel;
import com.glaf.core.config.ViewProperties;

import com.glaf.core.identity.User;

import com.glaf.core.service.EntityDefinitionService;
import com.glaf.core.service.IBlobService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.*;

import com.glaf.form.core.domain.*;
import com.glaf.form.core.service.*;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.ProcessContext;
import com.glaf.jbpm.datafield.DataField;
import com.glaf.jbpm.model.ActivityInstance;
import com.glaf.jbpm.model.TaskItem;

@Controller("/form/jbpm")
@RequestMapping("/form/jbpm")
public class JbpmFormController {

	private static final Log logger = LogFactory
			.getLog(JbpmFormController.class);

	protected IBlobService blobService;

	protected EntityDefinitionService entityDefinitionService;

	protected FormDataService formDataService;

	protected ITableDefinitionService tableDefinitionService;

	public JbpmFormController() {

	}

	@RequestMapping("/completeTask")
	@ResponseBody
	public byte[] completeTask(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);
		String appId = request.getParameter("appId");
		String businessKey = request.getParameter("businessKey");
		FormApplication formApplication = formDataService
				.getFormApplication(appId);
		DataModel dataModel = formDataService.getDataModelByBusinessKey(appId,
				businessKey);
		if (formApplication != null && dataModel != null) {
			if (dataModel.getProcessInstanceId() != null
					&& dataModel.getWfStatus() != 9999) {
				TaskItem taskItem = ProcessContainer
						.getContainer()
						.getMinTaskItem(
								actorId,
								Long.parseLong(dataModel.getProcessInstanceId()));
				if (taskItem != null) {
					String route = request.getParameter("route");
					String isAgree = request.getParameter("isAgree");
					String opinion = request.getParameter("opinion");
					ProcessContext ctx = new ProcessContext();
					Collection<DataField> datafields = new java.util.ArrayList<DataField>();
					if (StringUtils.isNotEmpty(isAgree)) {
						DataField datafield = new DataField();
						datafield.setName("isAgree");
						datafield.setValue(isAgree);
						datafields.add(datafield);
					}

					if (StringUtils.isNotEmpty(route)) {
						DataField datafield = new DataField();
						datafield.setName("route");
						datafield.setValue(route);
						datafields.add(datafield);
					}

					if (StringUtils.isNotEmpty(formApplication.getDataField())) {
						List<String> fields = StringTools.split(formApplication
								.getDataField());
						if (fields != null && !fields.isEmpty()) {
							for (String name : fields) {
								if (dataModel.getDataMap().get(name) != null) {
									DataField dataField = new DataField();
									dataField.setName(name);
									dataField.setValue(dataModel.getDataMap()
											.get(name));
									datafields.add(dataField);
								}
							}
						}
					}

					ctx.setActorId(actorId);
					ctx.setOpinion(opinion);
					ctx.setDataFields(datafields);
					ctx.setTaskInstanceId(taskItem.getTaskInstanceId());
					ctx.setProcessInstanceId(Long.parseLong(dataModel
							.getProcessInstanceId()));
					try {
						boolean isOK = ProcessContainer.getContainer()
								.completeTask(ctx);
						if (isOK) {
							return ResponseUtils.responseJsonResult(true);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error(ex);
					}
				}
			}
		}

		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		String appId = request.getParameter("appId");
		String businessKeys = request.getParameter("businessKeys");
		FormApplication formApplication = formDataService
				.getFormApplication(appId);
		if (formApplication != null) {
			if (StringUtils.isNotEmpty(businessKeys)) {
				List<String> list = StringTools.split(businessKeys);
				formDataService.deleteDataModel(appId, list);
			}
		}
	}

	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String appId = ParamUtils.getString(params, "appId");
		String businessKey = request.getParameter("businessKey");
		FormApplication formApplication = null;
		if (StringUtils.isNotEmpty(appId)) {
			formApplication = formDataService.getFormApplication(appId);
			request.setAttribute("formApplication", formApplication);
			if (StringUtils.isNotEmpty(businessKey)) {
				DataModel dataModel = formDataService
						.getDataModelByBusinessKey(appId, businessKey);
				request.setAttribute("dataModel", dataModel);

				if (dataModel != null) {
					boolean canUpdate = false;
					boolean canSubmit = false;
					String x_method = request.getParameter("x_method");
					if (StringUtils.equals(x_method, "submit")) {
						if (dataModel.getProcessInstanceId() != null) {
							ProcessContainer container = ProcessContainer
									.getContainer();
							Collection<Long> processInstanceIds = container
									.getRunningProcessInstanceIds(actorId);
							if (processInstanceIds
									.contains(Long.parseLong(dataModel
											.getProcessInstanceId()))) {
								canSubmit = true;
							}
							if (dataModel.getStatus() == 0
									|| dataModel.getStatus() == -1) {
								canUpdate = true;
							}
							TaskItem taskItem = container.getMinTaskItem(
									actorId, Long.parseLong(dataModel
											.getProcessInstanceId()));
							if (taskItem != null) {
								request.setAttribute("taskItem", taskItem);
							}
							List<ActivityInstance> stepInstances = container
									.getActivityInstances(Long
											.parseLong(dataModel
													.getProcessInstanceId()));
							request.setAttribute("stepInstances", stepInstances);
							request.setAttribute("stateInstances",
									stepInstances);
						} else {
							canSubmit = true;
							canUpdate = true;
						}

						if (StringUtils.containsIgnoreCase(x_method, "update")) {
							if (dataModel.getStatus() == 0
									|| dataModel.getStatus() == -1) {
								canUpdate = true;
							}
						}

						List<DataFile> dataFiles = blobService
								.getBlobList(businessKey);
						request.setAttribute("dataFiles", dataFiles);

						request.setAttribute("canSubmit", canSubmit);
						request.setAttribute("canUpdate", canUpdate);
					}
				}
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("form.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/edit", modelMap);
	}

	@javax.annotation.Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	@javax.annotation.Resource
	public void setEntityDefinitionService(
			EntityDefinitionService entityDefinitionService) {
		this.entityDefinitionService = entityDefinitionService;
	}

	@javax.annotation.Resource
	public void setFormDataService(FormDataService formDataService) {
		this.formDataService = formDataService;
	}

	@javax.annotation.Resource
	public void setTableDefinitionService(
			ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

	@RequestMapping("/startProcess")
	@ResponseBody
	public byte[] startProcess(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);
		String appId = request.getParameter("appId");
		String businessKey = request.getParameter("businessKey");
		FormApplication formApplication = formDataService
				.getFormApplication(appId);
		DataModel dataModel = formDataService.getDataModelByBusinessKey(appId,
				businessKey);
		if (formApplication != null && dataModel != null) {
			if (dataModel.getProcessInstanceId() == null
					&& dataModel.getWfStatus() == 0) {

				ProcessContext ctx = new ProcessContext();
				Collection<DataField> datafields = new java.util.ArrayList<DataField>();

				ctx.setActorId(actorId);
				ctx.setRowId(businessKey);
				ctx.setContextMap(params);
				ctx.setProcessName(formApplication.getProcessName());

				if (StringUtils.isNotEmpty(formApplication.getDataField())) {
					List<String> fields = StringTools.split(formApplication
							.getDataField());
					if (fields != null && !fields.isEmpty()) {
						for (String name : fields) {
							if (dataModel.getDataMap().get(name) != null) {
								DataField dataField = new DataField();
								dataField.setName(name);
								dataField.setValue(dataModel.getDataMap().get(
										name));
								datafields.add(dataField);
							}
						}
					}
				}

				ctx.setDataFields(datafields);

				try {
					Long processInstanceId = ProcessContainer.getContainer()
							.startProcess(ctx);
					if (processInstanceId != null) {
						return ResponseUtils.responseJsonResult(true);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error(ex);
				}

			}
		}

		return ResponseUtils.responseJsonResult(false);
	}

}
