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

package com.glaf.core.db;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;

import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.SysData;
import com.glaf.core.domain.SysDataLog;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.SysDataService;
import com.glaf.core.util.Dom4jUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.JacksonUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.SysDataLogFactory;
import com.glaf.core.xml.XmlBuilder;

public class DataServiceBean {

	protected static final Log logger = LogFactory
			.getLog(DataServiceBean.class);

	protected SysDataService sysDataService;

	public SysDataService getSysDataService() {
		if (sysDataService == null) {
			sysDataService = ContextFactory.getBean("sysDataService");
		}
		return sysDataService;
	}

	/**
	 * 
	 * @param id
	 *            数据服务编号
	 * @param actorId
	 *            用户编号
	 * @param ipAddress
	 *            IP地址
	 * @param dataType
	 *            数据类型
	 * @param contextMap
	 *            参数
	 * @return
	 */
	private byte[] response(String systemName, String id, String actorId,
			String ipAddress, String dataType, Map<String, Object> contextMap) {
		LoginContext loginContext = IdentityFactory.getLoginContext(actorId);

		if (dataType == null) {
			dataType = "xml";
		}

		contextMap.put("id", id);
		contextMap.put("dataType", dataType);
		contextMap.put("actorId", loginContext.getActorId());
		contextMap.put("serviceUrl", SystemConfig.getServiceUrl());

		XmlBuilder builder = new XmlBuilder();
		InputStream inputStream = null;
		boolean hasPermission = false;

		SysData sysData = null;
		try {
			sysData = getSysDataService().getSysData(id);
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
				if (StringUtils.isNotEmpty(perm) && StringUtils.isNumeric(perm)) {
					if (loginContext.getRoleIds()
							.contains(Long.parseLong(perm))) {
						hasPermission = true;
					}
				}
			}
			if (!hasPermission) {
				throw new RuntimeException("Permission denied.");
			}
		}

		long start = System.currentTimeMillis();
		SysDataLog log = new SysDataLog();
		try {
			String filename = SystemProperties.getConfigRootPath()
					+ sysData.getPath();
			inputStream = FileUtils.getInputStream(filename);
			log.setAccountId(loginContext.getUser().getId());
			log.setActorId(loginContext.getActorId());
			log.setCreateTime(new Date());
			log.setIp(ipAddress);
			log.setOperate(id);
			log.setContent(JsonUtils.encode(contextMap));
			contextMap.put("loginContext", loginContext);
			contextMap.put("loginUser", loginContext.getUser());
			Document doc = builder.process(systemName, inputStream, contextMap);
			log.setFlag(9);
			log.setModuleId("DS");

			int timeMS = (int) (System.currentTimeMillis() - start);
			logger.debug("用时（毫秒）:" + timeMS);
			log.setTimeMS(timeMS);

			if (StringUtils.equals(dataType, "json")) {
				return JacksonUtils.xml2json(doc.asXML()).getBytes("UTF-8");
			}

			return Dom4jUtils.getBytesFromPrettyDocument(doc, "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			log.setFlag(-1);
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(inputStream);
			SysDataLogFactory.create(log);
		}
	}

	/**
	 * 
	 * @param id
	 *            数据服务编号
	 * @param actorId
	 *            用户编号
	 * @param ipAddress
	 *            IP地址
	 * @param contextMap
	 *            参数
	 * @return 返回JSON格式数据
	 */
	public byte[] responseJson(String id, String actorId, String ipAddress,
			Map<String, Object> contextMap) {
		String systemName = com.glaf.core.config.Environment.DEFAULT_SYSTEM_NAME;
		return this.response(systemName, id, actorId, ipAddress, "json",
				contextMap);
	}

	/**
	 * @param systemName
	 *            系统名
	 * @param id
	 *            数据服务编号
	 * @param actorId
	 *            用户编号
	 * @param ipAddress
	 *            IP地址
	 * @param contextMap
	 *            参数
	 * @return 返回JSON格式数据
	 */
	public byte[] responseJson(String systemName, String id, String actorId,
			String ipAddress, Map<String, Object> contextMap) {
		return this.response(systemName, id, actorId, ipAddress, "json",
				contextMap);
	}

	/**
	 * 
	 * @param id
	 *            数据服务编号
	 * @param actorId
	 *            用户编号
	 * @param ipAddress
	 *            IP地址
	 * @param dataType
	 *            数据类型
	 * @param contextMap
	 *            参数
	 * @return 返回XML格式数据
	 */
	public byte[] responseXml(String id, String actorId, String ipAddress,
			Map<String, Object> contextMap) {
		String systemName = com.glaf.core.config.Environment.DEFAULT_SYSTEM_NAME;
		return this.response(systemName, id, actorId, ipAddress, "xml",
				contextMap);
	}

	/**
	 * 
	 * @param systemName
	 *            系统名
	 * @param id
	 *            数据服务编号
	 * @param actorId
	 *            用户编号
	 * @param ipAddress
	 *            IP地址
	 * @param dataType
	 *            数据类型
	 * @param contextMap
	 *            参数
	 * @return 返回XML格式数据
	 */
	public byte[] responseXml(String systemName, String id, String actorId,
			String ipAddress, Map<String, Object> contextMap) {
		return this.response(systemName, id, actorId, ipAddress, "xml",
				contextMap);
	}

	public void setSysDataService(SysDataService sysDataService) {
		this.sysDataService = sysDataService;
	}

}
