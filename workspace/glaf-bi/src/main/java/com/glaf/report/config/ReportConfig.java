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

package com.glaf.report.config;

import com.glaf.core.config.SystemConfig;
import com.glaf.report.domain.Report;

public class ReportConfig {

	public static String getReportDestFileName(Report report) {
		String destFileName = SystemConfig.getReportSavePath() + "/"
				+ SystemConfig.getCurrentYYYYMM() + "/"
				+ SystemConfig.getCurrentYYYYMMDD() + "_" + report.getId()
				+ "." + report.getReportFormat();
		return destFileName;
	}

	public static String getReportSaveAsDestFileName(Report report) {
		String destFileName = SystemConfig.getReportSavePath() + "/"
				+ SystemConfig.getCurrentYYYYMM() + "/"
				+ SystemConfig.getCurrentYYYYMMDD() + "_" + report.getSubject()
				+ "." + report.getReportFormat();
		return destFileName;
	}

}