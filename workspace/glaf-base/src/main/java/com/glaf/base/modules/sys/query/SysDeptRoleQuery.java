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

package com.glaf.base.modules.sys.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SysDeptRoleQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> rowIds;
	protected Integer grade;
	protected Integer gradeGreaterThanOrEqual;
	protected Integer gradeLessThanOrEqual;
	protected List<Integer> grades;
	protected String code;
	protected String codeLike;
	protected List<String> codes;
	protected Integer sort;
	protected Integer sortGreaterThanOrEqual;
	protected Integer sortGreaterThan;
	protected Integer sortLessThanOrEqual;
	protected Integer sortLessThan;
	protected List<Integer> sorts;
	protected Long sysRoleId;
	protected Long sysRoleIdGreaterThanOrEqual;
	protected Long sysRoleIdLessThanOrEqual;
	protected List<Long> sysRoleIds;
	protected Long deptId;
	protected Long deptIdGreaterThanOrEqual;
	protected Long deptIdLessThanOrEqual;
	protected List<Long> deptIds;

	public SysDeptRoleQuery() {

	}

	public Integer getSortGreaterThan() {
		return sortGreaterThan;
	}

	public void setSortGreaterThan(Integer sortGreaterThan) {
		this.sortGreaterThan = sortGreaterThan;
	}

	public Integer getSortLessThan() {
		return sortLessThan;
	}

	public void setSortLessThan(Integer sortLessThan) {
		this.sortLessThan = sortLessThan;
	}

	public Integer getGrade() {
		return grade;
	}

	public Integer getGradeGreaterThanOrEqual() {
		return gradeGreaterThanOrEqual;
	}

	public Integer getGradeLessThanOrEqual() {
		return gradeLessThanOrEqual;
	}

	public List<Integer> getGrades() {
		return grades;
	}

	public String getCode() {
		return code;
	}

	public String getCodeLike() {
		if (codeLike != null && codeLike.trim().length() > 0) {
			if (!codeLike.startsWith("%")) {
				codeLike = "%" + codeLike;
			}
			if (!codeLike.endsWith("%")) {
				codeLike = codeLike + "%";
			}
		}
		return codeLike;
	}

	public List<String> getCodes() {
		return codes;
	}

	public Integer getSort() {
		return sort;
	}

	public Integer getSortGreaterThanOrEqual() {
		return sortGreaterThanOrEqual;
	}

	public Integer getSortLessThanOrEqual() {
		return sortLessThanOrEqual;
	}

	public List<Integer> getSorts() {
		return sorts;
	}

	public Long getSysRoleId() {
		return sysRoleId;
	}

	public Long getSysRoleIdGreaterThanOrEqual() {
		return sysRoleIdGreaterThanOrEqual;
	}

	public Long getSysRoleIdLessThanOrEqual() {
		return sysRoleIdLessThanOrEqual;
	}

	public List<Long> getSysRoleIds() {
		return sysRoleIds;
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

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public void setGradeGreaterThanOrEqual(Integer gradeGreaterThanOrEqual) {
		this.gradeGreaterThanOrEqual = gradeGreaterThanOrEqual;
	}

	public void setGradeLessThanOrEqual(Integer gradeLessThanOrEqual) {
		this.gradeLessThanOrEqual = gradeLessThanOrEqual;
	}

	public void setGrades(List<Integer> grades) {
		this.grades = grades;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setSortGreaterThanOrEqual(Integer sortGreaterThanOrEqual) {
		this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
	}

	public void setSortLessThanOrEqual(Integer sortLessThanOrEqual) {
		this.sortLessThanOrEqual = sortLessThanOrEqual;
	}

	public void setSorts(List<Integer> sorts) {
		this.sorts = sorts;
	}

	public void setSysRoleId(Long sysRoleId) {
		this.sysRoleId = sysRoleId;
	}

	public void setSysRoleIdGreaterThanOrEqual(Long sysRoleIdGreaterThanOrEqual) {
		this.sysRoleIdGreaterThanOrEqual = sysRoleIdGreaterThanOrEqual;
	}

	public void setSysRoleIdLessThanOrEqual(Long sysRoleIdLessThanOrEqual) {
		this.sysRoleIdLessThanOrEqual = sysRoleIdLessThanOrEqual;
	}

	public void setSysRoleIds(List<Long> sysRoleIds) {
		this.sysRoleIds = sysRoleIds;
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

	public SysDeptRoleQuery grade(Integer grade) {
		if (grade == null) {
			throw new RuntimeException("grade is null");
		}
		this.grade = grade;
		return this;
	}

	public SysDeptRoleQuery gradeGreaterThanOrEqual(
			Integer gradeGreaterThanOrEqual) {
		if (gradeGreaterThanOrEqual == null) {
			throw new RuntimeException("grade is null");
		}
		this.gradeGreaterThanOrEqual = gradeGreaterThanOrEqual;
		return this;
	}

	public SysDeptRoleQuery gradeLessThanOrEqual(Integer gradeLessThanOrEqual) {
		if (gradeLessThanOrEqual == null) {
			throw new RuntimeException("grade is null");
		}
		this.gradeLessThanOrEqual = gradeLessThanOrEqual;
		return this;
	}

	public SysDeptRoleQuery grades(List<Integer> grades) {
		if (grades == null) {
			throw new RuntimeException("grades is empty ");
		}
		this.grades = grades;
		return this;
	}

	public SysDeptRoleQuery code(String code) {
		if (code == null) {
			throw new RuntimeException("code is null");
		}
		this.code = code;
		return this;
	}

	public SysDeptRoleQuery codeLike(String codeLike) {
		if (codeLike == null) {
			throw new RuntimeException("code is null");
		}
		this.codeLike = codeLike;
		return this;
	}

	public SysDeptRoleQuery codes(List<String> codes) {
		if (codes == null) {
			throw new RuntimeException("codes is empty ");
		}
		this.codes = codes;
		return this;
	}

	public SysDeptRoleQuery sort(Integer sort) {
		if (sort == null) {
			throw new RuntimeException("sort is null");
		}
		this.sort = sort;
		return this;
	}

	public SysDeptRoleQuery sortGreaterThanOrEqual(
			Integer sortGreaterThanOrEqual) {
		if (sortGreaterThanOrEqual == null) {
			throw new RuntimeException("sort is null");
		}
		this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
		return this;
	}

	public SysDeptRoleQuery sortLessThanOrEqual(Integer sortLessThanOrEqual) {
		if (sortLessThanOrEqual == null) {
			throw new RuntimeException("sort is null");
		}
		this.sortLessThanOrEqual = sortLessThanOrEqual;
		return this;
	}

	public SysDeptRoleQuery sorts(List<Integer> sorts) {
		if (sorts == null) {
			throw new RuntimeException("sorts is empty ");
		}
		this.sorts = sorts;
		return this;
	}

	public SysDeptRoleQuery sysRoleId(Long sysRoleId) {
		if (sysRoleId == null) {
			throw new RuntimeException("sysRoleId is null");
		}
		this.sysRoleId = sysRoleId;
		return this;
	}

	public SysDeptRoleQuery sysRoleIdGreaterThanOrEqual(
			Long sysRoleIdGreaterThanOrEqual) {
		if (sysRoleIdGreaterThanOrEqual == null) {
			throw new RuntimeException("sysRoleId is null");
		}
		this.sysRoleIdGreaterThanOrEqual = sysRoleIdGreaterThanOrEqual;
		return this;
	}

	public SysDeptRoleQuery sysRoleIdLessThanOrEqual(
			Long sysRoleIdLessThanOrEqual) {
		if (sysRoleIdLessThanOrEqual == null) {
			throw new RuntimeException("sysRoleId is null");
		}
		this.sysRoleIdLessThanOrEqual = sysRoleIdLessThanOrEqual;
		return this;
	}

	public SysDeptRoleQuery sysRoleIds(List<Long> sysRoleIds) {
		if (sysRoleIds == null) {
			throw new RuntimeException("sysRoleIds is empty ");
		}
		this.sysRoleIds = sysRoleIds;
		return this;
	}

	public SysDeptRoleQuery deptId(Long deptId) {
		if (deptId == null) {
			throw new RuntimeException("deptId is null");
		}
		this.deptId = deptId;
		return this;
	}

	public SysDeptRoleQuery deptIdGreaterThanOrEqual(
			Long deptIdGreaterThanOrEqual) {
		if (deptIdGreaterThanOrEqual == null) {
			throw new RuntimeException("deptId is null");
		}
		this.deptIdGreaterThanOrEqual = deptIdGreaterThanOrEqual;
		return this;
	}

	public SysDeptRoleQuery deptIdLessThanOrEqual(Long deptIdLessThanOrEqual) {
		if (deptIdLessThanOrEqual == null) {
			throw new RuntimeException("deptId is null");
		}
		this.deptIdLessThanOrEqual = deptIdLessThanOrEqual;
		return this;
	}

	public SysDeptRoleQuery deptIds(List<Long> deptIds) {
		if (deptIds == null) {
			throw new RuntimeException("deptIds is empty ");
		}
		this.deptIds = deptIds;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("grade".equals(sortColumn)) {
				orderBy = "E.GRADE" + a_x;
			}

			if ("code".equals(sortColumn)) {
				orderBy = "E.CODE" + a_x;
			}

			if ("sort".equals(sortColumn)) {
				orderBy = "E.SORT" + a_x;
			}

			if ("sysRoleId".equals(sortColumn)) {
				orderBy = "E.SYSROLEID" + a_x;
			}

			if ("deptId".equals(sortColumn)) {
				orderBy = "E.DEPTID" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("grade", "GRADE");
		addColumn("code", "CODE");
		addColumn("sort", "SORT");
		addColumn("sysRoleId", "SYSROLEID");
		addColumn("deptId", "DEPTID");
	}

}