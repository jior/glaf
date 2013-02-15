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


package org.jpage.jbpm.ibatis;

import java.util.HashMap;
import java.util.Map;

public class RuntimeSQLMapper implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String sqlMapAction;

	/**
	 * 流程及相关参数，引擎内部处理使用
	 */
	private Map params = new HashMap();

	/**
	 * SQLMAP语句的集合 其中key是定义在SQLMap配置文件中的id，不带名称空间
	 * value是对应于key的值，可以是任何的pojo或Map对象
	 */
	private Map statementMap = new HashMap();

	public RuntimeSQLMapper() {

	}

	public String getSqlMapAction() {
		return sqlMapAction;
	}

	public void setSqlMapAction(String sqlMapAction) {
		this.sqlMapAction = sqlMapAction;
	}

	/**
	 * 流程及相关参数，引擎内部处理使用
	 */
	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	/**
	 * SQLMAP语句的集合 其中key是定义在SQLMap配置文件中的id，不带名称空间
	 * value是对应于key的值，可以是任何的pojo或Map对象
	 */
	public Map getStatementMap() {
		return statementMap;
	}

	public void setStatementMap(Map statementMap) {
		this.statementMap = statementMap;
	}

}
