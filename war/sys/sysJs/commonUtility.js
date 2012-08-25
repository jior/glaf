/**
 * 将字符串转换成json串
 *  
 **/
function strToJson(str) {
	var json = eval('(' + str + ')');
	return json;
}

/**
 * 将json串转换为字符串
 * @param o
 * @return
 */
function JsonToStr(o) {       
	var arr = [];       
	var fmt = function(s) {       
		if (typeof s == 'object' && s != null) return JsonToStr(s);       
		//return /^(string|number)$/.test(typeof s) ? "'" + s + "'" : s;
		//数字不转换
		return /^(string)$/.test(typeof s) ? '"' + s + '"' : s;
		       
	}       
	for (var i in o) arr.push("'" + i + "':" + fmt(o[i]));       
	return '{' + arr.join(',') + '}';       
}   

Ext.Loader.setConfig({
    enabled: true
});

/**
 * 引入EXT需要使用的控件 
 **/
Ext.require([
             'Ext.selection.CellModel',
             'Ext.grid.*',
             'Ext.data.*',
             'Ext.util.*',
             'Ext.state.*',
             'Ext.form.*',
             'Ext.LoadMask.*'
         ]);

/**
 * 初始化grid，如果有数据，则刷新grid数据（数据存放在myData中） 
 * 入参  ：gridID--需要初始化的grid（不含后缀-table）
 * 返回值：初始化好的grid，便于后续操作使用（比如后续刷新grid数据）
 **/
function initGrid(gridID) {
	//列表右键初始化
	Ext.QuickTips.init();

	//设置保存cookie，例如用户拖动了列，则将新的列的信息记录到cookie中，
	//下次再打开画面时，仍显示拖动后的状态。当cookie清除后还原为默认值。
	//Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));

	//根据id获取grid
	var tab1 = document.getElementById(gridID + "-table");
	
	var colCfgNum = 0;
	var insertbar = "";
	var copytbar = "";
	
	if(tab1.rows.length == 2){
		colCfgNum = 1;
	}
	
	//获取table的高度和宽度,默认为宽800，高为280（10行高）
	var tabWidth = 800;
	var tabHeight = 270;
	if(typeof tab1.width != "undefined"){
		tabWidth = parseInt(tab1.width);
	}
	if(typeof tab1.height != "undefined"){
		tabHeight = parseInt(tab1.height);
	}
	
	//创建Checkbox列模式
	var sm = "[]";
	
	//遍历grid的各列属性及设置，拼接成json串，并设置给extjs grid
	var storeFields = "";
	var gridColumns = "{xtype: 'rownumberer',width: 25,sortable: false}";
	var gridFeilds = "";
	for(var i=0;i<tab1.rows[colCfgNum].cells.length;i++){
		//检查必要属性是否配置
		//检查type是否设置
		//检查dataIndex是否设置
		if((typeof tab1.rows[colCfgNum].cells[i].type != "undefined") &&
				(tab1.rows[colCfgNum].cells[i].type == "checkCol")){
			//如果type属性为checkcol时不检查dataIndex和title
			sm = Ext.create('Ext.selection.CheckboxModel');
			continue;
		}else{
			if(typeof tab1.rows[colCfgNum].cells[i].dataIndex == "undefined"){
				thorw(new   Error(-1,   " dataIndex not null! "))
			}
			//检查title是否设置
			if(typeof tab1.rows[colCfgNum].cells[i].title == "undefined"){
				thorw(new   Error(-1,   " title not null! "))
			}
		}
		colDataIndex = tab1.rows[colCfgNum].cells[i].dataIndex;
		//获取配置
		storeFields = storeFields + ",{name :'" + colDataIndex + "'}";
		gridColumns = gridColumns + ",{text :'" + tab1.rows[colCfgNum].cells[i].title + "'" +
		              ", sortable:true" +
                      ", dataIndex :'" + colDataIndex + "'";
		
		if(typeof tab1.rows[colCfgNum].cells[i].width != "undefined"){
			gridColumns = gridColumns + ",width:" + tab1.rows[colCfgNum].cells[i].width + "";
		}
		if(typeof tab1.rows[colCfgNum].cells[i].hidden != "undefined"){
			gridColumns = gridColumns + ",hidden:'" + tab1.rows[colCfgNum].cells[i].hidden + "'";
		}
		
		if(typeof tab1.rows[colCfgNum].cells[i].type != "undefined"){
			gridFeilds = "" ;
			switch(tab1.rows[colCfgNum].cells[i].type){
			case "checkbox": 
				//gridFeilds = gridFeilds + ",xtype: 'checkbox'";
				gridFeilds = gridFeilds + ",xtype: 'checkbox'";
				//gridColumns = gridColumns + ",xtype: 'checkcolumn'";
				break;
			case "link": 
				//找到href中需要使用行中某字段值的变量，并作处理
				var hrefString = tab1.rows[colCfgNum].cells[i].href;
				var vais = "";
				var con = 1;
				while(hrefString.indexOf('{tr.') != -1){
					for(var k=0;k<tab1.rows[colCfgNum].cells.length;k++){
						if(hrefString.indexOf('{tr.' + tab1.rows[colCfgNum].cells[k].dataIndex + "}")!=-1){
							hrefString = hrefString.replace('{tr.' + tab1.rows[colCfgNum].cells[k].dataIndex + "}","{" + con + "}");
							vais = vais + ",record.data." + tab1.rows[colCfgNum].cells[k].dataIndex;
							con = con + 1;
						}
					}
				}
				
				var clickStr = "";
				if(typeof tab1.rows[colCfgNum].cells[i].clickHander != "undefined"){
					clickStr = " onclick=\"" + tab1.rows[colCfgNum].cells[i].clickHander + "\" ";
				}
				gridColumns = gridColumns + ",id:'" + colDataIndex + 
							"',renderer: function renderlink(value, p, record){" +
							"  return Ext.String.format('<a href=\"" + 
							hrefString + "\" target=\"_blank\"" + clickStr + ">{0}</a>'," +
							"value" + vais + ");}" + 
							",flex: 1";
				break;
			case "combobox": 
				var combData = "[]";
				if(typeof tab1.rows[colCfgNum].cells[i].comboData != "undefined"){
					combData = tab1.rows[colCfgNum].cells[i].comboData;
				}
				//alert('combData:' + combData);
				gridFeilds = gridFeilds + ",xtype: 'combobox',selectOnTab: true,"
				             + "lazyRender: true,typeAhead: true,"
				             + "store: " + combData ;
				             //+ "store: combStore,displayField:value,valueField:label ";
				break;
			case "text": 
				gridFeilds = gridFeilds + ",allowBlank:" + tab1.rows[colCfgNum].cells[i].allowBlank;
				break;
			case "button": 
				gridColumns = gridColumns + ",xtype: 'actioncolumn'" +
						",items: [{icon   :'" + tab1.rows[colCfgNum].cells[i].icon + "'" + 
						"   ,handler:function(grid, rowIndex, colIndex) {" +
						"       var record = grid.store.getAt(rowIndex);" + tab1.rows[colCfgNum].cells[i].clickHander + ";}}]";
				break;
			case "textNum":
				gridFeilds = gridFeilds + ",xtype: 'numberfield'";
				if(typeof tab1.rows[colCfgNum].cells[i].minValue != "undefined"){
					gridFeilds = gridFeilds + ",minValue:" + tab1.rows[colCfgNum].cells[i].minValue;
				}
				if(typeof tab1.rows[colCfgNum].cells[i].maxValue != "undefined"){
					gridFeilds = gridFeilds + ",maxValue:" + tab1.rows[colCfgNum].cells[i].maxValue;
				}
				if(typeof tab1.rows[colCfgNum].cells[i].format != "undefined"){
					gridFeilds = gridFeilds + ",format:'" + tab1.rows[colCfgNum].cells[i].format + "'";
				}
				gridFeilds = gridFeilds + ",allowBlank:" + tab1.rows[colCfgNum].cells[i].allowBlank;
				break;
			case "textDate":
				gridFeilds = gridFeilds + ",xtype: 'datefield'";
				if(typeof tab1.rows[colCfgNum].cells[i].minValue != "undefined"){
					gridFeilds = gridFeilds + ",minValue:" + tab1.rows[colCfgNum].cells[i].minValue;
				}
				if(typeof tab1.rows[colCfgNum].cells[i].maxValue != "undefined"){
					gridFeilds = gridFeilds + ",maxValue:" + tab1.rows[colCfgNum].cells[i].maxValue;
				}
				if(typeof tab1.rows[colCfgNum].cells[i].format != "undefined"){
					gridFeilds = gridFeilds + ",format:'" + tab1.rows[colCfgNum].cells[i].format + "'";
				}
				gridFeilds = gridFeilds + ",allowBlank:" + tab1.rows[colCfgNum].cells[i].allowBlank;
				break;
			}
			if(gridFeilds.length > 1){
				gridColumns = gridColumns + ",field:{" + gridFeilds.substring(1, gridFeilds.length) + "}";
			}
			
		}else{
			if(typeof tab1.rows[colCfgNum].cells[i].format != "undefined"){
				switch(tab1.rows[colCfgNum].cells[i].format){
				case "0,0":
					//gridColumns = gridColumns + ",renderer:function formatNum(value){formatNum(value,-1);}";
					break;
				}
			}
			
		}
		gridColumns = gridColumns + "}";
	}
	//默认所有grid都有no列
	gridColumns = "[" + gridColumns + "]";
	if(storeFields.length>1){
		storeFields = storeFields.substring(1, storeFields.length);
		//insertFeilds = insertFeilds.substring(1, insertFeilds.length);
	}
	
	//根据列设置数据模式
	Ext.define('dataModel', {
        extend: 'Ext.data.Model',
        fields: strToJson("[" + storeFields + "]")
    });
	
	var toolbar = "[]";
	if(colCfgNum == 1){
		//读取toolbar
		for(var j=0;j<tab1.rows[colCfgNum-1].cells.length;j++){
			if(typeof tab1.rows[colCfgNum-1].cells[j].type == "undefined"){
				thorw(new   Error(-1,   " type not null! "))
			}
			if(typeof tab1.rows[colCfgNum-1].cells[j].dataIndex == "undefined"){
				thorw(new   Error(-1,   " dataIndex not null! "))
			}
			if(tab1.rows[colCfgNum-1].cells[j].dataIndex == "insert"){
				insertbar = insertbar + "{text: 'insert',id : 'insertBut'}"
			}
			if(tab1.rows[colCfgNum-1].cells[j].dataIndex == "copy"){
				copytbar = copytbar + "{text: 'copy',id : 'copyBut'}"
			}
		}
		if((insertbar != "") &&(copytbar == "")){
			toolbar = "[" + insertbar + "]";
		}
		if((insertbar == "") &&(copytbar != "")){
			toolbar = "[" + copytbar + "]";
		}
		if((insertbar != "") &&(copytbar != "")){
			toolbar = "[" + insertbar + "," + copytbar + "]";
		}
		

	}
	
	//创建grid点击后修改的模式
	var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
        clicksToEdit: 1
    });
	

	//创建grid数据结构对象
	var store = Ext.create('Ext.data.Store', {
		fields : strToJson("[" + storeFields + "]"),
		data :myData,
		storeId : 'myStore'
	});
	/**
	var combData = [['1','t1'],['2','t2']];
	var combStore = Ext.create('Ext.data.Store', {
		fields : strToJson("[{name: 'value'},{name: 'label'}]"),
		data :combData,
		storeId : 'combStore'
	});
	**/
	var waitMask = new Ext.LoadMask(Ext.getBody(), { msg: "系统正在处理数据，请稍候..." });  

	//创建grid
	var grid = Ext.create(
					'Ext.grid.Panel',
					{
						loadMask: waitMask,
						store :store,
						selModel: sm,
						stateId :'stateGrid',
						columns : strToJson(gridColumns),
						height :tabHeight,
						width :tabWidth,
						frame: true,
						renderTo :gridID,
						tbar: strToJson(toolbar),
					    plugins: cellEditing
					    
					});
	store.load();
	/**
	if(typeof tab1.rows[colCfgNum].clickHander != "undefined"){
		grid.addListener('cellclick',function(grid, rowIndex, columnIndex, e)
				  {alert("test");
				   var record = grid.getStore().getAt(rowIndex);
				   alert(record.get("company"));
				   rowClick(record);});
	}
	**/
	document.getElementById(gridID + "-table").height = "1px";
	//返回创建好的grid，以便后续刷新等操作时使用
	return grid;
}


/**
 * 异步提交画面
 * @param docForm 当前需要提交的form
 * @param grid    当前grid
 * @return
 */
function asynchSubmit(docForm,grid) {
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	//获取form中的所有元素，并设置为提交参数
	var submitEles = "";
	for(var i=0;i<docForm.length;i++){
       var element = docForm[i];
       if (element.type!="button"){
           if(element.name!=""){
               submitEles = submitEles + "&" + element.name + "=" + element.value;
           }
       }
    }
	
	//获取grid中的所有选中行数据
	var selectedRecords = grid.getSelectionModel().getSelection();
    var recordsJsonStr = "";
    Ext.each(selectedRecords,function(arecord){
        recordsJsonStr = recordsJsonStr + "," + JsonToStr(arecord.data);
    	
                  });
    //过滤掉{}和"
    recordsJsonStr = recordsJsonStr.substring(1,recordsJsonStr.length);
    recordsJsonStr = recordsJsonStr.replace(/{}/g,"''");
    recordsJsonStr = recordsJsonStr.replace(/\"/g,"'");
    recordsJsonStr = "recordsJsonStr=[" + recordsJsonStr + "]";
	
    //提交页面
	//xmlhttp.open("POST",
	//		docForm.action + "?x=1" + submitEles + "&" + recordsJsonStr, true);
	//xmlhttp.send();
	xmlhttp.open("POST",docForm.action , true);
    xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
    xmlhttp.send("x=1" + submitEles + "&" + recordsJsonStr);

	//回调函数中抛出消息，设置翻页属性，并刷新grid
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var cc = document.createElement("div");
			cc.innerHTML = xmlhttp.responseText;
			var doc = cc.document;
			
			//抛出消息 
			if(doc.getElementById("messages") != null){
				alert(doc.getElementById("messages").value);
			}
			//设置翻页信息
			if(doc.getElementById("recordCount") != null){
				var recordCount = doc.getElementById("recordCount").value;
				document.getElementById("recordCount").value = recordCount;
			}
			if(doc.getElementById("pageNo") != null){
				var pageNo = doc.getElementById("pageNo").value;
				document.getElementById("pageNo").value = pageNo;
			}
			if(doc.getElementById("pageNoMax") != null){
				var pageNoMax = doc.getElementById("pageNoMax").value;
				document.getElementById("pageNoMax").value = pageNoMax;
			}
			if(doc.getElementById("goPageID") != null){
				document.getElementById("goPageID").value = "";
				document.getElementById("pageNoBefore").value = "";
				document.getElementById("pageNoNext").value = "";
				document.getElementById("pageNoEnd").value = "";
			}
			//刷新grid
			if(doc.getElementById("gridData") != null){
				var gridData = doc.getElementById("gridData").value;
				if(gridData == ''){
					gridData = '[]';
				}
				grid.store.loadData(strToJson(gridData));
			}
			//回调函数
			try{
				if(doc.getElementById("extCBFlag") != null){
					var extCBFlag = doc.getElementById("extCBFlag").value;
					if(extCBFlag == "YES"){				
						if(typeof(eval("extCalllBack"))=="function"){
							extCalllBack();
						}
					}
				}
			}
			catch(e){
			}			
		}
	}
	
	
}


/**
 * 新增按钮的处理事件
 */
function insertData(grid,insertFeilds){
	//设置新增行的默认默认值
	var r = Ext.ModelManager.create(strToJson(insertFeilds) , 'dataModel');
	//新增行到第一行，已有行向后移
	grid.store.insert(0, r); 
}

/**
 * 复制按钮的处理事件
 */
function copyData(grid){
	//获取选中的行
	var records = grid.getSelectionModel().getSelection();
	//复制每个选中行到第一行中
	Ext.each(records,function(record){
		grid.store.insert(0, record);
	});
}

/**
 * 初始化翻页控件
 * @param objFrm 当前form
 * @param grid   需要翻页的grid
 */
function initPageController(objFrm,grid){
	//设置首页的点击事件
	var linkPageStart = Ext.get("pageStart");
	linkPageStart.on("click" , function(){
        //异步提交，并将检索到的数据刷新至table中
		objFrm.actionMethodId.value = "runPageCtrl";
	    objFrm.pageNo.value = "1";
	    objFrm.pageNoBefore.value = "";
	    objFrm.pageNoNext.value = "";
	    objFrm.pageNoEnd.value = "";
        asynchSubmit(objFrm,grid);
         });
    
	//设置前一页的点击事件
    var linkPageBefore = Ext.get("pageBefore");
	linkPageBefore.on("click" , function(){
        //异步提交，并将检索到的数据刷新至table中
		objFrm.actionMethodId.value = "runPageCtrl";
		objFrm.pageNoBefore.value = objFrm.pageNo.value - 1;
	    objFrm.pageNo.value = "";
	    objFrm.pageNoNext.value = "";
	    objFrm.pageNoEnd.value = "";
        asynchSubmit(objFrm,grid);
         });
    
	//设置下一页的点击事件
    var linkPageNext = Ext.get("pageNext");
	linkPageNext.on("click" , function(){
        //异步提交，并将检索到的数据刷新至table中
		objFrm.actionMethodId.value = "runPageCtrl";
		objFrm.pageNoNext.value = objFrm.pageNo.value - 0 + 1;
		objFrm.pageNo.value = "";
	    objFrm.pageNoBefore.value = "";
	    objFrm.pageNoEnd.value = "";
        asynchSubmit(objFrm,grid);
         });
    
	//设置末页的点击事件
    var linkPageEnd = Ext.get("pageEnd");
	linkPageEnd.on("click" , function(){
        //异步提交，并将检索到的数据刷新至table中
		objFrm.actionMethodId.value = "runPageCtrl";
		objFrm.pageNoEnd.value = objFrm.pageNoMax.value ;
		objFrm.pageNo.value = "";
	    objFrm.pageNoBefore.value = "";
	    objFrm.pageNoNext.value = "";
        asynchSubmit(objFrm,grid);
         }); 
    
	//设置转向页的点击事件
    var linkSearchPageGo = Ext.get("searchPageGo");
	linkSearchPageGo.on("click" , function(){
        //异步提交，并将检索到的数据刷新至table中
		var vGoPageID = document.getElementById("goPageID").value;
	    objFrm.actionMethodId.value = "runPageCtrl";
	    objFrm.pageNo.value = vGoPageID;
	    objFrm.pageNoBefore.value = "";
	    objFrm.pageNoNext.value = "";
	    objFrm.pageNoEnd.value = "";
	    
        asynchSubmit(objFrm,grid);
        
         });
         
}

function checkmsg(){
	alert(msg01);
}
