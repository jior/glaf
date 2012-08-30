package baseSrc.common.upload;


import baseSrc.framework.BaseActionForm;

public class UploaddownForm extends BaseActionForm{

	private static final long serialVersionUID = 1L;
	
	private String fileId;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
}
