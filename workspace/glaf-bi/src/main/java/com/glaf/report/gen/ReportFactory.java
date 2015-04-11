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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.JFreeChart;

import com.glaf.core.base.TableModel;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.QueryDefinition;
import com.glaf.core.el.ExpressionTools;
import com.glaf.core.el.Mvel2ExpressionEvaluator;
import com.glaf.core.security.Authentication;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.StringTools;
import com.glaf.chart.bean.ChartDataManager;
import com.glaf.chart.domain.Chart;
import com.glaf.chart.gen.JFreeChartFactory;
import com.glaf.chart.gen.ChartGen;
import com.glaf.chart.util.ChartUtils;
import com.glaf.report.domain.Report;
import com.glaf.report.domain.ReportFile;
import com.glaf.report.query.ReportQuery;
import com.glaf.report.service.IReportFileService;
import com.glaf.report.service.IReportService;

public class ReportFactory {
	protected static final Log logger = LogFactory.getLog(ReportFactory.class);

	/**
	 * 创建全部报表文件
	 */
	public static void createAllReportFiles() {
		IReportService reportService = ContextFactory.getBean("reportService");
		ReportQuery query = new ReportQuery();
		List<Report> reports = reportService.list(query);
		if (reports != null && !reports.isEmpty()) {
			createReportFiles(reports);
		}
	}

	/**
	 * 生成图表
	 * 
	 * @param chartDefinition
	 * @param params
	 */
	public static void createChart(Chart chartDefinition,
			Map<String, Object> params) {
		if (chartDefinition != null) {
			ChartGen chartGen = JFreeChartFactory.getChartGen(chartDefinition
					.getChartType());
			if (chartGen != null) {
				JFreeChart chart = chartGen.createChart(chartDefinition);
				byte[] bytes = ChartUtils.createChart(chartDefinition, chart);
				String path = SystemConfig.getReportSavePath() + "/temp/"
						+ SystemConfig.getCurrentYYYYMMDD();
				try {
					FileUtils.mkdirs(path);
				} catch (IOException e) {
				}
				String filename = path + "/" + chartDefinition.getId();
				if ("png".equalsIgnoreCase(chartDefinition.getImageType())) {
					filename = filename + ".png";
				} else {
					filename = filename + ".jpg";
				}
				try {
					FileUtils.save(filename, bytes);
				} catch (Exception ex) {
					throw new RuntimeException("save chart failed", ex);
				}

				if (params != null) {
					if (!params.containsKey(chartDefinition.getChartName())) {
						params.put(chartDefinition.getChartName(), filename);
					}
					if (chartDefinition.getMapping() != null) {
						if (!params.containsKey(chartDefinition.getMapping())) {
							params.put(chartDefinition.getMapping(), filename);
						}
					}
				}
			}
		}
	}

	/**
	 * 创建某个报表的报表文件
	 * 
	 * @param reportId
	 */
	public static void createReportFile(String reportId) {
		IReportService reportService = ContextFactory.getBean("reportService");
		Report report = reportService.getReport(reportId);
		byte[] bytes = createReportStream(report);

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

		String filename = null;
		String reportName = report.getReportName();
		if (reportName != null) {
			filename = ExpressionTools.evaluate(reportName, contextMap);
		}
		ReportFile reportFile = new ReportFile();
		reportFile.setCreateDate(new Date());
		reportFile.setFileContent(bytes);
		reportFile.setFileSize(bytes.length);
		reportFile.setReportId(report.getId());
		reportFile.setReportYearMonthDay(DateUtils.getYearMonthDay(new Date()));
		if (filename != null) {
			reportFile.setFilename(filename + "." + report.getReportFormat());
		} else {
			reportFile.setFilename(report.getId() + "_"
					+ reportFile.getReportYearMonthDay() + "."
					+ report.getReportFormat());
		}
		IReportFileService reportFileService = ContextFactory
				.getBean("reportFileService");
		reportFile.setId(report.getId() + "_"
				+ reportFile.getReportYearMonthDay());
		reportFileService.save(reportFile);
	}

	/**
	 * 创建全部报表文件
	 */
	public static void createReportFiles(List<Report> reports) {
		if (reports != null && !reports.isEmpty()) {
			for (Report report : reports) {
				if (report != null && report.getReportTemplate() != null) {
					createReportFile(report.getId());
				}
			}
		}
	}

	/**
	 * 创建某个报表的报表文件
	 * 
	 * @param report
	 */
	public static byte[] createReportStream(Report report) {
		Map<String, Object> params = SystemConfig.getContextMap();
		byte[] bytes = createReportStream(report, params);
		return bytes;
	}

	/**
	 * 根据指定参数生成
	 * 
	 * @param report
	 * @param params
	 */
	public static byte[] createReportStream(Report report,
			Map<String, Object> params) {
		byte[] rptBytes = null;
		ReportGen reportGen = null;
		if ("jasper".equals(report.getType())) {
			reportGen = new JasperReportGen();
		} else if ("jxls".equals(report.getType())) {
			reportGen = new JxlsReportGen();
		}
		if (reportGen != null) {
			// 准备查询结果集和生成图片信息

			ITablePageService tablePageService = ContextFactory
					.getBean("tablePageService");
			IQueryDefinitionService queryDefinitionService = ContextFactory
					.getBean("queryDefinitionService");

			String json = report.getJsonParameter();
			if (StringUtils.isNotEmpty(json)) {
				Map<String, Object> jsonMap = JsonUtils.decode(json);
				if (jsonMap != null && !jsonMap.isEmpty()) {
					Set<Entry<String, Object>> entrySet = jsonMap.entrySet();
					for (Entry<String, Object> entry : entrySet) {
						String key = entry.getKey();
						Object value = entry.getValue();
						if (!params.containsKey(key)) {
							params.put(key, value);
						}
					}
				}
			}

			logger.debug("params:" + params);

			String value = null;
			String reportMonth = report.getReportMonth();
			if (reportMonth != null) {
				value = ExpressionTools.evaluate(reportMonth, params);
				params.put("reportMonth", value);
			}
			String reportName = report.getReportName();
			if (reportName != null) {
				value = ExpressionTools.evaluate(reportName, params);
				params.put("reportName", value);
			}

			String reportTitleDate = report.getReportTitleDate();
			if (reportTitleDate != null) {
				value = ExpressionTools.evaluate(reportTitleDate, params);
				params.put("reportDate", value);
			}

			String reportDateYYYYMMDD = report.getReportDateYYYYMMDD();
			if (reportDateYYYYMMDD != null) {
				Object val = Mvel2ExpressionEvaluator.evaluate(
						reportDateYYYYMMDD, params);
				params.put("reportDateYYYYMMdd", val);
				params.put("reportDateYYYYMMDD", val);
			}

			if (StringUtils.isNotEmpty(report.getQueryIds())) {
				List<String> queryIds = StringTools.split(report.getQueryIds());
				if (queryIds != null && !queryIds.isEmpty()) {
					for (String queryId : queryIds) {
						boolean success = false;
						int retry = 0;
						while (retry < 2 && !success) {
							try {
								retry++;
								QueryDefinition qd = queryDefinitionService
										.getQueryDefinition(queryId);
								if (qd != null) {
									String querySQL = qd.getSql();
									querySQL = QueryUtils
											.replaceSQLVars(querySQL);
									querySQL = QueryUtils.replaceSQLParas(
											querySQL, params);
									TableModel rowModel = new TableModel();
									rowModel.setSql(querySQL);
									List<Map<String, Object>> rows = tablePageService
											.getListData(querySQL, params);
									if (rows != null && !rows.isEmpty()) {
										if (qd.getMapping() != null) {
											if (!params.containsKey(qd
													.getName())) {
												params.put(qd.getName(), rows);
											}
											if (!params.containsKey(qd
													.getMapping())) {
												params.put(qd.getMapping(),
														rows);
											}
										}
									}
								}
								success = true;
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			}
			if (StringUtils.isNotEmpty(report.getChartIds())) {
				List<String> chartIds = StringTools.split(report.getChartIds());
				if (chartIds != null && !chartIds.isEmpty()) {
					for (String chartId : chartIds) {
						boolean success = false;
						int retry = 0;
						while (retry < 2 && !success) {
							try {
								retry++;
								ChartDataManager manager = new ChartDataManager();
								Chart chart = manager.getChartAndFetchDataById(
										chartId, params, Authentication
												.getAuthenticatedActorId());
								createChart(chart, params);
								success = true;
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			}
			logger.debug("report parameter:" + params);
			// 产生报表
			rptBytes = reportGen.createReport(report, params);
		}
		return rptBytes;
	}

	/**
	 * 创建某个报表的报表文件
	 * 
	 * @param reportId
	 */
	public static byte[] createReportStream(String reportId) {
		IReportService reportService = ContextFactory.getBean("reportService");
		Report report = reportService.getReport(reportId);
		return createReportStream(report);
	}

	public static void genAllReportFile() {
		IReportService reportService = ContextFactory.getBean("reportService");
		ReportQuery query = new ReportQuery();
		List<Report> reports = reportService.list(query);
		if (reports != null && !reports.isEmpty()) {
			for (Report report : reports) {
				boolean success = false;
				int retry = 0;
				while (retry < 2 && !success) {
					try {
						retry++;
						createReportFile(report.getId());
						success = true;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	private ReportFactory() {

	}

}