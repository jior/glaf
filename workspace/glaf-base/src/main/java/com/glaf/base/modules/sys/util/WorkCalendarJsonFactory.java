package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.base.modules.sys.model.WorkCalendar;

public class WorkCalendarJsonFactory {

	public static WorkCalendar jsonToObject(JSONObject jsonObject) {
            WorkCalendar model = new WorkCalendar();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("freeDay")) {
			model.setFreeDay(jsonObject.getInteger("freeDay"));
		}
		if (jsonObject.containsKey("freeMonth")) {
			model.setFreeMonth(jsonObject.getInteger("freeMonth"));
		}
		if (jsonObject.containsKey("freeYear")) {
			model.setFreeYear(jsonObject.getInteger("freeYear"));
		}

            return model;
	}

	public static JSONObject toJsonObject(WorkCalendar model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
        jsonObject.put("freeDay", model.getFreeDay());
        jsonObject.put("freeMonth", model.getFreeMonth());
        jsonObject.put("freeYear", model.getFreeYear());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(WorkCalendar model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                jsonObject.put("freeDay", model.getFreeDay());
                jsonObject.put("freeMonth", model.getFreeMonth());
                jsonObject.put("freeYear", model.getFreeYear());
                return jsonObject;
	}


	private WorkCalendarJsonFactory() {

	}

}
