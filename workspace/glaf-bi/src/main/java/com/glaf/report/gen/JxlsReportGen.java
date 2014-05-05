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
import java.sql.Connection;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;

import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.JdbcUtils;
import com.glaf.report.domain.Report;

public class JxlsReportGen extends AbstractReportGen implements ReportGen {

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
			params.put("con", con);

			XLSTransformer transformer = new XLSTransformer();

			Workbook workbook = transformer.transformXLS(bis, params);
			workbook.write(bos);

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