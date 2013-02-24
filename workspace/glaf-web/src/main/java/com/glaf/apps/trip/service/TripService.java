package com.glaf.apps.trip.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.apps.trip.model.*;
import com.glaf.apps.trip.query.*;

@Transactional(readOnly = true)
public interface TripService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> rowIds);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<Trip> list(TripQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getTripCountByQueryCriteria(TripQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<Trip> getTripsByQueryCriteria(int start, int pageSize, TripQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	Trip getTrip(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(Trip trip);

}
