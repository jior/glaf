//================================================================================================
//项目名称 ：    基盘
//功    能 ：    获取session
//文件名称 ：    BaseDaoSupport.java                                   
//描    述 ：    获取HibernateDaoSupport的session
//================================================================================================
//修改履历                                                                
//年 月 日		区分			所 属/担 当           内 容									标识        
//----------   ----   -------------------- ---------------                          ------        
//2009/04/28   	编写   		Intasect/廖学志     新規作成                                                                            
//================================================================================================

package baseSrc.common;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class BaseDaoSupport extends HibernateDaoSupport {
	//获取session
	public Session getHdsSession(){
		return super.getSession();
	}
}
