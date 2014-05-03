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
package com.glaf.survey.website.springmvc;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.survey.domain.Survey;
import com.glaf.survey.domain.SurveyResult;
import com.glaf.survey.service.SurveyResultService;
import com.glaf.survey.service.SurveyService;

@Controller("/public/survey")
@RequestMapping("/public/survey")
public class PublicSurveyController {

	protected static final Log logger = LogFactory
			.getLog(PublicSurveyController.class);

	protected SurveyService surveyService;

	protected SurveyResultService surveyResultService;

	@ResponseBody
	@RequestMapping("/post/{id}")
	public byte[] post(@PathVariable("id") String id, HttpServletRequest request)
			throws IOException {
		Survey survey = null;
		if (StringUtils.isNotEmpty(id)) {
			survey = surveyService.getSurvey(Long.parseLong(id));
			if (survey != null) {
				if (survey.getStatus() != 1) {
					return ResponseUtils.responseJsonResult(false,
							"投票主题已经结束，谢谢您的关注！");
				}
				Date now = new Date();
				if (survey.getEndDate() != null && survey.getEndDate().getTime() < now.getTime()) {
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
									"您已经投过票了，谢谢您的参与！");
						}
					}
				}
				if (survey.getRelations() != null
						&& !survey.getRelations().isEmpty()) {
					List<SurveyResult> surveyResults = new java.util.ArrayList<SurveyResult>();
					for (Survey relation : survey.getRelations()) {
						SurveyResult result = new SurveyResult();
						result.setIp(ip);
						result.setSurveyId(relation.getId());
						result.setSurveyDate(new Date());
						result.setResult(request.getParameter("result_"
								+ relation.getId()));
						surveyResults.add(result);
					}
					surveyResultService.saveAll(surveyResults);
				} else {
					SurveyResult result = new SurveyResult();
					result.setIp(ip);
					result.setSurveyId(survey.getId());
					result.setSurveyDate(new Date());
					result.setResult(request.getParameter("result"));
					surveyResultService.save(result);
				}
				return ResponseUtils.responseJsonResult(true, "操作成功，谢谢您的参与！");
			}
		}
		return ResponseUtils.responseJsonResult(false, "操作不成功，请稍候再试！");
	}

	@javax.annotation.Resource
	public void setSurveyResultService(SurveyResultService surveyResultService) {
		this.surveyResultService = surveyResultService;
	}

	@javax.annotation.Resource
	public void setSurveyService(SurveyService surveyService) {
		this.surveyService = surveyService;
	}

	@RequestMapping("/survey/{id}")
	public ModelAndView survey(@PathVariable("id") String id,
			HttpServletRequest request, ModelMap modelMap) throws IOException {
		RequestUtils.setRequestParameterToAttribute(request);
		Survey survey = null;
		if (StringUtils.isNotEmpty(id)) {
			survey = surveyService.getSurvey(Long.parseLong(id));
			if (survey != null) {
				request.setAttribute("survey", survey);
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("survey.survey");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/survey/survey", modelMap);
	}

}
