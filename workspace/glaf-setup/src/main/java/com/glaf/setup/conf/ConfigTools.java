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

package com.glaf.setup.conf;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class ConfigTools {

	public final static String newline = System.getProperty("line.separator");

	public static String getJavaFileSystemPath(String path) {
		if (path == null) {
			return null;
		}
		path = path.replace('\\', '/');
		return path;
	}

	private static Map<String, Object> lowerKeyMap(Map<String, Object> paramMap) {
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		Set<Entry<String, Object>> entrySet = paramMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();
			dataMap.put(key.toLowerCase(), value);
			dataMap.put(key, value);
		}
		return dataMap;
	}

	public static String readFile(String filename) throws IOException {
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = new FileInputStream(filename);
		if (inputStream != null) {
			InputStreamReader is = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(is);
			String entity = null;
			while ((entity = reader.readLine()) != null) {
				buffer.append(entity).append(newline);
			}
			reader.close();
			reader = null;
			is.close();
			is = null;
			inputStream.close();
			inputStream = null;
		}
		return buffer.toString();
	}

	public static String replaceIgnoreCase(String line, String oldString,
			String newString) {
		if (line == null || oldString == null || newString == null) {
			return line;
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

	public static String replaceTextParas(String str, Map<String, Object> params) {
		if (str == null || params == null) {
			return str;
		}
		Map<String, Object> dataMap = lowerKeyMap(params);
		StringBuffer sb = new StringBuffer();
		int begin = 0;
		int end = 0;
		boolean flag = false;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '$' && str.charAt(i + 1) == '{') {
				sb.append(str.substring(end, i));
				begin = i + 2;
				flag = true;
			}
			if (flag && str.charAt(i) == '}') {
				String temp = str.substring(begin, i);
				String x = temp;
				temp = temp.toLowerCase();
				if (dataMap.get(temp) != null) {
					String value = dataMap.get(temp).toString();
					sb.append(value);
					end = i + 1;
					flag = false;
				} else {
					sb.append("${").append(x).append("}");
					end = i + 1;
					flag = false;
				}
			}
			if (i == str.length() - 1) {
				sb.append(str.substring(end, i + 1));
			}
		}
		return sb.toString();
	}

	public static void save(String filename, InputStream inputStream)
			throws IOException {
		if (filename == null || inputStream == null) {
			return;
		}
		String path = "";
		String sp = System.getProperty("file.separator");
		if (filename.indexOf(sp) != -1) {
			path = filename.substring(0, filename.lastIndexOf(sp));
		}
		path = getJavaFileSystemPath(path);
		java.io.File directory = new java.io.File(path + sp);
		if (!directory.exists()) { // 如果目录不存在，新建一个
			boolean isOK = directory.mkdirs();
			if (isOK) {
				throw new RuntimeException(directory.getAbsolutePath()
						+ " create error ");
			}
		}
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(inputStream);
			bos = new BufferedOutputStream(new FileOutputStream(filename));
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			bos.flush();
			bis.close();
			bos.close();
			bis = null;
			bos = null;
		} catch (IOException ex) {
			bis = null;
			bos = null;
			throw ex;
		} finally {
			try {
				if (bis != null) {
					bis.close();
					bis = null;
				}
				if (bos != null) {
					bos.close();
					bos = null;
				}
			} catch (IOException ioe) {
			}
		}
	}

	public static void save(String filename, Map<String, String> dataMap)
			throws IOException {
		StringBuffer writer = new StringBuffer();
		Set<Entry<String, String>> entrySet = dataMap.entrySet();
		for (Entry<String, String> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();
			writer.append(key);
			writer.append("=");
			writer.append(value);
			writer.append("\n");
		}
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(filename);
			fout.write(writer.toString().getBytes());
			fout.close();
		} catch (IOException ex) {
			throw ex;
		} finally {
			try {
				if (fout != null) {
					fout.close();
					fout = null;
				}
			} catch (IOException ioe) {
			}
		}
	}

	public static void save(String filename, Properties p) throws IOException {
		StringBuffer writer = new StringBuffer();
		java.util.Enumeration<?> e = p.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = p.getProperty(key);
			writer.append(key);
			writer.append("=");
			writer.append(value);
			writer.append("\n");
		}
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(filename);
			fout.write(writer.toString().getBytes());
			fout.close();
		} catch (IOException ex) {
			throw ex;
		} finally {
			try {
				if (fout != null) {
					fout.close();
					fout = null;
				}
			} catch (IOException ioe) {
			}
		}
	}

}