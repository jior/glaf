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

package com.glaf.cluster.catalina.session;

import java.security.Principal;

import org.apache.catalina.Manager;
import org.apache.catalina.session.StandardSession;
 
import java.util.Map;

public class ZooKeeperSession extends StandardSession {

	private static final long serialVersionUID = 1L;
	protected static Boolean manualDirtyTrackingSupportEnabled = false;

	protected static String manualDirtyTrackingAttributeKey = "__changed__";

	public static void setManualDirtyTrackingAttributeKey(String key) {
		manualDirtyTrackingAttributeKey = key;
	}

	public static void setManualDirtyTrackingSupportEnabled(Boolean enabled) {
		manualDirtyTrackingSupportEnabled = enabled;
	}

	protected Map<String, Object> changedAttributes;
	protected Boolean dirty;

	public ZooKeeperSession(Manager manager) {
		super(manager);
		resetDirtyTracking();
	}

	public Map<String, Object> getChangedAttributes() {
		return changedAttributes;
	}

	public Boolean isDirty() {
		return dirty || !changedAttributes.isEmpty();
	}

	@Override
	public void removeAttribute(String name) {
		dirty = true;
		super.removeAttribute(name);
	}

	public void resetDirtyTracking() {
		changedAttributes = new java.util.HashMap<String, Object>();
		dirty = false;
	}

	@Override
	public void setAttribute(String key, Object value) {
		if (manualDirtyTrackingSupportEnabled
				&& manualDirtyTrackingAttributeKey.equals(key)) {
			dirty = true;
			return;
		}

		Object oldValue = getAttribute(key);
		if ((value == null && oldValue != null)
				|| (oldValue == null && value != null)
				|| (value != null && !value.getClass().isInstance(oldValue))
				|| (value != null && !value.equals(oldValue))) {
			changedAttributes.put(key, value);
		}

		super.setAttribute(key, value);
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setPrincipal(Principal principal) {
		dirty = true;
		super.setPrincipal(principal);
	}

}
