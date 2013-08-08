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
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

 import com.glaf.chart.domain.Chart;
import com.glaf.chart.util.ChartUtils;
import com.glaf.core.base.ColumnModel;

public class StackedBarSummationLineChartGen implements ChartGen {
	protected static final Log logger = LogFactory.getLog(StackedBarSummationLineChartGen.class);
	
	public static void main(String[] paramArrayOfString) {
		StackedBarSummationLineChartGen chartDemo = new StackedBarSummationLineChartGen();
		Chart chartModel = new Chart();
		chartModel.setChartFont("宋体");
		chartModel.setChartFontSize(15);
		chartModel.setChartHeight(544);
		chartModel.setChartWidth(1066);
		chartModel.setChartTitle("堆积条形图");
		chartModel.setImageType("png");
		chartModel.setChartName("stackedbar");
		chartModel.setChartType("stackedbar");
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
				cell1.setDoubleValue(Math.abs(rand.nextInt(10)) * 1.0D);
			}
			chartModel.addCellData(cell1);
			System.out.println(cell1.getDoubleValue());

			ColumnModel cell2 = new ColumnModel();
			cell2.setColumnName("col2_" + i);
			cell2.setCategory(String.valueOf(i));
			cell2.setSeries("Fit");
			if (i <= 20) {
				cell2.setDoubleValue(Math.abs(rand.nextInt(10)) * 1.0D);
			}
			chartModel.addCellData(cell2);
			System.out.println(cell2.getDoubleValue());

			ColumnModel cell3 = new ColumnModel();
			cell3.setColumnName("col3_" + i);
			cell3.setCategory(String.valueOf(i));
			cell3.setSeries("Accord");
			if (i > 20) {
				cell3.setDoubleValue(null);
			} else {
				cell3.setDoubleValue((Math.abs(rand.nextInt(20)) + 20D
						- cell2.getDoubleValue() - cell1.getDoubleValue()) * 1.0D);
			}
			chartModel.addCellData(cell3);
			System.out.println(cell3.getDoubleValue());
		}

		JFreeChart chart = chartDemo.createChart(chartModel);
		ChartUtils.createChart(".", chartModel, chart);
	}

	public JFreeChart createChart(Chart chartModel) {
		logger.debug("------------StackedBarSummationLineChartGen.createChart-----------------");
		ChartUtils.setChartTheme(chartModel);
		CategoryDataset localDefaultCategoryDataset1 = this
				.createDataset(chartModel);
		CategoryPlot localCategoryPlot = new CategoryPlot();

		ExtendedStackedBarRenderer localExtendedStackedBarRenderer = new ExtendedStackedBarRenderer();
		localExtendedStackedBarRenderer.setBaseItemLabelsVisible(true);
		localExtendedStackedBarRenderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		localExtendedStackedBarRenderer
				.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		localExtendedStackedBarRenderer.setDrawBarOutline(false);
		localExtendedStackedBarRenderer.setBaseItemLabelsVisible(true);
		localCategoryPlot.setRenderer(localExtendedStackedBarRenderer);

		DecimalFormat localDecimalFormat2 = new DecimalFormat("####");
		localDecimalFormat2.setNegativePrefix("");
		localDecimalFormat2.setNegativeSuffix("");

		localExtendedStackedBarRenderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator(
						"{2}", localDecimalFormat2));

		localExtendedStackedBarRenderer.setBaseItemLabelsVisible(true);

		for (int i = 0; i < 5; i++) {
			if (chartModel.getChartFont() != null
					&& chartModel.getChartFontSize() > 0) {
				localExtendedStackedBarRenderer.setSeriesItemLabelFont(i,
						new Font(chartModel.getChartFont(), Font.PLAIN,
								chartModel.getChartFontSize()));
				localExtendedStackedBarRenderer.setSeriesItemLabelsVisible(i,
						true);
			} else {
				localExtendedStackedBarRenderer.setSeriesItemLabelFont(i,
						new Font("宋体", Font.PLAIN, 13));
				localExtendedStackedBarRenderer.setSeriesItemLabelsVisible(i,
						true);
			}
		}

		// 设置柱子上比例数值的显示，如果按照默认方式显示，数值为方向正常显示
		// 设置柱子上显示的数据旋转90度,最后一个参数为旋转的角度值/3.14
		ItemLabelPosition itemLabelPosition = new ItemLabelPosition(
				ItemLabelAnchor.CENTER, TextAnchor.CENTER, TextAnchor.CENTER,
				-1.57D);

		// 下面的设置是为了解决，当柱子的比例过小，而导致表示该柱子比例的数值无法显示的问题
		// 设置不能在柱子上正常显示的那些数值的显示方式，将这些数值显示在柱子外面
		ItemLabelPosition itemLabelPositionFallback = new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT,
				TextAnchor.HALF_ASCENT_LEFT, -1.57D);

		localExtendedStackedBarRenderer
				.setBasePositiveItemLabelPosition(itemLabelPosition);
		localExtendedStackedBarRenderer
				.setBaseNegativeItemLabelPosition(itemLabelPositionFallback);

		for (int i = 0; i < 5; i++) {
			// 设置正常显示的柱子label的position
			localExtendedStackedBarRenderer.setSeriesPositiveItemLabelPosition(
					i, itemLabelPosition);
			localExtendedStackedBarRenderer.setSeriesNegativeItemLabelPosition(
					i, itemLabelPosition);
		}

		// 设置不能正常显示的柱子label的position
		localExtendedStackedBarRenderer
				.setPositiveItemLabelPositionFallback(itemLabelPositionFallback);
		localExtendedStackedBarRenderer
				.setNegativeItemLabelPositionFallback(itemLabelPositionFallback);

		localCategoryPlot.setDataset(localDefaultCategoryDataset1);
		localCategoryPlot.setRenderer(localExtendedStackedBarRenderer);
		localCategoryPlot.setDomainAxis(new CategoryAxis(chartModel
				.getCoordinateX()));
		localCategoryPlot.setRangeAxis(new NumberAxis(chartModel
				.getCoordinateY()));
		localCategoryPlot.setOrientation(PlotOrientation.VERTICAL);
		localCategoryPlot.setRangeGridlinesVisible(true);
		localCategoryPlot.setDomainGridlinesVisible(true);

		CategoryDataset localDefaultCategoryDataset2 = this
				.createSumDataset(chartModel);
		LineAndShapeRenderer localLineAndShapeRenderer = new LineAndShapeRenderer();

		localLineAndShapeRenderer.setBaseShapesVisible(true);
		localLineAndShapeRenderer.setDrawOutlines(true);
		localLineAndShapeRenderer.setUseFillPaint(true);
		localLineAndShapeRenderer.setBaseItemLabelsVisible(true);// 显示数值
		localLineAndShapeRenderer.setBaseFillPaint(Color.white);

		for (int i = 0; i <= 6; i++) {
			localLineAndShapeRenderer.setSeriesStroke(i, new BasicStroke(6.0F));
			localLineAndShapeRenderer.setSeriesOutlineStroke(i,
					new BasicStroke(4.0F));
			localLineAndShapeRenderer.setSeriesShape(i, new Ellipse2D.Double(
					-5.0D, -5.0D, 10.0D, 10.0D));
		}

		localLineAndShapeRenderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());

		NumberAxis localNumberAxis = new NumberAxis("");
		localNumberAxis.setStandardTickUnits(NumberAxis
				.createIntegerTickUnits());
		DecimalFormat localDecimalFormat3 = new DecimalFormat("####");
		localDecimalFormat3.setNegativePrefix("");
		localDecimalFormat3.setNegativeSuffix("");
		//localNumberAxis.setAutoRangeIncludesZero(false);
		//localNumberAxis.setUpperMargin(0.12D);
		
		localNumberAxis.setNumberFormatOverride(localDecimalFormat3);
		localCategoryPlot.setRangeAxis(1, localNumberAxis);
		localCategoryPlot.setDataset(1, localDefaultCategoryDataset2);
		localCategoryPlot.setRenderer(1, localLineAndShapeRenderer);
		localCategoryPlot.mapDatasetToRangeAxis(1, 1);

		JFreeChart localJFreeChart = new JFreeChart(localCategoryPlot);
		localJFreeChart.setTitle(chartModel.getChartTitle());
		ChartUtilities.applyCurrentTheme(localJFreeChart);
		return localJFreeChart;
	}

	public CategoryDataset createDataset(Chart chartModel) {
		DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
		for (ColumnModel cell : chartModel.getColumns()) {
			if (cell.getSeries() != null && cell.getCategory() != null) {
				localDefaultCategoryDataset.addValue(cell.getDoubleValue(),
						cell.getSeries(), cell.getCategory());
			}
		}
		return localDefaultCategoryDataset;
	}

	public CategoryDataset createSumDataset(Chart chartModel) {
		DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
		Map<String, Double> total = new HashMap<String, Double>();
		for (ColumnModel cell : chartModel.getColumns()) {
			if (cell.getSeries() != null && cell.getCategory() != null) {
				Double value = total.get(cell.getSeries());
				if (value == null) {
					value = 0D;
				}
				if (cell.getDoubleValue() != null) {
					value += cell.getDoubleValue();
					total.put(cell.getSeries(), value);
					localDefaultCategoryDataset.addValue(value,
							cell.getSeries(), cell.getCategory());
				} else {
					localDefaultCategoryDataset.addValue(null,
							cell.getSeries(), cell.getCategory());
				}
			}
		}
		return localDefaultCategoryDataset;
	}

}
