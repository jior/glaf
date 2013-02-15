/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.jbpm.el;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.jpdl.el.ELException;
import org.jbpm.jpdl.el.VariableResolver;
import org.jbpm.jpdl.el.impl.ExpressionEvaluatorImpl;

/**
 * 
 * 表达式支持的符号如下：
 * ["}", ".", ">", "gt", "<", "lt", "==", "eq", "<=", "le", ">=", "ge", "!=",
 * "ne", "[", "+", "-", "*", "/", "div", "%", "mod", "and", "&&", "or", "||",
 * "?"]
 * 
 */
public class DefaultExpressionEvaluator {

	private final static ExpressionEvaluatorImpl evaluator = new ExpressionEvaluatorImpl();

	public static Object evaluate(String expression, Map context) {
		Object result = null;
		try {
			if (expression != null && context != null) {
				String dollarExpression = translateExpressionToDollars(expression);
				VariableResolver usedResolver = new MapVariableResolver(context);
				result = evaluator.evaluate(dollarExpression, Object.class,
						usedResolver, null);
			}
		} catch (ELException ex) {
			ex.printStackTrace();
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
		String expression = "${ not empty roleId}";
		Map context = new HashMap();
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

		expression = "${ c >= 0 and d >0 }";
		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);
		if (obj != null) {
			System.out.println(obj.getClass().getName());
		}

		context.put("money", "12345");
		expression = "${ money ge 1000000 }";
		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);
		if (obj != null) {
			System.out.println(obj.getClass().getName());
		}

		context.put("money", "12345678");
		expression = "${ money ge 1000000 }";
		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);
		if (obj != null) {
			System.out.println(obj.getClass().getName());
		}

		context.put("money", new Integer(12345));
		expression = "${ money ge 1000000 }";
		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);
		if (obj != null) {
			System.out.println(obj.getClass().getName());
		}

		context.put("money", new Integer(12345678));
		expression = "${ money ge 1000000 }";
		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);
		if (obj != null) {
			System.out.println(obj.getClass().getName());
		}

		context.put("money", new Double(1234.5678));
		expression = "${ money ge 1000000 }";
		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);
		if (obj != null) {
			System.out.println(obj.getClass().getName());
		}

		context.put("money", new Double(12345678));
		expression = "${ money ge 1000000 }";
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

		context.put("money", "600000");
		expression = "#{money * 1.0 le 500000}";
		obj = DefaultExpressionEvaluator.evaluate(expression, context);
		System.out.println(obj);

	}

}
