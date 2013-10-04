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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtils {
	private static JarOutputStream jos;

	private static byte buf[] = new byte[256];

	private static int len;

	private static int BUFFER = 65536;

	private static String sp = System.getProperty("file.separator");

	public static String convertEncoding(String s) {
		String s1 = s;
		try {
			s1 = new String(s.getBytes("UTF-8"), "GBK");
		} catch (Exception exception) {
		}
		return s1;
	}

	public static byte[] getBytes(byte[] bytes, String name) {
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(bytes);
			return getBytes(inputStream, name);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (java.io.IOException ex) {
			}
		}
	}

	public static byte[] getBytes(InputStream inputStream, String name) {
		byte[] bytes = null;
		try {
			ZipInputStream zipInputStream = new ZipInputStream(inputStream);
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			while (zipEntry != null) {
				String entryName = zipEntry.getName();
				if (entryName.equalsIgnoreCase(name)) {
					bytes = FileUtils.getBytes(zipInputStream);
					if (bytes != null) {
						break;
					}
				}
				zipEntry = zipInputStream.getNextEntry();
			}
			zipInputStream.close();
			zipInputStream = null;
			return bytes;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static byte[] getBytes(ZipInputStream zipInputStream, String name) {
		byte[] bytes = null;
		try {
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			while (zipEntry != null) {
				String entryName = zipEntry.getName();
				if (entryName.equalsIgnoreCase(name)) {
					bytes = FileUtils.getBytes(zipInputStream);
					if (bytes != null) {
						break;
					}
				}
				zipEntry = zipInputStream.getNextEntry();
			}
			return bytes;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static byte[] getZipBytes(Map<String, InputStream> dataMap) {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(baos);
			jos = new JarOutputStream(bos);
			if (dataMap != null) {
				Set<Entry<String, InputStream>> entrySet = dataMap.entrySet();
				for (Entry<String, InputStream> entry : entrySet) {
					String name = entry.getKey();
					InputStream inputStream = entry.getValue();
					if (name != null && inputStream != null) {
						BufferedInputStream bis = new BufferedInputStream(
								inputStream);
						JarEntry jarEntry = new JarEntry(name);
						jos.putNextEntry(jarEntry);

						while ((len = bis.read(buf)) >= 0) {
							jos.write(buf, 0, len);
						}

						bis.close();
						jos.closeEntry();
					}
				}
			}
			jos.flush();
			jos.close();
			bos.flush();
			bos.close();
			bytes = baos.toByteArray();
			baos.close();
			return bytes;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Map<String, byte[]> getZipBytesMap(
			ZipInputStream zipInputStream) {
		Map<String, byte[]> zipMap = new HashMap<String, byte[]>();
		java.util.zip.ZipEntry zipEntry = null;
		try {
			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				byte abyte0[] = new byte[BUFFER];
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				BufferedOutputStream bos = new BufferedOutputStream(baos,
						BUFFER);
				int i = 0;
				while ((i = zipInputStream.read(abyte0, 0, BUFFER)) != -1) {
					bos.write(abyte0, 0, i);
				}
				bos.flush();
				byte[] bytes = baos.toByteArray();
				bos.close();
				baos.close();
				bos = null;
				baos = null;
				zipMap.put(zipEntry.getName(), bytes);
			}
			zipInputStream.close();
			zipInputStream = null;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return zipMap;
	}

	public static void makeZip(File dir, File zipFile) throws IOException,
			FileNotFoundException {
		jos = new JarOutputStream(new FileOutputStream(zipFile));
		String as[] = dir.list();
		if (as != null) {
			for (int i = 0; i < as.length; i++)
				recurseFiles(new File(dir, as[i]), "");
		}
		jos.close();
	}

	private static void recurseFiles(File file, String s) throws IOException,
			FileNotFoundException {
		if (file.isDirectory()) {
			s = s + file.getName() + "/";
			jos.putNextEntry(new JarEntry(s));
			String as[] = file.list();
			if (as != null) {
				for (int i = 0; i < as.length; i++)
					recurseFiles(new File(file, as[i]), s);
			}
		} else {
			if (file.getName().endsWith("MANIFEST.MF")
					|| file.getName().endsWith("META-INF/MANIFEST.MF")) {
				return;
			}
			JarEntry jarentry = new JarEntry(s + file.getName());
			FileInputStream fileinputstream = new FileInputStream(file);
			BufferedInputStream bufferedinputstream = new BufferedInputStream(
					fileinputstream);
			jos.putNextEntry(jarentry);
			while ((len = bufferedinputstream.read(buf)) >= 0) {
				jos.write(buf, 0, len);
			}
			bufferedinputstream.close();
			jos.closeEntry();
		}
	}

	public static byte[] toZipBytes(Map<String, byte[]> zipMap) {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(baos);
			jos = new JarOutputStream(bos);
			if (zipMap != null) {
				Set<Entry<String, byte[]>> entrySet = zipMap.entrySet();
				for (Entry<String, byte[]> entry : entrySet) {
					String name = entry.getKey();
					byte[] x_bytes = entry.getValue();
					InputStream inputStream = new ByteArrayInputStream(x_bytes);
					if (name != null && inputStream != null) {
						BufferedInputStream bis = new BufferedInputStream(
								inputStream);
						JarEntry jarEntry = new JarEntry(name);
						jos.putNextEntry(jarEntry);

						while ((len = bis.read(buf)) >= 0) {
							jos.write(buf, 0, len);
						}

						bis.close();
						jos.closeEntry();
					}
				}
			}
			jos.flush();
			jos.close();
			bos.flush();
			bos.close();
			bytes = baos.toByteArray();
			baos.close();
			return bytes;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void unzip(File zipFile) {
		try {
			FileInputStream fileInputStream = new FileInputStream(zipFile);
			ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
			java.util.zip.ZipEntry zipEntry;
			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				byte abyte0[] = new byte[BUFFER];
				FileOutputStream fileoutputstream = new FileOutputStream(
						zipEntry.getName());
				BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(
						fileoutputstream, BUFFER);
				int i;
				while ((i = zipInputStream.read(abyte0, 0, BUFFER)) != -1) {
					bufferedoutputstream.write(abyte0, 0, i);
				}
				bufferedoutputstream.flush();
				bufferedoutputstream.close();
			}
			zipInputStream.close();
			zipInputStream = null;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void unzip(File zipFile, String dir) throws Exception {
		File file = new File(dir);
		FileUtils.mkdirsWithExistsCheck(file);
		FileInputStream fileInputStream = new FileInputStream(zipFile);
		ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
		java.util.zip.ZipEntry zipEntry;
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			boolean isDirectory = zipEntry.isDirectory();
			byte abyte0[] = new byte[BUFFER];
			String s1 = zipEntry.getName();
			s1 = convertEncoding(s1);

			String s2 = dir + "/" + s1;
			s2 = FileUtils.getJavaFileSystemPath(s2);

			if (s2.indexOf('/') != -1 || isDirectory) {
				String s4 = s2.substring(0, s2.lastIndexOf('/'));
				File file2 = new File(s4);
				FileUtils.mkdirsWithExistsCheck(file2);
			}

			if (isDirectory) {
				continue;
			}

			FileOutputStream fileoutputstream = new FileOutputStream(s2);
			BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(
					fileoutputstream, BUFFER);
			int i = 0;
			while ((i = zipInputStream.read(abyte0, 0, BUFFER)) != -1) {
				bufferedoutputstream.write(abyte0, 0, i);
			}
			bufferedoutputstream.flush();
			bufferedoutputstream.close();
		}

		zipInputStream.close();
		zipInputStream = null;
	}

	public static void unzip(java.io.InputStream inputStream, String dir)
			throws Exception {
		File file = new File(dir);
		FileUtils.mkdirsWithExistsCheck(file);
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		java.util.zip.ZipEntry zipEntry;
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			boolean isDirectory = zipEntry.isDirectory();
			byte abyte0[] = new byte[BUFFER];
			String s1 = zipEntry.getName();

			s1 = convertEncoding(s1);

			String s2 = dir + sp + s1;
			s2 = FileUtils.getJavaFileSystemPath(s2);

			if (s2.indexOf('/') != -1 || isDirectory) {
				String s4 = s2.substring(0, s2.lastIndexOf('/'));
				File file2 = new File(s4);
				FileUtils.mkdirsWithExistsCheck(file2);
			}
			if (isDirectory) {
				continue;
			}
			FileOutputStream fileoutputstream = new FileOutputStream(s2);
			BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(
					fileoutputstream, BUFFER);
			int i = 0;
			while ((i = zipInputStream.read(abyte0, 0, BUFFER)) != -1) {
				bufferedoutputstream.write(abyte0, 0, i);
			}
			bufferedoutputstream.flush();
			bufferedoutputstream.close();
		}

		zipInputStream.close();
		zipInputStream = null;
	}

	public static void unzip(java.io.InputStream inputStream, String path,
			List<String> excludes) throws Exception {
		File file = new File(path);
		FileUtils.mkdirsWithExistsCheck(file);
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		java.util.zip.ZipEntry zipEntry;
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			boolean isDirectory = zipEntry.isDirectory();
			byte abyte0[] = new byte[BUFFER];
			String s1 = zipEntry.getName();
			String ext = FileUtils.getFileExt(s1);
			if (excludes.contains(ext) || excludes.contains(ext.toLowerCase())) {
				continue;
			}

			s1 = convertEncoding(s1);

			String s2 = path + sp + s1;
			s2 = FileUtils.getJavaFileSystemPath(s2);

			if (s2.indexOf('/') != -1 || isDirectory) {
				String s4 = s2.substring(0, s2.lastIndexOf('/'));
				File file2 = new File(s4);
				FileUtils.mkdirsWithExistsCheck(file2);
			}
			if (isDirectory) {
				continue;
			}
			FileOutputStream fileoutputstream = new FileOutputStream(s2);
			BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(
					fileoutputstream, BUFFER);
			int i = 0;
			while ((i = zipInputStream.read(abyte0, 0, BUFFER)) != -1) {
				bufferedoutputstream.write(abyte0, 0, i);
			}
			bufferedoutputstream.flush();
			bufferedoutputstream.close();
		}

		zipInputStream.close();
		zipInputStream = null;
	}

	private ZipUtils() {
	}

}