
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;

import com.glaf.core.config.SystemProperties;

import com.glaf.core.util.Dom4jUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.ZipUtils;

import com.glaf.form.core.graph.def.FormApplication;
import com.glaf.form.core.graph.def.FormDefinition;
import com.glaf.form.core.model.FormDefinitionType;
import com.glaf.form.core.util.*;
import com.glaf.form.core.xml.FormApplicationWriter;

public class MxFormArchiveWriter {

	protected static final Log logger = LogFactory
			.getLog(MxFormArchiveWriter.class);

	public void rebuild(boolean copyToLib) {

	}

	public void repack(File srcDir, File destDir) throws IOException {
		MxFormArchiveReader reader = new MxFormArchiveReader();
		if (srcDir.isDirectory()) {
			File[] entries = srcDir.listFiles();
			if (entries == null) {
				throw new IOException("Could not list files in directory: "
						+ srcDir);
			}
			for (int i = 0; i < entries.length; i++) {
				File file = entries[i];
				if (file.getName().endsWith(".jar")
						|| file.getName().endsWith(".zip")) {
					List<FormDefinition> rows = reader
							.getFormDefinitions(new FileInputStream(file));
					for (FormDefinition formDefinition : rows) {
						try {
							byte[] bytes = this.buildAndZip(formDefinition,
									"UTF-8");
							String filename = destDir.getAbsolutePath() + "/"
									+ formDefinition.getName() + ".jar";
							FileUtils.save(filename, bytes);
						} catch (Exception ex) {
							logger.error(ex);
						}
					}
				}
			}
		}
	}

	public void writeAndSave(FormDefinition formDefinition) throws IOException {
		byte[] bytes = this.buildAndZip(formDefinition, "UTF-8");
		String path = SystemProperties.getConfigRootPath();
		String filename = path + "/deploy/bundles/" + formDefinition.getName()
				+ ".jar";
		FileUtils.save(filename, bytes);

		String filename2 = path + "/deploy/lib/" + formDefinition.getName()
				+ ".jar";
		FileUtils.save(filename2, bytes);

	}

	public byte[] writeApp(FormApplication formApplication, String encoding) {
		FormApplicationWriter formApplicationWriter = new FormApplicationWriter();
		Document doc = formApplicationWriter.write(formApplication);
		byte[] appBytes = Dom4jUtils.getBytesFromPrettyDocument(doc, encoding);
		return appBytes;
	}

	public byte[] buildAndZip(FormDefinition formDefinition, String encoding) {
		Map<String, byte[]> zipMap = new HashMap<String, byte[]>();
		FormDefinitionType fdt = FdlConverter
				.toFormDefinitionType(formDefinition);
		byte[] bytes01 = FdlConverter.toXml(fdt, encoding);
		zipMap.put(formDefinition.getName() + ".fdl.xml", bytes01);

		return ZipUtils.toZipBytes(zipMap);
	}

	public static void main(String[] args) throws Exception {
		System.out.println("System Properties:" + System.getProperties());
		MxFormArchiveWriter writer = new MxFormArchiveWriter();
		writer.repack(new File(args[0]), new File(args[1]));
	}

}