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

package com.glaf.batch.mapper;

import java.util.*;
import org.springframework.stereotype.Component;
import com.glaf.batch.domain.*;
import com.glaf.batch.query.*;

@Component
public interface JobStepDefinitionParamMapper {

	void deleteJobStepDefinitionParamById(Long id);

	void deleteParamsByStepDefinitionId(Long stepDefinitionId);

	JobStepDefinitionParam getJobStepDefinitionParamById(Long id);

	int getJobStepDefinitionParamCount(JobStepDefinitionParamQuery query);

	List<JobStepDefinitionParam> getJobStepDefinitionParams(
			JobStepDefinitionParamQuery query);

	List<JobStepDefinitionParam> getParamsByJobDefinitionId(Long jobDefinitionId);

	List<JobStepDefinitionParam> getParamsByStepDefinitionId(
			Long stepDefinitionId);

	void insertJobStepDefinitionParam(JobStepDefinitionParam model);

	void updateJobStepDefinitionParam(JobStepDefinitionParam model);

}
