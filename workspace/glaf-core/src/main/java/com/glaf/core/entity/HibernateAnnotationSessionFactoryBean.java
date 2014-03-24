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

package com.glaf.core.entity;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import com.glaf.core.config.DBConfiguration;

public class HibernateAnnotationSessionFactoryBean extends
		AnnotationSessionFactoryBean {

	public HibernateAnnotationSessionFactoryBean() {
		System.out.println("#################################################");
	}

	@Override
	protected SessionFactory buildSessionFactory() throws Exception {
		System.out.println("-----------------buildSessionFactory------------");
		String hibernateDialet = DBConfiguration.getCurrentHibernateDialect();
		java.util.Properties props = super.getHibernateProperties();
		if (props == null) {
			props = new java.util.Properties();
		}
		props.put("hibernate.dialect", hibernateDialet);
		SessionFactory sessionFactory = super.buildSessionFactory();

		return sessionFactory;
	}

}
