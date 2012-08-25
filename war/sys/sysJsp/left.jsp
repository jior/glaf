<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="baseSrc.framework.*" %>
<%@ page import="baseSrc.common.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="bean" uri="/sys/sysTld/struts-bean.tld"%>
<%
	//获取session
	BaseCom baseCom = (BaseCom)request.getSession().getAttribute(BaseConstants.ISC_BASE_BEANCOM_ID);
	
	//取得权限
	Map<String, List> privilegeMap = baseCom.getUserPrivilageMap();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>jquery弹性菜单</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/sysCss/menu/menu.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysCss/menu/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysCss/menu/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysCss/menu/xixi.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/frameWorkUtility.js"></script>
<script language="javascript" type="text/javascript">
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
		    	<a name="lightbutton" onClick="window.parent.frames['mainFrame'].location.href='<%=request.getContextPath()%>/sys/sysJsp/main.jsp';changeClassButton(this);" href="#" class="red">首页 </a>
		    </li>   
          </ul>
      </li>
      <%
            
			
            if(privilegeMap.containsKey("2")){ %>
      <li class="menu"><ul>	  	  
      	
	    <li class="button"><a href="#" class="red">示例 <span></span></a></li>
		
        <li class="dropdown">
            <ul>
            <% 
            	List<String> l2 =  privilegeMap.get("2");
            	String sMenu = "";
            	for(String s2 : l2){
            		String[] sl2 = s2.split(",");
            		sMenu = sMenu + "<li class='highlight'><a name='lightli' onclick='changeClass(this)' href='" + sl2[2] + "' target='mainFrame'>" + sl2[1] + "</a></li>";
             	}
             %>
             <%=sMenu%>
           </ul>
		</li>
          </ul>
      </li>
      <%
            }
			
            if(privilegeMap.containsKey("3")){ %>
      <li class="menu"><ul>	  	  
      	
	    <li class="button"><a href="#" class="red"><bean:message key="funLeft.menu"/><span></span></a></li>
		
        <li class="dropdown">
            <ul>
            <% 
            	List<String> l3 =  privilegeMap.get("3");
            	String sMenu = "";
            	for(String s3 : l3){
            		String[] sl3 = s3.split(",");
            		sMenu = sMenu + "<li class='highlight'><a name='lightli' onclick='changeClass(this)' href='" + sl3[2] + "' target='mainFrame'>" + sl3[1] + "</a></li>";
             	}
             %>
             <%=sMenu%>
           </ul>
		</li>
          </ul>
      </li>
      
	<%
            }
			
            if(privilegeMap.containsKey("4")){ %>
      <li class="menu"><ul>	  	  
      	
	    <li class="button"><a href="#" class="red">系统管理<span></span></a></li>
		
        <li class="dropdown">
            <ul>
            <% 
            	List<String> l4 =  privilegeMap.get("4");
            	String sMenu = "";
            	for(String s4 : l4){
            		String[] sl4 = s4.split(",");
            		sMenu = sMenu + "<li class='highlight'><a name='lightli' onclick='changeClass(this)' href='" + sl4[2] + "' target='mainFrame'>" + sl4[1] + "</a></li>";
             	}
             %>
             <%=sMenu%>
           </ul>
		</li>
          </ul>
      </li>
      
	<%}%>

<div class="clear"></div>
</div>



<p>&nbsp;</p>
</body>
</html>
