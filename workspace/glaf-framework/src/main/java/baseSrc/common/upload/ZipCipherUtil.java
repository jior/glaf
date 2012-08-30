//================================================================================================
//项目名称 ：    基盘
//功    能 ：  系统字典查询

//文件名称 ：   ZipCipherUtil.java                                   
//描    述 ：    
//================================================================================================
//修改履历                                                                
//年 月 日		区分		所 属/担 当           		内 容									标识        
//----------   	----   	------------------- ---------------                          ------        
//2012/08/17   	编写   	Intasect/钟敏    	 新規作成                                                                            
//================================================================================================

package baseSrc.common.upload;

import java.io.File;   
import java.util.UUID;   
  
public class ZipCipherUtil {   
    /**  
     * 对目录srcFile下的所有文件目录进行先压缩后加密,然后保存为destfile  
     *   
     * @param srcFile  
     *            要操作的文件或文件夹  
     * @param destfile  
     *            压缩加密后存放的文件  
     * @param keyfile  
     *            密钥  
     */  
    public static void encryptZip(String srcFile, String destfile, String keyStr) throws Exception {   
        File temp = new File(UUID.randomUUID().toString() + ".zip");   
        temp.deleteOnExit();   
        // 先压缩文件   
        new ZipUtil().zip(srcFile, temp.getAbsolutePath());   
        // 对文件加密   
        new CipherUtil().encrypt(temp.getAbsolutePath(), destfile, keyStr);   
        temp.delete();   
    }   
  
    /**  
     * 对文件srcfile进行先解密后解压缩,然后解压缩到目录destfile下  
     *   
     * @param srcfile  
     *            要解密和解压缩的文件名   
     * @param destfile  
     *            解压缩后的目录  
     * @param publicKey  
     *            密钥  
     */  
    public static void decryptUnzip(String srcfile, String destfile, String keyStr) throws Exception {   
        File temp = new File(UUID.randomUUID().toString() + ".zip");   
        temp.deleteOnExit();   
        // 先对文件解密   
        new CipherUtil().decrypt(srcfile, temp.getAbsolutePath(), keyStr);   
        // 解压缩   
        new ZipUtil().unZip(temp.getAbsolutePath(),destfile);   
        temp.delete();   
    }   
       
    public static void main(String[] args) throws Exception {   
        long l1 = System.currentTimeMillis();   
           
        //加密   
//        ZipCipherUtil.encryptZip("E:\\alarm\\的说法大法师地方.txt", "E:\\alarm\\的说法大法师地方.zip", "12345"); 
        //解密   
        ZipCipherUtil.decryptUnzip("E:\\alarm\\的说法大法师地方.zip", "E:\\alarm\\", "12345");   
           
        long l2 = System.currentTimeMillis();   
        System.out.println((l2 - l1) + "毫秒.");   
        System.out.println(((l2 - l1) / 1000) + "秒.");   
    }   
    
    /**
     * 删除文件
     * @param filePath
     */
    public static void deleteFile(String filePath){
    	File file = new File(filePath);
    	System.out.println("DELETEPATH:"+filePath);
        if(file.exists()){
        	if(file.isFile()){
        		file.delete();
        	}else if(file.isDirectory()){
        		File[] files = file.listFiles();
        		for(int i=0;i<files.length;i++){
        			ZipCipherUtil.deleteFile(files[i].getPath());
        		}
        		file.delete();
        	}
        	
        }
    }

	public static void clearFile(String filePath) {
		File file = new File(filePath);
        if(!file.isFile()&&file.exists()){
        	File[] files = file.listFiles();
        	for(int i=0;i<file.length();i++){
        		files[i].delete();
        	}
        	
        }
		
	}
}  
