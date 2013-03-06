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

package com.glaf.activiti.spring;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.db.IbatisVariableTypeHandler;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.engine.impl.variable.VariableType;
import org.activiti.spring.SpringProcessEngineConfiguration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ObjectUtils;
import org.xml.sax.EntityResolver;

import com.glaf.activiti.identity.MyIdentityServiceImpl;
import com.glaf.core.util.Dom4jUtils;
import com.glaf.core.util.IOUtils;

public class SpringProcessEngineConfigurationBean extends
		SpringProcessEngineConfiguration {

	protected final static Log logger = LogFactory
			.getLog(SpringProcessEngineConfigurationBean.class);

	protected Resource configLocation;

	protected Resource[] mapperLocations;

	public SpringProcessEngineConfigurationBean() {
		super();
		identityService = new MyIdentityServiceImpl();
	}

	public Resource getConfigLocation() {
		return configLocation;
	}

	public Resource[] getMapperLocations() {
		return mapperLocations;
	}

	@Override
	protected void initSqlSessionFactory() {
		logger.info("-------------------------------------------");
		logger.info("-------------initSqlSessionFactory()-------");
		logger.info("sqlSessionFactory:" + sqlSessionFactory);
		logger.info("transactionFactory:" + transactionFactory);
		if (sqlSessionFactory == null) {
			InputStream inputStream = null;
			try {
				if (configLocation != null) {
					logger.info("mybatis config:"
							+ this.configLocation.getFile().getAbsolutePath());
					inputStream = this.configLocation.getInputStream();
				} else {
					Resource resource = new ClassPathResource(
							"com/glaf/activiti/activiti.mybatis.conf.xml");
					inputStream = resource.getInputStream();
				}

				if (!ObjectUtils.isEmpty(this.mapperLocations)) {
					SAXReader xmlReader = new SAXReader();
					EntityResolver entityResolver = new XMLMapperEntityResolver();
					xmlReader.setEntityResolver(entityResolver);
					Document doc = xmlReader.read(inputStream);
					Element root = doc.getRootElement();
					Element mappers = root.element("mappers");
					if (mappers != null) {
						java.util.List<?> list = mappers.elements();
						Collection<String> files = new HashSet<String>();

						if (list != null && !list.isEmpty()) {
							Iterator<?> iterator = list.iterator();
							while (iterator.hasNext()) {
								Element elem = (Element) iterator.next();
								if (elem.attributeValue("resource") != null) {
									String file = elem
											.attributeValue("resource");
									files.add(file);
								} else if (elem.attributeValue("url") != null) {
									String file = elem.attributeValue("url");
									files.add(file);
								}
							}
						}

						for (Resource mapperLocation : this.mapperLocations) {
							if (mapperLocation == null) {
								continue;
							}
							String url = mapperLocation.getURL().toString();
							// logger.debug("find mapper:" + url);
							if (!files.contains(url)) {
								Element element = mappers.addElement("mapper");
								element.addAttribute("url", url);
							}
						}
					}

					IOUtils.closeStream(inputStream);

					byte[] bytes = Dom4jUtils.getBytesFromPrettyDocument(doc);
					inputStream = new ByteArrayInputStream(bytes);

				}

				// update the jdbc parameters to the configured ones...
				Environment environment = new Environment("default",
						transactionFactory, dataSource);
				Reader reader = new InputStreamReader(inputStream);
				Properties properties = new Properties();
				properties.put("prefix", databaseTablePrefix);
				if (databaseType != null) {
					properties
							.put("limitBefore",
									DbSqlSessionFactory.databaseSpecificLimitBeforeStatements
											.get(databaseType));
					properties
							.put("limitAfter",
									DbSqlSessionFactory.databaseSpecificLimitAfterStatements
											.get(databaseType));
					properties
							.put("limitBetween",
									DbSqlSessionFactory.databaseSpecificLimitBetweenStatements
											.get(databaseType));
					properties
							.put("orderBy",
									DbSqlSessionFactory.databaseSpecificOrderByStatements
											.get(databaseType));
				}
				XMLConfigBuilder parser = new XMLConfigBuilder(reader, "",
						properties);
				Configuration configuration = parser.getConfiguration();
				configuration.setEnvironment(environment);
				configuration.getTypeHandlerRegistry().register(
						VariableType.class, JdbcType.VARCHAR,
						new IbatisVariableTypeHandler());
				configuration = parser.parse();

				sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

			} catch (Exception e) {
				throw new ActivitiException(
						"Error while building ibatis SqlSessionFactory: "
								+ e.getMessage(), e);
			} finally {
				IoUtil.closeSilently(inputStream);
			}
		}
	}

	public void setConfigLocation(Resource configLocation) {
		this.configLocation = configLocation;
	}

	public void setMapperLocations(Resource[] mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

}