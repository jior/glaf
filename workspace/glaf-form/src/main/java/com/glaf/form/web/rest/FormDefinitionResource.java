package com.glaf.form.web.rest;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.glaf.core.base.DataFile;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.ResponseUtils;
import com.glaf.form.core.domain.FormDefinition;
import com.glaf.form.core.query.FormDefinitionQuery;
import com.glaf.form.core.service.FormDataService;

@Controller("/rs/form/definition")
@Path("/rs/form/definition")
public class FormDefinitionResource {
	protected final static Log logger = LogFactory
			.getLog(FormDefinitionResource.class);

	protected IBlobService blobService;

	protected FormDataService formDataService;

	@GET
	@POST
	@Path("/download")
	public void download(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws IOException {
		String formDefinitionId = request.getParameter("formDefinitionId");
		String formName = request.getParameter("formName");
		FormDefinition fd = null;
		if (StringUtils.isNotEmpty(formDefinitionId)) {
			fd = formDataService.getFormDefinition(formDefinitionId);
		} else if (StringUtils.isNotEmpty(formName)) {
			fd = formDataService.getLatestFormDefinition(formName);
		}
		if (fd != null) {
			DataFile dataFile = blobService
					.getBlobWithBytesByFileId(formDefinitionId);
			if (dataFile != null) {
				try {
					ResponseUtils.download(request, response,
							dataFile.getData(), dataFile.getFilename());
				} catch (Exception ex) {
					logger.error(ex);
				}
			}
		}
	}

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
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	@javax.annotation.Resource
	public void setFormDataService(FormDataService formDataService) {
		this.formDataService = formDataService;
	}

}
