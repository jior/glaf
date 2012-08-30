//================================================================================================
//项目名称 ：    基盘
//功    能 ：   统计查询

//文件名称 ：   Grid.java                                   
//描    述 ：    
//================================================================================================
//修改履历                                                                
//年 月 日		区分		所 属/担 当           		内 容									标识        
//----------   	----   	------------------- ---------------                          ------        
//2012/08/14   	编写   	Intasect/钟敏    	 新規作成                                                                            
//================================================================================================

package sysSrc.common.gridTag;

import baseSrc.common.BaseUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Grid {
	/**
	 * 分隔符
	 */
	private static final String Splitor = ":";

	/**
	 * 合计计算：合计项目对应列的列名
	 */
	private ArrayList<String> valueColumns = new ArrayList<String>();
	
	/**
	 * 合计计算：合计判断键值对应列的列名
	 */
	private ArrayList<String> keyColumns = new ArrayList<String>();
	
	/**
	 * 合计计算：合计判断键值对应列的列名
	 */
	private ArrayList<String> showValues = new ArrayList<String>();
	
	/**
	 * 取得后继后的list
	 * @param listget 保存数据的list
	 * @param keyColumnsName  合计判断键值对应列的列名
	 * @param valueColumnsName 合计项目对应列的列名
	 * @param showKey 合计列明显示的列名
	 * @param showValue 合计列明显示的value值 ，其中 {0} 会被列值取代
	 * @param isTotal 是否有合计项
	 * @param totalName 合计项的列名，默认为： 合计
	 * @return
	 */
	public List<HashMap<String,Object>> getTotalList(
			List<HashMap<String,Object>> listget,
			String keyColumnsName,
			String valueColumnsName,
			String showKey,
			String showValue,
			boolean isTotal,
			String totalName){
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		try{
			boolean isAdd = true;	//是否锁定判断
			
			//初始化
			keyColumnsName = StringUtils.lowerCase(keyColumnsName);
			valueColumnsName = StringUtils.lowerCase(valueColumnsName);
			showKey = StringUtils.lowerCase(showKey);
			String[] tmp = keyColumnsName.split(Splitor);
			keyColumns.addAll(Arrays.asList(tmp));
			tmp = valueColumnsName.split(Splitor);
			valueColumns.addAll(Arrays.asList(tmp));
			tmp = showValue.split(Splitor);
			showValues.addAll(Arrays.asList(tmp));
			
			//listget为null时直接返回list
			if(BaseUtility.isListNull(listget)){
				return list;
			}
			HashMap<String,Object> mapFirst = (HashMap<String,Object>)listget.get(0);   //第一条数据
			
			//初始化小计池
			HashMap<String,Object> map = new HashMap<String, Object>();          //对比的map
			HashMap<String,Object> mapTotal = new HashMap<String,Object>();       //合计map
			HashMap<String,HashMap<String,Object>> mapAll = new HashMap<String,HashMap<String,Object>>();   //每种小计的map合计
			List<HashMap<String, Object>> listAll = new ArrayList<HashMap<String,Object>>();                //小计集合
			List<HashMap<String, Object>> listLast = new ArrayList<HashMap<String,Object>>();               //最后一条的小计合计
			
			//将所有的key值放入map中，以便小计使用
			for(int i=0;i<keyColumns.size();i++){
				String key = keyColumns.get(i);
				String name = showValues.get(i);        //合计显示name
				HashMap<String,Object> mapNew = new HashMap<String,Object>();          //一个小计一个map
				mapNew.put(showKey, name);
				mapAll.put(key,mapNew);
				map.put(key, mapFirst.get(key));
			}
			
			//遍历每一条数据
			for(int i=0;i<listget.size();i++){
				HashMap<String,Object> mapGet = (HashMap<String,Object>)listget.get(i);
				//遍历合计KEY
				for(int j=0;j<keyColumns.size();j++){
					//合计对比key
					String key = keyColumns.get(j);  
					
					//合计显示name
					String name = showValues.get(j);      
					
					HashMap<String,Object> mapNew = mapAll.get(key);
					
					//如果上一条的key与本条的key相同
					String firstKey = map.get(key) == null ? "NULL":map.get(key).toString();
					String checkKey = mapGet.get(key) == null ? "NULL":mapGet.get(key).toString();
					if (firstKey.equals(checkKey)&&isAdd) {             
						//计算合计
						getGrid(mapNew,mapGet,mapTotal,mapAll,j,key);
					}
					//上一条的key与本条不相同
					else{                                   
						isAdd = false;  //锁定判断
						HashMap<String,Object> mapIn = mapAll.get(key); 
						name = StringUtils.replace(name, "{0}",firstKey);
						mapIn.put(showKey, name);
						
						//list装入本小计map
						listAll.add(mapIn);      
						
						//更新上一条map的对比key值
						map.put(key, checkKey);      		
						
						//新建本小计的Map
						mapNew = new HashMap<String,Object>();      
						
						//本小计装入显示的列的key
						mapNew.put(showKey, showValues.get(j));     
						
						//计算合计
						getGrid(mapNew,mapGet,mapTotal,mapAll,j,key);
						
					}
					
					//如果是最后一条，list装入本小计map
					if(i+1==listget.size()){                
						HashMap<String,Object> mapIn = mapAll.get(key);
						
						//合计显示name
						name = showValues.get(j);        
						
						name = StringUtils.replace(name, "{0}",checkKey);
						mapIn.put(showKey, name);
						listLast.add(mapIn);      
					}
				}
				//初始化锁定判断
				isAdd = true;
				
				//倒叙循环取得小计并放入list中
				for(int j=listAll.size()-1;j>=0;j--){
					list.add(listAll.get(j));
				}
				//清空小计list
				listAll.clear();
				
				//将本条数据放入list中
				list.add(mapGet);
			}
			
			//倒叙循环取得小计并放入list中
			for(int j=listLast.size()-1;j>=0;j--){
				list.add(listLast.get(j));
			}
			//清空小计list
			listLast.clear();
			
			//如果有合计项，装入合计
			if(isTotal){                                    
				mapTotal.put(showKey, totalName);
				list.add(mapTotal);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return listget;
		}
		return list;
	}
	/**
	 * 计算小计
	 * @param mapNew 一个小计一个map
	 * @param mapGet 本行数据
	 * @param mapTotal 小计map
	 * @param mapAll 每种小计的map合计
	 * @param j 合计KEY的索引
	 * @param key 合计对比key
	 */
	private void getGrid(HashMap<String,Object> mapNew,
						 HashMap<String,Object> mapGet,
						 HashMap<String,Object> mapTotal,
						 HashMap<String,HashMap<String,Object>> mapAll,
						 int j,
						 String key
						 ) {
		
			for (int k = 0; k < valueColumns.size(); k++) {   
				// 合计列名称
				String value = valueColumns.get(k); 
				String ssumNew = mapNew.get(value) == null ? "0": mapNew.get(value).toString();
				String ssumOld = mapGet.get(value) == null ? "0": mapGet.get(value).toString();
				String ssumAll = mapTotal.get(value) == null ? "0": mapTotal.get(value).toString();
				
				double d = Double.parseDouble(ssumNew) + Double.parseDouble(ssumOld);
				if(j==0){
				double dTotal = Double.parseDouble(ssumAll) + Double.parseDouble(ssumOld);
				//精度调整
				mapTotal.put(value, dTotal);    //更新合计
				}
				//精度调整
				mapNew.put(value, d);           //更新小计
			}
			mapAll.put(key, mapNew);            //更新本小计的map
	}


}
