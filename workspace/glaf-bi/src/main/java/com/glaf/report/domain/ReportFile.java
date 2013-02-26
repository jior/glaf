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
package com.glaf.report.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.glaf.core.util.DateUtils;

@Entity
@Table(name = "SYS_REPORT_FILE")
public class ReportFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 报表编号
	 */
	@Column(name = "REPORTID_", length = 50)
	protected String reportId;

	/**
	 * 文件名称
	 */
	@Column(name = "FILENAME_", length = 50)
	protected String filename;

	/**
	 * 文件大小
	 */
	@Column(name = "FILESIZE_")
	protected Integer fileSize;

	/**
	 * 文件内容
	 */
	@Lob
	@Column(name = "FILECONTENT_")
	protected byte[] fileContent;

	/**
	 * 文件年月日
	 */
	@Column(name = "REPORTYEARMONTHDAY_")
	protected Integer reportYearMonthDay;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	public ReportFile() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportFile other = (ReportFile) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public byte[] getFileContent() {
		return this.fileContent;
	}

	public String getFilename() {
		return this.filename;
	}

	public Integer getFileSize() {
		return this.fileSize;
	}

	public String getId() {
		return this.id;
	}

	public String getReportId() {
		return this.reportId;
	}

	public Integer getReportYearMonthDay() {
		return this.reportYearMonthDay;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public ReportFile jsonToObject(JSONObject jsonObject) {
		ReportFile model = new ReportFile();
		if (jsonObject.containsKey("reportId")) {
			model.setReportId(jsonObject.getString("reportId"));
		}
		if (jsonObject.containsKey("filename")) {
			model.setFilename(jsonObject.getString("filename"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		return model;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public void setReportYearMonthDay(Integer reportYearMonthDay) {
		this.reportYearMonthDay = reportYearMonthDay;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		if (reportId != null) {
			jsonObject.put("reportId", reportId);
		}
		if (filename != null) {
			jsonObject.put("filename", filename);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		if (reportId != null) {
			jsonObject.put("reportId", reportId);
		}
		if (filename != null) {
			jsonObject.put("filename", filename);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
