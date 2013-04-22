package com.glaf.form.web.rest;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.glaf.form.core.domain.FormDefinition;
import com.glaf.form.core.query.FormDefinitionQuery;
import com.glaf.form.core.service.FormDataService;

@Controller("/rs/form/definition")
@Path("/rs/form/definition")
public class FormDefinitionResource {

	protected FormDataService formDataService;

	@GET
	@POST
	@Path("/json")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@ResponseBody
	public byte[] json(@javax.ws.rs.core.Context HttpServletRequest request)
			throws IOException {
		JSONArray array = new JSONArray();
		FormDefinitionQuery query = new FormDefinitionQuery();

		List<FormDefinition> rows = formDataService
				.getLatestFormDefinitions(query);
		if (rows != null && !rows.isEmpty()) {
			for (FormDefinition fd : rows) {
				array.add(fd.toJsonObject());
			}
		}

		return array.toJSONString().getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setFormDataService(FormDataService formDataService) {
		this.formDataService = formDataService;
	}

}
