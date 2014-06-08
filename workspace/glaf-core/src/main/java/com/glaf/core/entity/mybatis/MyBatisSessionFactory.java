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

package com.glaf.core.entity.mybatis;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;

public class MyBatisSessionFactory {
	protected static final Log logger = LogFactory
			.getLog(MyBatisSessionFactory.class);

	private static class MyBatisSessionFactoryHolder {
		public static MyBatisSessionFactory instance = new MyBatisSessionFactory();
	}

	private static int BUFFER = 8192;

	private static SqlSessionFactory sqlSessionFactory;

	static {
		reloadSessionFactory();
	}

	public static Map<String, byte[]> getZipBytesMap(
			ZipInputStream zipInputStream) {
		Map<String, byte[]> zipMap = new java.util.HashMap<String, byte[]>();
		java.util.zip.ZipEntry zipEntry = null;
		ByteArrayOutputStream baos = null;
		BufferedOutputStream bos = null;
		byte tmpByte[] = null;
		try {
			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				String name = zipEntry.getName();
				if (StringUtils.endsWith(name, "Mapper.xml")) {
					tmpByte = new byte[BUFFER];
					baos = new ByteArrayOutputStream();
					bos = new BufferedOutputStream(baos, BUFFER);
					int i = 0;
					while ((i = zipInputStream.read(tmpByte, 0, BUFFER)) != -1) {
						bos.write(tmpByte, 0, i);
					}
					bos.flush();
					byte[] bytes = baos.toByteArray();
					IOUtils.closeStream(baos);
					IOUtils.closeStream(baos);
					zipMap.put(zipEntry.getName(), bytes);
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(baos);
			IOUtils.closeStream(baos);
		}
		return zipMap;
	}

	public static Map<String, byte[]> getLibMappers() {
		List<String> includes = new ArrayList<String>();
		includes.add("Mapper.xml");
		Map<String, byte[]> dataMap = new HashMap<String, byte[]>();
		String path = SystemProperties.getConfigRootPath() + "/lib";
		File dir = new File(path);
		File contents[] = dir.listFiles();
		if (contents != null) {
			ZipInputStream zipInputStream = null;
			for (int i = 0; i < contents.length; i++) {
				if (contents[i].isFile()
						&& contents[i].getName().startsWith("glaf")
						&& contents[i].getName().endsWith(".jar")) {
					try {
						zipInputStream = new ZipInputStream(
								FileUtils.getInputStream(contents[i]
										.getAbsolutePath()));
						Map<String, byte[]> zipMap = getZipBytesMap(zipInputStream);
						if (zipMap != null && !zipMap.isEmpty()) {
							dataMap.putAll(zipMap);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		return dataMap;
	}

	public static List<String> getClassPathMappers() {
		List<String> list = new ArrayList<String>();
		String path = SystemProperties.getConfigRootPath()
				+ "/classes/com/glaf";
		loadMappers(list, new File(path));
		return list;
	}

	private static void loadMappers(List<String> list, File dir) {
		if (dir.isDirectory()) {
			File contents[] = dir.listFiles();
			if (contents != null) {
				for (int i = 0; i < contents.length; i++) {
					if (contents[i].isFile()
							&& contents[i].getName().endsWith("Mapper.xml")) {
						list.add(contents[i].getAbsolutePath());
					} else {
						loadMappers(list, contents[i]);
					}
				}
			}
		} else if (dir.isFile() && dir.getName().endsWith("Mapper.xml")) {
			list.add(dir.getAbsolutePath());
		}
	}

	protected static void reloadSessionFactory() {
		long start = System.currentTimeMillis();
		Set<String> mappers = new HashSet<String>();
		Configuration configuration = new Configuration();
		String path = SystemProperties.getConfigRootPath() + "/conf/mapper";
		try {
			Map<String, byte[]> dataMap = getLibMappers();
			for (int i = 0; i < dataMap.size(); i++) {
				Set<Entry<String, byte[]>> entrySet = dataMap.entrySet();
				for (Entry<String, byte[]> entry : entrySet) {
					String key = entry.getKey();
					if (key.indexOf("/") != -1) {
						key = key.substring(key.lastIndexOf("/"), key.length());
					}
					byte[] bytes = entry.getValue();
					String filename = path + "/" + key;
					try {
						FileUtils.save(filename, bytes);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}

			List<String> list = getClassPathMappers();
			for (int i = 0; i < list.size(); i++) {
				Resource mapperLocation = new FileSystemResource(list.get(i));
				try {
					XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(
							mapperLocation.getInputStream(), configuration,
							mapperLocation.toString(),
							configuration.getSqlFragments());
					xmlMapperBuilder.parse();
					mappers.add(mapperLocation.getFilename());
				} catch (Exception e) {
					throw new NestedIOException(
							"Failed to parse mapping resource: '"
									+ mapperLocation + "'", e);
				} finally {
					ErrorContext.instance().reset();
				}
			}

			File dir = new File(path);
			if (dir.exists() && dir.isDirectory()) {
				File contents[] = dir.listFiles();
				if (contents != null) {
					for (int i = 0; i < contents.length; i++) {
						if (contents[i].isFile()
								&& contents[i].getName().endsWith("Mapper.xml")) {
							if (mappers.contains(contents[i].getName())) {
								continue;
							}
							Resource mapperLocation = new FileSystemResource(
									contents[i]);
							try {
								XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(
										mapperLocation.getInputStream(),
										configuration,
										mapperLocation.toString(),
										configuration.getSqlFragments());
								xmlMapperBuilder.parse();
							} catch (Exception e) {
								throw new NestedIOException(
										"Failed to parse mapping resource: '"
												+ mapperLocation + "'", e);
							} finally {
								ErrorContext.instance().reset();
							}
						}
					}
				}
			}
			sqlSessionFactory = new SqlSessionFactoryBuilder()
					.build(configuration);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		long time = System.currentTimeMillis() - start;
		System.out.println("加载SessionFactory用时（耗秒）：" + (time));
	}

	public static MyBatisSessionFactory getInstance() {
		return MyBatisSessionFactoryHolder.instance;
	}

	public static SqlSessionFactory getSessionFactory() {
		if (sqlSessionFactory == null) {
			reloadSessionFactory();
		}
		return sqlSessionFactory;
	}

	private MyBatisSessionFactory() {

	}

}