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

public class ResourceType {
	public final static int PROCESS_OPINION_TYPE = 1;

	public final static int PROCESS_DATAFIELD_TYPE = 2;

	public final static int REQUEST_ATTRIBUTE_TYPE = 3;

	public final static int SESSION_ATTRIBUTE_TYPE = 4;

	public final static int GRID_PARAMETER_TYPE = 99;

	public final static int HTML_CONTENT_TYPE = 100;

	public final static int JSP_INCLUDE_TYPE = 10000;

	public final static String TASK_FORWARD_PAGE = "task@forward:";

	public final static String NEXT_STEP_PAGE = "jsp@nextstep:";

	public final static String PAGE_REF_TAG = "page@ref:";

	public final static String GRID_PARAMETER_TAG = "grid@param:";

	public final static String GRID_PARAMETERS_TAG = "grid@params:";

	public final static String JSP_INCLUDE_TAG = "jsp@include:";

	public final static String JSP_FORWARD_TAG = "jsp@forward:";

	public final static String RESOURCE_REF_TAG = "resource@ref:";

	public final static String REQUEST_ATTRIBUTE = "request@attribute:";

	public final static String SESSION_ATTRIBUTE = "session@attribute:";

	public final static String MAIL_MESSAGE = "message@mail:";

	public final static String SMS_MESSAGE = "message@sms:";

	public final static String HTML_CONTENT = "html@content:";

	public final static String PROCESS_OPINION = "process@opinion:";

	public final static String PROCESS_DATAFIELD = "process@datafield:";

	public final static String PROCESS_FALLBACK = "process@fallback:";

	private static Map dataMap = new HashMap();

	static {
		dataMap.put(HTML_CONTENT, new Integer(HTML_CONTENT_TYPE));
		dataMap.put(PROCESS_OPINION, new Integer(PROCESS_OPINION_TYPE));
		dataMap.put(PROCESS_DATAFIELD, new Integer(PROCESS_DATAFIELD_TYPE));
		dataMap.put(GRID_PARAMETER_TAG, new Integer(GRID_PARAMETER_TYPE));
		dataMap.put(JSP_INCLUDE_TAG, new Integer(JSP_INCLUDE_TYPE));
		dataMap.put(REQUEST_ATTRIBUTE, new Integer(REQUEST_ATTRIBUTE_TYPE));
		dataMap.put(SESSION_ATTRIBUTE, new Integer(SESSION_ATTRIBUTE_TYPE));
	}

	public static int getResourceType(String variableName) {
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
