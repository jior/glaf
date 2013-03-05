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

package com.glaf.base.modules.todo.dao;

import java.sql.*;
import java.util.*;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.glaf.base.modules.todo.model.UserEntity;

public class TodoDAOImpl extends HibernateDaoSupport implements TodoDAO {

	public TodoDAOImpl() {

	}

	public void saveAll(final List<Object> rows) {
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				if (rows != null && rows.size() > 0) {
					Iterator<Object> iter = rows.iterator();
					while (iter.hasNext()) {
						Object obj = iter.next();
						session.save(obj);
					}
				}
				return null;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<UserEntity> getUserEntityList(final String actorId) {
		return (List<UserEntity>) getHibernateTemplate().execute(
				new HibernateCallback<Object>() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						StringBuffer sb = new StringBuffer();
						sb.append(
								" SELECT distinct d.account actorId, b.deptId deptId, b.sysRoleId roleId ")
								.append(" FROM sys_user_role a INNER JOIN ")
								.append(" sys_dept_role b ON a.roleId = b.id INNER JOIN ")
								.append(" sys_role c ON b.sysRoleId = c.id INNER JOIN ")
								.append(" sys_user d ON a.userId = d.id ")
								.append(" WHERE ( d.account = :account ) ");
						logger.debug("@@actorId:"+actorId);
						SQLQuery query = session.createSQLQuery(sb.toString());
						query.setParameter("account", actorId);

						List<?> list = query.list();
						List<UserEntity> rows = new ArrayList<UserEntity>();
						for (Object object : list) {
							Object[] array = (Object[]) object;
							UserEntity m = new UserEntity();
							m.setActorId((String) array[0]);
							if (array[1] != null) {
								m.setDeptId(Long.parseLong(array[1].toString()));
							}
							if (array[2] != null) {
								m.setRoleId(Long.parseLong(array[2].toString()));
							}
							rows.add(m);
						}
						return rows;
					}
				});
	}

}