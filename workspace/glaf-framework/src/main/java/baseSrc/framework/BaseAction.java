//================================================================================================
//项目名称 ：    基盘
//功    能 ：   Action
//文件名称 ：    BaseAction.java                                   
//描    述 ：    所有Action都需要继承

//================================================================================================
//修改履历                                                                
//年 月 日		区分		所 属/担 当           		内 容									标识        
//----------   	----   	------------------- ---------------                          ------        
//2009/04/28   	编写   	Intasect/余海涛		新規作成
//2009/05/19   	修改   	Intasect/殷翔    	 	添加日志和异常处理           
//================================================================================================

package baseSrc.framework;

import baseSrc.common.BaseCom;
import baseSrc.common.BaseUtility;
import baseSrc.common.LogHelper;
import baseSrc.common.report.ReportGenerator;
import java.lang.reflect.*;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.commons.lang.StringUtils;

import com.glaf.base.utils.RequestUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.OutputStream;

public class BaseAction extends Action {
	// 日志对象
	private static LogHelper logger = new LogHelper(BaseAction.class);
	// 业务DAO
	private BaseDAO busDao;

	private ReportGenerator reportGenerator;

	// 登录path
	protected String loginPath;
	// 登录action转向
	protected String loginForward;

	private void doAfterMethod(String actionMethodId, BaseActionForm usrForm,
			Object baseCom, BaseDTOMap dtoMap, HttpServletRequest request,
			HttpServletResponse response) {

		// 设置了报表模版就显示报表
		if (!BaseUtility.isStringNull(dtoMap.getReportTpl())) {
			// this.reportGenerator = new ReportGenerator();
			this.reportGenerator.generateReport(dtoMap.getReportType(),
					dtoMap.getReportTpl(), dtoMap.getReportQuerys(),
					dtoMap.getReportParameters(), response);

		}

		// 将list转换为json串
		if (null != dtoMap.getDetailList() && dtoMap.getDetailList().size() > 0) {
			// 将列表对象转换为json串并返回给前台
			JSONArray jo = JSONArray.fromObject(dtoMap.getDetailList());
			request.setAttribute("myDefaultDetailData", jo.toString());
		}

		// 设置了下载文件路径，就下载文件
		if (!StringUtils.isEmpty(dtoMap.getFile())) {
			InputStream fis = null;
			try {

				String path = dtoMap.getFile();
				File file = new File(path);
				String filename = file.getName();

				fis = new BufferedInputStream(new FileInputStream(path));
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();
				fis = null;

				response.reset();
				response.addHeader(
						"Content-Disposition",
						"attachment;filename="
								+ new String(filename.getBytes("gb2312"),
										"iso8859-1"));
				response.addHeader("Content-Length", "" + file.length());
				OutputStream toClient = new BufferedOutputStream(
						response.getOutputStream());
				if (StringUtils.isNotBlank(dtoMap.getFileContentType())) {
					response.setContentType(dtoMap.getFileContentType());
				}
				toClient.write(buffer);
				toClient.flush();
				toClient.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
					}
				}
			}
		}

	}

	/**
	 * action执行方法
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// 取得Form
		BaseActionForm usrForm = (BaseActionForm) form;

		// 取得actionMethodId
		String actionMethodId = this.getActionMethodId(usrForm);

		// 跳转方向
		String actionForwardId = "";
		ActionForward actionForward = null;

		// 为sys开放的接口,即系统级处理可增加至expandSysStartEvent方法中,由此处进行调用
		// 如果该方法返回页面转向,则该方法后面的代码将不会执行
		actionForward = expandSysStartEvent(mapping, form, request, response);
		if (null != actionForward) {
			return actionForward;
		}

		// 从Session取BaseCom（用户信息）
		Object obaseCom = this.getBaseCom(request);
		BaseCom baseCom = (BaseCom) this.getBaseCom(request);

		// 响应方法并跳转
		if (baseCom.isDAOExecutable(mapping.getPath())
				|| this.loginPath.equals(mapping.getPath())) {
			// 如果指定Action可以执行

			// 执行制定方法
			BaseDTOMap dtoMap = null;

			// 如果为登录画面，则获取出登录ip地址
			if (this.loginPath.equals(mapping.getPath())) {
				String ip = getIpAddr(request);
				usrForm.setIp(ip);
			}

			try {
				// 执行业务dao中对应的方法
				dtoMap = this.exeDaoMethod(actionMethodId, usrForm, obaseCom);
				logger.info("执行方法成功!");
			} catch (Exception ex) {
				// 异常时写日志并抛出异常
				logger.info("执行方法失败!");
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}

			// 将执行后的Msg设置到Request中
			setMsgToJsp(dtoMap, request);

			// 将执行后返回的Dto设置到Request中
			this.setDtoToRequest(dtoMap, request);

			// 处理json返回对象
			if (null != dtoMap.getDetailList()
					&& dtoMap.getDetailList().size() > 0) {
				// 将列表对象转换为json串并返回给前台
				JSONArray jo = JSONArray.fromObject(dtoMap.getDetailList());
				request.setAttribute("myDefaultDetailData", jo.toString());
			}

			// dtomap中的设置执行对应动作
			doAfterMethod(actionMethodId, usrForm, obaseCom, dtoMap, request,
					response);

			// 如果Dao中指定了跳转则跳转到指定方向
			actionForward = mapping.findForward(dtoMap.getForwardId());
			actionForwardId = dtoMap.getForwardId();

			// 提供给系统级的接口,系统级可重写该方法，以实现自身需求
			ActionForward endActionForward = expandSysEndEvent(mapping, form,
					request, response);
			if (null != endActionForward) {
				return endActionForward;
			}
		} else {
			// 如果不是有效的Action则跳转到Login
			actionForward = mapping.findForward(this.loginForward);
			actionForwardId = this.loginForward;
		}

		return actionForward;

	}

	/**
	 * 执行Dao的指定方法
	 * 
	 * @param actionMethodId
	 * @param usrForm
	 * @param baseCom
	 * @return
	 */
	private BaseDTOMap exeDaoMethod(String actionMethodId,
			BaseActionForm usrForm, Object baseCom) {
		// 执行方法后返回的DTO
		BaseDTOMap ret = null;
		Method m = null;

		// 定义接口需要的类型
		Class<?>[] clazz = new Class[2];
		clazz[0] = usrForm.getClass();
		clazz[1] = baseCom.getClass();

		// 执行方法
		try {

			// 取得方法
			m = this.busDao.getClass().getMethod(actionMethodId, clazz);

			// 构造参数
			Object[] object = new Object[2];
			object[0] = usrForm; // 画面传入的Form
			object[1] = baseCom; // Session中的用户基本信息

			// 调用方法
			logger.info("执行方法:" + m.getDeclaringClass().getName() + "."
					+ actionMethodId + " 用户名:"
					+ ((BaseCom) baseCom).getUserId());
			ret = (BaseDTOMap) m.invoke(this.busDao, object);

		}
		// 找不到方法异常

		catch (NoSuchMethodException ex) {
			throw new BaseException(
					"[" + actionMethodId + "] is not be found in the "
							+ busDao.getClass().getName(), ex);
		}
		// 访问权限异常
		catch (IllegalAccessException ex) {
			throw new BaseException(ex);
		}
		// 参数异常
		catch (IllegalArgumentException ex) {
			throw new BaseException(ex);
		}
		// Dao错误
		catch (InvocationTargetException ex) {
			BusException be = (BusException) ex.getTargetException();
			be.printStackTrace();
			ret = be.getReturnDTOMap();
		}

		// 返回DTO
		return ret;
	}

	/**
	 * 为系统级扩展的action处理接口,位于action处理开始时调用 系统级action可重写该方法,以达到调用的目的
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward expandSysEndEvent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return null;
	}

	/**
	 * 为系统级扩展的action处理接口,位于action处理结束时调用 系统级action可重写该方法,以达到调用的目的
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward expandSysStartEvent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return null;
	}

	/**
	 * 取得方法名
	 * 
	 * @param usrForm
	 *            画面的Form对象
	 * @return 调用的方法名
	 */
	protected String getActionMethodId(BaseActionForm usrForm) {

		// 方法名
		String ret = null;

		// 从Form取指定的方法名
		ret = usrForm.getActionMethodId();

		// 如果方法名为空
		if (null == ret || "".equals(ret)) {
			// 设置默认的方法名
			// <property name="defaultMethod" value="runPageLoad"></property>
			ret = this.busDao.getDefaultMethod();
		}

		// 默认方法名仍然未指定，抛出异常
		if (null == ret || "".equals(ret)) {
			throw new BaseException("the actionMethodId not found!");
		}

		// 返回方法名
		return ret;
	}

	/**
	 * 获取用户基本信息
	 * 
	 * @param request
	 * @return
	 */
	private Object getBaseCom(HttpServletRequest request) {
		// 用户基本信息
		Object ret = null;

		// 从Session获取用户基本信息
		ret = request.getSession().getAttribute(
				BaseConstants.ISC_BASE_BEANCOM_ID);

		// 如果不存在，新实例化一个

		if (null == ret) {
			ret = new BaseCom();
		}

		// 返回用户基本信息
		return ret;

	}

	/**
	 * 业务DAO注入时使用的get方法
	 * 
	 * @return
	 */
	public BaseDAO getBusDao() {
		return busDao;
	}

	/**
	 * 获取客户端IP地址
	 * 
	 * @param request
	 * @return 客户端ip地址
	 */
	public String getIpAddr(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("Proxy-Client-IP");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("WL-Proxy-Client-IP");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getRemoteAddr();

		}

		return ip;
	}

	/**
	 * 设置用户基本信息
	 * 
	 * @param bc
	 *            用户基本信息
	 * @param request
	 */
	private void setBaseCom(Object bc, HttpServletRequest request) {
		// 写入Session
		request.getSession()
				.setAttribute(BaseConstants.ISC_BASE_BEANCOM_ID, bc);
		if (bc instanceof BaseCom) {
			try {
				BaseCom bx = (BaseCom) bc;
				RequestUtil.setLoginUser(request, bx.getUserId());
				logger.info("---------" + bx.getUserId() + " Login OK------");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 业务DAO注入时使用的set方法
	 * 
	 * @param busDao
	 */
	public void setBusDao(BaseDAO busDao) {
		this.busDao = busDao;
	}

	/**
	 * 将调用DAO返回的DTO写入Request
	 * 
	 * @param dtoMap
	 *            调用DAO返回的DTO
	 * @param request
	 */
	private void setDtoToRequest(BaseDTOMap dtoMap, HttpServletRequest request) {

		// 写用户基本信息

		if (null != dtoMap.getBeanCom()) {

			this.setBaseCom(dtoMap.getBeanCom(), request);
			request.getSession().setAttribute(Globals.LOCALE_KEY, Locale.CHINA);
		}

		// 写默认DTO
		if (null != dtoMap.getDefaultDTO()) {

			if (null != dtoMap.getDefaultDTO()) {

				request.setAttribute(BaseConstants.ISC_DEFAULT_DTO_ID,
						dtoMap.getDefaultDTO());
			}
		}
		// 写默认DTO_A
		if (null != dtoMap.getDefaultDTO_A()) {

			if (null != dtoMap.getDefaultDTO_A()) {

				request.setAttribute(BaseConstants.ISC_DEFAULT_DTO_ID_A,
						dtoMap.getDefaultDTO_A());
			}
		}
		// 写默认DTO_B
		if (null != dtoMap.getDefaultDTO_B()) {

			if (null != dtoMap.getDefaultDTO_B()) {

				request.setAttribute(BaseConstants.ISC_DEFAULT_DTO_ID_B,
						dtoMap.getDefaultDTO_B());
			}
		}

		if (null != dtoMap.getLightDTO()) {
			request.setAttribute(BaseConstants.ISC_LIGHT_DTO_ID,
					dtoMap.getLightDTO());
			SelectCommonDTO sc = (SelectCommonDTO) dtoMap.getLightDTO();
			String lightDTOJson = "";
			for (int i = 0; i < sc.getLabels().length; i++) {
				// lightDTOJson = lightDTOJson + ",['value':'" +
				// sc.getValues()[i] + "','label':'" + sc.getLabels()[i] + "']";
				lightDTOJson = lightDTOJson + ",['" + sc.getValues()[i] + "','"
						+ sc.getLabels()[i] + "']";
			}
			lightDTOJson = lightDTOJson.substring(1, lightDTOJson.length());
			lightDTOJson = "[" + lightDTOJson + "]";
			request.setAttribute(BaseConstants.ISC_LIGHT_DTO_ID + "Json",
					lightDTOJson);
		}

		// 写返回值

		if (null != dtoMap && 0 != dtoMap.size()) {
			Iterator<Entry<String, Object>> dtos = dtoMap.entrySet().iterator();
			while (dtos.hasNext()) {
				Entry<String, Object> entry = dtos.next();
				request.setAttribute(entry.getKey(), entry.getValue());
			}
		}

	}

	/**
	 * 登录转向的set方法
	 * 
	 * @param loginForward
	 */
	public void setLoginForward(String loginForward) {
		this.loginForward = loginForward;
	}

	/**
	 * 登录path的set方法
	 * 
	 * @param loginPath
	 */
	public void setLoginPath(String loginPath) {
		this.loginPath = loginPath;
	}

	/**
	 * 将DAO返回的MSG设置到Request
	 * 
	 * @param dtoMap
	 *            执行DAO返回的DAO
	 * @param request
	 *            页面Request
	 */
	private void setMsgToJsp(BaseDTOMap dtoMap, HttpServletRequest request) {
		// Struts msg
		ActionMessages ams = new ActionMessages();

		// 设置Msg
		if (!BaseUtility.isStringNull(dtoMap.getMsgId())) {
			// 如果MsgId不为空

			// 获得Msg参数列表
			Object[] args = dtoMap.getMsgArgs().toArray();

			// 设置Msg
			ams.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage(dtoMap.getMsgId(), args));
		}

		// 错误处理
		if (!BaseUtility.isListNull(dtoMap.getErrObjIds())) {
			// 如果发生错误,设置错误Id
			request.setAttribute(BaseConstants.ISC_ERROR_DTO_ID,
					dtoMap.getErrObjIds());
		}

		// 返回Msg
		if (ams.size() > 0) {
			// 如果Msg不为空，添加到Request
			this.addMessages(request, ams);
		}

	}

	public void setReportGenerator(ReportGenerator reportGenerator) {
		this.reportGenerator = reportGenerator;
	}
}
