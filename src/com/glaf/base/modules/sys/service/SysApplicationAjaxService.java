package com.glaf.base.modules.sys.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.sys.model.SysApplication;

public class SysApplicationAjaxService {
	private static final Log logger = LogFactory.getLog(SysApplicationAjaxService.class);
	private SysApplicationService sysApplicationService;
	public void setSysApplicationService(SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
		logger.info("sysApplicationService");
	}
	/**
	 * ≈≈–Ú
	 * @param id
	 * @param operate
	 */
	public void sort(int parent, int id, int operate){
		logger.info("parent:"+parent+"; id:"+id+"; operate:"+operate);
		SysApplication bean = sysApplicationService.findById(id);
		sysApplicationService.sort(parent, bean, operate);
	}
}
