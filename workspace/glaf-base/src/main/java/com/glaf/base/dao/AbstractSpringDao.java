package com.glaf.base.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.type.Type;
import org.jpage.util.DateTools;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.glaf.base.utils.PageResult;
import com.glaf.base.utils.MyBeanUtils;

/**
 * 抽象Dao类，实现CRUD基本方法
 * 
 * @author user
 * 
 */
public class AbstractSpringDao extends HibernateDaoSupport {
	private static final Log logger = LogFactory
			.getLog(AbstractSpringDao.class);

	public static void main(String args[]) {
		String query = "SELECT COUNT(B.ID) FROM PAYMENT AS B RIGHT JOIN B.ORDER AS A WHERE B.STATUS <> -999 AND (A.CONTRACT.ID IS NULL OR A.CONTRACT.ID = 0) AND A.STATUS NOT IN (0,10,20,-1,80) ORDER BY A.ID";
		System.out.println("query.toUpperCase() " + query.toUpperCase());
		int position = query.toUpperCase().indexOf("ORDER BY");
		if (-1 != position) {
			System.out.println("str.length = " + query.length()
					+ "  indexof(ORDER BY)="
					+ query.toUpperCase().indexOf("ORDER BY"));
			query = query.substring(0, position);
		}
		System.out.println("getResutlTotalByQuery  query:" + query);
	}

	public AbstractSpringDao() {
		logger.info("AbstractSpringDao init");
	}

	/**
	 * 保存
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean create(Object obj) {
		boolean ret = false;
		try {
			getHibernateTemplate().save(obj);
			logger.debug("create");
			ret = true;
		} catch (Exception e) {
			logger.error(e);
		}
		return ret;
	}

	/**
	 * 删除
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean delete(Object obj) {
		boolean ret = false;
		try {
			getHibernateTemplate().delete(obj);
			logger.debug("delete");
			ret = true;
		} catch (Exception e) {
			logger.error(e);
		}
		return ret;
	}

	/**
	 * 批量删除
	 * 
	 * @param c
	 */
	public boolean deleteAll(Collection c) {
		boolean ret = false;
		try {
			if (c != null) {
				getHibernateTemplate().deleteAll(c);
			}
			logger.debug("deleteAll");
			ret = true;
		} catch (Exception e) {
			logger.error(e);
		}
		return ret;
	}

	/**
	 * 执行批量操作，如delete、update
	 * 
	 * @param hsql
	 *            hsql语句
	 */
	public boolean execute(final String hsql) {
		boolean ret = true;
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				logger.debug("execute HQL: " + hsql);
				session.createQuery(hsql).executeUpdate();
				return null;
			}
		});
		return ret;
	}

	/**
	 * 执行批量操作，如delete、update
	 * 
	 * @param hsql
	 *            hsql语句
	 */
	public boolean execute(final String hsql, final Object[] values,
			final Type[] types) {
		boolean ret = true;
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				logger.debug("execute HQL: " + hsql);
				session.createQuery(hsql).setParameters(values, types)
						.executeUpdate();
				return null;
			}
		});
		return ret;
	}

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 * @return
	 */
	public boolean executeSQL(final String sql) {
		boolean ret = true;
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				logger.debug("execute sql: " + sql);
				session.createSQLQuery(sql).executeUpdate();
				return null;
			}
		});
		return ret;
	}

	public boolean executeSQL(final String sql, final List values) {
		boolean ret = true;
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				logger.debug("execute SQL: " + sql);
				java.sql.PreparedStatement psmt = session.connection()
						.prepareStatement(sql);

				if (values != null && values.size() > 0) {
					fillStatement(psmt, values);
				}

				psmt.executeUpdate();
				psmt.close();
				psmt = null;

				return null;
			}
		});
		return ret;
	}

	public void fillStatement(PreparedStatement stmt, List params)
			throws SQLException {
		if (params == null || params.size() == 0) {
			return;
		}
		for (int i = 0; i < params.size(); i++) {
			Object obj = params.get(i);
			if (obj != null) {
				if (obj instanceof java.sql.Date) {
					java.sql.Date sqlDate = (java.sql.Date) obj;
					stmt.setDate(i + 1, sqlDate);
				} else if (obj instanceof java.sql.Time) {
					java.sql.Time sqlTime = (java.sql.Time) obj;
					stmt.setTime(i + 1, sqlTime);
				} else if (obj instanceof java.sql.Timestamp) {
					Timestamp datetime = (Timestamp) obj;
					stmt.setTimestamp(i + 1, datetime);
				} else if (obj instanceof java.util.Date) {
					Timestamp datetime = DateTools
							.toTimestamp((java.util.Date) obj);
					stmt.setTimestamp(i + 1, datetime);
				} else {
					stmt.setObject(i + 1, obj);
				}
			} else {
				stmt.setString(i + 1, null);
			}
		}
	}

	/**
	 * 查询
	 * 
	 * @param clazz
	 *            Class
	 * @param id
	 *            Long
	 * @return Object
	 */
	public Object find(Class clazz, Long id) {
		Object ret = null;
		try {
			ret = getHibernateTemplate().get(clazz, id);
			logger.debug("find");
		} catch (Exception e) {
			logger.error(e);
		}
		return ret;
	}

	/**
	 * 根据sql拿记录数
	 * 
	 * @param sql
	 * @return
	 */
	public double getCountBySQL(String sql) {
		double count = 0;
		Session session = null;
		try {
			getHibernateTemplate().setAllowCreate(true);
			session = getSession();
			Query q = session.createSQLQuery(sql);
			if (q.uniqueResult() != null) {
				count = ((java.lang.Number) q.uniqueResult()).doubleValue();
			}
		} catch (HibernateException e) {
			logger.error(e);
		} finally {
			if (session != null) {
				releaseSession(session);
			}
		}
		return count;
	}

	public double getDoubleBySQL(String sql) {
		Session session = null;
		double dd = 0;
		try {
			getHibernateTemplate().setAllowCreate(true);
			session = getSession();
			if (((BigDecimal) session.createSQLQuery(sql).uniqueResult()) != null)
				dd = ((BigDecimal) session.createSQLQuery(sql).uniqueResult())
						.doubleValue();

		} catch (Exception e) {
			logger.error(e);
		}

		return dd;
	}

	/**
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	public List getList(final DetachedCriteria detachedCriteria) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = detachedCriteria
						.getExecutableCriteria(session);
				return criteria.list();
			}
		});
	}

	/**
	 * 
	 * @param criteria
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageResult getList(final DetachedCriteria detachedCriteria,
			final int pageNo, final int pageSize) {
		PageResult pager = new PageResult();

		Criteria criteria = (Criteria) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);
						return criteria;
					}
				});
		CriteriaImpl impl = (CriteriaImpl) criteria;

		Projection projection = impl.getProjection();

		List orderEntries = null;
		try {
			orderEntries = (List) MyBeanUtils.getFieldValue(impl,
					"orderEntries");
			MyBeanUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		Long iCount = (Long) criteria.setProjection(Projections.rowCount())
				.uniqueResult();
		int totalCount = ((iCount != null) ? iCount.intValue() : 0);
		pager.setTotalRecordCount(totalCount);// 查询总记录数
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		try {
			List innerOrderEntries = (List) MyBeanUtils.getFieldValue(impl,
					"orderEntries");
			Iterator it = orderEntries.iterator();
			while (it.hasNext()) {
				innerOrderEntries.add(it.next());
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		criteria.setFirstResult(pageSize * (pageNo - 1));
		criteria.setMaxResults(pageSize);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setResults(criteria.list());
		return pager;
	}

	public List getList(final String sql, final Map params) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session.createQuery(sql);
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
				List rows = query.list();
				return rows;
			}
		});
	}

	/**
	 * 获取分页列表
	 * 
	 * @param query
	 *            String
	 * @param args
	 *            Object
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @param count
	 *            int
	 * @return PageResult
	 */
	public PageResult getList(String query, Object args[], int pageNo,
			int pageSize, int count) {
		logger.debug("query:" + query);
		PageResult pager = new PageResult();
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		Session session = null;
		try {
			getHibernateTemplate().setAllowCreate(true);
			session = getSession();
			Query q = session.createQuery(query);

			if (args != null && args.length > 0) {// 设置参数
				for (int i = 0; i < args.length; i++)
					q.setParameter(i, args[i]);
			}
			pager.setTotalRecordCount(count);// 查询总记录数
			q.setFirstResult(pageSize * (pager.getCurrentPageNo() - 1));
			q.setMaxResults(pageSize);
			pager.setResults(q.list());

			q = null;
		} catch (HibernateException e) {
			logger.error(e);
		} finally {
			if (session != null) {
				releaseSession(session);
			}
		}
		return pager;
	}

	/**
	 * 获取列表
	 * 
	 * @param query
	 *            String
	 * @param values
	 *            Object[]
	 * @param types
	 *            Type[]
	 * @return List
	 */
	public List getList(String query, Object[] values, Type[] types) {
		logger.debug("query:" + query);
		List list = null;
		try {
			list = getHibernateTemplate().find(query, values);
		} catch (Exception ex) {
			logger.error(ex);
		}
		return list;
	}

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 * @return
	 */
	public List getListBySQL(final String sql) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				logger.debug("execute sql: " + sql);
				return session.createSQLQuery(sql).list();
			}
		});
	}

	/**
	 * 获取分页列表
	 * 
	 * @param query
	 *            String
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @param count
	 *            int
	 * @return PageResult
	 */
	public PageResult getListBySQL(String query, int pageNo, int pageSize,
			int count) {
		logger.debug("query:" + query);
		PageResult pager = new PageResult();
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		Session session = null;
		try {
			getHibernateTemplate().setAllowCreate(true);
			session = getSession();
			Query q = session.createSQLQuery(query);
			pager.setTotalRecordCount(count);// 查询总记录数
			q.setFirstResult(pageSize * (pager.getCurrentPageNo() - 1));
			q.setMaxResults(pageSize);
			pager.setResults(q.list());
			q = null;
		} catch (HibernateException e) {
			logger.error(e);
		} finally {
			if (session != null) {
				releaseSession(session);
			}
		}
		return pager;
	}

	/**
	 * 立即加载一个对象
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Object getObject(Class clazz, Long id) {
		Object ret = null;
		try {
			ret = getHibernateTemplate().get(clazz, id);
			logger.debug("get");
		} catch (Exception e) {
			logger.error(e);
		}
		return ret;
	}

	/**
	 * 由Query 得到列表记录的总记录数。
	 * 
	 * @param query
	 * @param values
	 * @param types
	 * @return
	 */
	public int getResutlTotalByQuery(String query, Object[] values, Type[] types) {
		int total = 0;
		Session session = null;
		logger.debug("getResutlTotalByQuery  query:" + query);
		int position = query.toUpperCase().indexOf("ORDER BY");
		if (-1 != position)
			query = query.substring(0, position);
		try {
			getHibernateTemplate().setAllowCreate(true);
			session = getSession();
			Query q = session.createQuery(query);
			if (values != null && values.length > 0) {// 设置参数
				for (int i = 0; i < values.length; i++)
					q.setParameter(i, values[i]);
			}
			total = ((java.lang.Number) q.uniqueResult()).intValue();
		} catch (HibernateException e) {
			logger.error(e);
		} finally {
			if (session != null) {
				releaseSession(session);
			}
		}
		return total;
	}

	/**
	 * 根据sql拿指定字段的值
	 * 
	 * @param sql
	 * @return
	 */
	public String getStringBySQL(String sql) {
		String str = "";
		Session session = null;
		try {
			getHibernateTemplate().setAllowCreate(true);
			session = getSession();
			Query q = session.createSQLQuery(sql);
			List list = q.list();
			if (list != null && list.size() > 0) {
				str = (String) list.get(0);
			}
 
		} catch (HibernateException e) {
			logger.error(e);
		} finally {
			if (session != null) {
				releaseSession(session);
			}
		}
		return str;
	}

	public Object load(Class clazz, Long id) {
		Object ret = null;
		try {
			ret = getHibernateTemplate().load(clazz, id);
			logger.debug("load");
		} catch (Exception e) {
			logger.error(e);
		}
		return ret;
	}

	public boolean saveAll(java.util.Collection rows) {
		boolean ret = false;
		try {
			if (rows != null && rows.size() > 0) {
				Iterator iterator = rows.iterator();
				while (iterator.hasNext()) {
					Object obj = iterator.next();
					getHibernateTemplate().saveOrUpdate(obj);
				}
			}
			logger.debug("saveAll");
			ret = true;
		} catch (Exception e) {
			logger.error(e);
		}
		return ret;
	}

	/**
	 * 保存或更新
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean saveOrUpdate(Object obj) {
		boolean ret = false;
		try {
			getHibernateTemplate().saveOrUpdate(obj);
			logger.debug("saveOrUpdate");
			ret = true;
		} catch (Exception e) {
			logger.error(e);
		}
		return ret;
	}

	/**
	 * 保存
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean update(Object obj) {
		boolean ret = false;
		try {
			getHibernateTemplate().merge(obj);
			logger.debug("update");
			ret = true;
		} catch (Exception e) {
			logger.error(e);
		}
		return ret;
	}

}
