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

import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.ClassUtils;

public class JFreeChartFactory {

	private JFreeChartFactory() {

	}

	public static ChartGen getChartGen(String chartType) {
		ChartGen chartGen = null;
		if ("pie".equals(chartType)) {
			chartGen = new PieChartGen();
		} else if ("line".equals(chartType)) {
			chartGen = new LineChartGen();
		} else if ("line_sum".equals(chartType)) {
			chartGen = new SummationLineChartGen();
		} else if ("bar".equals(chartType)) {
			chartGen = new BarChartGen();
		} else if ("bar_line_sum".equals(chartType)) {
			chartGen = new BarSummationLineChartGen();
		} else if ("line_sum".equals(chartType)) {
			chartGen = new SummationLineChartGen();
		} else if ("radarLine".equals(chartType)) {
			chartGen = new RadarLineChartGen();
		} else if ("stackedbar".equals(chartType)) {
			chartGen = new StackedBarChartGen();
		} else if ("stackedbar_line_sum".equals(chartType)) {
			chartGen = new StackedBarSummationLineChartGen();
		}

		try {
			Properties props = ChartProperties.getProperties();
			if (props != null && props.keys().hasMoreElements()) {
				Enumeration<?> e = props.keys();
				while (e.hasMoreElements()) {
					String name = (String) e.nextElement();
					String value = props.getProperty(name);
					if (StringUtils.equals(name, chartType)
							&& StringUtils.isNotEmpty(value)
							&& (value.length() > 0 && value.charAt(0) == '{')
							&& value.endsWith("}")) {
						JSONObject jsonObject = JSON.parseObject(value);
						String className = jsonObject.getString("className");
						if (StringUtils.isNotEmpty(className)) {
							Object gen = ClassUtils
									.instantiateObject(className);
							if (gen instanceof ChartGen) {
								chartGen = (ChartGen) gen;
								break;
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return chartGen;
	}

}