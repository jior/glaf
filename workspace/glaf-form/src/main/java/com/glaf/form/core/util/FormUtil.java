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
package com.glaf.form.core.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.form.core.model.FormDefinitionType;
import com.glaf.form.core.model.ObjectFactory;

public class FormUtil {
	protected static final Log logger = LogFactory.getLog(FormUtil.class);

	public byte[] toXml(FormDefinitionType fdt, String encoding) {
		ByteArrayOutputStream baos = null;
		BufferedOutputStream bos = null;
		try {
			JAXBContext jc = JAXBContext
					.newInstance("com.glaf.form.core.model");
			Marshaller marshaller = jc.createMarshaller();
			ObjectFactory factory = new ObjectFactory();
			JAXBElement<?> jaxbElement = factory.createFormDefinition(fdt);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			baos = new ByteArrayOutputStream();
			bos = new BufferedOutputStream(baos);
			marshaller.marshal(jaxbElement, bos);
			bos.flush();
			baos.flush();
			return baos.toByteArray();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				if (baos != null) {
					baos.close();
					baos = null;
				}
				if (bos != null) {
					bos.close();
					bos = null;
				}
			} catch (IOException ioe) {
			}
		}
	}

}