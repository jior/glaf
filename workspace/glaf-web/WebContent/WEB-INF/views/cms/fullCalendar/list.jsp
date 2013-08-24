<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%
     String contextPath = request.getContextPath();
	 java.util.Date today = new java.util.Date();
%>
<!DOCTYPE html>
<html>
<head>
<link rel='stylesheet' href='<%=contextPath%>/scripts/jqueryui/cupertino/theme.css' />
<link href='<%=contextPath%>/scripts/fullcalendar/fullcalendar.css' rel='stylesheet' />
<link href='<%=contextPath%>/scripts/fullcalendar/fullcalendar.print.css' rel='stylesheet' />
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>

<script src='<%=contextPath%>/scripts/jqueryui/jquery-ui.custom.min.js'></script>
<script src='<%=contextPath%>/scripts/fullcalendar/fullcalendar.min.js'></script>

<script type="text/javascript">
	var calendar;
	$(document).ready(function() {
	
		var date = new Date();
		var d = date.getDate();
		var m = date.getMonth();
		var y = date.getFullYear();
		
		calendar = $('#calendar').fullCalendar({
			theme: true,
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},
			dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],    
			dayNamesShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"], 
			monthNames:['一月','二月', '三月', '四月', '五月', '六月', '七月','八月', '九月', '十月', '十一月', '十二月'],
			monthNamesShort:['一月','二月', '三月', '四月', '五月', '六月', '七月','八月', '九月', '十月', '十一月', '十二月'],
			selectable: true,
			selectHelper: true,
			select: function(start, end, allDay) {//点击日期新增日程
				addNew($.fullCalendar.formatDate(start,'yyyy-MM-dd HH:mm'),$.fullCalendar.formatDate(end,'yyyy-MM-dd HH:mm'),allDay);
			},
			eventClick:function( event, jsEvent, view ) {//已有的日程点击事件，编辑  
				editRecord(event.id);
			},
			editable: true,
			events:[],
			events:function(start, end, callback) {
				jQuery.ajax({  
					url:"<%=request.getContextPath()%>/mx/cms/fullCalendar/getJson?dateStartGreaterThanOrEqual="+$.fullCalendar.formatDate(start,'yyyy-MM-dd')+"&dateStartLessThanOrEqual="+$.fullCalendar.formatDate(end,'yyyy-MM-dd'),  
					cache:false,  
					success:function(data) { 
						var events = [];  
						var dataObjs=eval("("+data+")");//转换为json对象 
						$.each(dataObjs,function(ids,item) { 
							var id = item.id;
							var title = item.title;  
							var evtstart = item.dateStart_datetime;  
							var evtend = item.dateEnd_datetime; 
							events.push({  
								title:title,  
								start:evtstart,  
								end:evtend,  
								id:id,
								allDay:item.allDay,
								url:item.url
							});  
						}); 
						callback(events);  
					},  
					error:function() {  
						alert('有错误，请稍候再试！')  
					}  
				})  
			}  
			
		});
		
	});

	function formatDateStr(date)  {  
		var month = (date.getMonth()+1);
		var dateStr = date.getDate();
		if(month<10){
			month = '0'+month;
		}
		if(dateStr<10){
			dateStr = '0'+dateStr;
		}
		return date.getFullYear()+"-"+month+"-"+dateStr;  
	}

	function addNew(start,end,allDay){
	    var link="<%=request.getContextPath()%>/mx/cms/fullCalendar/edit?start="+start+"&end="+end+"&allDay="+allDay;
	    art.dialog.open(link, { height: 420, width: 680, title: "编辑日程", lock: true, scrollbars:"no" }, false);
	}

	function editRecord(id){
		var link="<%=request.getContextPath()%>/mx/cms/fullCalendar/edit?id="+id;
	    art.dialog.open(link, { height: 420, width: 680, title: "编辑日程", lock: true, scrollbars:"no" }, false);
	}

	function saveCallback(title,start,end,id,allDayStatus){
		var isAllDay = false;
		if(allDayStatus==1){
			isAllDay = true;
		}
		if (title) {
			removeCallback(id);
			calendar.fullCalendar('renderEvent',
				{
					title: title,
					start: start,
					end: end,
					id:id,
					allDay: isAllDay
				},
				false // make the event "stick"
			);
		}
		calendar.fullCalendar('unselect');
	}

	function removeCallback(id){
		if (id) {
			calendar.fullCalendar('removeEvents',[id]);
		}
	}

</script>
<style>

	body {
		margin-top: 20px;
		text-align: center;
		font-size: 14px;
		font-family: "宋体",Helvetica,Arial,Verdana,sans-serif;
	}

	#calendar {
		width: 980px;
		margin: 0 auto;
	}

</style>
</head>
<body>
<div id='calendar'></div>
<br><br>
</body>
</html>
