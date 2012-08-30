package glaf.batch.log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class BatchLogFile {
	private Logger logger = null;
	
	public BatchLogFile(Class<?> clazz){
		String pathconfig = System.getProperty("user.dir") + "\\configfiles\\log4j.properties";
		PropertyConfigurator.configure(pathconfig);
		logger = Logger.getLogger(clazz);
	}
	
	/**
	 * 写消息日志

	 * @param message 日志内容
	 */
	public void info(String message)
	{
		logger.info(message);
	}
	
	/**
	 * 写错误日志

	 * @param message 日志内容
	 */
	public void error(String message)
	{
		logger.error(message);
	}
}
