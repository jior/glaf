/******************************************************/
/* 文件名：univerify.js                               */
/* 功  能：基于自定义属性的统一检测用Javascript函数库 */
/** *************************************************** */

/*
 * ================================================================================
 * 功能: 是否为邮政编码
 * ================================================================================
 */
function IsPost(str){
	var regExp = /^[1-9]\d{5}$/g;
	return regExp.test(str);
}

/*
 * ================================================================================
 * 功能: 是否为电话号码
 * ================================================================================
 */
function IsPhone(str){
	var regExp = /^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}$/g;
	return regExp.test(str);
}

/*
 * ================================================================================
 * 功能: 是否为手机号码
 * ================================================================================
 */
function IsMobile(str){
	var regExp = /^((\(\d{3}\))|(\d{3}\-))?13\d{9}$/g;
	return regExp.test(str);
}

/*
 * ================================================================================
 * 功能: 是否为中文字符
 * ================================================================================
 */
function IsChinese(str){
	var regExp = /^[\u0391-\uFFE5]+$/g;
	return regExp.test(str);
}

/*
 * ================================================================================
 * 功能: 是否为日期(yyyy[/-]mm[/-]dd)
 * ================================================================================
 */
function IsDate(str){
	var regExp = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/g;
	return regExp.test(str);
}

/*
 * ================================================================================
 * 功能: 是否为日期(yyyy[-]mm[-]dd)
 * ================================================================================
 */
function IsDate(str,type){
	var regExp = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/g;
	if(type == "-")regExp = /^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/g;
	return regExp.test(str);
}
/*
 * ================================================================================
 * 功能: 限制字符串长度 参数: minLen 最小长度, maxLen 最大长度(0代表没有限制) 返回值: boolean
 * ================================================================================
 */
function LenB(str){
	return str.replace(/[^\x00-\xff]/g,"**").length;
}
function IsLength(str,minLen,maxLen){
	var strLen = LenB(str);
	isLen = false;
	if(maxLen > 0){
		if(strLen >= minLen && strLen <= maxLen){
			isLen = true;
		}else{
			isLen = false;
		}
	}else{
		if(strLen >= minLen){
			isLen = true;
		}else{
			isLen = false;
		}
	}
	return isLen;
}

/*
 * ================================================================================
 * 功能: 是否为允许的上传文件类型 参数: limitType为允许的文件类型变量(用,分隔).如:limitType = "gif,jpg,bmp";
 * 返回值: boolean
 * ================================================================================
 */
function FileType(str){
	var regExp = /\.([^\.]+)(\?|$)/;
	if(regExp.test(str))
	{
		var type = str.match(/\.([^\.]+)(\?|$)/)[1];
		return type;
	}
	else
	{
		return "";
	}
}
function IsUploadFileType(str,limitType){
	var types = FileType(str);
	if(("," + limitType.toLowerCase() + ",").indexOf("," + types.toLowerCase() + ",") > -1)
	{
		return true;
	}
	else
	{
		return false;
	}
}

/*
 * ================================================================================
 * XMLEncode(string):对字符串进行XML编码.
 * ================================================================================
 */
function XMLEncode(str){
	str = Trim(str);
	str = str.replace("&","&amp;");
	str = str.replace("<","&lt;");
	str = str.replace(">","&gt;");
	str = str.replace("'","&apos;");
	str = str.replace("\"","&quot;");
	return str;
}

/*
 * ================================================================================
 * CheckAll(obj):复选框全选.
 * ================================================================================
 */
function CheckAll(obj){
	var obj = document.getElementsByName(obj);
	for(var i = 0; i < obj.length; i++){
		obj.item(i).checked = true;
	}
}

/*
 * ================================================================================
 * CheckNone(obj):复选框全不选.
 * ================================================================================
 */
function CheckNone(obj){
	var obj = document.getElementsByName(obj);
	for(var i = 0; i < obj.length; i++)
	{
		obj.item(i).checked = false;
	}
}

/*
 * ================================================================================
 * CheckItem(obj):复选框反选.
 * ================================================================================
 */
function CheckItem(obj){
	var obj = document.getElementsByName(obj);
	for(var i = 0; i < obj.length; i++)	{
		var e = obj.item(i);
		if(e.checked == false)		{
			e.checked = true;
		}else{
			e.checked = false;
		}
	}
}

/**
 * ================================================================================
 * 判断日期是否在指定日期的未来几天内.
 * 
 * @param Date
 *            appDate 开始日期.
 * @param Date
 *            futDate 结束日期.
 * @param int
 *            mixDays 至少天数.
 * @param int
 *            maxDays 最大天数.
 *            ================================================================================
 */
function InDate(appDate,futDate,mixDays,maxDays)
{
	var mixDiff = mixDays * 24 * 60  * 60 * 1000;
	var maxDiff = maxDays * 24 * 60  * 60 * 1000;
	if(appDate.toString().indexOf("-") > 0)
	{
	  appDate = new Date(appDate.replace("-","/"));
	}
	if(futDate.toString().indexOf("-") > 0)
	{
	  futDate = new Date(futDate.replace("-","/"));
	}	
	var value = futDate - appDate;
	if(value >= mixDiff && value <= maxDiff)
	{
		return true;
	}
	else
	{
		return false;
	}
}

/* 取得字符串的字节长度 */
function strlen(str){
  var i;
  var len = 0;
  
  for (i=0;i<str.length;i++){
    if (str.charCodeAt(i)>255) len+=2; else len++;
  }
  return len;
}

/*
 * 检测字符串是否为空
 */
function isNull(str){
  var i;
    
  for (i=0;i<str.length;i++){
    if (str.charAt(i)!=' ') return false;
  }
  return true;
}

/*
 * 检测字符串是否全为数字
 */
function isNumber(str){
  var number_chars = "1234567890";
  var i;

  for (i=0;i<str.length;i++){
    if (number_chars.indexOf(str.charAt(i))==-1) return false;
  }
  return true;
}
/*
 * 校验是否为Integer型的数据,返回的值为真和假,true和false
 */
function checkInteger(strInteger){
  if(strInteger.length==0){
    return true;
  }else{
    var pattern = /^-?\d+$/;
    if(strInteger.match(pattern)==null){
      return false;
    }else{
     return true;
    }
  }
} 
/*
 * 校验是否为float型的数据,返回的值为真和假,true和false;
 */
function checkFloat(strFloat){
  if(strFloat.length==0) {
    return true;
  } else {
    var pattern=/^(-?\d+)(\.\d+)?$/;
    if(strFloat.match(pattern)==null){
      return false;
    }else{
      return true;
    }
  }
} 
/*
 * 是否小时数字格式
 */
function isHour(str){
  if(!isNumber(str)) return false;  
  var value=parseInt(str);
  if(value<0 || value>23) return false;
  return true;   
}
/*
 * 是否分钟数字格式
 */
function isMinute(str){
  if(!isNumber(str)) return false;  
  var value=parseInt(str);
  if(value<0 || value>59) return false;
  return true;   
}
/*
 * 校验是否为email型的数据,返回的值为真和假,true和false;
 */
function isEmail(email){
  var pattern = /[a-zA-Z0-9_.]{1,}@[a-zA-Z0-9_]{1,}.[a-zA-Z0-9_]{1,}/;
  if (email.length==0) return true;
  if(email.match(pattern)==null){
    return false;
  }else{
    return true;
  }
} 
/*
 * 判断是否是日期型字符串 用法：isDate("2002-1-31")
 */
function isDate(str){ 
  var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/; 
  var r = str.match(reg); 
  if(r==null)return false; 
  var d= new Date(r[1], r[3]-1,r[4]); 
  var newStr=d.getFullYear()+r[2]+(d.getMonth()+1)+r[2]+d.getDate();
  return (newStr==str);
} 
/*
 * 判断是否是日期型字符串 用法：isDateTime("2002-1-31 12:34:56")
 */
function isDateTime(str){ 
  var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
  var r = str.match(reg); 
  if(r==null)return false; 
  var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]); 
  var newStr=d.getFullYear()+r[2]+(d.getMonth()+1)+r[2]+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds() 
  return newStr==str 
} 
/*
 * 判断日期是否正确 用法：chkDate(2002-4-16)
 */
function chkDate(sDate){ 
  var r="/\d{4}(?:-\d{1,2}){0,2}/" ;
  // 正则表达式，判断是否为yyyy-mm-dd,yyyy-mm,yyyy格式
  if(sDate.match(r)==sDate){ 
　  var arr=sDate.split("-"); 
　  switch(arr.length){ 
　  // 根据不同的yyyy-mm-dd,yyyy-mm格式判断年月日数字是否正确
　   case 3: 
　　   var tmpDate=new Date(arr[0],arr[1],arr[2]); 
　　   if(tmpDate.getMonth()==arr[1] && tmpDate.getFullYear()==arr[0]) return true; 
　　   break; 
　   case 2: 
　　   if(arr[1]<13) return true; 
　　   break; 
　   default: 
　　   return false; 
　  } 
  } 
  return false; 
} 
/*
 * 判断日期DateOne是否在日期DateTwo后面
 */
function compareDate(DateOne,DateTwo){
  var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ("-"));
  var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ("-")+1);
  var OneYear = DateOne.substring(0,DateOne.indexOf ("-"));

  var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ("-"));
  var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ("-")+1);
  var TwoYear = DateTwo.substring(0,DateTwo.indexOf ("-"));

  if (Date.parse(OneMonth+"/"+OneDay+"/"+OneYear) > Date.parse(TwoMonth+"/"+TwoDay+"/"+TwoYear)){
    return true;
  }else{
    return false;
  }
}

/**
 * 判断日期是否在指定日期的未来几天内.
 * 
 * @param Date
 *            appDate 参考日期.
 * @param Date
 *            futDate 输入日期.
 * @param int
 *            days 天数.
 */
function isInDate(appDate,futDate,days)
{
	var msDiff = days * 24 * 60  * 60 * 1000;
	if(appDate.toString().indexOf("-") > 0)
	{
	  appDate = new Date(appDate.replace("-","/"));
	}
	if(futDate.toString().indexOf("-") > 0)
	{
	  futDate = new Date(futDate.replace("-","/"));
	}	
	var value = futDate - appDate;
	if(value > 0 && value <= msDiff)
	{
		return true;
	}
	else
	{
		return false;
	}
}
function IsInDate(futDate)
{
	var curDate = new Date();
	return isInDate(curDate, futDate, 7);	
}
/**
 * 判断文件类型是否正确
 */
function isFile(file, fileType){
  var arr = fileType.split(",");  
  var start = file.lastIndexOf(".");
  var end = file.length;
  
  if(start>0){
    var ext = file.substring(start+1, end);
	ext = ext.toLowerCase( );
	// alert(ext + ":" + fileType);
    for(var i=0;i<arr.length;i++){
	  // alert(arr[i]+":"+ext);
      if(arr[i]==ext){// 类型匹配
		  return true;
		  break;  
	  }
	}
  }
  return false;
}

/*
 * 检测指定文本框输入是否合法 true验收失败，false成功
 */
function verifyInput(input){
  var image;
  var i;

  /* 非空校验 */
  if (input.nullable=="no" && isNull(input.value)){
    alert(input.chname+"不能为空值");
    return true;
  }

  /* 长度校验 */
  var inputSize = strlen(input.value);
  var maxSize = parseInt(input.maxsize);
  var minSize = parseInt(input.minsize);
  if (inputSize>maxSize){
    alert(input.chname + "，最大长度为 "+ maxSize +" 字符");
    return true;
  }
  if (minSize !=0 && inputSize<minSize){
    alert(input.chname + "，最小长度为 "+ minSize +" 字符");
    return true;
  }

   /* 数据类型校验 */
  switch(input.datatype){
	case "int": 
	  if (checkInteger(input.value)==false){
		alert(input.chname+" 应该为int类型");
		return true;
	  }
	  break;
	case "float": 
	  if (checkFloat(input.value)==false){
		alert(input.chname+" 应该为float类型");
		return true;
	  }
	  break;  
	case "hour":
	  if (isHour(input.value)==false){
		alert(input.chname+"值应该全为0-23的数字");
		return true;
	  }
	  break;
	case "minute":
	  if (isMinute(input.value)==false){
		alert(input.chname+"值应该全为0-59的数字");
		return true;
	  }
	  break;  
	case "email":
	  if (isEmail(input.value)==false){
		alert(input.chname+"值应该为Email类型");
		return true;
	  }
	  break;
	case "date":
	  if(isNull(input.value))return false;
	  if (IsDate(input.value)==false){
		alert(input.chname+"值应该为日期类型");
		return true;
	  }
	  break;	
	case "file":
	  if(!isNull(input.value) && isFile(input.value,input.filetype)==false){
		alert(input.chname+"类型不正确");
		return true;
	  }
	  break;
	default : break;
  }	
  return false;
}

/**
 * 校验两个日期的先后 返回值： 如果其中有一个日期为空，校验通过, 返回false 如果起始日期早于终止日期，校验通过， 返回true
 * 如果起始日期晚于或等于终止日期， 返回false 参考提示信息： 起始日期不能晚于结束日期。
 */
function checkDateEarlier(strStart,strEnd)
{
    if(IsDate(strStart) == false || IsDate(strEnd) == false)
        return false;
    // 如果有一个输入为空，则通过检验
    if (( strStart == "" ) || ( strEnd == "" ))
        return false;
    var arr1 = strStart.split("-");
    var arr2 = strEnd.split("-");
    var date1 = new Date(arr1[0],parseInt(arr1[1].replace(/^0/,""),10) - 1,arr1[2]);
    var date2 = new Date(arr2[0],parseInt(arr2[1].replace(/^0/,""),10) - 1,arr2[2]);
    if(arr1[1].length == 1)
        arr1[1] = "0" + arr1[1];
    if(arr1[2].length == 1)
        arr1[2] = "0" + arr1[2];
    if(arr2[1].length == 1)
        arr2[1] = "0" + arr2[1];
    if(arr2[2].length == 1)
        arr2[2]="0" + arr2[2];
    var d1 = arr1[0] + arr1[1] + arr1[2];
    var d2 = arr2[0] + arr2[1] + arr2[2];
    if(parseInt(d1,10) < parseInt(d2,10))
       return true;
    else
       return false;
}


/* 检测指定FORM表单所有应被检测的元素（那些具有自定义属性的元素）是否合法，此函数用于表单的onsubmit事件 */
function verifyAll(myform){
  var i;

  for (i=0;i<myform.elements.length;i++){
    /* 非自定义属性的元素不予理睬 */
    if (myform.elements[i].chname+""=="undefined") continue;

    /* 校验当前元素 */	
    if (verifyInput(myform.elements[i])){
      myform.elements[i].focus()      
      return false;
    }
  }
  return true;
}

/** css增加事件* */
function suckerfish(type, tag, parentId) {
	if (window.attachEvent) {
		window.attachEvent("onload", function() {
			var sfEls = (parentId==null)?document.getElementsByTagName(tag):document.getElementById(parentId).getElementsByTagName(tag);
			type(sfEls);
			// 自动调整列表宽度
			if(document.getElementById("listDiv") && document.getElementById("screen")){
			  document.getElementById("listDiv").style.width=document.getElementById("screen").clientWidth;
			}
		});
	}
}

sfFocus = function(sfEls) {
	for (var i=0; i<sfEls.length; i++) {
		sfEls[i].onfocus=function() {
			this.className+=" sffocus";
			/*
			 * if(this.component=="currency"){//货币类型 parseNum(this); }
			 */
		}
		sfEls[i].onblur=function() {
			this.className=this.className.replace(new RegExp(" sffocus\\b"), "");
			/*
			 * if(this.component=="currency"){//货币类型 parseMoney(this); }
			 */
		}
	};
}

sfTarget = function(sfEls) {
	var aEls = document.getElementsByTagName("A");
	document.lastTarget = null;
	for (var i=0; i<sfEls.length; i++) {
		if (sfEls[i].id) {
			if (location.hash==("#" + sfEls[i].id)) {
				sfEls[i].className+=" sftarget";
				document.lastTarget=sfEls[i];
			}
			for (var j=0; j<aEls.length; j++) {
				if (aEls[j].hash==("#" + sfEls[i].id)) {
					aEls[j].targetEl = sfEls[i];
					aEls[j].onclick = function() {
						if (document.lastTarget) document.lastTarget.className = document.lastTarget.className.replace(new RegExp(" sftarget\\b"), "");
						this.targetEl.className+=" sftarget";
						document.lastTarget=this.targetEl;
						return true;
					}
				};
			}
		}
	}
}

suckerfish(sfFocus, "INPUT");
suckerfish(sfFocus, "TEXTAREA");


/**
 * --------------------------------------------------------------------- 修改人：kxr
 * 修改日期:2010-06-21 描述：原来的不可能输入负数，主要修改了对负数输入的准输
 * ---------------------------------------------------------------------
 */
function LimitNumberInput(){
	if (!(((window.event.keyCode >= 48) && (window.event.keyCode <= 57) ) || (window.event.keyCode == 13) ||
			  (window.event.keyCode == 45) || (window.event.keyCode == 46)) 
	){
		window.event.keyCode = 0 ;  
		    
	} else {
		var evtObj = window.event.srcElement;	  
		var rep = /^(-?)[0-9]+(\.?)[0-9]*$/;
		if(!rep.test(evtObj.value)){
			rep = /^(-?)[0-9]+(\.?)[0-9]*/;
		    var v = evtObj.value;
		    var s = v.match(rep);
		    if(s){
		    	evtObj.value = s[0];
		    }else{
		    	if(v != "-")
		    		evtObj.value = "";
		    }
		    v = null, s = null;
		}
		rep = null;
	}
} 

/*
 * parent为父对象如form tag为类型，如input
 */
function attachLimitNumEvent(tag, parent){
  if (window.attachEvent) {
    window.attachEvent("onload", function() {
	  var e = (parent==null)? document.getElementsByTagName(tag):document.getElementById(parent).getElementsByTagName(tag);
	  for (var i=0; i<e.length; i++) {
	    if(e[i].datatype=="int" || e[i].datatype=="float"){
		  e[i].attachEvent('onkeypress', LimitNumberInput);
		  e[i].style.imeMode="disabled";
		}
	  }
	});
  }
}
attachLimitNumEvent("INPUT");


// 数字四舍五入, 直接用toFixed()
Number.prototype.toFixed=function(n){
  with(Math) return round(Number(this)*pow(10, n))/pow(10, n);
  /*
	 * var add = 0; var s, temp; var s1 = this + ""; var start =
	 * s1.indexOf("."); if(s1.substr(start+len+1,1)>=5) add=1; var temp =
	 * Math.pow(10,len); s = Math.floor(this * temp) + add; return s/temp;
	 */
}

function parseCurrency(num){
  num = num+"";
  var  re=/(-?\d+)(\d{3})/;
  while(re.test(num)){
    num=num.replace(re,"$1,$2");
  }
  return num;
}
function parseMoney(obj){
  obj.currency=obj.value;
  obj.value=parseCurrency(obj.value);
}
function parseNum(obj){
  obj.value=obj.currency;
};