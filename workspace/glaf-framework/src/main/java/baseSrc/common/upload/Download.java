//================================================================================================
//项目名称 ：    基盘
//功    能 ：    文件下载控制
//文件名称 ：    Download.java                                   
//描    述 ：    
//================================================================================================
//修改履历                                                                								标识        
//年 月 日		区分			所 属/担 当           内 容									标识        
//----------   ----   -------------------- ---------------                          ------        
//2009/4/28   	编写   		Intasect/李闻海     新規作成                                                                            
//================================================================================================

package baseSrc.common.upload;

import baseSrc.common.Constants;
import baseSrc.framework.BaseConstants;
import baseSrc.framework.BaseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class Download extends HttpServlet {

	private static final long serialVersionUID = 1L;
    //上传文件路径
	public static final String UPLOAD_DIR = System.getProperty("WEBPATH") + Constants.UPLOAD_DIR;
   
	protected HttpServletRequest m_request;
    
	protected HttpServletResponse m_response;
	
	protected ServletContext m_application;
    //系统资源文件对象
	public static MessageResources   resources;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		resources   =  ((MessageResources)   getServletContext().getAttribute(Globals.MESSAGES_KEY));
		m_application = getServletContext();
		m_request = request;
		m_response = response;
		m_response.setCharacterEncoding("GBK");
		String fileId = m_request.getParameter("fileId");
		downloadFileById(fileId,response);
		
	}

	/**
	 * 根据页面传回的文件ID，下载文件
	 * @param id
	 * @throws ServletException
	 * @throws IOException
	 */
	public void downloadFileById(String id,HttpServletResponse response) throws ServletException,
			IOException {
		//文件路径
		String attachmentUrl = "";
		//文件名
		String fileName = "";
		
		//加密标示
		String flag = "";
		
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		try {
			Map map = new HashMap();
			map.put("fid", id);
			//使用容器初始化DbAccess
			BasicDataSource htm = (BasicDataSource)context.getBean("dataSource");
			Connection conn =  htm.getConnection();
			String sql = "select f_id,F_URL,F_FLAG,F_NAME from t_attachment where f_id=" + id;
			PreparedStatement pstmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY) ;
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				attachmentUrl = rs.getString("F_URL");
				fileName = rs.getString("F_NAME");
				flag = rs.getString("F_FLAG");
			}
			
			/**
			DbAccess dbAccess = (DbAccess)context.getBean("iscDbAccess");
			//获取附件对象
			List list = dbAccess.find("Attachment", "where F_ID=:fid", map);
			if (list.size() > 0) {
				Attachment attachment = (Attachment) list.get(0);
				attachmentUrl = attachment.getUrl();
				fileName = attachment.getName();
			}
			**/
			
			//关闭数据集
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new BaseException(e);
				}
			}
			//关闭预编译查询语句
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					throw new BaseException(e);
				}
			}
			//关闭数据库链接
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new BaseException(e);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			downloadFile(attachmentUrl, 2048, fileName,flag);
		} catch (FileNotFoundException e) {			
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head><body><script type=\"text/javascript\" language=\"Javascript\">alert(\""+resources.getMessage("commonDownload.downLoadError")+"\");</script></body></html>");
			response.getWriter().flush();
		}
	}



	/**
	 * 输出文件到客户端
	 * @param attachmentUri　文件地址
	 * @param bufferSize 缓冲区大小
	 * @throws ServletException
	 * @throws IOException
	 */
	public void downloadFile(String attachmentUri, int bufferSize, String fileName,String flag)
			throws ServletException, IOException {
		// 文件地址必须输入
		if (attachmentUri == null || attachmentUri.length() == 0) {
			throw new IllegalArgumentException("File '" + attachmentUri
					+ "' not found (1040).");
		}

		// 检查文件是否存在
		attachmentUri = getVirtualPath(attachmentUri);

		//解密加密文件
		if(BaseConstants.UPLOAD_DB_ZIP_PASW_FLAG.equals(flag)){
			try {
				ZipCipherUtil.decryptUnzip(attachmentUri.substring(0,attachmentUri.lastIndexOf(".")) + "." + BaseConstants.UPLOAD_ZIP_PASW_LASTNAME ,
						attachmentUri.replace(fileName, ""),
						BaseConstants.ISC_ZIP_PASSWORD);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 流格式输出
		OutputStream outs = null;
		FileInputStream fileinputstream = null;
		m_response.reset();
		try {
			File file = new File(attachmentUri);
			fileinputstream = new FileInputStream(file);

			long l = file.length();
			int k = 0;
			byte abyte0[] = new byte[bufferSize];

			
		    //获取页面类型
			String mimetype = null;
			mimetype = m_application.getMimeType(attachmentUri);
			if (mimetype == null) {
				mimetype = " application/octet-stream;charset=ISO8859-1 ";
			}

			m_response.setContentType(mimetype);
			

			// 设置response类型
			String inlineType =  "attachment;";

			// 设置文件类型和下载的文件名
            m_response.setHeader("Content-Disposition", inlineType
						+ " filename=" + encodeString(fileName));
			
			// 设置内容长度
			m_response.setContentLength((int) l);

			outs = m_response.getOutputStream();

			while ((long) k < l) {
				int j = fileinputstream.read(abyte0, 0, bufferSize);
				k += j;
				outs.write(abyte0, 0, j);
			}

			m_response.flushBuffer();
		}
		finally {
			if (fileinputstream != null) {
				fileinputstream.close();
			}
			if (outs != null) {
				outs.close();
			}
			if(BaseConstants.UPLOAD_DB_ZIP_PASW_FLAG.equals(flag)){
				ZipCipherUtil.deleteFile(attachmentUri);
			}
			
		}
	}
	
	//获取文件真实路径
	private String getVirtualPath(String s) {
		s = UPLOAD_DIR + s;
		File file = null;
		String virtualPath = "";
		try {
			file = new File(s);
			if (file.exists()) {
				virtualPath = s;
			} else {
				//加密上传时
				String fileName = s.substring(0,s.lastIndexOf(".")) + "." + BaseConstants.UPLOAD_ZIP_PASW_LASTNAME ;
				file = new File(fileName);
				if(file.exists()){
					virtualPath = s;
				}
				
				if (m_application.getRealPath(s) != null) {
					file = new File(m_application.getRealPath(s));
					if (file.exists()) {
						virtualPath = m_application.getRealPath(s);
					}else{
						//加密上传时
						fileName = s.substring(0,s.lastIndexOf(".")) + "." + BaseConstants.UPLOAD_ZIP_PASW_LASTNAME  ;
						file = new File(m_application.getRealPath(fileName));
						if(file.exists()){
							virtualPath = m_application.getRealPath(s);
						}
					}
				}
			}
		} catch (Exception e) {
			return "";
		}

		return virtualPath;
	}
	//设置字符编码为ISO-8859-1
	private String encodeString(String s) {
		try {
			s = new String(s.getBytes("GBK"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

}
