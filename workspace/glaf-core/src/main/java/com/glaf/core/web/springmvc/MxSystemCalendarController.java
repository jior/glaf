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

package com.glaf.core.web.springmvc;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.domain.SysCalendar;
import com.glaf.core.identity.User;
import com.glaf.core.query.SysCalendarQuery;
import com.glaf.core.service.ISysCalendarService;
import com.glaf.core.util.CalendarUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;

@Controller("/sys/calendar")
@RequestMapping("/sys/calendar")
public class MxSystemCalendarController {
	protected static final Log logger = LogFactory
			.getLog(MxSystemCalendarController.class);

	protected ISysCalendarService sysCalendarService;

	public MxSystemCalendarController() {

	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		Calendar cal = Calendar.getInstance();
		int year = RequestUtils.getInt(request, "year", cal.get(Calendar.YEAR));
		int month = RequestUtils.getInt(request, "month",
				cal.get(Calendar.MONTH) + 1);
		String productionLine = request.getParameter("productionLine");
		if (StringUtils.isEmpty(productionLine)) {
			productionLine = CalendarUtils.DEFAULT_PRODUCTION_LINE;
		}
		int daySize = DateUtils.getYearMonthDays(year, month);
		SysCalendarQuery query = new SysCalendarQuery();
		query.setYear(year);
		query.setMonth(month);
		query.setProductionLine(productionLine);
		List<SysCalendar> list = sysCalendarService.list(query);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("productionLine", productionLine);
		request.setAttribute("daySize", daySize);

		Map<String, SysCalendar> map = new java.util.HashMap<String, SysCalendar>();
		for (int i = 0, len = list.size(); i < len; i++) {
			SysCalendar sysCalendar = list.get(i);
			String key = sysCalendar.getProductionLine() + ""
					+ sysCalendar.getYear() + "" + sysCalendar.getMonth() + ""
					+ sysCalendar.getDay();
			map.put(key, sysCalendar);
		}
		request.setAttribute("map", map);
		return new ModelAndView("/modules/sys/calendar/sys_calendar_list",
				modelMap);
	}

	@ResponseBody
	@RequestMapping("/save")
	public byte[] save(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		int year = RequestUtils.getInt(request, "year");
		int month = RequestUtils.getInt(request, "month");
		int groupA = RequestUtils.getInt(request, "groupA");
		int groupB = RequestUtils.getInt(request, "groupB");
		int days = DateUtils.getYearMonthDays(year, month);
		String productionLine = request.getParameter("productionLine");
		Calendar cal = Calendar.getInstance();
		try {
			for (int i = 1; i <= days; i++) {
				String dayStr = "checkDay_" + i;
				int checkValue = RequestUtils.getInt(request, dayStr);

				SysCalendar sysCalendar = this.sysCalendarService
						.getSysCalendar(productionLine, year, month, i);
				boolean isExist = true;
				if (null == sysCalendar) {
					sysCalendar = new SysCalendar();
					isExist = false;
				}
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month - 1);
				cal.set(Calendar.DATE, i);
				Date workDate = cal.getTime();
				sysCalendar.setWorkDate(workDate);
				sysCalendar.setYear(year);
				sysCalendar.setMonth(month);
				sysCalendar.setDay(i);
				sysCalendar.setWeek(cal.get(Calendar.WEEK_OF_YEAR));

				if (checkValue == 1) {
					if (!isExist) {
						sysCalendar.setIsFreeDay(1);
					}
					if (-1 != groupA) {
						sysCalendar.setIsFreeDay(0);
						sysCalendar.setGroupA("A");
						sysCalendar.setDutyA(groupA + "");
					}
					if (-1 != groupB) {
						sysCalendar.setIsFreeDay(0);
						sysCalendar.setGroupB("B");
						sysCalendar.setDutyB(groupB + "");
					}
					if (groupA == 0 && groupB == 0) {
						sysCalendar.setIsFreeDay(1);
					}
				} else {
					if (!isExist) {
						sysCalendar.setIsFreeDay(1);
					}
				}

				sysCalendar.setProductionLine(productionLine);
				sysCalendar.setCreateBy(actorId);
				sysCalendar.setCreateDate(new Date());

				if (checkValue == 1 || !isExist) {
					this.sysCalendarService.save(sysCalendar);
				}
			}
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@ResponseBody
	@RequestMapping("/saveCalendarTime")
	public byte[] saveCalendarTime(HttpServletRequest request) {
		String v1 = request.getParameter("v1");
		String v2 = request.getParameter("v2");
		String mode = request.getParameter("mode");

		Map<String, String> map = new java.util.HashMap<String, String>();
		map.put("morning", v1);
		map.put("evening", v2);
		map.put("mode", mode);

		try {
			CalendarUtils.saveCalendarProperties(map);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setSysCalendarService(ISysCalendarService sysCalendarService) {
		this.sysCalendarService = sysCalendarService;
	}

}
