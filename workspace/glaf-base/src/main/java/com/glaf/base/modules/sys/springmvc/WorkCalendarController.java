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

package com.glaf.base.modules.sys.springmvc;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.sys.model.WorkCalendar;
import com.glaf.base.modules.sys.service.WorkCalendarService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.util.ResponseUtils;

@Controller("/sys/workCalendar")
@RequestMapping("/sys/workCalendar.do")
public class WorkCalendarController {
	private static final Log logger = LogFactory
			.getLog(WorkCalendarController.class);

	private WorkCalendarService workCalendarService;

	/**
	 * ������¼
	 * 
	 * @param year
	 * @param month
	 * @param day
	 */
	@ResponseBody
	@RequestMapping(params = "method=createData")
	public byte[] createData(@RequestParam(value = "year") int year,
			@RequestParam(value = "month") int month,
			@RequestParam(value = "day") int day) {
		try {
			WorkCalendar calendar = workCalendarService.find(year, month, day);
			if (calendar == null) {
				calendar = new WorkCalendar();
				calendar.setFreeYear(year);
				calendar.setFreeMonth(month);
				calendar.setFreeDay(day);
				workCalendarService.create(calendar);
				return ResponseUtils.responseJsonResult(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	/**
	 * ɾ����¼
	 * 
	 * @param year
	 * @param month
	 * @param day
	 */
	@ResponseBody
	@RequestMapping(params = "method=deleteData")
	public byte[] deleteData(@RequestParam(value = "year") int year,
			@RequestParam(value = "month") int month,
			@RequestParam(value = "day") int day) {
		try {
			WorkCalendar calendar = workCalendarService.find(year, month, day);
			if (calendar != null) {
				workCalendarService.delete(calendar.getId());
				return ResponseUtils.responseJsonResult(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setWorkCalendarService(WorkCalendarService workCalendarService) {
		this.workCalendarService = workCalendarService;

	}

	/**
	 * ��ʾ���������б�
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=showCalendar")
	public ModelAndView showCalendar(HttpServletRequest request,
			ModelMap modelMap) {
		Calendar cal = Calendar.getInstance();
		int month = ParamUtil.getIntParameter(request, "month",
				cal.get(Calendar.MONTH));
		int year = ParamUtil.getIntParameter(request, "year",
				cal.get(Calendar.YEAR));

		cal.set(Calendar.MONTH, month); // �����·�
		cal.set(Calendar.YEAR, year); // �������
		cal.setFirstDayOfWeek(Calendar.SUNDAY);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		logger.info("month:" + month);
		int firstIndex = cal.get(Calendar.DAY_OF_WEEK) - 1; // ���µ�һ�������ڼ�
		logger.info("firstIndex:" + firstIndex);
		int maxIndex = cal.getActualMaximum(Calendar.DAY_OF_MONTH);// ���µ�����
		logger.info("maxIndex:" + maxIndex);
		int weeks = Calendar.WEEK_OF_MONTH;// ���µ�����
		cal.set(Calendar.DATE, 1);// ����1�������ڼ�
		if (cal.get(Calendar.DAY_OF_WEEK) == 7)
			weeks += 1;
		logger.info("day of week:" + cal.get(Calendar.DAY_OF_WEEK));
		logger.info("weeks:" + weeks);

		String days[] = new String[42];
		for (int i = 0; i < 42; i++) {
			days[i] = "";
		}
		for (int i = 0; i < maxIndex; i++) {
			days[firstIndex + i] = String.valueOf(i + 1);
		}

		List<Integer> list = workCalendarService.getWorkDateList(year,
				month + 1);
		if (list == null) {
			list = new java.util.ArrayList<Integer>();
		}

		request.setAttribute("list", list);
		request.setAttribute("year", String.valueOf(year));
		request.setAttribute("month", String.valueOf(month));
		request.setAttribute("weeks", String.valueOf(weeks));
		request.setAttribute("days", days);

		String x_view = ViewProperties.getString("calendar.showCalendar");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/calendar/calendar", modelMap);
	}

	/**
	 * ��ʾ���������б�
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=showList")
	public ModelAndView showList(HttpServletRequest request, ModelMap modelMap) {
		Calendar cal = Calendar.getInstance();
		int year = ParamUtil.getIntParameter(request, "year",
				cal.get(Calendar.YEAR));
		request.setAttribute("year", String.valueOf(year));

		String x_view = ViewProperties.getString("calendar.showList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/calendar/work_calendar", modelMap);
	}
}