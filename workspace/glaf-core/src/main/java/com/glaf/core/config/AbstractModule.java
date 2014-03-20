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

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;

public abstract class AbstractModule implements Module {

	protected Properties properties;

	protected AbstractModule() {
		properties = new Properties();
		try {
			properties = PropertiesLoaderUtils
					.loadProperties(new ClassPathResource(
							getPropertiesFileName(), getClass()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected String getPropertiesFileName() {
		return getClass().getSimpleName() + ".properties";
	}

	@Override
	public String getVersion() {
		return properties.getProperty("application.version");
	}

	@Override
	public String getFileName() {
		return properties.getProperty("application.fileName");
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
	public String getLicenseName() {
		return properties.getProperty("application.license.name");
	}

	@Override
	public String getLicenseUrl() {
		return properties.getProperty("application.license.url");
	}

	@Override
	public String getName() {
		return properties.getProperty("application.name");
	}

	@Override
	public String getProjectUrl() {
		return properties.getProperty("application.url");
	}

	@Override
	public ModuleType getType() {
		return ModuleType.valueOf(properties.getProperty("application.type")
				.trim().toUpperCase());
	}

	@Override
	public String toString() {
		return getFileName();
	}

	@Override
	public int compareTo(Module o) {
		return Integer.valueOf(getOrdinal()).compareTo(o.getOrdinal());
	}

}
