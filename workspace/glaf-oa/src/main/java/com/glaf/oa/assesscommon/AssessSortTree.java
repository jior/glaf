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
package com.glaf.oa.assesscommon;

import java.io.Serializable;
import com.glaf.base.modules.sys.model.BaseDataInfo;

public class AssessSortTree implements Serializable {

	private static final long serialVersionUID = 1L;

	private int aid;

	private String aname;

	private int bid;

	private String bname;

	private String tree;

	private int deep;

	private BaseDataInfo baseInfo;

	private int contentSize;

	private int subTreeSize;

	public int getAid() {
		return aid;
	}

	public String getAname() {
		return aname;
	}

	public BaseDataInfo getBaseInfo() {
		return baseInfo;
	}

	public int getBid() {
		return bid;
	}

	public String getBname() {
		return bname;
	}

	public int getContentSize() {
		return contentSize;
	}

	public int getDeep() {
		return deep;
	}

	public int getSubTreeSize() {
		return subTreeSize;
	}

	public String getTree() {
		String t = aname + "\\" + bname;
		this.tree = t;
		return tree;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public void setBaseInfo(BaseDataInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public void setContentSize(int contentSize) {
		this.contentSize = contentSize;
	}

	public void setDeep(int deep) {
		this.deep = deep;
	}

	public void setSubTreeSize(int subTreeSize) {
		this.subTreeSize = subTreeSize;
	}

	public void setTree(String tree) {
		this.tree = tree;
	}

}