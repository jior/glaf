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

package com.glaf.base.modules.image.rest;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.glaf.base.modules.image.ImageBrowserDao;
import com.glaf.base.modules.image.ImageBrowserEntry;
import com.glaf.core.base.DataFile;
import com.glaf.core.context.ApplicationContext;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.RequestUtils;

@Controller("editor-imagebrowser-resource")
@Path("/rs/editor/")
public class ImageBrowserResource {
	protected static final Log LOG = LogFactory
			.getLog(ImageBrowserResource.class);

	private ImageBrowserDao imageBrowser;

	@POST
	@Path("/imagebrowser/create")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] create(@Context final HttpServletRequest request)
			throws IOException {
		final String name = request.getParameter("name");
		final String type = request.getParameter("type");
		final String path = request.getParameter("path");
		ImageBrowserEntry entry = new ImageBrowserEntry() {
			private static final long serialVersionUID = 1L;
			{
				setName(name);
				setType(type);
				setActorId(RequestUtils.getActorId(request));
			}
		};
		imageBrowser.create(path, entry);
		return entry.toJsonObject().toJSONString().getBytes("UTF-8");
	}

	@POST
	@Path("/imagebrowser/destroy")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] destroy(@Context final HttpServletRequest request)
			throws IOException {
		final String name = request.getParameter("name");
		final String type = request.getParameter("type");
		final String path = request.getParameter("path");
		ImageBrowserEntry entry = new ImageBrowserEntry() {
			private static final long serialVersionUID = 1L;
			{
				setName(name);
				setType(type);
				setActorId(RequestUtils.getActorId(request));
			}
		};
		imageBrowser.destroy(path, entry);
		return entry.toJsonObject().toJSONString().getBytes("UTF-8");
	}

	@POST
	@Path("/imagebrowser/read")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] read(@Context HttpServletRequest request) throws IOException {
		String path = request.getParameter("path");
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

	@GET
	@POST
	@Path("/imagebrowser/thumbnail")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] thumbnail(@Context HttpServletRequest request)
			throws IOException {
		String path = request.getParameter("path");
		LOG.debug("path:" + path);
		return imageBrowser.getThumbnail(path);
	}

	@POST
	@Path("/imagebrowser/upload")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] upload(@Context HttpServletRequest request)
			throws IllegalStateException, IOException {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		LOG.debug("paramMap:" + paramMap);
		try {
			DiskFileItemFactory diskFactory = new DiskFileItemFactory();

			diskFactory.setSizeThreshold(8 * FileUtils.MB_SIZE);

			diskFactory.setRepository(new File(ApplicationContext.getAppPath()
					+ "/temp"));
			ServletFileUpload upload = new ServletFileUpload(diskFactory);

			upload.setHeaderEncoding("UTF-8");
			upload.setSizeMax(20 * FileUtils.MB_SIZE);
			upload.setFileSizeMax(20 * FileUtils.MB_SIZE);

			if (ServletFileUpload.isMultipartContent(request)) {
				String path = request.getParameter("path");

				DataFile dataFile = new BlobItemEntity();
				List<FileItem> fileItems = upload.parseRequest(request);
				Iterator<FileItem> iter2 = fileItems.iterator();
				while (iter2.hasNext()) {
					FileItem item = (FileItem) iter2.next();
					if (item.isFormField()) {
						if ("path".equals(item.getFieldName())) {
							path = item.getString();
						}
					} else {
						if (item.get() != null) {
							String filename = item.getName();
							int index = filename.lastIndexOf("\\");
							filename = filename.substring(index + 1,
									filename.length());
							LOG.debug("filename=" + filename);
							dataFile.setData(item.get());
							dataFile.setSize(item.getSize());
							dataFile.setFilename(filename);
						}
					}
				}

				if (dataFile.getData() != null) {
					LOG.debug("path=" + path);
					dataFile.setCreateBy(RequestUtils.getActorId(request));
					ImageBrowserEntry imageBrowserEntry = imageBrowser
							.saveFile(dataFile, path);
					return imageBrowserEntry.toJsonObject().toJSONString()
							.getBytes("UTF-8");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}