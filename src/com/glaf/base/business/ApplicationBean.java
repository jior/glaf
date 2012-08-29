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
	 * ��ȡ�û��˵�֮Json����
	 * 
	 * @param parent
	 *            ���ڵ���
	 * @param userId
	 *            �û���¼�˺�
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
