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

package com.glaf.core.el;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.glaf.core.util.StringTools;

public class ExpressionTools {

	public static String evaluate(String expression, Map<String, Object> params) {
		if (expression == null || params == null) {
			return expression;
		}
		expression = StringTools.replaceIgnoreCase(expression, "${", "#{");
		StringBuffer sb = new StringBuffer(expression.length()+1000);
		int begin = 0;
		int end = 0;
		boolean flag = false;
		for (int i = 0; i < expression.length(); i++) {
			if (expression.charAt(i) == '#' && expression.charAt(i + 1) == '{') {
				sb.append(expression.substring(end, i));
				begin = i + 2;
				flag = true;
			}
			if (flag && expression.charAt(i) == '}') {
				String temp = expression.substring(begin, i);
				String value = null;
				try {
					Object object = Mvel2ExpressionEvaluator.evaluate(temp,
							params);
					if (object != null) {
						value = object.toString();
					}
				} catch (Exception ex) {
					// ex.printStackTrace();
				}
				if (StringUtils.isNotEmpty(value)) {
					sb.append(value);
					end = i + 1;
					flag = false;
				} else {
					sb.append("");
					end = i + 1;
					flag = false;
				}
			}
			if (i == expression.length() - 1) {
				sb.append(expression.substring(end, i + 1));
			}
		}
		return sb.toString();
	}

}