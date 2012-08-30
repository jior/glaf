package sysSrc.orm;

import java.util.Date;

/**
 * TBatchargsbackId entity. @author MyEclipse Persistence Tools
 */

public class TBatchargsbackId implements java.io.Serializable {

	// Fields

	private String batchname;
	private Date backdate;

	// Constructors

	/** default constructor */
	public TBatchargsbackId() {
	}

	/** full constructor */
	public TBatchargsbackId(String batchname, Date backdate) {
		this.batchname = batchname;
		this.backdate = backdate;
	}

	// Property accessors

	public String getBatchname() {
		return this.batchname;
	}

	public void setBatchname(String batchname) {
		this.batchname = batchname;
	}

	public Date getBackdate() {
		return this.backdate;
	}

	public void setBackdate(Date backdate) {
		this.backdate = backdate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TBatchargsbackId))
			return false;
		TBatchargsbackId castOther = (TBatchargsbackId) other;

		return ((this.getBatchname() == castOther.getBatchname()) || (this
				.getBatchname() != null
				&& castOther.getBatchname() != null && this.getBatchname()
				.equals(castOther.getBatchname())))
				&& ((this.getBackdate() == castOther.getBackdate()) || (this
						.getBackdate() != null
						&& castOther.getBackdate() != null && this
						.getBackdate().equals(castOther.getBackdate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getBatchname() == null ? 0 : this.getBatchname().hashCode());
		result = 37 * result
				+ (getBackdate() == null ? 0 : this.getBackdate().hashCode());
		return result;
	}

}