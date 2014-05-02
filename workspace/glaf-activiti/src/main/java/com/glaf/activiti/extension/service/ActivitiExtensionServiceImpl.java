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

package com.glaf.activiti.extension.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.glaf.activiti.extension.model.ExtensionEntity;
import com.glaf.activiti.extension.model.ExtensionFieldEntity;
import com.glaf.activiti.extension.model.ExtensionParamEntity;
import com.glaf.core.dao.MyBatisEntityDAO;
import com.glaf.core.util.UUID32;

public class ActivitiExtensionServiceImpl implements ActivitiExtensionService {

	protected SqlSession sqlSession;

	protected MyBatisEntityDAO myBatis3EntityDAO;

	public ActivitiExtensionServiceImpl() {

	}

	public ActivitiExtensionServiceImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
		myBatis3EntityDAO = new MyBatisEntityDAO(sqlSession);
	}

	public ExtensionEntity getExtension(String extendId) {
		ExtensionEntity extension = (ExtensionEntity) myBatis3EntityDAO
				.getById("getExtensionById", extendId);
		if (extension != null) {
			this.populate(extension);
		}

		return extension;
	}

	public List<ExtensionFieldEntity> getExtensionFields(String extendId) {
		List<?> list = myBatis3EntityDAO
				.getList("getExtensionFields", extendId);
		List<ExtensionFieldEntity> rows = new java.util.ArrayList<ExtensionFieldEntity>();
		if (list != null && !list.isEmpty()) {
			for (Object model : list) {
				rows.add((ExtensionFieldEntity) model);
			}
		}
		return rows;
	}

	public ExtensionEntity getExtensionListener(String name) {
		Map<String, Object> params = new java.util.HashMap<String, Object>();
		params.put("name", name);
		List<?> list = myBatis3EntityDAO
				.getList("getExtensionEntities", params);
		if (list != null && !list.isEmpty()) {
			ExtensionEntity extension = (ExtensionEntity) list.get(0);
			this.populate(extension);
			return extension;
		}
		return null;
	}

	public ExtensionEntity getExtensionListener(String processName, String name) {
		Map<String, Object> params = new java.util.HashMap<String, Object>();
		params.put("processName", processName);
		params.put("name", name);
		List<?> list = myBatis3EntityDAO
				.getList("getExtensionEntities", params);
		if (list != null && !list.isEmpty()) {
			ExtensionEntity extension = (ExtensionEntity) list.get(0);
			this.populate(extension);
			return extension;
		}
		return null;
	}

	public List<ExtensionParamEntity> getExtensionParams(String extendId) {
		List<?> list = myBatis3EntityDAO
				.getList("getExtensionParams", extendId);
		List<ExtensionParamEntity> rows = new java.util.ArrayList<ExtensionParamEntity>();
		if (list != null && !list.isEmpty()) {
			for (Object model : list) {
				rows.add((ExtensionParamEntity) model);
			}
		}
		return rows;
	}

	public List<ExtensionEntity> getExtensions(String processName) {
		Map<String, Object> params = new java.util.HashMap<String, Object>();
		params.put("processName", processName);
		List<?> list = myBatis3EntityDAO
				.getList("getExtensionEntities", params);
		List<ExtensionEntity> rows = new java.util.ArrayList<ExtensionEntity>();
		if (list != null && !list.isEmpty()) {
			for (Object model : list) {
				ExtensionEntity extension = (ExtensionEntity) model;
				this.populate(extension);
				rows.add(extension);
			}
		}
		return rows;
	}

	public ExtensionEntity getExtensionTask(String processName, String taskName) {
		Map<String, Object> params = new java.util.HashMap<String, Object>();
		params.put("processName", processName);
		params.put("taskName", taskName);
		List<?> list = myBatis3EntityDAO
				.getList("getExtensionEntities", params);
		if (list != null && !list.isEmpty()) {
			ExtensionEntity extension = (ExtensionEntity) list.get(0);
			this.populate(extension);
			return extension;
		}
		return null;
	}

	protected void populate(ExtensionEntity extension) {
		List<ExtensionFieldEntity> fields = this.getExtensionFields(extension
				.getExtendId());
		List<ExtensionParamEntity> params = this.getExtensionParams(extension
				.getExtendId());
		if (fields != null && !fields.isEmpty()) {
			for (ExtensionFieldEntity field : fields) {
				extension.addField(field);
			}
		}
		if (params != null && !params.isEmpty()) {
			for (ExtensionParamEntity param : params) {
				extension.addParam(param);
			}
		}
	}

	public void save(ExtensionEntity extension) {
		if (this.getExtension(extension.getExtendId()) != null) {
			myBatis3EntityDAO
					.delete("deleteExtension", extension.getExtendId());
			myBatis3EntityDAO.delete("deleteExtensionField",
					extension.getExtendId());
			myBatis3EntityDAO.delete("deleteExtensionParam",
					extension.getExtendId());
		}
		extension.setId(UUID32.getUUID());
		myBatis3EntityDAO.insert("insertExtension", extension);
		if (extension.getFields() != null && !extension.getFields().isEmpty()) {
			Collection<ExtensionFieldEntity> fields = extension.getFields()
					.values();
			for (ExtensionFieldEntity field : fields) {
				field.setExtendId(extension.getExtendId());
				field.setId(UUID32.getUUID());
				myBatis3EntityDAO.insert("insertExtensionField", field);
			}
		}
		if (extension.getParams() != null && !extension.getParams().isEmpty()) {
			Collection<ExtensionParamEntity> params = extension.getParams();
			for (ExtensionParamEntity param : params) {
				param.setExtendId(extension.getExtendId());
				param.setId(UUID32.getUUID());
				myBatis3EntityDAO.insert("insertExtensionParam", param);
			}
		}
	}

	public void saveAll(Collection<ExtensionEntity> extensions) {
		for (ExtensionEntity extension : extensions) {
			this.save(extension);
		}
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
		myBatis3EntityDAO = new MyBatisEntityDAO(sqlSession);
	}

}