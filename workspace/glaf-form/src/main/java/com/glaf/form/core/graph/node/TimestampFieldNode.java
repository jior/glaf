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
package com.glaf.form.core.graph.node;

import org.apache.commons.lang.StringUtils;

import com.glaf.core.util.FieldType;

public class TimestampFieldNode extends DateFieldNode {

	private static final long serialVersionUID = 1L;

	public static final String NODE_TYPE = "timestamp";

	public TimestampFieldNode() {
		setNodeType(NODE_TYPE);
	}

	public String getNodeType() {
		return NODE_TYPE;
	}

	@Override
	public int getDataType() {
		return FieldType.TIMESTAMP_TYPE;
	}

	public String getJavaType() {
		return FieldType.getJavaType(FieldType.TIMESTAMP_TYPE);
	}

	public boolean isDate(Object value) {
		if (value != null) {
			if (value instanceof java.util.Date) {
				return true;
			}
			if (value instanceof String) {
				String datePattern = pattern;
				if (StringUtils.isEmpty(datePattern)) {
					datePattern = "yyyy-MM-dd HH:mm:ss";
				}
			}
		}
		return false;
	}

}