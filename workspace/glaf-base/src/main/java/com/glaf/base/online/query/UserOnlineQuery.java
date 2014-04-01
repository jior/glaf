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

package com.glaf.base.online.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class UserOnlineQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String searchWord;
	protected Date loginDateGreaterThanOrEqual;
	protected Date loginDateLessThanOrEqual;
	protected String loginIP;

	public UserOnlineQuery() {

	}

	public String getActorId() {
		return actorId;
	}

	public Date getLoginDateGreaterThanOrEqual() {
		return loginDateGreaterThanOrEqual;
	}

	public Date getLoginDateLessThanOrEqual() {
		return loginDateLessThanOrEqual;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public String getName() {
		return name;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("actorId".equals(sortColumn)) {
				orderBy = "E.ACTORID_" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("loginDate".equals(sortColumn)) {
				orderBy = "E.LOGINDATE_" + a_x;
			}

			if ("loginIP".equals(sortColumn)) {
				orderBy = "E.LOGINIP_" + a_x;
			}

			if ("sessionId".equals(sortColumn)) {
				orderBy = "E.SESSIONID_" + a_x;
			}

		}
		return orderBy;
	}

	public String getSearchWord() {
		if (searchWord != null && searchWord.trim().length() > 0) {
			if (!searchWord.startsWith("%")) {
				searchWord = "%" + searchWord;
			}
			if (!searchWord.endsWith("%")) {
				searchWord = searchWord + "%";
			}
		}
		return searchWord;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("actorId", "ACTORID_");
		addColumn("name", "NAME_");
		addColumn("loginDate", "LOGINDATE_");
		addColumn("loginIP", "LOGINIP_");
		addColumn("sessionId", "SESSIONID_");
	}

	public UserOnlineQuery loginDateGreaterThanOrEqual(
			Date loginDateGreaterThanOrEqual) {
		if (loginDateGreaterThanOrEqual == null) {
			throw new RuntimeException("loginDate is null");
		}
		this.loginDateGreaterThanOrEqual = loginDateGreaterThanOrEqual;
		return this;
	}

	public UserOnlineQuery loginDateLessThanOrEqual(
			Date loginDateLessThanOrEqual) {
		if (loginDateLessThanOrEqual == null) {
			throw new RuntimeException("loginDate is null");
		}
		this.loginDateLessThanOrEqual = loginDateLessThanOrEqual;
		return this;
	}

	public UserOnlineQuery loginIP(String loginIP) {
		if (loginIP == null) {
			throw new RuntimeException("loginIP is null");
		}
		this.loginIP = loginIP;
		return this;
	}

	public UserOnlineQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public UserOnlineQuery searchWord(String searchWord) {
		if (searchWord == null) {
			throw new RuntimeException("searchWord is null");
		}
		this.searchWord = searchWord;
		return this;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setActorIds(List<String> actorIds) {
		this.actorIds = actorIds;
	}

	public void setLoginDateGreaterThanOrEqual(Date loginDateGreaterThanOrEqual) {
		this.loginDateGreaterThanOrEqual = loginDateGreaterThanOrEqual;
	}

	public void setLoginDateLessThanOrEqual(Date loginDateLessThanOrEqual) {
		this.loginDateLessThanOrEqual = loginDateLessThanOrEqual;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

}