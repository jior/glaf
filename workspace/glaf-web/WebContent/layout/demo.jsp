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
<link href="<%=request.getContextPath()%>/layout/css/styles.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/${theme}/styles.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/icons/styles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/layout/js/outlook3.js'> </script>
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
                        //document.getElementById("themeui").href=contextPath+"/scripts/easyui/themes/"+theme+"/easyui.css";
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

	var _mxm_ = {
	  "children":  <%=scripts%>
    };

</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden"  fit="true"   scroll="no">
<noscript>
<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
</div>
</noscript>

<div id="loading-mask" style="position:absolute;top:0px; left:0px; width:100%; height:100%; background:#D2E0F2; z-index:20000">
<div id="pageloading" style="position:absolute; top:50%; left:50%; margin:-120px 0px 0px -120px; text-align:center;  border:2px solid #8DB2E3; width:200px; height:40px;  font-size:14px;padding:10px; font-weight:bold; background:#fff; color:#15428B;"> 
    <img src="${contextPath}/images/loading.gif" align="absmiddle" /> 正在加载中,请稍候...</div>
</div>

<div region="north" split="true" border="false" style="overflow: hidden; height: 63px;
        background: url(${contextPath}/themes/${theme}/images/top_bar_bg.jpg) #7f99be repeat-x center 50%;
        line-height: 63px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head">
		欢迎 <%=RequestUtils.getLoginContext(request).getUser().getName()%> 
		<a href="#" id="editTheme" onclick="javascript:changeTheme();">切换主题</a>
		<a href="#" id="loginOut">退出</a>
        </span>
        <span style="padding-left:10px; font-size: 24px; ">
		<img src="images/logo.gif" border="0" align="absmiddle" /> GLAF基础应用框架</span>
</div>

<div region="south" split="true" class="south-backgroud" style="height: 30px;   ">
        <div class="footer">By &nbsp;Global Application Framework</div>
</div>

<div region="west" split="true"  title="导航菜单" style="width:180px;" id="west">
			<div id="nav">
		     <!--  导航内容 -->
				
			</div>
</div>

<div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<div title="我的桌面" style="padding:0px;overflow:hidden; color:red; " >
				<iframe
					src="${contextPath}/mx/user/portal"
					width='100%' height='100%' frameborder='0' scrolling='no' noResize></iframe>
			</div>
		</div>
</div>
    
    
    <!--修改密码窗口-->
    <div id="w" class="easyui-window" title="修改密码" collapsible="false" 
         minimizable="false" maximizable="false" icon="icon-save"  
		 style="width: 300px; height: 150px; padding: 5px; background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <table cellpadding=3>
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
							 <script type="text/javascript">
							    document.getElementById("theme").value="${theme}";
							 </script>
                        </td>
                    </tr>
                </table>
            </div>
            <div region="south" border="false" style="text-align: right; margin-top:2px; height: 32px; line-height: 32px;">
                <input type="button" id="btnEp" class="button"  value="确 定"   onclick="javascript:setMyTheme();"/> 
            </div>
        </div>
    </div>

	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="close">关闭</div>
		<div id="closeall">全部关闭</div>
		<div id="closeother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="closeright">当前页右侧全部关闭</div>
		<div id="closeleft">当前页左侧全部关闭</div>
	</div>

</body>
</html>