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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.core.db.DataServiceBean;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.SysDataService;
import com.glaf.core.util.RequestUtils;

@Controller("/rs/data/service")
@Path("/rs/data/service")
public class DataServiceResource {

	protected static final Log logger = LogFactory
			.getLog(DataServiceResource.class);

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
		String systemName = request.getParameter("systemName");
		if (dataType == null) {
			dataType = "xml";
		}
		if (systemName == null) {
			systemName = com.glaf.core.config.Environment.DEFAULT_SYSTEM_NAME;
		}
		Map<String, Object> contextMap = RequestUtils.getParameterMap(request);
		String ipAddress = RequestUtils.getIPAddress(request);
		DataServiceBean bean = new DataServiceBean();
		bean.setSysDataService(sysDataService);
		if (StringUtils.equals(dataType, "json")) {
			return bean.responseJson(systemName, id, loginContext.getActorId(),
					ipAddress, contextMap);
		}
		return bean.responseXml(systemName, id, loginContext.getActorId(),
				ipAddress, contextMap);
	}

}
