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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysDeptRole;
import com.glaf.base.modules.sys.model.SysFunction;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.*;
import com.glaf.core.util.PageResult;

public class SysDeptRoleServiceImpl implements SysDeptRoleService {
	private static final Log logger = LogFactory
			.getLog(SysDeptRoleServiceImpl.class);
	private AbstractSpringDao abstractDao;
	private SysApplicationService sysApplicationService;
	private SysFunctionService sysFunctionService;
	private SysRoleService sysRoleService;

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
		logger.info("setAbstractDao");
	}

	public void setSysApplicationService(
			SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
		logger.info("setSysApplicationService");
	}

	public void setSysFunctionService(SysFunctionService sysFunctionService) {
		this.sysFunctionService = sysFunctionService;
		logger.info("setSysFunctionService");
	}

	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
		logger.info("setSysRoleService");
	}

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysDeptRole
	 * @return boolean
	 */
	public boolean create(SysDeptRole bean) {
		return abstractDao.create(bean);
	}

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysDeptRole
	 * @return boolean
	 */
	public boolean update(SysDeptRole bean) {
		return abstractDao.update(bean);
	}

	/**
	 * ɾ��
	 * 
	 * @param bean
	 *            SysDeptRole
	 * @return boolean
	 */
	public boolean delete(SysDeptRole bean) {
		return abstractDao.delete(bean);
	}

	/**
	 * ɾ��
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	public boolean delete(long id) {
		SysDeptRole bean = findById(id);
		if (bean != null) {
			return delete(bean);
		} else {
			return false;
		}
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
			SysDeptRole bean = findById(id[i]);
			if (bean != null)
				list.add(bean);
		}
		return abstractDao.deleteAll(list);
	}

	public boolean deleteByDept(long deptId) {
		Object[] values = new Object[] { new Long(deptId) };
		String hql = "delete from SysDeptRole a where a.dept.id=?";
		return abstractDao.execute(hql, values, null);
	}

	/**
	 * ��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	public SysDeptRole findById(long id) {
		return (SysDeptRole) abstractDao.find(SysDeptRole.class, new Long(id));
	}

	/**
	 * 
	 * @param deptId
	 * @param roleId
	 * @return
	 */
	public SysDeptRole find(long deptId, long roleId) {
		SysDeptRole bean = null;
		Object[] values = new Object[] { new Long(deptId), new Long(roleId) };
		String query = "from SysDeptRole a where a.dept.id=? and a.role.id=? order by a.id desc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// �м�¼
			bean = (SysDeptRole) list.get(0);
		}
		return bean;
	}

	/**
	 * 
	 * @param deptId
	 * @param code
	 * @return
	 */
	public Set<SysUser> findRoleUser(long deptId, String code) {
		SysDeptRole deptRole = null;

		SysRole role = sysRoleService.findByCode(code);
		if (role != null) {
			deptRole = find(deptId, role.getId());
		}
		if (deptRole == null) {
			deptRole = new SysDeptRole();
		}
		return deptRole.getUsers();
	}

	/**
	 * ��ȡ��ҳ�б�
	 * 
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	public PageResult getRoleList(long deptId, int pageNo, int pageSize) {
		// ��������
		Object[] values = new Object[] { new Long(deptId) };
		String query = "select count(*) from SysDeptRole a where a.dept.id=?";
		int count = ((Long) abstractDao.getList(query, values, null).iterator()
				.next()).intValue();
		if (count == 0) {// �����Ϊ��
			PageResult pager = new PageResult();
			pager.setPageSize(pageSize);
			return pager;
		}
		// ��ѯ�б�
		query = "from SysDeptRole a where a.dept.id=? order by a.sort desc";
		return abstractDao.getList(query, values, pageNo, pageSize, count);
	}

	/**
	 * ��ȡ�б�
	 * 
	 * @return List
	 */
	public List getRoleList(long deptId) {
		Object[] values = new Object[] { new Long(deptId) };
		String query = "from SysDeptRole a where a.dept.id=? order by a.sort desc";
		return abstractDao.getList(query, values, null);
	}

	/**
	 * ����
	 * 
	 * @param bean
	 *            SysDeptRole
	 * @param operate
	 *            int ����
	 */
	public void sort(SysDeptRole bean, int operate) {
		if (bean == null)
			return;
		if (operate == SysConstants.SORT_PREVIOUS) {// ǰ��
			sortByPrevious(bean);
		} else if (operate == SysConstants.SORT_FORWARD) {// ����
			sortByForward(bean);
		}
	}

	/**
	 * ��ǰ�ƶ�����
	 * 
	 * @param bean
	 */
	private void sortByPrevious(SysDeptRole bean) {
		Object[] values = new Object[] { bean.getDept(),
				new Integer(bean.getSort()) };
		// ����ǰһ������
		String query = "from SysDeptRole a where a.dept=? and a.sort>? order by a.sort asc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// �м�¼
			SysDeptRole temp = (SysDeptRole) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);// ����bean

			temp.setSort(i);
			abstractDao.update(temp);// ����temp
		}
	}

	/**
	 * ����ƶ�����
	 * 
	 * @param bean
	 */
	private void sortByForward(SysDeptRole bean) {
		Object[] values = new Object[] { bean.getDept(),
				new Integer(bean.getSort()) };
		// ���Һ�һ������
		String query = "from SysDeptRole a where a.dept=? and a.sort<? order by a.sort desc";
		List list = abstractDao.getList(query, values, null);
		if (list != null && list.size() > 0) {// �м�¼
			SysDeptRole temp = (SysDeptRole) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			abstractDao.update(bean);// ����bean

			temp.setSort(i);
			abstractDao.update(temp);// ����temp
		}
	}

	/**
	 * ���ý�ɫ��Ӧ��ģ�顢����
	 * 
	 * @param roleId
	 * @param appId
	 * @param funcId
	 * @return
	 */
	public boolean saveRoleApplication(long roleId, long[] appId, long[] funcId) {
		boolean ret = false;
		SysDeptRole role = findById(roleId);

		if (appId != null) {
			// ���ý�ɫ��Ӧ��ģ�����Ȩ��
			Set apps = new HashSet();
			for (int i = 0; i < appId.length; i++) {
				logger.info("app id:" + appId[i]);
				SysApplication bean = sysApplicationService.findById(appId[i]);
				if (bean != null) {
					apps.add(bean);
				}
			}
			role.setApps(apps);
		}

		// ����ģ���Ӧ�Ĺ��ܲ���Ȩ��
		if (funcId != null) {
			Set functions = new HashSet();
			for (int i = 0; i < funcId.length; i++) {
				logger.info("function id:" + funcId[i]);
				SysFunction bean = sysFunctionService.findById(funcId[i]);
				if (bean != null) {
					functions.add(bean);
				}
			}
			role.setFunctions(functions);
		}
		ret = update(role);
		return ret;
	}

}