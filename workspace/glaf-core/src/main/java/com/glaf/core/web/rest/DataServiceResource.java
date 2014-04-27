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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.domain.SysDataLog;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.SysDataLogFactory;
import com.glaf.core.xml.XmlBuilder;
import com.glaf.core.xml.XmlProperties;

@Controller("/rs/data/service")
@Path("/rs/data/service")
public class DataServiceResource {

	@GET
	@POST
	@Path("/xml/{name}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] xml(@PathParam("name") String name,
			@Context HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> dataMap = RequestUtils.getParameterMap(request);
		dataMap.put("name", name);
		dataMap.put("queryString", request.getQueryString());
		String text = XmlProperties.getString(name);
		JSONObject json = JSON.parseObject(text);
		String perms = json.getString("perms");

		if (StringUtils.isNotEmpty(perms)
				&& !StringUtils.equalsIgnoreCase(perms, "anyone")) {
			boolean hasPermission = false;

			if (loginContext.hasSystemPermission()
					|| loginContext.hasAdvancedPermission()) {
				hasPermission = true;
			}
			List<String> permissions = StringTools.split(perms);
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
		String systemName = "default";
		String filename = SystemProperties.getConfigRootPath()
				+ json.getString("path");
		XmlBuilder builder = new XmlBuilder();
		InputStream inputStream = null;
		SysDataLog log = new SysDataLog();
		try {
			inputStream = FileUtils.getInputStream(filename);
			log.setAccountId(loginContext.getUser().getId());
			log.setActorId(loginContext.getActorId());
			log.setCreateTime(new Date());
			log.setIp(RequestUtils.getIPAddress(request));
			log.setOperate(name);
			log.setContent(JsonUtils.encode(dataMap));
			Document doc = builder.process(systemName, inputStream, dataMap);
			log.setFlag(9);
			return com.glaf.core.util.Dom4jUtils.getBytesFromPrettyDocument(
					doc, "UTF-8");
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
