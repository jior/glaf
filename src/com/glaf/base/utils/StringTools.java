package com.glaf.base.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.ParserException;

public final class StringTools {
	public final static int BUFFER = 4096;
	public final static int IMAGE_SIZE = 120;
	public static final String HTML_END = "</body></html>";
	public static final String HTML_START = "<html><body>";
	private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();
	private static final char[] AMP_ENCODE = "&amp;".toCharArray();
	private static final char[] LT_ENCODE = "&lt;".toCharArray();
	private static final char[] GT_ENCODE = "&gt;".toCharArray();
	public static final String[] emptyStringArray = {};

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

	/** Same as byteToHexString(bytes, 0, bytes.length). */
	public static String byteToHexString(byte bytes[]) {
		return byteToHexString(bytes, 0, bytes.length);
	}

	/**
	 * Given an array of bytes it will convert the bytes to a hex string
	 * representation of the bytes
	 * 
	 * @param bytes
	 * @param start
	 *            start index, inclusively
	 * @param end
	 *            end index, exclusively
	 * @return hex string representation of the byte array
	 */
	public static String byteToHexString(byte[] bytes, int start, int end) {
		if (bytes == null) {
			throw new IllegalArgumentException("bytes == null");
		}
		StringBuilder s = new StringBuilder();
		for (int i = start; i < end; i++) {
			s.append(String.format("%02x", bytes[i]));
		}
		return s.toString();
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

	/**
	 * Escapes HTML Special characters present in the string.
	 * 
	 * @param string
	 * @return HTML Escaped String representation
	 */
	public static String escapeHTML(String string) {
		if (string == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		boolean lastCharacterWasSpace = false;
		char[] chars = string.toCharArray();
		for (char c : chars) {
			if (c == ' ') {
				if (lastCharacterWasSpace) {
					lastCharacterWasSpace = false;
					sb.append("&nbsp;");
				} else {
					lastCharacterWasSpace = true;
					sb.append(" ");
				}
			} else {
				lastCharacterWasSpace = false;
				switch (c) {
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '&':
					sb.append("&amp;");
					break;
				case '"':
					sb.append("&quot;");
					break;
				default:
					sb.append(c);
					break;
				}
			}
		}

		return sb.toString();
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

	public static String extractText(InputStream in, String encoding)
			throws ParserException, UnsupportedEncodingException {
		Parser parser = new Parser();
		Lexer lexer = new Lexer();
		Page page = new Page(in, encoding);
		lexer.setPage(page);
		parser.setLexer(lexer);
		StringBean stringBean = new StringBean();
		parser.visitAllNodesWith(stringBean);
		return stringBean.getStrings();
	}

	public static String extractText(String content, String encoding)
			throws ParserException, UnsupportedEncodingException {
		StringBuffer newContent = new StringBuffer(content.length() + 32);
		newContent.append(HTML_START);
		newContent.append(content);
		newContent.append(HTML_END);
		InputStream in = new ByteArrayInputStream(newContent.toString()
				.getBytes(encoding));
		return extractText(in, encoding);
	}

	public static String formatLine(String sourceString, int length) {
		if (sourceString == null) {
			return "";
		}
		if (sourceString.length() <= length) {
			StringBuffer buffer = new StringBuffer();
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
		return new String(distinChrs) + "бн";
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
		List<String> values = new ArrayList<String>();
		if (str == null)
			return values;
		StringTokenizer tokenizer = new StringTokenizer(str, ",");
		values = new ArrayList<String>();
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

	/**
	 * Given a hexstring this will return the byte array corresponding to the
	 * string
	 * 
	 * @param hex
	 *            the hex String array
	 * @return a byte array that is a hex string representation of the given
	 *         string. The size of the byte array is therefore hex.length/2
	 */
	public static byte[] hexStringToByte(String hex) {
		byte[] bts = new byte[hex.length() / 2];
		for (int i = 0; i < bts.length; i++) {
			bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2),
					16);
		}
		return bts;
	}

	public static String lower(String name) {
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

	public static void main(String[] args) {
		String text = "123,234,345,567,789";
		List<String> rows = StringTools.split(text, ",");
		System.out.println(rows.get(2));
		System.out.println(rows);
		for (int i = 0; i < rows.size(); i++) {
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
		List<String> pieces = new ArrayList<String>();
		int start = 0;
		int end = text.indexOf(delimiter);
		while (end != -1) {
			pieces.add(text.substring(start, end));
			start = end + delimiter.length();
			end = text.indexOf(delimiter, start);
		}
		if (start < text.length()) {
			String temp = text.substring(start);
			if (temp != null && temp.length() > 0) {
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
		return new String(distinChrs) + "бн";
	}

	public static String[] splitToArray(String text, String delimiter) {
		List<String> result = split(text, delimiter);
		return result.toArray(new String[result.size()]);
	}

	public static Collection<String> stringToCollection(String string) {
		if (string == null || string.trim().length() == 0) {
			return Collections.emptyList();
		}
		Collection<String> collection = new ArrayList<String>();
		StringTokenizer tokens = new StringTokenizer(string, ",");
		while (tokens.hasMoreTokens()) {
			collection.add(tokens.nextToken().trim());
		}
		return collection;
	}

	public static String[] toLowerCaseWordArray(String text) {
		if (text == null || text.length() == 0) {
			return new String[0];
		}

		List<String> wordList = new ArrayList<String>();
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