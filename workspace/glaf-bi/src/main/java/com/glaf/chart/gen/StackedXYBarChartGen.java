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

package com.glaf.chart.gen;

import java.awt.Color;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StackedXYBarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.data.time.Year;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

import com.glaf.chart.domain.Chart;
import com.glaf.chart.util.ChartUtils;
import com.glaf.core.base.ColumnModel;

public class StackedXYBarChartGen implements ChartGen {

	public static void main(String[] paramArrayOfString) {
		StackedXYBarChartGen chartDemo = new StackedXYBarChartGen();
		Chart chartModel = new Chart();
		chartModel.setChartFont("宋体");
		chartModel.setChartFontSize(12);
		chartModel.setChartHeight(500);
		chartModel.setChartWidth(1500);
		chartModel.setChartTitle("堆积条形图");
		chartModel.setImageType("png");
		chartModel.setChartName("stackedxybar");
		chartModel.setChartType("stackedxybar");
		chartModel.setCoordinateX("日期");
		chartModel.setCoordinateY("量产");
		java.util.Random rand = new java.util.Random();
		for (int i = 2011; i <= 2030; i++) {
			ColumnModel cell = new ColumnModel();
			cell.setIntValue(i);
			cell.setColumnName("col_" + i);
			if (i % 3 == 0) {
				cell.setSeries("Fit");
			}
			if (i % 3 == 1) {
				cell.setSeries("Accord");
			}
			if (i % 3 == 2) {
				cell.setSeries("Crosstour");
			}
			cell.setDoubleValue(rand.nextInt(10) + 1.0);
			chartModel.addCellData(cell);
		}
		for (int i = 2011; i <= 2030; i++) {
			ColumnModel cell = new ColumnModel();
			cell.setIntValue(i);
			cell.setColumnName("col_" + i);
			if (i % 3 == 1) {
				cell.setSeries("Fit");
			}
			if (i % 3 == 2) {
				cell.setSeries("Accord");
			}
			if (i % 3 == 0) {
				cell.setSeries("Crosstour");
			}
			cell.setDoubleValue(rand.nextInt(10) + 1.0);
			chartModel.addCellData(cell);
		}
		for (int i = 2001; i <= 2030; i++) {
			ColumnModel cell = new ColumnModel();
			cell.setIntValue(i);
			cell.setColumnName("col_" + i);
			if (i % 3 == 1) {
				cell.setSeries("Fit");
			}
			if (i % 3 == 0) {
				cell.setSeries("Accord");
			}
			if (i % 3 == 2) {
				cell.setSeries("Crosstour");
			}
			cell.setDoubleValue(rand.nextInt(10) + 1.0);
			chartModel.addCellData(cell);
		}
		JFreeChart chart = chartDemo.createChart(chartModel);
		ChartUtils.createChart(".", chartModel, chart);
	}

	public JFreeChart createChart(Chart chartModel) {
		ChartUtils.setChartTheme(chartModel);
		TableXYDataset paramTableXYDataset = this.createDataset(chartModel);
		DateAxis localDateAxis = new DateAxis(chartModel.getCoordinateX());
		localDateAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
		localDateAxis.setLowerMargin(0.01D);
		localDateAxis.setUpperMargin(0.01D);
		NumberAxis localNumberAxis = new NumberAxis(chartModel.getCoordinateY());
		localNumberAxis.setStandardTickUnits(NumberAxis
				.createIntegerTickUnits());
		localNumberAxis.setUpperMargin(0.1D);
		StackedXYBarRenderer localStackedXYBarRenderer = new StackedXYBarRenderer(
				0.15D);
		localStackedXYBarRenderer.setDrawBarOutline(false);
		localStackedXYBarRenderer.setBaseItemLabelsVisible(true);
		localStackedXYBarRenderer
				.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
		localStackedXYBarRenderer
				.setBasePositiveItemLabelPosition(new ItemLabelPosition(
						ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER));

		XYPlot localXYPlot = new XYPlot(paramTableXYDataset, localDateAxis,
				localNumberAxis, localStackedXYBarRenderer);
		JFreeChart localJFreeChart = new JFreeChart(chartModel.getChartTitle(),
				localXYPlot);
		localJFreeChart.removeLegend();

		// localJFreeChart.setTextAntiAlias(RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
		LegendTitle localLegendTitle = new LegendTitle(localXYPlot);
		localLegendTitle.setBackgroundPaint(Color.white);
		// localLegendTitle.setFrame(new BlockBorder());
		localLegendTitle.setPosition(RectangleEdge.TOP);
		localJFreeChart.addSubtitle(localLegendTitle);
		// ChartUtilities.applyCurrentTheme(localJFreeChart);
		return localJFreeChart;
	}

	public TableXYDataset createDataset(Chart chartModel) {
		TimeTableXYDataset localTimeTableXYDataset = new TimeTableXYDataset();
		for (ColumnModel cell : chartModel.columns) {
			if (cell.getSeries() != null && cell.getCategory() != null
					&& cell.getDoubleValue() > 0) {
				localTimeTableXYDataset.add(new Year(cell.getIntValue()),
						cell.getDoubleValue(), cell.getSeries());
			}
		}

		return localTimeTableXYDataset;
	}

}