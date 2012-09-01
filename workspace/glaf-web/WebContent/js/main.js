var context = "/glaf";

/**
 * 打开弹出窗口 url 页面地址 width 数字，窗口宽度 height 数字窗口高度 scroll true/false, 是否有滚动条
 */

function openWindow(url, width, height, scroll) {
	var y = ((window.screen.availHeight - height) / 2);
	var x = ((window.screen.availWidth - width) / 2);
	scroll = scroll == true ? 'yes' : scroll;
	var rst = window.open(url, "", "height=" + height + ", width=" + width
			+ ", toolbar =no, menubar=no, scrollbars=" + scroll
			+ ", resizable=yes, location=no, status=no, top=" + y + ", left="
			+ x + "");
	rst.focus();
}
function openMaxWindow(url) {
	var width, height;
	if (document.layers) {
		width = screen.availWidth - 10;
		height = screen.availHeight - 20;
	} else {
		width = screen.availWidth - 2;
		height = screen.availHeight;
	}
	var win = window
			.open(
					url,
					"",
					"height="
							+ height
							+ ", width="
							+ width
							+ ", toolbar =no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no, top=0, left=0");
	win.focus();
	if (document.all) {
		win.resizeTo(screen.availWidth, screen.availHeight);
		win.moveTo(0, 0);
	}
}
/**
 * 关闭弹出窗口，并刷新上层页面
 */
function closeOpenWindow() {
	opener.location.reload();
	self.close();
}

/**
 * 全选/反选checkbox form form表单 obj 对象
 */

function checkAll(form, obj) {

	for ( var i = 0; i < form.elements.length; i++) {
		var e = form.elements[i];
		if (e.type == 'checkbox' && e.disabled == false)
			e.checked = obj.checked;
	}
}
function setCheckAllFlag() {
	checkAllFlag = 1;
}
function getCheckedBoxNum(form, name) {
	var num = 0;
	for ( var i = 0; i < form.elements.length; i++) {
		var e = form.elements[i];
		if (e.type == 'checkbox' && e.checked && e.name == name) {
			num = num + 1;
		}
	}
	return num;
}
/**
 * 确认批量删除 form 表单
 */
function confirmDelete(form) {
	var isChecked = false;
	for ( var i = 0; i < form.elements.length; i++) {
		var e = form.elements[i];
		if (e.name != "chkall" && e.type == "checkbox" && e.checked == true) {
			isChecked = true;
			break;
		}
	}
	if (isChecked) {
		if (confirm("您确定要删除所选的记录吗？")) {
			return true;
		} else {
			return false;
		}
	} else {
		alert("您还没有选择要删除的记录.");
		return false;
	}
}
function confirmAudit(form) {
	var isChecked = false;
	for ( var i = 0; i < form.elements.length; i++) {
		var e = form.elements[i];
		if (e.name != "chkall" && e.type == "checkbox" && e.checked == true) {
			isChecked = true;
			break;
		}
	}
	if (isChecked) {
		if (confirm("您确定要审核所选的记录吗？")) {
			return true;
		} else {
			return false;
		}
	} else {
		alert("您还没有选择要审核的记录.");
		return false;
	}
}
function getXMLHttpObject() {
	var C = null;
	try {
		C = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			C = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (sc) {
			C = null;
		}
	}
	if (!C && typeof XMLHttpRequest != "undefined") {
		C = new XMLHttpRequest();
	}
	return C;
}
function getChildTree(catId) {
	var xmlhttp = getXMLHttpObject();
	if (xmlhttp) {
		xmlhttp.onreadystatechange = function() {// 激活事件
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {// 有返回
				try {
					eval("node_" + catId + ".innerHTML=xmlhttp.responseText");
				} catch (e) {
				}
			}
		};
		xmlhttp.open("GET", context+"/sys/authorize.do?method=showSubMenu&parent="
				+ catId, true);
		xmlhttp.send(null);
	}
}
function createChildNode(catId) {
	var temp;
	try {
		eval("temp=node_" + catId + ".innerHTML");
	} catch (e) {
	}

	if (temp == "") {
		eval("node_" + catId + ".style.display='block'");
		eval("node_" + catId + ".innerHTML='节点加载中...'");

		getChildTree(catId);
	}
}
function showHide() {
	var temp;
	eval("temp=menuRoot.style.display");
	if (temp == "block") {
		eval("menuRoot.style.display='none'");
	} else {
		eval("menuRoot.style.display='block'");
	}
}
/**
 * 列表页面，当多选时，编辑按钮将不可用 form form表单 name checkBox名字 btn 编辑按钮
 */
function disableEditBtn(form, name, btn) {
	var num = getCheckedBoxNum(form, name);

	if (num > 1) {
		btn.disabled = true;
	}
}

/**
 * 按钮(只有在CheckBox只有选择1个时使能） form form表单 name checkBox名字 btn 编辑按钮
 */
function editBtnOperation(form, name, btn) {
	var num = getCheckedBoxNum(form, name);
	if (num != 1) {
		btn.disabled = true;
	} else {
		btn.disabled = false;
	}
}

/**
 * 按钮(只有在CheckBox只有选择多个时使能） form form表单 name checkBox名字 btn 编辑按钮
 */
function thisBtnOperation(form, name, btn) {
	var num = getCheckedBoxNum(form, name);
	if (num > 1) {
		btn.disabled = false;
	} else {
		btn.disabled = true;
	}
}

/**
 * 删除/提交按钮(只有在CheckBox只有选择>=1个时使能） form form表单 name checkBox名字 btn 编辑按钮
 */
function dsBtnOperation(form, name, btn) {
	var num = getCheckedBoxNum(form, name);

	if (num >= 1) {
		btn.disabled = false;
	} else {
		btn.disabled = true;
	}
}

/**
 * 列表页面，当无选时，X buttom将不可用 form form表单 name checkBox名字 btn 编辑按钮
 */
function disableBtn(form, name, btn) {
	var num = getCheckedBoxNum(form, name);

	if (num < 1) {
		btn.disabled = true;
	}
}

function confirmCheckBox(form, name, message) {
	var isChecked = false;
	for ( var i = 0; i < form.elements.length; i++) {
		var e = form.elements[i];
		if (e.name == name && e.type == "checkbox" && e.checked == true) {
			isChecked = true;
			break;
		}
	}
	if (!isChecked) {
		return false;
	}
	return true;
}

// 刷新当前页面
function reloadPage() {
	window.location.reload();
}

/*
 * ================================================================================
 * getRadioValue(obj[, attribute]...):取Radio的值
 * ================================================================================
 */
function getRadioValue(obj) {
	var obj = document.getElementsByName(obj);
	for ( var i = 0; i < obj.length; i++) {
		var e = obj.item(i);
		if (e.checked) {
			if (arguments.length >= 2) {
				var values = new Array();
				var isReturnString = false;
				var m = 0;
				for ( var j = 1; j < arguments.length; j++) {
					if (arguments[j] == true || arguments[j] == false) {
						isReturnString = arguments[j];
					} else {
						values[m] = e.getAttribute(arguments[j]);
						m++;
					}
				}
				return isReturnString ? values.toString() : values;
			} else {
				return e.value;
			}
			break;
		}
	}
	return "";
}

/*
 * ================================================================================
 * getCheckboxValue(obj[, attribute]...):取复选框的值
 * ================================================================================
 */
function getCheckboxValue(obj) {
	var obj = document.getElementsByName(obj);
	var values = new Array();
	var index = 0;
	var isReturnString = true;
	for ( var i = 0; i < obj.length; i++) {
		var e = obj.item(i);
		if (e.checked) {
			if (arguments.length >= 2) {
				isReturnString = false;
				var m = 0;
				values[index] = new Array();
				for ( var j = 1; j < arguments.length; j++) {
					if (arguments[j] == true || arguments[j] == false) {
						isReturnString = arguments[j];
					} else {
						values[index][m] = e.getAttribute(arguments[j]);
						m++;
					}
				}
			} else {
				values[index] = e.value;
			}
			index++;
		}
	}

	if (isReturnString) {
		return values.toString();
	} else {
		return values;
	}
}

/*
 * ================================================================================
 * CheckAll(this, obj):复选框全选/反选.
 * ================================================================================
 */
function CheckAlls(obj, target) {
	if (obj.checked) {
		CheckAll(target);
	} else {
		CheckNone(target);
	}
}

/*
 * ================================================================================
 * CheckAll(obj):复选框全选.
 * ================================================================================
 */
function CheckAll(obj) {
	checkAllFlag = 1;
	var obj = document.getElementsByName(obj);
	for ( var i = 0; i < obj.length; i++) {
		obj.item(i).checked = true;
	}
}

/*
 * ================================================================================
 * CheckNone(obj):复选框全不选.
 * ================================================================================
 */
function CheckNone(obj) {
	var obj = document.getElementsByName(obj);
	for ( var i = 0; i < obj.length; i++) {
		obj.item(i).checked = false;
	}
}

function getCheckedboxNums(obj) {
	var obj = document.getElementsByName(obj);
	var num = 0;
	for ( var i = 0; i < obj.length; i++) {
		var e = obj.item(i);
		if (e.checked) {
			num++;
		}
	}
	return num;
}

function getObjNum(obj) {
	var obj = document.getElementsByName(obj);
	var num = 0;
	if (obj) {
		num = obj.length;
	}
	return num;
}

function $(element) {
	if (arguments.length > 1) {
		for ( var i = 0, elements = [], length = arguments.length; i < length; i++)
			elements.push($(arguments[i]));
		return elements;
	}
	if (typeof element == 'string')
		element = document.getElementById(element);
	return element;
}

function getValues(name) {
	var inputObj = document.getElementsByName(name);
	var values = '';
	for ( var i = 0; i < inputObj.length; i++) {
		if (inputObj.item(i).value != '') {
			values += inputObj.item(i).value + ',';
		}
	}
	if (values.substring(values.length - 1) == ",") {
		values = values.substring(0, values.length - 1);
	}
	return values.replace(/\r\n/ig, "%0A");
}

/** *** 显示无模式对话框 **** */
function ShowDialog(url, width, height, dialogscroll, obj) {
	var arr = showModalDialog(url, window, "dialogWidth:" + width
			+ "px;dialogHeight:" + height
			+ "px;center:yes;help:no;resizable:no;status:no;scroll:"
			+ dialogscroll + "");
	var isAdd = arguments[4];
	var submitFormName = "submitForm";
	var submitForm = null;
	var submitFormTemp = null;
	for ( var i = 5, m = 0; i < arguments.length; i++, m++) {
		var o = arguments[i];
		if (!o) {
			continue;
		}
		if (typeof (o) != 'object') {
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
			if (typeof (arr) == 'object') {
				if (typeof (arr[0]) == 'object') {
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
function getArrayValue(arr, index) {
	var arrLen = arr.length;
	var returnValue = new Array();
	for ( var n = 0; n < arrLen; n++) {
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


// 选择基础数据
function selectData(dataCode, referId, referTitle, referCode, parent) {
	var url = context + "/sys/dictory.do?method=showDictData&code=" + dataCode
			+ "&parent=" + parent;
	var features = "dialogHeight:310px; dialogWidth:360px; center: yes; resizable: no; status: no; help:no";
	var ret = window.showModalDialog(url, window, features);
	if (ret != null) {
		if (referId != null)
			referId.value = ret[0];
		if (referTitle != null)
			referTitle.value = ret[1];
		if (referCode != null)
			referCode.value = ret[2];
	}
}
// 选择基础数据
function selectCategory(dataCode, referId, referTitle, referCode, parent) {
	var url = context + "/sys/dictory.do?method=showDictData&code=" + dataCode
			+ "&parent=" + parent;
	var features = "dialogHeight:310px; dialogWidth:360px; center: yes; resizable: no; status: no; help:no";
	var ret = window.showModalDialog(url, window, features);
	if (ret != null) {
		if (referId != null)
			referId.value = ret[0];
		if (referTitle != null)
			referTitle.value = ret[1];
		if (referCode != null)
			referCode.value = ret[2];
	}
}

function jump(action, openType) {
	if (openType && openType == 2) {
		var newWin = window.open(action, '', "height=" + (screen.height - 88)
				+ ", width=" + (screen.width - 30)
				+ ", toolbar =no, menubar=no, scrollbars=" + scroll
				+ ", resizable=yes, location=no, status=no, top=5, left=5");
		newWin.focus();
	} else {
		if (action != '')
			window.location.href = action;
	}
}
/** 根据搜索标签构造搜索条件* */
function getSearchElement(form) {
	var url = "";
	for ( var i = 0; i < form.elements.length; i++) {
		var obj = form.elements[i];
		if (obj.searchflag + "" == "undefined")
			continue;
		if (obj.searchflag == "1" && obj.value.length > 0) {// 是搜索标志
			url += obj.name + "=" + escape(obj.value) + "&";
		}
	}
	return url;
	// alert(url);
}
function getQueryStringRegExp(name) {
	var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
	if (reg.test(location.href)) {
		var s = unescape(RegExp.$2.replace(/\+/g, " "));
		return s;
	}
	return "";
};
/* 根据搜索条件设置页面查询框 */
function setSearchElement(form) {
	var url = window.location;
	for ( var i = 0; i < form.elements.length; i++) {
		var obj = form.elements[i];
		if (obj.searchflag + "" == "undefined")
			continue;
		if (obj.searchflag == "1") {// 是搜索标志
			obj.value = getQueryStringRegExp(obj.name);
		}
	}
}
function UrlDecode(str) {
	var ret = "";
	for ( var i = 0; i < str.length; i++) {
		var chr = str.charAt(i);
		if (chr == "+") {
			ret += " ";
		} else if (chr == "%") {
			var asc = str.substring(i + 1, i + 3);
			if (parseInt("0x" + asc) > 0x7f) {
				ret += asc2str(parseInt("0x" + asc
						+ str.substring(i + 4, i + 6)));
				i += 5;
			} else {
				ret += asc2str(parseInt("0x" + asc));
				i += 2;
			}
		} else {
			ret += chr;
		}
	}
	return ret;
}

function addToMyMenu() {
	openWindow(context
			+ '/workspace/mymenu.do?method=prepareAddMyMenu&url='
			+ encodeURIComponent(window.location.pathname
					+ window.location.search), 600, 450);
}

function openUpload(obj, type) {
	return ShowDialog(context + '/inc/upload/fileUpload.jsp'
			+ (type ? '?type=' + type : ''), 450, 230, 'no', false, obj);
}

// 第一个日期是否大于或等于第二个日期
function checkDate(DateOne, DateTwo, opgt) {
	DateOne = DateOne.toString();
	DateTwo = DateTwo.toString();
	var OneMonth = DateOne.substring(5, DateOne.lastIndexOf("-"));
	var OneDay = DateOne
			.substring(DateOne.length, DateOne.lastIndexOf("-") + 1);
	var OneYear = DateOne.substring(0, DateOne.indexOf("-"));

	var TwoMonth = DateTwo.substring(5, DateTwo.lastIndexOf("-"));
	var TwoDay = DateTwo
			.substring(DateTwo.length, DateTwo.lastIndexOf("-") + 1);
	var TwoYear = DateTwo.substring(0, DateTwo.indexOf("-"));

	var date1 = Date.parse(OneMonth + "/" + OneDay + "/" + OneYear);
	var date2 = Date.parse(TwoMonth + "/" + TwoDay + "/" + TwoYear);
	if (opgt ? (date1 > date2) : (date1 >= date2)) {
		return true;
	} else {
		return false;
	}
}

function getToday() {
	var date = new Date();
	return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
			+ date.getDate();
}

function setDisabled(name, value) {
	var obj = document.getElementsByName(name);
	for ( var i = 0; i < obj.length; i++) {
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
	for ( var i = 0; i < obj.length; i++) {
		var o = obj.item(i);
		if (o.value == value) {
			o.disabled = false;
		} else {
			o.disabled = true;
		}
	}
}

// 选择部门信息
function selectDept(parent, referId, referTitle) {
	var url = context + "/sys/department.do?method=showDeptSelect&parent="
			+ parent;
	var features = "dialogHeight:310px; dialogWidth:360px; center: yes; resizable: no; status: no; help:no";
	var ret = window.showModalDialog(url, window, features);
	if (ret != null) {
		if (referId != null)
			referId.value = ret[0];
		if (referTitle != null)
			referTitle.value = ret[1];
	}
}
// 部门信息选择：(选择到科)
// 参数（部门id）
// 返回值：{部门id、部门名称}
function selectDeptList(parent) {
	var url = context + "/sys/department.do?method=showDeptSelect&parent="
			+ parent;
	var features = "dialogHeight:310px; dialogWidth:360px; center: yes; resizable: no; status: no; help:no";
	return window.showModalDialog(url, window, features);
}
// 使用部门信息选择：(选择到科)
// 参数：(部门id)
// 返回值：{部门id、部门名称}
function selectDeptList2(parent) {
	var url = context
			+ "/sys/department.do?method=showDeptSelect&type=2&parent="
			+ parent;
	var features = "dialogHeight:350px; dialogWidth:360px; center: yes; resizable: no; status: no; help:no";
	return window.showModalDialog(url, window, features);
}
// 归口部门信息选择：(选择到科)
// 参数：(部门id)
// 返回值：{部门id、部门名称}
function selectDeptList3(parent) {
	var url = context
			+ "/sys/department.do?method=showDeptSelect&type=3&parent="
			+ parent;
	var features = "dialogHeight:350px; dialogWidth:360px; center: yes; resizable: no; status: no; help:no";
	return window.showModalDialog(url, window, features);
}
// 角色用户信息选择(无直接返回到页面值)
// 参数（部门id、角色代码）
// 返回值：{用户id、用户名称、用户帐号、手机}
function selectDuty(deptId, code) {
	var url = context + "/sys/user.do?method=showPurchaseDuty&dept=" + deptId
			+ "&code=" + code;
	var features = "dialogHeight:350px; dialogWidth:350px; center: yes; resizable: no; status: no; help:no";
	return window.showModalDialog(url, window, features);
}

// 角色用户信息选择
// 参数（部门id、角色代码）
// 返回值：{用户id、用户名称、用户帐号、手机}
function selectUserDuty(deptId, code, userId, userName, userAccount, userMobile) {
	var url = context + "/sys/user.do?method=showPurchaseDuty&dept=" + deptId
			+ "&code=" + code;
	var features = "dialogHeight:350px; dialogWidth:350px; center: yes; resizable: no; status: no; help:no";
	var user = window.showModalDialog(url, window, features);
	if (user != null) {
		if (userId != null)
			userId.value = user[0];
		if (userName != null)
			userName.value = user[1];
		if (userAccount != null)
			userAccount.value = user[2];
		if (userMobile != null)
			userMobile.value = user[3];
	}
}

function attachFrame() {
	document
			.writeln("<iframe name='hiddenFrame' id='hiddenFrame' width=0 height=0></iframe>");
}
// 计算百分比,小数点保留2位
// firstNum/secondNum
// 返回值类似 50.35%
function countPercent(firstNum, secondNum) {
	if (secondNum == 0) {
		return ("0");
	} else {
		return ((firstNum * 100 / secondNum)).toFixed(2) + "%";
		// return (firstNum/secondNum*100).toPrecision(4).toString()+"%";
	}
}
function countPercent2(firstNum, secondNum) {
	if (firstNum != null && secondNum != null) {
		return (firstNum + secondNum).toFixed(2) + "%";
	}
}
// 显示审批意见
// 参数：引用类型，引用id
function doComment(referType, referId) {
	var url = context + "/others/audit.do?method=prepareComment&referType="
			+ referType + "&referId=" + referId;
	openWindow(url, 350, 350);
}
//
function cutDigits(Dight, How) {
	if (How == null || How == '' || How == 'undefined') {
		How = 2;
	}
	Dight = Math.round(Dight * Math.pow(10, How)) / Math.pow(10, How);
	if (Dight == 'NaN')
		Dight = 0;
	var dotPos = (Dight + "").indexOf(".", 0);
	var strLen = (Dight + "").length;
	if (dotPos == -1) {
		Dight = Dight + ".";
		for ( var k = 0; k < How; k++) {
			Dight = Dight + "0";
		}
	} else {
		for ( var k = 0; k <= (How - (strLen - dotPos)); k++) {
			Dight = Dight + "0";
		}
	}
	return Dight;
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
function disableEnter() {
	var k = window.event.keyCode;
	if (k == 13) {
		window.event.keyCode = 0;
		window.event.returnValue = false;
	}
}
// referType:类型
// referId:表单id
// viewType:0：查看页面, 1：管理页面
function viewFile(referType, referId, viewType) {
	var url = context + "/others/attachment.do?method=showList&referType="
			+ referType + "&referId=" + referId + "&viewType=" + viewType;
	openWindow(url, 400, 250, "no");
}
// referType:类型 (只作查看)
// referId:表单id,多个
function viewFiles(referType, referIds) {
	var url = context + "/others/attachment.do?method=showLists&referType="
			+ referType + "&referId=" + referIds;
	openWindow(url, 400, 250, "no");
}

// 是否需要验证重财
function isNeedCheckFinance() {
	/*
	 * if ($('dutyDept')) { return $('dutyDept').value != 303; }
	 */
	return true;
}

// 查看工作流状态
function viewProcessStatus(idName) {
	var id = getCheckboxValue(idName, 'processId');
	if (id != 'null') {
		openWindow(
				context
						+ '/workflow/processMonitorController.jspa?method=stateInstances&view=simpleStateInstances&processInstanceId='
						+ id, 850, 600, 'yes');
	} else {
		alert("该记录没有启动工作流");
	}
}

// 查看审核意见
function showAuditList(referId, referType) {
	openWindow('../others/audit.do?method=showList2&referId=' + referId
			+ '&referType=' + referType, 650, 400, true);
}

// 返回所有用户列表,列表可搜索
function selectAllUserList(referId, referTitle) {
	return ShowDialog(context + '/sys/user.do?method=selectSysUser', 430, 450,
			false, false, referId, referTitle);
}

function selectUserByDept(deptId, referId, referTitle) {
	return ShowDialog(context
			+ '/sys/user.do?method=selectSysUserByDept&deptId=' + deptId, 430,
			450, false, false, referId, referTitle);
}
 
// ---------------------------------
// num：复选框被选中的个数；ckAll：全选框的name；count：页面中复选框个数（除去“全选框”）
function isChkAll(num, ckAll, count) {
	if (num != count) { // 如果有一个没选中，全选框则取消全选
		ckAll.checked = false;
	}
}
 
 

function getValuesForSQL(name) {
	var inputObj = document.getElementsByName(name);
	var values = "";
	for ( var i = 0; i < inputObj.length; i++) {
		if (inputObj.item(i).value != "") {
			values += "'" + inputObj.item(i).value + "',";
		}
	}
	if (values.substring(values.length - 1) == ",") {
		values = values.substring(0, values.length - 1);
	}
	return values.replace(/\r\n/ig, "%0A");
}

var newPurchase;
function exectEval(str) {
	eval(str);
	window.focus();
}
function openWindowWithName(url, winName, width, height, scroll) {
	var y = ((window.screen.availHeight - height) / 2);
	var x = ((window.screen.availWidth - width) / 2);
	scroll = scroll == true ? 'yes' : scroll;
	var rst = window.open(url, winName, "height=" + height + ", width=" + width
			+ ", toolbar =no, menubar=no, scrollbars=" + scroll
			+ ", resizable=yes, location=no, status=no, top=" + y + ", left="
			+ x + "");
	rst.focus();
	return rst;
}