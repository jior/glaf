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

package org.jpage.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class ElementType {
	public final static int TEXT_TYPE = 0;

	public final static int RADIO_TYPE = 10;

	public final static int PASS_RADIO_TYPE = 11;

	public final static int CHECKBOX_TYPE = 20;

	public final static int PASSWORD_TYPE = 30;

	public final static int HIDDEN_TYPE = 40;

	public final static int SELECT_TYPE = 50;

	public final static int TEXTAREA_TYPE = 80;

	private final static Map dataMap = new HashMap();

	private final static Map nameMap = new HashMap();

	static {
		dataMap.put("TEXT", new Integer(TEXT_TYPE));
		dataMap.put("RADIO", new Integer(RADIO_TYPE));
		dataMap.put("CHECKBOX", new Integer(CHECKBOX_TYPE));
		dataMap.put("PASSWORD", new Integer(PASSWORD_TYPE));
		dataMap.put("HIDDEN", new Integer(HIDDEN_TYPE));
		dataMap.put("SELECT", new Integer(SELECT_TYPE));
		dataMap.put("TEXTAREA", new Integer(TEXTAREA_TYPE));

		nameMap.put(new Integer(TEXT_TYPE), "文本输入框");
		nameMap.put(new Integer(RADIO_TYPE), "单选项");
		nameMap.put(new Integer(CHECKBOX_TYPE), "复选框");
		nameMap.put(new Integer(PASSWORD_TYPE), "密码输入框");
		nameMap.put(new Integer(HIDDEN_TYPE), "隐藏域");
		nameMap.put(new Integer(SELECT_TYPE), "下拉列表");
		nameMap.put(new Integer(TEXTAREA_TYPE), "文本域");
	}

	private ElementType() {
	}

	public final static int getElementType(String elementType) {
		if (elementType == null) {
			return -1;
		}
		elementType = elementType.trim().toUpperCase();
		if (dataMap.containsKey(elementType)) {
			Integer value = (Integer) dataMap.get(elementType);
			return value.intValue();
		}
		return -1;
	}

	public static String getTypeNameScript(String elementName, String typeName) {
		StringBuffer buffer = new StringBuffer();
		if (StringUtils.isBlank(elementName)) {
			elementName = "elementTypeName";
		}
		if (StringUtils.isBlank(typeName)) {
			typeName = "TEXT";
		}
		buffer.append("<select name=\"").append(elementName).append(
				"\" size=\"1\">\n");
		Iterator iterator = dataMap.keySet().iterator();
		while (iterator.hasNext()) {
			String pType = (String) iterator.next();
			Integer value = (Integer) dataMap.get(pType);
			String display = (String) nameMap.get(value);
			if (display == null) {
				continue;
			}
			buffer.append("<option value=\"").append(pType).append("\"");
			if (StringUtils.equalsIgnoreCase(pType, typeName)) {
				buffer.append(" selected");
			}
			buffer.append(">").append(display).append("</option>\n");
		}
		buffer.append("</select>\n");
		return buffer.toString();
	}

}
