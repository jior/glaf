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
package com.glaf.oa.traveladdress.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.oa.traveladdress.model.*;
import com.glaf.oa.traveladdress.query.*;

@Transactional(readOnly = true)
public interface TraveladdressService {

	/**
	 * ��������ɾ����¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	@Transactional
	void deleteByTravelid(Long travelid);

	/**
	 * ��������ɾ��������¼
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> addressids);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<Traveladdress> list(TraveladdressQuery query);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getTraveladdressCountByQueryCriteria(TraveladdressQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<Traveladdress> getTraveladdresssByQueryCriteria(int start,
			int pageSize, TraveladdressQuery query);

	List<Traveladdress> getTraveladdresssByParentId(Long parentId);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	Traveladdress getTraveladdress(Long id);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	void save(Traveladdress traveladdress);

}