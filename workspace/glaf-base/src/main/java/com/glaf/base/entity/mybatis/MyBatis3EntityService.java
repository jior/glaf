package com.glaf.base.entity.mybatis;

import java.util.*;

import com.glaf.base.entity.EntityDAO;
import com.glaf.base.entity.EntityService;

public class MyBatis3EntityService implements EntityService {

	protected EntityDAO entityDAO;

	public MyBatis3EntityService() {

	}

	public void delete(String statementId, Object parameter) {
		entityDAO.delete(statementId, parameter);
	}

	public void deleteAll(String statementId, List<Object> rows) {
		if (rows != null && rows.size() > 0) {
			entityDAO.deleteAll(statementId, rows);
		}
	}

	public void deleteById(String statementId, Object rowId) {
		entityDAO.deleteById(statementId, rowId);
	}

	public Object getById(String statementId, Object parameterObject) {
		return entityDAO.getById(statementId, parameterObject);
	}

	public int getCount(String statementId, Object parameterObject) {
		return entityDAO.getCount(statementId, parameterObject);
	}

	/**
	 * ≤È—Ø
	 * 
	 * @param queryId
	 * @param params
	 * @return
	 */
	public List<Object> getList(String statementId, Object parameter) {
		return entityDAO.getList(statementId, parameter);
	}

	public Object getSingleObject(String statementId, Object parameterObject) {
		return entityDAO.getSingleObject(statementId, parameterObject);
	}

	public void insert(String statementId, Object obj) {
		entityDAO.insert(statementId, obj);
	}

	public void insertAll(String statementId, List<Object> rows) {
		if (rows != null && rows.size() > 0) {
			entityDAO.insertAll(statementId, rows);
		}
	}

	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	public void update(String statementId, Object obj) {
		entityDAO.update(statementId, obj);
	}

	public void updateAll(String statementId, List<Object> rows) {
		if (rows != null && rows.size() > 0) {
			entityDAO.updateAll(statementId, rows);
		}
	}

}