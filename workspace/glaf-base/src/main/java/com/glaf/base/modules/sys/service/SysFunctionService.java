package com.glaf.base.modules.sys.service;

import java.util.List;

import com.glaf.base.modules.sys.model.SysFunction;

public interface SysFunctionService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysFunction
	 * @return boolean
	 */
	boolean create(SysFunction bean);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysFunction
	 * @return boolean
	 */
	boolean update(SysFunction bean);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SysFunction
	 * @return boolean
	 */
	boolean delete(SysFunction bean);

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * ����ɾ��
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteAll(long[] id);

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	SysFunction findById(long id);

	/**
	 * ��ȡ�б�
	 * 
	 * @param appId
	 *            int
	 * @return List
	 */
	List<SysFunction> getSysFunctionList(int appId);

	/**
	 * ��ȡȫ���б�
	 * 
	 * @return List
	 */
	List<SysFunction> getSysFunctionList();

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysFunction
	 * @param operate
	 *            int ����
	 */
	void sort(SysFunction bean, int operate);

}
