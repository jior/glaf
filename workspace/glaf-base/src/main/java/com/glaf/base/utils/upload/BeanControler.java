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
 * <p>Title: �������</p>
 *
 * <p>Description: ��Ҫ�����Ƕ�FileUploadStatus���й���Ϊ�ͻ����ṩ��Ӧ��
 * FileUploadStatus���������һ�������ࡣ</p>
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
	 * ȡ����ӦFileUploadStatus�����Ĵ洢λ��
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
	 * ȡ����ӦFileUploadStatus�����
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
	 * �洢FileUploadStatus�����
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
     * ɾ��FileUploadStatus�����
     */
    public void removeUploadStatus(String strID){
        int nIndex = indexOf(strID);
        if(-1!=nIndex)
            vector.removeElementAt(nIndex);
    }
}