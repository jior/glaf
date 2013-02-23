/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.jbpm.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 

public class JbpmBeanFactory {
	protected static final Log logger = LogFactory
			.getLog(JbpmBeanFactory.class);
	private final static String DEFAULT_CONFIG = "com/glaf/jbpm/context/jbpm-context.xml";

	private static ApplicationContext ctx;

	private JbpmBeanFactory() {

	}
	 
	public static ApplicationContext getApplicationContext() {
		return ctx;
	}

	public static Object getBean(Object name) {
		if (ctx == null) {
			ctx = reload();
		}
		return ctx.getBean((String) name);
	}

	protected static ApplicationContext reload() {
		if (null != ctx) {
			ctx = null;
		}
		ctx = new ClassPathXmlApplicationContext(DEFAULT_CONFIG);
		return ctx;
	}

}
