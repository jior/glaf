package sysSrc.orm;
import java.util.Date;

/**
 * TRolePrivilege entity. @author MyEclipse Persistence Tools
 */

public class TRolePrivilege implements java.io.Serializable {

	// Fields

	private TRolePrivilegeId id;
	private String FCreatuserid;
	private String FUpdateuserid;
	private Date FCreatdate;
	private Date FUpdatedate;

	// Constructors

	/** default constructor */
	public TRolePrivilege() {
	}

	/** minimal constructor */
	public TRolePrivilege(TRolePrivilegeId id) {
		this.id = id;
	}

	/** full constructor */
	public TRolePrivilege(TRolePrivilegeId id, String FCreatuserid,
			String FUpdateuserid, Date FCreatdate, Date FUpdatedate) {
		this.id = id;
		this.FCreatuserid = FCreatuserid;
		this.FUpdateuserid = FUpdateuserid;
		this.FCreatdate = FCreatdate;
		this.FUpdatedate = FUpdatedate;
	}

	// Property accessors

	public TRolePrivilegeId getId() {
		return this.id;
	}

	public void setId(TRolePrivilegeId id) {
		this.id = id;
	}

	public String getFCreatuserid() {
		return this.FCreatuserid;
	}

	public void setFCreatuserid(String FCreatuserid) {
		this.FCreatuserid = FCreatuserid;
	}

	public String getFUpdateuserid() {
		return this.FUpdateuserid;
	}

	public void setFUpdateuserid(String FUpdateuserid) {
		this.FUpdateuserid = FUpdateuserid;
	}

	public Date getFCreatdate() {
		return this.FCreatdate;
	}

	public void setFCreatdate(Date FCreatdate) {
		this.FCreatdate = FCreatdate;
	}

	public Date getFUpdatedate() {
		return this.FUpdatedate;
	}

	public void setFUpdatedate(Date FUpdatedate) {
		this.FUpdatedate = FUpdatedate;
	}

}