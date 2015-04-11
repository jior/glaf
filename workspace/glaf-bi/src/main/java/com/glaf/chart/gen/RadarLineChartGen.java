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

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;

import com.glaf.chart.domain.Chart;
import com.glaf.chart.util.ChartUtils;
import com.glaf.core.base.ColumnModel;

public class RadarLineChartGen implements ChartGen {

	public static void main(String[] paramArrayOfString) {
		RadarLineChartGen chartDemo = new RadarLineChartGen();
		Chart chartModel = new Chart();
		chartModel.setChartFont("宋体");
		chartModel.setChartFontSize(15);
		chartModel.setChartHeight(544);
		chartModel.setChartWidth(1066);
		chartModel.setChartTitle("条形图");
		chartModel.setChartTitleFont("宋体");
		chartModel.setChartTitleFontSize(72);
		chartModel.setImageType("png");
		chartModel.setChartName("radarLine");
		chartModel.setChartType("radarLine");
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
				cell1.setDoubleValue(Math.abs(rand.nextInt(20)) * 1.0D);
			}
			chartModel.addCellData(cell1);
			System.out.println(cell1.getDoubleValue());

			ColumnModel cell2 = new ColumnModel();
			cell2.setColumnName("col2_" + i);
			cell2.setCategory(String.valueOf(i));
			cell2.setSeries("Fit");
			if (i <= 20) {
				cell2.setDoubleValue(Math.abs(rand.nextInt(20)) * 1.0D);
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
				cell3.setDoubleValue((40D - cell2.getDoubleValue() - cell1
						.getDoubleValue()) * 1.0D);
			}
			chartModel.addCellData(cell3);
			System.out.println(cell3.getDoubleValue());
		}

		JFreeChart chart = chartDemo.createChart(chartModel);
		ChartUtils.createChart(".", chartModel, chart);
	}

	public JFreeChart createChart(Chart chartModel) {
		ChartUtils.setChartTheme(chartModel);
		CategoryDataset categoryDataset = this.createDataset(chartModel);
		SpiderWebPlot localSpiderWebPlot = new MySpiderWebPlot(categoryDataset);
		JFreeChart localJFreeChart = new JFreeChart(chartModel.getChartTitle(),
				TextTitle.DEFAULT_FONT, localSpiderWebPlot, false);
		LegendTitle localLegendTitle = new LegendTitle(localSpiderWebPlot);
		localLegendTitle.setPosition(RectangleEdge.BOTTOM);
		localJFreeChart.addSubtitle(localLegendTitle);
		ChartUtilities.applyCurrentTheme(localJFreeChart);

		return localJFreeChart;
	}

	public CategoryDataset createDataset(Chart chartModel) {
		DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
		for (ColumnModel cell : chartModel.columns) {
			if (cell.getSeries() != null && cell.getCategory() != null) {
				if (cell.getDoubleValue() != null
						&& cell.getDoubleValue() != 0D)
					localDefaultCategoryDataset.addValue(cell.getDoubleValue(),
							cell.getSeries(), cell.getCategory());
				else
					localDefaultCategoryDataset.addValue(null,
							cell.getSeries(), cell.getCategory());
			}
		}
		return localDefaultCategoryDataset;
	}

}
