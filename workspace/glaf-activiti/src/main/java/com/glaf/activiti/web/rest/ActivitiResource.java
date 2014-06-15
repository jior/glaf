/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.activiti.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.BoundaryEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.CallActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.ErrorEventDefinition;
import org.activiti.engine.impl.bpmn.parser.EventSubscriptionDeclaration;
import org.activiti.engine.impl.jobexecutor.TimerDeclarationImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.Lane;
import org.activiti.engine.impl.pvm.process.LaneSet;
import org.activiti.engine.impl.pvm.process.ParticipantProcess;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;

@Controller("/rs/activiti")
@Path("/rs/activiti")
public class ActivitiResource {
	private ProcessEngine processEngine;
	private RuntimeService runtimeService;
	private RepositoryServiceImpl repositoryService;
	private HistoryService historyService;
	private String processInstanceId;
	private String processDefinitionId;
	private ProcessInstance processInstance;
	private ProcessDefinitionEntity processDefinition;
	private List<String> highLightedFlows;
	private List<String> highLightedActivities;
	private Map<String, ObjectNode> subProcessInstanceMap;
	private List<String> historicActivityInstanceList;

	@GET
	@Path("diagram")
	public JSONPObject diagram(
			@QueryParam("processDefinitionId") String processDefinitionId,
			@QueryParam("processInstanceId") String processInstanceId,
			@QueryParam("callback") String callback) {
		init();
		this.processDefinitionId = processDefinitionId;
		this.processInstanceId = processInstanceId;

		ObjectNode diagram = getDiagram();

		return new JSONPObject(callback, diagram);
	}

	@SuppressWarnings("unchecked")
	private void getActivity(ActivityImpl activity, ArrayNode activityArray,
			ArrayNode sequenceFlowArray) {
		ObjectNode activityJSON = new ObjectMapper().createObjectNode();

		// Gather info on the multi instance marker
		String multiInstance = (String) activity.getProperty("multiInstance");

		if (multiInstance != null) {
			if (!"sequential".equals(multiInstance)) {
				multiInstance = "parallel";
			}
		}

		ActivityBehavior activityBehavior = activity.getActivityBehavior();

		// Gather info on the collapsed marker
		Boolean collapsed = (activityBehavior instanceof CallActivityBehavior);
		Boolean expanded = (Boolean) activity
				.getProperty(BpmnParse.PROPERTYNAME_ISEXPANDED);

		if (expanded != null) {
			collapsed = !expanded;
		}

		Boolean isInterrupting = null;

		if (activityBehavior instanceof BoundaryEventActivityBehavior) {
			isInterrupting = ((BoundaryEventActivityBehavior) activityBehavior)
					.isInterrupting();
		}

		// Outgoing transitions of activity
		for (PvmTransition sequenceFlow : activity.getOutgoingTransitions()) {
			String flowName = (String) sequenceFlow.getProperty("name");
			boolean isHighLighted = (highLightedFlows.contains(sequenceFlow
					.getId()));
			boolean isConditional = (sequenceFlow
					.getProperty(BpmnParse.PROPERTYNAME_CONDITION) != null)
					&& !((String) activity.getProperty("type")).toLowerCase()
							.contains("gateway");
			boolean isDefault = sequenceFlow.getId().equals(
					activity.getProperty("default"))
					&& ((String) activity.getProperty("type")).toLowerCase()
							.contains("gateway");

			List<Integer> waypoints = ((TransitionImpl) sequenceFlow)
					.getWaypoints();
			ArrayNode xPointArray = new ObjectMapper().createArrayNode();
			ArrayNode yPointArray = new ObjectMapper().createArrayNode();

			for (int i = 0; i < waypoints.size(); i += 2) {
				xPointArray.add(waypoints.get(i));
				yPointArray.add(waypoints.get(i + 1));
			}

			ObjectNode flowJSON = new ObjectMapper().createObjectNode();
			flowJSON.put("id", sequenceFlow.getId());
			flowJSON.put("name", flowName);
			flowJSON.put("flow", "(" + sequenceFlow.getSource().getId() + ")--"
					+ sequenceFlow.getId() + "-->("
					+ sequenceFlow.getDestination().getId() + ")");

			if (isConditional) {
				flowJSON.put("isConditional", isConditional);
			}

			if (isDefault) {
				flowJSON.put("isDefault", isDefault);
			}

			if (isHighLighted) {
				flowJSON.put("isHighLighted", isHighLighted);
			}

			flowJSON.set("xPointArray", xPointArray);
			flowJSON.set("yPointArray", yPointArray);

			sequenceFlowArray.add(flowJSON);
		}

		// Nested activities (boundary events)
		ArrayNode nestedActivityArray = new ObjectMapper().createArrayNode();

		for (ActivityImpl nestedActivity : activity.getActivities()) {
			nestedActivityArray.add(nestedActivity.getId());
		}

		Map<String, Object> properties = activity.getProperties();
		ObjectNode propertiesJSON = new ObjectMapper().createObjectNode();

		for (String key : properties.keySet()) {
			Object prop = properties.get(key);

			if (prop instanceof String) {
				propertiesJSON.put(key, (String) properties.get(key));
			} else if (prop instanceof Integer) {
				propertiesJSON.put(key, (Integer) properties.get(key));
			} else if (prop instanceof Boolean) {
				propertiesJSON.put(key, (Boolean) properties.get(key));
			} else if ("initial".equals(key)) {
				ActivityImpl act = (ActivityImpl) properties.get(key);
				propertiesJSON.put(key, act.getId());
			} else if ("timerDeclarations".equals(key)) {
				List<TimerDeclarationImpl> timerDeclarations = (ArrayList<TimerDeclarationImpl>) properties
						.get(key);
				ArrayNode timerDeclarationArray = new ObjectMapper()
						.createArrayNode();

				if (timerDeclarations != null) {
					for (TimerDeclarationImpl timerDeclaration : timerDeclarations) {
						ObjectNode timerDeclarationJSON = new ObjectMapper()
								.createObjectNode();

						timerDeclarationJSON.put("isExclusive",
								timerDeclaration.isExclusive());

						if (timerDeclaration.getRepeat() != null) {
							timerDeclarationJSON.put("repeat",
									timerDeclaration.getRepeat());
						}

						timerDeclarationJSON.put("retries",
								String.valueOf(timerDeclaration.getRetries()));
						timerDeclarationJSON.put("type",
								timerDeclaration.getJobHandlerType());
						timerDeclarationJSON.put("configuration",
								timerDeclaration.getJobHandlerConfiguration());
						// timerDeclarationJSON.put("expression",
						// timerDeclaration.getDescription());
						timerDeclarationArray.add(timerDeclarationJSON);
					}
				}

				if (timerDeclarationArray.size() > 0) {
					propertiesJSON.set(key, timerDeclarationArray);
				}

			} else if ("eventDefinitions".equals(key)) {
				ArrayList<EventSubscriptionDeclaration> eventDefinitions = (ArrayList<EventSubscriptionDeclaration>) properties
						.get(key);
				ArrayNode eventDefinitionsArray = new ObjectMapper()
						.createArrayNode();

				if (eventDefinitions != null) {
					for (EventSubscriptionDeclaration eventDefinition : eventDefinitions) {
						ObjectNode eventDefinitionJSON = new ObjectMapper()
								.createObjectNode();

						if (eventDefinition.getActivityId() != null) {
							eventDefinitionJSON.put("activityId",
									eventDefinition.getActivityId());
						}

						eventDefinitionJSON.put("eventName",
								eventDefinition.getEventName());
						eventDefinitionJSON.put("eventType",
								eventDefinition.getEventType());
						eventDefinitionJSON.put("isAsync",
								eventDefinition.isAsync());
						eventDefinitionJSON.put("isStartEvent",
								eventDefinition.isStartEvent());
						eventDefinitionsArray.add(eventDefinitionJSON);
					}
				}

				if (eventDefinitionsArray.size() > 0) {
					propertiesJSON.set(key, eventDefinitionsArray);
				}

			} else if ("errorEventDefinitions".equals(key)) {
				ArrayList<ErrorEventDefinition> errorEventDefinitions = (ArrayList<ErrorEventDefinition>) properties
						.get(key);
				ArrayNode errorEventDefinitionsArray = new ObjectMapper()
						.createArrayNode();

				if (errorEventDefinitions != null) {
					for (ErrorEventDefinition errorEventDefinition : errorEventDefinitions) {
						ObjectNode errorEventDefinitionJSON = new ObjectMapper()
								.createObjectNode();

						if (errorEventDefinition.getErrorCode() != null) {
							errorEventDefinitionJSON.put("errorCode",
									errorEventDefinition.getErrorCode());
						} else {
							errorEventDefinitionJSON.putNull("errorCode");
						}

						errorEventDefinitionJSON.put("handlerActivityId",
								errorEventDefinition.getHandlerActivityId());

						errorEventDefinitionsArray
								.add(errorEventDefinitionJSON);
					}
				}

				if (errorEventDefinitionsArray.size() > 0) {
					propertiesJSON.set(key, errorEventDefinitionsArray);
				}
			}
		}

		if ("callActivity".equals(properties.get("type"))) {
			CallActivityBehavior callActivityBehavior = null;

			if (activityBehavior instanceof CallActivityBehavior) {
				callActivityBehavior = (CallActivityBehavior) activityBehavior;
			}

			if (callActivityBehavior != null) {
				propertiesJSON.put("processDefinitonKey",
						callActivityBehavior.getProcessDefinitonKey());

				ArrayNode processInstanceArray = new ObjectMapper()
						.createArrayNode();

				if (processInstance != null) {
					List<Execution> executionList = runtimeService
							.createExecutionQuery()
							.processInstanceId(processInstanceId)
							.activityId(activity.getId()).list();

					if (executionList.size() > 0) {
						for (Execution execution : executionList) {
							ObjectNode processInstanceJSON = subProcessInstanceMap
									.get(execution.getId());
							processInstanceArray.add(processInstanceJSON);
						}
					}
				}

				// If active activities nas no instance of this callActivity
				// then add
				// last definition
				if (processInstanceArray.size() == 0) {
					// Get last definition by key
					ProcessDefinition lastProcessDefinition = repositoryService
							.createProcessDefinitionQuery()
							.processDefinitionKey(
									callActivityBehavior
											.getProcessDefinitonKey())
							.latestVersion().singleResult();

					ObjectNode processInstanceJSON = new ObjectMapper()
							.createObjectNode();
					processInstanceJSON.put("processDefinitionId",
							lastProcessDefinition.getId());
					processInstanceJSON.put("processDefinitionKey",
							lastProcessDefinition.getKey());
					processInstanceJSON.put("processDefinitionName",
							lastProcessDefinition.getName());
					processInstanceArray.add(processInstanceJSON);
				}

				if (processInstanceArray.size() > 0) {
					propertiesJSON.set("processDefinitons",
							processInstanceArray);
				}
			}
		}

		activityJSON.put("activityId", activity.getId());
		activityJSON.set("properties", propertiesJSON);

		if (multiInstance != null) {
			activityJSON.put("multiInstance", multiInstance);
		}

		if (collapsed) {
			activityJSON.put("collapsed", collapsed);
		}

		if (nestedActivityArray.size() > 0) {
			activityJSON.set("nestedActivities", nestedActivityArray);
		}

		if (isInterrupting != null) {
			activityJSON.put("isInterrupting", isInterrupting);
		}

		activityJSON.put("x", activity.getX());
		activityJSON.put("y", activity.getY());
		activityJSON.put("width", activity.getWidth());
		activityJSON.put("height", activity.getHeight());

		activityArray.add(activityJSON);

		// Nested activities (boundary events)
		for (ActivityImpl nestedActivity : activity.getActivities()) {
			getActivity(nestedActivity, activityArray, sequenceFlowArray);
		}
	}

	public ObjectNode getDiagram() {
		if (processInstanceId != null) {
			processInstance = runtimeService.createProcessInstanceQuery()
					.processInstanceId(processInstanceId).singleResult();

			if (processInstance == null) {
				ObjectNode processInstanceJSON = new ObjectMapper()
						.createObjectNode();
				return processInstanceJSON;
			}

			processDefinitionId = processInstance.getProcessDefinitionId();

			List<ProcessInstance> subProcessInstances = runtimeService
					.createProcessInstanceQuery()
					.superProcessInstanceId(processInstanceId).list();

			for (ProcessInstance subProcessInstance : subProcessInstances) {
				String subDefId = subProcessInstance.getProcessDefinitionId();

				String superExecutionId = ((ExecutionEntity) subProcessInstance)
						.getSuperExecutionId();
				ProcessDefinitionEntity subDef = (ProcessDefinitionEntity) repositoryService
						.getDeployedProcessDefinition(subDefId);

				ObjectNode processInstanceJSON = new ObjectMapper()
						.createObjectNode();
				processInstanceJSON.put("processInstanceId",
						subProcessInstance.getId());
				processInstanceJSON.put("superExecutionId", superExecutionId);
				processInstanceJSON.put("processDefinitionId", subDef.getId());
				processInstanceJSON
						.put("processDefinitionKey", subDef.getKey());
				processInstanceJSON.put("processDefinitionName",
						subDef.getName());

				subProcessInstanceMap
						.put(superExecutionId, processInstanceJSON);
			}
		}

		if (processDefinitionId == null) {
			throw new ActivitiException("No process definition id provided");
		}

		processDefinition = (ProcessDefinitionEntity) repositoryService
				.getDeployedProcessDefinition(processDefinitionId);

		if (processDefinition == null) {
			throw new ActivitiException("Process definition "
					+ processDefinitionId + " could not be found");
		}

		ObjectNode responseJSON = new ObjectMapper().createObjectNode();

		// Process definition
		JsonNode pdrJSON = getProcessDefinitionResponse(processDefinition);

		if (pdrJSON != null) {
			responseJSON.set("processDefinition", pdrJSON);
		}

		// Highlighted activities
		if (processInstance != null) {
			ArrayNode activityArray = new ObjectMapper().createArrayNode();
			ArrayNode flowsArray = new ObjectMapper().createArrayNode();

			highLightedActivities = runtimeService
					.getActiveActivityIds(processInstanceId);
			highLightedFlows = getHighLightedFlows();

			for (String activityName : highLightedActivities) {
				activityArray.add(activityName);
			}

			for (String flow : highLightedFlows){
				flowsArray.add(flow);
			}

			responseJSON.set("highLightedActivities", activityArray);
			responseJSON.set("highLightedFlows", flowsArray);
		}

		// Pool shape, if process is participant in collaboration
		if (processDefinition.getParticipantProcess() != null) {
			ParticipantProcess pProc = processDefinition
					.getParticipantProcess();

			ObjectNode participantProcessJSON = new ObjectMapper()
					.createObjectNode();
			participantProcessJSON.put("id", pProc.getId());

			if (StringUtils.isNotEmpty(pProc.getName())) {
				participantProcessJSON.put("name", pProc.getName());
			} else {
				participantProcessJSON.put("name", "");
			}

			participantProcessJSON.put("x", pProc.getX());
			participantProcessJSON.put("y", pProc.getY());
			participantProcessJSON.put("width", pProc.getWidth());
			participantProcessJSON.put("height", pProc.getHeight());

			responseJSON.set("participantProcess", participantProcessJSON);
		}

		// Draw lanes
		if ((processDefinition.getLaneSets() != null)
				&& (processDefinition.getLaneSets().size() > 0)) {
			ArrayNode laneSetArray = new ObjectMapper().createArrayNode();

			for (LaneSet laneSet : processDefinition.getLaneSets()) {
				ArrayNode laneArray = new ObjectMapper().createArrayNode();

				if ((laneSet.getLanes() != null)
						&& (laneSet.getLanes().size() > 0)) {
					for (Lane lane : laneSet.getLanes()) {
						ObjectNode laneJSON = new ObjectMapper()
								.createObjectNode();
						laneJSON.put("id", lane.getId());

						if (StringUtils.isNotEmpty(lane.getName())) {
							laneJSON.put("name", lane.getName());
						} else {
							laneJSON.put("name", "");
						}

						laneJSON.put("x", lane.getX());
						laneJSON.put("y", lane.getY());
						laneJSON.put("width", lane.getWidth());
						laneJSON.put("height", lane.getHeight());

						List<String> flowNodeIds = lane.getFlowNodeIds();
						ArrayNode flowNodeIdsArray = new ObjectMapper()
								.createArrayNode();

						for (String flowNodeId : flowNodeIds) {
							flowNodeIdsArray.add(flowNodeId);
						}

						laneJSON.set("flowNodeIds", flowNodeIdsArray);

						laneArray.add(laneJSON);
					}
				}

				ObjectNode laneSetJSON = new ObjectMapper().createObjectNode();
				laneSetJSON.put("id", laneSet.getId());

				if (StringUtils.isNotEmpty(laneSet.getName())) {
					laneSetJSON.put("name", laneSet.getName());
				} else {
					laneSetJSON.put("name", "");
				}

				laneSetJSON.set("lanes", laneArray);

				laneSetArray.add(laneSetJSON);
			}

			if (laneSetArray.size() > 0) {
				responseJSON.set("laneSets", laneSetArray);
			}
		}

		ArrayNode sequenceFlowArray = new ObjectMapper().createArrayNode();
		ArrayNode activityArray = new ObjectMapper().createArrayNode();

		// Activities and their sequence-flows
		for (ActivityImpl activity : processDefinition.getActivities()) {
			getActivity(activity, activityArray, sequenceFlowArray);
		}

		responseJSON.set("activities", activityArray);
		responseJSON.set("sequenceFlows", sequenceFlowArray);

		return responseJSON;
	}

	public ObjectNode getHighlighted() {
		if (processInstanceId == null) {
			throw new ActivitiException("No process instance id provided");
		}

		ObjectNode responseJSON = new ObjectMapper().createObjectNode();

		responseJSON.put("processInstanceId", processInstanceId);

		ArrayNode activitiesArray = new ObjectMapper().createArrayNode();
		ArrayNode flowsArray = new ObjectMapper().createArrayNode();

		try {
			processInstance = runtimeService.createProcessInstanceQuery()
					.processInstanceId(processInstanceId).singleResult();
			processDefinition = (ProcessDefinitionEntity) repositoryService
					.getDeployedProcessDefinition(processInstance
							.getProcessDefinitionId());

			responseJSON.put("processDefinitionId",
					processInstance.getProcessDefinitionId());

			List<String> highLightedActivities = runtimeService
					.getActiveActivityIds(processInstanceId);
			List<String> highLightedFlows = getHighLightedFlows(
					processDefinition, processInstanceId);

			for (String activityId : highLightedActivities)
				activitiesArray.add(activityId);

			for (String flow : highLightedFlows)
				flowsArray.add(flow);

			for (String activityId : highLightedActivities) {
				Execution execution = runtimeService
						.createExecutionQuery()
						.processInstanceId(
								processInstance.getProcessInstanceId())
						.activityId(activityId).singleResult();
				ExecutionEntity executionEntity = (ExecutionEntity) execution;
				executionEntity.getProcessDefinitionId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		responseJSON.set("activities", activitiesArray);
		responseJSON.set("flows", flowsArray);

		return responseJSON;
	}

	private void getHighlightedFlows(List<ActivityImpl> activityList) {
		for (ActivityImpl activity : activityList) {
			if (activity.getProperty("type").equals("subProcess")) {
				// get flows for the subProcess
				getHighlightedFlows(activity.getActivities());
			}

			if (historicActivityInstanceList.contains(activity.getId())) {
				List<PvmTransition> pvmTransitionList = activity
						.getOutgoingTransitions();

				for (PvmTransition pvmTransition : pvmTransitionList) {
					String destinationFlowId = pvmTransition.getDestination()
							.getId();

					if (historicActivityInstanceList
							.contains(destinationFlowId)) {
						highLightedFlows.add(pvmTransition.getId());
					}
				}
			}
		}
	}

	private List<String> getHighLightedFlows() {
		List<String> highLightedFlows = new java.util.ArrayList<String>();
		List<HistoricActivityInstance> historicActivityInstances = historyService
				.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId)
				.orderByHistoricActivityInstanceStartTime().asc().list();

		List<String> historicActivityInstanceList = new java.util.ArrayList<String>();

		for (HistoricActivityInstance hai : historicActivityInstances) {
			historicActivityInstanceList.add(hai.getActivityId());
		}

		// add current activities to list
		List<String> highLightedActivities = runtimeService
				.getActiveActivityIds(processInstanceId);
		historicActivityInstanceList.addAll(highLightedActivities);

		// activities and their sequence-flows
		for (ActivityImpl activity : processDefinition.getActivities()) {
			int index = historicActivityInstanceList.indexOf(activity.getId());

			if ((index >= 0)
					&& ((index + 1) < historicActivityInstanceList.size())) {
				List<PvmTransition> pvmTransitionList = activity
						.getOutgoingTransitions();

				for (PvmTransition pvmTransition : pvmTransitionList) {
					String destinationFlowId = pvmTransition.getDestination()
							.getId();

					if (destinationFlowId.equals(historicActivityInstanceList
							.get(index + 1))) {
						highLightedFlows.add(pvmTransition.getId());
					}
				}
			}
		}

		return highLightedFlows;
	}

	private List<String> getHighLightedFlows(
			ProcessDefinitionEntity processDefinition, String processInstanceId) {
		List<HistoricActivityInstance> historicActivityInstances = historyService
				.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId)
				.orderByHistoricActivityInstanceStartTime().asc().list();

		for (HistoricActivityInstance hai : historicActivityInstances) {
			historicActivityInstanceList.add(hai.getActivityId());
		}

		// add current activities to list
		List<String> highLightedActivities = runtimeService
				.getActiveActivityIds(processInstanceId);
		historicActivityInstanceList.addAll(highLightedActivities);

		// activities and their sequence-flows
		getHighlightedFlows(processDefinition.getActivities());

		return highLightedFlows;
	}

	private JsonNode getProcessDefinitionResponse(
			ProcessDefinitionEntity processDefinition) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode pdrJSON = mapper.createObjectNode();
		pdrJSON.put("id", processDefinition.getId());
		pdrJSON.put("name", processDefinition.getName());
		pdrJSON.put("key", processDefinition.getKey());
		pdrJSON.put("version", processDefinition.getVersion());
		pdrJSON.put("deploymentId", processDefinition.getDeploymentId());
		pdrJSON.put("isGraphicNotationDefined",
				isGraphicNotationDefined(processDefinition));

		return pdrJSON;
	}

	@GET
	@Path("highlighted")
	public JSONPObject highlighted(
			@QueryParam("processInstanceId") String processInstanceId,
			@QueryParam("callback") String callback) {
		init();
		this.processInstanceId = processInstanceId;

		ObjectNode highlighted = getHighlighted();

		return new JSONPObject(callback, highlighted);
	}

	private void init() {
		runtimeService = processEngine.getRuntimeService();
		historyService = processEngine.getHistoryService();
		repositoryService = (RepositoryServiceImpl) processEngine
				.getRepositoryService();
		processInstance = null;
		processDefinition = null;
		highLightedFlows = new java.util.ArrayList<String>();
		highLightedActivities = new java.util.ArrayList<String>();
		subProcessInstanceMap = new java.util.HashMap<String, ObjectNode>();
		historicActivityInstanceList = new java.util.ArrayList<String>();
	}

	private boolean isGraphicNotationDefined(
			ProcessDefinitionEntity processDefinition) {
		return ((ProcessDefinitionEntity) repositoryService
				.getDeployedProcessDefinition(processDefinition.getId()))
				.isGraphicalNotationDefined();
	}

	@javax.annotation.Resource
	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}
}
