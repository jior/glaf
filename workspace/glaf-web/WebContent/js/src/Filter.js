/**
 * Filter 过滤器(查询条件)
 * created by kxr 20090319
 */
function Filter(config, type)
{
	try {
		this.sortable = false;
		this.msg = {type : "",description:"",excode : "", source:""};
		this.doc = null;
		this.root = null;
		this.xRs = null;
		
		for(var name in config)
			this[name] = config[name];	
		
		this.doc = DocHelper.newDoc();
		this.doc.loadXML("<filter/>");
		this.root = this.doc.documentElement;
		if(this.objName)
			this.root.setAttribute("objName", this.objName);
		else 
			GzkEx.doThrow(100, "Please set the objName for Filter!");
		this.root.setAttribute("mtdName",(this.mtdName)?this.mtdName:"getList");
		if(this.qkey) this.root.setAttribute("qkey", this.qkey);
		if(this.url) this.root.setAttribute("url", this.url);
		this.root.setAttribute("pageNo", (this.pageNo)?this.pageNo:DEF_PAGENO);
		this.root.setAttribute("pageSize",(this.pageSize)?this.pageSize:DEF_PAGESIZE);
		if(this.sortcol) this.setSort(this.sortcol, (this.order)?this.order : OD_ASC);
		
	}catch(ex){
		alert(ex.description);
	}
};

Filter.prototype = {
	/**
	 *功能：设置过滤器属性
	 * @param name 属性名
	 * @param value 属性值
	 * @return
	 */
	setAttribute : function(name,value){
		this.root.setAttribute(name,value);
	},
	/**
	 * 功能：获取过滤器属性值
	 * @param name 属性名
	 * @return
	 */
	getAttribute : function(name) {
		return this.root.getAttribute(name);
	},
	/**
	 * 功能：删除过滤器属性
	 * @param name 过滤属性名
	 * @return
	 */
	removeAttribute : function(name){
		return this.root.removeAttribute(name);
	},
	/**
	 * 功能：添加过滤条件
	 * @param nam  条件项
	 * @param value 条件项值 
	 * @param opt  条件项匹配类型[= 等于, %值匹配等]
	 * @return
	 */
	addFilter : function(name, value, opt) {
		try{
			var field = this.doc.createElement("field");
			field.setAttribute("name",name);
			field.setAttribute("value",value);
			field.setAttribute("opt",opt?opt:"=");
			
			this.root.appendChild(field);
		}catch(ex){
		}
	},
	/**
	 * 功能：设置条件项值
	 * @param name  条件项
	 * @param value 条件项值 
	 * @param opt 条件项匹配类型[= 等于, %值匹配等]
	 * @return
	 */
	setFilter : function(name, value, opt){
		try{
			var field = this.doc.selectSingleNode("//field[@name='" + name + "']");
			if(field) {
				field.setAttribute("name",name);
				field.setAttribute("value",value);
				field.setAttribute("opt",opt?opt:"=");
			} else {
				this.addFilter(name, value, opt);
			}
		}catch(ex){
		}
	},
	/**
	 * 功能：移除过滤条件
	 * @param name 条件项
	 * @return
	 */
	removeFilter : function(name) {
		try{
			var field = this.doc.selectSingleNode("//field[@name='" + name + "']");
			if(field) {
				this.root.removeChild(field);
			}
		}catch(ex){
		}
	},
	/**
	 * 功能：清除过滤器条件项
	 * @return
	 */
	cleanFilter : function() {
		try{
			while(this.root.lastChild) {
				this.root.removeChild(this.root.lastChild);
			}
		}catch(ex){
		}
	},
	/**
	 * 功能：设置过滤结果排序方式
	 * @param col 排序项(列)
	 * @param sort 排序方式[desc...]
	 * @return
	 */
	setSort : function(col, sort) {
		this.root.setAttribute("sort", col + " " + sort);
		this.sortable = true;
	},
	/**
	 * 功能：设置后台查询关键字(后参应用Hibernate的SqlQuery时用到)
	 * @param qkey  查询关键字
	 * @return
	 */
	setQueyKey : function(qkey) {
		this.root.setAttribute("qkey",qkey);
	},
	/**
	 * 功能：设置过滤项目(表)的明细关联(从表)
	 * @param dtl  明细关联体(从表名称)
	 * @return
	 */
	setDtl : function(dtl) {
		this.root.setAttribute("dtlname", dtl);
	},
	/**
	 * 功能：保存过滤结果分页号码(要显示的页码，后要根据该码过滤，前台根据该码显示)
	 * @param pageno  页码
	 * @return
	 */
	setPageNo : function(pageno) {
		this.root.setAttribute("pageNo",pageno);
	},
	/**
	 * 功能：保存过滤分页每页记录数(后参根据该数分页，前参根据该数显示记录)
	 * @param pagesize  每页记录数
	 * @return
	 */
	setPageSize : function(pagesize) {
		this.root.setAttribute("pageSize",pagesize);
	},
	/**
	 * 功能：过滤器向后右提交过滤条件要求数据
	 * @return
	 * 参数说明：	fn[回调函数]
	 * 				reqtype[请求参数]
	 */
	txSend : function(fn, reqtype) {
		try{
			this.xRs = TxService.postXML(this.doc, fn, reqtype);
			
			var msg = this.xRs.selectSingleNode("//msg");
			if(msg) {
				this.msg.description = msg.getAttribute("description");
				this.msg.excode = msg.getAttribute("number");
				this.msg.source = msg.getAttribute("source");
				this.msg.type = msg.getAttribute("msgtype");
				
				GzkEx.responseException(this.msg);
			}
			return this.xRs;
		}catch(ex){
			alert(ex.description);
		}
	},
	/**
	 * 功能：同txSend
	 * @param v  回归处理方法
	 * @return
	 */
	send : function(v) {
		try{
			if(!v) v = {};
			this.txSend();
			
			var msg = this.msg;			
		}catch(ex){
			
		}
	},
	/**
	 * 功能：过滤器向后台请求下一分页数据
	 * @return
	 */
	nextPage : function()
	{
		var data = this.xRs.selectSingleNode("//rs:data");
		var count = parseInt(data.getAttribute("pageCount"));
		if(!count)
			return false;
		var no = parseInt(data.getAttribute("pageNo"));
		if(no < count) {
			this.setPageNo(++no);
		} else if(count == no)
			this.setPageNo(1);
		
		return this.txSend();	
	},
	/**
	 * 功能：过滤器向后右请求上一分页数据
	 * @return
	 */
	previousPage : function()
	{
		var data = this.xRs.selectSingleNode("//rs:data");
		var count = parseInt(data.getAttribute("pageCount"));
		if(!count)
			return false;
		var no = parseInt(data.getAttribute("pageNo"));
		if(no >1) {
			this.setPageNo(--no);
		} else if(count == 1)
			this.setPageNo(count);
		
		return this.txSend();	
	},
	/**
	 * 功能：过滤器向后台请求第一分页数据
	 * @return
	 */
	firstPage : function()
	{
		var data = this.xRs.selectSingleNode("//rs:data");
		var count = parseInt(data.getAttribute("pageCount"));
		if(!count)
			return false;
		this.setPageNo(1);
		
		return this.txSend();
	},
	/**
	 * 功能：过滤器向后台请求最后一分页数据
	 * @return
	 */
	lastPage : function(){
		var data = this.xRs.selectSingleNode("//rs:data");
		var count = parseInt(data.getAttribute("pageCount"));
		if(!count)
			return false;
		this.setPageNo(count);
		
		return this.txSend();
	},
	/**
	 * 功能：获取过滤器过滤结果分页信息
	 * @return
	 */
	getPageInfo : function()
	{
		var data = this.xRs.selectSingleNode("//rs:data");
		var rcount = parseInt(data.getAttribute("recordCount"));
		if(!rcount)
			return false;
		var pcount = data.getAttribute("pageCount");
		var pageno = data.getAttribute("pageNo");
		
		return {pNO : pageno,rCount : rcount, pCount : pcount};
	}
};