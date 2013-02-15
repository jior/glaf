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
import java.util.Iterator;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipInputStream;

public class ZipTools {
	private static JarOutputStream jos;

	private static byte buf[] = new byte[256];

	private static int len;

	private static int BUFFER = 4096;

	private static String sp = System.getProperty("file.separator");

	private ZipTools() {
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

	public static byte[] getZipStream(Map dataMap) {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(baos);
			jos = new JarOutputStream(bos);
			if (dataMap != null) {
				Iterator iterator = dataMap.keySet().iterator();
				while (iterator.hasNext()) {
					String name = (String) iterator.next();
					InputStream inputStream = (InputStream) dataMap.get(name);
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
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public static void unzip(File zipFile) {
		try {
			FileInputStream fileinputstream = new FileInputStream(zipFile);
			CheckedInputStream checkedinputstream = new CheckedInputStream(
					fileinputstream, new Adler32());
			ZipInputStream zipinputstream = new ZipInputStream(
					new BufferedInputStream(checkedinputstream));
			java.util.zip.ZipEntry zipEntry;
			while ((zipEntry = zipinputstream.getNextEntry()) != null) {
				byte abyte0[] = new byte[BUFFER];
				FileOutputStream fileoutputstream = new FileOutputStream(
						zipEntry.getName());
				BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(
						fileoutputstream, BUFFER);
				int i;
				while ((i = zipinputstream.read(abyte0, 0, BUFFER)) != -1)
					bufferedoutputstream.write(abyte0, 0, i);
				bufferedoutputstream.flush();
				bufferedoutputstream.close();
			}
			zipinputstream.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void unzip(File zipFile, String dir) throws Exception {
		try {
			File file = new File(dir);
			if (!file.exists()) {
				file.mkdirs();
			}
			FileInputStream fileinputstream = new FileInputStream(zipFile);
			CheckedInputStream checkedinputstream = new CheckedInputStream(
					fileinputstream, new Adler32());
			ZipInputStream zipinputstream = new ZipInputStream(
					new BufferedInputStream(checkedinputstream));
			java.util.zip.ZipEntry zipEntry;
			while ((zipEntry = zipinputstream.getNextEntry()) != null) {
				boolean isDirectory = zipEntry.isDirectory();
				byte abyte0[] = new byte[BUFFER];
				String s1 = zipEntry.getName();
				s1 = convertEncoding(s1);

				String s2 = dir + "/" + s1;
				s2 = FileTools.getJavaFileSystemPath(s2);

				if (s2.indexOf('/') != -1 || isDirectory) {
					String s4 = s2.substring(0, s2.lastIndexOf('/'));
					File file2 = new File(s4);
					if (!file2.exists())
						file2.mkdirs();
				}

				if (isDirectory) {
					continue;
				}

				FileOutputStream fileoutputstream = new FileOutputStream(s2);
				BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(
						fileoutputstream, BUFFER);
				int i = 0;
				while ((i = zipinputstream.read(abyte0, 0, BUFFER)) != -1) {
					bufferedoutputstream.write(abyte0, 0, i);
				}
				bufferedoutputstream.flush();
				bufferedoutputstream.close();
			}

			zipinputstream.close();
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}
	}

	public static void unzip(java.io.InputStream zipInputStream, String dir)
			throws Exception {
		try {
			File file = new File(dir);
			if (!file.exists()) {
				file.mkdirs();
			}
			CheckedInputStream checkedinputstream = new CheckedInputStream(
					zipInputStream, new Adler32());
			ZipInputStream zipinputstream = new ZipInputStream(
					new BufferedInputStream(checkedinputstream));
			java.util.zip.ZipEntry zipEntry;
			while ((zipEntry = zipinputstream.getNextEntry()) != null) {
				boolean isDirectory = zipEntry.isDirectory();
				byte abyte0[] = new byte[BUFFER];
				String s1 = zipEntry.getName();

				s1 = convertEncoding(s1);

				String s2 = dir + sp + s1;
				s2 = FileTools.getJavaFileSystemPath(s2);

				if (s2.indexOf('/') != -1 || isDirectory) {
					String s4 = s2.substring(0, s2.lastIndexOf('/'));
					File file2 = new File(s4);
					if (!file2.exists()) {
						file2.mkdirs();
					}
				}
				if (isDirectory) {
					continue;
				}
				FileOutputStream fileoutputstream = new FileOutputStream(s2);
				BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(
						fileoutputstream, BUFFER);
				int i = 0;
				while ((i = zipinputstream.read(abyte0, 0, BUFFER)) != -1) {
					bufferedoutputstream.write(abyte0, 0, i);
				}
				bufferedoutputstream.flush();
				bufferedoutputstream.close();
			}

			zipinputstream.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new Exception(exception.getMessage());
		}
	}

	public static byte[] getBytes(byte[] bytes, String name) {
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(bytes);
			return getBytes(inputStream, name);
		} catch (Exception ex) {
			ex.printStackTrace();
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

	public static String convertEncoding(String s) {
		String s1 = s;
		try {
			s1 = new String(s.getBytes("UTF-8"), "GB2312");
		} catch (Exception exception) {
		}
		return s1;
	}

	public static void main(String[] args) throws Exception {
		// ZipTools.makeZip(new File("E:\\JBoss\\jpage\\java\\hbm"), new
		// File("hbm.zip"));
		Map dataMap = new HashMap();
		dataMap.put("build.xml", new FileInputStream("build.xml"));
		InputStream in = new ByteArrayInputStream(ZipTools
				.getZipStream(dataMap));
		FileTools.save("resources.zip", in);
		in.close();
	}

}