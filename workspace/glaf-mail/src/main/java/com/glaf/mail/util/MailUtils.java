/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glaf.mail.util;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.mail.MailMessage;
import com.glaf.mail.MailThread;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.ThreadFactory;

public class MailUtils {

	protected final static Log logger = LogFactory.getLog(MailUtils.class);

	public static void send(String to, String subject, String content) {
		MailMessage mailMessage = new MailMessage();
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setContent(content);

		logger.debug("subject:" + subject);
		logger.debug(content);

		if (LogUtils.isDebug()) {
			logger.debug(content);
		}
		MailThread thread = new MailThread(mailMessage);
		ThreadFactory.run(thread);
	}

	public static String chineseStringToAscii(String str) {
		if (str == null) {
			return null;
		}
		try {
			byte[] source = str.getBytes();
			char[] dest = new char[source.length];
			for (int i = 0; i < source.length; i++) {
				dest[i] = (char) (source[i] & 0xFF);
			}
			return new String(dest);
		} catch (Exception ex) {
			return str;
		}
	}

	public static String getFromBASE64(String base64String) {
		if (base64String == null) {
			return null;
		}
		try {
			byte[] b = Base64.decodeBase64(base64String);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}

	public static String convertString(String str) {
		String temp = str;
		if (str != null) {
			String str2 = str.toUpperCase();
			if (((str2.indexOf("=?GBK?Q?")) != -1)
					|| ((str2.indexOf("=?ISO-8859-1?Q?")) != -1)
					|| ((str2.indexOf("=?GB2312?Q?")) != -1)) {
				temp = StringTools.replaceIgnoreCase(temp, "=?GBK?Q?", "");
				temp = StringTools.replaceIgnoreCase(temp, "=?ISO-8859-1?Q?",
						"");
				temp = StringTools.replaceIgnoreCase(temp, "=?GB2312?Q?", "");
				temp = StringTools.replaceIgnoreCase(temp, "?=", "");
				byte[] mainByte = temp.getBytes();
				byte[] remain = new byte[mainByte.length];
				int index = 0;
				int i = 0;
				while (index < mainByte.length) {
					if (mainByte[index] == '=') {
						index++;
						byte a1 = mainByte[index];
						if (a1 >= 65) {
							a1 -= 55;
						} else {
							a1 -= 48;
						}
						index++;
						byte a2 = mainByte[index];
						if (a2 >= 65) {
							a2 -= 55;
						} else {
							a2 -= 48;
						}

						remain[i] = (byte) (a1 * 16 + a2);

						index++;
						i++;
					} else {
						remain[i] = mainByte[index];
						i++;
						index++;
					}
				}
				str = null;
				str = new String(remain).substring(0, i);
			}
			str = replaceBase64Decode(temp);
		}
		return str;
	}

	public static String replaceBase64Decode(String str) {
		if (str.toUpperCase().indexOf("=?GBK?B?") != -1
				&& str.indexOf("?=") != -1) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < str.length(); i++) {
				if (str.toUpperCase().indexOf("=?GBK?B?") != -1
						&& str.indexOf("?=") != -1) {
					sb.append(str.substring(0,
							str.toUpperCase().indexOf("=?GBK?B?")));
					String temp = str.substring(
							str.toUpperCase().indexOf("=?GBK?B?") + 8,
							str.indexOf("?="));
					temp = getFromBASE64(temp);
					sb.append(temp);
					if (str.indexOf("?=") != -1) {
						str = str
								.substring(str.indexOf("?=") + 2, str.length());
					}
				}
			}
			str = sb.toString();
		} else if (str.toUpperCase().indexOf("=?GB2312?B?") != -1
				&& str.indexOf("?=") != -1) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < str.length(); i++) {
				if (str.toUpperCase().indexOf("=?GB2312?B?") != -1
						&& str.indexOf("?=") != -1) {
					sb.append(str.substring(0,
							str.toUpperCase().indexOf("=?GB2312?B?")));
					String temp = str.substring(
							str.toUpperCase().indexOf("=?GB2312?B?") + 11,
							str.indexOf("?="));
					temp = getFromBASE64(temp);
					sb.append(temp);
					if (str.indexOf("?=") != -1) {
						str = str
								.substring(str.indexOf("?=") + 2, str.length());
					}
				}
			}
			str = sb.toString();
		}
		return str;
	}

	public static String encodeFilename(String pFilename, boolean needEncode)
			throws Exception {
		String filename = pFilename;
		try {
			filename = MimeUtility.decodeText(filename);
		} catch (Exception ex) {
			filename = "未知类型文件";
		}
		boolean b_else = needEncode;
		if (b_else) {
			String s = filename;
			try {
				filename = new String(s.getBytes("8859-1"));
			} catch (Exception ex) {
				try {
					filename = new String(s.getBytes("ISO-8859-1"));
					if (filename.indexOf("?") != -1) {
						filename = new String(s.getBytes("GBK"));
					}
				} catch (Exception eee) {
					filename = new String(s.getBytes("GBK"));
				}
			}
		} else {
			filename = new String(filename.getBytes("8859_1"));
		}
		return filename;
	}

	public static String getMailAddress(Address aaddress[]) {
		String mailAddress = "";
		if (aaddress != null) {
			try {
				StringBuffer addrBuffer = new StringBuffer();
				for (int j = 0; j < aaddress.length; j++) {
					InternetAddress internetaddress = (InternetAddress) aaddress[j];
					if (internetaddress.getPersonal() == null
							|| internetaddress.getPersonal().length() == 0) {
						addrBuffer.append(internetaddress.getAddress())
								.append(" < ")
								.append(internetaddress.getAddress())
								.append(" > ");
					} else {
						addrBuffer.append(internetaddress.getPersonal())
								.append(" < ")
								.append(internetaddress.getAddress())
								.append(" > ");
					}
				}
				String addr = addrBuffer.toString();
				if (addr.startsWith("=?GBK?Q?=")
						|| addr.startsWith("=?GB2312?Q?=")) {
					addr = convertString(addr);
				}
				mailAddress = addr;
			} catch (Exception ex) {
			}
		}

		return mailAddress;
	}

	public static String getMailAddress(String mailAddress) {
		if (mailAddress == null) {
			return null;
		}
		try {
			mailAddress = MimeUtility.encodeText(mailAddress);
			if (mailAddress.startsWith("=?GBK?Q?=")
					|| mailAddress.startsWith("=?GB2312?Q?=")) {
				mailAddress = native2unicode(mailAddress);
			} else {
				mailAddress = MimeUtility.decodeText(mailAddress);
			}
			mailAddress = convertString(mailAddress);
			mailAddress = StringTools
					.replaceIgnoreCase(mailAddress, "<", " < ");
			mailAddress = StringTools
					.replaceIgnoreCase(mailAddress, ">", " > ");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mailAddress;
	}

	public static String getMailText(int type, String text) {
		String mailText = "";
		if (text == null || text.trim().equalsIgnoreCase("null")) {
			return mailText;
		}
		String newline = System.getProperty("line.separator");
		StringBuffer buffer = new StringBuffer();
		buffer.append(newline);
		buffer.append(newline);
		if (type == 1) {
			buffer.append(newline);
			buffer.append(newline);
			buffer.append("=================== 您在来信中写道：====================");
			buffer.append(newline);
		}
		if (type == 2) {
			buffer.append(newline);
			buffer.append("********************下面是转发邮件**********************");
		}

		StringTokenizer token = new StringTokenizer(text, "\r\n");
		while (token.hasMoreTokens()) {
			String temp = token.nextToken();
			buffer.append(newline);
			buffer.append(">").append(temp);
		}
		buffer.append(newline);
		buffer.append(newline);
		buffer.append("= = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
		buffer.append(newline);
		return buffer.toString();
	}

	public static String getSubject(Message message) {
		String mailSubject = "";
		try {
			mailSubject = message.getSubject();
			mailSubject = convertString(message.getSubject());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mailSubject;
	}

	public static String getSubject(String mailSubject) {
		if (mailSubject == null) {
			mailSubject = "（无主题）";
			return mailSubject;
		}
		try {
			mailSubject = MimeUtility.encodeText(mailSubject);
			if (mailSubject.startsWith("=?GBK?Q?=")
					|| mailSubject.startsWith("=?GB2312?Q?=")) {
				mailSubject = native2unicode(mailSubject);
			} else {
				mailSubject = MimeUtility.decodeText(mailSubject);
			}
			mailSubject = convertString(mailSubject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (mailSubject == null || mailSubject.trim().equals("null")
				|| mailSubject.trim().equals("")) {
			mailSubject = "（无主题）";
		}
		return mailSubject;
	}

	/**
	 * 按类型返回正文
	 * 
	 * @param pText
	 *            原正文
	 * @return 处理后的正文
	 * @throws Exception
	 */
	public static String getText(String pText, String name, String mail)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n您好！");
		sb.append("\n    " + pText);
		sb.append("\n\n     致");
		sb.append("\n礼！");
		sb.append("\n");
		sb.append("\n        ").append(name);
		sb.append("\n        ").append(mail);
		java.util.Date date = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 EEE",
				Locale.getDefault());
		String lastdate = formatter.format(date);
		sb.append("\n        " + lastdate);
		return sb.toString();
	}

	public static String native2unicode(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		byte abyte0[] = new byte[s.length()];
		for (int i = 0; i < s.length(); i++) {
			abyte0[i] = (byte) s.charAt(i);
		}

		return new String(abyte0);
	}

	public static String unicode2native(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char ac[] = new char[s.length() * 2];
		int i = 0;
		for (int j = 0; j < s.length(); j++) {
			if (s.charAt(j) >= '\u0100') {
				char c = s.charAt(j);
				byte abyte0[] = ("" + c).getBytes();
				ac[i++] = (char) abyte0[0];
				ac[i++] = (char) abyte0[1];
			} else {
				ac[i++] = s.charAt(j);
			}
		}

		return new String(ac, 0, i);
	}

	private MailUtils() {

	}

	public static void main(String[] args) {
		System.out
				.println(MailUtils
						.replaceBase64Decode("职工=?GBK?B?am95KLvGs6/OxCnU2jIwMTMtMDEtMjEtMjAxMy0wMS0yONauvOS1xFRpbWVTaGVldLK7?="));
		System.out
				.println(MailUtils
						.replaceBase64Decode("职工=?GBK?B?am95KLvGs6/OxCnU2jIwMTMtMDEtMjEtMjAxMy0wMS0yONauvOS1xFRpbWVTaGVldLK7?==?GBK?B?ubujoQ==?="));
		System.out
				.println(MailUtils
						.replaceBase64Decode("职工=?GB2312?B?am95KLvGs6/OxCnU2jIwMTMtMDEtMjEtMjAxMy0wMS0yONauvOS1xFRpbWVTaGVldLK7?="));
		System.out
				.println(MailUtils
						.replaceBase64Decode("职工=?GB2312?B?am95KLvGs6/OxCnU2jIwMTMtMDEtMjEtMjAxMy0wMS0yONauvOS1xFRpbWVTaGVldLK7?==?GB2312?B?ubujoQ==?="));

	}

}
