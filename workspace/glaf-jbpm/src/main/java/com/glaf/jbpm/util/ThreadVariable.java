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

package com.glaf.jbpm.util;

import java.util.Map;

import com.glaf.jbpm.datafield.DataField;

public class ThreadVariable {

	static ThreadLocal<Map<String, Object>> dataFieldsThreadLocal = new ThreadLocal<Map<String, Object>>();

	static ThreadLocal<Object> approveThreadLocal = new ThreadLocal<Object>();

	public static void clear() {
		dataFieldsThreadLocal.remove();
		approveThreadLocal.remove();
	}

	public static void setApprove(Object approve) {
		approveThreadLocal.set(approve);
	}

	public static Object getApprove() {
		return approveThreadLocal.get();
	}

	public static void setDataFields(Map<String, Object> dataFields) {
		dataFieldsThreadLocal.set(dataFields);
	}

	public static Map<String, Object> getDataFields() {
		return dataFieldsThreadLocal.get();
	}

	public static void addDataField(DataField dataField) {
		if (dataField != null) {
			if (dataFieldsThreadLocal == null) {
				dataFieldsThreadLocal = new ThreadLocal<Map<String, Object>>();
			}
			if (dataFieldsThreadLocal.get() != null) {
				dataFieldsThreadLocal.get().put(dataField.getName(), dataField);
			}
		}
	}

}