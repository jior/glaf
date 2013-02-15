/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
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
