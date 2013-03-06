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

package com.glaf.activiti.util;

import java.io.InputStream;

import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;

import com.glaf.activiti.service.ActivitiDeployQueryService;
import com.glaf.core.context.ApplicationContext;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.security.DigestUtil;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.StringTools;

public class ProcessImage {
	protected final static Log logger = LogFactory.getLog(ProcessImage.class);

	private ProcessImage() {

	}

	public static String getFileExt(String fileName) {
		String value = "";
		int start = 0;
		int end = 0;
		if (fileName == null) {
			return null;
		}
		start = fileName.lastIndexOf(46) + 1;
		end = fileName.length();
		value = fileName.substring(start, end);
		if (fileName.lastIndexOf(46) > 0) {
			return value;
		} else {
			return "";
		}
	}

	public static String getImagePath(ProcessDefinition processDefinition) {
		String resourceName = processDefinition.getDiagramResourceName();
		if (resourceName != null) {
			String ext = getFileExt(resourceName);
			String md5_hex = DigestUtil.digestString(resourceName, "MD5");
			md5_hex = StringTools.encodeHex(md5_hex.getBytes());
			String imagePath = processDefinition.getDeploymentId() + "_"
					+ md5_hex + "." + ext;
			return imagePath;
		}
		return null;
	}

	public static void saveProcessImageToFileSystem(
			ProcessDefinition processDefinition, String imagePath) {
		logger.debug("@deploymentId:" + processDefinition.getDeploymentId());
		String resourceName = processDefinition.getDiagramResourceName();
		if (resourceName != null && imagePath != null) {
			ActivitiDeployQueryService activitiDeployQueryService = ContextFactory
					.getBean("activitiDeployQueryService");
			InputStream inputStream = activitiDeployQueryService
					.getResourceAsStream(processDefinition.getDeploymentId(),
							resourceName);
			logger.debug("@resourceName:" + resourceName);
			String filename = ApplicationContext.getAppPath() + "/apps/temp/"
					+ imagePath;
			FileSystemResource fs = new FileSystemResource(filename);
			if (!fs.exists()) {
				try {
					logger.debug("save:" + filename);
					FileUtils.save(filename, inputStream);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}