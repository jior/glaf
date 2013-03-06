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

package com.glaf.jbpm.assignment;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.jbpm.taskmgmt.exe.Assignable;

import com.glaf.core.identity.User;

public class AssignableHelper {

	public AssignableHelper() {

	}

	public void setActors(Assignable assignable, String actorId) {
		if (StringUtils.isEmpty(actorId)) {
			throw new RuntimeException(" actorId is null ");
		}
		if (actorId.indexOf(",") > 0) {
			Set<String> actorIds = new HashSet<String>();
			StringTokenizer token = new StringTokenizer(actorId, ",");
			while (token.hasMoreTokens()) {
				String elem = token.nextToken();
				if (StringUtils.isNotEmpty(elem)) {
					actorIds.add(elem);
				}
			}
			if (actorIds.size() > 0) {
				int i = 0;
				String[] users = new String[actorIds.size()];
				Iterator<String> iterator = actorIds.iterator();
				while (iterator.hasNext()) {
					users[i++] = iterator.next();
				}
				assignable.setPooledActors(users);
			}
		} else {
			assignable.setActorId(actorId);
		}
	}

	public void setActors(Assignable assignable, Set<String> actorIds) {
		if (actorIds != null && actorIds.size() > 0) {
			if (actorIds.size() == 1) {
				assignable.setActorId(actorIds.iterator().next());
			} else {
				int i = 0;
				String[] users = new String[actorIds.size()];
				Iterator<String> iterator = actorIds.iterator();
				while (iterator.hasNext()) {
					users[i++] = iterator.next();
				}
				assignable.setPooledActors(users);
			}
		} else {
			throw new RuntimeException(" actorIds is null ");
		}
	}

	public String populateActorId(Object obj) {
		String actorId = null;
		if (obj instanceof String) {
			actorId = (String) obj;
		} else if (obj instanceof User) {
			User user = (User) obj;
			actorId = user.getActorId();
		} else {
			throw new RuntimeException(
					"actor object must instanceof String, com.glaf.jbpm.actor.Actor or com.glaf.jbpm.actor.User");
		}
		return actorId;
	}

}