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
package com.glaf.base.modules.sys.service.mybatis;

import java.util.*;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.util.PageResult;

import com.glaf.base.modules.sys.mapper.*;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;
import com.glaf.base.modules.sys.service.*;

@Service("subjectCodeService")
@Transactional(readOnly = true)
public class SubjectCodeServiceImpl implements SubjectCodeService {
	protected IdGenerator idGenerator;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SubjectCodeMapper subjectCodeMapper;

	public SubjectCodeServiceImpl() {

	}

	public int count(SubjectCodeQuery query) {
		query.ensureInitialized();
		return subjectCodeMapper.getSubjectCodeCount(query);
	}

	public boolean create(SubjectCode bean) {
		this.save(bean);
		return true;
	}

	public boolean delete(long id) {
		this.deleteById(id);
		return true;
	}

	public boolean delete(SubjectCode bean) {
		this.deleteById(bean.getId());
		return true;
	}

	public boolean deleteAll(long[] ids) {
		if (ids != null && ids.length > 0) {
			for (long id : ids) {
				this.deleteById(id);
			}
		}
		return true;
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			subjectCodeMapper.deleteSubjectCodeById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			SubjectCodeQuery query = new SubjectCodeQuery();
			query.rowIds(rowIds);
			subjectCodeMapper.deleteSubjectCodes(query);
		}
	}

	public SubjectCode findByCode(String code) {
		SubjectCodeQuery query = new SubjectCodeQuery();
		query.subjectCode(code);
		query.setOrderBy(" E.subjectCode asc ");
		List<SubjectCode> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public SubjectCode findById(long id) {
		return this.getSubjectCode(id);
	}

	public PageResult getFeePage(Map<String, String> filter) {
		// 计算总数
		PageResult pager = new PageResult();
		SubjectCodeQuery query = new SubjectCodeQuery();

		String parent = (String) filter.get("parent");
		logger.info("parent:" + parent);
		if (parent != null) {
			query.parentId(Long.parseLong(parent));
		}

		// 费用编号
		String subjectCode = (String) filter.get("subjectCode");
		logger.info("subjectCode:" + subjectCode);
		if (subjectCode != null) {
			query.subjectCodeLike(subjectCode);
		}
		// 费用名称
		String subjectName = (String) filter.get("subjectName");
		logger.info("subjectName:" + subjectName);
		if (subjectName != null) {
			query.subjectNameLike(subjectName);
		}

		int pageNo = 1;
		if ((String) filter.get("page_no") != null) {
			pageNo = Integer.parseInt((String) filter.get("page_no"));
		}
		int pageSize = 15;
		if ((String) filter.get("page_size") != null) {
			pageSize = Integer.parseInt((String) filter.get("page_size"));
		}

		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.SORT desc");

		int start = pageSize * (pageNo - 1);
		List<SubjectCode> list = this.getSubjectCodesByQueryCriteria(start,
				pageSize, query);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public List<SubjectCode> getSubFeeList(Map<String, String> filter) {
		SubjectCodeQuery query = new SubjectCodeQuery();
		String parent = (String) filter.get("parent");
		logger.info("parent:" + parent);
		if (parent != null) {
			query.parentId(Long.parseLong(parent));
		}
		query.setOrderBy(" E.subjectCode asc ");
		List<SubjectCode> list = this.list(query);
		return list;
	}

	public SubjectCode getSubjectCode(Long id) {
		if (id == null) {
			return null;
		}
		SubjectCode subjectCode = subjectCodeMapper.getSubjectCodeById(id);
		return subjectCode;
	}

	public int getSubjectCodeCountByQueryCriteria(SubjectCodeQuery query) {
		return subjectCodeMapper.getSubjectCodeCount(query);
	}

	public List<SubjectCode> getSubjectCodeList() {
		SubjectCodeQuery query = new SubjectCodeQuery();
		query.setOrderBy(" E.subjectCode asc ");
		List<SubjectCode> list = this.list(query);
		return list;
	}

	public List<SubjectCode> getSubjectCodesByQueryCriteria(int start,
			int pageSize, SubjectCodeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SubjectCode> rows = sqlSessionTemplate.selectList(
				"getSubjectCodes", query, rowBounds);
		return rows;
	}

	public List<SubjectCode> getSysSubjectCodeList(long parent) {
		SubjectCodeQuery query = new SubjectCodeQuery();
		query.parentId(parent);
		query.setOrderBy(" E.subjectCode asc ");
		List<SubjectCode> list = this.list(query);
		return list;
	}

	public List<SubjectCode> list(SubjectCodeQuery query) {
		query.ensureInitialized();
		List<SubjectCode> list = subjectCodeMapper.getSubjectCodes(query);
		return list;
	}

	@Transactional
	public void save(SubjectCode subjectCode) {
		if (subjectCode.getId() == 0) {
			subjectCode.setId(idGenerator.nextId());
			subjectCodeMapper.insertSubjectCode(subjectCode);
		} else {
			subjectCodeMapper.updateSubjectCode(subjectCode);
		}
	}

	@Resource
	@Qualifier("myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Resource
	public void setSubjectCodeMapper(SubjectCodeMapper subjectCodeMapper) {
		this.subjectCodeMapper = subjectCodeMapper;
	}

	public boolean update(SubjectCode bean) {
		this.save(bean);
		return true;
	}

}
