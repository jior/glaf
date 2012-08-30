//================================================================================================
//项目名称 ：    GTMC 资材管理系统
//功    能 ：    菜单处理类
//文件名称 ：    MenusManager.java
//描    述 ：    
//================================================================================================
//修改履历                                                                
//年 月 日		区分			所 属/担 当           内 容									标识        
//----------   ----   -------------------- ---------------                          ------        
//2008/12/15   	编写   		PMPARK/李淼煌       新規作成                                                                            
//================================================================================================

package sysSrc.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.dom4j.Node;
import java.util.*;

/**
* rem: 菜单处理类
* sep: 
* aut: PMPARK)LMH
* log: 
*/

public class PopTree {
	private final static Log logger = LogFactory.getLog(PopTree.class);
	private static PopTree instance;
	private Map menusMap = new HashMap();
	
	/**
	 * 单例模式
	 * 
	 * @return
	 * @throws Exception
	 */
	public static synchronized PopTree getInstance() {
		logger.info("getInstance");
		if (instance == null) {
			instance = new PopTree();
		}
		return instance;
	}
	
	public Map getMenusMap() {
		return menusMap;
	}

	public void setMenusMap(Map menusMap) {
		this.menusMap = menusMap;
	}
	
	/**
	 * 取得菜单内容
	 * @param node
	 */
	public Map getMenus(Node node){
		Map menuMap = new LinkedHashMap();
    	List menuNodesList = node.selectNodes("menu");
    	int num = menuNodesList.size() - 1;
    	for (Element menu : (List<Element>)menuNodesList) {
    		List menuList = new ArrayList();
    		String pageid = menu.attributeValue("pageid");
    		String title = menu.attributeValue("title");
			String src = menu.attributeValue("src");
			String target = menu.attributeValue("target");
			if(menu.selectNodes("menu").size() > 0){
				Map map = getMenus(menu);
				menuList.add(map);
				
				if(null != title && !"".equals(title.trim())){
					menuList.add(title);
					pageid = title;
					menuMap.put(pageid, menuList);
				}
			}else if(null != pageid && !"".equals(pageid)){
//				if(menu.selectNodes("menu").size() > 0){
//					Map map = getMenus(menu);
//					menuList.add(map);
//				}else{
					menuList.add(null);
//				}
				
				if(null != title && !"".equals(title.trim())){
					menuList.add(title);
					
					if(null != src && !"".equals(src.trim())){
						menuList.add(src);
					}else{
						menuList.add(null);
					}
					
					menuMap.put(pageid, menuList);
				}
    		}
    	}
    	
    	return menuMap;
    }
	
	/**
	 * 取得菜单输出字符串
	 * @param context
	 * @param node
	 */
	public String getMenusToString(String context, Map menuMap, Map privilegeMap){
		String menuString = "";
		Iterator iter = menuMap.keySet().iterator();
		int num = menuMap.size() - 1;
		int i = 0;
		while(iter.hasNext()){
			String key = (String)iter.next();
			String subMenuString = "";
			List menuList = (List)menuMap.get(key);
			String title = (String)menuList.get(1);
			if(null != title && !"".equals(title)){
				boolean menuBln = false;//能否访问
				if(null != menuList.get(0)){//是否有子目录
					subMenuString = getMenusToString(context, (Map)menuList.get(0), privilegeMap);
					if(!"".equals(subMenuString)){
						menuBln = true;
					}
				}else if(privilegeMap.containsKey(key)){//如果没有子目录，那么是否有权限访问
					menuBln = true;
				}
				
				if(menuBln){//可以访问
					if(2 < menuList.size()){
						String src = (String)menuList.get(2);
						if(null != src && !"".equals(src)){
							if(src.toLowerCase().startsWith("javascript")){
								menuString = menuString + "<li><a href=\"" + src + "\">" + title + "</a></li>";
							}else{
								menuString = menuString + "<li><a href=\"" + context + src + "\">" + title + "</a></li>";
							}
						}else{
							menuString = menuString + "<li>" + title + "</li>";
						}
					}else{
						menuString = menuString + "<li>" + title + "</li>";
					}
					
					menuString = menuString + subMenuString + "<li></li>";
				}
			}
			i ++;
		}
		if(!"".equals(menuString)){
			menuString = "<ul>" + menuString.substring(0,menuString.length() - 9) + "</ul>";
		}
		
		return menuString;
    }

}
