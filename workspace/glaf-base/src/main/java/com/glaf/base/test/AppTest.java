package com.glaf.base.test;

import org.junit.Test;

import com.glaf.base.business.ApplicationBean;
import com.glaf.base.modules.sys.service.SysApplicationService;

public class AppTest extends AbstractTest {
	
	protected SysApplicationService sysApplicationService;

	@Test
	public void testUserMenu() {
		sysApplicationService = super.getBean("sysApplicationProxy");
		ApplicationBean bean = new ApplicationBean();
		bean.setSysApplicationService(sysApplicationService);
		System.out.println("sysApplicationService="+sysApplicationService);
		String scripts = bean.getMenuScripts(3, "root", "/glaf");
		System.out.println(scripts);
	}

}
