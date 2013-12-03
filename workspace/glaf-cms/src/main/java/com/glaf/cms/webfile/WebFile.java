package com.glaf.cms.webfile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import com.glaf.core.util.RequestUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class WebFile {

	public static final int FILES_PROPERTIES = 3;

	public static final int FOLDERS_PROPERTIES = 4;

	public static final int NEW_FOLDER_FORM = 2;

	public static final int PHYSICAL = 2;

	public static final int UPLOAD_FILE_FORM = 1;

	public static final int VIRTUAL = 1;

	private ResourceBundle colors = null;

	private Locale currentLocale = null;

	private ResourceBundle icons = null;

	private ResourceBundle images = null;

	private ResourceBundle labels = null;

	private Hashtable m_allowedFilesList = null;

	private ServletContext m_application = null;

	private boolean m_authentication = false;

	private ServletConfig m_config = null;

	private String m_contextPath = null;

	private int m_defaultSortField = 0;

	private int m_defaultSortOrder = 0;

	private Hashtable m_deniedFilesList = null;

	private boolean m_denyPhysicalPath = false;

	private String m_fileExtension = null;

	private int m_fileListPercentSize = 0;

	private String m_fileName = null;

	private String m_filePhysicalPath = null;

	private String m_fileRelativePath = null;

	private String m_fileSeparator = null;

	private String m_fileVirtualPath = null;

	private String m_folderName = null;

	private String m_folderPhysicalPath = null;

	private String m_folderRelativePath = null;

	private String m_folderVirtualPath = null;

	private String m_iconsPath = null;

	private String m_imagesPath = null;

	private int m_initialize = 0;

	private int m_inputFileSize = 0;

	private boolean m_isVirtual = false;

	private String m_jsEventHandler = null;

	private long m_maxFileSize = 0;

	private String m_mimeType = null;

	private JspWriter m_outNew = null;

	private PrintWriter m_outOld = null;

	private boolean m_overWrite = false;

	private PageContext m_pageContext = null;

	private Hashtable m_properties = null;

	private long m_quota = 0;

	private boolean m_readOnly = false;

	private HttpServletRequest m_request = null;

	private int m_requestedAction = 0;

	private HttpServletResponse m_response = null;

	private String m_rootPath = null;

	private boolean m_showFolderSize = false;

	private boolean m_showHiddenFiles = false;

	private boolean m_showPathSelect = false;

	private boolean m_showProperties = false;

	private boolean m_showToolbarText = false;

	private String m_strAllowedFilesList = null;

	private String m_strDefaultPath = null;

	private String m_strDeniedFilesList = null;

	private String m_stylesPath = null;

	private String m_title = null;

	private String m_unvisibleFilesList = null;

	private String m_unvisibleFoldersList = null;

	private ResourceBundle mimes = null;

	public WebFile() {
		m_rootPath = "";
		m_requestedAction = 0;
		m_strDefaultPath = "";
		m_readOnly = false;
		m_authentication = false;
		m_denyPhysicalPath = false;
		m_overWrite = true;
		m_showHiddenFiles = false;
		m_showProperties = true;
		m_showToolbarText = true;
		m_showPathSelect = true;
		m_showFolderSize = true;
		m_fileListPercentSize = 55;
		m_defaultSortOrder = 1;
		m_defaultSortField = 1;
		m_title = "Site File Manager";
		m_iconsPath = "";
		m_imagesPath = "";
		m_stylesPath = "";
		m_jsEventHandler = "";
		m_strAllowedFilesList = "";
		m_strDeniedFilesList = "";
		m_allowedFilesList = new Hashtable();
		m_deniedFilesList = new Hashtable();
		m_unvisibleFoldersList = "";
		m_unvisibleFilesList = "";
		m_inputFileSize = 50;
		m_quota = -1L;
		m_fileSeparator = "";
		m_isVirtual = true;
		m_initialize = 0;
		m_contextPath = "";
		m_fileName = "";
		m_fileExtension = "";
		m_mimeType = "";
		m_filePhysicalPath = "";
		m_fileRelativePath = "";
		m_fileVirtualPath = "";
		m_folderName = "";
		m_folderPhysicalPath = "";
		m_folderRelativePath = "";
		m_folderVirtualPath = "";
		currentLocale = new Locale(Locale.getDefault().getLanguage(), Locale
				.getDefault().getCountry());
		// System.out.println(Locale.getDefault().getLanguage());
		// System.out.println(Locale.getDefault().getCountry());
		labels = ResourceBundle.getBundle("com.glaf.cms.webfile.WebFileLabels",
				currentLocale);
		mimes = ResourceBundle.getBundle(
				"com.glaf.cms.webfile.WebFileMIMETypes", currentLocale);
		images = ResourceBundle.getBundle("com.glaf.cms.webfile.WebFileImages",
				currentLocale);
		colors = ResourceBundle.getBundle("com.glaf.cms.webfile.WebFileColors",
				currentLocale);
		icons = ResourceBundle.getBundle("com.glaf.cms.webfile.WebFileIcons",
				currentLocale);
		m_properties = new Hashtable();
		m_contextPath = null;
		m_properties.put("NAME", new Integer(1));
		m_properties.put("SIZE", new Integer(1));
		m_properties.put("LINK", new Integer(1));
		m_properties.put("URL", new Integer(1));
		m_properties.put("TYPE", new Integer(1));
		m_properties.put("LOCATION", new Integer(1));
		m_properties.put("CONTAINS", new Integer(1));
		m_properties.put("MODIFIED", new Integer(1));
		m_properties.put("ATTRIBUTES", new Integer(1));
	}

	private boolean auth() throws IOException {
		String username = "";
		username = m_request.getRemoteUser();
		if (username == null) {
			m_response.setStatus(401);
			responseWriteLn(String.valueOf((new StringBuffer(
					"<font size=2 face=helv,helvetica color="))
					.append(colors.getString("ERR_MSG")).append("><strong>")
					.append(labels.getString("9000"))
					.append("</strong></font><br>")));
			return false;
		} else {
			return true;
		}
	}

	private int compareToIgnoreCase(String str1, String str2) {
		return str1.toUpperCase().toLowerCase()
				.compareTo(str2.toUpperCase().toLowerCase());
	}

	private void copy(String relativePath, String newPath, String name)
			throws IOException {
		String rootPath = "";
		String srcPathName = "";
		String dstPathName = "";
		long size = 0L;
		boolean isFile = false;
		rootPath = getPhysicalPath(m_rootPath);
		srcPathName = String
				.valueOf((new StringBuffer(String.valueOf(rootPath)))
						.append(relativePath)
						.append(System.getProperty("file.separator"))
						.append(name));
		dstPathName = String
				.valueOf((new StringBuffer(String.valueOf(rootPath)))
						.append(newPath)
						.append(System.getProperty("file.separator"))
						.append(name));
		File srcFile = new File(srcPathName);
		File dstFile = new File(dstPathName);
		if (srcFile.exists()) {
			if (srcFile.isFile())
				isFile = true;
			size = srcFile.length();
		}
		if (dstFile.exists())
			dstPathName = String.valueOf((new StringBuffer(String
					.valueOf(rootPath))).append(newPath)
					.append(System.getProperty("file.separator"))
					.append(labels.getString("7004")).append(name));
		if (m_quota >= (long) 0 && size > m_quota) {
			responseWriteLn("<SCRIPT>");
			responseWriteLn(String.valueOf((new StringBuffer("alert('"))
					.append(labels.getString("1400")).append("');")));
			responseWriteLn("</SCRIPT>");
		} else if (isFile)
			try {
				FileHandle.copyFile(srcPathName, dstPathName);
			} catch (Exception e) {
				responseWriteLn("<SCRIPT>");
				responseWriteLn(String.valueOf((new StringBuffer("alert('"))
						.append(labels.getString("1486")).append("');")));
				responseWriteLn("</SCRIPT>");
			}
		else if (dstPathName.lastIndexOf(srcPathName) >= 0) {
			responseWriteLn("<SCRIPT>");
			responseWriteLn(String.valueOf((new StringBuffer("alert('"))
					.append(labels.getString("1480")).append("');")));
			responseWriteLn("</SCRIPT>");
		} else {
			try {
				FileHandle.copyFolder(srcPathName, dstPathName);
			} catch (Exception e) {
				responseWriteLn("<SCRIPT>");
				responseWriteLn(String.valueOf((new StringBuffer("alert('"))
						.append(labels.getString("1485")).append("');")));
				responseWriteLn("</SCRIPT>");
			}
		}
	}

	private void createDirectory(String path, String name) throws IOException {
		String rootPath = "";
		rootPath = getPhysicalPath(m_rootPath);
		File dir = new File(String.valueOf((new StringBuffer(String
				.valueOf(rootPath))).append(path).append(File.separator)
				.append(name)));
		if (!dir.exists()) {
			boolean success = dir.mkdir();
			if (!success) {
				responseWriteLn("<SCRIPT>");
				responseWriteLn(String.valueOf((new StringBuffer("alert('"))
						.append(labels.getString("1405")).append("');")));
				responseWriteLn("parent.FLIST.document.SHOW_LIST.submit();");
				responseWriteLn("</SCRIPT>");
			}
		} else {
			responseWriteLn("<SCRIPT>");
			responseWriteLn(String.valueOf((new StringBuffer("alert('"))
					.append(labels.getString("1405")).append("');")));
			responseWriteLn("parent.FLIST.document.SHOW_LIST.submit();");
			responseWriteLn("</SCRIPT>");
		}
	}

	private void cut(String relativePath, String newPath, String name)
			throws IOException {
		String rootPath = "";
		String srcPathName = "";
		String dstPathName = "";
		rootPath = getPhysicalPath(m_rootPath);
		srcPathName = String
				.valueOf((new StringBuffer(String.valueOf(rootPath)))
						.append(relativePath)
						.append(System.getProperty("file.separator"))
						.append(name));
		dstPathName = String
				.valueOf((new StringBuffer(String.valueOf(rootPath)))
						.append(newPath)
						.append(System.getProperty("file.separator"))
						.append(name));
		File srcFile = new File(srcPathName);

		if (srcFile.isFile() && srcFile.exists())
			try {
				FileHandle.moveFile(srcPathName, dstPathName);
			} catch (Exception e) {
				responseWriteLn("<SCRIPT>");
				responseWriteLn(String.valueOf((new StringBuffer("alert('"))
						.append(labels.getString("1485")).append("');")));
				responseWriteLn("</SCRIPT>");
			}
		else if (srcFile.isDirectory() && srcFile.exists())
			if (dstPathName.indexOf(srcPathName) >= 0) {
				responseWriteLn("<SCRIPT>");
				responseWriteLn(String.valueOf((new StringBuffer("alert('"))
						.append(labels.getString("1480")).append("');")));
				responseWriteLn("</SCRIPT>");
			} else {
				try {
					FileHandle.moveFolder(srcPathName, dstPathName);
				} catch (Exception e) {
					responseWriteLn("<SCRIPT>");
					responseWriteLn(String
							.valueOf((new StringBuffer("alert('")).append(
									labels.getString("1485")).append("');")));
					responseWriteLn("</SCRIPT>");
				}
			}
	}

	private void fileAdmDelete() throws IOException {
		if (m_authentication && !auth())
			return;
		String scriptPath = "";
		String relativePath = "";
		String rootPath = "";
		String sort = "";
		String sortOrder = "";
		String newPath = "";
		relativePath = m_request.getParameter("RELATIVEPATH");
		sort = m_request.getParameter("SORT");
		sortOrder = m_request.getParameter("SORTORDER");
		scriptPath = m_request.getRequestURI();
		rootPath = getPhysicalPath(m_rootPath);
		File fileToDelete = new File(rootPath + relativePath);
		responseWriteLn("<HTML>");
		responseWriteLn("<HEAD>");
		if (!fileToDelete.exists()) {
			responseWriteLn("<SCRIPT>");
			responseWriteLn(String.valueOf((new StringBuffer("alert('"))
					.append(labels.getString("1415")).append("');")));
			responseWriteLn("parent.FLIST.document.SHOW_FICHE.submit();");
			responseWriteLn("</SCRIPT>");
		} else {
			newPath = relativePath.substring(0,
					relativePath.lastIndexOf(File.separator));
			try {
				if (fileToDelete.isFile()) {
					// 不允许删除文件
					// FileHandle.deleteFile(fileToDelete.getAbsolutePath());
				} else {
					// 不允许删除目录
					// FileHandle.deleteFolder(fileToDelete.getAbsolutePath());
				}
			} catch (Exception e) {
				responseWriteLn("<SCRIPT>");
				responseWriteLn(String.valueOf((new StringBuffer("alert('"))
						.append(labels.getString("1415")).append("');")));
				responseWriteLn("parent.FLIST.document.SHOW_FICHE.QUERY.value='FILEADM_PROP';");
				responseWriteLn("parent.FLIST.document.SHOW_FICHE.submit();");
				responseWriteLn("</SCRIPT>");
			}
		}
		responseWriteLn("</HEAD>");
		responseWriteLn(String.valueOf((new StringBuffer("<BODY bgcolor="))
				.append(colors.getString("PROP_BG")).append(
						" onload='document.SHOW_LIST.submit();'>")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"<form method=POST  NAME=SHOW_LIST action='")).append(
				scriptPath).append("?QUERY=FILEADM_LIST' TARGET='FLIST'>")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"<input type=hidden name=RELATIVEPATH value=\"")).append(
				newPath).append("\">")));
		responseWriteLn("<input type=hidden name=NAME value=''>");
		responseWriteLn(String.valueOf((new StringBuffer(
				"<input type=hidden name=SORT value='")).append(sort).append(
				"'>")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"<input type=hidden name=SORTORDER value='")).append(sortOrder)
				.append("'>")));
		responseWriteLn("</form>");
		responseWriteLn("</BODY>");
		responseWriteLn("</HTML>");
	}

	private void fileAdmDownLoad() throws ServletException, IOException {
		if (m_authentication && !auth())
			return;
		String relativePath = "";
		String contentType = "";
		String path = "";
		relativePath = m_request.getParameter("RELATIVEPATH");
		contentType = m_request.getParameter("CONTENTTYPE");
		contentType = contentType != null && !contentType.equals("") ? contentType
				: "content/unknown";
		path = m_rootPath + relativePath;
		FileUpload mySmartUpload = new FileUpload();
		if (m_initialize == 1) {
			mySmartUpload.init(m_config);
			mySmartUpload.service(m_request, m_response);
		}
		if (m_initialize == 2)
			mySmartUpload.initialize(m_config, m_request, m_response);
		if (m_initialize == 3)
			mySmartUpload.initialize(m_pageContext);

		try {
			mySmartUpload.downloadFile(path, contentType);
		} catch (Exception ex) {
			responseWriteLn(ex.getMessage());
		}
	}

	private void fileAdmFrame() throws IOException {
		if (m_authentication && !auth()) {
			return;
		}
		String userAgent = "";
		String scriptPath = "";
		int borderWidth = 6;
		int paramWidth = 2;
		int toolbarWidth = 20;
		int pathWidth = 28;
		int columnsWidth = 24;
		int listHeight = m_fileListPercentSize;
		int propHeight = 100 - listHeight;

		userAgent = m_request.getHeader("User-Agent");
		scriptPath = m_request.getRequestURI();
		if (userAgent.indexOf("Win") > 0)
			if (userAgent.indexOf("MSIE") > 0) {
				borderWidth = 6;
				paramWidth = 2;
				toolbarWidth = 20;
				pathWidth = 28;
				columnsWidth = 24;
			} else {
				borderWidth = 3;
				paramWidth = 2;
				toolbarWidth = 20;
				pathWidth = 34;
				columnsWidth = 27;
			}
		if (userAgent.indexOf("Mac") > 0)
			if (userAgent.indexOf("MSIE") > 0) {
				borderWidth = 6;
				paramWidth = 2;
				toolbarWidth = 20;
				pathWidth = 28;
				columnsWidth = 24;
			} else {
				borderWidth = 3;
				paramWidth = 2;
				toolbarWidth = 20;
				pathWidth = 29;
				columnsWidth = 20;
			}
		if (userAgent.indexOf("Linux") > 0) {
			borderWidth = 0;
			paramWidth = 0;
			toolbarWidth = 20;
			pathWidth = 28;
			columnsWidth = 20;
		}
		if (m_readOnly)
			toolbarWidth = 0;
		if (!m_showPathSelect)
			pathWidth = 0;
		responseWriteLn("<HTML>");
		responseWriteLn("<HEAD>");
		responseWriteLn("<TITLE>");
		responseWriteLn(m_title);
		responseWriteLn("</TITLE>");
		responseWriteLn("</HEAD>");
		responseWriteLn(String.valueOf((new StringBuffer("<FRAMESET ROWS='"))
				.append(toolbarWidth).append(",*,").append(borderWidth)
				.append("' FRAMESPACING=0 FRAMEBORDER=NO BORDER=0>")));
		responseWriteLn(String
				.valueOf((new StringBuffer("<FRAME NAME='FTOOLBAR'  SRC='"))
						.append(getImages("imageNull"))
						.append("' MARGINHEIGHT=0 MARGINWIDTH=0 NORESIZE SCROLLING=NO>")));
		responseWriteLn(String.valueOf((new StringBuffer("<FRAMESET COLS='"))
				.append(borderWidth).append(",*,").append(borderWidth)
				.append("' FRAMESPACING=0 FRAMEBORDER=NO BORDER=0>")));
		responseWriteLn(String
				.valueOf((new StringBuffer("<FRAME NAME='RBORDER'  SRC='"))
						.append(getImages("imageNull"))
						.append("' MARGINHEIGHT=0 MARGINWIDTH=0 NORESIZE SCROLLING=NO>")));
		responseWriteLn(String.valueOf((new StringBuffer("<FRAMESET ROWS='"))
				.append(paramWidth).append(",").append(pathWidth).append(",")
				.append(columnsWidth).append(", ").append(listHeight)
				.append("%, ").append(borderWidth).append(", ")
				.append(propHeight)
				.append("%' FRAMESPACING=0 FRAMEBORDER=NO BORDER=0>")));
		responseWriteLn(String
				.valueOf((new StringBuffer("<FRAME NAME='FPARAM'    SRC='"))
						.append(scriptPath)
						.append("?QUERY=FILEADM_PARAM&RELATIVEPATH=' MARGINHEIGHT=0 MARGINWIDTH=0 NORESIZE SCROLLING=NO>")));
		responseWriteLn(String
				.valueOf((new StringBuffer("<FRAME NAME='FPATH'     SRC='"))
						.append(getImages("imageNull"))
						.append("' MARGINHEIGHT=0 MARGINWIDTH=0 NORESIZE SCROLLING=NO>")));
		responseWriteLn(String
				.valueOf((new StringBuffer("<FRAME NAME='FCOLUMNS'  SRC='"))
						.append(getImages("imageNull"))
						.append("' MARGINHEIGHT=0 MARGINWIDTH=0 NORESIZE SCROLLING=NO>")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"<FRAME NAME='FLIST'     SRC='"))
				.append(getImages("imageNull")).append(
						"' MARGINHEIGHT=0 MARGINWIDTH=0 NORESIZE>")));
		responseWriteLn(String
				.valueOf((new StringBuffer("<FRAME NAME='MBORDER'   SRC='"))
						.append(getImages("imageNull"))
						.append("' MARGINHEIGHT=0 MARGINWIDTH=0 NORESIZE SCROLLING=NO>")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"<FRAME NAME='FPROP'     SRC='"))
				.append(getImages("imageNull")).append(
						"' MARGINHEIGHT=0 MARGINWIDTH=0 NORESIZE>")));
		responseWriteLn("</FRAMESET>");
		responseWriteLn(String
				.valueOf((new StringBuffer("<FRAME NAME='LBORDER' SRC='"))
						.append(getImages("imageNull"))
						.append("'  SCROLLING=NO MARGINHEIGHT=0 MARGINWIDTH=0 NORESIZE FRAMEBORDER=NO>")));
		responseWriteLn("</FRAMESET>");
		responseWriteLn(String
				.valueOf((new StringBuffer("<FRAME NAME='BBORDER' SRC='"))
						.append(getImages("imageNull"))
						.append("'  SCROLLING=NO MARGINHEIGHT=0 MARGINWIDTH=0 NORESIZE FRAMEBORDER=NO>")));
		responseWriteLn("</FRAMESET>");
		responseWriteLn("</HTML>");
	}

	private void fileAdmList() throws IOException {
		if (m_authentication && !auth())
			return;
		String relativePath = "";
		String userAgent = "";
		String scriptPath = "";
		String rootPath = "";
		String name = "";
		String sort = "";
		String sortOrder = "";
		String path = "";
		String currentPath = "";
		String refresh = "";
		String extension = "";
		String tmp = "";

		int i = 0;
		int j = 0;
		int k = 0;
		int nbFiles = 0;
		int nbFolders = 0;

		relativePath = m_request.getParameter("RELATIVEPATH");
		sort = m_request.getParameter("SORT");
		sortOrder = m_request.getParameter("SORTORDER");
		name = m_request.getParameter("NAME");
		name = RequestUtils.decodeString(name);
		refresh = m_request.getParameter("REFRESHFRAMES");
		userAgent = m_request.getHeader("User-Agent");
		scriptPath = m_request.getRequestURI();
		if (sort == null)
			sort = String.valueOf(m_defaultSortField);
		if (sortOrder == null)
			sortOrder = String.valueOf(m_defaultSortOrder);
		rootPath = getPhysicalPath(m_rootPath);
		currentPath = getPhysicalPath(m_rootPath + relativePath);
		if (currentPath == null) {
			currentPath = rootPath;
			relativePath = "";
			refresh = "OPEN";
		}
		File fpath = new File(currentPath);
		if (!fpath.exists()) {
			currentPath = m_rootPath;
			fpath = new File(currentPath);
			refresh = "OPEN";
		}
		File arrayElements[] = filterListFiles(fpath, m_unvisibleFoldersList,
				m_unvisibleFilesList);
		for (i = 0; i < arrayElements.length; i++)
			if (arrayElements[i].isDirectory())
				nbFolders++;
			else
				nbFiles++;

		File arrayFolders[] = new File[nbFolders];
		File arrayFiles[] = new File[nbFiles];
		for (i = 0; i < arrayElements.length; i++) {
			if (arrayElements[i].isDirectory()) {
				j = 0;
				try {
					if (sort.equals("1"))
						if (sortOrder.equals("0"))
							for (; compareToIgnoreCase(
									arrayFolders[j].getName(),
									arrayElements[i].getName()) > 0; j++)
								;
						else
							for (; compareToIgnoreCase(
									arrayFolders[j].getName(),
									arrayElements[i].getName()) < 0; j++)
								;
					if (sort.equals("2"))
						if (sortOrder.equals("0"))
							for (; arrayFolders[j].length() > arrayElements[i]
									.length(); j++)
								;
						else
							for (; arrayFolders[j].length() < arrayElements[i]
									.length(); j++)
								;
					if (sort.equals("3"))
						if (sortOrder.equals("0"))
							for (; compareToIgnoreCase(
									getExtension(arrayFolders[j].getName()),
									getExtension(arrayElements[i].getName())) > 0; j++)
								;
						else
							for (; compareToIgnoreCase(
									getExtension(arrayFolders[j].getName()),
									getExtension(arrayElements[i].getName())) < 0; j++)
								;
					if (sort.equals("4"))
						if (sortOrder.equals("0"))
							for (; arrayFolders[j].lastModified() > arrayElements[i]
									.lastModified(); j++)
								;
						else
							for (; arrayFolders[j].lastModified() < arrayElements[i]
									.lastModified(); j++)
								;
				} catch (Exception exception) {
				}
				for (int m = nbFolders; m > j; m--)
					try {
						arrayFolders[m] = arrayFolders[m - 1];
					} catch (Exception exception3) {
					}

				arrayFolders[j] = arrayElements[i];
				continue;
			}
			k = 0;
			try {
				if (sort.equals("1"))
					if (sortOrder.equals("0"))
						for (; compareToIgnoreCase(arrayFiles[k].getName(),
								arrayElements[i].getName()) > 0; k++)
							;
					else
						for (; compareToIgnoreCase(arrayFiles[k].getName(),
								arrayElements[i].getName()) < 0; k++)
							;
				if (sort.equals("2"))
					if (sortOrder.equals("0"))
						for (; arrayFiles[k].length() > arrayElements[i]
								.length(); k++)
							;
					else
						for (; arrayFiles[k].length() < arrayElements[i]
								.length(); k++)
							;
				if (sort.equals("3"))
					if (sortOrder.equals("0"))
						for (; compareToIgnoreCase(
								getExtension(arrayFiles[k].getName()),
								getExtension(arrayElements[i].getName())) > 0; k++)
							;
					else
						for (; compareToIgnoreCase(
								getExtension(arrayFiles[k].getName()),
								getExtension(arrayElements[i].getName())) < 0; k++)
							;
				if (sort.equals("4"))
					if (sortOrder.equals("0"))
						for (; arrayFiles[k].lastModified() > arrayElements[i]
								.lastModified(); k++)
							;
					else
						for (; arrayFiles[k].lastModified() < arrayElements[i]
								.lastModified(); k++)
							;
			} catch (Exception exception1) {
			}
			for (int n = nbFiles; n > k; n--)
				try {
					arrayFiles[n] = arrayFiles[n - 1];
				} catch (Exception exception4) {
				}

			arrayFiles[k] = arrayElements[i];
		}

		responseWriteLn("<HTML>");
		responseWriteLn("<HEAD>");
		responseWriteLn(String.valueOf((new StringBuffer(
				"<link rel=\"stylesheet\" type=\"text/css\" href=\"")).append(
				getStylesPath()).append("\"/>")));
		responseWriteLn("<STYLE>");
		responseWriteLn("   A {text-decoration:none};");
		responseWriteLn(String.valueOf((new StringBuffer(
				"   .selected {text-decoration:none;color:"))
				.append(colors.getString("LIST_SUBJECT_SELECTED"))
				.append(";background-color:")
				.append(colors.getString("LIST_SUBJECT_SELECTED_BG"))
				.append(";font-weight:normal;};")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"   .unselected {text-decoration:none;color:")).append(
				colors.getString("LIST_SUBJECT_FONT")).append(
				";font-weight:normal;};")));
		responseWriteLn("</STYLE>");
		responseWriteLn("<SCRIPT LANGUAGE='JavaScript'>");
		responseWriteLn("var lastImgRed=''");
		responseWriteLn("function HighLight(ITEM){ ");
		responseWriteLn("   if (!document.all) {return;}");
		responseWriteLn("   if (ITEM>=0) {");
		responseWriteLn("       if (lastImgRed!=''){eval('div'+lastImgRed+'.className=\"unselected\"')}");
		responseWriteLn("       lastImgRed=ITEM;");
		responseWriteLn("       eval('div'+ITEM+'.className=\"selected\"');");
		responseWriteLn("   } ");
		responseWriteLn("} ");
		responseWriteLn("function displayProperty (RELATIVEPATH, NAME) { ");
		responseWriteLn("   parent.FPARAM.document.Info.RELATIVEPATH.value=RELATIVEPATH;");
		responseWriteLn("   parent.FPARAM.refreshToolbar('ONE'); ");
		responseWriteLn("   document.SHOW_FICHE.RELATIVEPATH.value=RELATIVEPATH; ");
		responseWriteLn("   document.SHOW_FICHE.NAME.value=NAME; ");
		responseWriteLn("   document.SHOW_FICHE.submit(); ");
		responseWriteLn("} ");
		responseWriteLn("function openFolder(RELATIVEPATH) { ");
		responseWriteLn("   document.SHOW_LIST.REFRESHFRAMES.value = 'OPEN';");
		responseWriteLn("   document.SHOW_LIST.RELATIVEPATH.value = RELATIVEPATH;");
		responseWriteLn("   document.SHOW_LIST.submit();");
		responseWriteLn("} ");
		responseWriteLn("function init(RELATIVEPATH) { ");
		responseWriteLn(String.valueOf((new StringBuffer(
				"   if(RELATIVEPATH.length==0) {RELATIVEPATH='")).append(
				m_fileSeparator).append("'};")));
		responseWriteLn("   if(parent.FPARAM.document.Info.CONTROL.value!='') parent.FPARAM.displayToolbar('');");
		responseWriteLn("   parent.FPARAM.document.Info.STATE.value = '';");
		if (m_showPathSelect)
			if (refresh == "OPEN" || refresh == null) {
				responseWriteLn("   document.SHOW_SELECTTREE.submit();");
			} else {
				responseWriteLn(" var mylong=parent.FPATH.document.LIST_SELECT.RELATIVEPATH.length;");
				responseWriteLn(" var n=0;");
				responseWriteLn(" var mytext = parent.FPATH.document.LIST_SELECT.RELATIVEPATH.options[0].text;");
				responseWriteLn("  while((n<mylong-1)&&(mytext.toUpperCase())!=(RELATIVEPATH.toUpperCase())) {");
				responseWriteLn("   n=n+1;");
				responseWriteLn("   mytext = parent.FPATH.document.LIST_SELECT.RELATIVEPATH.options[n].text;");
				responseWriteLn("  }");
				responseWriteLn("parent.FPATH.document.LIST_SELECT.RELATIVEPATH.options[n].selected=true;");
			}
		responseWriteLn("}");
		responseWriteLn("</SCRIPT>");
		responseWriteLn("</head>");
		responseWriteLn(String.valueOf((new StringBuffer("<body bgcolor="))
				.append(colors.getString("LIST_BG")).append(" link=")
				.append(colors.getString("LIST_LINK_ENABLE")).append(" vlink=")
				.append(colors.getString("LIST_LINK_ENABLE"))
				.append(" onload=\"init('").append(m_fileSeparator)
				.append(pathEncode(relativePath)).append("');\">")));
		responseWriteLn("<table class=listFrame border=0 cellpadding=1 cellspacing=0 width='100%'>");
		int iconWidth = 3;
		int nameWidth = 30;
		int sizeWidth = 12;
		int typeWidth = 30;
		int dateWidth = 25;
		if (!currentPath.equals(rootPath)) {
			if (relativePath.indexOf("/") >= 0) {
				for (i = 0; i < relativePath.length(); i++)
					if (relativePath.charAt(i) == '\'')
						tmp = String.valueOf(tmp).concat("\\'");
					else
						tmp = tmp + relativePath.charAt(i);

				tmp = tmp.substring(0, tmp.lastIndexOf("/"));
			} else {
				tmp = m_fileSeparator
						+ pathEncode(relativePath.substring(0,
								relativePath.lastIndexOf("\\")));
			}
			if (tmp.equals("\\\\"))
				tmp = "";
			responseWriteLn("<tr>");
			responseWriteLn(String.valueOf((new StringBuffer(
					"<td align=center width='")).append(iconWidth)
					.append("%'>")));
			if (userAgent.indexOf("Mac") > 0 && userAgent.indexOf("MSIE") > 0)
				responseWriteLn(String.valueOf((new StringBuffer(
						"<a onclick=\"javascript:{openFolder('")).append(tmp)
						.append("');}\">")));
			else
				responseWriteLn(String.valueOf((new StringBuffer(
						"<a href=\"javascript:{openFolder('")).append(tmp)
						.append("');}\">")));
			responseWriteLn(String.valueOf((new StringBuffer("<IMG SRC='"))
					.append(getImages("iconBackFolder")).append(
							"' WIDTH=18 HEIGHT=14 border=0>")));
			responseWriteLn("</a>");
			responseWriteLn("</td>");
			responseWriteLn(String.valueOf((new StringBuffer(
					"<td align=left width='")).append(nameWidth).append("%'>")));
			responseWriteLn("");
			if (userAgent.indexOf("Mac") > 0 && userAgent.indexOf("MSIE") > 0)
				responseWriteLn(String.valueOf((new StringBuffer(
						"<a onclick=\"javascript:{openFolder('")).append(tmp)
						.append("');}\">")));
			else
				responseWriteLn(String.valueOf((new StringBuffer(
						"<a href=\"javascript:{openFolder('")).append(tmp)
						.append("');}\">")));
			responseWriteLn(String.valueOf((new StringBuffer(
					"<span ID=div0 CLASS=unselected>&nbsp;")).append(
					labels.getString("7001")).append("</span>")));
			responseWriteLn("</a>");
			responseWriteLn("</td>");
			responseWriteLn(String.valueOf((new StringBuffer(
					"<td align=right width='")).append(sizeWidth).append(
					"%'>&nbsp;</td>")));
			responseWriteLn(String.valueOf((new StringBuffer(
					"<td align=left width='")).append(typeWidth).append(
					"%'>&nbsp;</td>")));
			responseWriteLn(String.valueOf((new StringBuffer(
					"<td align=left width='")).append(dateWidth).append(
					"%'>&nbsp;</td>")));
			responseWriteLn("</tr>");
		}
		for (i = 0; i < nbFolders; i++) {
			tmp = arrayFolders[i].getAbsolutePath();
			int pos = tmp.lastIndexOf(System.getProperty("file.separator")) + 1;
			name = tmp.substring(pos, tmp.length());
			if (name != null)
				path = m_fileSeparator
						+ pathEncode(String.valueOf((new StringBuffer(String
								.valueOf(relativePath))).append(
								System.getProperty("file.separator")).append(
								name)));
			else
				path = m_fileSeparator
						+ pathEncode(System.getProperty("file.separator")
								+ relativePath);
			responseWriteLn("<tr>");
			responseWriteLn(String.valueOf((new StringBuffer(
					"<td align=center width='")).append(iconWidth)
					.append("%'>")));
			if (userAgent.indexOf("Mac") > 0 && userAgent.indexOf("MSIE") > 0)
				responseWriteLn(String.valueOf((new StringBuffer(
						"<a onclick=\"javascript:{openFolder('")).append(path)
						.append("');}\">")));
			else
				responseWriteLn(String.valueOf((new StringBuffer(
						"<a href=\"javascript:{openFolder('")).append(path)
						.append("');}\">")));
			responseWriteLn(String.valueOf((new StringBuffer("<IMG alt='"))
					.append(labels.getString("7003")).append("' SRC='")
					.append(getImages("iconFolder")).append("' border=0>")));
			responseWriteLn("</a>");
			responseWriteLn("</td>");
			responseWriteLn(String.valueOf((new StringBuffer(
					"<td align=left width='")).append(nameWidth).append("%'>")));
			responseWriteLn("");
			if (userAgent.indexOf("Mac") > 0 && userAgent.indexOf("MSIE") > 0)
				responseWriteLn(String.valueOf((new StringBuffer(
						"<a onclick=\"javascript:{displayProperty('"))
						.append(path).append("','").append(name)
						.append("');HighLight(").append(i++).append(");}\">")));
			else
				responseWriteLn(String
						.valueOf((new StringBuffer(
								"<a href=\"javascript:{displayProperty('"))
								.append(path).append("','").append(name)
								.append("');HighLight(").append(i + 1)
								.append(");}\">")));
			responseWriteLn(String.valueOf((new StringBuffer("<span ID=div"))
					.append(i + 1).append(" CLASS=unselected>&nbsp;")
					.append(name).append("</span>")));
			responseWriteLn("</a>");
			responseWriteLn("</td>");
			responseWriteLn(String.valueOf((new StringBuffer(
					"<td align=right width='")).append(sizeWidth).append("%'>")
					.append("&nbsp;</td>")));
			responseWriteLn(String.valueOf((new StringBuffer(
					"<td align=left width='")).append(typeWidth).append("%'>")
					.append("&nbsp;").append(labels.getString("5001"))
					.append("</td>")));
			Date fileDate = new Date(arrayFolders[i].lastModified());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.getDefault());
			tmp = sdf.format(fileDate);
			responseWriteLn(String.valueOf((new StringBuffer(
					"<td align=left width='")).append(dateWidth).append("%'>")
					.append("&nbsp;").append(tmp).append("</td>")));
			responseWriteLn("</tr>");
		}

		for (i = 0; i < nbFiles; i++) {
			tmp = arrayFiles[i].getAbsolutePath();
			int pos = tmp.lastIndexOf(System.getProperty("file.separator")) + 1;
			name = tmp.substring(pos, tmp.length());
			extension = getExtension(arrayFiles[i].getName());
			extension = extension != null ? extension : "";
			if (name != null)
				path = pathEncode(String.valueOf((new StringBuffer(String
						.valueOf(System.getProperty("file.separator"))))
						.append(relativePath)
						.append(System.getProperty("file.separator"))
						.append(name)));
			else
				path = pathEncode(System.getProperty("file.separator")
						+ relativePath);
			responseWriteLn("<tr>");
			responseWriteLn(String.valueOf((new StringBuffer(
					"<td align=center width='")).append(iconWidth)
					.append("%'>")));
			if (userAgent.indexOf("Mac") > 0 && userAgent.indexOf("MSIE") > 0)
				responseWriteLn(String.valueOf((new StringBuffer(
						"<a onclick=\"javascript:{displayProperty('"))
						.append(path).append("','');HighLight(")
						.append(nbFolders + k + i).append(");}\">")));
			else
				responseWriteLn(String.valueOf((new StringBuffer(
						"<a href=\"javascript:{displayProperty('"))
						.append(path).append("','');HighLight(")
						.append(nbFolders + k + i).append(");}\">")));
			try {
				responseWriteLn(String.valueOf((new StringBuffer("<IMG SRC='"))
						.append(getIcons(extension)).append(
								"' WIDTH=16 HEIGHT=16 BORDER=0>")));
			} catch (MissingResourceException e) {
				responseWriteLn(String.valueOf((new StringBuffer("<IMG SRC='"))
						.append(getIcons("")).append(
								"' WIDTH=16 HEIGHT=16 BORDER=0>")));
			} catch (Exception e) {
				responseWriteLn("<IMG SRC='' WIDTH=16 HEIGHT=16 BORDER=0>");
			}
			responseWriteLn("</a>");
			responseWriteLn("</td>");
			responseWriteLn(String.valueOf((new StringBuffer(
					"<td align=left width='")).append(nameWidth).append("%'>")));
			responseWriteLn("");
			if (userAgent.indexOf("Mac") > 0 && userAgent.indexOf("MSIE") > 0)
				responseWriteLn(String.valueOf((new StringBuffer(
						"<a onclick=\"javascript:{displayProperty('"))
						.append(path).append("','").append(name)
						.append("');HighLight(").append(nbFolders + k + i)
						.append(");}\">")));
			else
				responseWriteLn(String.valueOf((new StringBuffer(
						"<a href=\"javascript:{displayProperty('"))
						.append(path).append("','").append(name)
						.append("');HighLight(").append(nbFolders + k + i)
						.append(");}\">")));
			responseWriteLn(String.valueOf((new StringBuffer("<span ID=div"))
					.append(nbFolders + k + i)
					.append(" CLASS=unselected>&nbsp;").append(name)
					.append("</span>")));
			responseWriteLn("</a>");
			responseWriteLn("</td>");
			long size = arrayFiles[i].length();
			long sizeKo = size / (long) 1024;
			if (sizeKo > (long) 0)
				responseWriteLn(String
						.valueOf((new StringBuffer("<td align=right width='"))
								.append(sizeWidth).append("%'>").append(sizeKo)
								.append(" ").append(labels.getString("7002"))
								.append("&nbsp;</td>")));
			else
				responseWriteLn(String
						.valueOf((new StringBuffer("<td align=right width='"))
								.append(sizeWidth).append("%'>").append("1 ")
								.append(labels.getString("7002"))
								.append("&nbsp;</td>")));
			try {
				extension = helperLeft(mimes.getString(extension), 32);
			} catch (MissingResourceException e) {
				extension = extension.toUpperCase();
				extension = " ".concat(String.valueOf(extension));
				extension = labels.getString("5000") + extension;
			} catch (Exception exception2) {
			}
			responseWriteLn(String.valueOf((new StringBuffer(
					"<td align=left width='")).append(typeWidth).append("%'>")
					.append("&nbsp;").append(extension).append("</td>")));
			Date fileDate = new Date(arrayFiles[i].lastModified());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.getDefault());
			tmp = sdf.format(fileDate);
			responseWriteLn(String.valueOf((new StringBuffer(
					"<td align=left width='")).append(dateWidth).append("%'>")
					.append("&nbsp;").append(tmp).append("</td>")));
			responseWriteLn("</tr>");
		}

		responseWriteLn("</table>");
		responseWriteLn(String.valueOf((new StringBuffer(
				"<form method=POST  NAME=SHOW_LIST action='")).append(
				scriptPath).append("?QUERY=FILEADM_LIST' target='FLIST'>")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"<input type=hidden name=SORT value='")).append(sort).append(
				"'>")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"<input type=hidden name=SORTORDER value='")).append(sortOrder)
				.append("'>")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"<input type=hidden name=RELATIVEPATH value=\"")).append(
				relativePath).append("\">")));
		responseWriteLn("<input type=hidden name=REFRESHFRAMES value=''>");
		responseWriteLn("</form>");
		responseWriteLn(String.valueOf((new StringBuffer(
				"<form method=POST  NAME=SHOW_FICHE action='")).append(
				scriptPath).append("' target='FPROP'>")));
		responseWriteLn("<input type=hidden name=QUERY VALUE=FILEADM_PROP>");
		responseWriteLn("<input type=hidden name=RELATIVEPATH VALUE=\"\">");
		responseWriteLn(String.valueOf((new StringBuffer(
				"<input type=hidden name=SORT value='")).append(sort).append(
				"'>")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"<input type=hidden name=SORTORDER value='")).append(sortOrder)
				.append("'>")));
		responseWriteLn("<input type=hidden name=NAME VALUE=''>");
		responseWriteLn("</form>");
		responseWriteLn(String.valueOf((new StringBuffer(
				"<form method=POST  NAME='SHOW_SELECTTREE' action='")).append(
				scriptPath).append("' target='FPATH'>")));
		responseWriteLn("<input type=hidden name=QUERY VALUE=FILEADM_SELECTTREE>");
		responseWriteLn(String.valueOf((new StringBuffer(
				"<input type=hidden name=RELATIVEPATH VALUE=\"")).append(
				relativePath).append("\">")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"<input type=hidden name=NBFILES VALUE='")).append(nbFiles)
				.append("'>")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"<input type=hidden name=NBFOLDERS VALUE='")).append(nbFolders)
				.append("'>")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"<input type=hidden name=SORT value='")).append(sort).append(
				"'>")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"<input type=hidden name=SORTORDER value='")).append(sortOrder)
				.append("'>")));
		responseWriteLn("<input type=hidden name=NAME VALUE=''>");
		responseWriteLn("</form>");
		if (m_request.getParameter("ACTIONTRI") == null) {
			responseWriteLn("<SCRIPT>");
			responseWriteLn(String.valueOf((new StringBuffer(
					"parent.FPARAM.displayHeader(")).append(nbFiles)
					.append(", ").append(nbFolders).append(")")));
			responseWriteLn("</SCRIPT>");
		}
		responseWriteLn("</BODY>");
		responseWriteLn("</HTML>");
	}

	private void fileAdmNewFile() throws FileUploadException, ServletException,
			IOException {
		if (m_authentication && !auth())
			return;
		String scriptPath = "";
		String relativePath = "";
		String rootPath = "";
		String path = "";
		String virtualPath = "";
		String save = "";
		String name = "";
		long size = 0L;

		scriptPath = m_request.getRequestURI();
		save = getQueryString("SAVE");
		save = save != null ? save : "";
		responseWriteLn("<HTML>");
		responseWriteLn("<HEAD>");
		responseWriteLn("<SCRIPT>");
		responseWriteLn("function CheckInput() {");
		responseWriteLn("   if(document.UPLOADFILE.FILE.length==0) {");
		responseWriteLn(String.valueOf((new StringBuffer("       alert('"))
				.append(labels.getString("1455")).append("');")));
		responseWriteLn("       document.UPLOADFILE.FILE.focus();");
		responseWriteLn("       return false;");
		responseWriteLn("   }");
		responseWriteLn("   FName=document.UPLOADFILE.FILE.value;");
		responseWriteLn("   if (navigator.appVersion.indexOf('Win')>0) {");
		responseWriteLn("   FName=FName.substring(FName.lastIndexOf('\\\\')+1,FName.length);");
		responseWriteLn("   }else{");
		responseWriteLn("   FName=FName.substring(FName.lastIndexOf('/')+1,FName.length);");
		responseWriteLn("   }");
		responseWriteLn("   if ((navigator.appVersion.lastIndexOf('Win'))!=-1) {");
		responseWriteLn("       if((FName.indexOf('\\\\')>=0) || (FName.indexOf('/')>=0) || (FName.indexOf(':')>=0) || (FName.indexOf('*')>=0) || (FName.indexOf('?')>=0) || (FName.indexOf('\"')>=0)|| (FName.indexOf('<')>=0) || (FName.indexOf('>')>=0) || (FName.indexOf('|')>=0)) {");
		responseWriteLn(String.valueOf((new StringBuffer("           alert('"))
				.append(labels.getString("1465")).append("');")));
		responseWriteLn("           document.UPLOADFILE.FILE.focus();");
		responseWriteLn("           return false;");
		responseWriteLn("       };");
		responseWriteLn("   }else{");
		responseWriteLn("       if((FName.indexOf('\\\\')>=0) || (FName.indexOf(':')>=0) || (FName.indexOf('*')>=0) || (FName.indexOf('?')>=0) || (FName.indexOf('\"')>=0)|| (FName.indexOf('<')>=0) || (FName.indexOf('>')>=0) || (FName.indexOf('|')>=0)) {");
		responseWriteLn(String.valueOf((new StringBuffer("           alert('"))
				.append(labels.getString("1465")).append("');")));
		responseWriteLn("           document.UPLOADFILE.FILE.focus();");
		responseWriteLn("           return false;");
		responseWriteLn("       }");
		responseWriteLn("   }");
		responseWriteLn("   MyStr=document.UPLOADFILE.FILE.value;");
		responseWriteLn("   i=0;");
		responseWriteLn("   while (MyStr.charAt(i)==' ') { i ++ }");
		responseWriteLn("   if (i==MyStr.length) {");
		responseWriteLn(String.valueOf((new StringBuffer("       alert('"))
				.append(labels.getString("1460")).append("');")));
		responseWriteLn("       document.UPLOADFILE.FILE.focus();");
		responseWriteLn("       return false;");
		responseWriteLn("       }");
		responseWriteLn("   return true;");
		responseWriteLn("}");
		responseWriteLn("function ShowWaitPost(){");
		responseWriteLn("   top.waitWin=null;");
		responseWriteLn("   var strAgent=navigator.userAgent;");
		responseWriteLn("   var xCoord;var yCoord;");
		responseWriteLn("   if (strAgent.indexOf('Mozilla/3')==-1 && strAgent.indexOf('MSIE 3')==-1){");
		responseWriteLn("       xCoord=screen.width/2 - 150 ;");
		responseWriteLn("       yCoord=(screen.height/2) - 30;");
		responseWriteLn("   }");
		responseWriteLn("   top.waitWin=open('','waitWin','top='+ yCoord +',left=' + xCoord +',directories=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no,width=300,height=60');");
		responseWriteLn("   top.waitWin.document.open();");
		responseWriteLn(String.valueOf((new StringBuffer(
				"   top.waitWin.document.write('<html><body bgcolor=")).append(
				colors.getString("POPUP_BG")).append("><center>');")));
		responseWriteLn(String
				.valueOf((new StringBuffer(
						"   top.waitWin.document.write('<table cellpadding=10 cellspacing=0 border=0><tr><td><img src="))
						.append(getImages("imageWait")).append("></td>');")));
		responseWriteLn(String
				.valueOf((new StringBuffer(
						"   top.waitWin.document.write('<td><font face=helv,helvetica size=2 color="))
						.append(colors.getString("POPUP_FONT")).append(">")
						.append(labels.getString("8017"))
						.append("</font></td></tr></table>');")));
		responseWriteLn("   top.waitWin.document.write('</center></body></html>');");
		responseWriteLn("   top.waitWin.document.close();");
		responseWriteLn("}");
		responseWriteLn("function WaitPost(){");
		responseWriteLn("   top.waitWin.close(); ");
		responseWriteLn("}");
		responseWriteLn("function refreshList() { ");
		responseWriteLn("   document.SHOW_LIST.submit();");
		responseWriteLn("}");
		responseWriteLn("function LoadHeader(strMode) { ");
		responseWriteLn("   parent.FPARAM.document.Info.STATE.value = strMode;");
		responseWriteLn("   parent.FPARAM.displayToolbar(strMode);");
		responseWriteLn("}");
		responseWriteLn("function JSError(strErr) { ");
		responseWriteLn("   WaitPost();");
		responseWriteLn("   alert(strErr);");
		responseWriteLn("   parent.FLIST.document.SHOW_LIST.submit();");
		responseWriteLn("}");
		responseWriteLn("</SCRIPT>");
		responseWriteLn("</HEAD>");
		if (save.equals("YES")) {
			FileUpload myUpload = new FileUpload();
			rootPath = getPhysicalPath(m_rootPath);
			try {
				if (m_initialize == 1) {
					myUpload.init(m_config);
					myUpload.service(m_request, m_response);
				}
				if (m_initialize == 2)
					myUpload.initialize(m_config, m_request, m_response);
				if (m_initialize == 3)
					myUpload.initialize(m_pageContext);

				myUpload.setDeniedFilesList(m_strDeniedFilesList);
				myUpload.setAllowedFilesList(m_strAllowedFilesList);
				myUpload.setMaxFileSize(m_maxFileSize);
				myUpload.upload();
			} catch (Exception e) {
				responseWriteLn(String.valueOf((new StringBuffer(
						"<BODY bgcolor=")).append(colors.getString("PROP_BG"))
						.append(" onload=\" JSError('")
						.append(labels.getString("1005")).append(" :\\n")
						.append(e.getMessage()).append("');\">")));
				responseWriteLn("</BODY></HTML>");
				return;
			}
			File myRoot[] = filterListFiles(new File(rootPath), "", "");
			size = getFolderSize(myRoot)
					+ (long) myUpload.getFiles().getFile(0).getSize();
			name = myUpload.getFiles().getFile(0).getFileName();
			relativePath = myUpload.getRequest().getParameter("PATH");
			path = String.valueOf((new StringBuffer(String.valueOf(rootPath)))
					.append(relativePath)
					.append(System.getProperty("file.separator")).append(name));
			if (m_quota >= (long) 0 && size > m_quota) {
				responseWriteLn(String.valueOf((new StringBuffer(
						"<BODY bgcolor=")).append(colors.getString("PROP_BG"))
						.append(" onload=\" JSError('")
						.append(labels.getString("1400")).append("');\">")));
				responseWriteLn("</BODY></HTML>");
			} else {
				String ow = "";
				ow = myUpload.getRequest().getParameter("OverWrite");
				ow = ow == null ? "" : ow;
				if (!ow.equals("ok") && FileHandle.fileExists(path)) {
					responseWriteLn(String.valueOf((new StringBuffer(
							"<BODY bgcolor="))
							.append(colors.getString("PROP_BG"))
							.append(" onload=\" JSError('")
							.append(labels.getString("1445")).append("');\">")));
					responseWriteLn("</BODY></HTML>");
				} else {
					if (m_overWrite && FileHandle.fileExists(path)) {
						// 不允许删除文件
						// FileHandle.deleteFile(path);
					}
					try {
						if (m_isVirtual) {
							if (StringUtils.isEmpty(m_rootPath)
									|| StringUtils.equals(m_rootPath, "/")) {
								m_rootPath = rootPath;
							}
							virtualPath = String.valueOf((new StringBuffer(
									String.valueOf(m_rootPath)))
									.append(relativePath)
									.append("/")
									.append(myUpload.getFiles().getFile(0)
											.getFileName()));
							virtualPath = virtualPath.replace('\\', '/');
							if (!StringUtils.endsWithIgnoreCase(virtualPath,
									"web.xml")) {
								myUpload.getFiles().getFile(0)
										.saveAs(virtualPath);
							}
						} else {
							if (!StringUtils
									.endsWithIgnoreCase(path, "web.xml")) {
								myUpload.getFiles().getFile(0).saveAs(path);
							}
						}
					} catch (Exception e) {
						responseWriteLn(String.valueOf((new StringBuffer(
								"<BODY bgcolor="))
								.append(colors.getString("PROP_BG"))
								.append(" onload=\" JSError('")
								.append(labels.getString("1120"))
								.append("');\">")));
						responseWriteLn("</BODY></HTML>");
					}
				}
			}
		}
		responseWriteLn(String.valueOf((new StringBuffer("<BODY bgcolor="))
				.append(colors.getString("PROP_BG")).append(" onload=\"")));
		if (save.equals("YES")) {
			responseWriteLn("refreshList();WaitPost();\">");
			responseWriteLn(String.valueOf((new StringBuffer(
					"<form method=POST NAME=SHOW_LIST action='")).append(
					scriptPath).append("?QUERY=FILEADM_LIST' TARGET='FLIST'>")));
			responseWriteLn(String.valueOf((new StringBuffer(
					"<input type=hidden name=RELATIVEPATH value=\"")).append(
					relativePath).append("\">")));
			responseWriteLn(String.valueOf((new StringBuffer(
					"<input type=hidden name=NAME value=\"")).append(name)
					.append("\">")));
			responseWriteLn("</form>");
			responseWriteLn(String
					.valueOf((new StringBuffer(
							"<form method=POST NAME=SHOW_FICHE action='"))
							.append(scriptPath).append(
									"?QUERY=FILEADM_FICHE' TARGET='FPROP'>")));
			responseWriteLn(String.valueOf((new StringBuffer(
					"<input type=hidden name=RELATIVEPATH value=\""))
					.append(relativePath).append(m_fileSeparator).append(name)
					.append("\">")));
			responseWriteLn("</form>");
		} else {
			responseWriteLn("LoadHeader('SAVEFILE');document.UPLOADFILE.FILE.focus();\">");
		}
		responseWriteLn("<br>");
		responseWriteLn("<table>");
		responseWriteLn("<tr>");
		responseWriteLn(String
				.valueOf((new StringBuffer(
						"<form NAME='UPLOADFILE' METHOD='POST' ENCTYPE='multipart/form-data' action='"))
						.append(scriptPath).append(
								"?QUERY=FILEADM_NEWFILE&SAVE=YES'")));
		responseWriteLn("onSubmit=\"if (CheckInput()) {ShowWaitPost();document.UPLOADFILE.PATH.value=parent.FLIST.document.SHOW_LIST.RELATIVEPATH.value;}else{return false;}\"");
		responseWriteLn(">");
		responseWriteLn("<input type=hidden name=PATH value=''>");
		responseWriteLn("<input type=hidden name=ERROR value=''>");
		responseWriteLn(String.valueOf((new StringBuffer(
				"<td><font face='helv,helvetica' size=1 color="))
				.append(colors.getString("PROP_VALUE")).append(">&nbsp;&nbsp;")
				.append(labels.getString("8020"))
				.append("&nbsp;</font></td></td>")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"<td><input TYPE='file' name='FILE' size='")).append(
				m_inputFileSize).append("'></td></tr>")));
		if (m_overWrite) {
			responseWriteLn(String.valueOf((new StringBuffer(
					"<tr><td><font face='helv,helvetica' size=1 color="))
					.append(colors.getString("PROP_VALUE"))
					.append(">&nbsp;&nbsp;").append(labels.getString("8024"))
					.append("</font></td>")));
			responseWriteLn("<td><input TYPE='checkbox' name='OverWrite' value='ok'></td></tr>");
		}
		responseWriteLn(String
				.valueOf((new StringBuffer(
						"<tr><td>&nbsp;</td><td><input TYPE='Submit' name='SEND' value='"))
						.append(labels.getString("5003")).append("'></td>")));
		responseWriteLn("</form>");
		responseWriteLn("</tr>");
		responseWriteLn("</table>");
		responseWriteLn("</BODY>");
		responseWriteLn("</HTML>");
	}

	private void fileAdmNewFolder() throws IOException {
		if (m_authentication && !auth())
			return;
		String scriptPath = "";
		String relativePath = "";
		String sort = "";
		String sortOrder = "";
		String folderName = "";
		String add = "";
		relativePath = m_request.getParameter("RELATIVEPATH");
		sort = m_request.getParameter("SORT");
		sortOrder = m_request.getParameter("SORTORDER");
		folderName = m_request.getParameter("FOLDERNAME");
		add = m_request.getParameter("TREATMENT");
		scriptPath = m_request.getRequestURI();
		add = add != null ? add : "";
		if (add.equals("YES")) {
			createDirectory(relativePath, folderName);
			responseWriteLn("<HTML>");
			responseWriteLn(String.valueOf((new StringBuffer("<BODY bgcolor="))
					.append(colors.getString("PROP_BG")).append(" onload=\"")));
			responseWriteLn("document.SHOW_LIST.submit();\">");
			responseWriteLn(String.valueOf((new StringBuffer(
					"<form method=POST name=SHOW_LIST action='")).append(
					scriptPath).append("?QUERY=FILEADM_LIST' TARGET='FLIST'>")));
			responseWriteLn(String.valueOf((new StringBuffer(
					"<input type=hidden name=RELATIVEPATH value=\"")).append(
					relativePath).append("\">")));
			responseWriteLn(String.valueOf((new StringBuffer(
					"<input type=hidden name=NAME value=\""))
					.append(folderName).append("\">")));
			responseWriteLn(String.valueOf((new StringBuffer(
					"<input type=hidden name=SORT value='")).append(sort)
					.append("'>")));
			responseWriteLn(String.valueOf((new StringBuffer(
					"<input type=hidden name=SORTORDER value='")).append(
					sortOrder).append("'>")));
			responseWriteLn("</form>");
		} else {
			responseWriteLn("<HTML>");
			responseWriteLn("<SCRIPT>");
			responseWriteLn("function checkValues() {");
			responseWriteLn("   if(document.FOLDER_FORM.FOLDERNAME.length==0){");
			responseWriteLn(String.valueOf((new StringBuffer("       alert('"))
					.append(labels.getString("1455")).append("');")));
			responseWriteLn("       return false;");
			responseWriteLn("   }");
			responseWriteLn("   if ((document.FOLDER_FORM.FOLDERNAME.value.indexOf('\\\\')>=0) || (document.FOLDER_FORM.FOLDERNAME.value.indexOf('/')>=0) || (document.FOLDER_FORM.FOLDERNAME.value.indexOf(':')>=0) || (document.FOLDER_FORM.FOLDERNAME.value.indexOf('*')>=0) || (document.FOLDER_FORM.FOLDERNAME.value.indexOf('?')>=0) || (document.FOLDER_FORM.FOLDERNAME.value.indexOf('\"')>=0)|| (document.FOLDER_FORM.FOLDERNAME.value.indexOf('<')>=0) || (document.FOLDER_FORM.FOLDERNAME.value.indexOf('>')>=0) || (document.FOLDER_FORM.FOLDERNAME.value.indexOf('|')>=0)) {");
			responseWriteLn(String.valueOf((new StringBuffer("       alert('"))
					.append(labels.getString("1465")).append("');")));
			responseWriteLn("       return false;");
			responseWriteLn("   }");
			responseWriteLn("   myStr=document.FOLDER_FORM.FOLDERNAME.value;");
			responseWriteLn("   var i=0;");
			responseWriteLn("   while (myStr.charAt(i)==' ') { i++; }");
			responseWriteLn("   if (i==myStr.length) {");
			responseWriteLn(String.valueOf((new StringBuffer("       alert('"))
					.append(labels.getString("1460")).append("');")));
			responseWriteLn("       return false;");
			responseWriteLn("   }");
			responseWriteLn("   return true;");
			responseWriteLn("}");
			responseWriteLn("function init() { ");
			responseWriteLn("   document.FOLDER_FORM.RELATIVEPATH.value=parent.FLIST.document.SHOW_LIST.RELATIVEPATH.value;");
			responseWriteLn("   document.FOLDER_FORM.FOLDERNAME.focus();");
			responseWriteLn("   document.FOLDER_FORM.FOLDERNAME.select();");
			responseWriteLn("   LoadHeader('SAVEFOLDER');");
			responseWriteLn("}");
			responseWriteLn("function LoadHeader(strMode) { ");
			responseWriteLn("   parent.FPARAM.document.Info.STATE.value = strMode;");
			responseWriteLn("   parent.FPARAM.displayToolbar(strMode)");
			responseWriteLn("}");
			responseWriteLn("</SCRIPT>");
			responseWriteLn(String.valueOf((new StringBuffer("<BODY bgcolor="))
					.append(colors.getString("PROP_BG")).append(
							" onload=\"init();\">")));
			responseWriteLn("<br>");
			responseWriteLn(String.valueOf((new StringBuffer(
					"<form  method=POST name=FOLDER_FORM action='")).append(
					scriptPath).append(
					"?QUERY=FILEADM_NEWFOLDER' TARGET='FPROP'")));
			responseWriteLn("onSubmit=\"if (!checkValues()) document.FOLDER_FORM.TREATMENT.value='no';\">");
			responseWriteLn("<input type=HIDDEN name=TREATMENT value='YES'>");
			responseWriteLn(String.valueOf((new StringBuffer(
					"<input type=HIDDEN name=SORT value='")).append(sort)
					.append("'>")));
			responseWriteLn(String.valueOf((new StringBuffer(
					"<input type=HIDDEN name=SORTORDER value='")).append(
					sortOrder).append("'>")));
			responseWriteLn("<input type=HIDDEN name=RELATIVEPATH value=\"\">");
			responseWriteLn(String.valueOf((new StringBuffer(
					"<font size=1 face=helv,helvetica color="))
					.append(colors.getString("PROP_VALUE"))
					.append(">&nbsp;&nbsp;").append(labels.getString("8021"))
					.append("&nbsp;</font>")));
			responseWriteLn("<input type=text name=FOLDERNAME size=54 maxlength=250>");
			responseWriteLn("<input type=submit name=submitt value='?'>");
			responseWriteLn("</form>");
		}
		responseWriteLn("</BODY>");
		responseWriteLn("</HTML>");
	}

	private void fileAdmParam() throws IOException {
		if (m_authentication && !auth())
			return;
		String scriptPath = "";
		String relativePath = "";

		relativePath = m_request.getParameter("RELATIVEPATH");

		scriptPath = m_request.getRequestURI();
		responseWriteLn("<HTML>");
		responseWriteLn("<HEAD>");
		responseWriteLn(String.valueOf((new StringBuffer(
				"<link rel=\"stylesheet\" type=\"text/css\" href=\"")).append(
				getStylesPath()).append("\"/>")));
		responseWriteLn("<SCRIPT>"); // Begin of <script>
		// Begin refreshToolbar
		responseWriteLn("function refreshToolbar(strMode) { ");
		responseWriteLn("   if((parent.FPARAM.document.Info.CONTROL.value=='CUT')||(parent.FPARAM.document.Info.CONTROL.value=='COPY')){");
		responseWriteLn("       parent.FPARAM.displayToolbar(strMode);");
		responseWriteLn("   }");
		responseWriteLn("   if(parent.FPARAM.document.Info.STATE.value!=strMode){");
		responseWriteLn("       parent.FPARAM.document.Info.STATE.value = strMode;");
		responseWriteLn("       parent.FPARAM.displayToolbar(strMode);");
		responseWriteLn("   }");
		responseWriteLn("}");
		// End of refreshToolbar

		responseWriteLn("function newFile() {");
		responseWriteLn("   parent.FTOOLBAR.document.NEW_FILE.RELATIVEPATH.value = parent.FLIST.document.SHOW_LIST.RELATIVEPATH.value;");
		responseWriteLn("   parent.FTOOLBAR.document.NEW_FILE.submit();");
		responseWriteLn("}");
		responseWriteLn("function newFolder() {");
		responseWriteLn("   parent.FTOOLBAR.document.TOOLBAR.QUERY.value = 'FILEADM_NEWFOLDER';");
		responseWriteLn("   parent.FTOOLBAR.document.TOOLBAR.SORT.value = parent.FLIST.document.SHOW_LIST.SORT.value;");
		responseWriteLn("   parent.FTOOLBAR.document.TOOLBAR.SORTORDER.value = parent.FLIST.document.SHOW_LIST.SORTORDER.value;");
		responseWriteLn("   parent.FTOOLBAR.document.TOOLBAR.submit();");
		responseWriteLn("}");
		responseWriteLn("function saveFile()");
		responseWriteLn("{");
		responseWriteLn("   if (parent.FPROP.CheckInput()) {");
		responseWriteLn("      parent.FPROP.ShowWaitPost();");
		responseWriteLn("      parent.FPROP.document.UPLOADFILE.PATH.value = parent.FLIST.document.SHOW_LIST.RELATIVEPATH.value;");
		responseWriteLn("      parent.FPROP.document.UPLOADFILE.submit();");
		responseWriteLn("   }");
		responseWriteLn("}");
		responseWriteLn("function saveFolder() {");
		responseWriteLn("   if (parent.FPROP.checkValues()) {");
		responseWriteLn("       parent.FPROP.document.FOLDER_FORM.submit();");
		responseWriteLn("   }");
		responseWriteLn("}");
		responseWriteLn("function Cut() {");
		responseWriteLn("   parent.FPARAM.document.PASTE.CONTROL.value = 'CUT';");
		responseWriteLn("   parent.FPARAM.document.PASTE.RELATIVEPATH.value = parent.FLIST.document.SHOW_LIST.RELATIVEPATH.value;");
		responseWriteLn("   parent.FPARAM.document.PASTE.NAME.value = parent.FLIST.document.SHOW_FICHE.NAME.value;");
		responseWriteLn("   parent.FPARAM.displayToolbar('CUT');");
		responseWriteLn("}");
		responseWriteLn("function Copy() {");
		responseWriteLn("   parent.FPARAM.document.PASTE.CONTROL.value = 'COPY';");
		responseWriteLn("   parent.FPARAM.document.PASTE.RELATIVEPATH.value = parent.FLIST.document.SHOW_LIST.RELATIVEPATH.value;");
		responseWriteLn("   parent.FPARAM.document.PASTE.NAME.value = parent.FLIST.document.SHOW_FICHE.NAME.value;");
		responseWriteLn("   parent.FPARAM.displayToolbar('COPY');");
		responseWriteLn("}");
		responseWriteLn("function Paste() {");
		responseWriteLn("   parent.FPARAM.document.PASTE.NEWPATH.value=parent.FLIST.document.SHOW_LIST.RELATIVEPATH.value;");
		responseWriteLn("   parent.FPARAM.document.PASTE.submit();");
		responseWriteLn("}");
		responseWriteLn("function Supprimer() {");
		responseWriteLn("   if (parent.FLIST.document.SHOW_FICHE.RELATIVEPATH.value.length==0) {");
		responseWriteLn(String.valueOf((new StringBuffer("       alert('"))
				.append(labels.getString("9001")).append("');")));
		responseWriteLn("       return;");
		responseWriteLn("   }");
		responseWriteLn(String.valueOf((new StringBuffer("   if (confirm('"))
				.append(labels.getString("9002")).append("')) {")));
		responseWriteLn("       parent.FLIST.document.SHOW_FICHE.QUERY.value = 'FILEADM_DELETE';");
		responseWriteLn("       parent.FLIST.document.SHOW_FICHE.submit();");
		responseWriteLn("   }");
		responseWriteLn("}");
		responseWriteLn("function callRename() {");
		responseWriteLn("   parent.FLIST.document.SHOW_FICHE.QUERY.value = 'FILEADM_RENAME';");
		responseWriteLn("   parent.FLIST.document.SHOW_FICHE.submit();");
		responseWriteLn("}");
		responseWriteLn("function Rename() {");
		responseWriteLn("   if (parent.FPROP.CheckInput()) {");
		responseWriteLn("       parent.FPROP.document.FOLDER_FORM.submit();");
		responseWriteLn("   }");
		responseWriteLn("}");

		// Begin of displayToolbar
		responseWriteLn("function displayToolbar(treatment) {");
		responseWriteLn("ftarget=parent.FTOOLBAR.document;");
		responseWriteLn("ftarget.open();");
		responseWriteLn("ftarget.write('<html>');");
		responseWriteLn("ftarget.write('<head>');");
		responseWriteLn(String
				.valueOf((new StringBuffer(
						"ftarget.write('<link rel=\"stylesheet\" type=\"text/css\" href=\""))
						.append(getStylesPath()).append("\" />');")));
		responseWriteLn("ftarget.write('</head>');");
		responseWriteLn("document.Info.CONTROL.value=treatment;");
		responseWriteLn("if (treatment=='') {");
		responseWriteLn("   ft=parent.FPROP.document;");
		responseWriteLn("   ft.open();");
		responseWriteLn("   ft.write('<html>');");
		responseWriteLn("   ft.write('<head>');");
		responseWriteLn("   ft.write('</head>');");
		if (!m_showProperties)
			responseWriteLn(String.valueOf((new StringBuffer(
					"   ft.write('<body bgcolor=")).append(
					colors.getString("FRAME_BORDER")).append(" >');")));
		else
			responseWriteLn(String.valueOf((new StringBuffer(
					"   ft.write('<body bgcolor=")).append(
					colors.getString("PROP_BG")).append(" >');")));
		responseWriteLn("   ft.write('&nbsp;');");
		responseWriteLn("   ft.write('</body>');");
		responseWriteLn("   ft.write('</html>');");
		responseWriteLn("   ft.close();");
		responseWriteLn("}");

		if (!m_readOnly) {
			responseWriteLn("ftarget.write('<table class=mainMenu border=0 cellpadding=0 cellspacing=0 WIDTH=\"100%\" Height=\"20\">');");
			responseWriteLn("ftarget.write('<tr>');");
			responseWriteLn("ftarget.write('<td>');");
			responseWriteLn("ftarget.write('<table border=0 cellpadding=0 cellspacing=0>');");
			responseWriteLn("ftarget.write('<tr>');");
			responseWriteLn("ftarget.write('<td>&nbsp;&nbsp;</td>');");

			// new file
			responseWriteLn("ftarget.write('<td align=center>');");
			responseWriteLn("ftarget.write('<a href=\"javascript:parent.FPARAM.newFile();\" >');");
			responseWriteLn("ftarget.write('<span class=\"mainMenuItem\" onmousemove=\"this.className=\\'mainMenuMoveItem\\'\" onmouseout=\"this.className=\\'mainMenuItem\\'\">');");
			responseWriteLn(String
					.valueOf((new StringBuffer("ftarget.write('")).append(
							labels.getString("5009")).append("');")));
			responseWriteLn("ftarget.write('</span>');");
			responseWriteLn("ftarget.write('</a>');");
			responseWriteLn("ftarget.write('</td>');");
			responseWriteLn("ftarget.write('<td>&nbsp;&nbsp;</td>');");

			// new folder
			responseWriteLn("ftarget.write('<td align=center>');");
			responseWriteLn("ftarget.write('<a href=\"javascript:parent.FPARAM.newFolder();\" >');");
			responseWriteLn("ftarget.write('<span class=\"mainMenuItem\" onmousemove=\"this.className=\\'mainMenuMoveItem\\'\" onmouseout=\"this.className=\\'mainMenuItem\\'\">');");
			responseWriteLn(String
					.valueOf((new StringBuffer("ftarget.write('")).append(
							labels.getString("5010")).append("');")));
			responseWriteLn("ftarget.write('</span>');");
			responseWriteLn("ftarget.write('</a>');");
			responseWriteLn("ftarget.write('</td>');");
			responseWriteLn("ftarget.write('<td>&nbsp;&nbsp;</td>');");

			// separator
			responseWriteLn("ftarget.write('<td align=center>');");
			responseWriteLn(String.valueOf((new StringBuffer(
					"ftarget.write('<img src=\"")).append(
					getImages("separatorHigh2")).append(
					"\" WIDTH=3 HEIGHT=20 border=0>');")));
			responseWriteLn("ftarget.write('</td>');");
			responseWriteLn("ftarget.write('<td>&nbsp;&nbsp;</td>');");

			// copy
			responseWriteLn("ftarget.write('<td align=center>');");
			responseWriteLn("if ((treatment == 'ONE')||(treatment == 'CUT')||(treatment == 'COPY')) {");
			responseWriteLn("   ftarget.write('<a href=\"javascript:parent.FPARAM.Copy();\">');");
			responseWriteLn("ftarget.write('<span class=\"mainMenuItem\" onmousemove=\"this.className=\\'mainMenuMoveItem\\'\" onmouseout=\"this.className=\\'mainMenuItem\\'\">');");
			responseWriteLn(String
					.valueOf((new StringBuffer("ftarget.write('")).append(
							labels.getString("5005")).append("');")));
			responseWriteLn("ftarget.write('</span>');");
			responseWriteLn("ftarget.write('</a>');");
			responseWriteLn("}else{");
			responseWriteLn("ftarget.write('<span class=\"mainMenuItemDisabled\" >');");
			responseWriteLn(String
					.valueOf((new StringBuffer("ftarget.write('")).append(
							labels.getString("5005")).append("');")));
			responseWriteLn("ftarget.write('</span>');");
			responseWriteLn("}");
			responseWriteLn("ftarget.write('</td>');");
			responseWriteLn("ftarget.write('<td>&nbsp;&nbsp;</td>');");

			// paste
			responseWriteLn("ftarget.write('<td align=center>');");
			responseWriteLn("if ((document.PASTE.CONTROL.value=='CUT')||(document.PASTE.CONTROL.value=='COPY')) {");
			responseWriteLn("   ftarget.write('<a href=\"javascript:parent.FPARAM.Paste();\">');");
			responseWriteLn("ftarget.write('<span class=\"mainMenuItem\" onmousemove=\"this.className=\\'mainMenuMoveItem\\'\" onmouseout=\"this.className=\\'mainMenuItem\\'\">');");
			responseWriteLn(String
					.valueOf((new StringBuffer("ftarget.write('")).append(
							labels.getString("5006")).append("');")));
			responseWriteLn("ftarget.write('</span>');");
			responseWriteLn("ftarget.write('</a>');");
			responseWriteLn("}else{");
			responseWriteLn("ftarget.write('<span class=\"mainMenuItemDisabled\" >');");
			responseWriteLn(String
					.valueOf((new StringBuffer("ftarget.write('")).append(
							labels.getString("5006")).append("');")));
			responseWriteLn("ftarget.write('</span>');");
			responseWriteLn("}");
			responseWriteLn("ftarget.write('</td>');");
			responseWriteLn("ftarget.write('<td>&nbsp;&nbsp;</td>');");

			responseWriteLn("ftarget.write('</tr>');");
			responseWriteLn("ftarget.write('</table>');");
			responseWriteLn("ftarget.write('</td>');");

			responseWriteLn("ftarget.write('<td align=right>');");

			responseWriteLn("ftarget.write('</td>');");
			responseWriteLn("ftarget.write('</tr>');");
			responseWriteLn("ftarget.write('</table>');");

			responseWriteLn(String
					.valueOf((new StringBuffer(
							"ftarget.write('<form method=\"POST\"  NAME=\"NEW_FILE\" action=\""))
							.append(scriptPath)
							.append("?QUERY=FILEADM_NEWFILE&RELATIVEPATH=")
							.append(relativePath).append("\" TARGET=FPROP>');")));
			responseWriteLn(String
					.valueOf((new StringBuffer(
							"ftarget.write('<input type=hidden name=RELATIVEPATH value=\""))
							.append(relativePath).append("\">');")));
			responseWriteLn("ftarget.write('</form>');");
		}
		responseWriteLn(String.valueOf((new StringBuffer(
				"ftarget.write('<form method=POST  NAME=TOOLBAR action=\""))
				.append(scriptPath).append("\" TARGET=FPROP>\\n');")));
		responseWriteLn("ftarget.write('<input type=hidden name=QUERY value=\"\">\\n');");
		responseWriteLn("ftarget.write('<input type=hidden name=RELATIVEPATH >\\n');");
		responseWriteLn("ftarget.write('<input type=hidden name=SORT value=\"\">\\n');");
		responseWriteLn("ftarget.write('<input type=hidden name=SORTORDER value=\"\">\\n');");
		responseWriteLn("ftarget.write('<input type=hidden name=NAME value=\"\">\\n');");
		responseWriteLn("ftarget.write('<input type=hidden name=TREATMENT value=\"\">\\n');");
		responseWriteLn("ftarget.write('</form>\\n');");
		responseWriteLn("ftarget.write('</body>\\n');");
		responseWriteLn("ftarget.write('</html>\\n');");
		responseWriteLn("ftarget.close();");
		responseWriteLn("}");
		// End of displayToolbar

		// Begin of displayHeader
		responseWriteLn("function displayHeader(nbFiles, nbFolders) {");
		responseWriteLn("   ftarget=parent.FCOLUMNS.document;");
		responseWriteLn("   ftarget.open();");
		responseWriteLn("   ftarget.writeln('<html>');");
		responseWriteLn("   ftarget.writeln('<head>');");
		responseWriteLn("   ftarget.writeln('</head>');");
		responseWriteLn(String
				.valueOf((new StringBuffer(
						"ftarget.write('<link rel=\"stylesheet\" type=\"text/css\" href=\""))
						.append(getStylesPath()).append("\" />');")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"   ftarget.writeln('<body bgcolor=\""))
				.append(colors.getString("COLHEADER_BG")).append("\" vlink=")
				.append(colors.getString("COLHEADER_CAPTION_FONT"))
				.append(" link=")
				.append(colors.getString("COLHEADER_CAPTION_FONT"))
				.append(">');")));
		responseWriteLn(String
				.valueOf((new StringBuffer(
						"   ftarget.writeln('<table class=listFrame border=0 cellpadding=0 cellspacing=2 width=\"100%\" bgcolor="))
						.append(colors.getString("COLHEADER_BG"))
						.append(">');")));
		responseWriteLn("   ftarget.writeln('<tr class=listTitle>');");
		responseWriteLn(String.valueOf((new StringBuffer(
				"   ftarget.writeln('<td align=left width=\"33%\" bgcolor=\""))
				.append(colors.getString("COLHEADER_CAPTION_BG")).append(
						"\">');")));
		responseWriteLn("   if (nbFiles+nbFolders==0) {");
		responseWriteLn(String.valueOf((new StringBuffer(
				"       ftarget.writeln('<IMG NAME=img1 SRC=\""))
				.append(getImages("imageNull"))
				.append("\" HSPACE=2 WIDTH=8 HEIGHT=7 border=0>")
				.append(labels.getString("6000")).append("');")));
		responseWriteLn("   }else{");
		responseWriteLn("       ftarget.writeln('<a href=\"JavaScript:parent.FPARAM.actionTri(1);\">');");
		responseWriteLn(String.valueOf((new StringBuffer(
				"       ftarget.writeln('<IMG NAME=img1 SRC=\""))
				.append(getImages("imageNull"))
				.append("\" HSPACE=2 WIDTH=8 HEIGHT=7 border=0>")
				.append(labels.getString("6000")).append("</a>');")));
		responseWriteLn("   }");
		responseWriteLn(String.valueOf((new StringBuffer(
				"   ftarget.writeln('');"))));
		responseWriteLn(String
				.valueOf((new StringBuffer(
						"   if (nbFolders>1) ftarget.writeln('&nbsp;&nbsp;&nbsp;&nbsp;[ ' + nbFolders + ' "))
						.append(labels.getString("6003")).append(", ');")));
		responseWriteLn(String
				.valueOf((new StringBuffer(
						"   else ftarget.writeln('&nbsp;&nbsp;&nbsp;&nbsp;[ ' + nbFolders + ' "))
						.append(labels.getString("6001")).append(", ');")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"   if (nbFiles>1) ftarget.writeln(nbFiles + ' ")).append(
				labels.getString("6004")).append(" ]');")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"   else ftarget.writeln(nbFiles + ' ")).append(
				labels.getString("6002")).append(" ]');")));
		responseWriteLn("   ftarget.writeln('</td>');");
		responseWriteLn(String.valueOf((new StringBuffer(
				"   ftarget.writeln('<td align=right width=\"12%\" bgcolor="))
				.append(colors.getString("COLHEADER_CAPTION_BG"))
				.append(">');")));
		responseWriteLn("   if (nbFiles+nbFolders==0) {");
		responseWriteLn(String.valueOf((new StringBuffer(
				"       ftarget.writeln('<IMG NAME=img2 SRC=\""))
				.append(getImages("imageNull"))
				.append("\" HSPACE=2 WIDTH=8 HEIGHT=7 border=0>")
				.append(labels.getString("6005")).append("&nbsp;');")));
		responseWriteLn("   }else{");
		responseWriteLn("       ftarget.writeln('<a href=\"JavaScript:parent.FPARAM.actionTri(2);\">');");
		responseWriteLn(String.valueOf((new StringBuffer(
				"       ftarget.writeln('<IMG NAME=img2 SRC=\""))
				.append(getImages("imageNull"))
				.append("\" HSPACE=2 WIDTH=8 HEIGHT=7 border=0>")
				.append(labels.getString("6005")).append("&nbsp;</a>');")));
		responseWriteLn("   }");
		responseWriteLn("   ftarget.writeln('</td>');");
		responseWriteLn(String.valueOf((new StringBuffer(
				"   ftarget.writeln('<td align=left width=\"30%\" bgcolor="))
				.append(colors.getString("COLHEADER_CAPTION_BG"))
				.append(">');")));
		responseWriteLn("   if (nbFiles+nbFolders== 0){");
		responseWriteLn(String.valueOf((new StringBuffer(
				"       ftarget.writeln('<IMG NAME=img3 SRC=\""))
				.append(getImages("imageNull"))
				.append("\" HSPACE=2 WIDTH=8 HEIGHT=7 border=0>")
				.append(labels.getString("6006")).append("');")));
		responseWriteLn("   }else{");
		responseWriteLn("       ftarget.writeln('<a href=\"JavaScript:parent.FPARAM.actionTri(3);\">');");
		responseWriteLn(String.valueOf((new StringBuffer(
				"       ftarget.writeln('<IMG NAME=img3 SRC=\""))
				.append(getImages("imageNull"))
				.append("\" HSPACE=2 WIDTH=8 HEIGHT=7 border=0>")
				.append(labels.getString("6006")).append("</a>');")));
		responseWriteLn("   }");
		responseWriteLn("   ftarget.writeln('</td>');");
		responseWriteLn(String.valueOf((new StringBuffer(
				"   ftarget.writeln('<td align=left width=\"25%\" bgcolor="))
				.append(colors.getString("COLHEADER_CAPTION_BG"))
				.append(">');")));
		responseWriteLn("   if (nbFiles+nbFolders== 0){");
		responseWriteLn(String.valueOf((new StringBuffer(
				"       ftarget.writeln('<IMG NAME=img4 SRC=\""))
				.append(getImages("imageNull"))
				.append("\" HSPACE=2 WIDTH=8 HEIGHT=7 border=0>")
				.append(labels.getString("6007")).append("');")));
		responseWriteLn("   }else{");
		responseWriteLn("       ftarget.writeln('<a href=\"JavaScript:parent.FPARAM.actionTri(4);\">');");
		responseWriteLn(String.valueOf((new StringBuffer(
				"       ftarget.writeln('<IMG NAME=img4 SRC=\""))
				.append(getImages("imageNull"))
				.append("\" HSPACE=2 WIDTH=8 HEIGHT=7 border=0>")
				.append(labels.getString("6007")).append("</a>');")));
		responseWriteLn("   }");
		responseWriteLn("   ftarget.writeln('</td>');");
		responseWriteLn(String.valueOf((new StringBuffer(
				"   ftarget.writeln('<td align=left width=15 bgcolor="))
				.append(colors.getString("COLHEADER_CAPTION_BG"))
				.append(">');")));
		responseWriteLn(String.valueOf((new StringBuffer(
				"   ftarget.writeln('<img src=\"")).append(
				getImages("imageNull")).append(
				"\" WIDTH=15 HEIGHT=7 border=0></td>');")));
		responseWriteLn("   ftarget.writeln('</tr>');");
		responseWriteLn("   ftarget.writeln('</table>');");
		responseWriteLn("   ftarget.writeln('</body>');");
		responseWriteLn("   ftarget.writeln('</html>');");
		responseWriteLn("   ftarget.close();");
		responseWriteLn("   ShowTriIcon() ;");
		responseWriteLn("}");
		// End of displayHeader

		// Begin of actionTri
		responseWriteLn("function actionTri(str){");
		responseWriteLn("   parent.FLIST.document.SHOW_LIST.SORT.value=str;");
		responseWriteLn("   if (document.Info.SORT.value==str){");
		responseWriteLn("       if (document.Info.SORTORDER.value==1){");
		responseWriteLn("           parent.FLIST.document.SHOW_LIST.SORTORDER.value = 0;");
		responseWriteLn("           document.Info.SORTORDER.value=0;");
		responseWriteLn("       }else{");
		responseWriteLn("           parent.FLIST.document.SHOW_LIST.SORTORDER.value = 1;");
		responseWriteLn("           document.Info.SORTORDER.value = 1;");
		responseWriteLn("       }");
		responseWriteLn("   }else{");
		responseWriteLn("       parent.FLIST.document.SHOW_LIST.SORTORDER.value = 0;");
		responseWriteLn("       document.Info.SORTORDER.value = 0;");
		responseWriteLn("   }");
		responseWriteLn("   document.Info.SORT.value=str;");
		responseWriteLn("   ShowTriIcon();");
		responseWriteLn("   parent.FLIST.document.SHOW_LIST.submit();");
		responseWriteLn("}");
		// End of actionTri

		// Begin ShowTriIcon
		responseWriteLn("var lasticonpos='';");
		responseWriteLn("function ShowTriIcon(){");
		responseWriteLn(String
				.valueOf((new StringBuffer(
						"   if (lasticonpos !='') eval('parent.FCOLUMNS.document.img'+lasticonpos+'.src=\""))
						.append(getImages("imageNull")).append("\"')")));
		responseWriteLn("   if (isNaN(parseInt(document.Info.SORTORDER.value))){");
		responseWriteLn(String
				.valueOf((new StringBuffer("       lasticonpos=")).append(
						m_defaultSortField).append(";")));
		if (m_defaultSortField == 3)
			responseWriteLn(String.valueOf((new StringBuffer(
					"       eval('parent.FCOLUMNS.document.img"))
					.append(m_defaultSortField).append(".src=\"")
					.append(getImages("imageSortUp")).append("\"')")));
		else
			responseWriteLn(String.valueOf((new StringBuffer(
					"       eval('parent.FCOLUMNS.document.img"))
					.append(m_defaultSortField).append(".src=\"")
					.append(getImages("imageSortDown")).append("\"')")));
		responseWriteLn("   }else{");
		responseWriteLn("       lasticonpos=document.Info.SORT.value;");
		responseWriteLn("       if (document.Info.SORTORDER.value==1){");
		responseWriteLn(String
				.valueOf((new StringBuffer(
						"           eval('parent.FCOLUMNS.document.img'+document.Info.SORT.value+'.src=\""))
						.append(getImages("imageSortUp")).append("\"')")));
		responseWriteLn("       }else{");
		responseWriteLn(String
				.valueOf((new StringBuffer(
						"           eval('parent.FCOLUMNS.document.img'+document.Info.SORT.value+'.src=\""))
						.append(getImages("imageSortDown")).append("\"')")));
		responseWriteLn("       }");
		responseWriteLn("   }");
		responseWriteLn("}");
		// End of ShowTriIcon

		// Begin of init
		responseWriteLn("function init() {");
		if (!m_strDefaultPath.equals("")) {
			responseWriteLn("if (document.Info.DEFAULTPATH.value=='') {");
			responseWriteLn(String.valueOf((new StringBuffer(
					"  document.FORMLIST.RELATIVEPATH.value='")).append(
					m_strDefaultPath).append("';")));
			responseWriteLn(String.valueOf((new StringBuffer(
					"  document.Info.DEFAULTPATH.value='")).append(
					m_strDefaultPath).append("';")));
			responseWriteLn("}else{ ");
			responseWriteLn(String.valueOf((new StringBuffer(
					"  document.Info.RELATIVEPATH.value='")).append(
					m_strDefaultPath).append("';}")));
		} else {
			responseWriteLn(String.valueOf((new StringBuffer(
					"document.Info.RELATIVEPATH.value='")).append(
					m_strDefaultPath).append("';")));
		}
		responseWriteLn(String.valueOf((new StringBuffer(
				"   document.Info.RELATIVEPATH.value='")).append(relativePath)
				.append("';")));
		responseWriteLn("   document.Info.CONTROL.value='init';");
		responseWriteLn("   document.FORMLIST.submit();");
		responseWriteLn("   parent.FTOOLBAR.document.open();");
		responseWriteLn(String.valueOf((new StringBuffer(
				"   parent.FTOOLBAR.document.write('<HTML><BODY BGCOLOR="))
				.append(colors.getString("FRAME_BORDER")).append(
						"></BODY></HTML>');")));
		responseWriteLn("   parent.FTOOLBAR.document.close();");
		responseWriteLn("   parent.RBORDER.document.open();");
		responseWriteLn(String.valueOf((new StringBuffer(
				"   parent.RBORDER.document.write('<HTML><BODY BGCOLOR="))
				.append(colors.getString("FRAME_BORDER")).append(
						"></BODY></HTML>');")));
		responseWriteLn("   parent.RBORDER.document.close();");
		responseWriteLn("   parent.LBORDER.document.open();");
		responseWriteLn(String.valueOf((new StringBuffer(
				"   parent.LBORDER.document.write('<HTML><BODY BGCOLOR="))
				.append(colors.getString("FRAME_BORDER")).append(
						"></BODY></HTML>');")));
		responseWriteLn("   parent.LBORDER.document.close();");
		responseWriteLn("   parent.BBORDER.document.open();");
		responseWriteLn(String.valueOf((new StringBuffer(
				"   parent.BBORDER.document.write('<HTML><BODY BGCOLOR="))
				.append(colors.getString("FRAME_BORDER")).append(
						"></BODY></HTML>');")));
		responseWriteLn("   parent.BBORDER.document.close();");
		responseWriteLn("   parent.MBORDER.document.open();");
		responseWriteLn(String.valueOf((new StringBuffer(
				"   parent.MBORDER.document.write('<HTML><BODY BGCOLOR="))
				.append(colors.getString("FRAME_BORDER")).append(
						"></BODY></HTML>');")));
		responseWriteLn("   parent.MBORDER.document.close();");
		if (m_showPathSelect) {
			responseWriteLn("   parent.FPATH.document.open();");
			responseWriteLn(String.valueOf((new StringBuffer(
					"   parent.FPATH.document.write('<HTML><BODY BGCOLOR="))
					.append(colors.getString("FOLDERSELECT_BG")).append(
							"></BODY></HTML>');")));
			responseWriteLn("   parent.FPATH.document.close();");
		}
		responseWriteLn("   parent.FPROP.document.open();");
		responseWriteLn(String.valueOf((new StringBuffer(
				"   parent.FPROP.document.write('<HTML><BODY BGCOLOR="))
				.append(colors.getString("FRAME_BORDER")).append(
						"></BODY></HTML>');")));
		responseWriteLn("   parent.FPROP.document.close();");
		responseWriteLn("}");
		// End of Init

		// Begin ns4_resizebuffix
		responseWriteLn("function ns4_resizebuffix() {location.reload(true);}");
		responseWriteLn("if (navigator.appVersion.lastIndexOf('Win') == -1) onresize=ns4_resizebuffix;");
		responseWriteLn("</SCRIPT>"); // End of <script>
		responseWriteLn("</head>");
		responseWriteLn(String.valueOf((new StringBuffer("<body bgcolor='"))
				.append(colors.getString("FRAME_BORDER")).append("' onload=")
				.append("init();").append(">")));

		responseWriteLn(String.valueOf((new StringBuffer(
				"<form  method=POST NAME='Info' action='")).append(scriptPath)
				.append("'>")));
		responseWriteLn("<input type=hidden name=QUERY VALUE=FILEADM_PARAM>");
		responseWriteLn("<input type=hidden name=RELATIVEPATH value=''>");
		responseWriteLn("<input type=hidden name=DEFAULTPATH value=''>");
		responseWriteLn("<input type=hidden name=STATE value=''>");
		responseWriteLn("<input type=hidden name=CONTROL value=''>");
		if (m_defaultSortOrder == 1)
			responseWriteLn("<input type=hidden name=SORTORDER value='1'>");
		else
			responseWriteLn("<input type=hidden name=SORTORDER value='0'>");
		responseWriteLn(String.valueOf((new StringBuffer(
				"<input type=hidden name=SORT value='")).append(
				m_defaultSortField).append("'>")));
		responseWriteLn("<input type=hidden name=RELOAD VALUE='YES'>");
		responseWriteLn("</form>");
		responseWriteLn(String.valueOf((new StringBuffer(
				"<form  method=POST  NAME='FORMLIST' action='")).append(
				scriptPath).append("' TARGET='FLIST'>")));
		responseWriteLn("<input type=hidden name=QUERY VALUE=FILEADM_LIST>");
		responseWriteLn(String.valueOf((new StringBuffer(
				"<input type=hidden name=RELATIVEPATH VALUE=\"")).append(
				relativePath).append("\">")));
		responseWriteLn("</form>");
		responseWriteLn(String.valueOf((new StringBuffer(
				"<form  method=POST  NAME='PASTE' action='"))
				.append(scriptPath).append(
						"?QUERY=FILEADM_PASTE' TARGET=FPROP>")));
		responseWriteLn("<input type=hidden name=CONTROL value=''>");
		responseWriteLn("<input type=hidden name=RELATIVEPATH value=\"\">");
		responseWriteLn("<input type=hidden name=NEWPATH value=''>");
		responseWriteLn("<input type=hidden name=NAME value=''>");
		responseWriteLn("</form>");
		responseWriteLn("</BODY>");
		responseWriteLn("</HTML>");
	}

	private void fileAdmPaste() throws IOException {
		if (m_authentication && !auth())
			return;

		String relativePath = "";
		String newPath = "";
		String name = "";
		String control = "";
		relativePath = m_request.getParameter("RELATIVEPATH");
		newPath = m_request.getParameter("NEWPATH");
		name = m_request.getParameter("NAME");
		name = RequestUtils.decodeString(name);
		control = m_request.getParameter("CONTROL");

		control = control != null ? control : "";
		responseWriteLn("<HTML>");
		responseWriteLn("<HEAD>");
		responseWriteLn("<SCRIPT>");
		responseWriteLn("function refreshList(strRELATIVE_PATH) { ");
		responseWriteLn("   document.SHOW_LIST.submit();");
		responseWriteLn("}");
		responseWriteLn("function LoadHeader(strMode) { ");
		responseWriteLn("   parent.FPARAM.document.Info.STATE.value = strMode;");
		responseWriteLn("   parent.FPARAM.displayToolbar(strMode)");
		responseWriteLn("}");
		responseWriteLn("</SCRIPT>");
		responseWriteLn("</HEAD>");
		responseWriteLn(String.valueOf((new StringBuffer("<BODY bgcolor="))
				.append(colors.getString("PROP_BG")).append(
						" onload=\"LoadHeader('ONE'); \">")));
		if (control.equals("")) {
			responseWriteLn("<SCRIPT>");
			responseWriteLn(String.valueOf((new StringBuffer("alert('"))
					.append(labels.getString("1475")).append("');")));
			responseWriteLn("</SCRIPT>");
		}
		if (control.equals("CUT"))
			cut(relativePath, newPath, name);
		else
			copy(relativePath, newPath, name);
		responseWriteLn("<SCRIPT>");
		responseWriteLn("parent.FPARAM.document.PASTE.CONTROL.value='';");
		responseWriteLn("parent.FLIST.document.SHOW_LIST.submit();");
		responseWriteLn("</SCRIPT>");
		responseWriteLn("</BODY>");
		responseWriteLn("</HTML>");
	}

	private void fileAdmProperties() throws NoSuchMethodException, IOException {
		if (m_authentication && !auth())
			return;
		String scriptPath = "";
		String rootPath = "";
		String relativePath = "";
		String contextPath = "";

		String location = "";
		String place = "";
		String tmp = "";

		String extension = "";

		boolean isFile = false;
		relativePath = m_request.getParameter("RELATIVEPATH");

		scriptPath = m_request.getRequestURI();
		contextPath = findContextPath();
		responseWriteLn("<HTML>");
		responseWriteLn("<HEAD>");
		responseWriteLn("<STYLE>");
		responseWriteLn(String.valueOf((new StringBuffer(" A:link {color : "))
				.append(colors.getString("PROP_LINK_ENABLE")).append(
						"; text-decoration : none}")));
		responseWriteLn(String
				.valueOf((new StringBuffer(" A:visited {color : ")).append(
						colors.getString("PROP_LINK_VISITED")).append(
						"; text-decoration : none}")));
		responseWriteLn(String.valueOf((new StringBuffer(" A:hover {color : "))
				.append(colors.getString("PROP_LINK_HOVER")).append(
						"; text-decoration : underline}")));
		responseWriteLn(String
				.valueOf((new StringBuffer(" A:active {color : ")).append(
						colors.getString("PROP_LINK_ACTIVE")).append(
						"; text-decoration : underline}")));
		responseWriteLn("</STYLE>");
		rootPath = getPhysicalPath(m_rootPath + relativePath);
		if (rootPath == null) {
			responseWriteLn("<SCRIPT>");
			responseWriteLn(String.valueOf((new StringBuffer("   alert('"))
					.append(labels.getString("1425")).append("');")));
			responseWriteLn("   parent.FLIST.document.SHOW_LIST.submit();");
			responseWriteLn("</SCRIPT>");
			return;
		}
		File fpath = new File(rootPath);
		if (!fpath.exists()) {
			responseWriteLn("<SCRIPT>");
			responseWriteLn(String.valueOf((new StringBuffer("   alert('"))
					.append(labels.getString("1425")).append("');")));
			responseWriteLn("   parent.FLIST.document.SHOW_LIST.submit();");
			responseWriteLn("</SCRIPT>");
			return;
		}
		if (fpath.isDirectory())
			isFile = false;
		else
			isFile = true;
		extension = getExtension(fpath.getName());
		extension = extension != null ? extension : "";
		place = relativePath.replace('\\', '/');
		if (contextPath.endsWith("/"))
			contextPath = contextPath.substring(0, contextPath.length() - 2);
		if (!m_rootPath.equals("/"))
			contextPath = contextPath + m_rootPath;
		responseWriteLn("<SCRIPT LANGUAGE='JavaScript'>");
		responseWriteLn("function fdownload() {document.FDOWNLOAD.submit();}");
		if (!m_jsEventHandler.equals(null)) {
			responseWriteLn("function selFile(isFolder,isFile,Name,Location,VirtualPath,Url,Size,DateLastModified,canWrite,isHidden){");
			responseWriteLn("   this.isFolder=isFolder");
			responseWriteLn("   this.isFile=isFile");
			responseWriteLn("   this.Name=Name");
			responseWriteLn("   this.Location=Location");
			responseWriteLn("   this.VirtualPath=VirtualPath");
			responseWriteLn("   this.Url=Url");
			responseWriteLn("   this.Size=Size");
			responseWriteLn("   this.DateLastModified=DateLastModified");
			responseWriteLn("   this.canWrite=canWrite");
			responseWriteLn("   this.isHidden=isHidden");
			responseWriteLn("}");
			Date fileDate = new Date(fpath.lastModified());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.getDefault());
			tmp = sdf.format(fileDate);
			location = pathEncode(relativePath);
			if (isFile) {
				if (m_isVirtual) {
					if (m_request.getServerPort() != 80)
						responseWriteLn(String
								.valueOf((new StringBuffer(
										"var selectedFile = new selFile(false,true,\""))
										.append(RequestUtils.encodeString(fpath
												.getName())).append("\",\"")
										.append(location).append("\",\"")
										.append(contextPath)
										.append(urlEncode(place))
										.append("\",\"").append("http://")
										.append(m_request.getServerName())
										.append(":")
										.append(m_request.getServerPort())
										.append(contextPath)
										.append(urlEncode(place)).append("\",")
										.append(fpath.length()).append(",\"")
										.append(tmp).append("\",")
										.append(fpath.canWrite()).append(",")
										.append(isHidden(fpath)).append(");")));
					else
						responseWriteLn(String
								.valueOf((new StringBuffer(
										"var selectedFile = new selFile(false,true,\""))
										.append(RequestUtils.encodeString(fpath
												.getName())).append("\",\"")
										.append(location).append("\",\"")
										.append(contextPath)
										.append(urlEncode(place))
										.append("\",\"").append("http://")
										.append(m_request.getServerName())
										.append(contextPath)
										.append(urlEncode(place)).append("\",")
										.append(fpath.length()).append(",\"")
										.append(tmp).append("\",")
										.append(fpath.canWrite()).append(",")
										.append(isHidden(fpath)).append(");")));
				} else {
					responseWriteLn(String.valueOf((new StringBuffer(
							"var selectedFile = new selFile(false,true,\""))
							.append(RequestUtils.encodeString(fpath.getName()))
							.append("\",\"").append(location)
							.append("\",\"\",\"\",").append(fpath.length())
							.append(",\"").append(tmp).append("\",")
							.append(fpath.canWrite()).append(",")
							.append(isHidden(fpath)).append(");")));
				}
			} else if (m_isVirtual) {
				if (m_request.getServerPort() != 80)
					responseWriteLn(String.valueOf((new StringBuffer(
							"var selectedFile = new selFile(true,false,\""))
							.append(RequestUtils.encodeString(fpath.getName()))
							.append("\",\"").append(location).append("\",\"")
							.append(contextPath).append(urlEncode(place))
							.append("\",\"").append("http://")
							.append(m_request.getServerName()).append(":")
							.append(m_request.getServerPort())
							.append(contextPath).append(urlEncode(place))
							.append("\",").append(fpath.length()).append(",\"")
							.append(tmp).append("\",").append(fpath.canWrite())
							.append(",").append(isHidden(fpath)).append(");")));
				else
					responseWriteLn(String.valueOf((new StringBuffer(
							"var selectedFile = new selFile(true,false,\""))
							.append(RequestUtils.encodeString(fpath.getName()))
							.append("\",\"").append(location).append("\",\"")
							.append(contextPath).append(urlEncode(place))
							.append("\",\"").append("http://")
							.append(m_request.getServerName())
							.append(contextPath).append(urlEncode(place))
							.append("\",").append(fpath.length()).append(",\"")
							.append(tmp).append("\",").append(fpath.canWrite())
							.append(",").append(isHidden(fpath)).append(");")));
			} else {
				responseWriteLn(String.valueOf((new StringBuffer(
						"var selectedFile = new selFile(true,false,\""))
						.append(RequestUtils.encodeString(fpath.getName()))
						.append("\",\"").append(location)
						.append("\",\"\",\"\",").append(fpath.length())
						.append(",\"").append(tmp).append("\",")
						.append(fpath.canWrite()).append(",")
						.append(isHidden(fpath)).append(");")));
			}
		}
		responseWriteLn("</SCRIPT>");
		responseWriteLn("</HEAD>");
		if (!m_showProperties) {
			if (m_jsEventHandler != null)
				responseWriteLn(String.valueOf((new StringBuffer(
						"<body bgcolor="))
						.append(colors.getString("FRAME_BORDER"))
						.append(" onload=\"").append(m_jsEventHandler)
						.append("(selectedFile);\">")));
			else
				responseWriteLn(String.valueOf((new StringBuffer(
						"<body bgcolor=")).append(
						colors.getString("FRAME_BORDER")).append(">")));
			responseWriteLn("</BODY>");
			responseWriteLn("</HTML>");
		} else {
			if (relativePath == null || "".equals(relativePath)) {
				responseWriteLn(String.valueOf((new StringBuffer(
						"<body bgcolor=")).append(colors.getString("PROP_BG"))
						.append(">")));
				responseWriteLn("&nbsp;");
				responseWriteLn("</BODY>");
				responseWriteLn("</HTML>");
			} else if (m_jsEventHandler != null) {
				responseWriteLn(String.valueOf((new StringBuffer(
						"<body bgcolor=")).append(colors.getString("PROP_BG"))
						.append(" onload=\"").append(m_jsEventHandler)
						.append("(selectedFile);\">")));
			} else {
				responseWriteLn(String.valueOf((new StringBuffer(
						"<body bgcolor=")).append(colors.getString("PROP_BG"))
						.append(">")));
			}
			responseWriteLn(String.valueOf((new StringBuffer(
					"<table width='100%' bgcolor=")).append(
					colors.getString("PROP_BG")).append(
					" border='0' cellspacing='6' cellpadding='0'>")));
			responseWriteLn("<tr>");
			if (((Integer) m_properties.get("NAME")).intValue() == 1) {
				responseWriteLn(String.valueOf((new StringBuffer(
						"<td align='left' bgcolor=")).append(
						colors.getString("PROP_BG")).append(
						" width='65%' align='left'>")));
				responseWriteLn("<table border='0' cellspacing='0' cellpadding='0' width='100%'>");
				responseWriteLn("<tr>");
				responseWriteLn(String.valueOf((new StringBuffer(
						"<td NOWRAP width='120' align='right' bgcolor="))
						.append(colors.getString("PROP_CAPTION_BG"))
						.append("><font face='helv,helvetica' color=")
						.append(colors.getString("PROP_CAPTION"))
						.append(" size='1'>&nbsp;")
						.append(labels.getString("8000"))
						.append("&nbsp;:&nbsp;</font></td>")));
				responseWriteLn(String
						.valueOf((new StringBuffer(
								"<td align='left' width='75%' bgcolor="))
								.append(colors.getString("PROP_VALUE_BG"))
								.append(">&nbsp;<font face='helv,helvetica' size='1' color=")
								.append(colors.getString("PROP_LINK_ENABLE"))
								.append(">")));
				if (isFile) {
					if (m_isVirtual)
						responseWriteLn(fpath.getName() + "</font></td>");
					else
						try {
							responseWriteLn(String
									.valueOf((new StringBuffer(
											"<a href=\"javascript:{document.FDOWNLOAD.CONTENTTYPE.value='"))
											.append(mimes.getString(extension))
											.append("';fdownload();}\"")
											.append(">")
											.append(fpath.getName())
											.append("</a></font></td>")));
						} catch (MissingResourceException e) {
							responseWriteLn(String
									.valueOf((new StringBuffer(
											"<a href=\"javascript:document.FDOWNLOAD.CONTENTTYPE.value='"))
											.append(mimes.getString(""))
											.append("';fdownload();\">")
											.append(fpath.getName())
											.append("</a></font></td>")));
						}
				} else {
					responseWriteLn(String.valueOf(fpath.getName()).concat(
							"</font></td>"));
				}
				responseWriteLn(String
						.valueOf((new StringBuffer(
								"<td align='right' width='25%' bgcolor="))
								.append(colors.getString("PROP_VALUE_BG"))
								.append(">&nbsp;<font face='helv,helvetica' size='1' color=")
								.append(colors.getString("PROP_LINK_ENABLE"))
								.append(">")));
				if (isFile)
					responseWriteLn(String
							.valueOf((new StringBuffer(
									"<a href=\"javascript:document.FDOWNLOAD.CONTENTTYPE.value='';fdownload();\">"))
									.append(labels.getString("8016")).append(
											"</a>&nbsp;</font></td>")));
				else
					responseWriteLn("&nbsp;</font></td>");
				responseWriteLn("</tr>");
				responseWriteLn("</table>");
				responseWriteLn("</td>");
				responseWriteLn("</tr>");
			}

			if (((Integer) m_properties.get("SIZE")).intValue() == 1) {
				responseWriteLn("<tr>");
				responseWriteLn(String.valueOf((new StringBuffer(
						"<td colspan='1' align='left' bgcolor=")).append(
						colors.getString("PROP_VALUE_BG")).append(">")));
				responseWriteLn("<table border='0' cellspacing='0' cellpadding='0'>");
				responseWriteLn("<tr>");
				responseWriteLn(String.valueOf((new StringBuffer(
						"<td nowrap width='120' align='right' bgcolor="))
						.append(colors.getString("PROP_CAPTION_BG"))
						.append("><font face='helv,helvetica' color=")
						.append(colors.getString("PROP_CAPTION"))
						.append(" size='1'>&nbsp;")
						.append(labels.getString("8001"))
						.append("&nbsp;:&nbsp;</font></td>")));
				responseWriteLn(String
						.valueOf((new StringBuffer("<td align='left' bgcolor="))
								.append(colors.getString("PROP_VALUE_BG"))
								.append(">&nbsp;<font face='helv,helvetica' size='1' color=")
								.append(colors.getString("PROP_VALUE"))
								.append(">")));
				long size;
				if (isFile) {
					size = fpath.length();
					responseWriteLn("&nbsp;".concat(String.valueOf(size)));
				} else if (m_showFolderSize) {
					size = getFolderSize(filterListFiles(fpath, "", ""));
					responseWriteLn("&nbsp;".concat(String.valueOf(size)));
				} else {
					size = 0L;
					responseWriteLn("&nbsp;");
				}
				if (size > (long) 1)
					responseWriteLn(String.valueOf((new StringBuffer(" "))
							.append(labels.getString("8019")).append("&nbsp;")));
				else if (m_showFolderSize)
					responseWriteLn(String.valueOf((new StringBuffer(" "))
							.append(labels.getString("8018")).append("&nbsp;")));
				responseWriteLn("</font></td>");
				responseWriteLn("</tr>");
				responseWriteLn("</table>");
				responseWriteLn("</td>");
				responseWriteLn("</tr>");
			}

			if (((Integer) m_properties.get("TYPE")).intValue() == 1) {
				responseWriteLn("<tr>");
				responseWriteLn(String.valueOf((new StringBuffer(
						"<td colspan='1' align='left' bgcolor=")).append(
						colors.getString("PROP_VALUE_BG")).append(">")));
				responseWriteLn("<table border='0' cellspacing='0' cellpadding='0'>");
				responseWriteLn("<tr>");
				responseWriteLn(String.valueOf((new StringBuffer(
						"<td width='120' align='right' bgcolor="))
						.append(colors.getString("PROP_CAPTION_BG"))
						.append("><font face='helv,helvetica' color=")
						.append(colors.getString("PROP_CAPTION"))
						.append(" size='1'>&nbsp;")
						.append(labels.getString("8004"))
						.append("&nbsp;:&nbsp;</font></td>")));
				responseWriteLn(String
						.valueOf((new StringBuffer("<td align='left' bgcolor="))
								.append(colors.getString("PROP_VALUE_BG"))
								.append(">&nbsp;<font face='helv,helvetica' size='1' color=")
								.append(colors.getString("PROP_VALUE"))
								.append(">")));
				if (isFile) {
					try {
						extension = helperLeft(mimes.getString(extension), 32);
					} catch (MissingResourceException e) {
						extension = extension.toUpperCase();
						extension = " ".concat(String.valueOf(extension));
						extension = labels.getString("5000") + extension;
					} catch (Exception exception) {
					}
					responseWriteLn(String.valueOf((new StringBuffer("&nbsp;"))
							.append(extension).append(" &nbsp;")));
				} else {
					responseWriteLn("&nbsp;".concat(String.valueOf(labels
							.getString("5001"))));
				}
				responseWriteLn("</font></td>");
				responseWriteLn("</tr>");
				responseWriteLn("</table>");
				responseWriteLn("</td>");
				responseWriteLn("</tr>");
			}
			if (((Integer) m_properties.get("LOCATION")).intValue() == 1) {
				responseWriteLn("<tr>");
				responseWriteLn(String.valueOf((new StringBuffer(
						"<td colspan='1' align='left' bgcolor=")).append(
						colors.getString("PROP_VALUE_BG")).append(">")));
				responseWriteLn("<table border='0' cellspacing='0' cellpadding='0'>");
				responseWriteLn("<tr>");
				responseWriteLn(String.valueOf((new StringBuffer(
						"<td width='120' align='right' bgcolor="))
						.append(colors.getString("PROP_CAPTION_BG"))
						.append("><font face='helv,helvetica' color=")
						.append(colors.getString("PROP_CAPTION"))
						.append(" size='1'>&nbsp;")
						.append(labels.getString("8006"))
						.append("&nbsp;:&nbsp;</font></td>")));
				responseWriteLn(String
						.valueOf((new StringBuffer("<td align='left' bgcolor="))
								.append(colors.getString("PROP_VALUE_BG"))
								.append(">&nbsp;<font face='helv,helvetica' size='1' color=")
								.append(colors.getString("PROP_VALUE"))
								.append(">")));
				if (isFile)
					responseWriteLn(String.valueOf((new StringBuffer("&nbsp;"))
							.append(relativePath).append(" &nbsp;")));
				else
					responseWriteLn(String.valueOf((new StringBuffer("&nbsp;"))
							.append(relativePath).append(" &nbsp;")));
				responseWriteLn("</font></td>");
				responseWriteLn("</tr>");
				responseWriteLn("</table>");
				responseWriteLn("</td>");
				responseWriteLn("</tr>");
			}
			if (((Integer) m_properties.get("MODIFIED")).intValue() == 1) {
				responseWriteLn("<tr>");
				responseWriteLn(String.valueOf((new StringBuffer(
						"<td colspan='1' align='left' bgcolor=")).append(
						colors.getString("PROP_VALUE_BG")).append(">")));
				responseWriteLn("<table border='0' cellspacing='0' cellpadding='0'>");
				responseWriteLn("<tr bgcolor='#FFFFFF'>");
				responseWriteLn(String.valueOf((new StringBuffer(
						"<td width='120' align='right' bgcolor="))
						.append(colors.getString("PROP_CAPTION_BG"))
						.append("><font face='helv,helvetica' color=")
						.append(colors.getString("PROP_CAPTION"))
						.append(" size='1'>&nbsp;")
						.append(labels.getString("8009"))
						.append("&nbsp;:&nbsp;</font></td>")));
				responseWriteLn(String
						.valueOf((new StringBuffer("<td align='left' bgcolor="))
								.append(colors.getString("PROP_VALUE_BG"))
								.append(">&nbsp;<font face='helv,helvetica' size='1' color=")
								.append(colors.getString("PROP_VALUE"))
								.append(">")));
				Date fileDate = new Date(fpath.lastModified());
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
				tmp = sdf.format(fileDate);
				responseWriteLn(String.valueOf((new StringBuffer("&nbsp;"))
						.append(tmp).append(" &nbsp;")));
				responseWriteLn("</font></td>");
				responseWriteLn("</tr>");
				responseWriteLn("</table>");
				responseWriteLn("</td>");
				responseWriteLn("</tr>");
			}
			responseWriteLn("</table>");
			if (isFile) {
				responseWriteLn(String
						.valueOf((new StringBuffer(
								"<form method=POST  NAME='FDOWNLOAD' action='"))
								.append(scriptPath).append(
										"?QUERY=FILEADM_DOWNLOAD'>")));
				responseWriteLn("<input type=hidden name=CONTENTTYPE value=''>");
				responseWriteLn(String.valueOf((new StringBuffer(
						"<input type=hidden name=RELATIVEPATH value=\""))
						.append(relativePath).append("\">")));
				responseWriteLn(String.valueOf((new StringBuffer(
						"<input type=hidden name=FILENAME value=\"")).append(
						fpath.getName()).append("\">")));
				responseWriteLn("</form>");
				responseWriteLn(String.valueOf((new StringBuffer(
						"<form method=POST  NAME='FICHE' action='")).append(
						scriptPath).append("'>")));
				responseWriteLn(String.valueOf((new StringBuffer(
						"<input type=hidden name=RELATIVEPATH value=\""))
						.append(relativePath).append("\">")));
				responseWriteLn(String.valueOf((new StringBuffer(
						"<input type=hidden name=NAME value=\"")).append(
						fpath.getName()).append("\">")));
				responseWriteLn("</form>");
			} else {
				responseWriteLn(String.valueOf((new StringBuffer(
						"<form method=POST  NAME='FICHE' action='")).append(
						scriptPath).append("'>")));
				responseWriteLn(String.valueOf((new StringBuffer(
						"<input type=hidden name=RELATIVEPATH value=\""))
						.append(relativePath).append("\">")));
				responseWriteLn(String.valueOf((new StringBuffer(
						"<input type=hidden name=NAME value=\"")).append(
						fpath.getName()).append("\">")));
				responseWriteLn("</form>");
			}
			responseWriteLn("</BODY>");
			responseWriteLn("</HTML>");
		}
	}

	private void fileAdmRename() throws IOException {
		if (m_authentication && !auth())
			return;
		String scriptPath = "";
		String relativePath = "";
		String sort = "";
		String sortOrder = "";
		String newName = "";

		String oldName = "";
		String rename = "";
		String error = "";
		String path = "";

		int pos = 0;
		relativePath = m_request.getParameter("RELATIVEPATH");
		sort = m_request.getParameter("SORT");
		sortOrder = m_request.getParameter("SORTORDER");
		newName = m_request.getParameter("RENAME");
		rename = m_request.getParameter("TREATMENT");
		scriptPath = m_request.getRequestURI();
		rename = rename != null ? rename : "";
		pos = relativePath.lastIndexOf(System.getProperty("file.separator"));
		path = relativePath.substring(0, pos);
		if (pos < relativePath.length())
			oldName = relativePath.substring(pos + 1, relativePath.length());
		else
			oldName = relativePath.substring(pos, relativePath.length());
		if (rename.equals("YES")) {
			rename(path, oldName, newName, error);

			responseWriteLn("<HTML>");
			if (!error.equals("")) {
				responseWriteLn(String.valueOf((new StringBuffer(
						"<BODY bgcolor=")).append(colors.getString("PROP_BG"))
						.append(" >")));
				responseWriteLn(String.valueOf((new StringBuffer(
						"<font face=helv,helvetica color='")).append(
						colors.getString("ERR_TITLE")).append("' size='1'>")));
				responseWriteLn(String.valueOf((new StringBuffer("&nbsp;"))
						.append(error).append("&nbsp;")));
				responseWriteLn("</font>");
			} else {
				responseWriteLn(String.valueOf((new StringBuffer(
						"<BODY bgcolor=")).append(colors.getString("PROP_BG"))
						.append(" onload=\"document.SHOW_LIST.submit();\">")));
				responseWriteLn(String.valueOf((new StringBuffer(
						"<form method=POST  NAME=SHOW_LIST action='")).append(
						scriptPath).append(
						"?QUERY=FILEADM_LIST' TARGET='FLIST'>")));
				responseWriteLn(String.valueOf((new StringBuffer(
						"<input type=hidden name=RELATIVEPATH value=\""))
						.append(path).append("\">")));
				responseWriteLn(String.valueOf((new StringBuffer(
						"<input type=hidden name=NAME value=\"")).append(
						newName).append("\">")));
				responseWriteLn(String.valueOf((new StringBuffer(
						"<input type=hidden name=SORT value='")).append(sort)
						.append("'>")));
				responseWriteLn(String.valueOf((new StringBuffer(
						"<input type=hidden name=SORTORDER value='")).append(
						sortOrder).append("'>")));
				responseWriteLn("</form>");
			}
		} else {
			responseWriteLn("<HTML>");
			responseWriteLn("<HEAD>");
			responseWriteLn("<SCRIPT>");
			responseWriteLn("function CheckInput() {");
			responseWriteLn("   if(document.FOLDER_FORM.RENAME.value==''){");
			responseWriteLn(String.valueOf((new StringBuffer("       alert('"))
					.append(labels.getString("1455")).append("');")));
			responseWriteLn("       return false;");
			responseWriteLn("   }");
			responseWriteLn("   if ((document.FOLDER_FORM.RENAME.value.indexOf('\\\\')>=0) || (document.FOLDER_FORM.RENAME.value.indexOf('/')>=0) || (document.FOLDER_FORM.RENAME.value.indexOf(':')>=0) || (document.FOLDER_FORM.RENAME.value.indexOf('*')>=0) || (document.FOLDER_FORM.RENAME.value.indexOf('?')>=0) || (document.FOLDER_FORM.RENAME.value.indexOf('\"')>=0)|| (document.FOLDER_FORM.RENAME.value.indexOf('<')>=0) || (document.FOLDER_FORM.RENAME.value.indexOf('>')>=0) || (document.FOLDER_FORM.RENAME.value.indexOf('|')>=0)) {");
			responseWriteLn(String.valueOf((new StringBuffer("       alert('"))
					.append(labels.getString("1465")).append("');")));
			responseWriteLn("       return false;");
			responseWriteLn("   }");
			responseWriteLn("   myStr=document.FOLDER_FORM.RENAME.value;");
			responseWriteLn("   var i=0;");
			responseWriteLn("   while (myStr.charAt(i)==' ') { i++; }");
			responseWriteLn("   if (i==myStr.length) {");
			responseWriteLn(String.valueOf((new StringBuffer("       alert('"))
					.append(labels.getString("1460")).append("');")));
			responseWriteLn("       return false;");
			responseWriteLn("   }");
			responseWriteLn("   return true;");
			responseWriteLn("}");
			responseWriteLn("function refreshList() { ");
			responseWriteLn("   document.SHOW_LIST.submit();");
			responseWriteLn("}");
			responseWriteLn("function LoadHeader(strMode) { ");
			responseWriteLn("   parent.FPARAM.document.Info.STATE.value = strMode;");
			responseWriteLn("   parent.FPARAM.displayToolbar(strMode);");
			responseWriteLn("}");
			responseWriteLn("</SCRIPT>");
			responseWriteLn("</HEAD>");
			responseWriteLn(String.valueOf((new StringBuffer("<BODY bgcolor="))
					.append(colors.getString("PROP_BG")).append(" onload=\"")));
			responseWriteLn("LoadHeader('RENAME');document.FOLDER_FORM.RENAME.focus();document.FOLDER_FORM.RENAME.select();\">");
			responseWriteLn("<BR>");
			responseWriteLn(String.valueOf((new StringBuffer(
					"<table width=550 bgcolor=")).append(
					colors.getString("PROP_BG")).append(
					" border=0 cellspacing=2 cellpadding=0>")));
			responseWriteLn(String.valueOf((new StringBuffer(
					"<form  method=POST name=FOLDER_FORM action='")).append(
					scriptPath)
					.append("?QUERY=FILEADM_RENAME' TARGET='FPROP'>")));
			responseWriteLn("<input type=HIDDEN name=TREATMENT value='YES'>");
			responseWriteLn(String.valueOf((new StringBuffer(
					"<input type=HIDDEN name=RELATIVEPATH value=\"")).append(
					relativePath).append("\">")));
			responseWriteLn(String.valueOf((new StringBuffer(
					"<input type=HIDDEN name=SORT value='")).append(sort)
					.append("'>")));
			responseWriteLn(String.valueOf((new StringBuffer(
					"<input type=HIDDEN name=SORTORDER value='")).append(
					sortOrder).append("'>")));
			responseWriteLn("<tr>");
			responseWriteLn("<td valign=center align=Left>");
			responseWriteLn(String.valueOf((new StringBuffer(
					"<font size=1 face=helv,helvetica color="))
					.append(colors.getString("PROP_VALUE")).append(">")
					.append(labels.getString("8022"))
					.append("&nbsp;</font></td>")));
			responseWriteLn("<td>");
			responseWriteLn(String
					.valueOf((new StringBuffer(
							"<input type=text size=54 maxlength=250 name=RENAME value=\""))
							.append(oldName).append("\"></td>")));
			responseWriteLn("</tr>");
			responseWriteLn("</form>");
			responseWriteLn("</table>");
		}
		responseWriteLn("</BODY>");
		responseWriteLn("</HTML>");
	}

	private void fileAdmSelectTree() throws IOException {
		String scriptPath = "";
		scriptPath = m_request.getRequestURI();
		if (!m_showPathSelect) {
			responseWriteLn("<html>");
			responseWriteLn("<body>");
			responseWriteLn("</body>");
			responseWriteLn("</html>");
		} else {
			String rootPath = "";
			String relativePath = "";

			relativePath = m_request.getParameter("RELATIVEPATH");

			responseWriteLn("<HTML>");
			responseWriteLn("<HEAD>");
			responseWriteLn("<STYLE>");
			responseWriteLn("<!-- A {text-decoration : none} -->");
			responseWriteLn("</STYLE>");
			responseWriteLn("</HEAD>");
			responseWriteLn(String.valueOf((new StringBuffer("<body bgcolor="))
					.append(colors.getString("FOLDERSELECT_BG")).append(">")));
			responseWriteLn(String.valueOf((new StringBuffer(
					"<table border=0 cellpadding=0 cellspacing=0 bgcolor="))
					.append(colors.getString("FOLDERSELECT_BG")).append(">")));
			responseWriteLn(String.valueOf((new StringBuffer(
					"<form method=POST  NAME=LIST_SELECT action='")).append(
					scriptPath).append("?QUERY=FILEADM_LIST' target=FLIST>")));
			responseWriteLn("<tr>");

			responseWriteLn(String.valueOf((new StringBuffer(
					"<td valign=left bgcolor=")).append(
					colors.getString("FOLDERSELECT_BG")).append(">&nbsp;")));
			responseWriteLn("<SELECT SIZE=1 name=RELATIVEPATH onChange=\"");
			responseWriteLn(String
					.valueOf((new StringBuffer(
							"if (document.LIST_SELECT.RELATIVEPATH.options[document.LIST_SELECT.RELATIVEPATH.selectedIndex].text!='"))
							.append(m_fileSeparator).append("') {")));
			responseWriteLn("   parent.FLIST.document.SHOW_LIST.RELATIVEPATH.value=document.LIST_SELECT.RELATIVEPATH.options[document.LIST_SELECT.RELATIVEPATH.selectedIndex].text;");
			responseWriteLn("}else{");
			responseWriteLn("   parent.FLIST.document.SHOW_LIST.RELATIVEPATH.value='';");
			responseWriteLn("}");
			responseWriteLn("parent.FLIST.document.SHOW_LIST.REFRESHFRAMES.value='NOTOPEN';");
			responseWriteLn("parent.FLIST.document.SHOW_LIST.submit();\">");
			if (m_fileSeparator.equals("/"))
				responseWriteLn(String.valueOf((new StringBuffer(
						"<OPTION value=''>")).append(m_fileSeparator).append(
						"</OPTION>")));
			else
				responseWriteLn("<OPTION value=''>\\</OPTION>");
			rootPath = getPhysicalPath(m_rootPath);
			File fpath = new File(rootPath);
			File arrayElements[] = filterListFiles(fpath, "", "");
			getTree(arrayElements, relativePath);
			responseWriteLn("</SELECT>");
			responseWriteLn("</td>");
			responseWriteLn("</tr>");
			responseWriteLn("</table>");
			responseWriteLn("</form>");
			responseWriteLn("</body>");
			responseWriteLn("</html>");
		}
	}

	private File[] filterListFiles(File dir, String folderFilter,
			String fileFilter) {
		if (dir.isDirectory()) {
			String listFiles[] = dir.list();
			Vector fileVector = new Vector();
			try {
				for (int i = 0; i < listFiles.length; i++) {
					File aFile = new File(String.valueOf((new StringBuffer(
							String.valueOf(dir))).append(
							System.getProperty("file.separator")).append(
							listFiles[i])));
					if ((aFile.isDirectory()
							&& !matchWildcard(listFiles[i], folderFilter) || aFile
							.isFile()
							&& !matchWildcard(listFiles[i], fileFilter))
							&& (!isHidden(aFile) || m_showHiddenFiles))
						fileVector.addElement(aFile);
				}

			} catch (Exception e) {
				System.out.println("filterListFiles error : ".concat(String
						.valueOf(e.getMessage())));
			}
			int size = fileVector.size();
			if (size == 0) {
				return new File[0];
			} else {
				File f[] = new File[size];
				fileVector.copyInto(f);
				return f;
			}
		} else {
			return null;
		}
	}

	private String findContextPath() throws IOException, NoSuchMethodException {
		if (m_contextPath != null)
			return m_contextPath;
		String requestURI;
		String servletPath;
		try {
			m_contextPath = m_request.getContextPath();
			String s = m_request.getContextPath();
			return s;
		} catch (Exception e) {
			requestURI = "";
		}
		servletPath = "";
		requestURI = m_request.getRequestURI() != null ? m_request
				.getRequestURI() : "";
		servletPath = m_request.getServletPath() != null ? m_request
				.getServletPath() : "";
		m_contextPath = requestURI
				.substring(0, requestURI.indexOf(servletPath));
		String s2 = requestURI.substring(0, requestURI.indexOf(servletPath));
		return s2;
	}

	public String getAllowedFilesList() {
		return m_allowedFilesList.toString();
	}

	public String getColor(String key) {
		return colors.getString(key);
	}

	public String getContextPath() {
		return m_contextPath;
	}

	public String getDeniedFilesList() {
		return m_deniedFilesList.toString();
	}

	public boolean getDenyPhysicalPath() {
		return m_denyPhysicalPath;
	}

	public long getDiskSpaceQuota() {
		return m_quota;
	}

	private String getExtension(String myFileName) {
		int index = myFileName.lastIndexOf(".") + 1;
		if (index > 0)
			return myFileName.substring(index, myFileName.length());
		else
			return null;
	}

	public String getFileExtension() {
		return m_fileExtension;
	}

	public int getFileListDefaultSortField() {
		return m_defaultSortField;
	}

	public int getFileListDefaultSortOrder() {
		return m_defaultSortOrder;
	}

	public int getFileListPercentSize() {
		return m_fileListPercentSize;
	}

	public String getFileMimeType() {
		return m_mimeType;
	}

	public String getFileName() {
		return m_fileName;
	}

	public String getFilePhysicalPath() {
		return m_filePhysicalPath;
	}

	public String getFileRelativePath() {
		return m_fileRelativePath;
	}

	public String getFileVirtualPath() {
		return m_fileVirtualPath;
	}

	public String getFolderName() {
		return m_folderName;
	}

	public String getFolderPhysicalPath() {
		return m_folderPhysicalPath;
	}

	public String getFolderRelativePath() {
		return m_folderRelativePath;
	}

	private long getFolderSize(File myFolder[]) throws IOException {
		long size = 0L;
		for (int i = 0; i < myFolder.length; i++)
			if (myFolder[i].isFile())
				size += myFolder[i].length();
			else
				size += getFolderSize(filterListFiles(myFolder[i], "*", "*"));

		return size;
	}

	public String getFolderVirtualPath() {
		return m_folderVirtualPath;
	}

	public boolean getForceAuthentication() {
		return m_authentication;
	}

	private String getIcons(String key) {
		return m_iconsPath + icons.getString(key);
	}

	public String getIconsPath() {
		return m_iconsPath;
	}

	private String getImages(String key) {
		return m_imagesPath + images.getString(key);
	}

	public String getImagesPath() {
		return m_imagesPath;
	}

	public int getInputFileSize() {
		return m_inputFileSize;
	}

	public String getJSEventHandler() {
		return m_jsEventHandler;
	}

	public Locale getLanguage() {
		return currentLocale;
	}

	public long getMaxFileSize() {
		return m_maxFileSize;
	}

	public boolean getOverWrite() {
		return m_overWrite;
	}

	private String getPhysicalPath(String path) throws IOException {
		String rootPath = null;
		if (m_isVirtual) {
			path = path.replace('\\', '/');
			if (m_application.getRealPath(path) != null) {
				File virtualFile = new File(m_application.getRealPath(path));
				if (virtualFile.exists()) {
					rootPath = virtualFile.getAbsolutePath();
				}
			}
		} else {
			File physicalFile = new File(path);
			if (physicalFile.exists()) {
				rootPath = physicalFile.getAbsolutePath();
			}
		}

		if (StringUtils.isBlank(rootPath) || StringUtils.equals(rootPath, "/")) {
			rootPath = m_application.getRealPath("/");
		}

		return rootPath;
	}

	public Hashtable getProperties() {
		return m_properties;
	}

	public boolean getProperty(String name) {
		Integer n = (Integer) m_properties.get(name);
		return n.intValue() != 0;
	}

	private String getQueryString(String s) {
		Hashtable hashtable = null;
		try {
			hashtable = this.parseQueryString(m_request.getQueryString());
			String as[] = (String[]) hashtable.get(s);
			return as[0];
		} catch (Exception exception) {
			return null;
		}
	}

	public boolean getReadOnly() {
		return m_readOnly;
	}

	public int getRequestedAction() {
		return m_requestedAction;
	}

	public boolean getShowFolderSize() {
		return m_showFolderSize;
	}

	public boolean getShowHiddenFiles() {
		return m_showHiddenFiles;
	}

	public boolean getShowPathSelect() {
		return m_showPathSelect;
	}

	public boolean getShowProperties() {
		return m_showProperties;
	}

	public boolean getShowToolbarText() {
		return m_showToolbarText;
	}

	public String getStylesPath() {
		return m_stylesPath;
	}

	public String getTitle() {
		return m_title;
	}

	private void getTree(File array[], String relativePath) throws IOException {
		String tmp = "";
		String rootPath = "";
		for (int i = 0; i < array.length; i++) {
			if (!array[i].isDirectory())
				continue;
			File newPath = new File(array[i].getAbsolutePath());
			File newArray[] = filterListFiles(newPath, "", "");
			rootPath = getPhysicalPath(m_rootPath);
			tmp = newPath.getPath();
			tmp = tmp.substring(rootPath.length(), tmp.length());
			if (relativePath.equals(tmp))
				responseWriteLn(String.valueOf((new StringBuffer(
						"<OPTION SELECTED>")).append(tmp).append("</OPTION>")));
			else
				responseWriteLn(String.valueOf((new StringBuffer("<OPTION>"))
						.append(tmp).append("</OPTION>")));
			getTree(newArray, relativePath);
		}

	}

	public String getUnvisibleFilesList() {
		return m_unvisibleFilesList;
	}

	public String getUnvisibleFoldersList() {
		return m_unvisibleFoldersList;
	}

	private String helperLeft(String myString, int size) {
		if (myString.length() == size)
			return String.valueOf(myString.substring(0, size)).concat("...");
		else
			return myString;
	}

	public final void init(ServletConfig config) throws ServletException {
		m_config = config;
		m_application = config.getServletContext();
		m_initialize = 1;
	}

	public void initExplorer() throws NoSuchMethodException,
			FileUploadException, ServletException, IOException {
		if (m_authentication && !auth()) {
			return;
		} else {
			initExplorer("/");
			return;
		}
	}

	public void initExplorer(String path) throws NoSuchMethodException,
			FileUploadException, ServletException, IOException {
		if (m_authentication && !auth()) {
			return;
		} else {
			initExplorer(path, "", 1);
			return;
		}
	}

	public void initExplorer(String path, int option)
			throws NoSuchMethodException, FileUploadException,
			ServletException, IOException {
		if (m_authentication && !auth()) {
			return;
		} else {
			initExplorer(path, "", option);
			return;
		}
	}

	public void initExplorer(String path, String defaultPath)
			throws NoSuchMethodException, FileUploadException,
			ServletException, IOException {
		if (m_authentication && !auth()) {
			return;
		} else {
			initExplorer(path, defaultPath, 1);
			return;
		}
	}

	public void initExplorer(String path, String defaultPath, int option)
			throws NoSuchMethodException, FileUploadException,
			ServletException, IOException {
		if (m_authentication && !auth())
			return;
		String strRootPath = "";
		String strRelativePath = "";
		String strPlace = "";
		String strPath = "";
		String strQuery = "";
		boolean pathIsValid = false;
		strQuery = getQueryString("QUERY");
		if (strQuery == null)
			strQuery = m_request.getParameter("QUERY");
		strQuery = strQuery == null ? "" : strQuery;
		if (!strQuery.equals("FILEADM_NEWFILE") && !strQuery.equals("YES")) {
			strRelativePath = getQueryString("RELATIVEPATH");
			if (strRelativePath == null)
				strRelativePath = m_request.getParameter("RELATIVEPATH");
		}
		strRelativePath = strRelativePath == null ? "" : strRelativePath;
		if (option == 1) {
			if (m_application.getRealPath(path) != null) {
				File virtualFile = new File(m_application.getRealPath(path));
				if (virtualFile.exists()) {
					strRootPath = m_application.getRealPath(path);
					m_isVirtual = true;
					pathIsValid = true;
				}
			}
		} else {
			m_isVirtual = false;
			if (m_denyPhysicalPath) {
				responseWriteLn(labels.getString("1500"));
			} else {
				File physicalFile = new File(path);
				if (!physicalFile.exists()) {
					responseWriteLn(labels.getString("1501"));
				} else {
					strRootPath = path;
					pathIsValid = true;
				}
			}
		}
		if (pathIsValid) {
			File rootFile = new File(strRootPath);
			m_fileSeparator = System.getProperty("file.separator");
			if (m_fileSeparator.equals("\\"))
				m_fileSeparator = "\\\\";
			m_rootPath = path;
			m_strDefaultPath = "";
			if (!defaultPath.equals(""))
				if (option == 1
						&& m_application.getRealPath(defaultPath) != null) {
					File defaultFile = new File(
							m_application.getRealPath(defaultPath));
					if (defaultFile.exists()
							&& isSubFolder(defaultFile, rootFile)) {
						if (!path.equals("/"))
							defaultPath = defaultPath.substring(path.length(),
									defaultPath.length());
						for (int i = 0; i < defaultPath.length(); i++)
							if (defaultPath.charAt(i) == '/')
								m_strDefaultPath = String.valueOf(
										m_strDefaultPath).concat("\\\\");
							else if (defaultPath.charAt(i) == '\'')
								m_strDefaultPath = String.valueOf(
										m_strDefaultPath).concat("\\'");
							else
								m_strDefaultPath = m_strDefaultPath
										+ defaultPath.charAt(i);

					}
				} else {
					File defaultFile = new File(defaultPath);
					if (defaultFile.exists()
							&& isSubFolder(defaultFile, rootFile)) {
						if (!path.equals("/"))
							defaultPath = defaultPath.substring(path.length(),
									defaultPath.length());
						for (int i = 0; i < defaultPath.length(); i++) {
							if (defaultPath.charAt(i) == '\\') {
								m_strDefaultPath = String.valueOf(
										m_strDefaultPath).concat("\\\\");
								continue;
							}
							if (defaultPath.charAt(i) == '\'')
								m_strDefaultPath = String.valueOf(
										m_strDefaultPath).concat("\\'");
							else
								m_strDefaultPath = m_strDefaultPath
										+ defaultPath.charAt(i);
						}

					}
				}
			strPath = strReplace(m_rootPath + strRelativePath, "//", "/");
			strPath = getPhysicalPath(strPath);
			File file = new File(strPath);
			if (m_isVirtual)
				strPlace = strRelativePath.replace('\\', '/');
			else
				strPlace = strRelativePath;
			String contextPath = "";
			contextPath = m_isVirtual ? findContextPath() : "";
			if (contextPath.endsWith("/"))
				contextPath = contextPath
						.substring(0, contextPath.length() - 2);
			if (!m_rootPath.equals("/"))
				contextPath = contextPath + m_rootPath;
			if (file.isDirectory()) {
				m_fileName = "";
				m_fileExtension = "";
				m_mimeType = "";
				m_folderName = file.getName();
				m_folderPhysicalPath = file.getAbsolutePath();
				m_folderRelativePath = strPlace;
				m_folderVirtualPath = m_isVirtual ? contextPath + strPlace : "";
				m_filePhysicalPath = "";
				m_fileRelativePath = "";
				m_fileVirtualPath = "";
			} else {
				m_fileName = file.getName();
				m_fileExtension = getExtension(file.getName());
				try {
					m_mimeType = mimes.getString(m_fileExtension);
				} catch (MissingResourceException e) {
					m_mimeType = String.valueOf((new StringBuffer(String
							.valueOf(labels.getString("5000")))).append(" ")
							.append(m_fileExtension.toUpperCase()));
				} catch (Exception exception) {
				}
				m_filePhysicalPath = file.getAbsolutePath();
				m_fileRelativePath = strPlace;
				m_fileVirtualPath = m_isVirtual ? contextPath + strPlace : "";
				m_folderName = "";
				m_folderPhysicalPath = "";
				m_folderRelativePath = "";
				m_folderVirtualPath = "";
			}
			if (strQuery.equals("FILEADM_PROP"))
				if (StringUtils.isNotEmpty(m_fileName))
					m_requestedAction = 3;
				else
					m_requestedAction = 4;
			if (strQuery.equals("FILEADM_NEWFOLDER"))
				m_requestedAction = 2;
			if (strQuery.equals("FILEADM_NEWFILE"))
				m_requestedAction = 1;
		}
	}

	public final void initialize(PageContext pageContext)
			throws ServletException, IOException {
		m_pageContext = pageContext;
		m_application = pageContext.getServletContext();
		m_request = (HttpServletRequest) pageContext.getRequest();
		m_response = (HttpServletResponse) pageContext.getResponse();
		m_outNew = pageContext.getOut();
		m_initialize = 3;
	}

	public final void initialize(ServletConfig config,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		m_config = config;
		m_application = config.getServletContext();
		m_request = request;
		m_response = response;
		m_initialize = 2;
	}

	private boolean isHidden(File file) throws IOException,
			NoSuchMethodException {
		boolean flag2;
		try {
			boolean flag = file.isHidden();
			return flag;
		} catch (Exception e) {
			flag2 = false;
		}
		return flag2;
	}

	private boolean isSubFolder(File subFolder, File parentFolder)
			throws IOException, NoSuchMethodException {
		if (subFolder.exists() && subFolder.getParent() != null) {
			if (parentFolder.getPath().equals(subFolder.getParent()))
				return true;
			else
				return isSubFolder(new File(subFolder.getParent()),
						parentFolder);
		} else {
			return false;
		}
	}

	private boolean matchWildcard(String strChaine, String strListeMotifs) {

		int nbFiltres = 0;

		try {
			if (strChaine == null || strChaine.equals("")
					|| strListeMotifs == null || strListeMotifs.equals("")) {
				boolean flag = false;
				return flag;
			}
			StringTokenizer st = new StringTokenizer(strListeMotifs, ",");
			String tabFiltre[] = (String[]) Array.newInstance(
					Class.forName("java.lang.String"), st.countTokens());
			while (st.hasMoreTokens()) {
				Array.set(tabFiltre, nbFiltres, st.nextToken());
				nbFiltres++;
			}

			for (int i = 0; i < nbFiltres; i++) {
				String strFiltreCourant = tabFiltre[i];
				WildCard w = new WildCard(strFiltreCourant);
				if (w.Match(strChaine)) {
					boolean flag2 = true;
					return flag2;
				}
			}

		} catch (Exception e) {
			boolean flag1 = false;
			return flag1;
		}
		return false;
	}

	private String parseName(String s, StringBuffer sb) {
		sb.setLength(0);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '+':
				sb.append(' ');
				break;
			case '%':
				try {
					sb.append((char) Integer.parseInt(
							s.substring(i + 1, i + 3), 16));
					i += 2;
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException();
				} catch (StringIndexOutOfBoundsException e) {
					String rest = s.substring(i);
					sb.append(rest);
					if (rest.length() == 2)
						i++;
				}

				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	private Hashtable parseQueryString(String queryString) {
		String valArray[] = null;

		if (queryString == null) {
			throw new IllegalArgumentException();
		}
		Hashtable ht = new Hashtable();
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(queryString, "&");
		while (st.hasMoreTokens()) {
			String pair = (String) st.nextToken();
			int pos = pair.indexOf('=');
			if (pos == -1) {
				throw new IllegalArgumentException();
			}
			String key = parseName(pair.substring(0, pos), sb);
			String val = parseName(pair.substring(pos + 1, pair.length()), sb);
			if (ht.containsKey(key)) {
				String oldVals[] = (String[]) ht.get(key);
				valArray = new String[oldVals.length + 1];
				for (int i = 0; i < oldVals.length; i++)
					valArray[i] = oldVals[i];
				valArray[oldVals.length] = val;
			} else {
				valArray = new String[1];
				valArray[0] = val;
			}
			ht.put(key, valArray);
		}
		return ht;
	}

	private String pathEncode(String myPath) {
		String tmp = "";
		for (int i = 1; i < myPath.length(); i++) {
			if (myPath.charAt(i) == '\\') {
				tmp = String.valueOf(tmp).concat("\\\\");
				continue;
			}
			if (myPath.charAt(i) == '\'')
				tmp = String.valueOf(tmp).concat("\\'");
			else
				tmp = tmp + myPath.charAt(i);
		}

		return tmp;
	}

	private void rename(String path, String oldName, String newName,
			String error) throws IOException {
		String rootPath = "";

		String oldPathName = "";
		String newPathName = "";
		String extension = "";

		rootPath = getPhysicalPath(m_rootPath);
		oldPathName = String
				.valueOf((new StringBuffer(String.valueOf(rootPath)))
						.append(path).append(m_fileSeparator).append(oldName));
		newPathName = String
				.valueOf((new StringBuffer(String.valueOf(rootPath)))
						.append(path).append(m_fileSeparator).append(newName));
		File oldFile = new File(oldPathName);
		File newFile = new File(newPathName);
		boolean isFile = oldFile.isFile();
		if (newFile.exists() && isFile) {
			responseWriteLn("<SCRIPT>");
			responseWriteLn(String.valueOf((new StringBuffer("alert('"))
					.append(labels.getString("1490")).append("');")));
			responseWriteLn("parent.FLIST.document.SHOW_LIST.submit();");
			responseWriteLn("</SCRIPT>");
		}
		if (newFile.exists() && !isFile) {
			responseWriteLn("<SCRIPT>");
			responseWriteLn(String.valueOf((new StringBuffer("alert('"))
					.append(labels.getString("1495")).append("');")));
			responseWriteLn("parent.FLIST.document.SHOW_LIST.submit();");
			responseWriteLn("</SCRIPT>");
		}
		if (isFile) {
			extension = newName.substring(newName.lastIndexOf(".") + 1,
					newName.length());
			if (extension.equals(newName))
				extension = "";
			extension = extension != null ? extension : "";
			if (m_deniedFilesList.contains(extension)) {
				responseWriteLn("<SCRIPT>");
				responseWriteLn(String.valueOf((new StringBuffer("alert('"))
						.append(labels.getString("1015")).append("');")));
				responseWriteLn("parent.FLIST.document.SHOW_LIST.submit();");
				responseWriteLn("</SCRIPT>");
				return;
			}
			if (!m_allowedFilesList.isEmpty()
					&& !m_allowedFilesList.contains(extension)) {
				responseWriteLn("<SCRIPT>");
				responseWriteLn(String.valueOf((new StringBuffer("alert('"))
						.append(labels.getString("1010")).append("');")));
				responseWriteLn("parent.FLIST.document.SHOW_LIST.submit();");
				responseWriteLn("</SCRIPT>");
				return;
			}
		}
		try {
			if (isFile)
				FileHandle.moveFile(oldPathName, newPathName);
			else
				FileHandle.moveFolder(oldPathName, newPathName);
		} catch (Exception e) {
			responseWriteLn("<SCRIPT>");
			if (isFile)
				responseWriteLn(String.valueOf((new StringBuffer("alert('"))
						.append(labels.getString("1426")).append("');")));
			else
				responseWriteLn(String.valueOf((new StringBuffer("alert('"))
						.append(labels.getString("1436")).append("');")));
			responseWriteLn("parent.FLIST.document.SHOW_LIST.submit();");
			responseWriteLn("</SCRIPT>");
		}
	}

	private void responseWriteLn(String str) throws IOException {
		if (m_initialize == 1 || m_initialize == 2) {
			m_outOld = m_response.getWriter();
			m_outOld.println(str);
		} else {
			m_outNew.println(str);
		}
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		m_request = request;
		m_response = response;
	}

	public void setAllowedFilesList(String allowedFilesList) {
		int pos = 1;
		String ext = "";
		m_strAllowedFilesList = allowedFilesList;
		if (allowedFilesList != null) {
			for (int i = 0; i < allowedFilesList.length(); i++) {
				ext = ext + allowedFilesList.charAt(i);
				if (allowedFilesList.charAt(i) == ',') {
					ext = ext.substring(0, ext.length() - 1);
					m_allowedFilesList.put(new Integer(pos), ext);
					ext = "";
					pos++;
				}
				if (i + 1 == allowedFilesList.length()
						&& StringUtils.isNotEmpty(ext)) {
					m_allowedFilesList.put(new Integer(pos), ext);
					ext = "";
					pos++;
				}
			}

			if (allowedFilesList.indexOf(",,") >= 0)
				m_allowedFilesList.put(new Integer(pos), "");
		} else {
			m_allowedFilesList = null;
		}
	}

	public void setContextPath(String contextPath) {
		m_contextPath = contextPath;
	}

	public void setDeniedFilesList(String deniedFilesList) throws IOException,
			ServletException {
		int pos = 1;
		String ext = "";
		m_strDeniedFilesList = deniedFilesList;
		if (deniedFilesList != null) {
			for (int i = 0; i < deniedFilesList.length(); i++) {
				ext = ext + deniedFilesList.charAt(i);
				if (deniedFilesList.charAt(i) == ',') {
					ext = ext.substring(0, ext.length() - 1);
					m_deniedFilesList.put(new Integer(pos), ext);
					ext = "";
					pos++;
				}
				if (i + 1 == deniedFilesList.length()
						&& StringUtils.isNotEmpty(ext)) {
					m_deniedFilesList.put(new Integer(pos), ext);
					ext = "";
					pos++;
				}
			}

			if (deniedFilesList.indexOf(",,") >= 0)
				m_deniedFilesList.put(new Integer(pos), "");
		} else {
			m_deniedFilesList = null;
		}
	}

	public void setDenyPhysicalPath(boolean denyPhysicalPath) {
		m_denyPhysicalPath = denyPhysicalPath;
	}

	public void setDiskSpaceQuota(long diskSpaceQuota) {
		m_quota = diskSpaceQuota;
	}

	public void setFileListDefaultSortField(int defaultSortField) {
		if (defaultSortField > 0 && defaultSortField < 5)
			m_defaultSortField = defaultSortField;
	}

	public void setFileListDefaultSortOrder(int defaultSortOrder) {
		if (defaultSortOrder == 0 || defaultSortOrder == 1)
			m_defaultSortOrder = defaultSortOrder;
	}

	public void setFileListPercentSize(int fileListPercentSize) {
		if (fileListPercentSize >= 0 && fileListPercentSize <= 100)
			m_fileListPercentSize = fileListPercentSize;
	}

	public void setForceAuthentication(boolean authentication) {
		m_authentication = authentication;
	}

	public void setIconsPath(String iconsPath) {
		m_iconsPath = String.valueOf(iconsPath).concat("/");
	}

	public void setImagesPath(String imagesPath) {
		m_imagesPath = String.valueOf(imagesPath).concat("/");
	}

	public void setInputFileSize(int inputFileSize) {
		m_inputFileSize = inputFileSize;
	}

	public void setJSEventHandler(String jsEventHandler) {
		m_jsEventHandler = jsEventHandler;
	}

	public void setLanguage(String language, String country) {
		if (language != null && country != null) {
			currentLocale = new Locale(language, country);
			labels = ResourceBundle.getBundle(
					"com.glaf.cms.webfile.WebFileLabels", currentLocale);
			try {
				mimes = ResourceBundle.getBundle(
						"com.glaf.cms.webfile.WebFileMIMETypes", currentLocale);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			try {
				images = ResourceBundle.getBundle(
						"com.glaf.cms.webfile.WebFileImages", currentLocale);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			try {
				colors = ResourceBundle.getBundle(
						"com.glaf.cms.webfile.WebFileColors", currentLocale);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			try {
				icons = ResourceBundle.getBundle(
						"com.glaf.cms.webfile.WebFileIcons", currentLocale);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void setMaxFileSize(long maxFileSize) {
		m_maxFileSize = maxFileSize;
	}

	public void setOverWrite(boolean overWrite) {
		m_overWrite = overWrite;
	}

	public void setProperty(String name, boolean show) {
		if (show)
			m_properties.put(name, new Integer(1));
		else
			m_properties.put(name, new Integer(0));
	}

	public void setReadOnly(boolean readOnly) {
		m_readOnly = readOnly;
	}

	public void setShowFolderSize(boolean showFolderSize) {
		m_showFolderSize = showFolderSize;
	}

	public void setShowHiddenFiles(boolean showHiddenFiles) {
		m_showHiddenFiles = showHiddenFiles;
	}

	public void setShowPathSelect(boolean showPathSelect) {
		m_showPathSelect = showPathSelect;
	}

	public void setShowProperties(boolean showProperties) {
		m_showProperties = showProperties;
	}

	public void setShowToolbarText(boolean showToolbarText) {
		m_showToolbarText = showToolbarText;
	}

	public void setStylesPath(String stylesPath) {
		m_stylesPath = String.valueOf(stylesPath);
	}

	public void setTitle(String title) {
		m_title = title;
	}

	public void setUnvisibleFilesList(String unvisibleFilesList) {
		m_unvisibleFilesList = unvisibleFilesList;
	}

	public void setUnvisibleFoldersList(String unvisibleFoldersList) {
		m_unvisibleFoldersList = unvisibleFoldersList;
	}

	public void showDefaultAction() throws NoSuchMethodException,
			FileUploadException, ServletException, IOException {
		if (m_isVirtual)
			showExplorer(m_rootPath);
		else
			showExplorer(m_rootPath, 2);
	}

	public void showExplorer() throws NoSuchMethodException,
			FileUploadException, ServletException, IOException {
		if (m_authentication && !auth()) {
			return;
		} else {
			showExplorer("/");
			return;
		}
	}

	public void showExplorer(String path) throws NoSuchMethodException,
			FileUploadException, ServletException, IOException {
		if (m_authentication && !auth()) {
			return;
		} else {
			showExplorer(path, VIRTUAL);
			return;
		}
	}

	public void showExplorer(String path, int option)
			throws NoSuchMethodException, FileUploadException,
			ServletException, IOException {
		boolean pathIsValid = false;
		String query = "";
		if (m_authentication && !auth())
			return;
		if (option == VIRTUAL) {
			if (m_application.getRealPath(path) != null) {
				File virtualFile = new File(m_application.getRealPath(path));
				if (virtualFile.exists()) {
					m_isVirtual = true;
					pathIsValid = true;
				}
			}
			if (!pathIsValid)
				responseWriteLn(labels.getString("1500"));
		} else {
			m_isVirtual = false;
			if (m_denyPhysicalPath) {
				responseWriteLn(labels.getString("1500"));
			} else {
				File physicalFile = new File(path);
				if (!physicalFile.exists())
					responseWriteLn(labels.getString("1501"));
				else
					pathIsValid = true;
			}
		}
		if (pathIsValid) {
			m_fileSeparator = System.getProperty("file.separator");
			if (m_fileSeparator.equals("\\"))
				m_fileSeparator = "\\\\";
			m_rootPath = path;
			query = getQueryString("QUERY");
			if (query == null)
				query = m_request.getParameter("QUERY");
			if (query == null) {
				fileAdmFrame();
			} else {
				if (query.equals("FILEADM_PARAM"))
					fileAdmParam();
				if (query.equals("FILEADM_LIST"))
					fileAdmList();
				if (query.equals("FILEADM_SELECTTREE"))
					fileAdmSelectTree();
				if (query.equals("FILEADM_PROP"))
					fileAdmProperties();
				if (query.equals("FILEADM_NEWFOLDER"))
					fileAdmNewFolder();
				if (query.equals("FILEADM_DELETE"))
					fileAdmDelete();
				if (query.equals("FILEADM_NEWFILE"))
					fileAdmNewFile();
				if (query.equals("FILEADM_RENAME"))
					fileAdmRename();
				if (query.equals("FILEADM_PASTE"))
					fileAdmPaste();
				if (query.equals("FILEADM_DOWNLOAD"))
					fileAdmDownLoad();
			}
		}
	}

	private String strReplace(String str, String search, String replace) {
		if (str == null || search == null || replace == null) {
			return str;
		}
		String tmp = "";
		int pos = 0;
		tmp = str;
		for (pos = tmp.indexOf(search); pos >= 0; pos = tmp.indexOf(search)) {
			tmp = String.valueOf((new StringBuffer(String.valueOf(tmp
					.substring(0, pos)))).append(replace).append(
					tmp.substring(pos + search.length(), tmp.length())));
		}
		return tmp;
	}

	private String urlEncode(String strIn) throws IOException {
		String strCar = "";
		String strTmp = "";
		for (int i = 0; i < strIn.length(); i++) {
			strCar = strIn.substring(i, i + 1);
			if (strCar.equals(" "))
				strTmp = String.valueOf(strTmp).concat("%20");
			if (strCar.equals("#"))
				strTmp = String.valueOf(strTmp).concat("%23");
			if (!strCar.equals("#") && !strCar.equals(" "))
				strTmp = strTmp + strCar;
		}

		return strTmp;
	}

}
