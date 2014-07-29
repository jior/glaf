package com.glaf.cms.webfile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang3.StringUtils;

public class FileUpload {
	public static final int SAVE_AUTO = 0;

	public static final int SAVE_PHYSICAL = 2;

	public static final int SAVE_VIRTUAL = 1;

	private Vector<String> m_allowedFilesList;

	protected ServletContext m_application;

	protected byte m_binArray[];

	private String m_boundary;

	private int m_currentIndex;

	private Vector<String> m_deniedFilesList;

	private boolean m_denyPhysicalPath;

	private int m_endData;

	private Files m_files;

	private Request m_formRequest;

	private long m_maxFileSize;

	protected HttpServletRequest m_request;

	protected HttpServletResponse m_response;

	private int m_startData;

	private int m_totalBytes;

	private long m_totalMaxFileSize;

	public FileUpload() {
		m_totalBytes = 0;
		m_currentIndex = 0;
		m_startData = 0;
		m_endData = 0;
		m_boundary = "";
		m_totalMaxFileSize = 0L;
		m_maxFileSize = 0L;
		m_denyPhysicalPath = false;
		m_deniedFilesList = new Vector<String>();
		m_deniedFilesList.addElement("js");
		m_deniedFilesList.addElement("jsp");
		m_allowedFilesList = new Vector<String>();
		m_files = new Files();
		m_formRequest = new Request();
	}

	public void destroy() {
		m_binArray = null;
	}

	public void downloadFile(String sourceFilePathName)
			throws FileUploadException, IOException, ServletException {
		downloadFile(sourceFilePathName, null, null);
	}

	public void downloadFile(String sourceFilePathName, String contentType)
			throws FileUploadException, IOException, ServletException {
		downloadFile(sourceFilePathName, contentType, null);
	}

	public void downloadFile(String sourceFilePathName, String contentType,
			String destFileName) throws FileUploadException, IOException,
			ServletException {
		downloadFile(sourceFilePathName, contentType, destFileName, 65000);
	}

	public void downloadFile(String sourceFilePathName, String contentType,
			String destFileName, int blockSize) throws FileUploadException,
			IOException, ServletException {
		if (sourceFilePathName == null) {
			throw new IllegalArgumentException(
					String.valueOf((new StringBuffer("File '")).append(
							sourceFilePathName).append("' not found (1040).")));
		}
		if (sourceFilePathName.equals("")) {
			throw new IllegalArgumentException(
					String.valueOf((new StringBuffer("File '")).append(
							sourceFilePathName).append("' not found (1040).")));
		}
		if (!isVirtual(sourceFilePathName) && m_denyPhysicalPath) {
			throw new SecurityException("Physical path is denied (1035).");
		}
		if (StringUtils.endsWith(sourceFilePathName, "mail.properties")
				|| StringUtils.endsWith(sourceFilePathName, "key")
				|| StringUtils.containsAny(sourceFilePathName, "jdbc")
				|| StringUtils.endsWith(sourceFilePathName, "jdbc.properties")
				|| StringUtils
						.endsWith(sourceFilePathName, "hibernate.cfg.xml")) {
			throw new SecurityException("file is access denied (1036).");
		}
		if (isVirtual(sourceFilePathName)) {
			sourceFilePathName = m_application.getRealPath(sourceFilePathName);
		}
		m_response.reset();
		java.io.File file = new java.io.File(sourceFilePathName);
		FileInputStream fileIn = new FileInputStream(file);
		long fileLen = file.length();
		int readBytes = 0;
		int totalRead = 0;
		byte b[] = new byte[blockSize];
		if (contentType == null)
			m_response.setContentType("application/x-msdownload");
		else if (contentType.length() == 0)
			m_response.setContentType("application/x-msdownload");
		else
			m_response.setContentType(contentType);
		m_response.setContentLength((int) fileLen);
		if (destFileName == null) {
			String filename = getFileName(sourceFilePathName);
			filename = new String(filename.getBytes("GBK"), "ISO8859_1");
			m_response.setHeader("Content-Disposition",
					"attachment; filename=".concat(String.valueOf(filename)));
		} else if (destFileName.length() == 0) {
			m_response.setHeader("Content-Disposition", "attachment;");
		} else {
			String filename = destFileName;
			filename = new String(filename.getBytes("GBK"), "ISO8859_1");
			m_response.setHeader("Content-Disposition",
					"attachment; filename=".concat(filename));
		}
		m_response.setContentType("application/octet-stream");
		while (totalRead < fileLen) {
			readBytes = fileIn.read(b, 0, blockSize);
			totalRead += readBytes;
			m_response.getOutputStream().write(b, 0, readBytes);
		}
		fileIn.close();
	}

	public byte getBinaryData(int index) {
		byte retval;
		try {
			retval = m_binArray[index];
		} catch (Exception e) {
			throw new ArrayIndexOutOfBoundsException(
					"Index out of range (1005).");
		}
		return retval;
	}

	private String getContentDisposition(String dataHeader) {
		String value = "";
		int start = 0;
		int end = 0;
		start = dataHeader.indexOf(":") + 1;
		end = dataHeader.indexOf(";");
		value = dataHeader.substring(start, end);
		return value;
	}

	private String getContentType(String dataHeader) {
		String token = "";
		String value = "";
		int start = 0;
		int end = 0;
		token = "Content-Type:";
		start = dataHeader.indexOf(token) + token.length();
		if (start != -1) {
			end = dataHeader.length();
			value = dataHeader.substring(start, end);
		}
		return value;
	}

	private String getDataFieldValue(String dataHeader, String fieldName) {
		String token = "";
		String value = "";
		int pos = 0;
		int i = 0;
		int start = 0;
		int end = 0;
		token = String.valueOf((new StringBuffer(String.valueOf(fieldName)))
				.append("=").append('"'));
		pos = dataHeader.indexOf(token);
		if (pos > 0) {
			i = pos + token.length();
			start = i;
			token = "\"";
			end = dataHeader.indexOf(token, i);
			if (start > 0 && end > 0) {
				value = dataHeader.substring(start, end);
			}
		}
		return value;
	}

	private String getDataHeader() {
		int start = m_currentIndex;
		int end = 0;
		boolean found = false;
		while (!found) {
			if (m_binArray[m_currentIndex] == 13
					&& m_binArray[m_currentIndex + 2] == 13) {
				found = true;
				end = m_currentIndex - 1;
				m_currentIndex = m_currentIndex + 2;
			} else {
				m_currentIndex++;
			}
		}
		String dataHeader = new String(m_binArray, start, (end - start) + 1);
		return dataHeader;
	}

	private void getDataSection() {
		int searchPos = m_currentIndex;
		int keyPos = 0;
		int boundaryLen = m_boundary.length();
		m_startData = m_currentIndex;
		m_endData = 0;
		do {
			if (searchPos >= m_totalBytes) {
				break;
			}
			if (m_binArray[searchPos] == (byte) m_boundary.charAt(keyPos)) {
				if (keyPos == boundaryLen - 1) {
					m_endData = ((searchPos - boundaryLen) + 1) - 3;
					break;
				}
				searchPos++;
				keyPos++;
			} else {
				searchPos++;
				keyPos = 0;
			}
		} while (true);
		m_currentIndex = m_endData + boundaryLen + 3;
	}

	private String getFileExt(String fileName) {
		String value = "";
		int start = 0;
		int end = 0;
		if (fileName == null) {
			return null;
		}
		start = fileName.lastIndexOf(46) + 1;
		end = fileName.length();
		value = fileName.substring(start, end);
		if (fileName.lastIndexOf(46) > 0) {
			return value;
		} else {
			return "";
		}
	}

	private String getFileName(String filePathName) {
		int pos = 0;
		pos = filePathName.lastIndexOf(47);
		if (pos != -1) {
			return filePathName.substring(pos + 1, filePathName.length());
		}
		pos = filePathName.lastIndexOf(92);
		if (pos != -1) {
			return filePathName.substring(pos + 1, filePathName.length());
		} else {
			return filePathName;
		}
	}

	public Files getFiles() {
		return m_files;
	}

	public Request getRequest() {
		return m_formRequest;
	}

	public int getSize() {
		return m_totalBytes;
	}

	private String getSubTypeMIME(String ContentType) {
		int start = 0;
		int end = 0;
		start = ContentType.indexOf("/") + 1;
		if (start != -1) {
			end = ContentType.length();
			return ContentType.substring(start, end);
		} else {
			return ContentType;
		}
	}

	private String getTypeMIME(String ContentType) {
		int pos = 0;
		pos = ContentType.indexOf("/");
		if (pos != -1) {
			return ContentType.substring(1, pos);
		} else {
			return ContentType;
		}
	}

	public final void init(ServletConfig config) throws ServletException {
		m_application = config.getServletContext();
	}

	public final void initialize(PageContext pageContext)
			throws ServletException {
		m_application = pageContext.getServletContext();
		m_request = (HttpServletRequest) pageContext.getRequest();
		m_response = (HttpServletResponse) pageContext.getResponse();
	}

	public final void initialize(ServletConfig config,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		m_application = config.getServletContext();
		m_request = request;
		m_response = response;
	}

	private boolean isVirtual(String pathName) {
		if (m_application.getRealPath(pathName) != null) {
			java.io.File virtualFile = new java.io.File(
					m_application.getRealPath(pathName));
			return virtualFile.exists();
		} else {
			return false;
		}
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		m_request = request;
		m_response = response;
	}

	public void setAllowedFilesList(String allowedFilesList) {
		String ext = "";
		if (allowedFilesList != null) {
			ext = "";
			for (int i = 0; i < allowedFilesList.length(); i++) {
				if (allowedFilesList.charAt(i) == ',') {
					if (!m_allowedFilesList.contains(ext)) {
						m_allowedFilesList.addElement(ext);
					}
					ext = "";
				} else {
					ext = ext + allowedFilesList.charAt(i);
				}
			}

			if (StringUtils.isNotEmpty(ext)) {
				ext = ext.toLowerCase();
				m_allowedFilesList.addElement(ext);
			}
		} else {
			m_allowedFilesList = null;
		}
	}

	public void setDeniedFilesList(String deniedFilesList) throws IOException,
			ServletException {
		String ext = "";
		if (deniedFilesList != null) {
			ext = "";
			for (int i = 0; i < deniedFilesList.length(); i++) {
				if (deniedFilesList.charAt(i) == ',') {
					if (!m_deniedFilesList.contains(ext)) {
						m_deniedFilesList.addElement(ext);
					}
					ext = "";
				} else {
					ext = ext + deniedFilesList.charAt(i);
				}
			}

			if (StringUtils.isNotEmpty(ext)) {
				m_deniedFilesList.addElement(ext);
			}
		} else {
			m_deniedFilesList = null;
		}
		if (m_deniedFilesList == null) {
			m_deniedFilesList = new Vector<String>();
		}

	}

	public void setDenyPhysicalPath(boolean deny) {
		m_denyPhysicalPath = deny;
	}

	public void setMaxFileSize(long maxFileSize) {
		m_maxFileSize = maxFileSize;
	}

	public void setTotalMaxFileSize(long totalMaxFileSize) {
		m_totalMaxFileSize = totalMaxFileSize;
	}

	public void upload() throws FileUploadException, IOException,
			ServletException {
		int totalRead = 0;
		int readBytes = 0;
		long totalFileSize = 0L;
		boolean found = false;
		String dataHeader = "";
		String fieldName = "";
		String fileName = "";
		String fileExt = "";
		String filePathName = "";
		String contentType = "";
		String contentDisposition = "";
		String typeMIME = "";
		String subTypeMIME = "";
		boolean isFile = false;
		m_totalBytes = m_request.getContentLength();
		m_binArray = new byte[m_totalBytes];
		for (; totalRead < m_totalBytes; totalRead += readBytes) {
			try {
				m_request.getInputStream();
				readBytes = m_request.getInputStream().read(m_binArray,
						totalRead, m_totalBytes - totalRead);
			} catch (Exception e) {
				throw new FileUploadException("Unable to upload.");
			}
		}

		for (; !found && m_currentIndex < m_totalBytes; m_currentIndex++) {
			if (m_binArray[m_currentIndex] == 13) {
				found = true;
			} else {
				m_boundary = m_boundary + (char) m_binArray[m_currentIndex];
			}
		}

		if (m_currentIndex == 1) {
			return;
		}
		m_currentIndex++;

		do {
			if (m_currentIndex >= m_totalBytes) {
				break;
			}
			dataHeader = getDataHeader();
			m_currentIndex = m_currentIndex + 2;
			isFile = dataHeader.indexOf("filename") > 0;
			fieldName = getDataFieldValue(dataHeader, "name");
			if (isFile) {
				filePathName = getDataFieldValue(dataHeader, "filename");
				fileName = getFileName(filePathName);
				fileExt = getFileExt(fileName);
				contentType = getContentType(dataHeader);
				contentDisposition = getContentDisposition(dataHeader);
				typeMIME = getTypeMIME(contentType);
				subTypeMIME = getSubTypeMIME(contentType);
			}
			getDataSection();
			if (isFile && fileName.length() > 0) {
				if (m_deniedFilesList.contains(fileExt)) {
					throw new SecurityException(
							"The extension of the file is denied to be uploaded (1015).");
				}
				if (m_allowedFilesList != null && !m_allowedFilesList.isEmpty()
						&& !m_allowedFilesList.contains(fileExt.toLowerCase())) {
					throw new SecurityException(
							"The extension of the file is not allowed to be uploaded (1010).");
				}
				if (m_maxFileSize > 0
						&& ((m_endData - m_startData) + 1) > m_maxFileSize) {
					throw new SecurityException(
							String.valueOf((new StringBuffer(
									"Size exceeded for this file : ")).append(
									fileName).append(" (1105).")));
				}
				totalFileSize += (m_endData - m_startData) + 1;
				if (m_totalMaxFileSize > 0
						&& totalFileSize > m_totalMaxFileSize) {
					throw new SecurityException(
							"Total File Size exceeded (1110).");
				}
			}
			if (isFile) {
				com.glaf.cms.webfile.MyFile newFile = new com.glaf.cms.webfile.MyFile();
				newFile.setParent(this);
				newFile.setFieldName(fieldName);
				newFile.setFileName(fileName);
				newFile.setFileExt(fileExt);
				newFile.setFilePathName(filePathName);
				newFile.setIsMissing(filePathName.length() == 0);
				newFile.setContentType(contentType);
				newFile.setContentDisposition(contentDisposition);
				newFile.setTypeMIME(typeMIME);
				newFile.setSubTypeMIME(subTypeMIME);
				if (contentType.indexOf("application/x-macbinary") > 0) {
					m_startData = m_startData + 128;
				}
				newFile.setSize((m_endData - m_startData) + 1);
				newFile.setStartData(m_startData);
				newFile.setEndData(m_endData);
				m_files.addFile(newFile);
			} else {
				String value = new String(m_binArray, m_startData,
						(m_endData - m_startData) + 1);
				m_formRequest.putParameter(fieldName, value);
			}
			if ((char) m_binArray[m_currentIndex + 1] == '-') {
				break;
			}
			m_currentIndex = m_currentIndex + 2;
		} while (true);
	}

}
