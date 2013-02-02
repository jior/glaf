/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.glaf.base.utils.upload;

/**
 * <p>Title: 类控制器</p>
 *
 * <p>Description: 主要作用是对FileUploadStatus进行管理，为客户端提供相应的
 * FileUploadStatus类对象。这是一个单例类。</p>
 *
 */
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