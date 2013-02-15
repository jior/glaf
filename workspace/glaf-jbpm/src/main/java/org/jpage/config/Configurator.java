/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.jpage.context.ApplicationContext;
import org.jpage.util.PropertiesTools;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Configurator {

	public static final String USER_DIR = "user.dir";

	public static final String USER_HOME = "user.home";

	public static final String JAVA_IO_TMPDIR = "java.io.tmpdir";

	private static boolean USE_ACEGI_SECURITY = false;

	private static Properties properties;

	private static String classpath;

	public static Properties getProperties() {
		if (properties == null) {
			java.io.InputStream inputStream = null;
			try {
				Resource resource = new ClassPathResource("/jpage.properties");
				classpath = resource.getFile().getParent();
				inputStream = resource.getInputStream();
				properties = PropertiesTools.load(inputStream);
				inputStream.close();
				inputStream = null;
				System.out.println("load system config:"
						+ resource.getFile().getAbsolutePath());
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (Exception ex) {
				}
			}
		}
		return properties;
	}

	public static String getClassPath() {
		return classpath;
	}

	public static String getSystem() {
		Properties properties = Configurator.getProperties();
		String system = properties.getProperty("jpage.system", "jpage");
		return system;
	}

	public static String getCompanyCode() {
		Properties properties = Configurator.getProperties();
		String system = properties.getProperty("jpage.company.code", "jpage");
		return system;
	}

	public static String getCompanyName() {
		Properties properties = Configurator.getProperties();
		String system = properties.getProperty("jpage.company.name", "jpage");
		return system;
	}

	public static String getProperty(String key) {
		Properties properties = Configurator.getProperties();
		String value = properties.getProperty(key, "");
		return value;
	}

	public static boolean getBoolean(String key) {
		Properties properties = Configurator.getProperties();
		String value = properties.getProperty(key, "");
		if (value != null) {
			if ("true".equalsIgnoreCase(value)) {
				return true;
			}
		}
		return false;
	}

	public static int getInt(String key, int defaultValue) {
		Properties properties = Configurator.getProperties();
		String value = properties.getProperty(key, "");
		if (StringUtils.isNotBlank(value)) {
			return new Integer(value).intValue();
		}
		return defaultValue;
	}

	public static long getLong(String key, long defaultValue) {
		Properties properties = Configurator.getProperties();
		String value = properties.getProperty(key, "");
		if (StringUtils.isNotBlank(value)) {
			return new Long(value).longValue();
		}
		return defaultValue;
	}

	public static double getDouble(String key, double defaultValue) {
		Properties properties = Configurator.getProperties();
		String value = properties.getProperty(key, "");
		if (StringUtils.isNotBlank(value)) {
			return new Double(value).doubleValue();
		}
		return defaultValue;
	}

	public static boolean useAcegiSecurity() {
		Properties properties = Configurator.getProperties();
		String useAcegiSecurity = properties.getProperty(
				"jpage.useAcegiSecurity", "false");
		if ("true".equalsIgnoreCase(useAcegiSecurity)) {
			USE_ACEGI_SECURITY = true;
		}
		return USE_ACEGI_SECURITY;
	}

	public static String getDataRootPath() {
		if (properties == null) {
			properties = getProperties();
		}
		String absolutePath = null;
		if (properties != null
				&& properties.getProperty("dataRootPath") != null) {
			absolutePath = properties.getProperty("dataRootPath");
		}

		if (absolutePath == null) {
			absolutePath = ApplicationContext.getAppPath();
		}

		String translatedPath = replaceToken(USER_HOME,
				System.getProperty(USER_HOME), absolutePath);
		translatedPath = replaceToken(USER_DIR, System.getProperty(USER_DIR),
				translatedPath);
		translatedPath = replaceToken(JAVA_IO_TMPDIR,
				System.getProperty(JAVA_IO_TMPDIR), translatedPath);

		absolutePath = translatedPath;

		absolutePath = absolutePath.replace('\\', '/');

		return absolutePath;
	}

	public static String replaceToken(final String token,
			final String replacement, final String source) {
		int foundIndex = source.indexOf(token);
		if (foundIndex == -1) {
			return source;
		} else {
			String firstFragment = source.substring(0, foundIndex);
			String lastFragment = source.substring(foundIndex + token.length(),
					source.length());
			return new StringBuffer().append(firstFragment).append(replacement)
					.append(lastFragment).toString();
		}
	}

}
