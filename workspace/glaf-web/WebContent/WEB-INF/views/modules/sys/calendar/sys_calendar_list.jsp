<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.glaf.core.util.DateUtils"%>
<%@page import="com.glaf.core.domain.SysCalendar"%>
<%

 	java.util.Map<String, SysCalendar> map  = (java.util.Map)request.getAttribute("map");
	Integer year = (Integer) request.getAttribute("year");
	Integer month =(Integer) request.getAttribute("month");
	Integer e = (Integer) request.getAttribute("daySize");
	String productionLine = (String)request.getAttribute("productionLine") ;
	java.util.Date today = new java.util.Date();
	java.util.Calendar sDate = java.util.Calendar.getInstance();
	sDate.setTime(new java.util.Date(year-1900, month-1, 1));
	int s = sDate.get(java.util.Calendar.DAY_OF_WEEK)-1;
 	
	Properties props = com.glaf.core.util.CalendarUtils.loadCalendarProperties();
	String morning = (String)props.get("morning");
	String nooning = (String)props.get("nooning");
	String evening = (String)props.get("evening");
	String mode = (String)props.get("mode");
	if(morning==null)morning="00:00~00:00";
	if(nooning==null)nooning="00:00~00:00";
	if(evening==null)evening="00:00~00:00";
	
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作日历</title>
<link href="<%=request.getContextPath()%>/scripts/artDialog/skins/default.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<style>
.calendar_head td {padding:5px 2px 2px 2px;background-color:#C0C0C0;text-align:center;font-weight:bold;font-size:16px;}
.calendar_body td {padding:2px 2px 2px 2px;background-color:#FFFFFF;}
</style>
<script type="text/javascript">

   var contextPath="<%=request.getContextPath()%>";

	function changeXYTime(obj){
		//alert(obj.value);
		var m1 = document.getElementById("m1");
		var m2 = document.getElementById("m2");
		var mode = document.getElementById("mode");
		if(obj.value=='修改时间'){
			obj.value='保存时间';
			m1.innerHTML = changeStr(m1);
			m2.innerHTML = changeStr(m2);
		} else {
			var v1 = changeStr1(m1);
			var v2 = changeStr1(m2);
			var modeValue = mode.value;
			jQuery.post("<%=request.getContextPath()%>/mx/sys/calendar/saveCalendarTime", 
				{Action:"post",v1:v1,v2:v2,mode:modeValue}, 
				function (data,textStatus){
					if(textStatus=='success'){
						alert("保存成功！");
						obj.value='修改时间';
						m1.innerHTML = v1;
						m2.innerHTML = v2;
					} else {
						alert("保存时间失败！");
					}
				}
			);
		}
	}

	function changeStr(ob){
		initSelect(ob);
		return document.getElementById("timeTemp").innerHTML;
	}

	function changeStr1(ob){
		var objArray = $(ob).find("select");
		return objArray[0].value+":"+objArray[1].value+"~"+objArray[2].value+":"+objArray[3].value;
	}
	
	function initSelect(ob){
		var valueArray1 = ob.innerHTML.split("~");
		var valueArray2 = valueArray1[0].split(":");
		var valueArray3 = valueArray1[1].split(":");
		var h1 = document.getElementById("h1");
		var s1 = document.getElementById("s1");
		var h2 = document.getElementById("h2");
		var s2 = document.getElementById("s2");
		h1.options.length=0;
		h2.options.length=0;
		s1.options.length=0;
		s2.options.length=0;
		for(var i=0;i<24;i++){
			var k=""+i;
			if(k.length==1)k="0"+k;
			h1.options[h1.options.length] = new Option(k,k);
			h2.options[h2.options.length] = new Option(k,k);
			if(valueArray2[0]==k)h1.options[h1.options.length-1].selected=true;
			if(valueArray3[0]==k)h2.options[h2.options.length-1].selected=true;
		}
		for(var i=0;i<60;i++){
			var k=""+i;
			if(k.length==1)k="0"+k;
			s1.options[s1.options.length] = new Option(k,k);
			s2.options[s2.options.length] = new Option(k,k);
			if(valueArray2[1]==k)s1.options[s1.options.length-1].selected=true;
			if(valueArray3[1]==k)s2.options[s2.options.length-1].selected=true;
		}
	}

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/calendar/save',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						 alert(data.message);
					   } else {
						 alert('操作成功完成！');
					   }
					   window.location.reload();
				   }
			 });
	}
	
	function changeDate(){
		var params = jQuery("#iForm").formSerialize();
		window.location.href="<%=request.getContextPath()%>/mx/sys/calendar?"+params;
	}
	 
</script>
</head>
<body style="margin:10px;">  
<div id="timeTemp" style="display:none">
  <select name="h1" id="h1">
  </select>:
  <select name="s1" id="s1">
  </select>~
  <select name="h2" id="h2">
  </select>:
  <select name="s2" id="s2">
  </select>
</div>
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'center',border:true">
	 <form id="iForm" name="iForm" method="post">
	 <table width="100%">
	<tr>
		<td valign="top">
			<b style="font-size:16px;"><%=year%>-<%=month%>&nbsp;&nbsp;&nbsp;&nbsp;</b>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  		<input type="hidden" name="productionLine" value="<%=productionLine%>">
			<select id="year" name="year" onchange="javascript:changeDate();">
			<%
				for (int i=today.getYear()+1900-3; i<=today.getYear()+1901; i++) {
					out.print("<option value=\""+i+"\""+(i==year?" selected=\"selected\"":"")+">"+i+"</option>");
				}
			%>
			</select>
			-
			<select id="month" name="month" onChange="javascript:changeDate();">
			<%
				for (int i=1; i<=12; i++) {
					out.print("<option value=\""+i+"\""+(i==month?" selected=\"selected\"":"")+">"+i+"</option>");
				}
			%>
			</select>
	  </td>
	</tr>
</table>
	 <table width="100%" border="0" cellspacing="1" cellpadding="0" style="table-layout:fixed;background-color:#999999;">
	<tr class="calendar_head">
		<td>日</td>
		<td>一</td>
		<td>二</td>
		<td>三</td>
		<td>四</td>
		<td>五</td>
		<td>六</td>
	</tr>
	<%
	for (int r=0, i=0, d=0; r<6; r++) {
%>
	<tr class="calendar_body">
<%
	for (int c=0; c<7; c++, i++) {
			if (i>=s && d<e) {
				d++;
				String key = productionLine+""+year+""+month+""+d;
				SysCalendar cal = map.get(key);
%>
       <td style="<%=null!=cal&&cal.getIsFreeDay()==1?"background-color:#E4E4E4;":""%>">
			<table cellpadding="0" cellspacing="0" width="100%" >
				<tr>
					<td style="<%=null!=cal&&cal.getIsFreeDay()==1?"background-color:#E4E4E4;":""%>"><input type="checkbox" name="checkDay_<%=d%>" value="1"/></td>
					<td style="font-size:24px;text-align:right;<%=null!=cal&&cal.getIsFreeDay()==1?"background-color:#E4E4E4;":""%>"><%=d%></td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="1" width="100%" bgcolor="#999999" style="margin-top:5px;">
				<tr bgcolor="#FFFFFF">
					<td style="padding:0px 4px 0px 4px;<%=null!=cal&&"1".equals(cal.getDutyA())?"background-color:#00ee00;color:#FFFFFF;":null!=cal&&"2".equals(cal.getDutyA())?"background-color:#2a00ff;color:#FFFFFF;":""%><%=null!=cal&&cal.getIsFreeDay()==1?"background-color:#E4E4E4;":""%>">A班</td>
					<td style="padding:0px 4px 0px 4px;<%=null!=cal&&"1".equals(cal.getDutyB())?"background-color:#00ee00;color:#FFFFFF;":null!=cal&&"2".equals(cal.getDutyB())?"background-color:#2a00ff;color:#FFFFFF;":""%><%=null!=cal&&cal.getIsFreeDay()==1?"background-color:#E4E4E4;":""%>">B班</td>
				</tr>
			</table>
		</td>
<%
			} else {
%>
		<td>&nbsp;</td>
<%
			}
		}
%>
	</tr>
<%
	}
%>
</table>
<table>
	<tr>
		<td style="padding-left:200px;">
			<table>
				<tr>
					<td rowspan="2">将已选项目设为：&nbsp;&nbsp;</td>
					<td>A班&nbsp;&nbsp;</td>
					<td>
						<select name="groupA">
							<option value="-1">不改变</option>
							<option value="0">非工作日</option>
							<option value="1">早班</option>
							<option value="2">晚班</option>
						</select>
					</td>	
				
					<td rowspan="2">&nbsp;&nbsp;
					  <input type="button" name="save" id="save" value=" 保 存 " 
					       onclick="javascript:saveData();" class="button"/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>

					<td nowrap style="padding:0px 4px 0px 4px;background-color:#00ee00;color:#FFFFFF;">
					早班：<span id="m1"><%=morning%></span> 
					</td>

					<td rowspan="2" style="padding-left:8px">
					<select name="mode" id="mode">
						<option value="0" <%="0".equals(mode)?"selected":"" %>>早班时间为当日</option>
						<option value="1" <%="1".equals(mode)?"selected":"" %>>早班时间为前日</option>
					</select>
					<input name="changeTime" type="button" id="changeTime" value="修改时间" 
					       onclick="javascript:changeXYTime(this);">
				</tr>
				<tr>
					<td>B班&nbsp;&nbsp;</td>
					<td>
						<select name="groupB">
							<option value="-1">不改变</option>
							<option value="0">非工作日</option>
							<option value="1">早班</option>
							<option value="2">晚班</option>
						</select>
					</td>					
					<td nowrap style="padding:0px 4px 0px 4px;background-color:#2a00ff;color:#FFFFFF;">
					晚班：<span id="m2"><%=evening%></span>
					</td>
				</tr>
			</table>
		</td>
	</tr>
  </table>
  </form>
  </div>  
</div>

</body>
</html>
 