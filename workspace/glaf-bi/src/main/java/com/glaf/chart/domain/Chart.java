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

package com.glaf.chart.domain;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.chart.util.ChartJsonFactory;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "BI_CHART")
public class Chart implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Column(name = "NODEID_")
	protected Long nodeId;

	/**
	 * ��ѯ���
	 */
	@Column(name = "QUERYIDS_")
	protected String queryIds;

	/**
	 * ��ѯSQL
	 */
	@Lob
	@Column(name = "QUERYSQL_")
	protected String querySQL;

	/**
	 * ����
	 */
	@Column(name = "SUBJECT_")
	protected String subject;

	/**
	 * ͼ������
	 */
	@Column(name = "CHARTNAME_")
	protected String chartName;

	/**
	 * ͼ������
	 */
	@Column(name = "CHARTTITLE_")
	protected String chartTitle;

	/**
	 * ͼ������
	 */
	@Column(name = "CHARTTYPE_")
	protected String chartType;

	/**
	 * ͼ������
	 */
	@Column(name = "CHARTFONT_")
	protected String chartFont;

	/**
	 * ͼ�������С
	 */
	@Column(name = "CHARTFONTSIZE_")
	protected Integer chartFontSize;

	/**
	 * ͼ�����������
	 */
	@Column(name = "CHARTTITLEFONT_")
	protected String chartTitleFont;

	/**
	 * ͼ������������С
	 */
	@Column(name = "CHARTTITLEFONTSIZE_")
	protected Integer chartTitleFontSize;

	/**
	 * ͼ����
	 */
	@Column(name = "CHARTWIDTH_")
	protected Integer chartWidth;

	/**
	 * ͼ��߶�
	 */
	@Column(name = "CHARTHEIGHT_")
	protected Integer chartHeight;

	/**
	 * �Ƿ���ʾͼ��
	 */
	@Column(name = "LEGEND_")
	protected String legend;

	/**
	 * �Ƿ���ʾtooltip
	 */
	@Column(name = "TOOLTIP_", length = 100)
	protected String tooltip;

	/**
	 * ӳ������
	 */
	@Column(name = "MAPPING_", length = 50)
	protected String mapping;

	/**
	 * X�����ǩ
	 */
	@Column(name = "COORDINATEX_")
	protected String coordinateX;

	/**
	 * Y�����ǩ
	 */
	@Column(name = "COORDINATEY_")
	protected String coordinateY;

	/**
	 * ���Ʒ���
	 */
	@Column(name = "PLOTORIENTATION_")
	protected String plotOrientation;

	/**
	 * ����ͼ������
	 */
	@Column(name = "IMAGETYPE_")
	protected String imageType;

	/**
	 * �Ƿ�����
	 */
	@Column(name = "ENABLEFLAG_", length = 1)
	protected String enableFlag;

	/**
	 * ��������
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	/**
	 * ������
	 */
	@Column(name = "CREATEBY_")
	protected String createBy;

	@javax.persistence.Transient
	public List<ColumnModel> columns = new java.util.ArrayList<ColumnModel>();

	public Chart() {

	}

	public void addCellData(ColumnModel cell) {
		if (columns == null) {
			columns = new java.util.ArrayList<ColumnModel>();
		}
		columns.add(cell);
	}

	public void addColumn(ColumnModel cell) {
		if (columns == null) {
			columns = new java.util.ArrayList<ColumnModel>();
		}
		if (!columns.contains(cell)) {
			columns.add(cell);
		}
	}

	public String getChartFont() {
		return this.chartFont;
	}

	public Integer getChartFontSize() {
		return this.chartFontSize;
	}

	public Integer getChartHeight() {
		return this.chartHeight;
	}

	public String getChartName() {
		return this.chartName;
	}

	public String getChartTitle() {
		return this.chartTitle;
	}

	public String getChartTitleFont() {
		return chartTitleFont;
	}

	public Integer getChartTitleFontSize() {
		return chartTitleFontSize;
	}

	public String getChartType() {
		return this.chartType;
	}

	public Integer getChartWidth() {
		return this.chartWidth;
	}

	public List<ColumnModel> getColumns() {
		if (columns == null) {
			columns = new java.util.ArrayList<ColumnModel>();
		}
		return columns;
	}

	public String getCoordinateX() {
		return this.coordinateX;
	}

	public String getCoordinateY() {
		return this.coordinateY;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public String getId() {
		return this.id;
	}

	public String getImageType() {
		return imageType;
	}

	public String getLegend() {
		return this.legend;
	}

	public String getMapping() {
		return mapping;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public String getPlotOrientation() {
		return this.plotOrientation;
	}

	public String getQueryIds() {
		return this.queryIds;
	}

	public String getQuerySQL() {
		return querySQL;
	}

	public String getSubject() {
		return this.subject;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public Chart jsonToObject(JSONObject jsonObject) {
		return ChartJsonFactory.jsonToObject(jsonObject);
	}

	public void removeColumn(ColumnModel cell) {
		if (columns != null) {
			if (columns.contains(cell)) {
				columns.remove(cell);
			}
		}
	}

	public void setChartFont(String chartFont) {
		this.chartFont = chartFont;
	}

	public void setChartFontSize(Integer chartFontSize) {
		this.chartFontSize = chartFontSize;
	}

	public void setChartHeight(Integer chartHeight) {
		this.chartHeight = chartHeight;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public void setChartTitleFont(String chartTitleFont) {
		this.chartTitleFont = chartTitleFont;
	}

	public void setChartTitleFontSize(Integer chartTitleFontSize) {
		this.chartTitleFontSize = chartTitleFontSize;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public void setChartWidth(Integer chartWidth) {
		this.chartWidth = chartWidth;
	}

	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}

	public void setCoordinateX(String coordinateX) {
		this.coordinateX = coordinateX;
	}

	public void setCoordinateY(String coordinateY) {
		this.coordinateY = coordinateY;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public void setLegend(String legend) {
		this.legend = legend;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setPlotOrientation(String plotOrientation) {
		this.plotOrientation = plotOrientation;
	}

	public void setQueryIds(String queryIds) {
		this.queryIds = queryIds;
	}

	public void setQuerySQL(String querySQL) {
		this.querySQL = querySQL;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public JSONObject toJsonObject() {
		return ChartJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ChartJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}