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
package com.glaf.core.config;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * ���԰��ղ�ͬ������ģʽ������Ӧ������
 * 
 */
public class MutilPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer implements InitializingBean {

	protected static final Log LOG = LogFactory
			.getLog(MutilPropertyPlaceholderConfigurer.class);

	public static final String DEVELOPMENT = "DEV";

	public static final String PRODUCTION = "PRD";

	public static final String RUN_MODE = "run.mode";

	// �������е���������
	private Properties properties;

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return properties.getProperty(RUN_MODE);
	}

	/**
	 * ���Ŵ˷�������Ҫ��ҵ��
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return resolvePlaceholder(key, properties);
	}

	@Override
	protected Properties mergeProperties() throws IOException {
		Properties mergeProperties = super.mergeProperties();
		// ����·��ԭ����ȡ������Ч��properties
		this.properties = new Properties();
		// ��ȡ·�ɹ���,ϵͳ��������mode����
		String mode = System.getProperty(RUN_MODE);
		if (StringUtils.isEmpty(mode)) {
			String str = mergeProperties.getProperty(RUN_MODE);
			mode = str != null ? str : PRODUCTION;
		}
		LOG.info("####run mode:" + mode);
		properties.put(RUN_MODE, mode);
		String[] modes = mode.split(",");
		Set<Entry<Object, Object>> es = mergeProperties.entrySet();
		for (Entry<Object, Object> entry : es) {
			String key = (String) entry.getKey();
			int idx = key.lastIndexOf('_');
			String realKey = idx == -1 ? key : key.substring(0, idx);
			if (!properties.containsKey(realKey)) {
				Object value = null;
				for (String md : modes) {
					value = mergeProperties.get(realKey + "_" + md);
					if (value != null) {
						properties.put(realKey, value);
						break;
					}
				}
				if (value == null) {
					value = mergeProperties.get(realKey);
					if (value != null) {
						properties.put(realKey, value);
					}
				}
			}
		}
		logger.debug(properties);
		return properties;
	}
}
