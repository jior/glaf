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

package com.glaf.jbpm.persistence;

import java.util.List;

import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.taskmgmt.def.Task;
import org.springframework.stereotype.Component;

import com.glaf.jbpm.model.TaskItem;
import com.glaf.jbpm.query.ProcessQuery;

@Component
public interface JbpmPersistence {

	List<ProcessDefinition> findLatestProcessDefinitions(ProcessQuery query);

	List<Task> findTasksByProcessDefinition(ProcessQuery query);

	List<TaskItem> getTaskItems(ProcessQuery query);

	List<TaskItem> getPooledTaskItems(ProcessQuery query);

	List<TaskItem> getWorkedTaskItems(ProcessQuery query);

	List<Long> getFinishedProcessInstanceIds(ProcessQuery query);

	List<Long> getWorkedProcessInstanceIds(ProcessQuery query);

}