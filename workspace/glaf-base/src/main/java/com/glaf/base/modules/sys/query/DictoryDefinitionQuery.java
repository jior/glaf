package com.glaf.base.modules.sys.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DictoryDefinitionQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String columnName;
	protected String columnNameLike;
	protected List<String> columnNames;
	protected Integer length;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected Long nodeId;
	protected List<Long> nodeIds;
	protected Integer sortGreaterThanOrEqual;
	protected Integer sortLessThanOrEqual;
	protected String target;
	protected String targetLike;
	protected List<String> targets;
	protected String titleLike;
	protected String type;
	protected List<String> types;

	public DictoryDefinitionQuery() {

	}

	public DictoryDefinitionQuery columnName(String columnName) {
		if (columnName == null) {
			throw new RuntimeException("columnName is null");
		}
		this.columnName = columnName;
		return this;
	}

	public DictoryDefinitionQuery columnNameLike(String columnNameLike) {
		if (columnNameLike == null) {
			throw new RuntimeException("columnName is null");
		}
		this.columnNameLike = columnNameLike;
		return this;
	}

	public DictoryDefinitionQuery columnNames(List<String> columnNames) {
		if (columnNames == null) {
			throw new RuntimeException("columnNames is empty ");
		}
		this.columnNames = columnNames;
		return this;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getColumnNameLike() {
		if (columnNameLike != null && columnNameLike.trim().length() > 0) {
			if (!columnNameLike.startsWith("%")) {
				columnNameLike = "%" + columnNameLike;
			}
			if (!columnNameLike.endsWith("%")) {
				columnNameLike = columnNameLike + "%";
			}
		}
		return columnNameLike;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public Integer getLength() {
		return length;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
		if (nameLike != null && nameLike.trim().length() > 0) {
			if (!nameLike.startsWith("%")) {
				nameLike = "%" + nameLike;
			}
			if (!nameLike.endsWith("%")) {
				nameLike = nameLike + "%";
			}
		}
		return nameLike;
	}

	public List<String> getNames() {
		return names;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public List<Long> getNodeIds() {
		return nodeIds;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("nodeId".equals(sortColumn)) {
				orderBy = "E.NODEID" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME" + a_x;
			}

			if ("columnName".equals(sortColumn)) {
				orderBy = "E.COLUMNNAME" + a_x;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE" + a_x;
			}

			if ("length".equals(sortColumn)) {
				orderBy = "E.LENGTH" + a_x;
			}

			if ("sort".equals(sortColumn)) {
				orderBy = "E.SORT" + a_x;
			}

			if ("required".equals(sortColumn)) {
				orderBy = "E.REQUIRED" + a_x;
			}

			if ("target".equals(sortColumn)) {
				orderBy = "E.TARGET" + a_x;
			}

		}
		return orderBy;
	}

	public Integer getSortGreaterThanOrEqual() {
		return sortGreaterThanOrEqual;
	}

	public Integer getSortLessThanOrEqual() {
		return sortLessThanOrEqual;
	}

	public String getTarget() {
		return target;
	}

	public String getTargetLike() {
		if (targetLike != null && targetLike.trim().length() > 0) {
			if (!targetLike.startsWith("%")) {
				targetLike = "%" + targetLike;
			}
			if (!targetLike.endsWith("%")) {
				targetLike = targetLike + "%";
			}
		}
		return targetLike;
	}

	public List<String> getTargets() {
		return targets;
	}

	public String getTitleLike() {
		if (titleLike != null && titleLike.trim().length() > 0) {
			if (!titleLike.startsWith("%")) {
				titleLike = "%" + titleLike;
			}
			if (!titleLike.endsWith("%")) {
				titleLike = titleLike + "%";
			}
		}
		return titleLike;
	}

	public String getType() {
		return type;
	}

	public List<String> getTypes() {
		return types;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("nodeId", "NODEID");
		addColumn("name", "NAME");
		addColumn("columnName", "COLUMNNAME");
		addColumn("title", "TITLE");
		addColumn("type", "TYPE");
		addColumn("length", "LENGTH");
		addColumn("sort", "SORT");
		addColumn("required", "REQUIRED");
		addColumn("target", "TARGET");
	}

	public DictoryDefinitionQuery length(Integer length) {
		if (length == null) {
			throw new RuntimeException("length is null");
		}
		this.length = length;
		return this;
	}

	public DictoryDefinitionQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public DictoryDefinitionQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public DictoryDefinitionQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public DictoryDefinitionQuery nodeId(Long nodeId) {
		if (nodeId == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeId = nodeId;
		return this;
	}

	public DictoryDefinitionQuery nodeIds(List<Long> nodeIds) {
		if (nodeIds == null) {
			throw new RuntimeException("nodeIds is empty ");
		}
		this.nodeIds = nodeIds;
		return this;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setColumnNameLike(String columnNameLike) {
		this.columnNameLike = columnNameLike;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodeIds(List<Long> nodeIds) {
		this.nodeIds = nodeIds;
	}

	public void setSortGreaterThanOrEqual(Integer sortGreaterThanOrEqual) {
		this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
	}

	public void setSortLessThanOrEqual(Integer sortLessThanOrEqual) {
		this.sortLessThanOrEqual = sortLessThanOrEqual;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setTargetLike(String targetLike) {
		this.targetLike = targetLike;
	}

	public void setTargets(List<String> targets) {
		this.targets = targets;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public DictoryDefinitionQuery sortGreaterThanOrEqual(
			Integer sortGreaterThanOrEqual) {
		if (sortGreaterThanOrEqual == null) {
			throw new RuntimeException("sort is null");
		}
		this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
		return this;
	}

	public DictoryDefinitionQuery sortLessThanOrEqual(
			Integer sortLessThanOrEqual) {
		if (sortLessThanOrEqual == null) {
			throw new RuntimeException("sort is null");
		}
		this.sortLessThanOrEqual = sortLessThanOrEqual;
		return this;
	}

	public DictoryDefinitionQuery target(String target) {
		if (target == null) {
			throw new RuntimeException("target is null");
		}
		this.target = target;
		return this;
	}

	public DictoryDefinitionQuery targetLike(String targetLike) {
		if (targetLike == null) {
			throw new RuntimeException("target is null");
		}
		this.targetLike = targetLike;
		return this;
	}

	public DictoryDefinitionQuery targets(List<String> targets) {
		if (targets == null) {
			throw new RuntimeException("targets is empty ");
		}
		this.targets = targets;
		return this;
	}

	public DictoryDefinitionQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public DictoryDefinitionQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public DictoryDefinitionQuery types(List<String> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

}