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
package com.glaf.shiro;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

public class MyShiroFilterFactoryBean extends ShiroFilterFactoryBean {

	private static final Log logger = LogFactory
			.getLog(MyShiroFilterFactoryBean.class);

	private Map<String, String> filterChainDefinitionMap;

	public MyShiroFilterFactoryBean() {
		super();
		this.filterChainDefinitionMap = new LinkedHashMap<String, String>();
	}

	@Override
	public Map<String, String> getFilterChainDefinitionMap() {
		logger.debug("load system security properties...");
		logger.debug("filterChain size:" + filterChainDefinitionMap.size());
		LinkedHashMap<String, String> props = SecurityConfig.getProperties();
		Iterator<String> it = props.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = props.get(key);
			if (StringUtils.startsWith("**", key)
					|| StringUtils.startsWith("/**", key)) {
				continue;
			}
			filterChainDefinitionMap.put(key, value);
			logger.debug("add security filter chain:" + key + "=" + value);
		}
		/**
		 * 如下定义是为了防止配置错误导致安全漏洞
		 */
		filterChainDefinitionMap.put("/rs/**", "authc");
		filterChainDefinitionMap.put("/mx/**", "authc");
		logger.debug(filterChainDefinitionMap);
		return filterChainDefinitionMap;
	}

	public void setFilterChainDefinitionMap(
			Map<String, String> filterChainDefinitionMap) {
		this.filterChainDefinitionMap = filterChainDefinitionMap;
	}

}
