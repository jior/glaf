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
import com.glaf.core.dao.*;
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

	protected DataTransferMapper dataTransferMapper;

	public MxDataTransferServiceImpl() {

	}

	public int count(DataTransferQuery query) {
		query.ensureInitialized();
		return dataTransferMapper.getDataTransferCount(query);
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

	public DataTransfer getDataTransfer(String id) {
		if (id == null) {
			return null;
		}
		DataTransfer dataTransfer = dataTransferMapper.getDataTransferById(id);
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
