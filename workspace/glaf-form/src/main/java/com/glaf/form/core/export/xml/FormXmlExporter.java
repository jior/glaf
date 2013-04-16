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
package com.glaf.form.core.export.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;

import com.glaf.core.base.DataModel;
import com.glaf.form.core.context.FormContext;
import com.glaf.form.core.domain.FormDefinition;
import com.glaf.form.core.graph.def.FormNode;

public class FormXmlExporter {

	public Document export(DataModel dataModel, FormContext formContext) {
		Document doc = null;
		FormDefinition formDefinition = formContext.getFormDefinition();
		if (formDefinition != null) {
			List<FormNode> nodes = formDefinition.getNodes();
			Map<String, Object> dataMap = dataModel.getDataMap();
			Iterator<FormNode> iterator = nodes.iterator();
			while (iterator.hasNext()) {
				FormNode node = iterator.next();
				String name = node.getName();
				if (StringUtils.isEmpty(name)) {
					continue;
				}
				Object value = dataMap.get(name);
				node.setValue(value);
			}
			FormModelXmlWriter xmlWriter = new FormModelXmlWriter(
					formDefinition);
			doc = xmlWriter.writeFormDefinition();
		}
		return doc;
	}

}