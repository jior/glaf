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

package com.glaf.survey.web.rest;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.survey.domain.Survey;
import com.glaf.survey.domain.SurveyResult;
import com.glaf.survey.service.SurveyResultService;
import com.glaf.survey.service.SurveyService;

@Controller
@Path("/rs/base/survey")
public class SurveyResourceRest {
	protected static final Log logger = LogFactory
			.getLog(SurveyResourceRest.class);

	protected SurveyService surveyService;

	protected SurveyResultService surveyResultService;

	@GET
	@POST
	@Path("/post")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] post(@Context HttpServletRequest request) throws IOException {
		Survey survey = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			survey = surveyService.getSurvey(RequestUtils
					.getLong(request, "id"));
			if (survey != null) {
				if (survey.getStatus() != 0) {
					return ResponseUtils.responseJsonResult(false,
							"调查主题已经结束，谢谢您的关注！");
				}
				Date now = new Date();
				if (survey.getEndDate() != null
						&& survey.getEndDate().getTime() < now.getTime()) {
					return ResponseUtils.responseJsonResult(false,
							"调查主题已经结束，谢谢您的关注！");
				}
				String ip = RequestUtils.getIPAddress(request);
				if (survey.getLimitFlag() == 1) {// 限制每个IP地址投票次数
					SurveyResult result = surveyService.getLatestSurveyResult(
							survey.getId(), ip);
					if (result != null) {
						Date surveyDate = result.getSurveyDate();
						long ts = now.getTime() - surveyDate.getTime();
						if (ts / (1000 * 60) <= survey.getLimitTimeInterval()) {
							return ResponseUtils.responseJsonResult(false,
									"您已经投过票了，谢谢您的关注！");
						}
					}
				}
				if (survey.getRelations() != null
						&& !survey.getRelations().isEmpty()) {
					List<SurveyResult> wxSurveyResults = new java.util.concurrent.CopyOnWriteArrayList<SurveyResult>();
					for (Survey relation : survey.getRelations()) {
						SurveyResult result = new SurveyResult();
						result.setIp(ip);
						result.setSurveyId(relation.getId());
						result.setSurveyDate(new Date());
						result.setResult(request.getParameter("result_"
								+ relation.getId()));
						wxSurveyResults.add(result);
					}
					surveyResultService.saveAll(wxSurveyResults);
				} else {
					SurveyResult result = new SurveyResult();
					result.setIp(ip);
					result.setSurveyId(survey.getId());
					result.setSurveyDate(new Date());
					result.setResult(request.getParameter("result"));
					surveyResultService.save(result);
				}
			}
		}
		return ResponseUtils.responseJsonResult(false, "调查不成功，请稍候再试！");
	}

	@javax.annotation.Resource
	public void setSurveyResultService(SurveyResultService surveyResultService) {
		this.surveyResultService = surveyResultService;
	}

	@javax.annotation.Resource
	public void setSurveyService(SurveyService surveyService) {
		this.surveyService = surveyService;
	}

}
