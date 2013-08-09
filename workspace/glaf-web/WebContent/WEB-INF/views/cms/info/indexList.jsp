<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%
  String context = request.getContextPath();
 
%>
<style type="text/css">                     
.aa:link{
    font-size: 12px;
    line-height: 18px;
	color: #696969;
    letter-spacing: 0.1em;
    text-decoration: none;
}
.aa:visited {
    font-size: 12px;
    line-height: 18px;
	color: #696969;
    letter-spacing: 0.1em;
    text-decoration: none;
}
.aa:hover {
    font-size: 12px;
    line-height: 18px;
    color: #FF8C00;
    letter-spacing: 0.1em;
    text-decoration: none;
}
.aa:active {
    font-size: 12px;
    line-height: 18px;
    color: #696969;
    letter-spacing: 0.1em;
    text-decoration: none;
}
</style>
<div class="demo-info">
        <div class="demo-tip icon-tip"></div>
    </div>
    <div style="margin:10px 10px;"></div>
    <div id="tt" class="easyui-tabs" style="height:250px;margin:5px 5px;">
	<c:forEach items="${rows}" var="map">
		<div title="${map.key}" style="padding:10px;vertical-align:middle">
		<c:forEach items="${map.value}" var="info">
		<table width="98%" border="0" cellspacing="1" cellpadding="0" valign="middle"  style="BORDER-RIGHT: #CCDDFF 1px solid; BORDER-TOP: #CCDDFF 1px solid; BORDER-LEFT: #CCDDFF 1px solid; BORDER-BOTTOM: #CCDDFF 1px solid;vertical-align:middle">
		<tr>
			<td width="75%" align="left" height="25" ><div style="overflow:hidden;text-overflow:ellipsis;word-break: keep-all;white-space:nowrap;width:230px;vertical-align:middle;"><a
				href='<%=request.getContextPath()%>/mx/public/info/view?id=${info.id}'
				title='${info.subject}' target="_blank" class="aa">.${info.subject}</a></div>
			</td>
			<td width="25%" align="center" height="25" ><div style="vertical-align:middle;">[<fmt:formatDate
				value="${info.createDate}" pattern="yyyy-MM-dd" />]</div>
			</td>
		</tr>
		</table>
		</c:forEach>
		</div>
	</c:forEach>
    </div>
    <script type="text/javascript">
        $(function(){
            var tabs = $('#tt').tabs().tabs('tabs');
            for(var i=0; i<tabs.length; i++){
                tabs[i].panel('options').tab.unbind().bind('mouseenter',{index:i},function(e){
                    $('#tt').tabs('select', e.data.index);
                });
            }
        });
    </script>