package com.glaf.base.modules.workspace.service;

import java.util.Collection;
import java.util.List;

import com.glaf.base.modules.workspace.model.MyMenu;
import com.glaf.base.utils.PageResult;

public interface MyMenuService {

	/**
	 * 保存新增信息
	 * 
	 * @param myMenu
	 * @return
	 */
	boolean create(MyMenu bean);

	/**
	 * 单项删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * 单项删除
	 * 
	 * @param myMenu
	 * @return
	 */
	boolean delete(MyMenu myMenu);

	/**
	 * 批量删除
	 * 
	 * @param c
	 * @return
	 */
	boolean deleteAll(Collection c);

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	MyMenu find(long id);

	/**
	 * 获取列表(按序列号、编号排序)
	 * 
	 * @param userId
	 * @return
	 */
	List getMyMenuList(long userId);

	/**
	 * 获取列表(按序列号、编号排序)
	 * 
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageResult getMyMenuList(long userId, int pageNo, int pageSize);

	/**
	 * 排序
	 * 
	 * @param bean
	 *            MyMenu
	 * @param operate
	 *            int 操作
	 */
	void sort(MyMenu bean, int operate);

	/**
	 * 更新信息
	 * 
	 * @param myMenu
	 * @return
	 */
	boolean update(MyMenu myMenu);

}
