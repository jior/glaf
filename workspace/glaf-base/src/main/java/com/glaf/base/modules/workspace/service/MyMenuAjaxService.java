package com.glaf.base.modules.workspace.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.workspace.model.MyMenu;

public class MyMenuAjaxService {
	private static final Log logger = LogFactory
			.getLog(MyMenuAjaxService.class);

	private MyMenuService myMenuService;

	public void setMyMenuService(MyMenuService myMenuService) {
		this.myMenuService = myMenuService;
		logger.info("setMyMenuService");
	}

	/**
	 * ≈≈–Ú
	 * 
	 * @param id
	 * @param operate
	 */
	public void sort(int id, int operate) {
		logger.info("id:" + id + ";operate:" + operate + ";myMenuService:"
				+ myMenuService);
		MyMenu bean = myMenuService.find(id);
		logger.info("MyMenu:" + bean);
		myMenuService.sort(bean, operate);
	}

}
