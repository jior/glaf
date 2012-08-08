package com.glaf.base.modules.sys.service;

import java.util.List;

import com.glaf.base.modules.sys.model.SysFunction;

public interface SysFunctionService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            SysFunction
	 * @return boolean
	 */
	boolean create(SysFunction bean);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SysFunction
	 * @return boolean
	 */
	boolean update(SysFunction bean);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            SysFunction
	 * @return boolean
	 */
	boolean delete(SysFunction bean);

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
	SysFunction findById(long id);

	/**
	 * 获取列表
	 * 
	 * @param appId
	 *            int
	 * @return List
	 */
	List<SysFunction> getSysFunctionList(int appId);

	/**
	 * 获取全部列表
	 * 
	 * @return List
	 */
	List<SysFunction> getSysFunctionList();

	/**
	 * 排序
	 * 
	 * @param bean
	 *            SysFunction
	 * @param operate
	 *            int 操作
	 */
	void sort(SysFunction bean, int operate);

}
