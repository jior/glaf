package sysSrc.common.userPrivilege;

import sysSrc.framework.SysBaseActionForm;

public class RolesSelectFrom extends SysBaseActionForm {
	private static final long serialVersionUID = 1L;
	private String userId;
	private String rolesId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRolesId() {
		return rolesId;
	}
	public void setRolesId(String rolesId) {
		this.rolesId = rolesId;
	}

}
