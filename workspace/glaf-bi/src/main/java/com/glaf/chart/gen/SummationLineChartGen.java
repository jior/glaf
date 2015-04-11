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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.LineRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.glaf.chart.domain.Chart;
import com.glaf.chart.util.ChartUtils;
import com.glaf.core.base.ColumnModel;

public class SummationLineChartGen implements ChartGen {
	int chartType = 0;

	public static void main(String[] paramArrayOfString) {
		SummationLineChartGen chartDemo = new SummationLineChartGen();
		Chart chartModel = new Chart();
		chartModel.setChartFont("宋体");
		chartModel.setChartFontSize(25);
		chartModel.setChartHeight(800);
		chartModel.setChartWidth(1800);
		chartModel.setChartTitleFont("宋体");
		chartModel.setChartTitleFontSize(72);
		chartModel.setChartTitle("生产下线趋势图");
		chartModel.setImageType("png");
		chartModel.setChartName("line");
		chartModel.setChartType("line");
		chartModel.setCoordinateX("日期");
		chartModel.setCoordinateY("量产");
		java.util.Random rand = new java.util.Random();

		for (int i = 1; i <= 30; i++) {
			System.out.println("---------------------------");
			ColumnModel cell1 = new ColumnModel();
			cell1.setCategory(String.valueOf(i));
			cell1.setIntValue(i);
			cell1.setColumnName("col_" + i);
			cell1.setSeries("Cross");
			if (i <= 20) {
				cell1.setDoubleValue(rand.nextInt(50) * 1.0D);
			}
			chartModel.addCellData(cell1);
			System.out.println(cell1.getDoubleValue());

			ColumnModel cell2 = new ColumnModel();
			cell2.setColumnName("col2_" + i);
			cell2.setCategory(String.valueOf(i));
			cell2.setSeries("Fit");
			if (i <= 20) {
				cell2.setDoubleValue(rand.nextInt(50) * 1.0D);
			}
			chartModel.addCellData(cell2);
			System.out.println(cell2.getDoubleValue());

			ColumnModel cell3 = new ColumnModel();
			cell3.setColumnName("col3_" + i);
			cell3.setCategory(String.valueOf(i));
			cell3.setSeries("Accord");
			if (i <= 20) {
				cell3.setDoubleValue((94.999999D - cell2.getDoubleValue() - cell1
						.getDoubleValue()) * 1.0D);
			}
			chartModel.addCellData(cell3);

			ColumnModel cell4 = new ColumnModel();
			cell4.setColumnName("col4_" + i);
			cell4.setCategory(String.valueOf(i));
			cell4.setSeries("Accord1");
			if (i <= 20) {
				cell4.setDoubleValue(rand.nextInt(50) * 1.0D);
			}
			chartModel.addCellData(cell4);
			System.out.println(cell4.getDoubleValue());

			ColumnModel cell5 = new ColumnModel();
			cell5.setColumnName("col5_" + i);
			cell5.setCategory(String.valueOf(i));
			cell5.setSeries("Accord2");
			if (i <= 20) {
				cell5.setDoubleValue(rand.nextInt(50) * 1.0D);
			}
			chartModel.addCellData(cell5);
			System.out.println(cell5.getDoubleValue());

			ColumnModel cell6 = new ColumnModel();
			cell6.setColumnName("col6_" + i);
			cell6.setCategory(String.valueOf(i));
			cell6.setSeries("Accord3");
			if (i <= 20) {
				cell6.setDoubleValue(rand.nextInt(50) * 1.0D);
			}
			chartModel.addCellData(cell6);
			System.out.println(cell6.getDoubleValue());
		}

		JFreeChart chart = chartDemo.createChart(chartModel);
		ChartUtils.createChart(".", chartModel, chart);
	}

	public JFreeChart createChart(Chart chartModel) {
		ChartUtils.setChartTheme(chartModel);
		CategoryDataset categoryDataset = this.createDataset(chartModel);
		JFreeChart localJFreeChart = null;
		if (StringUtils.equals(chartModel.getEnable3DFlag(), "1")) {
			localJFreeChart = ChartFactory.createLineChart3D(
					chartModel.getChartTitle(), chartModel.getCoordinateX(),
					chartModel.getCoordinateY(), categoryDataset,
					PlotOrientation.VERTICAL, true, true, false);
		} else {
			localJFreeChart = ChartFactory.createLineChart(
					chartModel.getChartTitle(), chartModel.getCoordinateX(),
					chartModel.getCoordinateY(), categoryDataset,
					PlotOrientation.VERTICAL, true, true, false);
		}
		CategoryPlot localCategoryPlot = (CategoryPlot) localJFreeChart
				.getPlot();
		localCategoryPlot.setRangeZeroBaselineVisible(false);
		NumberAxis localNumberAxis = (NumberAxis) localCategoryPlot
				.getRangeAxis();
		localNumberAxis.setStandardTickUnits(NumberAxis
				.createIntegerTickUnits());
		localNumberAxis.setStandardTickUnits(NumberAxis
				.createIntegerTickUnits());
		localNumberAxis.setAutoRangeIncludesZero(false);
		localNumberAxis.setUpperMargin(0.12D);

		if (StringUtils.equals(chartModel.getEnable3DFlag(), "1")) {
			LineRenderer3D lineRenderer = (LineRenderer3D) localCategoryPlot
					.getRenderer();
			lineRenderer.setBaseShapesVisible(true);
			lineRenderer.setDrawOutlines(true);
			lineRenderer.setUseFillPaint(true);
			lineRenderer.setBaseItemLabelsVisible(true);// 显示数值
			lineRenderer.setBaseFillPaint(Color.white);
			lineRenderer
					.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		} else {
			LineAndShapeRenderer localLineAndShapeRenderer = (LineAndShapeRenderer) localCategoryPlot
					.getRenderer();

			localLineAndShapeRenderer.setBaseShapesVisible(true);
			localLineAndShapeRenderer.setDrawOutlines(true);
			localLineAndShapeRenderer.setUseFillPaint(true);
			localLineAndShapeRenderer.setBaseItemLabelsVisible(true);// 显示数值
			localLineAndShapeRenderer.setBaseFillPaint(Color.white);

			Color[] color = new Color[8];
			color[0] = Color.red;
			color[1] = Color.red;
			color[2] = Color.blue;
			color[3] = Color.blue;
			color[4] = Color.yellow;
			color[5] = Color.yellow;
			color[6] = Color.green;
			color[7] = Color.green;
			float dashes[] = { 8.0f };
			for (int i = 0; i < 8; i++) {
				BasicStroke brokenLine = new BasicStroke(6f,
						BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.f,
						dashes, 0.0f);
				if (chartType == 0) {
					brokenLine = new BasicStroke(8.0F);
				} else {
					if (i % 2 == 0) {
						brokenLine = new BasicStroke(8.0F);
					}
					localLineAndShapeRenderer.setSeriesPaint(i, color[i]);
				}
				localLineAndShapeRenderer.setSeriesStroke(i, brokenLine);
				localLineAndShapeRenderer.setSeriesOutlineStroke(i,
						new BasicStroke(5.0F));
				localLineAndShapeRenderer.setSeriesShape(i,
						new Ellipse2D.Double(-5.0D, -5.0D, 10.0D, 10.0D));
			}
			localLineAndShapeRenderer
					.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		}

		return localJFreeChart;
	}

	public CategoryDataset createDataset(Chart chartModel) {
		DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
		Map<String, Double> total = new java.util.HashMap<String, Double>();
		for (ColumnModel cell : chartModel.getColumns()) {
			if (cell.getSeries() != null && cell.getCategory() != null) {
				Double d = total.get(cell.getSeries());
				if (d == null) {
					d = 0D;
				}
				if (cell.getDoubleValue() != null) {
					if (chartType == 1) {
						d += cell.getDoubleValue();
					} else {
						d = cell.getDoubleValue();
					}
					total.put(cell.getSeries(), d);
					localDefaultCategoryDataset.addValue(d, cell.getSeries(),
							cell.getCategory());
				} else {
					if (chartType == 1) {
						localDefaultCategoryDataset.addValue(d,
								cell.getSeries(), cell.getCategory());
					} else {
						localDefaultCategoryDataset.addValue(null,
								cell.getSeries(), cell.getCategory());
					}
				}
			}
		}
		return localDefaultCategoryDataset;
	}

}
