/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.jbpm.context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.jbpm.JbpmContext;
import org.jpage.jbpm.datafield.DataField;

public class ProcessContext implements Serializable {
	private static final long serialVersionUID = 1L;

	private String rowId;

	private String title;

	private String opinion;

	private String actorId;

	private String processName;

	private String processInstanceId;

	private String taskInstanceId;

	private String nextStepId;

	private String runtimeAction;

	private String operation;

	private String jumpToNode;

	private String transitionName;

	private JbpmContext jbpmContext;

	private Map contextMap = new HashMap();

	private Collection agentIds = new ArrayList();

	private Collection dataFields = new ArrayList();

	private Collection dataInstances = new HashSet();

	public ProcessContext() {

	}

	public void addDataField(DataField df) {
		if (dataFields == null) {
			dataFields = new ArrayList();
		}
		dataFields.add(df);
	}

	public String getActorId() {
		return actorId;
	}

	public Collection getAgentIds() {
		return agentIds;
	}

	public Map getContextMap() {
		return contextMap;
	}

	public Collection getDataFields() {
		return dataFields;
	}

	public Collection getDataInstances() {
		return dataInstances;
	}

	public JbpmContext getJbpmContext() {
		return jbpmContext;
	}

	public String getJumpToNode() {
		return jumpToNode;
	}

	public String getNextStepId() {
		return nextStepId;
	}

	public String getOperation() {
		return operation;
	}

	public String getOpinion() {
		return opinion;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public String getProcessName() {
		return processName;
	}

	public String getRowId() {
		return rowId;
	}

	public String getRuntimeAction() {
		return runtimeAction;
	}

	public String getTaskInstanceId() {
		return taskInstanceId;
	}

	public String getTitle() {
		return title;
	}

	public String getTransitionName() {
		return transitionName;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setAgentIds(Collection agentIds) {
		this.agentIds = agentIds;
	}

	public void setContextMap(Map map) {
		if (map != null) {
			this.contextMap.putAll(map);
		}
	}

	public void setDataFields(Collection dataFields) {
		this.dataFields = dataFields;
	}

	public void setDataInstances(Collection dataInstances) {
		this.dataInstances = dataInstances;
	}

	public void setJbpmContext(JbpmContext jbpmContext) {
		this.jbpmContext = jbpmContext;
	}

	public void setJumpToNode(String jumpToNode) {
		this.jumpToNode = jumpToNode;
	}

	public void setNextStepId(String nextStepId) {
		this.nextStepId = nextStepId;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public void setRuntimeAction(String runtimeAction) {
		this.runtimeAction = runtimeAction;
	}

	public void setTaskInstanceId(String taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
