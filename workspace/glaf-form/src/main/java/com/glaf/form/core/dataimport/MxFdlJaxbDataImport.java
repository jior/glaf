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
package com.glaf.form.core.dataimport;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.form.core.model.FormDefinitionType;
import com.glaf.core.util.LogUtils;

public class MxFdlJaxbDataImport implements FormDataImport {
	private static final Log logger = LogFactory
			.getLog(MxFdlJaxbDataImport.class);

	public FormDefinitionType read(java.io.InputStream inputStream) {
		FormDefinitionType formDefinition = null;
		try {
			JAXBContext jc = JAXBContext
					.newInstance("com.glaf.form.core.model");
			Unmarshaller u = jc.createUnmarshaller();
			JAXBElement<?> element = (JAXBElement<?>) u.unmarshal(inputStream);
			formDefinition = (FormDefinitionType) element.getValue();
		} catch (Exception ex) {
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
			throw new RuntimeException(ex);
		}
		return formDefinition;
	}

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 1000; i++) {
			long start = System.currentTimeMillis();
			MxFdlJaxbDataImport di = new MxFdlJaxbDataImport();
			FormDefinitionType formDefinition = di
					.read(new java.io.FileInputStream(args[0]));
			long time = System.currentTimeMillis() - start;
			logger.info("time:" + time);
			logger.info(formDefinition);
		}
	}
}