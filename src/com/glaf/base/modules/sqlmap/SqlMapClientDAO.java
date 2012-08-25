package com.glaf.base.modules.sqlmap;

import java.util.List;

import org.jpage.core.query.paging.Page;
import org.jpage.persistence.Executor;

public interface SqlMapClientDAO {

	/**
	 * ����ִ�в���
	 * 
	 * @param rows
	 *            Executor����ļ���
	 * @see Executor
	 */
	public void execute(final List<Executor> rows);

	/**
	 * ����һ����¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	public void insertObject(String statementId, Object parameterObject);

	/**
	 * �޸�һ����¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	public void updateObject(String statementId, Object parameterObject);

	/**
	 * ɾ��һ����¼
	 * 
	 * @param statementId
	 * @param parameterObject
	 */
	public void deleteObject(String statementId, Object parameterObject);

	/**
	 * ���������¼
	 * 
	 * @param statementId
	 * @param rows
	 */
	public void insertAll(final String statementId, final List<Object> rows);

	/**
	 * �޸Ķ�����¼
	 * 
	 * @param statementId
	 * @param rows
	 */
	public void updateAll(final String statementId, final List<Object> rows);

	/**
	 * ɾ��������¼
	 * 
	 * @param statementId
	 * @param rows
	 */
	public void deleteAll(final String statementId, final List<Object> rows);

	public Object queryForObject(String statementId);

	public Object queryForObject(String statementId, Object parameterObject);

	public Object queryForObject(String statementId, Object parameterObject,
			Object resultObject);

	public List<Object> getList(Executor executor);

	/**
	 * ��ȡĳҳ�ļ�¼
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param executor
	 * @return
	 */

	public List<Object> getList(int pageNo, int pageSize, Executor executor);

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param currPageNo
	 * @param pageSize
	 * @param countExecutor
	 * @param queryExecutor
	 * @return
	 */
	public Page getPage(final int pageNo, final int pSize,
			final Executor countExecutor, final Executor queryExecutor);

}
