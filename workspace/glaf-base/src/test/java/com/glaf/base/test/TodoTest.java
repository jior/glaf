package com.glaf.base.test;

import org.junit.Test;

import com.glaf.base.modules.todo.service.TodoService;

public class TodoTest extends AbstractTest {
	
	protected TodoService todoService;
	
	@Test
	public void testUserEntityList() {
		todoService = super.getBean("todoService");
		logger.info("user list:" +todoService.getUserEntityList("joy"));
	}

}
