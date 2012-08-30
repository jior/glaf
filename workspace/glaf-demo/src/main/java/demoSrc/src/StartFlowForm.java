package demoSrc.src;

import sysSrc.framework.SysBaseActionForm; 


public class StartFlowForm extends SysBaseActionForm{

	private static final long serialVersionUID = 1L;
	
	private String fstuffapplyno  = "";
	private String fchangeereason  = "";
	private String fsafetypeid  = "";
	private String fapplytypeid  = "";
	private String processinstanceid  = "";
	
	public String getFstuffapplyno() {
		return fstuffapplyno;
	}
	public void setFstuffapplyno(String fstuffapplyno) {
		this.fstuffapplyno = fstuffapplyno;
	}
	public String getFchangeereason() {
		return fchangeereason;
	}
	public void setFchangeereason(String fchangeereason) {
		this.fchangeereason = fchangeereason;
	}
	public String getFsafetypeid() {
		return fsafetypeid;
	}
	public void setFsafetypeid(String fsafetypeid) {
		this.fsafetypeid = fsafetypeid;
	}
	public String getFapplytypeid() {
		return fapplytypeid;
	}
	public void setFapplytypeid(String fapplytypeid) {
		this.fapplytypeid = fapplytypeid;
	}
	public String getProcessinstanceid() {
		return processinstanceid;
	}
	public void setProcessinstanceid(String processinstanceid) {
		this.processinstanceid = processinstanceid;
	}

}
