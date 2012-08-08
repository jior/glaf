package com.glaf.base.modules.sys.service;

import java.util.List;
import java.util.Set;

import com.glaf.base.modules.sys.model.SysDeptRole;
import com.glaf.base.utils.PageResult;

public interface SysDeptRoleService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysDeptRole
	 * @return boolean
	 */
	boolean create(SysDeptRole bean);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysDeptRole
	 * @return boolean
	 */
	boolean update(SysDeptRole bean);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SysDeptRole
	 * @return boolean
	 */
	boolean delete(SysDeptRole bean);

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

	boolean deleteByDept(long deptId);

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	SysDeptRole findById(long id);

	/**
	 * 
	 * @param deptId
	 * @param roleId
	 * @return
	 */
	SysDeptRole find(long deptId, long roleId);

	/**
	 * 
	 * @param deptId
	 * @param code
	 * @return
	 */
	Set<SysDeptRole> findRoleUser(long deptId, String code);

	/**
	 * ��ȡ��ҳ�б�
	 * 
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	PageResult getRoleList(long deptId, int pageNo, int pageSize);

	/**
	 * ��ȡ�б�
	 * 
	 * @return List
	 */
	List<SysDeptRole> getRoleList(long deptId);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysDeptRole
	 * @param operate
	 *            int ����
	 */
	void sort(SysDeptRole bean, int operate);

	/**
	 * ���ý�ɫ��Ӧ��ģ�顢����
	 * 
	 * @param roleId
	 * @param appId
	 * @param funcId
	 * @return
	 */
	boolean saveRoleApplication(long roleId, long[] appId, long[] funcId);

}
