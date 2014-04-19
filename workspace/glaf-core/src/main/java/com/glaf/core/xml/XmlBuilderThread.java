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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;

import com.glaf.core.util.Dom4jUtils;
import com.glaf.core.util.FileUtils;

public class XmlBuilderThread extends Thread {
	protected final static Log logger = LogFactory
			.getLog(XmlBuilderThread.class);
	public String systemName;
	public byte[] bytes;
	public Map<String, Object> paramMap;
	public String filename;
	public int index;

	public XmlBuilderThread(String systemName, byte[] bytes,
			Map<String, Object> paramMap, String filename, int index) {
		this.systemName = systemName;
		this.bytes = bytes;
		this.paramMap = paramMap;
		this.filename = filename;
		this.index = index;
	}

	public void run() {
		XmlBuilder builder = new XmlBuilder();
		boolean success = false;
		int retry = 0;
		Document doc = null;
		while (retry < 3 && !success) {
			retry++;
			InputStream inputStream = null;
			try {
				inputStream = new ByteArrayInputStream(bytes);
				doc = builder.process(systemName, inputStream, paramMap);
				FileUtils.save(filename,
						Dom4jUtils.getBytesFromPrettyDocument(doc, "GBK"));
				ThreadCounter.addFinished();
				logger.info(ThreadCounter.getFinished() + " finished.");
				retry = Integer.MAX_VALUE;
				success = true;
				break;
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				doc = null;
				IOUtils.closeQuietly(inputStream);
			}
		}
		if (!success) {
			ThreadCounter.addFailed();
		}
	}

}