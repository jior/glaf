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

package com.glaf.base.modules.sys.model;

import java.io.Serializable;
import java.util.Date;

public class SerialNumber implements Serializable {
	private static final long serialVersionUID = 7285967860734876783L;
	private long id;
	private String moduleNo;
	private Date lastDate;
	private int intervelNo;
	private int currentSerail;

	public int getCurrentSerail() {
		return currentSerail;
	}

	public long getId() {
		return id;
	}

	public int getIntervelNo() {
		return intervelNo;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public String getModuleNo() {
		return moduleNo;
	}

	public void setCurrentSerail(int currentSerail) {
		this.currentSerail = currentSerail;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIntervelNo(int intervelNo) {
		this.intervelNo = intervelNo;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}

}