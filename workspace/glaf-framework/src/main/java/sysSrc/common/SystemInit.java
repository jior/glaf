package sysSrc.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * rem: 系统初始化 * sep: 
 * aut: 
 * log: 
 */

public class SystemInit extends HttpServlet implements Serializable{
	private static final long serialVersionUID = 1L; 
	private static Logger logger = Logger.getLogger(SystemInit.class); 

	public SystemInit() { 
		
	} 

	public void init(ServletConfig config) throws ServletException { 
		//设置系统路径
		String prefix = config.getServletContext().getRealPath("/"); 
		System.setProperty ("webapp.root",prefix.replace("\\", "/"));
		System.setProperty ("WEBPATH",prefix.replace("\\", "/"));
		String projectName = prefix.substring(0,prefix.length()-1);
		projectName = projectName.substring(projectName.lastIndexOf('\\')+1,projectName.length());   
		System.setProperty ("webapp.name",projectName.replace("\\", "/"));
		
		//加载页面菜单内容
		try{
			popTreeInit();
		}catch(Exception e){
			logger.info("menus加载失败:"+e.getMessage());
		}
		
		
	} 
	
	/**
	 * 加载pop树到内存

	 * @param config
	 * @throws DocumentException 
	 */
	public void popTreeInit() throws DocumentException{
		String webPath = System.getProperty("WEBPATH");
		
		File xmlFile = new File(webPath+"/WEB-INF/classes/sysConfigfiles/popTree.xml");
		SAXReader reader = new SAXReader();
		Document document = reader.read(xmlFile);
        Element root = document.getRootElement();
        
        PopTree treeManager = PopTree.getInstance();
		Map treeMap = treeManager.getMenusMap();
		
        //资材品番申请
        Node node = root.selectSingleNode("tree1");
        Map tree1Map = treeManager.getMenus(node);
        if(0 < tree1Map.size()){
        	treeMap.put("tree1", tree1Map);
        }
        
        //供应商管理


        node = root.selectSingleNode("menu2");
        Map  tree2Map = treeManager.getMenus(node);
        if(0 < tree2Map.size()){
        	treeMap.put("menu2", tree2Map);
        }
        
        //系统管理
        node = root.selectSingleNode("menu3");
        Map tree3Map = treeManager.getMenus(node);
        if(0 < tree3Map.size()){
        	treeMap.put("menu3", tree3Map);
        }
	}
	
}
