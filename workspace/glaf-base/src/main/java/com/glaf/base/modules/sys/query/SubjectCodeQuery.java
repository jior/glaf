package com.glaf.base.modules.sys.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SubjectCodeQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Long parent;
	protected Long parentGreaterThanOrEqual;
	protected Long parentLessThanOrEqual;
	protected List<Long> parents;
	protected List<Long> rowIds;
	protected Integer sortGreaterThanOrEqual;
	protected Integer sortLessThanOrEqual;
	protected String subjectCode;
	protected String subjectCodeLike;
	protected List<String> subjectCodes;
	protected String subjectNameLike;

	public SubjectCodeQuery() {

	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("parent".equals(sortColumn)) {
				orderBy = "E.PARENT" + a_x;
			}

			if ("subjectCode".equals(sortColumn)) {
				orderBy = "E.SUBJECTCODE" + a_x;
			}

			if ("subjectName".equals(sortColumn)) {
				orderBy = "E.SUBJECTNAME" + a_x;
			}

			if ("sort".equals(sortColumn)) {
				orderBy = "E.SORT" + a_x;
			}

		}
		return orderBy;
	}

	public Long getParent() {
		return parent;
	}

	public Long getParentGreaterThanOrEqual() {
		return parentGreaterThanOrEqual;
	}

	public Long getParentLessThanOrEqual() {
		return parentLessThanOrEqual;
	}

	public List<Long> getParents() {
		return parents;
	}

	public Integer getSortGreaterThanOrEqual() {
		return sortGreaterThanOrEqual;
	}

	public Integer getSortLessThanOrEqual() {
		return sortLessThanOrEqual;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public String getSubjectCodeLike() {
		if (subjectCodeLike != null && subjectCodeLike.trim().length() > 0) {
			if (!subjectCodeLike.startsWith("%")) {
				subjectCodeLike = "%" + subjectCodeLike;
			}
			if (!subjectCodeLike.endsWith("%")) {
				subjectCodeLike = subjectCodeLike + "%";
			}
		}
		return subjectCodeLike;
	}

	public List<String> getSubjectCodes() {
		return subjectCodes;
	}

	public String getSubjectNameLike() {
		if (subjectNameLike != null && subjectNameLike.trim().length() > 0) {
			if (!subjectNameLike.startsWith("%")) {
				subjectNameLike = "%" + subjectNameLike;
			}
			if (!subjectNameLike.endsWith("%")) {
				subjectNameLike = subjectNameLike + "%";
			}
		}
		return subjectNameLike;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("parent", "PARENT");
		addColumn("subjectCode", "SUBJECTCODE");
		addColumn("subjectName", "SUBJECTNAME");
		addColumn("sort", "SORT");
	}

	public SubjectCodeQuery parent(Long parent) {
		if (parent == null) {
			throw new RuntimeException("parent is null");
		}
		this.parent = parent;
		return this;
	}

	public SubjectCodeQuery parentGreaterThanOrEqual(
			Long parentGreaterThanOrEqual) {
		if (parentGreaterThanOrEqual == null) {
			throw new RuntimeException("parent is null");
		}
		this.parentGreaterThanOrEqual = parentGreaterThanOrEqual;
		return this;
	}

	public SubjectCodeQuery parentLessThanOrEqual(Long parentLessThanOrEqual) {
		if (parentLessThanOrEqual == null) {
			throw new RuntimeException("parent is null");
		}
		this.parentLessThanOrEqual = parentLessThanOrEqual;
		return this;
	}

	public SubjectCodeQuery parents(List<Long> parents) {
		if (parents == null) {
			throw new RuntimeException("parents is empty ");
		}
		this.parents = parents;
		return this;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public void setParentGreaterThanOrEqual(Long parentGreaterThanOrEqual) {
		this.parentGreaterThanOrEqual = parentGreaterThanOrEqual;
	}

	public void setParentLessThanOrEqual(Long parentLessThanOrEqual) {
		this.parentLessThanOrEqual = parentLessThanOrEqual;
	}

	public void setParents(List<Long> parents) {
		this.parents = parents;
	}

	public void setSortGreaterThanOrEqual(Integer sortGreaterThanOrEqual) {
		this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
	}

	public void setSortLessThanOrEqual(Integer sortLessThanOrEqual) {
		this.sortLessThanOrEqual = sortLessThanOrEqual;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public void setSubjectCodeLike(String subjectCodeLike) {
		this.subjectCodeLike = subjectCodeLike;
	}

	public void setSubjectCodes(List<String> subjectCodes) {
		this.subjectCodes = subjectCodes;
	}

	public void setSubjectNameLike(String subjectNameLike) {
		this.subjectNameLike = subjectNameLike;
	}

	public SubjectCodeQuery sortGreaterThanOrEqual(
			Integer sortGreaterThanOrEqual) {
		if (sortGreaterThanOrEqual == null) {
			throw new RuntimeException("sort is null");
		}
		this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
		return this;
	}

	public SubjectCodeQuery sortLessThanOrEqual(Integer sortLessThanOrEqual) {
		if (sortLessThanOrEqual == null) {
			throw new RuntimeException("sort is null");
		}
		this.sortLessThanOrEqual = sortLessThanOrEqual;
		return this;
	}

	public SubjectCodeQuery subjectCode(String subjectCode) {
		if (subjectCode == null) {
			throw new RuntimeException("subjectCode is null");
		}
		this.subjectCode = subjectCode;
		return this;
	}

	public SubjectCodeQuery subjectCodeLike(String subjectCodeLike) {
		if (subjectCodeLike == null) {
			throw new RuntimeException("subjectCode is null");
		}
		this.subjectCodeLike = subjectCodeLike;
		return this;
	}

	public SubjectCodeQuery subjectCodes(List<String> subjectCodes) {
		if (subjectCodes == null) {
			throw new RuntimeException("subjectCodes is empty ");
		}
		this.subjectCodes = subjectCodes;
		return this;
	}

	public SubjectCodeQuery subjectNameLike(String subjectNameLike) {
		if (subjectNameLike == null) {
			throw new RuntimeException("subjectName is null");
		}
		this.subjectNameLike = subjectNameLike;
		return this;
	}

}