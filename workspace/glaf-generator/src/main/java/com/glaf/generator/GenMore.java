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

package com.glaf.generator;

import java.io.*;

import com.glaf.core.domain.TableDefinition;
import com.glaf.core.xml.XmlReader;

public class GenMore {

	private final static String DEFAULT_CONFIG = "/templates/codegen/codegen_springmvc.xml";

	public static void main(String[] args) throws Exception {
		GenMore gen = new GenMore();
		gen.genAll(new File(args[0]), new File(args[1]));
	}

	public void genAll(File templateDir, File outputDir) throws Exception {
		File[] list = templateDir.listFiles();
		if (list != null && list.length > 0) {
			for (File file : list) {
				if (file.isDirectory()) {
					this.genAll(file, outputDir);
				} else {
					if (file.getName().endsWith("mapping.xml")) {
						FileInputStream fin = new FileInputStream(file);
						TableDefinition def = new XmlReader()
								.read(fin);
						JavaCodeGen gen = new JavaCodeGen();
						String config = System.getProperty("codegen.cfg");
						if (config == null) {
							config = DEFAULT_CONFIG;
						}
						gen.codeGen(def, new File(outputDir, ""), config);
					}
				}
			}
		}
	}

}