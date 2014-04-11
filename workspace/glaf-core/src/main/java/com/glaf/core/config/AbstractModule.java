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

package com.glaf.core.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.PropertiesUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractModule implements Module {
	protected static final Log logger = LogFactory.getLog(AbstractModule.class);

	protected static AtomicBoolean loading = new AtomicBoolean(false);

	protected Properties properties;

	protected AbstractModule() {
		reload();
	}

	@Override
	public int compareTo(Module o) {
		return Integer.valueOf(getOrdinal()).compareTo(o.getOrdinal());
	}

	public boolean eq(String key, String value) {
		if (key != null && value != null) {
			String x = properties.getProperty(key);
			if (StringUtils.equals(value, x)) {
				return true;
			}
		}
		return false;
	}

	public boolean getBoolean(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Boolean.valueOf(value).booleanValue();
		}
		return false;
	}

	public double getDouble(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Double.parseDouble(value);
		}
		return 0;
	}

	@Override
	public String getFileName() {
		return properties.getProperty("application.fileName");
	}

	public int getInt(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Integer.parseInt(value);
		}
		return 0;
	}

	@Override
	public String getLicenseName() {
		return properties.getProperty("application.license.name");
	}

	@Override
	public String getLicenseUrl() {
		return properties.getProperty("application.license.url");
	}

	public long getLong(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Long.parseLong(value);
		}
		return 0;
	}

	@Override
	public String getName() {
		return properties.getProperty("application.name");
	}

	@Override
	public String getOrganizationName() {
		return properties.getProperty("application.organization.name");
	}

	@Override
	public String getOrganizationUrl() {
		return properties.getProperty("application.organization.url");
	}

	@Override
	public String getProjectUrl() {
		return properties.getProperty("application.url");
	}

	public Properties getProperties() {
		Properties props = new Properties();
		Enumeration<?> e = properties.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = properties.getProperty(key);
			props.put(key, value);
		}
		return props;
	}

	protected String getPropertiesFileName() {
		return getClass().getSimpleName() + ".properties";
	}

	public String getString(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			if (value == null) {
				value = properties.getProperty(key.toUpperCase());
			}
			return value;
		}
		return null;
	}

	@Override
	public ModuleType getType() {
		return ModuleType.valueOf(properties.getProperty("application.type")
				.trim().toUpperCase());
	}

	@Override
	public String getVersion() {
		return properties.getProperty("application.version");
	}

	public boolean hasObject(String key) {
		if (properties == null || key == null) {
			return false;
		}
		String value = properties.getProperty(key);
		if (value != null) {
			return true;
		}
		return false;
	}

	public void reload() {
		properties = new Properties();
		if (!loading.get()) {
			InputStream inputStream = null;
			try {
				loading.set(true);
				String filename = SystemProperties.getConfigRootPath()
						+ "/conf/" + getPropertiesFileName();
				File file = new File(filename);
				if (file.exists() && file.isFile()) {
					logger.info("load config:" + filename);
					inputStream = FileUtils.getInputStream(filename);
					properties = PropertiesUtils.loadProperties(inputStream);
					IOUtils.closeStream(inputStream);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (properties.isEmpty()) {
					logger.info("load classpath config:"
							+ getPropertiesFileName());
					properties = PropertiesLoaderUtils
							.loadProperties(new ClassPathResource(
									getPropertiesFileName(), getClass()));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				loading.set(false);
				IOUtils.closeStream(inputStream);
			}
		}
	}

	@Override
	public String toString() {
		return getFileName();
	}

}
