package com.glaf.base.modules.sys.service;

import java.util.List;

import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.utils.PageResult;

public interface SysDepartmentService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            SysDepartment
	 * @return boolean
	 */
	boolean create(SysDepartment bean);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SysDepartment
	 * @return boolean
	 */
	boolean update(SysDepartment bean);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            SysDepartment
	 * @return boolean
	 */
	boolean delete(SysDepartment bean);

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
	SysDepartment findById(long id);

	/**
	 * 按代码查找对象
	 * 
	 * @param name
	 *            String
	 * @return SysDepartment
	 */

	SysDepartment findByNo(String code);

	/**
	 * 按名称查找对象
	 * 
	 * @param name
	 *            String
	 * @return SysDepartment
	 */
	SysDepartment findByName(String name);

	/**
	 * 获取分页列表
	 * 
	 * @param parent
	 *            int
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	PageResult getSysDepartmentList(int parent, int pageNo, int pageSize);

	/**
	 * 获取列表
	 * 
	 * @return List
	 */
	List getSysDepartmentList();

	/**
	 * 获取列表
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	List getSysDepartmentList(int parent);

	/**
	 * 排序
	 * 
	 * @param bean
	 *            SysDepartment
	 * @param operate
	 *            int 操作
	 */
	void sort(long parent, SysDepartment bean, int operate);

	/**
	 * 获取用户部门列表
	 * 
	 * @param list
	 * @param node
	 */
	void findNestingDepartment(List list, SysDepartment node);
}
