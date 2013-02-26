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

package com.glaf.dts.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.glaf.dts.transform.MxTransformManager;

public class MxTransformTableJob implements Job {
	public final static Logger log = LoggerFactory
			.getLogger(MxTransformTableJob.class);

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String tableName = (String) context.getJobDetail().getJobDataMap()
				.get("tableName");
		if (tableName != null && tableName.trim().length() > 0) {
			MxTransformManager manager = new MxTransformManager();
			try {
				manager.transformTable(tableName);
				Thread.sleep(5000);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}