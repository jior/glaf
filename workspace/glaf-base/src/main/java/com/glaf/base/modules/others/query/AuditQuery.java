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

package com.glaf.base.modules.others.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class AuditQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Long referId;
	protected Long referIdGreaterThanOrEqual;
	protected Long referIdLessThanOrEqual;
	protected List<Long> referIds;
	protected Integer referType;
	protected Integer referTypeGreaterThanOrEqual;
	protected Integer referTypeLessThanOrEqual;
	protected List<Integer> referTypes;
	protected Long deptId;
	protected Long deptIdGreaterThanOrEqual;
	protected Long deptIdLessThanOrEqual;
	protected List<Long> deptIds;
	protected String deptNameLike;
	protected String headshipLike;
	protected String leaderName;
	protected String leaderNameLike;
	protected Long leaderId;
	protected Long leaderIdGreaterThanOrEqual;
	protected Long leaderIdLessThanOrEqual;
	protected List<Long> leaderIds;
	protected String memoLike;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected Integer flag;
	protected Integer flagGreaterThanOrEqual;
	protected Integer flagLessThanOrEqual;

	public AuditQuery() {

	}

	public Long getReferId() {
		return referId;
	}

	public Long getReferIdGreaterThanOrEqual() {
		return referIdGreaterThanOrEqual;
	}

	public Long getReferIdLessThanOrEqual() {
		return referIdLessThanOrEqual;
	}

	public List<Long> getReferIds() {
		return referIds;
	}

	public Integer getReferType() {
		return referType;
	}

	public Integer getReferTypeGreaterThanOrEqual() {
		return referTypeGreaterThanOrEqual;
	}

	public Integer getReferTypeLessThanOrEqual() {
		return referTypeLessThanOrEqual;
	}

	public List<Integer> getReferTypes() {
		return referTypes;
	}

	public Long getDeptId() {
		return deptId;
	}

	public Long getDeptIdGreaterThanOrEqual() {
		return deptIdGreaterThanOrEqual;
	}

	public Long getDeptIdLessThanOrEqual() {
		return deptIdLessThanOrEqual;
	}

	public List<Long> getDeptIds() {
		return deptIds;
	}

	public String getDeptNameLike() {
		if (deptNameLike != null && deptNameLike.trim().length() > 0) {
			if (!deptNameLike.startsWith("%")) {
				deptNameLike = "%" + deptNameLike;
			}
			if (!deptNameLike.endsWith("%")) {
				deptNameLike = deptNameLike + "%";
			}
		}
		return deptNameLike;
	}

	public String getHeadshipLike() {
		if (headshipLike != null && headshipLike.trim().length() > 0) {
			if (!headshipLike.startsWith("%")) {
				headshipLike = "%" + headshipLike;
			}
			if (!headshipLike.endsWith("%")) {
				headshipLike = headshipLike + "%";
			}
		}
		return headshipLike;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public String getLeaderNameLike() {
		if (leaderNameLike != null && leaderNameLike.trim().length() > 0) {
			if (!leaderNameLike.startsWith("%")) {
				leaderNameLike = "%" + leaderNameLike;
			}
			if (!leaderNameLike.endsWith("%")) {
				leaderNameLike = leaderNameLike + "%";
			}
		}
		return leaderNameLike;
	}

	public Long getLeaderId() {
		return leaderId;
	}

	public Long getLeaderIdGreaterThanOrEqual() {
		return leaderIdGreaterThanOrEqual;
	}

	public Long getLeaderIdLessThanOrEqual() {
		return leaderIdLessThanOrEqual;
	}

	public List<Long> getLeaderIds() {
		return leaderIds;
	}

	public String getMemoLike() {
		if (memoLike != null && memoLike.trim().length() > 0) {
			if (!memoLike.startsWith("%")) {
				memoLike = "%" + memoLike;
			}
			if (!memoLike.endsWith("%")) {
				memoLike = memoLike + "%";
			}
		}
		return memoLike;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public Integer getFlag() {
		return flag;
	}

	public Integer getFlagGreaterThanOrEqual() {
		return flagGreaterThanOrEqual;
	}

	public Integer getFlagLessThanOrEqual() {
		return flagLessThanOrEqual;
	}

	public void setReferId(Long referId) {
		this.referId = referId;
	}

	public void setReferIdGreaterThanOrEqual(Long referIdGreaterThanOrEqual) {
		this.referIdGreaterThanOrEqual = referIdGreaterThanOrEqual;
	}

	public void setReferIdLessThanOrEqual(Long referIdLessThanOrEqual) {
		this.referIdLessThanOrEqual = referIdLessThanOrEqual;
	}

	public void setReferIds(List<Long> referIds) {
		this.referIds = referIds;
	}

	public void setReferType(Integer referType) {
		this.referType = referType;
	}

	public void setReferTypeGreaterThanOrEqual(
			Integer referTypeGreaterThanOrEqual) {
		this.referTypeGreaterThanOrEqual = referTypeGreaterThanOrEqual;
	}

	public void setReferTypeLessThanOrEqual(Integer referTypeLessThanOrEqual) {
		this.referTypeLessThanOrEqual = referTypeLessThanOrEqual;
	}

	public void setReferTypes(List<Integer> referTypes) {
		this.referTypes = referTypes;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public void setDeptIdGreaterThanOrEqual(Long deptIdGreaterThanOrEqual) {
		this.deptIdGreaterThanOrEqual = deptIdGreaterThanOrEqual;
	}

	public void setDeptIdLessThanOrEqual(Long deptIdLessThanOrEqual) {
		this.deptIdLessThanOrEqual = deptIdLessThanOrEqual;
	}

	public void setDeptIds(List<Long> deptIds) {
		this.deptIds = deptIds;
	}

	public void setDeptNameLike(String deptNameLike) {
		this.deptNameLike = deptNameLike;
	}

	public void setHeadshipLike(String headshipLike) {
		this.headshipLike = headshipLike;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public void setLeaderNameLike(String leaderNameLike) {
		this.leaderNameLike = leaderNameLike;
	}

	public void setLeaderId(Long leaderId) {
		this.leaderId = leaderId;
	}

	public void setLeaderIdGreaterThanOrEqual(Long leaderIdGreaterThanOrEqual) {
		this.leaderIdGreaterThanOrEqual = leaderIdGreaterThanOrEqual;
	}

	public void setLeaderIdLessThanOrEqual(Long leaderIdLessThanOrEqual) {
		this.leaderIdLessThanOrEqual = leaderIdLessThanOrEqual;
	}

	public void setLeaderIds(List<Long> leaderIds) {
		this.leaderIds = leaderIds;
	}

	public void setMemoLike(String memoLike) {
		this.memoLike = memoLike;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public void setFlagGreaterThanOrEqual(Integer flagGreaterThanOrEqual) {
		this.flagGreaterThanOrEqual = flagGreaterThanOrEqual;
	}

	public void setFlagLessThanOrEqual(Integer flagLessThanOrEqual) {
		this.flagLessThanOrEqual = flagLessThanOrEqual;
	}

	public AuditQuery referId(Long referId) {
		if (referId == null) {
			throw new RuntimeException("referId is null");
		}
		this.referId = referId;
		return this;
	}

	public AuditQuery referIdGreaterThanOrEqual(Long referIdGreaterThanOrEqual) {
		if (referIdGreaterThanOrEqual == null) {
			throw new RuntimeException("referId is null");
		}
		this.referIdGreaterThanOrEqual = referIdGreaterThanOrEqual;
		return this;
	}

	public AuditQuery referIdLessThanOrEqual(Long referIdLessThanOrEqual) {
		if (referIdLessThanOrEqual == null) {
			throw new RuntimeException("referId is null");
		}
		this.referIdLessThanOrEqual = referIdLessThanOrEqual;
		return this;
	}

	public AuditQuery referIds(List<Long> referIds) {
		if (referIds == null) {
			throw new RuntimeException("referIds is empty ");
		}
		this.referIds = referIds;
		return this;
	}

	public AuditQuery referType(Integer referType) {
		if (referType == null) {
			throw new RuntimeException("referType is null");
		}
		this.referType = referType;
		return this;
	}

	public AuditQuery referTypeGreaterThanOrEqual(
			Integer referTypeGreaterThanOrEqual) {
		if (referTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("referType is null");
		}
		this.referTypeGreaterThanOrEqual = referTypeGreaterThanOrEqual;
		return this;
	}

	public AuditQuery referTypeLessThanOrEqual(Integer referTypeLessThanOrEqual) {
		if (referTypeLessThanOrEqual == null) {
			throw new RuntimeException("referType is null");
		}
		this.referTypeLessThanOrEqual = referTypeLessThanOrEqual;
		return this;
	}

	public AuditQuery referTypes(List<Integer> referTypes) {
		if (referTypes == null) {
			throw new RuntimeException("referTypes is empty ");
		}
		this.referTypes = referTypes;
		return this;
	}

	public AuditQuery deptId(Long deptId) {
		if (deptId == null) {
			throw new RuntimeException("deptId is null");
		}
		this.deptId = deptId;
		return this;
	}

	public AuditQuery deptIdGreaterThanOrEqual(Long deptIdGreaterThanOrEqual) {
		if (deptIdGreaterThanOrEqual == null) {
			throw new RuntimeException("deptId is null");
		}
		this.deptIdGreaterThanOrEqual = deptIdGreaterThanOrEqual;
		return this;
	}

	public AuditQuery deptIdLessThanOrEqual(Long deptIdLessThanOrEqual) {
		if (deptIdLessThanOrEqual == null) {
			throw new RuntimeException("deptId is null");
		}
		this.deptIdLessThanOrEqual = deptIdLessThanOrEqual;
		return this;
	}

	public AuditQuery deptIds(List<Long> deptIds) {
		if (deptIds == null) {
			throw new RuntimeException("deptIds is empty ");
		}
		this.deptIds = deptIds;
		return this;
	}

	public AuditQuery deptNameLike(String deptNameLike) {
		if (deptNameLike == null) {
			throw new RuntimeException("deptName is null");
		}
		this.deptNameLike = deptNameLike;
		return this;
	}

	public AuditQuery headshipLike(String headshipLike) {
		if (headshipLike == null) {
			throw new RuntimeException("headship is null");
		}
		this.headshipLike = headshipLike;
		return this;
	}

	public AuditQuery leaderName(String leaderName) {
		if (leaderName == null) {
			throw new RuntimeException("leaderName is null");
		}
		this.leaderName = leaderName;
		return this;
	}

	public AuditQuery leaderNameLike(String leaderNameLike) {
		if (leaderNameLike == null) {
			throw new RuntimeException("leaderName is null");
		}
		this.leaderNameLike = leaderNameLike;
		return this;
	}

	public AuditQuery leaderId(Long leaderId) {
		if (leaderId == null) {
			throw new RuntimeException("leaderId is null");
		}
		this.leaderId = leaderId;
		return this;
	}

	public AuditQuery leaderIdGreaterThanOrEqual(Long leaderIdGreaterThanOrEqual) {
		if (leaderIdGreaterThanOrEqual == null) {
			throw new RuntimeException("leaderId is null");
		}
		this.leaderIdGreaterThanOrEqual = leaderIdGreaterThanOrEqual;
		return this;
	}

	public AuditQuery leaderIdLessThanOrEqual(Long leaderIdLessThanOrEqual) {
		if (leaderIdLessThanOrEqual == null) {
			throw new RuntimeException("leaderId is null");
		}
		this.leaderIdLessThanOrEqual = leaderIdLessThanOrEqual;
		return this;
	}

	public AuditQuery leaderIds(List<Long> leaderIds) {
		if (leaderIds == null) {
			throw new RuntimeException("leaderIds is empty ");
		}
		this.leaderIds = leaderIds;
		return this;
	}

	public AuditQuery memoLike(String memoLike) {
		if (memoLike == null) {
			throw new RuntimeException("memo is null");
		}
		this.memoLike = memoLike;
		return this;
	}

	public AuditQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public AuditQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public AuditQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public AuditQuery flag(Integer flag) {
		if (flag == null) {
			throw new RuntimeException("flag is null");
		}
		this.flag = flag;
		return this;
	}

	public AuditQuery flagGreaterThanOrEqual(Integer flagGreaterThanOrEqual) {
		if (flagGreaterThanOrEqual == null) {
			throw new RuntimeException("flag is null");
		}
		this.flagGreaterThanOrEqual = flagGreaterThanOrEqual;
		return this;
	}

	public AuditQuery flagLessThanOrEqual(Integer flagLessThanOrEqual) {
		if (flagLessThanOrEqual == null) {
			throw new RuntimeException("flag is null");
		}
		this.flagLessThanOrEqual = flagLessThanOrEqual;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("referId".equals(sortColumn)) {
				orderBy = "E.REFERID" + a_x;
			}

			if ("referType".equals(sortColumn)) {
				orderBy = "E.REFERTYPE" + a_x;
			}

			if ("deptId".equals(sortColumn)) {
				orderBy = "E.DEPTID" + a_x;
			}

			if ("deptName".equals(sortColumn)) {
				orderBy = "E.DEPTNAME" + a_x;
			}

			if ("headship".equals(sortColumn)) {
				orderBy = "E.HEADSHIP" + a_x;
			}

			if ("leaderName".equals(sortColumn)) {
				orderBy = "E.LEADERNAME" + a_x;
			}

			if ("leaderId".equals(sortColumn)) {
				orderBy = "E.LEADERID" + a_x;
			}

			if ("memo".equals(sortColumn)) {
				orderBy = "E.MEMO" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE" + a_x;
			}

			if ("flag".equals(sortColumn)) {
				orderBy = "E.FLAG" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("referId", "REFERID");
		addColumn("referType", "REFERTYPE");
		addColumn("deptId", "DEPTID");
		addColumn("deptName", "DEPTNAME");
		addColumn("headship", "HEADSHIP");
		addColumn("leaderName", "LEADERNAME");
		addColumn("leaderId", "LEADERID");
		addColumn("memo", "MEMO");
		addColumn("createDate", "CREATEDATE");
		addColumn("flag", "FLAG");
	}

}