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

package com.glaf.core.query;

import java.util.Date;
import java.util.List;

public class BlobItemQuery extends DataQuery {
	private static final long serialVersionUID = 1L; 
	protected String contentType;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected String deviceId;
	protected String fileId;
	protected String filename;
	protected String filenameLike;
	protected Long lastModifiedGreaterThanOrEqual;
	protected Long lastModifiedLessThanOrEqual;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected List<String> objectIds;
	protected List<String> objectValues;
	protected Long size;
	protected Long sizeGreaterThanOrEqual;
	protected Long sizeLessThanOrEqual;
	protected String type;

	public BlobItemQuery() {

	}

	 

	public BlobItemQuery contentType(String contentType) {
		if (contentType == null) {
			throw new RuntimeException("contentType is null");
		}
		this.contentType = contentType;
		return this;
	}

	public BlobItemQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public BlobItemQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public BlobItemQuery deviceId(String deviceId) {
		if (deviceId == null) {
			throw new RuntimeException("deviceId is null");
		}
		this.deviceId = deviceId;
		return this;
	}

	public BlobItemQuery fileId(String fileId) {
		if (fileId == null) {
			throw new RuntimeException("fileId is null");
		}
		this.fileId = fileId;
		return this;
	}

	public BlobItemQuery filename(String filename) {
		if (filename == null) {
			throw new RuntimeException("filename is null");
		}
		this.filename = filename;
		return this;
	}

	public BlobItemQuery filenameLike(String filenameLike) {
		if (filenameLike == null) {
			throw new RuntimeException("filename is null");
		}
		this.filenameLike = filenameLike;
		return this;
	}

	 

	public String getContentType() {
		return contentType;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getFileId() {
		return fileId;
	}

	public String getFilename() {
		return filename;
	}

	public String getFilenameLike() {
		return filenameLike;
	}

	public Long getLastModifiedGreaterThanOrEqual() {
		return lastModifiedGreaterThanOrEqual;
	}

	public Long getLastModifiedLessThanOrEqual() {
		return lastModifiedLessThanOrEqual;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
		return nameLike;
	}

	public List<String> getNames() {
		return names;
	}

	public List<String> getObjectIds() {
		return objectIds;
	}

	public List<String> getObjectValues() {
		return objectValues;
	}

	public Long getSize() {
		return size;
	}

	public Long getSizeGreaterThanOrEqual() {
		return sizeGreaterThanOrEqual;
	}

	public Long getSizeLessThanOrEqual() {
		return sizeLessThanOrEqual;
	}

	public String getType() {
		return type;
	}

	public BlobItemQuery lastModifiedGreaterThanOrEqual(
			Long lastModifiedGreaterThanOrEqual) {
		if (lastModifiedGreaterThanOrEqual == null) {
			throw new RuntimeException("lastModified is null");
		}
		this.lastModifiedGreaterThanOrEqual = lastModifiedGreaterThanOrEqual;
		return this;
	}

	public BlobItemQuery lastModifiedLessThanOrEqual(
			Long lastModifiedLessThanOrEqual) {
		if (lastModifiedLessThanOrEqual == null) {
			throw new RuntimeException("lastModified is null");
		}
		this.lastModifiedLessThanOrEqual = lastModifiedLessThanOrEqual;
		return this;
	}

	public BlobItemQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public BlobItemQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public BlobItemQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("name is null");
		}
		this.names = names;
		return this;
	}

	public BlobItemQuery objectIds(List<String> objectIds) {
		if (objectIds == null) {
			throw new RuntimeException("objectIds is empty ");
		}
		this.objectIds = objectIds;
		return this;
	}

	public BlobItemQuery objectValues(List<String> objectValues) {
		if (objectValues == null) {
			throw new RuntimeException("objectValues is empty ");
		}
		this.objectValues = objectValues;
		return this;
	}

	public void setBusinessKeys(List<String> businessKeys) {
		this.businessKeys = businessKeys;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setCreateDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setFilenameLike(String filenameLike) {
		this.filenameLike = filenameLike;
	}

	public void setLastModifiedGreaterThanOrEqual(
			Long lastModifiedGreaterThanOrEqual) {
		this.lastModifiedGreaterThanOrEqual = lastModifiedGreaterThanOrEqual;
	}

	public void setLastModifiedLessThanOrEqual(Long lastModifiedLessThanOrEqual) {
		this.lastModifiedLessThanOrEqual = lastModifiedLessThanOrEqual;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setObjectIds(List<String> objectIds) {
		this.objectIds = objectIds;
	}

	public void setObjectValues(List<String> objectValues) {
		this.objectValues = objectValues;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public void setSizeGreaterThanOrEqual(Long sizeGreaterThanOrEqual) {
		this.sizeGreaterThanOrEqual = sizeGreaterThanOrEqual;
	}

	public void setSizeLessThanOrEqual(Long sizeLessThanOrEqual) {
		this.sizeLessThanOrEqual = sizeLessThanOrEqual;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BlobItemQuery size(Long size) {
		if (size == null) {
			throw new RuntimeException("size is null");
		}
		this.size = size;
		return this;
	}

	public BlobItemQuery sizeGreaterThanOrEqual(Long sizeGreaterThanOrEqual) {
		if (sizeGreaterThanOrEqual == null) {
			throw new RuntimeException("size is null");
		}
		this.sizeGreaterThanOrEqual = sizeGreaterThanOrEqual;
		return this;
	}

	public BlobItemQuery sizeLessThanOrEqual(Long sizeLessThanOrEqual) {
		if (sizeLessThanOrEqual == null) {
			throw new RuntimeException("size is null");
		}
		this.sizeLessThanOrEqual = sizeLessThanOrEqual;
		return this;
	}

	public BlobItemQuery status(Integer status) {
		if (status == null) {
			throw new RuntimeException("status is null");
		}
		this.status = status;
		return this;
	}

	public BlobItemQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

}