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
package com.glaf.oa.assesssort.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;

import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.service.DictoryService;

import com.glaf.oa.assessquestion.model.AssessortTree;
import com.glaf.oa.assesssort.mapper.AssesssortMapper;
import com.glaf.oa.assesssort.model.Assesssort;
import com.glaf.oa.assesssort.model.AssesssortType;
import com.glaf.oa.assesssort.query.AssesssortQuery;

@Service("assesssortService")
@Transactional(readOnly = true)
public class AssesssortServiceImpl implements AssesssortService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected AssesssortMapper assesssortMapper;

	protected DictoryService dictoryService;

	public AssesssortServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			assesssortMapper.deleteAssesssortById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> assesssortids) {
		if (assesssortids != null && !assesssortids.isEmpty()) {
			for (Long id : assesssortids) {
				assesssortMapper.deleteAssesssortById(id);
			}
		}
	}

	public int count(AssesssortQuery query) {
		query.ensureInitialized();
		return assesssortMapper.getAssesssortCount(query);
	}

	public List<Assesssort> list(AssesssortQuery query) {
		query.ensureInitialized();
		List<Assesssort> list = assesssortMapper.getAssesssorts(query);
		return list;
	}

	public int getAssesssortCountByQueryCriteria(AssesssortQuery query) {
		return assesssortMapper.getAssesssortCount(query);
	}

	public List<Assesssort> getAssesssortsByQueryCriteria(int start,
			int pageSize, AssesssortQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Assesssort> rows = sqlSessionTemplate.selectList("getAssesssorts",
				query, rowBounds);
		return rows;
	}

	public Assesssort getAssesssort(Long id) {
		if (id == null) {
			return null;
		}
		Assesssort assesssort = assesssortMapper.getAssesssortById(id);
		return assesssort;
	}

	@Transactional
	public void save(Assesssort assesssort) {
		if (assesssort.getAssesssortid() == null) {
			assesssort.setAssesssortid(idGenerator.nextId("oa_assesssort"));
			// assesssort.setCreateDate(new Date());
			// assesssort.setDeleteFlag(0);
			assesssortMapper.insertAssesssort(assesssort);
		} else {
			assesssortMapper.updateAssesssort(assesssort);
		}
	}

	@Transactional
	public List<BaseDataInfo> getAssessTypeByCode(String typeCode) {

		return assesssortMapper.getAssessTypeByCode(typeCode);
	}

	@Transactional
	public List<BaseDataInfo> getAssessTypeById(long id) {

		return assesssortMapper.getAssessTypeById(id);
	}

	@Transactional
	public List<BaseDataInfo> getAssessTypeByStandardAndSortIds(
			AssesssortQuery sortQuery) {

		return assesssortMapper.getAssessTypeByStandardAndSortIds(sortQuery);
	}

	@Transactional
	public List<AssesssortType> getAssesssortsType(String typeCode) {
		// 按CODE先获得类型ID
		List<BaseDataInfo> sortTypeList = assesssortMapper
				.getAssessTypeByCode(typeCode);
		Collections.sort(sortTypeList, new ComparetorAssessTypeBySort());
		List<AssesssortType> assessortTypeList = new ArrayList<AssesssortType>(
				sortTypeList.size());
		AssesssortType assesssortType = null;
		for (BaseDataInfo dataInfo : sortTypeList) {
			String code = dataInfo.getCode();
			String name = dataInfo.getName();
			long id = dataInfo.getId();
			assesssortType = new AssesssortType();
			assesssortType.setId(id);
			assesssortType.setName(name);
			assesssortType.setCode(code);
			// 按parentId获得子类型
			List<AssesssortType> subList = new ArrayList<AssesssortType>();
			List<Dictory> dictorys = dictoryService.getDictoryList(dataInfo
					.getId());
			for (Dictory dictory : dictorys) {
				AssesssortType subAssesssortType = new AssesssortType();
				subAssesssortType.setId(dictory.getId());
				subAssesssortType.setCode(dictory.getCode());
				subAssesssortType.setName(dictory.getName());
				subList.add(subAssesssortType);
			}
			assesssortType.setSubAssessList(subList);
			assessortTypeList.add(assesssortType);
		}

		return assessortTypeList;
	}

	/**
	 * 按具体某标准获得上级层次信息
	 */
	@Transactional
	public List<AssessortTree> getParentsInfoByDictId(Integer dictId) {
		return assesssortMapper.getParentsInfoByDictId(dictId);

	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setDictoryService(DictoryService dictoryService) {
		this.dictoryService = dictoryService;
	}

	@javax.annotation.Resource
	public void setAssesssortMapper(AssesssortMapper assesssortMapper) {
		this.assesssortMapper = assesssortMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}

/**
 * 排序标准
 * 
 * @author Administrator
 * 
 */
class ComparetorAssessTypeBySort implements Comparator<BaseDataInfo> {

	public int compare(BaseDataInfo o1, BaseDataInfo o2) {
		return o1.getDeep() - o1.getDeep();
	}

}