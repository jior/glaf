<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>任务列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/yui/build/datatable/assets/skins/sam/datatable.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/yui/build/paginator/assets/skins/sam/paginator.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/yui/build/button/assets/skins/sam/button.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/yui/build/menu/assets/skins/sam/menu.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css"/>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/yui/yui-2.8.1-min.js"></script>
</head>
<body  class="yui-skin-sam" leftmargin="0" topmargin="0" marginheight="0" marginwidth="0" style="padding-top:10px;padding-left:20px;"> 
 
<div class="x_content_title">
<img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="任务列表">&nbsp;任务列表
</div>
<br>

  <table id="screen" width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td>
			<table  border="0" cellspacing="0" cellpadding="0" >
				<tr> 
				 <td width="95%" height="25" align="left" > 
					<div id="paginationContainerTop"  width="100%"></div>
					<div id="menu_line" class="menu_line2" width="100%"></div>
					<div id="DataTableYUI"  width="100%">
					
					</div>
					<div id="paginationContainerBottom"  width="100%"></div>
				 </td>
			  </tr>
			  <tr> 
				 <td width="95%" height="25" align="left" > 
				 <div>
 				  <span id="view-button-2"> </span>
  				 </div>
				</td>
			  </tr>
			</table>			    
		</td>
  </tr>
</table>

 <script type="text/javascript"> 

  YAHOO.example.DynamicData = ( function() {
    
    var x_app = '&deploymentId=${deploymentId}&x_complex_query=${x_complex_query}'

    var s_link = "<%=request.getContextPath()%>/mx/activiti/task/taskListJson?q=1"+x_app;
 

    var viewbutton2 = new YAHOO.widget.Button({ label:"查看流程", id:"view-button-2", container:"view-button-2" });
    viewbutton2.on("click", onViewProcessButtonClick);


    function onViewButtonClick(e) {
    	var selectRows = myDataTable.getSelectedRows();
    	 if(selectRows.length!=null && selectRows.length==1){
          oRecord = myDataTable.getRecordSet().getRecord(selectRows[0]);
          var processInstanceId = YAHOO.lang.dump(oRecord.getData("processInstanceId"));
          window.open("<%=request.getContextPath()%>/mx/activiti/task?q=1&processInstanceId="+processInstanceId);
        } else{
          alert('<%=MessageProperties.getString("res_choose_one_only")%>');
    	}
    }

	function onViewProcessButtonClick(e) {
    	var selectRows = myDataTable.getSelectedRows();
    	 if(selectRows.length!=null && selectRows.length==1){
          oRecord = myDataTable.getRecordSet().getRecord(selectRows[0]);
          var processInstanceId = YAHOO.lang.dump(oRecord.getData("processInstanceId"));
		  if(processInstanceId != null && processInstanceId != "null"){
              window.open("<%=request.getContextPath()%>/mx/activiti/task?processInstanceId="+processInstanceId);
		  } else {
              alert('<%=MessageProperties.getString("res_no_process_instance")%>');
		  }
        } else{
          alert('<%=MessageProperties.getString("res_choose_one_only")%>');
    	}
    }

    
	 function onMyClickRow(oArgs) {
	    myDataTable.onEventSelectRow(oArgs);
		var elTarget = oArgs.target;
        var elTargetRow = myDataTable.getTrEl(elTarget);
        if(elTargetRow) {
              var oRecord = myDataTable.getRecord(elTargetRow);
              var status = YAHOO.lang.dump(oRecord.getData("status"));			 
              if(status <= 10 ){                  
				     editbutton.removeAttribute("disabled");
					 editbutton.removeStateCSSClasses("disabled");
               } else {
                     editbutton.setAttribute("disabled", "disabled");
					 editbutton.addStateCSSClasses("disabled");
			   }
	    }
	}

	function onMyDblClickRow(oArgs) {
	    myDataTable.onEventSelectRow(oArgs);
		var elTarget = oArgs.target;
        var elTargetRow = myDataTable.getTrEl(elTarget);
        if(elTargetRow) {
              var oRecord = myDataTable.getRecord(elTargetRow);
              var processInstanceId = YAHOO.lang.dump(oRecord.getData("processInstanceId"));
			  window.open("<%=request.getContextPath()%>/mx/activiti/task?q=1&processInstanceId="+processInstanceId);
	    }
	}

	 var formatFunKey = function(elCell, oRecord, oColumn, oData) {
        var processInstanceId = YAHOO.lang.dump(oRecord.getData("processInstanceId"));
		var processDefinitionId = YAHOO.lang.dump(oRecord.getData("processDefinitionId"));
        var link ='<%=request.getContextPath()%>/mx/activiti/task?processInstanceId='+processInstanceId;
		var link2 ='<%=request.getContextPath()%>/mx/activiti/image?processInstanceId='+processInstanceId;
		var msg = '<a href=\" '+link+'  " target=_blank><img src="<%=request.getContextPath()%>/images/view.gif"/>&nbsp;<%=MessageProperties.getString("res_view")%></a>';
		msg += '&nbsp;&nbsp;<a href=\" '+link2+'  " target=_blank><img src="<%=request.getContextPath()%>/images/process.gif"/>&nbsp;流程图</a>';
        elCell.innerHTML = msg ;
      }

	  var formatRow = function(elCell, oRecord, oColumn, oData) {
        var processInstanceId = YAHOO.lang.dump(oRecord.getData("processInstanceId"));
        var link = "<%=request.getContextPath()%>/mx/activiti/task?processInstanceId="+processInstanceId+x_app;
        elCell.innerHTML = "<a href=\""+link+"\" target='_blank'>"+oData+"</a>";
      }

	  var formatProgress = function(elCell, oRecord, oColumn, oData) {
        var progress = YAHOO.lang.dump(oRecord.getData("progress"));
    	var msg = progress+"%";
        elCell.innerHTML = msg;
      }


 	 var imgFormatter = function(elCell, oRecord, oColumn, oData) {
           var imginfo = oData; 
           elCell.innerHTML = "<img width=12 height=12 src=" + imginfo + "></img>";
        };

 	var myRowFormatter = function(elTr, oRecord) {
		if (oRecord.getData('status') == -1) {
			Dom.addClass(elTr, 'mark');
		} else if (oRecord.getData('status') == 50) {
			Dom.addClass(elTr, 'greenmark');
		}
		return true;
	}; 

      YAHOO.widget.DataTable.Formatter.imginfoFormatter = imgFormatter;
	  YAHOO.widget.DataTable.Formatter.formatFunKey = formatFunKey;
	  YAHOO.widget.DataTable.Formatter.formatProgress = formatProgress;
	  YAHOO.widget.DataTable.Formatter.formatRow = formatRow;

	   myDataSource = new YAHOO.util.DataSource(s_link);
       myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
       myDataSource.connXhrMode = "queueRequests";
       myDataSource.responseSchema = { 
	       resultsList : "records",  
	       fields : [ 
						{key: 'id'},
						{key: 'executionId'},
						{key: 'processInstanceId'},
						{key: 'taskDefinitionKey'},
						{key: 'name'},
						{key: 'description'},
						{key: 'priority'},
			            {key: 'createDate'},
			            {key: 'createDateTime'},
			            {key: 'actorId'},
			            {key: 'actorName'}
                     ],
           metaFields: {
                totalRecords: "totalRecords"
          }
	   }; 

	  var myColumnDefs = [ 
		{label: '任务编号', key: 'id', width: 90, resizeable: true, formatter: formatRow},
	    {label: '流程实例编号', key: 'processInstanceId', resizeable: true, width: 90},
		{label: '参与者', key: 'actorName', resizeable: true, width: 120},
	    {label: '任务定义编号', key: 'taskDefinitionKey', resizeable: true, width: 90},
	    {label: '任务名称', key: 'name', resizeable: true, width: 120},
		{label: '任务描述', key: 'description', resizeable: true, width: 180},
		{label: '创建日期', key: 'createDate', resizeable: true, width: 90},
        {label: '<%=MessageProperties.getString("res_functionKey")%>', key: 'functionKey', resizeable: true, width: 135, align: 'center', formatter: formatFunKey}
       ];

    var t0="{FirstPageLink}&nbsp;&nbsp;{PreviousPageLink}&nbsp;&nbsp;{NextPageLink}&nbsp;&nbsp;{LastPageLink}&nbsp;&nbsp; {RowsPerPageDropdown}&nbsp;&nbsp;{CurrentPageReport}";

    var t2 = '&nbsp;&nbsp;<%=MessageProperties.getString("res_x_di")%>&nbsp;{currentPage}&nbsp;<%=MessageProperties.getString("res_x_page")%>&nbsp;&nbsp;<%=MessageProperties.getString("res_x_total_page")%>:&nbsp;{totalPages}&nbsp;&nbsp;<%=MessageProperties.getString("res_x_total_records")%>:&nbsp;{totalRecords}&nbsp;&nbsp;';

	var myConfigs = { 
     initialRequest:"sort=id&dir=asc&startIndex=0&results=10",
     dynamicData: true, 
     MSG_LOADING: '<center><%=MessageProperties.getString("res_msg_loadingr")%></center>',
     MSG_ERROR: '<%=MessageProperties.getString("res_msg_error")%>',
     MSG_EMPTY: '<%=MessageProperties.getString("res_msg_empty")%>',
     MSG_SORTASC: '<%=MessageProperties.getString("res_msg_sortasc")%>',
     MSG_SORTDESC: '<%=MessageProperties.getString("res_msg_sortdesc")%>',
     paginator: new YAHOO.widget.Paginator({
            rowsPerPage: 10,
			rowsPerPageOptions: [10,15,20,25,50,100,200,500],
        	containers : 'paginationContainerBottom',
        	PageLinks : YAHOO.widget.Paginator.VALUE_UNLIMITED,
            firstPageLinkLabel : '<%=MessageProperties.getString("res_firstPageLinkLabel")%>', 
            lastPageLinkLabel : '<%=MessageProperties.getString("res_lastPageLinkLabel")%>', 
            previousPageLinkLabel:'<%=MessageProperties.getString("res_previousPageLinkLabel")%>',
            nextPageLinkLabel:'<%=MessageProperties.getString("res_nextPageLinkLabel")%>',
        	template: t0,
         	pageReportTemplate: t2
        })
	}; 

      
    this.myDataTable = new YAHOO.widget.DataTable("DataTableYUI", myColumnDefs, myDataSource, myConfigs);
	this.myDataTable.subscribe("rowMouseoverEvent", this.myDataTable.onEventHighlightRow);
    this.myDataTable.subscribe("rowMouseoutEvent", this.myDataTable.onEventUnhighlightRow);
    this.myDataTable.subscribe("rowClickEvent", onMyClickRow);
	this.myDataTable.subscribe("rowDblclickEvent", onMyDblClickRow);

 	myDataTable.selectRow(myDataTable.getTrEl(0));
 	myDataTable.focus();

    this.myDataTable.handleDataReturnPayload = function(oRequest, oResponse, oPayload) {
        oPayload.totalRecords = oResponse.meta.totalRecords;
        return oPayload;
    }    

	return {
    	ds: myDataSource,
    	dt: myDataTable
    };

   })();
 </script> 
 <br><br>
</body>
</html>