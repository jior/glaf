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
package com.glaf.oa.assesssort.mapper;

import java.util.*;

import org.springframework.stereotype.Component;

import com.glaf.oa.assessquestion.model.AssessortTree;
import com.glaf.oa.assesssort.model.*;
import com.glaf.oa.assesssort.query.*;
import com.glaf.base.modules.sys.model.BaseDataInfo;

@Component
public interface AssesssortMapper {

	void deleteAssesssorts(AssesssortQuery query);

	void deleteAssesssortById(Long id);

	Assesssort getAssesssortById(Long id);

	int getAssesssortCount(AssesssortQuery query);

	List<Assesssort> getAssesssorts(AssesssortQuery query);

	void insertAssesssort(Assesssort model);

	void updateAssesssort(Assesssort model);

	List<AssessortTree> getParentsInfoByDictId(Integer dictId);

	List<BaseDataInfo> getAssessTypeByCode(String typeCode);

	List<BaseDataInfo> getAssessTypeById(Long id);

	List<BaseDataInfo> getAssessTypeByStandardAndSortIds(AssesssortQuery query);

	// List<BaseDataInfo> getAssessTopSortByCode( String code );
}