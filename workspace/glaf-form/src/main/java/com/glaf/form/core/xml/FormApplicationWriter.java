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
package com.glaf.form.core.xml;

import java.util.*;
import java.util.Map.Entry;

import org.dom4j.*;
import com.glaf.form.core.graph.def.*;
import com.glaf.core.util.*;

public class FormApplicationWriter {

	public Document write(FormApplication formApplication) {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("application");
		Map<String, Object> dataMap = Tools.getDataMap(formApplication);
		dataMap.remove("id");
		dataMap.remove("events");
		dataMap.remove("properties");
		Set<Entry<String, Object>> entrySet = dataMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String name = entry.getKey();
			Object value = entry.getValue();
			if (value != null && !(value instanceof Collection<?>)) {
				Element element = root.addElement(name);
				if (value instanceof Date) {
					element.setText(DateUtils.getDateTime((Date) value));
				} else {
					element.setText(value.toString());
				}
			}
		}

		return doc;
	}

}