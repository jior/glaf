/**
 * Filter ������(��ѯ����)
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
	 *���ܣ����ù���������
	 * @param name ������
	 * @param value ����ֵ
	 * @return
	 */
	setAttribute : function(name,value){
		this.root.setAttribute(name,value);
	},
	/**
	 * ���ܣ���ȡ����������ֵ
	 * @param name ������
	 * @return
	 */
	getAttribute : function(name) {
		return this.root.getAttribute(name);
	},
	/**
	 * ���ܣ�ɾ������������
	 * @param name ����������
	 * @return
	 */
	removeAttribute : function(name){
		return this.root.removeAttribute(name);
	},
	/**
	 * ���ܣ���ӹ�������
	 * @param nam  ������
	 * @param value ������ֵ 
	 * @param opt  ������ƥ������[= ����, %ֵƥ���]
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
	 * ���ܣ�����������ֵ
	 * @param name  ������
	 * @param value ������ֵ 
	 * @param opt ������ƥ������[= ����, %ֵƥ���]
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
	 * ���ܣ��Ƴ���������
	 * @param name ������
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
	 * ���ܣ����������������
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
	 * ���ܣ����ù��˽������ʽ
	 * @param col ������(��)
	 * @param sort ����ʽ[desc...]
	 * @return
	 */
	setSort : function(col, sort) {
		this.root.setAttribute("sort", col + " " + sort);
		this.sortable = true;
	},
	/**
	 * ���ܣ����ú�̨��ѯ�ؼ���(���Ӧ��Hibernate��SqlQueryʱ�õ�)
	 * @param qkey  ��ѯ�ؼ���
	 * @return
	 */
	setQueyKey : function(qkey) {
		this.root.setAttribute("qkey",qkey);
	},
	/**
	 * ���ܣ����ù�����Ŀ(��)����ϸ����(�ӱ�)
	 * @param dtl  ��ϸ������(�ӱ�����)
	 * @return
	 */
	setDtl : function(dtl) {
		this.root.setAttribute("dtlname", dtl);
	},
	/**
	 * ���ܣ�������˽����ҳ����(Ҫ��ʾ��ҳ�룬��Ҫ���ݸ�����ˣ�ǰ̨���ݸ�����ʾ)
	 * @param pageno  ҳ��
	 * @return
	 */
	setPageNo : function(pageno) {
		this.root.setAttribute("pageNo",pageno);
	},
	/**
	 * ���ܣ�������˷�ҳÿҳ��¼��(��θ��ݸ�����ҳ��ǰ�θ��ݸ�����ʾ��¼)
	 * @param pagesize  ÿҳ��¼��
	 * @return
	 */
	setPageSize : function(pagesize) {
		this.root.setAttribute("pageSize",pagesize);
	},
	/**
	 * ���ܣ�������������ύ��������Ҫ������
	 * @return
	 * ����˵����	fn[�ص�����]
	 * 				reqtype[�������]
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
	 * ���ܣ�ͬtxSend
	 * @param v  �ع鴦����
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
	 * ���ܣ����������̨������һ��ҳ����
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
	 * ���ܣ������������������һ��ҳ����
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
	 * ���ܣ����������̨�����һ��ҳ����
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
	 * ���ܣ����������̨�������һ��ҳ����
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
	 * ���ܣ���ȡ���������˽����ҳ��Ϣ
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