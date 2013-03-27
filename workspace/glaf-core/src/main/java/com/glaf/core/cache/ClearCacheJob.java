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

package com.glaf.core.cache;

import java.util.concurrent.Semaphore;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ClearCacheJob implements Job {
	protected final static Log logger = LogFactory.getLog(ClearCacheJob.class);

	private static final int MAX_AVAILABLE = 1;
	// ���ź�����ʼ��Ϊ 1��ʹ������ʹ��ʱ���ֻ��һ�����õ���ɣ��Ӷ�������һ���໥�ų������
	private final Semaphore semaphore = new Semaphore(MAX_AVAILABLE, true);

	public ClearCacheJob() {

	}

	public void clearAll() {
		try {
			semaphore.acquire();
			logger.debug("start clear cache...");
			CacheFactory.clearAll();
			logger.debug("end clear cache.");
		} catch (Exception ex) {
			logger.debug(ex);
		} finally {
			semaphore.release();
		}
	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			semaphore.acquire();
			logger.debug("start clear cache...");
			CacheFactory.clearAll();
			logger.debug("end clear cache.");
		} catch (Exception ex) {
			logger.debug(ex);
		} finally {
			semaphore.release();
		}
	}
}