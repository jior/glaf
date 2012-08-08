package com.glaf.base.modules.sys.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.sys.model.SysLog;

public class SysLogServiceImpl implements SysLogService {
	private static final Log logger = LogFactory
			.getLog(SysLogServiceImpl.class);
	private AbstractSpringDao abstractDao;

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}

	/**
	 * 保存
	 * 
	 * @param bean
	 *            SysLog
	 * @return boolean
	 */
	public boolean create(SysLog bean) {
		return abstractDao.create(bean);
	}

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SysLog
	 * @return boolean
	 */
	public boolean update(SysLog bean) {
		return abstractDao.update(bean);
	}

	/**
	 * 删除
	 * 
	 * @param bean
	 *            SysLog
	 * @return boolean
	 */
	public boolean delete(SysLog bean) {
		return abstractDao.delete(bean);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	public boolean delete(long id) {
		SysLog bean = findById(id);
		if (bean != null) {
			return delete(bean);
		} else {
			return false;
		}
	}

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	public SysLog findById(long id) {
		return (SysLog) abstractDao.find(SysLog.class, new Long(id));
	}
}
