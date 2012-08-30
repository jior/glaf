package com.glaf.base.modules.sys.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SysRoleAjaxService {
	private static final Log logger = LogFactory.getLog(SysRoleAjaxService.class);
	private SysRoleService sysRoleService;
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
		logger.info("setSysRoleService");
	}
	/**
	 * ≈≈–Ú
	 * @param id
	 * @param operate
	 */
	public void sort(int id, int operate){
		logger.info("id:"+id+";operate:"+operate);
		sysRoleService.sort(sysRoleService.findById(id), operate);
	}
}
