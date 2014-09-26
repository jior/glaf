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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysDeptRole;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.model.SysUserRole;
import com.glaf.base.modules.sys.query.SysDepartmentQuery;
import com.glaf.base.modules.sys.query.SysUserQuery;
import com.glaf.base.modules.sys.service.ComplexUserService;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysDeptRoleService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserRoleService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.core.base.TreeModel;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.Membership;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.query.MembershipQuery;
import com.glaf.core.service.ITreeModelService;
import com.glaf.core.service.MembershipService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.Tools;

@Service("complexUserService")
@Transactional(readOnly = true)
public class ComplexUserServiceImpl implements ComplexUserService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MembershipService membershipService;

	protected ITreeModelService treeModelService;

	protected SysUserService sysUserService;

	protected SysUserRoleService sysUserRoleService;

	protected SysDepartmentService sysDepartmentService;

	protected SysDeptRoleService sysDeptRoleService;

	protected SysRoleService sysRoleService;

	protected SysTreeService sysTreeService;

	@Transactional
	public boolean createUser(SysUser bean, List<String> roleCodes) {
		boolean ret = false;
		if (sysUserService.findByMail(bean.getEmail()) != null) {
			throw new RuntimeException(bean.getEmail() + " is exist.");
		}
		if (sysUserService.create(bean)) {
			for (String roleCode : roleCodes) {
				SysRole role = sysRoleService.findByCode(roleCode);
				if (role != null) {
					SysDeptRole deptRole = sysDeptRoleService.find(
							bean.getDeptId(), role.getId());
					if (deptRole == null) {
						deptRole = new SysDeptRole();
						deptRole.setDeptId(bean.getDeptId());
						deptRole.setCreateBy("system");
						deptRole.setCreateDate(new Date());
						deptRole.setRole(role);
						deptRole.setSysRoleId(role.getId());
						sysDeptRoleService.create(deptRole);
					}
					if (deptRole != null) {
						Map<String, Object> dataMap = new java.util.HashMap<String, Object>();
						dataMap.put("authorizeFrom", "0");
						dataMap.put("userId", bean.getId());
						dataMap.put("deptRoleId", deptRole.getId());
						SysUserRole userRole = new SysUserRole();
						Tools.populate(userRole, dataMap);
						userRole.setAuthorized(0);
						userRole.setCreateBy("system");
						userRole.setDeptRole(deptRole);
						userRole.setUser(bean);
						userRole.setCreateDate(new Date());
						userRole.setAvailDateStart(new Date());
						userRole.setAvailDateEnd(DateUtils.toDate("2049-10-01"));
						sysUserRoleService.create(userRole);
					}
				}
			}
			ret = true;
		}
		return ret;
	}

	/**
	 * 获取用户管理的分支机构
	 * 
	 * @param actorId
	 * @return
	 */
	public List<TreeModel> getUserManageBranch(String actorId) {
		List<TreeModel> treeModels = new java.util.ArrayList<TreeModel>();

		SysUserQuery qx = new SysUserQuery();
		qx.setAccount(actorId);
		qx.setRoleCode(SysConstants.BRANCH_ADMIN);
		List<SysTree> subTrees = sysTreeService.getRoleUserTrees(qx);
		if (subTrees != null && !subTrees.isEmpty()) {
			for (SysTree tree : subTrees) {
				treeModels.add(tree);
				List<TreeModel> children = treeModelService
						.getChildrenTreeModels(tree.getId());
				if (children != null && !children.isEmpty()) {
					for (TreeModel child : children) {
						if (!treeModels.contains(child)) {
							treeModels.add(child);
						}
					}
				}
			}
		}

		MembershipQuery query = new MembershipQuery();
		query.type(SysConstants.BRANCH_ADMIN);
		query.actorId(actorId);
		List<Membership> list = membershipService.list(query);
		List<Long> deptIds = new ArrayList<Long>();
		if (list != null && !list.isEmpty()) {
			for (Membership m : list) {
				deptIds.add(m.getNodeId());
			}
		}

		logger.debug("分级管理部门编号:" + deptIds);

		if (!deptIds.isEmpty()) {
			for (Long deptId : deptIds) {
				SysDepartment dept = sysDepartmentService.findById(deptId);
				if (dept != null && dept.getNodeId() > 0) {
					treeModels.add(dept.getNode());
					List<TreeModel> children = treeModelService
							.getChildrenTreeModels(dept.getNodeId());
					if (children != null && !children.isEmpty()) {
						for (TreeModel child : children) {
							if (!treeModels.contains(child)) {
								treeModels.add(child);
							}
						}
					}
				}
			}
		}
		return treeModels;
	}

	/**
	 * 获取用户管理的分支机构的编号集合
	 * 
	 * @param actorId
	 * @return
	 */
	public List<Long> getUserManageBranchNodeIds(String actorId) {
		List<Long> nodeIds = new java.util.ArrayList<Long>();
		nodeIds.add(-1L);
		SysUserQuery qx = new SysUserQuery();
		qx.setAccount(actorId);
		qx.setRoleCode(SysConstants.BRANCH_ADMIN);
		List<SysTree> subTrees = sysTreeService.getRoleUserTrees(qx);
		if (subTrees != null && !subTrees.isEmpty()) {
			for (SysTree tree : subTrees) {
				List<TreeModel> children = treeModelService
						.getChildrenTreeModels(tree.getId());
				if (children != null && !children.isEmpty()) {
					for (TreeModel child : children) {
						if (!nodeIds.contains(child.getId())) {
							nodeIds.add(child.getId());
						}
					}
				}
			}
		}

		MembershipQuery query2 = new MembershipQuery();
		query2.type(SysConstants.BRANCH_ADMIN);
		query2.actorId(actorId);
		List<Membership> listx = membershipService.list(query2);
		List<Long> deptIds = new ArrayList<Long>();
		if (listx != null && !listx.isEmpty()) {
			for (Membership m : listx) {
				deptIds.add(m.getNodeId());
			}
		}

		if (!deptIds.isEmpty()) {
			for (Long deptId : deptIds) {
				SysDepartment dept = sysDepartmentService.findById(deptId);
				if (dept != null && dept.getNodeId() > 0) {
					nodeIds.add(dept.getNodeId());
					List<TreeModel> children = treeModelService
							.getChildrenTreeModels(dept.getNodeId());
					if (children != null && !children.isEmpty()) {
						for (TreeModel child : children) {
							if (!nodeIds.contains(child.getId())) {
								nodeIds.add(child.getId());
							}
						}
					}
				}
			}
		}
		return nodeIds;
	}

	/**
	 * 获取用户管理的分支机构(SYS_DEPARTMENT的封装)
	 * 
	 * @param actorId
	 * @return
	 */
	public List<SysDepartment> getUserManageDeptments(String actorId) {
		List<Long> nodeIds = this.getUserManageBranchNodeIds(actorId);
		nodeIds.add(-1L);
		SysDepartmentQuery query = new SysDepartmentQuery();
		query.nodeIds(nodeIds);
		return sysDepartmentService.list(query);
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
	public void setMembershipService(MembershipService membershipService) {
		this.membershipService = membershipService;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(
			SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	@javax.annotation.Resource
	public void setSysDeptRoleService(SysDeptRoleService sysDeptRoleService) {
		this.sysDeptRoleService = sysDeptRoleService;
	}

	@javax.annotation.Resource
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	@javax.annotation.Resource
	public void setSysUserRoleService(SysUserRoleService sysUserRoleService) {
		this.sysUserRoleService = sysUserRoleService;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@javax.annotation.Resource
	public void setTreeModelService(ITreeModelService treeModelService) {
		this.treeModelService = treeModelService;
	}

}
