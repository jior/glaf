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
	 * ����
	 * 
	 * @param bean
	 *            SysDepartment
	 * @return boolean
	 */
	public boolean create(SysDepartment bean) {
		boolean ret = false;
		if (abstractDao.create(bean)) {// �����¼�ɹ�
			bean.setSort((int) bean.getId());// ���������Ϊ�ղ����idֵ
			bean.getNode().setSort(bean.getSort());
			abstractDao.update(bean);
			ret = true;
		}
		return ret;
	}

	/**
	 * ɾ��
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
	 * ɾ��
	 * 
	 * @param bean
	 *            SysDepartment
	 * @return boolean
	 */
	public boolean delete(SysDepartment bean) {
		return sysTreeService.delete(bean.getId());
	}

	/**
	 * ����ɾ��
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
	 * ��������Ҷ���
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
		if (list != null && list.size() > 0) {// �м�¼
			bean = (SysDepartment) list.get(0);
		}
		return bean;
	}

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	public SysDepartment findById(long id) {
		return (SysDepartment) abstractDao.find(SysDepartment.class, new Long(
				id));
	}

	/**
	 * �����Ʋ��Ҷ���
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
		if (list != null && list.size() > 0) {// �м�¼
			bean = (SysDepartment) list.get(0);
		}
		return bean;
	}

	/**
	 * ��������Ҷ���
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
		if (list != null && list.size() > 0) {// �м�¼
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
	 * ��ȡ�û������б�
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
		if (tree.getParent() == 0) {// �ҵ����ڵ�
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
	 * ��ȡ�б�
	 * 
	 * @return List
	 */
	public List<SysDepartment> getSysDepartmentList() {
		String query = "from SysDepartment a ";
		return abstractDao.getList(query, null, null);
	}

	/**
	 * ��ȡ�б�
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	public List<SysDepartment> getSysDepartmentList(int parent) {
		// ��������
		Object[] values = new Object[] { new Long(parent) };
		String query = "from SysDepartment a where a.node.parent=? order by a.sort desc";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * ��ȡ��ҳ�б�
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
		// ��������
		Object[] values = new Object[] { new Long(parent) };
		String query = "select count(*) from SysDepartment a where a.node.parent=?";
		int count = ((Long) abstractDao.getList(query, values, null).iterator()
				.next()).intValue();
		if (count == 0) {// �����Ϊ��
			PageResult pager = new PageResult();
			pager.setPageSize(pageSize);
			return pager;
		}
		// ��ѯ�б�
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
	 * ����
	 * 
	 * @param bean
	 *            SysDepartment
	 * @param operate
	 *            int ����
	 */
	public void sort(long parent, SysDepartment bean, int operate) {
		if (bean == null)
			return;
		if (operate == SysConstants.SORT_PREVIOUS) {// ǰ��
			sortByPrevious(parent, bean);
		} else if (operate == SysConstants.SORT_FORWARD) {// ����
			sortByForward(parent, bean);
		}
	}

	/**
	 * ����ƶ�����
	 * 
	 * @param bean
	 */
	private void sortByForward(long parent, SysDepartment bean) {
		Object[] values = new Object[] { new Long(parent),
				new Integer(bean.getSort()) };
		// ���Һ�һ������
		String query = "from SysDepartment a where a.node.parent=? and a.sort<? order by a.sort desc";
		List<SysDepartment> list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// �м�¼
			SysDepartment temp = (SysDepartment) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);// ����bean
			SysTree node = bean.getNode();
			node.setSort(bean.getSort());
			abstractDao.update(node);

			temp.setSort(i);
			abstractDao.update(temp);// ����temp
			node = temp.getNode();
			node.setSort(temp.getSort());
			abstractDao.update(node);
		}
	}

	/**
	 * ��ǰ�ƶ�����
	 * 
	 * @param bean
	 */
	private void sortByPrevious(long parent, SysDepartment bean) {
		Object[] values = new Object[] { new Long(parent),
				new Integer(bean.getSort()) };
		// ����ǰһ������
		String query = "from SysDepartment a where a.node.parent=? and a.sort>? order by a.sort asc";
		List<SysDepartment> list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// �м�¼
			SysDepartment temp = (SysDepartment) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);// ����bean
			SysTree node = bean.getNode();
			node.setSort(bean.getSort());
			abstractDao.update(node);

			temp.setSort(i);
			abstractDao.update(temp);// ����temp
			node = temp.getNode();
			node.setSort(temp.getSort());
			abstractDao.update(node);
		}
	}

	/**
	 * ����
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
	 * �޸��Ӳ��ŵ�״̬
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