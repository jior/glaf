<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ taglib uri="http://github.com/jior/glaf/tags" prefix="glaf"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%
    String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(context);
	pageContext.setAttribute("contextPath", context);
	boolean debug = false;
	String host = com.glaf.core.util.RequestUtils.getIPAddress(request);
	//System.out.println("host:"+host);
	if("127.0.0.1".equals(host)){
		debug = true;
	}
	java.util.Random random = new java.util.Random();
	String rand = Math.abs(random.nextInt(9999))+com.glaf.core.util.UUID32.getUUID()+Math.abs(random.nextInt(9999));
	session = request.getSession(true);
	if (session != null) {
       session.setAttribute("x_y", rand);
	}
%>
<!DOCTYPE html >
<html>
<head id="Head1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录<%=SystemConfig.getString("res_system_name")%></title>
<link href="${contextPath}/scripts/easyui/themes/gray/easyui.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.default {
	font-weight:normal;
	color:#ff3333;
}
.puton{
	font-weight:normal;
	color:black;
}
</style>
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/verify.js" ></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
    var login='<%=request.getParameter("login")%>';

    function openMainPage(){
		var browser=navigator.appName
		//alert(browser);
		//alert(navigator.userAgent);
		if(browser=="Microsoft Internet Explorer") {
		//页面加载时即关闭父窗口，此页面全屏显示
	      if(window.opener!=null){
            if(login!='true') {
	    	   window.opener.opener = null;
	           window.opener.open('','_self');
	           window.opener.close();
	           window.opener = null;
             }
		  } else if(login!='true') {
	        var screenWidth = screen.availWidth, screenHeight = screen.availHeight;
            var args = "top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no,titlebar=no";
	        //打开全屏的新窗口
	        var win = window.open(window.location.href+"?login=true","fullscreen", args);
	        if(win){
	            win.resizeTo(screenWidth, screenHeight);
	            win.outerWidth = screenWidth;
	            win.outerHeight = screenHeight;
	            win.moveTo(0,0);
	        }
	      }
	    }
	}

	 
    $(function() {
        $("#loginWindow").window({
                title: '登录<%=SystemConfig.getString("res_system_name")%>',
                width: 290,
                height: 190,
                modal: true,
                shadow: false,
                closable: false,
                maximizable: false,
                minimizable: false,
                collapsible: false,
                resizable: false
        });                    
    });

	function doLogin(){
	    var x = document.getElementById("x").value;
        var y = document.getElementById("y").value;
		if(x==''){
			alert("请输入您的用户名。");
			document.getElementById("x").focus();
            return;
		}
		if(y==''){
            alert("请输入您的密码。");
			document.getElementById("y").focus();
            return;
		}
        location.href="${contextPath}/mx/login/doLogin?x="+x+"&y=<%=rand%>"+y;
	}


	$(document).ready(function(){
		$("#x").focus(function () {
                var $this = $(this);
                if ($this.val() == $this[0].defaultValue) {
                    $this.val("");
                }
            }).blur(function () {
                var $this = $(this);
                if ($this.val() == "") {
                    $this.val($this[0].defaultValue);
                }
            })
	});

	$(document).ready(function(){
		 $("#y").focus(function () {
                var $this = $(this);
                if ($this.val() == $this[0].defaultValue) {
                    $this.val("");
                }
            }).blur(function () {
                var $this = $(this);
                if ($this.val() == "") {
                    $this.val($this[0].defaultValue);
                }
            })
	});
</script>
</head>
<body <%if(!debug){%>onload="openMainPage();"<%}%>>
    <div id="loginWindow" class="easyui-window" title="Login Form" iconcls="icon-login"
        style="width: 300px; height: 180px; padding: 5px; background: #fafafa;">
        <div border="false" style="padding-left: 30px;  border: 1px solid #ccc;">
            <html:form method="post" action="${contextPath}/login.do?method=login" onsubmit="return verifyAll(this);" > 
            <table>
                <tr>
                    <td>
                        用户名：
                    </td>
                    <td>
                        <input id="x" name="x" class="easyui-validatebox input" required="true" validtype="length[3,20]"
                            style="width: 150px;" datatype="string" nullable="no" maxsize="20" chname="用户名" value="root" />
                    </td>
                </tr>
                <tr>
                    <td>
                        密&nbsp;&nbsp;&nbsp;&nbsp;码：
                    </td>
                    <td>
                        <input type="password" id="y" name="y" style="width: 150px;" class="easyui-validatebox input"
                            required="true" validtype="length[6,20]" datatype="string" nullable="no" maxsize="20" chname="密码" value="111111"/>
                    </td>
                </tr>
            </table>
            </html:form>
        </div>
             
        <div class="toolbar" style="text-align: center; margin-top: 20px;">
             <a href="#" class="easyui-linkbutton" iconcls="icon-ok" id="btnLogin" style="margin-left: 2px;" 
			    onclick="javascript:doLogin();"> 登 录 </a>
        </div>
    </div>
</body>
</html>
