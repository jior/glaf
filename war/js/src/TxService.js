var AppCode = "tms";   //项目名称

/**
 * Ajax业务处理器
 * created by kxr 20090320
 */
TxService = {
	xResult : null,
	
	/**
	 * 功能：创建Ajax异步操作xmlhttp
	 * @return
	 */
	create : function()
	{		
		if(this.xmlHttp)
			return true;
		
		if(window.XMLHttpRequest) {
			this.xmlHttp = new XMLHttpRequest();
		}
		if(!this.xmlHttp) {
			this.xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
			if(!this.xmlHttp) {
				this.xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
				if(!this.xmlHttp){
					this.xmlHttp = new ActiveXObject("Msxml2.XMLHTTP.3.0");
				}
			}
		}
		if(!this.xmlHttp) {
			alert("你的浏览器版本太低，建议您升级后开启XMLHttpRequest ActiveX功能浏览此页面");
			return false;
		}
		
		return true;
	},
	/**
	 * 功能：提交数据
	 * @param doc
	 * @return
	 */
	postXML : function(doc, fn, reqtype){
		try{
			var xResult;
			if(this.create()) {
				var reqUrl = doc.documentElement.getAttribute("url");
				if(!reqUrl) reqUrl = "/" + AppCode + "/sv";
				else reqUrl = "/" + AppCode + reqUrl;
		
				if(!fn){//同步提交
					//alert('同步提交数据');
					this.xmlHttp.open("POST", reqUrl, false);
					if(!reqtype){
						this.xmlHttp.setRequestHeader("content-type","text/xml");
					} else {
						this.xmlHttp.setRequestHeader("content-type",reqtype);
					}
					this.xmlHttp.send(doc);
					if(!reqtype){
						return this.xmlHttp.responseXML;	
					} else {
						return this.xmlHttp.responseText;
					}
				}else{	//异步提交
					//alert('异步提交数据');
					var onReadyState = (function(obj, fn, checkFn) {
						return function () {
							try {
								if (obj.readyState == 4) {
									var xRs = obj.responseXML;
									checkFn(xRs);
									if (typeof fn.ok == "string") {
										eval(fn.ok + "(xResult);");
									} else {
										fn.ok(xRs);
									}
									xRs = null;
									fn.ok = null;
									fn.err = null;
									fn = null;									
									obj = null;	
									checkFn = null;												
								}
							}catch(exception){
								alert(exception);
								if (typeof fn.err == "string") {
									eval(fn.err + "(xResult, exception);");
								} else {
									fn.err(xRs, exception);
								}
								fn.ok = null;
								fn.err = null;
								fn = null;									
								obj = null;		
								checkFn = null;												
							}
						}
					})(this.xmlHttp, fn, this.checkResult);
					this.xmlHttp.onreadystatechange = onReadyState;
					this.xmlHttp.open("POST", reqUrl, true);
					this.xmlHttp.send(doc);
					onReadyState = null;	
				}
			}	
		}catch(ex){
			throw ex;
		}
	},
	
	/**
	 * 功能：获取指定路径页面文本
	 * @param url
	 * @return
	 */
	getText : function(url, fn) {
		try{
			var xResult = url ? url : "";	
			
			if(this.create()){				
				if(!fn){//同步提交
					this.xmlHttp.open("POST", reqUrl, false);
					this.xmlHttp.send();
					xResult = this.xmlHttp.responseText;	
					this.xmlhttp = null;
					
					return xResult;
				}else{	//异步提交
					var onReadyState = (function(obj, fn, checkFn) {
						return function () {
							try {
								if (obj.readyState == 4) {
									var xRs = obj.responseXML;
									checkFn(xRs);
									if (typeof fn.ok == "string") {
										eval(fn.ok + "(xResult);");
									} else {
										fn.ok(xRs);
									}
									xRs = null;
									fn.ok = null;
									fn.err = null;
									fn = null;									
									obj = null;	
									checkFn = null;												
								}
							}catch(exception) {
								if (typeof fn.err == "string") {
									eval(fn.err + "(xResult, exception);");
								} else {
									fn.err(xRs, exception);
								}
								fn.ok = null;
								fn.err = null;
								fn = null;									
								obj = null;		
								checkFn = null;												
							}
						}
					})(XMLHttp, fn, this.checkResult);
					this.xmlHttp.onreadystatechange = onReadyState;
					this.xmlHttp.open("POST", reqUrl, true);
					this.xmlHttp.send();
					onReadyState = null;	
					this.xmlHttp = null;
					url = null;
					fn = null;
				}
				
				
				this.xmlHttp.open("POST",url,false);
				this.xmlHttp.send();
				xResult = this.xmlHttp.responseText;
			}
			
			return xResult;
		}catch(ex) {
			throw ex;
		}
	},
	
	/** =====================================================================
	 * 方法名: checkResult
	 * 创建人: kxr
	 * 创建时间: 2010-05-20
	 * 描述: 检查后台返回的XML对象是否包含自义定错误信息
	 * 参数: doc 后台返回的XML对象
	 * =====================================================================
	 */			
	checkResult: function(doc)
	{
		try {
			var root = doc.documentElement;
			var sErrNo = root.getAttribute("number");  
			if (sErrNo && (sErrNo != 0))
			{
				var sSource = "Source: " + root.getAttribute("source");
				var sErrDesc = "Reason: "+ root.getAttribute("description");
				sErrDesc = sSource + "\n" + sErrDesc;
				GzkEx.doThrow(sErrNo, sErrDesc, "TxService.checkResult");
			}
		} 
		catch(exception) {
			GzkEx.doAlert(exception, "TxService.checkResult");
		}
	}
};