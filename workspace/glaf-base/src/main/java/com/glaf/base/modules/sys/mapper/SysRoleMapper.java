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

package com.glaf.base.modules.sys.mapper;

import java.util.*;
import org.springframework.stereotype.Component;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;

@Component
public interface SysRoleMapper {

	void deleteSysRoles(SysRoleQuery query);

	void deleteSysRoleById(Long id);

	SysRole getSysRoleById(Long id);

	int getSysRoleCount(SysRoleQuery query);
	
	List<SysRole> getSysRolesOfUser(String actorId);

	List<SysRole> getSysRoles(SysRoleQuery query);
	
	List<SysRole> getSysRolesOfDeptRole(Long roleId);
	
	List<SysRole> getSysRolesOfDepts(SysDepartmentQuery query);

	void insertSysRole(SysRole model);

	void updateSysRole(SysRole model);

}
