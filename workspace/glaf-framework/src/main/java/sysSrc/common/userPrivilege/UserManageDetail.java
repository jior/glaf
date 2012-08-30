package sysSrc.common.userPrivilege;

import java.util.Date;
public class UserManageDetail {
	private String p_userID;
	private String p_Name;
	private String p_Password;
	private String p_Email;
	//private String p_HRdeptName;
	//private String p_leaderEmail;
	private String p_Status;
	private String p_rolesId;
	private String p_rolesName;
	private String p_creatuserid;
	private Date p_creatdate;
	private String p_updateuserid;
	private Date p_updatedate;
	//private String p_rolecode;
	//private String p_rolename;
	private String p_flag;
	private String p_gys;
	
	
	public String getP_Status() {
		return p_Status;
	}
	public void setP_Status(String status) {
		p_Status = status;
	}
	public String getP_gys() {
		return p_gys;
	}
	public void setP_gys(String p_gys) {
		this.p_gys = p_gys;
	}
	public String getP_userID() {
		return p_userID;
	}
	public void setP_userID(String p_userid) {
		p_userID = p_userid;
	}
	public String getP_Name() {
		return p_Name;
	}
	public void setP_Name(String name) {
		p_Name = name;
	}
	public String getP_Password() {
		return p_Password;
	}
	public void setP_Password(String password) {
		p_Password = password;
	}
	public String getP_Email() {
		return p_Email;
	}
	public void setP_Email(String email) {
		p_Email = email;
	}
	public String getP_rolesName() {
		return p_rolesName;
	}
	public void setP_rolesName(String name) {
		p_rolesName = name;
	}
	public String getP_rolesId() {
		return p_rolesId;
	}
	public void setP_rolesId(String id) {
		p_rolesId = id;
	}
	public String getP_creatuserid() {
		return p_creatuserid;
	}
	public void setP_creatuserid(String p_creatuserid) {
		this.p_creatuserid = p_creatuserid;
	}
	public Date getP_creatdate() {
		return p_creatdate;
	}
	public void setP_creatdate(Date p_creatdate) {
		this.p_creatdate = p_creatdate;
	}
	public String getP_updateuserid() {
		return p_updateuserid;
	}
	public void setP_updateuserid(String p_updateuserid) {
		this.p_updateuserid = p_updateuserid;
	}
	public Date getP_updatedate() {
		return p_updatedate;
	}
	public void setP_updatedate(Date p_updatedate) {
		this.p_updatedate = p_updatedate;
	}
	public String getP_flag() {
		return p_flag;
	}
	public void setP_flag(String p_flag) {
		this.p_flag = p_flag;
	}
}

