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

package com.glaf.core.xml;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.util.FileUtils;

public class FileExtConverter implements FieldConverter {
	protected static final Log LOG = LogFactory.getLog(FileExtConverter.class);

	public void convert(String fromName, String toName,
			Map<String, Object> dataMap) {
		String filename = (String) dataMap.get(fromName);
		if (filename != null) {
			String fileExt = FileUtils.getFileExt(filename);
			dataMap.put(toName, fileExt);
		}
	}

}