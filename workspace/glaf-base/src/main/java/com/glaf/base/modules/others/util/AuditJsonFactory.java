package com.glaf.base.modules.others.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.util.DateUtils;
import com.glaf.base.modules.others.model.*;

public class AuditJsonFactory {

	public static Audit jsonToObject(JSONObject jsonObject) {
            Audit model = new Audit();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("referId")) {
			model.setReferId(jsonObject.getLong("referId"));
		}
		if (jsonObject.containsKey("referType")) {
			model.setReferType(jsonObject.getInteger("referType"));
		}
		if (jsonObject.containsKey("deptId")) {
			model.setDeptId(jsonObject.getLong("deptId"));
		}
		if (jsonObject.containsKey("deptName")) {
			model.setDeptName(jsonObject.getString("deptName"));
		}
		if (jsonObject.containsKey("headship")) {
			model.setHeadship(jsonObject.getString("headship"));
		}
		if (jsonObject.containsKey("leaderName")) {
			model.setLeaderName(jsonObject.getString("leaderName"));
		}
		if (jsonObject.containsKey("leaderId")) {
			model.setLeaderId(jsonObject.getLong("leaderId"));
		}
		if (jsonObject.containsKey("memo")) {
			model.setMemo(jsonObject.getString("memo"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("flag")) {
			model.setFlag(jsonObject.getInteger("flag"));
		}

            return model;
	}

	public static JSONObject toJsonObject(Audit model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
        jsonObject.put("referId", model.getReferId());
        jsonObject.put("referType", model.getReferType());
        jsonObject.put("deptId", model.getDeptId());
		if (model.getDeptName() != null) {
			jsonObject.put("deptName", model.getDeptName());
		} 
		if (model.getHeadship() != null) {
			jsonObject.put("headship", model.getHeadship());
		} 
		if (model.getLeaderName() != null) {
			jsonObject.put("leaderName", model.getLeaderName());
		} 
        jsonObject.put("leaderId", model.getLeaderId());
		if (model.getMemo() != null) {
			jsonObject.put("memo", model.getMemo());
		} 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
        jsonObject.put("flag", model.getFlag());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(Audit model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                jsonObject.put("referId", model.getReferId());
                jsonObject.put("referType", model.getReferType());
                jsonObject.put("deptId", model.getDeptId());
                if (model.getDeptName() != null) {
                     jsonObject.put("deptName", model.getDeptName());
                } 
                if (model.getHeadship() != null) {
                     jsonObject.put("headship", model.getHeadship());
                } 
                if (model.getLeaderName() != null) {
                     jsonObject.put("leaderName", model.getLeaderName());
                } 
                jsonObject.put("leaderId", model.getLeaderId());
                if (model.getMemo() != null) {
                     jsonObject.put("memo", model.getMemo());
                } 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
                jsonObject.put("flag", model.getFlag());
                return jsonObject;
	}


	private AuditJsonFactory() {

	}

}
