package com.glaf.base.modules.others.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.base.modules.others.model.*;

public class AttachmentJsonFactory {

	public static Attachment jsonToObject(JSONObject jsonObject) {
            Attachment model = new Attachment();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("referId")) {
			model.setReferId(jsonObject.getLong("referId"));
		}
		if (jsonObject.containsKey("referType")) {
			model.setReferType(jsonObject.getInteger("referType"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("url")) {
			model.setUrl(jsonObject.getString("url"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("createId")) {
			model.setCreateId(jsonObject.getLong("createId"));
		}

            return model;
	}

	public static JSONObject toJsonObject(Attachment model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
        jsonObject.put("referId", model.getReferId());
        jsonObject.put("referType", model.getReferType());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		} 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
        jsonObject.put("createId", model.getCreateId());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(Attachment model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                jsonObject.put("referId", model.getReferId());
                jsonObject.put("referType", model.getReferType());
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                if (model.getUrl() != null) {
                     jsonObject.put("url", model.getUrl());
                } 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
                jsonObject.put("createId", model.getCreateId());
                return jsonObject;
	}


	private AttachmentJsonFactory() {

	}

}
