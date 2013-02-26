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
package com.glaf.report.service.impl;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.entity.EntityDAO;
import com.glaf.core.id.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.report.domain.*;
import com.glaf.report.query.*;
import com.glaf.report.mapper.*;
import com.glaf.report.service.*;

@Service("reportFileService")
@Transactional(readOnly = true)
public class MxReportFileServiceImpl implements IReportFileService {
	protected static final Log logger = LogFactory
			.getLog(MxReportFileServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected ReportFileMapper reportFileMapper;

	public MxReportFileServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		reportFileMapper.deleteReportFileById(id);
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			for (String rowId : rowIds) {
				reportFileMapper.deleteReportFileById(rowId);
			}
		}
	}

	public int count(ReportFileQuery query) {
		query.ensureInitialized();
		return reportFileMapper.getReportFileCountByQueryCriteria(query);
	}

	public List<ReportFile> list(ReportFileQuery query) {
		query.ensureInitialized();
		List<ReportFile> list = reportFileMapper
				.getReportFilesByQueryCriteria(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getReportFileCount(Map<String, Object> parameter) {
		return reportFileMapper.getReportFileCount(parameter);
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getReportFileCountByQueryCriteria(ReportFileQuery query) {
		return reportFileMapper.getReportFileCountByQueryCriteria(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ReportFile> getReportFilesByQueryCriteria(int start,
			int pageSize, ReportFileQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ReportFile> rows = sqlSession.selectList(
				"getReportFilesByQueryCriteria", query, rowBounds);
		return rows;
	}

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	public List<ReportFile> getReportFiles(Map<String, Object> parameter) {
		return reportFileMapper.getReportFiles(parameter);
	}

	public ReportFile getReportFile(String id) {
		ReportFile reportFile = reportFileMapper.getReportFileById(id);
		return reportFile;
	}

	@Transactional
	public void save(ReportFile reportFile) {
		ReportFile model = this.getReportFile(reportFile.getId());
		if (model != null) {
			if (reportFile.getReportId() != null) {
				model.setReportId(reportFile.getReportId());
			}
			if (reportFile.getFilename() != null) {
				model.setFilename(reportFile.getFilename());
			}
			model.setFileSize(reportFile.getFileSize());
			model.setFileContent(reportFile.getFileContent());
			model.setReportYearMonthDay(reportFile.getReportYearMonthDay());
			if (reportFile.getCreateDate() != null) {
				model.setCreateDate(reportFile.getCreateDate());
			}
			if (StringUtils.equals(DBUtils.POSTGRESQL,
					DBConnectionFactory.getDatabaseType())) {
				reportFileMapper.updateReportFile_postgres(model);
			} else {
				reportFileMapper.updateReportFile(model);
			}
		} else {
			if (StringUtils.equals(DBUtils.POSTGRESQL,
					DBConnectionFactory.getDatabaseType())) {
				reportFileMapper.insertReportFile_postgres(reportFile);
			} else {
				reportFileMapper.insertReportFile(reportFile);
			}
		}
	}

	@javax.annotation.Resource(name = "myBatisEntityDAO")
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource(name = "myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setReportFileMapper(ReportFileMapper reportFileMapper) {
		this.reportFileMapper = reportFileMapper;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

}
