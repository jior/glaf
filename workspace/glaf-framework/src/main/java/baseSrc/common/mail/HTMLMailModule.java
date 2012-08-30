/**
 * HTML格式邮件内容模板读取工具类
 * 
 * 该类提供了HTML邮件内容模板读取的相关操作，
 * 1、模板中变量的申明：#变量名
 *    该变量作用：动态填入数据，例如签名部分的数据需要根据不同发送人动态填写。
 * 2、模板格式要求：
 *    模板第一行必为邮件主题，若邮件主题不需要写在模板中，则将第一行设置为空行。 
 * 使用说明：
 * a、创建mailModule,参数：模板存放路径，模板编码格式
 * HTMLMailModule mailModule = new HTMLMailModule("c:\module.txt","UTF-8");
 * b、设置变量的值。如果没有要传入的值，则必须传入空串
 * mailModule.setVariableValue("applyNo", "123");
 * mailModule.setVariableValue("applyName", "");
 * c、获取模板标题
 * mailModule.getMailTitle();
 * d、获取模板内容
 * mailModule.getMailContext()
 * 
 * HTMLMailModuleUtil.java（类名）
 * 1.0.1.0（版本）
 * 作成者：ISC)yx
 * 作成时间：2008-07-24
 * 修改履历：
 *       年   月 日 区分 所 属/担 当   内 容                             版本号
 *     ----------  ---- ----------- ------------------------------  -----------
 *     
 */
package baseSrc.common.mail;

import baseSrc.common.LogHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class HTMLMailModule {
	//日志文件
	private static LogHelper logger = new LogHelper(HTMLMailModule.class);
	//默认邮件模板的编码格式
	private static String defaultBodyCharSet = "UTF-8";
	//默认邮件模板中变量的格式前缀
	private static String defaultVariablePrefix = "#";
	
	//邮件内容
	private String mailContext = "";
	//邮件标题
	private String mailTitle = "";
	//邮件编码格式，默认为UTF-8
	private String mailCharSet = defaultBodyCharSet;
	//邮件模板路径
	private String mailPath = "";
	//邮件模板上变量
	private Map<String, String> mailVaris = new HashMap<String, String>();
	
	
	/**
	 * HTML邮件模板类的构造函数
	 * @param path 邮件模板（完整路径）
	 * @param bodyCharSet 邮件模板编码格式（默认以UTF-8格式读取），若传入null或空串，则默认以UTF-8的格式读取
	 */
	public HTMLMailModule(String path,String bodyCharSet){
		logger.info("创建HTMLMailModule开始。");
		if(null != bodyCharSet && !"".equals(bodyCharSet.trim())){
			mailCharSet = bodyCharSet.trim();	
		}
		mailPath = path;
		//设置模板内容
		setMailContext();
		//设置模板标题
		setMailTitle();
		logger.info("创建HTMLMailModule结束。");
	}
	
	/**
	 * 将邮件模板中邮件内容部分获取出来，将其存放在mailContext中
	 */
	private void setMailContext(){
		StringBuffer sb = new StringBuffer();
		try{	
			File f = new File(mailPath);
			InputStreamReader read = new InputStreamReader (new FileInputStream(f),mailCharSet);
			BufferedReader reader=new BufferedReader(read);
			boolean firstLine = true;
			String line;
			while ((line = reader.readLine()) != null) {	
				if(!firstLine){
					sb.append(line);
				}
				firstLine = false;
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("setMailContext: " + e.getMessage());
			throw new MailSendException(e);
		}
		mailContext = sb.toString();
	}
	
	/**
	 * 获取邮件正文
	 */
	public String getMailContext(){
		String retMailContext = mailContext;
		if(!mailVaris.isEmpty()){
			Iterator<String> iterator = mailVaris.keySet().iterator();
			while(iterator.hasNext()){
				String varName = (String)iterator.next();
				String varValue = (String)mailVaris.get(varName);
				retMailContext = retMailContext.replaceAll(defaultVariablePrefix + varName, varValue);
			}
		}
		return retMailContext;
	}

	
	/**
	 * 根据传入路径读取文件第一行:邮件标题
	 * @param path 模板存放路径：需要完整路径及模板名称
	 * @param bodyCharSet 模板字符集，若传入null或空串，则默认以utf-8的格式读取
	 * @return String 邮件title
	 */
	private void setMailTitle(){
		StringBuffer sb = new StringBuffer();
		try{			
			File f = new File(mailPath);
			InputStreamReader read = new InputStreamReader (new FileInputStream(f),mailCharSet);
			BufferedReader reader=new BufferedReader(read);
			sb.append(reader.readLine());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("setMailTitle: " + e.getMessage());
			throw new MailSendException(e);
		}
		mailTitle = sb.toString();
	}
	
	/**
	 * 获取邮件title，即模板第一行内容
	 */
	public String getMailTitle(){
		return mailTitle;
	}

	
	/**
	 * 设置邮件模板中变量的值，以便在发出后显示该变量的值
	 * 变量格式：#变量名
	 * 若需要直接输出#，则模板上可直接写#，无需转义
	 * 变量名区分大小写
	 * @param varName 变量名（不需要将#传入）
	 * @param value 变量值，需要在邮件发送后显示的内容
	 */
	public void setVariableValue(String varName, String value){
		mailVaris.put(varName, value);		
	}
}
