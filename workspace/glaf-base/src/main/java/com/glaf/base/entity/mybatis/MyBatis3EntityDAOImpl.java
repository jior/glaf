package com.glaf.base.entity.mybatis;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.ibatis.session.SqlSession;
import org.jpage.util.Tools;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.glaf.base.entity.EntityDAO;
import com.glaf.base.utils.ClassUtil;

public class MyBatis3EntityDAOImpl extends SqlSessionDaoSupport implements
		EntityDAO {
	protected final static Log logger = LogFactory
			.getLog(MyBatis3EntityDAOImpl.class);

	@SuppressWarnings("unchecked")
	public void delete(String statementId, Object parameterObject) {
		if (parameterObject instanceof Map) {
			Map<String, Object> dataMap = (Map<String, Object>) parameterObject;
			String className = (String) dataMap.get("className");
			if (className != null) {
				Object object = ClassUtil.instantiateObject(className);
				Tools.populate(object, dataMap);
				getSqlSession().delete(statementId, object);
			} else {
				getSqlSession().delete(statementId, dataMap);
			}
		} else {
			getSqlSession().delete(statementId, parameterObject);
		}
	}

	public void deleteAll(String statementId, List<Object> rowIds) {
		for (Object rowId : rowIds) {
			getSqlSession().delete(statementId, rowId);
		}
	}

	public void deleteById(String statementId, Object rowId) {
		getSqlSession().delete(statementId, rowId);
	}

	public Object getById(String statementId, Object parameterObject) {
		return getSqlSession().selectOne(statementId, parameterObject);
	}

	public int getCount(String statementId, Object parameterObject) {
		int totalCount = 0;
		SqlSession session = getSqlSession();

		Object object = null;
		if (parameterObject != null) {
			object = session.selectOne(statementId, parameterObject);
		} else {
			object = session.selectOne(statementId);
		}

		if (object instanceof Integer) {
			Integer iCount = (Integer) object;
			totalCount = iCount.intValue();
		} else if (object instanceof Long) {
			Long iCount = (Long) object;
			totalCount = iCount.intValue();
		} else if (object instanceof BigDecimal) {
			BigDecimal bg = (BigDecimal) object;
			totalCount = bg.intValue();
		} else if (object instanceof BigInteger) {
			BigInteger bi = (BigInteger) object;
			totalCount = bi.intValue();
		} else {
			String value = object.toString();
			totalCount = Integer.valueOf(value);
		}
		return totalCount;
	}

	@SuppressWarnings("unchecked")
	public List<Object> getList(String statementId, Object parameterObject) {
		logger.debug("statementId:" + statementId);
		logger.debug("parameter:" + parameterObject);
		List<Object> rows = getSqlSession().selectList(statementId,
				parameterObject);

		return rows;
	}

	public Object getSingleObject(String statementId, Object parameterObject) {
		return getSqlSession().selectOne(statementId, parameterObject);
	}

	public void insert(String statementId, Object parameterObject) {
		getSqlSession().insert(statementId, parameterObject);
	}

	public void insertAll(String statementId, List<Object> rows) {
		for (Object entity : rows) {
			getSqlSession().insert(statementId, entity);
		}
	}

	public void update(String statementId, Object parameterObject) {
		getSqlSession().update(statementId, parameterObject);
	}

	public void updateAll(String statementId, List<Object> rows) {
		for (Object entity : rows) {
			getSqlSession().update(statementId, entity);
		}
	}

}
