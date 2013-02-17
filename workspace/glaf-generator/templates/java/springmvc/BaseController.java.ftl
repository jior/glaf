package ${packageName}.web.springmvc;

import java.io.IOException;
import java.util.*;
 
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.ModelMap;
import org.json.*;
import org.jpage.util.JSONTools;


import com.glaf.base.*;
import com.glaf.base.config.*;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.security.*;
import com.glaf.base.utils.*;
 

import ${packageName}.model.*;
import ${packageName}.query.*;
import ${packageName}.service.*;


public class ${entityName}BaseController {
	protected static final Log logger = LogFactory
			.getLog(${entityName}BaseController.class);

    @Autowired
	protected ${entityName}Service ${modelName}Service;

	public ${entityName}BaseController() {

	}


	@RequestMapping(params = "method=save")
	public ModelAndView save(HttpServletRequest request,
			ModelMap modelMap) {
		SysUser user = RequestUtil
				.getSysUser(request);
		String actorId =  user.getAccount();
		Map<String, Object> params = RequestUtil.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
		${entityName} ${modelName} = new ${entityName}();
		Tools.populate(${modelName}, params);
		${modelName}.setCreateBy(actorId);
         
		${modelName}Service.save(${modelName});   

		return this.list(request, modelMap);
	}

    @RequestMapping(params = "method=update")
	public ModelAndView update(HttpServletRequest request,
			ModelMap modelMap) {
		SysUser user = RequestUtil
				.getSysUser(request);
		Map<String, Object> params = RequestUtil.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
		String rowId = ParamUtil.getString(params, "rowId");
		${entityName} ${modelName} = null;
		if (StringUtils.isNotEmpty(rowId)) {
			${modelName} = ${modelName}Service.get${entityName}(rowId);
		}
 
		if (${modelName} != null && StringUtils.equals(${modelName}.getCreateBy(), user.getAccount())) {
			if (${modelName}.getStatus() == 0
					|| ${modelName}.getStatus() == -1) {
				Tools.populate(${modelName}, params);
				${modelName}Service.save(${modelName});
			}
		}

		return this.list(request, modelMap);
	}

    @RequestMapping(params = "method=delete")
	public ModelAndView delete(HttpServletRequest request,
			ModelMap modelMap) {
		SysUser user = RequestUtil
				.getSysUser(request);
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		String rowId = ParamUtil.getString(params, "rowId");
		String rowIds = request.getParameter("rowIds");
		if (StringUtils.isNotEmpty(rowIds)) {
			StringTokenizer token = new StringTokenizer(rowIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					${entityName} ${modelName} = ${modelName}Service.get${entityName}(x);
					if (${modelName} != null && StringUtils.equals(${modelName}.getCreateBy(), user.getAccount())) {
						${modelName}.setDeleteFlag(1);
						${modelName}Service.save(${modelName});
					}
				}
			}
		} else if (StringUtils.isNotEmpty(rowId)) {
			${entityName} ${modelName} = ${modelName}Service
					.get${entityName}(rowId);
			if (${modelName} != null && StringUtils.equals(${modelName}.getCreateBy(), user.getAccount())) {
				${modelName}.setDeleteFlag(1);
				${modelName}Service.save(${modelName});
			}
		}

		return this.list(request, modelMap);
	}

    

    @RequestMapping(params = "method=edit")
	public ModelAndView edit(HttpServletRequest request,
			ModelMap modelMap) {
		SysUser user = RequestUtil
				.getSysUser(request);
		String actorId =  user.getAccount();
		RequestUtil.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		String rowId = ParamUtil.getString(params, "rowId");
		${entityName} ${modelName} = null;
		if (StringUtils.isNotEmpty(rowId)) {
			${modelName} = ${modelName}Service.get${entityName}(rowId);
			request.setAttribute("${modelName}", ${modelName});
			Map<String, Object> dataMap = Tools.getDataMap(${modelName});
			String x_json = JSONTools.encode(dataMap);
			x_json = RequestUtil.encodeString(x_json);
			request.setAttribute("x_json", x_json);
		}

        boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {
			 
		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (${modelName} != null) {
				if (${modelName}.getStatus() == 0
						|| ${modelName}.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		 
		request.setAttribute("canUpdate",  canUpdate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = SystemConfig.getString("${modelName}.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/apps/${modelName}/edit", modelMap);
	}

    @RequestMapping(params = "method=view")
	public ModelAndView view(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtil.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		String rowId = ParamUtil.getString(params, "rowId");
		${entityName} ${modelName} = null;
		if (StringUtils.isNotEmpty(rowId)) {
			${modelName} = ${modelName}Service.get${entityName}(rowId);
			request.setAttribute("${modelName}", ${modelName});
			Map<String, Object> dataMap = Tools.getDataMap(${modelName});
			String x_json = JSONTools.encode(dataMap);
			x_json = RequestUtil.encodeString(x_json);
			request.setAttribute("x_json", x_json);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = SystemConfig.getString("${modelName}.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/apps/${modelName}/view");
	}

     @RequestMapping(params = "method=query")
	public ModelAndView query(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtil.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = SystemConfig.getString("${modelName}.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/apps/${modelName}/query", modelMap);
	}

	@RequestMapping(params = "method=json")
	@ResponseBody
	public byte[] json(HttpServletRequest request,
			ModelMap modelMap) throws IOException {
		Map<String, Object> params = RequestUtil.getParameterMap(request);
		${entityName}Query query = new ${entityName}Query();
		Tools.populate(query, params);

                String gridType = ParamUtil.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}
		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;
	 
		int pageNo = ParamUtil.getInt(params, "page");
		limit = ParamUtil.getInt(params, "rows");
		start = (pageNo - 1) * limit;
		orderName = ParamUtil.getString(params, "sortName");
		order = ParamUtil.getString(params, "sortOrder");
		 

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = ${modelName}Service.get${entityName}CountByQueryCriteria(query);
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

			Map<String, SysUser> userMap = IdentityFactory.getUserMap();
			List<${entityName}> list = ${modelName}Service.get${entityName}sByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (${entityName} ${modelName} : list) {
					JSONObject rowJSON = new JSONObject();
					rowJSON.put("id", ${modelName}.getId());
					rowJSON.put("${modelName}Id", ${modelName}.getId());

<#if pojo_fields?exists>
    <#list  pojo_fields as field>
        <#if field.type?exists && ( field.type== 'Date' )>
	                if(${modelName}.get${field.firstUpperName}() != null) {
	                    rowJSON.put("${field.name}", DateTools.getDateTime(${modelName}.get${field.firstUpperName}()));
                    }
	<#else>
            <#if field.name?exists && field.name == 'createByName' >  
			        SysUser createByUser = userMap.get(${modelName}.getCreateBy());
				    if(createByUser != null){
					    rowJSON.put("createByName", createByUser.getName());
                    }
	    <#elseif field.name?exists && field.name == 'updateByName' >
				SysUser updateByUser = userMap.get(${modelName}.getUpdateBy());
				if(updateByUser != null){
					rowJSON.put("updateByName", updateByUser.getName());
                }
	     <#else>
	            if(${modelName}.get${field.firstUpperName}() != null) {
                    rowJSON.put("${field.name}", ${modelName}.get${field.firstUpperName}());
                }
	    </#if>
       </#if>
    </#list>
</#if>
					rowsJSON.put(rowJSON);
				}

			}
		}
		return result.toString().getBytes("UTF-8");
	}

    @RequestMapping 
	public ModelAndView list(HttpServletRequest request,
			ModelMap modelMap) {
		RequestUtil.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
			String x_complex_query = JSONTools.encode(paramMap);
			x_complex_query = RequestUtil.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
 

		return new ModelAndView("/apps/${modelName}/list", modelMap);
	}

}
