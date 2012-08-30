package sysSrc.orm;

/**
 * TBatchcheckerrordadaId entity. @author MyEclipse Persistence Tools
 */

public class TBatchcheckerrordadaId implements java.io.Serializable {

	// Fields

	private Long id;
	private String filepath;
	private String filename;

	// Constructors

	/** default constructor */
	public TBatchcheckerrordadaId() {
	}

	/** full constructor */
	public TBatchcheckerrordadaId(Long id, String filepath, String filename) {
		this.id = id;
		this.filepath = filepath;
		this.filename = filename;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TBatchcheckerrordadaId))
			return false;
		TBatchcheckerrordadaId castOther = (TBatchcheckerrordadaId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getFilepath() == castOther.getFilepath()) || (this
						.getFilepath() != null
						&& castOther.getFilepath() != null && this
						.getFilepath().equals(castOther.getFilepath())))
				&& ((this.getFilename() == castOther.getFilename()) || (this
						.getFilename() != null
						&& castOther.getFilename() != null && this
						.getFilename().equals(castOther.getFilename())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getFilepath() == null ? 0 : this.getFilepath().hashCode());
		result = 37 * result
				+ (getFilename() == null ? 0 : this.getFilename().hashCode());
		return result;
	}

}