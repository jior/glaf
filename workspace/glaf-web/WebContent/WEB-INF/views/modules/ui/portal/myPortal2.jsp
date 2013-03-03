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
	

	String skin = "gray";

 	String styles = "/skins/"+skin+"/styles.css";

	String defaultHeight="height:250px;";
    //System.out.println(arr.toJSONString());
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/extjs/ux/css/Portal.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/extjs/resources/css/xtheme-<%=skin%>.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%><%=styles%>" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/extjs/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/extjs/ux/Portal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/extjs/ux/PortalColumn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/extjs/ux/Portlet.js"></script>

<script type="text/javascript">
Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/scripts/extjs/resources/images/default/s.gif';
 
Ext.onReady(function(){


    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    var tools = [{
        id:'close',
        handler: function(e, target, panel){
            panel.ownerCt.remove(panel, true);
        }
    }];

    var viewport = new Ext.Viewport({
        layout:'border',
        items:[ {
            xtype:'portal',
            region:'center',
            margins:'0 0 0 0',//边距
            items:[{
                columnWidth:.73,
                style:'padding:10px 0 10px 10px',
                items:[
				<%
					Iterator it001 = panels01.iterator();
				    while(it001.hasNext()){
						Panel pp = (Panel)it001.next();
                        if(pp.getContent() != null && pp.getContent().trim().length()>0 ){
						   pp.setLink("/mx/panel/content?pid="+pp.getId());
						}
						
				%>	
				{
					id: '<%=pp.getId()%>',
                    title: '<%=pp.getTitle()%>',
                    layout:'fit',
                    tools: tools,
                    autoLoad: {url:'<%=request.getContextPath()%><%=pp.getLink()%>', scripts:true}
                }
				<% if(it001.hasNext()){
					  out.println(",");
					}
				   }
				%>
				]
            },{
                columnWidth:.25,
                style:'padding:10px 0 20px 20px',
                items:[<%
					Iterator it002 = panels02.iterator();
				    while(it002.hasNext()){
						Panel pp = (Panel)it002.next();
						if(pp.getContent() != null && pp.getContent().trim().length()>0 ){
						   pp.setLink("/mx/panel/content?pid="+pp.getId());
						}
				%>	
				{
					id: '<%=pp.getId()%>',
                    title: '<%=pp.getTitle()%>',
                    layout:'fit',
                    tools: tools,
                    autoLoad: {url:'<%=request.getContextPath()%><%=pp.getLink()%>', scripts:true}
                }
				<% if(it002.hasNext()){
					  out.println(",");
					}
				   }
				%>]
            }]
            
            ,listeners: {
                'drop': function(e){
                    Ext.Msg.alert('Portlet Dropped', e.panel.id+" "+e.panel.title + '<br />Column: ' + 
                        e.columnIndex + '<br />Position: ' + e.position);
					var link ="<%=request.getContextPath()%>/rs/user/portal/update?panelId="+e.panel.id+"&columnIndex="+e.columnIndex+"&position="+e.position;
					Ext.Ajax.request({
					   url: link, 
					   method: 'POST', 
					   callback: function (options, success, response) {
						   if (success) {  
							 
						   } else {
						
						   } 
					   }
					 });
                }
            }

        }]
    });
});


	
</script>
</head>
<body style="overflow:hidden;">
</body>
</html>
 