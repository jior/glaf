package ${packageName}.action;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.springframework.web.struts.DispatchActionSupport;
import com.alibaba.fastjson.*;
 
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import ${packageName}.domain.*;
import ${packageName}.query.*;
import ${packageName}.service.*;

public class ${entityName}BaseAction extends DispatchActionSupport {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected ${entityName}Service ${modelName}Service;

	public ${entityName}BaseAction() {

	}

	public void set${entityName}Service(${entityName}Service ${modelName}Service) {
		this.${modelName}Service = ${modelName}Service;
	}

	public ActionForward save(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");
		${entityName} ${modelName} = new ${entityName}();
		Tools.populate(${modelName}, params);
		${modelName}.setCreateBy(actorId);

		${modelName}Service.save(${modelName});

		return this.list(mapping, actionForm, request, response);
	}

	public ActionForward save${entityName}(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			User user = RequestUtils.getUser(request);
			String actorId = user.getActorId();
			Map<String, Object> params = RequestUtils.getParameterMap(request);
			params.remove("status");
			params.remove("wfStatus");
			${entityName} ${modelName} = new ${entityName}();
			Tools.populate(${modelName}, params);
			${modelName}.setCreateBy(actorId);

			${modelName}Service.save(${modelName});
			byte[] bytes = ResponseUtils.responseJsonResult(true);
			response.getOutputStream().write(bytes);
			response.flushBuffer();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		byte[] bytes = ResponseUtils.responseJsonResult(false);
		response.getOutputStream().write(bytes);
		response.flushBuffer();
		return null;
	}

	public ActionForward update(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");
		${entityName} ${modelName} = new ${entityName}();
		Tools.populate(${modelName}, params);
		${modelName}.setCreateBy(actorId);

		${modelName}Service.save(${modelName});

		return this.list(mapping, actionForm, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String ${idField.name} = ParamUtils.getString(params, "${idField.name}");
		String ${idField.name}s = request.getParameter("${idField.name}s");
		if (StringUtils.isNotEmpty(${idField.name}s)) {
			StringTokenizer token = new StringTokenizer(${idField.name}s, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					${entityName} ${modelName} = ${modelName}Service.get${entityName}(x);
					if (${modelName} != null
							&& StringUtils.equals(${modelName}.getCreateBy(),
									user.getActorId())) {
						${modelName}.setDeleteFlag(1);
						${modelName}Service.save(${modelName});
					}
				}
			}
		} else if (StringUtils.isNotEmpty(${idField.name})) {
			${entityName} ${modelName} = ${modelName}Service.get${entityName}(${idField.name});
			if (${modelName} != null
					&& StringUtils
							.equals(${modelName}.getCreateBy(), user.getActorId())) {
				${modelName}.setDeleteFlag(1);
				${modelName}Service.save(${modelName});
			}
		}

		return this.list(mapping, actionForm, request, response);
	}

	public ActionForward edit(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String ${idField.name} = ParamUtils.getString(params, "${idField.name}");
		${entityName} ${modelName} = null;
		if (StringUtils.isNotEmpty(${idField.name})) {
			${modelName} = ${modelName}Service.get${entityName}(${idField.name});
			request.setAttribute("${modelName}", ${modelName});
			JSONObject rowJSON = ${modelName}.toJsonObject();
			request.setAttribute("x_json", rowJSON.toString());
		}

		boolean canUpdate = false;
		String x_method = request.getParameter("x_method");
		if (StringUtils.equals(x_method, "submit")) {

		}

		if (StringUtils.containsIgnoreCase(x_method, "update")) {
			if (${modelName} != null) {
				if (${modelName}.getStatus() == 0 || ${modelName}.getStatus() == -1) {
					canUpdate = true;
				}
			}
		}

		request.setAttribute("canUpdate", canUpdate);

		return mapping.findForward("show_modify");
	}

	public ActionForward view(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String ${idField.name} = ParamUtils.getString(params, "${idField.name}");
		${entityName} ${modelName} = null;
		if (StringUtils.isNotEmpty(${idField.name})) {
			${modelName} = ${modelName}Service.get${entityName}(${idField.name});
			request.setAttribute("${modelName}", ${modelName});
			JSONObject rowJSON = ${modelName}.toJsonObject();
			request.setAttribute("x_json", rowJSON.toString());
		}

		return mapping.findForward("show_view");
	}

	public ActionForward query(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RequestUtils.setRequestParameterToAttribute(request);

		return mapping.findForward("show_query");
	}

	public ActionForward json(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		${entityName}Query query = new ${entityName}Query();
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

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<${entityName}> list = ${modelName}Service.get${entityName}sByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (${entityName} ${modelName} : list) {
					JSONObject rowJSON = ${modelName}.toJsonObject();
					rowJSON.put("id", ${modelName}.getId());
					rowJSON.put("${modelName}Id", ${modelName}.getId());

					rowsJSON.add(rowJSON);
				}

			}
		}

		response.getOutputStream().write(result.toString().getBytes("UTF-8"));
		return null;
	}

	public ActionForward list(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		return mapping.findForward("show_list");
	}

}
