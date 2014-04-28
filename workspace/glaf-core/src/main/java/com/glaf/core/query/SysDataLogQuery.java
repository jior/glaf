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
package com.glaf.core.query;

import java.util.*;

import com.glaf.core.query.DataQuery;

public class SysDataLogQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Long accountId;
	protected List<Long> accountIds;
	protected String openId;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;
	protected Integer flag;
	protected String suffix;
	protected String ip;
	protected String ipLike;
	protected String moduleId;
	protected String operate;
	protected String operateLike;
	protected List<String> operates;

	public SysDataLogQuery() {

	}

	public SysDataLogQuery createTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public SysDataLogQuery createTimeLessThanOrEqual(
			Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public SysDataLogQuery flag(Integer flag) {
		if (flag == null) {
			throw new RuntimeException("flag is null");
		}
		this.flag = flag;
		return this;
	}

	public Long getAccountId() {
		return accountId;
	}

	public List<Long> getAccountIds() {
		return accountIds;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public Integer getFlag() {
		return flag;
	}

	public String getIp() {
		return ip;
	}

	public String getIpLike() {
		if (ipLike != null && ipLike.trim().length() > 0) {
			if (!ipLike.startsWith("%")) {
				ipLike = "%" + ipLike;
			}
			if (!ipLike.endsWith("%")) {
				ipLike = ipLike + "%";
			}
		}
		return ipLike;
	}

	public String getModuleId() {
		return moduleId;
	}

	public String getOpenId() {
		return openId;
	}

	public String getOperate() {
		return operate;
	}

	public String getOperateLike() {
		if (operateLike != null && operateLike.trim().length() > 0) {
			if (!operateLike.startsWith("%")) {
				operateLike = "%" + operateLike;
			}
			if (!operateLike.endsWith("%")) {
				operateLike = operateLike + "%";
			}
		}
		return operateLike;
	}

	public List<String> getOperates() {
		return operates;
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

			if ("ip".equals(sortColumn)) {
				orderBy = "E.IP_" + a_x;
			}

			if ("createTime".equals(sortColumn)) {
				orderBy = "E.CREATETIME_" + a_x;
			}

			if ("operate".equals(sortColumn)) {
				orderBy = "E.OPERATE_" + a_x;
			}

			if ("flag".equals(sortColumn)) {
				orderBy = "E.FLAG_" + a_x;
			}

		}
		return orderBy;
	}

	public String getSuffix() {
		return suffix;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("actorId", "ACTORID_");
		addColumn("accountId", "ACCOUNTID_");
		addColumn("ip", "IP_");
		addColumn("createTime", "CREATETIME_");
		addColumn("operate", "OPERATE_");
		addColumn("flag", "FLAG_");
	}

	public SysDataLogQuery ip(String ip) {
		if (ip == null) {
			throw new RuntimeException("ip is null");
		}
		this.ip = ip;
		return this;
	}

	public SysDataLogQuery ipLike(String ipLike) {
		if (ipLike == null) {
			throw new RuntimeException("ip is null");
		}
		this.ipLike = ipLike;
		return this;
	}

	public SysDataLogQuery moduleId(String moduleId) {
		if (moduleId == null) {
			throw new RuntimeException("moduleId is null");
		}
		this.moduleId = moduleId;
		return this;
	}

	public SysDataLogQuery operate(String operate) {
		if (operate == null) {
			throw new RuntimeException("operate is null");
		}
		this.operate = operate;
		return this;
	}

	public SysDataLogQuery operateLike(String operateLike) {
		if (operateLike == null) {
			throw new RuntimeException("operate is null");
		}
		this.operateLike = operateLike;
		return this;
	}

	public SysDataLogQuery operates(List<String> operates) {
		if (operates == null) {
			throw new RuntimeException("operates is empty ");
		}
		this.operates = operates;
		return this;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public void setAccountIds(List<Long> accountIds) {
		this.accountIds = accountIds;
	}

	public void setCreateTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setIpLike(String ipLike) {
		this.ipLike = ipLike;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public void setOperateLike(String operateLike) {
		this.operateLike = operateLike;
	}

	public void setOperates(List<String> operates) {
		this.operates = operates;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

}