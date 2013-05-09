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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.security.Authentication;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.MembershipService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.base.TableModel;
import com.glaf.core.domain.Membership;

import com.glaf.base.modules.Constants;
import com.glaf.base.modules.sys.mapper.*;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;
import com.glaf.base.modules.sys.service.*;

@Service("sysUserRoleService")
@Transactional(readOnly = true)
public class SysUserRoleServiceImpl implements SysUserRoleService {
	protected final static Log logger = LogFactory
			.getLog(SysUserRoleServiceImpl.class);

	protected IdGenerator idGenerator;

	protected MembershipService membershipService;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysDepartmentService sysDepartmentService;

	protected SysDeptRoleMapper sysDeptRoleMapper;

	protected SysUserMapper sysUserMapper;

	protected SysRoleMapper sysRoleMapper;

	protected SysUserRoleMapper sysUserRoleMapper;

	protected SysUserService sysUserService;

	protected ITableDataService tableDataService;

	public SysUserRoleServiceImpl() {

	}

	@Transactional
	public boolean addRole(long fromUserId, long toUserId, String startDate,
			String endDate, int mark, String processNames,
			String processDescriptions) {
		boolean ret = false;
		if (null != processNames && processNames.length() > 0)
			processNames = processNames.substring(0,
					processNames.lastIndexOf(","));
		if (null != processDescriptions && processDescriptions.length() > 0)
			processDescriptions = processDescriptions.substring(0,
					processDescriptions.lastIndexOf(","));
		SysUser fromUser = sysUserService.findById(fromUserId);
		SysUser toUser = sysUserService.findById(toUserId);
		if (fromUser == null || toUser == null || fromUserId == toUserId) {
			return ret;
		}
		// 取出授权人fromUser的角色集合
		SysUserRoleQuery query = new SysUserRoleQuery();
		query.userId(fromUserId);
		query.authorized(0);
		List<SysUserRole> userRoles = this.list(query);
		Iterator<SysUserRole> iter = userRoles.iterator();
		while (iter.hasNext()) {
			SysUserRole userRole = (SysUserRole) iter.next();
			logger.debug("##" + userRole.getUserId() + ":"
					+ userRole.getDeptRoleId());
			// 授给被授权人
			SysUserRole bean = new SysUserRole();
			bean.setId(this.idGenerator.nextId());
			bean.setAuthorizeFrom(fromUser.getId());
			bean.setUser(toUser);
			bean.setUserId(toUser.getId());
			bean.setDeptRoleId(userRole.getDeptRoleId());
			bean.setAuthorized(1);
			bean.setAvailDateStart(DateUtils.toDate(startDate));
			bean.setAvailDateEnd(DateUtils.toDate(endDate));
			bean.setProcessDescription(processDescriptions);
			if (mark == 1) {
				bean.setProcessDescription("全局代理");
			}

			bean.setCreateDate(new Date());
			bean.setCreateBy(Authentication.getAuthenticatedActorId());

			sysUserRoleMapper.insertSysUserRole(bean);

		}
		// 增加工作流
		insertAgent(fromUser, toUser, startDate, endDate, mark, processNames);
		return true;
	}

	public int count(SysUserRoleQuery query) {
		query.ensureInitialized();
		return sysUserRoleMapper.getSysUserRoleCount(query);
	}

	@Transactional
	public boolean create(SysUserRole bean) {
		if (bean.getId() == 0) {
			bean.setId(idGenerator.nextId());
		}
		bean.setCreateDate(new Date());
		sysUserRoleMapper.insertSysUserRole(bean);
		return true;
	}

	@Transactional
	public boolean delete(long id) {
		this.deleteById(id);
		return true;
	}

	@Transactional
	public boolean delete(SysUserRole bean) {
		this.deleteById(bean.getId());
		return true;
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			sysUserRoleMapper.deleteSysUserRoleById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			SysUserRoleQuery query = new SysUserRoleQuery();
			query.rowIds(rowIds);
			sysUserRoleMapper.deleteSysUserRoles(query);
		}
	}

	public SysUserRole findById(long id) {
		return this.getSysUserRole(id);
	}

	public PageResult getAllAuthorizedUser(Map<String, String> filter) {
		int pageNo = 1;
		if ((String) filter.get("page_no") != null) {
			pageNo = Integer.parseInt((String) filter.get("page_no"));
		}
		int pageSize = 2 * Constants.PAGE_SIZE;
		if ((String) filter.get("page_size") != null) {
			pageSize = Integer.parseInt((String) filter.get("page_size"));
		}

		SysUserQuery query = new SysUserQuery();

		String deptId = (String) filter.get("deptId");
		if (deptId != null) {
			query.deptId(Long.parseLong(deptId));
		}

		String name = (String) filter.get("name");
		if (name != null) {
			query.nameLike(name);
		}

		String startDate = (String) filter.get("startDate");
		if (startDate != null) {
			query.setAvailDateStartGreaterThanOrEqual(DateUtils
					.toDate(startDate));
		}

		String endDate = (String) filter.get("endDate");
		if (endDate != null) {
			query.setAvailDateEndLessThanOrEqual(DateUtils.toDate(endDate));
		}

		// 计算总数
		PageResult pager = new PageResult();

		int count = sysUserMapper.getCountAuthorizedUsers(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.SORT desc");

		int start = pageSize * (pageNo - 1);

		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysUser> rows = sqlSessionTemplate.selectList(
				"getAuthorizedUsers", query, rowBounds);
		this.initUserDepartments(rows);
		this.initUserRoles(rows);
		pager.setResults(rows);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getAuthorizedUser(SysUser user) {
		List list = new ArrayList();
		Map<Long, SysUser> userMap = new HashMap<Long, SysUser>();
		List<SysUser> users = sysUserMapper.getAuthorizedUsersByUserId(user
				.getId());
		if (users != null && !users.isEmpty()) {
			for (SysUser u : users) {
				userMap.put(u.getId(), u);
			}
		}

		this.initUserDepartments(users);

		SysUserRoleQuery query = new SysUserRoleQuery();
		query.authorizeFrom(user.getId());

		List<SysUserRole> roles = this.list(query);
		if (roles != null && !roles.isEmpty()) {
			for (SysUserRole role : roles) {
				Object[] array = new Object[4];
				array[0] = userMap.get(role.getUserId());
				array[1] = role.getAvailDateStart();
				array[2] = role.getAvailDateEnd();
				array[3] = role.getProcessDescription();
				list.add(array);
			}
		}

		return list;
	}

	public List<SysUser> getChildrenMembershipUsers(long deptId, long roleId) {
		List<SysUser> users = new ArrayList<SysUser>();
		SysDepartment dept = sysDepartmentService.findById(deptId);
		if (dept != null) {
			List<SysDepartment> list = new ArrayList<SysDepartment>();
			sysDepartmentService.findNestingDepartment(list, deptId);
			if (!list.isEmpty()) {
				for (SysDepartment dp : list) {
					List<SysUser> userlist = getMembershipUsers(
							(int) dp.getId(), roleId);
					if (!userlist.isEmpty()) {
						users.addAll(userlist);
					}
				}
			} else {
				return this.getMembershipUsers(deptId, roleId);
			}
		}
		return users;
	}

	public List<SysUser> getMembershipUsers(List<Long> deptIds, long roleId) {
		List<SysUser> users = new ArrayList<SysUser>();
		for (Long deptId : deptIds) {
			List<SysUser> list = this.getMembershipUsers(deptId, roleId);
			if (!list.isEmpty()) {
				users.addAll(list);
			}
		}
		this.initUserDepartments(users);
		return users;
	}

	public List<SysUser> getMembershipUsers(long deptId, long roleId) {
		List<SysUser> users = new ArrayList<SysUser>();
		SysDepartment dept = sysDepartmentService.findById(deptId);
		if (dept != null && dept.getRoles() != null) {
			Set<SysDeptRole> roles = dept.getRoles();
			for (SysDeptRole role : roles) {
				if (role.getRole() != null && role.getRole().getId() == roleId) {
					if (role.getUsers() != null && !role.getUsers().isEmpty()) {
						users.addAll(role.getUsers());
					}
				}
			}
		}
		this.initUserDepartments(users);
		return users;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getProcessByUser(SysUser user) {
		List list = new ArrayList();
		List<Map<String, Object>> rows = sysUserRoleMapper
				.getProcessByUser(user.getId());
		if (rows != null && !rows.isEmpty()) {
			for (Map<String, Object> dataMap : rows) {
				Object[] array = new Object[3];
				array[0] = dataMap.get("moduleName");
				array[1] = dataMap.get("processName");
				array[2] = dataMap.get("objectValue");
				list.add(array);
			}
		}

		return list;
	}

	public SysUserRole getSysUserRole(Long id) {
		if (id == null) {
			return null;
		}
		SysUserRole sysUserRole = sysUserRoleMapper.getSysUserRoleById(id);
		return sysUserRole;
	}

	public int getSysUserRoleCountByQueryCriteria(SysUserRoleQuery query) {
		return sysUserRoleMapper.getSysUserRoleCount(query);
	}

	public List<SysUserRole> getSysUserRolesByQueryCriteria(int start,
			int pageSize, SysUserRoleQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysUserRole> rows = sqlSessionTemplate.selectList(
				"getSysUserRoles", query, rowBounds);
		return rows;
	}

	public List<SysUser> getUnAuthorizedUser(SysUser user) {
		if (user == null) {
			return new ArrayList<SysUser>();
		}
		logger.info("name:" + user.getName());
		user = sysUserService.findById(user.getId());

		// 取所在部门,判断是否还有下级部门
		SysDepartment dept = user.getDepartment();
		logger.info("dept:" + dept.getName());
		List<SysUser> list = sysUserService.getSysUserList((int) dept.getId());

		// 循环取各部门用户
		List<SysDepartment> deptList = sysDepartmentService
				.getSysDepartmentList((int) dept.getId());
		Iterator<SysDepartment> iter = deptList.iterator();
		while (iter.hasNext()) {
			SysDepartment dept2 = (SysDepartment) iter.next();
			logger.info("dept:" + dept2.getName());
			list.addAll(sysUserService.getSysUserList((int) dept2.getId()));
		}
		// 除去自己
		list.remove(user);
		// 除去已授权用户
		list.removeAll(getAuthorizedUser(user));

		return list;
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

	protected void initUserRoles(List<SysUser> users) {
		if (users != null && !users.isEmpty()) {
			for (SysUser user : users) {
				List<SysUserRole> userRoles = sysUserRoleMapper
						.getSysUserRolesByUserId(user.getId());
				user.getUserRoles().addAll(userRoles);
			}
		}
	}

	@Transactional
	public void insertAgent(SysUser fromUser, SysUser toUser, String startDate,
			String endDate, int mark, String processNames) {
		if (endDate.length() == 10) {
			endDate += " 23:59:59";
		}
		if (mark == 1) {// 全局代理
			TableModel table = new TableModel();
			table.setTableName("SYS_AGENT");
			table.addColumn("ID_", "String",
					String.valueOf(idGenerator.getNextId()));
			table.addColumn("ASSIGNTO_", "String", toUser.getAccount());
			table.addColumn("ASSIGNFROM_", "String", fromUser.getAccount());
			table.addColumn("AGENTTYPE_", "Integer", 0);
			table.addColumn("STARTDATE_", "Date", DateUtils.toDate(startDate));
			table.addColumn("ENDDATE_", "Date", DateUtils.toDate(endDate));
			table.addColumn("SERVICEKEY_", "String", "JBPM");
			table.addColumn("LOCKED_", "Integer", 0);
			tableDataService.insertTableData(table);
		} else {
			String[] ss = processNames.split(",");
			for (int i = 0; i < ss.length; i++) {
				String processName = ss[i];
				TableModel table = new TableModel();
				table.setTableName("SYS_AGENT");
				table.addColumn("ID_", "String",
						String.valueOf(idGenerator.getNextId()));
				table.addColumn("ASSIGNTO_", "String", toUser.getAccount());
				table.addColumn("ASSIGNFROM_", "String", fromUser.getAccount());
				table.addColumn("AGENTTYPE_", "Integer", 0);
				table.addColumn("STARTDATE_", "Date",
						DateUtils.toDate(startDate));
				table.addColumn("ENDDATE_", "Date", DateUtils.toDate(endDate));
				table.addColumn("SERVICEKEY_", "String", "JBPM");
				table.addColumn("PROCESSNAME_", "String", processName);
				table.addColumn("LOCKED_", "Integer", 0);
				tableDataService.insertTableData(table);
			}
		}
	}

	public boolean isAuthorized(long fromUserId, long toUserId) {
		SysUserRoleQuery query = new SysUserRoleQuery();
		query.userId(toUserId);
		query.authorizeFrom(fromUserId);

		int count = this.count(query);
		logger.info("count:" + count + ",fromUserId:" + fromUserId
				+ ",toUserId:" + toUserId);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<SysUserRole> list(SysUserRoleQuery query) {
		query.ensureInitialized();
		List<SysUserRole> list = sysUserRoleMapper.getSysUserRoles(query);
		return list;
	}

	@Transactional
	public void removeAgent(SysUser fromUser, SysUser toUser) {
		TableModel table = new TableModel();
		table.setTableName("SYS_AGENT");
		table.addColumn("ASSIGNTO_", "String", toUser.getAccount());
		table.addColumn("ASSIGNFROM_", "String", fromUser.getAccount());
		tableDataService.deleteTableData(table);
	}

	@Transactional
	public boolean removeRole(long fromUserId, long toUserId) {
		boolean ret = false;
		SysUser fromUser = sysUserService.findById(fromUserId);
		SysUser toUser = sysUserService.findById(toUserId);
		if (fromUser == null || toUser == null)
			return ret;

		// 取出被授权人toUser的角色集合
		SysUserRoleQuery query = new SysUserRoleQuery();
		query.userId(toUser.getId());

		List<SysUserRole> userRoles = this.list(query);
		Iterator<SysUserRole> iter = userRoles.iterator();
		while (iter.hasNext()) {
			SysUserRole userRole = (SysUserRole) iter.next();
			// 判断是否是授权人
			if (userRole.getAuthorizeFrom() != 0) {
				delete(userRole);// 删除权限
			}
		}
		// 删除工作流授权
		removeAgent(fromUser, toUser);
		return true;
	}

	@Transactional
	public boolean removeRoles() {
		SysUserRoleQuery query = new SysUserRoleQuery();
		query.availDateEndLessThanOrEqual(new Date());
		query.authorized(1);

		// 取出被授权人toUser的角色集合
		List<SysUserRole> userRoles = this.list(query);
		Iterator<SysUserRole> iter = userRoles.iterator();
		while (iter.hasNext()) {
			SysUserRole userRole = (SysUserRole) iter.next();
			long fromUserId = userRole.getAuthorizeFrom();
			SysUser fromUser = sysUserService.findById(fromUserId);
			SysUser toUser = userRole.getUser();
			// 判断是否是授权人
			logger.info("toUser:" + toUser.getName() + ",fromUser:"
					+ fromUser.getName() + ",remove role:"
					+ userRole.getDeptRole().getRole().getName()
					+ ",availDateEnd=" + userRole.getAvailDateEnd());
			delete(userRole);// 删除权限
			removeAgent(fromUser, toUser);// 删除工作流
		}
		return true;
	}

	@Transactional
	public void save(SysUserRole sysUserRole) {
		if (sysUserRole.getId() == 0) {
			sysUserRole.setId(idGenerator.nextId());
			sysUserRole.setCreateDate(new Date());
			sysUserRoleMapper.insertSysUserRole(sysUserRole);
		} else {
			sysUserRoleMapper.updateSysUserRole(sysUserRole);
		}

		SysDeptRole sysDeptRole = sysDeptRoleMapper
				.getSysDeptRoleById(sysUserRole.getDeptRoleId());
		if (sysDeptRole != null) {
			SysUser user = sysUserMapper
					.getSysUserById(sysUserRole.getUserId());
			Membership membership = new Membership();
			membership.setActorId(user.getAccount());
			membership.setModifyBy(sysUserRole.getCreateBy());
			membership.setModifyDate(new java.util.Date());
			membership.setNodeId(sysDeptRole.getDeptId());
			membership.setRoleId(sysDeptRole.getSysRoleId());
			membership.setObjectId("SYS_USER_ROLE");
			membership.setObjectValue(String.valueOf(sysUserRole.getId()));
			membership.setType("SysUserRole");
			membershipService.save(membership);
		}
	}

	@Resource
	@Qualifier("myBatisDbIdGenerator")
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setMembershipService(MembershipService membershipService) {
		this.membershipService = membershipService;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Resource
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	@Resource
	public void setSysRoleMapper(SysRoleMapper sysRoleMapper) {
		this.sysRoleMapper = sysRoleMapper;
	}

	@Resource
	public void setSysDeptRoleMapper(SysDeptRoleMapper sysDeptRoleMapper) {
		this.sysDeptRoleMapper = sysDeptRoleMapper;
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
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	@Transactional
	public boolean update(SysUserRole bean) {
		this.save(bean);
		return true;
	}

}
