//================================================================================================
//项目名称 ：    基盘
//功    能 ：    文件上传
//文件名称 ：    BackGroundService.java                                   
//描    述 ：    文件上传后台服务程序
//================================================================================================
//修改履历                                                                
//年 月 日		区分			所 属/担 当           内 容									标识        
//----------   ----   -------------------- ---------------                          ------        
//2009/04/28   	编写   		Intasect/李闻海     新規作成                                                                            
//================================================================================================

package baseSrc.common.upload;


import baseSrc.common.BaseUtility;
import baseSrc.common.Constants;
import baseSrc.common.DbAccess;
import baseSrc.framework.BaseConstants;
import baseSrc.orm.Attachment;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
public class BackGroundService extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 6162190133320085279L;
	//文件上传路径
	public static final String UPLOAD_DIR = System.getProperty("WEBPATH") + Constants.UPLOAD_DIR;
    //上传结果页面
	public static final String DEFAULT_UPLOAD_FAILURE_URL = "/sys/sysJsp/common/upload/result.jsp";
	//加密压缩文件标示
	private static final String UPLOAD_ZIP_PASSWORD = "YESBYPASSWORD";
	//上传后压缩文件夹标志
	private static final String UPLOAD_ZIP_NEW = "YESTOZIP";
	//压缩文件名称
	private static final String RARFILENAME_DEF = "FLAGZIP";
	private static Random randGen = null;
	
	private static Object initLock = new Object();
	
	private static char[] numbersAndLetters = null;
	//文件上传最大值

	public static final long UPLOAD_FILE_SIZE_MAX = Constants.UPLOAD_FILE_SIZE_MAX;
    //资源文件对象
	public static MessageResources   resources;
	
	public BackGroundService() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 从文件路径中取出文件名


	 */
	private String takeOutFileName(String filePath) {
		int pos = filePath.lastIndexOf(File.separator);
		if (pos > 0) {
			return filePath.substring(pos + 1);
		} else {
			return filePath;
		}
	}

	/**
	 * 从request中取出FileUploadStatus Bean
	 */
	public static FileUploadStatus getStatusBean(HttpServletRequest request) {
		BeanControler beanCtrl = BeanControler.getInstance();
		return beanCtrl.getUploadStatus(request.getRemoteAddr());
	}

	/**
	 * 把FileUploadStatus Bean保存到类控制器BeanControler
	 */
	public static void saveStatusBean(HttpServletRequest request,
			FileUploadStatus statusBean) {
		statusBean.setUploadAddr(request.getRemoteAddr());
		BeanControler beanCtrl = BeanControler.getInstance();
		beanCtrl.setUploadStatus(statusBean);
	}

	/**
	 * 删除已经上传的文件
	 */
	private void deleteUploadedFile(HttpServletRequest request) {
		FileUploadStatus satusBean = getStatusBean(request);
		for (int i = 0; i < satusBean.getUploadFileUrlList().size(); i++) {
			File uploadedFile = new File(UPLOAD_DIR + File.separator
					+ satusBean.getUploadFileUrlList().get(i));
			uploadedFile.delete();
		}
		satusBean.getUploadFileUrlList().clear();
		satusBean.setStatus(resources.getMessage("commonUpload.deleteFile"));
		saveStatusBean(request, satusBean);
	}

	/**
	 * 上传过程中出错处理


	 */
	private void uploadExceptionHandle(HttpServletRequest request, String errMsg)
			throws ServletException, IOException {
		// 首先删除已经上传的文件

		deleteUploadedFile(request);
		FileUploadStatus satusBean = getStatusBean(request);
		satusBean.setStatus(errMsg);
		saveStatusBean(request, satusBean);
	}

	/**
	 * 初始化文件上传状态Bean
	 */
	private FileUploadStatus initStatusBean(HttpServletRequest request) {
		resources   =  ((MessageResources)   getServletContext().getAttribute(Globals.MESSAGES_KEY));
		FileUploadStatus satusBean = new FileUploadStatus();
		satusBean.setStatus(resources.getMessage("commonUpload.readytoProcess"));
		satusBean.setUploadTotalSize(request.getContentLength());
		satusBean.setProcessStartTime(System.currentTimeMillis());
		satusBean.setBaseDir(Constants.UPLOAD_DIR);
		return satusBean;
	}

	/**
	 * 处理文件上传
	 */
	private void processFileUpload(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		
		
		String cip = (String)request.getSession().getAttribute("cip");
		String cid = (String)request.getSession().getAttribute("cid");
		String uid = (String)request.getSession().getAttribute("uid");
		String sZip = (String)request.getSession().getAttribute("rarFlag");
		if(cip==null){
			cip="";
		}
		if(cid==null){
			cid="";
		}
		if(uid==null){
			uid="";
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		// 设置内存缓冲区，超过后写入临时文件

		factory.setSizeThreshold(204800);
		File tempDir = new File(UPLOAD_DIR + "/temp");
		if (!tempDir.exists()) {
			tempDir.mkdir();
		}
		// 设置临时文件存储位置
		factory.setRepository(tempDir);
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 设置单个文件的最大上传值

		upload.setFileSizeMax(UPLOAD_FILE_SIZE_MAX);
		// 设置整个request的最大值
		
		//设置编码方式
		upload.setHeaderEncoding("UTF-8");
		
		upload.setSizeMax(UPLOAD_FILE_SIZE_MAX);
		upload.setProgressListener(new FileUploadListener(request));
		
		// 保存初始化后的FileUploadStatus Bean
		saveStatusBean(request, initStatusBean(request));
		//上传文件名

		String fileName = "";
		// 文件储存目录
		String uploadDir = "";
		String forwardURL = "";
		
		//压缩文件目录
		request.setCharacterEncoding("UTF-8");
		String zipDir = "";
		if(UPLOAD_ZIP_NEW.equals(sZip)){
			if (request.getParameter("rarFileName") != null) { 
				zipDir = request.getParameter("rarFileName"); 
				zipDir = URLDecoder.decode(zipDir, "utf-8"); 
				zipDir = getDir(zipDir);
			} 
		}
		if(BaseUtility.isStringNull(zipDir)){
			zipDir = RARFILENAME_DEF;
		}
		try {
			List items = upload.parseRequest(request);
			// 获得返回url
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				if (item.isFormField()) {
					forwardURL = item.getString();
					break;
				}
			}
			
			// 处理文件上传
			for (int i = 0; i < items.size(); i++) {
				String flag = BaseConstants.UPLOAD_DB_ZIP_NOPASW_FLAG;
				if (i == 0) {
					// 生成两层随机目录
					uploadDir = createDir(UPLOAD_DIR);
					if(UPLOAD_ZIP_NEW.equals(sZip)){
						uploadDir = uploadDir + zipDir + "\\";
						File fizip = new File(UPLOAD_DIR+uploadDir);
						if (!fizip.isDirectory()){
							fizip.mkdir();
						}
					}
				}
				FileItem item = (FileItem) items.get(i);

				// 取消上传
				if (getStatusBean(request).getCancel()) {
					deleteUploadedFile(request);
					break;
				}

				// 保存文件
				else if (!item.isFormField() && item.getName().length() > 0) {
//					logger.info("222222222222  request char Encoding = "+request.getCharacterEncoding());
					
					String itemName =  item.getName();

					String fileEncodeString = itemName;
					fileName = getFileName(takeOutFileName(fileEncodeString));
					File uploadedFile = new File(
							new File(UPLOAD_DIR, uploadDir), fileName);
					item.write(uploadedFile);
					
					//压缩处理
					String zipName = fileName.substring(0,fileName.lastIndexOf(".")) + "." + BaseConstants.UPLOAD_ZIP_PASW_LASTNAME ;
					
					if(UPLOAD_ZIP_PASSWORD.equals(sZip)){
						flag = BaseConstants.UPLOAD_DB_ZIP_PASW_FLAG;
						//加密压缩
						ZipCipherUtil.encryptZip(UPLOAD_DIR + uploadDir + fileName, UPLOAD_DIR + uploadDir + zipName, BaseConstants.ISC_ZIP_PASSWORD);
						//删除文件
						ZipCipherUtil.deleteFile(UPLOAD_DIR + uploadDir + fileName);
					
					}
					
					// 更新上传文件列表
					if(!UPLOAD_ZIP_NEW.equals(sZip)){
						FileUploadStatus satusBean = getStatusBean(request);
						satusBean.getUploadFileUrlList().add(new File(uploadDir, fileName).toString());
						saveStatusBean(request, satusBean);
					}
					Thread.sleep(500);
				}
				
				//将文件信息存入数据库
				if(!UPLOAD_ZIP_NEW.equals(sZip)){
					insertDb(fileName, uploadDir, cip, cid, uid, flag, request);
				}
			}
			if(UPLOAD_ZIP_NEW.equals(sZip)){
				String flag = BaseConstants.UPLOAD_DB_ZIP_PASW_FLAG;
				String rarName = zipDir+"." + BaseConstants.UPLOAD_ZIP_LASTNAME ;
				String zipName = zipDir+"." + BaseConstants.UPLOAD_ZIP_PASW_LASTNAME ;
				uploadDir = uploadDir.substring(0,uploadDir.indexOf(zipDir));
				//加密压缩
				String path = UPLOAD_DIR +uploadDir+zipDir;
				path = path.replace("//", "\\").replace("/", "\\");
				
//				//普通压缩处理
				ZipUtil.zipFile(UPLOAD_DIR +uploadDir, zipDir, UPLOAD_DIR + uploadDir + rarName);
				
				ZipCipherUtil.encryptZip(UPLOAD_DIR + uploadDir + rarName, UPLOAD_DIR + uploadDir + zipName, BaseConstants.ISC_ZIP_PASSWORD);
				//删除文件
				ZipCipherUtil.deleteFile(path);
				ZipCipherUtil.deleteFile(UPLOAD_DIR + uploadDir + rarName);
				fileName = rarName;
				
				insertDb(fileName, uploadDir, cip, cid, uid, flag, request);
				
				FileUploadStatus satusBean = getStatusBean(request);
				satusBean.getUploadFileUrlList().add(new File(fileName).toString());
				saveStatusBean(request, satusBean);
			}
		}catch (SizeLimitExceededException e) {
			double fileSizeMax = (int)(((double)(BackGroundService.UPLOAD_FILE_SIZE_MAX / 1024 / 1024.0)) * 100) / 100.0;
			uploadExceptionHandle(request, resources.getMessage("commonUpload.uploadError")+":" + resources.getMessage("commonUpload.sizeError")+fileSizeMax+"M！");
		}catch (FileUploadException e) {
			uploadExceptionHandle(request, resources.getMessage("commonUpload.uploadError")+":" + e.getMessage());
		} catch (Exception e) {
			uploadExceptionHandle(request, resources.getMessage("commonUpload.saveError")+":" + e.getMessage());
			e.printStackTrace();
		}
		if (forwardURL.length() == 0) {
			forwardURL = DEFAULT_UPLOAD_FAILURE_URL;
		}
		request.getRequestDispatcher(forwardURL).forward(request, response);
	}
	private String getDir(String zipDir) {
		if(BaseUtility.isStringNull(zipDir)){
			return "";
		}
		zipDir = zipDir.trim();
		zipDir = zipDir.replace("\\", "");
		zipDir = zipDir.replace("//", "");
		zipDir = zipDir.replace(":", "");
		zipDir = zipDir.replace(";", "");
		zipDir = zipDir.replace("*", "");
		zipDir = zipDir.replace("?", "");
		zipDir = zipDir.replace("\"", "");
		zipDir = zipDir.replace("\"\"", "");
		zipDir = zipDir.replace("<", "");
		zipDir = zipDir.replace(">", "");
		zipDir = zipDir.replace("|", "");
		zipDir = zipDir.replace(".", "");
		zipDir = zipDir.replace(",", "");
		return zipDir;
	}
	/**
	 * 回应上传状态查询
	 */
	private void responseStatusQuery(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		FileUploadStatus satusBean = getStatusBean(request);
		response.getWriter().write(satusBean.toJSon());
	}

	/**
	 * 处理取消文件上传
	 */
	private void processCancelFileUpload(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		FileUploadStatus satusBean = getStatusBean(request);
		satusBean.setCancel(true);
		saveStatusBean(request, satusBean);
		responseStatusQuery(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			processFileUpload(request, response);
		} else {
			request.setCharacterEncoding("UTF-8");
			if (request.getParameter("uploadStatus") != null) {
				responseStatusQuery(request, response);
			}
			if (request.getParameter("cancelUpload") != null) {
				processCancelFileUpload(request, response);
			}

		}
	}

	// 取新文件名
	private String getFileName(String fileName) {
		// SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		int random = (int) (Math.random() * 1000);
		String newFileName = getPrefixName(fileName) + "_" + random
				+ getFileExtension(fileName);
		return newFileName;
	}

	// 取文件名(不含后缀名)
	private String getPrefixName(String fileName) {
		if (fileName == null) {
			return null;
		}
		String s = fileName;
		int pos = fileName.lastIndexOf("/");
		if (pos != -1) {
			s = fileName.substring(pos + 1);
		} else {
			pos = fileName.lastIndexOf("\\");
			s = fileName.substring(pos + 1);
		}
		pos = s.lastIndexOf(".");
		if (pos != -1) {
			s = s.substring(0, pos);
		}
		return s;
	}

	// 取文件后缀名

	private String getFileExtension(String fileName) {
		if (fileName == null) {
			return null;
		}
		String ext = "";
		int pos = fileName.lastIndexOf(".");
		if (pos != -1) {
			ext = fileName.substring(pos);
		}
		return ext;
	}

	/**
	 * 生成随机目录
	 * 
	 * @param root
	 * @return
	 */
	public static String createDir(String root) {
		String path = randomNumString(4);
		String slash = File.separator;
		String first = path.substring(0, 2);
		String second = path.substring(2, 4);
		File dir = new File(root + slash + first);
		if (!dir.isDirectory())
			dir.mkdir();
		dir = new File(root + slash + first + slash + second);
		if (!dir.isDirectory())
			dir.mkdir();
		return first + slash + second + slash;
	}
	
	/**
	 * 生成随机字符
	 * @param length长度
	 * @return String 随机字符
	 * 
	 */
	public static final String randomNumString(int length) {
		if (length < 1) {
			return null;
		}
		// 初始化随机数字生成器
		if (randGen == null) {
			synchronized (initLock) {
				if (randGen == null) {
					randGen = new Random();
					// 初始化数字字母数
					numbersAndLetters = ("0123456789").toCharArray();
				}
			}
		}
		// 创建字符缓存数组装入字母和数
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(9)];
		}
		return new String(randBuffer);
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
	private void insertDb(String fileName,String uploadDir,String cip,String cid,String uid,String flag,HttpServletRequest request){
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext context=
			WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		
		Attachment attachment = new Attachment();
		attachment.setName(fileName);
		attachment.setUrl(uploadDir+"\\"+fileName);
		attachment.setCdatetime(new Date());
		attachment.setFlag(flag);
		attachment.setCiP(cip);
		attachment.setCpID(cid);
		attachment.setCuID(uid);
		
		
		DbAccess dbAccess=(DbAccess)context.getBean("sysDbAccess");
		dbAccess.saveOrUpdate(attachment);
		request.setAttribute("fileId", attachment.getId().toString());
		request.setAttribute("fileurl", encodeString(attachment.getUrl().toString()));
	}
		 
}
