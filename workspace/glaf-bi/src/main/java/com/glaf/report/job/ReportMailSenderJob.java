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

package com.glaf.report.job;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.context.ContextFactory;
import com.glaf.report.mail.ReportMailSender;
import com.glaf.report.domain.Report;
import com.glaf.report.query.ReportQuery;
import com.glaf.report.service.IReportService;

/**
 * 
 * 每个报表作为一个邮件发送的Job
 * 
 */
public class ReportMailSenderJob implements Job {
	public final static Log log = LogFactory.getLog(ReportMailSenderJob.class);

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		IReportService reportService = ContextFactory.getBean("reportService");
		ReportQuery query = new ReportQuery();
		List<Report> reports = reportService.list(query);
		if (reports != null && !reports.isEmpty()) {
			for (Report rpt : reports) {
				if (rpt != null) {
					ReportMailSender sender = new ReportMailSender();
					sender.sendMail(rpt.getId());
				}
			}
		}
	}

}