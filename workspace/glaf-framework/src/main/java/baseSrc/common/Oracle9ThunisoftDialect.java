//================================================================================================
//项目名称 ：    基盘
//功    能 ：    不使用oracle rownum的方式进行分页处理
//文件名称 ：    Oracle9ThunisoftDialect.java                                   
//描    述 ：    重写了Oracle9iDialect的supportsLimit方法，返回false禁止hibernate使用rownum的方式进行分页处理           
//================================================================================================
//修改履历                                                                
//年 月 日		区分			所 属/担 当           内 容									标识        
//----------   ----   -------------------- ---------------                          ------        
//2009/05/13   	编写   		Intasect/李闻海      新規作成                                                                            
//================================================================================================

package baseSrc.common;

import org.hibernate.dialect.Oracle9iDialect;;


public class Oracle9ThunisoftDialect extends Oracle9iDialect
{

	//执行父类构造方法
	public Oracle9ThunisoftDialect()

	{
		super();
	}
	//返回false禁止hibernate使用rownum的方式进行分页处理
	//如果要使用rownum的分页方式 设置返回值为true即可。
	public boolean supportsLimit()
	{
		return false;
	}

}
