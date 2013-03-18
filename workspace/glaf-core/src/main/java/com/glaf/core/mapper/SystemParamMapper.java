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

package com.glaf.core.mapper;

import java.util.*;
import org.springframework.stereotype.Component;
import com.glaf.core.domain.*;
import com.glaf.core.query.SystemParamQuery;

@Component
public interface SystemParamMapper {

	void deleteSystemParamById(String id);
	
	void deleteSystemParamsByServiceKey(String serviceKey);

	List<SystemParam> getSystemParamsByServiceKey(String serviceKey);

	SystemParam getSystemParamById(String id);

	int getSystemParamCount(Map<String, Object> parameter);

	int getSystemParamCountByQueryCriteria(SystemParamQuery query);

	List<SystemParam> getSystemParams(Map<String, Object> parameter);

	List<Map<String, Object>> getSystemParamMapList(
			Map<String, Object> parameter);

	List<SystemParam> getSystemParamsByQueryCriteria(SystemParamQuery query);

	void insertSystemParam(SystemParam model);

	void updateSystemParam(SystemParam model);

}