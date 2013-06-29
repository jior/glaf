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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;

@Entity
@Table(name = "BI_CHART")
public class Chart implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 查询编号
	 */
	@Column(name = "QUERYIDS_")
	protected String queryIds;

	/**
	 * 查询SQL
	 */
	@Lob
	@Column(name = "QUERYSQL_")
	protected String querySQL;

	/**
	 * 标题
	 */
	@Column(name = "SUBJECT_")
	protected String subject;

	/**
	 * 图表名称
	 */
	@Column(name = "CHARTNAME_")
	protected String chartName;

	/**
	 * 图表主题
	 */
	@Column(name = "CHARTTITLE_")
	protected String chartTitle;

	/**
	 * 图表类型
	 */
	@Column(name = "CHARTTYPE_")
	protected String chartType;

	/**
	 * 图表字体
	 */
	@Column(name = "CHARTFONT_")
	protected String chartFont;

	/**
	 * 图表字体大小
	 */
	@Column(name = "CHARTFONTSIZE_")
	protected Integer chartFontSize;

	/**
	 * 图表标题栏字体
	 */
	@Column(name = "CHARTTITLEFONT_")
	protected String chartTitleFont;

	/**
	 * 图表标题栏字体大小
	 */
	@Column(name = "CHARTTITLEFONTSIZE_")
	protected Integer chartTitleFontSize;

	/**
	 * 图表宽带
	 */
	@Column(name = "CHARTWIDTH_")
	protected Integer chartWidth;

	/**
	 * 图表高度
	 */
	@Column(name = "CHARTHEIGHT_")
	protected Integer chartHeight;

	/**
	 * 是否显示图例
	 */
	@Column(name = "LEGEND_")
	protected String legend;

	/**
	 * 是否显示tooltip
	 */
	@Column(name = "TOOLTIP_", length = 100)
	protected String tooltip;

	/**
	 * 映射名称
	 */
	@Column(name = "MAPPING_", length = 50)
	protected String mapping;

	/**
	 * X坐标标签
	 */
	@Column(name = "COORDINATEX_")
	protected String coordinateX;

	/**
	 * Y坐标标签
	 */
	@Column(name = "COORDINATEY_")
	protected String coordinateY;

	/**
	 * 绘制方向
	 */
	@Column(name = "PLOTORIENTATION_")
	protected String plotOrientation;

	/**
	 * 生成图像类型
	 */
	@Column(name = "IMAGETYPE_")
	protected String imageType;

	/**
	 * 是否启用
	 */
	@Column(name = "ENABLEFLAG_", length = 1)
	protected String enableFlag;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_")
	protected String createBy;

	@javax.persistence.Transient
	public List<ColumnModel> columns = new ArrayList<ColumnModel>();

	public Chart() {

	}

	public void addCellData(ColumnModel cell) {
		if (columns == null) {
			columns = new ArrayList<ColumnModel>();
		}
		columns.add(cell);
	}

	public void addColumn(ColumnModel cell) {
		if (columns == null) {
			columns = new ArrayList<ColumnModel>();
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
			columns = new ArrayList<ColumnModel>();
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
		Chart model = new Chart();
		if (jsonObject.containsKey("queryIds")) {
			model.setQueryIds(jsonObject.getString("queryIds"));
		}
		if (jsonObject.containsKey("querySQL")) {
			model.setQuerySQL(jsonObject.getString("querySQL"));
		}
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("chartName")) {
			model.setChartName(jsonObject.getString("chartName"));
		}
		if (jsonObject.containsKey("chartTitle")) {
			model.setChartTitle(jsonObject.getString("chartTitle"));
		}
		if (jsonObject.containsKey("chartType")) {
			model.setChartType(jsonObject.getString("chartType"));
		}
		if (jsonObject.containsKey("chartFont")) {
			model.setChartFont(jsonObject.getString("chartFont"));
		}
		if (jsonObject.containsKey("chartTitleFont")) {
			model.setChartTitleFont(jsonObject.getString("chartTitleFont"));
		}
		if (jsonObject.containsKey("legend")) {
			model.setLegend(jsonObject.getString("legend"));
		}
		if (jsonObject.containsKey("tooltip")) {
			model.setTooltip(jsonObject.getString("tooltip"));
		}
		if (jsonObject.containsKey("mapping")) {
			model.setMapping(jsonObject.getString("mapping"));
		}
		if (jsonObject.containsKey("coordinateX")) {
			model.setCoordinateX(jsonObject.getString("coordinateX"));
		}
		if (jsonObject.containsKey("coordinateY")) {
			model.setCoordinateY(jsonObject.getString("coordinateY"));
		}
		if (jsonObject.containsKey("plotOrientation")) {
			model.setPlotOrientation(jsonObject.getString("plotOrientation"));
		}
		if (jsonObject.containsKey("imageType")) {
			model.setImageType(jsonObject.getString("imageType"));
		}
		if (jsonObject.containsKey("enableFlag")) {
			model.setEnableFlag(jsonObject.getString("enableFlag"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		return model;
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
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		if (queryIds != null) {
			jsonObject.put("queryIds", queryIds);
		}
		if (querySQL != null) {
			jsonObject.put("querySQL", querySQL);
		}
		if (subject != null) {
			jsonObject.put("subject", subject);
		}
		if (chartName != null) {
			jsonObject.put("chartName", chartName);
		}
		if (chartTitle != null) {
			jsonObject.put("chartTitle", chartTitle);
		}
		if (chartType != null) {
			jsonObject.put("chartType", chartType);
		}
		if (chartFont != null) {
			jsonObject.put("chartFont", chartFont);
		}
		if (chartTitleFont != null) {
			jsonObject.put("chartTitleFont", chartTitleFont);
		}
		if (legend != null) {
			jsonObject.put("legend", legend);
		}
		if (tooltip != null) {
			jsonObject.put("tooltip", tooltip);
		}
		if (mapping != null) {
			jsonObject.put("mapping", mapping);
		}
		if (coordinateX != null) {
			jsonObject.put("coordinateX", coordinateX);
		}
		if (coordinateY != null) {
			jsonObject.put("coordinateY", coordinateY);
		}
		if (plotOrientation != null) {
			jsonObject.put("plotOrientation", plotOrientation);
		}
		if (imageType != null) {
			jsonObject.put("imageType", imageType);
		}
		if (enableFlag != null) {
			jsonObject.put("enableFlag", enableFlag);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		if (queryIds != null) {
			jsonObject.put("queryIds", queryIds);
		}
		if (querySQL != null) {
			jsonObject.put("querySQL", querySQL);
		}
		if (subject != null) {
			jsonObject.put("subject", subject);
		}
		if (chartName != null) {
			jsonObject.put("chartName", chartName);
		}
		if (chartTitle != null) {
			jsonObject.put("chartTitle", chartTitle);
		}
		if (chartType != null) {
			jsonObject.put("chartType", chartType);
		}
		if (chartFont != null) {
			jsonObject.put("chartFont", chartFont);
		}
		if (chartTitleFont != null) {
			jsonObject.put("chartTitleFont", chartTitleFont);
		}
		if (legend != null) {
			jsonObject.put("legend", legend);
		}
		if (tooltip != null) {
			jsonObject.put("tooltip", tooltip);
		}
		if (mapping != null) {
			jsonObject.put("mapping", mapping);
		}
		if (coordinateX != null) {
			jsonObject.put("coordinateX", coordinateX);
		}
		if (coordinateY != null) {
			jsonObject.put("coordinateY", coordinateY);
		}
		if (plotOrientation != null) {
			jsonObject.put("plotOrientation", plotOrientation);
		}
		if (imageType != null) {
			jsonObject.put("imageType", imageType);
		}
		if (enableFlag != null) {
			jsonObject.put("enableFlag", enableFlag);
		}
		if (createDate != null) {
			jsonObject.put("createDate", DateUtils.getDate(createDate));
			jsonObject.put("createDate_date", DateUtils.getDate(createDate));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(createDate));
		}
		if (createBy != null) {
			jsonObject.put("createBy", createBy);
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}