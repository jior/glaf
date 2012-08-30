package com.glaf.base.modules.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.Constants;
 

public class SettingUtil {
	private static Log logger = LogFactory.getLog(SettingUtil.class);
	private static String fileName = Constants.ROOT_PATH + "\\WEB-INF\\conf\\setting.properties";
	
	
	public static String getFileName() {
		return fileName;
	}
	public static void setFileName(String fileName) {
		SettingUtil.fileName = fileName;
	}
	/**
	 * 读取属性设置
	 * @param key
	 * @return
	 */
	public static String getProperty(String key){
		Properties p=new Properties();
		try{
			InputStream in = new FileInputStream(fileName);
			p.load(in);			
		}catch(Exception e){
			logger.error(e);
		}
		return p.getProperty(key);
	}
	/**
	 * 设置属性
	 * @param properties
	 * @return
	 */
	public static boolean saveProperty(Map properties){
		boolean ret = false;
		try{
			logger.info(fileName);
			Properties property = new Properties();
			Iterator keySet = properties.keySet().iterator();
			while(keySet.hasNext()){
				String key = (String)keySet.next();
				String value = (String)properties.get(key);
				property.setProperty(key, value);
			}
			property.store(new FileOutputStream(fileName), "properties");
			ret = true;
		}catch(Exception e){
			logger.error(e);
		}
		return ret;
	}
	 
}
