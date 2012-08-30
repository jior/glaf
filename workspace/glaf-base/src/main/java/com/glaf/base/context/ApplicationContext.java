package com.glaf.base.context;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ApplicationContext {

	public final static String sp = System.getProperty("file.separator");

	private static String appPath;

	private static String classPath;

	private static String contextPath;

	public static String getAppClasspath() {
		if (classPath == null) {
			StringBuffer buffer = new StringBuffer();
			String root = getAppPath();
			String path = root + sp + "WEB-INF" + sp + "lib";
			java.io.File file = new java.io.File(path);
			if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					if (filelist[i].endsWith(".jar")) {
						String filename = path + sp + filelist[i];
						buffer.append(filename).append(';');
					}
				}
			}
			classPath = buffer.toString();
		}
		return classPath;
	}

	public static String getAppPath() {
		if (appPath == null) {
			try {
				Resource resource = new ClassPathResource("/system.properties");
				appPath = resource.getFile().getParentFile().getParentFile()
						.getParentFile().getAbsolutePath();
				System.out.println("app path:" + appPath);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return appPath;
	}

	public static String getContextPath() {
		return contextPath;
	}

	public static void setAppPath(String appPath) {
		ApplicationContext.appPath = appPath;
	}

	public static void setContextPath(String pContextPath) {
		if (StringUtils.isNotEmpty(pContextPath)) {
			if (StringUtils.isEmpty(contextPath)) {
				contextPath = pContextPath;
			}
		}
	}

	private ApplicationContext() {
	}

}