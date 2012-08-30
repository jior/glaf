package baseSrc.common;


import java.util.HashMap;
import java.util.List;

import sysSrc.orm.TPrivilege;


public class BaseCom {

	private boolean flg = false;
	private String userId = ""; 
	private String userName="";
	//private String deptId = "";
	private HashMap<String,List> userPrivilageMap = null ;
	//private String roleCode = "";
	//private String roleName = "";
	private List<String> allPrivilages = null;
	private List<TPrivilege> allPrivilege = null;
	private List<TPrivilege> rolePrivilege = null;
	private String ip;
	private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setFlg(boolean flg) {
		this.flg = flg;
	}

	public boolean isDAOExecutable(String actionPath) {
		return flg;
	}
	
	public String getJspAuthority(String jspId) {
		return "1";
	}
	
	public HashMap<String, List> getUserPrivilageMap() {
		return userPrivilageMap;
	}

	public void setUserPrivilageMap(HashMap<String, List> userPrivilageMap) {
		this.userPrivilageMap = userPrivilageMap;
	}
	
	public List<TPrivilege> getAllPrivilege() {
		return allPrivilege;
	}

	public void setAllPrivilege(List<TPrivilege> allPrivilege) {
		this.allPrivilege = allPrivilege;
	}

	public List<TPrivilege> getRolePrivilege() {
		return rolePrivilege;
	}

	public void setUserPrivilege(List<TPrivilege> userPrivilege) {
		this.rolePrivilege = userPrivilege;
	}
	
	public List getAllPrivilages() {
		return allPrivilages;
	}

	public void setAllPrivilages(List allPrivilages) {
		this.allPrivilages = allPrivilages;
	}
}
