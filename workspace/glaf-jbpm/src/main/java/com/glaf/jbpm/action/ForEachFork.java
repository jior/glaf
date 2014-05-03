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

import java.util.Collection;
import java.util.List;

import org.dom4j.Element;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.instantiation.FieldInstantiator;
import org.jbpm.jpdl.el.impl.JbpmExpressionEvaluator;

/**
 * 
 * <node name="startreview"> <br>
 * <action class="com.glaf.jbpm.action.ForEachFork"><br>
 * <foreach>#{reviewers}</foreach><br>
 * <var>reviewer</var> <br>
 * </action><br>
 * <transition name="review" to="review" /> <br>
 * </node>
 * 
 */
public class ForEachFork implements ActionHandler {
	/**
	 * Fork Transition
	 */
	private static class ForkedTransition {
		protected ExecutionContext executionContext;
		protected Transition transition;
	}

	private static final long serialVersionUID = 4643103713602441652L;
	private transient Element foreach;

	private String var;

	/**
	 * Create a new child token for each item in list.
	 * 
	 * @param executionContext
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public void execute(final ExecutionContext executionContext)
			throws Exception {
		if (foreach == null) {
			throw new Exception("forEach has not been provided");
		}

		// build "for each" collection
		List<String> forEachColl = null;
		String forEachCollStr = foreach.getTextTrim();
		if (forEachCollStr != null) {
			if (forEachCollStr.startsWith("#{")) {
				Object eval = JbpmExpressionEvaluator.evaluate(forEachCollStr,
						executionContext);
				if (eval == null) {
					throw new Exception("forEach expression '" + forEachCollStr
							+ "' evaluates to null");
				}

				// expression evaluates to string
				if (eval instanceof String) {
					String[] forEachStrs = ((String) eval).trim().split(",");
					forEachColl = new java.util.ArrayList<String>();
					for (String forEachStr : forEachStrs) {
						forEachColl.add(forEachStr);
					}
				}

				// expression evaluates to collection
				else if (eval instanceof Collection<?>) {
					forEachColl = (List<String>) eval;
				}
			}
		} else {
			forEachColl = (List<String>) FieldInstantiator.getValue(List.class,
					foreach);
		}

		if (var == null || var.length() == 0) {
			throw new Exception("forEach variable name has not been provided");
		}

		//
		// create forked paths
		//

		Token rootToken = executionContext.getToken();
		Node node = executionContext.getNode();
		List<ForkedTransition> forkTransitions = new java.util.ArrayList<ForkedTransition>();

		// first, create a new token and execution context for each item in list
		for (int i = 0; i < node.getLeavingTransitions().size(); i++) {
			Transition transition = node.getLeavingTransitions().get(i);

			for (int iVar = 0; iVar < forEachColl.size(); iVar++) {
				// create child token to represent new path
				String tokenName = getTokenName(rootToken,
						transition.getName(), iVar);
				Token loopToken = new Token(rootToken, tokenName);
				loopToken.setTerminationImplicit(true);
				executionContext.getJbpmContext().getSession().save(loopToken);

				// assign variable within path
				final ExecutionContext newExecutionContext = new ExecutionContext(
						loopToken);
				newExecutionContext.getContextInstance().createVariable(var,
						forEachColl.get(iVar), loopToken);

				// record path & transition
				ForkedTransition forkTransition = new ForkedTransition();
				forkTransition.executionContext = newExecutionContext;
				forkTransition.transition = transition;
				forkTransitions.add(forkTransition);
			}
		}

		//
		// let each new token leave the node.
		//
		for (ForkedTransition forkTransition : forkTransitions) {
			node.leave(forkTransition.executionContext,
					forkTransition.transition);
		}
	}

	public Element getForeach() {
		return foreach;
	}

	/**
	 * Create a token name
	 * 
	 * @param parent
	 * @param transitionName
	 * @return
	 */
	protected String getTokenName(Token parent, String transitionName,
			int loopIndex) {
		String tokenName = null;
		if (transitionName != null) {
			if (!parent.hasChild(transitionName)) {
				tokenName = transitionName;
			} else {
				int i = 2;
				tokenName = transitionName + Integer.toString(i);
				while (parent.hasChild(tokenName)) {
					i++;
					tokenName = transitionName + Integer.toString(i);
				}
			}
		} else {
			// no transition name
			int size = (parent.getChildren() != null ? parent.getChildren()
					.size() + 1 : 1);
			tokenName = Integer.toString(size);
		}
		return tokenName + "." + loopIndex;
	}

	public String getVar() {
		return var;
	}

	public void setForeach(Element foreach) {
		this.foreach = foreach;
	}

	public void setVar(String var) {
		this.var = var;
	}

}