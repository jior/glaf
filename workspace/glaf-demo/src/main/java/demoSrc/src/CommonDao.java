package demoSrc.src;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import baseSrc.common.DbAccess;

public class CommonDao {
	private DbAccess dbAccess;

	public CommonDao(DbAccess dbAccess) {
		this.dbAccess = dbAccess;
	}
	
	/**
	 * 根据大分类、小分类、code值从常数表检索value值
	 * @param fType 大分类
	 * @param fSubType 小分类
	 * @param fCode code值
	 * @return fValue 值
	 */
	//public String getTBpConstantValueByCode(String fType,String fSubType,String fCode){
	public String getTBpConstantValueByCode(){
		String fValue = null;
		
		Map<String, Object> params = new HashMap<String, Object>();		
		
		//定义检索条件
		String sql = "select COMPANY,PRICE from t_arraygrid	where COMPANY = 'tr1'";
		
		List<?> list = this.dbAccess.findSQL(sql,params);
		
		if (list != null && 0<list.size()){
			Object[] tabObj = (Object[])list.get(0);
			fValue = tabObj[0].toString();
		}
		
		return fValue;
	}
	

	public DbAccess getDbAccess() {
		return dbAccess;
	}

	public void setDbAccess(DbAccess dbAccess) {
		this.dbAccess = dbAccess;
	}
	
}
