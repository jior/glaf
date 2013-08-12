<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_config.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择指标</title>
<meta name="Keywords" content="<%=appKeywords%>" />
<meta name="Description" content="<%=appDescription%>" />
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>

<script type="text/javascript">
    var contextPath="${contextPath}";

	function validateForm(){
		var v = $("#name").val();
		if($.trim(v).length==0){
			alert("请输入指标名称！");
			return false;
		}
		
		v = $("#standard").val();
		if($.trim(v).length==0){
			alert("请输入评分标准！");
			return false;
		} 
		return true;
		 
	}
	function closeDialog(){
		window.parent.location.href=window.parent.location.href;
		
	}
	
	//父结点ID，子结点ID , 标准ID
	function selectAssessSort(pid1,sid){
		//alert(pid1+","+pid2+","+sid);
		var params = {};
		params.pid1=pid1;
		params.sid=sid;
		params.questionId=$("#questionId").val();
		//alert(params["pid1"]+","+params["pid2"]+","+params["sid"]);
		//alert(params.pid1 +","+params.pid2+","+params.sid);
		jQuery.ajax({
			   type: "POST",
			   url: '${contextPath}/mx/oa/assessquestion/insertTypeInMakeAssessIndex',
			   data: params,
			   dataType:  'json',
			   error: function(data){
				   alert('服务器处理错误！');
			   },
			   success: function(data){
				   if(data != null && data.message != null){
					 alert(data.message);
				   } else {
					 //alert('操作成功完成！');
				   }
				   var result=data.result;
				   var qid = data.qid; //assessquestionid
				   var sortid = data.sid; //assesssortid;
					 //alert('操作成功完成！');
				  if(result=="SUCCESS" && qid!=""){
					   url="${contextPath}/mx/oa/assessquestion/queryAssessIndex?questionId="+qid;
					   if (window.opener) {
							window.opener.location.href=url;
					   } else if (window.parent) {
						   var win = art.dialog.open.origin;
							win.ruash(url);
							art.dialog.close();
					   }
				  }else{
					  alert("插入数据失败！");
				  }
			   }
		 });
		
	}
</script>

<style type="text/css">
.textWidth {
	width: 450px;
}
</style>
</head>

<body>
	<div style="margin: 0;"></div>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false,cache:true">
			<form id="iForm" name="iForm" method="post">
				<input type="hidden" name="questionId" id="questionId"
					value="${questionId}">
				<ul id="tt" class="easyui-tree">
					<li><span>指标类型</span>
						<ul>
							<c:forEach var="assessSortType" items="${assessSortTypeList }"
								varStatus="typeIndex">
								<li><span>${assessSortType.name }</span>
									<ul class="easyui-tree">
										<c:forEach var="assessSortType1"
											items="${assessSortType.subAssessList }"
											varStatus="sortIndex">
											<li><span><a
													href="javascript:selectAssessSort(${assessSortType.id },${assessSortType1.id })">${assessSortType1.name}</a></span>
											</li>
										</c:forEach>
									</ul></li>
							</c:forEach>
						</ul></li>
				</ul>
			</form>
		</div>
	</div>
</body>
</html>
<%@ include file="/WEB-INF/views/inc/init_end.jsp"%>