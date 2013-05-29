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

package com.glaf.base.modules;

import javax.servlet.http.HttpServlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.utils.ContextUtil;

public class InitBaseDataServlet extends HttpServlet {
	private static final long serialVersionUID = 2072103368980714549L;

	private final static Log logger = LogFactory
			.getLog(InitBaseDataServlet.class);

	private BaseDataManager bdm = BaseDataManager.getInstance();// 基础信息管理

	public void init() {
		long startTime = System.currentTimeMillis();
		logger.info("初始化基础信息...");
		try {
			bdm.refreshBaseData();// 刷新数据
			logger.info("初始化基础信息完成.");
			// 装载系统功能列表
			ContextUtil.put("function", bdm.getBaseData(Constants.SYS_FUNCTIONS));
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("初始化基础信息失败！");
		}
		logger.info("耗时：" + (System.currentTimeMillis() - startTime) + " ms.");
	}
}