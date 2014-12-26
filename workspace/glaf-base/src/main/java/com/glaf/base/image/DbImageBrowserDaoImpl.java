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

package com.glaf.base.modules.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.glaf.core.base.BlobItem;
import com.glaf.core.base.DataFile;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.query.BlobItemQuery;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.io.ByteArrayInputStream;

@Component("dbImageBrowserDao")
public class DbImageBrowserDaoImpl implements ImageBrowserDao {

	protected static final Log LOG = LogFactory
			.getLog(DbImageBrowserDaoImpl.class);

	protected IBlobService blobService;

	@Override
	public void create(String path, ImageBrowserEntry entry) throws IOException {
		if (path == null || path.trim().length() == 0) {
			path = "/";
		}
		String name = path + "/" + entry.getName();
		name = StringTools.replace(name, "//", "/");
		BlobItem blob = new BlobItemEntity();
		blob.setName(name);
		blob.setFileId(name);
		blob.setPath(StringTools.replace(path, "//", "/"));
		blob.setType("folder");
		blobService.insertBlob(blob);
	}

	@Override
	public void destroy(String path, ImageBrowserEntry entry)
			throws IOException {
		if (StringUtils.isEmpty(path)) {
			path = "/";
		}
		if (!StringUtils.startsWith(path, "/")) {
			path = "/" + path;
		}
		path = path + "/" + entry.getName();
		path = StringTools.replace(path, "//", "/");
		LOG.debug("remove path:" + path);
		BlobItemQuery query = new BlobItemQuery();
		query.setPathLike(path + "%");
		List<DataFile> list = blobService.getBlobList(query);
		if (list != null && !list.isEmpty()) {
			for (DataFile dataFile : list) {
				blobService.deleteById(dataFile.getId());
			}
		}
	}

	protected String getFileExt(File file) {
		String name = file.getName();
		return name.substring(name.lastIndexOf('.') + 1);
	}

	@Override
	public List<ImageBrowserEntry> getList(String path) {
		if (path == null || path.trim().length() == 0) {
			path = "/";
		}
		if (!StringUtils.startsWith(path, "/")) {
			path = "/" + path;
		}
		path = StringTools.replace(path, "//", "/");
		List<ImageBrowserEntry> result = new ArrayList<ImageBrowserEntry>();
		try {
			BlobItemQuery query = new BlobItemQuery();
			query.setPathLike(path + "%");
			query.setType("folder");

			List<DataFile> list = blobService.getBlobList(query);
			if (list != null && !list.isEmpty()) {
				for (DataFile dataFile : list) {
					if ("folder".equals(dataFile.getType())) {
						ImageBrowserEntry entry = new ImageBrowserEntry();
						entry.setType("d");
						entry.setName(dataFile.getName());
						result.add(entry);
					}
				}
			}

			if (!StringUtils.equals(path, "/")) {
				if (StringUtils.endsWith(path, "/")) {
					path = path.substring(0, path.lastIndexOf("/"));
				}
			}

			query = new BlobItemQuery();
			query.setPath(path);
			query.setType("file");

			list = blobService.getBlobList(query);
			if (list != null && !list.isEmpty()) {
				for (DataFile dataFile : list) {
					if (dataFile.getFilename() != null
							&& dataFile.getFilename().matches(
									".*((png)|(gif)|(jpg)|(jpeg))$")) {
						ImageBrowserEntry entry = new ImageBrowserEntry();
						entry.setType("f");
						entry.setName(dataFile.getFilename());
						entry.setSize(dataFile.getSize());
						result.add(entry);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		LOG.debug(result);
		return result;
	}

	@Override
	public byte[] getThumbnail(String fileId) {
		if (!StringUtils.startsWith(fileId, "/")) {
			fileId = "/" + fileId;
		}
		fileId = StringTools.replace(fileId, "//", "/");
		LOG.debug("fetch fileId:" + fileId);
		InputStream inputStream = null;
		ByteArrayOutputStream outputStream = null;
		try {
			DataFile file = blobService.getBlobByFileId(fileId);
			if (file != null) {
				byte[] data = blobService.getBytesByFileId(fileId);
				LOG.debug("fetch file:" + file.getFilename());
				outputStream = new ByteArrayOutputStream();
				inputStream = new ByteArrayInputStream(data);
				ImageIO.write(scaleImage(ImageIO.read(inputStream), 80),
						FileUtils.getFileExt(file.getFilename()), outputStream);
				return outputStream.toByteArray();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeStream(inputStream);
			IOUtils.closeStream(outputStream);
		}
		return null;
	}

	public ImageBrowserEntry saveFile(final DataFile file, String path)
			throws IllegalStateException, IOException {
		if (StringUtils.isEmpty(path)) {
			path = "/";
		}
		if (!StringUtils.startsWith(path, "/")) {
			path = "/" + path;
		}
		String filename = path + "/" + file.getFilename();
		filename = StringTools.replace(filename, "//", "/");
		file.setStatus(9);
		file.setLastModified(System.currentTimeMillis());
		file.setCreateDate(new Date());
		file.setPath(filename);
		file.setFileId(filename);
		file.setName(filename);
		file.setBusinessKey(filename);
		file.setType("file");
		blobService.insertBlob(file);

		return new ImageBrowserEntry() {
			private static final long serialVersionUID = 1L;
			{
				setSize(file.getSize());
				setName(file.getFilename());
			}
		};
	}

	private BufferedImage scaleImage(final BufferedImage bufferedImage,
			final int size) {
		final double boundSize = size;
		final int origWidth = bufferedImage.getWidth();
		final int origHeight = bufferedImage.getHeight();

		double scale;

		if (origHeight > origWidth) {
			scale = boundSize / origHeight;
		} else {
			scale = boundSize / origWidth;
		}

		if (scale > 1.0)
			return (null);

		final int scaledWidth = (int) (scale * origWidth);
		final int scaledHeight = (int) (scale * origHeight);

		final Image scaledImage = bufferedImage.getScaledInstance(scaledWidth,
				scaledHeight, Image.SCALE_SMOOTH);

		final BufferedImage scaledBI = new BufferedImage(scaledWidth,
				scaledHeight, BufferedImage.TYPE_INT_RGB);

		final Graphics2D g = scaledBI.createGraphics();

		try {
			g.drawImage(scaledImage, 0, 0, null);
		} finally {
			g.dispose();
		}

		return scaledBI;
	}

	@javax.annotation.Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

}