package com.glaf.base.modules.sys.service;

import java.util.List;
import java.util.Set;

import com.glaf.base.modules.sys.model.SysDeptRole;
import com.glaf.base.utils.PageResult;

public interface SysDeptRoleService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            SysDeptRole
	 * @return boolean
	 */
	boolean create(SysDeptRole bean);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SysDeptRole
	 * @return boolean
	 */
	boolean update(SysDeptRole bean);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            SysDeptRole
	 * @return boolean
	 */
	boolean delete(SysDeptRole bean);

	/**
	 * 删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * 批量删除
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteAll(long[] id);

	boolean deleteByDept(long deptId);

	/**
	 * 获取对象
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
	 * 获取分页列表
	 * 
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	PageResult getRoleList(long deptId, int pageNo, int pageSize);

	/**
	 * 获取列表
	 * 
	 * @return List
	 */
	List<SysDeptRole> getRoleList(long deptId);

	/**
	 * 排序
	 * 
	 * @param bean
	 *            SysDeptRole
	 * @param operate
	 *            int 操作
	 */
	void sort(SysDeptRole bean, int operate);

	/**
	 * 设置角色对应的模块、功能
	 * 
	 * @param roleId
	 * @param appId
	 * @param funcId
	 * @return
	 */
	boolean saveRoleApplication(long roleId, long[] appId, long[] funcId);

}
