package com.glaf.base.modules.sys.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.sys.model.Dictory;

public class DictoryAjaxService {
	private static final Log logger = LogFactory.getLog(DictoryAjaxService.class);
	private DictoryService dictoryService;	
	public void setDictoryService(DictoryService dictoryService) {
		this.dictoryService = dictoryService;
	}

	/**
	 * ≈≈–Ú
	 * @param id
	 * @param operate
	 */
	public void sort(int parent, int id, int operate){
		logger.info("parent:"+parent+"; id:"+id+"; operate:"+operate);
		Dictory bean = dictoryService.find(id);
		dictoryService.sort(parent, bean, operate);
	}
}
