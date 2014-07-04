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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpUtils {

	protected final static Logger logger = LoggerFactory
			.getLogger(FtpUtils.class);

	protected static FTPClient ftpClient;

	/**
	 * 关闭FTP
	 */
	public static void closeConnect() {
		try {
			ftpClient.disconnect();
			logger.info("disconnect success");
		} catch (IOException ex) {
			logger.error("disconnect error", ex);
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 
	 * @param ip
	 *            服务器名或IP地址
	 * @param port
	 *            端口
	 * @param user
	 *            用户名
	 * @param password
	 *            密码
	 * @param path
	 *            服务器上的路径
	 */
	public static void connectServer(String ip, int port, String user,
			String password, String path) {
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ip, port);
			ftpClient.login(user, password);
			logger.info("login success!");
			if (path != null && path.length() != 0) {
				ftpClient.changeWorkingDirectory(path);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			logger.error("login failed", ex);
			throw new RuntimeException(ex);
		}
	}

	public static void download(String remoteFile, String localFile) {
		InputStream in = null;
		OutputStream out = null;
		try {
			// 设置被动模式
			ftpClient.enterLocalPassiveMode();
			// 设置以二进制方式传输
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			FTPFile[] files = ftpClient.listFiles(new String(remoteFile
					.getBytes("GBK"), "ISO-8859-1"));
			if (files.length != 1) {
				logger.warn("remote file is not exists");
				return;
			}
			long lRemoteSize = files[0].getSize();
			File file = new File(localFile);
			out = new FileOutputStream(file);
			in = ftpClient.retrieveFileStream(new String(remoteFile
					.getBytes("GBK"), "ISO-8859-1"));
			byte[] bytes = new byte[4096];
			long step = lRemoteSize / 100;
			long progress = 0;
			long localSize = 0L;
			int c;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);
				localSize += c;
				long nowProgress = localSize / step;
				if (nowProgress > progress) {
					progress = nowProgress;
					if (progress % 10 == 0) {
						logger.debug("download progress:" + progress);
					}
				}
			}
			out.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("download error", ex);
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(in);
			IOUtils.closeStream(out);
		}
	}

	/**
	 * 根据传输文件下载
	 * 
	 * @param remotePath
	 *            FTP路径
	 * @param remoteFile
	 *            FTP文件
	 * @param localFile
	 *            本地文件全路径
	 */
	public static void download(String remotePath, String remoteFile,
			String localFile) {
		try {
			if (remotePath != null && remotePath.length() != 0) {
				ftpClient.changeWorkingDirectory(remotePath);
			}
			download(remoteFile, localFile);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("download error", ex);
			throw new RuntimeException(ex);
		}
	}

	public static byte[] getBytes(String remoteFile) {
		byte[] bytes = null;
		InputStream in = null;
		ByteArrayOutputStream out = null;
		BufferedOutputStream bos = null;
		try {
			// 设置被动模式
			ftpClient.enterLocalPassiveMode();
			// 设置以二进制方式传输
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			FTPFile[] files = ftpClient.listFiles(new String(remoteFile
					.getBytes("GBK"), "ISO-8859-1"));
			if (files.length != 1) {
				logger.warn("remote file is not exists");
				return null;
			}
			long lRemoteSize = files[0].getSize();

			out = new ByteArrayOutputStream();
			bos = new BufferedOutputStream(out);
			in = ftpClient.retrieveFileStream(new String(remoteFile
					.getBytes("GBK"), "ISO-8859-1"));
			byte[] buff = new byte[4096];
			long step = lRemoteSize / 100;
			long progress = 0;
			long localSize = 0L;
			int c;
			while ((c = in.read(buff)) != -1) {
				out.write(buff, 0, c);
				localSize += c;
				long nowProgress = localSize / step;
				if (nowProgress > progress) {
					progress = nowProgress;
					if (progress % 10 == 0) {
						logger.debug("download progress:" + progress);
					}
				}
			}
			out.flush();
			bos.flush();
			bytes = out.toByteArray();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("download error", ex);
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(in);
			IOUtils.closeStream(out);
		}
		return bytes;
	}

	/**
	 * 根据传输文件下载
	 * 
	 * @param remotePath
	 *            FTP路径
	 * @param remoteFile
	 *            FTP文件
	 * @param localFile
	 *            本地文件全路径
	 */
	public static byte[] getBytes(String remotePath, String remoteFile) {
		try {
			if (remotePath != null && remotePath.length() != 0) {
				ftpClient.changeWorkingDirectory(remotePath);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("download error", ex);
		}
		return getBytes(remoteFile);
	}

	/**
	 * 根据文件路径上传
	 * 
	 * @param remoteFile
	 *            FTP文件
	 * @param input
	 *            本地输入流
	 */
	public static boolean upload(String remoteFile, byte[] bytes) {
		ByteArrayInputStream bais = null;
		BufferedInputStream bis = null;
		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			bais = new ByteArrayInputStream(bytes);
			bis = new BufferedInputStream(bais);
			boolean flag = ftpClient.storeFile(remoteFile, bis);
			if (flag) {
				logger.info("upload success");
			} else {
				logger.info("upload failure");
			}
			return flag;
		} catch (IOException ex) {
			ex.printStackTrace();
			logger.error("upload error", ex);
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(bis);
			IOUtils.closeStream(bais);
		}
	}

	/**
	 * 根据文件路径上传
	 * 
	 * @param remoteFile
	 *            FTP文件
	 * @param input
	 *            本地文件流
	 */
	public static boolean upload(String remoteFile, InputStream input) {
		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			boolean flag = ftpClient.storeFile(remoteFile, input);
			if (flag) {
				logger.info("upload success");
			} else {
				logger.info("upload failure");
			}
			return flag;
		} catch (IOException ex) {
			ex.printStackTrace();
			logger.error("upload error", ex);
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 根据文件路径上传
	 * 
	 * @param remoteFile
	 *            FTP文件
	 * @param localFile
	 *            本地文件全路径
	 */
	public static boolean upload(String remoteFile, String localFile) {
		InputStream input = null;
		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			File file = new File(localFile);
			input = new FileInputStream(file);
			boolean flag = ftpClient.storeFile(remoteFile, input);
			if (flag) {
				logger.info("upload success");
			} else {
				logger.info("upload failure");
			}
			return flag;
		} catch (IOException ex) {
			ex.printStackTrace();
			logger.error("upload error", ex);
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(input);
		}
	}

	/**
	 * 根据文件路径上传
	 * 
	 * @param remotePath
	 *            FTP路径
	 * @param remoteFile
	 *            FTP文件
	 * @param bytes
	 *            本地字节流
	 */
	public static boolean upload(String remotePath, String remoteFile,
			byte[] bytes) {
		try {
			if (remotePath != null && remotePath.length() != 0) {
				ftpClient.changeWorkingDirectory(remotePath);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("change working directory error", ex);
		}
		return upload(remoteFile, bytes);
	}

	/**
	 * 根据文件路径上传
	 * 
	 * @param remotePath
	 *            FTP路径
	 * @param remoteFile
	 *            FTP文件
	 * @param input
	 *            本地输入流
	 */
	public static boolean upload(String remotePath, String remoteFile,
			InputStream input) {
		try {
			if (remotePath != null && remotePath.length() != 0) {
				ftpClient.changeWorkingDirectory(remotePath);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("change working directory error", ex);
		}
		return upload(remoteFile, input);
	}

	/**
	 * 根据文件路径上传
	 * 
	 * @param remotePath
	 *            FTP路径
	 * @param remoteFile
	 *            FTP文件
	 * @param localFile
	 *            本地文件全路径
	 */
	public static boolean upload(String remotePath, String remoteFile,
			String localFile) {
		try {
			if (remotePath != null && remotePath.length() != 0) {
				ftpClient.changeWorkingDirectory(remotePath);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("change working directory error", ex);
		}
		return upload(remoteFile, localFile);
	}
}