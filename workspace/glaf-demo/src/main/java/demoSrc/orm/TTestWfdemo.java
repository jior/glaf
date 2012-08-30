package demoSrc.orm;
// default package

/**
 * TTestWfdemo entity. @author MyEclipse Persistence Tools
 */

public class TTestWfdemo implements java.io.Serializable {

	// Fields

	private String FStuffapplyno;
	private String FChangeereason;
	private String FSafetypeid;
	private String FApplytypeid;
	private Long processinstanceid;

	// Constructors

	/** default constructor */
	public TTestWfdemo() {
	}

	/** minimal constructor */
	public TTestWfdemo(String FStuffapplyno) {
		this.FStuffapplyno = FStuffapplyno;
	}

	/** full constructor */
	public TTestWfdemo(String FStuffapplyno, String FChangeereason,
			String FSafetypeid, String FApplytypeid, Long processinstanceid) {
		this.FStuffapplyno = FStuffapplyno;
		this.FChangeereason = FChangeereason;
		this.FSafetypeid = FSafetypeid;
		this.FApplytypeid = FApplytypeid;
		this.processinstanceid = processinstanceid;
	}

	// Property accessors

	public String getFStuffapplyno() {
		return this.FStuffapplyno;
	}

	public void setFStuffapplyno(String FStuffapplyno) {
		this.FStuffapplyno = FStuffapplyno;
	}

	public String getFChangeereason() {
		return this.FChangeereason;
	}

	public void setFChangeereason(String FChangeereason) {
		this.FChangeereason = FChangeereason;
	}

	public String getFSafetypeid() {
		return this.FSafetypeid;
	}

	public void setFSafetypeid(String FSafetypeid) {
		this.FSafetypeid = FSafetypeid;
	}

	public String getFApplytypeid() {
		return this.FApplytypeid;
	}

	public void setFApplytypeid(String FApplytypeid) {
		this.FApplytypeid = FApplytypeid;
	}

	public Long getProcessinstanceid() {
		return this.processinstanceid;
	}

	public void setProcessinstanceid(Long processinstanceid) {
		this.processinstanceid = processinstanceid;
	}

}