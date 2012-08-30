package demoSrc.orm;
// default package

/**
 * TTestWfdemoBakId entity. @author MyEclipse Persistence Tools
 */

public class TTestWfdemoBakId implements java.io.Serializable {

	// Fields

	private String FStuffapplyno;
	private Long FTaskinstanceid;

	// Constructors

	/** default constructor */
	public TTestWfdemoBakId() {
	}

	/** full constructor */
	public TTestWfdemoBakId(String FStuffapplyno, Long FTaskinstanceid) {
		this.FStuffapplyno = FStuffapplyno;
		this.FTaskinstanceid = FTaskinstanceid;
	}

	// Property accessors

	public String getFStuffapplyno() {
		return this.FStuffapplyno;
	}

	public void setFStuffapplyno(String FStuffapplyno) {
		this.FStuffapplyno = FStuffapplyno;
	}

	public Long getFTaskinstanceid() {
		return this.FTaskinstanceid;
	}

	public void setFTaskinstanceid(Long FTaskinstanceid) {
		this.FTaskinstanceid = FTaskinstanceid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TTestWfdemoBakId))
			return false;
		TTestWfdemoBakId castOther = (TTestWfdemoBakId) other;

		return ((this.getFStuffapplyno() == castOther.getFStuffapplyno()) || (this
				.getFStuffapplyno() != null
				&& castOther.getFStuffapplyno() != null && this
				.getFStuffapplyno().equals(castOther.getFStuffapplyno())))
				&& ((this.getFTaskinstanceid() == castOther
						.getFTaskinstanceid()) || (this.getFTaskinstanceid() != null
						&& castOther.getFTaskinstanceid() != null && this
						.getFTaskinstanceid().equals(
								castOther.getFTaskinstanceid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getFStuffapplyno() == null ? 0 : this.getFStuffapplyno()
						.hashCode());
		result = 37
				* result
				+ (getFTaskinstanceid() == null ? 0 : this.getFTaskinstanceid()
						.hashCode());
		return result;
	}

}