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


package org.jpage.core.mail.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import org.apache.commons.lang.StringUtils;
import org.jpage.datacenter.file.DataFile;
 

public class JavaMailDataSource implements DataSource {

	private DataFile dataFile;

	public JavaMailDataSource(DataFile dataFile) {
		this.dataFile = dataFile;
	}

	public InputStream getInputStream() throws IOException {
		InputStream inputStream = null;
		try {
			if (dataFile.getInputStream() != null) {
				inputStream = dataFile.getInputStream();
			} else if (StringUtils.isNotBlank(dataFile.getDeviceId())) {

			} else {

			}
			if (inputStream == null) {
				throw new IOException("inputStream is null");
			}
			return inputStream;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IOException(ex.getMessage());
		}
	}

	public OutputStream getOutputStream() {
		throw new UnsupportedOperationException(
				"Read-only javax.activation.DataSource");
	}

	public String getContentType() {
		if (dataFile.getContentType() != null) {
			return dataFile.getContentType();
		}
		return "application/octet-stream";
	}

	public String getName() {
		return dataFile.getName();
	}

	public DataFile getDataFile() {
		return dataFile;
	}

	public void setDataFile(DataFile dataFile) {
		this.dataFile = dataFile;
	}
}