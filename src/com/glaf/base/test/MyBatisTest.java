package com.glaf.base.test;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.glaf.base.entity.EntityService;

public class MyBatisTest extends AbstractTest {

	protected EntityService entityService;

	@SuppressWarnings("rawtypes")
	@Test
	public void testList() {
		entityService = super.getBean("myBatis3EntityProxy");
		for (int i = 0; i <= 1000; i++) {
			List<Object> todoList = entityService.getList("getTodoList",
					new HashMap());
			System.out.println(todoList);
		}
	}

}
