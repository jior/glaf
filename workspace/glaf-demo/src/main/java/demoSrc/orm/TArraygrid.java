package demoSrc.orm;

import java.util.Date;

/**
 * TArraygrid entity. @author MyEclipse Persistence Tools
 */

public class TArraygrid implements java.io.Serializable {

	// Fields

	private String company;
	private Short price;
	private Short change;
	private Short changeper;
	private Date laseupdated;

	// Constructors

	/** default constructor */
	public TArraygrid() {
	}

	/** minimal constructor */
	public TArraygrid(String company) {
		this.company = company;
	}

	/** full constructor */
	public TArraygrid(String company, Short price, Short change,
			Short changeper, Date laseupdated) {
		this.company = company;
		this.price = price;
		this.change = change;
		this.changeper = changeper;
		this.laseupdated = laseupdated;
	}

	// Property accessors

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Short getPrice() {
		return this.price;
	}

	public void setPrice(Short price) {
		this.price = price;
	}

	public Short getChange() {
		return this.change;
	}

	public void setChange(Short change) {
		this.change = change;
	}

	public Short getChangeper() {
		return this.changeper;
	}

	public void setChangeper(Short changeper) {
		this.changeper = changeper;
	}

	public Date getLaseupdated() {
		return this.laseupdated;
	}

	public void setLaseupdated(Date laseupdated) {
		this.laseupdated = laseupdated;
	}

}