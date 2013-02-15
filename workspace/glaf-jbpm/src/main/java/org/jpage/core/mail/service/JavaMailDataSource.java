/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.core.mail.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import org.apache.commons.lang.StringUtils;
import org.jpage.datacenter.file.DataFile;
 

public class JavaMailDataSource implements DataSource {

	private DataFile dataFile;

	public JavaMailDataSource(DataFile dataFile) {
		this.dataFile = dataFile;
	}

	public InputStream getInputStream() throws IOException {
		InputStream inputStream = null;
		try {
			if (dataFile.getInputStream() != null) {
				inputStream = dataFile.getInputStream();
			} else if (StringUtils.isNotBlank(dataFile.getDeviceId())) {

			} else {

			}
			if (inputStream == null) {
				throw new IOException("inputStream is null");
			}
			return inputStream;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IOException(ex.getMessage());
		}
	}

	public OutputStream getOutputStream() {
		throw new UnsupportedOperationException(
				"Read-only javax.activation.DataSource");
	}

	public String getContentType() {
		if (dataFile.getContentType() != null) {
			return dataFile.getContentType();
		}
		return "application/octet-stream";
	}

	public String getName() {
		return dataFile.getName();
	}

	public DataFile getDataFile() {
		return dataFile;
	}

	public void setDataFile(DataFile dataFile) {
		this.dataFile = dataFile;
	}
}