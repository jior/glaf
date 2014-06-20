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

package com.glaf.core.util;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public final class StringTools {
	public final static int BUFFER = 4096;
	public final static int IMAGE_SIZE = 120;
	public static final Pattern INT_PATTERN = Pattern.compile("^\\d+$");
	public static final String HTML_END = "</body></html>";
	public static final String HTML_START = "<html><body>";
	private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();
	private static final char[] AMP_ENCODE = "&amp;".toCharArray();
	private static final char[] LT_ENCODE = "&lt;".toCharArray();
	private static final char[] GT_ENCODE = "&gt;".toCharArray();
	private static final String[] emptyStringArray = {};
	private static final char SEPARATOR = '_';

	public static String arrayToString(String[] strs) {
		if (strs.length == 0) {
			return "";
		}
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(strs[0]);
		for (int idx = 1; idx < strs.length; idx++) {
			sbuf.append(",");
			sbuf.append(strs[idx]);
		}
		return sbuf.toString();
	}

	/**
	 * 字节数组转化成16进制形式
	 */
	public static String bytes2string(byte[] src) {
		StringBuilder sb = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				sb.append(0);
			}
			sb.append(hv.toUpperCase());
		}
		return sb.toString();
	}

	public static String camelStyle(String str) {
		if (str == null || str.indexOf("_") == -1) {
			return str;
		}
		StringBuffer buffer = new StringBuffer(str.length() + 100);
		int index = 0;
		StringTokenizer tokenizer = new StringTokenizer(str, "_");
		while (tokenizer.hasMoreTokens()) {
			String tmp = tokenizer.nextToken();
			if (StringUtils.isNotEmpty(tmp)) {
				if (index > 0) {
					tmp = tmp.substring(0, 1).toUpperCase() + tmp.substring(1);
					buffer.append(tmp);
				} else {
					buffer.append(tmp);
				}
			}
			index++;
		}
		return buffer.toString();
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static String chopAtWord(String string, int length) {
		if (string == null || string.length() == 0) {
			return string;
		}
		char[] charArray = string.toCharArray();
		int sLength = string.length();
		if (length < sLength) {
			sLength = length;
		}
		for (int i = 0; i < sLength - 1; i++) {
			if (charArray[i] == '\r' && charArray[i + 1] == '\n') {
				return string.substring(0, i + 1);
			} else if (charArray[i] == '\n') {
				return string.substring(0, i);
			}
		}
		if (charArray[sLength - 1] == '\n') {
			return string.substring(0, sLength - 1);
		}
		if (string.length() < length) {
			return string;
		}
		for (int i = length - 1; i > 0; i--) {
			if (charArray[i] == ' ') {
				return string.substring(0, i).trim();
			}
		}
		return string.substring(0, length);
	}

	/**
	 * 删除字符串中的空格
	 * 
	 * @param in
	 * @return
	 */
	public static String cleanWhitespace(String in) {
		char[] inArray = in.toCharArray();
		StringBuilder out = new StringBuilder(inArray.length);
		boolean lastWasSpace = false;
		for (int i = 0; i < inArray.length; i++) {
			char c = inArray[i];
			if (Character.isWhitespace(c)) {
				if (!lastWasSpace)
					out.append(' ');
				lastWasSpace = true;
			} else {
				out.append(c);
				lastWasSpace = false;
			}
		}
		return out.toString();
	}

	public static String collectionToString(Collection<?> collection) {
		if (collection == null || collection.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		String delim = "";
		for (Object element : collection) {
			sb.append(delim);
			sb.append(element);
			delim = ",";
		}
		return sb.toString();
	}

	public static byte[] decodeHex(String hex) {
		char[] chars = hex.toCharArray();
		byte[] bytes = new byte[chars.length / 2];
		int byteCount = 0;
		for (int i = 0; i < chars.length; i += 2) {
			int newByte = 0x00;
			newByte |= hexCharToByte(chars[i]);
			newByte <<= 4;
			newByte |= hexCharToByte(chars[i + 1]);
			bytes[byteCount] = (byte) newByte;
			byteCount++;
		}
		return bytes;
	}

	public static String encodeHex(byte[] bytes) {
		StringBuilder buff = new StringBuilder(bytes.length * 2);
		int i;
		for (i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buff.append('0');
			}
			buff.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buff.toString();
	}

	public static String encodeHtml(String text) {
		if (text == null) {
			return text;
		}
		int length = text.length();
		if (text != null && length > 0) {
			StringBuffer ret = new StringBuffer(length * 12 / 10);

			boolean isEncodeSpace = true;
			int last = 0;
			for (int i = 0; i < length; i++) {
				char c = text.charAt(i);
				switch (c) {
				case ' ':
					if (isEncodeSpace) {
						if (last < i) {
							ret.append(text.substring(last, i));
						}
						last = i + 1;
						ret.append("&#160;");
						isEncodeSpace = false;
					} else {
						isEncodeSpace = true;
					}
					break;
				case '&':
					if (last < i) {
						ret.append(text.substring(last, i));
					}
					last = i + 1;
					ret.append("&#38;");
					isEncodeSpace = false;
					break;
				case '>':
					if (last < i) {
						ret.append(text.substring(last, i));
					}
					last = i + 1;
					ret.append("&#62;");
					isEncodeSpace = false;
					break;
				case '<':
					if (last < i) {
						ret.append(text.substring(last, i));
					}
					last = i + 1;
					ret.append("&#60;");
					isEncodeSpace = false;
					break;
				case '\"':
					if (last < i) {
						ret.append(text.substring(last, i));
					}
					last = i + 1;
					ret.append("&#34;");
					isEncodeSpace = false;
					break;
				case '\n':
					if (last < i) {
						ret.append(text.substring(last, i));
					}
					last = i + 1;
					ret.append("<br/>");
					isEncodeSpace = false;
					break;

				default:
					isEncodeSpace = false;
					break;
				}
			}

			if (last < length) {
				ret.append(text.substring(last));
			}

			return ret.toString();
		}

		return text;
	}

	public static String escapeForSQL(String string) {
		if (string == null) {
			return null;
		} else if (string.length() == 0) {
			return string;
		}

		char ch;
		char[] input = string.toCharArray();
		int i = 0;
		int last = 0;
		int len = input.length;
		StringBuilder out = null;
		for (; i < len; i++) {
			ch = input[i];
			if (ch == '\'') {
				if (out == null) {
					out = new StringBuilder(len + 2);
				}
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append('\'').append('\'');
			}
		}

		if (out == null) {
			return string;
		} else if (i > last) {
			out.append(input, last, i - last);
		}

		return out.toString();
	}

	public static String escapeForXML(String string) {
		if (string == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = string.toCharArray();
		int len = input.length;
		StringBuilder out = new StringBuilder((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '&') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(AMP_ENCODE);
			} else if (ch == '"') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(QUOTE_ENCODE);
			}
		}
		if (last == 0) {
			return string;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	public static String escapeHTMLTags(String in) {
		if (in == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = in.toCharArray();
		int len = input.length;
		StringBuilder out = new StringBuilder((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '>') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(GT_ENCODE);
			} else if (ch == '\n') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append("<br>");
			}
		}
		if (last == 0) {
			return in;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	/**************************************************************************
	 * Find index of search character in str. This ignores content in () and
	 * 'texts'
	 * 
	 * @param str
	 *            string
	 * @param search
	 *            search character
	 * @return index or -1 if not found
	 */
	public static int findIndexOf(String str, char search) {
		return findIndexOf(str, search, search);
	}

	/**
	 * Find index of search characters in str. This ignores content in () and
	 * 'texts'
	 * 
	 * @param str
	 *            string
	 * @param search1
	 *            first search character
	 * @param search2
	 *            second search character (or)
	 * @return index or -1 if not found
	 */
	public static int findIndexOf(String str, char search1, char search2) {
		if (str == null) {
			return -1;
		}
		int endIndex = -1;
		int parCount = 0;
		boolean ignoringText = false;
		int size = str.length();
		while (++endIndex < size) {
			char c = str.charAt(endIndex);
			if (c == '\'')
				ignoringText = !ignoringText;
			else if (!ignoringText) {
				if (parCount == 0 && (c == search1 || c == search2))
					return endIndex;
				else if (c == ')')
					parCount--;
				else if (c == '(')
					parCount++;
			}
		}
		return -1;
	}

	/**
	 * Find index of search character in str. This ignores content in () and
	 * 'texts'
	 * 
	 * @param str
	 *            string
	 * @param search
	 *            search character
	 * @return index or -1 if not found
	 */
	public static int findIndexOf(String str, String search) {
		if (str == null || search == null || search.length() == 0) {
			return -1;
		}
		int endIndex = -1;
		int parCount = 0;
		boolean ignoringText = false;
		int size = str.length();
		while (++endIndex < size) {
			char c = str.charAt(endIndex);
			if (c == '\'')
				ignoringText = !ignoringText;
			else if (!ignoringText) {
				if (parCount == 0 && c == search.charAt(0)) {
					if (str.substring(endIndex).startsWith(search))
						return endIndex;
				} else if (c == ')')
					parCount--;
				else if (c == '(')
					parCount++;
			}
		}
		return -1;
	}

	public static String formatLine(String sourceString, int length) {
		if (sourceString == null) {
			return "";
		}
		if (sourceString.length() <= length) {
			StringBuffer buffer = new StringBuffer(sourceString.length() + 10);
			int k = length - sourceString.length();
			for (int j = 0; j < k; j++) {
				buffer.append(sourceString).append("&nbsp;");
			}
			sourceString = buffer.toString();
			return sourceString;
		}
		char[] sourceChrs = sourceString.toCharArray();
		char[] distinChrs = new char[length];

		for (int i = 0; i < length; i++) {
			if (i >= sourceChrs.length) {
				return sourceString;
			}
			Character chr = Character.valueOf(sourceChrs[i]);
			if (chr.charValue() <= 202 && chr.charValue() >= 8) {
				distinChrs[i] = chr.charValue();
			} else {
				distinChrs[i] = chr.charValue();
				length--;
			}
		}
		return new String(distinChrs) + "…";
	}

	public static String fromCSVString(String s) {
		if (s.charAt(0) != '\'') {
			throw new RuntimeException("Error deserializing string.");
		}
		int len = s.length();
		StringBuffer sb = new StringBuffer(len - 1);
		for (int i = 1; i < len; i++) {
			char c = s.charAt(i);
			if (c == '%') {
				char ch1 = s.charAt(i + 1);
				char ch2 = s.charAt(i + 2);
				i += 2;
				if (ch1 == '0' && ch2 == '0') {
					sb.append('\0');
				} else if (ch1 == '0' && ch2 == 'A') {
					sb.append('\n');
				} else if (ch1 == '0' && ch2 == 'D') {
					sb.append('\r');
				} else if (ch1 == '2' && ch2 == 'C') {
					sb.append(',');
				} else if (ch1 == '7' && ch2 == 'D') {
					sb.append('}');
				} else if (ch1 == '2' && ch2 == '5') {
					sb.append('%');
				} else {
					throw new RuntimeException("Error deserializing string.");
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String getDigit4Id(int id) {
		String value = String.valueOf(id);
		switch (value.length()) {
		case 1:
			value = "000" + value;
			break;
		case 2:
			value = "00" + value;
			break;
		case 3:
			value = "0" + value;
			break;
		default:
			break;
		}
		return value;
	}

	public static String getDigit8Id(int id) {
		String value = String.valueOf(id);
		switch (value.length()) {
		case 1:
			value = "0000000" + value;
			break;
		case 2:
			value = "000000" + value;
			break;
		case 3:
			value = "00000" + value;
			break;
		case 4:
			value = "0000" + value;
			break;
		case 5:
			value = "000" + value;
			break;
		case 6:
			value = "00" + value;
			break;
		case 7:
			value = "0" + value;
			break;
		default:
			break;
		}
		return value;
	}

	public static Collection<String> getStringCollection(String str) {
		List<String> values = new java.util.ArrayList<String>();
		if (str == null)
			return values;
		StringTokenizer tokenizer = new StringTokenizer(str, ",");
		values = new java.util.ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			values.add(tokenizer.nextToken());
		}
		return values;
	}

	public static String[] getStrings(String str) {
		Collection<String> values = getStringCollection(str);
		if (values.size() == 0) {
			return null;
		}
		return values.toArray(new String[values.size()]);
	}

	public static Collection<String> getTrimmedStringCollection(String str) {
		return Arrays.asList(getTrimmedStrings(str));
	}

	public static String[] getTrimmedStrings(String str) {
		if (null == str || "".equals(str.trim())) {
			return emptyStringArray;
		}

		return str.trim().split("\\s*,\\s*");
	}

	private static byte hexCharToByte(char ch) {
		switch (ch) {
		case '0':
			return 0x00;
		case '1':
			return 0x01;
		case '2':
			return 0x02;
		case '3':
			return 0x03;
		case '4':
			return 0x04;
		case '5':
			return 0x05;
		case '6':
			return 0x06;
		case '7':
			return 0x07;
		case '8':
			return 0x08;
		case '9':
			return 0x09;
		case 'a':
			return 0x0A;
		case 'b':
			return 0x0B;
		case 'c':
			return 0x0C;
		case 'd':
			return 0x0D;
		case 'e':
			return 0x0E;
		case 'f':
			return 0x0F;
		}
		return 0x00;
	}

	public static boolean isInteger(String str) {
		if (str == null || str.length() == 0)
			return false;
		return INT_PATTERN.matcher(str).matches();
	}

	public static String listToString(List<String> strs) {
		return listToString(strs, ",");
	}

	public static String listToString(List<String> strs, String separator) {
		if (strs == null || strs.size() == 0) {
			return "";
		}
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(strs.get(0));
		for (int idx = 1; idx < strs.size(); idx++) {
			sbuf.append(separator);
			sbuf.append(strs.get(idx));
		}
		return sbuf.toString();
	}

	public static String lower(String name) {
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

	public static void main(String[] args) {
		String text = "123,234,345,567,789";
		List<String> rows = StringTools.split(text, ",");
		System.out.println(rows.get(2));
		System.out.println(rows);
		for (int i = 0, len = rows.size(); i < len; i++) {
			System.out.println(rows.get(i));
		}

		Set<String> x = new HashSet<String>();
		for (int i = 0; i < 9999; i++) {
			String str = StringTools.random();
			x.add(str);
			System.out.println(str);
		}
		System.out.println("size=" + x.size());

		Random random = new Random();

		for (int i = 1; i < 100; i++) {
			int k = Math.abs(random.nextInt(9999));
			int a = String.valueOf(k).length();
			int b = String.valueOf(9999).length();
			System.out.println("a=" + a);
			System.out.println("b=" + b);
			String xx = "";
			if (a != b) {
				for (int j = 0; j < (b - a); j++) {
					xx = "0" + xx;
				}
			}
			xx = xx + k;
			System.out.println(xx);
		}

		System.out.println(toUnderLineName("ISOCertifiedStaff"));
		System.out.println(toUnderLineName("CertifiedStaff"));
		System.out.println(toUnderLineName("UserID"));
		System.out.println(toCamelCase("iso_certified_staff"));
		System.out.println(toCamelCase("certified_staff"));
		System.out.println(toCamelCase("user_id"));
	}

	public static int parseInteger(String str) {
		if (!isInteger(str))
			return 0;
		return Integer.parseInt(str);
	}

	public static String random() {
		return random(9999);
	}

	public static String random(int bits) {
		Random random = new Random();
		int x = Math.abs(random.nextInt(bits));
		int a = String.valueOf(x).length();
		int b = String.valueOf(bits).length();
		String xx = "";
		if (a < b) {
			for (int i = 0; i < (b - a); i++) {
				xx = "0" + xx;
			}
		}
		xx = xx + x;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String ret = formatter.format(new Date()) + xx;
		return ret;
	}

	public static String replace(String string, String oldString,
			String newString) {
		if (string == null) {
			return null;
		}
		int i = 0;
		if ((i = string.indexOf(oldString, i)) >= 0) {
			char[] string2 = string.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuilder buf = new StringBuilder(string2.length);
			buf.append(string2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = string.indexOf(oldString, i)) > 0) {
				buf.append(string2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(string2, j, string2.length - j);
			return buf.toString();
		}
		return string;
	}

	public static String replace(String line, String oldString,
			String newString, int[] count) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			int counter = 1;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuilder buf = new StringBuilder(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	public static String replaceFirst(String string, String oldString,
			String newString) {
		if (string == null) {
			return null;
		}
		int i = 0;
		if ((i = string.indexOf(oldString, i)) >= 0) {
			char[] string2 = string.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuilder buf = new StringBuilder(string2.length);
			buf.append(string2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			if ((i = string.indexOf(oldString, i)) > 0) {
				buf.append(string2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(string2, j, string2.length - j);
			return buf.toString();
		}
		return string;
	}

	public static String replaceIgnoreCase(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuilder buf = new StringBuilder(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	public static String replaceIgnoreCase(String line, String oldString,
			String newString, int[] count) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			int counter = 1;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuilder buf = new StringBuilder(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	public static List<String> split(String text) {
		return split(text, ",");
	}

	@SuppressWarnings("unchecked")
	public static List<String> split(String text, String delimiter) {
		if (delimiter == null) {
			throw new RuntimeException("delimiter is null");
		}
		if (text == null) {
			return Collections.EMPTY_LIST;
		}
		List<String> pieces = new java.util.ArrayList<String>();
		int start = 0;
		int end = text.indexOf(delimiter);
		while (end != -1) {
			pieces.add(text.substring(start, end));
			start = end + delimiter.length();
			end = text.indexOf(delimiter, start);
		}
		if (start < text.length()) {
			String temp = text.substring(start);
			if (temp != null && temp.trim().length() > 0) {
				pieces.add(temp);
			}
		}
		return pieces;
	}

	public static String splitLine(String sourceString, int length) {
		if (sourceString == null) {
			return "";
		}
		if (sourceString.length() <= length) {
			return sourceString;
		}
		char[] sourceChrs = sourceString.toCharArray();
		char[] distinChrs = new char[length];

		for (int i = 0; i < length; i++) {
			if (i >= sourceChrs.length) {
				return sourceString;
			}
			Character chr = Character.valueOf(sourceChrs[i]);
			if (chr.charValue() <= 202 && chr.charValue() >= 8) {
				distinChrs[i] = chr.charValue();
			} else {
				distinChrs[i] = chr.charValue();
				length--;
			}
		}
		return new String(distinChrs) + "…";
	}

	public static String[] splitToArray(String text, String delimiter) {
		List<String> result = split(text, delimiter);
		return result.toArray(new String[result.size()]);
	}

	public static List<Integer> splitToInt(String text) {
		return splitToInt(text, ",");
	}

	@SuppressWarnings("unchecked")
	public static List<Integer> splitToInt(String text, String delimiter) {
		if (delimiter == null) {
			throw new RuntimeException("delimiter is null");
		}
		if (text == null) {
			return Collections.EMPTY_LIST;
		}
		List<Integer> pieces = new java.util.ArrayList<Integer>();
		int start = 0;
		int end = text.indexOf(delimiter);
		while (end != -1) {
			String tmp = text.substring(start, end);
			if (StringUtils.isNotEmpty(tmp) && StringUtils.isNumeric(tmp)) {
				pieces.add(Integer.parseInt(tmp));
			}
			start = end + delimiter.length();
			end = text.indexOf(delimiter, start);
		}
		if (start < text.length()) {
			String tmp = text.substring(start);
			if (tmp != null && tmp.trim().length() > 0) {
				if (StringUtils.isNumeric(tmp)) {
					pieces.add(Integer.parseInt(tmp));
				}
			}
		}
		return pieces;
	}

	public static List<Long> splitToLong(String text) {
		return splitToLong(text, ",");
	}

	@SuppressWarnings("unchecked")
	public static List<Long> splitToLong(String text, String delimiter) {
		if (delimiter == null) {
			throw new RuntimeException("delimiter is null");
		}
		if (text == null) {
			return Collections.EMPTY_LIST;
		}
		List<Long> pieces = new java.util.ArrayList<Long>();
		int start = 0;
		int end = text.indexOf(delimiter);
		while (end != -1) {
			String tmp = text.substring(start, end);
			if (StringUtils.isNotEmpty(tmp) && StringUtils.isNumeric(tmp)) {
				pieces.add(Long.parseLong(tmp));
			}
			start = end + delimiter.length();
			end = text.indexOf(delimiter, start);
		}
		if (start < text.length()) {
			String tmp = text.substring(start);
			if (tmp != null && tmp.trim().length() > 0) {
				if (StringUtils.isNumeric(tmp)) {
					pieces.add(Long.parseLong(tmp));
				}
			}
		}
		return pieces;
	}

	/**
	 * 16进制字符串转化成字节数组
	 */
	public static byte[] string2bytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	public static Collection<String> stringToCollection(String string) {
		if (string == null || string.trim().length() == 0) {
			return Collections.emptyList();
		}
		Collection<String> collection = new java.util.ArrayList<String>();
		StringTokenizer tokens = new StringTokenizer(string, ",");
		while (tokens.hasMoreTokens()) {
			collection.add(tokens.nextToken().trim());
		}
		return collection;
	}

	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}

		s = s.toLowerCase();

		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static String toCapitalizeCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = toCamelCase(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public static String toCSVString(String s) {
		StringBuffer sb = new StringBuffer(s.length() + 1);
		sb.append('\'');
		int len = s.length();
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\0':
				sb.append("%00");
				break;
			case '\n':
				sb.append("%0A");
				break;
			case '\r':
				sb.append("%0D");
				break;
			case ',':
				sb.append("%2C");
				break;
			case '}':
				sb.append("%7D");
				break;
			case '%':
				sb.append("%25");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String[] toLowerCaseWordArray(String text) {
		if (text == null || text.length() == 0) {
			return new String[0];
		}

		List<String> wordList = new java.util.ArrayList<String>();
		BreakIterator boundary = BreakIterator.getWordInstance();
		boundary.setText(text);
		int start = 0;

		for (int end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary
				.next()) {
			String tmp = text.substring(start, end).trim();
			tmp = replace(tmp, "+", "");
			tmp = replace(tmp, "/", "");
			tmp = replace(tmp, "\\", "");
			tmp = replace(tmp, "#", "");
			tmp = replace(tmp, "*", "");
			tmp = replace(tmp, ")", "");
			tmp = replace(tmp, "(", "");
			tmp = replace(tmp, "&", "");
			if (tmp.length() > 0) {
				wordList.add(tmp);
			}
		}
		return wordList.toArray(new String[wordList.size()]);
	}

	public static String toUnderLineName(String s) {
		if (s == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			boolean nextUpperCase = true;

			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}

			if ((i >= 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					if (i > 0)
						sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}

			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}

	public static String unescapeFromXML(String string) {
		string = replace(string, "&lt;", "<");
		string = replace(string, "&gt;", ">");
		string = replace(string, "&quot;", "\"");
		return replace(string, "&amp;", "&");
	}

	public static String upper(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	private StringTools() {
	}

}