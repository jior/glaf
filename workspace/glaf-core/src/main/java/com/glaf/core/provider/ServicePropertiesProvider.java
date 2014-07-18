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

package com.glaf.core.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.Check;
import com.glaf.core.util.PropertiesUtils;

/**
 * This class implements a central location where the Core.properties are read
 * and made available for the rest of the application.
 * 
 */
public class ServicePropertiesProvider {
	private final Logger log = Logger
			.getLogger(ServicePropertiesProvider.class);

	private static ServicePropertiesProvider instance = new ServicePropertiesProvider();

	private static boolean friendlyWarnings = false;

	public static synchronized ServicePropertiesProvider getInstance() {
		return instance;
	}

	public static boolean isFriendlyWarnings() {
		return friendlyWarnings;
	}

	public static void setFriendlyWarnings(boolean doFriendlyWarnings) {
		friendlyWarnings = doFriendlyWarnings;
	}

	public static synchronized void setInstance(
			ServicePropertiesProvider instance) {
		ServicePropertiesProvider.instance = instance;
	}

	private Properties coreProperties = null;

	private Document formatXML;

	/**
	 * Looks for a boolean property key and return <code>true</code> in case its
	 * value is true or yes and false other case.
	 * 
	 */
	public boolean getBooleanProperty(String key) {
		Properties properties = getCoreProperties();
		if (properties == null) {
			return false;
		}

		String value = properties.getProperty(key);
		if (value == null) {
			return false;
		}

		return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes");
	}

	public Properties getCoreProperties() {
		if (coreProperties == null) {
			readPropertiesFromDevelopmentProject();
		}
		if (coreProperties == null) {
			String filename = SystemProperties.getConfigRootPath()
					+ "/Core.properties";
			coreProperties = PropertiesUtils.loadFilePathResource(filename);
		}
		return coreProperties;
	}

	private File getFileFromDevelopmentPath(String fileName) {
		// get the location of the current class file
		final URL url = this.getClass().getResource(
				getClass().getSimpleName() + ".class");
		File f = new File(url.getPath());
		File propertiesFile = null;
		while (f.getParentFile() != null && f.getParentFile().exists()) {
			f = f.getParentFile();
			final File configDirectory = new File(f, "config");
			if (configDirectory.exists()) {
				propertiesFile = new File(configDirectory, fileName);
				if (propertiesFile.exists()) {
					// found it and break
					break;
				}
			}
		}
		return propertiesFile;
	}

	public Document getFormatXMLDocument() {
		if (formatXML == null) {
			File file = getFileFromDevelopmentPath("Format.xml");
			if (file != null && file.exists() && file.isFile()) {
				try {
					SAXReader reader = new SAXReader();
					formatXML = reader.read(new FileReader(file));
				} catch (Exception ex) {
					throw new IllegalStateException(ex);
				}
			} else {
				file = new File(SystemProperties.getConfigRootPath()
						+ "/Format.xml");
				try {
					SAXReader reader = new SAXReader();
					formatXML = reader.read(new FileReader(file));
				} catch (Exception ex) {
					throw new IllegalStateException(ex);
				}
			}
		}
		return formatXML;
	}

	// tries to read the properties from the development project
	private void readPropertiesFromDevelopmentProject() {
		final File propertiesFile = getFileFromDevelopmentPath("Core.properties");
		if (propertiesFile == null) {
			return;
		}
		setProperties(propertiesFile.getAbsolutePath());
		ServiceConfigFileProvider.getInstance().setFileLocation(
				propertiesFile.getParentFile().getAbsolutePath());
	}

	public void setFormatXML(InputStream is) {
		try {
			SAXReader reader = new SAXReader();
			formatXML = reader.read(is);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public void setProperties(InputStream is) {
		if (coreProperties != null) {
			log.warn("Core properties have already been set, setting them again");
		}
		log.debug("Setting Core.properties through input stream");
		coreProperties = new Properties();
		try {
			coreProperties.load(is);
			is.close();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setProperties(Properties props) {
		Check.isNull(coreProperties, "Core properties have already been set");
		log.debug("Setting Core.properties through properties");
		coreProperties = new Properties();
		coreProperties.putAll(props);
	}

	public void setProperties(String fileLocation) {
		// Check.isNull(obProperties,
		// "Core properties have already been set");
		log.debug("Setting Core.properties through a file");
		coreProperties = new Properties();
		try {
			File file = new File(fileLocation);
			if (file.exists() && file.isFile()) {
				final FileInputStream fis = new FileInputStream(file);
				coreProperties.load(fis);
				fis.close();
			}
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}