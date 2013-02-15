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
import java.util.List;

public class FileByteArray implements Serializable {

	private static final long serialVersionUID = 1L;

	protected long id = 0;

	protected String name = null;

	protected String processInstanceId = null;

	protected List byteBlocks = null;

	public FileByteArray() {
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

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public FileByteArray(byte[] bytes) {
		this.byteBlocks = FileByteBlockChopper.chopItUp(bytes);
	}

	public FileByteArray(String name, byte[] bytes) {
		this(bytes);
		this.name = name;
	}

	public FileByteArray(String processInstanceId, String name, byte[] bytes) {
		this(bytes);
		this.name = name;
		this.processInstanceId = processInstanceId;
	}

	public FileByteArray(FileByteArray other) {
		this.byteBlocks = new ArrayList(other.getByteBlocks());
		this.name = other.name;
	}

	public byte[] getBytes() {
		return FileByteBlockChopper.glueChopsBackTogether(byteBlocks);
	}

	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof FileByteArray)) {
			return false;
		}
		FileByteArray other = (FileByteArray) o;
		return Arrays.equals(FileByteBlockChopper
				.glueChopsBackTogether(byteBlocks), FileByteBlockChopper
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
