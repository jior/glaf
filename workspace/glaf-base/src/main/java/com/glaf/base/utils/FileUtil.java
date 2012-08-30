package com.glaf.base.utils;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.Constants;

public class FileUtil {
	private static Log logger = LogFactory.getLog(FileUtil.class);
	public static final String UPLOAD_DIR = Constants.ROOT_PATH
	+ Constants.UPLOAD_DIR;
	
	/**
	 * ɾ��ָ��·���ĵ����ļ�
	 * @param filePath
	 */
	public static void delFiles(String filePath){
		logger.info("filepath="+filePath);
		File file = new File(filePath);
		if(file.exists() && file.isFile()){
			file.delete() ;
		}
	}
	
	/**
	 * ɾ��ָ��Ŀ¼�е������ļ�
	 * @param dirPath
	 */
	public static void delDirFlies(String dirPath,String ext){
		File file = new File(dirPath);
		if(file.isDirectory()){
			File files[] = file.listFiles();
			for(int i=0; i<files.length; i++){
				if(files[i].isDirectory()){
					delDirFlies(files[i].getAbsolutePath(),ext);
				}else{
					if(ext != null && files[i].getName().lastIndexOf(ext) != -1){
						if(files[i].isFile())files[i].delete();
					}else{
						files[i].delete();
					}
				}
			}
		}
	}
	
	/**
	 * ɾ��ϵͳ�е�Down�ļ����е�temp�ļ�Ŀ¼�е������ļ�
	 *
	 */
	public static void delDownTempFiles(){
		String path = Constants.ROOT_PATH + Constants.UPLOAD_DIR+"temp";
		delDirFlies(path,"xls");
	}
	
}
