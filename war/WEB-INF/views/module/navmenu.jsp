<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
List  list = (List)request.getAttribute("list");
if(list!=null){
%>
<script language="javascript">
var total = <%=list.size()%>;
function selectTab(id, url){
  for(var i=1; i<id; i++){
    tabBlur(i);
  }
  for(var i=id+1; i<=total; i++){
    tabBlur(i);
  }
  
  tabFocus(id);  
  
  document.frames["mainFrame"].location.href = url;
}
function tabFocus(id){
  var t1 = document.getElementById("img"+id+"l");
  var t2 = document.getElementById("tb"+id+"m");
  var t3 = document.getElementById("img"+id+"r");
  t1.src = "<%=request.getContextPath()%>/images/tbl_01.gif";
  t2.className = "tbSelect";
  t3.src = "<%=request.getContextPath()%>/images/tbl_05.gif";
}
function tabBlur(id){
  var t1 = document.getElementById("img"+id+"l");
  var t2 = document.getElementById("tb"+id+"m");
  var t3 = document.getElementById("img"+id+"r");
  t1.src = "<%=request.getContextPath()%>/images/tbl3_01.gif";
  t2.className = "tbDeselect";
  t3.src = "<%=request.getContextPath()%>/images/tbl2_03.gif";
}
</script>
	 <table border="0" cellspacing="0" cellpadding="0">
        <tr> 
<%
  Iterator iter=list.iterator();
  int i=1;
  while(iter.hasNext()){
    SysTree bean=(SysTree)iter.next();
%>		
          <td valign="bottom" style="cursor:hand;padding-left:4px;" onClick="selectTab(<%=i%>, '<%=bean.getApp().getUrl()%>');"> 
            <table border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td><img src="<%=request.getContextPath()%>/images/tbl_01.gif" id="img<%=i%>l"></td>
                <td id="tb<%=i%>m" class="tbSelect"><%=bean.getName()%></td>
                <td><img src="<%=request.getContextPath()%>/images/tbl_05.gif" id="img<%=i%>r"></td>
              </tr>
            </table>
	  </td>
<%
    i++;
  }
%>
        </tr>
      </table>
<script language="JavaScript">
  for(var i=2; i<=total; i++){
    tabBlur(i);
  }  
  tabFocus(1);  
</script>
<%
}
%>