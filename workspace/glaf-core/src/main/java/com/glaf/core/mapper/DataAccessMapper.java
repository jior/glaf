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

import com.glaf.core.domain.DataAccessEntity;
import com.glaf.core.domain.DynamicAccessEntity;
import com.glaf.core.domain.ModuleAccessEntity;

@Component
public interface DataAccessMapper {

	void deleteDataAccess(Object parameter);

	void deleteDynamicAccess(Object parameter);

	void deleteDynamicAccessByServiceKey(Object parameter);

	void deleteModuleAccess(Object parameter);

	void deleteModuleAccessByServiceKey(Object parameter);

	List<DataAccessEntity> getDataAccessByBusinessKey(Object parameter);

	List<DataAccessEntity> getDynamicAccessByServiceKey(Object parameter);

	List<ModuleAccessEntity> getModuleAccessByServiceKey(String serviceKey);

	void insertDataAccess(DataAccessEntity entity);

	void insertDynamicAccess(DynamicAccessEntity entity);

	void insertModuleAccess(ModuleAccessEntity entity);

	void updateDynamicAccess(DynamicAccessEntity entity);

	void updateModuleAccess(ModuleAccessEntity entity);

}