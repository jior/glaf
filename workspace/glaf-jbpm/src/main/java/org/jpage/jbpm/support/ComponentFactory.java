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


package org.jpage.jbpm.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.core.security.Cryptor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ComponentFactory {

	private final static Log logger = LogFactory.getLog(ComponentFactory.class);

	static BeanFactory factory;

	static Cryptor cryptor;

	static {
		logger.debug("◊∞‘ÿ≈‰÷√Œƒº˛jpage-context.xml......");
		Resource resource = new ClassPathResource(
				"/config/jbpm/jpage-context.xml");
		factory = new XmlBeanFactory(resource);
	}

	public static Object getBean(String name) {
		return factory.getBean(name);
	}

	public static Cryptor getCryptor() {
		if (cryptor == null) {
			cryptor = (Cryptor) factory.getBean("cryptor");
		}
		return cryptor;
	}

}