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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class AntUtils {

	private static List<String> list = new java.util.concurrent.CopyOnWriteArrayList<String>();

	private static byte buff[] = new byte[4096];

	private static int len;

	private static List<String> getFileList(String path) {
		File file = new File(path);
		String[] array = null;
		String temp = "";
		if (!file.isDirectory()) {
			return null;
		}
		array = file.list();
		if (array.length > 0) {
			for (int i = 0; i < array.length; i++) {
				temp = path + array[i];
				file = new File(temp);
				if (file.isDirectory()) {
					getFileList(temp + "/");
				} else {
					list.add(temp);
				}
			}
		} else {
			return null;
		}

		return list;
	}

	public static byte[] getZipStream(Map<String, InputStream> zipMap) {
		return getZipStream(zipMap, null);
	}

	public static byte[] getZipStream(Map<String, InputStream> zipMap,
			String encoding) {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			CheckedOutputStream cos = new CheckedOutputStream(baos, new CRC32());
			BufferedOutputStream bos = new BufferedOutputStream(cos);
			ZipOutputStream zipOutputStream = new ZipOutputStream(bos);
			if (encoding != null) {
				zipOutputStream.setEncoding(encoding);
			}
			if (zipMap != null) {
				Set<Entry<String, InputStream>> entrySet = zipMap.entrySet();
				for (Entry<String, InputStream> entry : entrySet) {
					String filename = entry.getKey();
					InputStream inputStream = entry.getValue();
					if (filename != null && inputStream != null) {
						BufferedInputStream bis = new BufferedInputStream(
								inputStream);
						ZipEntry zipEntry = new ZipEntry(filename);
						zipOutputStream.putNextEntry(zipEntry);
						while ((len = bis.read(buff)) >= 0) {
							zipOutputStream.write(buff, 0, len);
						}
						bis.close();
						zipOutputStream.closeEntry();
					}
				}
			}
			zipOutputStream.flush();
			zipOutputStream.close();
			bos.flush();
			bos.close();
			cos.flush();
			cos.close();
			bytes = baos.toByteArray();
			baos.close();
			return bytes;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void zip(String zipFile, String path) {
		try {
			list.clear();
			path = path.replace(File.separatorChar, '/');
			if (!path.endsWith("/")) {
				path = path + "/";
			}
			List<String> fileList = getFileList(path);
			FileOutputStream fout = new FileOutputStream(zipFile);
			// 使用输出流检查
			CheckedOutputStream cos = new CheckedOutputStream(fout, new CRC32());
			// 声明输出zip流
			ZipOutputStream zipOutputStream = new ZipOutputStream(
					new BufferedOutputStream(cos));
			InputStream in = null;
			for (int i = 0, len = fileList.size(); i < len; i++) {
				try {
					in = new FileInputStream(fileList.get(i));
					String filename = ((fileList.get(i)));

					filename = filename.substring(path.length());
					filename = filename.replace(File.separatorChar, '/');
					ZipEntry zipEntry = new ZipEntry(filename);
					zipOutputStream.putNextEntry(zipEntry);
					while ((len = in.read(buff)) != -1) {
						zipOutputStream.write(buff, 0, len);
					}
					zipOutputStream.closeEntry();
				} catch (IOException ex) {
				} finally {
					if (in != null) {
						in.close();
					}
				}
			}
			zipOutputStream.close();
			cos.close();
			fout.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private AntUtils() {

	}

	public static void main(String args[]) {
		// 压缩文件的保存路径
		String zipFile = args[0];
		// 压缩文件目录
		String filepath = args[1];
		AntUtils.zip(zipFile, filepath);
	}

}