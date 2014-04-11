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

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.glaf.core.util.FieldType;
import com.glaf.form.core.graph.node.CheckboxNode;
import com.glaf.form.core.graph.node.DateFieldNode;
import com.glaf.form.core.graph.node.NumberFieldNode;
import com.glaf.form.core.graph.node.PersistenceNode;
import com.glaf.form.core.graph.node.RadioNode;
import com.glaf.form.core.graph.node.SelectNode;
import com.glaf.form.core.graph.node.TextAreaNode;
import com.glaf.form.core.graph.node.TextFieldNode;

public class SearchFilterTools {

	protected final static Map<String, String> searchFilters = new java.util.concurrent.ConcurrentHashMap<String, String>();

	protected final static Map<String, String> searchNodeFilters = new java.util.concurrent.ConcurrentHashMap<String, String>();

	protected final static Map<Integer, String> searchTypeFilters = new java.util.concurrent.ConcurrentHashMap<Integer, String>();

	static {
		searchFilters.put("=", "eq");
		searchFilters.put("!=", "ne");
		searchFilters.put(">=", "ge");
		searchFilters.put(">", "gt");
		searchFilters.put("<=", "le");
		searchFilters.put("<", "lt");
		searchFilters.put("LIKE", "like");
		searchFilters.put("NOT LIKE", "not like");
		searchNodeFilters.put(CheckboxNode.NODE_TYPE, "=,!=");
		searchNodeFilters.put(RadioNode.NODE_TYPE, "=,!=");
		searchNodeFilters.put(SelectNode.NODE_TYPE, "=,!=");
		searchNodeFilters.put(DateFieldNode.NODE_TYPE, "=,!=,>=,>,<=,<");
		searchNodeFilters.put(NumberFieldNode.NODE_TYPE, "=,!=,>=,>,<=,<");
		searchNodeFilters.put(TextAreaNode.NODE_TYPE, "=,!=,LIKE,NOT LIKE");
		searchNodeFilters.put(TextFieldNode.NODE_TYPE, "=,!=,LIKE,NOT LIKE");
		searchTypeFilters.put(Integer.valueOf(FieldType.SHORT_TYPE),
				"=,!=,>=,>,<=,<");
		searchTypeFilters.put(Integer.valueOf(FieldType.INTEGER_TYPE),
				"=,!=,>=,>,<=,<");
		searchTypeFilters.put(Integer.valueOf(FieldType.LONG_TYPE),
				"=,!=,>=,>,<=,<");
		searchTypeFilters.put(Integer.valueOf(FieldType.DOUBLE_TYPE),
				"=,!=,>=,>,<=,<");
		searchTypeFilters.put(Integer.valueOf(FieldType.DATE_TYPE),
				"=,!=,>=,>,<=,<");
		searchTypeFilters.put(Integer.valueOf(FieldType.STRING_TYPE),
				"=,!=,LIKE,NOT LIKE");
		searchTypeFilters.put(Integer.valueOf(FieldType.CHAR_TYPE),
				"=,!=,LIKE,NOT LIKE");
		searchTypeFilters.put(Integer.valueOf(FieldType.TEXT_TYPE),
				"=,!=,LIKE,NOT LIKE");
		searchTypeFilters.put(Integer.valueOf(FieldType.CLOB_TYPE),
				"=,!=,LIKE,NOT LIKE");
	}

	public static String getSearchFilter(PersistenceNode formNode) {
		String searchFilter = "=";
		if (formNode.getDataType() != 0) {
			if (searchFilters.keySet().contains(formNode.getSearchFilter())) {
				String op = searchTypeFilters.get(Integer.valueOf(formNode
						.getDataType()));
				if (op != null && op.indexOf(formNode.getSearchFilter()) != -1) {
					searchFilter = formNode.getSearchFilter();
				}
			}
		} else if (StringUtils.isNotEmpty(formNode.getNodeType())
				&& StringUtils.isNotEmpty(formNode.getSearchFilter())) {
			if (searchFilters.keySet().contains(formNode.getSearchFilter())) {
				String op = searchNodeFilters.get(formNode.getNodeType());
				if (op != null && op.indexOf(formNode.getSearchFilter()) != -1) {
					searchFilter = formNode.getSearchFilter();
				}
			}
		}
		return searchFilter;
	}

}