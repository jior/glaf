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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jpage.util.DateTools;
import org.jpage.util.FieldType;

public class ConvertUtil {
	
	public static List toList(Collection rows){
		List list = new ArrayList();
		Iterator iterator =rows.iterator();
		while(iterator.hasNext()){
			list.add(iterator.next());
		}
		return list;
	}

	public static Object getValue(String value, String typeName) {
		java.util.Date date = null;
		Object obj = value;
		int type = FieldType.getFieldType(typeName);
		switch (type) {
		case FieldType.DATE_TYPE:
			if (StringUtils.isNotBlank(value)) {
				date = DateTools.toDate(value);
				obj = date;
			}
			break;
		case FieldType.TIMESTAMP_TYPE:
			if (StringUtils.isNotBlank(value)) {
				date = DateTools.toDate(value);
			}
			obj = DateTools.toTimestamp(date);
			break;
		case FieldType.DOUBLE_TYPE:
			if (StringUtils.isNotBlank(value)) {
				Double d = new Double(value);
				obj = d;
			}
			break;
		case FieldType.BOOLEAN_TYPE:
			if (StringUtils.isNotBlank(value)) {
				Boolean b = new Boolean(value);
				obj = b;
			}
			break;
		case FieldType.SHORT_TYPE:
			if (StringUtils.isNotBlank(value)) {
				Short s = new Short(value);
				obj = s;
			}
			break;
		case FieldType.INTEGER_TYPE:
			if (StringUtils.isNotBlank(value)) {
				Integer integer = new Integer(value);
				obj = integer;
			}
			break;
		case FieldType.LONG_TYPE:
			if (StringUtils.isNotBlank(value)) {
				Long l = new Long(value);
				obj = l;
			}
			break;
		default:
			break;
		}
		return obj;
	}

}
