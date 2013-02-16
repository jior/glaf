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


package org.jpage.jbpm.bytes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DataByteArray implements Serializable {

	private static final long serialVersionUID = 1L;

	protected long id = 0;

	protected String name = null;

	protected String objectId = null;

	protected String objectValue = null;

	protected int objectType = 0;

	protected String actorId = null;

	protected Date createDate = null;

	protected long versionNo = 0;

	protected String processInstanceId = null;

	protected List byteBlocks = null;

	public DataByteArray() {
	}

	public DataByteArray(byte[] bytes) {
		this.byteBlocks = DataByteBlockChopper.chopItUp(bytes);
	}

	public DataByteArray(String name, byte[] bytes) {
		this(bytes);
		this.name = name;
	}

	public DataByteArray(String processInstanceId, String name, byte[] bytes) {
		this(bytes);
		this.name = name;
		this.processInstanceId = processInstanceId;
	}

	public DataByteArray(DataByteArray other) {
		this.byteBlocks = new ArrayList(other.getByteBlocks());
		this.name = other.name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public int getObjectType() {
		return objectType;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}

	public byte[] getBytes() {
		return DataByteBlockChopper.glueChopsBackTogether(byteBlocks);
	}

	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof DataByteArray)) {
			return false;
		}
		DataByteArray other = (DataByteArray) o;
		return Arrays.equals(DataByteBlockChopper
				.glueChopsBackTogether(byteBlocks), DataByteBlockChopper
				.glueChopsBackTogether(other.byteBlocks));
	}

	public int hashCode() {
		if (byteBlocks == null) {
			return 0;
		}
		return byteBlocks.hashCode();
	}

	public List getByteBlocks() {
		return byteBlocks;
	}
}
