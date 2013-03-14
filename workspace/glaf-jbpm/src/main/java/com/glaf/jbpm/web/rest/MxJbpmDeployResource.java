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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.file.def.FileDefinition;
import org.jbpm.graph.def.ProcessDefinition;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;

import com.glaf.core.util.AntUtils;
import com.glaf.core.util.Dom4jUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.jbpm.config.JbpmExtensionWriter;
import com.glaf.jbpm.container.ProcessContainer;

import com.glaf.jbpm.deploy.MxJbpmProcessDeployer;
import com.glaf.jbpm.model.Extension;
import com.glaf.jbpm.manager.JbpmExtensionManager;

@Controller("/rs/jbpm/deploy")
@Path("/rs/jbpm/deploy")
public class MxJbpmDeployResource {
	protected final static Log logger = LogFactory
			.getLog(MxJbpmDeployResource.class);

	@POST
	@Path("deploy")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@ResponseBody
	public byte[] deploy(@Context HttpServletRequest request) {
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		Map<String, Object> paramMap = RequestUtils.getParameterMap(req);
		if (LogUtils.isDebug()) {
			logger.debug(paramMap);
		}
		int status_code = 0;
		String cause = null;
		Map<String, MultipartFile> fileMap = req.getFileMap();
		Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
		for (Entry<String, MultipartFile> entry : entrySet) {
			MultipartFile mFile = entry.getValue();
			String filename = mFile.getOriginalFilename();
			long filesize = mFile.getSize();
			if (filename == null || filesize <= 0) {
				continue;
			}
			if (filename.endsWith(".jar") || filename.endsWith(".zip")) {
				JbpmContext jbpmContext = null;
				MxJbpmProcessDeployer deployer = new MxJbpmProcessDeployer();
				try {
					jbpmContext = ProcessContainer.getContainer()
							.createJbpmContext();
					if (jbpmContext != null && jbpmContext.getSession() != null) {
						deployer.deploy(jbpmContext, mFile.getBytes());
						status_code = 200;
					}
				} catch (Exception ex) {
					if (jbpmContext != null) {
						jbpmContext.setRollbackOnly();
					}
					status_code = 500;
					throw new JbpmException(ex);
				} finally {
					com.glaf.jbpm.context.Context.close(jbpmContext);
				}
			}
		}

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (status_code == 200) {
			jsonMap.put("statusCode", 200);
			jsonMap.put("message", "发布成功！");
		} else if (status_code == 401) {
			jsonMap.put("statusCode", 401);
			jsonMap.put("message", "没有发布权限！");
		} else if (status_code == 500) {
			jsonMap.put("statusCode", 500);
			jsonMap.put("message", "发布失败，详细信息请参考服务器日志！");
			jsonMap.put("cause", cause);
		} else {
			jsonMap.put("statusCode", status_code);
			jsonMap.put("message", "未登录或会话已经过期，请重新登录后再访问！");
		}

		JSONObject jsonObject = new JSONObject(jsonMap);

		try {
			return jsonObject.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return jsonObject.toString().getBytes();
		}
	}

	@SuppressWarnings("unchecked")
	@GET
	@POST
	@Path("export")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@ResponseBody
	public byte[] export(HttpServletRequest request) {
		String process_name = request.getParameter("process_name");
		String encoding = request.getParameter("encoding");
		if (StringUtils.isEmpty(encoding)) {
			encoding = "UTF-8";
		}
		byte[] bytes = null;
		Document doc = null;
		if (StringUtils.isNotEmpty(process_name)) {
			Map<String, InputStream> zipMap = new HashMap<String, InputStream>();
			JbpmContext jbpmContext = null;
			try {
				JbpmExtensionManager jbpmExtensionManager = ProcessContainer
						.getContainer().getJbpmExtensionManager();
				jbpmContext = ProcessContainer.getContainer()
						.createJbpmContext();
				ProcessDefinition processDefinition = jbpmContext
						.getGraphSession().findLatestProcessDefinition(
								process_name);
				if (processDefinition != null
						&& processDefinition.getFileDefinition() != null) {
					FileDefinition fileDefinition = processDefinition
							.getFileDefinition();
					if (fileDefinition.getInputStreamMap() != null) {
						zipMap.putAll(fileDefinition.getInputStreamMap());
						byte[] gpdBytes = fileDefinition.getBytes("gpd.xml");
						if (gpdBytes != null) {
							try {
								Document doc2 = Dom4jUtils
										.toDocument(fileDefinition
												.getInputStream("gpd.xml"));
								if (doc2 != null) {
								}
							} catch (Exception ex) {
								try {
									String xml = new String(gpdBytes);
									xml = StringTools.replaceIgnoreCase(xml,
											"UTF-8", "GBK");
									InputStream inputStream = new ByteArrayInputStream(
											xml.getBytes("GBK"));
									Document doc2x = Dom4jUtils
											.toDocument(inputStream);
									inputStream.close();
									inputStream = new ByteArrayInputStream(
											Dom4jUtils
													.getBytesFromPrettyDocument(
															doc2x, "GBK"));
									zipMap.put("gpd.xml", inputStream);
								} catch (Exception ex2) {
									throw new RuntimeException(ex2);
								}
							}
						}
					}
				}
				List<Extension> extensions = jbpmExtensionManager
						.getExtensions(jbpmContext, process_name);
				if (extensions != null && extensions.size() > 0) {
					JbpmExtensionWriter writer = new JbpmExtensionWriter();
					doc = writer.write(extensions);
					byte[] cfgBytes = Dom4jUtils.getBytesFromPrettyDocument(
							doc, encoding);
					InputStream cfgStream = new BufferedInputStream(
							new ByteArrayInputStream(cfgBytes));
					zipMap.put("process.cfg.xml", cfgStream);
				}
			} catch (JbpmException ex) {
				throw ex;
			} finally {
				com.glaf.jbpm.context.Context.close(jbpmContext);
			}
			bytes = AntUtils.getZipStream(zipMap);
			zipMap.clear();
			zipMap = null;
		}
		return bytes;
	}
}