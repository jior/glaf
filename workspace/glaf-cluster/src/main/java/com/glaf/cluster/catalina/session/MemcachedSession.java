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

import com.whalin.MemCached.*;
import org.apache.catalina.*;
import org.apache.catalina.session.StandardSession;

public class MemcachedSession extends StandardSession {
	private static final long serialVersionUID = -1L;
	protected transient MemCachedClient memCachedClient = null;

	public MemcachedSession(Manager manager, MemCachedClient memCachedClient) {
		super(manager);
		this.memCachedClient = memCachedClient;
	}

	public void expire(boolean notify) {
		memCachedClient.delete(((MemcachedManager) manager).getPerfix()
				+ this.getId());
		super.expire(notify);
	}

	public Object getAttribute(String name) {
		Object obj = super.getAttribute(name);
		if (obj != null && !(obj instanceof java.io.Serializable)) {
			return obj;
		}
		String key = name + this.getId();
		obj = memCachedClient.get(key);
		return obj;
	}

	protected void removeAttributeInternal(String name, boolean notify) {
		super.removeAttributeInternal(name, notify);
		String key = name + this.getId();
		memCachedClient.delete(key);
	}

	public void setAttribute(String name, Object value) {
		removeAttribute(name);
		super.setAttribute(name, value);
		if (value != null && value instanceof java.io.Serializable) {
			String key = name + this.getId();
			memCachedClient.set(key, value);
		}
	}

}
