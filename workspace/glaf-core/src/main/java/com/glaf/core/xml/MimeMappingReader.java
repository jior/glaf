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

package com.glaf.core.xml;

import java.io.*;
import java.util.*;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;

public class MimeMappingReader {

	public Map<String, String> read() {
		InputStream inputStream = null;
		try {
			String configFile = SystemProperties.getConfigRootPath()
					+ "/conf/mime-mapping.xml";
			File file = new File(configFile);
			if (file.exists() && file.isFile()) {
				inputStream = FileUtils.getInputStream(configFile);
			} else {
				inputStream = MimeMappingReader.class
						.getResourceAsStream("/mime-mapping.xml");
			}
			return this.read(inputStream);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(inputStream);
		}
	}

	public Map<String, String> read(InputStream inputStream) {
		Map<String, String> mapping = new HashMap<String, String>();
		SAXReader xmlReader = new SAXReader();
		try {
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			List<?> elements = root.elements("mime-mapping");
			for (int i = 0; i < elements.size(); i++) {
				Element element = (Element) elements.get(i);
				mapping.put(element.elementTextTrim("extension"),
						element.elementTextTrim("mime-type"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return mapping;
	}
}
