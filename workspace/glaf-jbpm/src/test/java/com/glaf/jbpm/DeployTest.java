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

package com.glaf.jbpm;

import java.io.File;

import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.junit.Test;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.FileUtils;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;
import com.glaf.jbpm.deploy.MxJbpmProcessDeployer;

public class DeployTest {

	@Test
	public void testDeploy() {
		String filename = SystemProperties.getConfigRootPath()
				+ "/conf/jbpm/hibernate.cfg.xml";
		System.out.println(filename);
		MxJbpmProcessDeployer deployer = new MxJbpmProcessDeployer();
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			File dir = new File("jpdl");
			if (dir.exists() && dir.isDirectory()) {
				File contents[] = dir.listFiles();
				if (contents != null) {
					for (int i = 0; i < contents.length; i++) {
						if (contents[i].isFile()
								&& contents[i].getName().endsWith(".zip")) {
							deployer.deploy(jbpmContext,
									FileUtils.getBytes(contents[i]));
						} else {
							File path = contents[i];
							File chilrenContents[] = path.listFiles();
							if (chilrenContents != null) {
								for (int j = 0; j < chilrenContents.length; j++) {
									if (chilrenContents[j].isFile()
											&& chilrenContents[j].getName()
													.endsWith(".zip")) {
										deployer.deploy(jbpmContext, FileUtils
												.getBytes(chilrenContents[j]));
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			if (jbpmContext != null) {
				jbpmContext.setRollbackOnly();
			}
			ex.printStackTrace();
			throw new JbpmException(ex);
		} finally {
			Context.close(jbpmContext);
		}
	}

}
