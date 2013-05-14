package com.glaf.cms.webfile;

import java.io.FileOutputStream;
import java.io.IOException;

public class MyFile {
	public static final int SAVEAS_AUTO = 0;

	public static final int SAVEAS_PHYSICAL = 2;

	public static final int SAVEAS_VIRTUAL = 1;

	private String m_contentDisposition;

	private String m_contentType;

	private int m_endData;

	private String m_fieldname;

	private String m_fileExt;

	private String m_filename;

	private String m_filePathName;

	private boolean m_isMissing;

	private FileUpload m_parent;

	private int m_size;

	private int m_startData;

	private String m_subTypeMime;

	private String m_typeMime;

	public MyFile() {
		m_startData = 0;
		m_endData = 0;
		m_size = 0;
		m_fieldname = "";
		m_filename = "";
		m_fileExt = "";
		m_filePathName = "";
		m_contentType = "";
		m_contentDisposition = "";
		m_typeMime = "";
		m_subTypeMime = "";
		m_isMissing = true;
	}

	public byte getBinaryData(int index) {
		if (m_startData + index > m_endData) {
			throw new ArrayIndexOutOfBoundsException(
					"Index Out of range (1115).");
		}
		if (m_startData + index <= m_endData) {
			return m_parent.m_binArray[m_startData + index];
		} else {
			return 0;
		}
	}

	public byte[] getBytes() {
		byte binByte2[] = new byte[m_size];
		System.arraycopy(m_parent.m_binArray, m_startData, binByte2, 0, m_size);
		return binByte2;
	}

	public String getContentDisposition() {
		return m_contentDisposition;
	}

	public String getContentString() {
		String strTMP = new String(m_parent.m_binArray, m_startData, m_size);
		return strTMP;
	}

	public String getContentType() {
		return m_contentType;
	}

	protected int getEndData() {
		return m_endData;
	}

	public String getFieldName() {
		return m_fieldname;
	}

	public String getFileExt() {
		return m_fileExt;
	}

	public String getFileName() {
		return m_filename;
	}

	public String getFilePathName() {
		return m_filePathName;
	}

	public int getSize() {
		return m_size;
	}

	protected int getStartData() {
		return m_startData;
	}

	public String getSubTypeMIME() {
		return m_subTypeMime;
	}

	public String getTypeMIME() throws IOException {
		return m_typeMime;
	}

	public boolean isMissing() {
		return m_isMissing;
	}

	public void saveAs(String destFilePathName) throws FileUploadException,
			IOException {
		saveAs(destFilePathName, 0);
	}

	public void saveAs(String destFilePathName, int optionSaveAs)
			throws FileUploadException, IOException {
		String path = "";
		if (m_parent == null) {
			System.out.println("Error:FileUpload is null");
		}
		path = destFilePathName;
		if (path == null) {
			throw new IllegalArgumentException(
					"There is no specified destination file (1140).");
		}
		try {
			java.io.File file = new java.io.File(path);
			FileOutputStream fileOut = new FileOutputStream(file);
			fileOut.write(m_parent.m_binArray, m_startData, m_size);
			fileOut.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new FileUploadException("File can't be saved (1120).");
		}
	}

	protected void setContentDisposition(String contentDisposition) {
		m_contentDisposition = contentDisposition;
	}

	protected void setContentType(String contentType) {
		m_contentType = contentType;
	}

	protected void setEndData(int endData) {
		m_endData = endData;
	}

	protected void setFieldName(String fieldName) {
		m_fieldname = fieldName;
	}

	protected void setFileExt(String fileExt) {
		m_fileExt = fileExt;
	}

	protected void setFileName(String fileName) {
		m_filename = fileName;
	}

	protected void setFilePathName(String filePathName) {
		m_filePathName = filePathName;
	}

	protected void setIsMissing(boolean isMissing) {
		m_isMissing = isMissing;
	}

	protected void setParent(FileUpload parent) {
		m_parent = parent;
	}

	protected void setSize(int size) {
		m_size = size;
	}

	protected void setStartData(int startData) {
		m_startData = startData;
	}

	protected void setSubTypeMIME(String subTypeMime) {
		m_subTypeMime = subTypeMime;
	}

	protected void setTypeMIME(String TypeMime) {
		m_typeMime = TypeMime;
	}
}