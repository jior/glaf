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

package com.glaf.dts.input;

 
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.SystemProperty;
import com.glaf.core.parse.ParserFacede;
import com.glaf.core.service.ISystemPropertyService;

public class TextFileImporter {

	public void importData() {
		ISystemPropertyService systemPropertyService = ContextFactory
				.getBean("systemPropertyService");
		List<SystemProperty> props = systemPropertyService
				.getAllSystemProperties();
		if (props != null && !props.isEmpty()) {
			Map<String, String> dataMap = new java.util.concurrent.ConcurrentHashMap<String, String>();
			for (SystemProperty p : props) {
				dataMap.put(p.getName(), p.getValue());
			}

			String dataDir = dataMap.get("dataDir");
			String mappingDir = dataMap.get("mappingDir");

			if (StringUtils.isEmpty(mappingDir)) {
				mappingDir = SystemConfig.getMappingPath();
			}
			if (StringUtils.isEmpty(dataDir)) {
				dataDir = SystemConfig.getDataPath();
			}

			ParserFacede facede = new ParserFacede();
			boolean success = false;
			int retry = 0;
			while (retry < 2 && !success) {
				try {
					retry++;
					facede.process(mappingDir, dataDir);
					success = true;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}