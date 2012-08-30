package sysSrc.orm;

import java.util.Date;

/**
 * TUsersRole entity. @author MyEclipse Persistence Tools
 */

public class TUsersRole implements java.io.Serializable {

	// Fields

	private TUsersRoleId id;
	private String FCreatuserid;
	private Date FCreatdate;
	private String FUpdateuserid;
	private Date FUpdatedate;

	// Constructors

	/** default constructor */
	public TUsersRole() {
	}

	/** minimal constructor */
	public TUsersRole(TUsersRoleId id) {
		this.id = id;
	}

	/** full constructor */
	public TUsersRole(TUsersRoleId id, String FCreatuserid, Date FCreatdate,
			String FUpdateuserid, Date FUpdatedate) {
		this.id = id;
		this.FCreatuserid = FCreatuserid;
		this.FCreatdate = FCreatdate;
		this.FUpdateuserid = FUpdateuserid;
		this.FUpdatedate = FUpdatedate;
	}

	// Property accessors

	public TUsersRoleId getId() {
		return this.id;
	}

	public void setId(TUsersRoleId id) {
		this.id = id;
	}

	public String getFCreatuserid() {
		return this.FCreatuserid;
	}

	public void setFCreatuserid(String FCreatuserid) {
		this.FCreatuserid = FCreatuserid;
	}

	public Date getFCreatdate() {
		return this.FCreatdate;
	}

	public void setFCreatdate(Date FCreatdate) {
		this.FCreatdate = FCreatdate;
	}

	public String getFUpdateuserid() {
		return this.FUpdateuserid;
	}

	public void setFUpdateuserid(String FUpdateuserid) {
		this.FUpdateuserid = FUpdateuserid;
	}

	public Date getFUpdatedate() {
		return this.FUpdatedate;
	}

	public void setFUpdatedate(Date FUpdatedate) {
		this.FUpdatedate = FUpdatedate;
	}

}