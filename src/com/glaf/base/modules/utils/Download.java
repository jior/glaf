package com.glaf.base.modules.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
 

import org.apache.commons.lang.StringUtils;
 

public class Download {

	protected HttpServletRequest m_request;

	protected HttpServletResponse m_response;

	protected ServletContext m_application;

	private String m_contentDisposition;

	private String filenames;

	public Download(PageContext pageContext) throws ServletException {
		initialize(pageContext);
	}

	public Download(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		m_contentDisposition = null;
		m_application = request.getSession().getServletContext();
		m_request = request;
		m_response = response;
	}

	private void initialize(PageContext pageContext) throws ServletException {
		m_contentDisposition = null;
		m_application = pageContext.getServletContext();
		m_request = (HttpServletRequest) pageContext.getRequest();
		m_response = (HttpServletResponse) pageContext.getResponse();
	}

	public void downloadFile(String s) throws ServletException, IOException {
		downloadFile(s, null, null);
	}

	public void downloadFile(String s, String s1) throws ServletException,
			IOException {
		downloadFile(s, s1, null);
	}

	public void downloadFile(String s, String s1, String s2)
			throws ServletException, IOException {
		downloadFile(s, s1, s2, 2048);
	}

	public void downloadFile(String s, String s1, String s2, int i)
			throws ServletException, IOException {
		if (s == null || s.length() == 0) {
			throw new IllegalArgumentException("File '" + s
					+ "' not found (1040).");
		}
		if (isVirtual(s)) {
			s = m_application.getRealPath(s);
		}
		File file = new File(s);
		FileInputStream fileinputstream = new FileInputStream(file);
		long l = file.length();
		int k = 0;
		byte abyte0[] = new byte[i];
		if (s1 == null || s1.length() == 0) {
			m_response.setContentType("application/x-msdownload");
		} else {
			m_response.setContentType(s1);
		}
		m_response.setContentLength((int) l);
		m_contentDisposition = m_contentDisposition != null ? m_contentDisposition
				: "attachment;";
		if (s2 == null) {
			m_response.setHeader("Content-Disposition", m_contentDisposition
					+ " filename=" + encodeString(getFileName(s)));
		} else if (s2.length() == 0) {
			m_response.setHeader("Content-Disposition", m_contentDisposition);
		} else {
			m_response.setHeader("Content-Disposition", m_contentDisposition
					+ " filename=" + encodeString(s2));
		}
		OutputStream outs = m_response.getOutputStream();
		while ((long) k < l) {
			int j = fileinputstream.read(abyte0, 0, i);
			k += j;
			outs.write(abyte0, 0, j);
		}

		outs.flush();
		fileinputstream.close();
		outs.close();
	}

	public void downloadFilesAsZip(String s, String s1, String s2)
			throws FileNotFoundException, IOException {

		if (s == null || s.length() == 0) {
			throw new IllegalArgumentException("File '" + s
					+ "' not found (1040).");
		}

		if (s1 == null || s1.length() == 0) {
			m_response.setContentType("application/x-msdownload");
		} else {
			m_response.setContentType(s1);
		}
		m_contentDisposition = m_contentDisposition != null ? m_contentDisposition
				: "attachment;";
		if (s2 == null) {
			m_response.setHeader("Content-Disposition", m_contentDisposition
					+ " filename=newzip.zip");
		} else if (s2.length() == 0) {
			m_response.setHeader("Content-Disposition", m_contentDisposition);
		} else {
			m_response.setHeader("Content-Disposition", m_contentDisposition
					+ " filename=" + encodeString(s2));
		}

		OutputStream out = m_response.getOutputStream();

		String[] files = StringUtils.split(s, "|");
		File[] file = new File[files.length];
		for (int i = 0; i < files.length; i++) {
			String str = files[i];
			if (isVirtual(str)) {
				str = m_application.getRealPath(str);
			}
			file[i] = new File(str);
			if (!file[i].exists()) {
				throw new IllegalArgumentException("File '" + file[i]
						+ "' not found (1040).");
			}
		}

		//Zipper zipper = new Zipper(file);
		//zipper.setFilename(filenames);
		//zipper.zipOutputStream(out);

		out.flush();
		out.close();
	}

	public void downloadField(ResultSet resultset, String s, String s1,
			String s2) throws ServletException, IOException, SQLException {
		if (resultset == null) {
			throw new IllegalArgumentException(
					"The RecordSet cannot be null (1045).");
		}
		if (s == null || s.length() == 0) {
			throw new IllegalArgumentException(
					"The columnName cannot be null (1050).");
		}

		byte abyte0[] = resultset.getBytes(s);
		if (s1 == null) {
			m_response.setContentType("application/x-msdownload");
		} else if (s1.length() == 0) {
			m_response.setContentType("application/x-msdownload");
		} else {
			m_response.setContentType(s1);
		}
		m_response.setContentLength(abyte0.length);
		if (s2 == null) {
			m_response.setHeader("Content-Disposition", "attachment;");
		} else if (s2.length() == 0) {
			m_response.setHeader("Content-Disposition", "attachment;");
		} else {
			m_response.setHeader("Content-Disposition", "attachment; filename="
					+ encodeString(s2));
		}
		OutputStream outs = m_response.getOutputStream();
		outs.write(abyte0, 0, abyte0.length);
		outs.flush();
		outs.close();
	}

	public static String getFileName(String s) {
		int i = 0;
		i = s.lastIndexOf(47);
		if (i != -1)
			return s.substring(i + 1, s.length());
		i = s.lastIndexOf(92);
		if (i != -1)
			return s.substring(i + 1, s.length());
		else
			return s;
	}

	public void setContentDisposition(String s) {
		m_contentDisposition = s;
	}

	private boolean isVirtual(String s) {
		if (m_application.getRealPath(s) != null) {
			File file = new File(m_application.getRealPath(s));
			return file.exists();
		} else {
			return false;
		}
	}

	private String encodeString(String s) {
		try {
			s = new String(s.getBytes("GBK"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	public void setFilenames(String filenames) {
		this.filenames = filenames;
	}
 
	
	 
	public static void main(String[] args) {
		// Download download = new Download(pageContext);
		// download.downloadFile("test.jsp");
	}
}
