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

package com.glaf.core.util;

import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

public class AnnotationUtils {

	private static final Log logger = LogFactory.getLog(AnnotationUtils.class);

	public static Collection<String> findClasses(String name) {
		AnnotationDB db = getAnnotationDB();
		Map<String, Set<String>> annotationIndex = db.getAnnotationIndex();
		Set<String> entities = annotationIndex.get(name);
		Collection<String> sortSet = new TreeSet<String>();
		if (entities != null && !entities.isEmpty()) {
			for (String str : entities) {
				sortSet.add(str);
			}
		}
		return sortSet;
	}

	public static Collection<String> findJPAEntity(String packagePrefix) {
		AnnotationDB db = getAnnotationDB(packagePrefix);
		Map<String, Set<String>> annotationIndex = db.getAnnotationIndex();
		Set<String> entities = annotationIndex.get("javax.persistence.Entity");
		Collection<String> sortSet = new TreeSet<String>();
		if (entities != null && !entities.isEmpty()) {
			for (String str : entities) {
				if (packagePrefix != null && str.contains(packagePrefix)) {
					sortSet.add(str);
				}
			}
		}
		return sortSet;
	}

	public static Collection<String> findMapper(String packagePrefix) {
		AnnotationDB db = getAnnotationDB(packagePrefix);
		Map<String, Set<String>> annotationIndex = db.getAnnotationIndex();
		Set<String> entities = annotationIndex
				.get("org.springframework.stereotype.Component");
		Collection<String> sortSet = new TreeSet<String>();
		if (entities != null && !entities.isEmpty()) {
			for (String str : entities) {
				if (packagePrefix != null && str.contains(packagePrefix)
						&& StringUtils.contains(str, ".mapper.")) {
					sortSet.add(str);
				}
			}
		}
		return sortSet;
	}

	public static AnnotationDB getAnnotationDB() {
		AnnotationDB db = new AnnotationDB();
		db.setScanClassAnnotations(true);
		db.addIgnoredPackages("org");
		URL url = ClasspathUrlFinder.findClassBase(AnnotationUtils.class);
		try {
			db.scanArchives(url);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return db;
	}

	public static AnnotationDB getAnnotationDB(String packagePrefix) {
		AnnotationDB db = new AnnotationDB();
		db.addIgnoredPackages("org");
		db.setScanClassAnnotations(true);
		String[] scanPackages = new String[1];
		scanPackages[0] = packagePrefix;
		db.setScanPackages(scanPackages);
		URL url = ClasspathUrlFinder.findClassBase(AnnotationUtils.class);
		try {
			logger.debug("scan url:" + url.toURI().toString());
			db.scanArchives(url);
			URL[] urls = ClasspathUrlFinder.findClassPaths();
			for (URL url2 : urls) {
				logger.debug("scan url:" + url2.toURI().toString());
				db.scanArchives(url2);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		return db;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(System.getProperty("java.class.path"));
		long start = System.currentTimeMillis();
		Collection<String> entities = AnnotationUtils.findMapper("com.glaf");
		long time = System.currentTimeMillis() - start;
		for (String str : entities) {
			System.out.println(str);
		}
		System.out.println("time:" + time);
		URL[]  urls = ClasspathUrlFinder.findResourceBases("com/glaf/package.properties", Thread.currentThread().getContextClassLoader());
		for (URL url2 : urls) {
			logger.debug("->scan url:" + url2.toURI().toString());
		}
	}

	private AnnotationUtils() {

	}

}