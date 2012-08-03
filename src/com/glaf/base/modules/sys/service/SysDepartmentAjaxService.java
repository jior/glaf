package com.glaf.base.modules.sys.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.sys.model.SysDepartment;

public class SysDepartmentAjaxService {
	private static final Log logger = LogFactory.getLog(SysDepartmentAjaxService.class);
	private SysDepartmentService sysDepartmentService;
	public void setSysDepartmentService(SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
		logger.info("setSysDepartmentService");
	}
	
	/**
	 * ≈≈–Ú
	 * @param id
	 * @param operate
	 */
	public void sort(int parent, int id, int operate){
		logger.info("parent:"+parent+"; id:"+id+"; operate:"+operate);
		SysDepartment bean = sysDepartmentService.findById(id);
		sysDepartmentService.sort(parent, bean, operate);
	}
}
