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

package com.glaf.core.domain.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.Configuration;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.domain.SysDataLog;
import com.glaf.core.service.SysDataLogService;

public class SysDataLogFactory {
	protected final static Log logger = LogFactory
			.getLog(SysDataLogFactory.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected static boolean isMongoDB = false;

	static {
		isMongoDB = conf.getBoolean("mongodb.logEnable", false);
	}

	public static void create(SysDataLog log) {
		if (isMongoDB) {
			logger.debug(" save mongodb log.");
		} else {
			SysDataLogService sysDataLogService = ContextFactory
					.getBean("sysDataLogService");
			sysDataLogService.save(log);
			logger.debug(" save database log.");
		}
	}

}
