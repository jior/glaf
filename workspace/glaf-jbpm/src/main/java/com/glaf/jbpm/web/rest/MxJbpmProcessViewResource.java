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

package com.glaf.jbpm.web.rest;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.xpath.DefaultXPath;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.jbpm.config.JbpmProcessConfig;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;
import com.glaf.jbpm.manager.JbpmTaskManager;

@Controller("/rs/jbpm/view")
@Path("/rs/jbpm/view")
public class MxJbpmProcessViewResource {

	protected JbpmTaskManager jbpmTaskManager;

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

	@GET
	@POST
	@Path("detail/{processInstanceId}")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@ResponseBody
	public byte[] detail(@javax.ws.rs.core.Context HttpServletRequest request,
			@PathParam("processInstanceId") String pid) {
		if (StringUtils.isNotEmpty(pid)) {
			processInstanceId = Long.parseLong(pid);
			JbpmContext jbpmContext = null;
			try {
				contextPath = request.getContextPath();
				jbpmContext = ProcessContainer.getContainer()
						.createJbpmContext();
				initialize(jbpmContext);
				if (processInstance != null && processDefinition != null) {
					retrieveByteArrays();
					if (gpdBytes != null && imageBytes != null) {
						Writer writer = writeTable();
						return writer.toString().getBytes("UTF-8");
					}
				}
			} catch (Exception ex) {
				throw new RuntimeException("table couldn't be displayed", ex);
			} finally {
				release();
				Context.close(jbpmContext);
			}
		}
		return null;
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

	public JbpmTaskManager getJbpmTaskManager() {
		jbpmTaskManager = ProcessContainer.getContainer().getJbpmTaskManager();
		return jbpmTaskManager;
	}

	public long getProcessInstanceId() {
		return processInstanceId;
	}

	@GET
	@POST
	@Path("image/{processDefinitionId}")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@ResponseBody
	public byte[] image(
			@PathParam("processDefinitionId") String processDefinitionId) {
		byte[] bytes = null;
		try {
			if (StringUtils.isNotEmpty(processDefinitionId)
					&& StringUtils.isNumeric(processDefinitionId)) {
				bytes = JbpmProcessConfig.getImage(Long
						.parseLong(processDefinitionId));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bytes;
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

	private void release() {
		gpdBytes = null;
		imageBytes = null;
		currentToken = null;
		processInstanceId = -1;
		processDefinition = null;
		processInstance = null;
		currentToken = null;
	}

	private void retrieveByteArrays() {
		try {
			gpdBytes = JbpmProcessConfig.getGpd(processDefinition.getId());
			imageBytes = JbpmProcessConfig.getImage(processDefinition.getId());
		} catch (Exception ex) {
		}
	}

	public void setJbpmTaskManager(JbpmTaskManager jbpmTaskManager) {
		this.jbpmTaskManager = jbpmTaskManager;
	}

	public void setProcessInstanceId(long id) {
		this.processInstanceId = id;
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

	private Writer writeTable() throws IOException, DocumentException {
		int borderWidth = 4;
		Element rootDiagramElement = DocumentHelper.parseText(
				new String(gpdBytes)).getRootElement();
		int[] boxConstraint;
		int[] imageDimension = extractImageDimension(rootDiagramElement);
		String imageLink = contextPath + "/rs/jbpm/view/image/"
				+ processDefinition.getId();
		Writer writer = new StringWriter();

		if (tokenInstanceId > 0) {
			List<Token> allTokens = new java.util.concurrent.CopyOnWriteArrayList<Token>();
			walkTokens(currentToken, allTokens);
			writer.write("<div style='position:relative; background-image:url("
					+ imageLink + ");background-repeat:no-repeat; width: "
					+ imageDimension[0] + "px; height: " + imageDimension[1]
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

				writer.write("<div style='position:absolute; left: "
						+ boxConstraint[0] + "px; top: " + boxConstraint[1]
						+ "px; ");

				if (i == (allTokens.size() - 1)) {
					writer.write("border: " + currentTokenColor);
				} else {
					if (StringUtils.isNotEmpty(childTokenColor)) {
						writer.write("border: " + childTokenColor);
					}
				}

				writer.write(" " + borderWidth + "px groove; " + "width: "
						+ boxConstraint[2] + "px; height: " + boxConstraint[3]
						+ "px;'>");

				/**
				 * if (token.getName() != null) { jspOut
				 * .println("<span style='color:" + tokenNameColor +
				 * ";font-size:12px;font-weight:bold;font-style:italic;position:absolute;left:"
				 * + (boxConstraint[2] + 10) + "px;top:" + ((i - offset) * 20) +
				 * ";'>&nbsp;" + token.getName() + "</span>"); }
				 */

				writer.write("</div>");
			}
			writer.write("</div>");
		} else {
			boxConstraint = extractBoxConstraint(rootDiagramElement);

			writer.write("<table border=0 cellspacing=0 cellpadding=0 width="
					+ imageDimension[0] + " height=" + imageDimension[1] + ">");
			writer.write("  <tr>");
			writer.write("    <td width=" + imageDimension[0] + " height="
					+ imageDimension[1] + " style=\"background-image:url("
					+ imageLink + ")\" valign=top>");
			writer.write("      <table border=0 cellspacing=0 cellpadding=0>");
			writer.write("        <tr>");
			writer.write("          <td width="
					+ (boxConstraint[0] - borderWidth) + " height="
					+ (boxConstraint[1] - borderWidth)
					+ " style=\"background-color:transparent;\"></td>");
			writer.write("        </tr>");
			writer.write("        <tr>");
			writer.write("          <td style=\"background-color:transparent;\"></td>");
			writer.write("          <td style=\"border-color:"
					+ currentTokenColor
					+ "; border-width:"
					+ borderWidth
					+ "px; border-style:groove; background-color:transparent;\" width="
					+ boxConstraint[2] + " height="
					+ (boxConstraint[3] + (2 * borderWidth)) + ">&nbsp;</td>");
			writer.write("        </tr>");
			writer.write("      </table>");
			writer.write("    </td>");
			writer.write("  </tr>");
			writer.write("</table>");
		}
		return writer;
	}

}