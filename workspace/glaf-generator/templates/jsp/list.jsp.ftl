<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.config.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>FormApps</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/yui/yui-2.8.1.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/styles.css"/>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/yui/yui-2.8.1-min.js"></script>
</head>
<body  class="yui-skin-sam"> 
<div class="x_content_title">
<img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="${title?if_exists}">&nbsp;${title?if_exists}
</div>
<br>
 <table id="screen" width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td>
		    <table border="0" cellspacing="0" cellpadding="0" class="x-tabs-box">
              <tr>
                <td class="x-tab-l<c:if test="#F{taskType == 'all'}">c</c:if>">&nbsp;</td>
                <td width="65" class="x-tab-m<c:if test="#F{taskType == 'all'}">c</c:if>">
				    <a href="<%=request.getContextPath()%>/apps/${modelName?if_exists}.do?method=list&gridType=yuigrid&app_name=<c:out value="#F{app_name}"/>&taskType=all"><%=SystemConfig.getString("res_all_data")%></a>
				</td>
				<td class="x-tab-r<c:if test="#F{taskType == 'all'}">c</c:if>">&nbsp;</td>

				<td class="x-tab-l<c:if test="#F{taskType == 'draft'}">c</c:if>">&nbsp;</td>
                <td width="65" class="x-tab-m<c:if test="#F{taskType == 'draft'}">c</c:if>">
				    <a href="<%=request.getContextPath()%>/apps/${modelName?if_exists}.do?method=list&gridType=yuigrid&app_name=<c:out value="#F{app_name}"/>&taskType=draft"><%=SystemConfig.getString("res_app_draft")%></a>
				</td>
				<td class="x-tab-r<c:if test="#F{taskType == 'draft'}">c</c:if>">&nbsp;</td>

				<td class="x-tab-l<c:if test="#F{taskType == 'auditing'}">c</c:if>">&nbsp;</td>
                <td width="65" class="x-tab-m<c:if test="#F{taskType == 'auditing'}">c</c:if>">
				    <a href="<%=request.getContextPath()%>/apps/${modelName?if_exists}.do?method=list&gridType=yuigrid&app_name=<c:out value="#F{app_name}"/>&taskType=auditing"><%=SystemConfig.getString("res_app_tasklist")%></a>
				</td>
				<td class="x-tab-r<c:if test="#F{taskType == 'auditing'}">c</c:if>">&nbsp;</td>

				<td class="x-tab-l<c:if test="#F{taskType == 'worked'}">c</c:if>">&nbsp;</td>
                <td width="65" class="x-tab-m<c:if test="#F{taskType == 'worked'}">c</c:if>">
				    <a href="<%=request.getContextPath()%>/apps/${modelName?if_exists}.do?method=list&gridType=yuigrid&app_name=<c:out value="#F{app_name}"/>&taskType=worked"><%=SystemConfig.getString("res_app_worked")%></a>
				</td>
				<td class="x-tab-r<c:if test="#F{taskType == 'worked'}">c</c:if>">&nbsp;</td>

				<td class="x-tab-l<c:if test="#F{taskType == 'finished'}">c</c:if>">&nbsp;</td>
                <td width="65" class="x-tab-m<c:if test="#F{taskType == 'finished'}">c</c:if>">
				    <a href="<%=request.getContextPath()%>/apps/${modelName?if_exists}.do?method=list&gridType=yuigrid&app_name=<c:out value="#F{app_name}"/>&taskType=finished"><%=SystemConfig.getString("res_app_finished")%></a>
				</td>
				<td class="x-tab-r<c:if test="#F{taskType == 'finished'}">c</c:if>">&nbsp;</td>

              </tr>
            </table>
			<table  border="0" cellspacing="0" cellpadding="0" >
				<tr> 
				 <td width="95%" height="25" align="left" > 
					<div id="paginationContainerTop"  width="95%"></div>
					<div id="menu_line" class="menu_line" width="95%"></div>
					<div id="DataTableYUI"  width="95%"></div>
					<div id="paginationContainerBottom"  width="95%"></div>
				 </td>
			  </tr>
			  <tr> 
				 <td width="95%" height="25" align="left" > 
				 <div>
				  <c:if test="#F{taskType == 'draft' or taskType == 'all' }">
				  <span id="create-button-2"> </span>
				  </c:if>
				  <c:if test="#F{taskType == 'draft' or taskType == 'fallback' }">
				  <span id="edit-button-2"> </span>
				  </c:if>
				  <c:if test="#F{taskType == 'draft' }">
				  <span id="delete-button-2"> </span>
				  </c:if>
				  <span id="audit-button-2" > </span>
				  <span id="search-button-2"> </span>
				  <span id="print-button-2"> </span>
				 </div>
				</td>
			  </tr>
			</table>			    
		</td>
  </tr>
</table>

 <script type="text/javascript"> 

  YAHOO.example.DynamicData = ( function() {
    
     var x_app = "&app_name=<c:out value="#F{app_name}"/>&x_complex_query=<c:out value="#F{x_complex_query}"/>";


    var s_link = "<%=request.getContextPath()%>/apps/${modelName?if_exists}.do?method=listData&gridType=yuigrid&taskType=<c:out value="#F{taskType}"/>"+
                "&x_display_names=${x_display_names?if_exists}"+x_app;

	var createbutton2 = new YAHOO.widget.Button({ label:"${res_create?if_exists}", id:"create-button-2x", container:"create-button-2" });
    createbutton2.on("click", onCreateButtonClick);

	var editbutton2 = new YAHOO.widget.Button({ label:"${res_update?if_exists}", id:"edit-button-2x", container:"edit-button-2" });
    editbutton2.on("click", onEditButtonClick);

	var deletebutton2 = new YAHOO.widget.Button({ label:"${res_delete?if_exists}", id:"delete-button-2x", container:"delete-button-2" });
    deletebutton2.on("click", onDeleteButtonClick);

    var auditbutton2 = new YAHOO.widget.Button({ label:"${res_audit?if_exists}", id:"audit-button-2x", container:"audit-button-2" });
    auditbutton2.on("click", onAuditButtonClick);

    var searchbutton2 = new YAHOO.widget.Button({ label:"${res_query?if_exists}", id:"search-button-2x", container:"search-button-2" });
    searchbutton2.on("click", onSearchButtonClick);

    var printbutton2 = new YAHOO.widget.Button({ label:"${res_print?if_exists}", id:"print-button-2x", container:"print-button-2" });
    printbutton2.on("click", onPrintButtonClick);
    
	 function onCreateButtonClick(e) {
    	 location.href="<%=request.getContextPath()%>/apps/${modelName?if_exists}.do?method=edit&gridType=yuigrid&x_method=saveOrUpdate"+x_app;
     }
	   
	 function onEditButtonClick(e) {
    	var selectRows = myDataTable.getSelectedRows();
    	 if(selectRows.length!=null && selectRows.length==1){
          oRecord = myDataTable.getRecordSet().getRecord(selectRows[0]);
          var status = YAHOO.lang.dump(oRecord.getData("status"));    	
          if(status == 0 || status == -1){
              var ${idField.name} = YAHOO.lang.dump(oRecord.getData("${idField.name}"));
              location.href="<%=request.getContextPath()%>/apps/${modelName?if_exists}.do?method=edit&gridType=yuigrid&x_method=saveOrUpdate&${idField.name}="+${idField.name}+x_app;
          } else {
               alert("${res_update_audit_deny?if_exists}");
          }
        } else{
          alert("${res_choose_one_only?if_exists}");
       }
    }

    function onViewButtonClick(e) {
    	var selectRows = myDataTable.getSelectedRows();
    	 if(selectRows.length!=null && selectRows.length==1){
          oRecord = myDataTable.getRecordSet().getRecord(selectRows[0]);
          var ${idField.name} = YAHOO.lang.dump(oRecord.getData("${idField.name}"));
          window.open("<%=request.getContextPath()%>/apps/${modelName?if_exists}.do?method=view&gridType=yuigrid&x_method=view&gridType=yuigrid&${idField.name}="+${idField.name}+x_app);
        } else{
          alert("${res_choose_one_only?if_exists}");
    	}
    }

	function onViewProcessButtonClick(e) {
    	var selectRows = myDataTable.getSelectedRows();
    	 if(selectRows.length!=null && selectRows.length==1){
          oRecord = myDataTable.getRecordSet().getRecord(selectRows[0]);
          var processInstanceId = YAHOO.lang.dump(oRecord.getData("processInstanceId"));
		  if(processInstanceId != null && processInstanceId != "null"){
              window.open("<%=request.getContextPath()%>/pages/jbpm/jsp/task/task.jsp?processInstanceId="+processInstanceId);
		  } else {
              alert("${res_no_process_instance?if_exists}");
		  }
        } else{
          alert("${res_choose_one_only?if_exists}");
    	}
    }

    function onAuditButtonClick(e) {
    	var selectRows = myDataTable.getSelectedRows();
    	 if(selectRows.length!=null && selectRows.length==1){
          oRecord = myDataTable.getRecordSet().getRecord(selectRows[0]);
          var ${idField.name} = YAHOO.lang.dump(oRecord.getData("${idField.name}"));
          location.href="<%=request.getContextPath()%>/apps/${modelName?if_exists}.do?method=edit&gridType=yuigrid&x_method=submit&taskType=<c:out value="#F{taskType}"/>&${idField.name}="+${idField.name}+x_app;
        } else{
          alert("${res_choose_one_only?if_exists}");
    	}
    }

    function onDeleteButtonClick(e) {
    	var selectRows = myDataTable.getSelectedRows();
    	 if(selectRows.length!=null && selectRows.length > 0 ){
          oRecord = myDataTable.getRecordSet().getRecord(selectRows[0]);
          var status = YAHOO.lang.dump(oRecord.getData("status"));    	
          if(status == 0 ){
              var ${idField.name} = YAHOO.lang.dump(oRecord.getData("${idField.name}"));
           } else {
               alert("${res_delete_audit_deny?if_exists}");
           }
        } else{
          alert("${res_choose_one?if_exists}");
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

	function onSearchButtonClick(e) {
        location.href="<%=request.getContextPath()%>/apps/${modelName?if_exists}.do?method=query&gridType=yuigrid"+x_app;
	}

	function onPrintButtonClick(e) {
	    window.open("<%=request.getContextPath()%>/apps/${modelName?if_exists}.do?method=printList&taskType=<c:out value="#F{taskType}"/>"+x_app);
	 }

	 
    myDataSource = new YAHOO.util.DataSource(s_link);
    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
    myDataSource.connXhrMode = "queueRequests";
    myDataSource.responseSchema = { 
	    resultsList : "records",  
	    fields : [
        ${x_json_fields?if_exists}
        ],
        metaFields: {
            totalRecords: "totalRecords"
        }
	 }; 
	 

	 var formatFunKey = function(elCell, oRecord, oColumn, oData) {
        var ${idField.name} = YAHOO.lang.dump(oRecord.getData("${idField.name}"));
		var processInstanceId = YAHOO.lang.dump(oRecord.getData("processInstanceId"));
        var link = "<%=request.getContextPath()%>/apps/${modelName?if_exists}.do?method=view&gridType=yuigrid&x_method=view&gridType=yuigrid&${idField.name}="+${idField.name}+x_app;
		var msg = "<a href=\""+link+"\" target='_blank'>${res_view?if_exists}</a>";
		if(processInstanceId != null && processInstanceId != "null"){
			 <c:if test="#F{ taskType == 'running' || taskType =='fallback' }">
			 msg = msg + "&nbsp;<a href='<%=request.getContextPath()%>/FormService.do?method=view&gridType=yuigrid&x_method=submit&taskType=<c:out value="#F{taskType}"/>&${idField.name}="+${idField.name}+x_app+"'>${res_audit?if_exists}</a>";
			 </c:if>
             msg = msg + "&nbsp;<a href='<%=request.getContextPath()%>/pages/jbpm/jsp/task/task.jsp?processInstanceId="+processInstanceId+"' target='_blank'>${res_process_status?if_exists}</a>";
		}
        elCell.innerHTML = msg ;
      }

	  var formatRow = function(elCell, oRecord, oColumn, oData) {
        var ${idField.name} = YAHOO.lang.dump(oRecord.getData("${idField.name}"));
        var link = "<%=request.getContextPath()%>/apps/${modelName?if_exists}.do?method=view&gridType=yuigrid&x_method=view&gridType=yuigrid&${idField.name}="+${idField.name}+x_app;
        elCell.innerHTML = "<a href=\""+link+"\" target='_blank'>"+oData+"</a>";
      }

	  var formatStatus = function(elCell, oRecord, oColumn, oData) {
        var status = YAHOO.lang.dump(oRecord.getData("status"));
    	var msg = "";
        if (status == -1) {
            msg = "<span style='color: red; font-weight: bold; text-align: center;'> ${res_status_5555?if_exists} </span>";
    	} else if (status == 40) {
            msg = "<span style='color: blue; font-weight: bold; text-align: center;'> ${res_status_40?if_exists} </span>";
    	} else if (status == 50) {
            msg = "<span style='color: green; font-weight: bold; text-align: center;'> ${res_status_50?if_exists} </span>";
    	} else {
            msg = "<span style='font-weight: bold; text-align: center;'> ${res_status_0?if_exists} </span>";
    	}
        elCell.innerHTML = msg;
      }

 	 var imgFormatter = function(elCell, oRecord, oColumn, oData) {
           var imginfo = oData; 
           elCell.innerHTML = "<img width=12 height=12 src=" + imginfo + "></img>";
        };

      YAHOO.widget.DataTable.Formatter.imginfoFormatter = imgFormatter;
	  YAHOO.widget.DataTable.Formatter.formatFunKey = formatFunKey;
	  YAHOO.widget.DataTable.Formatter.formatStatus = formatStatus;
	  YAHOO.widget.DataTable.Formatter.formatRow = formatRow;
	 
	  var myColumnDefs = [ 
	      {label: '${res_row_no?if_exists}', key: 'x_row_no', width: 60, formatter: formatRow}
		  <#if x_json_display_nodes?exists>
		   ${x_json_display_nodes}
		  </#if>
		  ,{label: '${res_status?if_exists}', key: 'x_status', width: 90, resizeable: true, align: 'center' , formatter: formatStatus}
          ,{label: '${res_functionKey?if_exists}', key: 'functionKey', width: 150, align: 'center', formatter: formatFunKey}
       ];

    var t0="{FirstPageResultLink}&nbsp;&nbsp;{PreviousPageResultLink}&nbsp;&nbsp;{NextPageResultLink}&nbsp;&nbsp;{LastPageResultLink}&nbsp;&nbsp; {RowsPerPageResultDropdown}&nbsp;&nbsp;";
    t0+="{CurrentPageResultReport}";

    var t2 = "&nbsp;&nbsp;${res_x_di?if_exists}&nbsp;{currentPageResult}&nbsp;${res_x_page?if_exists}&nbsp;&nbsp;${res_x_total_page?if_exists}:&nbsp;{totalPageResults}&nbsp;&nbsp;${res_x_total_records?if_exists}:&nbsp;{totalRecords}&nbsp;&nbsp;";

	var myConfigs = { 
     initialRequest:"sort=id&dir=asc&startIndex=0&results=15",
     dynamicData: true, 
     MSG_LOADING: "<center>${res_msg_loading?if_exists}</center>",
     MSG_ERROR: "${res_msg_error?if_exists}",
     MSG_EMPTY: "${res_msg_empty?if_exists}",
     MSG_SORTASC: "${res_msg_sortasc?if_exists}",
     MSG_SORTDESC: "${res_msg_sortdesc?if_exists}",
     paginator: new YAHOO.widget.Paginator({
            rowsPerPageResult: 15,
			rowsPerPageResultOptions: [10,15,20,25,50],
        	containers : 'paginationContainerBottom',
        	PageResultLinks : YAHOO.widget.Paginator.VALUE_UNLIMITED,
        	firstPageResultLinkLabel : "${res_firstPageResultLinkLabel?if_exists}", 
            lastPageResultLinkLabel : "${res_lastPageResultLinkLabel?if_exists}", 
            previousPageResultLinkLabel:"${res_previousPageResultLinkLabel?if_exists}",
            nextPageResultLinkLabel:"${res_nextPageResultLinkLabel?if_exists}",
        	template: t0,
         	pageReportTemplate: t2
        })
	}; 
     
	// DataTable({formatRow: myRowFormatter})
	var myRowFormatter = function(elTr, oRecord) {
		if (oRecord.getData('status') == -1) {
			Dom.addClass(elTr, 'mark');
		} else if (oRecord.getData('status') == 50) {
			Dom.addClass(elTr, 'greenmark');
		}
		return true;
	}; 

    this.myDataTable = new YAHOO.widget.DataTable("DataTableYUI", myColumnDefs, myDataSource, myConfigs);
	this.myDataTable.subscribe("rowMouseoverEvent", this.myDataTable.onEventHighlightRow);
    this.myDataTable.subscribe("rowMouseoutEvent", this.myDataTable.onEventUnhighlightRow);
    this.myDataTable.subscribe("rowClickEvent", onMyClickRow);

	// Programmatically select the first row
	myDataTable.selectRow(myDataTable.getTrEl(0));
	// Programmatically bring focus to the instance so arrow selection works immediately
	myDataTable.focus();

    var onContextMenuClick = function(p_sType, p_aArgs, myDataTable) {
        var task = p_aArgs[1];
        if(task) {
        var elRow = this.contextEventTarget;
        elRow = myDataTable.getTrEl(elRow);
        if(elRow) {
		    var oRecord = myDataTable.getRecord(elRow);
            var status = YAHOO.lang.dump(oRecord.getData("status"));
            switch(task.index) {
            case 0:     
                onEditButtonClick();
            	break;
            case 1:      
                onDeleteButtonClick();
            	break;
            case 2:      
                onAuditButtonClick();
            	break;
            case 3:      
                onViewButtonClick();
            	break;
			case 4:      
                onViewProcessButtonClick();
            	break;
            }
          }
        }
    };

    var myContextMenu = new YAHOO.widget.ContextMenu("mycontextmenu", {trigger:myDataTable.getTbodyEl()});
     myContextMenu.addItem("${res_update?if_exists}");
     myContextMenu.addItem("${res_delete?if_exists}");
     myContextMenu.addItem("${res_audit?if_exists}");
     myContextMenu.addItem("${res_view?if_exists}");
     myContextMenu.addItem("${res_process_info?if_exists}");
     myContextMenu.render("DataTableYUI");
     myContextMenu.clickEvent.subscribe(onContextMenuClick, myDataTable);

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
 </body>
</html>