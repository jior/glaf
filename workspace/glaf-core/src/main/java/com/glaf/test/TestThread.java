package com.glaf.test;

import com.glaf.core.service.EntityService;

public class TestThread extends Thread{

	protected EntityService entityService;

	public TestThread(EntityService entityService) {
		this.entityService = entityService;
	}

	public void run() {
		for (int i = 0; i <= 10; i++) {
			entityService.nextDbidBlock("TBL_" + i);
		}
	}

}
