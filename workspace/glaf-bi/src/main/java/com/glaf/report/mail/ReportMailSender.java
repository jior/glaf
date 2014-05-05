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

package com.glaf.report.mail;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.base.DataFile;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.el.ExpressionTools;
import com.glaf.core.el.Mvel2ExpressionEvaluator;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;
import com.glaf.mail.MailMessage;
import com.glaf.mail.MailSender;
import com.glaf.mail.util.MailTools;
import com.glaf.template.util.TemplateUtils;
import com.glaf.report.domain.Report;
import com.glaf.report.domain.ReportFile;
import com.glaf.report.domain.ReportTask;
import com.glaf.report.gen.ReportFactory;
import com.glaf.report.query.ReportQuery;
import com.glaf.report.service.IReportFileService;
import com.glaf.report.service.IReportService;
import com.glaf.report.service.IReportTaskService;

public class ReportMailSender {
	protected static final Log logger = LogFactory
			.getLog(ReportMailSender.class);

	private static Configuration conf = BaseConfiguration.create();

	/**
	 * 获取报表的邮件信息
	 * 
	 * @param reportId
	 * @return
	 */
	public MailMessage getAllReportInOneMailMessage() {
		IReportService reportService = ContextFactory.getBean("reportService");
		IReportTaskService reportTaskService = ContextFactory
				.getBean("reportTaskService");
		ReportQuery query = new ReportQuery();
		query.enableFlag("1");
		List<Report> reports = reportService.list(query);

		ReportTask reportTask = reportTaskService
				.getReportTaskByName("report_allInOne");
		if (reportTask != null) {
			return this.getMultiReportsInOneMailMessage(reportTask, reports);
		}
		throw new RuntimeException("report_allInOne can't found");
	}

	/**
	 * 获取报表的邮件信息
	 * 
	 * @param reportId
	 * @return
	 */
	public MailMessage getMultiReportsInOneMailMessage(ReportTask reportTask,
			List<Report> reports) {
		IReportFileService reportFileService = ContextFactory
				.getBean("reportFileService");
		ReportQuery query = new ReportQuery();
		query.enableFlag("1");

		MailMessage mailMessage = new MailMessage();

		String subject = conf.get("report_mail_subject");
		String content = conf.get("report_mail_content");

		Map<String, Object> contextMap = SystemConfig.getContextMap();

		if (StringUtils.isNotEmpty(reportTask.getSendTitle())) {
			subject = ExpressionTools.evaluate(reportTask.getSendTitle(),
					contextMap);
		}
		if (StringUtils.isNotEmpty(reportTask.getSendContent())) {
			content = ExpressionTools.evaluate(reportTask.getSendContent(),
					contextMap);
		}

		if (subject == null) {
			subject = "";
		}
		if (content == null) {
			content = "";
		}

		subject = TemplateUtils.process(contextMap, subject);
		content = TemplateUtils.process(contextMap, content);
		mailMessage.setEncoding("GBK");
		mailMessage.setMailId(UUID32.getUUID());
		mailMessage.setMessageId(UUID32.getUUID());
		mailMessage.setSaveMessage(true);
		mailMessage.setSubject(subject);
		mailMessage.setContent(content);

		Collection<Object> dataFiles = new java.util.ArrayList<Object>();

		if (reports != null && !reports.isEmpty()) {
			for (Report report : reports) {
				String id = report.getId() + "_"
						+ DateUtils.getYearMonthDay(new Date());
				ReportFile reportFile = reportFileService.getReportFile(id);
				if (reportFile == null) {
					ReportFactory.createReportFile(report.getId());
					reportFile = reportFileService.getReportFile(id);
				}
				byte[] bytes = reportFile.getFileContent();
				if (bytes != null) {
					DataFile dataFile = new BlobItemEntity();
					dataFile.setFilename(report.getSubject() + "."
							+ report.getReportFormat());
					dataFile.setName(report.getSubject() + "."
							+ report.getReportFormat());
					dataFile.setData(bytes);
					dataFiles.add(dataFile);
					logger.debug("添加附件：" + dataFile.getFilename());
				}
			}
		}

		List<String> recipients = new java.util.ArrayList<String>();

		if (StringUtils.isNotEmpty(reportTask.getMailRecipient())) {
			recipients.addAll(StringTools.split(reportTask.getMailRecipient()));
		}

		String[] toArray = new String[recipients.size()];
		int index = 0;
		for (String x : recipients) {
			if (x.length() > 5 && MailTools.isMailAddress(x)) {
				toArray[index++] = x;
			}
		}
		logger.debug("附件数量：" + dataFiles.size());
		mailMessage.setTo(toArray);
		mailMessage.setFiles(dataFiles);

		return mailMessage;
	}

	/**
	 * 获取报表的邮件信息
	 * 
	 * @param reportId
	 * @return
	 */
	public MailMessage getReportMailMessage(String reportId) {
		IReportService reportService = ContextFactory.getBean("reportService");
		IReportFileService reportFileService = ContextFactory
				.getBean("reportFileService");
		Report report = reportService.getReport(reportId);
		if (report != null) {
			MailMessage mailMessage = new MailMessage();
			String subject = report.getTextTitle();
			String content = report.getTextContent();
			if (subject == null) {
				subject = report.getSubject();
			}
			if (content == null) {
				content = report.getSubject();
			}

			Map<String, Object> contextMap = SystemConfig.getContextMap();

			String json = report.getJsonParameter();
			if (StringUtils.isNotEmpty(json)) {
				Map<String, Object> jsonMap = JsonUtils.decode(json);
				if (jsonMap != null && !jsonMap.isEmpty()) {
					Set<Entry<String, Object>> entrySet = jsonMap.entrySet();
					for (Entry<String, Object> entry : entrySet) {
						String key = entry.getKey();
						Object value = entry.getValue();
						if (!contextMap.containsKey(key)) {
							contextMap.put(key, value);
						}
					}
				}
			}

			String value = null;
			String reportMonth = report.getReportMonth();
			if (reportMonth != null) {
				value = ExpressionTools.evaluate(reportMonth, contextMap);
				contextMap.put("reportMonth", value);
			}
			String reportName = report.getReportName();
			if (reportName != null) {
				value = ExpressionTools.evaluate(reportName, contextMap);
				contextMap.put("reportName", value);
			}

			String reportTitleDate = report.getReportTitleDate();
			if (reportTitleDate != null) {
				value = ExpressionTools.evaluate(reportTitleDate, contextMap);
				contextMap.put("reportDate", value);
			}

			String reportDateYYYYMMDD = report.getReportDateYYYYMMDD();
			if (reportDateYYYYMMDD != null) {
				Object val = Mvel2ExpressionEvaluator.evaluate(
						reportDateYYYYMMDD, contextMap);
				contextMap.put("reportDateYYYYMMdd", val);
				contextMap.put("reportDateYYYYMMDD", val);
			}

			subject = TemplateUtils.process(contextMap, subject);
			content = TemplateUtils.process(contextMap, content);

			mailMessage.setEncoding("GBK");
			mailMessage.setTo(StringTools.splitToArray(
					report.getMailRecipient(), ","));
			mailMessage.setMailId(UUID32.getUUID());
			mailMessage.setMessageId(UUID32.getUUID());
			mailMessage.setSaveMessage(true);
			mailMessage.setSubject(subject);
			mailMessage.setContent(content);
			Collection<Object> dataFiles = new java.util.ArrayList<Object>();

			byte[] bytes = null;
			try {
				String id = report.getId() + "_"
						+ DateUtils.getYearMonthDay(new Date());
				ReportFile reportFile = reportFileService.getReportFile(id);
				logger.debug("读取附件：" + reportFile.getFilename());
				bytes = reportFile.getFileContent();
				if (bytes != null) {
					DataFile dataFile = new BlobItemEntity();
					dataFile.setFilename(report.getSubject() + "."
							+ report.getReportFormat());
					dataFile.setName(report.getSubject() + "."
							+ report.getReportFormat());
					dataFile.setData(bytes);
					logger.debug("添加附件：" + dataFile.getFilename());
					dataFiles.add(dataFile);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			logger.debug("附件数量：" + dataFiles.size());

			// mailMessage.setDataFiles(dataFiles);
			mailMessage.setFiles(dataFiles);

			return mailMessage;
		}
		return null;
	}

	public void sendAllReportsInOneMail() {
		MailSender mailSender = (MailSender) ContextFactory
				.getBean("mailSender");
		try {
			MailMessage mailMessage = this.getAllReportInOneMailMessage();
			mailSender.send(mailMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("发送邮件失败！", ex);
		}
	}

	public void sendMail(String reportId) {
		MailSender mailSender = (MailSender) ContextFactory
				.getBean("mailSender");
		try {
			MailMessage mailMessage = this.getReportMailMessage(reportId);
			mailSender.send(mailMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("发送邮件失败！", ex);
		}
	}

}