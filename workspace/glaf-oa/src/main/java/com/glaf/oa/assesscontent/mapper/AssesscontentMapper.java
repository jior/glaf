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
package com.glaf.oa.assesscontent.mapper;

import java.util.*;
import org.springframework.stereotype.Component;
import com.glaf.oa.assesscontent.model.*;
import com.glaf.oa.assesscontent.query.*;
import com.glaf.oa.assessscore.query.AssessscoreQuery;

@Component
public interface AssesscontentMapper {

	void deleteAssesscontents(AssesscontentQuery query);

	void deleteAssesscontentById(Long id);

	Assesscontent getAssesscontentById(Long id);

	int getAssesscontentCount(AssesscontentQuery query);

	List<Assesscontent> getAssesscontents(AssesscontentQuery query);

	List<AssesscontentAndScore> getAssesscontentAndScoreList(
			AssessscoreQuery scoreQuery);

	void insertAssesscontent(Assesscontent model);

	void updateAssesscontent(Assesscontent model);

	void deleteAssesscontentByParentId(Long parentId);

}