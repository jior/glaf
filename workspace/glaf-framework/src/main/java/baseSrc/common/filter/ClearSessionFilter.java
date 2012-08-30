/**
 * 在一定的时机清除session中内容
 */
package baseSrc.common.filter;

import java.io.IOException;
import java.util.Enumeration;

import baseSrc.common.LogHelper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClearSessionFilter  extends HttpServlet implements Filter {
	private static final long serialVersionUID = 1L; 
	private static LogHelper logger = new LogHelper(ClearSessionFilter.class);
	private static String loginJsp;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		if ((filterConfig.getInitParameter("loginJsp") != null)
				&& (filterConfig.getInitParameter("loginJsp").length() > 0))
			loginJsp = filterConfig.getInitParameter("loginJsp");
		logger.info("登录页面设定为:" + loginJsp);
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest hsr = (HttpServletRequest) request;
		HttpServletResponse hsrs = (HttpServletResponse) response;
		
		//取出session中存储的所有属性
		Enumeration  e = hsr.getSession().getAttributeNames();
		int count = 0;
		//统计session中存放属性的个数
		while(e.hasMoreElements()){
			String sessionName=(String)e.nextElement();
			//统计session中对象个数
			count++;
		}
		//例子：如果存放属性个数超过15个，则清除session
		if(count>=15){
			hsr.getSession().invalidate();
			java.io.PrintWriter out = response.getWriter();   
		    out.println("<html>");   
		    out.println("<script>");   
		    out.println("window.open ('" + getPagePath(loginJsp, hsr) + "','_top')");   
		    out.println("</script>");   
		    out.println("</html>");   
			return;
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
	
}
