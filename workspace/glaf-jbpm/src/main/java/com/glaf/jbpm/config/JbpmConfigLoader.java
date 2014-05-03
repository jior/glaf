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

package com.glaf.jbpm.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.LogUtils;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;
import com.glaf.jbpm.manager.JbpmExtensionManager;
import com.glaf.jbpm.model.Extension;

public class JbpmConfigLoader {
	protected final static Log logger = LogFactory
			.getLog(JbpmConfigLoader.class);

	public JbpmConfigLoader() {

	}

	public void execute(java.util.Properties props) {
		if (SystemProperties.getBoolean("jbpm.config.install.onStartup")) {
			this.installActionsConfig();
		}
	}

	/**
	 * 安装流程配置文件
	 */
	public synchronized void installActionsConfig() {
		String configPath = SystemProperties.getConfigRootPath();
		String path = configPath + "/conf/jbpm/action";
		java.io.File directory = new java.io.File(path);
		JbpmContext jbpmContext = null;
		try {
			List<Extension> extensions = this.getActions(directory);
			if (extensions != null && extensions.size() > 0) {
				jbpmContext = ProcessContainer.getContainer()
						.createJbpmContext();
				if (jbpmContext.getSession() != null) {
					JbpmExtensionManager jbpmExtensionManager = ProcessContainer
							.getContainer().getJbpmExtensionManager();
					jbpmExtensionManager.reconfig(jbpmContext, extensions);
				}
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			if (LogUtils.isDebug()) {
				logger.debug(ex);
				ex.printStackTrace();
			}
			throw new JbpmException(ex);
		} finally {
			try {
				Context.close(jbpmContext);
			} catch (Exception ex) {
				throw new JbpmException(ex);
			}

		}
	}

	public List<Extension> getActions(java.io.File directory) {
		JbpmExtensionReader reader = new JbpmExtensionReader();
		List<Extension> extensions = new java.util.ArrayList<Extension>();
		InputStream inputStream = null;
		String[] filelist = directory.list();
		for (int i = 0; i < filelist.length; i++) {
			String filename = directory.getAbsolutePath() + "/" + filelist[i];
			File file = new File(filename);
			if (file.isFile() && file.getName().endsWith(".action.cfg.xml")) {
				try {
					inputStream = new FileInputStream(file);
					List<Extension> rows = reader.readActions(inputStream);
					if (rows != null && rows.size() > 0) {
						extensions.addAll(rows);
					}
					inputStream.close();
					inputStream = null;
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
		}
		return extensions;
	}

}