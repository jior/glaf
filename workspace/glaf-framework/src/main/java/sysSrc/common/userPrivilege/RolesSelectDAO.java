package sysSrc.common.userPrivilege;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sysSrc.common.SysBaseCom;
import sysSrc.framework.SysBaseDTOMap;
import sysSrc.orm.TRole;
import sysSrc.orm.TUsers;
import sysSrc.orm.TUsersRole;
import sysSrc.orm.TUsersRoleId;

import baseSrc.common.AutoArrayList;
import baseSrc.common.BaseUtility;
import baseSrc.common.LogHelper;
import baseSrc.framework.BaseDAO;
import baseSrc.framework.BusException;

public class RolesSelectDAO extends BaseDAO {
	protected static LogHelper log = new LogHelper(RolesSelectDAO.class);

	public SysBaseDTOMap runPageLoad(RolesSelectFrom form, SysBaseCom baseCom) {
		// 默认初始化函数
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();
		AutoArrayList T = getDataFormDB(form, baseCom);
		dtoMap.setDetailList(T);
		dtoMap.setForwardId("indexGo");

		return dtoMap;
	}

	private AutoArrayList getDataFormDB(RolesSelectFrom form, SysBaseCom baseCom) {
		AutoArrayList detailList = new AutoArrayList(RolesSelectDetail.class);
		Map<String, Object> params = new HashMap<String, Object>();

		// 获取角色
		List<TRole> T = (List<TRole>) this.dbAccess.find("sysSrc.orm.TRole A",
				" WHERE 1=1 ", params);

		if (T.size() > 0) {
			for (int i = 0; i < T.size(); i++) {
				RolesSelectDetail agDTO = new RolesSelectDetail();
				agDTO.setP_roleId(T.get(i).getFRoleid().toString());
				agDTO.setP_roleName(T.get(i).getFRolename());
				detailList.add(agDTO);
			}
		}

		return detailList;
	}

	public SysBaseDTOMap runSave(RolesSelectFrom form, SysBaseCom baseCom) {

		// 创建控制接口对象
		SysBaseDTOMap dtoMap = new SysBaseDTOMap();
		List<?> l = form.getGridList(RolesSelectDetail.class);
		String userId = form.getUserId();

		Calendar cal = Calendar.getInstance();
		try {
			if (!BaseUtility.isStringNull(userId)) {
				// 更新用户角色信息（未考虑并行情况,创建和更新时间判读未处理）
				// 删除该用户对应的所有权限
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userid", userId);
				String str = " A where 1=1 and A.id.TUsers.FUserid=:userid ";
				this.dbAccess.delete("sysSrc.orm.TUsersRole", str, params);
				
				// 新增该用户对应的权限。
				for (int i = 0; i < l.size(); i++) {
					RolesSelectDetail ced = (RolesSelectDetail) l.get(i);
					TUsersRole tcr = new TUsersRole();
					TUsersRoleId tcrid = new TUsersRoleId();
					TUsers tu = new TUsers();
					TRole tr = new TRole();
					tu.setFUserid(userId);
					tr.setFRoleid(Long.parseLong(ced.getP_roleId()));
					tcrid.setTRole(tr);
					tcrid.setTUsers(tu);
					tcr.setId(tcrid);
					tcr.setFCreatdate(cal.getTime());
					tcr.setFCreatuserid(baseCom.getUserId());
					tcr.setFUpdatedate(cal.getTime());
					tcr.setFUpdateuserid(baseCom.getUserId());
					this.dbAccess.saveOrUpdate(tcr);
				}
			}
			// 保存成功，则提示操作成功
			form.setExtCBFlag("YES");
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
		return dtoMap;
	}
}
