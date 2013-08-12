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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Administrator
 * 
 */
public class AssessquestionExt extends Assessquestion implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<AssessQuestionDetails> questionDetails;

	private List<AssessortTree> sortTreeList;

	public AssessquestionExt() {

	}

	public List<AssessQuestionDetails> getQuestionDetails() {

		return questionDetails;
	}

	public void setQuestionDetails(List<AssessQuestionDetails> questionDetails) {

		this.questionDetails = questionDetails;
	}

	public List<AssessortTree> getSortTreeList() {

		return sortTreeList;
	}

	public void setSortTreeList(List<AssessortTree> sortTreeList) {

		this.sortTreeList = sortTreeList;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}