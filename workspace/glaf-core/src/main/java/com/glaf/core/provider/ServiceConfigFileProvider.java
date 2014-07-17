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
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Is used to read config files from specific locations and initialize the
 * ServiceProvider.
 * 
 */
public class ServiceConfigFileProvider implements Singleton {
	protected static final Log log = LogFactory
			.getLog(ServiceConfigFileProvider.class);

	private static final String CUSTOM_POSTFIX = "-"
			+ ServiceProvider.CONFIG_FILE_NAME;

	private static ServiceConfigFileProvider instance;

	public static synchronized ServiceConfigFileProvider getInstance() {
		if (instance == null) {
			instance = ServiceProvider.getInstance().get(
					ServiceConfigFileProvider.class);
		}
		return instance;
	}

	public static synchronized void setInstance(
			ServiceConfigFileProvider instance) {
		ServiceConfigFileProvider.instance = instance;
	}

	// the location of the main file
	private String fileLocation;
	private String classPathLocation;
	private ServletContext servletContext;

	/**
	 * @return the directory containing the Openbravo.properties file, so
	 *         <b>not</b> the full path including the filename
	 *         (Openbravo.properties) but the path to and including the
	 *         directory.
	 */
	public String getFileLocation() {
		return fileLocation;
	}

	/**
	 * @param fileLocation
	 *            the path to the directory which contains the openbravo
	 *            properties file (Openbravo.properties). The path does not
	 *            include the Openbravo.properties file itself.
	 * @see #getFileLocation()
	 */
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getClassPathLocation() {
		return classPathLocation;
	}

	public void setClassPathLocation(String classPathLocation) {
		this.classPathLocation = classPathLocation;
	}

	/**
	 * This method will read the ob-provider config files (with bean
	 * specifications) and pass them to the {@link ServiceProvider}. It reads
	 * the file from the class path or from a file location. Depending on what
	 * is set: {@link #getClassPathLocation()} and/or {@link #getFileLocation()}
	 * .
	 */
	public void setConfigInProvider() {
		log.debug("Reading config files for setting the provider");
		if (classPathLocation != null) {
			readModuleConfigsFromClassPath();
		}
		if (fileLocation != null) {
			readModuleConfigsFromFile();
		}
		checkClassPathRoot();

	}

	// currently searches for modules at the same location at the
	// main config file
	protected void readModuleConfigsFromFile() {
		log.debug("Reading from fileLocation " + fileLocation);
		// find the parent
		try {
			File providerDir = new File(fileLocation);
			if (providerDir.exists()) {
				if (!providerDir.isDirectory()) {
					log.warn("File Location of config file should be a directory!");
					providerDir = providerDir.getParentFile();
				}
				File configFile = new File(providerDir,
						ServiceProvider.CONFIG_FILE_NAME);
				if (configFile.exists()) {
					final InputStream is = new FileInputStream(configFile);
					log.info("Found provider config file "
							+ configFile.getAbsolutePath());
					ServiceProvider.getInstance().register("", is);
				}

				for (final Module module : ModelProvider.getInstance()
						.getModules()) {
					if (module.getJavaPackage() == null) {
						continue;
					}
					final String fileName = module.getJavaPackage()
							+ CUSTOM_POSTFIX;
					configFile = new File(providerDir, fileName);
					if (configFile.exists()) {
						final InputStream is = new FileInputStream(configFile);
						log.info("Found provider config file "
								+ configFile.getAbsolutePath());
						ServiceProvider.getInstance().register(
								module.getJavaPackage(), is);
					}
				}
			}
		} catch (final Throwable t) {
			log.error(t.getMessage(), t);
		}
	}

	protected void readModuleConfigsFromClassPath() {
		try {
			if (classPathLocation.endsWith("/")) {
				log.warn("Classpathlocation of config file should not end with /");
				classPathLocation = classPathLocation.substring(0,
						classPathLocation.length());
			}

			final InputStream is = getResourceAsStream(classPathLocation + "/"
					+ ServiceProvider.CONFIG_FILE_NAME);
			if (is != null) {
				ServiceProvider.getInstance().register("", is);
			}

			for (final Module module : ModelProvider.getInstance().getModules()) {
				if (module.getJavaPackage() == null) {
					continue;
				}
				final String configLoc = classPathLocation + "/"
						+ module.getJavaPackage() + CUSTOM_POSTFIX;
				final InputStream cis = getResourceAsStream(configLoc);
				if (cis != null) {
					log.info("Found provider config file " + configLoc);
					ServiceProvider.getInstance().register(
							module.getJavaPackage(), cis);
				}
			}
		} catch (final Throwable t) {
			log.error(t.getMessage(), t);
		}
	}

	protected InputStream getResourceAsStream(String path) {
		if (getServletContext() != null) {
			return getServletContext().getResourceAsStream(path);
		}
		return this.getClass().getResourceAsStream(path);
	}

	private void checkClassPathRoot() {
		// and look in the root of the classpath
		for (final Module module : ModelProvider.getInstance().getModules()) {
			if (module.getJavaPackage() == null) {
				continue;
			}
			final String fileName = "/" + module.getJavaPackage()
					+ CUSTOM_POSTFIX;
			// always use this class itself for getting the resource
			final InputStream is = getClass().getResourceAsStream(fileName);
			if (is != null) {
				log.info("Found provider config file " + fileName);
				ServiceProvider.getInstance().register(module.getJavaPackage(),
						is);
			}
		}
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}