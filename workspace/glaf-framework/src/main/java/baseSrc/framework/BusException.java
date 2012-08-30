//================================================================================================
//项目名称 ：    基盘
//功    能 ：   Exception
//文件名称 ：    BusException.java                                   
//描    述 ：    所有业务异常的基类。
//================================================================================================
//修改履历                                                                
//年 月 日		区分		所 属/担 当           		内 容									标识        
//----------   	----   	------------------- ---------------                          ------        
//2012/02/28   	编写   	Intasect/tr    	 新規作成                                                                            
//================================================================================================
package baseSrc.framework;

import baseSrc.framework.BaseDTOMap;
import baseSrc.framework.BaseException;

public class BusException extends BaseException {

	private static final long serialVersionUID = 1L;
	private BaseDTOMap returnDTOMap = null;

	public BusException(BaseDTOMap baseDTOMap) {
		this.returnDTOMap = baseDTOMap;
		throw this;
    }

	public BaseDTOMap getReturnDTOMap() {
		return returnDTOMap;
	}

	public void setReturnDTOMap(BaseDTOMap returnDTOMap) {
		this.returnDTOMap = returnDTOMap;
	}

}
