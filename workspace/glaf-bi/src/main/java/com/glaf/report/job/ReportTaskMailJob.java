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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.mail.MailMessage;
import com.glaf.mail.MailSender;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.StringTools;
import com.glaf.report.domain.Report;
import com.glaf.report.domain.ReportTask;
import com.glaf.report.mail.ReportMailSender;
import com.glaf.report.query.ReportQuery;
import com.glaf.report.service.IReportService;
import com.glaf.report.service.IReportTaskService;

public class ReportTaskMailJob implements Job {
	public final static Log log = LogFactory.getLog(ReportTaskMailJob.class);

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String taskId = (String) context.getJobDetail().getJobDataMap()
				.get("taskId");
		if (StringUtils.isNotEmpty(taskId)) {
			boolean success = false;
			int retry = 0;
			while (retry < 2 && !success) {
				try {
					retry++;
					MailSender mailSender = (MailSender) ContextFactory
							.getBean("mailSender");
					IReportService reportService = ContextFactory
							.getBean("reportService");
					IReportTaskService reportTaskService = ContextFactory
							.getBean("reportTaskService");
					ReportTask reportTask = reportTaskService
							.getReportTask(taskId);
					if (reportTask != null
							&& StringUtils
									.isNotEmpty(reportTask.getReportIds())) {
						ReportQuery query = new ReportQuery();
						query.enableFlag("1");
						query.reportIds(StringTools.split(reportTask
								.getReportIds()));
						List<Report> reports = reportService.list(query);
						ReportMailSender sender = new ReportMailSender();
						MailMessage mailMessage = sender
								.getMultiReportsInOneMailMessage(reportTask,
										reports);
						mailSender.send(mailMessage);
						success = true;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}