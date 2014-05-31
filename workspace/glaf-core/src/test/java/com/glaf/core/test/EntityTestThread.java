package com.glaf.core.test;

import com.glaf.core.service.EntityService;

public class EntityTestThread extends Thread{

	protected EntityService entityService;

	public EntityTestThread(EntityService entityService) {
		this.entityService = entityService;
	}

	public void run() {
		for (int i = 0; i <= 10; i++) {
			entityService.nextDbidBlock("TBL_" + i);
		}
	}

}
