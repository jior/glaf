package demoSrc.orm;

/**
 * TCelleditingId entity. @author MyEclipse Persistence Tools
 */

public class TCelleditingId implements java.io.Serializable {

	// Fields

	private String select1;
	private String price;

	// Constructors

	/** default constructor */
	public TCelleditingId() {
	}

	/** full constructor */
	public TCelleditingId(String select1, String price) {
		this.select1 = select1;
		this.price = price;
	}

	// Property accessors

	public String getSelect1() {
		return this.select1;
	}

	public void setSelect1(String select1) {
		this.select1 = select1;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TCelleditingId))
			return false;
		TCelleditingId castOther = (TCelleditingId) other;

		return ((this.getSelect1() == castOther.getSelect1()) || (this
				.getSelect1() != null
				&& castOther.getSelect1() != null && this.getSelect1().equals(
				castOther.getSelect1())))
				&& ((this.getPrice() == castOther.getPrice()) || (this
						.getPrice() != null
						&& castOther.getPrice() != null && this.getPrice()
						.equals(castOther.getPrice())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getSelect1() == null ? 0 : this.getSelect1().hashCode());
		result = 37 * result
				+ (getPrice() == null ? 0 : this.getPrice().hashCode());
		return result;
	}

}