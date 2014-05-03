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

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.util.LogUtils;

public class Jexl2ExpressionEvaluator {
	protected final static Log logger = LogFactory
			.getLog(Jexl2ExpressionEvaluator.class);

	public static Object evaluate(String expression, Map<String, Object> beans) {
		Object result = null;
		try {
			if (expression != null && beans != null) {
				expression = expression.replace("#{", "{");
				expression = expression.replace("${", "{");
				JexlEngine jexlEngine = new JexlEngine();
				Expression jexlExpresssion = jexlEngine
						.createExpression(expression);
				JexlContext context = new MapContext(beans);
				result = jexlExpresssion.evaluate(context);
			}
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				logger.debug("error expression:" + expression);
				logger.debug(ex);
			}
			throw new RuntimeException(ex);
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		String expression = null;
		Object obj = null;
		Map<String, Object> context = new java.util.HashMap<String, Object>();
		context.put("roleId", "admin");
		context.put("roleType", "-5");

		expression = "#{roleType > 0}";

		obj = Jexl2ExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		context.put("roleType", "2345");
		expression = "#{roleType > 0}";

		obj = Jexl2ExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		context.put("a", 123);
		context.put("b", 789);
		expression = "#{ a + b }";

		obj = Jexl2ExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		context.put("a", "123");
		context.put("b", "789");
		expression = "#{ a*1.0 + b*1.0 }";

		obj = Jexl2ExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		context.put("c", 123D);
		context.put("d", 789D);
		expression = "#{ c / d }";

		obj = Jexl2ExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		context.put("a", "123");
		context.put("b", "789");
		expression = "#{ a*1.0 / b*1.0 }";

		obj = Jexl2ExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		expression = "#{ c >= 0 and d >0 }";
		obj = Jexl2ExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		expression = "#{ c >= 0 and d >0 and c/d > 0.16 }";
		obj = Jexl2ExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		expression = "#{ c >= 0 and d >0 and c/d > 0.15 }";
		obj = Jexl2ExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		expression = "#{ 0 > 10 }";
		obj = Jexl2ExpressionEvaluator.evaluate(expression, context);
		System.out.println("[type]=" + obj.getClass().getName() + "\t[value]="
				+ obj);

		expression = "#{ a +'-xy-'+ b }";
		obj = Jexl2ExpressionEvaluator.evaluate(expression, context);
		System.out.println("[type]=" + obj.getClass().getName() + "\t[value]="
				+ obj);

		expression = "#{ 0x10}";
		obj = Jexl2ExpressionEvaluator.evaluate(expression, context);
		System.out.println("[type]=" + obj.getClass().getName() + "\t[value]="
				+ obj);
	}

}