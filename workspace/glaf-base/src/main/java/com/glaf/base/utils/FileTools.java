package com.glaf.base.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;

import org.springframework.util.FileCopyUtils;

public class FileTools {

	public static final int BUFFER_SIZE = 65536;//64K

	public final static String sp = System.getProperty("file.separator");

	public final static String newline = System.getProperty("line.separator");

	private static DecimalFormat oneDecimal = new DecimalFormat("0.0");

	/**
	 * 拷贝文件的方法
	 * 
	 * @param srcFile
	 * @param descFile
	 */
	public static void copy(String srcFile, String descFile) throws IOException {
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
				boolean isOK = directory.mkdirs();
				if (isOK) {
					throw new RuntimeException(directory.getAbsolutePath()
							+ " create error ");
				}
			}
			srcFile = getJavaFileSystemPath(srcFile);
			descFile = getJavaFileSystemPath(descFile);
			fin = new FileInputStream(srcFile);
			fout = new FileOutputStream(descFile);
			int bytesRead = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = fin.read(buffer, 0, 8192)) != -1) {
				fout.write(buffer, 0, bytesRead);
			}
			fout.close();
			fin.close();
			fout = null;

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

	public static void copyJar(File src, File dest) throws IOException {
		if (src.isDirectory()) {
			File[] entries = src.listFiles();
			if (entries == null) {
				throw new IOException("Could not list files in directory: "
						+ src);
			}
			for (int i = 0; i < entries.length; i++) {
				File file = entries[i];
				copyJar(file, new File(dest, file.getName()));
			}
		} else if (src.isFile() && src.getName().endsWith(".jar")) {
			try {
				dest.createNewFile();
			} catch (IOException ex) {
			}
			FileCopyUtils.copy(src, dest);
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
			boolean isOK = file.delete();
			if (!isOK) {
				throw new RuntimeException(file.getAbsolutePath()
						+ " delete error");
			}
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
		if (file.exists() && file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				deleteFileTree(filePath + sp + filelist[i]);
			}
		} else {
			if (file.exists()) {
				boolean isOK = file.delete();
				if (!isOK) {
					throw new RuntimeException(file.getAbsolutePath()
							+ " delete error");
				}
			}
		}
	}

	public static String displayFileSize(int fileSizeInBytes) {
		if (fileSizeInBytes >= 1073741824) {
			double size = fileSizeInBytes * 1.0D / 1024 / 1024 / 1024;
			size = Math.round(size * 10D) / 10D;
			return size + " GB";
		} else if (fileSizeInBytes >= 1048576) {
			double size = fileSizeInBytes * 1.0D / 1024 / 1024;
			size = ((int) (size * 100D)) / 100D;
			return size + " MB";
		} else if (fileSizeInBytes >= 1024) {
			double size = fileSizeInBytes * 1.0D / 1024;
			size = Math.round(size);
			return size + " KB";
		} else {
			return fileSizeInBytes + " Bytes";
		}
	}

	
	/**
	 * 将文件转换为字节流
	 * 
	 * @param stream
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytes(File file) {
		if (file == null) {
			throw new java.lang.IllegalArgumentException("文件信息不能为空！");
		}
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
		} catch (IOException ex) {
			fin = null;
			throw new RuntimeException(ex);
		}
		return getBytes(fin);
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
					byte[] buffer = new byte[BUFFER_SIZE];
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
	 * 将文件转换为字节流
	 * 
	 * @param stream
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytes(String filename) {
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
			throw new RuntimeException(ex);
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

	public static String getFileExt(String fileName) {
		String value = "";
		int start = 0;
		int end = 0;
		if (fileName == null) {
			return null;
		}
		start = fileName.lastIndexOf(46) + 1;
		end = fileName.length();
		value = fileName.substring(start, end);
		if (fileName.lastIndexOf(46) > 0) {
			return value;
		} else {
			return "";
		}
	}

	public static String getFileName(String filePathName) {
		int pos = 0;
		pos = filePathName.lastIndexOf(47);
		if (pos != -1) {
			return filePathName.substring(pos + 1, filePathName.length());
		}
		pos = filePathName.lastIndexOf(92);
		if (pos != -1) {
			return filePathName.substring(pos + 1, filePathName.length());
		} else {
			return filePathName;
		}
	}

	public static java.io.InputStream getInputStream(String filename) {
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
			throw new RuntimeException(ex);
		}
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

	/**
	 * Given an integer, return a string that is in an approximate, but human
	 * readable format. It uses the bases 'k', 'm', and 'g' for 1024, 1024**2,
	 * and 1024**3.
	 * 
	 * @param number
	 *            the number to format
	 * @return a human readable form of the integer
	 */
	public static String humanReadableInt(long number) {
		long absNumber = Math.abs(number);
		double result = number;
		String suffix = "";
		if (absNumber < 1024) {
			// nothing
		} else if (absNumber < 1024 * 1024) {
			result = number / 1024.0;
			suffix = "KB";
		} else if (absNumber < 1024 * 1024 * 1024) {
			result = number / (1024.0 * 1024);
			suffix = "MB";
		} else {
			result = number / (1024.0 * 1024 * 1024);
			suffix = "GB";
		}
		return oneDecimal.format(result) + suffix;
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
		if (!directory.exists()) {
			// 如果目录不存在，新建一个
			boolean isOK = directory.mkdirs();
			if (!isOK) {

			}
		}
	}

	public static byte[] readBytes(InputStream inputStream) {
		byte[] bytes = null;
		if (inputStream == null) {
			throw new RuntimeException("inputStream is null");
		}
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			transfer(inputStream, outputStream);
			bytes = outputStream.toByteArray();
			outputStream.close();
			return bytes;
		} catch (IOException ex) {
			throw new RuntimeException("couldn't read bytes from inputStream",
					ex);
		}
	}

	public static String readFile(InputStream inputStream) throws IOException {
		StringBuffer buffer = new StringBuffer();
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

	public static String readFile(String filename) throws IOException {
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = new FileInputStream(filename);
		if (inputStream != null) {
			buffer.append(readFile(inputStream));
		}
		return buffer.toString();
	}

	public static byte[] readFileToBuffer(String file) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream in = new FileInputStream(file);
		int len = 0;
		byte[] buf = new byte[BUFFER_SIZE];
		while ((len = in.read(buf)) > 0) {
			baos.write(buf, 0, len);
		}
		in.close();
		baos.close();
		byte[] bytes = baos.toByteArray();
		return bytes;
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
			boolean isOK = directory.mkdirs();
			if (!isOK) {

			}
		}
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(inputStream);
			bos = new BufferedOutputStream(new FileOutputStream(filename));
			int bytesRead = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = bis.read(buffer, 0, BUFFER_SIZE)) != -1) {
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

	public static int transfer(InputStream in, OutputStream out) {
		int total = 0;
		byte[] buffer = new byte[BUFFER_SIZE];
		try {
			int bytesRead = in.read(buffer);
			while (bytesRead != -1) {
				out.write(buffer, 0, bytesRead);
				total += bytesRead;
				bytesRead = in.read(buffer);
			}
			return total;
		} catch (IOException ex) {
			throw new RuntimeException("couldn't write bytes to output stream",
					ex);
		}
	}

	private FileTools() {

	}
}
