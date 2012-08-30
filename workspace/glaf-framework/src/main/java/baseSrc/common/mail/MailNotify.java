/**
 * 邮件发送类
 * 
 * 该类实现了ActionHandler接口,可以在流程定义的事件中使用该类进行邮件自动发送
 * 也可以直接使用该类发送邮件,邮件发送将自动记录发送履历
 * 该类还实现了发件箱设置功能,发件箱信息通过常数表保存
 * 
 * MailNotify.java（类名）
 * 1.0.1.0（版本）
 * 作成者：ISC)yx
 * 作成时间：2008-07-24
 * 修改履历：
 *       年   月 日 区分 所 属/担 当   内 容                             版本号
 *     ---------- ---- ----------- ------------------------------  -----------
 */
package baseSrc.common.mail;

import baseSrc.common.LogHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Date;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.jbpm.graph.exe.ExecutionContext;

/**
 * 
 * 邮件发送类
 * 
 * 该类实现了邮件的发送功能。邮件发件人信息根据构造函数入参的mailKey从配置文件中读取。
 * 
 */
public class MailNotify {
	private static LogHelper logger = new LogHelper(MailNotify.class);
	private static final long serialVersionUID = 1L;
	private String mailKey = "";

	// 邮件服务器信息
	private MailServer mailserver = null;
	private boolean isAuth = true;
	private String replyTo = "";
	
	//邮件正文是否为HTML格式
	private boolean isHtmlBody = false;

	private String bodyCharSet = "gb2312";

	/**
	 * 获取邮件正文格式 true - HTML false - TEXT
	 */
	public boolean isHtmlBody() {
		return isHtmlBody;
	}

	/**
	 * 设置邮件正文格式 true - HTML false - TEXT
	 */
	public void setHtmlBody(boolean isHtmlBody) {
		this.isHtmlBody = isHtmlBody;
	}

	/**
	 * 获取邮件正文编码 默认gb2312
	 */
	public String getBodyCharSet() {
		return bodyCharSet;
	}

	/**
	 * 设置邮件正文编码 可忽略，默认gb2312
	 */
	public void setBodyCharSet(String bodyCharSet) {
		this.bodyCharSet = bodyCharSet;
	}
	
	/**
	 * 创造一个邮件发送类。发件人采用默认配置。
	 */
	public MailNotify() {
		try {
			logger.info("MailNotify-mailKey:" + mailKey);
			mailserver = new MailServer(mailKey);
			mailserver.loadMailServer();
			String sendername = mailserver.getUsername();
			mailserver.setSendername(sendername);
			String repeatmai = mailserver.getUsername() + "@"
					+ mailserver.getServerip();
			mailserver.setRepeatmail(repeatmai);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	/**
	 * 创造一个邮件发送类。发件人采用mailKey对应的配置。
	 * @param mailKey 配置文件中对以的发件人Key
	 */
	public MailNotify(String mailKey) {
		try {
			if (mailKey != null) {
				this.mailKey = mailKey;
			}
			logger.info("MailNotify-mailKey:" + mailKey);
			mailserver = new MailServer(mailKey);
			mailserver.loadMailServer();
			String sendername = mailserver.getUsername();
			mailserver.setSendername(sendername);
			String repeatmai = mailserver.getUsername() + "@"
					+ mailserver.getServerip();
			mailserver.setRepeatmail(repeatmai);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	public void execute(ExecutionContext executionContext) throws Exception {
		// TODO Auto-generated method stub
	}

	/**
	 * 
	 * @param recipients
	 *            收件人
	 * @param subject
	 *            主题
	 * @param content
	 *            邮件正文
	 * @param files
	 *            附件列表
	 * @throws IOException
	 *             附件地址不正确时抛出IO异常
	 */
	public void sendmail(String recipients, String subject, String content,
			List files) throws IOException {
		List recipientslist = new ArrayList();
		recipientslist.add(recipients);
		sendmail(recipientslist, subject, content, files);

	}

	/**
	 * 
	 * @param recipients
	 *            收件人列表
	 * @param subject
	 *            主题
	 * @param content
	 *            邮件正文
	 * @param files
	 *            附件列表
	 */
	public void sendmail(List recipients, String subject, String content,
			List files) throws IOException {
		logger.info("开始发送邮件！");

		String recipientsString = "";
		String fromString = mailserver.getUsername() + "@"
				+ mailserver.getServerip();

		// 设置发件箱服务器
		Properties props = System.getProperties();

		if (mailserver.getServerip() != null) {
			props.put("mail.smtp.host", mailserver.getServerip());
		}

		// 认证
		Email_Autherticatorbean auth = new Email_Autherticatorbean(mailserver
				.getUsername(), mailserver.getPassword());
		props.put("mail.transpost.protocol", "smtp");
		if (isAuth()) {
			props.put("mail.smtp.auth", "true");
		}
		System.out.println(mailserver.getServerPort());
		props.put("mail.smpt.port", mailserver.getServerPort());// 设置端口，默认为25

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
			logger.error("邮件服务器设置有误：[" + fromString + "]");
			throw new MailSendException("邮件服务器设置有误：[" + fromString + "]");
		} catch (MessagingException e) {
			// 邮件发送错误
			e.printStackTrace();
			logger.error("邮件服务器设置有误：[" + fromString + "]");
			throw new MailSendException("邮件服务器设置有误：[" + fromString + "]");
		} catch (Exception e) {
			// 其他错误
			e.printStackTrace();
			logger.error("邮件服务器设置有误：[" + fromString + "]");
			throw new MailSendException("邮件服务器设置有误：[" + fromString + "]");
		}
		// 设置收件人
		List<String> tempRecipients = new ArrayList<String>();
		for (int i = 0; i < recipients.size(); i++) {
			logger.info("recipients [" + i + "]:" + (String) recipients.get(i));
			if ((String) recipients.get(i) != null
					&& !"".equals((String) recipients.get(i))) {
				try {
					InternetAddress.parse((String) recipients.get(i));
				} catch (AddressException e) {
					logger.error("邮件地址设置有误：[" + (String) recipients.get(i)
							+ "]");
					throw new MailSendException("邮件地址设置有误：["
							+ (String) recipients.get(i) + "]");
				} catch (MessagingException e) {
					// 邮件发送错误
					e.printStackTrace();
					logger.error("邮件地址设置有误：[" + fromString + "]");
					throw new MailSendException("邮件地址设置有误：[" + fromString + "]");
				} catch (Exception e) {
					// 其他错误
					e.printStackTrace();
					logger.error("邮件地址设置有误：[" + fromString + "]");
					throw new MailSendException("邮件地址设置有误：[" + fromString + "]");
				}
				tempRecipients.add((String) recipients.get(i));
			}
			if (recipients.size() == i + 1) {
				recipientsString += String.valueOf(recipients.get(i));
			} else {
				recipientsString += String.valueOf(recipients.get(i)) + ",";
			}
		}

		try {

			recipients = tempRecipients;
			if (recipients.size() == 0) {
				logger.info("no Recipients!");
				logger.info("Mail Subject:" + subject);
				if ("".equals(getReplyTo())) {
					logger.info("Mail ReplyTo :" + mailserver.getRepeatmail());
				} else {
					logger.info("Mail ReplyTo :" + getReplyTo());
				}
				logger.info("Mail Content:" + content);
				return;
			}

			msg.setRecipients(javax.mail.Message.RecipientType.TO,
					getRecipientsAddress(recipients));
			// 设置回复邮件地址
			if ("".equals(getReplyTo())) {
				msg.setReplyTo(InternetAddress
						.parse(mailserver.getRepeatmail()));
			} else {
				msg.setReplyTo(InternetAddress.parse(getReplyTo()));
			}

			// 设置邮件主题
			msg.setSubject(subject);

			// 如果有附件,添加附件
			String logFiles = "";
			if (files != null && files.size() > 0) {
				for (int fi = 0; fi < files.size(); fi++) {
					logFiles = logFiles + ((String) files.get(fi)).toString()
							+ ";";
				}
				msg.setContent(getMailpart(content, files));
			} else {
				// 无附件直接设置正文
				if (isHtmlBody) {
					MimeMultipart mp = new MimeMultipart();
					MimeBodyPart mbp1 = new MimeBodyPart();
					String formatString = String.format("text/html;charset=%s",
							bodyCharSet);
					mbp1.setContent(content, formatString);
					mp.addBodyPart(mbp1);
					msg.setContent(mp);
				} else {
					msg.setText(content);
				}
			}

			// 设置发送时间
			msg.setSentDate(new Date());

			// 发送邮件
			Transport.send(msg);

			// 写日志
			logger.info("mailLog Begin");
			logger.info("fromString:" + fromString);
			logger.info("recipientsString:" + recipientsString);
			logger.info("Subject:" + msg.getSubject());
			logger.info("content:" + content);
			logger.info("Attachments:" + logFiles);
			System.out.println("mailLog End");
			System.out.println("发送完成！");
		} catch (AddressException e) {
			// 邮件地址错误
			e.printStackTrace();
			logger.info("发送邮件失败！");
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
			logger.info(message);
			throw new MailSendException(message);
		} catch (MessagingException e) {
			// 邮件发送错误
			e.printStackTrace();
			logger.info("发送邮件失败！");
			throw new MailSendException("发送邮件失败！");

		} catch (Exception e) {
			// 其他错误
			e.printStackTrace();
			logger.info("发送邮件失败！");
			throw new MailSendException("发送邮件失败！");
		}
	}

	public void setAuth(boolean isAuth) {
		this.isAuth = isAuth;
	}

	public boolean isAuth() {
		return this.isAuth;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public String getReplyTo() {
		return this.replyTo;
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

	/**
	 * 
	 * @param mailtext
	 *            邮件正文
	 * @param Files
	 *            附件列表
	 * @return
	 * @throws MessagingException
	 */
	private MimeMultipart getMailpart(String mailtext, List files)
			throws MessagingException {
		MimeMultipart mp = new MimeMultipart();

		if (files != null && files.size() > 0) {

			MimeBodyPart mbp1 = new MimeBodyPart();

			if (isHtmlBody) {
				String formatString = String.format("text/html;charset=%s",
						bodyCharSet);
				mbp1.setContent(mailtext, formatString);
			} else {
				mbp1.setText(mailtext);
			}

			mp.addBodyPart(mbp1);

			for (int i = 0; i < files.size(); i++) {
				MimeBodyPart mbp2 = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(String.valueOf(files
						.get(i)));
				mbp2.setDataHandler(new DataHandler(fds));
				mbp2.setFileName(fds.getName());

				mp.addBodyPart(mbp2);
			}

			return mp;
		} else {
			return null;
		}

	}

}
