//================================================================================================
//项目名称 ：    基盘
//功    能 ：   写日志
//文件名称 ：    LogHelper.java                                   
//描    述 ：    提供一组共通方法,调用框架提供的日志工具输出日志
//================================================================================================
//修改履历                                                                
//年 月 日		区分		所 属/担 当           		内 容									标识        
//----------   	----   	------------------- ---------------                         ------        
//2009/05/19   	编写   	Intasect/殷翔    	 	新規作成                                                                            
//================================================================================================

package baseSrc.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import sysSrc.common.SysBaseCom;

public class LogHelper {
	
	private Logger logger = null;
	
	/**
	 * 
	 * @param clazz 记录日志的类
	 */
	public LogHelper(Class<?> clazz)
	{
		logger = Logger.getLogger(clazz);
	}
	
	/**
	 * 
	 * @param name 日志的名字
	 */
	public LogHelper(String name)
	{
		logger = Logger.getLogger(name);
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
	 * 写消息日志
	 * @param baseCom 用户信息
	 * @param message 日志内容
	 */
	public void info(BaseCom baseCom, String message)
	{
		logger.info(" USERID = " + baseCom.getUserId() + " " + message);
	}
	
	/**
	 * 写错误日志
	 * @param message 日志内容
	 */
	public void error(String message)
	{
		logger.error(message);
	}
	
	/**
	 * 写错误日志
	 * @param baseCom 用户信息
	 * @param message 日志内容
	 */
	public void error(SysBaseCom baseCom, String message)
	{
		logger.error(" USERID = " + baseCom.getUserId() + " " + message);
	}

	/**
	 * 获取对象中各属性的值
	 * @param entityName 对象实体
	 * @return 各属性值的一个字符串(Format: id = 1; name = 2;)
	 * @throws Exception
	 */
	public String getPropertyString(Object entityName) throws Exception {
		Class<?> c = entityName.getClass();
		Field field[] = c.getDeclaredFields();
		StringBuffer sb = new StringBuffer();

		//sb.append("------ " + " begin ------\n");
		for (Field f : field) {
			sb.append(f.getName());
			sb.append(" = ");
			sb.append(invokeMethod(entityName, f.getName(), null));
//			sb.append("/n");
			sb.append("; ");
		}
		//sb.append("------ " + " end ------\n");
		return sb.toString();
	}

	/**
	 * 获取属性的值
	 * @param owner
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	private Object invokeMethod(Object owner, String methodName, Object[] args)
			throws Exception {
		Class<?> ownerClass = owner.getClass();

		methodName = methodName.substring(0, 1).toUpperCase()
				+ methodName.substring(1);
		Method method = null;
		try {
			method = ownerClass.getMethod("get" + methodName);
		} catch (SecurityException e) {
			// e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// e.printStackTrace();
			return " can't find 'get" + methodName + "' method";
		}

		return method.invoke(owner);
	}
}
