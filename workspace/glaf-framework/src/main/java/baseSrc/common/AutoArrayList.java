//================================================================================================
//项目名称 ：    基盘
//功    能 ：    自动实例化类类型对象。
//文件名称 ：    AutoArrayList.java                                   
//描    述 ：    重写了ArrayList的get方法，使其能够通过类类型自动创建该类类型的对象类型。
//             保存到ArrayList中，并且返回该对象。
//================================================================================================
//修改履历                                                                
//年 月 日		区分			所 属/担 当           内 容									标识        
//----------   ----   -------------------- ---------------                          ------        
//2009/04/28   	编写   		Intasect/廖学志      新規作成                                                                            
//================================================================================================

package baseSrc.common;
import java.util.ArrayList;

public class AutoArrayList extends ArrayList<Object> { 

	private static final long serialVersionUID = 1L;
	private Class<?> itemClass; 
	   
    /**
	 * 构造函数
	 * @param itemClass
	 */
	public AutoArrayList(Class<?> itemClass) { 
	    this.itemClass = itemClass; 
	  } 
	   
	/**
	 * 重写ArrayList的get方法使其能够通过类类型自动创建该类类型的对象类型。
       保存到ArrayList中，并且返回该对象。
	 * @param itemClass
	 */
	public Object get(int index) { 
	    try { 
	      while (index >= size()) { 
	        add(itemClass.newInstance()); 
	      } 
	    } catch (Exception e) { 
	      e.printStackTrace(); 
	    } 
	    return super.get(index); 
	  } 
	}