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

package org.jpage.core.mail.util;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import org.apache.commons.validator.GenericValidator;
import org.jpage.core.mail.model.MailHeader;
import org.jpage.util.Tools;

public class MailTools {

	public static boolean isMailAddress(String mail) {
		if (mail == null) {
			return false;
		}
		if (mail.indexOf("@") == -1) {
			return false;
		}
		return GenericValidator.isEmail(mail);
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

	public static String convertHTMLContent(String source, Collection headerList) {
		if (headerList == null || headerList.size() == 0) {
			return source;
		}
		source = Tools.replaceIgnoreCase(source, "cid:", "");
		Iterator item = headerList.iterator();
		while (item.hasNext()) {
			MailHeader mailHeader = (MailHeader) item.next();
			source = Tools.replaceIgnoreCase(source, mailHeader.getContentId(),
					mailHeader.getFilename());
		}
		return source;
	}

	public static String encodeFile(String pFilename, boolean needEncode)
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

	public static String chineseStringToAscii(String str) {
		if (str == null) {
			return "";
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

	public static String getSubject(Message message) {
		String mailSubject = "";
		try {
			mailSubject = message.getSubject();
			mailSubject = mailConverter(message.getSubject());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mailSubject;
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
						addrBuffer.append(internetaddress.getAddress()).append(
								" < ").append(internetaddress.getAddress())
								.append(" > ");
					} else {
						addrBuffer.append(internetaddress.getPersonal())
								.append(" < ").append(
										internetaddress.getAddress()).append(
										" > ");
					}
				}
				String addr = addrBuffer.toString();
				if (addr.startsWith("=?GBK?Q?=")
						|| addr.startsWith("=?GB2312?Q?=")) {
					addr = mailConverter(addr);
				}
				mailAddress = addr;
			} catch (Exception ex) {

			}
		}

		return mailAddress;
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
			mailSubject = mailConverter(mailSubject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (mailSubject == null || mailSubject.trim().equals("null")
				|| mailSubject.trim().equals("")) {
			mailSubject = "（无主题）";
		}
		return mailSubject;
	}

	public static String getMailAddress(String mailAddress) {
		if (mailAddress == null) {
			return "";
		}
		try {
			mailAddress = MimeUtility.encodeText(mailAddress);
			if (mailAddress.startsWith("=?GBK?Q?=")
					|| mailAddress.startsWith("=?GB2312?Q?=")) {
				mailAddress = native2unicode(mailAddress);
			} else {
				mailAddress = MimeUtility.decodeText(mailAddress);
			}
			mailAddress = mailConverter(mailAddress);
			mailAddress = Tools.replaceIgnoreCase(mailAddress, "<", " < ");
			mailAddress = Tools.replaceIgnoreCase(mailAddress, ">", " > ");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mailAddress;
	}

	public static String mailConverter(String str) {
		StringBuffer temp = new StringBuffer().append(str);
		if (str != null) {
			String str2 = str.toUpperCase();
			if (((str2.indexOf("=?GBK?Q?")) != -1)
					|| ((str2.indexOf("=?ISO-8859-1?Q?")) != -1)
					|| ((str2.indexOf("=?GB2312?Q?")) != -1)) {
				str = null;
				str = temp.toString();
				str = Tools.replaceIgnoreCase(str, "=?GBK?Q?", "");
				str = Tools.replaceIgnoreCase(str, "=?ISO-8859-1?Q?", "");
				str = Tools.replaceIgnoreCase(str, "=?GB2312?Q?", "");
				str = Tools.replaceIgnoreCase(str, "?=", "");
				byte[] mainByte = str.getBytes();
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
		}
		return str;
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
		buffer
				.append("= = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
		buffer.append(newline);
		return buffer.toString();
	}

	 

}