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

package com.glaf.jbpm.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jbpm.JbpmContext;

import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.util.Paging;
import com.glaf.jbpm.model.ConfigFile;

@SuppressWarnings("unchecked")
public class JbpmEntityDAO {

	public void delete(JbpmContext jbpmContext, Object model) {
		Session session = jbpmContext.getSession();
		session.delete(model);
	}

	public void delete(JbpmContext jbpmContext, String entityName, Object object) {
		Session session = jbpmContext.getSession();
		session.delete(entityName, object);
	}

	public void deleteAll(JbpmContext jbpmContext, Collection<Object> rows) {
		if (rows == null || rows.size() == 0) {
			return;
		}
		Session session = jbpmContext.getSession();
		Iterator<Object> iterator = rows.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			session.delete(model);
		}
	}

	public void executeSqlUpdate(JbpmContext jbpmContext, String sql) {
		Session session = jbpmContext.getSession();
		Query query = session.createSQLQuery(sql);
		query.executeUpdate();
	}

	public void executeSqlUpdate(JbpmContext jbpmContext, String sql,
			List<Object> values) {
		Session session = jbpmContext.getSession();
		Query query = session.createSQLQuery(sql);
		if (values != null && values.size() > 0) {
			for (int i = 0; i < values.size(); i++) {
				query.setParameter(i, values.get(i));
			}
		}
		query.executeUpdate();
	}

	public void executeUpdate(JbpmContext jbpmContext, String sql,
			List<Object> values) {
		Session session = jbpmContext.getSession();
		Query query = session.createQuery(sql);
		if (values != null && values.size() > 0) {
			for (int i = 0; i < values.size(); i++) {
				query.setParameter(i, values.get(i));
			}
		}
		query.executeUpdate();
	}

	public void executeUpdate(JbpmContext jbpmContext, String sql,
			Map<String, Object> params) {
		Session session = jbpmContext.getSession();
		Query query = session.createQuery(sql);
		if (params != null && params.size() > 0) {
			Set<Entry<String, Object>> entrySet = params.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {
					if (value instanceof Collection) {
						query.setParameterList(name, (Collection<?>) value);
					} else {
						query.setParameter(name, value);
					}
				}
			}
		}
		query.executeUpdate();
	}

	public long getLastModified(JbpmContext jbpmContext, String filename) {
		Session session = jbpmContext.getSession();
		long lastModified = -1;
		String name = filename;
		if (name.indexOf("WEB-INF") != -1) {
			name = name.substring(name.lastIndexOf("WEB-INF"));
		}
		Query query = session
				.createQuery(" select a from com.glaf.jbpm.model.ConfigFile as a where a.filename = ? ");
		query.setString(0, name);
		List<?> list = query.list();
		if (list != null && list.size() > 0) {
			ConfigFile model = (ConfigFile) list.get(0);
			lastModified = model.getLastModified();
		}
		return lastModified;
	}

	public List<Object> getList(JbpmContext jbpmContext,
			SqlExecutor queryExecutor) {
		Session session = jbpmContext.getSession();
		Query query = session.createQuery(queryExecutor.getSql());
		Object parameter = queryExecutor.getParameter();
		if (parameter instanceof Map) {
			Map<String, Object> params = (Map<String, Object>) parameter;
			if (params != null && params.size() > 0) {
				Set<Entry<String, Object>> entrySet = params.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					String name = entry.getKey();
					Object value = entry.getValue();
					if (value != null) {
						if (value instanceof Collection) {
							query.setParameterList(name, (Collection<?>) value);
						} else {
							query.setParameter(name, value);
						}
					}
				}
			}
		}

		List<Object> rows = query.list();
		return rows;
	}

	public List<Object> getList(JbpmContext jbpmContext, int currPageNo,
			int maxResults, SqlExecutor queryExecutor) {
		Session session = jbpmContext.getSession();
		Query query = session.createQuery(queryExecutor.getSql());
		Object parameter = queryExecutor.getParameter();
		if (parameter instanceof Map) {
			Map<String, Object> params = (Map<String, Object>) parameter;

			if (params != null && params.size() > 0) {
				Set<Entry<String, Object>> entrySet = params.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					String name = entry.getKey();
					Object value = entry.getValue();
					if (value != null) {
						if (value instanceof Collection) {
							query.setParameterList(name, (Collection<?>) value);
						} else {
							query.setParameter(name, value);
						}
					}
				}
			}
		}

		query.setFirstResult((currPageNo - 1) * maxResults);
		query.setMaxResults(maxResults);

		List<Object> rows = query.list();
		return rows;
	}

	public Paging getPage(JbpmContext jbpmContext, int currPageNo,
			int pageSize, SqlExecutor countExecutor, SqlExecutor queryExecutor) {
		Session session = jbpmContext.getSession();
		Paging page = new Paging();
		if (pageSize <= 0) {
			pageSize = Paging.DEFAULT_PAGE_SIZE;
		}
		if (currPageNo <= 0) {
			currPageNo = 1;
		}
		int totalCount = 0;
		if (countExecutor != null) {
			Object obj = null;
			String hql = countExecutor.getSql();
			hql = removeOrders(hql);
			Query q = session.createQuery(hql);

			Object parameter = queryExecutor.getParameter();
			if (parameter instanceof Map) {
				Map<String, Object> params = (Map<String, Object>) parameter;

				if (params != null && params.size() > 0) {
					Set<Entry<String, Object>> entrySet = params.entrySet();
					for (Entry<String, Object> entry : entrySet) {
						String name = entry.getKey();
						Object value = entry.getValue();
						if (value != null) {
							if (value instanceof Collection) {
								q.setParameterList(name, (Collection<?>) value);
							} else {
								q.setParameter(name, value);
							}
						}
					}
				}
			}

			obj = q.iterate().next();

			if (obj instanceof Integer) {
				Integer iCount = (Integer) obj;
				totalCount = iCount.intValue();
			} else if (obj instanceof Long) {
				Long iCount = (Long) obj;
				totalCount = iCount.intValue();
			} else if (obj instanceof BigDecimal) {
				BigDecimal bg = (BigDecimal) obj;
				totalCount = bg.intValue();
			} else if (obj instanceof BigInteger) {
				BigInteger bi = (BigInteger) obj;
				totalCount = bi.intValue();
			}

		} else {
			List<Object> list = null;
			Query q = session.createQuery(queryExecutor.getSql());

			Object parameter = queryExecutor.getParameter();
			if (parameter instanceof Map) {
				Map<String, Object> params = (Map<String, Object>) parameter;

				if (params != null && params.size() > 0) {
					Set<Entry<String, Object>> entrySet = params.entrySet();
					for (Entry<String, Object> entry : entrySet) {
						String name = entry.getKey();
						Object value = entry.getValue();
						if (value != null) {
							if (value instanceof Collection) {
								q.setParameterList(name, (Collection<?>) value);
							} else {
								q.setParameter(name, value);
							}
						}
					}
				}
			}

			list = q.list();
			if (list != null) {
				totalCount = list.size();
			}
		}

		if (totalCount == 0) {
			page.setRows(new java.util.concurrent.CopyOnWriteArrayList<Object>());
			page.setCurrentPage(0);
			page.setPageSize(0);
			page.setTotal(0);
			return page;
		}
		page.setTotal(totalCount);

		int maxPageNo = (page.getTotal() + (pageSize - 1)) / pageSize;
		if (currPageNo > maxPageNo) {
			currPageNo = maxPageNo;
		}

		Query query = session.createQuery(queryExecutor.getSql());

		Object parameter = queryExecutor.getParameter();
		if (parameter instanceof Map) {
			Map<String, Object> params = (Map<String, Object>) parameter;
			if (params != null && params.size() > 0) {
				Set<Entry<String, Object>> entrySet = params.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					String name = entry.getKey();
					Object value = entry.getValue();
					if (value != null) {
						if (value instanceof Collection) {
							query.setParameterList(name, (Collection<?>) value);
						} else {
							query.setParameter(name, value);
						}
					}
				}
			}
		}

		query.setFirstResult((currPageNo - 1) * pageSize);
		query.setMaxResults(pageSize);

		List<Object> list = query.list();
		page.setRows(list);
		page.setPageSize(pageSize);
		page.setCurrentPage(currPageNo);

		return page;
	}

	public Object getPersistObject(JbpmContext jbpmContext, Class<?> clazz,
			java.io.Serializable persistId) {
		Session session = jbpmContext.getSession();
		return session.get(clazz, persistId);
	}

	public Object getPersistObject(JbpmContext jbpmContext, String entityName,
			java.io.Serializable persistId) {
		Session session = jbpmContext.getSession();
		return session.get(entityName, persistId);
	}

	/**
	 * È¥³ýhqlµÄorderby ×Ó¾ä
	 * 
	 * @param hql
	 * @return hql
	 */
	public String removeOrders(String hql) {
		Pattern pattern = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(hql);
		StringBuffer buf = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(buf, "");
		}
		matcher.appendTail(buf);
		return buf.toString();
	}

	public void save(JbpmContext jbpmContext, Object model) {
		Session session = jbpmContext.getSession();
		session.save(model);
	}

	public void save(JbpmContext jbpmContext, String entityName, Object model) {
		Session session = jbpmContext.getSession();
		session.save(entityName, model);
	}

	public void saveAll(JbpmContext jbpmContext, Collection<?> rows) {
		if (rows == null || rows.size() == 0) {
			return;
		}
		Session session = jbpmContext.getSession();
		Iterator<?> iterator = rows.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			session.save(model);
		}
	}

	public void saveConfigFile(JbpmContext jbpmContext, ConfigFile file) {
		Session session = jbpmContext.getSession();
		String name = file.getFilename();
		if (name.indexOf("WEB-INF") != -1) {
			name = name.substring(name.lastIndexOf("WEB-INF"));
		}
		Query query = session
				.createQuery(" select a from com.glaf.jbpm.model.ConfigFile as a where a.filename = ? ");
		query.setString(0, name);
		List<Object> list = query.list();
		if (list != null && list.size() > 0) {
			ConfigFile model = (ConfigFile) list.get(0);
			model.setLastModified(file.getLastModified());
			session.update(model);
		} else {
			file.setFilename(name);
			session.save(file);
		}

	}

	public void saveOrUpdate(JbpmContext jbpmContext, Object model) {
		Session session = jbpmContext.getSession();
		session.saveOrUpdate(model);
	}

	public void update(JbpmContext jbpmContext, Object model) {
		Session session = jbpmContext.getSession();
		session.update(model);
	}

	public void update(JbpmContext jbpmContext, String entityName, Object model) {
		Session session = jbpmContext.getSession();
		session.update(entityName, model);
	}

	public void updateAll(JbpmContext jbpmContext, Collection<Object> rows) {
		if (rows == null || rows.size() == 0) {
			return;
		}
		Session session = jbpmContext.getSession();
		Iterator<Object> iterator = rows.iterator();
		while (iterator.hasNext()) {
			Object model = iterator.next();
			session.update(model);
		}
	}

}