package com.glaf.core.mail.business;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.security.DigestUtil;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.UUID32;

import com.glaf.core.mail.Mail;
import com.glaf.core.mail.domain.MailDataFile;
import com.glaf.core.mail.service.IMailService;
import com.glaf.core.mail.util.MailUtils;

public class ReceiveMailBean {

	protected static final Log logger = LogFactory
			.getLog(ReceiveMailBean.class);

	public static void main(String[] args) throws Exception {
		ReceiveMailBean rm = new ReceiveMailBean();
		rm.receiveMail("pop3.163.com", 110, "cinsoft2013@163.com",
				"cinsoft@2013", "c:/temp");
	}

	private MimeMessage mimeMessage = null;
	private Mail mail = null;
	private String attchSavePath = null;
	private String dateFormat = "yyyy-MM-dd HH:mm";

	public ReceiveMailBean() {

	}

	public ReceiveMailBean(MimeMessage mimeMessage, Mail mail) {
		this.mimeMessage = mimeMessage;
		this.mail = mail;
	}

	public String getAttchSavePath() {
		return attchSavePath;
	}

	/**
	 * ��ȡ�����ʼ�����Ϣ
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getFrom() throws MessagingException {
		InternetAddress[] address = (InternetAddress[]) mimeMessage.getFrom();
		String from = address[0].getAddress();
		if (from == null) {
			from = "";
		}
		String personal = address[0].getPersonal();
		if (personal == null) {
			personal = "";
		}
		String fromaddr = personal + "<" + from + ">";
		return fromaddr;
	}

	/**
	 * ��ȡ�ʼ��ռ��ˣ����ͣ����͵ĵ�ַ����Ϣ�����������ݵĲ�����ͬ "to"-->�ռ���,"cc"-->�����˵�ַ,"bcc"-->���͵�ַ
	 * 
	 * @param type
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public String getMailAddress(String type) throws MessagingException,
			UnsupportedEncodingException {
		String mailaddr = "";
		String addrType = type.toUpperCase();
		InternetAddress[] address = null;

		if (addrType.equals("TO") || addrType.equals("CC")
				|| addrType.equals("BCC")) {
			if (addrType.equals("TO")) {
				address = (InternetAddress[]) mimeMessage
						.getRecipients(Message.RecipientType.TO);
			}
			if (addrType.equals("CC")) {
				address = (InternetAddress[]) mimeMessage
						.getRecipients(Message.RecipientType.CC);
			}
			if (addrType.equals("BCC")) {
				address = (InternetAddress[]) mimeMessage
						.getRecipients(Message.RecipientType.BCC);
			}

			if (address != null) {
				for (int i = 0; i < address.length; i++) {
					String mail = address[i].getAddress();
					if (mail == null) {
						mail = "";
					} else {
						mail = MimeUtility.decodeText(mail);
					}
					String personal = address[i].getPersonal();
					if (personal == null) {
						personal = "";
					} else {
						personal = MimeUtility.decodeText(personal);
					}
					String compositeto = personal + "<" + mail + ">";
					mailaddr += "," + compositeto;
				}
				mailaddr = mailaddr.substring(1);
			}
		} else {
			throw new RuntimeException("Error email Type!");
		}
		mailaddr = MailUtils.convertString(mailaddr);
		return mailaddr;
	}

	/**
	 * �����ʼ������õ����ʼ����ݱ��浽һ��stringBuffer�����У������ʼ� ��Ҫ����MimeType�Ĳ�ִͬ�в�ͬ�Ĳ�����һ��һ���Ľ���
	 * 
	 * @param part
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void getMailContent(Part part) throws MessagingException,
			IOException {
		String contentType = part.getContentType();
		int nameindex = contentType.indexOf("name");
		boolean conname = false;
		if (nameindex != -1) {
			conname = true;
		}
		logger.debug("CONTENTTYPE:" + contentType);
		if (part.isMimeType("text/plain") && !conname) {
			mail.setContent((String) part.getContent());
		} else if (part.isMimeType("text/html") && !conname) {
			mail.setHtml((String) part.getContent());
		} else if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			int count = multipart.getCount();
			for (int i = 0; i < count; i++) {
				getMailContent(multipart.getBodyPart(i));
			}
		} else if (part.isMimeType("message/rfc822")) {
			getMailContent((Part) part.getContent());
		}

	}

	public Mail getMail() {
		return mail;
	}

	/**
	 * ��ȡ���ʼ���message-id
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getMessageId() throws MessagingException {
		return mimeMessage.getMessageID();
	}

	public MimeMessage getMimeMessage() {
		return mimeMessage;
	}

	/**
	 * �ж��ʼ��Ƿ���Ҫ��ִ�������ִ����true�����򷵻�false
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public boolean getReplySign() throws MessagingException {
		boolean replySign = false;
		String needreply[] = mimeMessage
				.getHeader("Disposition-Notification-TO");
		if (needreply != null) {
			replySign = true;
		}
		return replySign;
	}

	/**
	 * ��ȡ�ʼ���������
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public String getSendDate() throws MessagingException {
		Date sendDate = mimeMessage.getSentDate();
		SimpleDateFormat smd = new SimpleDateFormat(dateFormat);
		return smd.format(sendDate);
	}

	/**
	 * ��ȡ�ʼ�����
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	public String getSubject() throws UnsupportedEncodingException,
			MessagingException {
		String subject = "";
		if (mimeMessage.getSubject() != null) {
			String text = mimeMessage.getSubject();
			logger.debug("orgi:" + text);
			text = MimeUtility.decodeText(mimeMessage.getSubject());
			logger.debug("MimeUtility.decodeText:" + text);
			text = MailUtils.convertString(mimeMessage.getSubject());
			logger.debug("MailUtils.convertString:" + text);
			subject = text;
		}
		if (subject == null) {
			subject = "";
		}
		return subject;
	}

	/**
	 * �ж����Ƿ��������
	 * 
	 * @param part
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	public boolean isContainAttch(Part part) throws MessagingException,
			IOException {
		boolean flag = false;
		if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			int count = multipart.getCount();
			for (int i = 0; i < count; i++) {
				BodyPart bodypart = multipart.getBodyPart(i);
				String dispostion = bodypart.getDisposition();
				if ((dispostion != null)
						&& (dispostion.equals(Part.ATTACHMENT) || dispostion
								.equals(Part.INLINE))) {
					flag = true;
				} else if (bodypart.isMimeType("multipart/*")) {
					flag = isContainAttch(bodypart);
				} else {
					String contentType = bodypart.getContentType();
					if (contentType.toLowerCase().indexOf("appliaction") != -1) {
						flag = true;
					}
					if (contentType.toLowerCase().indexOf("name") != -1) {
						flag = true;
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			flag = isContainAttch((Part) part.getContent());
		}

		return flag;
	}

	/**
	 * �жϴ��ʼ��Ƿ��Ѷ������δ���򷵻�false���Ѷ�����true
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public boolean isNew() throws MessagingException {
		boolean isnew = false;
		Flags flags = ((Message) mimeMessage).getFlags();
		Flags.Flag[] flag = flags.getSystemFlags();
		logger.debug("flags's length:" + flag.length);
		for (int i = 0; i < flag.length; i++) {
			if (flag[i] == Flags.Flag.SEEN) {
				isnew = true;
				logger.debug("seen message .......");
				break;
			}
		}

		return isnew;
	}

	public void receive(Part part, int i) throws MessagingException,
			IOException {
		logger.debug("------------------START-----------------------");
		mail.setMessageId(this.getMessageId());
		mail.setSendDate(mimeMessage.getSentDate());
		mail.setSubject(this.getSubject());
		mail.setMailFrom(this.getFrom());
		mail.setMailTo(this.getMailAddress("TO"));
		mail.setMailCC(this.getMailAddress("CC"));

		logger.debug("Message" + i + " subject:" + getSubject());
		logger.debug("Message" + i + " from:" + getFrom());
		logger.debug("Message" + i + " isNew:" + isNew());
		boolean flag = isContainAttch(part);
		logger.debug("Message" + i + " isContainAttch:" + flag);
		logger.debug("Message" + i + " replySign:" + getReplySign());
		getMailContent(part);
		// logger.debug("Message" + i + " content:" + getBodyText());

		if (flag) {
			saveAttachment(part);
		}
		logger.debug("------------------END-----------------------");
	}

	public void receiveMail(String popServer, int port, String username,
			String password, String attachSavePath) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		URLName urlname = new URLName("pop3", popServer, port, null, username,
				password);
		Store store = null;
		try {
			store = session.getStore(urlname);
			store.connect();
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			Message msgs[] = folder.getMessages();
			int count = msgs.length;
			logger.debug("Message Count:" + count);
			Mail mail = null;
			IMailService mailService = ContextFactory.getBean("mailService");
			logger.debug(mailService.nextId());
			for (int i = 0; i < count; i++) {
				MimeMessage msg = (MimeMessage) msgs[i];
				if (mailService.getMailByMessageId(msg.getMessageID()) == null) {
					try {
						mail = new Mail();
						mail.setUsername(username);
						this.setMimeMessage(msg);
						this.setMail(mail);
						if (attachSavePath != null) {
							String md5_str = DigestUtil.digestString(
									msg.getMessageID(), "MD5");
							String hex_str = Hex.encodeHexString(md5_str
									.getBytes());
							FileUtils.mkdirsWithExistsCheck(new File(
									attachSavePath + FileUtils.sp
											+ mail.getUsername() + FileUtils.sp));
							this.setAttchSavePath(attachSavePath + FileUtils.sp
									+ mail.getUsername() + FileUtils.sp
									+ hex_str);
						}
						this.receive(msgs[i], i);
						mailService.saveMail(mail);
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						mail = null;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (store != null) {
					store.close();
					store = null;
				}
			} catch (MessagingException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * ���渽��
	 * 
	 * @param part
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void saveAttachment(Part part) throws MessagingException,
			IOException {
		String filename = "";
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mpart = mp.getBodyPart(i);
				String dispostion = mpart.getDisposition();
				if ((dispostion != null)
						&& (dispostion.equals(Part.ATTACHMENT) || dispostion
								.equals(Part.INLINE))) {
					filename = mpart.getFileName();
					if (filename != null) {
						logger.debug("orig filename=" + filename);
						if (filename.indexOf("?") != -1) {
							filename = new String(filename.getBytes("GBK"),
									"UTF-8");
						}
						if (filename.toLowerCase().indexOf("gb2312") != -1) {
							filename = MimeUtility.decodeText(filename);
						}
						filename = MailUtils.convertString(filename);
						logger.debug("filename=" + filename);
						saveFile(filename, mpart.getInputStream());
					}
				} else if (mpart.isMimeType("multipart/*")) {
					saveAttachment(mpart);
				} else {
					filename = mpart.getFileName();
					if (filename != null) {
						logger.debug("orig filename=" + filename);
						if (filename.indexOf("?") != -1) {
							filename = new String(filename.getBytes("GBK"),
									"UTF-8");
						}
						if (filename.toLowerCase().indexOf("gb2312") != -1) {
							filename = MimeUtility.decodeText(filename);
						}
						filename = MailUtils.convertString(filename);
						saveFile(filename, mpart.getInputStream());
						logger.debug("filename=" + filename);
					}
				}
			}

		} else if (part.isMimeType("message/rfc822")) {
			saveAttachment((Part) part.getContent());
		}
	}

	/**
	 * �����ļ�����
	 * 
	 * @param filename
	 * @param inputStream
	 * @throws IOException
	 */
	public void saveFile(String filename, InputStream inputStream) {
		if (filename == null) {
			return;
		}
		String sepatror = FileUtils.sp;
		try {
			filename = MailUtils.convertString(filename);
			filename = FileUtils.replaceWin32FileName(filename);
			logger.debug("filename:" + filename);
			byte[] bytes = FileUtils.getBytes(inputStream);
			MailDataFile att = new MailDataFile();
			att.setCreateDate(new Date());
			att.setFileContent(bytes);
			att.setFilename(filename);
			att.setSize(bytes.length);
			att.setFileId(UUID32.getUUID());
			mail.addFile(att);
			if (StringUtils.isNotEmpty(getAttchSavePath())) {
				File storeDir = new File(getAttchSavePath());
				FileUtils.mkdirsWithExistsCheck(storeDir);
				String storefile = getAttchSavePath() + sepatror + filename;
				FileUtils.save(storefile, bytes);
				logger.debug("store file's path:" + storefile.toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setAttchSavePath(String attchSavePath) {
		this.attchSavePath = attchSavePath;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}

}