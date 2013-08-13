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

package com.glaf.base.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;

import com.glaf.core.util.DateUtils;

public class HibernateUtil {
	protected static Log logger = LogFactory.getLog(HibernateUtil.class);

	// 页面参数类型
	final private static String QUERY_PREFIX = "query_";

	final private static String ORDER_PREFIX = "order_";

	/**
	 * 
	 * @param map
	 * @param forClass
	 * @return
	 */
	public static DetachedCriteria getCriteria(Map<String, String> map, Class<?> forClass) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(forClass);
		Set<String> params = map.keySet();
		// boolean createAliased = false;
		if (params != null) {
			Map<String, String> aliasMap = new HashMap<String, String>();// 储存别名
			int aliasNum = 0;// 别名数量
			Iterator<String> it = params.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = map.get(key) == null ? null : map.get(key)
						.toString();
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

					} else if ("mnn".equals(op)) { // 主表 日期字段，主要为了区别与关联表同名的字段
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
								DateUtils.toDate(value),
								StandardBasicTypes.DATE));

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
								DateUtils.toDate(value),
								StandardBasicTypes.DATE));
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
								aliasMap.put(alias,
										getNewAliasName(alias, aliasNum));
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
								aliasMap.put(alias,
										getNewAliasName(alias, aliasNum));
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
								aliasMap.put(alias,
										getNewAliasName(alias, aliasNum));
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
								aliasMap.put(alias,
										getNewAliasName(alias, aliasNum));
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
								DateUtils.toDate(value),
								StandardBasicTypes.DATE));
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
								+ dateOp + "?", DateUtils.toDate(value),
								StandardBasicTypes.DATE));
					} /*
					 * else if(op.equals("double")){ //对于Double String doubleOp
					 * = "="; if("double".equals(op)){ } }
					 */else if ("like".equals(op)) { // like
						detachedCriteria.add(Restrictions.like(name, "%"
								+ value + "%"));
					} else if ("es".equals(op)) {
						// if(Integer.parseInt(value) != -1){
						detachedCriteria.add(Restrictions.sqlRestriction(name
								+ " = ? ", value, StandardBasicTypes.STRING));
						// }
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
					} else if ("in".equals(op)) { // in (数字)
						if (!"".trim().equals(value)) {
							String maintAlias = detachedCriteria.getAlias();
							detachedCriteria.add(Restrictions
									.sqlRestriction(maintAlias + "_." + name
											+ " in (" + value + ") "));
							// detachedCriteria.add(Restrictions
							// .sqlRestriction(name + " in (" + value
							// + ") "));
						}
					} else if ("nin".equals(op)) { // not in (数字)
						if (!"".trim().equals(value)) {
							String maintAlias = detachedCriteria.getAlias();
							detachedCriteria.add(Restrictions
									.sqlRestriction(maintAlias + "_." + name
											+ " not in (" + value + ") "));
							// detachedCriteria.add(Restrictions
							// .sqlRestriction(name + " not in (" + value
							// + ") "));
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

}