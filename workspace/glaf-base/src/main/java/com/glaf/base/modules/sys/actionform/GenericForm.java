package com.glaf.base.modules.sys.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GenericForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private String name;
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.name = null;
	}
	
}
