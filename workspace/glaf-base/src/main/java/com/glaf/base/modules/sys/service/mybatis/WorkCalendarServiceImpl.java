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

package com.glaf.base.modules.sys.service.mybatis;

import java.util.*;

import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.base.modules.sys.mapper.WorkCalendarMapper;
import com.glaf.base.modules.sys.model.WorkCalendar;
import com.glaf.base.modules.sys.query.WorkCalendarQuery;
import com.glaf.base.modules.sys.service.WorkCalendarService;

@Service("workCalendarService")
@Transactional(readOnly = true)
public class WorkCalendarServiceImpl implements WorkCalendarService {
	protected final static Log logger = LogFactory
			.getLog(WorkCalendarServiceImpl.class);

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected WorkCalendarMapper workCalendarMapper;

	private static ArrayList<Integer> workDateList = new ArrayList<Integer>();

	public WorkCalendarServiceImpl() {

	}

	public boolean checkWorkDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		logger.info("year:" + year + ", month:" + month + ", day:" + day);
		WorkCalendar bean = find(year, month + 1, day);
		if (bean != null) {
			return false;
		}
		return true;
	}

	public int count(WorkCalendarQuery query) {
		query.ensureInitialized();
		return workCalendarMapper.getWorkCalendarCount(query);
	}

	@Transactional
	public boolean create(WorkCalendar bean) {
		if (bean.getId() == 0) {
			bean.setId(idGenerator.nextId());
		}
		this.workCalendarMapper.insertWorkCalendar(bean);
		return false;
	}

	@Transactional
	public boolean delete(long id) {
		this.deleteById(id);
		return true;
	}

	@Transactional
	public boolean delete(WorkCalendar bean) {
		this.deleteById(bean.getId());
		return true;
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			workCalendarMapper.deleteWorkCalendarById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			WorkCalendarQuery query = new WorkCalendarQuery();
			query.rowIds(rowIds);
			workCalendarMapper.deleteWorkCalendars(query);
		}
	}

	public WorkCalendar find(int freeYear, int freeMonth, int freeDay) {
		WorkCalendarQuery query = new WorkCalendarQuery();
		query.freeYear(freeYear);
		query.freeMonth(freeMonth);
		query.freeDay(freeDay);
		List<WorkCalendar> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public WorkCalendar findById(long id) {
		return this.getWorkCalendar(id);
	}

	public WorkCalendar getWorkCalendar(Long id) {
		if (id == null) {
			return null;
		}
		WorkCalendar workCalendar = workCalendarMapper.getWorkCalendarById(id);
		return workCalendar;
	}

	public int getWorkCalendarCountByQueryCriteria(WorkCalendarQuery query) {
		return workCalendarMapper.getWorkCalendarCount(query);
	}

	public List<WorkCalendar> getWorkCalendarsByQueryCriteria(int start,
			int pageSize, WorkCalendarQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<WorkCalendar> rows = sqlSessionTemplate.selectList(
				"getWorkCalendars", query, rowBounds);
		return rows;
	}

	public Date getWorkDate(Date startDate, int interval) {
		if (startDate == null) {
			return null;
		}
		Date endDate = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);

		ArrayList<Date> noneWorkDays = new ArrayList<Date>();
		if (workDateList.size() == 0)
			initWorkDate();
		if (!workDateList.isEmpty()) {
			Iterator<?> iter = workDateList.iterator();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DAY_OF_MONTH, ((Integer) iter.next()).intValue());
			noneWorkDays.add(cal.getTime());
		}

		endDate = getWorkDate(startDate, interval, noneWorkDays);
		return endDate;
	}

	public Date getWorkDate(Date startDate, int interval,
			List<Date> noneWorkDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);

		// 按照间隔天数,循环
		for (int i = 1; i <= interval; i++) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			Date nextDate = cal.getTime();
			if (noneWorkDays.contains(nextDate)) {// 下一天是非工作日,则往后移
				return getWorkDate(nextDate, interval + 1 - i, noneWorkDays);
			}
		}
		return cal.getTime();
	}

	public Date getWorkDate2(Date startDate, int interval) {
		if (startDate == null) {
			return null;
		}
		Date endDate = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);

		ArrayList<Date> noneWorkDays = new ArrayList<Date>();

		// 取连续2个月的非工作日
		List<Integer> list = getWorkDateList(year, month + 1);
		if (list != null) {
			Iterator<Integer> iter = list.iterator();
			while (iter.hasNext()) {
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month);
				cal.set(Calendar.DAY_OF_MONTH,
						((Integer) iter.next()).intValue());
				noneWorkDays.add(cal.getTime());
			}
		}
		list = getWorkDateList(year, month + 2);
		if (list != null) {
			Iterator<Integer> iter = list.iterator();
			while (iter.hasNext()) {
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month + 1);
				cal.set(Calendar.DAY_OF_MONTH,
						((Integer) iter.next()).intValue());
				noneWorkDays.add(cal.getTime());
			}
		}

		endDate = getWorkDate(startDate, interval, noneWorkDays);
		return endDate;
	}

	public List<Integer> getWorkDateList(int freeYear, int freeMonth) {
		WorkCalendarQuery query = new WorkCalendarQuery();
		query.freeYear(freeYear);
		query.freeMonth(freeMonth);

		List<WorkCalendar> list = this.list(query);
		List<Integer> days = new ArrayList<Integer>();
		if (list != null && !list.isEmpty()) {
			for (WorkCalendar cal : list) {
				days.add(cal.getFreeDay());
			}
		}

		return days;
	}

	public void initWorkDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);

		workDateList.addAll(getWorkDateList(year - 1, 12));
		for (int i = 1; i <= 12; i++) {
			workDateList.addAll(getWorkDateList(year, i));
		}
		workDateList.addAll(getWorkDateList(year + 1, 1));
	}

	public List<WorkCalendar> list(WorkCalendarQuery query) {
		query.ensureInitialized();
		List<WorkCalendar> list = workCalendarMapper.getWorkCalendars(query);
		return list;
	}

	@Transactional
	public void save(WorkCalendar workCalendar) {
		if (workCalendar.getId() == 0) {
			workCalendar.setId(idGenerator.nextId());
			// workCalendar.setCreateDate(new Date());
			workCalendarMapper.insertWorkCalendar(workCalendar);
		} else {
			workCalendarMapper.updateWorkCalendar(workCalendar);
		}
	}

	@Resource
	@Qualifier("myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Resource
	public void setWorkCalendarMapper(WorkCalendarMapper workCalendarMapper) {
		this.workCalendarMapper = workCalendarMapper;
	}

	@Transactional
	public boolean update(WorkCalendar bean) {
		workCalendarMapper.updateWorkCalendar(bean);
		return true;
	}

}
