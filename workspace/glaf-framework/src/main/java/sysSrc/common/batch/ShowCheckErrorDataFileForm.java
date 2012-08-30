package sysSrc.common.batch;

import baseSrc.common.AutoArrayList;
import sysSrc.framework.SysBaseActionForm;

public class ShowCheckErrorDataFileForm extends SysBaseActionForm {
	private static final long serialVersionUID = 1L;
	
	private String logid;
	
	private AutoArrayList showCheckErrorDataFileDetail = new AutoArrayList(ShowCheckErrorDataFileDetail.class);
	

	public void setShowCheckErrorDataFileDetail(AutoArrayList showCheckErrorDataFileDetail){
		this.showCheckErrorDataFileDetail.clear();
		this.showCheckErrorDataFileDetail.addAll(showCheckErrorDataFileDetail);
		}

	public AutoArrayList getShowCheckErrorDataFileDetail(){
		return this.showCheckErrorDataFileDetail;
	}

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	
	
	
}
