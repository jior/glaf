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

package com.glaf.core.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.domain.WorkCalendar;

@Transactional(readOnly = true)
public interface WorkCalendarService {

	/**
	 * 检测是否工作日期,false为否,true为是
	 * 
	 * @param date
	 * @return
	 */
	@Transactional
	boolean checkWorkDate(Date date);

	/**
	 * 保存
	 * 
	 * @param bean
	 *            WorkCalendar
	 * @return boolean
	 */
	@Transactional
	boolean create(WorkCalendar bean);

	/**
	 * 删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	@Transactional
	boolean delete(long id);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            WorkCalendar
	 * @return boolean
	 */
	@Transactional
	boolean delete(WorkCalendar bean);

	/**
	 * 获取对象
	 * 
	 * @param year
	 *            int
	 * @param month
	 *            int
	 * @param day
	 *            int
	 * @return WorkCalendar
	 */
	WorkCalendar find(int year, int month, int day);

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	WorkCalendar findById(long id);

	/**
	 * 取有效得工作日期
	 * 
	 * @param startDate
	 *            开始日期
	 * @param interval
	 *            间隔
	 * @return
	 */
	Date getWorkDate(Date startDate, int interval);

	/**
	 * 
	 * @param startDate
	 * @param interval
	 * @param noneWorkDays
	 * @return
	 */
	Date getWorkDate(Date startDate, int interval, List<Date> noneWorkDays);

	/**
	 * 取有效得工作日期
	 * 
	 * @param startDate
	 *            开始日期
	 * @param interval
	 *            间隔
	 * @return
	 */
	Date getWorkDate2(Date startDate, int interval);

	/**
	 * 返回所有非工作日列表
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	List<Integer> getWorkDateList(int year, int month);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            WorkCalendar
	 * @return boolean
	 */
	@Transactional
	boolean update(WorkCalendar bean);

}