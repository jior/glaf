package sysSrc.common.batch;

import sysSrc.framework.SysBaseActionForm;

public class ManageBatchForm extends SysBaseActionForm {
	private static final long serialVersionUID = 1L;
	
	private String s_Name;
	private String curBatName;
	private String curBatStatus;
	private String curBatWhat;

	public String getCurBatWhat() {
		return curBatWhat;
	}

	public void setCurBatWhat(String curBatWhat) {
		this.curBatWhat = curBatWhat;
	}

	public String getCurBatStatus() {
		return curBatStatus;
	}

	public void setCurBatStatus(String curBatStatus) {
		this.curBatStatus = curBatStatus;
	}

	public String getCurBatName() {
		return curBatName;
	}

	public void setCurBatName(String curBatName) {
		this.curBatName = curBatName;
	}

	public String getS_Name() {
		return s_Name;
	}

	public void setS_Name(String name) {
		s_Name = name;
	}

	
}
