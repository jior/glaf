/**
 * 传入form 系列化数组 
 * 返回一个对象数组{name:joy,sex:'男'}
 * @param params
 */
function getMxObjArray(params){
      var array = {};
      $.each( params , function(index){
          if(array[this['name']]){
          	array[this['name']] = array[this['name']] +","+this['value'];
          }else{
          	array[this['name']] = this['value'];
          }
      });
      return array ;
}

/**
 * 精确加法
 * @param arg1
 * @param arg2
 * @returns {Number}
 */
function accAdd(arg1,arg2){ 
	var r1,r2,m; 
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0} 
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0} 
	m=Math.pow(10,Math.max(r1,r2)) ;
	return (arg1*m+arg2*m)/m ;
} 
/**
 * 精确减法
 * @param arg1
 * @param arg2
 * @returns
 */
function Subtr(arg1,arg2){
    var r1,r2,m,n;
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2));
    n=(r1>=r2)?r1:r2;
    return ((arg1*m-arg2*m)/m).toFixed(n);
}
/**
 * 乘法
 * @param arg1
 * @param arg2
 * @returns {Number}
 */
function accMul(arg1,arg2) 
{ 
var m=0,s1=arg1.toString(),s2=arg2.toString(); 
try{m+=s1.split(".")[1].length}catch(e){} 
try{m+=s2.split(".")[1].length}catch(e){} 
return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m); 
} 

/**
 * 除法
 * @param arg1
 * @param arg2
 * @returns {Number}
 */
function accDiv(arg1,arg2){ 
var t1=0,t2=0,r1,r2; 
try{t1=arg1.toString().split(".")[1].length}catch(e){} 
try{t2=arg2.toString().split(".")[1].length}catch(e){} 
with(Math){ 
r1=Number(arg1.toString().replace(".","")) ;
r2=Number(arg2.toString().replace(".","")) ;
return (r1/r2)*pow(10,t2-t1); 
} 
} 


$.extend($.fn.validatebox.defaults.rules, {
    notNull: {
        validator: function(value, param){
    		var flag = value.trim().length>0 ;
    		var   pattern   =   /^[a-z\d\u4E00-\u9FA5]+$/i; 
    		var flag2 = pattern.test(value.trim()) ;
            return  flag && flag2;
        },
        message: '不能单独输入空格或特殊字符'
    }
})



$.extend($.fn.validatebox.defaults.rules, {
    Maxlength: {   	
        validator: function(value, param){
			if(typeof(param)=='undefined'){
				maxlength=200;
			}else{
				maxlength = param;
			}
    		var flag = value.length <= maxlength ;
            return  flag ;
        },
        message: '输入不能超过{0}位'
    }
})

$.extend($.fn.validatebox.defaults.rules, {
    notNullAndMaxLength: {
        validator: function(value, param){
    		var flag = value.trim().length>0 ;
    		var pattern   =   /^[a-z\d\u4E00-\u9FA5]+$/i; 
    		var flag2 = pattern.test(value.trim()) ;
    		var flag3 = value.length<param;
            return  flag && flag2 && flag3;
        },
        message: '输入不能超过{0}位且不能输入特殊字符'
    }
})

$.extend($.fn.validatebox.defaults.rules, {
    notNullAndLength: {
        validator: function(value, param){
    		var flag = value.trim().length>0 ;
    		var pattern   =   /^[a-z\d\u4E00-\u9FA5\、\（\）\~\/\n\ \,\.\，\。\-\(\)\[\]\{\}\*\!\:\;\'\"\“\”\|\\\；\：\<\>\《\》\?\？\【\】\｛\｝\+\-\=\—\%\￥\$\…\……\！\_\#\@\^\&]+$/i; 
    		var flag2 = pattern.test(value.trim()) ;
    		var flag3 = value.length<param;
            return  flag && flag2 && flag3;
        },
        message: '输入不能超过{0}位且不能输入特殊字符'
    }
})


$.extend($.fn.validatebox.defaults.rules, {
    isDate: {
        validator: function(value, param){
    		 if(value.trim()=="")return true;
    		 var dateType = param[0] ; 
    		 var dateTypeString = param[1] ;
    		 if(dateType==""){
    			 dateType = /^\d{4}\-{1}\d{1,2}\-{1}\d{1,2}$/;
    		 }
    		var flag = dateType.test(value.trim()) ;
            return  flag;
        },
        message: '请输入正确的时间格式：{1}'
    }
})
