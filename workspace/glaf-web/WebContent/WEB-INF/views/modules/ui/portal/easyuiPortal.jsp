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
			 jsonObject.put("closable", p.getPanel().isClose());
			 jsonObject.put("collapsible", p.getPanel().isCollapsible());
			 
             if(p.getPanel().getContent() != null && p.getPanel().getContent().trim().length()>0 ){
                  jsonObject.put("href", request.getContextPath()+"/mx/panel/content?pid="+p.getPanel().getId());
			 } else {
                 if(p.getPanel().getLink() != null){
					if(p.getPanel().getLink().startsWith("/mx/")){
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

	 String disp = "none";
	 if("true".equals(request.getParameter("edit"))){
		 disp="block";
	 }
	
	String defaultHeight="height:250px;";
    //System.out.println(arr.toJSONString());
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/easyui/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/easyui/themes/icon.css" type="text/css"></link>
<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/easyui/themes/portal.css" type="text/css"></link>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>		
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.portal.js"></script>
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
			for(var columnIndex=0; columnIndex<<%=columnQty%>; columnIndex++){
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
					//$("#vista_toolbar").show();
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
				url:"<%=request.getContextPath()%>/rs/user/portal/savePortal",
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
	
	</script>
</head>
<body class="easyui-layout">
	<div region="north" class="title" border="false" >
	  <div id="vista_toolbar" style="display:<%=disp%>;">
		<ul id="navs">
			<li style="float:right;"><a href="#" onClick="javascript:location.reload();" target="_self"><span><img src="<%=request.getContextPath()%>/images/refresh.gif" />&nbsp;刷新</span></a></li>
			<li style="float:right;">&nbsp;<a href="#" id="subButton" onclick="javascript:savePortal();"><span><img src="<%=request.getContextPath()%>/images/save.gif" />保存</span></a></li>
		</ul>
	  </div>
	</div>
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
			<div style="width:68%; ">
			</div>
			<div style="width:30%;">
			</div>
		 <%}%>
		</div>
	</div>
</body>
</html>
 