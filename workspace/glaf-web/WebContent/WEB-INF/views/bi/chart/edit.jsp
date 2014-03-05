<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图表定义</title>
<%@ include file="/WEB-INF/views/tm/mx_header.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript">

			function initData(){
				<c:if test="${!empty chart}">
			     // $('#iForm').form('load','<%=request.getContextPath()%>/rs/bi/chart/view/${chart.id}');
				</c:if>
			}

			function checkForm(){
              if(jQuery("#subject").val() == ''){
				   alert('主题是必须的！');
				   document.getElementById("subject").focus();
				   return false;
			  }
			  if(jQuery("#chartName").val() == ''){
				   alert('图表名称是必须的！');
				   document.getElementById("chartName").focus();
				   return false;
			  }
			  if(jQuery("#chartTitle").val() == ''){
				   alert('图表主题是必须的！');
				   document.getElementById("chartTitle").focus();
				   return false;
			  }
			  if(jQuery("#chartType").val() == ''){
				   alert('图表类型是必须的！');
				   document.getElementById("chartType").focus();
				   return false;
			  }
			  if(jQuery("#chartFont").val() == ''){
				   alert('图表字体是必须的！');
				   document.getElementById("chartFont").focus();
				   return false;
			  }
			  if(jQuery("#chartFontSize").val() == ''){
				   alert('图表字体大小是必须的！');
				   document.getElementById("chartFontSize").focus();
				   return false;
			  }
			  if(jQuery("#chartTitleFont").val() == ''){
				   alert('图表标题栏字体是必须的！');
				   document.getElementById("chartTitleFont").focus();
				   return false;
			  }
			  if(jQuery("#chartTitleFontSize").val() == ''){
				   alert('图表标题栏字体大小是必须的！');
				   document.getElementById("chartTitleFontSize").focus();
				   return false;
			  }
			  if(jQuery("#chartWidth").val() == ''){
				   alert('图表宽度是必须的！');
				   document.getElementById("chartWidth").focus();
				   return false;
			  }
			  if(jQuery("#chartHeight").val() == ''){
				   alert('图表高度是必须的！');
				   document.getElementById("chartHeight").focus();
				   return false;
			  }

              var queryIds = jQuery("#queryIds").val();
			  var querySQL = jQuery("#querySQL").val();

			  if( queryIds == '' && querySQL=='' ){
				   alert('查询数据集或查询条件必须选填一项！');
				   document.getElementById("queryNames").focus();
				   return false;
			  }

			  return true;
			}

			function saveData(){
				if(checkForm()){
			       var params = jQuery("#iForm").formSerialize();
				   jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/bi/chart/saveChart',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data.message != null){
						   alert(data.message);
					   } else {
						 alert('操作成功完成！');
					   }
				   }
			     });
				}
			}

			function saveAsData(){
			   if(checkForm()){
				   document.getElementById("id").value="";
				   document.getElementById("chartId").value="";
			       var params = jQuery("#iForm").formSerialize();
				   jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/bi/chart/saveChart',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data.message != null){
						   alert(data.message);
					   } else {
						 alert('操作成功完成！');
					   }
				   }
			     });
			   }
			}

			function testSql(){
				var querySQL = jQuery("#querySQL").val();
                if(  querySQL=='' ){
				   alert('查询语句不能为空！');
				   document.getElementById("querySQL").focus();
				   return;
			    }
                var params = jQuery("#iForm").formSerialize();
				   jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/bi/chart/checkSQL',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data.message != null){
						   alert(data.message);
					   } else {
						  var text = JSON.stringify(data); 
                          alert(text);
					   }
				   }
			 });
			}

			function viewChart(){
				window.open('<%=request.getContextPath()%>/mx/bi/chart/chart?chartId=${chart.id}');
			}

			function openQx(){
                var selected = jQuery("#queryIds").val();
                var link = '<%=request.getContextPath()%>/mx/dts/query/queryTree?elementId=queryIds&elementName=queryNames&nodeCode=report_category&selected='+selected;
				var x=100;
				var y=100;
				if(is_ie) {
					x=document.body.scrollLeft+event.clientX-event.offsetX-200;
					y=document.body.scrollTop+event.clientY-event.offsetY-200;
				 }
				openWindow(link,self,x, y, 495, 480);
			}

			function openQx2(){
                var link = '<%=request.getContextPath()%>/mx/bi/chart/chooseQuery?chartId=${chart.id}&elementId=queryIds&elementName=queryNames';
				var x=100;
				var y=100;
				if(is_ie) {
					x=document.body.scrollLeft+event.clientX-event.offsetX-200;
					y=document.body.scrollTop+event.clientY-event.offsetY-200;
				 }
				openWindow(link,self,x, y, 695, 480);
			}

	 </script>
</head>
<body>
 <br/>
	<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="图表定义"> &nbsp;图表定义
	</div>
 <br/>
	 <form id="iForm" name="iForm" method="post">
	    <input type="hidden" id="id" name="id" value="${chart.id}"/>
		<input type="hidden" id="nodeId" name="nodeId" value="${nodeId}"/>
	    <input type="hidden" id="chartId" name="chartId" value="${chart.id}"/>
	    <table class="easyui-form" style="width:800px;height:250px" align="center">
		<tbody>
			
			<tr>
				 <td height="28">标题</td>
				 <td colspan="3" height="28">
                 <input id="subject" name="subject" class="span8 x-text" type="text"
				        value="${chart.subject}" size="30"
				 ></input>
				 </td>
			 </tr>
			 <tr>
				 <td height="28">图表名称</td>
				 <td height="28">
                 <input id="chartName" name="chartName" class="span3 x-text" type="text"
				        value="${chart.chartName}" size="30"
				 ></input>
				 <td height="28">别名</td>
				 <td height="28">
				 <input id="mapping" name="mapping" class="span3 x-text" type="text"
				        value="${chart.mapping}" size="30"
				 ></input>
				 </td>
			</tr>
			<tr>
				 <td height="28">X坐标标签</td>
				 <td height="28">
                 <input id="coordinateX" name="coordinateX" class="span3 x-text" type="text"
				        value="${chart.coordinateX}" size="30"
				 ></input>
				 </td>
	 
				 <td height="28">Y坐标标签</td>
				 <td height="28">
                 <input id="coordinateY" name="coordinateY" class="span3 x-text" type="text"
				        value="${chart.coordinateY}" size="30"
				 ></input>
				 </td>
			</tr>
			<tr>
				 <td height="28">图表主题</td>
				 <td height="28">
                 <input id="chartTitle" name="chartTitle" class="span3 x-text" type="text"
				        value="${chart.chartTitle}" size="30"
				 ></input>
				 </td>
			 
				 <td height="28">图表类型</td>
				 <td height="28">
				 <select  id="chartType"  name="chartType" value="${chart.chartType}" class="span2" style="height:20px">
					<option value="pie">饼图</option>
					<option value="line">线型图</option>
					<option value="stackedbar">堆叠条形图</option>
				 </select>
				 <script type="text/javascript">
				    $('#chartType').val('${chart.chartType}');
				 </script>
				 </td>
			</tr>
			<tr>
				 <td height="28">图表标题栏字体</td>
				 <td height="28">
                 <input id="chartTitleFont" name="chartTitleFont" class="span3 x-text" type="text"
				        value="${chart.chartTitleFont}"  size="30"
				 ></input>
				 </td>
			 
				 <td height="28">图表标题栏字体大小</td>
				 <td height="28">
				 <input id="chartTitleFontSize" name="chartTitleFontSize" class="easyui-numberspinner x-text" 
                        value="${chart.chartTitleFontSize}" 
				            increment="1" style="width:120px;"  type="text"></input>
				 </td>
			</tr>
			<tr>
				 <td height="28">图表字体</td>
				 <td height="28">
                 <input id="chartFont" name="chartFont" class="span3 x-text" type="text"
				        value="${chart.chartFont}"  size="30"
				 ></input>
				 </td>
			 
				 <td height="28">图表字体大小</td>
				 <td height="28">
				 <input id="chartFontSize" name="chartFontSize" class="easyui-numberspinner x-text" 
                        value="${chart.chartFontSize}" 
				            increment="1" style="width:120px;"  type="text"></input>
				 </td>
			</tr>
			<tr>
				 <td height="28">图表宽度</td>
				 <td height="28">
				 <input id="chartWidth" name="chartWidth" class="easyui-numberspinner x-text" 
                        value="${chart.chartWidth}" 
				            increment="100" style="width:120px;"  type="text"></input>
				 </td>
			 
				 <td height="28">图表高度</td>
				 <td height="28">
				 <input id="chartHeight" name="chartHeight" class="easyui-numberspinner x-text" 
                        value="${chart.chartHeight}" 
				            increment="50" style="width:120px;"  type="text"></input>
				 </td>
			</tr>
			
			<tr>
				 <td height="28">图像类型</td>
				 <td height="28">
				 <select id="imageType" name="imageType" value="${chart.imageType}" class="span2" style="height:20px">
					<option value="png">PNG</option>
					<option value="jpeg">JPEG</option>
				 </select>
				 <script type="text/javascript">
				    $('#imageType').val('${chart.imageType}');
				 </script>
				 </td>
			 
				 <td height="28">显示图例</td>
				 <td height="28">
				 <select id="legend" name="legend" class="span2" style="height:20px">
					<option value="Y">是</option>
					<option value="N">否</option>
				 </select>
				 <script type="text/javascript">
				    $('#legend').val('${chart.legend}');
				 </script>
				 </td>
			</tr>
			<tr>
				 <td height="98">组合查询</td>
                 <td colspan="3" height="98">
				   <input type="hidden" id="queryIds" name="queryIds" value="${chart.queryIds}">
				   <textarea id="queryNames" name="queryNames" rows="12" cols="68" class="x-textarea"
				   style="width:580px;height:80px;" onclick="javascript:openQx();"  
				   readonly="true" >${queryNames}</textarea>&nbsp;
			       <a href="#" onclick="javascript:openQx();">
				     <img src="<%=request.getContextPath()%>/images/search_results.gif" border="0"
						title="如果图表需要多个查询数据集组成一个图表，请先建好查询数据再选择。">
				   </a>
				 </td>
			</tr>
			<tr>
				 <td  height="98">查询语句</td>
				 <td colspan="3"  height="98">
				 <textarea id="querySQL" name="querySQL" rows="12" cols="68"  class="x-textarea" style="width:580px;height:250px;">${chart.querySQL}</textarea>
				 <br><br>
				 提示：如果查询条件使用变量，请用 ( 某表字段 = <%out.println("${变量}");%> ) ，如果变量不存在则替换为 ( 1=1 )
                 <br><%out.println("${curr_yyyymmdd}");%>代表当天的日期，如<%=com.glaf.core.config.SystemConfig.getCurrentYYYYMMDD()%>
                 <br><%out.println("${curr_yyyymm}");%>代表当天的月份，如<%=com.glaf.core.config.SystemConfig.getCurrentYYYYMM()%>
				 <br><%out.println("${input_yyyymmdd}");%>代表报表输入的日期，如<%=com.glaf.core.config.SystemConfig.getInputYYYYMMDD()%>
                 <br><%out.println("${input_yyyymm}");%>代表报表输入的月份，如<%=com.glaf.core.config.SystemConfig.getInputYYYYMM()%>
                 <br>饼图要有别名为series（标签）及doublevalue（数值）字段
				 <br>线型图要有别名为series（纵坐标），category（横坐标）及doublevalue（数值）字段
				 <br>堆叠条形图要有别名为series（纵坐标），category（横坐标）及doublevalue（数值）字段
				 <br>注意：查询字段的别名要匹配并且小写！！
				 </td>
			</tr>
 
	  <tr>
		<td colspan="4" align="center">
		<br><br>
		<input type="button" name="save" value=" 测试 " class=" btn" onclick="javascript:testSql();" />
		&nbsp;&nbsp;
		<input type="button" name="save" value=" 图表 " class=" btn" onclick="javascript:viewChart();" />
		&nbsp;&nbsp;
		<input type="button" name="save" value=" 保存 " class=" btn btn-primary" onclick="javascript:saveData();" />
		&nbsp;&nbsp;
		<input type="button" name="saveAs" value=" 另存 " class=" btn" onclick="javascript:saveAsData();" />
		&nbsp;&nbsp;
		<input name="btn_back" type="button" value=" 返回 " class=" btn" onclick="javascript:history.back(0);">
		</td>
	 </tr>
	</tbody>
   </table>
  </form>
  <br><br>
  <script type="text/javascript">
         initData();
  </script>
</body>
</html>