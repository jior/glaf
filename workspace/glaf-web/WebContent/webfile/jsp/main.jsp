<%@ page contentType="text/html;charset=UTF-8"%><%@ page
	import="com.glaf.cms.webfile.*"%><jsp:useBean id="mySmartFile"
	scope="page" class="com.glaf.cms.webfile.WebFile" /><%	
        com.glaf.core.security.LoginContext loginContext = com.glaf.core.util.RequestUtils.getLoginContext(request);
        if (!(loginContext.isSystemAdministrator())) {
			 return;
		}
	    // Initialization
	    mySmartFile.initialize(pageContext);
	    // Images and icons
	    mySmartFile.setIconsPath(request.getContextPath()+"/webfile/icons");
	    mySmartFile.setImagesPath(request.getContextPath()+"/webfile/images");
		mySmartFile.setStylesPath(request.getContextPath()+"/webfile/styles/styles.css");

       if("root".equals(loginContext.getActorId()) || "admin".equals(loginContext.getActorId())){
		   String path = request.getParameter("path");
		   if(path == null){
			   path = getServletContext().getRealPath("");
		   }
	       mySmartFile.showExplorer(path, WebFile.PHYSICAL);
	    } else {
			String dir = getServletContext().getRealPath("")+"/WEB-INF/upload/files/"+loginContext.getActorId();
			com.glaf.core.util.FileUtils.mkdirs(dir);
			mySmartFile.showExplorer(dir, WebFile.PHYSICAL);
		}
%>