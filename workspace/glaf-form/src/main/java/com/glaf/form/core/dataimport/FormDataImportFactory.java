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
package com.glaf.form.core.dataimport;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.SystemProperties;
import com.glaf.form.core.model.FormDefinitionType;

public class FormDataImportFactory {
	protected static final Log logger = LogFactory
			.getLog(FormDataImportFactory.class);
	private final static String DEFAULT_CONFIG = "com/glaf/form/core/dataimport/dataimport-context.xml";

	private static ClassPathXmlApplicationContext ctx;

	public static ClassPathXmlApplicationContext getApplicationContext() {
		return ctx;
	}

	public static Object getBean(Object key) {
		if (ctx == null) {
			ctx = reload();
		}
		return ctx.getBean((String) key);
	}

	/**
	 * 通过模板类型获取解析接口实现类
	 * 
	 * @param templateType
	 * @return
	 */
	public static FormDataImport getDataImport(String templateType) {
		FormDataImport dataImport = (FormDataImport) getBean(templateType);
		return dataImport;
	}

	/**
	 * 通过模板类型和模板文件获取表单定义信息
	 * 
	 * @param templateType
	 * @param inputStream
	 * @return
	 */
	public static FormDefinitionType read(String templateType, byte[] bytes) {
		logger.debug("templateType:" + templateType);
		FormDataImport dataImport = getDataImport(templateType);
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(bytes);
			return dataImport.read(inputStream);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException ex) {
				}
			}
		}
	}

	/**
	 * 通过模板类型和模板文件获取表单定义信息
	 * 
	 * @param templateType
	 * @param inputStream
	 * @return
	 */
	public static FormDefinitionType read(String templateType,
			InputStream inputStream) {
		FormDataImport dataImport = getDataImport(templateType);
		return dataImport.read(inputStream);
	}

	public static ClassPathXmlApplicationContext reload() {
		if (ctx != null) {
			ctx.close();
			ctx = null;
		}
		String configLocation = CustomProperties
				.getString("sys.form.dataimport.context");
		if (StringUtils.isEmpty(configLocation)) {
			configLocation = SystemProperties
					.getString("sys.form.dataimport.context");
		}
		if (StringUtils.isEmpty(configLocation)) {
			configLocation = DEFAULT_CONFIG;
		}
		ctx = new ClassPathXmlApplicationContext(configLocation);
		logger.debug("start spring ioc from: " + configLocation);
		return ctx;
	}

}