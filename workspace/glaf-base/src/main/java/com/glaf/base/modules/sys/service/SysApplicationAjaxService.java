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

import com.glaf.base.modules.sys.model.SysApplication;

public class SysApplicationAjaxService {
	private static final Log logger = LogFactory
			.getLog(SysApplicationAjaxService.class);

	private SysApplicationService sysApplicationService;

	public void setSysApplicationService(
			SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
		logger.info("sysApplicationService");
	}

	/**
	 * ≈≈–Ú
	 * 
	 * @param id
	 * @param operate
	 */
	public void sort(int parent, int id, int operate) {
		logger.info("parent:" + parent + "; id:" + id + "; operate:" + operate);
		SysApplication bean = sysApplicationService.findById(id);
		sysApplicationService.sort(parent, bean, operate);
	}
}