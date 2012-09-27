package com.glaf.base.business;

import org.json.JSONArray;
import org.json.JSONObject;

import com.glaf.base.modules.sys.service.SysApplicationService;

public class ApplicationBean {

	protected SysApplicationService sysApplicationService;

	public ApplicationBean() {

	}

	/**
	 * 获取用户菜单之Javascript对象
	 * 
	 * @param parent
	 * @param userId
	 * @param contextPath
	 * @return
	 */
	public String getMenuScripts(long parent, String userId, String contextPath) {
		JSONArray jsonArray = sysApplicationService.getUserMenu(parent, userId);
		String sMenu = "";
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject rootJson = jsonArray.getJSONObject(i);
			sMenu = sMenu
					+ "<li class='menu'><ul><li class='button'><a href='#' class='red'>"
					+ rootJson.getString("name")
					+ "<span></span></a></li><li class='dropdown'><ul>";

			JSONArray children = rootJson.getJSONArray("children");

			for (int j = 0; children != null && j < children.length(); j++) {
				JSONObject childJson = children.getJSONObject(j);
				JSONArray items2 = null;
				if (childJson.has("children")) {
					items2 = childJson.getJSONArray("children");
				}

				if (items2 != null && items2.length() > 0) {
					sMenu = sMenu
							+ "</ul><ul class='father'><li class='button'><a style='width: 110px' name='lightli' onclick='changeClass(this)' href='#' target='mainFrame'>"
							+ childJson.getString("name")
							+ "</a></li><li class='dropdownFather'><ul>";

					for (int k = 0; k < items2.length(); k++) {
						JSONObject cd = items2.getJSONObject(k);
						sMenu = sMenu
								+ "<li class='highlight'><a name='lightli' onclick='changeClass(this)' href='"
								+ contextPath + "/" + cd.getString("url")
								+ "' target='mainFrame'>"
								+ cd.getString("name") + "</a></li>";
					}

					sMenu = sMenu + "</ul></li></ul><ul>";
				} else {
					sMenu = sMenu
							+ "<li class='highlight'><a name='lightli' onclick='changeClass(this)' href='"
							+ contextPath + "/" + childJson.getString("url")
							+ "' target='mainFrame'>"
							+ childJson.getString("name") + "</a></li>";
				}
			}
			sMenu = sMenu + " </ul></li></ul></li>";
		}
		return sMenu;
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
		JSONArray array = sysApplicationService.getUserMenu(parent, userId);
		return array;
	}

	public void setSysApplicationService(
			SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
	}

}
