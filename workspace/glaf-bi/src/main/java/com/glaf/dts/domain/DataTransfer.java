package com.glaf.dts.domain;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.glaf.core.base.*;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.dts.util.*;

@Entity
@Table(name = "DTS_DATA_TRANSFER")
public class DataTransfer implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	@Column(name = "TABLENAME_", length = 50)
	protected String tableName;

	@Column(name = "PARENTTABLENAME_", length = 50)
	protected String parentTableName;

	@Column(name = "PACKAGENAME_", length = 200)
	protected String packageName;

	@Column(name = "ENTITYNAME_", length = 50)
	protected String entityName;

	@Column(name = "CLASSNAME_", length = 200)
	protected String className;

	@Column(name = "TITLE_", length = 250)
	protected String title;

	@Column(name = "ENGLISHTITLE_", length = 250)
	protected String englishTitle;

	@Column(name = "PRIMARYKEY_", length = 50)
	protected String primaryKey;

	@Column(name = "FILEPREFIX_", length = 250)
	protected String filePrefix;

	@Column(name = "PARSETYPE_", length = 50)
	protected String parseType;

	@Column(name = "PARSECLASS_", length = 200)
	protected String parseClass;

	@Column(name = "SPLIT_", length = 200)
	protected String split;

	@Column(name = "BATCHSIZE_")
	protected Integer batchSize;

	@Column(name = "INSERTONLY_", length = 10)
	protected String insertOnly;

	@Column(name = "STARTROW_")
	protected Integer startRow;

	@Column(name = "STOPWORD_", length = 500)
	protected String stopWord;

	@Column(name = "STOPSKIPROW_")
	protected Integer stopSkipRow;

	@Column(name = "AGGREGATIONKEYS_", length = 500)
	protected String aggregationKeys;

	@Column(name = "QUERYIDS_", length = 500)
	protected String queryIds;

	@Column(name = "TEMPORARYFLAG_", length = 1)
	protected String temporaryFlag;

	@Column(name = "DELETEFETCH_", length = 1)
	protected String deleteFetch;

	@Column(name = "DESCRIPTION_", length = 500)
	protected String description;

	@Column(name = "TYPE_", length = 50)
	protected String type;

	@Column(name = "NODEID_")
	protected Long nodeId;

	@Column(name = "LOCKED_")
	protected Integer locked;

	@Column(name = "DELETEFLAG_")
	protected Integer deleteFlag;

	@Column(name = "SYSTEMFLAG_", length = 2)
	protected String systemFlag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@javax.persistence.Transient
	protected ColumnDefinition idColumn;

	@javax.persistence.Transient
	protected List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();

	/**
	 * 需要排除的行列表
	 */
	@javax.persistence.Transient
	protected List<String> excludes = new ArrayList<String>();

	@javax.persistence.Transient
	protected Map<String, String> properties = new HashMap<String, String>();

	public DataTransfer() {

	}

	public void addCollectionColumn(String columnName,
			Collection<Object> collection) {
		if (columns == null) {
			columns = new ArrayList<ColumnDefinition>();
		}
		ColumnDefinition column = new ColumnDefinition();
		column.setColumnName(columnName);
		column.setJavaType("Collection");
		column.setValue(collection);
		columns.add(column);
	}

	public void addColumn(ColumnDefinition column) {
		if (columns == null) {
			columns = new ArrayList<ColumnDefinition>();
		}
		columns.add(column);
	}

	public void addColumn(String columnName, String javaType, Object value) {
		if (columns == null) {
			columns = new ArrayList<ColumnDefinition>();
		}
		ColumnDefinition column = new ColumnDefinition();
		column.setColumnName(columnName);
		column.setJavaType(javaType);
		column.setValue(value);
		columns.add(column);
	}

	public void addExclude(String exclude) {
		if (excludes == null) {
			excludes = new ArrayList<String>();
		}
		excludes.add(exclude);
	}

	public void addProperty(String key, String value) {
		if (properties == null) {
			properties = new HashMap<String, String>();
		}
		properties.put(key, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataTransfer other = (DataTransfer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getAggregationKeys() {
		return this.aggregationKeys;
	}

	public List<String> getListStringAggregationKeys() {
		List<String> list = new ArrayList<String>();
		if (aggregationKeys != null && aggregationKeys.trim().length() > 0) {
			int start = 0;
			int end = aggregationKeys.indexOf(",");
			while (end != -1) {
				list.add("'" + aggregationKeys.substring(start, end) + "'");
				start = end + ",".length();
				end = aggregationKeys.indexOf(",", start);
			}
			if (start < aggregationKeys.length()) {
				String temp = aggregationKeys.substring(start);
				if (temp != null && temp.trim().length() > 0) {
					list.add("'" + temp + "'");
				}
			}
		}
		return list;
	}

	public Integer getBatchSize() {
		return this.batchSize;
	}

	public String getClassName() {
		return this.className;
	}

	public List<ColumnDefinition> getColumns() {
		return columns;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getDeleteFetch() {
		return this.deleteFetch;
	}

	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public String getDescription() {
		return this.description;
	}

	public String getEnglishTitle() {
		return this.englishTitle;
	}

	public String getEntityName() {
		return this.entityName;
	}

	public List<String> getExcludes() {
		return excludes;
	}

	public String getFilePrefix() {
		return this.filePrefix;
	}

	public String getId() {
		return this.id;
	}

	public ColumnDefinition getIdColumn() {
		return idColumn;
	}

	public String getInsertOnly() {
		return this.insertOnly;
	}

	public Integer getLocked() {
		return this.locked;
	}

	public Long getNodeId() {
		return this.nodeId;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public String getParentTableName() {
		return this.parentTableName;
	}

	public String getParseClass() {
		return this.parseClass;
	}

	public String getParseType() {
		return this.parseType;
	}

	public String getPrimaryKey() {
		return this.primaryKey;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public String getQueryIds() {
		return this.queryIds;
	}

	public String getSplit() {
		return this.split;
	}

	public Integer getStartRow() {
		return this.startRow;
	}

	public Integer getStopSkipRow() {
		return this.stopSkipRow;
	}

	public String getStopWord() {
		return this.stopWord;
	}

	public String getSystemFlag() {
		return this.systemFlag;
	}

	public String getTableName() {
		return this.tableName;
	}

	public String getTemporaryFlag() {
		return this.temporaryFlag;
	}

	public String getTitle() {
		return this.title;
	}

	public String getType() {
		return this.type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public DataTransfer jsonToObject(JSONObject jsonObject) {
		return DataTransferJsonFactory.jsonToObject(jsonObject);
	}

	public void setAggregationKeys(String aggregationKeys) {
		this.aggregationKeys = aggregationKeys;
	}

	public void setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setColumns(List<ColumnDefinition> columns) {
		this.columns = columns;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDeleteFetch(String deleteFetch) {
		this.deleteFetch = deleteFetch;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEnglishTitle(String englishTitle) {
		this.englishTitle = englishTitle;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public void setExcludes(List<String> excludes) {
		this.excludes = excludes;
	}

	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIdColumn(ColumnDefinition idColumn) {
		this.idColumn = idColumn;
	}

	public void setInsertOnly(String insertOnly) {
		this.insertOnly = insertOnly;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
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

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public void setQueryIds(String queryIds) {
		this.queryIds = queryIds;
	}

	public void setSplit(String split) {
		this.split = split;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public void setStopSkipRow(Integer stopSkipRow) {
		this.stopSkipRow = stopSkipRow;
	}

	public void setStopWord(String stopWord) {
		this.stopWord = stopWord;
	}

	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTemporaryFlag(String temporaryFlag) {
		this.temporaryFlag = temporaryFlag;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JSONObject toJsonObject() {
		return DataTransferJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DataTransferJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
