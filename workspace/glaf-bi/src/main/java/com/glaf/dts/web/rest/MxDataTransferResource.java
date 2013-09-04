package com.glaf.dts.web.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller("/rs/dts/dataTransfer")
@Path("/rs/dts/dataTransfer")
public class MxDataTransferResource {

	@GET
	@POST
	@Path("/javaType")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] javaType(@Context HttpServletRequest request)
			throws IOException {
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("code", "Integer");
		json.put("text", "整数型");
		array.add(json);

		JSONObject json2 = new JSONObject();
		json2.put("code", "Long");
		json2.put("text", "长整数型");
		array.add(json2);

		JSONObject json3 = new JSONObject();
		json3.put("code", "Double");
		json3.put("text", "数值型");
		array.add(json3);

		JSONObject json4 = new JSONObject();
		json4.put("code", "Date");
		json4.put("text", "日期型");
		array.add(json4);

		JSONObject json5 = new JSONObject();
		json5.put("code", "String");
		json5.put("text", "字符串型");
		array.add(json5);

		return array.toString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/parseTypeJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] parseTypeJson(@Context HttpServletRequest request)
			throws IOException {
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("code", "csv");
		json.put("text", "csv");
		array.add(json);

		JSONObject json2 = new JSONObject();
		json2.put("code", "text");
		json2.put("text", "text");
		array.add(json2);

		JSONObject json3 = new JSONObject();
		json3.put("code", "xls");
		json3.put("text", "Excel");
		array.add(json3);

		return array.toString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/valueExpression")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] valueExpression(@Context HttpServletRequest request)
			throws IOException {
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("code", "#{id}");
		json.put("text", "自增长ID");
		array.add(json);

		JSONObject json2 = new JSONObject();
		json2.put("code", "#{seqNo}");
		json2.put("text", "序号");
		array.add(json2);

		JSONObject json3 = new JSONObject();
		json3.put("code", "#{now}");
		json3.put("text", "当前日期");
		array.add(json3);

		JSONObject json4 = new JSONObject();
		json4.put("code", "#{uuid}");
		json4.put("text", "32位UUID");
		array.add(json4);

		return array.toString().getBytes("UTF-8");
	}
}
