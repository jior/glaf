/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.exceptions.InputInvalidException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Tools {
	private final static Log logger = LogFactory.getLog(Tools.class);

	public final static int BUFFER = 4096;

	public final static int IMAGE_SIZE = 120;

	public static String convertEncoding(String s) {
		String s1 = s;
		try {
			s1 = new String(s.getBytes("UTF-8"), "GBK");
		} catch (Exception exception) {
		}
		return s1;
	}

	public static void createPreviewImage(String srcFile, String destFile) {
		try {
			File fi = new File(srcFile);
			File fo = new File(destFile);
			BufferedImage bis = ImageIO.read(fi);

			int w = bis.getWidth();
			int h = bis.getHeight();

			int nw = IMAGE_SIZE;
			int nh = (nw * h) / w;
			if (nh > IMAGE_SIZE) {
				nh = IMAGE_SIZE;
				nw = (nh * w) / h;
			}
			double sx = (double) nw / w;
			double sy = (double) nh / h;

			AffineTransform transform = new AffineTransform();
			transform.setToScale(sx, sy);

			AffineTransformOp ato = new AffineTransformOp(transform, null);
			BufferedImage bid = new BufferedImage(nw, nh,
					BufferedImage.TYPE_3BYTE_BGR);
			ato.filter(bis, bid);
			ImageIO.write(bid, "jpeg", fo);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(
					"Failed in create preview image. Error: " + ex.getMessage());
		}
	}

	public static byte[] decodeBASE64(String source) {
		if (source == null) {
			return null;
		}
		return Base64.decodeBase64(source);
	}

	/**
	 * 进行base64解码
	 * 
	 * @return
	 */
	public static String decodeBASE64ToString(String source) {
		if (source == null) {
			return null;
		}
		return new String(Base64.decodeBase64(source));
	}

	public static final byte[] decodeHex(String hex) {
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

	/**
	 * 进行base64编码
	 * 
	 * @param
	 * @return
	 */
	public static String encodeBASE64(byte[] source) {
		if (source == null) {
			return null;
		}
		return Base64.encodeBase64String(source);
	}

	/**
	 * 进行base64编码
	 * 
	 * @return
	 */
	public static String encodeBASE64(String source) {
		if (source == null) {
			return null;
		}
		return Base64.encodeBase64String(source.getBytes());
	}

	public static final String encodeHex(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		int i;

		for (i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString();
	}

	public static final String encodeHex(String str) {
		if (str == null) {
			return null;
		}
		return encodeHex(str.getBytes());
	}

	public static String formatEmpty(String s) {
		if (s == null || s.equals("null")) {
			s = "";
		}
		return s;
	}

	public static String formatLine(String sourceString, int length) {
		if (sourceString == null) {
			return "";
		}
		if (sourceString.length() <= length) {
			int k = length - sourceString.length();
			for (int j = 0; j < k; j++) {
				sourceString = sourceString + "&nbsp;";
			}
			return sourceString;
		}
		char[] sourceChrs = sourceString.toCharArray();
		char[] distinChrs = new char[length];

		for (int i = 0; i < length; i++) {
			if (i >= sourceChrs.length) {
				return sourceString;
			}
			Character chr = new Character(sourceChrs[i]);
			if (chr.charValue() <= 202 && chr.charValue() >= 8) {
				distinChrs[i] = chr.charValue();
			} else {
				distinChrs[i] = chr.charValue();
				length--;
			}
		}
		return new String(distinChrs) + "…";
	}

	public static byte[] getBytes(InputStream inputStream, String name) {
		byte[] bytes = null;
		try {
			CheckedInputStream checkedinputstream = new CheckedInputStream(
					inputStream, new Adler32());
			ZipInputStream zipinputstream = new ZipInputStream(
					new BufferedInputStream(checkedinputstream));
			java.util.zip.ZipEntry zipEntry = null;
			while ((zipEntry = zipinputstream.getNextEntry()) != null) {
				byte abyte0[] = new byte[BUFFER];
				String s1 = zipEntry.getName();
				s1 = convertEncoding(s1);
				if (s1.equalsIgnoreCase(name)) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					BufferedOutputStream bos = new BufferedOutputStream(baos,
							BUFFER);
					int i = 0;
					while ((i = zipinputstream.read(abyte0, 0, BUFFER)) != -1) {
						bos.write(abyte0, 0, i);
					}
					bos.flush();
					bos.close();
					bytes = baos.toByteArray();
					baos.close();
				}
			}
			zipinputstream.close();
			zipinputstream = null;
			return bytes;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public static double getDouble(Map valueMap, String key) {
		if (valueMap != null && valueMap.get(key) != null) {
			String value = (String) valueMap.get(key);
			if (StringUtils.isNotBlank(value)) {
				return Double.parseDouble(value);
			}
		}
		return 0.0;
	}

	public static Map getGridRows(HttpServletRequest request) {
		return RequestUtil.getGridRows(request);
	}

	public static long getId(Map params, String parameterName) {
		long value = -1;
		String valueText = (String) params.get(parameterName);
		try {
			if (StringUtils.isNumeric(valueText)) {
				Long id = new Long(valueText);
				value = id.longValue();
			}
		} catch (NumberFormatException e) {
			throw new RuntimeException("couldn't parse '" + parameterName
					+ "'='" + valueText + "' as a long");
		}
		return value;
	}

	public static String getINSubSQL(java.util.Collection pIds) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" IN ( ");
		Iterator item = pIds.iterator();
		int index = 0;
		while (item.hasNext()) {
			String id = (String) item.next();
			buffer.append("'").append(id).append("' , ");
			index++;
		}
		if (index > 0) {
			buffer.delete(buffer.length() - 2, buffer.length());
		}
		buffer.append(" ) ");

		return buffer.toString();

	}

	public static int getInt(Map valueMap, String key) {
		if (valueMap != null && valueMap.get(key) != null) {
			String value = (String) valueMap.get(key);
			if (StringUtils.isNotBlank(value)) {
				return Integer.parseInt(value);
			}
		}
		return 0;
	}

	public static long getLong(Map valueMap, String key) {
		if (valueMap != null && valueMap.get(key) != null) {
			String value = (String) valueMap.get(key);
			if (StringUtils.isNotBlank(value)) {
				return Long.parseLong(value);
			}
		}
		return 0;
	}

	/**
	 * 从request中封装一个对象
	 * 
	 * @param pRequest
	 *            request对象
	 * @param pModel
	 *            封装对象的类
	 * @return 超类
	 * @throws InputInvalidException
	 */
	public static Object getParameter(HttpServletRequest request, Class pModel)
			throws ServletException {
		return RequestUtil.getParameter(request, pModel);
	}

	/**
	 * 从request中封装一个对象
	 * 
	 * @param pRequest
	 *            request对象
	 * @param pModel
	 *            封装对象的类
	 * @return 超类
	 * @throws InputInvalidException
	 */
	public static Object getParameter(HttpServletRequest request, Object obj)
			throws ServletException {
		return RequestUtil.getParameter(request, obj);
	}

	/**
	 * 从request中获取参数
	 * 
	 * @param request
	 * @return
	 */
	public static Map getParameterMap(HttpServletRequest request) {
		return RequestUtil.getParameterMap(request);
	}

	public static Map getParameters(HttpServletRequest request) {
		Map params = new HashMap();
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			String paramValue = request.getParameter(paramName);
			if (StringUtils.isNotBlank(paramName) && paramValue != null) {
				if (paramValue.equalsIgnoreCase("null")) {
					continue;
				}
				params.put(paramName, paramValue);
			}
		}
		return params;
	}

	public static Principal getPrincipalFromRequest(HttpServletRequest request)
			throws Exception {
		Principal principal = null;
		if (request.isSecure()) {
			X509Certificate[] certs = (X509Certificate[]) request
					.getAttribute("javax.servlet.request.X509Certificate");
			if (certs != null) {
				X509Certificate clientCert = certs[0];
				if (clientCert != null) {
					principal = clientCert.getSubjectDN();
				}
			}
		}
		return principal;
	}

	public static String getString(Map dataMap, String elementId) {
		if (dataMap == null) {
			return null;
		}

		Object obj = dataMap.get(elementId);
		if (obj == null) {
			obj = dataMap.get(elementId.toLowerCase());
		}
		if (obj == null) {
			obj = dataMap.get(elementId.toUpperCase());
		}
		if (obj != null) {
			return obj.toString();
		}

		return null;

	}

	public static Object getValue(Class type, String propertyValue) {
		if (type == null || propertyValue == null) {
			return null;
		}
		Object value = null;
		try {
			if (type == String.class) {
				value = propertyValue;
			} else if ((type == Integer.class) || (type == int.class)) {
				if (propertyValue.indexOf(',') != -1) {
					propertyValue = propertyValue.replaceAll(",", "");
				}
				value = new Integer(propertyValue);
			} else if ((type == Long.class) || (type == long.class)) {
				if (propertyValue.indexOf(',') != -1) {
					propertyValue = propertyValue.replaceAll(",", "");
				}
				value = new Long(propertyValue);
			} else if ((type == Float.class) || (type == float.class)) {
				if (propertyValue.indexOf(',') != -1) {
					propertyValue = propertyValue.replaceAll(",", "");
				}
				value = new Float(propertyValue);
			} else if ((type == Double.class) || (type == double.class)) {
				if (propertyValue.indexOf(',') != -1) {
					propertyValue = propertyValue.replaceAll(",", "");
				}
				value = new Double(propertyValue);
			} else if ((type == Boolean.class) || (type == boolean.class)) {
				value = Boolean.valueOf(propertyValue);
			} else if ((type == Character.class) || (type == char.class)) {
				value = new Character(propertyValue.charAt(0));
			} else if ((type == Short.class) || (type == short.class)) {
				if (propertyValue.indexOf(',') != -1) {
					propertyValue = propertyValue.replaceAll(",", "");
				}
				value = new Short(propertyValue);
			} else if ((type == Byte.class) || (type == byte.class)) {
				value = new Byte(propertyValue);
			} else if (type == java.util.Date.class) {
				value = DateTools.toDate(propertyValue);
			} else if (type == java.sql.Date.class) {
				value = DateTools.toDate(propertyValue);
			} else if (type == java.sql.Timestamp.class) {
				value = DateTools.toDate(propertyValue);
			} else if (type.isAssignableFrom(List.class)) {
			} else if (type.isAssignableFrom(Set.class)) {
			} else if (type.isAssignableFrom(Collection.class)) {
			} else if (type.isAssignableFrom(Map.class)) {
			} else {
				value = propertyValue;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return value;
	}

	private static final byte hexCharToByte(char ch) {
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

	public static boolean isDatabaseField(String sourceString) {
		if (sourceString == null || sourceString.trim().length() < 2
				|| sourceString.trim().length() > 32) {
			return false;
		}
		char[] sourceChrs = sourceString.toCharArray();
		Character chr = new Character(sourceChrs[0]);
		if (!((chr.charValue() == 95)
				|| (65 <= chr.charValue() && chr.charValue() <= 90) || (97 <= chr
				.charValue() && chr.charValue() <= 122))) {
			return false;
		}
		for (int i = 1; i < sourceChrs.length; i++) {
			chr = new Character(sourceChrs[i]);
			if (!((chr.charValue() == 95)
					|| (47 <= chr.charValue() && chr.charValue() <= 57)
					|| (65 <= chr.charValue() && chr.charValue() <= 90) || (97 <= chr
					.charValue() && chr.charValue() <= 122))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isExpectableString(String sourceString) {
		if (sourceString == null || sourceString.trim().length() < 2
				|| sourceString.trim().length() > 32) {
			return false;
		}
		char[] sourceChrs = sourceString.toCharArray();
		Character chr = new Character(sourceChrs[0]);
		if (!((chr.charValue() == 95)
				|| (65 <= chr.charValue() && chr.charValue() <= 90) || (97 <= chr
				.charValue() && chr.charValue() <= 122))) {
			return false;
		}
		for (int i = 1; i < sourceChrs.length; i++) {
			chr = new Character(sourceChrs[i]);
			if (!((chr.charValue() == 95)
					|| (47 <= chr.charValue() && chr.charValue() <= 57)
					|| (65 <= chr.charValue() && chr.charValue() <= 90) || (97 <= chr
					.charValue() && chr.charValue() <= 122))) {
				return false;
			}
		}
		return true;
	}

	public static Class loadClass(String className)
			throws ClassNotFoundException {
		try {
			ClassLoader contextClassLoader = Thread.currentThread()
					.getContextClassLoader();
			if (contextClassLoader != null) {
				return contextClassLoader.loadClass(className);
			} else {
				return Class.forName(className);
			}
		} catch (Exception e) {
			return Class.forName(className);
		}
	}

	public static Object newInstance(String className) {
		Object obj = null;
		try {
			ClassLoader contextClassLoader = Thread.currentThread()
					.getContextClassLoader();
			if (contextClassLoader != null) {
				Class clazz = contextClassLoader.loadClass(className);
				obj = clazz.newInstance();
			} else {
				Class clazz = Class.forName(className);
				obj = clazz.newInstance();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return obj;
	}

	public static void populate(Object model, Map dataMap) {
		PropertyDescriptor[] propertyDescriptor = PropertyUtils
				.getPropertyDescriptors(model);
		for (int i = 0; i < propertyDescriptor.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptor[i];
			String propertyName = descriptor.getName();
			if (propertyName.equalsIgnoreCase("class")) {
				continue;
			}
			String value = null;
			Object obj = null;
			Object o = dataMap.get(propertyName);
			if (o != null && o instanceof String) {
				value = (String) o;
			} else {
				obj = o;
			}
			try {

				Class clazz = descriptor.getPropertyType();
				obj = getValue(clazz, value);

				if (obj != null) {
					if (logger.isDebugEnabled()) {
						logger.debug("property name:" + propertyName);
						logger.debug("property value:" + obj.toString());
						logger.debug("property class name:"
								+ obj.getClass().getName());
					}
					PropertyUtils.setProperty(model, propertyName, obj);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(dataMap);
				logger.error(ex);
			}
		}
	}

	protected static void populate4(Object model, Map dataMap) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(model);
		PropertyDescriptor[] propertyDescriptor = beanWrapper
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptor.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptor[i];
			String propertyName = descriptor.getName();
			if (propertyName.equalsIgnoreCase("class")) {
				continue;
			}
			String value = (String) dataMap.get(propertyName);
			Class clazz = descriptor.getPropertyType();
			Object obj = null;
			try {
				if (clazz.getName().equals("byte")) {
					obj = new Byte(value);
				} else if (clazz.getName().equals("short")) {
					if (value.indexOf(',') != -1) {
						value = value.replaceAll(",", "");
					}
					obj = new Short(value);
				} else if (clazz.getName().equals("float")) {
					if (value.indexOf(',') != -1) {
						value = value.replaceAll(",", "");
					}
					obj = new Float(value);
				} else if (clazz.getName().equals("double")) {
					if (value.indexOf(',') != -1) {
						value = value.replaceAll(",", "");
					}
					obj = new Double(value);

				} else if (clazz.getName().equals("int")) {
					if (value.indexOf(',') != -1) {
						value = value.replaceAll(",", "");
					}
					obj = new Integer(value);
				} else if (clazz.getName().equals("long")) {
					if (value.indexOf(',') != -1) {
						value = value.replaceAll(",", "");
					}
					obj = new Long(value);
				} else if (clazz.getName().equals("java.lang.Byte")) {
					obj = new Byte(value);
				} else if (clazz.getName().equals("java.lang.Short")) {
					if (value.indexOf(',') != -1) {
						value = value.replaceAll(",", "");
					}
					obj = new Short(value);
				} else if (clazz.getName().equals("java.lang.Float")) {
					if (value.indexOf(',') != -1) {
						value = value.replaceAll(",", "");
					}
					obj = new Float(value);
				} else if (clazz.getName().equals("java.lang.Double")) {
					if (value.indexOf(',') != -1) {
						value = value.replaceAll(",", "");
					}
					obj = new Double(value);
				} else if (clazz.getName().equals("java.lang.Integer")) {
					if (value.indexOf(',') != -1) {
						value = value.replaceAll(",", "");
					}
					obj = new Integer(value);
				} else if (clazz.getName().equals("java.lang.Long")) {
					if (value.indexOf(',') != -1) {
						value = value.replaceAll(",", "");
					}
					obj = new Long(value);
				} else if (clazz.getName().equals("java.util.Date")) {
					obj = DateTools.toDate(value);
				} else if (clazz.getName().equals("java.sql.Date")) {
					obj = DateTools.toDate(value);
				} else if (clazz.getName().equals("java.sql.Timestamp")) {
					obj = DateTools.toDate(value);
				} else {
					obj = value;
				}
				beanWrapper.setPropertyValue(propertyName, obj);
			} catch (Exception ex) {
				logger.error(dataMap);
				logger.error(ex);
			}
		}
	}

	public static String replaceIgnoreCase(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		if (line.indexOf(oldString) == -1) {
			return line;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
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

	/**
	 * 截取字符串,可以处理中,英的问题+"…"
	 * 
	 * @param sourceString
	 *            待处理的字符串
	 * @param length
	 *            截取的长度,是字节长度,一个中文两个字节
	 * @return 返回截取后的字符串
	 */
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
			Character chr = new Character(sourceChrs[i]);
			if (chr.charValue() <= 202 && chr.charValue() >= 8) {
				distinChrs[i] = chr.charValue();
			} else {
				distinChrs[i] = chr.charValue();
				length--;
			}
		}
		return new String(distinChrs) + "…";
	}

	public static List splitString(String source, String split) {
		if (source == null) {
			return null;
		}
		List data = new ArrayList();
		StringTokenizer token = new StringTokenizer(source, split);
		while (token.hasMoreTokens()) {
			String str = token.nextToken();
			data.add(str);
		}
		return data;
	}

	public static void toLowerKeyMap(Map dataMap) {
		if (dataMap == null) {
			return;
		}
		Map map = new HashMap();
		Iterator iterator = dataMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			map.put(key.toLowerCase(), dataMap.get(key));
		}
		dataMap.putAll(map);
	}

	public static Set toSet(Collection data) {
		if (data == null) {
			return null;
		}
		Set set = new HashSet();
		Iterator iterator = data.iterator();
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (obj != null) {
				set.add(obj);
			}
		}
		return set;
	}

	/**
	 * Reformats a string where lines that are longer than <tt>width</tt> are
	 * split apart at the earliest wordbreak or at maxLength, whichever is
	 * sooner. If the width specified is less than 5 or greater than the input
	 * Strings length the string will be returned as is.
	 * <p/>
	 * Please note that this method can be lossy - trailing spaces on wrapped
	 * lines may be trimmed.
	 * 
	 * @param input
	 *            the String to reformat.
	 * @param width
	 *            the maximum length of any one line.
	 * @return a new String with reformatted as needed.
	 */
	public static String wordWrap(String input, int width, Locale locale) {
		// protect ourselves
		if (input == null) {
			return "";
		} else if (width < 5) {
			return input;
		} else if (width >= input.length()) {
			return input;
		}

		// default locale
		if (locale == null) {
			locale = Locale.getDefault();
		}

		StringBuffer buf = new StringBuffer(input);
		boolean endOfLine = false;
		int lineStart = 0;

		for (int i = 0; i < buf.length(); i++) {
			if (buf.charAt(i) == '\n') {
				lineStart = i + 1;
				endOfLine = true;
			}

			// handle splitting at width character
			if (i > lineStart + width - 1) {
				if (!endOfLine) {
					int limit = i - lineStart - 1;
					BreakIterator breaks = BreakIterator
							.getLineInstance(locale);
					breaks.setText(buf.substring(lineStart, i));
					int end = breaks.last();

					// if the last character in the search string isn't a space,
					// we can't split on it (looks bad). Search for a previous
					// break character
					if (end == limit + 1) {
						if (!Character
								.isWhitespace(buf.charAt(lineStart + end))) {
							end = breaks.preceding(end - 1);
						}
					}

					// if the last character is a space, replace it with a \n
					if (end != BreakIterator.DONE && end == limit + 1) {
						buf.replace(lineStart + end, lineStart + end + 1, "\n");
						lineStart = lineStart + end;
					}
					// otherwise, just insert a \n
					else if (end != BreakIterator.DONE && end != 0) {
						buf.insert(lineStart + end, '\n');
						lineStart = lineStart + end + 1;
					} else {
						buf.insert(i, '\n');
						lineStart = i + 1;
					}
				} else {
					buf.insert(i, '\n');
					lineStart = i + 1;
					endOfLine = false;
				}
			}
		}

		return buf.toString();
	}

	private Tools() {
	}

}