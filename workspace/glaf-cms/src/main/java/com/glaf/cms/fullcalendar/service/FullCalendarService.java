package com.glaf.cms.fullcalendar.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.cms.fullcalendar.model.*;
import com.glaf.cms.fullcalendar.query.*;

@Transactional(readOnly = true)
public interface FullCalendarService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> ids);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FullCalendar getFullCalendar(Long id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFullCalendarCountByQueryCriteria(FullCalendarQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FullCalendar> getFullCalendarsByQueryCriteria(int start, int pageSize,
			FullCalendarQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<FullCalendar> list(FullCalendarQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FullCalendar fullCalendar);

}
