package sysSrc.orm;

import sysSrc.orm.TBatchiofileId;
//default package

/**
 * TBatchiofile entity. @author MyEclipse Persistence Tools
 */

public class TBatchiofile implements java.io.Serializable {

	// Fields

	private TBatchiofileId id;

	// Constructors

	/** default constructor */
	public TBatchiofile() {
	}

	/** full constructor */
	public TBatchiofile(TBatchiofileId id) {
		this.id = id;
	}

	// Property accessors

	public TBatchiofileId getId() {
		return this.id;
	}

	public void setId(TBatchiofileId id) {
		this.id = id;
	}

}