package sysSrc.common.sysDictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sysSrc.common.Dictionary;
import sysSrc.common.SysBaseCom;
import sysSrc.framework.SysBaseDTOMap;

import baseSrc.common.BaseUtility;
import baseSrc.common.LogHelper;

import baseSrc.framework.BaseDAO;

public class SysDictionaryAddDAO extends BaseDAO {
	protected static LogHelper log = new LogHelper(SysDictionaryAddDAO.class);
	private static final String FLAG_ADD = "add";
	private static final String FLAG_UPDATE = "up";
	private static final String STRING_ZERO = "0";

	public SysBaseDTOMap runPageLoad(SysDictionaryAddFrom form,
			SysBaseCom baseCom) {

		SysBaseDTOMap ret = new SysBaseDTOMap();
		if (FLAG_UPDATE.equals(form.getFlag())) {
			List<?> list = getDataFormDB(form, baseCom);
			// 根据取得数值设置画面元素值
			if (!BaseUtility.isListNull(list)) {
				Object[] obj = (Object[]) list.get(0);
				form.setF_BigType(obj[0] + "");
				form.setF_SmallType(obj[1] + "");
				form.setF_Const(obj[2] + "");
				form.setF_IsLock(obj[3] + "");
				form.setF_Remark(obj[4] + "");
			}

			// 把设置好的值存放到画面对应FORMBEAN中
		}
		setDto(ret);
		ret.setForwardId("localGo");

		return ret;
	}

	public SysBaseDTOMap runPageSave(SysDictionaryAddFrom form,
			SysBaseCom baseCom) {

		SysBaseDTOMap ret = new SysBaseDTOMap();
		setDto(ret);
		try {
			// 取得画面中定义的数据元素
			// 大类型
			String bigType = form.getF_BigType();
			// 小类型
			String smallType = form.getF_SmallType();
			// 是否锁定
			String isLock = form.getF_IsLock();
			// 常数
			String con = form.getF_Const();
			// 备注
			String remark = form.getF_Remark();
			// flag
			String flag = form.getFlag();
			// 创建SQL条件参数对象
			Map<String, Object> params = new HashMap<String, Object>();
			// 创建SQL语句
			StringBuffer sql = new StringBuffer();
			if (!checkInput(ret, bigType, smallType, con)) {
				ret.setForwardId("localGo");
				return ret;
			}
			// 判断是新增数据还是修改数据
			if (this.FLAG_UPDATE.equals(flag)) {
				// 修改数据
				// 定义检索条件
				StringBuffer whereHql = new StringBuffer();
				whereHql.append("AND F_BIGTYPE =:bigType ");
				params.put("bigType", bigType);
				whereHql.append("AND F_SMALLTYPE =:smallType ");
				params.put("smallType", smallType);
				sql.append("update ");
				sql.append("t_sysdictionary ");
				sql.append("set f_Const = :const  ");
				params.put("const", con);
				sql.append(", f_remark = :remark  ");
				params.put("remark", remark);
				sql.append("where 1=1 ");
				sql.append(whereHql);
				this.dbAccess.exeSQL(sql.toString(), params);
			} else if (this.FLAG_ADD.equals(flag)) {
				// 新增数据
				// 创建检索条件参数对象
				params = new HashMap<String, Object>();
				// 创建SQL语句
				sql = new StringBuffer();

				// 定义检索条件
				StringBuffer whereHql = new StringBuffer();
				whereHql.append("AND F_BIGTYPE =:bigType ");
				params.put("bigType", form.getF_BigType());
				whereHql.append("AND F_SMALLTYPE =:smallType ");
				params.put("smallType", form.getF_SmallType());
				// 编写SQL语句
				sql.append("select ");
				sql.append("f_BigType, ");
				sql.append("f_SmallType ");
				sql.append("from ");
				sql.append("t_sysdictionary ");
				sql.append("where 1=1 ");
				sql.append(whereHql);
				List<?> listCount = dbAccess.findSQL(sql.toString(), params);
				if (this.STRING_ZERO.equals(listCount.size() + "")) {
					// 判断如果取得的为 "0",则数据库没有该条数据可以新增
					sql = new StringBuffer();
					params = new HashMap<String, Object>();
					sql.append("insert into ");
					sql.append("t_sysdictionary( ");
					sql.append("F_BIGTYPE, ");
					sql.append("F_SMALLTYPE, ");
					sql.append("F_ISLOCK, ");
					sql.append("F_Const, ");
					sql.append("F_remark ");
					sql.append(")values ( ");
					sql.append(":bigType, ");
					params.put("bigType", bigType);
					sql.append(":smallType, ");
					params.put("smallType", smallType);
					sql.append(":isLock, ");
					params.put("isLock", "0");
					sql.append(":const, ");
					params.put("const", con);
					sql.append(":remark )");
					params.put("remark", remark);
					this.dbAccess.exeSQL(sql.toString(), params);
				} else {
					// 如果新增的数据是否已存在数据库中，报错。错误消息ID：exeSQL.inset.fkerror
					ret.setMsgId("exeSQL.inset.fkerror");
					ret.addErrObjId("f_BigType$0");
					ret.addErrObjId("f_SmallType$0");
					ret.setForwardId("localGo");
					return ret;
				}
			}
			Dictionary.clear();
			form.setFlag("close");
			ret.setMsgId("exeSQL.ok");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setMsgId("exeSQL.error");
		}
		ret.setForwardId("localGo");
		return ret;
	}

	/**
	 * check是否通过
	 * 
	 * @param bigType
	 *            大分类
	 * @param smallType
	 *            小分类
	 * @param con
	 *            常数
	 * @return true:通过;false:不通过
	 */
	private boolean checkInput(SysBaseDTOMap dtoMap, String bigType,
			String smallType, String con) {
		boolean ret = true;
		String errors = "";
		if (BaseUtility.isStringNull(bigType)) {
			dtoMap.setMsgId("sysDictionary.check.error");
			dtoMap.addErrObjId("f_BigType$0");
			errors += "大分类、";
			ret = false;
		}
		if (BaseUtility.isStringNull(smallType)) {
			dtoMap.setMsgId("sysDictionary.check.error");
			dtoMap.addErrObjId("f_SmallType$0");
			errors += "小分类、";
			ret = false;
		}
		if (BaseUtility.isStringNull(con)) {
			dtoMap.setMsgId("sysDictionary.check.error");
			dtoMap.addErrObjId("f_Const$0");
			errors += "常数、";
			ret = false;
		}

		if (!BaseUtility.isStringNull(errors)) {
			errors = errors.substring(0, errors.length() - 1);
			dtoMap.addMsgArg(errors);
		}
		return ret;
	}

	private void setDto(SysBaseDTOMap dtoMap) {
		String value = Dictionary.getSysDictionaryCons(dbAccess, "1", "1");
		Map<String, String> map = Dictionary.getSysDictionaryMap(dbAccess, "1");
		List<?> list = Dictionary.getSysDictionaryList(dbAccess, "1");
	}

	private List<?> getDataFormDB(SysDictionaryAddFrom form, SysBaseCom baseCom) {

		// 创建检索条件参数对象
		Map<String, Object> params = new HashMap<String, Object>();
		// 创建SQL语句
		StringBuffer sql = new StringBuffer();

		// 定义检索条件
		StringBuffer whereHql = new StringBuffer();
		whereHql.append("AND F_BIGTYPE =:bigType ");
		params.put("bigType", form.getF_BigType());
		whereHql.append("AND F_SMALLTYPE =:smallType ");
		params.put("smallType", form.getF_SmallType());
		// 编写SQL语句
		sql.append("select ");
		sql.append("f_BigType, ");
		sql.append("f_SmallType, ");
		sql.append("f_Const, ");
		sql.append("f_IsLock, ");
		sql.append("f_remark ");
		sql.append("from ");
		sql.append("t_sysdictionary ");
		sql.append("where 1=1 ");
		sql.append(whereHql);

		// 取得每一页的数据对象
		List<?> list = this.dbAccess.findSQL(sql.toString(), params);
		return list;
	}
}
