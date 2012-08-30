package sysSrc.orm;

import java.util.HashSet;
import java.util.Set;

/**
 * TPrivilege entity. @author MyEclipse Persistence Tools
 */

public class TPrivilege implements java.io.Serializable {

	// Fields

	private String FPrivilegeid;
	private String FPrivilegename;
	private String FPrivilegetype;
	private String FUrl;
	private Set TRolePrivileges = new HashSet(0);

	// Constructors

	/** default constructor */
	public TPrivilege() {
	}

	/** minimal constructor */
	public TPrivilege(String FPrivilegeid) {
		this.FPrivilegeid = FPrivilegeid;
	}

	/** full constructor */
	public TPrivilege(String FPrivilegeid, String FPrivilegename,
			String FPrivilegetype, String FUrl, Set TRolePrivileges) {
		this.FPrivilegeid = FPrivilegeid;
		this.FPrivilegename = FPrivilegename;
		this.FPrivilegetype = FPrivilegetype;
		this.FUrl = FUrl;
		this.TRolePrivileges = TRolePrivileges;
	}

	// Property accessors

	public String getFPrivilegeid() {
		return this.FPrivilegeid;
	}

	public void setFPrivilegeid(String FPrivilegeid) {
		this.FPrivilegeid = FPrivilegeid;
	}

	public String getFPrivilegename() {
		return this.FPrivilegename;
	}

	public void setFPrivilegename(String FPrivilegename) {
		this.FPrivilegename = FPrivilegename;
	}

	public String getFPrivilegetype() {
		return this.FPrivilegetype;
	}

	public void setFPrivilegetype(String FPrivilegetype) {
		this.FPrivilegetype = FPrivilegetype;
	}

	public String getFUrl() {
		return this.FUrl;
	}

	public void setFUrl(String FUrl) {
		this.FUrl = FUrl;
	}

	public Set getTRolePrivileges() {
		return this.TRolePrivileges;
	}

	public void setTRolePrivileges(Set TRolePrivileges) {
		this.TRolePrivileges = TRolePrivileges;
	}

}