package demoSrc.src;

import java.util.ArrayList;
import java.util.List;


import junit.framework.TestCase;
import baseSrc.common.mail.HTMLMailModule;
import baseSrc.common.mail.MailNotify;
import baseSrc.common.mail.MailServer;

public class MailSendTest  extends TestCase {
	String mailCfgKeyId = "DEFAULT_MAIL";
	// please specify the path of a file which exists on your machine 
	String filePath = "C:\\Config.ini";
	
    @Override
    protected void setUp() throws Exception {
    	// TODO Auto-generated method stub
    	//dbcontext = new DBContext();
    }
	
	public void tearDown(){
		//dbcontext.close();
	}
	
	/**
	 * 测试设置发件箱
	 *
	 */
	public void test01SetMailServer()
	{
		MailServer mailserver2 = new MailServer(mailCfgKeyId);
		assertNotNull(mailserver2);
		System.out.println(mailserver2.getServerip());
		System.out.println(mailserver2.getUsername());
		System.out.println(mailserver2.getPassword());
	}
	
	/**
	 * 测试获取发件箱信息
	 *
	 */
	public void test02GetMailServer()
	{
		MailServer mailserver = new MailServer(mailCfgKeyId);
		
		assertNotNull(mailserver);
		System.out.println(mailserver.getServerip());
		System.out.println(mailserver.getUsername());
		System.out.println(mailserver.getPassword());
	}
	
	
	
	/**
	 * 测试邮件发送
	 * 收件人错误
	 */
	public void test03sendWithErrRecipients()
	{
		MailNotify mail = new MailNotify(mailCfgKeyId);
		boolean f = true;
		try {
			mail.sendmail("xumiaochendu@intasect.com.cn","test","test new send method",null);
		} catch (Exception e) {
			e.printStackTrace();
			f = false;
		}
		//assertFalse(f);
	}
	
	/**
	 * 测试邮件发送
	 * 附件错误
	 */
	public void test04sendWithErrFiles()
	{
		MailNotify mail = new MailNotify(mailCfgKeyId);
		List files = new ArrayList();
		
		files.add("C:\\test.txt");
		boolean f = true;
		try {
			mail.sendmail("xumiao@intasect.com.cn","test04","test new send method",files);
		} catch (Exception e) {
			e.printStackTrace();
			f=false;
		}
		assertFalse(f);
	}
	
	/**
	 * 测试邮件发送
	 * 发送给一个人（内/有附件）
	 */
	public void test05send()
	{
		MailNotify mail = new MailNotify(mailCfgKeyId);
		
		List files = new ArrayList();
		
		files.add(filePath);
		boolean f = true;
		try {
			mail.sendmail("xumiao@intasect.com.cn","test05","test new send method",files);
			
		} catch (Exception e) {
			e.printStackTrace();
			f=false;
		}
		assertTrue(f);
	}
	
	/**
	 * 测试邮件发送
	 * 多个接收者（内外、无附件）
	 */
	public void test06sendToMany()
	{
		MailNotify mail = new MailNotify(mailCfgKeyId);
		List recipients = new ArrayList();
		
		recipients.add("xumiao@intasect.com.cn");
		recipients.add("dengguangping@intasect.com.cn");
		boolean f = true;
		try {
			mail.sendmail(recipients,"test06","test new send method",null);
			
		} catch (Exception e) {
			e.printStackTrace();
			f=false;
		}
		assertTrue(f);
	}
	
	/**
	 * 测试邮件发送
	 * 发送给一个人(外/有附件)
	 */
	public void test07send()
	{
		MailNotify mail = new MailNotify(mailCfgKeyId);
		
		List files = new ArrayList();
		
		files.add(filePath);
		boolean f = true;
		try {
			mail.sendmail("308370117@163.com","test07","test new send method",files);
			
		} catch (Exception e) {
			e.printStackTrace();
			f=false;
		}
		assertTrue(f);
	}
	
	/**
	 * 测试邮件发送
	 * 多个接收者（无附件）（内外/无附件）
	 */
	public void test08send()
	{
		MailNotify mail = new MailNotify(mailCfgKeyId);
		List recipients = new ArrayList();
		
		recipients.add("xumiao@intasect.com.cn");
		recipients.add("dengguangping@intasect.com.cn");
		recipients.add("308370117@163.com");
		
		boolean f = true;
		try {
			mail.sendmail(recipients,"test08","test new send method",null);
			
		} catch (Exception e) {
			e.printStackTrace();
			f=false;
		}
		assertTrue(f);
	}
	
	/**
	 * 测试邮件发送
	 * 发送给一个人（内/无附件）
	 */
	public void test09send()
	{
		MailNotify mail = new MailNotify(mailCfgKeyId);
		
		boolean f = true;
		try {
			mail.sendmail("xumiao@intasect.com.cn","test09","test new send method",null);
			
		} catch (Exception e) {
			e.printStackTrace();
			f=false;
		}
		assertTrue(f);
	}
	
	/**
	 * 测试邮件发送
	 * 发送给一个人（内/多附件）
	 */
	public void test10send()
	{
		MailNotify mail = new MailNotify(mailCfgKeyId);
		List files = new ArrayList();		
		files.add("d:\\danti001.csv");
		files.add("d:\\danti002.csv");
		boolean f = true;
		try {
			mail.sendmail("xumiao@intasect.com.cn","test10","test new send method",files);
			
		} catch (Exception e) {
			e.printStackTrace();
			f=false;
		}
		assertTrue(f);
	}
	
	/**
	 * 测试邮件发送
	 * 发送给多个人（内外/多附件）
	 */
	public void test11send()
	{
		MailNotify mail = new MailNotify(mailCfgKeyId);
		List files = new ArrayList();		
		files.add("d:\\danti001.csv");
		files.add("d:\\danti002.csv");
		
		List recipients = new ArrayList();		
		recipients.add("xumiao@intasect.com.cn");
		recipients.add("308370117@163.com");
		recipients.add("dengguangping@intasect.com.cn");
		boolean f = true;
		try {
			mail.sendmail(recipients,"test11","test new send method",files);
			
		} catch (Exception e) {
			e.printStackTrace();
			f=false;
		}
		assertTrue(f);
	}
	
	/**
	 * 测试邮件发送
	 * 发送给1个人（内附件超过服务器大小）
	 */
	public void test12send()
	{
		MailNotify mail = new MailNotify(mailCfgKeyId);
		List files = new ArrayList();		
		files.add("E:\\softWare\\office2003兼容2007包.rar");
		
		List recipients = new ArrayList();		
		recipients.add("xumiao@intasect.com.cn");  //27.63M
		boolean f = true;
		try {
			mail.sendmail(recipients,"test12","test new send method",files);
			
		} catch (Exception e) {
			e.printStackTrace();
			f=false;
		}
		assertTrue(f);
	}
	
	
	/**
	 * 测试邮件发送
	 * 发送给多个人（内外/无附件）
	 */
	public void test13send()
	{
		MailNotify mail = new MailNotify(mailCfgKeyId);
		List recipients = new ArrayList();
		
		recipients.add("xumiao@intasect.com.cn");
		recipients.add("xumiaoxxx@intasect.com.cn");//错误地址
		recipients.add("dengguangping@intasect.com.cn");
		
		boolean f = true;
		try {
			mail.sendmail(recipients,"test13","test new send method",null);
			
		} catch (Exception e) {
			e.printStackTrace();
			f=false;
		}
		assertTrue(f);
	}
	
	/**
	 * 测试邮件发送
	 * 发送给1个人（日语）
	 */
	public void test14send()
	{
		MailNotify mail = new MailNotify(mailCfgKeyId);
		List recipients = new ArrayList();
		
		recipients.add("xumiao@intasect.com.cn");
		
		boolean f = true;
		try {
			mail.sendmail(recipients,"test14","七羽の からす からす) 七羽の鸦(からす 男だけ、七人の子供がいる父亲がいました。そこで、どうにかして女の子が 欲しいとね... ",null);
			
		} catch (Exception e) {
			e.printStackTrace();
			f=false;
		}
		assertTrue(f);
	}
}
