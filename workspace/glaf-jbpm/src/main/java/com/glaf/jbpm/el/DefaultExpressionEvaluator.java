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

package com.glaf.jbpm.el;

import java.util.Map;

import org.jbpm.jpdl.el.ELException;
import org.jbpm.jpdl.el.VariableResolver;
import org.jbpm.jpdl.el.impl.ExpressionEvaluatorImpl;

public class DefaultExpressionEvaluator {

	private final static ExpressionEvaluatorImpl evaluator = new ExpressionEvaluatorImpl();

	public static Object evaluate(String expression, Map<String, Object> context) {
		Object result = null;
		try {
			if (expression != null && context != null) {
				String dollarExpression = translateExpressionToDollars(expression);
				VariableResolver usedResolver = new MapVariableResolver(context);
				result = evaluator.evaluate(dollarExpression, Object.class,
						usedResolver, null);
			}
		} catch (ELException ex) {
			throw new RuntimeException(ex);
		}
		return result;
	}

	private static String translateExpressionToDollars(String expression) {
		if (expression == null) {
			return null;
		}
		char[] chars = expression.toCharArray();
		int index = 0;
		while (index != -1) {
			index = expression.indexOf("#{", index);
			if (index != -1) {
				chars[index] = '$';
				index++;
			}
		}
		return new String(chars);
	}

	public static void main(String[] args) throws Exception {
		String expression = "#{ not empty roleId}";
		Map<String, Object> context = new java.util.HashMap<String, Object>();
		context.put("roleId", "admin");
		context.put("roleType", "-5");
		context.put("isAgree02", "true");
		context.put("isAgree03", "false");
		Object obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);

		expression = " (\"true\".equals(isAgree02) && \"true\".equals(isAgree03)) ";

		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println("--->");
		System.out.println(obj);
		System.out.println("--->");

		expression = " #{ isAgree02 eq 'true' and isAgree03 eq 'true' } ";

		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println("##->");
		System.out.println(obj);
		System.out.println("##->");

		expression = " #{ isAgree02 eq 'true' && isAgree03 eq 'true' } ";

		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println("**->");
		System.out.println(obj);
		System.out.println("**->");

		expression = "#{ ! empty roleId}";

		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);

		expression = "#{roleType > 0}";

		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);

		context.put("roleType", "2345");
		expression = "#{roleType > 0}";

		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);

		context.put("a", "123");
		context.put("b", "789");
		expression = "#{ a + b }";

		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);
		if (obj != null) {
			System.out.println(obj.getClass().getName());
		}

		context.put("c", "123");
		context.put("d", "789");
		expression = "#{ c / d }";

		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);
		if (obj != null) {
			System.out.println(obj.getClass().getName());
		}

		expression = "#{ c >= 0 and d >0 }";
		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);
		if (obj != null) {
			System.out.println(obj.getClass().getName());
		}

		context.put("money", "12345");
		expression = "#{ money ge 1000000 }";
		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);
		if (obj != null) {
			System.out.println(obj.getClass().getName());
		}

		context.put("money", "12345678");
		expression = "#{ money ge 1000000 }";
		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);
		if (obj != null) {
			System.out.println(obj.getClass().getName());
		}

		context.put("money", 12345);
		expression = "#{ money ge 1000000 }";
		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);
		if (obj != null) {
			System.out.println(obj.getClass().getName());
		}

		context.put("money", 12345678);
		expression = "#{ money ge 1000000 }";
		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);
		if (obj != null) {
			System.out.println(obj.getClass().getName());
		}

		context.put("money", 1234.5678);
		expression = "#{ money ge 1000000 }";
		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);
		if (obj != null) {
			System.out.println(obj.getClass().getName());
		}

		context.put("money", 12345678);
		expression = "#{ money ge 1000000 }";
		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);
		if (obj != null) {
			System.out.println(obj.getClass().getName());
		}

		expression = "#{not empty route and ( route eq 'A' || route eq 'B' )}";
		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		context.put("route", "A");
		expression = "#{not empty route and ( route eq 'A' || route eq 'B' )}";
		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		context.put("route", "Y");
		expression = "#{not empty route and ( route eq 'A' || route eq 'B' )}";
		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

	}

}