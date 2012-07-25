package com.glaf.base.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.sys.model.SysUser;

public class AuthorizeFilter implements Filter{
	private Log logger = LogFactory.getLog(AuthorizeFilter.class);
	private String url="";
	private String require="";
	private String errorUrl="";
	private String loginUser="";
	
	/**
	 * 初始化Filter
	 * @param config FilterConfig
	 * @throws ServletException
	 */
	public void init(FilterConfig config) throws ServletException {
		this.url = config.getInitParameter("login_url");
		this.require = config.getInitParameter("require");
		this.errorUrl = config.getInitParameter("error_url");
		this.loginUser = config.getInitParameter("login_user");
		logger.info("url:"+url);
		logger.info("require:"+require);
		logger.info("errorUrl:"+errorUrl);
		logger.info("loginUser:"+loginUser);
	}
	
	/**
	 * 注销
	 */
	public void destroy() {
	}
	/**
	 * 是否忽略当前页面
	 * 
	 * @param uri
	 * @return
	 */
	private boolean ignoreUrl(String uri) {
		boolean ret = false;
		String[] ignoreUrls = url.split(",");
		for (int i = 0; i < ignoreUrls.length; i++) {
			if (ignoreUrls[i].trim().equals(uri)) {
				ret = true;
				break;
			}
		}
		return ret;
	}
	/**
	 * 过滤
	 * @param request ServletRequest
	 * @param response ServletResponse
	 * @param chain FilterChain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		//需要验证
		if("true".equals(require)){
			//检测用户是否已经登录
			SysUser bean = (SysUser) req.getSession().getAttribute(loginUser);
			String uri = req.getRequestURI();
			logger.info(uri);
			
			//用户没有登录且当前页不是登录页面
			logger.info("ignoreUrl:" + ignoreUrl(uri));
			if (bean==null && !ignoreUrl(uri)) {//显示登陆页
				res.sendRedirect(errorUrl);
				return;
			}
		}
		chain.doFilter(req, response);
	}
}
