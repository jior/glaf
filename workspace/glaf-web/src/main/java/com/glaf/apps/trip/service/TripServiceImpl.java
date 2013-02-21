package com.glaf.apps.trip.service;

import java.util.*;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.id.*;

import com.glaf.apps.trip.mapper.*;
import com.glaf.apps.trip.model.*;
import com.glaf.apps.trip.query.*;

@Service("tripService")
@Transactional(readOnly = true) 
public class TripServiceImpl implements TripService {
	protected final static Log logger = LogFactory.getLog(TripServiceImpl.class);

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TripMapper tripMapper;

	public TripServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		tripMapper.deleteTripById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
	    if(rowIds != null && !rowIds.isEmpty()){
		TripQuery query = new TripQuery();
		query.rowIds(rowIds);
		tripMapper.deleteTrips(query);
	    }
	}

	public int count(TripQuery query) {
		query.ensureInitialized();
		return tripMapper.getTripCount(query);
	}

	public List<Trip> list(TripQuery query) {
		query.ensureInitialized();
		List<Trip> list = tripMapper.getTrips(query);
		return list;
	}

         /**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	public int getTripCountByQueryCriteria(TripQuery query) {
		return tripMapper.getTripCount(query);
	}

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Trip> getTripsByQueryCriteria(int start, int pageSize,
			TripQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Trip> rows = (List<Trip>) sqlSessionTemplate.selectList(
				"getTrips", query, rowBounds);
		return rows;
	}


	public Trip getTrip(String id) {
	        if(id == null){
		    return null;
		}
		Trip trip = tripMapper.getTripById(id);
		return trip;
	}

	@Transactional
	public void save(Trip trip) {
           if (StringUtils.isEmpty(trip.getId())) {
			trip.setId(idGenerator.getNextId());
			//trip.setCreateDate(new Date());
			tripMapper.insertTrip(trip);
		} else {
			tripMapper.updateTrip(trip);
		}
	}

	@Resource
	@Qualifier("myBatis3DbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setTripMapper(TripMapper tripMapper) {
		this.tripMapper = tripMapper;
	}

        @Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
