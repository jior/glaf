//================================================================================================
//项目名称 ：    基盘
//功    能 ：    主要作用是对FileUploadStatus进行管理，为客户端提供相应的
//             FileUploadStatus类对象。这是一个单例类。
//文件名称 ：    BeanControler.java                                   
//描    述 ：    
//================================================================================================
//修改履历                                                                								标识        
//年 月 日		区分			所 属/担 当           内 容									标识        
//----------   ----   -------------------- ---------------                          ------        
//2009/4/28   	编写   		Intasect/李闻海     新規作成                                                                            
//================================================================================================

package baseSrc.common.upload;


import java.util.Vector;

public class BeanControler {
	private static BeanControler beanControler = new BeanControler();
	private Vector vector = new Vector();
	private BeanControler() {
	}
	
	public static BeanControler getInstance() {
		return beanControler;
	}
	
	/**
	 * 取得相应FileUploadStatus类对象的存储位置
	 */
	private int indexOf(String strID) {
		int nReturn = -1;
		for (int i = 0; i < vector.size(); i++) {
			FileUploadStatus status = (FileUploadStatus) vector.elementAt(i);
			if (status.getUploadAddr().equals(strID)) {
				nReturn = i;
				break;
			}
		}
		return nReturn;
	}
	/**
	 * 取得相应FileUploadStatus类对象

	 */
	public FileUploadStatus getUploadStatus(String strID) {
		int pos = indexOf(strID);
		if (pos != -1) {
			return (FileUploadStatus) vector.elementAt(indexOf(strID));
		} else {
			return new FileUploadStatus();
		}
	}
    /**
	 * 存储FileUploadStatus类对象

	 */
    public void setUploadStatus(FileUploadStatus status) {
        int nIndex = indexOf(status.getUploadAddr());
        if ( -1 == nIndex) {
            vector.add(status);
        } else {
            vector.insertElementAt(status, nIndex);
            vector.removeElementAt(nIndex + 1);
        }
    }
    /**
     * 删除FileUploadStatus类对象

     */
    public void removeUploadStatus(String strID){
        int nIndex = indexOf(strID);
        if(-1!=nIndex)
            vector.removeElementAt(nIndex);
    }
}

