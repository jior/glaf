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

package org.jpage.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.commons.codec.binary.Base64;

public class FileTools {

	public final static String sp = System.getProperty("file.separator");

	public final static String newline = System.getProperty("line.separator");

	private FileTools() {
	}

	public static void mkdir(String path) throws IOException {
		if (path == null) {
			return;
		}
		path = getJavaFileSystemPath(path);
		if (!path.endsWith(sp)) {
			path = path + sp;
		}
		java.io.File directory = new java.io.File(path);
		if (!directory.exists()) { // 如果目录不存在，新建一个
			directory.mkdirs();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filename
	 *            文件名
	 * @throws Exception
	 */
	public static void deleteFile(String filename) throws IOException {
		if (filename == null) {
			return;
		}
		filename = getJavaFileSystemPath(filename);
		java.io.File file = new java.io.File(filename);
		if (file.exists() && file.isFile()) {
			file.delete();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filename
	 *            文件名
	 * @throws Exception
	 */
	public static void renameTo(String oldFilename, String newFilename)
			throws IOException {
		if (oldFilename == null || newFilename == null) {
			return;
		}
		oldFilename = getJavaFileSystemPath(oldFilename);
		newFilename = getJavaFileSystemPath(newFilename);
		java.io.File file = new java.io.File(oldFilename);
		if (file.exists() && file.isFile()) {
			file.renameTo(new File(newFilename));
		}
	}

	/**
	 * 删除目录树
	 * 
	 * @param filePath
	 *            路径
	 * @throws Exception
	 */
	public static void deleteFileTree(String filePath) throws IOException {
		if (filePath == null) {
			return;
		}
		filePath = getJavaFileSystemPath(filePath);
		java.io.File file = new java.io.File(filePath);
		if (file.isDirectory()) { // 删除子目录
			String[] filelist = file.list(); // 列出所有的子文件（夹）名字
			for (int i = 0; i < filelist.length; i++) {
				deleteFileTree(filePath + sp + filelist[i]);
			}
		} else { // 删除文件
			file.delete();
		}
		// 不删除文件目录
		// file.delete(); // 删除该文件夹
	}

	/**
	 * 将文件转换为字节流
	 * 
	 * @param stream
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytes(String filename) throws IOException {
		if (filename == null) {
			throw new java.lang.IllegalArgumentException("文件名不能为空！");
		}
		filename = getJavaFileSystemPath(filename);
		File file = null;
		FileInputStream fin = null;
		try {
			file = new File(filename);
			fin = new FileInputStream(file);
			return getBytes(fin);
		} catch (IOException ex) {
			fin = null;
			throw ex;
		} finally {
			try {
				if (fin != null) {
					fin.close();
					fin = null;
				}
			} catch (IOException ioe) {
			}
		}
	}

	/**
	 * 将文件转换为字节流
	 * 
	 * @param stream
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytes(File file) throws IOException {
		if (file == null) {
			throw new java.lang.IllegalArgumentException("文件信息不能为空！");
		}
		FileInputStream fin = new FileInputStream(file);
		return getBytes(fin);
	}

	public static java.io.InputStream getInputStream(String filename)
			throws IOException {
		if (filename == null) {
			throw new java.lang.IllegalArgumentException("文件名不能为空！");
		}
		File file = null;
		FileInputStream fin = null;
		try {
			file = new File(filename);
			if (file != null && file.isFile()) {
				fin = new FileInputStream(file);
				return fin;
			}
			return null;
		} catch (IOException ex) {
			fin = null;
			throw ex;
		}
	}

	public static byte[] getBytes(InputStream inputStream) {
		if (inputStream == null) {
			return null;
		}
		ByteArrayOutputStream baos = null;
		BufferedOutputStream bos = null;
		try {
			baos = new ByteArrayOutputStream();
			bos = new BufferedOutputStream(baos);
			synchronized (inputStream) {
				synchronized (bos) {
					byte[] buffer = new byte[256];
					while (true) {
						int bytesRead = inputStream.read(buffer);
						if (bytesRead == -1)
							break;
						bos.write(buffer, 0, bytesRead);
					}
					bos.flush();
					baos.flush();
					baos.close();
					byte[] bytes = baos.toByteArray();
					bos.close();
					baos = null;
					bos = null;
					return bytes;
				}
			}
		} catch (IOException ex) {
			baos = null;
			bos = null;
			throw new RuntimeException(ex);
		} finally {
			try {
				if (baos != null) {
					baos.close();
					baos = null;
				}
				if (bos != null) {
					bos.close();
					bos = null;
				}
			} catch (IOException ioe) {
			}
		}
	}

	/**
	 * 拷贝文件的方法
	 * 
	 * @param srcFile
	 * @param descFile
	 */

	public static void copy(String srcFile, String descFile) throws IOException {
		if (srcFile == null || descFile == null) {
			throw new java.lang.IllegalArgumentException("源文件和目标文件都不能为空！");
		}
		FileInputStream fin = null;
		OutputStream fout = null;
		String path = "";
		try {
			if (descFile.indexOf(sp) != -1) {
				path = descFile.substring(0, descFile.lastIndexOf(sp));
			}
			path = getJavaFileSystemPath(path);
			java.io.File directory = new java.io.File(path + sp);
			if (!directory.exists()) { // 如果目录不存在，新建一个
				directory.mkdirs();
			}
			srcFile = getJavaFileSystemPath(srcFile);
			descFile = getJavaFileSystemPath(descFile);
			fin = new FileInputStream(srcFile);
			fout = new FileOutputStream(descFile);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = fin.read(buffer, 0, 8192)) != -1) {
				fout.write(buffer, 0, bytesRead);
			}
			fout.close();
			fin.close();
			fout = null;
			fin = null;
			buffer = null;
		} catch (IOException ex) {
			fout = null;
			fin = null;
			throw ex;
		} finally {
			try {
				if (fin != null) {
					fin.close();
				}
				if (fout != null) {
					fout.close();
				}
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filename
	 */
	public static void delete(String filename) {
		if (filename == null) {
			return;
		}
		java.io.File file = new java.io.File(filename);
		if (file.isFile()) {
			file.delete();
		}
	}

	/**
	 * 功能:文件的转换为StringBuffer
	 * 
	 * @param 文件的路径
	 * @return 字符串集合
	 */
	public static StringBuffer fileToStringBuffer(String pFileName)
			throws IOException {
		if (pFileName == null) {
			throw new java.lang.IllegalArgumentException("文件名不能为空！");
		}
		BufferedReader in = null;
		try {
			pFileName = getJavaFileSystemPath(pFileName);
			File file = new File(pFileName);
			if (!(file.exists() && file.isFile())) {
				return null;
			}
			in = new BufferedReader(new FileReader(file));
			String temp = "";
			StringBuffer sb = new StringBuffer();
			while ((temp = in.readLine()) != null) {
				sb.append(temp);
				sb.append(newline);
				temp = "";
			}
			in.close();
			in = null;
			return sb;
		} catch (IOException ex) {
			in = null;
			throw ex;
		} finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (IOException ioe) {
			}
		}
	}

	/**
	 * 功能:文件的转换为StringBuffer
	 * 
	 * @param 文件的路径
	 * @return 字符串集合
	 */
	public static String file2HTMLString(String pFileName) throws IOException {
		if (pFileName == null) {
			throw new java.lang.IllegalArgumentException("文件名不能为空！");
		}
		BufferedReader in = null;
		try {
			pFileName = getJavaFileSystemPath(pFileName);
			File file = new File(pFileName);
			if (!(file.exists() && file.isFile())) {
				return null;
			}
			in = new BufferedReader(new FileReader(file));
			String temp = "";
			StringBuffer sb = new StringBuffer();
			while ((temp = in.readLine()) != null) {
				sb.append(temp);
				sb.append(newline);
				sb.append("<BR>");
				temp = "";
			}
			in.close();
			in = null;
			return sb.toString();
		} catch (IOException ex) {
			in = null;
			throw ex;
		} finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (IOException ioe) {
			}
		}
	}

	public static void save(String filename, byte[] bytes) throws IOException {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			save(filename, bais);
			bais.close();
			bais = null;
		} catch (IOException ex) {
			throw ex;
		} finally {
			try {
				if (bais != null) {
					bais.close();
				}
			} catch (IOException ex) {
			}
		}
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
			directory.mkdirs();
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

	public static void save(String filename, String encoding,
			InputStream inputStream) throws IOException {
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
			directory.mkdirs();
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

	public static String replaceWin32FileName(String pFileName) {
		if (pFileName == null) {
			return null;
		}
		pFileName = pFileName.replace('\\', '_');
		pFileName = pFileName.replace('/', '_');
		pFileName = pFileName.replace(':', '_');
		pFileName = pFileName.replace('*', '_');
		pFileName = pFileName.replace('?', '_');
		pFileName = pFileName.replace('\"', '_');
		pFileName = pFileName.replace('<', '_');
		pFileName = pFileName.replace('>', '_');
		pFileName = pFileName.replace('|', '_');
		return pFileName;
	}

	public static String getJavaFileSystemPath(String path) {
		if (path == null) {
			return null;
		}
		path = path.replace('\\', '/');
		return path;
	}

	public static String getWebFilePath(String path) {
		if (path == null) {
			return null;
		}
		path = path.replace('\\', '/');
		return path;
	}

	public static byte[] getBytesFromPEM(byte[] inbuf, String beginKey,
			String endKey) throws IOException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(inbuf);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		String temp;
		while (((temp = reader.readLine()) != null) && !temp.equals(beginKey)) {
			continue;
		}
		if (temp == null) {
			throw new IOException("Error in input buffer, missing " + beginKey
					+ " boundary");
		}
		while (((temp = reader.readLine()) != null) && !temp.equals(endKey)) {
			ps.print(temp);
		}
		if (temp == null) {
			throw new IOException("Error in input buffer, missing " + endKey
					+ " boundary");
		}
		ps.close();
		byte[] bytes = baos.toByteArray();
		bytes = Base64.decodeBase64(bytes);
		return bytes;
	}

	public static byte[] readFiletoBuffer(String file) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream in = new FileInputStream(file);
		int len = 0;
		byte[] buf = new byte[1024];
		while ((len = in.read(buf)) > 0) {
			baos.write(buf, 0, len);
		}
		in.close();
		baos.close();
		byte[] bytes = baos.toByteArray();
		return bytes;
	}

	public static void main(String[] args) throws Exception {
		byte[] bytes = FileTools.getBytes(new FileInputStream("./test/12.txt"));
		System.out.println(bytes.length);
		System.out.println(new String(bytes));
	}

}