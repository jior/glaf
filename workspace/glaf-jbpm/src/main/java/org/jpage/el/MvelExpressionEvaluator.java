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

package org.jpage.el;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.util.Tools;
import org.mvel2.MVEL;

public class MvelExpressionEvaluator {
	private final static Log logger = LogFactory
			.getLog(MvelExpressionEvaluator.class);

	public static Object evaluate(String expression, Map context) {
		Object result = null;
		try {
			if (expression != null && context != null) {
				expression = translateExpression(expression);
				result = MVEL.eval(expression, context);
				if (result != null) {
					if (result instanceof Map) {
						return ((Map) result).keySet().iterator().next();
					}
				}
			}
		} catch (Exception ex) {
			logger.error("expression:" + expression);
			logger.error("context:" + context);
			throw new RuntimeException(ex);
		}
		return result;
	}

	public static Object evaluate(String expression,
			javax.servlet.jsp.PageContext pageContext) {
		Object result = null;
		try {
			if (expression != null && pageContext != null) {
				Map context = new HashMap();
				addVar(context, pageContext, PageContext.APPLICATION_SCOPE);
				addVar(context, pageContext, PageContext.SESSION_SCOPE);
				addVar(context, pageContext, PageContext.REQUEST_SCOPE);
				addVar(context, pageContext, PageContext.PAGE_SCOPE);
				context.put("pageContext", pageContext);

				expression = translateExpression(expression);

				result = MVEL.eval(expression, context);

				if (result != null) {
					if (result instanceof Map) {
						return ((Map) result).keySet().iterator().next();
					}
				}
			}
		} catch (Exception ex) {
			logger.error("expression:" + expression);
			throw new RuntimeException(ex);
		}
		return result;
	}

	private static void addVar(Map context, PageContext pageContext, int scope) {
		Enumeration e = pageContext.getAttributeNamesInScope(scope);
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			Object value = pageContext.getAttribute(name, scope);
			context.put(name, value);
		}
	}

	private static String translateExpression(String expression) {
		if (expression == null) {
			return null;
		}
		expression = Tools.replaceIgnoreCase(expression, "#{", "");
		expression = Tools.replaceIgnoreCase(expression, "${", "");
		expression = Tools.replaceIgnoreCase(expression, "}", "");
		return expression;
	}

	public static void main(String[] args) throws Exception {
		String expression = null;
		Object obj = null;
		Map context = new HashMap();
		context.put("roleId", "admin");
		context.put("roleType", "-5");

		expression = "${roleType > 0}";

		obj = MvelExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		context.put("roleType", "2345");
		expression = "${roleType > 0}";

		obj = MvelExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		context.put("a", new Integer(123));
		context.put("b", new Integer(789));
		expression = "${ a + b }";

		obj = MvelExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		context.put("a", "123");
		context.put("b", "789");
		expression = "${ a*1.0 + b*1.0 }";

		obj = MvelExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		context.put("c", new Double(123));
		context.put("d", new Double(789));
		expression = "${ c / d }";

		obj = MvelExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		context.put("a", "123");
		context.put("b", "789");
		expression = "${ a*1.0 / b*1.0 }";

		obj = MvelExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		expression = "${ c >= 0 and d >0 }";
		obj = MvelExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		expression = "${ c >= 0 and d >0 and c/d > 0.16 }";
		obj = MvelExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

		expression = "${ c >= 0 and d >0 and c/d > 0.15 }";
		obj = MvelExpressionEvaluator.evaluate(expression, context);
		if (obj != null) {
			System.out.println("[type]=" + obj.getClass().getName()
					+ "\t[value]=" + obj);
		}

	}

}
