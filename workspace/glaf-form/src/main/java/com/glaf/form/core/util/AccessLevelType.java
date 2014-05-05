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

package com.glaf.form.core.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class AccessLevelType {

	public final static int HIDE_TYPE = 0;

	public final static int VIEW_TYPE = 1;

	public final static int READ_ONLY_TYPE = 10;

	public final static int WRITE_TYPE = 11;

	public final static int REQUIRED_TYPE = 99;

	private final static Map<Integer, String> nameMap = new LinkedHashMap<Integer, String>();

	static {
		nameMap.put(HIDE_TYPE, "隐藏");
		nameMap.put(VIEW_TYPE, "可见");
		nameMap.put(READ_ONLY_TYPE, "只读");
		nameMap.put(WRITE_TYPE, "可编辑");
		nameMap.put(REQUIRED_TYPE, "必填");
	}

	private AccessLevelType() {

	}

	public static Map<Integer, String> getNameMap() {
		return nameMap;
	}

}