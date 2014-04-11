package com.glaf.jbpm.web.rest;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.taskmgmt.def.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.context.Context;

@Controller("/rs/jbpm/definition")
@Path("/rs/jbpm/definition")
public class MxJbpmProcessDefinitionResource {

	protected final static Log logger = LogFactory
			.getLog(MxJbpmProcessDefinitionResource.class);

	@GET
	@POST
	@Path("/json")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@ResponseBody
	public byte[] json(@javax.ws.rs.core.Context HttpServletRequest request)
			throws IOException {
		JSONArray array = new JSONArray();
		String processName = request.getParameter("processName");
		List<ProcessDefinition> result = null;
		GraphSession graphSession = null;
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			graphSession = jbpmContext.getGraphSession();
			if (StringUtils.isNotEmpty(processName)) {
				result = graphSession
						.findAllProcessDefinitionVersions(processName);
			} else {
				result = graphSession.findLatestProcessDefinitions();
			}

			if (result != null && !result.isEmpty()) {
				for (ProcessDefinition pd : result) {
					JSONObject json = new JSONObject();
					json.put("id", pd.getId());
					json.put("name", pd.getName());
					json.put("description", pd.getDescription());
					json.put("version", pd.getVersion());
					json.put("text", pd.getDescription() + "(" + pd.getName()
							+ ") V" + pd.getVersion() + ".0");
					array.add(json);
				}
			}

		} catch (Exception ex) {
			logger.debug(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return array.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/taskJson")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@ResponseBody
	public byte[] taskJson(@javax.ws.rs.core.Context HttpServletRequest request)
			throws IOException {
		JSONArray array = new JSONArray();
		String processName = request.getParameter("processName");
		String processDefinitionId = request
				.getParameter("processDefinitionId");
		GraphSession graphSession = null;
		JbpmContext jbpmContext = null;
		ProcessDefinition pd = null;
		try {
			jbpmContext = ProcessContainer.getContainer().createJbpmContext();
			graphSession = jbpmContext.getGraphSession();

			if (StringUtils.isNotEmpty(processDefinitionId)
					&& StringUtils.isNumeric(processDefinitionId)) {
				pd = graphSession.getProcessDefinition(Long
						.parseLong(processDefinitionId));
			} else if (StringUtils.isNotEmpty(processName)) {
				pd = graphSession.findLatestProcessDefinition(processName);
			}
			if (pd != null) {
				Map<String, Task> tasks = pd.getTaskMgmtDefinition().getTasks();
				if (tasks != null && !tasks.isEmpty()) {
					Iterator<Task> iterator = tasks.values().iterator();
					while (iterator.hasNext()) {
						Task task = iterator.next();
						JSONObject json = new JSONObject();
						json.put("id", task.getId());
						json.put("name", task.getName());
						json.put("description", task.getDescription());
						json.put("priority", task.getPriority());
						array.add(json);
					}
				}
			}
		} catch (Exception ex) {
			logger.debug(ex);
		} finally {
			Context.close(jbpmContext);
		}
		return array.toJSONString().getBytes("UTF-8");
	}

}
