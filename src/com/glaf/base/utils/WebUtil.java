package com.glaf.base.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class WebUtil {
	private static Log logger = LogFactory.getLog(WebUtil.class);

	// 页面参数类型
	final private static String QUERY_PREFIX = "query_";

	final private static String ORDER_PREFIX = "order_";

	final private static String COLLE_PREFIX = "colle_";

	final private static String ALIAS_PREFIX = "alias_";

	// 
	final private static String QUERY_ALIAS = "othert";

	final private static String QUERY_ALIAS1 = "othert1_.";

	final private static String QUERY_MAINT = "this_.";

	/**
	 * 根据queryString构造url地址
	 * 
	 * @param request
	 */
	public static String getQueryString(HttpServletRequest request) {
		return getQueryString(request, "GBK");
	}

	/**
	 * 根据queryString构造url地址
	 * 
	 * @param request
	 */
	public static String getQueryString(HttpServletRequest request,
			String encoding) {
		Enumeration names = request.getParameterNames();
		StringBuffer sb = new StringBuffer();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = request.getParameter(name);
			//logger.info("param name:" + name + ",value:" + value);
			try {
				//sb.append(name).append("=").append(URLEncoder.encode(value, encoding)).append("&");
				sb.append(name).append("=").append(value).append("&");
				//logger.info("param name:" + name + ",value:" + value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 根据queryString构造url地址
	 * 
	 * @param url
	 * @return
	 */
	public static String getQueryString(String url) {
		Map map = new HashMap();
		String[] param = url.split("&");
		for (int i = 0; i < param.length; i++) {
			String[] entry = param[i].split("=");
			if (entry.length == 2)
				map.put(entry[0], entry[1]);
		}
		StringBuffer sb = new StringBuffer();
		Iterator keys = map.keySet().iterator();
		while (keys != null && keys.hasNext()) {
			String name = (String) keys.next();
			String value = (String) map.get(name);
			sb.append(name).append("=").append(value).append("&");
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static Map getQueryMap(HttpServletRequest request) {
		Map map = new ListOrderedMap();// 排序Map

		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = request.getParameter(name).trim();
			map.put(name, value);
		}

		return map;
	}

	/**
	 * 
	 * @param map
	 * @param forClass
	 * @return
	 */
	public static DetachedCriteria getCriteria(Map map, Class forClass) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(forClass);
		Set params = map.keySet();
		// boolean createAliased = false;
		if (params != null) {
			Map aliasMap = new HashMap();// 储存别名
			int aliasNum = 0;// 别名数量
			Iterator it = params.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = map.get(key)==null?null:map.get(key).toString();
				value = value == null ? value : value.trim();
				if (key.startsWith(QUERY_PREFIX) && value != null
						&& value.trim().length() > 0) {
					// 取得该参数的值
					String name = key.substring(QUERY_PREFIX.length());
					// 获取操作符，默认为 "="
					String op = "eq";
					if (name.indexOf("_") != -1) {
						int pos = name.lastIndexOf("_");
						op = name.substring(pos + 1);
						name = name.substring(0, pos);
					}

					if ("me".equals(op)) { // 主表 数字字段，主要为了区别与关联表同名的字段
						String maintAlias = detachedCriteria.getAlias();
						// 子表连接 string 连接
						detachedCriteria.add(Restrictions
								.sqlRestriction(maintAlias + "_." + name + "="
										+ value));

					} else if ("zns".equals(op)) { // 无列表输出
						// 子表连接 string 连接
						detachedCriteria.add(Restrictions
								.sqlRestriction(" 1 = 2 "));

					} else if ("mn".equals(op)) { // 主表 日期字段，主要为了区别与关联表同名的字段
						String maintAlias = detachedCriteria.getAlias();
						// 子表连接 string 连接
						detachedCriteria.add(Restrictions
								.sqlRestriction(maintAlias + "_." + name
										+ " is NULL "));

					}else if ("mnn".equals(op)) { // 主表 日期字段，主要为了区别与关联表同名的字段 add by key 2012-05-21
						String maintAlias = detachedCriteria.getAlias();
						// 子表连接 string 连接
						detachedCriteria.add(Restrictions
								.sqlRestriction(maintAlias + "_." + name
										+ " is not NULL "));

					} else if ("md".equals(op)) { // 主表 日期字段，主要为了区别与关联表同名的字段
						String maintAlias = detachedCriteria.getAlias();
						// 子表连接 string 连接
						detachedCriteria.add(Restrictions.sqlRestriction(
								maintAlias + "_." + name + "=?",
								stringToDate(value), Hibernate.DATE));

					} else if ("mis".equals(op)) { // in( 子表_select )
						String maintAlias = detachedCriteria.getAlias();
						// 子表连接 string 连接
						detachedCriteria.add(Restrictions
								.sqlRestriction(maintAlias + "_." + name
										+ " in (" + value + ") "));
					} else if ("xd".equals(op)) { // 主表 相关表.字段 为date
						// 子表连接 string 连接
						int pos = name.lastIndexOf(".");
						String alias = name.substring(0, pos);
						name = name.substring(pos + 1);
						if (aliasMap.get(alias) == null) {
							detachedCriteria.createAlias(alias, alias);
							aliasNum++;
							aliasMap.put(alias,
									getNewAliasName(alias, aliasNum));
						}
						detachedCriteria.add(Restrictions.sqlRestriction(
								aliasMap.get(alias) + name + "=? ",
								stringToDate(value), Hibernate.DATE));
					} else if ("xs".equals(op)) { // 主表 相关表.字段 为String
						// 子表连接 string 连接
						int pos = name.lastIndexOf(".");
						String alias = name.substring(0, pos);
						name = name.substring(pos + 1);
						if (aliasMap.get(alias) == null) {
							detachedCriteria.createAlias(alias, alias);
							aliasNum++;
							aliasMap.put(alias,
									getNewAliasName(alias, aliasNum));
						}
						detachedCriteria.add(Restrictions
								.sqlRestriction(aliasMap.get(alias) + name
										+ "='" + value + "' "));
					} else if ("xe".equals(op)) { // 主表 相关表.字段 为 数字
						if (!value.equals("") && Integer.parseInt(value) > 0) {
							int pos = name.lastIndexOf(".");
							String alias = name.substring(0, pos);
							name = name.substring(pos + 1);
							if (aliasMap.get(alias) == null) {
								detachedCriteria.createAlias(alias, alias);
								aliasNum++;
								aliasMap.put(alias, getNewAliasName(alias,
										aliasNum));
							}
							detachedCriteria.add(Restrictions
									.sqlRestriction(aliasMap.get(alias) + name
											+ "=" + value));
						}
					} else if ("ixe".equals(op)) { // 主表 相关表.字段 为 数字
						if (!value.equals("") && Integer.parseInt(value) >= 0) {
							int pos = name.lastIndexOf(".");
							String alias = name.substring(0, pos);
							name = name.substring(pos + 1);
							if (aliasMap.get(alias) == null) {
								detachedCriteria.createAlias(alias, alias);
								aliasNum++;
								aliasMap.put(alias, getNewAliasName(alias,
										aliasNum));
							}
							detachedCriteria.add(Restrictions
									.sqlRestriction(aliasMap.get(alias) + name
											+ "=" + value));
						}
					} else if ("xel".equals(op)) { // 主表 相关表.字段 为 数字
						if (!value.equals("") && Long.parseLong(value) != -1) {
							int pos = name.lastIndexOf(".");
							String alias = name.substring(0, pos);
							name = name.substring(pos + 1);
							if (aliasMap.get(alias) == null) {
								detachedCriteria.createAlias(alias, alias);
								aliasNum++;
								aliasMap.put(alias, getNewAliasName(alias,
										aliasNum));
							}
							detachedCriteria.add(Restrictions
									.sqlRestriction(aliasMap.get(alias) + name
											+ "=" + value));
						}
					} else if ("xne".equals(op)) { // 主表 相关表.字段 为 数字
						if (!value.equals("") && Integer.parseInt(value) != -1) {
							int pos = name.lastIndexOf(".");
							String alias = name.substring(0, pos);
							name = name.substring(pos + 1);
							if (aliasMap.get(alias) == null) {
								detachedCriteria.createAlias(alias, alias);
								aliasNum++;
								aliasMap.put(alias, getNewAliasName(alias,
										aliasNum));
							}
							detachedCriteria.add(Restrictions
									.sqlRestriction(aliasMap.get(alias) + name
											+ "<>" + value));
						}
					} else if ("xi".equals(op)) { // 主表 相关表.字段 in () 为String
						int pos = name.lastIndexOf(".");
						String alias = name.substring(0, pos);
						name = name.substring(pos + 1);
						if (aliasMap.get(alias) == null) {
							detachedCriteria.createAlias(alias, alias);
							aliasNum++;
							aliasMap.put(alias,
									getNewAliasName(alias, aliasNum));
						}
						// 子表连接 string 连接
						detachedCriteria.add(Restrictions
								.sqlRestriction(aliasMap.get(alias) + name
										+ " in (" + value + ") "));
					} else if ("xl".equals(op)) { // 主表 like 相关表.字段 为String
						int pos = name.lastIndexOf(".");
						String alias = name.substring(0, pos);
						name = name.substring(pos + 1);
						if (aliasMap.get(alias) == null) {
							detachedCriteria.createAlias(alias, alias);
							aliasNum++;
							aliasMap.put(alias,
									getNewAliasName(alias, aliasNum));
						}
						detachedCriteria.add(Restrictions
								.sqlRestriction(aliasMap.get(alias) + name
										+ " like '%" + value + "%'"));
					} else if (op.startsWith("xdate")) { // 对于Date
						String dateOp = "=";
						if ("xdatelt".equals(op)) {
							dateOp = "<";
						} else if ("xdategt".equals(op)) {
							dateOp = ">";
						} else if ("xdatele".equals(op)) {
							dateOp = "<=";
						} else if ("xdatege".equals(op)) {
							dateOp = ">=";
						}
						int pos = name.lastIndexOf(".");
						String alias = name.substring(0, pos);
						name = name.substring(pos + 1);
						if (aliasMap.get(alias) == null) {
							detachedCriteria.createAlias(alias, alias);
							aliasNum++;
							aliasMap.put(alias,
									getNewAliasName(alias, aliasNum));
						}
						detachedCriteria.add(Restrictions.sqlRestriction(
								aliasMap.get(alias) + name + dateOp + "?",
								stringToDate(value), Hibernate.DATE));
					} else if (op.startsWith("date")) { // 对于Date
						String dateOp = "=";
						if ("datelt".equals(op)) {
							dateOp = "<";
						} else if ("dategt".equals(op)) {
							dateOp = ">";
						} else if ("datele".equals(op)) {
							dateOp = "<=";
						} else if ("datege".equals(op)) {
							dateOp = ">=";
						}
						detachedCriteria.add(Restrictions.sqlRestriction(name
								+ dateOp + "?", stringToDate(value),
								Hibernate.DATE));
					} /*
						 * else if(op.equals("double")){ //对于Double String
						 * doubleOp = "="; if("double".equals(op)){ } }
						 */else if ("like".equals(op)) { // like
						detachedCriteria.add(Restrictions.like(name, "%"
								+ value + "%"));
					} else if("es".equals(op)){
						//if(Integer.parseInt(value) != -1){
							detachedCriteria.add(Restrictions.sqlRestriction(name+" = ? ",value,Hibernate.STRING));
						//}
					} else if ("ex".equals(op)) { // =int
						if (Integer.parseInt(value) != -1) {
							detachedCriteria.add(Restrictions.eq(name,
									new Integer(value)));
						}
					} else if ("el".equals(op)) { // =long
						if (Long.parseLong(value) != -1) {
							detachedCriteria.add(Restrictions.eq(name,
									new Long(value)));
						}
					} else if ("ed".equals(op)) { // =double
						if (Double.parseDouble(value) != -1) {
							detachedCriteria.add(Restrictions.eq(name,
									new Double(value)));
						}
					} else if ("nei".equals(op)) { // <>int
						if (Integer.parseInt(value) != -1) {
							detachedCriteria.add(Restrictions.ne(name,
									new Integer(value)));
						}
					} else if ("nel".equals(op)) { // <>long
						if (Long.parseLong(value) != -1) {
							detachedCriteria.add(Restrictions.ne(name,
									new Long(value)));
						}
					}else if ("in".equals(op)) { // in (数字)
						if (!"".trim().equals(value)) {
							String maintAlias = detachedCriteria.getAlias();
							detachedCriteria.add(Restrictions
									.sqlRestriction(maintAlias + "_." + name + " in (" + value
											+ ") "));
//							detachedCriteria.add(Restrictions
//									.sqlRestriction(name + " in (" + value
//											+ ") "));
						}
					} else if ("nin".equals(op)) { // not in (数字)
						if (!"".trim().equals(value)) {
							String maintAlias = detachedCriteria.getAlias();
							detachedCriteria.add(Restrictions
									.sqlRestriction(maintAlias + "_." + name + " not in (" + value
											+ ") "));
//							detachedCriteria.add(Restrictions
//									.sqlRestriction(name + " not in (" + value
//											+ ") "));
						}
					} else {
						detachedCriteria.add(Restrictions.eq(name, value));
					}
				} else if (key.startsWith(ORDER_PREFIX)) {
					// 取得排序参数的名 //order_字段_asc(desc) 在页面为 hidden 类型
					String name = key.substring(ORDER_PREFIX.length());

					int pos = name.lastIndexOf(".");
					if (pos != -1) {
						String alias = name.substring(0, pos);
						if (aliasMap.get(alias) == null) {
							detachedCriteria.createAlias(alias, alias);
							aliasNum++;
							aliasMap.put(alias,
									getNewAliasName(alias, aliasNum));
						}
					}

					if (value.trim().equalsIgnoreCase("asc")) {
						detachedCriteria.addOrder(Order.asc(name));
					} else {
						detachedCriteria.addOrder(Order.desc(name));
					}
				}
			}
			map.putAll(aliasMap);
		}

		return detachedCriteria;
	}

	private static String getNewAliasName(String aliasName, int num) {
		String s = aliasName.toLowerCase();
		if (s.length() > 10) {
			s = s.substring(0, 10);
		}
		return s + num + "_.";
	}

	/**
	 * String->String
	 */
	public static String stringToString(String str) {
		return str != null ? str : "";
	}

	/**
	 * Date -> String
	 */
	public static String dateToString(Date date) {
		return dateToString(date, null);
	}

	/**
	 * Date -> String
	 */
	public static String dateToString(Date date, String format) {
		String retStr = "";
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(
					(format == null ? "yyyy-MM-dd" : format));
			retStr = sdf.format(date);
		}
		return retStr;
	}

	/**
	 * String -> Date
	 */
	public static Date stringToDate(String date) {
		return stringToDate(date, null);
	}

	/**
	 * String -> Date
	 */
	public static Date stringToDate(String date, String format) {
		Date retDate = null;
		if (date != null && !"".equals(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat(
					(format == null ? "yyyy-MM-dd" : format));
			try {
				retDate = sdf.parse(date);
			} catch (ParseException e) {
			}
		}
		return retDate;
	}

	/**
	 * 格式化数据并四舍五入
	 * 
	 * @param number
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static String getFormatCurrency(double number) {
		return getFormatCurrency(number, 2);
	}

	/**
	 * 格式化数据并四舍五入
	 * 
	 * @param number
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static String getFormatCurrency(Double number) {
		return getFormatCurrency(number.doubleValue(), 2);
	}

	public static String getFormatCurrency(double number, int digits) {
		BigDecimal bd = new BigDecimal(number);
		double n = bd.setScale(BigDecimal.ROUND_HALF_UP, digits).doubleValue();
		String num = "";
		for (int i = 0; i < digits; i++) {
			num += "0";
		}
		if (num.length() > 0) {
			num = "." + num;
		}
		DecimalFormat df = new DecimalFormat("##0" + num);

		return df.format(n);
	}

	/**
	 * 格式化数据并四舍五入,并返回以逗号分隔的字符串
	 * 
	 * @param number
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static String getFormatNumberToString(double number) {
		return getFormatNumberToString(number, 2);
	}

	/**
	 * 格式化数据并四舍五入,并返回以逗号分隔的字符串
	 * 
	 * @param number
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static String getFormatNumberToString(Double number) {
		return getFormatNumberToString(number.doubleValue(), 2);
	}

	public static String getFormatNumberToString(double number, int digits) {
		BigDecimal bd = new BigDecimal(number);
		double n = bd.setScale(BigDecimal.ROUND_HALF_UP, digits).doubleValue();
		String num = "";
		for (int i = 0; i < digits; i++) {
			num += "0";
		}
		if (num.length() > 0) {
			num = "." + num;
		}
		DecimalFormat df = new DecimalFormat("#,##0" + num);

		return df.format(n);
	}

	/**
	 * 格式化数据并四舍五入
	 * 
	 * @param number
	 * @param digits
	 *            小数位数
	 * @return
	 */
	public static String getFormarPercentage(double number) {
		return getFormarPercentage(number, 0);
	}

	public static String getFormarPercentage(double number, int digits) {
		return String.valueOf(number) + "%";
	}

	/**
	 * 拷贝BEAN
	 * 
	 * @param dest
	 * @param orig
	 */
	public static void copyProperties(Object dest, Object orig) {
		try {
			PropertyUtilExtends pue = new PropertyUtilExtends();
			pue.copyCustomerProperties(dest, orig);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 选择排序
	 * 
	 * @param array
	 * @return
	 */
	public static long[] selectionSort(long[] array) {
		int out, in, min, nElems = array.length;

		for (out = 0; out < nElems - 1; out++) // 外层循环
		{
			min = out; // 最小值
			for (in = out + 1; in < nElems; in++)
				// 内层循环
				if (array[in] < array[min]) // 如果有比最小值还小的
					min = in; // 得到新最小值
			// 交换
			long temp = array[out];
			array[out] = array[min];
			array[min] = temp;
		}
		return array;
	}

	public static void main(String[] args) {
		System.out.println(getFormatCurrency(1111.235234324, 2));
		System.out.println(getFormatCurrency(1111, 2));
		System.out.println(getFormatCurrency(0));

		System.out.println(getFormatNumberToString(1132411.235, 6));
		System.out.println(getFormatNumberToString(1123411.235, 2));
		System.out.println(getFormatNumberToString(1112341.34, 2));
        System.out.println("DATE -----------------"+stringToDate("20091126","yyyyMMdd"));
		long[] t = new long[] { 1, 3, 2, 43, 23, 3, 4, 5, 3, 2 };
		t = selectionSort(t);
		for (int i = 0; i < t.length; i++) {
			System.out.print(t[i] + ", ");
		}
	}

}
