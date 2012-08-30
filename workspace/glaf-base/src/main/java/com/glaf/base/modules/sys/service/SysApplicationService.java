package com.glaf.base.modules.sys.service;

import java.util.List;

import org.json.JSONArray;

import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.utils.PageResult;

public interface SysApplicationService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            SysApplication
	 * @return boolean
	 */
	boolean create(SysApplication bean);

	/**
	 * 删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            SysApplication
	 * @return boolean
	 */
	boolean delete(SysApplication bean);

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
	SysApplication findById(long id);

	/**
	 * 按名称查找对象
	 * 
	 * @param name
	 *            String
	 * @return SysApplication
	 */
	SysApplication findByName(String name);

	/**
	 * 获取用户能访问到的模块列表
	 * 
	 * @param userId
	 *            int
	 * @param parent
	 *            int
	 * @return List
	 */
	List<SysApplication> getAccessAppList(long parent, SysUser user);

	/**
	 * 获取全部列表
	 * 
	 * @return List
	 */
	List<SysApplication> getApplicationList();

	/**
	 * 获取列表
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	List<SysApplication> getApplicationList(int parent);

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
	PageResult getApplicationList(int parent, int pageNo, int pageSize);

	/**
	 * 获取菜单
	 * 
	 * @param parent
	 * @param userId
	 * @return
	 */
	String getMenu(long parent, SysUser user);

	/**
	 * 获取用户菜单之Json对象
	 * 
	 * @param parent
	 *            父节点编号
	 * @param userId
	 *            用户登录账号
	 * @return
	 */
	JSONArray getUserMenu(long parent, String userId);

	/**
	 * 排序
	 * 
	 * @param bean
	 *            SysApplication
	 * @param operate
	 *            int 操作
	 */
	void sort(long parent, SysApplication bean, int operate);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SysApplication
	 * @return boolean
	 */
	boolean update(SysApplication bean);
}
