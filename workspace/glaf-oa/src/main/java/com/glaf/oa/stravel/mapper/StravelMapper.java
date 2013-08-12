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
package com.glaf.oa.stravel.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.oa.stravel.model.Stravel;
import com.glaf.oa.stravel.query.StravelQuery;
import com.glaf.oa.traveladdress.model.Traveladdress;
import com.glaf.oa.travelfee.model.Travelfee;
import com.glaf.oa.travelpersonnel.model.Travelpersonnel;

@Component
public interface StravelMapper {

	void deleteStravels(StravelQuery query);

	void deleteStravelById(Long id);

	Stravel getStravelById(Long id);

	int getStravelCount(StravelQuery query);

	List<Stravel> getStravels(StravelQuery query);

	List<Traveladdress> getTraveladdressList(Long travelid);

	List<Travelpersonnel> getTravelpersonnelList(Long travelid);

	List<Travelfee> getTravelfeeList(Long travelid);

	void insertStravel(Stravel model);

	void updateStravel(Stravel model);

	int getReviewStravelCount(StravelQuery query);

	List<Stravel> getReviewStravels(StravelQuery query);

}