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

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.junit.Test;

import com.glaf.core.util.FileUtils;
import com.glaf.core.xml.XmlBuilder;

public class XmlBuilderTest {

	@Test
	public void test() throws Exception {
		for (int i = 0; i < 100; i++) {
			long start = System.currentTimeMillis();
			String systemName = "default";

			XmlBuilder builder = new XmlBuilder();
			InputStream in = new FileInputStream(
					"./template/user.template.xml");
			String filename = "user.xml";
			byte[] bytes = FileUtils.getBytes(in);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("tree_topid", 18);
			Document doc = builder.process(systemName,
					new ByteArrayInputStream(bytes), dataMap);
			FileUtils.save(filename, com.glaf.core.util.Dom4jUtils
					.getBytesFromPrettyDocument(doc, "GBK"));
			// net.sf.json.xml.XMLSerializer xmlSerializer = new
			// net.sf.json.xml.XMLSerializer();
			// net.sf.json.JSON json = xmlSerializer.read(doc.asXML());
			// System.out.println(json.toString(1));

			long time = (System.currentTimeMillis() - start);
			System.out.println("times:" + time + " millis seconds");
		}
	}

}
