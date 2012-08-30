//================================================================================================
//项目名称 ：    基盘
//功    能 ：  系统字典查询

//文件名称 ：   Dictionary.java                                   
//描    述 ：    
//================================================================================================
//修改履历                                                                
//年 月 日		区分		所 属/担 当           		内 容									标识        
//----------   	----   	------------------- ---------------                          ------        
//2012/08/14   	编写   	Intasect/钟敏    	 新規作成                                                                            
//================================================================================================

package sysSrc.common;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import baseSrc.common.BaseUtility;
import baseSrc.common.DbAccess;

public class Dictionary {

	private Dictionary(){
	} 
	private static final String PGADMIN_NULL = "null";
	private static final String SYSDICISLOCK_LOCKED = "1";
	//字典常数临时存储Map
	private static Map<String, String> CONMAP = new HashMap<String, String>();
	//字典常数临时存储Map
	private static Map<String,List<String>> CONLISTS = new HashMap<String,List<String>>();
	//字典常数临时存储Map
	private static Map<String,Map<String,String>> CONMAPS = new HashMap<String,Map<String,String>>();
	/**
	 * 获得字典常数
	 * @param dbAccess
	 * @param bigType 大类别
	 * @param smallType 小类别
	 * @return 常数
	 */
	public static String getSysDictionaryCons(DbAccess dbAccess,String bigType,String smallType){
		//初始化返回常数
		String ret = "";
		//获取CONMAP 中的key
		String key = bigType + "_" + smallType;
			   key = key.toUpperCase();
		if(CONMAP.get(key)!=null){
			//如果已取过的数据就直接取出来
			ret = CONMAP.get(key).toString();
			if(PGADMIN_NULL.equals(ret)){
				ret = "";
			}
		}else{
			ret = getSysDictionaryConFromTB(dbAccess,bigType,smallType);
		}
		
		return ret;
	}
	
	/**
	 * 从数据库中取得字典常数
	 * @param dbAccess
	 * @param bigType 大类别
	 * @param smallType 小类别
	 * @return 常数
	 */
	private static String getSysDictionaryConFromTB(DbAccess dbAccess,
			String bigType, String smallType) {
		String ret = "";
		//获取CONMAP 中的key
		String key = bigType + "_" + smallType;
		   key = key.toUpperCase();
		//初始化SQL
		StringBuffer sql = new StringBuffer();
		//初始化SQL查询条件
		StringBuffer whereHSQL = new StringBuffer();
		//创建检索条件参数对象
		Map<String, Object> params = new HashMap<String, Object>();
			whereHSQL.append(" AND F_BIGTYPE = :BIGETYPE ");
			params.put("BIGETYPE", bigType);
			whereHSQL.append(" AND F_SMALLTYPE = :SMALLTYPE ");
			params.put("SMALLTYPE", smallType);
		
		sql.append("SELECT ");
		sql.append("F_CONST,F_SMALLTYPE ");
		sql.append("FROM ");
		sql.append("t_sysdictionary ");
		sql.append("WHERE F_ISLOCK <> :ISLOCK ");
		params.put("ISLOCK", SYSDICISLOCK_LOCKED);
		sql.append(whereHSQL);
		sql.append("ORDER BY F_CONST ");
		List<?> list = dbAccess.findSQL(sql.toString(), params);
		if(BaseUtility.isListNull(list)){
			ret = "";
			CONMAP.put(key, ret);
		}else{
			Object[] obj = (Object[]) list.get(0);
			ret = obj[0] + "";
			if(PGADMIN_NULL.equals(ret)){
				ret = "";
			}
			CONMAP.put(key, ret);
		}
		
		return ret;
	}

	/**
	 * 获取该大类的所有常数
	 * @param dbAccess
	 * @param bigType 大类别
	 * @return 常数List
	 */
	public static List<String> getSysDictionaryList(DbAccess dbAccess,String bigType){
		//初始化返回常数
		List<String> list = new ArrayList<String>();
		//获取CONLISTS 中的key
		String key = bigType;
			   key = key.toUpperCase();
			   if(CONLISTS.get(key)!=null){
					//如果已取过的数据就直接取出来
				    list = CONLISTS.get(key);
				}else{
					list = getSysDictionaryConListsFromTB(dbAccess,bigType);
				}		
		return list;
	}
	/**
	 * 从数据库中取得字典常数
	 * @param dbAccess
	 * @param bigType 大类别
	 * @return 常数List
	 */
	private static List<String> getSysDictionaryConListsFromTB(
			DbAccess dbAccess, String bigType) {
		List<String> list = new ArrayList<String>();
		//获取CONMAP 中的key
		String key = bigType;
		       key = key.toUpperCase();
		//初始化SQL
		StringBuffer sql = new StringBuffer();
		//初始化SQL查询条件
		StringBuffer whereHSQL = new StringBuffer();
		//创建检索条件参数对象
		Map<String, Object> params = new HashMap<String, Object>();
			whereHSQL.append(" AND F_BIGTYPE = :BIGETYPE ");
			params.put("BIGETYPE", bigType);
			
		sql.append("SELECT ");
		sql.append("F_CONST,F_SMALLTYPE ");
		sql.append("FROM ");
		sql.append("t_sysdictionary ");
		sql.append("WHERE F_ISLOCK <> :ISLOCK ");
		params.put("ISLOCK", SYSDICISLOCK_LOCKED);
		sql.append(whereHSQL);
		sql.append("ORDER BY F_CONST ");
		List<?> listGet = dbAccess.findSQL(sql.toString(), params);
		if(BaseUtility.isListNull(listGet)){
			CONLISTS.put(key, list);
		}else{
			for(int i=0;i<listGet.size();i++){
				Object[] obj = (Object[]) listGet.get(i);
				list.add(obj[0]+"");
			}
			CONLISTS.put(key, list);
		}
		
		return list;
	}
	
	/**
	 * 获取该大类的所有常数
	 * @param dbAccess
	 * @param bigType 大类别
	 * @return Map   key:小类别;value:常数
	 */
	public static Map<String,String> getSysDictionaryMap(DbAccess dbAccess,String bigType){
		//初始化返回常数
		Map<String,String> map = new HashMap<String, String>();
		
		//获取CONLISTS 中的key
			String key = bigType;
				   key = key.toUpperCase();
				   if(CONMAPS.get(key)!=null){
					 //如果已取过的数据就直接取出来
					   map = CONMAPS.get(key);
					}else{
						map = getSysDictionaryConMapsFromTB(dbAccess,bigType);
					}		
		return map;
	}
	/**
	 * 从数据库中取得字典常数
	 * @param dbAccess
	 * @param bigType 大类别
	 * @return 常数List
	 */
	private static Map<String,String> getSysDictionaryConMapsFromTB(
			DbAccess dbAccess, String bigType) {
		Map<String,String> map = new HashMap<String, String>();
		//获取CONMAP 中的key
		String key = bigType;
		       key = key.toUpperCase();
		//初始化SQL
		StringBuffer sql = new StringBuffer();
		//初始化SQL查询条件
		StringBuffer whereHSQL = new StringBuffer();
		//创建检索条件参数对象
		Map<String, Object> params = new HashMap<String, Object>();
			whereHSQL.append(" AND F_BIGTYPE = :BIGETYPE ");
			params.put("BIGETYPE", bigType);
			
		sql.append("SELECT ");
		sql.append("F_CONST,F_SMALLTYPE ");
		sql.append("FROM ");
		sql.append("t_sysdictionary ");
		sql.append("WHERE F_ISLOCK <> :ISLOCK ");
		params.put("ISLOCK", SYSDICISLOCK_LOCKED);
		sql.append(whereHSQL);
		sql.append("ORDER BY F_CONST ");
		List<?> listGet = dbAccess.findSQL(sql.toString(), params);
		if(BaseUtility.isListNull(listGet)){
			CONMAPS.put(key, map);
		}else{
			for(int i=0;i<listGet.size();i++){
				Object[] obj = (Object[]) listGet.get(i);
				map.put(obj[1]+"",obj[0]+"");
			}
			CONMAPS.put(key, map);
		}
		return map;
	}
	public static void clear(){
		//清空字典常数临时存储Map
		CONMAP = new HashMap<String, String>();
		//清空字典常数临时存储Map
		CONLISTS = new HashMap<String,List<String>>();
		//清空字典常数临时存储Map
		CONMAPS = new HashMap<String,Map<String,String>>();
		
	}
}
