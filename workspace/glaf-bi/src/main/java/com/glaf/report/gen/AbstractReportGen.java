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

package com.glaf.report.gen;

import java.io.IOException;
import java.util.Map;

import com.glaf.report.domain.Report;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.SystemProperties;

import com.glaf.core.util.FileUtils;

public abstract class AbstractReportGen {

	public abstract byte[] createReport(Report report, byte[] templateData,
			Map<String, Object> params);

	public byte[] createReport(Report report, Map<String, Object> params) {
		String path = SystemConfig.getReportSavePath() + "/"
				+ SystemConfig.getCurrentYYYYMM();
		try {
			FileUtils.mkdirs(path);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		path = SystemConfig.getReportSavePath() + "/temp/"
				+ SystemConfig.getCurrentYYYYMMDD();
		try {
			FileUtils.mkdirs(path);
		} catch (IOException e) {
		}

		byte[] templateData = null;
		String templatePath = SystemProperties.getAppPath() + "/"
				+ report.getReportTemplate();
		try {
			templateData = FileUtils.getBytes(templatePath);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("读取报表模板失败", ex);
		}
		if (templateData == null) {
			throw new RuntimeException("没有找到报表模板数据。");
		}
		return createReport(report, templateData, params);
	}

}