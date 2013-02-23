<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="com.glaf.base.utils.*" %>
<%@ page import="com.glaf.base.business.*" %>
<%@ page import="com.glaf.core.context.*" %>
<%@ page import="com.glaf.base.modules.sys.model.*" %>
<%@ page import="com.glaf.base.modules.sys.service.*" %>
<%
	 
	String userId = RequestUtil.getActorId(request);
	ApplicationBean bean = new ApplicationBean();
	SysApplicationService sysApplicationService = ContextFactory.getBean("sysApplicationProxy");
    bean.setSysApplicationService(sysApplicationService);
	String scripts = bean.getMenuScripts(3, userId, request.getContextPath());

%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>jquery弹性菜单</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/menu.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easing.1.3.js"></script>
<script language="javascript" type="text/javascript">

$(document).ready(function(){
	/* This code is executed after the DOM has been completely loaded */

	/* Changing thedefault easing effect - will affect the slideUp/slideDown methods: */
	$.easing.def = "easeOutBounce";

	/* Binding a click event handler to the links: */
	$('li.button a').click(function(e){
	
		/* Finding the drop down list that corresponds to the current section: */
		var dropDown = $(this).parent().next();
		
		/* Closing all other drop down sections, except the current one */
		//$('.dropdown').not(dropDown).slideUp('slow');
		dropDown.slideToggle('slow');
		
		/* Preventing the default event (which would be to navigate the browser to the link's address) */
		e.preventDefault();
	})
	
});

function changeClass(thisObt){
  //找到所有菜单项，设置标题颜色为黑色
  var livar = document.getElementsByName("lightbutton");

	for(i=0;i<livar.length;i++){
		if (livar[i].className=='linkclassnewbutton'){
      livar[i].className='linkclassoldbutton';
    }
	}	
	
	var livar = document.getElementsByName("lightli");

	for(i=0;i<livar.length;i++){
		if (livar[i].className=='linkclassnew'){
      livar[i].className='linkclassold';
    }
	}	
	//设置当前选中项的字体颜色为蓝色
	thisObt.className="linkclassnew";
	
}

function changeClassButton(thisObt){
  //找到所有菜单项，设置标题颜色为黑色
  var livar = document.getElementsByName("lightli");

	for(i=0;i<livar.length;i++){
		if (livar[i].className=='linkclassnew'){
      livar[i].className='linkclassold';
    }
	}	
	
	var livar = document.getElementsByName("lightbutton");

	for(i=0;i<livar.length;i++){
		if (livar[i].className=='linkclassnewbutton'){
      livar[i].className='linkclassoldbutton';
    }
	}	
	//设置当前选中项的字体颜色为蓝色
	thisObt.className="linkclassnewbutton";
	
}
</script>
</head>

<body style="text-align:center">
<div id="main">
  <ul class="container">
      <li class="menu">
          <ul>
		    <li class="button">
		    	<a name="lightbutton" onClick="window.parent.frames['mainFrame'].location.href='main.jsp';changeClassButton(this);" href="#" class="red">首页 </a>
		    </li>   
          </ul>
      </li>
      <%=scripts%>
</ul>
<div class="clear"></div>
</div>

<p>&nbsp;</p>
</body>
</html>
