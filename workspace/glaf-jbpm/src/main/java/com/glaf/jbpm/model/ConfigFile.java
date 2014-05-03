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

package com.glaf.jbpm.model;

public class ConfigFile {

	protected long id;

	protected String filename;

	protected long lastModified = -1;

	public ConfigFile() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfigFile other = (ConfigFile) obj;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (id != other.id)
			return false;
		if (lastModified != other.lastModified)
			return false;
		return true;
	}

	public String getFilename() {
		return filename;
	}

	public long getId() {
		return id;
	}

	public long getLastModified() {
		return lastModified;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (lastModified ^ (lastModified >>> 32));
		return result;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public String toString() {
		return "ConfigFile [id=" + id + ", filename=" + filename
				+ ", lastModified=" + lastModified + "]";
	}

}