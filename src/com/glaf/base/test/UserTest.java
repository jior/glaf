package com.glaf.base.test;

import org.junit.Test;

import com.glaf.base.modules.sys.service.SysUserService;

public class UserTest extends AbstractTest {

	protected SysUserService sysUserService;

	@Test
	public void testList() {
		sysUserService = super.getBean("sysUserService");
		logger.info("user list:"+sysUserService.getSysUserList());
	}

}
