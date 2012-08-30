//================================================================================================
//项目名称 ：    基盘
//功    能 ：  系统字典查询

//文件名称 ：   ZipUtil.java                                   
//描    述 ：    
//================================================================================================
//修改履历                                                                
//年 月 日		区分		所 属/担 当           		内 容									标识        
//----------   	----   	------------------- ---------------                          ------        
//2012/08/17   	编写   	Intasect/钟敏    	 新規作成                                                                            
//================================================================================================

package sysSrc.common;

import java.io.File;   
import java.io.FileInputStream;   
import java.io.FileOutputStream;   
import java.io.IOException;   
import java.util.zip.ZipEntry;   
import java.util.zip.ZipInputStream;   
import java.util.zip.ZipOutputStream;   
  
/**  
 * 对文件或文件夹进行压缩和解压  
 *  
 */  
public class ZipUtil {   
    /**得到当前系统的分隔符*/  
//  private static String separator = System.getProperty("file.separator");   
  
    /**  
     * 添加到压缩文件中  
     * @param out  
     * @param f  
     * @param base  
     * @throws Exception  
     */  
    private void directoryZip(ZipOutputStream out, File f, String base) throws Exception {   
        // 如果传入的是目录   
        if (f.isDirectory()) {   
            File[] fl = f.listFiles();   
            // 创建压缩的子目录   
            out.putNextEntry(new ZipEntry(base + "/"));   
            if (base.length() == 0) {   
                base = "";   
            } else {   
                base = base + "/";   
            }   
            for (int i = 0; i < fl.length; i++) {   
                directoryZip(out, fl[i], base + fl[i].getName());   
            }   
        } else {   
            // 把压缩文件加入rar中   
            out.putNextEntry(new ZipEntry(base));   
            FileInputStream in = new FileInputStream(f);   
            byte[] bb = new byte[10240];   
            int aa = 0;   
            while ((aa = in.read(bb)) != -1) {   
                out.write(bb, 0, aa);   
            }   
            in.close();   
        }   
    }   
  
    /**  
     * 压缩文件  
     *   
     * @param zos  
     * @param file  
     * @throws Exception  
     */  
    private void fileZip(ZipOutputStream zos, File file) throws Exception {   
        if (file.isFile()) {   
            zos.putNextEntry(new ZipEntry(file.getName()));   
            FileInputStream fis = new FileInputStream(file);   
            byte[] bb = new byte[10240];   
            int aa = 0;   
            while ((aa = fis.read(bb)) != -1) {   
                zos.write(bb, 0, aa);   
            }   
            fis.close();   
            System.out.println(file.getName());   
        } else {   
            directoryZip(zos, file, "");   
        }   
    }   
  
    /**  
     * 解压缩文件  
     *   
     * @param zis  
     * @param file  
     * @throws Exception  
     */  
    private void fileUnZip(ZipInputStream zis, File file) throws Exception {   
        ZipEntry zip = zis.getNextEntry();   
        if (zip == null)   
            return;   
        String name = zip.getName();   
        File f = new File(file.getAbsolutePath() + "/" + name);   
        if (zip.isDirectory()) {   
            f.mkdirs();   
            fileUnZip(zis, file);   
        } else {   
            f.createNewFile();   
            FileOutputStream fos = new FileOutputStream(f);   
            byte b[] = new byte[10240];   
            int aa = 0;   
            while ((aa = zis.read(b)) != -1) {   
                fos.write(b, 0, aa);   
            }   
            fos.close();   
            fileUnZip(zis, file);   
        }   
    }   
       
    /**  
     * 根据filePath创建相应的目录  
     * @param filePath  
     * @return  
     * @throws IOException  
     */  
    private File mkdirFiles(String filePath) throws IOException{   
        File file = new File(filePath);   
        if(!file.getParentFile().exists()){   
            file.getParentFile().mkdirs();   
        }   
        file.createNewFile();   
           
        return file;   
    }   
  
    /**  
     * 对zipBeforeFile目录下的文件压缩，保存为指定的文件zipAfterFile  
     *   
     * @param zipBeforeFile     压缩之前的文件  
     * @param zipAfterFile      压缩之后的文件  
     */  
    public void zip(String zipBeforeFile, String zipAfterFile) {   
        try {   
               
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(mkdirFiles(zipAfterFile)));   
            fileZip(zos, new File(zipBeforeFile));   
            zos.close();   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
  
    /**  
     * 解压缩文件unZipBeforeFile保存在unZipAfterFile目录下  
     *   
     * @param unZipBeforeFile       解压之前的文件  
     * @param unZipAfterFile        解压之后的文件  
     */  
    public void unZip(String unZipBeforeFile, String unZipAfterFile) {   
        try {   
            ZipInputStream zis = new ZipInputStream(new FileInputStream(unZipBeforeFile));   
            File f = new File(unZipAfterFile);   
            f.mkdirs();   
            fileUnZip(zis, f);   
            zis.close();   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
    /**
     * 压缩文件支持文件夹
     * @param baseDirName 基本目录
     * @param fileName 文件名字
     * @param targerFileName 目标文件名Path
     * @throws IOException
     */
    public static void zipFile(String baseDirName, String fileName,
            String targerFileName) throws IOException {
    	baseDirName = baseDirName.replace("//", "\\").replace("/", "\\");
    	targerFileName = targerFileName.replace("//", "\\").replace("/", "\\");
    	System.out.println(baseDirName);
    	System.out.println(fileName);
    	System.out.println(targerFileName);
        if (baseDirName == null || "".equals(baseDirName)) {
            return;
        }
        File baseDir = new File(baseDirName);
        
        if (!baseDir.exists() || !baseDir.isDirectory()) {
            return;
        }

        String baseDirPath = baseDir.getAbsolutePath();

        File targerFile = new File(targerFileName);

        org.apache.tools.zip.ZipOutputStream out = new org.apache.tools.zip.ZipOutputStream(new FileOutputStream(
                targerFile));
        out.setEncoding("gbk");
        File file = new File(baseDir, fileName);

        if (file.isFile()) {
            fileToZip(baseDirPath, file, out);
        } else {
            dirToZip(baseDirPath, file, out);
        }
        out.close();
    }

    private static void dirToZip(String baseDirPath, File dir,
    		org.apache.tools.zip.ZipOutputStream out) throws IOException {
        if (!dir.isDirectory()) {
            return;
        }

        File[] files = dir.listFiles();

        if (files.length == 0) {
        	org.apache.tools.zip.ZipEntry entry = new org.apache.tools.zip.ZipEntry(getEntryName(baseDirPath, dir));

            try {
                out.putNextEntry(entry);
                out.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                fileToZip(baseDirPath, files[i], out);
            } else {
                dirToZip(baseDirPath, files[i], out);
            }

        }

    }

    private static void fileToZip(String baseDirPath, File file,
    		org.apache.tools.zip.ZipOutputStream out) throws IOException {
        FileInputStream in = null;
        org.apache.tools.zip.ZipEntry entry = null;

        byte[] buffer = new byte[4096];
        int bytes_read;

        if (file.isFile()) {
            try {
                in = new FileInputStream(file);
                
                entry = new org.apache.tools.zip.ZipEntry(getEntryName(baseDirPath, file));
                out.putNextEntry(entry);
                
                while ((bytes_read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytes_read);
                }
                out.closeEntry();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 关闭输入输出流
                if (out != null) {
                    out.closeEntry();
                }

                if (in != null) {
                    in.close();
                }
            }
        }
    }

    private static String getEntryName(String baseDirPath, File file) {
        if (!baseDirPath.endsWith(File.separator)) {
            baseDirPath = baseDirPath + File.separator;
        }

        String filePath = file.getAbsolutePath();
        if (file.isDirectory()) {
            filePath = filePath + "/";
        }

        int index = filePath.indexOf(baseDirPath);

        return filePath.substring(index + baseDirPath.length());
    }
}  

