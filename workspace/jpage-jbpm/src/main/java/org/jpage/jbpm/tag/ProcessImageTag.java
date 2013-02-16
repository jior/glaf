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

package org.jpage.jbpm.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.xpath.DefaultXPath;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.file.def.FileDefinition;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jpage.core.cache.CacheFactory;
import org.jpage.jbpm.context.Context;

public class ProcessImageTag extends TagSupport {

	private JbpmConfiguration jbpmConfiguration = JbpmConfiguration
			.getInstance();

	private static final long serialVersionUID = 1L;

	private long taskInstanceId = -1;

	private long tokenInstanceId = -1;

	private byte[] gpdBytes = null;

	private Token currentToken = null;

	private ProcessDefinition processDefinition = null;

	private ProcessInstance processInstance = null;

	static String currentTokenColor = "red";

	static String childTokenColor = "#0099FF";

	static String tokenNameColor = "green";

	public void release() {
		taskInstanceId = -1;
		gpdBytes = null;

		currentToken = null;
	}

	public int doEndTag() throws JspException {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = jbpmConfiguration.createJbpmContext();
			initialize(jbpmContext);
			retrieveByteArrays();
			if (gpdBytes != null) {
				writeTable();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new JspException("table couldn't be displayed", e);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new JspException("table couldn't be displayed", e);
		} finally {
			release();
			Context.close(jbpmContext);
		}
		return EVAL_PAGE;
	}

	private void retrieveByteArrays() {
		try {
			FileDefinition fileDefinition = null;
			String cacheKey = "fd_" + String.valueOf(processDefinition.getId());
			if (CacheFactory.get(cacheKey) != null) {
				fileDefinition = (FileDefinition) CacheFactory.get(cacheKey);
			} else {
				fileDefinition = processDefinition.getFileDefinition();
				CacheFactory.put(cacheKey, fileDefinition);
			}
			String cacheKey01 = "gpd_"
					+ String.valueOf(processDefinition.getId());

			if (CacheFactory.get(cacheKey01) != null) {
				String str = (String) CacheFactory.get(cacheKey01);
				gpdBytes = Base64.decodeBase64(str);
			} else {
				gpdBytes = fileDefinition.getBytes("gpd.xml");
				String str = Base64.encodeBase64String(gpdBytes);
				CacheFactory.put(cacheKey01, str);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeTable() throws IOException, DocumentException {
		int borderWidth = 4;
		Element rootDiagramElement = DocumentHelper.parseText(
				new String(gpdBytes)).getRootElement();
		int[] boxConstraint;
		int[] imageDimension = extractImageDimension(rootDiagramElement);
		String imageLink = "processimage?definitionId="
				+ processDefinition.getId();
		JspWriter jspOut = pageContext.getOut();

		if (tokenInstanceId > 0) {
			List allTokens = new ArrayList();
			walkTokens(currentToken, allTokens);
			jspOut.println("<div style='position:relative; background-image:url("
					+ imageLink
					+ ");background-repeat:no-repeat; width: "
					+ imageDimension[0]
					+ "px; height: "
					+ imageDimension[1]
					+ "px;'>");

			for (int i = 0; i < allTokens.size(); i++) {
				Token token = (Token) allTokens.get(i);
				// check how many tokens are on teh same level (= having the
				// same parent)
				int offset = i;
				if (i > 0) {
					while (offset > 0
							&& ((Token) allTokens.get(offset - 1)).getParent()
									.equals(token.getParent())) {
						offset--;
					}
				}
				boxConstraint = extractBoxConstraint(rootDiagramElement, token);

				// Adjust for borders
				// boxConstraint[2]-=borderWidth*2;
				// boxConstraint[3]-=borderWidth*2;

				jspOut.println("<div style='position:absolute; left: "
						+ boxConstraint[0] + "px; top: " + boxConstraint[1]
						+ "px; ");

				if (i == (allTokens.size() - 1)) {
					jspOut.println("border: " + currentTokenColor);
				} else {
					jspOut.println("border: " + childTokenColor);
				}

				jspOut.println(" " + borderWidth + "px groove; " + "width: "
						+ boxConstraint[2] + "px; height: " + boxConstraint[3]
						+ "px;'>");

				if (token.getName() != null) {
					jspOut.println("<span style='color:"
							+ tokenNameColor
							+ ";font-size:12px;font-weight:bold;font-style:italic;position:absolute;left:"
							+ (boxConstraint[2] + 10) + "px;top:"
							+ ((i - offset) * 20) + ";'>&nbsp;"
							+ token.getName() + "</span>");
				}

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
		result[0] = Integer.valueOf(node.attribute("x").getValue()).intValue();
		result[1] = Integer.valueOf(node.attribute("y").getValue()).intValue();
		result[2] = Integer.valueOf(node.attribute("width").getValue())
				.intValue();
		result[3] = Integer.valueOf(node.attribute("height").getValue())
				.intValue();
		return result;
	}

	private int[] extractBoxConstraint(Element root, Token token) {
		int[] result = new int[4];
		String nodeName = token.getNode().getName();
		XPath xPath = new DefaultXPath("//node[@name='" + nodeName + "']");
		Element node = (Element) xPath.selectSingleNode(root);
		result[0] = Integer.valueOf(node.attribute("x").getValue()).intValue();
		result[1] = Integer.valueOf(node.attribute("y").getValue()).intValue();
		result[2] = Integer.valueOf(node.attribute("width").getValue())
				.intValue();
		result[3] = Integer.valueOf(node.attribute("height").getValue())
				.intValue();
		return result;
	}

	private int[] extractImageDimension(Element root) {
		int[] result = new int[2];
		result[0] = Integer.valueOf(root.attribute("width").getValue())
				.intValue();
		result[1] = Integer.valueOf(root.attribute("height").getValue())
				.intValue();
		return result;
	}

	private void initialize(JbpmContext jbpmContext) {

		if (this.taskInstanceId > 0) {
			TaskInstance taskInstance = jbpmContext.getTaskMgmtSession()
					.loadTaskInstance(taskInstanceId);
			currentToken = taskInstance.getToken();
		} else {
			if (this.tokenInstanceId > 0) {
				currentToken = jbpmContext.getGraphSession().loadToken(
						this.tokenInstanceId);
			}
		}

		if (currentToken != null) {
			processInstance = currentToken.getProcessInstance();
		}

		if (processInstance != null) {
			processDefinition = currentToken.getProcessInstance()
					.getProcessDefinition();
		}
	}

	private void walkTokens(Token parent, List allTokens) {
		Map children = parent.getChildren();
		if (children != null && children.size() > 0) {
			Collection childTokens = children.values();
			for (Iterator iterator = childTokens.iterator(); iterator.hasNext();) {
				Token child = (Token) iterator.next();
				walkTokens(child, allTokens);
			}
		}

		allTokens.add(parent);
	}

	public void setTask(long id) {
		this.taskInstanceId = id;
	}

	public void setToken(long id) {
		this.tokenInstanceId = id;
	}

}
