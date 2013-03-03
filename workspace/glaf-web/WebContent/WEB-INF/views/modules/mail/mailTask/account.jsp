<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.mail.domain.*"%>
<%
            StringBuffer bufferx = new StringBuffer();
			StringBuffer buffery = new StringBuffer();
	        List accounts = (List)request.getAttribute("accounts");
			List selecteds = (List)request.getAttribute("selecteds");
            if(accounts != null && accounts.size() > 0){
				for(int j=0;j<accounts.size();j++){
					MailAccount r = (MailAccount) accounts.get(j);
					if(selecteds != null && selecteds.contains(r.getId())){
                            buffery.append("\n<option value=\"").append(r.getId()).append("\" selected>")
								        .append(r.getShowName())
								        .append("[").append(r.getMailAddress()).append("]")
								        .append("</option>");
					} else {
						    bufferx.append("\n<option value=\"").append(r.getId()).append("\">")
								        .append(r.getShowName())
								        .append("[").append(r.getMailAddress()).append("]")
								        .append("</option>");
					}
				}
			}

%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>邮件任务</title>
	<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
	
	 <script type="text/javascript">

     function addElement() {

        var list = document.iForm.noselected;
        for (i = 0; i < list.length; i++) {
            if (list.options[i].selected) {
                var value = list.options[i].value;
                var text = list.options[i].text;
                addToList(value, text);
				list.remove(i);
				i=i-1;
            }
        }

    }

	 function addToList(value, text) {
			var list = document.iForm.selected;
			if (list.length > 0) {
				for (k = 0; k < list.length; k++) {
					if (list.options[k].value == value) {
						return;
					}
				}
			}

			var len = list.options.length;
			list.length = len + 1;
			list.options[len].value = value;
			list.options[len].text = text;
		}

	 function removeElement() {
			var list = document.iForm.selected;
			var slist = document.iForm.noselected;
			if (list.length == 0 || list.selectedIndex < 0 || list.selectedIndex >= list.options.length)
				return;

			for (i = 0; i < list.length; i++) {
				if (list.options[i].selected) {
					var value = list.options[i].value;
					var text = list.options[i].text;
					list.options[i] = null;
					i--;
					var len = slist.options.length;
					slist.length = len+1;
					slist.options[len].value = value;
					slist.options[len].text = text;				
				}
			}
		}
	  

	     function saveData(){
				   	var len= document.iForm.selected.length;
					var result = "";
					var names = "";
					for (var i=0;i<len;i++) {
					  result = result + document.iForm.selected.options[i].value;
					  names = names + document.iForm.selected.options[i].text;
					  if(i < (len - 1)){
						  result = result + ",";
					   }
					}

				   document.getElementById("accountIds").value = result;
			       var params = jQuery("#iForm").formSerialize();
				   //alert(params);
				    jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/mail/mailTask/saveAccounts',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   alert('操作成功！');
					   location.href="<%=request.getContextPath()%>/mx/mail/mailTask";
				   }
			 });
			}

	 </script>
</head>
<body>
<div class="content-block" style="width: 845px;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="发送帐户设置">&nbsp;发送帐户设置</div>
<fieldset class="x-fieldset" style="width: 96%;">
	 <form id="iForm" name="iForm" method="post">
	    <input type="hidden" id="accountIds" name="accountIds"/>
	     <input type="hidden" id="taskId" name="taskId" value="${taskId}">
		 <table class="table-border" align="center" cellpadding="4" cellspacing="1" width="90%">
			<tbody>
				<tr>
					<td class="beta" colspan="2">
					<div align="center"><b>可选帐户</b></div>
					</td>
					<td class="beta"></td>
					<td class="beta" colspan="3">
					<div align="center"><b>已选帐户</b></div>
					</td>
				</tr>
				<tr>
					<td class="beta" width="18">&nbsp;</td>
					<td class="table-content" height="26" valign="top" width="320">
					<div align="center"><select class="list"
						style="width: 250px; height: 250px;" multiple="multiple" size="12"
						name="noselected" ondblclick="addElement()"><%=bufferx.toString()%>
					</select></div>
					</td>
					<td class="beta" width="114">
					<div align="center"><input name="add" value="添加 ->"
						onclick="addElement()" class="btn" type="button"> <br>
					<br>
					<input name="remove" value="<- 移除" onclick="removeElement()"
						class="btn" type="button"></div>
					</td>
					<td class="table-content" height="26" valign="top" width="320">
					<div align="center"><select class="list"
						style="width: 250px; height: 250px;" multiple="multiple" size="12"
						name="selected" ondblclick="removeElement()"><%=buffery.toString()%>
					</select></div>
					</td>
					<td class="beta" width="23">&nbsp;</td>
				</tr>
			</tbody>
		</table>
	   <table class="easyui-form" style="width:700px;height:80px">
		<tbody>
			<tr>
				 <td colspan="4" align="center">
				     <br />
				     <input type="button" name="save" value="保存" class="btn btn-primary" onclick="javascript:saveData();" />
					 <input type="button" name="back" value="返回" class="btn" onclick="javascript:history.back();" />
				 </td>
			</tr>
		</tbody>
	</table>
  </form>
  </fieldset>
  </div>
</body>
</html>