/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glaf.base.utils.upload;

/**
 *  
 * ��Ҫ�����Ƕ�FileUploadStatus���й���Ϊ�ͻ����ṩ��Ӧ��
 * FileUploadStatus����� 
 *
 */
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.glaf.core.util.RequestUtils;

public class FileMgmtFactory {

	private static final FileMgmtFactory instance = new FileMgmtFactory();

	public static FileMgmtFactory getInstance() {
		return instance;
	}

	/**
	 * ��request��ȡ��FileUploadStatus
	 */
	public static FileUploadStatus getStatusBean(HttpServletRequest request) {
		FileMgmtFactory factory = getInstance();
		return factory.getUploadStatus(RequestUtils.getIPAddress(request));
	}

	/**
	 * ��FileUploadStatus����Factory
	 */
	public static void saveStatusBean(HttpServletRequest request,
			FileUploadStatus statusBean) {
		statusBean.setUploadAddr(RequestUtils.getIPAddress(request));
		FileMgmtFactory factory = getInstance();
		factory.setUploadStatus(statusBean);
	}

	private Vector<FileUploadStatus> vector = new Vector<FileUploadStatus>();

	private FileMgmtFactory() {
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
	 * ɾ��FileUploadStatus�����
	 */
	public void removeUploadStatus(String strID) {
		int nIndex = indexOf(strID);
		if (nIndex != -1) {
			vector.removeElementAt(nIndex);
		}
	}

	/**
	 * �洢FileUploadStatus�����
	 */
	public void setUploadStatus(FileUploadStatus status) {
		int nIndex = indexOf(status.getUploadAddr());
		if (-1 == nIndex) {
			vector.add(status);
		} else {
			vector.insertElementAt(status, nIndex);
			vector.removeElementAt(nIndex + 1);
		}
	}
}
