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

package com.glaf.jbpm.action;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

import com.glaf.core.util.StringTools;
import com.glaf.jbpm.el.DefaultExpressionEvaluator;
import com.glaf.jbpm.util.Constant;

/**
 * ˳������ʵ��������<br/>
 * ������A�ڵ����task02���񣬼�����������ֱ���X,Y,Z ���赱ǰ���õ����ȼ���Y,Z,X��<br/>
 * ���Ȱ�A��create-tasks="false", Ȼ���node-enter�¼�<br/>
 * ���¼����ȴ�����һ������task02��taskInstance,�ڴ�������ʱ���Զ������taskInstance������ִ���� ��Y,Z,X��<br/>
 * ���ֻ����Y��taskInstance�� ��ʱֻ��Y������������task02�� <br/>
 * ��Y������֮�� ��task02�ϼ�һ��task-end�¼��� Ȼ�󴴽�Z��taskInstance���������ơ�<br/>
 * 
 */
public class SequenceTaskInstanceAction implements ActionHandler {

	private static final long serialVersionUID = 1L;

	/**
	 * ͨ���ж�����
	 */
	protected String expression;

	/**
	 * ��̬���õĲ����ߵĲ�������������������ͨ��contextInstance.getVariable()ȡ��
	 * ���磺contextInstance.getVariable("DeptAuditor");
	 */
	protected String dynamicActors;

	/**
	 * ��������
	 */
	protected String taskName;

	/**
	 * ת��·��������
	 */
	protected String transitionName;

	/**
	 * �����һ����ͨ�����뿪���ڵ㣨һƱ�����������������˾�����
	 */
	protected boolean leaveNodeIfActorNotAgree;

	/**
	 * ������ܻ�ȡ����������Ƿ��뿪���ڵ㣨����ڵ㣩
	 */
	protected boolean leaveNodeIfActorNotAvailable;

	public void execute(ExecutionContext ctx) throws Exception {
		boolean isAgree = true;

		Map<String, Object> params = new java.util.concurrent.ConcurrentHashMap<String, Object>();

		ContextInstance contextInstance = ctx.getContextInstance();
		Map<String, Object> variables = contextInstance.getVariables();
		if (variables != null && variables.size() > 0) {
			Iterator<String> iterator = variables.keySet().iterator();
			while (iterator.hasNext()) {
				String variableName = iterator.next();
				if (params.get(variableName) == null) {
					Object value = contextInstance.getVariable(variableName);
					params.put(variableName, value);
				}
			}
		}

		if (StringUtils.isNotEmpty(expression)) {
			if (expression.startsWith("#{") && expression.endsWith("}")) {
				Object value = DefaultExpressionEvaluator.evaluate(expression,
						params);
				if (value != null) {
					if (value instanceof Boolean) {
						Boolean b = (Boolean) value;
						isAgree = b.booleanValue();
					}
				}
			}
		}

		/**
		 * ������һ������˲�ͨ������������һƱ���
		 */
		if ((!isAgree) && leaveNodeIfActorNotAgree) {
			if (StringUtils.isNotEmpty(transitionName)) {
				ctx.leaveNode(transitionName);
			} else {
				ctx.leaveNode();
			}
			return;
		}

		boolean hasActors = false;

		List<String> actorIds = null;

		Task task = null;
		if (StringUtils.isNotEmpty(taskName)) {
			Node node = ctx.getNode();
			if (node instanceof TaskNode) {
				TaskNode taskNode = (TaskNode) node;
				task = taskNode.getTask(taskName);
			}
		}

		if (task == null) {
			task = ctx.getTask();
		}

		if (StringUtils.isNotEmpty(dynamicActors)) {
			String rowIds = (String) contextInstance.getVariable(dynamicActors);
			if (StringUtils.isNotEmpty(rowIds)) {
				actorIds = StringTools.split(rowIds);
			}
		}

		if (actorIds != null) {
			if (actorIds.size() > 0) {
				String actorId = actorIds.get(0);
				Token token = ctx.getToken();
				TaskMgmtInstance tmi = ctx.getTaskMgmtInstance();
				TaskInstance taskInstance = tmi.createTaskInstance(task, token);
				taskInstance.setActorId(actorId);
				taskInstance.setCreate(new Date());
				taskInstance.setSignalling(task.isSignalling());
				actorIds.remove(0);
				hasActors = true;
			}

			if (actorIds.size() > 0) {
				StringBuilder buffer = new StringBuilder();
				String delim = "";
				for (String element : actorIds) {
					buffer.append(delim);
					buffer.append(element);
					delim = ",";
				}
				contextInstance.setVariable(dynamicActors, buffer.toString());
			}
		}

		if (!hasActors) {
			if (leaveNodeIfActorNotAvailable) {
				contextInstance.setVariable(Constant.IS_AGREE, "true");
				if (StringUtils.isNotEmpty(transitionName)) {
					ctx.leaveNode(transitionName);
				} else {
					ctx.leaveNode();
				}
			}
		}
	}

	public String getDynamicActors() {
		return dynamicActors;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTransitionName() {
		return transitionName;
	}

	public boolean isLeaveNodeIfActorNotAgree() {
		return leaveNodeIfActorNotAgree;
	}

	public boolean isLeaveNodeIfActorNotAvailable() {
		return leaveNodeIfActorNotAvailable;
	}

	public void setDynamicActors(String dynamicActors) {
		this.dynamicActors = dynamicActors;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setLeaveNodeIfActorNotAgree(boolean leaveNodeIfActorNotAgree) {
		this.leaveNodeIfActorNotAgree = leaveNodeIfActorNotAgree;
	}

	public void setLeaveNodeIfActorNotAvailable(
			boolean leaveNodeIfActorNotAvailable) {
		this.leaveNodeIfActorNotAvailable = leaveNodeIfActorNotAvailable;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

}