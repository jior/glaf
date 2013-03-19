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

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.glaf.base.modules.utils.ContextUtil;

public class InitBaseDataServlet extends HttpServlet {
	private static final long serialVersionUID = 2072103368980714549L;

	private final static Log logger = LogFactory
			.getLog(InitBaseDataServlet.class);

	private Map<String, Object> beanMap = new HashMap<String, Object>();

	private Map<String, Object> serviceMap = new HashMap<String, Object>();

	private BaseDataManager bdm = BaseDataManager.getInstance();// 基础信息管理

	public void init() {
		long startTime = System.currentTimeMillis();
		logger.info("初始化基础信息...");
		try {
			WebApplicationContext wac = WebApplicationContextUtils
					.getRequiredWebApplicationContext(getServletContext());
			String[] beanNames = BaseDataManager.SV_NAMES;
			for (int i = 0; i < beanNames.length; i++) {
				logger.info("load service:" + beanNames[i]);
				try {
					Object bean = wac.getBean(beanNames[i]);
					serviceMap.put(beanNames[i], bean);
				} catch (Exception ex) {
					logger.error(ex);
				}
			}
			bdm.setServiceMap(serviceMap);
			bdm.refreshBaseData();// 刷新数据

			beanMap.put("todoJobBean", wac.getBean("todoJobBean"));

			bdm.setBeanMap(beanMap);

			logger.info("初始化基础信息完成.");

			// 装载系统功能列表
			ContextUtil.put("function", bdm.getBaseData("ZD0015"));
			ContextUtil.put("wac", wac);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("初始化基础信息失败！");
		}

		logger.info("耗时：" + (System.currentTimeMillis() - startTime) + " ms.");
	}
}