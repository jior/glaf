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

package com.glaf.activiti.model;

import org.activiti.engine.history.HistoricActivityInstance;

public class ActivityInfo {
	protected String activityId;

	private HistoricActivityInstance activityInstance;

	private ActivityCoordinates activityCoordinates;

	public ActivityInfo() {

	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public HistoricActivityInstance getActivityInstance() {
		return activityInstance;
	}

	public void setActivityInstance(HistoricActivityInstance activityInstance) {
		this.activityInstance = activityInstance;
	}

	public ActivityCoordinates getCoordinates() {
		return activityCoordinates;
	}

	public void setCoordinates(ActivityCoordinates activityCoordinates) {
		this.activityCoordinates = activityCoordinates;
	}

}