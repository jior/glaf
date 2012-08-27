package com.glaf.base.entity;

import java.util.List;

public interface EntityDAO {

	/**
	 * 删除记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	void delete(String statementId, Object parameterObject);

	/**
	 * 删除多条记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	void deleteAll(String statementId, List<Object> rowIds);

	/**
	 * 根据记录主键删除记录
	 * 
	 * @param statementId
	 * @param rowId
	 */
	void deleteById(String statementId, Object rowId);

	/**
	 * 根据主键获取记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 * @return
	 */
	Object getById(String statementId, Object parameterObject);

	/**
	 * 获取总记录数
	 * 
	 * @param statementId
	 * @param parameterObject
	 * @return
	 */
	int getCount(String statementId, Object parameterObject);

	/**
	 * 获取数据集
	 * 
	 * @param statementId
	 * @param parameterObject
	 * @return
	 */
	List<Object> getList(String statementId, Object parameterObject);

	/**
	 * 获取单个对象
	 * 
	 * @param statementId
	 * @param parameterObject
	 * @return
	 */
	Object getSingleObject(String statementId, Object parameterObject);

	/**
	 * 插入一条记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	void insert(String statementId, Object parameterObject);

	/**
	 * 插入多条记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	void insertAll(String statementId, List<Object> rows);

	/**
	 * 修改一条记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	void update(String statementId, Object parameterObject);

	/**
	 * 修改多条记录
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	void updateAll(String statementId, List<Object> rows);

}
