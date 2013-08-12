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

import com.glaf.oa.assesscontent.model.Assesscontent;
import com.glaf.oa.assesssort.model.Assesssort;

public class AssessQuestionDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 问题索引
	 */
	private String index;

	/**
	 * 分类层次结构
	 */
	private AssessortTree sortTree;

	/**
	 * 考核内容
	 */
	private List<Assesscontent> contentList;

	/**
	 * 指标考核分类
	 * 
	 * @return
	 */

	private Assesssort assessSort;

	public String getIndex() {

		return index;
	}

	public void setIndex(String index) {

		this.index = index;
	}

	public AssessortTree getSortTree() {

		return sortTree;
	}

	public void setSortTree(AssessortTree sortTree) {

		this.sortTree = sortTree;
	}

	public List<Assesscontent> getContentList() {

		return contentList;
	}

	public void setContentList(List<Assesscontent> contentList) {

		this.contentList = contentList;
	}

	public Assesssort getAssessSort() {

		return assessSort;
	}

	public void setAssessSort(Assesssort assessSort) {

		this.assessSort = assessSort;
	}

}