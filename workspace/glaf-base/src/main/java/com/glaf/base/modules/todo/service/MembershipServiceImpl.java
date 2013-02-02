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
import com.glaf.base.dao.AbstractSpringDao;
import com.glaf.base.modules.todo.model.TMembership;

public class MembershipServiceImpl implements MembershipService {

	private AbstractSpringDao abstractDao;

	public AbstractSpringDao getAbstractDao() {
		return abstractDao;
	}

	public void setAbstractDao(AbstractSpringDao abstractDao) {
		this.abstractDao = abstractDao;
	}

	/**
	 * 保存某个成员的上级
	 * 
	 * @param actorId
	 * @param memberships
	 */
	public void saveMembership(String actorId, List memberships) {
		String hsql = " delete from com.glaf.base.modules.todo.model.TMembership where actorId = '"
				+ actorId + "'";
		abstractDao.execute(hsql);
		if (memberships != null && memberships.size() > 0) {
			Date date = new Date();
			Iterator iterator = memberships.iterator();
			while (iterator.hasNext()) {
				TMembership m = (TMembership) iterator.next();
				m.setActorId(actorId);
				m.setModifyDate(date);
			}
			abstractDao.saveAll(memberships);
		}
	}

	/**
	 * 获取某个成员的上级
	 * 
	 * @param actorId
	 * @return
	 */
	public List getMemberships(String actorId) {
		String hsql = " select a from com.glaf.base.modules.todo.model.TMembership a where a.actorId = :actorId ";
		Map params = new HashMap();
		params.put("actorId", actorId);
		return abstractDao.getList(hsql, params);
	}

	/**
	 * 获取某个成员的下级
	 * 
	 * @param superiorId
	 * @return
	 */
	public List getChildMemberships(String superiorId) {
		String hsql = " select a from com.glaf.base.modules.todo.model.TMembership a where a.superiorId = :superiorId ";
		Map params = new HashMap();
		params.put("superiorId", superiorId);
		return abstractDao.getList(hsql, params);
	}

}