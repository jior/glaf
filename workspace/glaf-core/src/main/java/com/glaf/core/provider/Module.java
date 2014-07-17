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

package com.glaf.core.provider;

/**
 * Models a simple version of the module present in the database. It is a simple
 * version because only the properties required for the module related
 * functionality are modeled here.
 * 
 */

public class Module extends ModelObject {
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer seqno;
	private String javaPackage;

	public String getJavaPackage() {
		return javaPackage;
	}

	@Override
	public String getName() {
		return name;
	}

	public Integer getSeqno() {
		return seqno;
	}

	public void setJavaPackage(String javaPackage) {
		this.javaPackage = javaPackage;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public void setSeqno(Integer seqno) {
		this.seqno = seqno;
	}
}
