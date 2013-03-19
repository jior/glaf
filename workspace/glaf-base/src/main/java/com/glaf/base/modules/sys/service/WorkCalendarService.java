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

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.WorkCalendar;

@Transactional(readOnly = true)
public interface WorkCalendarService {

	/**
	 * ����Ƿ�������,falseΪ��,trueΪ��
	 * 
	 * @param date
	 * @return
	 */
	@Transactional
	boolean checkWorkDate(Date date);

	/**
	 * ����
	 * 
	 * @param bean
	 *            WorkCalendar
	 * @return boolean
	 */
	@Transactional
	boolean create(WorkCalendar bean);

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	@Transactional
	boolean delete(long id);

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            WorkCalendar
	 * @return boolean
	 */
	@Transactional
	boolean delete(WorkCalendar bean);

	/**
	 * ��ȡ����
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
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	WorkCalendar findById(long id);

	/**
	 * ȡ��Ч�ù�������
	 * 
	 * @param startDate
	 *            ��ʼ����
	 * @param interval
	 *            ���
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
	 * ȡ��Ч�ù�������
	 * 
	 * @param startDate
	 *            ��ʼ����
	 * @param interval
	 *            ���
	 * @return
	 */
	Date getWorkDate2(Date startDate, int interval);

	/**
	 * �������зǹ������б�
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	List<Integer> getWorkDateList(int year, int month);

	/**
	 * ����
	 * 
	 * @param bean
	 *            WorkCalendar
	 * @return boolean
	 */
	@Transactional
	boolean update(WorkCalendar bean);

}