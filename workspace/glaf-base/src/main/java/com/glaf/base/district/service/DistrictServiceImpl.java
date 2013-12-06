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

package com.glaf.base.district.service;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.query.TreeModelQuery;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.StringTools;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.base.TreeModel;
import com.glaf.core.dao.EntityDAO;
import com.glaf.base.district.domain.*;
import com.glaf.base.district.query.*;
import com.glaf.base.district.mapper.*;

@Service("districtService")
@Transactional(readOnly = true)
public class DistrictServiceImpl implements DistrictService {
	protected final static Log logger = LogFactory
			.getLog(DistrictServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected DistrictMapper districtMapper;

	protected ITableDataService tableDataService;

	public DistrictServiceImpl() {

	}

	public void deleteById(long id) {
		DistrictQuery query = new DistrictQuery();
		query.parentId(id);
		List<DistrictEntity> children = this.list(query);
		if (children == null || children.isEmpty()) {
			districtMapper.deleteDistrictById(id);
		}
	}

	public int count(DistrictQuery query) {
		return districtMapper.getDistrictCount(query);
	}

	public DistrictEntity getDistrict(Long id) {
		DistrictEntity district = districtMapper.getDistrictById(id);
		return district;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDistrictCountByQueryCriteria(DistrictQuery query) {
		return districtMapper.getDistrictCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DistrictEntity> getDistrictsByQueryCriteria(int start,
			int pageSize, DistrictQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DistrictEntity> rows = sqlSession.selectList("getDistricts",
				query, rowBounds);
		return rows;
	}

	protected String getTreeId(Map<Long, DistrictEntity> dataMap,
			DistrictEntity tree) {
		long parentId = tree.getParentId();
		long id = tree.getId();
		DistrictEntity parent = dataMap.get(parentId);
		if (parent != null && parent.getId() != 0) {
			if (StringUtils.isEmpty(parent.getTreeId())) {
				return getTreeId(dataMap, parent) + id + "|";
			}
			if (!parent.getTreeId().endsWith("|")) {
				parent.setTreeId(parent.getTreeId() + "|");
			}
			return parent.getTreeId() + id + "|";
		}
		return tree.getTreeId();
	}

	public int getDistrictTreeModelCount(TreeModelQuery query) {
		return districtMapper.getDistrictTreeModelCount(query);
	}

	public List<TreeModel> getDistrictTreeModels(TreeModelQuery query) {
		return districtMapper.getDistrictTreeModels(query);
	}

	public List<DistrictEntity> list(DistrictQuery query) {
		List<DistrictEntity> list = districtMapper.getDistricts(query);
		return list;
	}

	public List<DistrictEntity> getDistrictList(long parentId) {
		DistrictQuery query = new DistrictQuery();
		query.parentId(parentId);
		return list(query);
	}

	@Transactional
	public void save(DistrictEntity district) {
		if (district.getId() == 0) {
			district.setLevel(1);
			district.setId(idGenerator.nextId());
			if (district.getParentId() != 0) {
				DistrictEntity parent = this
						.getDistrict(district.getParentId());
				if (parent != null) {
					district.setLevel(parent.getLevel() + 1);
					if (parent.getTreeId() != null) {
						district.setTreeId(parent.getTreeId()
								+ district.getId() + "|");
					}
				}
			} else {
				district.setTreeId(district.getId() + "|");
			}
			districtMapper.insertDistrict(district);
		} else {
			DistrictEntity model = this.getDistrict(district.getId());
			if (model != null) {
				this.update(district);
			} else {
				district.setLevel(1);
				district.setId(idGenerator.nextId());
				if (district.getParentId() != 0) {
					DistrictEntity parent = this.getDistrict(district
							.getParentId());
					if (parent != null) {
						district.setLevel(parent.getLevel() + 1);
						if (parent.getTreeId() != null) {
							district.setTreeId(parent.getTreeId()
									+ district.getId() + "|");
						}
					}
				} else {
					district.setTreeId(district.getId() + "|");
				}
				districtMapper.insertDistrict(district);
			}
		}
	}

	public void loadChildren(List<DistrictEntity> list, long parentId) {
		DistrictQuery query = new DistrictQuery();
		query.setParentId(Long.valueOf(parentId));
		List<DistrictEntity> nodes = this.list(query);
		if (nodes != null && !nodes.isEmpty()) {
			for (DistrictEntity node : nodes) {
				list.add(node);
				this.loadChildren(list, node.getId());
			}
		}
	}

	@Transactional
	public boolean update(DistrictEntity bean) {
		DistrictEntity model = this.getDistrict(bean.getId());
		/**
		 * 如果节点移动了位置，即移动到别的节点下面去了
		 */
		if (model.getParentId() != bean.getParentId()) {
			List<DistrictEntity> list = new ArrayList<DistrictEntity>();
			this.loadChildren(list, bean.getId());
			if (!list.isEmpty()) {
				for (DistrictEntity node : list) {
					/**
					 * 不能移动到ta自己的子节点下面去
					 */
					if (bean.getParentId() == node.getId()) {
						throw new RuntimeException(
								"Can't change node into children");
					}
				}
				/**
				 * 修正所有子节点的treeId
				 */
				DistrictEntity oldParent = this
						.getDistrict(model.getParentId());
				DistrictEntity newParent = this.getDistrict(bean.getParentId());
				if (oldParent != null && newParent != null
						&& StringUtils.isNotEmpty(oldParent.getTreeId())
						&& StringUtils.isNotEmpty(newParent.getTreeId())) {
					TableModel tableModel = new TableModel();
					tableModel.setTableName("SYS_DISTRICT");
					ColumnModel idColumn = new ColumnModel();
					idColumn.setColumnName("ID_");
					idColumn.setJavaType("Long");
					tableModel.setIdColumn(idColumn);

					ColumnModel treeColumn = new ColumnModel();
					treeColumn.setColumnName("TREEID_");
					treeColumn.setJavaType("String");
					tableModel.addColumn(treeColumn);

					for (DistrictEntity node : list) {
						String treeId = node.getTreeId();
						if (StringUtils.isNotEmpty(treeId)) {
							treeId = StringTools.replace(treeId,
									oldParent.getTreeId(),
									newParent.getTreeId());
							idColumn.setValue(node.getId());
							treeColumn.setValue(treeId);
							tableDataService.updateTableData(tableModel);
						}
					}
				}
			}
		}

		if (bean.getParentId() != 0) {
			DistrictEntity parent = this.getDistrict(bean.getParentId());
			if (parent != null) {
				if (StringUtils.isNotEmpty(parent.getTreeId())) {
					bean.setTreeId(parent.getTreeId() + bean.getId() + "|");
				}
			}
		}

		districtMapper.updateDistrict(bean);

		return true;
	}

	@javax.annotation.Resource
	public void setDistrictMapper(DistrictMapper districtMapper) {
		this.districtMapper = districtMapper;
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
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

}