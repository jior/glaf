package sysSrc.common.userPrivilege;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sysSrc.common.SysBaseCom;
import sysSrc.framework.SysBaseDTOMap;
import sysSrc.orm.TUsers;
import sysSrc.orm.TUsersRole;

import net.sf.json.JSONArray;


import baseSrc.common.AutoArrayList;
import baseSrc.common.BaseUtility;
import baseSrc.common.LogHelper;
import baseSrc.framework.BaseConstants;
import baseSrc.framework.BaseDAO;
import baseSrc.framework.BusException;
import baseSrc.framework.SelectCommonDTO;

public class UserManageDAO extends BaseDAO {
	protected static LogHelper log = new LogHelper(UserManageDAO.class);

	public SysBaseDTOMap runPageLoad(UserManageFrom form, SysBaseCom baseCom) {
		// 默认初始化函数

		SysBaseDTOMap dtoMap = new SysBaseDTOMap();
		//AutoArrayList T = getDataFormDB(form, baseCom);

		//dtoMap.setDetailList(T);
		dtoMap.setForwardId("indexGo");

		setLoadValue(dtoMap);
		
		return dtoMap;
	}
	
	public void setLoadValue(SysBaseDTOMap dtoMap){
		String[] values = {"0","1"};
		String[] labels = {"启用","禁用"};
		SelectCommonDTO sc = new SelectCommonDTO();
		sc.setLabels(labels);
		sc.setValues(values);
		dtoMap.setLightDTO(sc);
	}

	public SysBaseDTOMap runPageCtrl(UserManageFrom form, SysBaseCom baseCom) {
		// 默认初始化函数

		SysBaseDTOMap dtoMap = new SysBaseDTOMap();

		AutoArrayList T = getDataFormDB(form, baseCom);

		dtoMap.setDetailList(T);
		dtoMap.setForwardId("indexGo");
		form.setGridData(JSONArray.fromObject(T).toString());
		setLoadValue(dtoMap);
		return dtoMap;
	}

	public SysBaseDTOMap runSave(UserManageFrom form, SysBaseCom baseCom) {

		// 创建控制接口对象
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();
		List<?> l = form.getGridList(UserManageDetail.class);

		try {
			for (int i = 0; i < l.size(); i++) {
				UserManageDetail ced = (UserManageDetail) l.get(i);
				// 更新用户信息
				Calendar cal = Calendar.getInstance();

				TUsers tce = new TUsers();
				if (!BaseUtility.isStringNull(ced.getP_flag())) {
					tce.setFCreatdate(ced.getP_creatdate());
					tce.setFCreatuserid(ced.getP_creatuserid());
					tce.setFUpdatedate(cal.getTime());
					tce.setFUpdateuserid(baseCom.getUserId());
				} else {
					tce.setFCreatdate(cal.getTime());
					tce.setFCreatuserid(baseCom.getUserId());
					tce.setFUpdatedate(cal.getTime());
					tce.setFUpdateuserid(baseCom.getUserId());
				}

				tce.setFEmail(ced.getP_Email());
				tce.setFName(ced.getP_Name());
				tce.setFPassword(ced.getP_Password());
				String st = "启用".equals(ced.getP_Status()) ? "1" : "0";
				tce.setFStatus(st);
				tce.setFUserid(ced.getP_userID());
				this.dbAccess.saveOrUpdate(tce);

			}
			// 保存成功，则提示操作成功
			dtoMap.setMsgId("info.saveOK");
			dtoMap.setForwardId("indexGo");
		} catch (Exception e) {
			// 保存异常时，提示异常消息
			dtoMap.setMsgId("info.saveNG");
			dtoMap.setForwardId("indexGo");
			log.error(baseCom, e.getMessage());
			// 抛出异常：BusException
			throw new BusException(dtoMap);
		}
		setLoadValue(dtoMap);
		return dtoMap;
	}

	public SysBaseDTOMap runDelete(UserManageFrom form, SysBaseCom baseCom) {

		// 创建控制接口对象
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();
		List<?> l = form.getGridList(UserManageDetail.class);

		try {
			for (int i = 0; i < l.size(); i++) {
				UserManageDetail ced = (UserManageDetail) l.get(i);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userid", ced.getP_userID());
				// 删除该用户对应的所有权限

				String str1 = " A where 1=1 and A.id.TUsers.FUserid = :userid ";
				this.dbAccess.delete("sysSrc.orm.TUsersRole", str1, params);

				String str = " where 1=1 and FUserid = :userid ";
				this.dbAccess.delete("sysSrc.orm.TUsers", str, params);
			}
			// 保存成功，则提示操作成功
			dtoMap.setMsgId("info.saveOK");
			dtoMap.setForwardId("indexGo");
		} catch (Exception e) {
			// 保存异常时，提示异常消息
			dtoMap.setMsgId("info.saveNG");
			dtoMap.setForwardId("indexGo");
			log.error(baseCom, e.getMessage());
			// 抛出异常：BusException
			throw new BusException(dtoMap);
		}
		setLoadValue(dtoMap);
		return dtoMap;
	}

	private AutoArrayList getDataFormDB(UserManageFrom form, SysBaseCom baseCom) {
		AutoArrayList detailList = new AutoArrayList(UserManageDetail.class);
		// 定义检索表名

		String tabName = "sysSrc.orm.TUsers";
		// 创建检索条件参数对象

		Map<String, Object> params = new HashMap<String, Object>();

		// 定义检索条件

		StringBuffer whereHql = new StringBuffer(" WHERE ");
		if (!BaseUtility.isStringNull(form.getQueryUserId())) {
			whereHql.append("FUserid like :userid AND");
			params.put("userid", ("%" + form.getQueryUserId() + "%"));
		}
		if (!BaseUtility.isStringNull(form.getQueryUseName())) {
			whereHql.append("FName like :userName AND");
			params.put("userName", ("%" + form.getQueryUseName()+ "%"));
		}
		whereHql.append(" 1=1");

		// 取得当前检索条件的数据总条数

		int count = this.dbAccess.getResutlsTotal(tabName, whereHql.toString(),
				params);

		// 取得每一页的显示条数
		int pageSize = BaseConstants.ISC_PAGE_SIZE_FIVE;

		// 取得每一页的数据对象
		List<?> T = this.dbAccess.getResutlsForPage(tabName, whereHql
				.toString(), params, form, count, pageSize);

		List<TUsers> usersTypeList = (List<TUsers>) T;

		if (usersTypeList.size() > 0) {
			for (int i = 0; i < usersTypeList.size(); i++) {
				UserManageDetail agDTO = new UserManageDetail();
				//agDTO.setP_HRdeptName(usersTypeList.get(i).getFHrdeptname());
				//agDTO.setP_leaderEmail(usersTypeList.get(i).getFLeaderemail());
				agDTO.setP_Name(usersTypeList.get(i).getFName());
				agDTO.setP_Password(usersTypeList.get(i).getFPassword());
				String st = "1".equals(usersTypeList.get(i).getFStatus()) ? "启用"
						: "禁用";
				agDTO.setP_Status(st);
				agDTO.setP_userID(usersTypeList.get(i).getFUserid());
				agDTO.setP_creatdate(usersTypeList.get(i).getFCreatdate());
				agDTO.setP_creatuserid(usersTypeList.get(i).getFCreatuserid());
				agDTO.setP_updatedate(usersTypeList.get(i).getFUpdatedate());
				agDTO
						.setP_updateuserid(usersTypeList.get(i)
								.getFUpdateuserid());
				//agDTO.setP_rolecode(usersTypeList.get(i).getFRolecode());
				//agDTO.setP_rolesName(usersTypeList.get(i).getFRolename());
				agDTO.setP_flag(usersTypeList.get(i).getFUserid());
				agDTO.setP_Email(usersTypeList.get(i).getFEmail());

				// 获取用户角色
				String roleIds = "";
				String roleNames = "";
				Map<String, Object> params1 = new HashMap<String, Object>();
				params1.put("userid", usersTypeList.get(i).getFUserid());

				// 获取用户角色
				List<TUsersRole> usersRoleTypeList = (List<TUsersRole>) this.dbAccess
						.find(
								"sysSrc.orm.TUsersRole A",
								" WHERE A.id.TUsers.FUserid=:userid order by A.id.TRole.FRoleid ",
								params1);

				// 拼接角色信息
				for (TUsersRole role : usersRoleTypeList) {
					roleIds = roleIds
							+ role.getId().getTRole().getFRoleid().toString()
							+ ";";
					roleNames = roleNames
							+ role.getId().getTRole().getFRolename().toString()
							+ ";";
				}
				// 去掉最后一个";"
				if (!BaseUtility.isStringNull(roleIds)) {
					roleIds = roleIds.substring(0, roleIds.length() - 1);
					roleNames = roleNames.substring(0, roleNames.length() - 1);
				}
				agDTO.setP_rolesId(roleIds);
				agDTO.setP_rolesName(roleNames);
				detailList.add(agDTO);
			}
		}

		return detailList;
	}
}
