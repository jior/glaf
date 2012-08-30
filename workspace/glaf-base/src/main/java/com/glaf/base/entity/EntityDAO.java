package com.glaf.base.entity;

import java.util.List;

public interface EntityDAO {

	/**
	 * ɾ����¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	void delete(String statementId, Object parameterObject);

	/**
	 * ɾ��������¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	void deleteAll(String statementId, List<Object> rowIds);

	/**
	 * ���ݼ�¼����ɾ����¼
	 * 
	 * @param statementId
	 * @param rowId
	 */
	void deleteById(String statementId, Object rowId);

	/**
	 * ����������ȡ��¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 * @return
	 */
	Object getById(String statementId, Object parameterObject);

	/**
	 * ��ȡ�ܼ�¼��
	 * 
	 * @param statementId
	 * @param parameterObject
	 * @return
	 */
	int getCount(String statementId, Object parameterObject);

	/**
	 * ��ȡ���ݼ�
	 * 
	 * @param statementId
	 * @param parameterObject
	 * @return
	 */
	List<Object> getList(String statementId, Object parameterObject);

	/**
	 * ��ȡ��������
	 * 
	 * @param statementId
	 * @param parameterObject
	 * @return
	 */
	Object getSingleObject(String statementId, Object parameterObject);

	/**
	 * ����һ����¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	void insert(String statementId, Object parameterObject);

	/**
	 * ���������¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	void insertAll(String statementId, List<Object> rows);

	/**
	 * �޸�һ����¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	void update(String statementId, Object parameterObject);

	/**
	 * �޸Ķ�����¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	void updateAll(String statementId, List<Object> rows);

}
