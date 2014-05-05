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
package com.glaf.activiti.business;

import java.io.*;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.FileUtils;

public class ProcessHelper {

	protected static final Log logger = LogFactory.getLog(ProcessHelper.class);

	/**
	 * 将全部流程图导出到硬盘
	 * 
	 * @param repositoryService
	 * @param exportDir
	 */
	public void exportAll(RepositoryService repositoryService, String exportDir) {
		List<ProcessDefinition> list = repositoryService
				.createProcessDefinitionQuery().list();
		if (list != null && !list.isEmpty()) {
			for (ProcessDefinition processDefinition : list) {
				try {
					this.exportDiagramToFile(repositoryService,
							processDefinition, exportDir);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将全部流程图导出到硬盘
	 * 
	 * @param repositoryService
	 * @param exportDir
	 */
	public void exportAll(String exportDir) {
		RepositoryService repositoryService = ContextFactory
				.getBean("repositoryService");
		this.exportAll(repositoryService, exportDir);
	}

	/**
	 * 导出图片文件到硬盘
	 * 
	 * @return 文件的全路径
	 */
	public String exportDiagramToFile(RepositoryService repositoryService,
			ProcessDefinition processDefinition, String exportDir) {
		String diagramResourceName = processDefinition.getDiagramResourceName();
		String key = processDefinition.getKey();
		int version = processDefinition.getVersion();

		// create folder if not exist
		File exportDirFile = new File(exportDir);
		if (!exportDirFile.exists()) {
			exportDirFile.mkdirs();
		}

		String diagramDir = exportDir + "/" + key + "_" + version;
		String diagramPath = diagramDir + "_" + diagramResourceName;
		File file = new File(diagramPath);

		// 文件存在退出
		if (file.exists()) {
			// 文件大小相同时直接返回否则重新创建文件(可能损坏)
			logger.debug("diagram exist, ignore... : " + diagramPath);
			return diagramPath;
		} else {
			logger.debug("export diagram to : " + diagramPath);
			InputStream resourceAsStream = repositoryService
					.getResourceAsStream(processDefinition.getDeploymentId(),
							diagramResourceName);
			FileUtils.save(diagramPath, resourceAsStream);
		}

		return diagramPath;
	}

	public static void main(String[] args) {
		ProcessHelper helper = new ProcessHelper();
		helper.exportAll("images");
	}

}
