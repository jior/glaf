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

package com.glaf.mail;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.service.EntityService;

public class MyBatisMailDataManager implements IMailDataManager {
	protected final Log log = LogFactory.getLog(getClass());

	public MyBatisMailDataManager() {

	}

	public List<Object> getResultList(String query, Map<String, Object> paramMap) {
		EntityService entityService = ContextFactory.getBean("entityService");
		return entityService.getList(query, paramMap);
	}

}