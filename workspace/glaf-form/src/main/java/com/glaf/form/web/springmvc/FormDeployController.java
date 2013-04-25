package com.glaf.form.web.springmvc;

import java.io.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.security.LoginContext;
import com.glaf.core.util.RequestUtils;
import com.glaf.form.core.dataimport.*;
import com.glaf.form.core.domain.FormDefinition;

import com.glaf.form.core.model.FormDefinitionType;
import com.glaf.form.core.context.FormContext;

import com.glaf.form.core.service.*;
import com.glaf.form.core.util.FdlConverter;

@Controller("/system/form/deploy")
@RequestMapping("/system/form/deploy.do")
public class FormDeployController {
	private static final Log logger = LogFactory
			.getLog(FormDeployController.class);

	protected FormDataService formDataService;

	public FormDeployController() {

	}

	@RequestMapping
	public ModelAndView deploy(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("file") MultipartFile mFile) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		String filename = mFile.getOriginalFilename();
		String name = filename;
		name = name.toLowerCase();
		if (filename.indexOf("/") != -1) {
			filename = filename.substring(filename.lastIndexOf("/") + 1,
					filename.length());
		} else if (filename.indexOf("\\") != -1) {
			filename = filename.substring(filename.lastIndexOf("\\") + 1,
					filename.length());
		}

		FormContext formContext = new FormContext();
		formContext.setLoginContext(loginContext);
		formContext.setContextPath(request.getContextPath());
		String templateType = "fdl";

		if (name.endsWith(".xls")) {
			templateType = "xls";
		} else if (name.endsWith(".fdl.xml")) {
			templateType = "fdl";
		}

		logger.debug("templateType:" + templateType);
		FormDataImport dataImport = FormDataImportFactory
				.getDataImport(templateType);
		InputStream inputStream = mFile.getInputStream();
		FormDefinitionType fdt = dataImport.read(inputStream);
		FormDefinition formDefinition = FdlConverter.toFormDefinition(fdt);
		formDefinition.setTemplateName(filename);
		formDefinition.setTemplateData(mFile.getBytes());
		formDefinition.setTemplateType(templateType);

		formDataService.saveFormDefinition(formDefinition);

		String linkTemplateId = request.getParameter("linkTemplateId");
		if (StringUtils.isNotEmpty(linkTemplateId)) {
			formDefinition.getFormContext().put("linkTemplateId",
					linkTemplateId);
		}
		formContext.setFormDefinition(formDefinition);

		try {
			java.io.Writer writer = new java.io.StringWriter();

			response.getWriter().write(writer.toString());
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ex) {
				logger.error(ex);
			}
		}

		return null;
	}

	@javax.annotation.Resource
	public void setFormDataService(FormDataService formDataService) {
		this.formDataService = formDataService;
	}

}
