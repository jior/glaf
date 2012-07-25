package com.glaf.base.modules.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.CRC32;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * @Title: Zipper.java
 * @Description: zip压缩解压类
 * @Copyright: Copyright (c) 2005
 * @Company: 
 * @author Xing
 * @Created on 2005-11-22
 * @version 1.0.051128
 * @version 1.1.060417 修正压缩时压缩路径不正确的BUG
 * @version 1.2.070627 解决压缩,解压缩中文名问题
 * @version 1.2.070713 增加压缩文件重命名功能
 */

public class Zipper {

	private static final int BUFFER = 1024;

	private static final long EMPTY_CRC = new CRC32().getValue();

	private List fileList = null;

	private File outputFile = null;

	private File[] file = null;

	private OutputStream outs = null;

	private ZipOutputStream zos = null;

	private String currentDirName;

	private boolean currentIsZipDir = false;

	private String filename = "";

	private String[] filenames = null;

	public Zipper() {
	}

	// 2005-11-27
	/**
	 * 压缩实例化
	 * 
	 * @param path
	 *            String 指定一个目录下的文件
	 * @param outputFile
	 *            Sting 压缩后文件存放目录
	 * @param file
	 *            String 要压缩的文件(可以是多个文件名和文件夹名,各个文件名和文件夹名之间的分隔符为竖线|.)
	 * @throws IOException
	 */
	public Zipper(String path, String outputFilePath, String file)
			throws IOException {
		String[] files = StringUtils.split(file, "|");
		int filesLength = files.length;

		outputFilePath = outputFilePath == null ? "" : outputFilePath;
		this.outputFile = new File(outputFilePath);
		// 当outputFilePath不为绝对路径时,取当前目录下的路径
		if (this.outputFile.toString().equals("/")
				|| this.outputFile.toString().equals("\\")
				|| !this.outputFile.exists()) {
			this.outputFile = new File(path, outputFilePath);
		}
		String outputFile = "NewZip.zip";
		// 当outputFile为空时,就取要压缩的文件中的第一个文件名来当压缩文件名
		if (filesLength > 0) {
			String f = files[0];
			if (new File(f).isDirectory() || new File(path, f).isDirectory()) {
				outputFile = new File(f).getName();
			} else {
				outputFile = getFileNameWithoutExtension(files[0]);
			}
			outputFile += ".zip";
		}
		this.outputFile = new File(this.outputFile, outputFile);

		File[] fileArray = new File[filesLength];
		for (int i = 0; i < filesLength; i++) {
			fileArray[i] = new File(files[i]);
			if (!fileArray[i].exists()) {
				fileArray[i] = new File(path, files[i]);
			}
		}
		this.file = fileArray;
	}

	/**
	 * 返回没有扩展名的文件名
	 * 
	 * @param fileName
	 *            String 文件名
	 * @return String
	 */
	private static String getFileNameWithoutExtension(String fileName) {
		fileName = new File(fileName).getName();
		int pos = fileName.lastIndexOf(".");
		return ((pos != -1) ? fileName.substring(0, pos) : "");
	}

	/**
	 * 压缩实例化
	 * 
	 * @param outputFile
	 *            压缩后文件
	 * @param file
	 *            要压缩的文件
	 * @throws IOException
	 */
	public Zipper(String outputFile, String file) throws IOException {
		this.outputFile = new File(outputFile);
		this.file = new File[1];
		this.file[0] = new File(file);
	}

	/**
	 * 压缩实例化
	 * 
	 * @param outputFile
	 *            压缩后文件
	 * @param file
	 *            要压缩的文件
	 * @throws IOException
	 */
	public Zipper(String outputFile, String[] file) throws IOException {
		this.outputFile = new File(outputFile);
		int size = file.length;
		this.file = new File[size];
		for (int i = 0; i < size; i++) {
			this.file[i] = new File(file[i]);
		}
	}

	/**
	 * 压缩实例化
	 * 
	 * @param outputFile
	 *            压缩后文件
	 * @param file
	 *            要压缩的文件
	 * @throws IOException
	 */
	public Zipper(File outputFile, File file) throws IOException {
		this.outputFile = outputFile;
		this.file = new File[1];
		this.file[0] = file;
	}

	/**
	 * 压缩实例化
	 * 
	 * @param outputFile
	 *            压缩后文件
	 * @param file
	 *            要压缩的文件
	 * @throws IOException
	 */
	public Zipper(File outputFile, File[] file) throws IOException {
		this.outputFile = outputFile;
		this.file = file;
	}

	/**
	 * 压缩实例化
	 * 
	 * @param file
	 *            要压缩的文件
	 * @throws IOException
	 */
	public Zipper(File[] file) throws IOException {
		this.file = file;
	}

	/**
	 * 压缩文件
	 * 
	 * @throws IOException
	 */
	public void zip() throws IOException {
		outs = new FileOutputStream(outputFile);
		zipOutputStream(outs);
		outs.flush();
		outs.close();
	}

	/**
	 * 压缩文件
	 * 
	 * @throws IOException
	 */
	public void zipOutputStream(OutputStream outs) throws IOException {
		fileList = new ArrayList();
		zos = new ZipOutputStream(outs);
		for (int i = 0; i < file.length; i++) {
			if (file[i].isDirectory()) {
				currentDirName = file[i].getAbsolutePath();
				currentIsZipDir = true;
				zipDir(file[i]);
			} else {
				currentDirName = new File(file[i].getParent())
						.getAbsolutePath();
				currentIsZipDir = false;

				zipFile(file[i]);
			}
		}
		zos.closeEntry();
		zos.flush();
		zos.close();
	}

	/**
	 * 压缩文件夹
	 * 
	 * @param dir
	 *            要压缩的文件夹
	 * @throws IOException
	 */
	private void zipDir(File dir) throws IOException {
		if (!dir.getPath().equals(currentDirName)) {
			String entryName = getEntryName(dir, currentDirName);
			ZipEntry ze = new ZipEntry(entryName + "/");
			if (dir != null && dir.exists()) {
				ze.setTime(dir.lastModified());
			} else {
				ze.setTime(System.currentTimeMillis());
			}
			ze.setSize(0);
			ze.setMethod(ZipEntry.STORED);
			// This is faintly ridiculous:
			ze.setCrc(EMPTY_CRC);
			zos.putNextEntry(ze);
		}

		if (dir.exists() && dir.isDirectory()) {
			File[] fileList = dir.listFiles();

			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isDirectory() && this.acceptDir(fileList[i])) {
					zipDir(fileList[i]);
				}
				if (fileList[i].isFile() && this.acceptFile(fileList[i])) {
					zipFile(fileList[i]);
				}
			}
		}

	}

	/**
	 * 压缩文件
	 * 
	 * @param file
	 *            要压缩的文件
	 * @throws IOException
	 */
	private void zipFile(File file) throws IOException {
		if (!file.equals(this.outputFile) && file.exists()) {

			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file), BUFFER);
			String entryName = getEntryName(file, currentDirName);
			if (filenames != null && filenames.length > fileList.size()) {
				entryName = StringUtils.isNotEmpty(filenames[fileList.size()]) ? filenames[fileList
						.size()]
						: entryName;
			}
			fileList.add(entryName);
			ZipEntry fileEntry = new ZipEntry(entryName);
			zos.putNextEntry(fileEntry);

			byte[] data = new byte[BUFFER];

			int byteCount;

			while ((byteCount = bis.read(data, 0, BUFFER)) != -1) {
				zos.write(data, 0, byteCount);
			}
			bis.close();

		}
	}

	private String getEntryName(File file, String currentDirName) {
		String entryName = file.getPath()
				.substring(currentDirName.length() + 1);
		entryName = entryName.replace('\\', '/');
		if (currentIsZipDir) {
			entryName = new File(currentDirName).getName() + "/" + entryName;
		}
		return entryName;
	}

	protected boolean acceptDir(File dir) {
		return true;
	}

	protected boolean acceptFile(File file) {
		return true;
	}

	private static final void copyInputStream(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[BUFFER];
		int len;

		while ((len = in.read(buffer)) >= 0) {
			out.write(buffer, 0, len);
		}

		in.close();
		out.close();
	}

	/**
	 * 解压文件
	 * 
	 * @param file
	 *            要解压的文件
	 * @param outputPath
	 *            解压目录
	 * @throws IOException
	 */
	public void unzip(String file, String outputPath) throws IOException {
		unzip(new File(file), new File(outputPath));
	}

	// 2005-11-28
	/**
	 * 解压文件
	 * 
	 * @param path
	 *            指定当前操作目录
	 * @param file
	 *            要解压的文件
	 * @param outputPath
	 *            解压目录
	 * @throws IOException
	 */
	public void unzip(String path, String file, String outputPath)
			throws IOException {
		unzip(getFilePath(path, file), getFilePath(path, outputPath));
	}

	/**
	 * 返回文件目录的路径
	 * 
	 * @param path
	 *            String 指定当前目录路径
	 * @param file
	 *            String 文件路径(可以是相对或者绝对路径,相对路径时就返回当前路径加上文件路径)
	 * @return String
	 */
	private static File getFilePath(String path, String file) {
		return new File(file).exists() ? new File(file) : new File(path, file);
	}

	/**
	 * 解压文件
	 * 
	 * @param file
	 *            要解压的文件
	 * @param outputPath
	 *            解压目录
	 * @throws IOException
	 */
	public void unzip(File file, File outputPath) throws IOException {
		fileList = new ArrayList();
		Enumeration entries;
		ZipFile zipFile = null;

		try {
			zipFile = new ZipFile(file);

			entries = zipFile.getEntries();
			// entries = zipFile.entries();

			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();

				if (entry.isDirectory()) {
					(new File(outputPath, entry.getName())).mkdir();
					continue;
				}

				// files
				File f = new File(outputPath, entry.getName());
				if (!f.getParentFile().exists()) {
					f.getParentFile().mkdirs();
				}
				copyInputStream(zipFile.getInputStream(entry),
						new BufferedOutputStream(new FileOutputStream(f)));
				fileList.add(entry.getName());
			}

			zipFile.close();
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 压缩/解压文件列表
	 * 
	 * @return
	 */
	public List getFileList() {
		return fileList;
	}

	/**
	 * 取压缩文件名列表
	 * 
	 * @return
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * 设置压缩文件名列表
	 * 
	 * @param filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
		filenames = StringUtils.split(filename, "|");
	}

	/**
	 * 取压缩文件名列表
	 * 
	 * @return
	 */
	public String[] getFilenames() {
		return filenames;
	}

	/**
	 * 设置压缩文件名列表
	 * 
	 * @param filenames
	 */
	public void setFilenames(String[] filenames) {
		this.filenames = filenames;
	}

	public static void main(String[] args) throws IOException {
		Zipper zip = new Zipper(new File("E:/upload/test.zip"), new File[] {
				new File("E:/upload/ECache.java"), new File("E:/upload/lib") });
		zip.zip();
		System.out.println(zip.getFileList());

		Zipper unzip = new Zipper();
		unzip.unzip(new File("E:/upload/upload.zip"), new File(
				"E:/upload/upload"));
		System.out.println(unzip.getFileList());

		Zipper zip2 = new Zipper("E:/upload/", null, "新建文件夹|文本1.txt|文本1.txt");
		zip2.setFilename("新建文件夹/3.txt|1.txt");
		zip2.zip();
		System.out.println(zip2.getFileList());

		System.out.println(new File("//test//t"));
	}

}
