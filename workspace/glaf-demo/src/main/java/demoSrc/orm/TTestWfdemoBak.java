package demoSrc.orm;
// default package

/**
 * TTestWfdemoBak entity. @author MyEclipse Persistence Tools
 */

public class TTestWfdemoBak implements java.io.Serializable {

	// Fields

	private TTestWfdemoBakId id;
	private String FChangeereason;
	private String FSafetypeid;
	private String FApplytypeid;
	private Long processinstanceid;

	// Constructors

	/** default constructor */
	public TTestWfdemoBak() {
	}

	/** minimal constructor */
	public TTestWfdemoBak(TTestWfdemoBakId id) {
		this.id = id;
	}

	/** full constructor */
	public TTestWfdemoBak(TTestWfdemoBakId id, String FChangeereason,
			String FSafetypeid, String FApplytypeid, Long processinstanceid) {
		this.id = id;
		this.FChangeereason = FChangeereason;
		this.FSafetypeid = FSafetypeid;
		this.FApplytypeid = FApplytypeid;
		this.processinstanceid = processinstanceid;
	}

	// Property accessors

	public TTestWfdemoBakId getId() {
		return this.id;
	}

	public void setId(TTestWfdemoBakId id) {
		this.id = id;
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