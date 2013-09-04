package com.glaf.dts.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DataTransferQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected String tableName;
	protected List<String> tableNames;
	protected String parentTableName;
	protected String titleLike;
	protected String englishTitleLike;
	protected String filePrefixLike;
	protected String parseType;
	protected String parseClass;
	protected String aggregationKeysLike;
	protected String temporaryFlag;
	protected String descriptionLike;
	protected String type;
	protected String typeLike;
	protected List<String> types;
	protected Long nodeId;
	protected List<Long> nodeIds;
	protected List<Integer> lockeds;
	protected String systemFlag;
	protected String systemFlagLike;
	protected List<String> systemFlags;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;
	protected String createByLike;
	protected List<String> createBys;

	public DataTransferQuery() {

	}

	public DataTransferQuery aggregationKeysLike(String aggregationKeysLike) {
		if (aggregationKeysLike == null) {
			throw new RuntimeException("aggregationKeys is null");
		}
		this.aggregationKeysLike = aggregationKeysLike;
		return this;
	}

	public DataTransferQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public DataTransferQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public DataTransferQuery createTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public DataTransferQuery createTimeLessThanOrEqual(
			Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public DataTransferQuery descriptionLike(String descriptionLike) {
		if (descriptionLike == null) {
			throw new RuntimeException("description is null");
		}
		this.descriptionLike = descriptionLike;
		return this;
	}

	public DataTransferQuery englishTitleLike(String englishTitleLike) {
		if (englishTitleLike == null) {
			throw new RuntimeException("englishTitle is null");
		}
		this.englishTitleLike = englishTitleLike;
		return this;
	}

	public DataTransferQuery filePrefixLike(String filePrefixLike) {
		if (filePrefixLike == null) {
			throw new RuntimeException("filePrefix is null");
		}
		this.filePrefixLike = filePrefixLike;
		return this;
	}

	public String getAggregationKeysLike() {
		if (aggregationKeysLike != null
				&& aggregationKeysLike.trim().length() > 0) {
			if (!aggregationKeysLike.startsWith("%")) {
				aggregationKeysLike = "%" + aggregationKeysLike;
			}
			if (!aggregationKeysLike.endsWith("%")) {
				aggregationKeysLike = aggregationKeysLike + "%";
			}
		}
		return aggregationKeysLike;
	}

	public String getCreateByLike() {
		if (createByLike != null && createByLike.trim().length() > 0) {
			if (!createByLike.startsWith("%")) {
				createByLike = "%" + createByLike;
			}
			if (!createByLike.endsWith("%")) {
				createByLike = createByLike + "%";
			}
		}
		return createByLike;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public String getDescriptionLike() {
		if (descriptionLike != null && descriptionLike.trim().length() > 0) {
			if (!descriptionLike.startsWith("%")) {
				descriptionLike = "%" + descriptionLike;
			}
			if (!descriptionLike.endsWith("%")) {
				descriptionLike = descriptionLike + "%";
			}
		}
		return descriptionLike;
	}

	public String getEnglishTitleLike() {
		if (englishTitleLike != null && englishTitleLike.trim().length() > 0) {
			if (!englishTitleLike.startsWith("%")) {
				englishTitleLike = "%" + englishTitleLike;
			}
			if (!englishTitleLike.endsWith("%")) {
				englishTitleLike = englishTitleLike + "%";
			}
		}
		return englishTitleLike;
	}

	public String getFilePrefixLike() {
		if (filePrefixLike != null && filePrefixLike.trim().length() > 0) {
			if (!filePrefixLike.startsWith("%")) {
				filePrefixLike = "%" + filePrefixLike;
			}
			if (!filePrefixLike.endsWith("%")) {
				filePrefixLike = filePrefixLike + "%";
			}
		}
		return filePrefixLike;
	}

	public List<Integer> getLockeds() {
		return lockeds;
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

			if ("tableName".equals(sortColumn)) {
				orderBy = "E.TABLENAME_" + a_x;
			}

			if ("parentTableName".equals(sortColumn)) {
				orderBy = "E.PARENTTABLENAME_" + a_x;
			}

			if ("packageName".equals(sortColumn)) {
				orderBy = "E.PACKAGENAME_" + a_x;
			}

			if ("entityName".equals(sortColumn)) {
				orderBy = "E.ENTITYNAME_" + a_x;
			}

			if ("className".equals(sortColumn)) {
				orderBy = "E.CLASSNAME_" + a_x;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("englishTitle".equals(sortColumn)) {
				orderBy = "E.ENGLISHTITLE_" + a_x;
			}

			if ("primaryKey".equals(sortColumn)) {
				orderBy = "E.PRIMARYKEY_" + a_x;
			}

			if ("filePrefix".equals(sortColumn)) {
				orderBy = "E.FILEPREFIX_" + a_x;
			}

			if ("parseType".equals(sortColumn)) {
				orderBy = "E.PARSETYPE_" + a_x;
			}

			if ("parseClass".equals(sortColumn)) {
				orderBy = "E.PARSECLASS_" + a_x;
			}

			if ("split".equals(sortColumn)) {
				orderBy = "E.SPLIT_" + a_x;
			}

			if ("batchSize".equals(sortColumn)) {
				orderBy = "E.BATCHSIZE_" + a_x;
			}

			if ("insertOnly".equals(sortColumn)) {
				orderBy = "E.INSERTONLY_" + a_x;
			}

			if ("startRow".equals(sortColumn)) {
				orderBy = "E.STARTROW_" + a_x;
			}

			if ("stopWord".equals(sortColumn)) {
				orderBy = "E.STOPWORD_" + a_x;
			}

			if ("stopSkipRow".equals(sortColumn)) {
				orderBy = "E.STOPSKIPROW_" + a_x;
			}

			if ("aggregationKeys".equals(sortColumn)) {
				orderBy = "E.AGGREGATIONKEYS_" + a_x;
			}

			if ("queryIds".equals(sortColumn)) {
				orderBy = "E.QUERYIDS_" + a_x;
			}

			if ("temporaryFlag".equals(sortColumn)) {
				orderBy = "E.TEMPORARYFLAG_" + a_x;
			}

			if ("deleteFetch".equals(sortColumn)) {
				orderBy = "E.DELETEFETCH_" + a_x;
			}

			if ("description".equals(sortColumn)) {
				orderBy = "E.DESCRIPTION_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("nodeId".equals(sortColumn)) {
				orderBy = "E.NODEID_" + a_x;
			}

			if ("locked".equals(sortColumn)) {
				orderBy = "E.LOCKED_" + a_x;
			}

			if ("deleteFlag".equals(sortColumn)) {
				orderBy = "E.DELETEFLAG_" + a_x;
			}

			if ("systemFlag".equals(sortColumn)) {
				orderBy = "E.SYSTEMFLAG_" + a_x;
			}

			if ("createTime".equals(sortColumn)) {
				orderBy = "E.CREATETIME_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

		}
		return orderBy;
	}

	public String getParentTableName() {
		return parentTableName;
	}

	public String getParseClass() {
		return parseClass;
	}

	public String getParseType() {
		return parseType;
	}

	public String getSystemFlag() {
		return systemFlag;
	}

	public String getSystemFlagLike() {
		if (systemFlagLike != null && systemFlagLike.trim().length() > 0) {
			if (!systemFlagLike.startsWith("%")) {
				systemFlagLike = "%" + systemFlagLike;
			}
			if (!systemFlagLike.endsWith("%")) {
				systemFlagLike = systemFlagLike + "%";
			}
		}
		return systemFlagLike;
	}

	public List<String> getSystemFlags() {
		return systemFlags;
	}

	public String getTableName() {
		return tableName;
	}

	public List<String> getTableNames() {
		return tableNames;
	}

	public String getTemporaryFlag() {
		return temporaryFlag;
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

	public String getTypeLike() {
		if (typeLike != null && typeLike.trim().length() > 0) {
			if (!typeLike.startsWith("%")) {
				typeLike = "%" + typeLike;
			}
			if (!typeLike.endsWith("%")) {
				typeLike = typeLike + "%";
			}
		}
		return typeLike;
	}

	public List<String> getTypes() {
		return types;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("tableName", "TABLENAME_");
		addColumn("parentTableName", "PARENTTABLENAME_");
		addColumn("packageName", "PACKAGENAME_");
		addColumn("entityName", "ENTITYNAME_");
		addColumn("className", "CLASSNAME_");
		addColumn("title", "TITLE_");
		addColumn("englishTitle", "ENGLISHTITLE_");
		addColumn("primaryKey", "PRIMARYKEY_");
		addColumn("filePrefix", "FILEPREFIX_");
		addColumn("parseType", "PARSETYPE_");
		addColumn("parseClass", "PARSECLASS_");
		addColumn("split", "SPLIT_");
		addColumn("batchSize", "BATCHSIZE_");
		addColumn("insertOnly", "INSERTONLY_");
		addColumn("startRow", "STARTROW_");
		addColumn("stopWord", "STOPWORD_");
		addColumn("stopSkipRow", "STOPSKIPROW_");
		addColumn("aggregationKeys", "AGGREGATIONKEYS_");
		addColumn("queryIds", "QUERYIDS_");
		addColumn("temporaryFlag", "TEMPORARYFLAG_");
		addColumn("deleteFetch", "DELETEFETCH_");
		addColumn("description", "DESCRIPTION_");
		addColumn("type", "TYPE_");
		addColumn("nodeId", "NODEID_");
		addColumn("locked", "LOCKED_");
		addColumn("deleteFlag", "DELETEFLAG_");
		addColumn("systemFlag", "SYSTEMFLAG_");
		addColumn("createTime", "CREATETIME_");
		addColumn("createBy", "CREATEBY_");
	}

	public DataTransferQuery lockeds(List<Integer> lockeds) {
		if (lockeds == null) {
			throw new RuntimeException("lockeds is empty ");
		}
		this.lockeds = lockeds;
		return this;
	}

	public DataTransferQuery nodeId(Long nodeId) {
		if (nodeId == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeId = nodeId;
		return this;
	}

	public DataTransferQuery nodeIds(List<Long> nodeIds) {
		if (nodeIds == null) {
			throw new RuntimeException("nodeIds is empty ");
		}
		this.nodeIds = nodeIds;
		return this;
	}

	public DataTransferQuery parentTableName(String parentTableName) {
		if (parentTableName == null) {
			throw new RuntimeException("parentTableName is null");
		}
		this.parentTableName = parentTableName;
		return this;
	}

	public DataTransferQuery parseClass(String parseClass) {
		if (parseClass == null) {
			throw new RuntimeException("parseClass is null");
		}
		this.parseClass = parseClass;
		return this;
	}

	public DataTransferQuery parseType(String parseType) {
		if (parseType == null) {
			throw new RuntimeException("parseType is null");
		}
		this.parseType = parseType;
		return this;
	}

	public void setAggregationKeysLike(String aggregationKeysLike) {
		this.aggregationKeysLike = aggregationKeysLike;
	}

	public void setCreateByLike(String createByLike) {
		this.createByLike = createByLike;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setCreateTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setDescriptionLike(String descriptionLike) {
		this.descriptionLike = descriptionLike;
	}

	public void setEnglishTitleLike(String englishTitleLike) {
		this.englishTitleLike = englishTitleLike;
	}

	public void setFilePrefixLike(String filePrefixLike) {
		this.filePrefixLike = filePrefixLike;
	}

	public void setLockeds(List<Integer> lockeds) {
		this.lockeds = lockeds;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodeIds(List<Long> nodeIds) {
		this.nodeIds = nodeIds;
	}

	public void setParentTableName(String parentTableName) {
		this.parentTableName = parentTableName;
	}

	public void setParseClass(String parseClass) {
		this.parseClass = parseClass;
	}

	public void setParseType(String parseType) {
		this.parseType = parseType;
	}

	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
	}

	public void setSystemFlagLike(String systemFlagLike) {
		this.systemFlagLike = systemFlagLike;
	}

	public void setSystemFlags(List<String> systemFlags) {
		this.systemFlags = systemFlags;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTableNames(List<String> tableNames) {
		this.tableNames = tableNames;
	}

	public void setTemporaryFlag(String temporaryFlag) {
		this.temporaryFlag = temporaryFlag;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTypeLike(String typeLike) {
		this.typeLike = typeLike;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public DataTransferQuery systemFlag(String systemFlag) {
		if (systemFlag == null) {
			throw new RuntimeException("systemFlag is null");
		}
		this.systemFlag = systemFlag;
		return this;
	}

	public DataTransferQuery systemFlagLike(String systemFlagLike) {
		if (systemFlagLike == null) {
			throw new RuntimeException("systemFlag is null");
		}
		this.systemFlagLike = systemFlagLike;
		return this;
	}

	public DataTransferQuery systemFlags(List<String> systemFlags) {
		if (systemFlags == null) {
			throw new RuntimeException("systemFlags is empty ");
		}
		this.systemFlags = systemFlags;
		return this;
	}

	public DataTransferQuery tableName(String tableName) {
		if (tableName == null) {
			throw new RuntimeException("tableName is null");
		}
		this.tableName = tableName;
		return this;
	}

	public DataTransferQuery tableNames(List<String> tableNames) {
		if (tableNames == null) {
			throw new RuntimeException("tableNames is empty ");
		}
		this.tableNames = tableNames;
		return this;
	}

	public DataTransferQuery temporaryFlag(String temporaryFlag) {
		if (temporaryFlag == null) {
			throw new RuntimeException("temporaryFlag is null");
		}
		this.temporaryFlag = temporaryFlag;
		return this;
	}

	public DataTransferQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public DataTransferQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public DataTransferQuery typeLike(String typeLike) {
		if (typeLike == null) {
			throw new RuntimeException("type is null");
		}
		this.typeLike = typeLike;
		return this;
	}

	public DataTransferQuery types(List<String> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

}