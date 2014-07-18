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

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.core.util.Check;
import com.glaf.core.util.BaseClassLoader;

/**
 * Reads the provider config file and processes it. The provider config file can
 * be used to configure the ServiceProvider. See the provider config xml file(s)
 * in the WEB-INF directory for examples.
 * 
 */
class ServiceProviderConfigReader {
	protected static final Log log = LogFactory
			.getLog(ServiceProviderConfigReader.class);

	void read(String prefix, InputStream is) {
		try {
			final SAXReader reader = new SAXReader();
			final Document doc = reader.read(is);
			process(prefix, doc);
		} catch (final Exception e) {
			throw new ServiceProviderException(e);
		}
	}

	void read(String prefix, String fileLocation) {
		try {
			final SAXReader reader = new SAXReader();
			final Document doc = reader.read(new FileInputStream(fileLocation));
			process(prefix, doc);
		} catch (final Exception ex) {
			ex.printStackTrace();
			throw new ServiceProviderException(ex);
		}
	}

	void process(String prefix, Document doc) {
		checkName(doc.getRootElement(), "provider");
		for (final Object o : doc.getRootElement().elements()) {
			final Element elem = (Element) o;
			checkName(elem, "bean");
			// now check for three children:
			final String name = getValue(elem, "name", true);
			final String clzName = getValue(elem, "class", true);
			Class<?> clz = null;
			try {
				clz = BaseClassLoader.getInstance().loadClass(clzName);
			} catch (final ClassNotFoundException e) {
				// catch ClassNotFoundException
				log.warn("Class " + clzName
						+ " can not be loaded. This can happen "
						+ "when rebuilding after installing new modules. "
						+ "The system needs to be restarted to find "
						+ "new services");
				continue;
			}
			if (ServiceModulePrefixRequired.class.isAssignableFrom(clz)
					&& prefix != null && prefix.trim().length() > 0) {
				ServiceProvider.getInstance().register(prefix + "." + name,
						clz, true);
			} else {
				
				ServiceProvider.getInstance().register(name, clz, true);
			}
		}
	}

	private String getValue(Element parentElem, String name, boolean mandatory) {
		final Element valueElement = parentElem.element(name);
		if (mandatory) {
			Check.isNotNull(valueElement, "Element with name " + name
					+ " not found");
		} else if (valueElement == null) {
			return null;
		}
		return valueElement.getText();
	}

	private void checkName(Element elem, String expectedName) {
		Check.isTrue(elem.getName().equals(expectedName),
				"The element should have the name: " + expectedName
						+ " but is has name " + elem.getName());
	}
}
