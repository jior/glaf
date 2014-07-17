/*
 *************************************************************************
 * The contents of this file are subject to the Openbravo  Public  License
 * Version  1.1  (the  "License"),  being   the  Mozilla   Public  License
 * Version 1.1  with a permitted attribution clause; you may not  use this
 * file except in compliance with the License. You  may  obtain  a copy of
 * the License at http://www.openbravo.com/legal/license.html 
 * Software distributed under the License  is  distributed  on  an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific  language  governing  rights  and  limitations
 * under the License. 
 * The Original Code is Openbravo ERP. 
 * The Initial Developer of the Original Code is Openbravo SLU 
 * All portions are Copyright (C) 2008-2010 Openbravo SLU 
 * All Rights Reserved. 
 * Contributor(s):  ______________________________________.
 ************************************************************************
 */

package com.glaf.core.util;

import com.glaf.core.provider.ServiceProvider;
import com.glaf.core.provider.Singleton;

/**
 * The BaseClassLoader is used to support different classloading scenarios. As a
 * default two classloaders are present: the context (the default, used in
 * Tomcat) and the class classloader. The class classloader is used in Ant
 * tasks.
 * <p/>
 * Use the {@link ServiceProvider ServiceProvider} to define which classloader
 * in a specific environment.
 * 
 */

public class BaseClassLoader implements Singleton {

	private static BaseClassLoader instance;

	public static BaseClassLoader getInstance() {
		if (instance == null) {
			instance = ServiceProvider.getInstance().get(BaseClassLoader.class);
		}
		return instance;
	}

	/**
	 * Load a class using the classloader. This method will throw an
	 * ClassNotFoundException if the class is not found. This exception is
	 * logged.
	 * 
	 * @param className
	 *            the name of the class to load
	 * @throws ClassNotFoundException
	 */
	public Class<?> loadClass(String className) throws ClassNotFoundException {
		return Thread.currentThread().getContextClassLoader()
				.loadClass(className);
	}

	/**
	 * A class loader which uses the classloader of the Class class.
	 * 
	 * To use this classloader do the following:
	 * ServiceProvider.getInstance().register(BaseClassLoader.class,
	 * BaseClassLoader.ClassBaseClassLoader.class, false);
	 */
	public static class ClassBaseClassLoader extends BaseClassLoader {

		@Override
		public Class<?> loadClass(String className)
				throws ClassNotFoundException {
			return Class.forName(className);
		}
	}
}