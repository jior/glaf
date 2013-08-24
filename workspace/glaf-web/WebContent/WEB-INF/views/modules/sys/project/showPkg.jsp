<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%
       String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导出</title>
 
<script language="javascript">
  String.prototype.trim = function() {
     return this.replace(/(^\s*)|(\s*$)/g, "");
  }

  function checkAll(){
	  var obj = document.getElementsByName("ext");
	  for(var i = 0; i < obj.length; i++)	{
		var e = obj.item(i);
		if(!e.checked){
			e.checked = true;
		}
	}
  }

  function submitRequest(){
	var includes="";
    var obj = document.getElementsByName("ext");
	for(var i = 0; i < obj.length; i++)	{
		var e = obj.item(i);
		if(e.checked){
			includes+=e.value+",";
		} 
	}
	document.getElementById("includes").value=includes;
    document.iForm.submit();
  }
 

</script>
</head>
<body>
<center> 

<form name="iForm" method="post"
	action="<%=request.getContextPath()%>/mx/sys/project/export"
	class="x-form"> 
<input type="hidden" name="includes" id="includes">
<div class="content-block" style="width: 585px;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="文件导出">&nbsp;文件导出</div>
<br>
 
<table border=0 cellspacing=0 cellpadding=2 >
	<tbody>
		<tr class="x-content-hight" >
			<td style="width: 150px; padding-right: 10px" align="left"><span
				class="field-name-required">请选择导出文件后缀</span></td>
			<td align="left">
               <input type="checkbox" name="ext" value="js">js
			   <input type="checkbox" name="ext" value="jsp">jsp
			   <input type="checkbox" name="ext" value="ftl">css
			   <input type="checkbox" name="ext" value="htm">htm
			   <input type="checkbox" name="ext" value="html">html
			   <br>
			   <input type="checkbox" name="ext" value="ftl">ftl
			   <input type="checkbox" name="ext" value="xml">xml
			   <input type="checkbox" name="ext" value="sql">sql
			   <input type="checkbox" name="ext" value="jasper">jasper
			   <input type="checkbox" name="ext" value="jrxml">jrxml
			   <br>
			   <input type="checkbox" name="ext" value="properties">properties
			   <input type="checkbox" name="ext" value="icon">icon
			   <input type="checkbox" name="ext" value="png">png
			   <input type="checkbox" name="ext" value="jpg">jpg
			   <input type="checkbox" name="ext" value="gif">gif
			   <br>
			   <input type="checkbox" name="ext" value="txt">txt
			   <input type="checkbox" name="ext" value="pdf">pdf
			   <input type="checkbox" name="ext" value="xls">xls
			   <input type="checkbox" name="ext" value="doc">doc
			   <input type="checkbox" name="ext" value="ppt">ppt
			   <br>
			   <input type="checkbox" name="ext" value="zip">zip
			   <input type="checkbox" name="ext" value="tar">tar
			   <input type="checkbox" name="ext" value="gz">gz
			   <input type="checkbox" name="ext" value="sh">sh
			   <input type="checkbox" name="ext" value="bat">bat
			   <br>
			   <input type="checkbox" name="ext" value="class">class
			   <input type="checkbox" name="ext" value="java">java
			   <input type="checkbox" name="ext" value="ini">ini
			   <input type="checkbox" name="ext" value="csv">csv
			   <input type="checkbox" name="ext" value="dic">dic
			   <br>
			   <input type="checkbox" name="ext" value="dll">dll
			   <input type="checkbox" name="ext" value="conf">conf
			   <input type="checkbox" name="ext" value="docx">docx
			   <input type="checkbox" name="ext" value="xlsx">xlsx
			   <input type="checkbox" name="ext" value="ppt">pptx
			   <br>
			   <input type="checkbox" name="ext" value="dtd">dtd
			   <input type="checkbox" name="ext" value="json">json
			   <input type="checkbox" name="ext" value="odc">odc
			   <input type="checkbox" name="ext" value="odf">odf
			   <input type="checkbox" name="ext" value="rar">rar
            </td>
		</tr>

</table>
 

<div align="center"><br />
<input type="button" class="btn" value="全选"
	onclick="javascript:checkAll();" />
&nbsp;
<input type="button" class="btn btn-primary" value="确定"
	onclick="javascript:submitRequest();" /> <br />
<br />
</div>


</div>
</form>
</center>
<br />
<br />