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

package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.Date;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Dictory implements Serializable, JSONable {
	private static final long serialVersionUID = 2756737871937885934L;
	private long id;
	private long typeId;
	private String code;
	private String name;
	private int sort;
	private String desc;
	private int blocked;
	private String ext1;
	private String ext2;
	private String ext3;
	private String ext4;
	private Date ext5;
	private Date ext6;

	public int getBlocked() {
		return blocked;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public String getExt1() {
		return ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public String getExt4() {
		return ext4;
	}

	public Date getExt5() {
		return ext5;
	}

	public Date getExt6() {
		return ext6;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getSort() {
		return sort;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setBlocked(int blocked) {
		this.blocked = blocked;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public void setExt5(Date ext5) {
		this.ext5 = ext5;
	}

	public void setExt6(Date ext6) {
		this.ext6 = ext6;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public Dictory jsonToObject(JSONObject jsonObject) {
		Dictory model = new Dictory();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("typeId")) {
			model.setTypeId(jsonObject.getLong("typeId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}
		if (jsonObject.containsKey("blocked")) {
			model.setBlocked(jsonObject.getInteger("blocked"));
		}
		if (jsonObject.containsKey("ext1")) {
			model.setExt1(jsonObject.getString("ext1"));
		}
		if (jsonObject.containsKey("ext2")) {
			model.setExt2(jsonObject.getString("ext2"));
		}
		if (jsonObject.containsKey("ext3")) {
			model.setExt3(jsonObject.getString("ext3"));
		}
		if (jsonObject.containsKey("ext4")) {
			model.setExt4(jsonObject.getString("ext4"));
		}
		if (jsonObject.containsKey("ext5")) {
			model.setExt5(jsonObject.getDate("ext5"));
		}
		if (jsonObject.containsKey("ext6")) {
			model.setExt6(jsonObject.getDate("ext6"));
		}
		return model;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		jsonObject.put("typeId", typeId);
		if (name != null) {
			jsonObject.put("name", name);
		}
		if (desc != null) {
			jsonObject.put("desc", desc);
		}
		if (code != null) {
			jsonObject.put("code", code);
		}
		jsonObject.put("sort", sort);
		jsonObject.put("blocked", blocked);
		if (ext1 != null) {
			jsonObject.put("ext1", ext1);
		}
		if (ext2 != null) {
			jsonObject.put("ext2", ext2);
		}
		if (ext3 != null) {
			jsonObject.put("ext3", ext3);
		}
		if (ext4 != null) {
			jsonObject.put("ext4", ext4);
		}
		if (ext5 != null) {
			jsonObject.put("ext5", DateUtils.getDate(ext5));
			jsonObject.put("ext5_date", DateUtils.getDate(ext5));
			jsonObject.put("ext5_datetime", DateUtils.getDateTime(ext5));
		}
		if (ext6 != null) {
			jsonObject.put("ext6", DateUtils.getDate(ext6));
			jsonObject.put("ext6_date", DateUtils.getDate(ext6));
			jsonObject.put("ext6_datetime", DateUtils.getDateTime(ext6));
		}
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		jsonObject.put("typeId", typeId);
		if (name != null) {
			jsonObject.put("name", name);
		}
		if (desc != null) {
			jsonObject.put("desc", desc);
		}
		if (code != null) {
			jsonObject.put("code", code);
		}
		jsonObject.put("sort", sort);
		jsonObject.put("blocked", blocked);
		if (ext1 != null) {
			jsonObject.put("ext1", ext1);
		}
		if (ext2 != null) {
			jsonObject.put("ext2", ext2);
		}
		if (ext3 != null) {
			jsonObject.put("ext3", ext3);
		}
		if (ext4 != null) {
			jsonObject.put("ext4", ext4);
		}
		if (ext5 != null) {
			jsonObject.put("ext5", DateUtils.getDate(ext5));
			jsonObject.put("ext5_date", DateUtils.getDate(ext5));
			jsonObject.put("ext5_datetime", DateUtils.getDateTime(ext5));
		}
		if (ext6 != null) {
			jsonObject.put("ext6", DateUtils.getDate(ext6));
			jsonObject.put("ext6_date", DateUtils.getDate(ext6));
			jsonObject.put("ext6_datetime", DateUtils.getDateTime(ext6));
		}
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}