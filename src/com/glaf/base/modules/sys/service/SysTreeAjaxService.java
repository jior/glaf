package com.glaf.base.modules.sys.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.sys.model.SysTree;

public class SysTreeAjaxService {
	private static final Log logger = LogFactory.getLog(SysTreeAjaxService.class);
	private SysTreeService sysTreeService;
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
		logger.info("setSysTreeService");
	}
	/**
	 * ≈≈–Ú
	 * @param id
	 * @param operate
	 */
	public void sort(int parent, int id, int operate){
		logger.info("parent:"+parent+"; id:"+id+"; operate:"+operate);
		SysTree bean = sysTreeService.findById(id);
		sysTreeService.sort(parent, bean, operate);
	}
}
