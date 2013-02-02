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

package com.glaf.base.modules.sys.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SysUserRoleAjaxService {
	private static final Log logger = LogFactory.getLog(SysUserRoleAjaxService.class);
	private SysUserRoleService sysUserRoleService;	
	public void setSysUserRoleService(SysUserRoleService sysUserRoleService) {
		this.sysUserRoleService = sysUserRoleService;
		logger.info("setSysUserRoleService");
	}


	/**
	 * 授权
	 * @param fromUserId long
	 * @param toUserId long
	 * @param startDate String
	 * @param endDate String
	 * @return booelan
	 */
	public boolean addRole(long fromUserId, long toUserId, String startDate, String endDate,int mark,String processNames,String processDescriptions){
		if(sysUserRoleService.isAuthorized(fromUserId, toUserId)){//已授权
			return false;
		}
		return sysUserRoleService.addRole(fromUserId, toUserId, startDate, endDate,mark,processNames,processDescriptions);
	}

	/**
	 * 取消授权
	 * @param fromUserId long
	 * @param toUserId long
	 * @return booelan
	 */
	public boolean removeRole(long fromUserId, long toUserId){
		return sysUserRoleService.removeRole(fromUserId, toUserId);
	}
	/**
	 * 授权给用户
	 * @param fromUserId long
	 * @param toUserId String
	 * @return booelan
	 */
	public boolean addRoleUser(long fromUserId, String toUserIds, String startDate, String endDate,int mark,String processNames,String processDescriptions){
		String[] ids = toUserIds.split(",");
		for(int i=0; i<ids.length; i++){
			long toUserId = Long.parseLong(ids[i]);
			addRole(fromUserId, toUserId, startDate, endDate,mark,processNames,processDescriptions);
		}
		return true;
	}
	/**
	 * 取消授权
	 * @param fromUserId  long
	 * @param toUserIds String
	 * @return  booelan
	 */
	public boolean removeRoleUser(long fromUserId, String toUserIds){
		logger.info("toUserIds:"+toUserIds);
		String[] ids = toUserIds.split(",");
		for(int i=0; i<ids.length; i++){
			long toUserId = Long.parseLong(ids[i]);
			removeRole(fromUserId, toUserId);
		}
		return true;
	}
}