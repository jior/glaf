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


package org.jpage.core.mail.model;

import java.io.Serializable;

public class MailHeader implements Serializable {
	private static final long serialVersionUID = 1L;

	private String contentType;

	private String contentEncoding;

	private String contentId;

	private String filename;

	public MailHeader() {
	}

	public String getContentEncoding() {
		return contentEncoding;
	}

	public String getContentId() {
		return contentId;
	}

	public String getContentType() {
		return contentType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}

}