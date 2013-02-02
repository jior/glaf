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

package com.glaf.base.modules.todo.service;

import java.util.*;
import com.glaf.base.modules.todo.model.TMembership;

public interface MembershipService {

	/**
	 * 获取某个成员的下级
	 * 
	 * @param superiorId
	 * @return
	 */
	List<TMembership> getChildMemberships(String superiorId);

	/**
	 * 获取某个成员的上级
	 * 
	 * @param actorId
	 * @return
	 */
	List<TMembership> getMemberships(String actorId);

	/**
	 * 保存某个成员的上级
	 * 
	 * @param actorId
	 * @param memberships
	 */
	void saveMembership(String actorId, List<TMembership> memberships);

}