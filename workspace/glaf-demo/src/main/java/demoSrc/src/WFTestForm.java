package demoSrc.src;

import baseSrc.common.AutoArrayList;
import sysSrc.framework.SysBaseActionForm;

public class WFTestForm extends SysBaseActionForm{

	private static final long serialVersionUID = 1L;
	
	private AutoArrayList wfTestDetails = new AutoArrayList(WFTestDetails.class);

	public AutoArrayList getWfTestDetails() {
		return wfTestDetails;
	}

	public void setWfTestDetails(AutoArrayList wfTestDetails) {
		this.wfTestDetails.clear();
		this.wfTestDetails.addAll(wfTestDetails);
	}

}
