package sysSrc.common.sysDictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sysSrc.common.Dictionary;
import sysSrc.common.SysBaseCom;
import sysSrc.common.sysDictionary.SysDictionaryFrom;
import sysSrc.framework.SysBaseDTOMap;
import baseSrc.common.AutoArrayList;
import baseSrc.common.BaseUtility;
import baseSrc.framework.BaseConstants;
import baseSrc.framework.BaseDAO;

public class SysDictionaryDAO extends BaseDAO {
	
	public SysBaseDTOMap runPageLoad(SysDictionaryFrom form, SysBaseCom baseCom) {

		SysBaseDTOMap ret = new SysBaseDTOMap();

		ret.setForwardId("localGo");

		return ret; 
	}
	public SysBaseDTOMap runPageCtrl(SysDictionaryFrom form, SysBaseCom baseCom) {

		SysBaseDTOMap dtoMap = new SysBaseDTOMap();
		//取得每一页的数据对象
		List<?> list = getDataFormDB(form,baseCom);
		//定义画面中数据元素
				AutoArrayList detailList = new AutoArrayList(SysDictionaryDetail.class);
				form.setSysDictionaryDetail(detailList);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("sysDictionaryDetail", list);
				//根据取得数值设置画面元素值
				if(!BaseUtility.isListNull(list)){
					this.FillFormByHashMapWithKey(form, map);
				}
				
				//把设置好的值存放到画面对应FORMBEAN中
				dtoMap.setForwardId("localGo");

		return dtoMap;
	}
	public SysBaseDTOMap runPageSave(SysDictionaryFrom form, SysBaseCom baseCom) {

		SysBaseDTOMap ret = new SysBaseDTOMap();

		try{
			//取得画面中定义的数据元素
			List<?> list = form.getSysDictionaryDetail();
			for(int i=0;i<list.size();i++){
				SysDictionaryDetail bf = (SysDictionaryDetail) list.get(i);
				//大类型
				String bigType = bf.getFbigtype();
				//小类型
				String smallType = bf.getFsmalltype();
				//是否锁定
				String isLock = form.getFlag();
				
				//创建检索条件参数对象
				Map<String, Object> params = new HashMap<String, Object>();
				//创建SQL语句
				StringBuffer sql = new StringBuffer();
				
				//定义检索条件
				StringBuffer whereHql = new StringBuffer();
					whereHql.append("AND F_BIGTYPE =:bigType ");
					params.put("bigType", bigType);
					whereHql.append("AND F_SMALLTYPE =:smallType ");
					params.put("smallType", smallType);
					if(BaseConstants.ISC_CHECKBOX_CHECKED.equals(bf.getCheck())){
						sql.append("update ");
						sql.append("t_sysdictionary ");
						sql.append("set F_ISLOCK = :isLock  ");
						params.put("isLock", isLock);		
						sql.append("where 1=1 ");
						sql.append(whereHql);
						this.dbAccess.exeSQL(sql.toString(), params);
					}
			}
			ret.setMsgId("exeSQL.ok");
			list = getDataFormDB(form,baseCom);
			//定义画面中数据元素
			AutoArrayList detailList = new AutoArrayList(SysDictionaryDetail.class);
			form.setSysDictionaryDetail(detailList);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("sysDictionaryDetail", list);
			//根据取得数值设置画面元素值
			if(!BaseUtility.isListNull(list)){
				this.FillFormByHashMapWithKey(form, map);
			}
			
			Dictionary.clear();
		}catch (Exception e) {
			e.printStackTrace();
			ret.setMsgId("exeSQL.error");
		}
		
		ret.setForwardId("localGo");

		return ret;
	}
	public SysBaseDTOMap runPageDelete(SysDictionaryFrom form, SysBaseCom baseCom) {

		SysBaseDTOMap ret = new SysBaseDTOMap();

		try{
			//取得画面中定义的数据元素
			List<?> list = form.getSysDictionaryDetail();
			for(int i=0;i<list.size();i++){
				SysDictionaryDetail bf = (SysDictionaryDetail) list.get(i);
				if(BaseConstants.ISC_CHECKBOX_CHECKED.equals(bf.getCheck())){
					//大类型
					String bigType = bf.getFbigtype();
					//小类型
					String smallType = bf.getFsmalltype();
					
					//创建检索条件参数对象
					Map<String, Object> params = new HashMap<String, Object>();
					//创建SQL语句
					StringBuffer sql = new StringBuffer();
					
					//定义检索条件
					StringBuffer whereHql = new StringBuffer();
					whereHql.append("AND F_BIGTYPE =:bigType ");
					params.put("bigType", bigType);
					whereHql.append("AND F_SMALLTYPE =:smallType ");
					params.put("smallType", smallType);
						sql.append("delete from ");
						sql.append("t_sysdictionary ");
						sql.append("where 1=1 ");
						sql.append(whereHql);
						this.dbAccess.exeSQL(sql.toString(), params);
				}
			}
			runPageCtrl(form,baseCom);
			ret.setMsgId("exeSQL.ok");
			
			Dictionary.clear();
		}catch (Exception e) {
			e.printStackTrace();
			ret.setMsgId("exeSQL.error");
		}
		
		ret.setForwardId("localGo");

		return ret;
	}
	private List<?> getDataFormDB(SysDictionaryFrom form, SysBaseCom baseCom) {
		
		//创建检索条件参数对象
		Map<String, Object> params = new HashMap<String, Object>();
		//创建SQL语句
		StringBuffer sql = new StringBuffer();
		
		//定义检索条件
		StringBuffer whereHql = new StringBuffer();
		if(!BaseUtility.isStringNull(form.getF_BigType())){
			whereHql.append("AND F_BIGTYPE like :bigType ");
			params.put("bigType", "%" + form.getF_BigType() + "%");
		}
		if(!BaseUtility.isStringNull(form.getF_SmallType())){
			whereHql.append("AND F_SMALLTYPE like :smallType ");
			params.put("smallType", "%" + form.getF_SmallType() + "%");
		}
		if(!BaseUtility.isStringNull(form.getF_IsLock())){
			whereHql.append("AND F_ISLOCK =:isLock ");
			params.put("isLock", form.getF_IsLock());
		}
		if(!BaseUtility.isStringNull(form.getF_Const())){
			whereHql.append("AND f_Const like :con ");
			params.put("con", "%" + form.getF_Const() + "%");
		}
		if(!BaseUtility.isStringNull(form.getF_Comon())){
			whereHql.append("AND f_remark like :comon ");
			params.put("comon", "%" + form.getF_Comon() + "%");
		}
		//编写SQL语句
		sql.append("select ");
		sql.append("f_BigType as text, ");
		sql.append("f_BigType as FBigType, ");
		sql.append("f_SmallType as FSmallType, ");
		sql.append("f_Const as FConst, ");
		sql.append("CASE f_IsLock WHEN '1' THEN 'YES' ELSE 'NO' END as FIsLock, ");
		sql.append("f_remark as FReMark ");
		sql.append("from ");
		sql.append("t_sysdictionary ");
		sql.append("where 1=1 ");
		sql.append(whereHql);
		sql.append("order by f_bigtype,f_smalltype ");
		//取得当前检索条件的数据总条数
		
		int count = this.dbAccess.getResutlsTotalSQL(sql.toString(), params);
		
		//取得每一页的显示条数
		int pageSize = BaseConstants.ISC_PAGE_SIZE_FIVE;
		
		//取得每一页的数据对象
		List<?> list = this.dbAccess.getMapsForPageSQL(sql.toString(), params, form, count, pageSize);
//		List<?> list = this.dbAccess.find2Map(sql.toString(), params);
		return list;
	}
}
