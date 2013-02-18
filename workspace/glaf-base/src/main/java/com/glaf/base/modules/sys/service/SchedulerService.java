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

package com.glaf.base.modules.sys.service;

import java.util.List;

import com.glaf.base.modules.sys.model.Scheduler;

public interface SchedulerService {

	/**
	 * 创建调度任务
	 * 
	 * @param scheduler
	 */
	void save(Scheduler scheduler);

	/**
	 * 获取全部调度任务
	 * 
	 * @return
	 */
	List<Scheduler> getAllSchedulers();

	/**
	 * 根据任务编号获取调度任务
	 * 
	 * @param taskId
	 * @return
	 */
	Scheduler getSchedulerById(String taskId);

	/**
	 * 根据任务类型获取调度任务
	 * 
	 * @param taskType
	 * @return
	 */
	List<Scheduler> getSchedulers(String taskType);

	/**
	 * 获取用户自行定义的调度任务
	 * 
	 * @param createBy
	 * @return
	 */
	List<Scheduler> getUserSchedulers(String createBy);

	/**
	 * 锁定调度任务
	 * 
	 * @param taskId
	 * @param locked
	 */
	void locked(String taskId, int locked);

}