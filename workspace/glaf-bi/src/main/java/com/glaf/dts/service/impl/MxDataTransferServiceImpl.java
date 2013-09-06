package com.glaf.dts.service.impl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.mapper.ColumnDefinitionMapper;
import com.glaf.core.dao.*;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.dts.mapper.*;
import com.glaf.dts.domain.*;
import com.glaf.dts.query.*;
import com.glaf.dts.service.*;

@Service("dataTransferService")
@Transactional(readOnly = true)
public class MxDataTransferServiceImpl implements IDataTransferService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ColumnDefinitionMapper columnDefinitionMapper;

	protected DataTransferMapper dataTransferMapper;

	public MxDataTransferServiceImpl() {

	}

	public int count(DataTransferQuery query) {
		query.ensureInitialized();
		return dataTransferMapper.getDataTransferCount(query);
	}

	@Transactional
	public void deleteByColumnId(String id) {
		columnDefinitionMapper.deleteColumnDefinitionById(id);
	}

	@Transactional
	public void deleteByColumnIds(List<String> columnIds) {
		if (columnIds != null && !columnIds.isEmpty()) {
			for (String columnId : columnIds) {
				columnDefinitionMapper.deleteColumnDefinitionById(columnId);
			}
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			dataTransferMapper.deleteDataTransferById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				dataTransferMapper.deleteDataTransferById(id);
			}
		}
	}

	public List<ColumnDefinition> getColumns(String tableName) {
		String targetId = "transfer_" + tableName.toLowerCase();
		return columnDefinitionMapper.getColumnDefinitionsByTargetId(targetId);
	}

	public DataTransfer getDataTransfer(String id) {
		if (id == null) {
			return null;
		}
		DataTransfer dataTransfer = dataTransferMapper.getDataTransferById(id);
		if(dataTransfer != null){
			List<ColumnDefinition> columns = this.getColumns(dataTransfer.getTableName());
			dataTransfer.setColumns(columns);
		}
		return dataTransfer;
	}

	public int getDataTransferCountByQueryCriteria(DataTransferQuery query) {
		return dataTransferMapper.getDataTransferCount(query);
	}

	public List<DataTransfer> getDataTransfersByQueryCriteria(int start,
			int pageSize, DataTransferQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DataTransfer> rows = sqlSessionTemplate.selectList(
				"getDataTransfers", query, rowBounds);
		return rows;
	}

	public List<DataTransfer> list(DataTransferQuery query) {
		query.ensureInitialized();
		List<DataTransfer> list = dataTransferMapper.getDataTransfers(query);
		return list;
	}

	@Transactional
	public void save(DataTransfer dataTransfer) {
		if (StringUtils.isEmpty(dataTransfer.getId())) {
			dataTransfer.setId(idGenerator.getNextId());
			dataTransfer.setCreateTime(new Date());
			dataTransfer.setDeleteFlag(0);
			dataTransferMapper.insertDataTransfer(dataTransfer);
		} else {
			dataTransferMapper.updateDataTransfer(dataTransfer);
		}
		if (dataTransfer.getColumns() != null
				&& !dataTransfer.getColumns().isEmpty()) {
			for (ColumnDefinition col : dataTransfer.getColumns()) {
				this.saveColumn(dataTransfer.getTableName(), col);
			}
		}
	}

	@Transactional
	public void saveColumn(String tableName, ColumnDefinition columnDefinition) {
		String id = "transfer_" + tableName.toLowerCase() + "_"
				+ columnDefinition.getColumnName().toLowerCase();
		ColumnDefinition col = columnDefinitionMapper
				.getColumnDefinitionById(id);
		if (col == null) {
			columnDefinition.setId(id);
			columnDefinition.setTargetId("transfer_" + tableName.toLowerCase());
			columnDefinitionMapper.insertColumnDefinition(columnDefinition);
		} else {
			columnDefinitionMapper.updateColumnDefinition(columnDefinition);
		}
	}

	@Resource
	public void setColumnDefinitionMapper(
			ColumnDefinitionMapper columnDefinitionMapper) {
		this.columnDefinitionMapper = columnDefinitionMapper;
	}

	@Resource
	public void setDataTransferMapper(DataTransferMapper dataTransferMapper) {
		this.dataTransferMapper = dataTransferMapper;
	}

	@Resource(name = "myBatisEntityDAO")
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@Resource(name = "myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
