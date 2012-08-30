package sysSrc.orm;

import java.util.HashSet;
import java.util.Set;

/**
 * TRole entity. @author MyEclipse Persistence Tools
 */

public class TRole implements java.io.Serializable {

	// Fields

	private Long FRoleid;
	private String FRolename;
	private String FRemark;
	private Set TUsersRoles = new HashSet(0);
	private Set TRolePrivileges = new HashSet(0);

	// Constructors

	/** default constructor */
	public TRole() {
	}

	/** minimal constructor */
	public TRole(Long FRoleid) {
		this.FRoleid = FRoleid;
	}

	/** full constructor */
	public TRole(Long FRoleid, String FRolename, String FRemark,
			Set TUsersRoles, Set TRolePrivileges) {
		this.FRoleid = FRoleid;
		this.FRolename = FRolename;
		this.FRemark = FRemark;
		this.TUsersRoles = TUsersRoles;
		this.TRolePrivileges = TRolePrivileges;
	}

	// Property accessors

	public Long getFRoleid() {
		return this.FRoleid;
	}

	public void setFRoleid(Long FRoleid) {
		this.FRoleid = FRoleid;
	}

	public String getFRolename() {
		return this.FRolename;
	}

	public void setFRolename(String FRolename) {
		this.FRolename = FRolename;
	}

	public String getFRemark() {
		return this.FRemark;
	}

	public void setFRemark(String FRemark) {
		this.FRemark = FRemark;
	}

	public Set getTUsersRoles() {
		return this.TUsersRoles;
	}

	public void setTUsersRoles(Set TUsersRoles) {
		this.TUsersRoles = TUsersRoles;
	}

	public Set getTRolePrivileges() {
		return this.TRolePrivileges;
	}

	public void setTRolePrivileges(Set TRolePrivileges) {
		this.TRolePrivileges = TRolePrivileges;
	}

}