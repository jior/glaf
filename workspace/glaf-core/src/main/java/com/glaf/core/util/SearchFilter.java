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

package com.glaf.core.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SearchFilter {

	public final static String EQUALS = "=";

	public final static String NOT_EQUALS = "!=";

	public final static String GREATER_THAN = ">";

	public final static String GREATER_THAN_OR_EQUAL = ">=";

	public final static String LESS_THAN = "<";

	public final static String LESS_THAN_OR_EQUAL = "<=";

	public final static String LIKE = "like";

	public final static String NOT_LIKE = "not like";

	protected final static ConcurrentMap<String, String> searchFilters = new ConcurrentHashMap<String, String>();
	protected final static ConcurrentMap<Integer, String> searchTypeFilters = new ConcurrentHashMap<Integer, String>();

	static {
		searchFilters.put("=", "eq");
		searchFilters.put("!=", "ne");
		searchFilters.put(">=", "ge");
		searchFilters.put(">", "gt");
		searchFilters.put("<=", "le");
		searchFilters.put("<", "lt");
		searchFilters.put("LIKE", "like");
		searchFilters.put("NOT LIKE", "not like");
		searchTypeFilters.put(FieldType.SHORT_TYPE, "=,!=,>=,>,<=,<");
		searchTypeFilters.put(FieldType.INTEGER_TYPE, "=,!=,>=,>,<=,<");
		searchTypeFilters.put(FieldType.LONG_TYPE, "=,!=,>=,>,<=,<");
		searchTypeFilters.put(FieldType.DOUBLE_TYPE, "=,!=,>=,>,<=,<");
		searchTypeFilters.put(FieldType.DATE_TYPE, "=,!=,>=,>,<=,<");
		searchTypeFilters.put(FieldType.STRING_TYPE, "=,!=,LIKE,NOT LIKE");
		searchTypeFilters.put(FieldType.CHAR_TYPE, "=,!=,LIKE,NOT LIKE");
		searchTypeFilters.put(FieldType.TEXT_TYPE, "=,!=,LIKE,NOT LIKE");
		searchTypeFilters.put(FieldType.CLOB_TYPE, "=,!=,LIKE,NOT LIKE");
	}

}