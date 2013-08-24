package com.glaf.cms.fullcalendar.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.cms.fullcalendar.model.*;
import com.glaf.cms.fullcalendar.query.*;

@Transactional(readOnly = true)
public interface FullCalendarService {

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	/**
	 * ��������ɾ��������¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> ids);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	FullCalendar getFullCalendar(Long id);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getFullCalendarCountByQueryCriteria(FullCalendarQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<FullCalendar> getFullCalendarsByQueryCriteria(int start, int pageSize,
			FullCalendarQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<FullCalendar> list(FullCalendarQuery query);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	void save(FullCalendar fullCalendar);

}
