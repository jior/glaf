package com.glaf.base.modules.sys.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.util.*;

import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.service.*;

@Controller("/sys/dictoryDefinition")
@RequestMapping("/sys/dictoryDefinition.do")
public class SysDictoryDefinitionController {
	@javax.annotation.Resource
	protected DictoryDefinitionService dictoryDefinitionService;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public SysDictoryDefinitionController() {

	}

	@RequestMapping(params = "method=edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Long nodeId = ParamUtils.getLong(params, "nodeId");
		String target = request.getParameter("target");
		if (StringUtils.isNotEmpty(target)) {
			Map<String, DictoryDefinition> defMap = new HashMap<String, DictoryDefinition>();
			List<DictoryDefinition> list = dictoryDefinitionService
					.getDictoryDefinitions(0L, target);
			if (nodeId > 0) {
				List<DictoryDefinition> rows = dictoryDefinitionService
						.getDictoryDefinitions(nodeId, target);
				if (rows != null && !rows.isEmpty()) {
					for (DictoryDefinition d : rows) {
						defMap.put(d.getName(), d);
					}
				}
			}
			if (list != null && !list.isEmpty()) {
				if (list != null && !list.isEmpty()) {
					for (DictoryDefinition d : list) {
						if (defMap.get(d.getName()) != null) {
							DictoryDefinition m = defMap.get(d.getName());
							d.setTitle(m.getTitle());
							d.setRequired(m.getRequired());
						}
					}
				}
				Collections.sort(list);
				request.setAttribute("list", list);
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dictoryDefinition.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/dictory_definition/edit",
				modelMap);
	}

	@ResponseBody
	@RequestMapping(params = "method=json")
	public byte[] json(HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Long nodeId = ParamUtils.getLong(params, "nodeId");
		String target = request.getParameter("target");
		if (StringUtils.isNotEmpty(target)) {
			Map<String, DictoryDefinition> defMap = new HashMap<String, DictoryDefinition>();
			List<DictoryDefinition> list = dictoryDefinitionService
					.getDictoryDefinitions(0L, target);
			if (nodeId > 0) {
				List<DictoryDefinition> rows = dictoryDefinitionService
						.getDictoryDefinitions(nodeId, target);
				if (rows != null && !rows.isEmpty()) {
					for (DictoryDefinition d : rows) {
						defMap.put(d.getName(), d);
					}
				}
			}
			if (list != null && !list.isEmpty()) {
				if (list != null && !list.isEmpty()) {
					for (DictoryDefinition d : list) {
						if (defMap.get(d.getName()) != null) {
							DictoryDefinition m = defMap.get(d.getName());
							d.setTitle(m.getTitle());
							d.setRequired(m.getRequired());
						}
					}
				}
				Collections.sort(list);
				JSONObject result = new JSONObject();
				JSONArray array = new JSONArray();
				for (DictoryDefinition d : list) {
					array.add(d.toJsonObject());
				}
				result.put("rows", array);
				logger.debug(result.toJSONString());
				return result.toJSONString().getBytes("UTF-8");
			}
		}

		return null;
	}

	@RequestMapping(params = "method=save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap,
			DictoryDefinition dictoryDefinition) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:" + params);
		String target = request.getParameter("target");
		Long nodeId = ParamUtils.getLong(params, "nodeId");
		if (StringUtils.isNotEmpty(target)) {
			List<DictoryDefinition> list = dictoryDefinitionService
					.getDictoryDefinitions(0L, target);
			if (list != null && !list.isEmpty()) {
				List<DictoryDefinition> rows = new ArrayList<DictoryDefinition>();
				for (DictoryDefinition m : list) {
					String title = request.getParameter(m.getName() + "_title");
					String required = request.getParameter(m.getName()
							+ "_required");
					if (StringUtils.isNotEmpty(title)) {
						DictoryDefinition model = new DictoryDefinition();
						model.setName(m.getName());
						model.setNodeId(nodeId);
						model.setTarget(target);
						model.setTitle(title);
						model.setType(m.getType());
						model.setSort(m.getSort());
						model.setColumnName(m.getColumnName());
						model.setLength(m.getLength());
						if (StringUtils.equals(required, "1")
								|| StringUtils.equals(required, "on")) {
							model.setRequired(1);
						}
						rows.add(model);
					}
				}
				dictoryDefinitionService.saveAll(nodeId, target, rows);
			}
		}
		return this.edit(request, modelMap);
	}

	@ResponseBody
	@RequestMapping(params = "method=saveDictoryDefinition")
	public byte[] saveDictoryDefinition(HttpServletRequest request) {
		try {
			Map<String, Object> params = RequestUtils.getParameterMap(request);
			logger.debug("params:" + params);
			String target = request.getParameter("target");
			Long nodeId = ParamUtils.getLong(params, "nodeId");
			if (StringUtils.isNotEmpty(target)) {
				List<DictoryDefinition> list = dictoryDefinitionService
						.getDictoryDefinitions(0L, target);
				if (list != null && !list.isEmpty()) {
					List<DictoryDefinition> rows = new ArrayList<DictoryDefinition>();
					for (DictoryDefinition m : list) {
						String title = request.getParameter(m.getName()
								+ "_title");
						String required = request.getParameter(m.getName()
								+ "_required");
						if (StringUtils.isNotEmpty(title)) {
							DictoryDefinition model = new DictoryDefinition();
							model.setName(m.getName());
							model.setNodeId(nodeId);
							model.setTarget(target);
							model.setTitle(title);
							model.setType(m.getType());
							model.setSort(m.getSort());
							model.setColumnName(m.getColumnName());
							model.setLength(m.getLength());
							if (StringUtils.equals(required, "1")
									|| StringUtils.equals(required, "on")) {
								model.setRequired(1);
							}
							rows.add(model);
						}
					}
					dictoryDefinitionService.saveAll(nodeId, target, rows);
				}
			}
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

}
