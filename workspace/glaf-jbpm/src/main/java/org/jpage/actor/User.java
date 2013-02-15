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

package org.jpage.actor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class User implements Serializable, java.lang.Comparable<User> {

	private static final long serialVersionUID = 1L;

	protected String actorId;

	protected int actorType;

	protected String name;

	protected String mail;

	protected String mobile;

	protected String msn;

	protected boolean isAdmin;

	protected Map properties = new HashMap();

	public int compareTo(User o) {
		if (o == null) {
			return -1;
		}
		if (!(o instanceof User)) {
			throw new ClassCastException();
		}
		User obj = (User) o;
		int ret = 0;
		if (name != null) {
			ret = new CompareToBuilder().append(this.name, obj.name)
					.append(this.actorId, obj.actorId).toComparison();
		}
		return ret;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final User other = (User) obj;
		if (actorId == null) {
			if (other.actorId != null)
				return false;
		} else if (!actorId.equals(other.actorId))
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (mobile == null) {
			if (other.mobile != null)
				return false;
		} else if (!mobile.equals(other.mobile))
			return false;
		if (msn == null) {
			if (other.msn != null)
				return false;
		} else if (!msn.equals(other.msn))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getActorId() {
		return actorId;
	}

	public int getActorType() {
		return actorType;
	}

	public String getMail() {
		return mail;
	}

	public String getMobile() {
		return mobile;
	}

	public String getMsn() {
		return msn;
	}

	public String getName() {
		return name;
	}

	public Map getProperties() {
		return properties;
	}

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((actorId == null) ? 0 : actorId.hashCode());
		result = PRIME * result + ((mail == null) ? 0 : mail.hashCode());
		result = PRIME * result + ((mobile == null) ? 0 : mobile.hashCode());
		result = PRIME * result + ((msn == null) ? 0 : msn.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setActorType(int actorType) {
		this.actorType = actorType;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProperties(Map properties) {
		this.properties = properties;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
