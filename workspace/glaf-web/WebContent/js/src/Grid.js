var Grid = Class.create();
Grid.grds = new Hash();
/* ========================================================
 * �����ˣ�kxr
 * ����ʱ�䣺2010-05-17
 * ��������ת����ҳ
 * ========================================================
 */
Grid.firstPage = function(gridid)
{
	try{
		var grd = Grid.grds.get(gridid);
		if(grd){
			if(grd.pageNo == 1){
				return;
			} else {
				if(grd.filter) {
					grd.xRs = grd.filter.firstPage();
					grd.fillGrid();
					grd.selectedRows = [];
					grd.visibleDoTool();					
				}
			}
		}
	}catch(ex){
		GzkEx.doAlert(ex,"Grid.firstPage");	
	}
},	
	
/* ========================================================
 * �����ˣ�kxr
 * ����ʱ�䣺2010-05-17
 * ��������ת����һҳ
 * ========================================================
*/
Grid.previousPage = function(gridid)
{
	try{
		var grd =  Grid.grds.get(gridid);
		if(grd){			
			if(grd.pageNo == 1){
				return;
			} else {
				if(grd.filter) {
					grd.xRs = grd.filter.previousPage();
					grd.fillGrid();
					grd.selectedRows = [];
					grd.visibleDoTool();	
				}
			}
		}
	}catch(ex){
		GzkEx.doAlert(ex,"Grid.previousPage");	
	}
},	
	
/* ========================================================
 * �����ˣ�kxr
 * ����ʱ�䣺2010-05-17
 * ��������ת����һҳ
 * ========================================================
 */
Grid.nextPage = function(gridid)
{
	try{
		var grd =  Grid.grds.get(gridid);
		if(grd){
			if(grd.pageNo == grd.pageCount){
				return;
			}else{
				if(grd.filter) {
					grd.xRs = grd.filter.nextPage();
					grd.fillGrid();
					grd.selectedRows = [];
					grd.visibleDoTool();	
				}
			}
		}
	}catch(ex){
		GzkEx.doAlert(ex,"Grid.nextPage");	
	}
},
	
/* ========================================================
 * �����ˣ�kxr
 * ����ʱ�䣺2010-05-17
 * ��������ת��βҳ
 * ========================================================
*/
Grid.lastPage = function(gridid)
{
	try{
		var grd =  Grid.grds.get(gridid);
		if(grd){
			if(grd.pageNo == grd.pageCount){
				return ;
			} else {
				if(grd.filter) {
					grd.xRs = grd.filter.lastPage();
					grd.fillGrid();
					grd.selectedRows = [];
					grd.visibleDoTool();	
				}
			}
		}
	}catch(ex){
		GzkEx.doAlert(ex,"Grid.lastPage");	
	}
};
	
/* ========================================================
 * �����ˣ�kxr
 * ����ʱ�䣺2010-05-19
 * ������ѡ��Grid����
 * ������gridid[Grid���id]  evObj[�¼�Դ�������checkbox]  checked[�Ƿ�ѡ��״̬�������ȫѡ���Ĵ���������ʱ�Ŵ�����
 * ========================================================
*/
Grid.selectRow = function(gridid, evObj, checked)
{
	try{
		var grd;
		if(typeof gridid == "string"){
			grd = Grid.grds.get(gridid);
		}else{
			grd = gridid;
		}
		if(grd)
		{	
			if(typeof checked != "undefined")
				evObj.checked = checked;	
			if(evObj.checked){
				if(evObj.rowid)
					grd.selectedRows.push(evObj.rowid);
			}else{
				if(evObj.rowid)
					grd.selectedRows = grd.selectedRows.without(evObj.rowid);
			}
			grd.selectedRows = grd.selectedRows.uniq();
			grd.visibleDoTool();
		}
	}catch(ex){
		GzkEx.doAlert(ex,"Grid.selectRow");
	}
};

	
/* ========================================================
 * �����ˣ�kxr
 * ����ʱ�䣺2010-05-19
 * ������ѡ��Grid��������
 * ������gridid[Grid���id]  evObj[�¼�Դ�������checkbox]
 * ========================================================
*/
Grid.selectAllRow = function(gridid, evObj)
{
	try{
		var evChecked = evObj.checked;
		var evTr = evObj.parentElement.parentElement;
		var evNext = evTr.nextSibling;
		while(evNext){
			if(!evNext.usefor){
				if(evNext.firstChild.firstChild.rowid){
					Grid.selectRow(gridid, evNext.firstChild.firstChild, evChecked);
				}
			}
			
			evNext = evNext.nextSibling;
		}
		var grd = Grid.grds.get(gridid);
		if(grd){
			if(!grd.seleBtn){	//����"ȫѡ"��input
				grd.seleBtn = evObj;
			}
		}
	}catch(ex){
		GzkEx.doAlert(ex,"Grid.selectAllRow");
	}
};
Grid.delRow = function(gridid)
{
	try{
		
	}catch(ex){
		GzkEx.doAlert(ex,"Grid.delRow");
	}
};
Grid.delRows = function()
{
	try{
		if(!window.confirm("��ȷ��Ҫɾ����ѡ�ļ�¼���Ҳ�Ҫ�����"))  return;
		var evtObj = event.srcElement;
		var grd = Grid.grds.get(evtObj.grd);
		if(grd)
		{
			var mtd = 'deleteBatchTX';
			var delFilter = new Filter({objName:grd.objName, mtdName:mtd});
			var ids = grd.selectedRows;
			for(var i = 0; i < ids.length; i++)
			{
				delFilter.addFilter(grd.objName+".id", ids[i],"=");
			}
			
			//alert(delFilter.root.xml);
			var rs = delFilter.txSend();
			var msg = rs.selectSingleNode("//msg");
			if(msg){
				alert(msg.getAttribute("description"));
			}
			grd.doSearch(null,true);
		}
	}catch(ex){
		GzkEx.doAlert(ex,"Grid.delRows");
	}
}
Grid.prototype = {
	type : "list",				//Grid���ͣ�List��������ʾ�б�choose:ѡ��ҳ��[������չ]
	linespace : 0,
	dwidth : 400,				//��̬������ܳ���
	pagesize: 10,				//ÿ��Gridҳ���¼��
	filter : null,				//��ѯ������װ
	initFilter : null,			//Ĭ�ϲ�ѯ����
	fn : {
		rowclick : function(){},		//�е����¼�
		rowcbclick : function(){},		//����ѡ��򵥻��¼�
		rowcbAllclick : function(){},	//����ѡ���ȫѡ��ȫ��ѡ�������¼�
		refresh : function(){}			//ˢ������ʱִ�еķ������簴�������������򡢷�ҳ��
	},
	
	container : null,			//Grid�������
	doTools : [],				//�������ܰ�ť�����ø�ʽ�� doTools : [{name:'btn_add',onclick:}]
	fixedCells : [],			//�̶��У����ø�ʽ�� fixedCells :[{code:'user.name',name:'����',align:'center',nowrap:'true' }]
	dynamicCells : [],
	selectedRows : [],
	
  	initialize: function(config) {
  		if(config && typeof config== 'object')
		{
			for(var p in config) 
			{
				this[p] = config[p];
			}
		}
		if(this.filter){
			if(!this.filter.pageSize) this.filter.setPageSize(this.pagesize || 10);
			this.initFilter = this.filter;
			this.container.initFilter = this.filter;	//��Filter���õ�ҳ�����������ȥ������Grid�ķ�ҳ���ܵ���
			this.container.filter = this.filter;
		}
		
		this.buildGridLayout();
		if(this.id)	Grid.grds.set(this.id,this);
  	}, 
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ��:2010-05-10
	 * ��������ʼ���б�
	 * ========================================================
	 */
	initList : function(){
		if(!this.fn.rowclick)	this.fn.rowclick = function(){};
		if(!this.fn.rowdblclick)	this.fn.rowdblclick = function(){};
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ��:2010-05-10
	 * ��������ȡGrid�ؼ���PageTool���HTML
	 * ========================================================
	 */
	getToolBar : function(type)
	{
		var phtm = new StrBuffer();
		phtm.append('');
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ��:2010-05-10
	 * ��������ȡGrid�ؼ���PageTool���HTML
	 * ========================================================
	 */
	getPageToolDom : function()
	{
		var phtm = new StrBuffer();
		phtm.append('<div style="width:319px;float:right;">');
		phtm.append('<table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">');
		phtm.append('<tr style="padding-top:15px">');
		phtm.append('<td class="pageborder"><table width="100%" border="0" cellpadding="0" cellspacing="0">');
		phtm.append('<tr>');
		phtm.append('<td align="center">��ǰ��<span id="rcNow"></span>ҳ/��<span id="rcPage"></span>ҳ��<span id="rcTotal"></span>����¼</td>');
		phtm.append('<td align="center"><a href="javascript:Grid.firstPage(\'' + this.id + '\')" style="pointer:hand;">��ҳ</a></td>');
		phtm.append('<td align="center"><a href="javascript:Grid.previousPage(\'' + this.id + '\')" style="pointer:hand;">��һҳ</a></td>');
		phtm.append('<td align="center"><a href="javascript:Grid.nextPage(\'' + this.id + '\')" style="pointer:hand;">��һҳ</a></td>');
		phtm.append('<td align="center"><a href="javascript:Grid.lastPage(\'' + this.id + '\')" style="pointer:hand;">βҳ</a></td>');
		phtm.append("</tr></table></td></tr></table></div>");
		
		return phtm.toString();
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ��:2010-05-10
	 * ��������ȡGrid�ؼ��Ĳ�����ť���HTML
	 * ========================================================
	 */
	getDoToolDom : function()
	{
		if(!this.doTools || this.doTools.length == 0)	return "";
		
		var dohtm = new StrBuffer();
		dohtm.append('<div id="doButton" style="width:' + (this.doTools.length*73) + 'px;float:left;">');
		dohtm.append('<table border="0" cellspacing="0" cellpadding="0">');
		dohtm.append('<tr><td height="35px" valign="bottom">');
		var dTool,dCss;
		for(var i = 0; i < this.doTools.length; i++)
		{
			dTool = this.doTools[i];
			dCss = dTool.csstext||'';
			dohtm.append('<input type="button" class="button" name="' + dTool.name + '" value="' + dTool.value + '" grd="' + this.id + '" ');
			if(i != (this.doTools.length-1))
			{
				var rexp = /\;&/;
				if(!rexp.test(dCss)) dCss  += ";";
				dCss += 'margin-right:5px;';
			}
			dohtm.append('style="' + dCss + '" ');
			if(dTool.selemode)	dohtm.append('selemode="' + dTool.selemode + '" ');
			if(dTool.useby != this.useby){
				dohtm.append('style="display:none" ');
			}
			dohtm.append((dTool.disabled?"disabled":"") + '/>');
		}
		dohtm.append("</td></tr></table></div>");
		
		return dohtm.toString();
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ��:2010-05-31
	 * ��������ȡGrid�ؼ�����Ƕ��������DOM
	 * ========================================================
	 */
	getInnerToolDom : function()
	{
		if(!this.innerTools || this.innerTools.length == 0)	return "";
		var inhtm = new StrBuffer();
		var iTool,iA, iCss;
		inhtm.append('<div id="doInner" style="text-align:center;display:none">');
		for(var i = 0; i < this.innerTools.length; i ++){
			iTool = this.innerTools[i];
			iCss = iTool.csstext||"";
			if(i>0) iCss = 'margin-left:10px;' + iCss;
			inhtm.append('<a id="' + iTool.code + '" class="node" ');
			if(iTool.useby) inhtm.append('useby="' + iTool.useby + '" ');
			else inhtm.append('useby="admin" ');//Ĭ��Ϊ����Ա�ſ���ʹ��
			if(iTool.href)	inhtm.append('href="' + iTool.href + '" ');
			else inhtm.append('href="#" ');
			if(iCss!="") inhtm.append('style="cursor:pointer;' + iCss + '" ');
			if(iTool.onclick) inhtm.append(' onclick="' + iTool.onclick + '(this.xrow)" ');
			inhtm.append('>' + iTool.value + '</a>')
		}
		inhtm.append('</div>');
		
		return inhtm.toString();
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ��:2010-05-10
	 * ��������ȡGrid�ؼ��Ĺ̶������HTML
	 * ========================================================
	 */
	getFixedCellDom : function()
	{
		var fcells = new StrBuffer();
		fcells.append('<table id="' + this.id + 'Fixed" border="0" cellspacing="1" cellpadding="0" class="list-box">');
		//������selected����ʾѡ���
		if(!this.unsele) fcells.append('<col width="55px"/>');	//�Ƿ���ʾѡ�񷽿�
		fcells.append('<col width="40px"/>');	//Ĭ����ʾ����
		if(this.fixedCells) fcells.append(this.getFixedColSet(this.fixedCells));
		fcells.append('<tr class="list-title">');
		if(!this.unsele){
			fcells.append('<td align="center" class="title" nowrap="true"><input type="checkbox" onclick="Grid.selectAllRow(\'' + this.id + '\', this)" name="id"/><span>ȫѡ</span></td>');
		}
		fcells.append('<td align="center" class="title">���</td>' + this.getFixedCellTitles(this.fixedCells));
		fcells.append('</tr>');
		var tds = "";
		var fcell;
		if(!this.unsele){
			tds = '<td select="true" align="center"><input type="checkbox" id="id" name="id" onclick="Grid.selectRow(\'' + this.id + '\',this)" style="display:none"/></td>';	
		}
		tds += '<td show="index"></td>'
		if(this.fixedCells){
			for(var i = 0; i < this.fixedCells.length; i++){
				fcell = this.fixedCells[i];
				tds +='<td key="' + fcell.code + '" ';
				if(fcell.wale) tds += 'wale="' + fcell.wale + '" ';
				if(fcell.style){
					tds += 'style="' + fcell.style;
					if(fcell.align){
						if(fcell.style.indexof('text-align')==0)
							tds += ';text-align:' + fcell.align;
					}
					tds += '"></td>';
				}else{
					if(fcell.align){
						tds +='style="text-align:' + fcell.align + '"></td>';
					}else{
						tds += '></td>';
					}
				}
			}
		}
		
		var trLen = this.pagesize;
		if(this.filter.pageSize){	//���filter������ÿҳ��¼����������ڣ�ȡfilter��ҳ��
			if(this.filter.pageSize > trLen)
				trLen = this.filter.pageSize;
		}
		for(var i = 0; i < trLen; i++)
		{
			if((i%2)==0){
				fcells.append('<tr class="list-back">' + tds + '</tr>');
			}
			else{
				fcells.append('<tr>' + tds + '</tr>');
			}
		}
		tds = null;
		
		var clen = this.fixedCells.length+1;
		if(!this.unsele) clen += 1;
		if(!this.noscroll) fcells.append('<tr usefor="fbottom" style="height:17px"><td colspan="' + clen + '"></td></tr>');
		fcells.append("</table>");
		
		return fcells.toString();
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ��:2010-05-10
	 * ��������ȡGrid�ؼ��Ŀɱ������HTML
	 * ========================================================
	 */
	getDynamicCellDom : function()
	{
		if(!this.dynamicCells || (this.dynamicCells.length == 0))	return "";
		
		var dcells = new StrBuffer();
		dcells.append('<div style="width:' + this.dwidth + 'px;margin-left:-1px;');
		if(!this.noscroll) dcells.append('border-right:1px solid #CDCBCC;border-bottom:1px solid #CDCBCC;overflow-x:scroll;">');
		else dcells.append('overflow-x:hidden;">');
        dcells.append('<table id="' + this.id + 'Dynamic" style="left:-1px" border="0" cellspacing="1" cellpadding="0" class="list-box">');
        dcells.append(this.getDynamicColSet(this.dynamicCells));
        if(this.innerTools)  dcells.append('<col width="160px"/>');
        dcells.append('<tr class="list-title">');
        dcells.append(this.getDynamicCellTitles(this.dynamicCells)); 
        dcells.append('</tr>');
		
		var tds = "";
		var dcell;
		for(var i = 0; i < this.dynamicCells.length; i++){
			dcell = this.dynamicCells[i];
			tds +='<td key="' + dcell.code + '" ';
			if(dcell.wale) tds += 'wale="' + dcell.wale + '" ';
			if(dcell.style){
				tds += 'style="' + dcell.style;
				if(dcell.align){
					if(dcell.style.indexof('text-align')==0)
						tds += ';text-align:' + dcell.align;
				}
				tds += '"></td>';
			}else{
				if(dcell.align){
					tds +='style="text-align:' + dcell.align + '"></td>';
				}else{
					tds += '></td>';
				}
			}
		}
		if(this.innerTools) tds += '<td btn="true" style="text-align:center">' + this.getInnerToolDom() + '</td>';
		var trLen = this.pagesize;
		if(this.filter.pageSize){	//���filter������ÿҳ��¼����������ڣ�ȡfilter��ҳ��
			if(this.filter.pageSize > trLen)
				trLen = this.filter.pageSize;
		}
		for(var i = 0; i < trLen; i++)
		{
			if((i%2)==0){
				dcells.append('<tr class="list-back">' + tds + '</tr>');
			}
			else{
				dcells.append('<tr>' + tds + '</tr>');
			}
		}
		tds = null;
        dcells.append('</table></div>');
        
        return dcells.toString();
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ��:2010-05-10
	 * ����������Grid�ؼ��е�Col
	 * ������cells[Ҫ��Col���õ���] dynamic[�Ƿ�̬��]
	 * ========================================================
	 */
	getCellsSet : function(cells, dynamic)
	{
		var cols = new StrBuffer();
		var cell;
		var sWidth = 0;
		var cLen = cells.length;
		for(var i = 0; i < cLen; i++)
		{
			cell = cells[i];
			sWidth += (cell.width?parseInt(cell.width)+1:61);
			cols.append('<col ');
			if(cell.dynamic == 'true') cols.append('/>');
			else
				cols.append('width="' + (cell.width?cell.width:"60") + '"/>');
		}
		
		return cols.toString();
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ��:2010-05-10
	 * ����������Grid�ؼ��̶��е�Col
	 * ������cells[Ҫ��Col���õĹ̶���]
	 * ========================================================
	 */
	getFixedColSet : function(cells)
	{
		if(cells && cells.length > 0)
			return this.getCellsSet(cells);
		else
			return "";
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ��:2010-05-10
	 * ����������Grid�ؼ��ɱ��е�Col
	 * ������cells[Ҫ��Col���õĿɱ���]
	 * ========================================================
	 */
	getDynamicColSet : function(cells)
	{
		if(cells && cells.length > 0){
			return this.getCellsSet(cells,true);
		}
		else
			return "";
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ��:2010-05-10
	 * ��������ȡGrid�ؼ����б���
	 * ������cells[Ҫ��ȡ��ȵ���]
	 * ========================================================
	 */
	getCellTitles : function(cells)
	{
		var fcells = new StrBuffer();
		var cell;
		for(var i = 0; i < cells.length; i++)
		{
			cell = cells[i];
			fcells.append('<td class="title"');
			if(cell.align)	fcells.append('align="' + cell.talign + '" ');
			if(cell.nowrap) fcells.append('nowrap="' + cell.nowrap + '" ');
			fcells.append(">");
			if(cell.name) fcells.append(cell.name);
			fcells.append('</td>');
		}
		
		return fcells.toString();
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ��:2010-05-10
	 * ��������ȡGrid�ؼ��Ĺ̶��б���
	 * ������cells[Ҫ��ȡ����Ĺ̶���]
	 * ========================================================
	 */
	getFixedCellTitles : function(cells)
	{
		if(cells && cells.length > 0)
			return this.getCellTitles(cells);
		else return "";
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ��:2010-05-10
	 * ��������ȡGrid�ؼ��Ŀɱ��б���
	 * ������cells[Ҫ��ȡ����Ŀɱ���]
	 * ========================================================
	 */
	getDynamicCellTitles : function(cells)
	{
		if(cells && cells.length > 0){
			var titles = this.getCellTitles(cells);
			if(this.innerTools) titles += '<td align="center" class="title">����</td>';
			
			return titles;
		}
		else return "";
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ��:2010-05-10
	 * ��������ȡGrid�ؼ��Ŀ��
	 * ������cells[Ҫ��ȡ��ȵ���], includespace[�Ƿ���������]
	 * ========================================================
	 */
	getCellsWidth : function(cells,includespace)
	{
		var sWidth = 0,cell,cwidth;
		for(var i = 0; i < cells.length; i++)
		{
			cell = cells[i];
			cwidth = cell.width || 0;
			sWidth += cwidth + (includespace?this.linespace:0);
		}
		
		return sWidth;
	},	
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ��:2010-05-10
	 * ��������ȡGrid�ؼ��Ĺ̶���������
	 * ������cells[Ҫ��ȡ��ȵĹ̶���], includespace[�Ƿ���������]
	 * ========================================================
	 */
	getFixedCellWidth : function(cells,includespace)
	{
		var sWidth = 0;
		if(!cells)	return sWidth;
	
		sWidth = this.getCellsWidth(cells,includespace);
		
		return sWidth;
	},	
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ��:2010-05-10
	 * ��������ȡGrid�ؼ��Ŀɱ���������
	 * ������cells[Ҫ��ȡ��ȵĿɱ���], includespace[�Ƿ���������]
	 * ========================================================
	 */
	getDynamicCellWidth : function(cells,includespace)
	{
		var sWidth = 0;
		if(!cells)	return sWidth;
		
		sWidth = this.getCellsWidth(cells,includespace);
		if(this.innerTools)	sWidth += 160;  //��Ƕ�в�������ʱ��Ҫ�������Ŀ��
		
		return sWidth;
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ�䣺2010-05-10
	 * ����������Grid�ؼ�����
	 * ========================================================
	 */
	buildGridLayout : function()
	{
		if(!this.container){
			alert('δ����Grid��������������ã�');	
			return;
		}
		
		var grhtm = new StrBuffer();
		var fwidth = this.getFixedCellWidth(this.fixedCells);
		var dwidth = this.getDynamicCellWidth(this.dynamicCells);
		var cwidth = this.container.getWidth();
		this.dwidth = cwidth - fwidth;
		//alert("FWidth:"+fwidth+" DWidth:"+dwidth+" CWidth:"+cwidth);
		grhtm.append('<div id="' + this.id + '" style="width:100%">');
		grhtm.append('<table width="100%" border="0" cellspacing="0" cellpadding="0">');
		grhtm.append('<col width="' + fwidth + 'px"/><col/>');
		grhtm.append('<tr id="pageToolTop" style="display:' + (this.doToolShow!='bottom'?"inline":"none") + '">');
		grhtm.append('<td colspan="2" nowrap="true">');
		grhtm.append(this.getPageToolDom());
		grhtm.append('</td></tr>');
		grhtm.append('<tr>');
		grhtm.append('<td id="' + this.id + 'fixContent">');
		grhtm.append(this.getFixedCellDom(fwidth));
		grhtm.append('</td><td id="' + this.id + 'dynamicContent">');
		//grhtm.append('<div style="width:1border:1px solid red;">');
		grhtm.append(this.getDynamicCellDom());
		//grhtm.append('</div>');
		grhtm.append('</td></tr>');
		grhtm.append('<tr id="pageToolBottom">');
		grhtm.append('<td colspan="2" nowrap="true">');
		grhtm.append(this.getDoToolDom());
		grhtm.append(this.getPageToolDom());
		grhtm.append('</td></tr></table></div>');
		
		this.container.innerHTML = grhtm.toString();
		//$('htmlText').innerText = grhtm.toString();
		
		this.initDoToolEvent();
	},
	getButtonOnclick : function(name)
	{
		try{
			var dTool;
			for(var i = 0; i < this.doTools.length; i++)
			{
				dTool = this.doTools[i];
				if(dTool.name == name){
					return dTool.onclick;
				}
			}
			return (function(){});
		}catch(ex){
			GzkEx.doAlert(ex,"Grid.getButtonFun");
		}
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ�䣺2010-05-18
	 * ��������ʼ�����ܰ�ť���¼�
	 * ========================================================
	 */
	initDoToolEvent : function()
	{
		try{
			var divBtn = $('doButton');
			if(divBtn){
				var inps = divBtn.getElementsBySelector("input");
				var btnFn;
				for(var i = 0; i < inps.length; i++)
				{
					btnFn = this.getButtonOnclick(inps[i].name);
					if(typeof btnFn == "string"){
						inps[i].onclickstr = btnFn;
						inps[i].onclick = function(){
							var evtObj = event.srcElement;
							eval(evtObj.onclickstr);
						};
					}else{
						inps[i].onclick = btnFn;
					}
				}
			}
		}catch(ex){
			GzkEx.doAlert(ex,'Grid.initDoToolEvent');
		}
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ�䣺2010-05-18
	 * ����������ѡ��ļ�¼�������¼���ť�Ŀ���״̬
	 * ========================================================
	 */
	visibleDoTool : function()
	{
		try{
			var divBtn = $('doButton');
			if(!divBtn) return;
			var btns = divBtn.getElementsBySelector("input");
			var btn;
			for(var i = 0; i < btns.length; i++)
			{
				btn = btns[i];
				if(btn.selemode == "single" && this.selectedRows.length == 1){
					btn.disabled = false;
				}else if(btn.selemode == "more" && this.selectedRows.length > 0){
					btn.disabled = false;
				}else {
					if(!btn.selemode)
						btn.disabled = false;
					else 
						btn.disabled = true;
				}
			}
			
		}catch(ex){
			GzkEx.doAlert(ex,"Grid.visibleDoTool");	
		}
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ�䣺2010-05-13
	 * ��������������������
	 * ������filter[��ѯ������װ������]  hold[�Ƿ�����ǰ��ҳ��һ��ɾ������ʱ�õ�]
	 * ========================================================
	 */
	doSearch : function(filter, hold)
	{
		this.filter = filter||this.initFilter;
		if(!hold)
			this.filter.setPageNo(1);
		this.xRs = this.filter.txSend();
		
		if(this.xRs){
			this.pageNo = 0;
			this.fillGrid();
		}
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ�䣺2010-07-08
	 * ������ˢ���б�
	 * ======================================================== 
	 */
	reFresh : function()
	{
		if(this.filter){
			this.xRs = this.filter.txSend();
			
			if(this.xRs){
				this.fillGrid(true);
			}
		}
	},
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ�䣺2010-05-17
	 * ���������Grid�����������
	 * ========================================================
	 */
	clearRow : function(doms)
	{
		var dom, tds,std;
		for(var i = 1; i < doms.length; i++)
		{
			dom = doms[i];
			if(dom){
				tds = dom.childNodes;
				if(tds && tds.length > 0){
					for(var j = 0; j < tds.length; j++)
					{
						if(tds[j].select || tds[j].btn){//ѡ��� �Ͳ������Ӱ�ť
							std = tds[j].firstChild;
							if(std){
								std.style.display = 'none';
							}
						} else {
							tds[j].innerText = "";
						}
					}
				}
			}
		}
		dom = null, tds = null;
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ�䣺2010-05-17
	 * ������ע�����ݵ������
	 * ���������·�ҳ��Ϣ����ǰҳ����ҳ���ͼ�¼����
	 * ========================================================
	 */
	fillPage : function()
	{
		var ptTop = $('pageToolTop');
		var ptBottom = $('pageToolBottom');
		
		var rcN = ptTop.getElementsBySelector("span");
		var sObj;
		for(var i = 0; i < rcN.length; i++)
		{
			sObj = rcN[i];
			if(sObj.id == 'rcNow') sObj.innerHTML = this.pageNo;
			if(sObj.id == 'rcPage'){
				sObj.innerHTML = this.pageCount;
				if(this.recordCount == 0)	sObj.innerHTML = "0";
			}
			if(sObj.id == 'rcTotal') sObj.innerHTML = this.recordCount;
		}		
		var rcP = ptBottom.getElementsBySelector("span");
		for(var i = 0; i < rcP.length; i++)
		{
			sObj = rcP[i];
			if(sObj.id == 'rcNow') sObj.innerHTML = this.pageNo;
			if(sObj.id == 'rcPage')	{
				sObj.innerHTML = this.pageCount;
				if(this.recordCount == 0)	sObj.innerHTML = "0";
			}
			if(sObj.id == 'rcTotal') sObj.innerHTML = this.recordCount;
		}
		ptTop = null, ptBottom = null, rcN = null, rcP = null;
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ�䣺2010-05-13
	 * ������ע�����ݵ������
	 * ������xrows[����row��]  doms[Grid���������tr��]
	 * ========================================================
	 */
	fillRow : function(xrows,doms){
		var xrow,dom;
		var rIndex = this.pagesize * (this.pageNo-1);
		var vls;
		var i = 0;
		for( ; i < xrows.length; i++)
		{
			xrow = xrows[i];
			dom = doms[i+1];//alert(dom.outerHTML);
			var tds = dom.childNodes;
			var tLen = tds.length;
			var td,value,domID, btype;
			for(var j = 0; j < tLen; j++)
			{	
				td = tds[j];
				
				if(!this.unsele){
					if(td.select){
						domID = td.firstChild;
						if(domID){
							domID.rowid = xrow.getAttribute("id");
							domID.value = xrow.getAttribute("id");
							domID.xrow = xrow;
							domID.checked = false;
							domID.style.display = "inline";
						}
					}else if(td.btn){
						if(btype == 'f'){ 	//��Լ�¼��fʱ������ʾ��Ӧ�Ĺ������Ӽ�
							this.showInnerBtn(td.firstChild,xrow);
						}else{
							td.firstChild.style.display = 'none';
						}
					}else if(td.show){
						td.innerText = ++rIndex;	
					} else if(td.key){
						value = xrow.getAttribute(td.key);
						if(td.key == 'btype') btype = value;
						td.title = value;
						if(td.wale) value = this.waleVale(td.wale, value);
						if(value) td.innerHTML = '<div class="viewstr">' + value + '</div>';
					}
				}else{
					if(td.btn){
						if(btype == 'f'){ 	//��Լ�¼��fʱ������ʾ��Ӧ�Ĺ������Ӽ�
							this.showInnerBtn(td.firstChild,xrow);
						}else{
							td.firstChild.style.display = 'none';
						}
					} else if(td.show){
						td.innerText = ++rIndex;	
					} else if(td.key){
						value = xrow.getAttribute(td.key);
						td.title = value;
						if(td.wale) value = this.waleVale(this.wale, value)
						if(value) td.innerHTML = '<div class="viewstr">' + value + '</div>';
					}
				}
			}
		}
	},
	
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ�䣺2010-06-01
	 * ��������ֵ�Ǹ����б��ʱ������Щ����
	 * ========================================================
	 */
	waleVale : function(wale, value){
		
		var vID = eval(wale);

		return vID.get(value)||"";
	},
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ�䣺2010-05-13
	 * ������ע�����ݵ�Grid�������ʾ
	 * --------------------------------------------------------
	 * �޸��ˣ�kxr
	 * �޸����ڣ�2010-07-08
	 * �޸����ݣ���Ӳ���freshsign���Ƿ�Ϊˢ�£�Ĭ��Ϊfalse
	 * ========================================================
	 */
	fillGrid : function(freshsign)
	{   
		var rInfo = this.xRs.selectSingleNode("//rs:data");
		var cPage,cPCount, cRCount;
		if(rInfo){
			cPage = rInfo.getAttribute("pageNo")||1;
			cPCount = rInfo.getAttribute("pageCount")||0;
			cRCount = rInfo.getAttribute("recordCount")||0;
		
			if(cPage == this.pageNo && (!freshsign)){
				return;
			} else {
				this.pageNo = cPage;
			}
			this.pageCount = cPCount;
			this.recordCount = cRCount;
	
			this.fillPage();
		}
	
		var xrows = this.xRs.selectNodes("//z:row");
		
		var fTable = $(this.id + "Fixed");
		var dTable = $(this.id + "Dynamic");
		
		var fTrs = fTable.tBodies[0].rows;
		var dTrs = dTable.tBodies[0].rows;
		this.clearRow(fTrs);
		this.clearRow(dTrs);
		if(!xrows || xrows.length == 0) return;
		this.fillRow(xrows,fTrs,'F');
		this.fillRow(xrows,dTrs,'D');	
		
		if(this.seleBtn) this.seleBtn.checked = false;  //��ѡ�񡱸�ѡ��ֵΪ��ѡ
	},
	/* ========================================================
	 * �����ˣ�kxr
	 * ����ʱ�䣺2010-05-13
	 * ��������ʾ���ļ���������ӹ��ܰ�ť
	 * ������btndiv   ���ӹ��ܰ�ť������    xrow ��Ӧ���ļ���¼��Ϣ
	 * ========================================================
	 */
	showInnerBtn : function(btndiv,xrow)
	{
		if(btndiv){
			var aLinks = btndiv.childNodes;
			var aLen = aLinks.length;
			var aLink;
			for(var i = 0; i < aLen; i++){
				aLink = aLinks[i];
				aLink.xrow = xrow;
				if(aLink.useby == this.useby || 'normal' == aLink.useby)	aLink.style.display = 'inline';
				else aLink.style.display = 'none';
			}
			
			btndiv.style.display = 'inline';
		}
	}
		
};
