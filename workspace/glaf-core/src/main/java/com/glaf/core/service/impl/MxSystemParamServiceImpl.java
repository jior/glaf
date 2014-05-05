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
package com.glaf.core.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.InputDefinition;
import com.glaf.core.domain.SystemParam;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.mapper.InputDefinitionMapper;
import com.glaf.core.mapper.SystemParamMapper;
import com.glaf.core.query.InputDefinitionQuery;
import com.glaf.core.query.SystemParamQuery;
import com.glaf.core.service.ISystemParamService;
import com.glaf.core.util.DateUtils;

@Service("systemParamService")
@Transactional(readOnly = true)
public class MxSystemParamServiceImpl implements ISystemParamService {
	protected static final Log logger = LogFactory
			.getLog(MxSystemParamServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected SystemParamMapper systemParamMapper;

	protected InputDefinitionMapper inputDefinitionMapper;

	public MxSystemParamServiceImpl() {

	}

	public int count(SystemParamQuery query) {
		return systemParamMapper.getSystemParamCountByQueryCriteria(query);
	}

	@Transactional
	public void createSystemParams(String serviceKey, String businessKey) {
		InputDefinitionQuery query = new InputDefinitionQuery();
		query.serviceKey(serviceKey);
		List<InputDefinition> list = inputDefinitionMapper
				.getInputDefinitionsByServiceKey(serviceKey);
		if (list != null && !list.isEmpty()) {
			for (InputDefinition def : list) {
				SystemParam m = new SystemParam();
				m.setServiceKey(serviceKey);
				m.setBusinessKey(businessKey);
				m.setKeyName(def.getKeyName());
				m.setJavaType(def.getJavaType());
				m.setTypeCd(def.getTypeCd());
				m.setTitle(def.getTitle());
				if (def.getInitValue() != null) {
					String javaType = def.getJavaType();

					if ("Integer".equals(javaType)) {
						m.setIntVal(Integer.parseInt(def.getInitValue()));
						m.setStringVal(def.getInitValue());
					} else if ("Long".equals(javaType)) {
						m.setLongVal(Long.parseLong(def.getInitValue()));
						m.setStringVal(def.getInitValue());
					} else if ("Double".equals(javaType)) {
						m.setDoubleVal(Double.parseDouble(def.getInitValue()));
						m.setStringVal(def.getInitValue());
					} else if ("Date".equals(javaType)) {
						m.setDateVal(DateUtils.toDate(def.getInitValue()));
						m.setStringVal(def.getInitValue());
					} else {
						/**
						 * 如果是文本输入框或数字输入框，用定义的值作为初始化值
						 */
						if ("text".equals(def.getInputType())
								|| "numberbox".equals(def.getInputType())) {
							if (def.getInitValue().length() < 2000) {
								m.setStringVal(def.getInitValue());
								m.setTextVal(def.getInitValue());
							} else {
								m.setTextVal(def.getInitValue());
							}
						}
					}
				}

				String id = def.getTypeCd() + "_" + def.getKeyName();
				m.setId(id);
				if (this.getSystemParam(id) == null) {
					this.save(m);
				}
			}
		}
	}

	@Transactional
	public void deleteById(String id) {
		systemParamMapper.deleteSystemParamById(id);
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			for (String rowId : rowIds) {
				systemParamMapper.deleteSystemParamById(rowId);
			}
		}
	}

	public SystemParam getSystemParam(String id) {
		SystemParam systemParam = systemParamMapper.getSystemParamById(id);
		return systemParam;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSystemParamCount(Map<String, Object> parameter) {
		return systemParamMapper.getSystemParamCount(parameter);
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSystemParamCountByQueryCriteria(SystemParamQuery query) {
		return systemParamMapper.getSystemParamCountByQueryCriteria(query);
	}

	public List<InputDefinition> getInputDefinitions(String serviceKey) {
		InputDefinitionQuery query = new InputDefinitionQuery();
		query.serviceKey(serviceKey);
		List<InputDefinition> list = inputDefinitionMapper
				.getInputDefinitionsByServiceKey(serviceKey);
		return list;
	}

	public List<InputDefinition> getInputDefinitions(String serviceKey,
			String typeCd) {
		InputDefinitionQuery query = new InputDefinitionQuery();
		query.serviceKey(serviceKey);
		query.typeCd(typeCd);
		List<InputDefinition> list = inputDefinitionMapper
				.getInputDefinitionsByServiceKey(serviceKey);
		return list;
	}

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	public List<SystemParam> getSystemParams(Map<String, Object> parameter) {
		return systemParamMapper.getSystemParams(parameter);
	}

	public List<SystemParam> getSystemParams(String serviceKey,
			String businessKey) {
		SystemParamQuery query = new SystemParamQuery();
		query.serviceKey(serviceKey);
		query.setBusinessKey(businessKey);
		return this.list(query);
	}

	/**
	 * 获取系统参数
	 * 
	 * @param serviceKey
	 * @param businessKey
	 * @param keyName
	 * @return
	 */
	public SystemParam getSystemParam(String serviceKey, String businessKey,
			String keyName) {
		SystemParamQuery query = new SystemParamQuery();
		query.serviceKey(serviceKey);
		query.setBusinessKey(businessKey);
		query.setKeyName(keyName);
		List<SystemParam> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SystemParam> getSystemParamsByQueryCriteria(int start,
			int pageSize, SystemParamQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SystemParam> rows = sqlSession.selectList(
				"getSystemParamsByQueryCriteria", query, rowBounds);
		return rows;
	}

	public List<SystemParam> list(SystemParamQuery query) {
		List<SystemParam> list = systemParamMapper
				.getSystemParamsByQueryCriteria(query);
		return list;
	}

	@Transactional
	public void save(SystemParam systemParam) {
		String id = systemParam.getId();
		if (StringUtils.isEmpty(id)) {
			id = systemParam.getTypeCd() + "_" + systemParam.getKeyName();
		}
		SystemParam model = this.getSystemParam(id);
		if (model != null) {
			if (systemParam.getServiceKey() != null) {
				model.setServiceKey(systemParam.getServiceKey());
			}
			if (systemParam.getTypeCd() != null) {
				model.setTypeCd(systemParam.getTypeCd());
			}
			if (systemParam.getKeyName() != null) {
				model.setKeyName(systemParam.getKeyName());
			}
			if (systemParam.getTitle() != null) {
				model.setTitle(systemParam.getTitle());
			}
			if (systemParam.getStringVal() != null) {
				model.setStringVal(systemParam.getStringVal());
			}
			if (systemParam.getTextVal() != null) {
				model.setTextVal(systemParam.getTextVal());
			}
			if (systemParam.getIntVal() != null) {
				model.setIntVal(systemParam.getIntVal());
			}
			if (systemParam.getLongVal() != null) {
				model.setLongVal(systemParam.getLongVal());
			}
			if (systemParam.getDoubleVal() != null) {
				model.setDoubleVal(systemParam.getDoubleVal());
			}
			if (systemParam.getDateVal() != null) {
				model.setDateVal(systemParam.getDateVal());
			}
			systemParamMapper.updateSystemParam(model);
		} else {
			systemParam.setId(id);
			systemParamMapper.insertSystemParam(systemParam);
		}
	}

	@Transactional
	public void saveAll(String serviceKey, String businessKey,
			List<SystemParam> rows) {
		if (rows != null && !rows.isEmpty()) {
			for (SystemParam param : rows) {
				param.setServiceKey(serviceKey);
				param.setBusinessKey(businessKey);
				this.save(param);
			}
		}
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
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@javax.annotation.Resource
	public void setInputDefinitionMapper(
			InputDefinitionMapper inputDefinitionMapper) {
		this.inputDefinitionMapper = inputDefinitionMapper;
	}

	@javax.annotation.Resource
	public void setSystemParamMapper(SystemParamMapper systemParamMapper) {
		this.systemParamMapper = systemParamMapper;
	}

	/**
	 * 修改多条参数定义
	 * 
	 * @param serviceKey
	 * @param rows
	 */
	@Transactional
	public void updateAll(String serviceKey, List<InputDefinition> rows) {
		Map<String, InputDefinition> rowMap = new java.util.HashMap<String, InputDefinition>();
		for (InputDefinition def : rows) {
			rowMap.put(def.getKeyName(), def);
		}
		InputDefinitionQuery query = new InputDefinitionQuery();
		query.serviceKey(serviceKey);
		List<InputDefinition> list = inputDefinitionMapper
				.getInputDefinitionsByServiceKey(serviceKey);
		if (list != null && !list.isEmpty()) {
			for (InputDefinition def : list) {
				if (rowMap.get(def.getKeyName()) != null) {
					InputDefinition m = rowMap.get(def.getKeyName());
					def.setJavaType(m.getJavaType());
					def.setTitle(m.getTitle());
					inputDefinitionMapper.updateInputDefinition(def);
				}
			}
		}
	}

}
