package baseSrc.common.filter;

import baseSrc.common.BaseCom;
import baseSrc.common.LogHelper;
import baseSrc.framework.BaseAction;
import baseSrc.framework.BaseConstants;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sysSrc.orm.TPrivilege;
/**
 * 控制用户登录及权限
 * 
 * @author
 * 
 */
public class PrivilegeFilter extends HttpServlet implements Filter {
	private static final long serialVersionUID = 1L; 
	
	private static String errorUrl;
	private static String loginJsp;
	private static LogHelper logger = new LogHelper(PrivilegeFilter.class);
	
	/**
	 * 初始化获取参数
	 */
	public void init(FilterConfig filterConfig) throws ServletException {

		if ((filterConfig.getInitParameter("loginJsp") != null)
				&& (filterConfig.getInitParameter("loginJsp").length() > 0))
			loginJsp = filterConfig.getInitParameter("loginJsp");
		logger.info("登录页面设定为:" + loginJsp);
		
	}

	/**
	 * 过滤处理
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest hsr = (HttpServletRequest) request;
		HttpServletResponse hsrs = (HttpServletResponse) response;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		// URL为空不处理
		// 没有登陆信息不处理
		String url = hsr.getRequestURI();
		if (url == null
				|| url.trim().length() == 0)
		{
			filterChain.doFilter(request, response);
			return;
		}
		//session为空时，判断请求路径是否为登录页面
		//是：发送请求
		//否：跳转至登录页面
		
		BaseCom baseCom = (BaseCom)hsr.getSession().getAttribute(BaseConstants.ISC_BASE_BEANCOM_ID);
		if(null == hsr.getSession().getAttribute(BaseConstants.ISC_BASE_BEANCOM_ID)){
			if(url.toUpperCase().indexOf("LOGIN") >= 0){
			    filterChain.doFilter(request, response);
				return;
			}else{
				java.io.PrintWriter out = response.getWriter();   
			    out.println("<html>");   
			    out.println("<script>");   
			    out.println("window.open ('" + getPagePath(loginJsp, hsr) + "','_top')");   
			    out.println("</script>");   
			    out.println("</html>");   
				return;
			}
		}
		// 到登录页面
		if (url.toUpperCase().indexOf("LOGIN") >= 0)
		{
			// 不拦截
			filterChain.doFilter(request, response);
			return;
		}
		// 不到登陆画面且未登陆
		else if(null == baseCom.getUserId()
				|| "".equals(baseCom.getUserId()))
		{
			// 则转向登录页面
			hsrs.sendRedirect(hsrs
					.encodeRedirectURL(getPagePath(loginJsp, hsr)));
			return;
		}
		
		// 此处应增加权限验证代码
		String uri = hsr.getRequestURI();
		String name = "";
		if(uri.indexOf(".do") > 0){
			name = uri.substring(0, uri.indexOf(".do"));
		}else if(uri.indexOf(".jsp") > 0){
			name = uri.substring(0, uri.indexOf(".jsp"));
		}
		name = name.substring(name.lastIndexOf("/") + 1).toUpperCase();
		
		//获取所有权限
		List<TPrivilege> allPrivilages = baseCom.getAllPrivilege();
		//没有对应权限时，转向登录页面，有权限时判断当前请求是否有权限访问
		boolean isPrivilegeflag = false;//判断是否为权限请求路径
		if(null!=allPrivilages && 0<allPrivilages.size()){
			for(int i=0;i<allPrivilages.size();i++){
				TPrivilege allPrivilege = (TPrivilege)allPrivilages.get(i);
				String pri = allPrivilege.getFUrl().toUpperCase();
				
				if(0 < pri.indexOf(".DO")){
					//pri = "/GMMC-PM/" + pri;
					pri = System.getProperty ("webapp.name") + pri;
				}
				if(name.equals(pri)){
					isPrivilegeflag = true;
					break;
				}
			}
		}
		
		///如果请求路径为权限之一，则判断该用户是否有该权限
		if(isPrivilegeflag){
			//获取用户拥有的权限
			List<TPrivilege> userPrivilages = baseCom.getRolePrivilege();
			if(null != userPrivilages && 0 < userPrivilages.size()){
				boolean flag = false;
				for(int i=1;i<userPrivilages.size();i++){
					TPrivilege privilegemaster = (TPrivilege)userPrivilages.get(i);
					String pri = privilegemaster.getFUrl().toUpperCase();
					if(0 < pri.indexOf(".DO")){
						//pri = "/GMMC-PM/" + pri;
						pri = System.getProperty ("webapp.name") + pri;
					}
					if(name.equals(pri)){
						flag = true;
						break;
					}
				}
				if(!flag){
					java.io.PrintWriter out = response.getWriter();   
				    out.println("<html>");   
				    out.println("<script>");   
				    out.println("window.open ('" + getPagePath(loginJsp, hsr) + "','_top')");   
				    out.println("</script>");   
				    out.println("</html>");   
					return;
				}
			}else{
				java.io.PrintWriter out = response.getWriter();   
			    out.println("<html>");   
			    out.println("<script>");   
			    out.println("window.open ('" + getPagePath(loginJsp, hsr) + "','_top')");   
			    out.println("</script>");   
			    out.println("</html>");   
				return;
			}
		}
		
		
		filterChain.doFilter(request, response);
	}	

	/**
	 * 取得画面的绝对路径
	 * @param pageName 画面名
	 * @param request
	 * @return
	 */
	private String getPagePath(String pageName, HttpServletRequest request) {
		if (pageName == null)
			pageName = "/";
		else if (!(pageName.startsWith("/")))
			pageName = pageName + "/";
		return request.getContextPath() + pageName;
	}
	
	public void destroy() {
		// do something
	}
}
