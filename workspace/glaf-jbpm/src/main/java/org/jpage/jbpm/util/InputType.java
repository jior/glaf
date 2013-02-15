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


package org.jpage.jbpm.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jpage.util.ElementType;

public class InputType {

	private static Map dataMap = new HashMap();

	static {
		dataMap.put("input@hidden", new Integer(ElementType.HIDDEN_TYPE));
		dataMap.put("input@radio", new Integer(ElementType.RADIO_TYPE));
		dataMap.put("input@radio.pass",
				new Integer(ElementType.PASS_RADIO_TYPE));
		dataMap.put("input@checkbox", new Integer(ElementType.CHECKBOX_TYPE));
		dataMap.put("input@select", new Integer(ElementType.SELECT_TYPE));
		dataMap.put("input@password", new Integer(ElementType.PASSWORD_TYPE));
		dataMap.put("input@textfield", new Integer(ElementType.TEXT_TYPE));
		dataMap.put("input@textarea", new Integer(ElementType.TEXTAREA_TYPE));
	}

	public static int getInputType(String variableName) {
		if (variableName == null) {
			return -1;
		}
		Iterator iterator = dataMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (variableName.startsWith(key)) {
				Integer value = (Integer) dataMap.get(key);
				return value.intValue();
			}
		}
		return -1;
	}

}
