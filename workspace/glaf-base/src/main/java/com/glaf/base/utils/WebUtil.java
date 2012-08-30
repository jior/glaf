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

	// ҳ���������
	final private static String QUERY_PREFIX = "query_";

	final private static String ORDER_PREFIX = "order_";

	final private static String COLLE_PREFIX = "colle_";

	final private static String ALIAS_PREFIX = "alias_";

	// 
	final private static String QUERY_ALIAS = "othert";

	final private static String QUERY_ALIAS1 = "othert1_.";

	final private static String QUERY_MAINT = "this_.";

	/**
	 * ����queryString����url��ַ
	 * 
	 * @param request
	 */
	public static String getQueryString(HttpServletRequest request) {
		return getQueryString(request, "GBK");
	}

	/**
	 * ����queryString����url��ַ
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
	 * ����queryString����url��ַ
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
		Map map = new ListOrderedMap();// ����Map

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
			Map aliasMap = new HashMap();// �������
			int aliasNum = 0;// ��������
			Iterator it = params.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = map.get(key)==null?null:map.get(key).toString();
				value = value == null ? value : value.trim();
				if (key.startsWith(QUERY_PREFIX) && value != null
						&& value.trim().length() > 0) {
					// ȡ�øò�����ֵ
					String name = key.substring(QUERY_PREFIX.length());
					// ��ȡ��������Ĭ��Ϊ "="
					String op = "eq";
					if (name.indexOf("_") != -1) {
						int pos = name.lastIndexOf("_");
						op = name.substring(pos + 1);
						name = name.substring(0, pos);
					}

					if ("me".equals(op)) { // ���� �����ֶΣ���ҪΪ�������������ͬ�����ֶ�
						String maintAlias = detachedCriteria.getAlias();
						// �ӱ����� string ����
						detachedCriteria.add(Restrictions
								.sqlRestriction(maintAlias + "_." + name + "="
										+ value));

					} else if ("zns".equals(op)) { // ���б����
						// �ӱ����� string ����
						detachedCriteria.add(Restrictions
								.sqlRestriction(" 1 = 2 "));

					} else if ("mn".equals(op)) { // ���� �����ֶΣ���ҪΪ�������������ͬ�����ֶ�
						String maintAlias = detachedCriteria.getAlias();
						// �ӱ����� string ����
						detachedCriteria.add(Restrictions
								.sqlRestriction(maintAlias + "_." + name
										+ " is NULL "));

					}else if ("mnn".equals(op)) { // ���� �����ֶΣ���ҪΪ�������������ͬ�����ֶ� add by key 2012-05-21
						String maintAlias = detachedCriteria.getAlias();
						// �ӱ����� string ����
						detachedCriteria.add(Restrictions
								.sqlRestriction(maintAlias + "_." + name
										+ " is not NULL "));

					} else if ("md".equals(op)) { // ���� �����ֶΣ���ҪΪ�������������ͬ�����ֶ�
						String maintAlias = detachedCriteria.getAlias();
						// �ӱ����� string ����
						detachedCriteria.add(Restrictions.sqlRestriction(
								maintAlias + "_." + name + "=?",
								stringToDate(value), Hibernate.DATE));

					} else if ("mis".equals(op)) { // in( �ӱ�_select )
						String maintAlias = detachedCriteria.getAlias();
						// �ӱ����� string ����
						detachedCriteria.add(Restrictions
								.sqlRestriction(maintAlias + "_." + name
										+ " in (" + value + ") "));
					} else if ("xd".equals(op)) { // ���� ��ر�.�ֶ� Ϊdate
						// �ӱ����� string ����
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
					} else if ("xs".equals(op)) { // ���� ��ر�.�ֶ� ΪString
						// �ӱ����� string ����
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
					} else if ("xe".equals(op)) { // ���� ��ر�.�ֶ� Ϊ ����
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
					} else if ("ixe".equals(op)) { // ���� ��ر�.�ֶ� Ϊ ����
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
					} else if ("xel".equals(op)) { // ���� ��ر�.�ֶ� Ϊ ����
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
					} else if ("xne".equals(op)) { // ���� ��ر�.�ֶ� Ϊ ����
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
					} else if ("xi".equals(op)) { // ���� ��ر�.�ֶ� in () ΪString
						int pos = name.lastIndexOf(".");
						String alias = name.substring(0, pos);
						name = name.substring(pos + 1);
						if (aliasMap.get(alias) == null) {
							detachedCriteria.createAlias(alias, alias);
							aliasNum++;
							aliasMap.put(alias,
									getNewAliasName(alias, aliasNum));
						}
						// �ӱ����� string ����
						detachedCriteria.add(Restrictions
								.sqlRestriction(aliasMap.get(alias) + name
										+ " in (" + value + ") "));
					} else if ("xl".equals(op)) { // ���� like ��ر�.�ֶ� ΪString
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
					} else if (op.startsWith("xdate")) { // ����Date
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
					} else if (op.startsWith("date")) { // ����Date
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
						 * else if(op.equals("double")){ //����Double String
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
					}else if ("in".equals(op)) { // in (����)
						if (!"".trim().equals(value)) {
							String maintAlias = detachedCriteria.getAlias();
							detachedCriteria.add(Restrictions
									.sqlRestriction(maintAlias + "_." + name + " in (" + value
											+ ") "));
//							detachedCriteria.add(Restrictions
//									.sqlRestriction(name + " in (" + value
//											+ ") "));
						}
					} else if ("nin".equals(op)) { // not in (����)
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
					// ȡ������������� //order_�ֶ�_asc(desc) ��ҳ��Ϊ hidden ����
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
	 * ��ʽ�����ݲ���������
	 * 
	 * @param number
	 * @param digits
	 *            С��λ��
	 * @return
	 */
	public static String getFormatCurrency(double number) {
		return getFormatCurrency(number, 2);
	}

	/**
	 * ��ʽ�����ݲ���������
	 * 
	 * @param number
	 * @param digits
	 *            С��λ��
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
	 * ��ʽ�����ݲ���������,�������Զ��ŷָ����ַ���
	 * 
	 * @param number
	 * @param digits
	 *            С��λ��
	 * @return
	 */
	public static String getFormatNumberToString(double number) {
		return getFormatNumberToString(number, 2);
	}

	/**
	 * ��ʽ�����ݲ���������,�������Զ��ŷָ����ַ���
	 * 
	 * @param number
	 * @param digits
	 *            С��λ��
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
	 * ��ʽ�����ݲ���������
	 * 
	 * @param number
	 * @param digits
	 *            С��λ��
	 * @return
	 */
	public static String getFormarPercentage(double number) {
		return getFormarPercentage(number, 0);
	}

	public static String getFormarPercentage(double number, int digits) {
		return String.valueOf(number) + "%";
	}

	/**
	 * ����BEAN
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
	 * ѡ������
	 * 
	 * @param array
	 * @return
	 */
	public static long[] selectionSort(long[] array) {
		int out, in, min, nElems = array.length;

		for (out = 0; out < nElems - 1; out++) // ���ѭ��
		{
			min = out; // ��Сֵ
			for (in = out + 1; in < nElems; in++)
				// �ڲ�ѭ��
				if (array[in] < array[min]) // ����б���Сֵ��С��
					min = in; // �õ�����Сֵ
			// ����
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
