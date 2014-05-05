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

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.query.TableDefinitionQuery;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.dts.input.TextFileImporter;
import com.glaf.dts.transform.MxTransformManager;

public class ImportAndFetchDataJob implements Job {
	public final static Log logger = LogFactory
			.getLog(ImportAndFetchDataJob.class);

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("-----------------------ImportAndFetchDataJob-----------------------");
		int counter = 0;
		while (counter < 3) {
			boolean isOK = this.importAndFetch();
			if (isOK) {
				break;
			}
			counter++;
		}

	}

	public boolean importAndFetch() {
		boolean execOK = false;
		boolean success = false;
		int retry = 0;
		while (retry < 2 && !success) {
			try {
				retry++;
				TextFileImporter imp = new TextFileImporter();
				imp.importData();
				success = true;
				logger.info("已经成功导入数据到基础表。");
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("导入数据到基础表失败，请再次重试。");
			}
		}

		TableDefinitionQuery query = new TableDefinitionQuery();
		ITableDefinitionService tableDefinitionService = ContextFactory
				.getBean("tableDefinitionService");
		List<TableDefinition> tables = tableDefinitionService.list(query);
		if (tables != null && !tables.isEmpty()) {
			/**
			 * 按照表定义的取数顺序排序
			 */
			Collections.sort(tables);
			int counterOK = 0;
			MxTransformManager manager = new MxTransformManager();
			for (TableDefinition tableDefinition : tables) {
				success = false;
				retry = 0;
				while (retry < 2 && !success) {
					try {
						retry++;
						manager.transformTable(tableDefinition.getTableName());
						success = true;
						counterOK = counterOK + 1;
						logger.info(tableDefinition.getTableName() + "已经成功抽取。");
						Thread.sleep(200);
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error(tableDefinition.getTableName() + "抽取失败。");
					}
				}
			}
			if (counterOK == tables.size()) {
				execOK = true;
			}
		}

		if (execOK) {
			logger.info("抽取数据已经成功。");
		} else {
			logger.info("抽取数据失败。");
		}
		return execOK;
	}

}