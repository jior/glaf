/******************************************************/
/* �ļ�����univerify.js                               */
/* ��  �ܣ������Զ������Ե�ͳһ�����Javascript������ */
/******************************************************/

/*
================================================================================
	����: �Ƿ�Ϊ��������
================================================================================
*/
function IsPost(str){
	var regExp = /^[1-9]\d{5}$/g;
	return regExp.test(str);
}

/*
================================================================================
	����: �Ƿ�Ϊ�绰����
================================================================================
*/
function IsPhone(str){
	var regExp = /^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}$/g;
	return regExp.test(str);
}

/*
================================================================================
	����: �Ƿ�Ϊ�ֻ�����
================================================================================
*/
function IsMobile(str){
	var regExp = /^((\(\d{3}\))|(\d{3}\-))?13\d{9}$/g;
	return regExp.test(str);
}

/*
================================================================================
	����: �Ƿ�Ϊ�����ַ�
================================================================================
*/
function IsChinese(str){
	var regExp = /^[\u0391-\uFFE5]+$/g;
	return regExp.test(str);
}

/*
================================================================================
	����: �Ƿ�Ϊ����(yyyy[/-]mm[/-]dd)
================================================================================
*/
function IsDate(str){
	var regExp = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/g;
	return regExp.test(str);
}

/*
================================================================================
	����: �Ƿ�Ϊ����(yyyy[-]mm[-]dd)
================================================================================
*/
function IsDate(str,type){
	var regExp = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/g;
	if(type == "-")regExp = /^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/g;
	return regExp.test(str);
}
/*
================================================================================
	����: �����ַ�������
	����: minLen ��С����, maxLen ��󳤶�(0����û������)
	����ֵ: boolean
================================================================================
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
================================================================================
	����: �Ƿ�Ϊ������ϴ��ļ�����
	����: limitTypeΪ������ļ����ͱ���(��,�ָ�).��:limitType = "gif,jpg,bmp";
	����ֵ: boolean
================================================================================
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
================================================================================
	XMLEncode(string):���ַ�������XML����.
================================================================================
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
================================================================================
	CheckAll(obj):��ѡ��ȫѡ.
================================================================================
*/
function CheckAll(obj){
	var obj = document.getElementsByName(obj);
	for(var i = 0; i < obj.length; i++){
		obj.item(i).checked = true;
	}
}

/*
================================================================================
	CheckNone(obj):��ѡ��ȫ��ѡ.
================================================================================
*/
function CheckNone(obj){
	var obj = document.getElementsByName(obj);
	for(var i = 0; i < obj.length; i++)
	{
		obj.item(i).checked = false;
	}
}

/*
================================================================================
	CheckItem(obj):��ѡ��ѡ.
================================================================================
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
================================================================================
 * �ж������Ƿ���ָ�����ڵ�δ��������.
 * @param Date appDate ��ʼ����.
 * @param Date futDate ��������.
 * @param int mixDays	��������.
 * @param int maxDays	�������.
================================================================================
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

/* ȡ���ַ������ֽڳ��� */
function strlen(str){
  var i;
  var len = 0;
  
  for (i=0;i<str.length;i++){
    if (str.charCodeAt(i)>255) len+=2; else len++;
  }
  return len;
}

/* 
����ַ����Ƿ�Ϊ�� 
*/
function isNull(str){
  var i;
    
  for (i=0;i<str.length;i++){
    if (str.charAt(i)!=' ') return false;
  }
  return true;
}

/* 
����ַ����Ƿ�ȫΪ���� 
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
У���Ƿ�ΪInteger�͵�����,���ص�ֵΪ��ͼ�,true��false
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
У���Ƿ�Ϊfloat�͵�����,���ص�ֵΪ��ͼ�,true��false;
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
�Ƿ�Сʱ���ָ�ʽ 
*/
function isHour(str){
  if(!isNumber(str)) return false;  
  var value=parseInt(str);
  if(value<0 || value>23) return false;
  return true;   
}
/* 
�Ƿ�������ָ�ʽ 
*/
function isMinute(str){
  if(!isNumber(str)) return false;  
  var value=parseInt(str);
  if(value<0 || value>59) return false;
  return true;   
}
/*
У���Ƿ�Ϊemail�͵�����,���ص�ֵΪ��ͼ�,true��false;
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
�ж��Ƿ����������ַ���
�÷���isDate("2002-1-31")
*/
function isDate(str){ 
  var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/; 
  var r = str.match(reg); 
  if(r==null)return false; 
  var d= new Date(r[1], r[3]-1,r[4]); 
  var newStr=d.getFullYear()+r[2]+(d.getMonth()+1)+r[2]+d.getDate() 
  return (newStr==str);
} 
/*
�ж��Ƿ����������ַ���
�÷���isDateTime("2002-1-31 12:34:56")
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
�ж������Ƿ���ȷ
�÷���chkDate(2002-4-16)
*/
function chkDate(sDate){ 
  var r=/\d{4}(?:-\d{1,2}){0,2}/ 
  //������ʽ���ж��Ƿ�Ϊyyyy-mm-dd,yyyy-mm,yyyy��ʽ 
  if(sDate.match(r)==sDate){ 
��  var arr=sDate.split("-") 
��  switch(arr.length){ 
��  //���ݲ�ͬ��yyyy-mm-dd,yyyy-mm��ʽ�ж������������Ƿ���ȷ 
��   case 3: 
����   var tmpDate=new Date(arr[0],arr[1],arr[2]); 
����   if(tmpDate.getMonth()==arr[1] && tmpDate.getFullYear()==arr[0]) return true; 
����   break; 
��   case 2: 
����   if(arr[1]<13) return true; 
����   break; 
��   default: 
����   return false; 
��  } 
  } 
  return false; 
} 
/*
�ж�����DateOne�Ƿ�������DateTwo����
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
 * �ж������Ƿ���ָ�����ڵ�δ��������.
 * @param Date appDate �ο�����.
 * @param Date futDate ��������.
 * @param int days	����.
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
 *�ж��ļ������Ƿ���ȷ
 */
function isFile(file, fileType){
  var arr = fileType.split(",");  
  var start = file.lastIndexOf(".");
  var end = file.length;
  
  if(start>0){
    var ext = file.substring(start+1, end);
	ext = ext.toLowerCase( );
	//alert(ext + ":" + fileType);
    for(var i=0;i<arr.length;i++){
	  //alert(arr[i]+":"+ext);
      if(arr[i]==ext){//����ƥ��
		  return true;
		  break;  
	  }
	}
  }
  return false;
}

/* 
*���ָ���ı��������Ƿ�Ϸ�
* true����ʧ�ܣ�false�ɹ� 
*/
function verifyInput(input){
  var image;
  var i;

  /* �ǿ�У�� */
  if (input.nullable=="no" && isNull(input.value)){
    alert(input.chname+"����Ϊ��ֵ");
    return true;
  }

  /* ����У�� */
  var inputSize = strlen(input.value);
  var maxSize = parseInt(input.maxsize);
  var minSize = parseInt(input.minsize);
  if (inputSize>maxSize){
    alert(input.chname + "����󳤶�Ϊ "+ maxSize +" �ַ�");
    return true;
  }
  if (minSize !=0 && inputSize<minSize){
    alert(input.chname + "����С����Ϊ "+ minSize +" �ַ�");
    return true;
  }

   /* ��������У�� */
  switch(input.datatype){
	case "int": 
	  if (checkInteger(input.value)==false){
		alert(input.chname+" Ӧ��Ϊint����");
		return true;
	  }
	  break;
	case "float": 
	  if (checkFloat(input.value)==false){
		alert(input.chname+" Ӧ��Ϊfloat����");
		return true;
	  }
	  break;  
	case "hour":
	  if (isHour(input.value)==false){
		alert(input.chname+"ֵӦ��ȫΪ0-23������");
		return true;
	  }
	  break;
	case "minute":
	  if (isMinute(input.value)==false){
		alert(input.chname+"ֵӦ��ȫΪ0-59������");
		return true;
	  }
	  break;  
	case "email":
	  if (isEmail(input.value)==false){
		alert(input.chname+"ֵӦ��ΪEmail����");
		return true;
	  }
	  break;
	case "date":
	  if(isNull(input.value))return false;
	  if (IsDate(input.value)==false){
		alert(input.chname+"ֵӦ��Ϊ��������");
		return true;
	  }
	  break;	
	case "file":
	  if(!isNull(input.value) && isFile(input.value,input.filetype)==false){
		alert(input.chname+"���Ͳ���ȷ");
		return true;
	  }
	  break;
	default : break;
  }	
  return false;
}

/**
*У���������ڵ��Ⱥ�
*����ֵ��
*���������һ������Ϊ�գ�У��ͨ��,          ����false
*�����ʼ����������ֹ���ڣ�У��ͨ����   ����true
*�����ʼ�������ڻ������ֹ���ڣ�                 ����false    �ο���ʾ��Ϣ�� ��ʼ���ڲ������ڽ������ڡ�
*/
function checkDateEarlier(strStart,strEnd)
{
    if(IsDate(strStart) == false || IsDate(strEnd) == false)
        return false;
    //�����һ������Ϊ�գ���ͨ������
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


/* ���ָ��FORM������Ӧ������Ԫ�أ���Щ�����Զ������Ե�Ԫ�أ��Ƿ�Ϸ����˺������ڱ���onsubmit�¼� */
function verifyAll(myform){
  var i;

  for (i=0;i<myform.elements.length;i++){
    /* ���Զ������Ե�Ԫ�ز������ */
    if (myform.elements[i].chname+""=="undefined") continue;

    /* У�鵱ǰԪ�� */	
    if (verifyInput(myform.elements[i])){
      myform.elements[i].focus()      
      return false;
    }
  }
  return true;
}

/**css�����¼�**/
function suckerfish(type, tag, parentId) {
	if (window.attachEvent) {
		window.attachEvent("onload", function() {
			var sfEls = (parentId==null)?document.getElementsByTagName(tag):document.getElementById(parentId).getElementsByTagName(tag);
			type(sfEls);
			//�Զ������б���
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
			/*if(this.component=="currency"){//��������
			  parseNum(this);
			}*/
		}
		sfEls[i].onblur=function() {
			this.className=this.className.replace(new RegExp(" sffocus\\b"), "");
			/*if(this.component=="currency"){//��������
			  parseMoney(this);
			}*/
		}
	}
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
				}
			}
		}
	}
}

suckerfish(sfFocus, "INPUT");
suckerfish(sfFocus, "TEXTAREA");


/**
 * ---------------------------------------------------------------------
 * �޸��ˣ�kxr
 * �޸�����:2010-06-21
 * ������ԭ���Ĳ��������븺������Ҫ�޸��˶Ը��������׼��
 * ---------------------------------------------------------------------
 **/
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
 *parentΪ��������form
 *tagΪ���ͣ���input
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


//������������, ֱ����toFixed()
Number.prototype.toFixed=function(n){
  with(Math) return round(Number(this)*pow(10, n))/pow(10, n);
  /*
  var add = 0;
  var s, temp;
  var s1 = this + "";
  var start = s1.indexOf(".");
  if(s1.substr(start+len+1,1)>=5) add=1;
  var temp = Math.pow(10,len);
  s = Math.floor(this * temp) + add;
  return s/temp;
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
}