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

package com.glaf.core.web.rest;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.LogUtils;
import com.glaf.core.util.RequestUtils;

@Controller("/rs/file")
@Path("/rs/file")
public class MxFileSystemResource {

	@GET
	@POST
	@Path("/json")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] json(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		LogUtils.debug(params);
		JSONArray array = new JSONArray();
		String path = request.getParameter("path");
		if (StringUtils.isEmpty(path)) {
			path = "/WEB-INF";
		}
		String root = SystemProperties.getAppPath() + "/" + path;
		File dir = new File(root);
		if (dir.exists() && dir.isDirectory()) {
			File[] contents = dir.listFiles();
			if (contents != null) {
				for (int i = 0; i < contents.length; i++) {
					File file = contents[i];
					if (file.exists() && file.isFile()) {
						if (StringUtils.contains(file.getName(),
								"jdbc.properties")) {
							continue;
						}
						if (StringUtils.contains(file.getName(),
								"hibernate.cfg.xml")) {
							continue;
						}
						JSONObject json = new JSONObject();
						json.put("id",
								DigestUtils.md5Hex(path + "/" + file.getName()));
						json.put("parentId", DigestUtils.md5Hex(path + "/"));
						json.put("startIndex", i + 1);
						json.put("date", DateUtils.getDateTime(new Date(file
								.lastModified())));
						json.put("name", file.getName());
						json.put("path", path + "/" + file.getName());
						json.put("class", "file");
						json.put("size", file.length());
						json.put("leaf", true);
						array.add(json);
					}
				}
			}
		}
		return array.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/dir")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] dir(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		LogUtils.debug(params);
		JSONArray array = new JSONArray();
		String path = request.getParameter("path");
		if (StringUtils.isEmpty(path)) {
			path = "/WEB-INF";
		}
		String root = SystemProperties.getAppPath() + "/" + path;
		LogUtils.debug("root:" + root);
		File dir = new File(root);
		if (dir.exists() && dir.isDirectory()) {
			File[] contents = dir.listFiles();
			if (contents != null) {
				for (int i = 0; i < contents.length; i++) {
					File file = contents[i];
					if (file.exists() && file.isDirectory()) {
						if (StringUtils.contains(file.getName(),
								"jdbc.properties")) {
							continue;
						}
						if (StringUtils.contains(file.getName(),
								"hibernate.cfg.xml")) {
							continue;
						}
						JSONObject json = new JSONObject();
						json.put("id",
								DigestUtils.md5Hex(path + "/" + file.getName()));
						json.put("parentId", DigestUtils.md5Hex(path + "/"));
						json.put("startIndex", i + 1);
						json.put("date", DateUtils.getDateTime(new Date(file
								.lastModified())));
						json.put("name", file.getName());
						json.put("path", path + "/" + file.getName());
						json.put("class", "folder");
						json.put("leaf", false);
						json.put("isParent", true);
						array.add(json);
					}
				}
			}
		}
		LogUtils.debug(array.toJSONString());
		return array.toJSONString().getBytes("UTF-8");
	}

}
