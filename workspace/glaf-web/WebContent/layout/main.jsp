<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="com.glaf.base.utils.*" %>
<%@ page import="com.glaf.base.business.*" %>
<%@ page import="com.glaf.core.context.*" %>
<%@ page import="com.glaf.core.util.*" %>
<%@ page import="com.glaf.base.modules.sys.model.*" %>
<%@ page import="com.glaf.base.modules.sys.service.*" %>
<%
	 
    String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(context);
	pageContext.setAttribute("contextPath", context);
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);

	String userId = RequestUtils.getActorId(request);
 	SysApplicationService sysApplicationService = ContextFactory.getBean("sysApplicationService");
	JSONArray array = sysApplicationService.getUserMenu(3, userId);
	String scripts = array.toString('\n');
%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>GLAF基础应用框架</title>
<link href="${contextPath}/layout/css/default.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/scripts/easyui/themes/${theme}/easyui.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/themes/${theme}/styles.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/icons/styles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/jquery.cookie.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src='${contextPath}/layout/js/outlook.js'> </script>
<script type="text/javascript">
   var contextPath = "<%=request.getContextPath()%>";

   function setMyTheme(){
	   var theme = $('#theme').val();
	   jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/theme/set?theme='+theme,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					    location.reload();
				   }
			 });
   }

       function changeTheme(){
	    $('#w').window({
                title: '设置主题',
                width: 300,
                modal: true,
                shadow: true,
                closed: true,
                height: 160,
                resizable:false
            });
   }

    $(function() {
        changeTheme();
        $('#editTheme').click(function() {
            $('#w').window('open');
        });           
	    $('#btnCancel').click(function(){closePwd();});

		$('#loginOut').click(function() {
             $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
                  if (r) {
                        location.href = '${contextPath}/login.do?method=logout';
                    }
                });
         })

    });

    var _menus = {
       basic:  <%=scripts%>
    };
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
    <noscript>
        <div style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px;
            width: 100%; background: white; text-align: center;">
            <img src="${contextPath}/images/noscript.gif" alt='抱歉，请开启脚本支持！' />
        </div>
    </noscript>
    <div region="north" split="true" border="false" style="overflow: hidden; height: 63px;
        background: url(${contextPath}/themes/${theme}/images/top_bar_bg.jpg) #7f99be repeat-x center 50%;
        line-height: 63px; color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float: right; padding-right: 20px;" class="head">欢迎 admin <a href="#" id="editTheme">
            切换主题</a> <a href="#" id="loginOut">退出</a></span> <span style="padding-left: 10px;
                font-size: 18px; float: left;">
                <img src="${contextPath}/images/logo.png" width="53" height="53" align="absmiddle" />
                GLAF基础应用框架</span>
        <ul id="css3menu" style="padding: 0px; margin: 0px; list-type: none; float: left; 
            margin-left: 40px;">
             <a class="active" name="basic" href="javascript:;" title="常用菜单">
			<span style="font-size: 16px;"></span></a> 
            <!-- <li><a name="point" href="javascript:;" title="邮件列表">
			<span style="font-size: 16px;">邮件列表</span></a></li> -->
        </ul>
    </div>
    <div region="south" split="true" style="height: 30px; background: #eee;">
        <div class="footer"> By GLAF基础应用框架</div>
    </div>
    <div region="west" hide="true" split="true" title="导航菜单" style="width: 180px;" id="west">
        <div id='wnav' class="easyui-accordion" fit="true" border="false">
            <!--  导航内容 -->
        </div>
    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y: hidden">
        <div id="tabs" class="easyui-tabs" fit="true" border="false">
            <div title="欢迎使用" style="padding: 20px; overflow: hidden;" id="home">
                <h1> Welcome !</h1>
            </div>
        </div>
    </div>
    <!--切换主题窗口-->
    <div id="w" class="easyui-window" title="切换主题" collapsible="false" minimizable="false"
        maximizable="false" icon="icon-save" style="width: 300px; height: 150px; padding: 5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <table cellpadding="3">
                    <tr>
                        <td>
                          请选择主题   
                        </td>
                        <td>
                            <select id="theme" name="theme">
								<option value="default" selected>蓝色</option>
								<option value="gray">灰色</option>
								<!-- <option value="red">红色</option> -->
								<option value="sunny">橙色</option>
                             </select> 
                        </td>
                    </tr>
                     
                </table>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:setMyTheme();">确定</a>
            </div>
        </div>
    </div>
    <div id="mm" class="easyui-menu" style="width: 150px;">
        <div id="mm-tabupdate">刷新</div>
        <div class="menu-sep"></div>
        <div id="mm-tabclose">关闭</div>
        <div id="mm-tabcloseall">全部关闭</div>
        <div id="mm-tabcloseother">除此之外全部关闭</div>
        <div class="menu-sep"></div>
        <div id="mm-tabcloseright">当前页右侧全部关闭</div>
        <div id="mm-tabcloseleft">当前页左侧全部关闭</div>
    </div>
</body>
</html>
