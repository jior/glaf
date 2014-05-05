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

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.domain.*;
import com.glaf.core.query.*;

@Transactional(readOnly = true)
public interface ISysCalendarService {

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	SysCalendar getSysCalendar(Long id);

	/**
	 * 获取某条生产线的系统日历
	 * 
	 * @param productionLine
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	SysCalendar getSysCalendar(String productionLine, int year, int month,
			int day);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<SysCalendar> list(SysCalendarQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(SysCalendar sysCalendar);

}
