package sysSrc.orm;

/**
 * TUsersRoleId entity. @author MyEclipse Persistence Tools
 */

public class TUsersRoleId implements java.io.Serializable {

	// Fields

	private TUsers TUsers;
	private TRole TRole;

	// Constructors

	/** default constructor */
	public TUsersRoleId() {
	}

	/** full constructor */
	public TUsersRoleId(TUsers TUsers, TRole TRole) {
		this.TUsers = TUsers;
		this.TRole = TRole;
	}

	// Property accessors

	public TUsers getTUsers() {
		return this.TUsers;
	}

	public void setTUsers(TUsers TUsers) {
		this.TUsers = TUsers;
	}

	public TRole getTRole() {
		return this.TRole;
	}

	public void setTRole(TRole TRole) {
		this.TRole = TRole;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TUsersRoleId))
			return false;
		TUsersRoleId castOther = (TUsersRoleId) other;

		return ((this.getTUsers() == castOther.getTUsers()) || (this
				.getTUsers() != null
				&& castOther.getTUsers() != null && this.getTUsers().equals(
				castOther.getTUsers())))
				&& ((this.getTRole() == castOther.getTRole()) || (this
						.getTRole() != null
						&& castOther.getTRole() != null && this.getTRole()
						.equals(castOther.getTRole())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTUsers() == null ? 0 : this.getTUsers().hashCode());
		result = 37 * result
				+ (getTRole() == null ? 0 : this.getTRole().hashCode());
		return result;
	}

}