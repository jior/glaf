<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="baseSrc.common.*"%>
<%@ page import="baseSrc.framework.*"%>
<%@ page import="com.glaf.base.modules.sys.action.*"%>
<%
  BaseCom baseCom = (BaseCom)request.getSession().getAttribute(BaseConstants.ISC_BASE_BEANCOM_ID);
  if(baseCom != null){
	  try{
	    AuthorizeBean bean = new AuthorizeBean();
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:"+baseCom.getUserId());
	    bean.login(baseCom.getUserId(), request);
	    //response.sendRedirect(request.getContextPath()+"/sys/frame.do");
	    //return;
	  }catch(Throwable ex){
		  ex.printStackTrace();
	  }
  }
%>
