/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.jbpm.support;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jpage.actor.Actor;
import org.jpage.actor.User;
import org.jpage.core.cache.CacheFactory;
import org.jpage.jbpm.service.ActorManagerImpl;
import org.jpage.jbpm.util.Constant;

public class IdentityManagerImpl extends ActorManagerImpl {
	private final static Log logger = LogFactory
			.getLog(IdentityManagerImpl.class);

	public IdentityManagerImpl() {

	}

	/**
	 * 获取用户 此处采用推-拉技术（Push-Pop）
	 * 
	 * @param actorId
	 * @return org.jpage.actor.User
	 */
	public User getUser(JbpmContext jbpmContext, String actorId) {
		actorId = actorId.trim().toLowerCase();
		String cacheKey = USER_CACHE_KEY + actorId;
		if (CacheFactory.get(cacheKey) != null) {
			logger.debug("从缓存中获取用户:" + actorId);
			return (User) CacheFactory.get(cacheKey);
		}
		User user = null;
		Map userMap = this.getUserMap(jbpmContext);
		Iterator iterator = userMap.keySet().iterator();
		while (iterator.hasNext()) {
			String userId = (String) iterator.next();
			userId = userId.trim().toLowerCase();
			if (userId.equalsIgnoreCase(actorId)) {
				user = (User) userMap.get(actorId);
			}
			String tKey = USER_CACHE_KEY + userId;
			CacheFactory.put(tKey, userMap.get(userId));
		}
		return user;
	}

	/**
	 * 获取用户Map。<br>
	 * 其中key是用户帐号，value是org.jpage.actor.User对象。 此处采用推-拉技术（Push-Pop）
	 * 
	 * @return
	 */
	public Map getUserMap(JbpmContext jbpmContext) {
		String cacheKey = "cache_users_xyz";
		if (CacheFactory.get(cacheKey) != null) {
			logger.debug("从缓存中获取用户");
			return (Map) CacheFactory.get(cacheKey);
		}
		Map userMap = new LinkedHashMap();
		SqlMapManager sqlMapManager = (SqlMapManager) ComponentFactory
				.getBean("sqlMapManager");
		List rows = sqlMapManager.query(Constant.JBPM_USERS);
		if (rows != null && rows.size() > 0) {
			Iterator iterator = rows.iterator();
			while (iterator.hasNext()) {
				User user = (User) iterator.next();
				String actorId = user.getActorId();
				actorId = actorId.trim().toLowerCase();
				user.setActorId(actorId);
				userMap.put(actorId, user);
			}
		}
		logger.debug("获取用户数目:" + userMap.size());
		CacheFactory.put(cacheKey, userMap);
		return userMap;
	}

	/**
	 * 获取角色Map。<br>
	 * 其中key是角色ID，value是org.jpage.actor.Actor对象。
	 * 
	 * @return
	 */
	public Map getRoleMap(JbpmContext jbpmContext) {
		String cacheKey = "cache_roles_xyz";
		if (CacheFactory.get(cacheKey) != null) {
			logger.debug("从缓存中获取角色");
			return (Map) CacheFactory.get(cacheKey);
		}
		Map roleMap = new LinkedHashMap();
		SqlMapManager sqlMapManager = (SqlMapManager) ComponentFactory
				.getBean("sqlMapManager");
		List rows = sqlMapManager.query(Constant.JBPM_ROLES);
		if (rows != null && rows.size() > 0) {
			Iterator iterator = rows.iterator();
			while (iterator.hasNext()) {
				Actor actor = (Actor) iterator.next();
				roleMap.put(actor.getActorId(), actor);
			}
		}
		logger.debug("获取角色数目:" + roleMap.size());
		CacheFactory.put(cacheKey, roleMap);
		return roleMap;
	}

}
