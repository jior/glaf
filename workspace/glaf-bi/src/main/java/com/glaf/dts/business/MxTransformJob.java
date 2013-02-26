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

package com.glaf.dts.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.util.DateUtils;
import com.glaf.dts.transform.*;

public class MxTransformJob implements Job {
	public final static Logger log = LoggerFactory
			.getLogger(MxTransformJob.class);

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String jobName = context.getJobDetail().getKey().getName();
		log.info("Executing job: " + jobName + " executing at "
				+ DateUtils.getDateTime(new java.util.Date()));
		String taskId = (String) context.getJobDetail().getJobDataMap()
				.get("TransformJob_taskId");
		log.info("taskId:" + taskId);
		if (taskId != null) {
			MxTransformThread thread = new MxTransformThread(taskId);
			thread.run();
		}
	}

}