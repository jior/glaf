package com.glaf.base.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ClassLoaderUtil {

	public static List<String> getClasses(InputStream is) {
		List<String> classes = new ArrayList<String>();
		ZipInputStream zipInputStream = null;
		BufferedInputStream bis = null;
		ZipEntry zipEntry = null;
		try {
			bis = new BufferedInputStream(is);
			zipInputStream = new ZipInputStream(bis);
			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				String name = zipEntry.getName();
				if (name.endsWith(".class")) {
					String className = name.replace('/', '.');
					className = className.substring(0,
							className.lastIndexOf("."));
					classes.add(className);
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (bis != null) {
				try {
					bis.close();
					bis = null;
				} catch (Exception ex) {
				}
			}
			if (zipInputStream != null) {
				try {
					zipInputStream.close();
					zipInputStream = null;
				} catch (Exception ex) {
				}
			}
		}
		return classes;
	}

	public static ClassLoader getClassLoader() {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		if (classLoader == null) {
			classLoader = ClassLoaderUtil.class.getClassLoader();
		}
		return classLoader;
	}

	public static List<URL> getLibUrls(String path) {
		List<URL> urls = new ArrayList<URL>();
		File appBase = new File(path);
		if (appBase.exists()) {
			File lib = new File(appBase, "/lib");
			if (lib.exists() && lib.isDirectory()) {
				String[] libs = lib.list();
				for (int i = 0; i < libs.length; i++) {
					if (libs[i].length() < 5)
						continue;
					String ext = libs[i].substring(libs[i].length() - 4);
					if (!(".jar".equalsIgnoreCase(ext) || ".zip"
							.equalsIgnoreCase(ext))) {
						continue;
					}
					try {
						File libFile = new File(lib, libs[i]);
						urls.add(libFile.getAbsoluteFile().toURI().toURL());
					} catch (IOException ioe) {
						throw new RuntimeException(ioe.toString());
					}
				}
			}
		}
		return urls;
	}

	public static URLClassLoader getURLClassLoader(Collection<?> urls,
			final ClassLoader parent) {
		final URL urlsArray[] = new URL[urls.size()];
		urls.toArray(urlsArray);
		URLClassLoader classLoader = AccessController
				.doPrivileged(new PrivilegedAction<URLClassLoader>() {
					public URLClassLoader run() {
						return new URLClassLoader(urlsArray, parent);
					}
				});
		return classLoader;
	}

	public static URLClassLoader getURLClassLoader(String path,
			final ClassLoader parent) {
		List<URL> urls = ClassLoaderUtil.getLibUrls(path);
		final URL urlsArray[] = new URL[urls.size()];
		urls.toArray(urlsArray);
		URLClassLoader classLoader = AccessController
				.doPrivileged(new PrivilegedAction<URLClassLoader>() {
					public URLClassLoader run() {
						return new URLClassLoader(urlsArray, parent);
					}
				});
		return classLoader;
	}

	 

	private ClassLoaderUtil() {

	}
}
