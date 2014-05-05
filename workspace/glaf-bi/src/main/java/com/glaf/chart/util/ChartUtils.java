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

package com.glaf.chart.util;

import java.awt.Font;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.servlet.ServletUtilities;

import com.glaf.chart.domain.Chart;

public class ChartUtils {

	public static byte[] createChart(Chart chartModel, JFreeChart chart) {
		ByteArrayOutputStream baos = null;
		BufferedOutputStream bos = null;
		try {
			baos = new ByteArrayOutputStream();
			bos = new BufferedOutputStream(baos);
			java.awt.image.BufferedImage bi = chart.createBufferedImage(
					chartModel.getChartWidth(), chartModel.getChartHeight());

			if ("png".equalsIgnoreCase(chartModel.getImageType())) {
				EncoderUtil.writeBufferedImage(bi, chartModel.getImageType(),
						bos);
				ChartRenderingInfo info = new ChartRenderingInfo(
						new StandardEntityCollection());
				ServletUtilities.saveChartAsPNG(chart,
						chartModel.getChartWidth(),
						chartModel.getChartHeight(), info, null);
			} else if ("jpeg".equalsIgnoreCase(chartModel.getImageType())) {
				EncoderUtil.writeBufferedImage(bi, chartModel.getImageType(),
						bos);
				ChartRenderingInfo info = new ChartRenderingInfo(
						new StandardEntityCollection());
				ServletUtilities.saveChartAsJPEG(chart,
						chartModel.getChartWidth(),
						chartModel.getChartHeight(), info, null);
			}

			bos.flush();
			baos.flush();

			return baos.toByteArray();

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeQuietly(baos);
			IOUtils.closeQuietly(bos);
		}
	}

	public static void createChart(String path, Chart chartModel,
			JFreeChart chart) {
		try {
			java.awt.image.BufferedImage bi = chart.createBufferedImage(
					chartModel.getChartWidth(), chartModel.getChartHeight());

			String name = chartModel.getChartName();
			if (StringUtils.isNotEmpty(chartModel.getMapping())) {
				name = chartModel.getMapping();
			}

			if ("png".equalsIgnoreCase(chartModel.getImageType())) {
				EncoderUtil.writeBufferedImage(bi, chartModel.getImageType(),
						new FileOutputStream(path + "/" + name + ".png"));
				ChartRenderingInfo info = new ChartRenderingInfo(
						new StandardEntityCollection());
				ServletUtilities.saveChartAsPNG(chart,
						chartModel.getChartWidth(),
						chartModel.getChartHeight(), info, null);
			} else if ("jpeg".equalsIgnoreCase(chartModel.getImageType())) {
				EncoderUtil.writeBufferedImage(bi, chartModel.getImageType(),
						new FileOutputStream(path + "/" + name + ".jpg"));
				ChartRenderingInfo info = new ChartRenderingInfo(
						new StandardEntityCollection());
				ServletUtilities.saveChartAsJPEG(chart,
						chartModel.getChartWidth(),
						chartModel.getChartHeight(), info, null);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public static void setChartTheme(Chart chartModel) {
		// 创建主题样式
		StandardChartTheme standardChartTheme = new StandardChartTheme("cn");
		// 设置标题字体
		if (chartModel.getChartTitleFont() != null
				&& chartModel.getChartTitleFontSize() != null
				&& chartModel.getChartTitleFontSize() > 0) {
			standardChartTheme.setExtraLargeFont(new Font(chartModel
					.getChartTitleFont(), Font.BOLD, chartModel
					.getChartTitleFontSize()));
		} else {
			standardChartTheme.setExtraLargeFont(new Font("宋体", Font.BOLD, 18));
		}
		if (chartModel.getChartFont() != null
				&& chartModel.getChartFontSize() > 0) {
			// 设置图例的字体
			standardChartTheme
					.setRegularFont(new Font(chartModel.getChartFont(),
							Font.PLAIN, chartModel.getChartFontSize()));
			// 设置轴向的字体
			standardChartTheme.setLargeFont(new Font(chartModel.getChartFont(),
					Font.PLAIN, chartModel.getChartFontSize()));
		} else {
			// 设置图例的字体
			standardChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 14));
			// 设置轴向的字体
			standardChartTheme.setLargeFont(new Font("宋体", Font.PLAIN, 14));
		}
		// 应用主题样式
		ChartFactory.setChartTheme(standardChartTheme);
	}

}