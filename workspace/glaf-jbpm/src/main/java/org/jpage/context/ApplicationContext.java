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

package org.jpage.context;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ApplicationContext {

	private static String appPath;

	private static String contextPath;

	private ApplicationContext() {
	}

	public static String getAppPath() {
		if (appPath == null) {
			try {
				Resource res = new ClassPathResource("jbpm.cfg.xml");
				appPath = res.getFile().getParentFile().getParentFile()
						.getParentFile().getAbsolutePath();
				System.out.println("app path:" + appPath);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return appPath;
	}

	public static void setAppPath(String pAppPath) {
		appPath = pAppPath;
	}

	public static String getContextPath() {
		return contextPath;
	}

	public static void setContextPath(String pContextPath) {
		if (StringUtils.isNotBlank(pContextPath)) {
			if (StringUtils.isBlank(contextPath)) {
				contextPath = pContextPath;
			}
		}
	}

}