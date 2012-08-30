package sysSrc.common.batch;

import baseSrc.common.AutoArrayList;
import sysSrc.framework.SysBaseActionForm;

public class ShowBatchExeLogForm extends SysBaseActionForm {
	private static final long serialVersionUID = 1L;
	
	private String f_batchid;
	private String f_batchExeStart;
	private String f_batchExeEnd;
	private String f_batchExeUser;
	private String f_batchBusName;
	private String f_batchExeResult;
	
	
	private String curBatName;
	private String curBatContext;
	
	private AutoArrayList showBatchExeLogDetail = new AutoArrayList(ShowBatchExeLogDetail.class);
	
	public String getCurBatName() {
		return curBatName;
	}

	public void setCurBatName(String curBatName) {
		this.curBatName = curBatName;
	}

	public String getCurBatContext() {
		return curBatContext;
	}

	public void setCurBatContext(String curBatContext) {
		this.curBatContext = curBatContext;
	}

	public void setShowBatchExeLogDetail(AutoArrayList showBatchExeLogDetail){
		this.showBatchExeLogDetail.clear();
		this.showBatchExeLogDetail.addAll(showBatchExeLogDetail);
		}

	public AutoArrayList getShowBatchExeLogDetail(){
		return this.showBatchExeLogDetail;
	}

	public String getF_batchid() {
		return f_batchid;
	}

	public void setF_batchid(String f_batchid) {
		this.f_batchid = f_batchid;
	}

	public String getF_batchExeStart() {
		return f_batchExeStart;
	}

	public void setF_batchExeStart(String exeStart) {
		f_batchExeStart = exeStart;
	}

	public String getF_batchExeEnd() {
		return f_batchExeEnd;
	}

	public void setF_batchExeEnd(String exeEnd) {
		f_batchExeEnd = exeEnd;
	}

	public String getF_batchExeUser() {
		return f_batchExeUser;
	}

	public void setF_batchExeUser(String exeUser) {
		f_batchExeUser = exeUser;
	}

	public String getF_batchBusName() {
		return f_batchBusName;
	}

	public void setF_batchBusName(String busName) {
		f_batchBusName = busName;
	}

	public String getF_batchExeResult() {
		return f_batchExeResult;
	}

	public void setF_batchExeResult(String exeResult) {
		f_batchExeResult = exeResult;
	}
	
	
}
