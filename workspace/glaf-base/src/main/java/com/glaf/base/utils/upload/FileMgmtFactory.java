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
 * 主要作用是对FileUploadStatus进行管理，为客户端提供相应的
 * FileUploadStatus类对象。 
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
	 * 从request中取出FileUploadStatus
	 */
	public static FileUploadStatus getStatusBean(HttpServletRequest request) {
		FileMgmtFactory factory = getInstance();
		return factory.getUploadStatus(RequestUtils.getIPAddress(request));
	}

	/**
	 * 把FileUploadStatus放入Factory
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
	 * 删除FileUploadStatus类对象
	 */
	public void removeUploadStatus(String strID) {
		int nIndex = indexOf(strID);
		if (nIndex != -1) {
			vector.removeElementAt(nIndex);
		}
	}

	/**
	 * 存储FileUploadStatus类对象
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
