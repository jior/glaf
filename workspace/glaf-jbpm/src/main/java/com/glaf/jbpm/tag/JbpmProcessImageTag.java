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

package com.glaf.jbpm.tag;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.xpath.DefaultXPath;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

import com.glaf.core.config.Configuration;
import com.glaf.jbpm.config.JbpmBaseConfiguration;
import com.glaf.jbpm.config.JbpmProcessConfig;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;

public class JbpmProcessImageTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private static Configuration conf = JbpmBaseConfiguration.create();

	private long processInstanceId = -1;

	private long tokenInstanceId = -1;

	private byte[] gpdBytes = null;

	private byte[] imageBytes = null;

	private Token currentToken = null;

	private String contextPath = null;

	private ProcessDefinition processDefinition = null;

	private ProcessInstance processInstance = null;

	private String currentTokenColor = "red";

	private String childTokenColor = "#0099FF";

	// private String tokenNameColor = "green";

	public void release() {
		gpdBytes = null;
		imageBytes = null;
		currentToken = null;
		processInstanceId = -1;
	}

	public int doEndTag() throws JspException {
		JbpmContext jbpmContext = null;
		HttpServletRequest request = null;
		try {
			request = (HttpServletRequest) pageContext.getRequest();
			contextPath = request.getContextPath();
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			initialize(jbpmContext);
			if (processInstance != null && processDefinition != null) {
				retrieveByteArrays();
				if (gpdBytes != null && imageBytes != null) {
					writeTable();
				}
			}
		} catch (IOException e) {
			throw new JspException("table couldn't be displayed", e);
		} catch (DocumentException e) {
			throw new JspException("table couldn't be displayed", e);
		} finally {
			release();
			Context.close(jbpmContext);
		}
		return EVAL_PAGE;
	}

	private void retrieveByteArrays() {
		try {
			gpdBytes = JbpmProcessConfig.getGpd(processDefinition.getId());
			imageBytes = JbpmProcessConfig.getImage(processDefinition.getId());
		} catch (Exception ex) {
		}
	}

	private void writeTable() throws IOException, DocumentException {
		int borderWidth = 4;
		Element rootDiagramElement = DocumentHelper.parseText(
				new String(gpdBytes)).getRootElement();
		int[] boxConstraint;
		int[] imageDimension = extractImageDimension(rootDiagramElement);
		String imagePath = contextPath + "/mx/jbpm/image";
		if (conf.get("jbpm.processImageUrl") != null) {
			imagePath = contextPath + conf.get("jbpm.processImageUrl");
		}
		String imageLink = imagePath + "?processDefinitionId="
				+ processDefinition.getId();
		JspWriter jspOut = pageContext.getOut();

		if (tokenInstanceId > 0) {
			List<Token> allTokens = new java.util.ArrayList<Token>();
			walkTokens(currentToken, allTokens);
			jspOut.println("<div style='position:relative; background-image:url("
					+ imageLink
					+ ");background-repeat:no-repeat; width: "
					+ imageDimension[0]
					+ "px; height: "
					+ imageDimension[1]
					+ "px;'>");

			for (int i = 0; i < allTokens.size(); i++) {
				Token token = allTokens.get(i);
				if (!token.getProcessInstance().hasEnded()) {
					if (!token.isAbleToReactivateParent()) {
						continue;
					}
				}
				// check how many tokens are on teh same level (= having the
				// same parent)
				int offset = i;
				if (i > 0) {
					while (offset > 0
							&& (allTokens.get(offset - 1)).getParent().equals(
									token.getParent())) {
						offset--;
					}
				}
				boxConstraint = extractBoxConstraint(rootDiagramElement, token);

				// Adjust for borders
				// boxConstraint[2] -= borderWidth * 2;
				// boxConstraint[3] -= borderWidth * 2;

				jspOut.println("<div style='position:absolute; left: "
						+ boxConstraint[0] + "px; top: " + boxConstraint[1]
						+ "px; ");

				if (i == (allTokens.size() - 1)) {
					jspOut.println("border: " + currentTokenColor);
				} else {
					if (StringUtils.isNotEmpty(childTokenColor)) {
						jspOut.println("border: " + childTokenColor);
					}
				}

				jspOut.println(" " + borderWidth + "px groove; " + "width: "
						+ (boxConstraint[2] - 2) + "px; height: "
						+ (boxConstraint[3] - 2) + "px;'>");

				jspOut.println("</div>");
			}
			jspOut.println("</div>");
		} else {
			boxConstraint = extractBoxConstraint(rootDiagramElement);

			jspOut.println("<table border=0 cellspacing=0 cellpadding=0 width="
					+ imageDimension[0] + " height=" + imageDimension[1] + ">");
			jspOut.println("  <tr>");
			jspOut.println("    <td width=" + imageDimension[0] + " height="
					+ imageDimension[1] + " style=\"background-image:url("
					+ imageLink + ")\" valign=top>");
			jspOut.println("      <table border=0 cellspacing=0 cellpadding=0>");
			jspOut.println("        <tr>");
			jspOut.println("          <td width="
					+ (boxConstraint[0] - borderWidth) + " height="
					+ (boxConstraint[1] - borderWidth)
					+ " style=\"background-color:transparent;\"></td>");
			jspOut.println("        </tr>");
			jspOut.println("        <tr>");
			jspOut.println("          <td style=\"background-color:transparent;\"></td>");
			jspOut.println("          <td style=\"border-color:"
					+ currentTokenColor
					+ "; border-width:"
					+ borderWidth
					+ "px; border-style:groove; background-color:transparent;\" width="
					+ boxConstraint[2] + " height="
					+ (boxConstraint[3] + (2 * borderWidth)) + ">&nbsp;</td>");
			jspOut.println("        </tr>");
			jspOut.println("      </table>");
			jspOut.println("    </td>");
			jspOut.println("  </tr>");
			jspOut.println("</table>");
		}
	}

	private int[] extractBoxConstraint(Element root) {
		int[] result = new int[4];
		String nodeName = currentToken.getNode().getName();
		XPath xPath = new DefaultXPath("//node[@name='" + nodeName + "']");
		Element node = (Element) xPath.selectSingleNode(root);
		if (node != null) {
			result[0] = Integer.parseInt(node.attribute("x").getValue());
			result[1] = Integer.parseInt(node.attribute("y").getValue());
			result[2] = Integer.parseInt(node.attribute("width").getValue());
			result[3] = Integer.parseInt(node.attribute("height").getValue());
		}
		return result;
	}

	private int[] extractBoxConstraint(Element root, Token token) {
		int[] result = new int[4];
		String nodeName = token.getNode().getName();
		XPath xPath = new DefaultXPath("//node[@name='" + nodeName + "']");
		Element node = (Element) xPath.selectSingleNode(root);
		if (node != null) {
			result[0] = Integer.parseInt(node.attribute("x").getValue());
			result[1] = Integer.parseInt(node.attribute("y").getValue());
			result[2] = Integer.parseInt(node.attribute("width").getValue());
			result[3] = Integer.parseInt(node.attribute("height").getValue());
		}
		return result;
	}

	private int[] extractImageDimension(Element root) {
		int[] result = new int[2];
		result[0] = Integer.parseInt(root.attribute("width").getValue());
		result[1] = Integer.parseInt(root.attribute("height").getValue());
		return result;
	}

	private void initialize(JbpmContext jbpmContext) {
		if (this.processInstanceId > 0) {
			processInstance = jbpmContext.getProcessInstance(processInstanceId);
			if (processInstance != null) {
				currentToken = processInstance.getRootToken();
				tokenInstanceId = currentToken.getId();
				processDefinition = currentToken.getProcessInstance()
						.getProcessDefinition();
				if (processInstance.hasEnded()) {
					childTokenColor = "";
				}
			}
		}
	}

	private void walkTokens(Token parent, List<Token> allTokens) {
		Map<String, Token> children = parent.getChildren();
		if (children != null && children.size() > 0) {
			Collection<Token> childTokens = children.values();
			for (Iterator<Token> iterator = childTokens.iterator(); iterator
					.hasNext();) {
				Token child = iterator.next();
				walkTokens(child, allTokens);
			}
		}
		allTokens.add(parent);
	}

	public long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(long id) {
		this.processInstanceId = id;
	}

}