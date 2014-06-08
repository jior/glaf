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

import java.io.File;
import java.util.Collection;

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
import com.glaf.core.util.AnnotationUtils;
import com.glaf.core.util.ClassUtils;

public class MyBatisSessionFactory {
	private static final Log logger = LogFactory
			.getLog(MyBatisSessionFactory.class);

	private static class MyBatisSessionFactoryHolder {
		public static MyBatisSessionFactory instance = new MyBatisSessionFactory();
	}

	private static SqlSessionFactory sqlSessionFactory;

	static {
		reloadSessionFactory();
	}

	protected static void reloadSessionFactory() {
		long start = System.currentTimeMillis();
		try {
			Configuration configuration = new Configuration();
			Collection<String> entities = AnnotationUtils
					.findMapper("com.glaf");
			for (String className : entities) {
				configuration.addMapper(ClassUtils.classForName(className));
				logger.debug("add mapper class " + className);
			}

			String path = SystemProperties.getConfigRootPath() + "/conf/mapper";
			File dir = new File(path);
			if (dir.exists() && dir.isDirectory()) {
				File contents[] = dir.listFiles();
				if (contents != null) {
					for (int i = 0; i < contents.length; i++) {
						if (contents[i].isFile()
								&& contents[i].getName().endsWith("Mapper.xml")) {
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