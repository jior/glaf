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
package com.glaf.oa.stravel.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import com.glaf.oa.stravel.model.Stravel;
import com.glaf.oa.stravel.query.StravelQuery;
import com.glaf.oa.traveladdress.model.Traveladdress;
import com.glaf.oa.travelfee.model.Travelfee;
import com.glaf.oa.travelpersonnel.model.Travelpersonnel;

@Transactional(readOnly = true)
public interface StravelService {

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
	void deleteByIds(List<Long> travelids);

	/**
	 * ���ݲ�ѯ������ȡ��¼�б�
	 * 
	 * @return
	 */
	List<Stravel> list(StravelQuery query);

	List<Traveladdress> getTraveladdressList(Long travelid);

	List<Travelpersonnel> getTravelpersonnelList(Long travelid);

	List<Travelfee> getTravelfeeList(Long travelid);

	/**
	 * ���ݲ�ѯ������ȡ��¼����
	 * 
	 * @return
	 */
	int getStravelCountByQueryCriteria(StravelQuery query);

	/**
	 * ���ݲ�ѯ������ȡһҳ������
	 * 
	 * @return
	 */
	List<Stravel> getStravelsByQueryCriteria(int start, int pageSize,
			StravelQuery query);

	/**
	 * ����������ȡһ����¼
	 * 
	 * @return
	 */
	Stravel getStravel(Long id);

	/**
	 * ����һ����¼
	 * 
	 * @return
	 */
	@Transactional
	void save(Stravel stravel);

	int getReviewStravelCountByQueryCriteria(StravelQuery query);

	List<Stravel> getReviewStravelsByQueryCriteria(int start, int pageSize,
			StravelQuery query);

}