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

package com.glaf.activiti.tasklistener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractTaskListener implements TaskListener {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory
			.getLogger(AbstractTaskListener.class);

	public void notify(DelegateTask delegateTask) {
		String eventName = delegateTask.getEventName();
		if ("create".equals(eventName)) {
			try {
				this.onCreate(delegateTask);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		if ("assignment".equals(eventName)) {
			try {
				this.onAssignment(delegateTask);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		if ("complete".equals(eventName)) {
			try {
				this.onComplete(delegateTask);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
	}

	public void onCreate(DelegateTask delegateTask) throws Exception {
		
	}

	public void onAssignment(DelegateTask delegateTask) throws Exception {
		
	}

	public void onComplete(DelegateTask delegateTask) throws Exception {
		
	}
}
