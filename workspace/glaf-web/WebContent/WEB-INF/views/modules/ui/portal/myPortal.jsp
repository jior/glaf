<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.*"%>
<%@ page import="com.alibaba.fastjson.*"%>
<%@ page import="com.glaf.core.security.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
 
	LoginContext loginContext = RequestUtils.getLoginContext(request);
	List<UserPortal> userPortals = (List<UserPortal>)request.getAttribute("userPortals");
	UserPanel userPanel = (UserPanel)request.getAttribute("userPanel");
	String layoutName = null;
	List<Panel> panels01 = new ArrayList<Panel>();
	List<Panel> panels02 = new ArrayList<Panel>();
	List<Panel> panels03 = new ArrayList<Panel>();
	StringBuffer sb = new StringBuffer(); 
	List alPanels=new ArrayList();
    JSONArray arr = new JSONArray();
	int columnQty = 2;
	if(userPanel != null){
	  layoutName = userPanel.getLayoutName();
	}
	if(layoutName==null){
		layoutName="P2";
	}
	if("P3".equals(layoutName)){
		columnQty=3;
	}
	if(userPortals != null && !userPortals.isEmpty()){
	  Set paneIds = new HashSet();
	  for(UserPortal p: userPortals){
		 if(p.getPanel() != null && p.getPanel().getLocked() == 0){
			 if(paneIds.contains(p.getPanel().getId())){
				 continue;
			 }
			 paneIds.add(p.getPanel().getId());
			 JSONObject jsonObject = new JSONObject();
             jsonObject.put("id", p.getPanel().getId());
			 jsonObject.put("title", p.getPanel().getTitle());
			 if(p.getPanel().getHeight() > 0){
			     jsonObject.put("height", p.getPanel().getHeight());
			 } else {
				 jsonObject.put("height", 300);
			 }
			 jsonObject.put("closable", p.getPanel().canClosable());
			 jsonObject.put("collapsible", p.getPanel().canCollapsible());
			 
             if(p.getPanel().getContent() != null && p.getPanel().getContent().trim().length()>0 ){
                  jsonObject.put("href", request.getContextPath()+"/mx/panel/content?pid="+p.getPanel().getId());
			 } else {
                 if(p.getPanel().getLink() != null){
					if(p.getPanel().getLink().startsWith("/mx/") || p.getPanel().getLink().indexOf(".do?")!=-1){
			           jsonObject.put("href", request.getContextPath()+p.getPanel().getLink());
					} else {
					   jsonObject.put("href", p.getPanel().getLink());
					}
			   }
			 }
			 if(jsonObject.get("href") == null){
			    continue;
			 }

			 arr.add(jsonObject);
             

		     if(0 == p.getColumnIndex()){
                panels01.add(p.getPanel());
			 } else if(1 == p.getColumnIndex()){
                panels02.add(p.getPanel());
			 } else if(2 == p.getColumnIndex()){
				 if("P3".equals(layoutName)){
                    panels03.add(p.getPanel());
				 } else {
					panels01.add(p.getPanel());
				 }
			 } else {
				 panels01.add(p.getPanel());
			 }
		 }
	   }
	 }

	 Iterator iter = panels01.iterator();
     while(iter.hasNext()){
         Panel p = (Panel)iter.next();
		 sb.append(p.getId());
		 if(iter.hasNext()){
			 sb.append(",");
		 }
	 }

	 
	sb.append(":");
	 
   
	 iter = panels02.iterator();
     while(iter.hasNext()){
         Panel p = (Panel)iter.next();
		 sb.append(p.getId());
		 if(iter.hasNext()){
			 sb.append(",");
		 }
	 }
      
	 if("P3".equals(layoutName)){
		 sb.append(":");
		
		 iter = panels03.iterator();
		 while(iter.hasNext()){
			 Panel p = (Panel)iter.next();
			 sb.append(p.getId());
			 if(iter.hasNext()){
				 sb.append(",");
			 }
		 }
	 }
	
	String defaultHeight="height:250px;";
    //System.out.println(arr.toJSONString());
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/easyui/css/portal.css" type="text/css"></link>
<style type="text/css">
	.mx_title{
			font-size:13px;
			font-weight:bold;
			padding:5px 10px;
			background:#eee;
			overflow:hidden;
			border-bottom:1px solid #ccc;
	}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>		
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.portal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript">

		var panels =  <%=arr.toJSONString()%>;
		 
		function getPanelOptions(id){
			for(var i=0; i<panels.length; i++){
				if (panels[i].id == id){
					return panels[i];
				}
			}
			return undefined;
		}

		function getPortalState(){
			var aa = [];
			for(var columnIndex=0; columnIndex < <%=columnQty%>; columnIndex++){
				var cc = [];
				var panels = $('#pp').portal('getPanels', columnIndex);
				for(var i=0; i<panels.length; i++){
					cc.push(panels[i].attr('id'));
				}
				aa.push(cc.join(','));
			}
			return aa.join(':');
		}

		function addPanels(portalState){
			var columns = portalState.split(':');
			for(var columnIndex=0; columnIndex<columns.length; columnIndex++){
				var cc = columns[columnIndex].split(',');
				for(var j=0; j<cc.length; j++){
					var options = getPanelOptions(cc[j]);
					if (options){
						var p = $('<div/>').attr('id',options.id).appendTo('body');
						p.panel(options);
						$('#pp').portal('add',{
							panel:p,
							columnIndex:columnIndex
						});
					}
				}
			}
		}
		
		$(function(){
			$('#pp').portal({
				fit:true,
				border:false,
				onStateChange:function(){
					$("#mx_toolbar").show();
				}
			});
			var state  = '<%=sb.toString()%>'; 
			addPanels(state);
			$('#pp').portal('resize');
		});

		function savePortal(){
             var state = getPortalState();
			 //alert(state);
			 $.ajax({
				type: "POST",
				url:"<%=request.getContextPath()%>/mx/user/portal/savePortal",
				data:{"portalState":state},
				success:function(){
					alert('保存成功！')
					window.location.reload();								
				},
				error:function(){
					alert('保存失败！');
					return;
				}
			 });
		}

        function openMoreMsg() {
	        openWindow('<%=request.getContextPath()%>/workspace/message.do?method=showReceiveList', 600, 450);
	    }

	    function openMsg(id) {
		    openWindow('<%=request.getContextPath()%>/workspace/message.do?method=showMessage&id=' + id, 600, 450);
	    }

</script>
</head>
<body class="easyui-layout">
    <%if("true".equals(request.getParameter("edit"))){%>
	<div id="mx_toolbar" region="north" class="mx_title" border="false" > 
	  <div  style="text-align:right;padding-right:10px;" >
		 <a href="#" onClick="javascript:location.reload();" target="_self"><span><img src="<%=request.getContextPath()%>/images/refresh.gif" border="0"/>&nbsp;刷新</span></a>
		&nbsp;<a href="#" id="subButton" onclick="javascript:savePortal();"><span><img src="<%=request.getContextPath()%>/images/save.gif" border="0"/>保存</span></a>
	  </div>
	</div>
	<%}%>
	<div region="center" border="false">
		<div id="pp" style="position:relative">
		  <%if("P3".equals(layoutName)){%>
		    <div style="width:28%;">
			</div>
			<div style="width:40%;">
			</div>
			<div style="width:28%;">
			</div>
		  <% }else{ %>
			<div style="width:60%; ">
			</div>
			<div style="width:25%;">
			</div>
		 <%}%>
		</div>
	</div>
</body>
</html>
 