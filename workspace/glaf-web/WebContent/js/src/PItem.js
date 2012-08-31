/**
 * @author suey
 * @date 2010-06-01
 */
var PItem = Class.create();
PItem.prototype = {
	area : null,
	initialize : function(config){
		if(config && typeof config== 'object')
		{
			for(var p in config) this[p] = config[p];
		}
	},
	
	
	/**
	 * =======================================================================
	 * 创建人：kxr
	 * 创建日期：2010-06-02
	 * 描述：当填写是下拉选择框时，使用一个隐藏的input来保存其值。当保存页面填写的数据时，先
	 *      设置好下拉选择框对应的input的值再保存
	 * 参数： 无
	 * =======================================================================
	 */
	saveSelect : function(){
		var seles = this.area.getElementsBySelector('[id="ctlSelect"]');
		var sLen = seles.length;
		var sele;
		for(var i = 0; i < sLen; i++){
			this.vSele(seles[i]);
		}
	},
	
	/**
	 * =======================================================================
	 * 创建人：kxr
	 * 创建日期：2010-06-02
	 * 描述：当填写是下拉选择框时，使用一个隐藏的input来保存其值。
	 * 参数： sele  事件源，即下拉选择框
	 * =======================================================================
	 */
	vSele : function(sele){
		var icon = sele.nextSibling;
		if(!icon || icon.tagName.toLowerCase() != "input") return;
		
		icon.value = sele.value;
	},
	/** 
	 * =======================================================================
	 * 创建人: kxr
	 * 创建时间: 2009-09-24
	 * 描述: 获取当前的操作状态
	 * 参数：当前操作模式【add:添加  mod:修改  del:删除  default:无操作，即查询】
	 * =======================================================================
	 */		
	getEdtFlag: function(mode)
	{
		switch(mode) {
			case "add" :return 1;break;
			case "mod" :return 2;break;
			case "del" :return 3;break;
			default : return ntjs.EDITFLAG.nOriginal;
		}
	},
	
	/**
	 * =======================================================================
	 * 创建人：kxr
	 * 创建日期：2010-06-02
	 * 描述：保存页面填写的内容
	 * 参数： method  后台调用的业务方法
	 * =======================================================================
	 */
	saveInputs : function(method)
	{
		var rsdata, eRow, root, value;
		this.xRs = DocHelper.newXML();
		root = this.xRs.documentElement;
		rsdata = this.xRs.selectSingleNode("//rs:data");
		if(rsdata)	this.xRs.documentElement.removeChild(rsdata);
		
		root.setAttribute("mtdName", method?method:"saveItemTX");
		root.setAttribute("objName", this.objName);
		
		rsdata = this.xRs.createElement("rs:data");
		rsdata.setAttribute("objName", this.objName);
		eRow = this.xRs.createElement("z:row");
		
		this.saveSelect();
		var inpFields = this.area.getElementsBySelector('[id="ctlField"]');
		if(inpFields){
			var iLen = inpFields.length;
			var field;
			for(var i = 0; i < iLen; i++){
				field = inpFields[i];
				value = field.ralvalue||field.value;
				
				if(field.tagName.toLowerCase() == "textarea") value = value.replace(/\r\n/g, "~!@#");
			
				if(value && value != "" && (field.usedfor && field.usedfor.indexOf('s') != 0)){
					eRow.setAttribute(field.fldName, value);
				}
				field = null;
				
			}
			eRow.setAttribute("edtFlag" , this.getEdtFlag(this.doMode));
			rsdata.appendChild(eRow);
			root.appendChild(rsdata);
		}
	},
	
	/**
	 * =======================================================================
	 * 创建人：kxr
	 * 创建日期：2010-06-02
	 * 描述：提交数据
	 * 参数：mode 当前操作模式   fn   回调函数
	 * =======================================================================
	 */
	postItem : function(mode, fn)
	{
		try{
			this.doMode = mode;
			this.saveInputs();
			this.postData(this.xRs,true,fn);
		}catch(ex){
			GzkEx.doAlert(ex, 'PItem.postItem');
		}
	},
	/**
	 * =======================================================================
	 * 创建人：kxr
	 * 创建日期：2010-06-02
	 * 描述：提交数据
	 * 参数： xRs - 要提交的XML数据
	 * 		isAsync 是否异步，true 是 false 否 如果否 fn无效 
	 * 		fn - 返回提示信息后所执行的方法。
	 * 		isReturn - 是否返回。
	 * =======================================================================
	 */
	postData: function(xRs, isAsync, fn, isReturn)
	{
		try {
			if (isAsync && !fn) {
				fn = (function(obj, isReturn){
					return {
						ok: function(xRs) {
							alert('提交成功了，在PItem');
						},
						err: function(xRs, exception) {
							alert('SaveItem Fail');
						}
					}
				})(this, isReturn);
			}
			if (isAsync && this.msgbox) this.msgbox.innerHTML ="正在提交数据";
			var msg =TxService.postXML(xRs, (isAsync ? fn : null));
		}
		catch(ex) {
			GzkEx.doAlert(ex,"PItem.postData");
		}
	}
}
PItem.getCtlField = function(fldname){		
	var fields = document.getElementsByTagName("input");
	var field;
	for(var i = 0; i < fields.length; i++){
		field = fields[i];
		if(field.fldName == fldname) return field;
	}
	
	return null;
};

PItem.getFiedValue = function(fldname){
	var field = PItem.getCtlField(fldname);
	if(field){
	   return field.value||"";
	}else{
	   return "";	
	}
};

PItem.setForSave = function(fldname){
	var field = PItem.getCtlField(fldname);
	if(field){
		var uf = field.usedfor;
		if(uf && uf.indexOf("s") == 0){
			field.usedfor = uf + 's';
		}else if(!uf){
			field.usedfor = "s";	
		}
	}
};
PItem.setForRead = function(fldname){
	var field = PItem.getCtlField(fldname);
	if(field){
		var uf = field.usedfor;
		if(uf && uf.indexOf("s") != 0){
			field.usedfor = uf.replace("s","");
		}else if(!uf){
			field.usedfor = "r";	
		}
	}
};
