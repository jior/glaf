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

package com.glaf.core.test;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.util.FileUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

public class MongoDBGridFSThread implements java.lang.Runnable {
	protected static final Log logger = LogFactory
			.getLog(MongoDBGridFSThread.class);

	private File file;
	private GridFS gridFS;

	public MongoDBGridFSThread(File file, GridFS gridFS) {
		this.file = file;
		this.gridFS = gridFS;
	}

	public File getFile() {
		return file;
	}

	public GridFS getGridFS() {
		return gridFS;
	}

	public void run() {
		if (file.exists() && file.isFile()) {
			String path = file.getAbsolutePath();
			path = path.replace('\\', '/');
			if (StringUtils.contains(path, "/temp/")
					|| StringUtils.contains(path, "/tmp/")
					|| StringUtils.contains(path, "/logs/")
					|| StringUtils.contains(path, "/work/")
					|| StringUtils.endsWith(path, ".log")
					|| StringUtils.endsWith(path, ".class")) {
				return;
			}
			int retry = 0;
			boolean success = false;
			byte[] bytes = null;
			GridFSInputFile inputFile = null;
			while (retry < 1 && !success) {
				try {
					retry++;
					bytes = FileUtils.getBytes(file);
					if (bytes != null) {
						inputFile = gridFS.createFile(bytes);
						DBObject metadata = new BasicDBObject();
						metadata.put("path", path);
						metadata.put("filename", file.getName());
						metadata.put("size", bytes.length);
						inputFile.setMetaData(metadata);
						inputFile.setId(path);
						inputFile.setFilename(file.getName());// 指定唯一文件名称
						inputFile.save();// 保存
						bytes = null;
						success = true;
						logger.debug(file.getAbsolutePath() + " save ok.");
					}
				} catch (Exception ex) {
					logger.error(ex);
					ex.printStackTrace();
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} finally {
					bytes = null;
					inputFile = null;
				}
			}
		}
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setGridFS(GridFS gridFS) {
		this.gridFS = gridFS;
	}

}
