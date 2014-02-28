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

package com.glaf.activiti.executionlistener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.activiti.util.ExecutionUtils;

public class SqlExecutionListener implements ExecutionListener {
	private static final long serialVersionUID = 1L;
	
	private static final Log logger = LogFactory
			.getLog(SqlExecutionListener.class);

	protected Expression sql;

	public void notify(DelegateExecution execution) throws Exception {
		logger.debug("-------------------------------------------------------");
		logger.debug("--------------SQLExecutionListener---------------------");
		logger.debug("-------------------------------------------------------");

		if (sql != null) {
			ExecutionUtils.executeSqlUpdate(execution, sql);
		}
	}

	public void setSql(Expression sql) {
		this.sql = sql;
	}

}