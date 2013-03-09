package com.glaf.base.modules.workspace.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.base.modules.workspace.model.*;

public class MyMenuJsonFactory {

	public static MyMenu jsonToObject(JSONObject jsonObject) {
            MyMenu model = new MyMenu();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getLong("userId"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("url")) {
			model.setUrl(jsonObject.getString("url"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}

            return model;
	}

	public static JSONObject toJsonObject(MyMenu model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
        jsonObject.put("userId", model.getUserId());
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		} 
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		} 
        jsonObject.put("sort", model.getSort());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(MyMenu model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                jsonObject.put("userId", model.getUserId());
                if (model.getTitle() != null) {
                     jsonObject.put("title", model.getTitle());
                } 
                if (model.getUrl() != null) {
                     jsonObject.put("url", model.getUrl());
                } 
                jsonObject.put("sort", model.getSort());
                return jsonObject;
	}


	private MyMenuJsonFactory() {

	}

}
