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
package com.glaf.oa.assessquestion.web.springmvc;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.oa.assesscontent.model.Assesscontent;
import com.glaf.oa.assesscontent.query.AssesscontentQuery;
import com.glaf.oa.assesscontent.service.AssesscontentService;
import com.glaf.oa.assessinfo.service.AssessinfoService;
import com.glaf.oa.assessquestion.model.AssessQuestionDetails;
import com.glaf.oa.assessquestion.model.AssessortTree;
import com.glaf.oa.assessquestion.model.Assessquestion;
import com.glaf.oa.assessquestion.model.AssessquestionExt;
import com.glaf.oa.assessquestion.query.AssessquestionQuery;
import com.glaf.oa.assessquestion.service.AssessquestionService;
import com.glaf.oa.assessquestion.util.AQUtils;
import com.glaf.oa.assesssort.model.Assesssort;
import com.glaf.oa.assesssort.query.AssesssortQuery;
import com.glaf.oa.assesssort.service.AssesssortService;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

@Controller("/oa/assessquestion")
@RequestMapping("/oa/assessquestion")
public class AssessquestionController {

	protected static final Log logger = LogFactory
			.getLog(AssessquestionController.class);

	protected AssessquestionService assessquestionService;

	protected AssesssortService assesssortService;

	protected AssesscontentService assesscontentService;

	protected AssessinfoService assessinfoService;

	public AssessquestionController() {

	}

	@javax.annotation.Resource
	public void setAssessquestionService(
			AssessquestionService assessquestionService) {
		this.assessquestionService = assessquestionService;
	}

	@javax.annotation.Resource
	public void setAssessinfoService(AssessinfoService assessinfoService) {
		this.assessinfoService = assessinfoService;
	}

	@javax.annotation.Resource
	public void setAssesssortService(AssesssortService assesssortService) {
		this.assesssortService = assesssortService;
	}

	@javax.annotation.Resource
	public void setAssesscontentService(
			AssesscontentService assesscontentService) {
		this.assesscontentService = assesscontentService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Assessquestion assessquestion = new Assessquestion();
		Tools.populate(assessquestion, params);

		assessquestion.setTitle(request.getParameter("title"));
		assessquestion.setValiddate(RequestUtils.getDate(request, "validdate"));
		assessquestion.setRate(RequestUtils.getInt(request, "rate"));
		assessquestion.setIseffective(RequestUtils.getInt(request,
				"iseffective"));
		assessquestion.setTargetsum(RequestUtils
				.getDouble(request, "targetsum"));
		assessquestion.setCreateBy(request.getParameter("createBy"));
		assessquestion.setCreateDate(RequestUtils
				.getDate(request, "createDate"));
		assessquestion.setUpdateDate(RequestUtils
				.getDate(request, "updateDate"));
		assessquestion.setUpdateBy(request.getParameter("updateBy"));

		assessquestion.setCreateBy(actorId);

		assessquestionService.save(assessquestion);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveAssessquestion")
	public byte[] saveAssessquestion(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Assessquestion assessquestion = new Assessquestion();
		try {
			Tools.populate(assessquestion, params);
			assessquestion.setTitle(request.getParameter("title"));
			assessquestion.setValiddate(RequestUtils.getDate(request,
					"validdate"));
			assessquestion.setRate(RequestUtils.getInt(request, "rate"));
			assessquestion.setIseffective(RequestUtils.getInt(request,
					"iseffective"));
			assessquestion.setTargetsum(RequestUtils.getDouble(request,
					"targetsum"));
			assessquestion.setCreateBy(request.getParameter("createBy"));
			assessquestion.setCreateDate(RequestUtils.getDate(request,
					"createDate"));
			assessquestion.setUpdateDate(RequestUtils.getDate(request,
					"updateDate"));
			assessquestion.setUpdateBy(request.getParameter("updateBy"));
			assessquestion.setCreateBy(actorId);
			this.assessquestionService.save(assessquestion);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Assessquestion assessquestion = assessquestionService
				.getAssessquestion(RequestUtils.getLong(request, "qustionid"));

		assessquestion.setTitle(request.getParameter("title"));
		assessquestion.setValiddate(RequestUtils.getDate(request, "validdate"));
		assessquestion.setRate(RequestUtils.getInt(request, "rate"));
		assessquestion.setIseffective(RequestUtils.getInt(request,
				"iseffective"));
		assessquestion.setTargetsum(RequestUtils
				.getDouble(request, "targetsum"));
		assessquestion.setCreateBy(request.getParameter("createBy"));
		assessquestion.setCreateDate(RequestUtils
				.getDate(request, "createDate"));
		assessquestion.setUpdateDate(RequestUtils
				.getDate(request, "updateDate"));
		assessquestion.setUpdateBy(request.getParameter("updateBy"));

		assessquestionService.save(assessquestion);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long qustionid = RequestUtils.getLong(request, "qustionid");
		String qustionids = request.getParameter("qustionids");
		if (StringUtils.isNotEmpty(qustionids)) {
			StringTokenizer token = new StringTokenizer(qustionids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Assessquestion assessquestion = assessquestionService
							.getAssessquestion(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (assessquestion != null
							&& (StringUtils.equals(
									assessquestion.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						// assessquestion.setDeleteFlag(1);
						assessquestionService.save(assessquestion);
					}
				}
			}
		} else if (qustionid != null) {
			Assessquestion assessquestion = assessquestionService
					.getAssessquestion(Long.valueOf(qustionid));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (assessquestion != null
					&& (StringUtils.equals(assessquestion.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				// assessquestion.setDeleteFlag(1);
				assessquestionService.save(assessquestion);
			}
		}
	}

	/**
	 * 岗位考核指标制作，选择分类后插入到表中
	 * 
	 * @param request
	 * @param modelMap
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping("/insertTypeInMakeAssessIndex")
	public byte[] insertTypeInMakeAssessIndex(HttpServletRequest request,
			ModelMap modelMap) throws IOException {
		JSONObject result = new JSONObject();
		result.put("result", "FAILD");
		result.put("qid", "");
		result.put("sid", "");

		// 具体标准ID
		long sid = RequestUtils.getInt(request, "sid");

		String squestionid = request.getParameter("questionId");
		long questionid = 0;
		Assessquestion assessquestion = null;
		Date now = new Date();
		if (StringUtils.isNotEmpty(squestionid)) {
			questionid = Long.valueOf(squestionid);
			assessquestion = assessquestionService
					.getAssessquestion(questionid);
		}
		if (assessquestion == null) {
			assessquestion = new Assessquestion();
			assessquestion.setCreateDate(now);
			assessquestion.setTitle("");
		}

		assessquestion.setUpdateDate(now);
		assessquestionService.save(assessquestion);
		Assesssort assesssort = new Assesssort();
		assesssort.setSortid(sid);
		assesssort.setQustionid(assessquestion.getQustionid());
		assesssort.setCreateDate(now);
		assesssort.setUpdateDate(now);
		assesssortService.save(assesssort);
		result.put("result", "SUCCESS");
		result.put("qid", assessquestion.getQustionid());
		result.put("sid", assesssort.getAssesssortid());
		return result.toJSONString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/detail")
	public byte[] detail(HttpServletRequest request) throws IOException {

		Assessquestion assessquestion = assessquestionService
				.getAssessquestion(RequestUtils.getLong(request, "qustionid"));

		JSONObject rowJSON = assessquestion.toJsonObject();
		return rowJSON.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		Assessquestion assessquestion = assessquestionService
				.getAssessquestion(RequestUtils.getLong(request, "qustionid"));

		List<BaseDataInfo> isValidList = AQUtils
				.getBaseInfoByCode("ASSESS_ISVALID");
		Map<String, BaseDataInfo> validMap = AQUtils
				.baseList2MapByCode(isValidList);
		if (assessquestion != null) {
			assessquestion.setIseffectiveText(validMap.get(
					assessquestion.getIseffective() + "").getName());
			modelMap.put("isValidList", isValidList);
			modelMap.put("assessquestion", assessquestion);
		}
		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (assessquestion != null) {
				canUpdate = true;
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("assessquestion.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/assessquestion/assessquestionEdit",
				modelMap);
	}

	/**
	 * 岗位考核
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {

		RequestUtils.setRequestParameterToAttribute(request);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("assessquestion.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/assessquestion/assessquestionList");
	}

	@RequestMapping("/viewAssess")
	public ModelAndView viewAssess(HttpServletRequest request, ModelMap modelMap) {
		String squestionId = request.getParameter("questionId");
		// 查看或修改状态
		String editType = request.getParameter("editType");
		modelMap.put("editType", editType);
		long questionId = 0;
		// Integer sid = 0; //
		AssessquestionExt assessquestion = new AssessquestionExt();
		List<AssessortTree> sortTreeList = new ArrayList<AssessortTree>();
		if (StringUtils.isNotBlank(squestionId)) {
			questionId = Long.valueOf(squestionId);
			Assessquestion assessquestion1 = assessquestionService
					.getAssessquestion(questionId);
			if (assessquestion1 != null) {
				assessquestion.setQustionid(assessquestion1.getQustionid());
				assessquestion.setTitle(assessquestion1.getTitle());
				assessquestion.setValiddate(assessquestion1.getValiddate());
				assessquestion.setRate(assessquestion1.getRate());
				assessquestion.setIseffective(assessquestion1.getIseffective());
				assessquestion.setTargetsum(assessquestion1.getTargetsum());

				// assessquestion.setSortTreeList( sortTreeList );
				AssesssortQuery sortQuery = new AssesssortQuery();
				sortQuery.setQustionid(assessquestion.getQustionid());
				// 一张问卷有多个分类类型,一层 共分三层次
				List<Assesssort> assessSortList = assesssortService
						.list(sortQuery);
				List<Long> sortIds = new ArrayList<Long>();
				List<Long> assessSortIds = new ArrayList<Long>();
				Map<Long, Assesssort> assessSortMap = new HashMap<Long, Assesssort>();
				for (Assesssort assessSort : assessSortList) {
					assessSortIds.add(assessSort.getAssesssortid());
					sortIds.add(assessSort.getSortid());
					assessSortMap.put(assessSort.getSortid(), assessSort);
				}

				AssesscontentQuery assessContentQuery = new AssesscontentQuery();
				assessContentQuery.setSortids(assessSortIds);
				List<Assesscontent> contentList = assesscontentService
						.list(assessContentQuery);
				Map<String, Assesscontent> contentMap = contentList2MapByContentId(contentList); // 可按contentId取
				Map<String, BaseDataInfo> level1Map = new HashMap<String, BaseDataInfo>();// 树型一层内容
				Map<String, BaseDataInfo> level2Map = new HashMap<String, BaseDataInfo>();// 树型二层内容
				Map<String, BaseDataInfo> level3Map = new HashMap<String, BaseDataInfo>();// 树型三层内容
				sortQuery = new AssesssortQuery();
				sortQuery.setSortids(sortIds);
				// 获得一层指标分类信息 ，如：企业工作指标，厂家工作指标
				List<BaseDataInfo> sortTypeLevelList1 = assesssortService
						.getAssessTypeByCode("ASSESS_CLASS");
				for (BaseDataInfo baseInfo1 : sortTypeLevelList1) {
					boolean l1 = false;
					// 获得二层类型 如：厂家要求工作完成情况 学习与成长
					List<BaseDataInfo> sortTypeLevelList2 = assesssortService
							.getAssessTypeById(baseInfo1.getId());
					for (BaseDataInfo baseInfo2 : sortTypeLevelList2) {
						boolean l2 = false;
						// 二层ID
						sortQuery.setQustionid(baseInfo2.getId());
						// 如果有结果表示该类下有指标
						List<BaseDataInfo> sortTypeLevelList3 = assesssortService
								.getAssessTypeByStandardAndSortIds(sortQuery);
						if (sortTypeLevelList3 != null
								&& sortTypeLevelList3.size() > 0) {
							l1 = true;// L1下存在内容
							l2 = true;// l2下存在内容
							for (BaseDataInfo baseInfo3 : sortTypeLevelList3) {
								level3Map
										.put(baseInfo3.getId() + "", baseInfo3);
							}
						}
						if (l2) {
							level2Map.put(baseInfo2.getId() + "", baseInfo2);
						}
					}
					if (l1) {
						level1Map.put(baseInfo1.getId() + "", baseInfo1);
					}
				}

				// 一层
				Iterator<BaseDataInfo> iter1 = level1Map.values().iterator();
				while (iter1.hasNext()) {
					BaseDataInfo info1 = iter1.next();
					AssessortTree sortTree1 = new AssessortTree();
					boolean flag1 = false;
					int contentLiseSize = 0;
					// 二层
					List<BaseDataInfo> level2List = AQUtils
							.getBaseListByParentId(level2Map,
									(int) info1.getId());
					List<AssessortTree> subTreeList = new ArrayList<AssessortTree>();
					AssessortTree sortTree2 = new AssessortTree();
					for (BaseDataInfo info2 : level2List) {
						boolean flag2 = false;
						int subTreeSize = 0;
						// 三层
						List<BaseDataInfo> level3List = AQUtils
								.getBaseListByParentId(level3Map,
										(int) info2.getId());
						if (level3List != null && !level3List.isEmpty()) {
							flag1 = true;
							flag2 = true;
							List<Assesscontent> contentList1 = new ArrayList<Assesscontent>();
							for (BaseDataInfo info3 : level3List) {
								List<Assesscontent> contentTempList = getContentListBySortId(
										contentMap,
										assessSortMap.get(info3.getId())
												.getAssesssortid() + "");
								// 复制到数组
								addToList(contentTempList, contentList1);
							}
							sortTree2.setContentList(contentList1);
							subTreeSize = contentList1.size();
							contentLiseSize += subTreeSize;
						}
						if (flag2) {
							sortTree2.setSubTreeSize(subTreeSize);
							sortTree2.setBid((int) info2.getId());
							sortTree2.setBname(info2.getName());
							subTreeList.add(sortTree2);
						}
					}

					if (flag1 && contentLiseSize > 0) {
						sortTree1.setSubTreeList(subTreeList);
						sortTree1.setContentSize(contentLiseSize);
						sortTree1.setAid((int) info1.getId());
						sortTree1.setAname(info1.getName());
						sortTreeList.add(sortTree1);
					}
				}
			}
		}
		assessquestion.setSortTreeList(sortTreeList);

		// ASSESS_ISVALID 是否有效列表
		List<BaseDataInfo> isValidList = AQUtils
				.getBaseInfoByCode("ASSESS_ISVALID");
		// request.setAttribute( "isValidList", isValidList );
		// 频率列表
		List<BaseDataInfo> frequencyList = AQUtils
				.getBaseInfoByCode("ASSESS_AUDIT_RATE");
		// request.setAttribute( "frequencyList", frequencyList );
		Map<String, BaseDataInfo> isValidMap = AQUtils
				.baseList2MapByCode(isValidList);
		Map<String, BaseDataInfo> frequencyMap = AQUtils
				.baseList2MapByCode(frequencyList);
		modelMap.put("assessquestion", assessquestion);
		modelMap.put(
				"assessRate",
				AQUtils.getValueByCode(frequencyMap,
						assessquestion.getRate() + "").getName());
		modelMap.put(
				"assessEffective",
				AQUtils.getValueByCode(isValidMap,
						assessquestion.getIseffective() + "").getName());
		return new ModelAndView("/oa/assessquestion/assessIndexQuery");
	}

	private void addToList(List<Assesscontent> srcList,
			List<Assesscontent> destList) {
		for (Assesscontent obj : srcList) {
			destList.add(obj);
		}
	}

	private List<Assesscontent> getContentListBySortId(
			Map<String, Assesscontent> contentMap, String sortId) {

		List<Assesscontent> contentList = new ArrayList<Assesscontent>();
		Iterator<Assesscontent> iter = contentMap.values().iterator();
		while (iter.hasNext()) {
			Assesscontent content = iter.next();
			if ((content.getSortid() + "").equals(sortId)) {
				contentList.add(content);
			}
		}
		return contentList;
	}

	private Map<String, Assesscontent> contentList2MapByContentId(
			List<Assesscontent> contentList) {

		Map<String, Assesscontent> contentMap = new HashMap<String, Assesscontent>();
		for (Assesscontent content : contentList) {
			contentMap.put(content.getContentid() + "", content);
		}
		return contentMap;
	}

	/**
	 * 岗位考核指标制作
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/queryAssessIndex")
	public ModelAndView queryAssessIndex(HttpServletRequest request,
			ModelMap modelMap) {

		// LoginContext loginContext = RequestUtils.getLoginContext( request );
		RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap( request );
		// ASSESS_ISVALID 是否有效列表
		List<BaseDataInfo> isValidList = AQUtils
				.getBaseInfoByCode("ASSESS_ISVALID");
		request.setAttribute("isValidList", isValidList);
		// 频率列表
		List<BaseDataInfo> frequencyList = AQUtils
				.getBaseInfoByCode("ASSESS_AUDIT_RATE");
		request.setAttribute("frequencyList", frequencyList);
		String squestionId = request.getParameter("questionId");
		// 查看或修改状态
		String editType = request.getParameter("editType");
		modelMap.put("editType", editType);
		long questionId = 0;
		// Integer sid = 0; //
		AssessquestionExt assessquestion = new AssessquestionExt();
		List<AssessQuestionDetails> questionDetails = new ArrayList<AssessQuestionDetails>();
		if (StringUtils.isNotBlank(squestionId)) {
			questionId = Long.valueOf(squestionId);
			Assessquestion assessquestion1 = assessquestionService
					.getAssessquestion(questionId);
			if (assessquestion1 != null) {
				assessquestion.setQustionid(assessquestion1.getQustionid());
				assessquestion.setTitle(assessquestion1.getTitle());
				assessquestion.setValiddate(assessquestion1.getValiddate());
				assessquestion.setRate(assessquestion1.getRate());
				assessquestion.setIseffective(assessquestion1.getIseffective());
				assessquestion.setTargetsum(assessquestion1.getTargetsum());

				AssesssortQuery sortQuery = new AssesssortQuery();
				sortQuery.setQustionid(assessquestion.getQustionid());
				// 一张问卷有多个分类类型
				List<Assesssort> assessSortList = assesssortService
						.list(sortQuery);
				if (assessSortList != null && !assessSortList.isEmpty()) {
					AssessQuestionDetails details = null;
					int index = 0;
					for (Assesssort assessSort : assessSortList) {
						details = new AssessQuestionDetails();
						details.setIndex(++index + "");
						// 返回唯一的记录
						List<AssessortTree> treeList = assesssortService
								.getParentsInfoByDictId(assessSort.getSortid()
										.intValue());
						AssessortTree sortTree = null;
						if (treeList != null && !treeList.isEmpty()) {
							sortTree = treeList.get(0);
						} else {
							sortTree = new AssessortTree();
						}
						details.setSortTree(sortTree);
						Long assessSortId = assessSort.getAssesssortid();
						AssesscontentQuery assessContentQuery = new AssesscontentQuery();
						assessContentQuery.setSortid(assessSortId);
						// 指标考核分类ID对应 一条内容
						List<Assesscontent> contentList = assesscontentService
								.list(assessContentQuery);
						if (contentList == null) {
							contentList = new ArrayList<Assesscontent>();
						}
						// Assessinfo assessInfo = new Assessinfo();
						// for( Assesscontent content : contentList ) {
						// assessInfo = assessinfoService.getAssessinfo(
						// content.getAssessId() );
						// content.setAssessInfo( assessInfo );
						// }
						details.setContentList(contentList);
						details.setAssessSort(assessSort);
						questionDetails.add(details);
					}
				}
			}
		}
		assessquestion.setQuestionDetails(questionDetails);
		modelMap.put("assessquestion", assessquestion);
		modelMap.put("assessRate", assessquestion.getRate() + "");
		modelMap.put("assessEffective", assessquestion.getIseffective() + "");
		return new ModelAndView("/oa/assessquestion/makeAssessIndex");
	}

	/**
	 * 点击添加指标类型时保存标题信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/saveMakeAssessTitle")
	public ModelAndView saveMakeAssessTitle(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws IOException {
		RequestUtils.setRequestParameterToAttribute(request);
		Assessquestion question = new Assessquestion();
		Long questionid = RequestUtils.getLong(request, "questionId");
		String title = RequestUtils.getString(request, "title");
		Date validDate = RequestUtils.getDate(request, "validDate");
		int rate = RequestUtils.getInt(request, "rate");
		int iseffective = RequestUtils.getInt(request, "iseffective");
		question.setQustionid(questionid == 0 ? null : questionid);
		question.setTitle(title);
		question.setValiddate(validDate);
		question.setRate(rate);
		question.setIseffective(iseffective);
		question.setUpdateDate(new Date());
		try {
			assessquestionService.save(question);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			ModelAndView mav = new ModelAndView();
			mav.addObject("message", "操作失败。");
			return mav;
		}
		modelMap.put("questionid", question.getQustionid());
		return new ModelAndView("/oa/assessquestion/makeAssessIndex", modelMap);
	}

	/**
	 * 保存岗位考核列表
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/saveMakeAssessIndex")
	public ModelAndView saveMakeAssessIndex(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws IOException {

		// LoginContext loginContext = RequestUtils.getLoginContext( request );
		RequestUtils.setRequestParameterToAttribute(request);
		// Map<String, Object> params = RequestUtils.getParameterMap( request );
		Assesscontent content = null;
		Assessquestion question = new Assessquestion();
		Long questionid = RequestUtils.getLong(request, "questionId");
		if (questionid == 0) {
			questionid = null;
		}
		String title = RequestUtils.getString(request, "title");
		Date validDate = RequestUtils.getDate(request, "validDate");
		int rate = RequestUtils.getInt(request, "rate");
		int iseffective = RequestUtils.getInt(request, "iseffective");
		question.setQustionid(questionid);
		question.setTitle(title);
		question.setValiddate(validDate);
		question.setRate(rate);
		question.setIseffective(iseffective);
		question.setUpdateDate(new Date());
		try {
			assessquestionService.save(question);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			ModelAndView mav = new ModelAndView();
			mav.addObject("message", "操作失败。");
			return mav;
		}

		String[] sortIds = request.getParameterValues("sortIds");
		if (sortIds != null && sortIds.length > 0) {
			for (String ssortId : sortIds) {
				String[] scontentIds = request.getParameterValues("contentIds"
						+ ssortId);
				String[] sstandards = request.getParameterValues("standards"
						+ ssortId);
				if (scontentIds != null && scontentIds.length > 0) {
					for (int k = 0; k < scontentIds.length; k++) {
						String scontent = scontentIds[k];
						Long contentid = Long.valueOf(scontent);
						Double standard = Double.valueOf(sstandards[k]);
						content = new Assesscontent();
						content.setContentid(contentid);
						content.setStandard(standard);
						content.setUpdateDate(new Date());
						try {
							assesscontentService.save(content);
						} catch (Exception ex) {
							ex.printStackTrace();
							logger.error(ex);
							ModelAndView mav = new ModelAndView();
							mav.addObject("message", "操作失败。");
							return mav;
						}
					}
				}

			}
		}
		modelMap.put("questionid", question.getQustionid());
		return new ModelAndView("/oa/assessquestion/makeAssessIndex", modelMap);
	}

	/**
	 * 查询岗位考核列表
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/viewPostAssess")
	public ModelAndView viewPostAssess(HttpServletRequest request,
			ModelMap modelMap) {

		return new ModelAndView("/oa/assessquestion/postAssessList", modelMap);
	}

	@RequestMapping("/queryPostAssess")
	@ResponseBody
	public byte[] queryPostAssess(HttpServletRequest request, ModelMap modelMap)
			throws IOException {

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		AssessquestionQuery query = new AssessquestionQuery();
		Tools.populate(query, params);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}
		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;

		int pageNo = ParamUtils.getInt(params, "page");
		limit = ParamUtils.getInt(params, "rows");
		start = (pageNo - 1) * limit;
		orderName = ParamUtils.getString(params, "sortName");
		order = ParamUtils.getString(params, "sortOrder");

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		String titleLike = request.getParameter("titleLike");
		query.setTitleLike(titleLike);
		query.setValiddateGreaterThanOrEqual(new Date());

		query.setIseffective(1);
		JSONObject result = new JSONObject();
		int total = assessquestionService
				.getAssessquestionCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			List<Assessquestion> assessList = assessquestionService
					.getAssessquestionsByQueryCriteria(start, limit, query);
			// 指标分类 ASSESS_CLASS
			// ENTERPRISE_INDEX

			// FACTORY_INDEX
			// 考核频率

			// quarter 季度
			// monthly =月度
			if (assessList != null && !assessList.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Assessquestion assessquestion : assessList) {
					JSONObject rowJSON = assessquestion.toJsonObject();

					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {

		RequestUtils.setRequestParameterToAttribute(request);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("assessquestion.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/assessquestion/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {

		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		AssessquestionQuery query = new AssessquestionQuery();
		Tools.populate(query, params);
		query.setLoginContext(loginContext);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}
		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;

		int pageNo = ParamUtils.getInt(params, "page");
		limit = ParamUtils.getInt(params, "rows");
		start = (pageNo - 1) * limit;
		orderName = ParamUtils.getString(params, "sortName");
		order = ParamUtils.getString(params, "sortOrder");

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = assessquestionService
				.getAssessquestionCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			List<Assessquestion> list = assessquestionService
					.getAssessquestionsByQueryCriteria(start, limit, query);
			// 指标分类 ASSESS_CLASS
			// ENTERPRISE_INDEX

			// quarter 季度
			// monthly =月度
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Assessquestion assessquestion : list) {
					JSONObject rowJSON = assessquestion.toJsonObject();

					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {

		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils
					.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/oa/assessquestion/list", modelMap);
	}

}