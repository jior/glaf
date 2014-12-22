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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.SysDataLog;
import com.glaf.core.domain.SysDataTable;
import com.glaf.core.query.SysDataTableQuery;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.ISysDataTableService;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.SysDataLogFactory;

public class DataTableBean {

	protected static final Log logger = LogFactory.getLog(DataTableBean.class);

	protected ISysDataTableService sysDataTableService;

	/**
	 * 权限检查，没有权限抛出异常
	 * 
	 * @param sysDataTable
	 *            数据表对象
	 * @param loginContext
	 *            用户登录上下文
	 * @param ipAddress
	 *            用户客户端的IP地址
	 */
	public void checkPermission(SysDataTable sysDataTable,
			LoginContext loginContext, String ipAddress) {
		boolean hasPermission = false;
		/**
		 * 非公开访问数据都需要检查是否有权限
		 */
		if (!StringUtils.equals(sysDataTable.getAccessType(), "PUB")) {
			/**
			 * 检查IP地址是否在允许访问列表内
			 */
			if (StringUtils.isNotEmpty(sysDataTable.getAddressPerms())) {
				List<String> addressList = StringTools.split(sysDataTable
						.getAddressPerms());
				for (String addr : addressList) {
					if (StringUtils.equals(ipAddress, addr)) {
						hasPermission = true;
					}
					if (StringUtils.equals(ipAddress, "127.0.0.1")) {
						hasPermission = true;
					}
					if (StringUtils.equals(ipAddress, "localhost")) {
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

			/**
			 * 检查权限是否满足
			 */
			if (StringUtils.isNotEmpty(sysDataTable.getPerms())
					&& !StringUtils.equalsIgnoreCase(sysDataTable.getPerms(),
							"anyone")) {
				if (loginContext.hasSystemPermission()
						|| loginContext.hasAdvancedPermission()) {
					hasPermission = true;
				}
				List<String> permissions = StringTools.split(sysDataTable
						.getPerms());
				for (String perm : permissions) {
					if (loginContext.getPermissions().contains(perm)) {
						hasPermission = true;
					}
					if (loginContext.getRoles().contains(perm)) {
						hasPermission = true;
					}
					if (StringUtils.isNotEmpty(perm)
							&& StringUtils.isNumeric(perm)) {
						if (loginContext.getRoleIds().contains(
								Long.parseLong(perm))) {
							hasPermission = true;
						}
					}
				}
				if (!hasPermission) {
					throw new RuntimeException("Permission denied.");
				}
			}
		}
	}

	public ISysDataTableService getSysDataTableService() {
		if (sysDataTableService == null) {
			sysDataTableService = ContextFactory.getBean("sysDataTableService");
		}
		return sysDataTableService;
	}

	/**
	 * 
	 * @param datatableId
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
	private byte[] response(String systemName, String datatableId,
			String actorId, String ipAddress, String dataType,
			SysDataTableQuery query) {
		LoginContext loginContext = IdentityFactory.getLoginContext(actorId);

		if (dataType == null) {
			dataType = "json";
		}

		InputStream inputStream = null;

		SysDataTable sysDataTable = null;
		try {
			sysDataTable = getSysDataTableService().getDataTableById(
					datatableId);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		if (sysDataTable == null || sysDataTable.getLocked() == 1) {
			throw new RuntimeException(" data service '" + datatableId
					+ "' not available.");
		}

		this.checkPermission(sysDataTable, loginContext, ipAddress);

		long start = System.currentTimeMillis();
		SysDataLog log = new SysDataLog();
		try {

			log.setAccountId(loginContext.getUser().getId());
			log.setActorId(loginContext.getActorId());
			log.setCreateTime(new Date());
			log.setIp(ipAddress);
			log.setOperate("read");
			log.setContent(datatableId);
			log.setFlag(9);
			log.setModuleId("DT");

			JSONObject json = getSysDataTableService().getPageTableData(0,
					50000, query);

			int timeMS = (int) (System.currentTimeMillis() - start);
			logger.debug("用时（毫秒）:" + timeMS);
			log.setTimeMS(timeMS);

			return json.toJSONString().getBytes("UTF-8");
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
	 * @param systemName
	 *            系统名
	 * @param datatableId
	 *            数据服务编号
	 * @param actorId
	 *            用户编号
	 * @param ipAddress
	 *            IP地址
	 * @param contextMap
	 *            参数
	 * @return 返回JSON格式数据
	 */
	public byte[] responseJson(String systemName, String datatableId,
			String actorId, String ipAddress, SysDataTableQuery query) {
		return this.response(systemName, datatableId, actorId, ipAddress,
				"json", query);
	}

	/**
	 * 
	 * @param datatableId
	 *            数据服务编号
	 * @param actorId
	 *            用户编号
	 * @param ipAddress
	 *            IP地址
	 * @param contextMap
	 *            参数
	 * @return 返回JSON格式数据
	 */
	public byte[] responseJson(String datatableId, String actorId,
			String ipAddress, SysDataTableQuery query) {
		String systemName = com.glaf.core.config.Environment.DEFAULT_SYSTEM_NAME;
		return this.response(systemName, datatableId, actorId, ipAddress,
				"json", query);
	}

	public void setSysDataTableService(ISysDataTableService sysDataTableService) {
		this.sysDataTableService = sysDataTableService;
	}

}
