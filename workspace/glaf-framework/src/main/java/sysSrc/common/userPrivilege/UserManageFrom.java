package sysSrc.common.userPrivilege;

import sysSrc.framework.SysBaseActionForm;

public class UserManageFrom  extends SysBaseActionForm {
private static final long serialVersionUID = 1L;
	private String queryUserId;
	private String queryUseName;
	public String getQueryUserId() {
		return queryUserId;
	}
	public void setQueryUserId(String queryUserId) {
		this.queryUserId = queryUserId;
	}
	public String getQueryUseName() {
		return queryUseName;
	}
	public void setQueryUseName(String queryUseName) {
		this.queryUseName = queryUseName;
	}
}
