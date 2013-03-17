<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<meta content="text/html; charset=UTF-8" http-equiv=Content-Type>
 <base target="_self">
</head>
<body 
style="BACKGROUND-COLOR: #6881A8; BORDER-BOTTOM: 0px; BORDER-LEFT: 0px; BORDER-RIGHT: 0px; BORDER-TOP: 0px; MARGIN: 0px" leftmargin="0" topmargin="0">
<script type="text/javascript">
	function ShowHideTree(){
		if (top.frame_mid.cols!="0,10,*"){
			top.frame_mid.cols="0,10,*";
			icon_arrow.src="<%=request.getContextPath()%>/images/open_left.gif";
		}else{
			top.frame_mid.cols="189,10,*";
			icon_arrow.src="<%=request.getContextPath()%>/images/close_left.gif";
		}
	}
</script>

<table height="100%" cellpadding="0" cellspacing="0" border="0" bgcolor="#DDDDDD" height="10">
  <tr>
    <td align="center" valign="middle"> 
      <p align="center"><img id='icon_arrow' onclick='ShowHideTree();' src="<%=request.getContextPath()%>/images/close_left.gif" 
      style="CURSOR: hand" title='隐藏或者展开目录树' width="10" height="141"></p>
</td>
</tr>
</table>
</body>
</html>