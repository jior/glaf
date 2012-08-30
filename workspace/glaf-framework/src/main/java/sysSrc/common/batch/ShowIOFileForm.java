package sysSrc.common.batch;

import baseSrc.common.AutoArrayList;
import sysSrc.framework.SysBaseActionForm;

public class ShowIOFileForm extends SysBaseActionForm {
	private static final long serialVersionUID = 1L;
	
	private String logid;
	
	private AutoArrayList showIOFileDetail = new AutoArrayList(ShowIOFileDetail.class);
	

	public void setShowIOFileDetail(AutoArrayList showIOFileDetail){
		this.showIOFileDetail.clear();
		this.showIOFileDetail.addAll(showIOFileDetail);
		}

	public AutoArrayList getShowIOFileDetail(){
		return this.showIOFileDetail;
	}

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	
	
	
}
