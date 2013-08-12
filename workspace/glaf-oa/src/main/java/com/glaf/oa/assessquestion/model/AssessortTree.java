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
package com.glaf.oa.assessquestion.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.glaf.oa.assesscontent.model.Assesscontent;
import com.glaf.base.modules.sys.model.BaseDataInfo;

public class AssessortTree implements Serializable {

	private static final long serialVersionUID = 1L;

	private int aid;
	private String aname;

	private int bid;
	private String bname;

	private int cid;
	private String cname;

	private int deep;

	private String tree;

	private List<Assesscontent> contentList;

	private List<AssessortTree> subTreeList;

	private Map<String, AssessortTree> treeMap;

	private BaseDataInfo baseInfo;

	private int contentSize;

	private int subTreeSize;

	public String getTree() {

		String t = aname + "\\" + bname + "\\" + cname;
		this.tree = t;
		return tree;
	}

	public int getContentSize() {

		return contentSize;
	}

	public void setContentSize(int contentSize) {

		this.contentSize = contentSize;
	}

	public int getSubTreeSize() {

		return subTreeSize;
	}

	public void setSubTreeSize(int subTreeSize) {

		this.subTreeSize = subTreeSize;
	}

	public BaseDataInfo getBaseInfo() {

		return baseInfo;
	}

	public void setBaseInfo(BaseDataInfo baseInfo) {

		this.baseInfo = baseInfo;
	}

	public List<AssessortTree> getSubTreeList() {

		return subTreeList;
	}

	public void setSubTreeList(List<AssessortTree> subTreeList) {

		this.subTreeList = subTreeList;
	}

	public Map<String, AssessortTree> getTreeMap() {

		return treeMap;
	}

	public void setTreeMap(Map<String, AssessortTree> treeMap) {

		this.treeMap = treeMap;
	}

	public List<Assesscontent> getContentList() {

		return contentList;
	}

	public int getDeep() {

		return deep;
	}

	public void setDeep(int deep) {

		this.deep = deep;
	}

	public void setContentList(List<Assesscontent> contentList) {

		this.contentList = contentList;
	}

	public void setTree(String tree) {

		this.tree = tree;
	}

	public int getAid() {

		return aid;
	}

	public void setAid(int aid) {

		this.aid = aid;
	}

	public String getAname() {

		return aname;
	}

	public void setAname(String aname) {

		this.aname = aname;
	}

	public int getBid() {

		return bid;
	}

	public void setBid(int bid) {

		this.bid = bid;
	}

	public String getBname() {

		return bname;
	}

	public void setBname(String bname) {

		this.bname = bname;
	}

	public int getCid() {

		return cid;
	}

	public void setCid(int cid) {

		this.cid = cid;
	}

	public String getCname() {

		return cname;
	}

	public void setCname(String cname) {

		this.cname = cname;
	}

}