package baseSrc.orm;

import java.io.Serializable;
import java.util.Date;

public class Attachment implements Serializable{
	
	private static final long serialVersionUID = -1267781839714359404L;
	
	private Long id;
	private String referId;
	private int referType;
	private String name;
	private String url;
	private String flag;
	private Date   cdatetime;
	private String cuID;
	private String cpID;
	private String ciP;
	
	private Date   udatetime;
	private String uuID;
	private String upID;
	private String uiP;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReferId() {
		return referId;
	}
	public void setReferId(String referId) {
		this.referId = referId;
	}
	public int getReferType() {
		return referType;
	}
	public void setReferType(int referType) {
		this.referType = referType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getCuID() {
		return cuID;
	}
	public void setCuID(String cuID) {
		this.cuID = cuID;
	}
	public String getCpID() {
		return cpID;
	}
	public void setCpID(String cpID) {
		this.cpID = cpID;
	}
	public String getCiP() {
		return ciP;
	}
	public void setCiP(String ciP) {
		this.ciP = ciP;
	}
	public String getUuID() {
		return uuID;
	}
	public void setUuID(String uuID) {
		this.uuID = uuID;
	}
	public String getUpID() {
		return upID;
	}
	public void setUpID(String upID) {
		this.upID = upID;
	}
	public String getUiP() {
		return uiP;
	}
	public void setUiP(String uiP) {
		this.uiP = uiP;
	}
	public Date getCdatetime() {
		return cdatetime;
	}
	public void setCdatetime(Date cdatetime) {
		this.cdatetime = cdatetime;
	}
	public Date getUdatetime() {
		return udatetime;
	}
	public void setUdatetime(Date udatetime) {
		this.udatetime = udatetime;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
}
