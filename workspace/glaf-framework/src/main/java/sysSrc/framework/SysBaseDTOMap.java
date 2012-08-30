//================================================================================================
//项目名称 ：    基盘
//功    能 ：   DTO
//文件名称 ：    BaseDTOMap.java                                   
//描    述 ：    DTO基类，内置对象：返回页面的ID；session对象；返回页面的消息；出错控件；
//         dao中方法返回值必须为BaseDTOMap
//================================================================================================
//修改履历                                                                
//年 月 日		区分		所 属/担 当           		内 容									标识        
//----------   	----   	------------------- ---------------                          ------        
//2009/04/28   	编写   	Intasect/余海涛    	 新規作成                                                                            
//================================================================================================
package sysSrc.framework;

import baseSrc.framework.BaseDTOMap;

public class SysBaseDTOMap extends BaseDTOMap{

	private static final long serialVersionUID = 1L;
	
	//sys session对象
	private String sysTestDTO;

	public String getSysTestDTO() {
		return sysTestDTO;
	}

	public void setSysTestDTO(String sysTestDTO) {
		this.sysTestDTO = sysTestDTO;
	}
	
	
	
}
