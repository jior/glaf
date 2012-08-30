//================================================================================================
//项目名称 ：    基盘
//功    能 ：   Exception
//文件名称 ：    BaseException.java                                   
//描    述 ：    所有异常的基类。
//================================================================================================
//修改履历                                                                
//年 月 日		区分		所 属/担 当           		内 容									标识        
//----------   	----   	------------------- ---------------                          ------        
//2009/04/28   	编写   	Intasect/余海涛    	 新規作成                                                                            
//================================================================================================
package baseSrc.framework;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BaseException() {
    	super();    	
    }

    public BaseException(String s) {
        super(s);
    }

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

}
