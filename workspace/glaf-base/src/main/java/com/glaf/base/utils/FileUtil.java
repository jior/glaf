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

package com.glaf.base.utils;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.Constants;
import com.glaf.core.config.SystemConfig;

public class FileUtil {
	private static Log logger = LogFactory.getLog(FileUtil.class);

	/**
	 * ɾ��ָ��·���ĵ����ļ�
	 * 
	 * @param filePath
	 */
	public static void delFiles(String filePath) {
		logger.info("filepath=" + filePath);
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			file.delete();
		}
	}

	/**
	 * ɾ��ָ��Ŀ¼�е������ļ�
	 * 
	 * @param dirPath
	 */
	public static void delDirFlies(String dirPath, String ext) {
		File file = new File(dirPath);
		if (file.isDirectory()) {
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					delDirFlies(files[i].getAbsolutePath(), ext);
				} else {
					if (ext != null
							&& files[i].getName().lastIndexOf(ext) != -1) {
						if (files[i].isFile())
							files[i].delete();
					} else {
						files[i].delete();
					}
				}
			}
		}
	}

	/**
	 * ɾ��ϵͳ�е�upload�ļ����е�temp�ļ�Ŀ¼�е������ļ� 
	 */
	public static void delDownTempFiles() {
		String path = SystemConfig.getConfigRootPath() + Constants.UPLOAD_DIR
				+ "temp";
		delDirFlies(path, "xls");
	}

}