package glaf.batch.mail;

import glaf.batch.BatchConstans;
import glaf.batch.exception.BatchException;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Mail {
	private final String CONFIG_FILE = "ExceptionMailConfig.xml";
	private Map<String,Map<String,String>> servers = new HashMap<String,Map<String,String>>();
	private Map<String,String> recipients = new HashMap<String,String>(); 
	
	public Mail(){
		getConfigs();
	}
	
	public void sendMail(String mailServerId,String batchName){
		String recipientsString = "";
		Map ser = (Map)servers.get(mailServerId);
		String serIp = (String)ser.get("ip");
		String serUserName = (String)ser.get("username");
		String serPwd = (String)ser.get("pwd");
		String serPort = (String)ser.get("port");
		
		String fromString = serUserName + "@" + serIp;
		
		// 设置发件箱服务器
		Properties props = System.getProperties();

		if (ser.get("ip") != null) {
			props.put("mail.smtp.host", serIp);
		}

		// 认证
		Email_Autherticatorbean auth = new Email_Autherticatorbean(serUserName,serPwd);
		props.put("mail.transpost.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		System.out.println(serPort);
		props.put("mail.smpt.port", serPort);// 设置端口，默认为25

		// 获取发件箱Session
		Session session = Session.getInstance(props, auth);

		// 打印错误到控制台
		session.setDebug(true);

		// 生成邮件对象
		javax.mail.Message msg = new MimeMessage(session);

		// 设置发件人

		try {
			msg.setFrom(new InternetAddress(fromString));
		} catch (AddressException e) {
			throw new MailSendException("邮件服务器设置有误：[" + fromString + "]");
		} catch (MessagingException e) {
			// 邮件发送错误
			e.printStackTrace();
			throw new MailSendException("邮件服务器设置有误：[" + fromString + "]");
		} catch (Exception e) {
			// 其他错误
			e.printStackTrace();
			throw new MailSendException("邮件服务器设置有误：[" + fromString + "]");
		}
		// 设置收件人
		List<String> tempRecipients = new ArrayList<String>();
		String rec = (String)recipients.get(batchName);
		if(null == rec || "".equals(rec)){
			throw new MailSendException("没有设置邮件收件人！");
		}
		
		String[]recs = rec.split(",");

		for (int i = 0; i < recs.length; i++) {
			try {
				InternetAddress.parse(recs[i]);
			} catch (AddressException e) {
				throw new MailSendException("邮件地址设置有误：["
						+ (String) recipients.get(i) + "]");
			} catch (MessagingException e) {
				// 邮件发送错误
				throw new MailSendException("邮件地址设置有误：[" + fromString + "]");
			} catch (Exception e) {
				// 其他错误
				throw new MailSendException("邮件地址设置有误：[" + fromString + "]");
			}
			tempRecipients.add(recs[i]);
			
		}

		try {

			msg.setRecipients(javax.mail.Message.RecipientType.TO,
					getRecipientsAddress(tempRecipients));
			
			// 设置邮件主题
			msg.setSubject("batch(" + batchName + ")执行失败");

			// 无附件直接设置正文
			MimeMultipart mp = new MimeMultipart();
			MimeBodyPart mbp1 = new MimeBodyPart();
			String formatString = "text/html;charset=gb2312";
			mbp1.setContent("batch(" + batchName + ")执行失败(失败时间：" + (new Date()).toLocaleString() +")", formatString);
			mp.addBodyPart(mbp1);
			msg.setContent(mp);

			// 设置发送时间

			msg.setSentDate(new Date());

			// 发送邮件

			Transport.send(msg);

			System.out.println("mailLog End");
			System.out.println("发送完成！");
		} catch (AddressException e) {
			// 邮件地址错误
			e.printStackTrace();
			throw new MailSendException("发送邮件失败！");
		} catch (SendFailedException e) {
			// 邮件发送错误

			e.printStackTrace();
			String message = "";
			if (e.getValidUnsentAddresses() != null) {
				message = "邮件地址有误：[" + recipientsString + "]";

			} else {
				message = "发送邮件失败！";
			}
			throw new MailSendException(message);
		} catch (MessagingException e) {
			// 邮件发送错误

			e.printStackTrace();
			throw new MailSendException("发送邮件失败！");

		} catch (Exception e) {
			// 其他错误
			e.printStackTrace();
			throw new MailSendException("发送邮件失败！");
		}
	}

	private void getConfigs() {
		try {
			// 设置配置文件路径
			String configFile = BatchConstans.BATCH_PATH + "\\configfiles\\" + CONFIG_FILE;
			File f = new File(configFile);
			//读取文件信息
			SAXReader reader = new SAXReader();
			Document doc = reader.read(f);
			Element root = doc.getRootElement();
			Element mailServer = null;
			for (Iterator i = root.elementIterator("mailServer"); i.hasNext();) {
				mailServer = (Element) i.next();
				Map<String,String> ser = new HashMap<String,String>();
				ser.put("ip", (String)mailServer.elementText("ip"));
				ser.put("port", (String)mailServer.elementText("port"));
				ser.put("username", (String)mailServer.elementText("username"));
				ser.put("pwd", (String)mailServer.elementText("pwd"));
				servers.put(mailServer.elementText("id"), ser);
			}
			Element recipient = null;
			for (Iterator i = root.elementIterator("recipients"); i.hasNext();) {
				recipient = (Element) i.next();
				recipients.put(recipient.elementText("batchName"), recipient.elementText("sendAddr"));
			}
		} catch (DocumentException e) {
			throw new BatchException(e);
		}
	}
	
	/**
	 * 获得收件人,从List转换为javamail的InternetAddress[]对象
	 * 
	 * @param recipients
	 *            收件人List
	 * @return
	 * @throws AddressException
	 *             邮件地址错误时抛出异常

	 */
	private InternetAddress[] getRecipientsAddress(List recipients)
			throws AddressException {
		InternetAddress[] recipientsaddress = null;

		if (recipients != null && recipients.size() > 0) {
			recipientsaddress = new InternetAddress[recipients.size()];

			for (int i = 0; i < recipients.size(); i++) {
				recipientsaddress[i] = new InternetAddress(String
						.valueOf(recipients.get(i)));
			}
		}

		return recipientsaddress;
	}

	public Map getServers() {
		return servers;
	}

	public void setServers(Map servers) {
		this.servers = servers;
	}

	public Map getRecipients() {
		return recipients;
	}

	public void setRecipients(Map recipients) {
		this.recipients = recipients;
	}
	
	
}
