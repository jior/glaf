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
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.ws.http.HTTPException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.glaf.core.base.DataFile;
import com.glaf.core.context.ApplicationContext;
import com.glaf.core.util.FileUtils;

@Component("imageBrowserDao")
public class ImageBrowserDaoImpl implements ImageBrowserDao {

	protected static final Log LOG = LogFactory
			.getLog(ImageBrowserDaoImpl.class);

	private ResourceLoader loader;

	private final String ContentPath = "/WEB-INF/resources/imagebrowser/";

	private Boolean canAccess(String path) {
		try {
			return new File(ContentPath, path).getCanonicalPath().startsWith(
					new File(ContentPath).getCanonicalPath());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public void create(String path, ImageBrowserEntry entry) throws IOException {
		if (!canAccess(path)) {
			throw new HTTPException(401);
		}
		new File(ApplicationContext.getAppPath(), new File(normalizePath(path),
				entry.getName()).getPath()).mkdir();
	}

	private void deleteRecursive(File directory) {
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				deleteRecursive(file);
			}
			file.delete();
		}
	}

	@Override
	public void destroy(String path, ImageBrowserEntry entry)
			throws IOException {
		if (!canAccess(path)) {
			throw new HTTPException(401);
		}

		File file = new File(ApplicationContext.getAppPath(), new File(
				normalizePath(path), entry.getName()).getPath());
		if (file.isDirectory()) {
			deleteRecursive(file);
		}
		file.delete();
	}

	private String getExtension(File file) {
		String name = file.getName();
		return name.substring(name.lastIndexOf('.') + 1);
	}

	@Override
	public List<ImageBrowserEntry> getList(String path) {
		if (!canAccess(path)) {
			throw new HTTPException(401);
		}
		List<ImageBrowserEntry> result = new ArrayList<ImageBrowserEntry>();
		try {
			File folder = loader.getResource(normalizePath(path)).getFile();
			for (final File fileEntry : folder.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.isDirectory()
							|| pathname.getName().matches(
									".*((png)|(gif)|(jpg)|(jpeg))$");
				}
			})) {
				ImageBrowserEntry entry = new ImageBrowserEntry();
				if (fileEntry.isDirectory()) {
					entry.setType("d");
				} else {
					entry.setType("f");
					entry.setSize(fileEntry.length());
				}
				entry.setName(fileEntry.getName());
				result.add(entry);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		LOG.debug(result);
		return result;
	}

	@Override
	public byte[] getThumbnail(String path) {
		if (!canAccess(path)) {
			throw new HTTPException(401);
		}
		try {
			File file = loader.getResource(normalizePath(path)).getFile();
			// LOG.debug("path:" + path);
			// LOG.debug("file:" + file.getAbsolutePath());
			if (file.exists() && file.isFile()) {
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				try {
					ImageIO.write(scaleImage(ImageIO.read(file), 80),
							getExtension(file), stream);
					return stream.toByteArray();
				} finally {
					stream.close();
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private String normalizePath(String path) {
		return new File(ContentPath, path).getPath();
	}

	public ImageBrowserEntry saveFile(final DataFile file, String path)
			throws IllegalStateException, IOException {
		if (!canAccess(path)) {
			throw new HTTPException(401);
		}
		String filename = new File(ApplicationContext.getAppPath(), new File(
				normalizePath(path)).getPath()).getAbsolutePath()
				+ FileUtils.sp + file.getFilename();
		LOG.debug("save file:" + filename);
		FileUtils.save(filename, file.getData());
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
	public void setLoader(ResourceLoader loader) {
		this.loader = loader;
	}
}