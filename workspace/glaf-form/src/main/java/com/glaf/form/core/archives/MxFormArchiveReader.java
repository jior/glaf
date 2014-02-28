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
package com.glaf.form.core.archives;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.form.core.dataimport.FormDataImportFactory;
import com.glaf.form.core.domain.FormApplication;
import com.glaf.form.core.domain.FormDefinition;
import com.glaf.form.core.model.FormDefinitionType;
import com.glaf.form.core.util.FdlConverter;
import com.glaf.form.core.xml.FormApplicationReader;

import com.glaf.core.util.ZipUtils;

public class MxFormArchiveReader {

	private static int BUFFER = 4096;

	/**
	 * 获取应用定义
	 * 
	 * @param zipBytes
	 * @return
	 */
	public FormApplication getFormApplication(byte[] zipBytes) {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(zipBytes);
			return this.getFormApplication(bais);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (bais != null) {
				try {
					bais.close();
				} catch (Exception ex) {
				}
			}
		}
	}

	/**
	 * 获取应用定义
	 * 
	 * @param is
	 * @return
	 */
	public FormApplication getFormApplication(InputStream is) {
		FormApplication formApplication = null;
		ZipInputStream zipInputStream = null;
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(is);
			zipInputStream = new ZipInputStream(bis);
			byte[] bytes = ZipUtils.getBytes(zipInputStream, "application.xml");
			if (bytes != null) {
				FormApplicationReader reader = new FormApplicationReader();
				SAXReader xmlReader = new SAXReader();
				Document doc = xmlReader.read(new ByteArrayInputStream(bytes));
				Element root = doc.getRootElement();
				formApplication = reader.parse(root);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (bis != null) {
				try {
					bis.close();
					bis = null;
				} catch (Exception ex) {
				}
			}
			if (zipInputStream != null) {
				try {
					zipInputStream.close();
					zipInputStream = null;
				} catch (Exception ex) {
				}
			}
		}
		return formApplication;
	}

	/**
	 * 获取表单定义
	 * 
	 * @param zipBytes
	 * @return
	 */
	public List<FormDefinition> getFormDefinitions(byte[] zipBytes) {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(zipBytes);
			return this.getFormDefinitions(bais);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (bais != null) {
				try {
					bais.close();
				} catch (Exception ex) {
				}
			}
		}
	}

	/**
	 * 获取表单定义
	 * 
	 * @param is
	 * @return
	 */
	public List<FormDefinition> getFormDefinitions(InputStream is) {
		List<FormDefinition> rows = new java.util.concurrent.CopyOnWriteArrayList<FormDefinition>();
		ZipInputStream zipInputStream = null;
		ByteArrayOutputStream baos = null;
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		ZipEntry zipEntry = null;
		try {
			bis = new BufferedInputStream(is);
			zipInputStream = new ZipInputStream(bis);
			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				String name = zipEntry.getName();
				FormDefinition formDefinition = null;

				byte abyte0[] = new byte[BUFFER];
				baos = new ByteArrayOutputStream();
				bos = new BufferedOutputStream(baos, BUFFER);
				int i = 0;
				while ((i = zipInputStream.read(abyte0, 0, BUFFER)) != -1) {
					bos.write(abyte0, 0, i);
				}
				bos.flush();
				byte[] bytes = baos.toByteArray();
				bos.close();
				baos.close();
				baos = null;
				bos = null;

				if (name.equals("formdefinition.xml")) {
					FormDefinitionType formDefinitionType = FormDataImportFactory
							.read("xml", bytes);
					formDefinition = FdlConverter
							.toFormDefinition(formDefinitionType);
				}

				if (name.endsWith(".fdl.xml")) {
					FormDefinitionType formDefinitionType = FormDataImportFactory
							.read("fdl", bytes);
					formDefinition = FdlConverter
							.toFormDefinition(formDefinitionType);
				}

				if (formDefinition != null) {
					rows.add(formDefinition);
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (bos != null) {
				try {
					bos.close();
					bos = null;
				} catch (Exception ex) {
				}
			}
			if (baos != null) {
				try {
					baos.close();
					baos = null;
				} catch (Exception ex) {
				}
			}
			if (bis != null) {
				try {
					bis.close();
					bis = null;
				} catch (Exception ex) {
				}
			}
			if (zipInputStream != null) {
				try {
					zipInputStream.close();
					zipInputStream = null;
				} catch (Exception ex) {
				}
			}
		}
		return rows;
	}

	public List<FormDefinition> readFormDefinitions(File dir) {
		List<FormDefinition> rows = new java.util.concurrent.CopyOnWriteArrayList<FormDefinition>();
		if (dir.exists() && dir.isDirectory()) {
			readInternal(dir, rows);
		}
		return rows;
	}

	protected void readInternal(File dir, List<FormDefinition> rows) {
		if (dir.isDirectory()) {
			File[] entries = dir.listFiles();
			if (entries == null) {
				throw new RuntimeException(
						"Could not list files in directory: " + dir);
			}
			for (int i = 0; i < entries.length; i++) {
				File file = entries[i];
				readInternal(file, rows);
			}
		} else if (dir.isFile()
				&& (dir.getName().endsWith(".jar") || dir.getName().endsWith(
						".zip"))) {
			try {
				List<FormDefinition> list = this
						.getFormDefinitions(new FileInputStream(dir));
				if (list != null && list.size() > 0) {
					rows.addAll(list);
				}
			} catch (FileNotFoundException ex) {
				throw new RuntimeException("File process error : " + ex);
			}
		}
	}

}