package com.glaf.base.modules.sys.service;

import java.util.List;

import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.utils.PageResult;

public interface SysRoleService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            SysRole
	 * @return boolean
	 */
	boolean create(SysRole bean);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SysRole
	 * @return boolean
	 */
	boolean update(SysRole bean);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            SysRole
	 * @return boolean
	 */
	boolean delete(SysRole bean);

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

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	SysRole findById(long id);

	/**
	 * 按名称查找对象
	 * 
	 * @param name
	 *            String
	 * @return SysRole
	 */
	SysRole findByName(String name);

	/**
	 * 按code查找对象
	 * 
	 * @param name
	 *            String
	 * @return SysRole
	 */
	SysRole findByCode(String code);

	/**
	 * 获取分页列表
	 * 
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	PageResult getSysRoleList(int pageNo, int pageSize);

	/**
	 * 获取列表
	 * 
	 * @return List
	 */
	List<SysRole> getSysRoleList();

	/**
	 * 排序
	 * 
	 * @param bean
	 *            SysRole
	 * @param operate
	 *            int 操作
	 */
	void sort(SysRole bean, int operate);

}
