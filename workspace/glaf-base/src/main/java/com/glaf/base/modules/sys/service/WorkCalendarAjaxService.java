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

package com.glaf.base.modules.sys.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.sys.model.WorkCalendar;

public class WorkCalendarAjaxService {
	private static final Log logger = LogFactory.getLog(WorkCalendarAjaxService.class);
	private WorkCalendarService workCalendarService;
	
	public void setWorkCalendarService(WorkCalendarService workCalendarService) {
		this.workCalendarService = workCalendarService;
		logger.info("setWorkCalendarService");
	}
	/**
	 * ´´½¨¼ÇÂ¼
	 * @param year
	 * @param month
	 * @param day
	 */
	public void createData(int year, int month, int day){
		WorkCalendar bean = workCalendarService.find(year, month, day);
		if(bean==null){
			bean = new WorkCalendar();
			bean.setFreeYear(year);
			bean.setFreeMonth(month);
			bean.setFreeDay(day);
			workCalendarService.create(bean);
		}
	}
	/**
	 * É¾³ý
	 * @param id
	 */
	public void deleteData(int year, int month, int day){
		WorkCalendar bean = workCalendarService.find(year, month, day);
		if(bean!=null) workCalendarService.delete(bean);
	}
}