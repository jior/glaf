/**
 * 在batch执行过程中写日志，每个batch的每次执行单独生成一个日志文件，
 * 文件名存放在logs目录下，命名规则：batch_batch名_执行时间.log
 */
package glaf.batch.log;

import glaf.batch.BatchConstans;
import glaf.batch.exception.BatchException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BatchExeLogFile {
	
	//文件
	private File file;
	//当前类的名称
	private String clazzName;
	//
	private String batName;

	public String getBatName() {
		return batName;
	}

	public void setBatName(String batName) {
		this.batName = batName;
	}

	/**
	 * 构造函数
	 * @param clazz 当前类的名称
	 */
	public BatchExeLogFile(String batName){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = sf.format(new Date(System.currentTimeMillis()));
		this.batName = batName;
		this.file = new File(BatchConstans.BATCH_PATH + "\\logs\\batch_" + batName + "_" + dateString + ".log");
	}
	
	/**
	 * 写消息日志
	 * @param message 日志内容
	 */
	public void info(String message){
		writeLog(message);
	}
	
	/**
	 * 写错误日志
	 * @param message 日志内容
	 */
	public void error(String message){
		writeLog(message);
	}
	
	/**
	 * 打开文件，写入日志
	 * @param message 日志中一行的内容
	 */
	private void writeLog(String message){
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = sf.format(new Date(System.currentTimeMillis()));
			
			FileOutputStream fos = new FileOutputStream(this.file,true);
			//写入文件的内容统一增加前缀：yyyy-mm-dd hh:mm:ss ：当前类的名称 ：日志内容
			String logContext = dateString + " : " + this.clazzName + " : " + message + "\r\n";
			fos.write(logContext.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			throw new BatchException(e);
		} catch (IOException e) {
			throw new BatchException(e);
		}
	}
	
	public File getFile() {
		return this.file;
	}

	public String getClazzName() {
		return this.clazzName;
	}
	
	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}


}
