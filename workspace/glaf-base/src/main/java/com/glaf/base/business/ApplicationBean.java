package com.glaf.base.business;

import org.json.JSONArray;

import com.glaf.base.context.ContextFactory;
import com.glaf.base.modules.sys.service.SysApplicationService;

public class ApplicationBean {

	protected SysApplicationService sysApplicationService;

	public ApplicationBean() {
		sysApplicationService = ContextFactory.getBean("sysApplicationProxy");
	}

	/**
	 * 获取用户菜单之Json对象
	 * 
	 * @param parent
	 *            父节点编号
	 * @param userId
	 *            用户登录账号
	 * @return
	 */
	public JSONArray getUserMenu(long parent, String userId) {
		return sysApplicationService.getUserMenu(parent, userId);
	}

	public void setSysApplicationService(
			SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
	}

}
