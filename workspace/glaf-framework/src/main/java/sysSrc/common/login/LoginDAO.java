package sysSrc.common.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import baseSrc.common.BaseCom;
import baseSrc.common.BaseUtility;
 


import sysSrc.common.SysBaseCom;
import sysSrc.framework.SysBaseDTOMap;
import sysSrc.orm.TPrivilege;
import sysSrc.orm.TRolePrivilege;
import sysSrc.orm.TUsers;
import sysSrc.orm.TUsersRole;

public class LoginDAO extends WFBaseDAO {

	public SysBaseDTOMap runPageLoad(LoginForm form, SysBaseCom baseCom) {

		SysBaseDTOMap ret = new SysBaseDTOMap();

		ret.setForwardId("loginGo");

		return ret;
	}
	public SysBaseDTOMap runPageLoad(LoginForm form, BaseCom baseCom) {

		SysBaseDTOMap ret = new SysBaseDTOMap();

		ret.setForwardId("loginGo");

		return ret;
	}
	public SysBaseDTOMap runLogin(LoginForm form, SysBaseCom sysbaseCom) {
		BaseCom baseCom = new BaseCom();
		baseCom.setUserPrivilageMap(sysbaseCom.getUserPrivilageMap());
		baseCom.setUserId(sysbaseCom.getUserId());
		baseCom.setUserName(sysbaseCom.getUserName());
		baseCom.setIp(sysbaseCom.getIp());
		baseCom.setEmail(sysbaseCom.getEmail());
		baseCom.setAllPrivilege(sysbaseCom.getAllPrivilege());
		baseCom.setAllPrivilages(sysbaseCom.getAllPrivilages());
		baseCom.setUserPrivilege(sysbaseCom.getRolePrivilege());
		baseCom.setFlg(true);
		return runLogin(form,baseCom);
	}

	public SysBaseDTOMap runLogin(LoginForm form, BaseCom baseCom) {

		SysBaseDTOMap ret = new SysBaseDTOMap();

		if (BaseUtility.isStringNull(form.getUserId())) {
			ret.setForwardId("loginGo");
		} else {
			// TBpUsers tbpUser = getUserInfo(form, ret);
			TUsers tUser = getUserInfo(form, ret);
			// 判断用户是否存在
			if (null != tUser) {
				// 判断密码是否正确
				if (tUser.getFPassword().equals(form.getPassword())) {
					// 判断是否启用 1:启动状态
					if ("1".equals(tUser.getFStatus())) {
						logger.info("tUser.getId():" + tUser.getFUserid());
						HashMap<String, List> rolePrivilageMap = getRolePrivilageMap(form
								.getUserId());
						SysBaseCom sysBaseCom = new SysBaseCom();
						sysBaseCom.setUserPrivilageMap(rolePrivilageMap);

						sysBaseCom.setUserId(tUser.getFUserid());
						// baseCom.setEmail(tbpUser.getFEmail());
						sysBaseCom.setUserName(tUser.getFName());
						sysBaseCom.setIp(form.getIp());
						sysBaseCom.setEmail(tUser.getFEmail());
						// baseCom.setDeptName(tbpUser.getFHrdeptname());

						//baseCom.setRoleCode(tUser.getFRolecode());
						//baseCom.setRoleName(tUser.getFRolename());
						// 获取所有权限并放入到session中
						sysBaseCom.setAllPrivilege(getAllPrivilageList(null));
						sysBaseCom.setAllPrivilages(getAllPrivilageIds(sysBaseCom
								.getAllPrivilege()));
						// 获取当前登录者拥有的所有权限，并放入session中
						sysBaseCom.setUserPrivilege(getAllPrivilageList(tUser
								.getFUserid()));

						ret.setForwardId("indexGo");
						sysBaseCom.setFlg(true);
						
						

						//测试用
						sysBaseCom.setSysTestComParm("tt");
						ret.setBeanCom(sysBaseCom);
					} else {
						ret.setForwardId("loginGo");
						ret.setMsgId("funLoginfrm.unuse");
					}
				} else {// 密码不正确
					ret.setForwardId("loginGo");
					ret.setMsgId("funLoginfrm.errorPassword");
					ret.addErrObjId("password$0");
				}
			} else {
				ret.setForwardId("loginGo");
				ret.setMsgId("lgFrm.noUser");
				ret.addMsgArg(form.getUserId());
				ret.addErrObjId("userId$0");
			}
		}

		return ret;
	}

	private TUsers getUserInfo(LoginForm form, SysBaseDTOMap dtoMap) {
		TUsers ret = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", form.getUserId());

		List l = this.dbAccess.find("sysSrc.orm.TUsers",
				"where FUserid=:userid", params);
		if (null != l && 0 != l.size()) {
			ret = (TUsers) l.get(0);
		}
		return ret;
	}

	/**
	 * 获取用户权限
	 * 
	 * @param userid
	 *            用户ID
	 * @returngetUserPrivilageMap
	 */
	private HashMap<String, List> getRolePrivilageMap(String userid) {
		HashMap<String, List> rolePrivilageMap = new HashMap<String, List>();
		HashMap<String, String> privilageMap = new HashMap<String, String>();
		Integer iCount = 0;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);

		// 获取用户角色
		List<TUsersRole> usersRoleTypeList = (List<TUsersRole>) this.dbAccess
				.find(
						"sysSrc.orm.TUsersRole A",
						" WHERE A.id.TUsers.FUserid=:userid order by A.id.TRole.FRoleid ",
						params);

		// 获取角色权限
		for (TUsersRole role : usersRoleTypeList) {

			Map<String, Object> paramsP = new HashMap<String, Object>();
			paramsP.put("roleid", role.getId().getTRole().getFRoleid());

			List<TRolePrivilege> rolePrivilegeTypeList = (List<TRolePrivilege>) this.dbAccess
					.find(
							"sysSrc.orm.TRolePrivilege A",
							" WHERE A.id.TRole.FRoleid=:roleid order by A.id.TPrivilege.FPrivilegeid ",
							paramsP);

			for (TRolePrivilege privilege : rolePrivilegeTypeList) {
				String s;
				String ss;
				s = privilege.getId().getTPrivilege().getFPrivilegetype()
						.toUpperCase();
				ss = privilege.getId().getTPrivilege().getFPrivilegeid()
						.toLowerCase();
				if (privilageMap.containsValue(ss)) {
					continue;
				} else {
					privilageMap.put(iCount.toString(), ss);
					iCount++;
				}
				if (rolePrivilageMap.containsKey(s)) {
					List l = rolePrivilageMap.get(privilege.getId()
							.getTPrivilege().getFPrivilegetype().toUpperCase());
					l.add(privilege.getId().getTPrivilege().getFPrivilegeid()
							.toUpperCase()
							+ ","
							+ privilege.getId().getTPrivilege()
									.getFPrivilegename()
							+ ","
							+ privilege.getId().getTPrivilege().getFUrl());
					rolePrivilageMap.put(privilege.getId().getTPrivilege()
							.getFPrivilegetype().toUpperCase(), l);
				} else {
					ArrayList list = new ArrayList();
					list.add(privilege.getId().getTPrivilege()
							.getFPrivilegeid().toUpperCase()
							+ ","
							+ privilege.getId().getTPrivilege()
									.getFPrivilegename()
							+ ","
							+ privilege.getId().getTPrivilege().getFUrl());
					rolePrivilageMap.put(privilege.getId().getTPrivilege()
							.getFPrivilegetype().toUpperCase(), list);
				}
			}
		}
		return rolePrivilageMap;
	}

	private List<String> getAllPrivilageIds(
			List<TPrivilege> allPrivilageTypeList) {
		List<String> allPrivilageMap = new ArrayList<String>();

		for (TPrivilege privilege : allPrivilageTypeList) {
			allPrivilageMap.add(privilege.getFPrivilegeid().toUpperCase());
		}
		return allPrivilageMap;
	}

	/**
	 * 获取所有权限
	 * 
	 * @param userid
	 *            用户ID
	 * @return
	 */
	private List<TPrivilege> getAllPrivilageList(String userid) {

		Map<String, Object> params = new HashMap<String, Object>();

		Integer iCount = 0;
		HashMap<String, String> privilageMap = new HashMap<String, String>();

		List<TPrivilege> allPrivilageTypeList = new ArrayList();
		if (null != userid && !"".equals(userid)) {
			params.put("userid", userid);

			List<TUsersRole> usersRoleTypeList = (List<TUsersRole>) this.dbAccess
			.find(
					"sysSrc.orm.TUsersRole A",
					" WHERE A.id.TUsers.FUserid=:userid order by A.id.TRole.FRoleid ",
					params);
			
			for (TUsersRole role : usersRoleTypeList) {
				Map<String, Object> paramsP = new HashMap<String, Object>();
				paramsP.put("roleid", role.getId().getTRole().getFRoleid());

				List<TRolePrivilege> temp = (List<TRolePrivilege>) this.dbAccess
						.find("sysSrc.orm.TRolePrivilege A",
								" where A.id.TRole.FRoleid=:roleid ", paramsP);
				if (null != temp && 0 < temp.size()) {
					for (int i = 0; i < temp.size(); i++) {
						String ss;
						ss = temp.get(i).getId().getTPrivilege()
								.getFPrivilegeid().toLowerCase();
						if (privilageMap.containsValue(ss)) {
							continue;
						} else {
							privilageMap.put(iCount.toString(), ss);
							iCount++;
						}
						allPrivilageTypeList.add(temp.get(i).getId()
								.getTPrivilege());
					}
				}
			}
		} else {
			allPrivilageTypeList = (List<TPrivilege>) this.dbAccess.find(
					"sysSrc.orm.TPrivilege A",
					" WHERE 1=1 order by A.FPrivilegeid ", params);
		}

		return allPrivilageTypeList;

	}

}
