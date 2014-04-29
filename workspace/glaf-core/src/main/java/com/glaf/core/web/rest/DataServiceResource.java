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

package com.glaf.core.web.rest;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.util.IOUtils;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.domain.SysData;
import com.glaf.core.domain.SysDataLog;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.SysDataService;
import com.glaf.core.util.Dom4jUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.SysDataLogFactory;
import com.glaf.core.xml.XmlBuilder;

@Controller("/rs/data/service")
@Path("/rs/data/service")
public class DataServiceResource {

	protected SysDataService sysDataService;

	@javax.annotation.Resource
	public void setSysDataService(SysDataService sysDataService) {
		this.sysDataService = sysDataService;
	}

	@GET
	@POST
	@Path("/response/{id}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] response(@PathParam("id") String id,
			@Context HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String dataType = request.getParameter("dataType");
		if (dataType == null) {
			dataType = "xml";
		}
		Map<String, Object> dataMap = RequestUtils.getParameterMap(request);
		dataMap.put("queryString", request.getQueryString());
		dataMap.put("dataType", dataType);
		dataMap.put("actorId", loginContext.getActorId());
		dataMap.put("id", id);

		String ipAddress = RequestUtils.getIPAddress(request);
		XmlBuilder builder = new XmlBuilder();
		InputStream inputStream = null;
		boolean hasPermission = false;
		String systemName = "default";
		SysData sysData = null;
		try {
			sysData = sysDataService.getSysData(id);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		if (sysData == null || sysData.getLocked() == 1) {
			throw new RuntimeException(" data service '" + id
					+ "' not available.");
		}

		if (StringUtils.isNotEmpty(sysData.getAddressPerms())) {
			List<String> addressList = StringTools.split(sysData
					.getAddressPerms());
			for (String addr : addressList) {
				if (StringUtils.equals(ipAddress, addr)) {
					hasPermission = true;
				}
				if (addr.endsWith("*")) {
					String tmp = addr.substring(0, addr.indexOf("*"));
					// System.out.println(">>>>>>>>>>>>>>>"+tmp);
					if (StringUtils.contains(ipAddress, tmp)) {
						hasPermission = true;
					}
				}
			}
			if (!hasPermission) {
				throw new RuntimeException("Permission denied.");
			}
		}

		if (StringUtils.isNotEmpty(sysData.getPerms())
				&& !StringUtils.equalsIgnoreCase(sysData.getPerms(), "anyone")) {
			if (loginContext.hasSystemPermission()
					|| loginContext.hasAdvancedPermission()) {
				hasPermission = true;
			}
			List<String> permissions = StringTools.split(sysData.getPerms());
			for (String perm : permissions) {
				if (loginContext.getPermissions().contains(perm)) {
					hasPermission = true;
				}
				if (loginContext.getRoles().contains(perm)) {
					hasPermission = true;
				}
			}
			if (!hasPermission) {
				throw new RuntimeException("Permission denied.");
			}
		}

		SysDataLog log = new SysDataLog();
		try {
			String filename = SystemProperties.getConfigRootPath()
					+ sysData.getPath();
			inputStream = FileUtils.getInputStream(filename);
			log.setAccountId(loginContext.getUser().getId());
			log.setActorId(loginContext.getActorId());
			log.setCreateTime(new Date());
			log.setIp(RequestUtils.getIPAddress(request));
			log.setOperate(id);
			log.setContent(JsonUtils.encode(dataMap));
			dataMap.put("loginContext", loginContext);
			dataMap.put("loginUser", loginContext.getUser());
			Document doc = builder.process(systemName, inputStream, dataMap);
			log.setFlag(9);
			log.setModuleId("DS");

			if (StringUtils.equals(dataType, "json")) {
				net.sf.json.xml.XMLSerializer xmlSerializer = new net.sf.json.xml.XMLSerializer();
				net.sf.json.JSON json = xmlSerializer.read(doc.asXML());
				return json.toString(2).getBytes("UTF-8");
			}

			return Dom4jUtils.getBytesFromPrettyDocument(doc, "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			log.setFlag(-1);
			throw new RuntimeException(ex);
		} finally {
			IOUtils.close(inputStream);
			SysDataLogFactory.create(log);
		}
	}

}
