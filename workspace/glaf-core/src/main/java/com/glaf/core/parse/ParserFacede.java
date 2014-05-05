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

package com.glaf.core.parse;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.base.DataFile;
import com.glaf.core.base.TableModel;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.service.IBlobService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.UUID32;
import com.glaf.core.xml.XmlMappingReader;
import com.glaf.core.xml.XmlReader;

public class ParserFacede {
	protected static final Log logger = LogFactory.getLog(ParserFacede.class);

	public static void main(String[] args) throws Exception {
		String mappingDir = "./report/mapping";
		String dataDir = "./report/data";
		ParserFacede facede = new ParserFacede();
		facede.process(mappingDir, dataDir);
	}

	protected volatile IBlobService blobService;

	protected volatile ITableDataService tableDataService;

	protected volatile ITableDefinitionService tableDefinitionService;

	public IBlobService getBlobService() {
		if (blobService == null) {
			blobService = ContextFactory.getBean("blobService");
		}
		return blobService;
	}

	public ITableDataService getTableDataService() {
		if (tableDataService == null) {
			tableDataService = ContextFactory.getBean("tableDataService");
		}
		return tableDataService;
	}

	public ITableDefinitionService getTableDefinitionService() {
		if (tableDefinitionService == null) {
			tableDefinitionService = ContextFactory
					.getBean("tableDefinitionService");
		}
		return tableDefinitionService;
	}

	public void parse(File file, Set<String> prefixs,
			Map<String, TableModel> tplMap) {
		InputStream inputStream = null;
		Parser parser = null;
		boolean insert = false;
		DataFile dataFile = getBlobService().getBlobByFilename(
				file.getAbsolutePath());
		if (dataFile == null) {
			insert = true;
			dataFile = new BlobItemEntity();
			dataFile.setFileId(UUID32.getUUID());
			dataFile.setFilename(file.getAbsolutePath());
			dataFile.setData(FileUtils.getBytes(file));
			dataFile.setCreateDate(new Date());
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
			dataFile.setCreateDate(new Date());
			dataFile.setLastModified(file.lastModified());
			dataFile.setSize(file.length());
		}

		String filename = file.getName();
		for (String prefix : prefixs) {
			parser = null;
			if (filename.indexOf(prefix) != -1) {
				TableModel tableModel = tplMap.get(prefix);
				String parseClass = tableModel.getParseClass();
				if (StringUtils.isNotEmpty(parseClass)) {
					// 加载自定义的解析器
					parser = (Parser) ClassUtils.instantiateClass(parseClass);
				} else {
					String parseType = tableModel.getParseType();
					if ("csv".equals(parseType)) {
						parser = new CsvTextParser();
					} else if ("text".equals(parseType)) {
						parser = new PlainTextParser();
					} else if ("xls".equals(parseType)) {
						parser = new POIExcelParser();
					}
				}
				if (parser != null) {
					try {
						inputStream = new java.io.FileInputStream(file);
						List<TableModel> rows = parser.parse(tableModel,
								inputStream);
						if (rows != null && !rows.isEmpty()) {
							getTableDataService().saveAll(
									tableModel.getTableName(), null, rows);
							if (insert) {
								dataFile.setStatus(9);
								getBlobService().insertBlob(dataFile);
								logger.debug(dataFile.getFilename() + "内容已经存储。");
							} else {
								dataFile.setStatus(9);
								getBlobService().updateBlobFileInfo(dataFile);
								logger.debug(dataFile.getFilename() + "内容已经更新。");
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						throw new RuntimeException(ex);
					} finally {
						IOUtils.closeQuietly(inputStream);
					}
				}
			}
		}
	}

	/**
	 * 解析数据模型
	 * 
	 * @param mappingFile
	 *            元数据配置文件
	 * @param dataFile
	 *            解析数据文件
	 * @param saveToDB
	 *            是否保存到数据库
	 * @return
	 */
	public List<TableModel> parse(InputStream mappingFile,
			InputStream dataFile, String seqNo, boolean saveToDB) {
		List<TableModel> rows = null;
		XmlReader reader = new XmlReader();
		XmlMappingReader xmlReader = new XmlMappingReader();
		TableDefinition tableDefinition = null;
		TableModel tableModel = null;

		tableDefinition = reader.read(mappingFile);

		if (tableDefinition != null) {
			ColumnDefinition column4 = new ColumnDefinition();
			column4.setTitle("聚合主键");
			column4.setName("aggregationKey");
			column4.setColumnName("AGGREGATIONKEY");
			column4.setJavaType("String");
			column4.setLength(500);
			tableDefinition.addColumn(column4);
			if (DBUtils.tableExists(tableDefinition.getTableName())) {
				com.glaf.core.util.DBUtils.alterTable(tableDefinition);
			} else {
				com.glaf.core.util.DBUtils.createTable(tableDefinition);
			}
		}

		Parser parser = null;
		try {
			tableModel = xmlReader.read(mappingFile);
			String parseClass = tableModel.getParseClass();
			if (StringUtils.isNotEmpty(parseClass)) {
				// 加载自定义的解析器
				parser = (Parser) ClassUtils.instantiateClass(parseClass);
			} else {
				String parseType = tableModel.getParseType();
				if ("csv".equals(parseType)) {
					parser = new CsvTextParser();
				} else if ("text".equals(parseType)) {
					parser = new PlainTextParser();
				} else if ("xls".equals(parseType)) {
					parser = new POIExcelParser();
				}
			}
			if (parser != null) {
				rows = parser.parse(tableModel, dataFile);
				logger.debug("saveToDB=" + saveToDB);
				if (rows != null && !rows.isEmpty()) {
					if (saveToDB) {
						logger.info("save data to " + tableModel.getTableName());
						getTableDataService().saveAll(tableDefinition, seqNo,
								rows);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return rows;
	}

	/**
	 * 解析数据模型
	 * 
	 * @param mappingFile
	 *            元数据配置文件
	 * @param dataFile
	 *            解析数据文件
	 * @param saveToDB
	 *            是否保存到数据库
	 * @return
	 */
	public List<TableModel> parse(byte[] mappingFile, InputStream dataFile,
			String seqNo, boolean saveToDB) {
		List<TableModel> rows = null;
		XmlReader reader = new XmlReader();
		XmlMappingReader xmlReader = new XmlMappingReader();
		TableDefinition tableDefinition = null;
		TableModel tableModel = null;

		logger.debug(new String(mappingFile));

		tableDefinition = reader.read(new BufferedInputStream(
				new ByteArrayInputStream(mappingFile)));

		if (tableDefinition != null) {
			ColumnDefinition column4 = new ColumnDefinition();
			column4.setTitle("聚合主键");
			column4.setName("aggregationKey");
			column4.setColumnName("AGGREGATIONKEY");
			column4.setJavaType("String");
			column4.setLength(500);
			tableDefinition.addColumn(column4);
			if (DBUtils.tableExists(tableDefinition.getTableName())) {
				com.glaf.core.util.DBUtils.alterTable(tableDefinition);
			} else {
				com.glaf.core.util.DBUtils.createTable(tableDefinition);
			}
			getTableDefinitionService().save(tableDefinition);
		}

		Parser parser = null;
		try {
			tableModel = xmlReader.read(new BufferedInputStream(
					new ByteArrayInputStream(mappingFile)));
			String parseClass = tableModel.getParseClass();
			if (StringUtils.isNotEmpty(parseClass)) {
				// 加载自定义的解析器
				parser = (Parser) ClassUtils.instantiateClass(parseClass);
			} else {
				String parseType = tableModel.getParseType();
				if ("csv".equals(parseType)) {
					parser = new CsvTextParser();
				} else if ("text".equals(parseType)) {
					parser = new PlainTextParser();
				} else if ("xls".equals(parseType)) {
					parser = new POIExcelParser();
				}
			}
			if (parser != null) {
				rows = parser.parse(tableModel, dataFile);
				logger.debug("saveToDB=" + saveToDB);
				if (rows != null && !rows.isEmpty()) {
					if (saveToDB) {
						logger.info("save data to " + tableModel.getTableName());
						getTableDataService().saveAll(tableDefinition, seqNo,
								rows);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return rows;
	}

	/**
	 * 解析数据模型
	 * 
	 * @param mappingFile
	 *            元数据配置文件
	 * @param dataFile
	 *            解析数据文件
	 * @param saveToDB
	 *            是否保存到数据库
	 * @return
	 */
	public List<TableModel> parse(String mappingFile, String dataFile,
			String seqNo, boolean saveToDB) {
		XmlReader reader = new XmlReader();
		XmlMappingReader xmlReader = new XmlMappingReader();
		TableDefinition tableDefinition = null;
		TableModel tableModel = null;
		try {
			tableDefinition = reader.read(new FileInputStream(mappingFile));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		if (tableDefinition != null) {
			ColumnDefinition column4 = new ColumnDefinition();
			column4.setTitle("聚合主键");
			column4.setName("aggregationKey");
			column4.setColumnName("AGGREGATIONKEY");
			column4.setJavaType("String");
			column4.setLength(500);
			tableDefinition.addColumn(column4);
			if (DBUtils.tableExists(tableDefinition.getTableName())) {
				com.glaf.core.util.DBUtils.alterTable(tableDefinition);
			} else {
				com.glaf.core.util.DBUtils.createTable(tableDefinition);
			}
		}

		InputStream inputStream = null;
		Parser parser = null;
		try {
			tableModel = xmlReader
					.read(new java.io.FileInputStream(mappingFile));
			String parseClass = tableModel.getParseClass();
			if (StringUtils.isNotEmpty(parseClass)) {
				// 加载自定义的解析器
				parser = (Parser) ClassUtils.instantiateClass(parseClass);
			} else {
				String parseType = tableModel.getParseType();
				if ("csv".equals(parseType)) {
					parser = new CsvTextParser();
				} else if ("text".equals(parseType)) {
					parser = new PlainTextParser();
				} else if ("xls".equals(parseType)) {
					parser = new POIExcelParser();
				}
			}
			if (parser != null) {
				logger.info("parser=" + parser.getClass().getName());
				inputStream = new java.io.FileInputStream(dataFile);
				List<TableModel> rows = parser.parse(tableModel, inputStream);
				logger.info("saveToDB=" + saveToDB);
				if (rows != null && !rows.isEmpty()) {
					if (saveToDB) {
						logger.info("save data to " + tableModel.getTableName());
						getTableDataService().saveAll(tableDefinition, seqNo,
								rows);
					}
				}
				return rows;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return null;
	}

	public void process(String mappingDir, String dataDir) {
		XmlMappingReader reader = new XmlMappingReader();
		Set<String> prefixs = new HashSet<String>();
		Map<String, TableModel> tplMap = new java.util.HashMap<String, TableModel>();
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

	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

}