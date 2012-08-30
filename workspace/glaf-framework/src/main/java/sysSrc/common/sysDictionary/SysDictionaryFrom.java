package sysSrc.common.sysDictionary;

import baseSrc.common.AutoArrayList;
import sysSrc.framework.SysBaseActionForm;

public class SysDictionaryFrom extends SysBaseActionForm {
	private static final long serialVersionUID = 1L;
	private String f_BigType;
	private String f_SmallType;
	private String f_IsLock;
	private String f_Const;
	private String f_Remark;
	private String f_Comon;
	private String flag;
	private int allCount = 0;
	
	public int getAllCount() {
		return allCount;
	}
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	private AutoArrayList sysDictionaryDetail = new AutoArrayList(SysDictionaryDetail.class);
	
	public AutoArrayList getSysDictionaryDetail() {
		return sysDictionaryDetail;
	}
	public void setSysDictionaryDetail(AutoArrayList sysDictionaryDetail) {
		this.sysDictionaryDetail.clear();
		this.sysDictionaryDetail = sysDictionaryDetail;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getF_BigType() {
		return f_BigType;
	}
	public void setF_BigType(String f_BigType) {
		this.f_BigType = f_BigType;
	}
	public String getF_SmallType() {
		return f_SmallType;
	}
	public void setF_SmallType(String f_SmallType) {
		this.f_SmallType = f_SmallType;
	}
	public String getF_IsLock() {
		return f_IsLock;
	}
	public void setF_IsLock(String f_IsLock) {
		this.f_IsLock = f_IsLock;
	}
	
	public String getF_Const() {
		return f_Const;
	}
	public void setF_Const(String f_Const) {
		this.f_Const = f_Const;
	}
	public String getF_Remark() {
		return f_Remark;
	}
	public void setF_Remark(String f_Remark) {
		this.f_Remark = f_Remark;
	}
	public String getF_Comon() {
		return f_Comon;
	}
	public void setF_Comon(String f_Comon) {
		this.f_Comon = f_Comon;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
