package com.glaf.base.modules.sys.service;

import com.glaf.base.modules.sys.model.SysLog;

public interface SysLogService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            SysLog
	 * @return boolean
	 */
	boolean create(SysLog bean);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SysLog
	 * @return boolean
	 */
	boolean update(SysLog bean);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            SysLog
	 * @return boolean
	 */
	boolean delete(SysLog bean);

	/**
	 * 删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	SysLog findById(long id);
}
