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

import com.glaf.form.core.graph.def.FormNode;
import com.glaf.core.util.FieldType;

public class PersistenceNode extends FormNode {

	private static final long serialVersionUID = 1L;

	public String getColumnName() {
		if (StringUtils.isEmpty(columnName)) {
			columnName = name.toUpperCase() + "_";
		}
		return columnName;
	}

	public Object getValue(String name) {
		return null;
	}

	public String getJavaType() {
		return FieldType.getJavaType(FieldType.STRING_TYPE);
	}

}