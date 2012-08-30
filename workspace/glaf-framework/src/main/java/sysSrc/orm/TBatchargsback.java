package sysSrc.orm;

/**
 * TBatchargsback entity. @author MyEclipse Persistence Tools
 */

public class TBatchargsback implements java.io.Serializable {

	// Fields

	private TBatchargsbackId id;
	private String args;

	// Constructors

	/** default constructor */
	public TBatchargsback() {
	}

	/** minimal constructor */
	public TBatchargsback(TBatchargsbackId id) {
		this.id = id;
	}

	/** full constructor */
	public TBatchargsback(TBatchargsbackId id, String args) {
		this.id = id;
		this.args = args;
	}

	// Property accessors

	public TBatchargsbackId getId() {
		return this.id;
	}

	public void setId(TBatchargsbackId id) {
		this.id = id;
	}

	public String getArgs() {
		return this.args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

}