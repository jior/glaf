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
package com.glaf.activiti.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipInputStream;

import org.junit.Test;

public class DeployTest extends AbstractActivitiTest {

	@Test
	public void testDeploy() {

		ZipInputStream zipInputStream = null;
		try {
			File dir = new File("bpmn");
			if (dir.exists() && dir.isDirectory()) {
				File contents[] = dir.listFiles();
				if (contents != null) {
					for (int i = 0; i < contents.length; i++) {
						if (contents[i].isFile()
								&& contents[i].getName().endsWith(".zip")) {
							try {
								zipInputStream = new ZipInputStream(
										new FileInputStream(contents[i]));
								activitiDeployService
										.addZipInputStream(zipInputStream);
								zipInputStream.close();
								zipInputStream = null;
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						} else {
							File path = contents[i];
							File chilrenContents[] = path.listFiles();
							if (chilrenContents != null) {
								for (int j = 0; j < chilrenContents.length; j++) {
									if (chilrenContents[j].isFile()
											&& chilrenContents[j].getName()
													.endsWith(".zip")) {
										try {
											zipInputStream = new ZipInputStream(
													new FileInputStream(
															chilrenContents[j]));
											activitiDeployService
													.addZipInputStream(zipInputStream);
											zipInputStream.close();
											zipInputStream = null;
										} catch (Exception ex) {
											ex.printStackTrace();
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}
