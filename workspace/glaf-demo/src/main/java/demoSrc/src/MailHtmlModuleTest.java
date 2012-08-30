package demoSrc.src;

import baseSrc.common.mail.HTMLMailModule;
import baseSrc.common.mail.MailNotify;
import junit.framework.TestCase;


public class MailHtmlModuleTest  extends TestCase {
	String dbcontext = "DEFAULT_MAIL";
	String filePath = "e:\\MailTemplate_SignalNext.txt";
	
    @Override
    protected void setUp() throws Exception {
    	// TODO Auto-generated method stub
    	//dbcontext = new DBContext();
    }
	
	public void tearDown(){
		//dbcontext.close();
	}

	/**
	 * case 1.1.1 v2.0.1.0设置变量的值并获取模板内容
	 *
	 */
	public void test1_1_1()
	{
		HTMLMailModule mailModule = new HTMLMailModule(filePath,"UTF-8");
		mailModule.setVariableValue("applyNo", "test1_1_1_1...");
		mailModule.setVariableValue("paryname", "test1_1_1_2...");
		
		MailNotify mail = new MailNotify(dbcontext);
		try {
			mail.setBodyCharSet("UTF-8");
			mail.setHtmlBody(true);
			mail.sendmail("xumiao@intasect.com.cn",mailModule.getMailTitle(),mailModule.getMailContext(),null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * case 1.1.2 v2.0.1.0不设置变量的值并获取模板内容
	 *
	 */
	public void test1_1_2()
	{
		HTMLMailModule mailModule = new HTMLMailModule(filePath,"UTF-8");
		//mailModule.setVariableValue("applyNo", "tairong..test...");
		
		MailNotify mail = new MailNotify(dbcontext);
		try {
			mail.setBodyCharSet("UTF-8");
			mail.setHtmlBody(true);
			mail.sendmail("xumiao@intasect.com.cn",mailModule.getMailTitle(),mailModule.getMailContext(),null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * case 2.1 v2.0.1.0获取模板标题--有标题
	 *
	 */
	public void test2_1()
	{
		HTMLMailModule mailModule = new HTMLMailModule(filePath,"UTF-8");
		mailModule.setVariableValue("applyNo", "test2_1_1");
		
		MailNotify mail = new MailNotify(dbcontext);
		try {
			mail.setBodyCharSet("UTF-8");
			mail.setHtmlBody(true);
			mail.sendmail("xumiao@intasect.com.cn",mailModule.getMailTitle(),mailModule.getMailContext(),null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * case 2.2 v2.0.1.0获取模板标题--无标题题
	 *
	 */
	public void test2_2()
	{
		HTMLMailModule mailModule = new HTMLMailModule(filePath,"UTF-8");
		mailModule.setVariableValue("applyNo", "test2_2_1");
		
		MailNotify mail = new MailNotify(dbcontext);
		try {
			mail.setBodyCharSet("UTF-8");
			mail.setHtmlBody(true);
			mail.sendmail("xumiao@intasect.com.cn",mailModule.getMailTitle(),mailModule.getMailContext(),null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * case 3.1 v2.0.1.0重复设置变量的值
	 *
	 */
	public void test3_1()
	{
		HTMLMailModule mailModule = new HTMLMailModule(filePath,"UTF-8");
		mailModule.setVariableValue("applyNo", "test3_1_1");
		mailModule.setVariableValue("applyNo", "test3_1_2");
		mailModule.setVariableValue("applyNo", "test3_1_3");
		
		MailNotify mail = new MailNotify(dbcontext);
		try {
			mail.setBodyCharSet("UTF-8");
			mail.setHtmlBody(true);
			mail.sendmail("xumiao@intasect.com.cn",mailModule.getMailTitle(),mailModule.getMailContext(),null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * case 3.2 v2.0.1.0设置不存在的变量的值
	 *
	 */
	public void test3_2()
	{
		HTMLMailModule mailModule = new HTMLMailModule(filePath,"UTF-8");
		mailModule.setVariableValue("123applyNo", "test3_2_1");
		
		MailNotify mail = new MailNotify(dbcontext);
		try {
			mail.setBodyCharSet("UTF-8");
			mail.setHtmlBody(true);
			mail.sendmail("xumiao@intasect.com.cn",mailModule.getMailTitle(),mailModule.getMailContext(),null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * case 4.1 v2.0.1.0异常测试--没有找到模板文件
	 *
	 */
	public void test4_1()
	{
		HTMLMailModule mailModule = new HTMLMailModule("e:\\123.txt","UTF-8");
		mailModule.setVariableValue("applyNo", "test4_1_1");
		
		MailNotify mail = new MailNotify(dbcontext);
		try {
			mail.setBodyCharSet("UTF-8");
			mail.setHtmlBody(true);
			mail.sendmail("xumiao@intasect.com.cn",mailModule.getMailTitle(),mailModule.getMailContext(),null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * case 4.2 v2.0.1.0异常测试--没有设置模板编码格式
	 *
	 */
	public void test4_2()
	{
		HTMLMailModule mailModule = new HTMLMailModule(filePath,"");
		mailModule.setVariableValue("applyNo", "test4_2_1");
		
		MailNotify mail = new MailNotify(dbcontext);
		try {
			//mail.setBodyCharSet("UTF-8");
			mail.setHtmlBody(true);
			mail.sendmail("xumiao@intasect.com.cn",mailModule.getMailTitle(),mailModule.getMailContext(),null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * case 4.3 v2.0.1.0异常测试--设置编码格式为gbk
	 *
	 */
	public void test4_3()
	{
		HTMLMailModule mailModule = new HTMLMailModule(filePath,"gbk");
		mailModule.setVariableValue("applyNo", "test4_3_1");
		
		MailNotify mail = new MailNotify(dbcontext);
		try {
			mail.setBodyCharSet("gbk");
			mail.setHtmlBody(true);
			mail.sendmail("xumiao@intasect.com.cn",mailModule.getMailTitle(),mailModule.getMailContext(),null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
