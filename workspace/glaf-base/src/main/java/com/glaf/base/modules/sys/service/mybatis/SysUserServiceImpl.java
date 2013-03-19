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

import com.glaf.core.id.*;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.PageResult;
import com.glaf.core.base.TableModel;
 

import com.glaf.base.modules.sys.mapper.*;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;
import com.glaf.base.modules.sys.service.*;
import com.glaf.base.utils.StringTools;

@Service("sysUserService")
@Transactional(readOnly = true)
public class SysUserServiceImpl implements SysUserService {
	protected final static Log logger = LogFactory
			.getLog(SysUserServiceImpl.class);

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysAccessMapper sysAccessMapper;

	protected SysApplicationMapper sysApplicationMapper;

	protected SysDepartmentService sysDepartmentService;

	protected SysDeptRoleMapper sysDeptRoleMapper;

	protected SysFunctionMapper sysFunctionMapper;

	protected SysPermissionMapper sysPermissionMapper;

	protected SysRoleMapper sysRoleMapper;

	protected SysUserMapper sysUserMapper;

	protected SysUserRoleMapper sysUserRoleMapper;

	protected ITableDataService tableDataService;

	public SysUserServiceImpl() {

	}

	public int count(SysUserQuery query) {
		query.ensureInitialized();
		return sysUserMapper.getSysUserCount(query);
	}

	@Transactional
	public boolean create(SysUser bean) {
		this.save(bean);
		return true;
	}

	@Transactional
	public boolean delete(long id) {
		return false;
	}

	@Transactional
	public boolean delete(SysUser bean) {
		return false;
	}

	@Transactional
	public boolean deleteAll(long[] ids) {
		return false;
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			sysUserMapper.deleteSysUserById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			SysUserQuery query = new SysUserQuery();
			query.rowIds(rowIds);
			sysUserMapper.deleteSysUsers(query);
		}
	}

	@Override
	public SysUser findByAccount(String account) {
		SysUserQuery query = new SysUserQuery();
		query.account(account);
		query.setOrderBy(" E.ID desc ");

		List<SysUser> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			SysUser user = list.get(0);
			user.setDepartment(sysDepartmentService.findById(user.getDeptId()));
			return user;
		}

		return null;
	}

	public SysUser findByAccountWithAll(String account) {
		SysUserQuery query = new SysUserQuery();
		query.account(account);
		query.setOrderBy(" E.ID desc ");

		List<SysUser> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			SysUser user = list.get(0);
			user.setDepartment(sysDepartmentService.findById(user.getDeptId()));
			List<SysUserRole> userRoles = sysUserRoleMapper
					.getSysUserRolesByUserId(user.getId());
			user.getUserRoles().addAll(userRoles);
			List<SysDeptRole> deptRoles = sysDeptRoleMapper
					.getSysDeptRolesByUserId(user.getId());
			user.getRoles().addAll(deptRoles);
			List<SysApplication> apps = sysApplicationMapper
					.getSysApplicationByUserId(user.getId());
			user.getApps().addAll(apps);
			List<SysFunction> funs = sysFunctionMapper
					.getSysFunctionByUserId(user.getId());
			user.getFunctions().addAll(funs);
			return user;
		}

		return null;
	}

	public SysUser findById(long id) {
		SysUser user = this.getSysUser(id);
		if (user != null) {
			user.setDepartment(sysDepartmentService.findById(user.getDeptId()));
		}
		return user;
	}

	protected Map<Long, SysDepartment> getDepartmentMap() {
		Map<Long, SysDepartment> deptMap = new HashMap<Long, SysDepartment>();
		List<SysDepartment> depts = sysDepartmentService.getSysDepartmentList();
		if (depts != null && !depts.isEmpty()) {
			for (SysDepartment dept : depts) {
				deptMap.put(dept.getId(), dept);
			}
		}
		return deptMap;
	}

	public List<SysUser> getSuperiors(String account) {
		List<SysUser> superiors = new ArrayList<SysUser>();
		SysUser bean = this.findByAccount(account);
		if (bean != null && bean.getSuperiorIds() != null) {
			List<String> superiorIds = StringTools.split(bean.getSuperiorIds());
			if (superiorIds != null && !superiorIds.isEmpty()) {
				for (String superiorId : superiorIds) {
					SysUser user = this.findByAccount(superiorId);
					if (user != null && user.getBlocked() == 0) {
						superiors.add(user);
					}
				}
			}
		}
		return superiors;
	}

	public List<SysUser> getSupplierUser(String supplierNo) {
		SysUserQuery query = new SysUserQuery();
		query.account(supplierNo);
		List<SysUser> users = this.list(query);
		this.initUserDepartments(users);
		return users;
	}

	public SysUser getSysUser(Long id) {
		if (id == null) {
			return null;
		}
		SysUser sysUser = sysUserMapper.getSysUserById(id);
		return sysUser;
	}

	public int getSysUserCountByQueryCriteria(SysUserQuery query) {
		return sysUserMapper.getSysUserCount(query);
	}

	public List<SysUser> getSysUserList() {
		SysUserQuery query = new SysUserQuery();
		return this.list(query);
	}

	public List<SysUser> getSysUserList(int deptId) {
		SysUserQuery query = new SysUserQuery();
		query.deptId(Integer.valueOf(deptId));
		return this.list(query);
	}

	public PageResult getSysUserList(int deptId, int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		SysUserQuery query = new SysUserQuery();
		query.deptId(Integer.valueOf(deptId));
		query.accountType(0);
		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.ID asc");

		int start = pageSize * (pageNo - 1);
		List<SysUser> list = this.getSysUsersByQueryCriteria(start, pageSize,
				query);
		this.initUserDepartments(list);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public PageResult getSysUserList(int deptId, String fullName, int pageNo,
			int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		SysUserQuery query = new SysUserQuery();
		query.deptId(Integer.valueOf(deptId));
		query.accountType(0);
		if (fullName != null && fullName.trim().length() > 0) {
			query.nameLike(fullName);
		}
		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.ID asc");

		int start = pageSize * (pageNo - 1);
		List<SysUser> list = this.getSysUsersByQueryCriteria(start, pageSize,
				query);
		this.initUserDepartments(list);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public PageResult getSysUserList(int deptId, String userName,
			String account, int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		SysUserQuery query = new SysUserQuery();
		query.deptId(Integer.valueOf(deptId));
		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.ID asc, E.DEPTID asc ");

		int start = pageSize * (pageNo - 1);
		List<SysUser> list = this.getSysUsersByQueryCriteria(start, pageSize,
				query);
		this.initUserDepartments(list);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public List<SysUser> getSysUsersByQueryCriteria(int start, int pageSize,
			SysUserQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysUser> rows = sqlSessionTemplate.selectList("getSysUsers",
				query, rowBounds);
		this.initUserDepartments(rows);
		return rows;
	}

	public List<SysUser> getSysUserWithDeptList() {
		SysUserQuery query = new SysUserQuery();
		List<SysUser> users = this.list(query);
		if (users != null && !users.isEmpty()) {
			this.initUserDepartments(users);
		}
		return users;
	}

	public SysUser getUserAndPrivileges(SysUser user) {
		SysUser bean = user;
		try {
			bean.setRoles(getUserRoles(bean));
			Iterator<SysDeptRole> roles = bean.getRoles().iterator();
			while (roles.hasNext()) {
				SysDeptRole role = (SysDeptRole) roles.next();
				List<SysApplication> apps = sysApplicationMapper
						.getSysApplicationByRoleId(role.getId());
				List<SysFunction> functions = sysFunctionMapper
						.getSysFunctionByRoleId(role.getId());
				bean.getFunctions().addAll(functions);
				bean.getApps().addAll(apps);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return bean;
	}

	public SysUser getUserPrivileges(SysUser user) {
		SysUser bean = user;
		try {
			bean.setRoles(getUserRoles(bean));
			Iterator<SysDeptRole> roles = bean.getRoles().iterator();
			while (roles.hasNext()) {
				SysDeptRole role = (SysDeptRole) roles.next();
				List<SysApplication> apps = sysApplicationMapper
						.getSysApplicationByRoleId(role.getId());
				List<SysFunction> functions = sysFunctionMapper
						.getSysFunctionByRoleId(role.getId());
				bean.getFunctions().addAll(functions);
				bean.getApps().addAll(apps);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return bean;
	}

	public Set<SysDeptRole> getUserRoles(SysUser user) {
		Set<SysDeptRole> deptRoles = new HashSet<SysDeptRole>();
		SysUserRoleQuery query = new SysUserRoleQuery();
		query.setUserId(user.getId());
		List<SysUserRole> userRoles = sysUserRoleMapper.getSysUserRoles(query);
		if (userRoles != null && !userRoles.isEmpty()) {
			for (SysUserRole userRole : userRoles) {
				if (userRole.getDeptRoleId() != null
						&& userRole.getDeptRoleId() > 0) {
					SysDeptRole deptRole = sysDeptRoleMapper
							.getSysDeptRoleById(userRole.getDeptRoleId());
					deptRoles.add(deptRole);
				}
			}
		}
		return deptRoles;
	}

	protected void initUserDepartments(List<SysUser> users) {
		if (users != null && !users.isEmpty()) {
			List<SysDepartment> depts = sysDepartmentService
					.getSysDepartmentList();
			Map<Long, SysDepartment> deptMap = new HashMap<Long, SysDepartment>();
			if (depts != null && !depts.isEmpty()) {
				for (SysDepartment dept : depts) {
					deptMap.put(dept.getId(), dept);
				}
			}
			for (SysUser user : users) {
				user.setDepartment(deptMap.get(Long.valueOf(user.getDeptId())));
			}
		}
	}

	public boolean isThisPlayer(SysUser user, String code) {
		boolean flag = false;
		Set<SysDeptRole> set = this.getUserRoles(user);
		Iterator<SysDeptRole> it = set.iterator();
		while (it.hasNext()) {
			SysDeptRole deptRole = (SysDeptRole) it.next();
			SysRole role = sysRoleMapper
					.getSysRoleById(deptRole.getSysRoleId());
			if (role != null && StringUtils.equals(role.getCode(), code)) {
				// 代判断用户是否拥有此角色
				flag = true;
				break;
			}
		}

		return flag;
	}

	public List<SysUser> list(SysUserQuery query) {
		query.ensureInitialized();
		List<SysUser> list = sysUserMapper.getSysUsers(query);
		this.initUserDepartments(list);
		return list;
	}

	@Transactional
	public void save(SysUser sysUser) {
		if (sysUser.getId() == 0L) {
			sysUser.setId(idGenerator.nextId());
			// sysUser.setCreateDate(new Date());
			sysUserMapper.insertSysUser(sysUser);
		} else {
			sysUserMapper.updateSysUser(sysUser);
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
	public void setSysAccessMapper(SysAccessMapper sysAccessMapper) {
		this.sysAccessMapper = sysAccessMapper;
	}

	@Resource
	public void setSysApplicationMapper(
			SysApplicationMapper sysApplicationMapper) {
		this.sysApplicationMapper = sysApplicationMapper;
	}

	@Resource
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	@Resource
	public void setSysDeptRoleMapper(SysDeptRoleMapper sysDeptRoleMapper) {
		this.sysDeptRoleMapper = sysDeptRoleMapper;
	}

	@Resource
	public void setSysFunctionMapper(SysFunctionMapper sysFunctionMapper) {
		this.sysFunctionMapper = sysFunctionMapper;
	}

	@Resource
	public void setSysPermissionMapper(SysPermissionMapper sysPermissionMapper) {
		this.sysPermissionMapper = sysPermissionMapper;
	}

	@Resource
	public void setSysRoleMapper(SysRoleMapper sysRoleMapper) {
		this.sysRoleMapper = sysRoleMapper;
	}

	@Resource
	public void setSysUserMapper(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}

	@Resource
	public void setSysUserRoleMapper(SysUserRoleMapper sysUserRoleMapper) {
		this.sysUserRoleMapper = sysUserRoleMapper;
	}

	@Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	@Transactional
	public boolean update(SysUser bean) {
		sysUserMapper.updateSysUser(bean);
		return true;
	}

	@Transactional
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean updateRole(SysUser user, Set delRoles, Set newRoles) {
		// 先删除用户之前的权限
		List<SysUserRole> userRoles = sysUserRoleMapper
				.getSysUserRolesByUserId(user.getId());
		if (userRoles != null && !userRoles.isEmpty()) {
			for (SysUserRole userRole : userRoles) {
				TableModel table = new TableModel();
				table.setTableName("sys_user_role");
				table.addColumn("userId", "Long", user.getId());
				table.addColumn("roleId", "Long", userRole.getDeptRoleId());
				tableDataService.deleteTableData(table);
			}
		}

		// 增加新权限
		if (newRoles != null && !newRoles.isEmpty()) {
			Iterator<Object> iter = newRoles.iterator();
			while (iter.hasNext()) {
				Object object = iter.next();
				if (object instanceof SysUserRole) {
					SysUserRole r = (SysUserRole) object;
					r.setId(idGenerator.nextId());
					sysUserRoleMapper.insertSysUserRole(r);
				}
				if (object instanceof SysDeptRole) {
					SysDeptRole deptRole = (SysDeptRole) object;
					SysUserRole userRole = new SysUserRole();
					userRole.setId(idGenerator.nextId());
					userRole.setUserId(user.getId());
					userRole.setDeptRoleId(deptRole.getId());
					sysUserRoleMapper.insertSysUserRole(userRole);
				}
			}
		}

		return true;
	}

	@Transactional
	public boolean updateUser(SysUser bean) {
		sysUserMapper.updateSysUser(bean);
		return true;
	}

}
