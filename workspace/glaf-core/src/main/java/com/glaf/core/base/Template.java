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

package com.glaf.core.base;

public class Template implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected String name;

	protected String desccription;

	protected String sysType;

	protected String templateType;

	protected String callbackUrl;

	protected String title;

	protected String content;

	protected String json;

	public Template() {

	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public String getContent() {
		return content;
	}

	public String getDesccription() {
		return desccription;
	}

	public String getJson() {
		return json;
	}

	public String getName() {
		return name;
	}

	public String getSysType() {
		return sysType;
	}

	public String getTemplateType() {
		return templateType;
	}

	public String getTitle() {
		return title;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDesccription(String desccription) {
		this.desccription = desccription;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
