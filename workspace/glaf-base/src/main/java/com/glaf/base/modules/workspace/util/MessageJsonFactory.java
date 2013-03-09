package com.glaf.base.modules.workspace.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.util.DateUtils;
import com.glaf.base.modules.workspace.model.*;

public class MessageJsonFactory {

	public static Message jsonToObject(JSONObject jsonObject) {
            Message model = new Message();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getInteger("type"));
		}
		if (jsonObject.containsKey("sysType")) {
			model.setSysType(jsonObject.getInteger("sysType"));
		}
		if (jsonObject.containsKey("recverList")) {
			model.setRecverList(jsonObject.getString("recverList"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("readed")) {
			model.setReaded(jsonObject.getInteger("readed"));
		}
		if (jsonObject.containsKey("category")) {
			model.setCategory(jsonObject.getInteger("category"));
		}
		if (jsonObject.containsKey("senderId")) {
			model.setSenderId(jsonObject.getLong("senderId"));
		}
		if (jsonObject.containsKey("recverId")) {
			model.setRecverId(jsonObject.getLong("recverId"));
		}

            return model;
	}

	public static JSONObject toJsonObject(Message model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
        jsonObject.put("type", model.getType());
        jsonObject.put("sysType", model.getSysType());
		if (model.getRecverList() != null) {
			jsonObject.put("recverList", model.getRecverList());
		} 
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		} 
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		} 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
        jsonObject.put("readed", model.getReaded());
        jsonObject.put("category", model.getCategory());
        jsonObject.put("senderId", model.getSenderId());
        jsonObject.put("recverId", model.getRecverId());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(Message model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                jsonObject.put("type", model.getType());
                jsonObject.put("sysType", model.getSysType());
                if (model.getRecverList() != null) {
                     jsonObject.put("recverList", model.getRecverList());
                } 
                if (model.getTitle() != null) {
                     jsonObject.put("title", model.getTitle());
                } 
                if (model.getContent() != null) {
                     jsonObject.put("content", model.getContent());
                } 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
                jsonObject.put("readed", model.getReaded());
                jsonObject.put("category", model.getCategory());
                jsonObject.put("senderId", model.getSenderId());
                jsonObject.put("recverId", model.getRecverId());
                return jsonObject;
	}


	private MessageJsonFactory() {

	}

}
