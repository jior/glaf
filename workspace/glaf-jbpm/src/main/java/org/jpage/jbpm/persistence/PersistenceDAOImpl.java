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


package org.jpage.jbpm.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jbpm.JbpmContext;
import org.jpage.core.query.paging.Page;
import org.jpage.persistence.Executor;

public class PersistenceDAOImpl implements PersistenceDAO {

	public void delete(JbpmContext jbpmContext, Serializable model) {
		Session session = jbpmContext.getSession();
		session.delete(model);
	}

	public void deleteAll(JbpmContext jbpmContext, Collection rows) {
		if (rows == null || rows.size() == 0) {
			return;
		}
		Session session = jbpmContext.getSession();
		Iterator iterator = rows.iterator();
		while (iterator.hasNext()) {
			Serializable model = (Serializable) iterator.next();
			session.delete(model);
		}
	}

	public void merge(JbpmContext jbpmContext, Serializable model) {
		Session session = jbpmContext.getSession();
		session.merge(model);
	}

	public void mergeAll(JbpmContext jbpmContext, Collection rows) {
		if (rows == null || rows.size() == 0) {
			return;
		}
		Session session = jbpmContext.getSession();
		Iterator iterator = rows.iterator();
		while (iterator.hasNext()) {
			Serializable model = (Serializable) iterator.next();
			session.merge(model);
		}
	}

	public void persist(JbpmContext jbpmContext, Serializable model) {
		Session session = jbpmContext.getSession();
		session.persist(model);
	}

	public void persistAll(JbpmContext jbpmContext, Collection rows) {
		if (rows == null || rows.size() == 0) {
			return;
		}
		Session session = jbpmContext.getSession();
		Iterator iterator = rows.iterator();
		while (iterator.hasNext()) {
			Serializable model = (Serializable) iterator.next();
			session.persist(model);
		}
	}

	public void save(JbpmContext jbpmContext, Serializable model) {
		Session session = jbpmContext.getSession();
		session.save(model);
	}

	public void saveAll(JbpmContext jbpmContext, Collection rows) {
		if (rows == null || rows.size() == 0) {
			return;
		}
		Session session = jbpmContext.getSession();
		Iterator iterator = rows.iterator();
		while (iterator.hasNext()) {
			Serializable model = (Serializable) iterator.next();
			session.save(model);
		}
	}

	public void update(JbpmContext jbpmContext, Serializable model) {
		Session session = jbpmContext.getSession();
		session.update(model);
	}

	public void updateAll(JbpmContext jbpmContext, Collection rows) {
		if (rows == null || rows.size() == 0) {
			return;
		}
		Session session = jbpmContext.getSession();
		Iterator iterator = rows.iterator();
		while (iterator.hasNext()) {
			Serializable model = (Serializable) iterator.next();
			session.update(model);
		}
	}

	public Object getPersistObject(JbpmContext jbpmContext, Class clazz,
			java.io.Serializable persistId) {
		Session session = jbpmContext.getSession();
		return session.get(clazz, persistId);
	}

	public List query(JbpmContext jbpmContext, Executor queryExecutor) {
		Session session = jbpmContext.getSession();
		Query query = session.createQuery(queryExecutor.getQuery());
		Object[] values = queryExecutor.getValues();

		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}

		Map params = queryExecutor.getParams();
		if (params != null && params.size() > 0) {
			Iterator iterator = params.keySet().iterator();
			while (iterator.hasNext()) {
				String name = (String) iterator.next();
				Object value = params.get(name);
				if (value != null) {
					if (value instanceof Collection) {
						query.setParameterList(name, (Collection) value);
					} else {
						query.setParameter(name, value);
					}
				}
			}
		}

		if (queryExecutor.isCacheable()) {
			query.setCacheable(true);
		}

		List rows = query.list();
		return rows;
	}

	public List query(JbpmContext jbpmContext, int currPageNo, int maxResults,
			Executor queryExecutor) {
		Session session = jbpmContext.getSession();
		Query query = session.createQuery(queryExecutor.getQuery());
		Object[] values = queryExecutor.getValues();

		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}

		Map params = queryExecutor.getParams();
		if (params != null && params.size() > 0) {
			Iterator iterator = params.keySet().iterator();
			while (iterator.hasNext()) {
				String name = (String) iterator.next();
				Object value = params.get(name);
				if (value != null) {
					if (value instanceof Collection) {
						query.setParameterList(name, (Collection) value);
					} else {
						query.setParameter(name, value);
					}
				}
			}
		}

		query.setFirstResult((currPageNo - 1) * maxResults);
		query.setMaxResults(maxResults);

		if (queryExecutor.isCacheable()) {
			query.setCacheable(true);
		}

		List rows = query.list();
		return rows;
	}

	public Page getPage(JbpmContext jbpmContext, int currPageNo, int pageSize,
			Executor countExecutor, Executor queryExecutor) {
		Session session = jbpmContext.getSession();
		Page page = new Page();
		if (pageSize <= 0) {
			pageSize = Page.DEFAULT_PAGE_SIZE;
		}
		if (currPageNo <= 0) {
			currPageNo = 1;
		}
		int totalCount = 0;
		if (countExecutor != null) {
			Object obj = null;
			Query q = session.createQuery(countExecutor.getQuery());
			Object[] values = countExecutor.getValues();
			if (values != null && values.length > 0) {
				for (int i = 0; i < values.length; i++) {
					q.setParameter(i, values[i]);
				}
			}

			Map params = countExecutor.getParams();

			if (params != null && params.size() > 0) {
				Iterator iterator = params.keySet().iterator();
				while (iterator.hasNext()) {
					String name = (String) iterator.next();
					Object value = params.get(name);
					if (value != null) {
						if (value instanceof Collection) {
							q.setParameterList(name, (Collection) value);
						} else {
							q.setParameter(name, value);
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
			List list = null;
			Query q = session.createQuery(queryExecutor.getQuery());
			Object[] values = queryExecutor.getValues();
			if (values != null && values.length > 0) {
				for (int i = 0; i < values.length; i++) {
					q.setParameter(i, values[i]);
				}
			}

			Map params = queryExecutor.getParams();

			if (params != null && params.size() > 0) {
				Iterator iterator = params.keySet().iterator();
				while (iterator.hasNext()) {
					String name = (String) iterator.next();
					Object value = params.get(name);
					if (value != null) {
						if (value instanceof Collection) {
							q.setParameterList(name, (Collection) value);
						} else {
							q.setParameter(name, value);
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
			page.setRows(new ArrayList());
			page.setCurrentPage(0);
			page.setPageSize(0);
			page.setTotalRecordCount(0);
			return page;
		}
		page.setTotalRecordCount(totalCount);

		int maxPageNo = (page.getTotalRecordCount() + (pageSize - 1))
				/ pageSize;
		if (currPageNo > maxPageNo) {
			currPageNo = maxPageNo;
		}

		Query query = session.createQuery(queryExecutor.getQuery());
		Object[] values = queryExecutor.getValues();

		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}

		Map params = queryExecutor.getParams();
		if (params != null && params.size() > 0) {
			Iterator iterator = params.keySet().iterator();
			while (iterator.hasNext()) {
				String name = (String) iterator.next();
				Object value = params.get(name);
				if (value != null) {
					if (value instanceof Collection) {
						query.setParameterList(name, (Collection) value);
					} else {
						query.setParameter(name, value);
					}
				}
			}
		}

		query.setFirstResult((currPageNo - 1) * pageSize);
		query.setMaxResults(pageSize);

		if (queryExecutor.isCacheable()) {
			query.setCacheable(true);
		}

		List list = query.list();
		page.setRows(list);
		page.setPageSize(pageSize);
		page.setCurrentPage(currPageNo);

		return page;
	}

}
