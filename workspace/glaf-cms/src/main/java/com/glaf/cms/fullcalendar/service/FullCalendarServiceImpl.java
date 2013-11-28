package com.glaf.cms.fullcalendar.service;

import java.util.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.cms.fullcalendar.mapper.*;
import com.glaf.cms.fullcalendar.model.*;
import com.glaf.cms.fullcalendar.query.*;
import com.glaf.cms.fullcalendar.service.FullCalendarService;

@Service("fullCalendarService")
@Transactional(readOnly = true)
public class FullCalendarServiceImpl implements FullCalendarService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FullCalendarMapper fullCalendarMapper;

	public FullCalendarServiceImpl() {

	}

	public int count(FullCalendarQuery query) {
		query.ensureInitialized();
		return fullCalendarMapper.getFullCalendarCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			fullCalendarMapper.deleteFullCalendarById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				fullCalendarMapper.deleteFullCalendarById(id);
			}
		}
	}

	public FullCalendar getFullCalendar(Long id) {
		if (id == null) {
			return null;
		}
		FullCalendar fullCalendar = fullCalendarMapper.getFullCalendarById(id);
		return fullCalendar;
	}

	public int getFullCalendarCountByQueryCriteria(FullCalendarQuery query) {
		return fullCalendarMapper.getFullCalendarCount(query);
	}

	public List<FullCalendar> getFullCalendarsByQueryCriteria(int start,
			int pageSize, FullCalendarQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FullCalendar> rows = sqlSessionTemplate.selectList(
				"getFullCalendars", query, rowBounds);
		return rows;
	}

	public List<FullCalendar> list(FullCalendarQuery query) {
		List<FullCalendar> list = fullCalendarMapper.getFullCalendars(query);
		return list;
	}

	@Transactional
	public void save(FullCalendar fullCalendar) {
		if (fullCalendar.getId() == null) {
			fullCalendar.setId(idGenerator.nextId("CMS_FULLCALENDAR"));
			fullCalendarMapper.insertFullCalendar(fullCalendar);
		} else {
			fullCalendarMapper.updateFullCalendar(fullCalendar);
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setFullCalendarMapper(FullCalendarMapper fullCalendarMapper) {
		this.fullCalendarMapper = fullCalendarMapper;
	}

	@javax.annotation.Resource 
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
