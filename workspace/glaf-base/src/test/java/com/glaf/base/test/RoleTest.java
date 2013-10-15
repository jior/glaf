package com.glaf.base.test;

import java.util.List;

import org.junit.Test;

import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.security.BaseIdentityFactory;

public class RoleTest extends AbstractTest {
	
	protected SysRoleService sysRoleService;
	
	@Test
	public void testUserRoles(){
		sysRoleService = getBean("sysRoleService");
		List<String> codes = BaseIdentityFactory.getUserRoles("root");
		logger.debug("codes:"+codes);
	}

	public SysRoleService getSysRoleService() {
		return sysRoleService;
	}

	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}
	

}
