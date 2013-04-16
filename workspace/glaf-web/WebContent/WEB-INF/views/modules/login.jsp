<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://github.com/jior/glaf/tags" prefix="glaf"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%
    String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(context);
	pageContext.setAttribute("contextPath", context);
	boolean debug = false;
	String host = com.glaf.core.util.RequestUtils.getIPAddress(request);
	System.out.println("host:"+host);
	if("127.0.0.1".equals(host)){
		debug = true;
	}
%>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>GLAF基础应用框架-系统登录</title>
<link href="${contextPath}/css/system.css" type="text/css" rel="stylesheet">
<link href="${contextPath}/css/site.css" rel="stylesheet" type="text/css">
<link href="${contextPath}/icons/styles.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/scripts/easyui/themes/gray/easyui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/verify.js" ></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
    var login='${requestContext.login}';

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
                title: 'GLAF基础应用框架',
                width: 290,
                height: 180,
                modal: true,
                shadow: false,
                closable: false,
                maximizable: false,
                minimizable: false,
                collapsible: false,
                resizable: false
            });
            //登录
            $("#btnLogin").click(function() {
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

                loginSys(x, y);
            });

            
        });

        //登陆操作方法
        function loginSys(x, y) {
            window.location = "${contextPath}/login.do?method=login&x="+x+"&y="+y;
        }
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
                        <input id="x" class="easyui-validatebox input" required="true" validtype="length[1,15]"
                            style="width: 150px;" datatype="string" nullable="no" maxsize="20" chname="用户名" value="root" />
                    </td>
                </tr>
                <tr>
                    <td>
                        密&nbsp;&nbsp;&nbsp;&nbsp;码：
                    </td>
                    <td>
                        <input type="password" id="y" style="width: 150px;" class="easyui-validatebox input"
                            required="true" validtype="length[3,10]" datatype="string" nullable="no" maxsize="20" chname="密码" value="111111"/>
                    </td>
                </tr>
            </table>
            </html:form>
        </div>
             
        <div class="toolbar" style="text-align: center; margin-top: 20px;">
             <a href="#" class="easyui-linkbutton" iconcls="icon-ok" id="btnLogin" style="margin-left: 10px;">登录</a>
        </div>
    </div>
</body>
</html>
