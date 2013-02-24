package com.glaf.apps.trip.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.apps.trip.model.*;
import com.glaf.apps.trip.query.*;

@Transactional(readOnly = true)
public interface TripService {

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * ��������ɾ��������¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> rowIds);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<Trip> list(TripQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getTripCountByQueryCriteria(TripQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<Trip> getTripsByQueryCriteria(int start, int pageSize, TripQuery query);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	Trip getTrip(String id);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	void save(Trip trip);

}
