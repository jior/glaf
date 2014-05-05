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
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;

public class ZipUtils {
	protected static Configuration conf = BaseConfiguration.create();

	private static byte buf[] = new byte[256];

	private static int len;

	private static int BUFFER = 8192;

	private static String sp = System.getProperty("file.separator");

	public static void main(String[] args) {
		String filename = "user.xml";
		if (args != null && args.length == 1) {
			filename = args[0];
		}
		byte[] bytes = FileUtils.getBytes(filename);
		byte[] input = null;
		byte[] zipBytes = null;
		try {
			zipBytes = org.xerial.snappy.Snappy.compress(bytes);
			FileUtils.save("output", zipBytes);
			input = FileUtils.getBytes("output");
			bytes = org.xerial.snappy.Snappy.uncompress(input);
			FileUtils.save("input", bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 把文件压缩成zip格式
	 * 
	 * @param files
	 *            需要压缩的文件
	 * 
	 * @param zipFilePath
	 *            压缩后的zip文件路径 ,如"/var/data/aa.zip";
	 */
	public static void compressFile(File[] files, String zipFilePath) {
		if (files != null && files.length > 0) {
			if (isEndsWithZip(zipFilePath)) {
				ZipArchiveOutputStream zaos = null;
				try {
					File zipFile = new File(zipFilePath);
					zaos = new ZipArchiveOutputStream(zipFile);

					// Use Zip64 extensions for all entries where they are
					// required
					zaos.setUseZip64(Zip64Mode.AsNeeded);

					for (File file : files) {
						if (file != null) {
							ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(
									file, file.getName());
							zaos.putArchiveEntry(zipArchiveEntry);
							InputStream is = null;
							try {
								is = new BufferedInputStream(
										new FileInputStream(file));
								byte[] buffer = new byte[BUFFER];
								int len = -1;
								while ((len = is.read(buffer)) != -1) {
									zaos.write(buffer, 0, len);
								}

								// Writes all necessary data for this entry.
								zaos.closeArchiveEntry();
							} catch (Exception e) {
								throw new RuntimeException(e);
							} finally {
								IOUtils.closeStream(is);
							}
						}
					}
					zaos.finish();
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					IOUtils.closeStream(zaos);
				}
			}
		}
	}

	public static String convertEncoding(String s) {
		String s1 = s;
		try {
			s1 = new String(s.getBytes("UTF-8"), "GBK");
		} catch (Exception exception) {
		}
		return s1;
	}

	/**
	 * 
	 * 把zip文件解压到指定的文件夹
	 * 
	 * @param zipFilePath
	 *            zip文件路径, 如 "/var/data/aa.zip"
	 * 
	 * @param saveFileDir
	 *            解压后的文件存放路径, 如"/var/test/"
	 */
	public static void decompressZip(String zipFilePath, String saveFileDir) {
		if (isEndsWithZip(zipFilePath)) {
			File file = new File(zipFilePath);
			if (file.exists() && file.isFile()) {
				InputStream inputStream = null;
				ZipArchiveInputStream zais = null;
				try {
					inputStream = new FileInputStream(file);
					zais = new ZipArchiveInputStream(inputStream);
					ArchiveEntry archiveEntry = null;
					while ((archiveEntry = zais.getNextEntry()) != null) {
						String entryFileName = archiveEntry.getName();
						String entryFilePath = saveFileDir + entryFileName;
						byte[] content = new byte[(int) archiveEntry.getSize()];
						zais.read(content);
						OutputStream os = null;
						try {
							File entryFile = new File(entryFilePath);
							os = new BufferedOutputStream(new FileOutputStream(
									entryFile));
							os.write(content);
						} catch (IOException e) {
							throw new IOException(e);
						} finally {
							IOUtils.closeStream(os);
						}
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					IOUtils.closeStream(zais);
					IOUtils.closeStream(inputStream);
				}
			}
		}
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
		ZipInputStream zipInputStream = null;
		try {
			zipInputStream = new ZipInputStream(inputStream);
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
		} finally {
			IOUtils.closeStream(zipInputStream);
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
			JarOutputStream jos = new JarOutputStream(bos);
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
		Map<String, byte[]> zipMap = new java.util.HashMap<String, byte[]>();
		java.util.zip.ZipEntry zipEntry = null;
		ByteArrayOutputStream baos = null;
		BufferedOutputStream bos = null;
		byte tmpByte[] = null;
		try {
			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				tmpByte = new byte[BUFFER];
				baos = new ByteArrayOutputStream();
				bos = new BufferedOutputStream(baos, BUFFER);
				int i = 0;
				while ((i = zipInputStream.read(tmpByte, 0, BUFFER)) != -1) {
					bos.write(tmpByte, 0, i);
				}
				bos.flush();
				byte[] bytes = baos.toByteArray();
				IOUtils.closeStream(baos);
				IOUtils.closeStream(baos);
				zipMap.put(zipEntry.getName(), bytes);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(baos);
			IOUtils.closeStream(baos);
		}
		return zipMap;
	}

	public static Map<String, byte[]> getZipBytesMap(
			ZipInputStream zipInputStream, List<String> includes) {
		Map<String, byte[]> zipMap = new java.util.HashMap<String, byte[]>();
		java.util.zip.ZipEntry zipEntry = null;
		ByteArrayOutputStream baos = null;
		BufferedOutputStream bos = null;
		byte tmpByte[] = null;
		try {
			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				String name = zipEntry.getName();
				String ext = FileUtils.getFileExt(name);
				if (includes.contains(ext)
						|| includes.contains(ext.toLowerCase())) {
					tmpByte = new byte[BUFFER];
					baos = new ByteArrayOutputStream();
					bos = new BufferedOutputStream(baos, BUFFER);
					int i = 0;
					while ((i = zipInputStream.read(tmpByte, 0, BUFFER)) != -1) {
						bos.write(tmpByte, 0, i);
					}
					bos.flush();
					byte[] bytes = baos.toByteArray();
					IOUtils.closeStream(baos);
					IOUtils.closeStream(baos);
					zipMap.put(zipEntry.getName(), bytes);
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(baos);
			IOUtils.closeStream(baos);
		}
		return zipMap;
	}

	/**
	 * 
	 * 判断文件名是否以.zip为后缀
	 * 
	 * @param fileName
	 *            需要判断的文件名
	 * 
	 * @return
	 */
	public static boolean isEndsWithZip(String fileName) {
		boolean flag = false;
		if (fileName != null && !"".equals(fileName.trim())) {
			if (fileName.toLowerCase().endsWith(".zip")) {
				flag = true;
			}
		}

		return flag;
	}

	public static void makeZip(File dir, File zipFile) throws IOException,
			FileNotFoundException {
		JarOutputStream jos = new JarOutputStream(new FileOutputStream(zipFile));
		String as[] = dir.list();
		if (as != null) {
			for (int i = 0; i < as.length; i++)
				recurseFiles(jos, new File(dir, as[i]), "");
		}
		jos.close();
	}

	private static void recurseFiles(JarOutputStream jos, File file, String s)
			throws IOException, FileNotFoundException {

		if (file.isDirectory()) {
			s = s + file.getName() + "/";
			jos.putNextEntry(new JarEntry(s));
			String as[] = file.list();
			if (as != null) {
				for (int i = 0; i < as.length; i++)
					recurseFiles(jos, new File(file, as[i]), s);
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
		InputStream inputStream = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream baos = null;
		BufferedOutputStream bos = null;
		JarOutputStream jos = null;
		try {
			baos = new ByteArrayOutputStream();
			bos = new BufferedOutputStream(baos);
			jos = new JarOutputStream(bos);
			if (zipMap != null) {
				Set<Entry<String, byte[]>> entrySet = zipMap.entrySet();
				for (Entry<String, byte[]> entry : entrySet) {
					String name = entry.getKey();
					byte[] x_bytes = entry.getValue();
					inputStream = new ByteArrayInputStream(x_bytes);
					if (name != null && inputStream != null) {
						bis = new BufferedInputStream(inputStream);
						JarEntry jarEntry = new JarEntry(name);
						jos.putNextEntry(jarEntry);
						while ((len = bis.read(buf)) >= 0) {
							jos.write(buf, 0, len);
						}
						IOUtils.closeStream(bis);
						jos.closeEntry();
					}
					IOUtils.closeStream(inputStream);
				}
			}
			jos.flush();
			jos.close();

			bytes = baos.toByteArray();
			IOUtils.closeStream(baos);
			IOUtils.closeStream(bos);
			return bytes;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(inputStream);
			IOUtils.closeStream(baos);
			IOUtils.closeStream(bos);
		}
	}

	public static void unzip(File zipFile) {
		FileInputStream fileInputStream = null;
		ZipInputStream zipInputStream = null;
		FileOutputStream fileoutputstream = null;
		BufferedOutputStream bufferedoutputstream = null;
		try {
			fileInputStream = new FileInputStream(zipFile);
			zipInputStream = new ZipInputStream(fileInputStream);
			java.util.zip.ZipEntry zipEntry;
			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				byte abyte0[] = new byte[BUFFER];
				fileoutputstream = new FileOutputStream(zipEntry.getName());
				bufferedoutputstream = new BufferedOutputStream(
						fileoutputstream, BUFFER);
				int i;
				while ((i = zipInputStream.read(abyte0, 0, BUFFER)) != -1) {
					bufferedoutputstream.write(abyte0, 0, i);
				}
				bufferedoutputstream.flush();
				IOUtils.closeStream(fileoutputstream);
				IOUtils.closeStream(bufferedoutputstream);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(fileInputStream);
			IOUtils.closeStream(zipInputStream);
			IOUtils.closeStream(fileoutputstream);
			IOUtils.closeStream(bufferedoutputstream);
		}
	}

	public static void unzip(File zipFile, String dir) throws Exception {
		File file = new File(dir);
		FileUtils.mkdirsWithExistsCheck(file);
		FileInputStream fileInputStream = null;
		ZipInputStream zipInputStream = null;
		FileOutputStream fileoutputstream = null;
		BufferedOutputStream bufferedoutputstream = null;
		try {
			fileInputStream = new FileInputStream(zipFile);
			zipInputStream = new ZipInputStream(fileInputStream);
			fileInputStream = new FileInputStream(zipFile);
			zipInputStream = new ZipInputStream(fileInputStream);
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

				fileoutputstream = new FileOutputStream(s2);
				bufferedoutputstream = new BufferedOutputStream(
						fileoutputstream, BUFFER);
				int i = 0;
				while ((i = zipInputStream.read(abyte0, 0, BUFFER)) != -1) {
					bufferedoutputstream.write(abyte0, 0, i);
				}
				bufferedoutputstream.flush();
				IOUtils.closeStream(fileoutputstream);
				IOUtils.closeStream(bufferedoutputstream);
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(fileInputStream);
			IOUtils.closeStream(zipInputStream);
			IOUtils.closeStream(fileoutputstream);
			IOUtils.closeStream(bufferedoutputstream);
		}
	}

	public static void unzip(java.io.InputStream inputStream, String dir)
			throws Exception {
		File file = new File(dir);
		FileUtils.mkdirsWithExistsCheck(file);
		ZipInputStream zipInputStream = null;
		FileOutputStream fileoutputstream = null;
		BufferedOutputStream bufferedoutputstream = null;
		try {
			zipInputStream = new ZipInputStream(inputStream);

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
				fileoutputstream = new FileOutputStream(s2);
				bufferedoutputstream = new BufferedOutputStream(
						fileoutputstream, BUFFER);
				int i = 0;
				while ((i = zipInputStream.read(abyte0, 0, BUFFER)) != -1) {
					bufferedoutputstream.write(abyte0, 0, i);
				}
				bufferedoutputstream.flush();
				IOUtils.closeStream(fileoutputstream);
				IOUtils.closeStream(bufferedoutputstream);
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(zipInputStream);
			IOUtils.closeStream(fileoutputstream);
			IOUtils.closeStream(bufferedoutputstream);
		}
	}

	public static void unzip(java.io.InputStream inputStream, String path,
			List<String> excludes) throws Exception {
		File file = new File(path);
		FileUtils.mkdirsWithExistsCheck(file);
		ZipInputStream zipInputStream = null;
		FileOutputStream fileoutputstream = null;
		BufferedOutputStream bufferedoutputstream = null;
		try {
			zipInputStream = new ZipInputStream(inputStream);
			java.util.zip.ZipEntry zipEntry;
			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				boolean isDirectory = zipEntry.isDirectory();
				byte abyte0[] = new byte[BUFFER];
				String s1 = zipEntry.getName();
				String ext = FileUtils.getFileExt(s1);
				if (excludes.contains(ext)
						|| excludes.contains(ext.toLowerCase())) {
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
				fileoutputstream = new FileOutputStream(s2);
				bufferedoutputstream = new BufferedOutputStream(
						fileoutputstream, BUFFER);
				int i = 0;
				while ((i = zipInputStream.read(abyte0, 0, BUFFER)) != -1) {
					bufferedoutputstream.write(abyte0, 0, i);
				}
				bufferedoutputstream.flush();
				IOUtils.closeStream(fileoutputstream);
				IOUtils.closeStream(bufferedoutputstream);
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(zipInputStream);
			IOUtils.closeStream(fileoutputstream);
			IOUtils.closeStream(bufferedoutputstream);
		}
	}

	private ZipUtils() {
	}

}