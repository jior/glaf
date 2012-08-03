package com.glaf.base.modules.sys.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SysFunctionAjaxService {
	private static final Log logger = LogFactory.getLog(SysFunctionAjaxService.class);
	private SysFunctionService sysFunctionService;
	public void setSysFunctionService(SysFunctionService sysFunctionService) {
		this.sysFunctionService = sysFunctionService;
		logger.info("setSysFunctionService");
	}
	/**
	 * ≈≈–Ú
	 * @param id
	 * @param operate
	 */
	public void sort(int id, int operate){
		logger.info("id:"+id+";operate:"+operate);
		sysFunctionService.sort(sysFunctionService.findById(id), operate);
	}
}
