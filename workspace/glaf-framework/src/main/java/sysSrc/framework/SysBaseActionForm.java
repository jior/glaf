//================================================================================================
//项目名称 ：    基盘
//功    能 ：   ActionForm
//文件名称 ：    BaseActionForm.java                                   
//描    述 ：    所有Form都需要继承
//================================================================================================
//修改履历                                                                
//年 月 日		区分		所 属/担 当           		内 容									标识        
//----------   	----   	------------------- ---------------                          ------        
//2009/04/28   	编写   	Intasect/余海涛    	 新規作成                                                                            
//================================================================================================

package sysSrc.framework;

import baseSrc.framework.BaseActionForm;


public class SysBaseActionForm extends BaseActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private String sysTestParm;

	public String getSysTestParm() {
		return sysTestParm;
	}

	public void setSysTestParm(String sysTestParm) {
		this.sysTestParm = sysTestParm;
	}
	
	
	
}
