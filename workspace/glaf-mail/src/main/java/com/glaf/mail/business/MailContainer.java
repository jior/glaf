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

package com.glaf.mail.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.FileUtils;
import com.glaf.mail.def.MailTemplate;
import com.glaf.mail.xml.MailXmlReader;

public class MailContainer {
	private static class MailContainerHolder {
		private static final MailContainer INSTANCE = new MailContainer();
	}

	protected final static Log logger = LogFactory.getLog(MailContainer.class);

	private final static String sp = System.getProperty("file.separator");

	private final static String DEFAULT_CONFIG_PATH = "/conf/mail";

	public static final MailContainer getContainer() {
		return MailContainerHolder.INSTANCE;
	}

	private MailContainer() {
		
	}

	public MailTemplate getMailDefinition(String mailDefId) {
		Map<String, MailTemplate> mailMap = reload();
		MailTemplate mdf = mailMap.get(mailDefId);

		if (mdf != null) {
			if (mdf.getTemplateId() != null) {

			} else if (mdf.getTemplatePath() != null) {
				String filename = SystemProperties.getConfigRootPath() + sp
						+ mdf.getTemplatePath();
				logger.debug("read template:" + filename);
				byte[] data = FileUtils.getBytes(filename);
				mdf.setData(data);
				mdf.setContent(new String(data));
			}
		}

		return mdf;
	}

	public Map<String, MailTemplate> reload() {
		Map<String, MailTemplate> mailMap = new java.util.HashMap<String, MailTemplate>();
		String configLocation = SystemProperties.getString("mail.config.path");
		if (StringUtils.isEmpty(configLocation)) {
			configLocation = CustomProperties.getString("mail.config.path");
		}
		if (StringUtils.isEmpty(configLocation)) {
			configLocation = DEFAULT_CONFIG_PATH;
		}

		MailXmlReader reader = new MailXmlReader();
		InputStream inputStream = null;
		try {
			String configPath = SystemProperties.getConfigRootPath()
					+ configLocation;
			logger.info(configPath);
			File directory = new File(configPath);
			if (directory.isDirectory()) {
				String[] filelist = directory.list();
				for (int i = 0; i < filelist.length; i++) {
					String filename = configPath + sp + filelist[i];
					File file = new File(filename);
					if (file.isFile() && file.getName().endsWith(".mail.xml")) {
						logger.debug(file.getAbsolutePath());
						inputStream = new FileInputStream(file);
						List<MailTemplate> mails = reader.read(inputStream);
						for (MailTemplate rdf : mails) {
							mailMap.put(rdf.getMailDefId(), rdf);
						}
						inputStream.close();
						inputStream = null;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}

		return mailMap;
	}

}