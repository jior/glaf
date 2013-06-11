package com.glaf.form.web.rest;

import javax.ws.rs.Path;

import org.springframework.stereotype.Controller;

import com.glaf.form.core.service.FormDataService;

@Controller("/rs/form/data")
@Path("/rs/form/data")
public class FormDataResource {

	protected FormDataService formDataService;

	@javax.annotation.Resource
	public void setFormDataService(FormDataService formDataService) {
		this.formDataService = formDataService;
	}

}
