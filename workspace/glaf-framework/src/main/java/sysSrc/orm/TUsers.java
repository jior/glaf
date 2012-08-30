package sysSrc.orm;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TUsers entity. @author MyEclipse Persistence Tools
 */

public class TUsers implements java.io.Serializable {

	// Fields

	private String FUserid;
	private String FName;
	private String FPassword;
	private String FEmail;
	private String FStatus;
	private String FCreatuserid;
	private Date FCreatdate;
	private String FUpdateuserid;
	private Date FUpdatedate;
	private Set TUsersRoles = new HashSet(0);

	// Constructors

	/** default constructor */
	public TUsers() {
	}

	/** minimal constructor */
	public TUsers(String FUserid) {
		this.FUserid = FUserid;
	}

	/** full constructor */
	public TUsers(String FUserid, String FName, String FPassword,
			String FEmail, String FStatus, 
			String FCreatuserid, Date FCreatdate, String FUpdateuserid,
			Date FUpdatedate, Set TUsersRoles) {
		this.FUserid = FUserid;
		this.FName = FName;
		this.FPassword = FPassword;
		this.FEmail = FEmail;
		this.FStatus = FStatus;
		this.FCreatuserid = FCreatuserid;
		this.FCreatdate = FCreatdate;
		this.FUpdateuserid = FUpdateuserid;
		this.FUpdatedate = FUpdatedate;
		this.TUsersRoles = TUsersRoles;
	}

	// Property accessors

	public String getFUserid() {
		return this.FUserid;
	}

	public void setFUserid(String FUserid) {
		this.FUserid = FUserid;
	}

	public String getFName() {
		return this.FName;
	}

	public void setFName(String FName) {
		this.FName = FName;
	}

	public String getFPassword() {
		return this.FPassword;
	}

	public void setFPassword(String FPassword) {
		this.FPassword = FPassword;
	}

	public String getFEmail() {
		return this.FEmail;
	}

	public void setFEmail(String FEmail) {
		this.FEmail = FEmail;
	}

	public String getFStatus() {
		return this.FStatus;
	}

	public void setFStatus(String FStatus) {
		this.FStatus = FStatus;
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

	
	public Set getTUsersRoles() {
		return this.TUsersRoles;
	}

	public void setTUsersRoles(Set TUsersRoles) {
		this.TUsersRoles = TUsersRoles;
	}


}