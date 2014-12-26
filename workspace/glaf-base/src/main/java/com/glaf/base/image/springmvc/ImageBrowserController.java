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

package com.glaf.base.modules.image.springmvc;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.glaf.base.modules.image.ImageBrowserDao;
import com.glaf.base.modules.image.ImageBrowserEntry;
import com.glaf.core.base.DataFile;
import com.glaf.core.domain.BlobItemEntity;

@Controller("editor-imagebrowser-controller")
@RequestMapping("/editor/")
public class ImageBrowserController {

	private ImageBrowserDao imageBrowser;

	@RequestMapping(value = "/imagebrowser/create", method = RequestMethod.POST)
	@ResponseBody
	public byte[] create(@RequestParam final String name,
			@RequestParam final String type, @RequestParam String path)
			throws IOException {
		ImageBrowserEntry entry = new ImageBrowserEntry() {
			private static final long serialVersionUID = 1L;

			{
				setName(name);
				setType(type);
			}
		};
		imageBrowser.create(path, entry);
		return entry.toJsonObject().toJSONString().getBytes("UTF-8");
	}

	@RequestMapping(value = "/imagebrowser/destroy", method = RequestMethod.POST)
	@ResponseBody
	public byte[] destroy(@RequestParam final String name,
			@RequestParam final String type, @RequestParam String path)
			throws IOException {
		ImageBrowserEntry entry = new ImageBrowserEntry() {
			private static final long serialVersionUID = 1L;
			{
				setName(name);
				setType(type);
			}
		};
		imageBrowser.destroy(path, entry);
		return entry.toJsonObject().toJSONString().getBytes("UTF-8");
	}

	@RequestMapping(value = { "/imagebrowser" }, method = RequestMethod.GET)
	public String index() {
		return "editor/imagebrowser";
	}

	@RequestMapping(value = { "/imagebrowser/read" }, method = RequestMethod.POST)
	@ResponseBody
	public byte[] read(String path) throws IOException {
		JSONArray result = new JSONArray();
		List<ImageBrowserEntry> rows = imageBrowser.getList(path);
		if (rows != null && !rows.isEmpty()) {
			for (ImageBrowserEntry entry : rows) {
				result.add(entry);
			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@javax.annotation.Resource(name = "dbImageBrowserDao")
	public void setImageBrowser(ImageBrowserDao imageBrowser) {
		this.imageBrowser = imageBrowser;
	}

	@RequestMapping(value = { "/imagebrowser/thumbnail" }, method = RequestMethod.GET)
	@ResponseBody
	public byte[] thumbnail(String path) throws IOException {
		return imageBrowser.getThumbnail(path);
	}

	@RequestMapping(value = "/imagebrowser/upload", method = RequestMethod.POST)
	@ResponseBody
	public byte[] upload(@RequestParam MultipartFile file,
			@RequestParam String path) throws IllegalStateException,
			IOException {
		if (file != null) {
			DataFile dataFile = new BlobItemEntity();
			dataFile.setFilename(file.getOriginalFilename());
			dataFile.setSize(file.getSize());
			dataFile.setData(file.getBytes());
			return imageBrowser.saveFile(dataFile, path).toJsonObject()
					.toJSONString().getBytes("UTF-8");
		}
		return null;
	}
}