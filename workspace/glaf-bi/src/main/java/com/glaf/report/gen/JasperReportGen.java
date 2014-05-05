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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.SimpleExporterInput;

import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.JdbcUtils;
import com.glaf.report.domain.Report;

public class JasperReportGen extends AbstractReportGen implements ReportGen {

	public byte[] createReport(Report report, byte[] templateData,
			Map<String, Object> params) {
		Connection con = null;
		ByteArrayInputStream bais = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream baos = null;
		BufferedOutputStream bos = null;
		try {
			bais = new ByteArrayInputStream(templateData);
			bis = new BufferedInputStream(bais);

			baos = new ByteArrayOutputStream();
			bos = new BufferedOutputStream(baos);

			con = DBConnectionFactory.getConnection();
			if (report.getReportTemplate().endsWith(".jrxml")) {
				JasperReport jasperReport = JasperCompileManager
						.compileReport(bis);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, params, con);
				if ("pdf".equals(report.getReportFormat())) {
					JasperExportManager.exportReportToPdfStream(jasperPrint,
							bos);
				} else if ("xls".equals(report.getReportFormat())) {
					JRXlsExporter exporter = new JRXlsExporter();
					ExporterInput exporterInput = new SimpleExporterInput(
							jasperPrint);
					exporter.setExporterInput(exporterInput);
					exporter.exportReport();
				}
			} else {
				JasperPrint jasperPrint = JasperFillManager.fillReport(bis,
						params, con);
				if ("pdf".equals(report.getReportFormat())) {
					JasperExportManager.exportReportToPdfStream(jasperPrint,
							bos);
				} else if ("xls".equals(report.getReportFormat())) {
					JRXlsExporter exporter = new JRXlsExporter();
					ExporterInput exporterInput = new SimpleExporterInput(
							jasperPrint);
					exporter.setExporterInput(exporterInput);
					exporter.exportReport();
				}
			}
			bos.flush();
			baos.flush();
			return baos.toByteArray();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(
					"生成" + report.getReportFormat() + "文件出错", ex);
		} finally {
			JdbcUtils.close(con);
			IOUtils.closeQuietly(bais);
			IOUtils.closeQuietly(bis);
			IOUtils.closeQuietly(baos);
			IOUtils.closeQuietly(bos);
		}
	}

}