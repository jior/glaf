package sysSrc.orm;

/**
 * TBatchcheckerrordada entity. @author MyEclipse Persistence Tools
 */

public class TBatchcheckerrordada implements java.io.Serializable {

	// Fields

	private TBatchcheckerrordadaId id;

	// Constructors

	/** default constructor */
	public TBatchcheckerrordada() {
	}

	/** full constructor */
	public TBatchcheckerrordada(TBatchcheckerrordadaId id) {
		this.id = id;
	}

	// Property accessors

	public TBatchcheckerrordadaId getId() {
		return this.id;
	}

	public void setId(TBatchcheckerrordadaId id) {
		this.id = id;
	}

}