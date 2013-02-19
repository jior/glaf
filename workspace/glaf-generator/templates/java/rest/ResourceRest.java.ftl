package ${packageName}.web.rest;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang.StringUtils;
import org.json.*;

 
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.security.*;
import com.glaf.base.utils.*;
 

import ${packageName}.model.${entityName};
import ${packageName}.query.${entityName}Query;
import ${packageName}.service.${entityName}Service;

@Controller
@Path("/rs/apps/${modelName}")
public class ${entityName}ResourceRest {
	protected static Log logger = LogFactory.getLog(${entityName}ResourceRest.class);

	@javax.annotation.Resource
	protected ${entityName}Service ${modelName}Service;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("rowIds");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				${modelName}Service.deleteByIds(ids);
			}
		}
		return ResponseUtil.responseJsonResult(true);
	}

	@POST
	@Path("/delete/{rowIds}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@PathParam("rowIds") String rowIds,
			@Context HttpServletRequest request) throws IOException {
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				${modelName}Service.deleteByIds(ids);
			}
		}
		return ResponseUtil.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@Context HttpServletRequest request)
			throws IOException {
		String ${modelName}Id = request.getParameter("${modelName}Id");
		if (StringUtils.isEmpty(${modelName}Id)) {
                      ${modelName}Id = request.getParameter("id");
		}
		${modelName}Service.deleteById(${modelName}Id);
		return ResponseUtil.responseJsonResult(true);
	}

	@POST
	@Path("/delete/{${modelName}Id}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@PathParam("${modelName}Id") String ${modelName}Id,
			@Context HttpServletRequest request) throws IOException {
		${modelName}Service.deleteById(${modelName}Id);
		return ResponseUtil.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
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

	@POST
	@Path("/save${entityName}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] save${entityName}(@Context HttpServletRequest request) {
		String ${modelName}Id = request.getParameter("${modelName}Id");
		if (StringUtils.isEmpty(${modelName}Id)) {
                   ${modelName}Id = request.getParameter("id");
		}
		${entityName} ${modelName} = null;
		if (StringUtils.isNotEmpty(${modelName}Id)) {
		    ${modelName} = ${modelName}Service.get${entityName}(${modelName}Id);
		}

		if (${modelName} == null) {
		    ${modelName} = new ${entityName}();
		}
             
	        try {
		    Map<String, Object> params = RequestUtil.getParameterMap(request);
		    Tools.populate(${modelName}, params);

		    this.${modelName}Service.save(${modelName});

		    return ResponseUtil.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtil.responseJsonResult(false);
	}

	public void set${entityName}Service(${entityName}Service ${modelName}Service) {
		this.${modelName}Service = ${modelName}Service;
	}

	@GET
	@POST
	@Path("/view/{${modelName}Id}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@PathParam("${modelName}Id") String ${modelName}Id,
			@Context HttpServletRequest request) throws IOException {
		${entityName} ${modelName} = null;
		if (StringUtils.isNotEmpty(${modelName}Id)) {
			${modelName} = ${modelName}Service.get${entityName}(${modelName}Id);
		}
		JSONObject result = new JSONObject();
		if (${modelName} != null) {
		    Map<String, SysUser> userMap = IdentityFactory.getUserMap();
			result.put("id", ${modelName}.getId());
			result.put("${modelName}Id", ${modelName}.getId());
<#if pojo_fields?exists>
    <#list  pojo_fields as field>
        <#if field.type?exists && ( field.type== 'Date' )>
	        if(${modelName}.get${field.firstUpperName}() != null) {
	          result.put("${field.name}", DateTools.getDateTime(${modelName}.get${field.firstUpperName}()));
            }
	<#else>
            <#if field.name?exists && field.name == 'createByName' >  
			SysUser createByUser = userMap.get(${modelName}.getCreateBy());
		    if(createByUser != null){
				result.put("createByName", createByUser.getName());
            }
	    <#elseif field.name?exists && field.name == 'updateByName' >
			SysUser updateByUser = userMap.get(${modelName}.getUpdateBy());
			if(updateByUser != null){
			    result.put("updateByName", updateByUser.getName());
            }
	     <#else>
	        if(${modelName}.get${field.firstUpperName}() != null) {
                result.put("${field.name}", ${modelName}.get${field.firstUpperName}());
            }
	    </#if>
       </#if>
    </#list>
</#if>
		}
		return result.toString().getBytes("UTF-8");
	}
}
