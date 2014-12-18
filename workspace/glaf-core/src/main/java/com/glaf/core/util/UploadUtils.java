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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.FileUtils;

public class UploadUtils {
	protected final static Log logger = LogFactory.getLog(UploadUtils.class);

	public final static String UPLOAD_DIR = "/upload/";

	/**
	 * 取上传文件字节流
	 * 
	 * @param request
	 * @param fileParam
	 * @return
	 */
	public static byte[] getBytes(HttpServletRequest request, String fileParam) {
		MultipartFile mFile = getMultipartFile(request, fileParam);
		byte[] bytes = null;
		try {
			if (mFile != null && !mFile.isEmpty()) {
				bytes = mFile.getBytes();
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		return bytes;
	}

	/**
	 * 取上传文件流
	 * 
	 * @param request
	 * @param fileParam
	 * @return
	 */
	public static InputStream getInputStream(HttpServletRequest request,
			String fileParam) {
		MultipartFile mFile = getMultipartFile(request, fileParam);
		InputStream inputStream = null;
		try {
			if (mFile != null && !mFile.isEmpty()) {
				inputStream = mFile.getInputStream();
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		return inputStream;
	}

	/**
	 * 取MultipartFile
	 * 
	 * @param request
	 * @param fileParam
	 * @param uploadDir
	 * @return
	 */
	public static MultipartFile getMultipartFile(HttpServletRequest request,
			String fileParam) {
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		return mRequest.getFile(fileParam);
	}

	private static String getSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 取得上传目录的绝对路径
	 */
	public static String getUploadAbsolutePath(String uploadDir) {
		if (StringUtils.isEmpty(uploadDir)) {
			return SystemProperties.getAppPath() + uploadDir;
		} else {
			return SystemProperties.getAppPath() + UPLOAD_DIR;
		}
	}

	/**
	 * 取得上传目录的相对路径
	 */
	public static String getUploadRelativePath(String uploadDir) {
		if (StringUtils.isEmpty(uploadDir)) {
			return uploadDir;
		} else {
			return UPLOAD_DIR;
		}
	}

	/**
	 * 判断上传文件格式是否符合要求
	 */
	public static int getUploadStatus(HttpServletRequest request,
			String fileParam, String fileType) {
		return getUploadStatus(request, fileParam, fileType, -1);
	}

	/**
	 * 判断上传文件格式是否符合要求
	 * 
	 * @param request
	 * @param fileParam
	 * @param fileType
	 *            格式如:jpg,gif,png,jpeg,swf
	 * @param fileSize
	 *            以MB为单位
	 * @return status=0 通过合法性检查<br/>
	 *         status=1 文件类型错误<br/>
	 *         status=2 文件超过最大限制<br/>
	 */
	public static int getUploadStatus(HttpServletRequest request,
			String fileParam, String fileType, long fileSize) {
		int status = 0;
		MultipartFile mFile = getMultipartFile(request, fileParam);
		if (!mFile.isEmpty()) {
			String ext = FileUtils.getFileExt(mFile.getOriginalFilename());
			if (!StringUtils.containsIgnoreCase(fileType, ext)) {
				status = 1;
			}
			long size = mFile.getSize();
			if (fileSize != -1 && size > FileUtils.MB_SIZE * fileSize) {
				status = 2;
			}
		}
		return status;
	}

	public static String mkdirs(String root) {
		String sp = File.separator;
		String parent = String.valueOf(DateUtils.getNowYearMonth());
		String child = String.valueOf(DateUtils.getNowYearMonthDay());
		String path = root + sp + parent + sp + child;
		File dir = new File(path);
		if (!dir.exists() || !dir.isDirectory()) {
			try {
				FileUtils.mkdirs(path);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return path;
	}

	/**
	 * 重命名
	 * 
	 * @param name
	 * @return
	 */
	public static String rename(String name) {
		Long now = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date()));
		String fileName = name;
		if (name.indexOf(".") != -1) {
			fileName = name.substring(0, name.lastIndexOf(".")) + "_" + now
					+ name.substring(name.lastIndexOf("."));
		}
		return fileName;
	}

	/**
	 * 上传文件
	 */
	public static String upload(HttpServletRequest request, String fileParam) {
		return upload(request, fileParam, null);
	}

	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param fileParam
	 * @param uploadDir
	 *            自定义上传文件保存路径
	 * @return 返回相对路径
	 */
	public static String upload(HttpServletRequest request, String fileParam,
			String uploadDir) {
		MultipartFile mFile = getMultipartFile(request, fileParam);
		String filePath = "";
		try {
			String pathDir = mkdirs(getUploadAbsolutePath(uploadDir));
			if (!mFile.isEmpty()) {
				String fileName = mFile.getOriginalFilename();
				String saveName = rename(fileName);
				mFile.transferTo(new File(getUploadAbsolutePath(uploadDir)
						+ pathDir + saveName));
				filePath = getUploadRelativePath(uploadDir) + pathDir
						+ saveName;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		return filePath;
	}

	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param fileParam
	 * @param uploadDir
	 *            上传文件保存相对路径
	 * @return 返回相对路径
	 */
	public static String upload(HttpServletRequest request, String fileParam,
			String uploadDir, String fileNameParam) {
		MultipartFile mFile = getMultipartFile(request, fileParam);
		String filePath = "";
		if (StringUtils.isEmpty(uploadDir)) {
			uploadDir = UPLOAD_DIR;
		}
		try {
			FileUtils.mkdirs(getUploadAbsolutePath(uploadDir));
			if (!mFile.isEmpty()) {
				String fileName = mFile.getOriginalFilename();
				String saveName = "";
				if (StringUtils.isEmpty(fileNameParam)) {
					saveName = fileNameParam + getSuffix(fileName);
				} else {
					saveName = rename(fileName);
				}
				mFile.transferTo(new File(getUploadAbsolutePath(uploadDir)
						+ saveName));
				filePath = uploadDir + saveName;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		return filePath;
	}

}
