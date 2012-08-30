package com.glaf.base.test;

import java.io.IOException;

import org.jpage.util.FileTools;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.SysUserService;

public class UserTest extends AbstractTest {

	protected SysUserService sysUserService;

	protected SysApplicationService sysApplicationService;

	@Test
	public void testList() {
		sysUserService = super.getBean("sysUserProxy");
		logger.info("user list:" + sysUserService.getSysUserList());
	}

	@Test
	public void testUserMenu() {
		sysApplicationService = super.getBean("sysApplicationProxy");
		JSONArray array = sysApplicationService.getUserMenu(3, "root");
		System.out.println("menus:\n" + array.toString('\n'));
		System.out.println();
		try {
			FileTools.save("menus.json", array.toString('\n').getBytes());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
