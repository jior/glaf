package demoSrc.orm;

/**
 * TCellediting entity. @author MyEclipse Persistence Tools
 */

public class TCellediting implements java.io.Serializable {

	// Fields

	private TCelleditingId id;
	private String common;
	private String light;
	private String availdate;

	// Constructors

	/** default constructor */
	public TCellediting() {
	}

	/** minimal constructor */
	public TCellediting(TCelleditingId id) {
		this.id = id;
	}

	/** full constructor */
	public TCellediting(TCelleditingId id, String common, String light,
			String availdate) {
		this.id = id;
		this.common = common;
		this.light = light;
		this.availdate = availdate;
	}

	// Property accessors

	public TCelleditingId getId() {
		return this.id;
	}

	public void setId(TCelleditingId id) {
		this.id = id;
	}

	public String getCommon() {
		return this.common;
	}

	public void setCommon(String common) {
		this.common = common;
	}

	public String getLight() {
		return this.light;
	}

	public void setLight(String light) {
		this.light = light;
	}

	public String getAvaildate() {
		return this.availdate;
	}

	public void setAvaildate(String availdate) {
		this.availdate = availdate;
	}

}