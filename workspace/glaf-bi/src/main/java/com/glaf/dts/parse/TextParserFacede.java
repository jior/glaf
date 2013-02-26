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

package com.glaf.dts.parse;

import java.io.*;
import java.util.*;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.base.DataFile;
import com.glaf.core.base.TableModel;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.service.IBlobService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.UUID32;

public class TextParserFacede {
	protected static final Log logger = LogFactory
			.getLog(TextParserFacede.class);

	public static void main(String[] args) throws Exception {
		String mappingDir = "./report/mapping";
		String dataDir = "./report/data";
		TextParserFacede facede = new TextParserFacede();
		facede.process(mappingDir, dataDir);
	}

	public void process(String mappingDir, String dataDir) {
		XmlMappingReader reader = new XmlMappingReader();
		Set<String> prefixs = new HashSet<String>();
		Map<String, TableModel> tplMap = new HashMap<String, TableModel>();
		java.io.File directory = new java.io.File(mappingDir);
		if (directory.exists()) {
			File[] entries = directory.listFiles();
			for (int i = 0; i < entries.length; i++) {
				File file = entries[i];
				try {
					if (file.getName().endsWith(".xml")) {
						TableModel tableModel = reader
								.read(new FileInputStream(file));
						// System.out.println("tableModel="+tableModel.toString());
						if (tableModel.getFilePrefix() != null) {
							tplMap.put(tableModel.getFilePrefix(), tableModel);
							prefixs.add(tableModel.getFilePrefix());
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		/**
		 * 下面读取文件只考虑两层
		 */
		java.io.File directory2 = new java.io.File(dataDir);
		if (directory2.exists()) {
			File[] entries = directory2.listFiles();
			for (int i = 0; i < entries.length; i++) {
				File file = entries[i];
				if (file.isFile()) {
					boolean success = false;
					int retry = 0;
					while (retry < 2 && !success) {
						try {
							retry++;
							this.parse(file, prefixs, tplMap);
							success = true;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				} else {
					File[] entries02 = file.listFiles();
					for (int ii = 0; ii < entries02.length; ii++) {
						File f = entries02[ii];
						if (f.isFile()) {
							boolean success = false;
							int retry = 0;
							while (retry < 2 && !success) {
								try {
									retry++;
									this.parse(f, prefixs, tplMap);
									success = true;
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
	}

	public void parse(File file, Set<String> prefixs,
			Map<String, TableModel> tplMap) {
		ITableDataService tableDataService = ContextFactory
				.getBean("tableDataService");
		IBlobService blobService = ContextFactory.getBean("blobService");
		InputStreamReader is = null;
		TextParser textReader = null;
		boolean insert = false;
		DataFile dataFile = blobService.getBlobByFilename(file
				.getAbsolutePath());
		if (dataFile == null) {
			insert = true;
			dataFile = new BlobItemEntity();
			dataFile.setFileId(UUID32.getUUID());
			dataFile.setFilename(file.getAbsolutePath());
			dataFile.setData(FileUtils.getBytes(file));
			dataFile.setCreateDate(new Date(file.lastModified()));
			dataFile.setLastModified(file.lastModified());
			dataFile.setServiceKey("DTS");
			dataFile.setId(UUID32.getUUID());
			dataFile.setName(file.getName());
			dataFile.setSize(file.length());
		} else {
			if (dataFile.getLastModified() == file.lastModified()) {
				logger.debug(file.getAbsolutePath() + " 已经成功处理了，不再重复处理。");
				return;
			}
			insert = false;
			dataFile.setData(FileUtils.getBytes(file));
			dataFile.setCreateDate(new Date(file.lastModified()));
			dataFile.setLastModified(file.lastModified());
			dataFile.setSize(file.length());
		}

		String filename = file.getName();
		for (String prefix : prefixs) {
			textReader = null;
			if (filename.indexOf(prefix) != -1) {
				TableModel tableModel = tplMap.get(prefix);
				String parseType = tableModel.getParseType();
				if ("csv".equals(parseType)) {
					textReader = new CsvTextParser();
				} else if ("text".equals(parseType)) {
					textReader = new PlainTextParser();
				}
				if (textReader != null) {
					try {
						is = new InputStreamReader(new java.io.FileInputStream(
								file));
						List<TableModel> rows = textReader.read(tableModel, is);
						if (rows != null && !rows.isEmpty()) {
							// System.out.println("rm=="+tableModel.toString());
							// System.out.println(" rows size:"+rows.size());
							tableDataService.saveAll(tableModel.getTableName(),
									rows);
							if (insert) {
								dataFile.setStatus(9);
								blobService.insertBlob(dataFile);
								logger.debug(dataFile.getFilename() + "内容已经存储。");
							} else {
								dataFile.setStatus(9);
								blobService.updateBlobFileInfo(dataFile);
								logger.debug(dataFile.getFilename() + "内容已经更新。");
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						throw new RuntimeException(ex);
					} finally {
						IOUtils.closeQuietly(is);
					}
				}
			}
		}
	}

}