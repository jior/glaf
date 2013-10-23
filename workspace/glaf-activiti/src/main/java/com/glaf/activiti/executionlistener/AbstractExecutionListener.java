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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractExecutionListener implements ExecutionListener {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory
			.getLogger(AbstractExecutionListener.class);

	public void notify(DelegateExecution delegateExecution) {
		String eventName = delegateExecution.getEventName();
		if ("start".equals(eventName)) {
			try {
				this.onStart(delegateExecution);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		if ("end".equals(eventName)) {
			try {
				this.onEnd(delegateExecution);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
	}

	public void onStart(DelegateExecution delegateExecution) throws Exception {
		
	}

	public void onEnd(DelegateExecution delegateExecution) throws Exception {
		
	}
}
