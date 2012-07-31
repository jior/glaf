/**
* 打开弹出窗口
* url 页面地址
* width 数字，窗口宽度
* height 数字窗口高度
* scroll true/false, 是否有滚动条
*/

function openWindow(url, width, height, scroll){  
  var  y=((window.screen.availHeight-height)/2);  
  var  x=((window.screen.availWidth-width)/2);
  scroll = scroll == true ? 'yes' : scroll; 
  var rst = window.open(url, "", "height=" + height + ", width=" + width + ", toolbar =no, menubar=no, scrollbars=" + scroll + ", resizable=yes, location=no, status=no, top=" + y + ", left=" + x + "");
	rst.focus();
}
function openMaxWindow(url){  
	var width,height;
	if (document.layers) {  
		width=screen.availWidth-10;  
		height=screen.availHeight-20; 
	} else {  
		width=screen.availWidth-2;  
		height=screen.availHeight;  
	}
  var win = window.open(url, "", "height=" + height + ", width=" + width + ", toolbar =no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no, top=0, left=0");
	win.focus();
	if (document.all) {
		win.resizeTo(screen.availWidth, screen.availHeight);
	  win.moveTo(0, 0);
	}
}
/**
* 关闭弹出窗口，并刷新上层页面
*/
function closeOpenWindow(){
  opener.location.reload();
  self.close();
}

/**
* 全选/反选checkbox
* form form表单
* obj 对象
*/

function checkAll(form, obj) {
	
  for (var i=0;i<form.elements.length;i++) {
    var e = form.elements[i];
    if (e.type=='checkbox' && e.disabled==false)
      e.checked = obj.checked;
  }
}
function setCheckAllFlag()
{
	checkAllFlag=1;
}
function getCheckedBoxNum(form, name) {
  var num =0;
  for (var i=0;i<form.elements.length;i++) {
    var e = form.elements[i];
    if (e.type=='checkbox' && e.checked && e.name==name){
	  num=num+1;
	}     
  }
  return num;
}
/**
*  确认批量删除
*  form 表单 
*/
function confirmDelete(form){
  var isChecked = false;
  for(var i = 0; i < form.elements.length; i++){
    var e = form.elements[i];
    if(e.name != "chkall" && e.type == "checkbox" && e.checked == true){
      isChecked = true;
      break;
    }
  }
  if(isChecked){
    if(confirm("您确定要删除所选的记录吗？")){
      return true;
    }else{
      return false;
    }
  }else{
    alert("您还没有选择要删除的记录.");
    return false;
  }
}
function confirmAudit(form){
  var isChecked = false;
  for(var i = 0; i < form.elements.length; i++){
    var e = form.elements[i];
    if(e.name != "chkall" && e.type == "checkbox" && e.checked == true){
      isChecked = true;
      break;
    }
  }
  if(isChecked){
    if(confirm("您确定要审核所选的记录吗？")){
      return true;
    }else{
      return false;
    }
  }else{
    alert("您还没有选择要审核的记录.");
    return false;
  }
}
function getXMLHttpObject(){
  var C=null;
  try{
    C=new ActiveXObject("Msxml2.XMLHTTP");
  }catch(e){
    try{
      C=new ActiveXObject("Microsoft.XMLHTTP");
    }catch(sc){
      C=null;
    }
  }
  if(!C && typeof XMLHttpRequest!="undefined"){
    C=new XMLHttpRequest();
  }
  return C;
}
function getChildTree(catId){
  var xmlhttp = getXMLHttpObject();
  if(xmlhttp){
    xmlhttp.onreadystatechange=function(){//激活事件
      if(xmlhttp.readyState==4 && xmlhttp.status==200){//有返回
	    try{
	      eval("node_" + catId + ".innerHTML=xmlhttp.responseText"); 		
		}catch(e){
		}  
      }
    };
	xmlhttp.open("GET", "/glaf/sys/authorize.do?method=showSubMenu&parent=" + catId, true);    
	xmlhttp.send(null);
  }  
}
function createChildNode(catId){
  var temp;
  try{
    eval("temp=node_" + catId + ".innerHTML");
  }catch(e){}
  
  if(temp==""){
     eval("node_" + catId +".style.display='block'");
     eval("node_" + catId + ".innerHTML='节点加载中...'");
	 
	 getChildTree(catId);
  }
}
function showHide(){
  var temp;
  eval("temp=menuRoot.style.display");
  if(temp=="block"){
    eval("menuRoot.style.display='none'");
  }else{
    eval("menuRoot.style.display='block'");
  }
}
/**
*列表页面，当多选时，编辑按钮将不可用
*form form表单
*name checkBox名字
*btn 编辑按钮
*/
function disableEditBtn(form,name,btn){
	var num = getCheckedBoxNum(form,name);
	 
	if( num > 1 ){
		btn.disabled = true;	
	}
}

/**
*按钮(只有在CheckBox只有选择1个时使能）
*form form表单
*name checkBox名字
*btn 编辑按钮
*/
function editBtnOperation(form,name,btn){
	var num = getCheckedBoxNum(form,name);
	if( num != 1 ){
		btn.disabled = true;	
	}else{
		btn.disabled = false;	
	}
}

/**
*按钮(只有在CheckBox只有选择多个时使能）
*form form表单
*name checkBox名字
*btn 编辑按钮
*/
function thisBtnOperation(form,name,btn){
	var num = getCheckedBoxNum(form,name);
	if( num > 1 ){
		btn.disabled = false;	
	}else{
		btn.disabled = true;	
	}
}

/**
*删除/提交按钮(只有在CheckBox只有选择>=1个时使能）
*form form表单
*name checkBox名字
*btn 编辑按钮
*/
function dsBtnOperation(form,name,btn){
	var num = getCheckedBoxNum(form,name);
	 
	if( num >= 1 ){
		btn.disabled = false;	
	}else{
		btn.disabled = true;	
	}
}

/**
*列表页面，当无选时，X buttom将不可用
*form form表单
*name checkBox名字
*btn 编辑按钮
*/
function disableBtn(form,name,btn){
	var num = getCheckedBoxNum(form,name);
 
	if( num < 1 ){
		btn.disabled = true;	
	}
}

function confirmCheckBox(form,name,message){
  var isChecked = false;
  for(var i = 0; i < form.elements.length; i++){
    var e = form.elements[i];
    if(e.name == name && e.type == "checkbox" && e.checked == true){
      isChecked = true;
      break;
    }
  }
  if(!isChecked){
    return false;
  } 
  return true;
}

//刷新当前页面
function reloadPage(){
	window.location.reload();
}

/*
================================================================================
	getRadioValue(obj[, attribute]...):取Radio的值
================================================================================
*/
function getRadioValue(obj)
{
	var obj = document.getElementsByName(obj);
	for (var i = 0; i < obj.length; i++)
	{
		var e = obj.item(i);
		if (e.checked)
		{
			if (arguments.length >= 2)
			{
				var values = new Array();
				var isReturnString = false;
				var m = 0;
				for (var j = 1; j < arguments.length; j++)
				{
					if (arguments[j] == true || arguments[j] == false)
					{
						isReturnString = arguments[j];
					}
					else
					{
					  values[m] = e.getAttribute(arguments[j]);
						m++;
					}
				}
				return isReturnString ? values.toString() : values;
			}
			else{
			  return e.value;
			}
			break;
		}
	}
	return "";
}

/*
================================================================================
	getCheckboxValue(obj[, attribute]...):取复选框的值
================================================================================
*/
function getCheckboxValue(obj)
{
	var obj = document.getElementsByName(obj);
	var values = new Array();
	var index = 0;
	var isReturnString = true;
	for (var i = 0; i < obj.length; i++)
	{
		var e = obj.item(i);
		if (e.checked)
		{
			if (arguments.length >= 2)
			{
				isReturnString = false;
				var m = 0;
				values[index] = new Array();
				for (var j = 1; j < arguments.length; j++)
				{
					if (arguments[j] == true || arguments[j] == false)
					{
						isReturnString = arguments[j];
					}
					else
					{
						values[index][m] = e.getAttribute(arguments[j]);
						m++;
					}
				}
			}
			else
			{
				values[index] = e.value;
			}
			index++;
		}
	}
	
	if (isReturnString)
	{
		return values.toString();
	}
	else
	{
	  return values;
	}
}


/*
================================================================================
	CheckAll(this, obj):复选框全选/反选.
================================================================================
*/
function CheckAlls(obj, target)
{
	if (obj.checked) {
		CheckAll(target);
	} else {
		CheckNone(target);
	}
}

/*
================================================================================
	CheckAll(obj):复选框全选.
================================================================================
*/
function CheckAll(obj)
{   checkAllFlag=1;
	var obj = document.getElementsByName(obj);
	for(var i = 0; i < obj.length; i++)
	{
		obj.item(i).checked = true;
	}
}

/*
================================================================================
	CheckNone(obj):复选框全不选.
================================================================================
*/
function CheckNone(obj)
{
	var obj = document.getElementsByName(obj);
	for(var i = 0; i < obj.length; i++)
	{
		obj.item(i).checked = false;
	}
}

function getCheckedboxNums(obj)
{
	var obj = document.getElementsByName(obj);
	var num = 0;
	for (var i = 0; i < obj.length; i++)
	{
		var e = obj.item(i);
		if (e.checked)
		{
			num++;
		}
	}
	return num;
}

function getObjNum(obj)
{
	var obj = document.getElementsByName(obj);
	var num = 0;
	if (obj) {
		num = obj.length;
	}
	return num;
}


function $(element) {
  if (arguments.length > 1) {
    for (var i = 0, elements = [], length = arguments.length; i < length; i++)
      elements.push($(arguments[i]));
    return elements;
  }
  if (typeof element == 'string')
    element = document.getElementById(element);
  return element;
}

function getValues(name)
{
  var inputObj = document.getElementsByName(name);
	var values = '';
	for (var i = 0; i < inputObj.length; i++)
	{
	  if (inputObj.item(i).value != '')
		{
		  values += inputObj.item(i).value + ',';
		}
	}
	if (values.substring(values.length - 1) == ",")
	{
	  values = values.substring(0, values.length - 1);
	}
	return values.replace(/\r\n/ig, "%0A");
}

/***** 显示无模式对话框 *****/
function ShowDialog(url, width, height, dialogscroll, obj) {
    var arr = showModalDialog(url, window, "dialogWidth:" + width + "px;dialogHeight:" + height + "px;center:yes;help:no;resizable:no;status:no;scroll:" + dialogscroll + "");
		var isAdd = arguments[4];
		var submitFormName = "submitForm";
		var submitForm = null;
		var submitFormTemp = null;
		for (var i = 5, m = 0 ; i < arguments.length; i++, m++) {
			var o = arguments[i];
			if (!o) {
				continue;
			}
			if (typeof(o) != 'object') {
				  if (o.indexOf(submitFormName) == 0) {
						submitFormTemp = o;
					}
					o = document.getElementsByName(o).item(0);
			}
			var arrValue = '';
			if (arr != null) {
				if (submitFormTemp) {
					submitForm = submitFormTemp;
					continue;
				}
				if (typeof(arr) == 'object') {
						if (typeof(arr[0]) == 'object') {
						  arrValue = getArrayValue(arr, m);
						} else {
							if (m < arr.length) {
								arrValue = arr[m];
							}
						}
				} else {
					arrValue = arr;
				}
				if (isAdd) {
					addValue(o, arrValue);
				} else {
					o.value = arrValue;
				}
			} else {
				o.value = '';
			}
		}
		
		if (submitForm) {
			eval(submitForm).submit();
		} else {
			return arr;
		}
}
function getArrayValue(arr, index) 
{
	var arrLen = arr.length;
	var returnValue = new Array();
	for (var n = 0; n < arrLen; n++) {
		returnValue[n] = arr[n][index];
	}
	return returnValue;
}
function addValue(obj, addValue) {
	var value = obj.value;
	if (value.length == 0) {
		obj.value += addValue;
	} else {
		obj.value += ',' + addValue;
	}
}

var context = "/glaf";
//选择基础数据
function selectData(dataCode, referId, referTitle, referCode,parent){
  var url = context + "/sys/dictory.do?method=showDictData&code="+dataCode+"&parent="+parent;
  var features="dialogHeight:310px; dialogWidth:360px; center: yes; resizable: no; status: no; help:no";
  var ret = window.showModalDialog(url, window, features);
  if(ret!=null){
    if(referId!=null) referId.value=ret[0];
	if(referTitle!=null) referTitle.value=ret[1];
	if(referCode!=null) referCode.value=ret[2];
  }
}
//选择基础数据
function selectCategory(dataCode, referId, referTitle, referCode,parent){
  var url = context + "/sys/dictory.do?method=showDictData&code="+dataCode+"&parent="+parent;
  var features="dialogHeight:310px; dialogWidth:360px; center: yes; resizable: no; status: no; help:no";
  var ret = window.showModalDialog(url, window, features);
  if(ret!=null){
    if(referId!=null) referId.value=ret[0];
	if(referTitle!=null) referTitle.value=ret[1];
	if(referCode!=null) referCode.value=ret[2];
  }
}
/**推荐 及 申请供应商时 有条件地 显示 行业种类
* treeCode : 树节点; noCode : 不要包含的sys_dictory 表中的 code 值
* zoumin
*/
function selectRecommendCategory(treeCode, noCode, referId, referTitle, referCode,parent){
  var url = context + "/spadmin/supplier.do?method=showRecommendData&code="+treeCode+"&noCode="+noCode;
  var features="dialogHeight:310px; dialogWidth:360px; center: yes; resizable: no; status: no; help:no";
  var ret = window.showModalDialog(url, window, features);
  if(ret!=null){
    if(referId!=null) referId.value=ret[0];
	if(referTitle!=null) referTitle.value=ret[1];
	if(referCode!=null) referCode.value=ret[2];
  }
}
//选择供应商
function selectSupplier(type, referId, referTitle, referCode, category){
	var url = context + '/spadmin/supplier.do?method=showSelectList';
	if (type) {
		url += '&query_type_ex=' + type;
	}
	if (category) {
		url += '&query_category_el=' + category;
	}
	return ShowDialog(url, 520, 540, false, false, referId, referTitle, referCode);
}
function selectMultSupplier(type, referId, referTitle, referCode, category){
	var url = context + '/spadmin/supplier.do?method=showSelectList&mult=true';
	if (type) {
		url += '&query_type_ex=' + type;
	}
	if (category) {
		url += '&query_category_el=' + category;
	}
	return ShowDialog(url, 520, 540, false, true, referId, referTitle, referCode);
}
//选择物品类别
function selectGoodsCategory(referId, referTitle, submitForm) {
	var url = context + "/goods/category_select.do";
	var rst = ShowDialog(url, 310, 360, false, false);
	if(rst!=null){
		var s;
		eval("s=" + rst[1]);
		var o = referId;
		if (typeof(o) != 'object') {
			o = document.getElementsByName(referId).item(0);
		}
		if (o) {
		  o.value = s.id;
		}
		o = referTitle;
		if (typeof(o) != 'object') {
			o = document.getElementsByName(referTitle).item(0);
		}
		if (o) {
		  o.value = s.name;
		}
		if (submitForm) {
			eval(submitForm).submit();
		}
	}
}
//选择供应商对应的物品类别[无论类别的status=1 或 status=-1] referId--对应物品类别Id，referTitle--对应物品类别name，referParentId--对应物品类别的父Id, status 标明是否显示全部物品类别[flag=1:可用的、GoodsCategory表中status=1的物品类别;  flag=-1:不论状态,全部显示]
function selectSupplierGoodsCategory(referId, referTitle, flag, referParentId, chargeId, submitForm) {
	var url = context + "/spadmin/category_select.do?status=" + flag;
	arguments
	var rst = ShowDialog(url, 310, 360, false, false);
	if(rst!=null){
		var s;
		eval("s=" + rst[1]);
		var o = referId;
		if (typeof(o) != 'object') {
			o = document.getElementsByName(referId).item(0);
		}
		if (o) {
		  o.value = s.id;
		}
		o = referTitle;
		if (typeof(o) != 'object') {
			o = document.getElementsByName(referTitle).item(0);
		}
		if (o) {
		  o.value = s.name;
		}
		o = referParentId;
		if(typeof(o) != 'object'){
			o = document.getElementsByName(referParentId).item(0);
		}
		if(o){
			o.value = s.parentId;
		}
		o = chargeId;
		if(typeof(o) != 'object'){
			o = document.getElementsByName(chargeId).item(0);
		}
		if(o){
			o.value = s.chargeId;
		}
		if (submitForm) {
			eval(submitForm).submit();
		}
	}
}


function jump(action, openType){
	if (openType && openType == 2) {
    var newWin = window.open(action, '', "height=" + (screen.height - 88) + ", width=" + (screen.width - 30) + ", toolbar =no, menubar=no, scrollbars=" + scroll + ", resizable=yes, location=no, status=no, top=5, left=5");
		newWin.focus();
	} else {
	  if(action!='')window.location.href = action;
	}
}
/**根据搜索标签构造搜索条件**/
function getSearchElement(form){
  var url="";
  for (var i=0;i<form.elements.length;i++){
    var obj=form.elements[i];
	if (obj.searchflag+"" == "undefined") continue;
	if(obj.searchflag=="1" && obj.value.length>0){//是搜索标志
	  url+= obj.name + "=" + escape(obj.value) +"&";
	}
  }
  return url;
  //alert(url);
}
function getQueryStringRegExp(name) {
  var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i");  
  if (reg.test(location.href)){
    var s = unescape(RegExp.$2.replace(/\+/g, " "));
    return s; 
  }
  return ""; 
};
/*根据搜索条件设置页面查询框*/
function setSearchElement(form){
  var url = window.location;
  for (var i=0;i<form.elements.length;i++){
    var obj=form.elements[i];
	if (obj.searchflag+"" == "undefined") continue;
	if(obj.searchflag=="1"){//是搜索标志
	  obj.value=getQueryStringRegExp(obj.name);
	}
  }
}
function UrlDecode(str){ 
  var ret=""; 
  for(var i=0;i<str.length;i++){ 
   var chr = str.charAt(i); 
    if(chr == "+"){ 
      ret+=" "; 
    }else if(chr=="%"){ 
     var asc = str.substring(i+1,i+3); 
     if(parseInt("0x"+asc)>0x7f){ 
      ret+=asc2str(parseInt("0x"+asc+str.substring(i+4,i+6))); 
      i+=5; 
     }else{ 
      ret+=asc2str(parseInt("0x"+asc)); 
      i+=2; 
     } 
    }else{ 
      ret+= chr; 
    } 
  } 
  return ret; 
} 

function addToMyMenu()
{
  openWindow(context + '/workspace/mymenu.do?method=prepareAddMyMenu&url=' + encodeURIComponent(window.location.pathname + window.location.search), 600, 450);
}

function openUpload(obj, type)
{  
	return ShowDialog(context + '/inc/upload/fileUpload.jsp' + (type ? '?type=' + type : ''), 450, 230, 'no', false, obj);
}

//第一个日期是否大于或等于第二个日期
function checkDate(DateOne, DateTwo, opgt){
  DateOne = DateOne.toString();
  DateTwo = DateTwo.toString();
  var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ("-"));
  var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ("-")+1);
  var OneYear = DateOne.substring(0,DateOne.indexOf ("-"));

  var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ("-"));
  var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ("-")+1);
  var TwoYear = DateTwo.substring(0,DateTwo.indexOf ("-"));

  var date1 = Date.parse(OneMonth+"/"+OneDay+"/"+OneYear);
  var date2 = Date.parse(TwoMonth+"/"+TwoDay+"/"+TwoYear);
  if (opgt ? (date1 > date2) : (date1 >= date2)){
    return true;
  }else{
    return false;
  }
}

function getToday() {
	var date = new Date();
	return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
}

function setDisabled(name, value) {
	var obj = document.getElementsByName(name);
	for (var i = 0; i < obj.length; i++) {
		 var o = obj.item(i);
		 if (o.value == value) {
				o.disabled = true;
		 } else {
				o.disabled = false;
		 }
	}
}
function setEnabled(name, value) {
	var obj = document.getElementsByName(name);
	for (var i = 0; i < obj.length; i++) {
		 var o = obj.item(i);
		 if (o.value == value) {
				o.disabled = false;
		 } else {
				o.disabled = true;
		 }
	}
}

//新版数据选择对话框

//选择供应商
//selectType：单选-1、多选-2
//supplierType：无平台-0、有平台-1、全部-2
//返回值：{id、名称、编号}
function selectSupplierList(selectType, supplierType){
  var url = context + '/spadmin/supplier.do?method=showSelectList';
  if(selectType==1) url += '&mult=true';
	
  if (supplierType!=2) {
    url += '&query_webUser_ex=' + supplierType;
  }
  return ShowDialog(url, 520, 540, false, true);
}
//物品选择
//返回值：{物品id，物品名称，物品编号，物品规格，物品不含税价格，供应商id, 物品单位}
function selectGoods(category) {
	return selectGoodsList(category);
}
//选择分类下的物品
function selectGoodsList(category){
  var url = context + "/goods/select_frame.do" + (category == 'undefined' ? "" : "?category=" + category);
  var features="dialogHeight:500px; dialogWidth:950px; center: yes; resizable: no; status: no; help:no";
  return window.showModalDialog(url, window, features);  
}
//预算信息选择
//返回值：{预算id，预算No，预算名称，预算金额}
function selectBudgetsList(){
  var url = context + "/budget/budget.do?method=showSelectorList";
  var features="dialogHeight:550px; dialogWidth:900px; center: yes; resizable: no; status: no; help:no";
  return window.showModalDialog(url, window, features);  
}
function selectNoBudgetsList(){     
  var url = context + "/budget/budget.do?method=showSelectorList&hasBudget=0";
  var features="dialogHeight:550px; dialogWidth:900px; center: yes; resizable: no; status: no; help:no";
  return window.showModalDialog(url, window, features); 
}
function selectBudgetsList2(b){
  var url = context + "/budget/budget.do?method=showAllSelectorList&deptId="+b;
  var features="dialogHeight:550px; dialogWidth:900px; center: yes; resizable: no; status: no; help:no";
  return window.showModalDialog(url, window, features);  
}
function selectFeeBudgetsList(){
  var url = context + "/budget/budget.do?method=showSelectorList&budgetType=1";
  var features="dialogHeight:550px; dialogWidth:900px; center: yes; resizable: no; status: no; help:no";
  return window.showModalDialog(url, window, features);  
}
function selectNoFeeBudgetsList(){      
  var url = context + "/budget/budget.do?method=showSelectorList&budgetType=1&hasBudget=0";
  var features="dialogHeight:550px; dialogWidth:900px; center: yes; resizable: no; status: no; help:no";
  return window.showModalDialog(url, window, features); 
}
function selectBudgetsListByDeptment(c,d,e){   
  var url = context + "/budget/budget.do?method=showSelectorList2&budgetType="+c+"&hasBudget="+d+"&deptId="+e;
  var features="dialogHeight:550px; dialogWidth:900px; center: yes; resizable: no; status: no; help:no";
  return window.showModalDialog(url, window, features); 
}
//重财信息选择
//返回值：{重财id、重财编号、重财金额、有无预算、预算id、预算编号、预算金额、费用科目、部门代码、预算费用标志}
function selectFinanceList(hasBudget){
  var url = context + "/finance/finance.do?method=showSelectFinance" + (hasBudget == null ? '' : '&hasBudget=' + hasBudget);
  var features="dialogHeight:500px; dialogWidth:900px; center: yes; resizable: no; status: no; help:no";
  return window.showModalDialog(url, window, features);  
}
function selectFinanceList2(hasBudget){
  var url = context + "/finance/finance.do?method=showSelectFinance2";
  var features="dialogHeight:500px; dialogWidth:900px; center: yes; resizable: no; status: no; help:no";
  return window.showModalDialog(url, window, features);  
}

//选择部门信息
function selectDept(parent, referId, referTitle){
  var url = context + "/sys/department.do?method=showDeptSelect&parent="+parent;
  var features="dialogHeight:310px; dialogWidth:360px; center: yes; resizable: no; status: no; help:no";
  var ret = window.showModalDialog(url, window, features);
  if(ret!=null){
    if(referId!=null) referId.value=ret[0];
	if(referTitle!=null) referTitle.value=ret[1];
  }
}
//部门信息选择：(选择到科)
//参数（部门id）
//返回值：{部门id、部门名称}
function selectDeptList(parent){
  var url = context + "/sys/department.do?method=showDeptSelect&parent="+parent;
  var features="dialogHeight:310px; dialogWidth:360px; center: yes; resizable: no; status: no; help:no";
  return window.showModalDialog(url, window, features);
}
//使用部门信息选择：(选择到科)
//参数：(部门id)
//返回值：{部门id、部门名称}
function selectDeptList2(parent){
  var url = context + "/sys/department.do?method=showDeptSelect&type=2&parent="+parent;
  var features="dialogHeight:350px; dialogWidth:360px; center: yes; resizable: no; status: no; help:no";
  return window.showModalDialog(url, window, features);
}
//归口部门信息选择：(选择到科)
//参数：(部门id)
//返回值：{部门id、部门名称}
function selectDeptList3(parent){
  var url = context + "/sys/department.do?method=showDeptSelect&type=3&parent="+parent;
  var features="dialogHeight:350px; dialogWidth:360px; center: yes; resizable: no; status: no; help:no";
  return window.showModalDialog(url, window, features);
}
//角色用户信息选择(无直接返回到页面值)
//参数（部门id、角色代码）
//返回值：{用户id、用户名称、用户帐号、手机}
function selectDuty(deptId, code){
  var url = context + "/sys/user.do?method=showPurchaseDuty&dept="+deptId+"&code="+code;
  var features="dialogHeight:350px; dialogWidth:350px; center: yes; resizable: no; status: no; help:no";
  return window.showModalDialog(url, window, features); 
}

//角色用户信息选择
//参数（部门id、角色代码）
//返回值：{用户id、用户名称、用户帐号、手机}
function selectUserDuty(deptId, code, userId, userName, userAccount, userMobile){
  var url = context + "/sys/user.do?method=showPurchaseDuty&dept="+deptId+"&code="+code;
  var features="dialogHeight:350px; dialogWidth:350px; center: yes; resizable: no; status: no; help:no";
  var user = window.showModalDialog(url, window, features); 
  if(user != null){
	  	if(userId != null)	userId.value=user[0];
		if(userName != null) userName.value=user[1];
		if(userAccount != null) userAccount.value=user[2];
		if(userMobile != null)	userMobile.value=user[3];
  }
}
//采购分类选择
//参数：applyDeptId
//返回值：{id、名称、使用部门id、使用部门名称、归口部门id、归口部门名称、采购担当id、采购担当名称、code}
function selectGoodsCategoryList(applyDeptId, id, name) {
  var url = context + "/goods/category_select.do?applyDeptId="+applyDeptId;
  var rst = ShowDialog(url, 310, 360, false, false);
	if (rst) {
		var goodsCat;
		eval("goodsCat=" + rst[1]);
	  if (id) {$(id).value = goodsCat.id; }
	  if (name) { $(name).value = goodsCat.name; }
	}
	return rst;
}

//科目代码：
//返回值：{科目id、科目名称SubjectName，科目代码SubjectCode}
function selectSubjectList(parent,referId,referName,referCode){
	var url = context + "/sys/select_subject.do?parent="+parent;
  	var ret = ShowDialog(url, 310, 360, false, false);
	if(ret!=null && ret[1]!=null){		
		if(ret[1] != null){
			eval("subCode="+ret[1]);
			if(referId!=null) referId.value=subCode.id;
			if(referName!=null) referName.value=subCode.name;	
			if(referCode!=null) referCode.value=subCode.code;	
			return subCode
		}else{
			if(referId!=null)referId.value="";
			if(referName!=null)referName.value="";
			if(referCode!=null)referCode.value="";
		}		
    }
    return null;
}

function attachFrame(){
  document.writeln("<iframe name='hiddenFrame' id='hiddenFrame' width=0 height=0></iframe>");
}
//计算百分比,小数点保留2位
//firstNum/secondNum
//返回值类似 50.35%
function countPercent(firstNum, secondNum){
  if(secondNum==0){
	return ("0"); 
  }else{
	return ((firstNum*100/secondNum)).toFixed(2)+"%";
    //return (firstNum/secondNum*100).toPrecision(4).toString()+"%";
  }  
}
function countPercent2(firstNum, secondNum){
   if(firstNum != null && secondNum != null){
      return (firstNum+secondNum).toFixed(2)+"%";
   }
}
//显示审批意见
//参数：引用类型，引用id
function doComment(referType, referId){
  var url = context + "/others/audit.do?method=prepareComment&referType="+referType+"&referId="+referId;
  openWindow(url, 350, 350);
}
//
function  cutDigits(Dight,How){          
   if(How==null||How==''||How=='undefined'){
		 How=2;
   }    
   Dight  =  Math.round  (Dight*Math.pow(10,How))/Math.pow(10,How);  
   if(Dight=='NaN')
	  Dight=0;
   var dotPos = (Dight+"").indexOf(".",0);
   var strLen = (Dight+"").length;
   if(dotPos==-1){
	 Dight=Dight+".";
	 for(var k=0;k<How;k++){
	  Dight=Dight+"0";
	 }
   }else{
	for(var k=0;k<=(How-(strLen-dotPos));k++){
	  Dight=Dight+"0";
	 }
   }
   return  Dight;  
}

var Attachment = function(attachment, url) {
	this.addAttachment = function() {
		var s = openUpload(attachment);
		if (s && s.length > 0) {
			$('viewFile').style.display = '';
			$('delFile').style.display = '';
		}
	}
	this.viewFile = function() {
		var file = $(attachment).value;
		if (file.length > 0) {
			window.open(url + file);
		}
	}
	this.delFile = function() {
		$(attachment).value = '';
		$('viewFile').style.display = 'none';
		$('delFile').style.display = 'none';
	}
}
function disableEnter()   
{   
	var k = window.event.keyCode;   
	if(k == 13)   
	{   
		window.event.keyCode = 0;   
		window.event.returnValue = false;   
	}
} 
//referType:类型
//referId:表单id
//viewType:0：查看页面, 1：管理页面
function viewFile(referType, referId, viewType){
  var url = context + "/others/attachment.do?method=showList&referType=" + referType + "&referId=" + referId + "&viewType="+viewType;
  openWindow(url, 400, 250, "no");  
}
//referType:类型  (只作查看)
//referId:表单id,多个
function viewFiles(referType, referIds){
  var url = context + "/others/attachment.do?method=showLists&referType=" + referType + "&referId=" + referIds;
  openWindow(url, 400, 250, "no");  
}

function purchaseViewFile(referType, referId, viewType){
  var url = context + "/purchase/purchase.do?method=showAttachmentList&referType=" + referType + "&referId=" + referId + "&viewType="+viewType;
  openWindow(url, 400, 250, "no");  
}

function purchaseAddViewFile(referType, referId, viewType, type){
  var url = context + "/purchase/purchase.do?method=showAddAttachmentList&referType=" + referType + "&referId=" + referId + "&viewType="+viewType + "&type="+type;
  openWindow(url, 400, 250, "no");  
}

function purchaseOldviewFile(referType, referId, viewType){
  var url = context + "/purchase/purchase.do?method=showAttachmentOldList&referType=" + referType + "&referId=" + referId + "&viewType="+viewType;
  openWindow(url, 400, 250, "no");  
}

//是否需要验证重财
function isNeedCheckFinance() {
	/*if ($('dutyDept')) {
		return $('dutyDept').value != 303;
	}*/
	return true;
}
function expPurchase(sqlWhere,checkboxName){
  if(!window.confirm("确定导出清单吗？")) {
	  return false;
	}
	window.location.href = '../purchase/purchase.do?method=expNoCheckPurchase&checkAllFlag='+checkAllFlag
	+sqlWhere
	+'&id=' + getCheckboxValue(checkboxName ? checkboxName : 'id');
}
function expReturnPurchaseData(sqlWhere,checkboxName){
  if(!window.confirm("确定导出清单吗？")) {
	  return false;
	}
	window.location.href = '../purchase/purchase.do?method=expReturnPurchase&id=' + getCheckboxValue(checkboxName ? checkboxName : 'id')
	+"&checkAllFlag="+checkAllFlag
	+sqlWhere
	;
}
function expPurchaseData(sqlWhere,checkboxName,isroot){
  if(!window.confirm("确定导出清单吗？")) {
	  return false;
	}
	window.location.href = "../purchase/purchase.do?method=expPurchase&id=" + getCheckboxValue(checkboxName ? checkboxName : 'id')
	+"&checkAllFlag="+checkAllFlag
	+sqlWhere
	;
	
//	+'&query_currentPurchaseApply.submitDate_xdatege='
//	+"<%=request.getParameter('query_currentPurchaseApply.submitDate_xdatege')%>"
//	+'&query_currentPurchaseApply.submitDate_xdatele='
//	+"<%=request.getParameter('query_currentPurchaseApply.submitDate_xdatele')%>"
	
}
function expPurchaseSubmitData(checkboxName){
	var expFlag=1;
  if(!window.confirm("确定导出清单吗？")) {
	  return false;
	}
	window.location.href = '../purchase/purchase.do?method=expPurchase&id=' + getCheckboxValue(checkboxName ? checkboxName : 'id')+"&expFlag="+expFlag;
}
function expPriceData(){
  if(!window.confirm("确定导出清单吗？")) {
	  return false;
	}
	window.location.href = '../price/pricequery.do?method=expPriceQuery&id=' + (getCheckboxValue('id') == '' ? getCheckboxValue('priceQueryId') : getCheckboxValue('id'));
}
function expDecisionData(){
  if(!window.confirm("确定导出清单吗？")) {
	  return false;
	}
	window.location.href = '../price/decision.do?method=expDecision&id=' + getCheckboxValue('decisionId');
}
/*ids 为所选择的id，expFlag 为选择的EXCEL文件，expAll 为标志是否全选，param 为当全选时的参数 */

function expOrderListData(form,expFlag){
	var params = $("params")?$("params").value:"";
	var expAll = $("expAll")?$("expAll").value:0;
	if(expAll == 0){
		if(!window.confirm("确定导出所选择的清单吗？")) {
		  return false;
		}
	}else if(expAll == 1){
		if(!window.confirm("确定导出所有的清单吗？")) {
		  return false;
		}
	}
	window.document.location.href = "../order/order.do?method=printOrderList&"+ params +"&ids="+ getCheckboxValue('ids')
	+"&expFlag="+expFlag+"&expAll="+expAll;
}
function expContractListData(listNum){
	 var params = $("params")?$("params").value:"";
	 var isChecked =0;
   var obj = document.getElementsByName("chkall");
   for (var i=0;i<obj.length;i++) {
    var e = obj.item(i);
    if (e.checked ){
       isChecked = 1;
	  } 
	}
  if(!window.confirm("确定导出清单吗？")) {
	  return false;
	}
	window.document.location.href = "../contract/contract.do?method=printContractList&ids="+ getCheckboxValue('ids')+"&isChecked="+isChecked+"&listNum="+listNum+"&"+params;
}
function expCheckListData(type){
	var params = $("params")?$("params").value:"";
	var expAll = $("expAll")?$("expAll").value:0;
	if(expAll == 0){
		if(!window.confirm("确定导出所选择的清单吗？")) {
		  return false;
		}
	}else if(expAll == 1){
		if(!window.confirm("确定导出所有的清单吗？")) {
		  return false;
		}
	}
	window.document.location.href = "../check/accept.do?method=printCheckList"+ params +"&id="+ getCheckboxValue('id')+"&type="+type+"&expAll="+expAll;
}

/*
 *flag   0: 选择不同的打印模板
 *expAll 1: 是全选的打印
 *params  : 是所传的参数
 *add     : agoo
 */
function expPaymentListData2(num){
  var listNum = num ;
  var params = $("params")?$("params").value:"";
	var isChecked =0;
   var obj = document.getElementsByName("batchSelect");
   for (var i=0;i<obj.length;i++) {
    var e = obj.item(i);
    if (e.checked ){
       isChecked = 1;
	  } 
	}
	if(isChecked == 0){
		if(!window.confirm("确定导出所选择的清单吗？")) {
		  return false;
		}
	}else if(isChecked == 1){
		if(!window.confirm("确定导出所有的清单吗？")) {
		  return false;
		}
	}
	window.document.location.href = "../pay/paylist.do?method=expPaymentList&id="+ getCheckboxValue('id')+"&listNum="+listNum+"&isChecked="+isChecked+"&"+params;
}
function expPaymentListData(flag){
  var exp_flag=1;
  if(flag)exp_flag = flag;
  if(!window.confirm("确定导出清单吗？")) {
	  return false;
	}
	window.document.location.href = "../pay/paylist.do?method=printPaymentList&id="+ getCheckboxValue('id')+"&exp_flag="+exp_flag;
}
/*
 *	打印付款通知书
 */
function printNote(form){
if(!confirm("是否打印？")){
	return ;
}
	for (var i=0;i<form.id.length;i++) {
		var e = form.id[i];
		if (e.checked){
			window.document.location.href=context+"/print/payment.do?method=printPaymentAction&id="+e.value;
			break;
		} 
	}    
}

/*
 * 打印付款通知书对应的验收清单
 */
function printCheckForPayment(form){
if(!confirm("是否打印？")){
	return ;
}
for (var i=0;i<form.id.length;i++) {
		var e = form.id[i];
		if (e.checked){
			switch(e.checkType){
				case "0"://进度
						window.document.location.href=context+"/print/check.do?method=printCheckAction&printType=1&id="+e.value;
					//alert("对不起！对于进度打印系统需要修改打印！暂时不提供！我们将尽快解决！");
					break;
				case "1"://合同、订单
					window.document.location.href=context+"/print/check.do?method=printCheckAction&printType=1&id="+e.value;
				break;
				default:
					alert("此付款通知书没有验收打印格式！");
				break;
			}
		} 
	}    
}

/*
 * 打印付款通知书的发票粘贴单
 */
function printExcelInvoicePaste(form){
if(!confirm("是否打印？")){
	return ;
}
	window.document.location.href=context+"/pay/paylist.do?method=printExcelInvoicePaste";
}
function expCheck_submitListData()
{  var expFlag=1;
  if(!window.confirm("确定导出清单吗？")) {
	  return false;
	}
	window.document.location.href = "../check/accept.do?method=printCheckList&id="+ getCheckboxValue('id')+"&expFlag="+expFlag+"&checkAllFlag="+checkAllFlag+sqlWhere;
}
function expCheck_auditListData(sqlWhere)
{  var expFlag=2;
  if(!window.confirm("确定导出清单吗？")) {
	  return false;
	}
	window.document.location.href = "../check/accept.do?method=printCheckList&id="+ getCheckboxValue('id')+"&expFlag="+expFlag+"&checkAllFlag="+checkAllFlag+sqlWhere;
}
//将表单输入框的属性由无效转换成有效只读
function setDisabledToReadOnly(box) {
  var obj = box;
	if (typeof(obj) != 'object') {
	  obj = document.getElementById(obj);
	}
	if (!obj) {
	  return;
	}
	
	var inputs = obj.getElementsByTagName('input');
	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].type == 'text' && inputs[i].disabled) {
			inputs[i].disabled = false;
			inputs[i].readOnly = true;
		}
	}
	var textareas = obj.getElementsByTagName('textarea');
	for (var i = 0; i < textareas.length; i++) {
		if (textareas[i].disabled) {
			textareas[i].disabled = false;
			textareas[i].readOnly = true;
		}
	}
}
//查看工作流状态
function viewProcessStatus(idName) {
  var id = getCheckboxValue(idName,'processId');
  if(id!='null'){
    openWindow(context + '/workflow/processMonitorController.jspa?method=stateInstances&view=simpleStateInstances&processInstanceId='+ id, 850, 600,'yes');
  }else{
    alert("该记录没有启动工作流");
  }
}

//查看审核意见
function showAuditList(referId, referType) {
  openWindow('../others/audit.do?method=showList2&referId=' + referId + '&referType=' + referType, 650, 400, true);
}


//返回所有用户列表,列表可搜索
//zoumin
function selectAllUserList(referId, referTitle){
	return ShowDialog(context + '/sys/user.do?method=selectSysUser', 430, 450, false, false, referId, referTitle);
}
//zoumin
function selectUserByDept(deptId, referId, referTitle){
	return ShowDialog(context + '/sys/user.do?method=selectSysUserByDept&deptId=' + deptId, 430, 450, false, false, referId, referTitle);
}
//zoumin
function selectSupplierBy(referId, referTitle){
	return ShowDialog(context + '/spadmin/supplier.do?method=selectSupplier', 460, 420, false, false, referId, referTitle);
}
//---------------------------------
//zoumin
//num：复选框被选中的个数；ckAll：全选框的name；count：页面中复选框个数（除去“全选框”）
function isChkAll(num, ckAll, count){
	if(num!=count){ //如果有一个没选中，全选框则取消全选
		ckAll.checked = false;
	}
}
//退单原因samlee
function showRejectReason(id) {
  //var url = 'purchase.do?method=showRejectReason&id=' + id;
  var url = '../purchase/purchase.do?method=showRejectReason&id=' + id;
	openWindow(url, 650, 400, true);
}

function showRejectReason2(id) {
  var url = '../purchase/purchase.do?method=showRejectReason&id=' + id+'&cancelbefore=true';
	openWindow(url, 650, 400, true);
}

//打开查看预算窗口
function showBudget(budgetid,budetFlag){
	if (budetFlag == 0) {//投资预算
		openWindow('../budget/budget.do?method=view&id='+budgetid, 850, 430,'yes');
	}
	
	if (budetFlag == 1){//费用预算
		openWindow('../budget/fee.do?method=view&id='+budgetid, 850, 430,'yes');
	}
}

function getValuesForSQL(name)
{
  var inputObj = document.getElementsByName(name);
	var values = "";
	for (var i = 0; i < inputObj.length; i++)
	{
	  if (inputObj.item(i).value != "")
		{
		  values += "'"+inputObj.item(i).value + "',";
		}
	}
	if (values.substring(values.length - 1) == ",")
	{
	  values = values.substring(0, values.length - 1);
	}
	return values.replace(/\r\n/ig, "%0A");
}

var newPurchase;
function exectEval(str){
	eval(str);
	window.focus();
}
function openWindowWithName(url, winName, width, height, scroll){  
  var  y=((window.screen.availHeight-height)/2);  
  var  x=((window.screen.availWidth-width)/2);
  scroll = scroll == true ? 'yes' : scroll; 
  var rst = window.open(url, winName, "height=" + height + ", width=" + width + ", toolbar =no, menubar=no, scrollbars=" + scroll + ", resizable=yes, location=no, status=no, top=" + y + ", left=" + x + "");
  rst.focus();
  return rst;
}