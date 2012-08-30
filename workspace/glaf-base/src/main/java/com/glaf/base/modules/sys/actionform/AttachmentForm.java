package com.glaf.base.modules.sys.actionform;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class AttachmentForm extends ActionForm{
	private static final long serialVersionUID = 1L;
	private FormFile file;//нд╪Ч
	
	public FormFile getFile() {
		return file;
	}
	public void setFile(FormFile file) {
		this.file = file;
	}

}
