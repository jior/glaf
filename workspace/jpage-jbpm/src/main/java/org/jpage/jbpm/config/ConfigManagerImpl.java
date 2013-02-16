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


package org.jpage.jbpm.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import org.jpage.core.cache.CacheFactory;
import org.jpage.jbpm.ibatis.SQLMap;
import org.jpage.jbpm.ibatis.SQLMapAction;
import org.jpage.jbpm.ibatis.SQLMapActionMapping;
import org.jpage.jbpm.ibatis.SqlMapReader;

public class ConfigManagerImpl implements ConfigManager {
	private final static Log logger = LogFactory
			.getLog(ConfigManagerImpl.class);

	public final static String sp = System.getProperty("file.separator");

	public static String JBPM_CONFIG_PATH;

	static {
		Resource resource = new ClassPathResource(
				"/config/jbpm/jpage-jbpm-context.xml");
		try {
			JBPM_CONFIG_PATH = resource.getFile().getParentFile()
					.getAbsolutePath();
			System.out.println("获取配置文件目录:" + JBPM_CONFIG_PATH);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public ConfigManagerImpl() {

	}

	public void clear() {

	}

	/**
	 * 根据命名空间获取SQLMap配置
	 * 
	 * @param namespace
	 * @return
	 */
	public SQLMapActionMapping getSQLMapActionMapping(String namespace) {
		String cacheKey = "cache-sqlmap-mapping-" + namespace;
		if (CacheFactory.get(cacheKey) != null) {
			return (SQLMapActionMapping) CacheFactory.get(cacheKey);
		}
		return loadSQLMapActionMappings(namespace);
	}

	private SQLMapActionMapping loadSQLMapActionMappings(String namespace) {
		String configFile = JBPM_CONFIG_PATH + sp + "action";
		System.out.println("search sqlmap dir:" + configFile);
		SQLMapActionMapping model = null;
		SqlMapReader sqlmapReader = new SqlMapReader();
		try {
			File file = new File(configFile);
			if (file.exists() && file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					File f = files[i];
					if (f.isFile() && f.getName().endsWith(".xml")) {
						System.out.println("load sqlmap action file:"
								+ f.getName());
						java.io.InputStream inputStream = new FileInputStream(f);
						if (inputStream != null) {
							SQLMapActionMapping mapping = sqlmapReader
									.getActionMapping(inputStream);
							inputStream.close();
							System.out.println("namespace->"
									+ mapping.getNamespace());
							if (StringUtils.isNotBlank(mapping.getNamespace())) {
								String cacheKey = "cache-sqlmap-mapping-"
										+ mapping.getNamespace();
								CacheFactory.put(cacheKey, mapping);
								System.out.println("加载到缓存的sqlmap名称空间是:"
										+ mapping.getNamespace());
								if (StringUtils.equalsIgnoreCase(namespace,
										mapping.getNamespace())) {
									model = mapping;
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("加载SQLMAP文件出错:" + ex);
			throw new RuntimeException(ex);
		}
		return model;
	}

	/**
	 * 获取全局定义的SQLMap配置。要作为全局定义的SQLMap，必须在jbpm-sqlmap-action.xml文件中定义
	 * 
	 * @return
	 */
	public Map getGlobalSQLMap() {
		String cacheKey = "global@jpage_jbpm_sqlmap";
		if (CacheFactory.get(cacheKey) != null) {
			return (Map) CacheFactory.get(cacheKey);
		}
		Map rowMap = new HashMap();
		String configFile = JBPM_CONFIG_PATH + sp + "action" + sp
				+ "jbpm-sqlmap-action.xml";
		System.out.println("search sqlmap dir:" + configFile);
		SqlMapReader sqlmapReader = new SqlMapReader();
		try {
			File f = new File(configFile);
			java.io.InputStream inputStream = new FileInputStream(f);
			if (inputStream != null) {
				SQLMapActionMapping mapping = sqlmapReader
						.getActionMapping(inputStream);
				inputStream.close();
				Map actions = mapping.getActions();
				Iterator iterator = actions.keySet().iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					SQLMapAction action = (SQLMapAction) actions.get(key);
					if (action.getSqlmaps() != null
							&& action.getSqlmaps().size() > 0) {
						Iterator iter = action.getSqlmaps().iterator();
						while (iter.hasNext()) {
							SQLMap sqlmap = (SQLMap) iter.next();
							if (rowMap.get(sqlmap.getName()) == null) {
								rowMap.put(sqlmap.getName(), sqlmap);
							}
						}
					}
				}
			}
			CacheFactory.put(cacheKey, rowMap);
			return rowMap;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("加载全局SQLMAP文件出错:" + ex);
			throw new RuntimeException(ex);
		}
	}

}
