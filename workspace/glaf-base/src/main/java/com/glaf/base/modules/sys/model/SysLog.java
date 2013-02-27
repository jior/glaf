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
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;

public class SysLog implements Serializable, JSONable {
	private static final long serialVersionUID = 3489584842305336744L;
	private long id;
	private String account;
	private String ip;
	private Date createTime;
	private String operate;
	private int flag;

	public String getAccount() {
		return account;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public int getFlag() {
		return flag;
	}

	public long getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public String getOperate() {
		return operate;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public SysLog jsonToObject(JSONObject jsonObject) {
		SysLog model = new SysLog();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("account")) {
			model.setAccount(jsonObject.getString("account"));
		}
		if (jsonObject.containsKey("ip")) {
			model.setIp(jsonObject.getString("ip"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("operate")) {
			model.setOperate(jsonObject.getString("operate"));
		}
		if (jsonObject.containsKey("flag")) {
			model.setFlag(jsonObject.getInteger("flag"));
		}
		return model;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		if (account != null) {
			jsonObject.put("account", account);
		}
		if (ip != null) {
			jsonObject.put("ip", ip);
		}
		if (createTime != null) {
			jsonObject.put("createTime", DateUtils.getDate(createTime));
			jsonObject.put("createTime_date", DateUtils.getDate(createTime));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(createTime));
		}
		if (operate != null) {
			jsonObject.put("operate", operate);
		}
		jsonObject.put("flag", flag);
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", id);
		jsonObject.put("_id_", id);
		jsonObject.put("_oid_", id);
		if (account != null) {
			jsonObject.put("account", account);
		}
		if (ip != null) {
			jsonObject.put("ip", ip);
		}
		if (createTime != null) {
			jsonObject.put("createTime", DateUtils.getDate(createTime));
			jsonObject.put("createTime_date", DateUtils.getDate(createTime));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(createTime));
		}
		if (operate != null) {
			jsonObject.put("operate", operate);
		}
		jsonObject.put("flag", flag);
		return jsonObject;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}