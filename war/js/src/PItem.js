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
	 * �����ˣ�kxr
	 * �������ڣ�2010-06-02
	 * ����������д������ѡ���ʱ��ʹ��һ�����ص�input��������ֵ��������ҳ����д������ʱ����
	 *      ���ú�����ѡ����Ӧ��input��ֵ�ٱ���
	 * ������ ��
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
	 * �����ˣ�kxr
	 * �������ڣ�2010-06-02
	 * ����������д������ѡ���ʱ��ʹ��һ�����ص�input��������ֵ��
	 * ������ sele  �¼�Դ��������ѡ���
	 * =======================================================================
	 */
	vSele : function(sele){
		var icon = sele.nextSibling;
		if(!icon || icon.tagName.toLowerCase() != "input") return;
		
		icon.value = sele.value;
	},
	/** 
	 * =======================================================================
	 * ������: kxr
	 * ����ʱ��: 2009-09-24
	 * ����: ��ȡ��ǰ�Ĳ���״̬
	 * ��������ǰ����ģʽ��add:���  mod:�޸�  del:ɾ��  default:�޲���������ѯ��
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
	 * �����ˣ�kxr
	 * �������ڣ�2010-06-02
	 * ����������ҳ����д������
	 * ������ method  ��̨���õ�ҵ�񷽷�
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
	 * �����ˣ�kxr
	 * �������ڣ�2010-06-02
	 * �������ύ����
	 * ������mode ��ǰ����ģʽ   fn   �ص�����
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
	 * �����ˣ�kxr
	 * �������ڣ�2010-06-02
	 * �������ύ����
	 * ������ xRs - Ҫ�ύ��XML����
	 * 		isAsync �Ƿ��첽��true �� false �� ����� fn��Ч 
	 * 		fn - ������ʾ��Ϣ����ִ�еķ�����
	 * 		isReturn - �Ƿ񷵻ء�
	 * =======================================================================
	 */
	postData: function(xRs, isAsync, fn, isReturn)
	{
		try {
			if (isAsync && !fn) {
				fn = (function(obj, isReturn){
					return {
						ok: function(xRs) {
							alert('�ύ�ɹ��ˣ���PItem');
						},
						err: function(xRs, exception) {
							alert('SaveItem Fail');
						}
					}
				})(this, isReturn);
			}
			if (isAsync && this.msgbox) this.msgbox.innerHTML ="�����ύ����";
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
