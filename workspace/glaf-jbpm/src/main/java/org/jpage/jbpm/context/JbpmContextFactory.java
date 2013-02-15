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

package org.jpage.jbpm.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpage.core.security.Cryptor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class JbpmContextFactory {

	private final static Log logger = LogFactory
			.getLog(JbpmContextFactory.class);

	static BeanFactory factory;

	static Cryptor cryptor;

	static {
		logger.debug("◊∞‘ÿ≈‰÷√Œƒº˛jpage-jbpm-context.xml......");
		Resource resource = new ClassPathResource(
				"config/jbpm/jpage-jbpm-context.xml");
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