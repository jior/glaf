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

package com.glaf.core.batch.mapper;

import java.util.*;
import org.springframework.stereotype.Component;
import com.glaf.core.batch.domain.*;
import com.glaf.core.batch.query.*;

@Component
public interface JobExecutionMapper {

	void deleteJobExecutionById(int id);

	void deleteJobExecutionByJobInstanceId(int jobInstanceId);

	JobExecution getJobExecutionById(int id);

	List<JobExecution> getJobExecutionByJobInstanceId(int jobInstanceId);

	int getJobExecutionCount(Map<String, Object> parameter);

	int getJobExecutionCountByQueryCriteria(JobExecutionQuery query);

	List<JobExecution> getJobExecutions(Map<String, Object> parameter);

	List<JobExecution> getJobExecutionsByQueryCriteria(JobExecutionQuery query);

	void insertJobExecution(JobExecution model);

	void updateJobExecution(JobExecution model);

}