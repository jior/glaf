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

package com.glaf.core.test;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.glaf.core.jdbc.QueryHelper;

public class QueryHelperTest {

	protected static final Log logger = LogFactory
			.getLog(QueryHelperTest.class);

	@Test
	public void testListQuery() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		QueryHelper helper = new QueryHelper();
		List<Map<String, Object>> result = helper
				.getResultList(
						"select d.ID as id, d.ID as deptId, d.NAME as name, d.CODE as code, d.DEPTDESC as desc, d.FINCODE as fincode  from SYS_DEPARTMENT d order by d.ID asc ",
						paramMap, 0, 100);
		logger.info(result);
	}

	@Test
	public void testSingleQuery() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("actorId", "joy");
		QueryHelper helper = new QueryHelper();
		Map<String, Object> result = helper
				.selectOne(
						" select u.ID as id, u.ACCOUNT as account, u.NAME as name, u.CODE as code, u.EMAIL as email, u.MOBILE as mobile, u.DEPTID as deptId from SYS_USER u where u.account = #{actorId} ",
						paramMap);
		logger.info(result);
	}

}
