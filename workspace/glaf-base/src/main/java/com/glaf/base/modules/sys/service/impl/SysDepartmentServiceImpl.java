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

package com.glaf.base.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.service.*;
import com.glaf.core.util.PageResult;

public class SysDepartmentServiceImpl implements SysDepartmentService {
	private static final Log logger = LogFactory
			.getLog(SysDepartmentServiceImpl.class);
	private SysTreeService sysTreeService;
	private AbstractSpringDao abstractDao;

	/**
	 * 保存
	 * 
	 * @param bean
	 *            SysDepartment
	 * @return boolean
	 */
	public boolean create(SysDepartment bean) {
		boolean ret = false;
		if (abstractDao.create(bean)) {// 插入记录成功
			bean.setSort((int) bean.getId());// 设置排序号为刚插入的id值
			bean.getNode().setSort(bean.getSort());
			abstractDao.update(bean);
			ret = true;
		}
		return ret;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	public boolean delete(long id) {
		SysDepartment bean = findById(id);
		if (bean != null) {
			return delete(bean);
		} else {
			return false;
		}
	}

	/**
	 * 删除
	 * 
	 * @param bean
	 *            SysDepartment
	 * @return boolean
	 */
	public boolean delete(SysDepartment bean) {
		return sysTreeService.delete(bean.getId());
	}

	/**
	 * 批量删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteAll(long[] id) {
		List list = new ArrayList();
		for (int i = 0; i < id.length; i++) {
			logger.info("id:" + id[i]);
			SysDepartment bean = findById(id[i]);
			if (bean != null)
				list.add(bean);
		}
		return abstractDao.deleteAll(list);
	}

	/**
	 * 按编码查找对象
	 * 
	 * @param code
	 * 
	 * @return SysDepartment
	 */
	public SysDepartment findByCode(String code) {
		SysDepartment bean = null;
		Object[] values = new Object[] { code };
		String query = "from SysDepartment a where a.code=? order by a.id asc";
		List<SysDepartment> list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// 有记录
			bean = (SysDepartment) list.get(0);
		}
		return bean;
	}

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	public SysDepartment findById(long id) {
		return (SysDepartment) abstractDao.find(SysDepartment.class, new Long(
				id));
	}

	/**
	 * 按名称查找对象
	 * 
	 * @param name
	 *            String
	 * @return SysDepartment
	 */
	public SysDepartment findByName(String name) {
		SysDepartment bean = null;
		Object[] values = new Object[] { name };
		String query = "from SysDepartment a where a.name=? order by a.id asc";
		List<SysDepartment> list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// 有记录
			bean = (SysDepartment) list.get(0);
		}
		return bean;
	}

	/**
	 * 按代码查找对象
	 * 
	 * @param name
	 *            String
	 * @return SysDepartment
	 */
	public SysDepartment findByNo(String deptno) {
		SysDepartment bean = null;
		Object[] values = new Object[] { deptno };
		String query = "from SysDepartment a where a.no=? order by a.id asc";
		List<SysDepartment> list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// 有记录
			bean = (SysDepartment) list.get(0);
		}
		return bean;
	}

	public void findNestingDepartment(List<SysDepartment> list, long deptId) {
		SysDepartment node = this.findById(deptId);
		if (node != null) {
			this.findNestingDepartment(list, node);
		}
	}

	/**
	 * 获取用户部门列表
	 * 
	 * @param list
	 * @param node
	 */
	public void findNestingDepartment(List<SysDepartment> list,
			SysDepartment node) {
		if (node == null) {
			return;
		}
		SysTree tree = node.getNode();
		if (tree.getParent() == 0) {// 找到根节点
			logger.info("findFirstNode:" + node.getId());
			list.add(node);
		} else {
			SysTree treeParent = sysTreeService.findById(tree.getParent());
			SysDepartment parent = treeParent.getDepartment();
			findNestingDepartment(list, parent);
		}
		list.add(node);
	}

	/**
	 * 获取列表
	 * 
	 * @return List
	 */
	public List<SysDepartment> getSysDepartmentList() {
		String query = "from SysDepartment a ";
		return abstractDao.getList(query, null, null);
	}

	/**
	 * 获取列表
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	public List<SysDepartment> getSysDepartmentList(int parent) {
		// 计算总数
		Object[] values = new Object[] { new Long(parent) };
		String query = "from SysDepartment a where a.node.parent=? order by a.sort desc";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * 获取分页列表
	 * 
	 * @param parent
	 *            int
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	public PageResult getSysDepartmentList(int parent, int pageNo, int pageSize) {
		// 计算总数
		Object[] values = new Object[] { new Long(parent) };
		String query = "select count(*) from SysDepartment a where a.node.parent=?";
		int count = ((Long) abstractDao.getList(query, values, null).iterator()
				.next()).intValue();
		if (count == 0) {// 结果集为空
			PageResult pager = new PageResult();
			pager.setPageSize(pageSize);
			return pager;
		}
		// 查询列表
		query = "from SysDepartment a where a.node.parent=? order by a.sort desc";
		return abstractDao.getList(query, values, pageNo, pageSize, count);
	}

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}

	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
		logger.info("setSysTreeService");
	}

	/**
	 * 排序
	 * 
	 * @param bean
	 *            SysDepartment
	 * @param operate
	 *            int 操作
	 */
	public void sort(long parent, SysDepartment bean, int operate) {
		if (bean == null)
			return;
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
	private void sortByForward(long parent, SysDepartment bean) {
		Object[] values = new Object[] { new Long(parent),
				new Integer(bean.getSort()) };
		// 查找后一个对象
		String query = "from SysDepartment a where a.node.parent=? and a.sort<? order by a.sort desc";
		List<SysDepartment> list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// 有记录
			SysDepartment temp = (SysDepartment) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);// 更新bean
			SysTree node = bean.getNode();
			node.setSort(bean.getSort());
			abstractDao.update(node);

			temp.setSort(i);
			abstractDao.update(temp);// 更新temp
			node = temp.getNode();
			node.setSort(temp.getSort());
			abstractDao.update(node);
		}
	}

	/**
	 * 向前移动排序
	 * 
	 * @param bean
	 */
	private void sortByPrevious(long parent, SysDepartment bean) {
		Object[] values = new Object[] { new Long(parent),
				new Integer(bean.getSort()) };
		// 查找前一个对象
		String query = "from SysDepartment a where a.node.parent=? and a.sort>? order by a.sort asc";
		List<SysDepartment> list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// 有记录
			SysDepartment temp = (SysDepartment) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);// 更新bean
			SysTree node = bean.getNode();
			node.setSort(bean.getSort());
			abstractDao.update(node);

			temp.setSort(i);
			abstractDao.update(temp);// 更新temp
			node = temp.getNode();
			node.setSort(temp.getSort());
			abstractDao.update(node);
		}
	}

	/**
	 * 更新
	 * 
	 * @param bean
	 *            SysDepartment
	 * @return boolean
	 */
	public boolean update(SysDepartment bean) {
		if (bean.getNode() != null) {
			List<SysTree> sts = (List<SysTree>) this.sysTreeService
					.getSysTreeList((int) bean.getNode().getId());
			if (sts != null && sts.size() > 0) {
				this.updateSubStatus(sts, bean.getStatus());
			}
		}
		return abstractDao.update(bean);
	}

	/**
	 * 修改子部门的状态
	 * 
	 * @param list
	 * @param status
	 * @return
	 */
	private boolean updateSubStatus(List<SysTree> list, Integer status) {
		List<SysTree> sts = null;
		SysDepartment sdp = null;
		for (SysTree st : list) {
			sts = (List<SysTree>) this.sysTreeService.getSysTreeList((int) st
					.getId());
			if (sts != null && sts.size() > 0) {
				this.updateSubStatus(sts, status);
			}
			sdp = st.getDepartment();
			if (sdp != null) {
				sdp.setStatus(status);
				this.update(sdp);
			}
		}
		return true;
	}
}