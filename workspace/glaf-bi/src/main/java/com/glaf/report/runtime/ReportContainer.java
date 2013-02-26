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

package com.glaf.report.runtime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.SystemProperties;
import com.glaf.report.def.ReportDefinition;
import com.glaf.report.xml.ReportDefinitionReader;
import com.glaf.core.util.FileUtils;

public class ReportContainer {
	protected final static Log logger = LogFactory
			.getLog(ReportContainer.class);

	private final static String sp = System.getProperty("file.separator");

	private final static String DEFAULT_CONFIG_PATH = "/conf/report";

	private static ReportContainer container = new ReportContainer();

	public final static ReportContainer getContainer() {
		return container;
	}

	private ReportContainer() {

	}

	public Map<String, ReportDefinition> reload() {
		Map<String, ReportDefinition> reportMap = new HashMap<String, ReportDefinition>();
		String configLocation = SystemProperties
				.getString("report.config.path");
		if (StringUtils.isEmpty(configLocation)) {
			configLocation = CustomProperties.getString("report.config.path");
		}
		if (StringUtils.isEmpty(configLocation)) {
			configLocation = DEFAULT_CONFIG_PATH;
		}

		ReportDefinitionReader reader = new ReportDefinitionReader();
		InputStream inputStream = null;
		try {
			String configPath = SystemProperties.getConfigRootPath()
					+ configLocation;
			logger.info(configPath);
			File directory = new File(configPath);
			if (directory.isDirectory()) {
				String[] filelist = directory.list();
				for (int i = 0; i < filelist.length; i++) {
					String filename = configPath + sp + filelist[i];
					File file = new File(filename);
					if (file.isFile() && file.getName().endsWith(".report.xml")) {
						logger.debug(file.getAbsolutePath());
						inputStream = new FileInputStream(file);
						List<ReportDefinition> reports = reader
								.read(inputStream);
						for (ReportDefinition rdf : reports) {
							reportMap.put(rdf.getReportId(), rdf);
						}
						inputStream.close();
						inputStream = null;
					}
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
		return reportMap;
	}

	public ReportDefinition getReportDefinition(String reportId) {
		Map<String, ReportDefinition> reportMap = reload();
		ReportDefinition rdf = reportMap.get(reportId);

		if (rdf != null) {
			if (rdf.getTemplateId() != null) {

			} else if (rdf.getTemplateFile() != null) {
				String filename = SystemProperties.getConfigRootPath() + sp
						+ rdf.getTemplateFile();
				logger.debug("read template:" + filename);
				byte[] data = FileUtils.getBytes(filename);
				rdf.setData(data);
			}
		}

		return rdf;
	}

}