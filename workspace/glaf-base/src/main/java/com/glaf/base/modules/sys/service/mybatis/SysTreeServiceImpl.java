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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.id.*;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.StringTools;

import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.mapper.*;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;
import com.glaf.base.modules.sys.service.*;

@Service("sysTreeService")
@Transactional(readOnly = true)
public class SysTreeServiceImpl implements SysTreeService {
	protected final static Log logger = LogFactory
			.getLog(SysTreeServiceImpl.class);

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysApplicationMapper sysApplicationMapper;

	protected SysDepartmentMapper sysDepartmentMapper;

	protected SysTreeMapper sysTreeMapper;

	protected ITableDataService tableDataService;

	public SysTreeServiceImpl() {

	}

	public int count(SysTreeQuery query) {
		return sysTreeMapper.getSysTreeCount(query);
	}

	@Transactional
	public boolean create(SysTree bean) {
		if (bean.getParentId() != 0) {
			SysTree parent = this.findById(bean.getParentId());
			if (parent != null) {
				if (bean.getDiscriminator() == null) {
					bean.setDiscriminator(parent.getDiscriminator());
				}
				if (bean.getCacheFlag() == null) {
					bean.setCacheFlag(parent.getCacheFlag());
				}
			}
		}
		bean.setCreateDate(new Date());
		this.save(bean);
		return true;
	}

	@Transactional
	public boolean delete(long id) {
		this.deleteById(id);
		return true;
	}

	@Transactional
	public boolean delete(SysTree bean) {
		this.deleteById(bean.getId());
		return true;
	}

	@Transactional
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
			sysTreeMapper.deleteSysTreeById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			SysTreeQuery query = new SysTreeQuery();
			query.rowIds(rowIds);
			sysTreeMapper.deleteSysTrees(query);
		}
	}

	public SysTree findById(long id) {
		return this.getSysTree(id);
	}

	public SysTree findByName(String name) {
		SysTreeQuery query = new SysTreeQuery();
		query.name(name);

		List<SysTree> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	public List<SysTree> getAllSysTreeList() {
		SysTreeQuery query = new SysTreeQuery();
		List<SysTree> list = this.list(query);
		Collections.sort(list);
		return list;
	}

	public List<SysTree> getAllSysTreeListForDept(long parentId, int status) {
		List<SysTree> list = new ArrayList<SysTree>();
		this.loadChildrenTreeListForDept(list, parentId, status);
		Collections.sort(list);
		this.initDepartments(list);
		return list;
	}

	public List<SysTree> getApplicationSysTrees(SysTreeQuery query) {
		return sysTreeMapper.getApplicationSysTrees(query);
	}

	public List<SysTree> getDepartmentSysTrees(SysTreeQuery query) {
		return sysTreeMapper.getDepartmentSysTrees(query);
	}

	public List<SysTree> getDictorySysTrees(SysTreeQuery query) {
		return sysTreeMapper.getDictorySysTrees(query);
	}

	/**
	 * 获取关联表树型结构
	 * 
	 * @param relationTable
	 *            表名
	 * @param relationColumn
	 *            关联字段名
	 * @param query
	 * @return
	 */
	public List<SysTree> getRelationSysTrees(String relationTable,
			String relationColumn, SysTreeQuery query) {
		query.setRelationTable(relationTable);
		query.setRelationColumn(relationColumn);
		return sysTreeMapper.getRelationSysTrees(query);
	}

	/**
	 * 获取某个用户某个角色的树节点
	 * 
	 * @param query
	 * @return
	 */
	public List<SysTree> getRoleUserTrees(SysUserQuery query) {
		return sysTreeMapper.getRoleUserTrees(query);
	}

	public void getSysTree(List<SysTree> tree, long parentId, int deep) {
		SysTreeQuery query = new SysTreeQuery();
		query.setParentId(Long.valueOf(parentId));
		query.setOrderBy("  E.SORT desc ");
		List<SysTree> nodes = this.list(query);
		if (nodes != null && !nodes.isEmpty()) {
			this.initDepartments(nodes);
			this.initApplications(nodes);
			Iterator<SysTree> iter = nodes.iterator();
			while (iter.hasNext()) {// 递归遍历
				SysTree bean = iter.next();
				bean.setDeep(deep + 1);
				tree.add(bean);// 加入到数组
				getSysTree(tree, (int) bean.getId(), bean.getDeep());
			}
		}
	}

	public SysTree getSysTree(Long id) {
		if (id == null) {
			return null;
		}
		SysTree sysTree = sysTreeMapper.getSysTreeById(id);
		return sysTree;
	}

	public SysTree getSysTreeByCode(String code) {
		SysTreeQuery query = new SysTreeQuery();
		query.code(code);

		List<SysTree> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	public int getSysTreeCountByQueryCriteria(SysTreeQuery query) {
		return sysTreeMapper.getSysTreeCount(query);
	}

	public List<SysTree> getSysTreeList(long parentId) {
		SysTreeQuery query = new SysTreeQuery();
		query.setParentId(Long.valueOf(parentId));
		query.setOrderBy("  E.SORT asc ");
		List<SysTree> list = this.list(query);
		Collections.sort(list);
		return list;
	}

	public PageResult getSysTreeList(long parentId, int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		SysTreeQuery query = new SysTreeQuery();
		query.parentId(Long.valueOf(parentId));
		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.SORT desc");

		int start = pageSize * (pageNo - 1);
		List<SysTree> list = this.getSysTreesByQueryCriteria(start, pageSize,
				query);
		Collections.sort(list);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public List<SysTree> getSysTreeListForDept(long parentId, int status) {
		SysTreeQuery query = new SysTreeQuery();
		query.setParentId(Long.valueOf(parentId));
		if (status != -1) {
			query.setDepartmentStatus(status);
		}
		query.setOrderBy(" E.SORT desc");
		List<SysTree> list = this.list(query);
		Collections.sort(list);
		this.initDepartments(list);
		return list;
	}

	/**
	 * 获取父节点列表，如:根目录>A>A1>A11
	 * 
	 * @param tree
	 * @param int id
	 */
	public void getSysTreeParent(List<SysTree> tree, long id) {
		// 查找是否有父节点
		SysTree bean = findById(id);
		if (bean != null) {
			if (bean.getParentId() != 0) {
				getSysTreeParent(tree, bean.getParentId());
			}
			tree.add(bean);
		}
	}

	public List<SysTree> getSysTreesByQueryCriteria(int start, int pageSize,
			SysTreeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysTree> rows = sqlSessionTemplate.selectList("getSysTrees",
				query, rowBounds);
		Collections.sort(rows);
		return rows;
	}

	protected String getTreeId(Map<Long, SysTree> dataMap, SysTree tree) {
		long parentId = tree.getParentId();
		long id = tree.getId();
		SysTree parent = dataMap.get(parentId);
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

	protected void initApplications(List<SysTree> list) {
		if (list != null && !list.isEmpty()) {
			SysApplicationQuery query = new SysApplicationQuery();
			List<SysApplication> apps = sysApplicationMapper
					.getSysApplications(query);
			Map<Long, SysApplication> appMap = new HashMap<Long, SysApplication>();
			if (apps != null && !apps.isEmpty()) {
				for (SysApplication m : apps) {
					appMap.put(m.getNodeId(), m);
				}
			}
			for (SysTree bean : list) {
				bean.setApp(appMap.get(Long.valueOf(bean.getId())));
			}
		}
	}

	protected void initDepartments(List<SysTree> list) {
		if (list != null && !list.isEmpty()) {
			SysDepartmentQuery query = new SysDepartmentQuery();
			query.status(0);
			List<SysDepartment> depts = sysDepartmentMapper
					.getSysDepartments(query);
			Map<Long, SysDepartment> deptMap = new HashMap<Long, SysDepartment>();
			if (depts != null && !depts.isEmpty()) {
				for (SysDepartment dept : depts) {
					deptMap.put(dept.getNodeId(), dept);
				}
			}
			for (SysTree bean : list) {
				bean.setDepartment(deptMap.get(bean.getId()));
			}
		}
	}

	public List<SysTree> list(SysTreeQuery query) {
		List<SysTree> list = sysTreeMapper.getSysTrees(query);
		return list;
	}

	public void loadChildren(List<SysTree> list, long parentId) {
		SysTreeQuery query = new SysTreeQuery();
		query.setParentId(Long.valueOf(parentId));
		List<SysTree> nodes = this.list(query);
		if (nodes != null && !nodes.isEmpty()) {
			for (SysTree node : nodes) {
				list.add(node);
				this.loadChildren(list, node.getId());
			}
		}
	}

	protected void loadChildrenTreeListForDept(List<SysTree> list,
			long parentId, int status) {
		SysTreeQuery query = new SysTreeQuery();
		query.setParentId(Long.valueOf(parentId));
		if (status != -1) {
			query.setDepartmentStatus(status);
		}
		query.setOrderBy(" E.SORT desc");
		List<SysTree> trees = this.list(query);
		if (trees != null && !trees.isEmpty()) {
			for (SysTree tt : trees) {
				list.add(tt);
				this.loadChildrenTreeListForDept(list, tt.getId(), status);
			}
		}
	}

	@Transactional
	public void save(SysTree bean) {
		String parentTreeId = null;
		if (bean.getParentId() != 0) {
			SysTree parent = this.findById(bean.getParentId());
			if (parent != null) {
				if (bean.getDiscriminator() == null) {
					bean.setDiscriminator(parent.getDiscriminator());
				}
				if (bean.getCacheFlag() == null) {
					bean.setCacheFlag(parent.getCacheFlag());
				}
				if (StringUtils.isNotEmpty(parent.getTreeId())) {
					parentTreeId = parent.getTreeId();
				}
			}
		}

		if (bean.getId() == 0) {
			bean.setSort(1);
			bean.setId(idGenerator.nextId());
			bean.setCreateDate(new Date());
			if (parentTreeId != null) {
				bean.setTreeId(parentTreeId + bean.getId() + "|");
			}
			sysTreeMapper.insertSysTree(bean);
		} else {
			bean.setUpdateDate(new Date());
			this.update(bean);
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
	public void setSysApplicationMapper(
			SysApplicationMapper sysApplicationMapper) {
		this.sysApplicationMapper = sysApplicationMapper;
	}

	@Resource
	public void setSysDepartmentMapper(SysDepartmentMapper sysDepartmentMapper) {
		this.sysDepartmentMapper = sysDepartmentMapper;
	}

	@Resource
	public void setSysTreeMapper(SysTreeMapper sysTreeMapper) {
		this.sysTreeMapper = sysTreeMapper;
	}

	@Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	@Transactional
	public void sort(long parent, SysTree bean, int operate) {
		if (operate == SysConstants.SORT_PREVIOUS) {// 前移
			sortByPrevious(parent, bean);
		} else if (operate == SysConstants.SORT_FORWARD) {// 后移
			sortByForward(parent, bean);
		}

	}

	/**
	 * 向后移动排序
	 * 
	 * @param bean
	 */
	private void sortByForward(long parent, SysTree bean) {
		SysTreeQuery query = new SysTreeQuery();
		query.parentId(Long.valueOf(parent));
		query.setSortLessThan(bean.getSort());
		query.setOrderBy(" E.SORT desc ");
		// 查找后一个对象
		List<SysTree> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			SysTree temp = (SysTree) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean

			temp.setSort(i);
			this.update(temp);// 更新temp
		}
	}

	/**
	 * 向前移动排序
	 * 
	 * @param bean
	 */
	private void sortByPrevious(long parent, SysTree bean) {
		SysTreeQuery query = new SysTreeQuery();
		query.parentId(Long.valueOf(parent));
		query.setSortGreaterThan(bean.getSort());
		query.setOrderBy(" E.SORT asc ");

		// 查找前一个对象
		List<SysTree> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			SysTree temp = (SysTree) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean

			temp.setSort(i);
			this.update(temp);// 更新temp
		}
	}

	@Transactional
	public boolean update(SysTree bean) {
		SysTree model = this.findById(bean.getId());
		/**
		 * 如果节点移动了位置，即移动到别的节点下面去了
		 */
		if (model.getParentId() != bean.getParentId()) {
			List<SysTree> list = new ArrayList<SysTree>();
			this.loadChildren(list, bean.getId());
			if (!list.isEmpty()) {
				for (SysTree node : list) {
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
				SysTree oldParent = this.findById(model.getParentId());
				SysTree newParent = this.findById(bean.getParentId());
				if (oldParent != null && newParent != null
						&& StringUtils.isNotEmpty(oldParent.getTreeId())
						&& StringUtils.isNotEmpty(newParent.getTreeId())) {
					TableModel tableModel = new TableModel();
					tableModel.setTableName("SYS_TREE");
					ColumnModel idColumn = new ColumnModel();
					idColumn.setColumnName("ID");
					idColumn.setJavaType("Long");
					tableModel.setIdColumn(idColumn);

					ColumnModel treeColumn = new ColumnModel();
					treeColumn.setColumnName("TREEID");
					treeColumn.setJavaType("String");
					tableModel.addColumn(treeColumn);

					for (SysTree node : list) {
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
			SysTree parent = this.findById(bean.getParentId());
			if (parent != null) {
				if (bean.getDiscriminator() == null) {
					bean.setDiscriminator(parent.getDiscriminator());
				}
				if (bean.getCacheFlag() == null) {
					bean.setCacheFlag(parent.getCacheFlag());
				}
				if (StringUtils.isNotEmpty(parent.getTreeId())) {
					bean.setTreeId(parent.getTreeId() + bean.getId() + "|");
				}
			}
		}

		sysTreeMapper.updateSysTree(bean);
		return true;
	}

	@Transactional
	public void updateTreeIds() {
		SysTree root = this.findById(1);
		if (root != null) {
			if (!StringUtils.equals(root.getTreeId(), "1|")) {
				root.setTreeId("1|");
				this.update(root);
			}
			List<SysTree> trees = this.getAllSysTreeList();
			if (trees != null && !trees.isEmpty()) {
				Map<Long, SysTree> dataMap = new HashMap<Long, SysTree>();
				for (SysTree tree : trees) {
					dataMap.put(tree.getId(), tree);
				}
				Map<Long, String> treeIdMap = new HashMap<Long, String>();
				for (SysTree tree : trees) {
					if (StringUtils.isEmpty(tree.getTreeId())) {
						String treeId = this.getTreeId(dataMap, tree);
						if (treeId != null && treeId.endsWith("|")) {
							treeIdMap.put(tree.getId(), treeId);
						}
					}
				}
				this.updateTreeIds(treeIdMap);
			}
		}
	}

	/**
	 * 更新树的treeId字段
	 * 
	 * @param treeMap
	 */
	@Transactional
	public void updateTreeIds(Map<Long, String> treeMap) {
		TableModel tableModel = new TableModel();
		tableModel.setTableName("SYS_TREE");
		ColumnModel idColumn = new ColumnModel();
		idColumn.setColumnName("ID");
		idColumn.setJavaType("Long");
		tableModel.setIdColumn(idColumn);

		ColumnModel treeColumn = new ColumnModel();
		treeColumn.setColumnName("TREEID");
		treeColumn.setJavaType("String");
		tableModel.addColumn(treeColumn);

		Iterator<Long> iterator = treeMap.keySet().iterator();
		while (iterator.hasNext()) {
			Long id = iterator.next();
			String value = treeMap.get(id);
			if (value != null) {
				idColumn.setValue(id);
				treeColumn.setValue(value);
				tableDataService.updateTableData(tableModel);
			}
		}
	}

}
