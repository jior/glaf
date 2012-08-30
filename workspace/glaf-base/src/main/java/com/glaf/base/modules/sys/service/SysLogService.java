package com.glaf.base.modules.sys.service;

import com.glaf.base.modules.sys.model.SysLog;

public interface SysLogService {

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysLog
	 * @return boolean
	 */
	boolean create(SysLog bean);

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysLog
	 * @return boolean
	 */
	boolean update(SysLog bean);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SysLog
	 * @return boolean
	 */
	boolean delete(SysLog bean);

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	SysLog findById(long id);
}
