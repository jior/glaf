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

package com.glaf.jbpm.export;

import java.io.IOException;
import java.util.*;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.file.def.FileDefinition;
import org.jbpm.graph.def.ProcessDefinition;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.ZipUtils;

import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;

public class MxZipAllExporter {

	public static void zipAll() {
		String path = SystemProperties.getConfigRootPath() + "/deploy/jpdl";
		try {
			FileUtils.mkdirs(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		zipAll(path);
	}

	@SuppressWarnings("rawtypes")
	public static void zipAll(String path) {
		GraphSession graphSession = null;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			graphSession = jbpmContext.getGraphSession();
			List processDefinitions = graphSession
					.findLatestProcessDefinitions();
			if (processDefinitions != null && processDefinitions.size() > 0) {
				Iterator iterator = processDefinitions.iterator();
				while (iterator.hasNext()) {
					ProcessDefinition processDefinition = (ProcessDefinition) iterator
							.next();
					FileDefinition fileDefinition = processDefinition
							.getFileDefinition();
					if (fileDefinition.getInputStreamMap() != null) {
						@SuppressWarnings("unchecked")
						byte[] bytes = ZipUtils.getZipBytes(fileDefinition
								.getInputStreamMap());
						String filename = path + "/"
								+ processDefinition.getName() + "_"
								+ processDefinition.getVersion() + ".zip";
						FileUtils.save(filename, bytes);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Context.close(jbpmContext);
		}
	}
}