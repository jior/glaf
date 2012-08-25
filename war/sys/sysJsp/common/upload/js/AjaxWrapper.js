//类工具
var ClassUtils=Class.create();
ClassUtils.prototype={
	_ClassUtilsName:'ClassUtils',
	initialize:function(){
	},
	/**
	 * 给类的每个方法注册一个对类对象的自我引用
	 * @param reference 对类对象的引用
	 */
	registerFuncSelfLink:function(reference){
		for (var n in reference) {
        	var item = reference[n];                        
        	if (item instanceof Function) 
				item.$ = reference;
    	}
	}
}
//Ajax操作封装类：
//由于调用AjaxRequest类进行XMLHTTPRequest操作时，this引用（指向当前的对象）会出现了call stack问题，从而指向当前的对象。
//所以，对putRequest、callBackHandler、以及callback方法都要使用arguments.callee.$来获得正确的类对象引用
var AjaxWrapper=Class.create();
AjaxWrapper.prototype={
	debug_flag:false,
	xml_source:'',
	/**
	 * 初始化
	 * @param isDebug 是否显示调试信息
	 */
	initialize:function(isDebug){
		new ClassUtils().registerFuncSelfLink(this);
		this.debug_flag=isDebug;
		
	},
	/**
	 * 以get的方式向server发送request
	 * @param url
	 * @param params
	 * @param callBackFunction 发送成功后回调的函数或者函数名
	 */
	putRequest:function(url,params,callBackFunction){
		var funcHolder=arguments.callee.$;
	    var xmlHttp = new Ajax.Request(url,
			{
				method: 'get', 
	    		parameters: params, 
				requestHeaders:['my-header-encoding','UTF-8'],
	    		onFailure: function(){
					//alert('对不起，网络通讯失败，请重新刷新！');
				},
				onSuccess: function(transport){
				},
				onComplete: function(transport){
					funcHolder.callBackHandler.apply(funcHolder,[transport,callBackFunction]);
				}
			});
	},
	/**
	 * 以post的方式向server发送xml请求
	 * @param url
	 * @param postDataBody
	 * @param callBackFunction 发送成功后回调的函数或者函数名
	 */
	pushRequest:function(url,postDataBody,callBackFunction){
		var funcHolder=arguments.callee.$;
		var options={
				method: 'post', 
		    	parameters:'',
		    	requestHeaders:['my-header-encoding','UTF-8'],
		    	postBody: postDataBody,
				onFailure: function(transport){
					//alert('对不起，网络通讯失败，请重新发送！');
				},
				onComplete: function(transport){
					funcHolder.callBackHandler.apply(funcHolder,[transport,callBackFunction]);
				}
			};
		var xmlHttp = new Ajax.Request(url,options);
	},
	/**
	 * 远程调用的回调处理
	 * @param transport xmlhttp的transport
	 * @param callBackFunction 回调时call的方法，可以是函数也可以是函数名
	 */
	callBackHandler:function(transport,callBackFunction){
		var funcHolder=arguments.callee.$;
		if(transport.status!=200){
			//alert("获得回应失败,请求状态:"+transport.status);
		}
		else{
			funcHolder.xml_source=transport.responseText;
			if (funcHolder.debug_flag)
				alert('call callback function');
			if (typeof(callBackFunction)=='function'){
				if (funcHolder.debug_flag){
					alert('invoke callbackFunc');
				}
				callBackFunction(transport.responseText);
			}
			else{
				if (funcHolder.debug_flag){
					alert('evalFunc callbackFunc');
				}
				new execute().evalFunc(callBackFunction,transport.responseText);
			}
			if (funcHolder.debug_flag)
				alert('end callback function');
		}
	},
	//显示xml信息
	showXMLResponse:function(){
		var funcHolder=arguments.callee.$;
		alert(funcHolder.xml_source);
	}
}

var XMLDomForAjax=Class.create();
XMLDomForAjax.prototype={
	isDebug:false,
	//dom节点类型常量
	ELEMENT_NODE:1,
	ATTRIBUTE_NODE:2,
    TEXT_NODE:3,
    CDATA_SECTION_NODE:4,
    ENTITY_REFERENCE_NODE:5,
    ENTITY_NODE:6,
    PROCESSING_INSTRUCTION_NODE:7,
    COMMENT_NODE:8,
    DOCUMENT_NODE:9,
    DOCUMENT_TYPE_NODE:10,
    DOCUMENT_FRAGMENT_NODE:11,
    NOTATION_NODE:12,
    
	initialize:function(isDebug){
		new ClassUtils().registerFuncSelfLink(this);
		this.isDebug=isDebug;
	},
	/**
	 * 建立跨平台的dom解析器
	 * @param xml xml字符串
	 * @return dom解析器
	 */
	createDomParser:function(xml){
		// code for IE
		if (window.ActiveXObject){
		  var doc=new ActiveXObject("Microsoft.XMLDOM");
		  doc.async="false";
		  doc.loadXML(xml);
		}
		// code for Mozilla, Firefox, Opera, etc.
		else{
		  var parser=new DOMParser();
		  var doc=parser.parseFromString(xml,"text/xml");
		}
		return doc;
	},
	/**
	 * 反向序列化xml到javascript Bean
	 * @param xml xml字符串
	 * @return javascript Bean
	 */
	deserializedBeanFromXML:function (xml){
		var funcHolder=arguments.callee.$;
		var doc=funcHolder.createDomParser(xml);
		// documentElement总表示文档的root
		var objDomTree=doc.documentElement;
		var obj=new Object();
	    for (var i=0; i<objDomTree.childNodes.length; i++) {
	    	//获得节点
	    	var node=objDomTree.childNodes[i];
	    	//取出其中的field元素进行处理
	        if ((node.nodeType==funcHolder.ELEMENT_NODE) && (node.tagName == 'field')) {
	        	var nodeText=funcHolder.getNodeText(node);
	        	if (funcHolder.isDebug){
		            alert(node.getAttribute('name')+' type:'+node.getAttribute('type')+' text:'+nodeText);
	        	}
	            var objFieldValue=null;
	            //如果为列表
	            if (node.getAttribute('type')=='java.util.List'){
	            	if (objFieldValue && typeof(objFieldValue)=='Array'){
		            	if (nodeText.length>0){
							objFieldValue[objFieldValue.length]=nodeText;
						}
					}
					else{
						objFieldValue=new Array();
					}
				}
				else if (node.getAttribute('type')=='long' 
					|| node.getAttribute('type')=='java.lang.Long'
					|| node.getAttribute('type')=='int'
					|| node.getAttribute('type')=='java.lang.Integer'){
					
					objFieldValue=parseInt(nodeText);
				}
				else if (node.getAttribute('type')=='double' 
					|| node.getAttribute('type')=='float'
					|| node.getAttribute('type')=='java.lang.Double'
					|| node.getAttribute('type')=='java.lang.Float'){
					
					objFieldValue=parseFloat(nodeText);
				}
				else if (node.getAttribute('type')=='java.lang.String'){
					objFieldValue=nodeText;
				}
				else{
					objFieldValue=nodeText;
				}
				//赋值给对象
				obj[node.getAttribute('name')]=objFieldValue;
				if (funcHolder.isDebug){
					alert(eval('obj.'+node.getAttribute('name')));
				}
	        }
	        else if (node.nodeType == funcHolder.TEXT_NODE){
	        	if (funcHolder.isDebug){
			        //alert('TEXT_NODE');
	        	}
		        
	        }
	        else if (node.nodeType == funcHolder.CDATA_SECTION_NODE){
	        	if (funcHolder.isDebug){
		        	//alert('CDATA_SECTION_NODE');
	        	}
	        }
	    }
	    return obj;
	},
	/**
	 * 获得dom节点的text
	 */
	getNodeText:function (node) {
		var funcHolder=arguments.callee.$;
	    // is this a text or CDATA node?
	    if (node.nodeType == funcHolder.TEXT_NODE || node.nodeType == funcHolder.CDATA_SECTION_NODE) {
	        return node.data;
	    }
	    var i;
	    var returnValue = [];
	    for (i = 0; i < node.childNodes.length; i++) {
	    	//采用递归算法
	        returnValue.push(funcHolder.getNodeText(node.childNodes[i]));
	    }
	    return returnValue.join('');
	}
}

//委托者类
var Dispatcher=Class.create();
Dispatcher.prototype={
	name:'Dispatcher',
	//对class中的每个function都赋值一个值为this的$属性
	initialize:function(){
		new ClassUtils().registerFuncSelfLink(this);
	},
	/**
	 * 委托调用
	 * @param caller 调用者，func的拥有者
	 * @param func 如果是function对象，则使用Dispatcher对象自己的name作为参数；否则直接调用func
	 */
	dispatch:function(caller,func){
		if (func instanceof Function){
			var funcArguments=new Array();
			funcArguments[0]=arguments.callee.$.name;
			func.apply(caller,funcArguments);
		}
		else{
			eval(func);
		}
	}
}
//祈祷者类
var Invoker=Class.create();
Invoker.prototype={
	name:'Invoker',
	initialize:function(){
	},
	invoke:function(showMsg){
		alert(showMsg+"——this.name="+this.name);
	}
}