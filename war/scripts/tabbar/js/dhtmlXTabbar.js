
 
function dhtmlXTabBar(parentObject,mode,height)
{
 mode=mode||"top";
 this._mode=mode+"/";


  if(typeof(parentObject)!="object")
 this.entBox=document.getElementById(parentObject);
 else
 this.entBox=parentObject;

 this.width = this.entBox.getAttribute("width")||(window.getComputedStyle?window.getComputedStyle(this.entBox,null)["width"]:(this.entBox.currentStyle?this.entBox.currentStyle["width"]:0));
 if((!this.width)||(this.width=="auto")||(parseInt(this.width)==0))
 this.width=this.entBox.offsetWidth+"px";

 this.height = this.entBox.getAttribute("height")||(window.getComputedStyle?window.getComputedStyle(this.entBox,null)["height"]:(this.entBox.currentStyle?this.entBox.currentStyle["height"]:0));
 if((!this.height)||(this.height=="auto"))this.height=this.entBox.offsetHeight+"px";

 this.activeTab = null; this.tabsId = new Object();

 this._align="left";
 this._offset=5;
 this._margin=1;
 this._height=parseInt(height||20);
 this._bMode=(mode=="right"||mode=="bottom");
 this._tabSize='150px';
 this._content=new Array();
 this._tbst="win_text";
 this._styles={
 winDflt:["p_left.gif","p_middle.gif","p_right.gif","a_left.gif","a_middle.gif","a_right.gif","a_middle.gif",3,3,6,"#F4F3EE","#F0F8FF"],
 winScarf:["with_bg/p_left.gif","with_bg/p_middle.gif","with_bg/p_right_skos.gif","with_bg/a_left.gif","with_bg/a_middle.gif","with_bg/a_right_skos.gif","with_bg/p_middle_over.gif",3,18,6,false,false],
 winBiScarf:["with_bg/p_left_skos.gif","with_bg/p_middle.gif","with_bg/p_right_skos.gif","with_bg/a_left_skos.gif","with_bg/a_middle.gif","with_bg/a_right_skos.gif","with_bg/p_middle_over.gif",18,18,6,false,false],
 winRound:["circuses/p_left.gif","circuses/p_middle.gif","circuses/p_right.gif","circuses/a_left.gif","circuses/a_middle.gif","circuses/a_right.gif","circuses/p_middle_over.gif",10,10,6,false,false]

};
 this._createSelf(mode=="right"||mode=="left"); this.setStyle("winDflt");
 return this;
}

 
dhtmlXTabBar.prototype.setOffset = function(offset){
 this._offset=offset;
}
 
dhtmlXTabBar.prototype.setAlign = function(align){
 if(align=="top")align="left";
 if(align=="bottom")align="right";
 this._align=align;
}
 
dhtmlXTabBar.prototype.setMargin = function(margin){
 this._margin=margin;
}





 
dhtmlXTabBar.prototype._createSelf = function(vMode)
{
 this._tabAll=document.createElement("DIV");
 this._tabZone=document.createElement("DIV");
 this._conZone=document.createElement("DIV");


 this.entBox.appendChild(this._tabAll);
 if(this._bMode){
 this._tabAll.appendChild(this._conZone);
 this._tabAll.appendChild(this._tabZone);
}
 else
 {
 this._tabAll.appendChild(this._tabZone);
 this._tabAll.appendChild(this._conZone);
}


 this._vMode=vMode;
  if(vMode){
 this._tabAll.className='dhx_tabbar_zoneV';
 this._setSizes=this._setSizesV;
 this._redrawRow=this._redrawRowV;

}
 else
  this._tabAll.className='dhx_tabbar_zone';

  if(this._bMode)
 this._tabAll.className+='B';
  this._tabZone.className='dhx_tablist_zone';
 this._conZone.className='dhx_tabcontent_zone';

 this._tabAll.onselectstart = function(){return false;};
 this._tabAll.onclick = this._onClickHandler;
 this._tabAll.onmouseover = this._onMouseOverHandler;
 if(_isFF)
 this._tabZone.onmouseout = this._onMouseOutHandler;
 else
 this._tabZone.onmouseleave = this._onMouseOutHandler;
 this._tabAll.tabbar=this;

 this._lineA=document.createElement("div");
 this._lineA.className="dhx_tablist_line";

 this._lineA.style[vMode?"left":"top"]=(this._bMode?0:(this._height+2))+"px";
 this._lineA.style[vMode?"height":"width"]=this[vMode?"height":"width"];
  if(vMode)
 this._conZone.style.height=this.height;
 else
  this._conZone.style.width=this.width;

 this.rows=new Array();
 this.rowscount=1;
 this._createRow();
 this._setSizes();
}

 
dhtmlXTabBar.prototype._createRow = function(){
 var z=document.createElement("DIV");
 z.className='dhx_tabbar_row';
 this._tabZone.appendChild(z);
 z._rowScroller=document.createElement('DIV');
 z._rowScroller.style.display="none";
 z.appendChild(z._rowScroller);
 this.rows[this.rows.length]=z;
  if(this._vMode){
 z.style.width=this._height+3+"px";
 z.style.height=parseInt(this.height)+"px";
 if(!this._bMode)
 this.setRowSizesA();
 else
 this.setRowSizesB();
}
 else
 {
 z.style.height=this._height+3+"px";
 z.style.width=parseInt(this.width)+"px";
}

 z.appendChild(this._lineA);
}
 
dhtmlXTabBar.prototype._setSizes = function(){
 
 this._tabAll.height=this.height;
 this._tabAll.width=this.width;

 if(this._tabZone.childNodes.length)
 var z=this._tabZone.lastChild.offsetTop-this._tabZone.firstChild.offsetTop+this._height+(_isIE?5:0);
 else
 var z=this._height+(_isIE?5:0);

 var a=z-2;
 this._tabZone.style.height=(a>0?a:0)+"px";
 a=parseInt(this.height)-z-4;
 this._conZone.style.height=(a>0?a:0)+"px";
}
  
dhtmlXTabBar.prototype._setSizesV = function(){
 this._tabAll.height=this.height;
 this._tabAll.width=this.width;

 var z=this._height*this.rows.length;

 if(!this._bMode){
 this._tabZone.style.width=z+3+"px";
 this._conZone.style.width=parseInt(this.width)-z+"px";
 this._conZone.style.left= z+3+"px";
}
 else{
 this._tabZone.style.width=z+3+"px";
 this._conZone.style.width=parseInt(this.width)-z+"px";
 this._tabZone.style.left=parseInt(this.width)-z+"px";
}

 this._conZone.style.height=this.height;
 this._tabZone.style.height=this.height;
}


 
dhtmlXTabBar.prototype._redrawRowV=function(row){
 var talign=this._align=="left"?"top":"bottom";
 var count=parseInt(this._offset);
 for(var i=0;i<row.tabCount;i++){
 row.childNodes[i]._cInd=i;
 row.childNodes[i].style[talign]=count+"px";
 count+=row.childNodes[i]._offsetSize+parseInt(this._margin);
}


};



 
dhtmlXTabBar.prototype.setRowSizesA=function(){
 for(var i=0;i<this.rows.length;i++){
 this.rows[i].style.left=i*this._height+"px";
 this.rows[i].style.zIndex=5+i;
}
}
 
dhtmlXTabBar.prototype.setRowSizesB=function(){
 for(var i=this.rows.length-1;i>=0;i--){
 this.rows[i].style.left=i*this._height+"px";
 this.rows[i].style.zIndex=15-i;
}
}
 
dhtmlXTabBar.prototype.setRowSizesC=function(){
 for(var i=this.rows.length-1;i>=0;i--){
 this.rows[i].style.zIndex=15-i;
}
}

 

 
dhtmlXTabBar.prototype._onMouseOverHandler=function(e)
{
 if(_isIE)
 var target = event.srcElement;
 else
 var target = e.target;
 target=this.tabbar._getTabTarget(target);
 if(!target){
 this.tabbar._hideHover(target);return;
}

 this.tabbar._showHover(target);

(e||event).cancelBubble=true;
}
 
dhtmlXTabBar.prototype._onMouseOutHandler=function(e)
{
 this.parentNode.tabbar._hideHover(null);return;
}




 
dhtmlXTabBar.prototype._onClickHandler=function(e)
{
 if(_isIE)
 var target = event.srcElement;
 else
 var target = e.target;

(e||event).cancelBubble=true;

 target=this.tabbar._getTabTarget(target);
 if(!target)return;

 this.tabbar._setTabActive(target);
 return false;
}

 
dhtmlXTabBar.prototype._getTabTarget=function(t){
 while((!t.className)||(t.className.indexOf("dhx_tab_element")==-1)){
 if((t.className)&&(t.className.indexOf("dhx_tabbar_zone")!=-1))return null;
 t=t.parentNode;
}
 return t;
}

 
dhtmlXTabBar.prototype._redrawRow=function(row){
 var count=parseInt(this._offset);
 for(var i=0;i<row.tabCount;i++){
 row.childNodes[i]._cInd=i;
 row.childNodes[i].style[this._align]=count+"px";
 count+=row.childNodes[i]._offsetSize+parseInt(this._margin);
}

};


 
dhtmlXTabBar.prototype.addTab = function(id,text,size,position,row){ row=row||0;

 var z=this.rows[row].tabCount||0;
 if((!position)&&(position!==0))
 position=z;

 var tab=this._createTab(text,size);
 tab.idd=id;
 this.tabsId[id] = tab;

 this.rows[row].insertBefore(tab,this.rows[row].childNodes[position]);

 this.rows[row].tabCount=z+1;
 this._redrawRow(this.rows[row]);
 this._setSizes();
}

 
dhtmlXTabBar.prototype._showHover=function(tab){
 this._hideHover(tab);
 if(tab==this._lastActive)return;
 switch(this._tbst){
 case "win_text":
 tab._lChild.style.backgroundImage='url('+this._imgPath+this._mode+this._styles[this._cstyle][6]+')';
 break;
}
 this._lastHower=tab;
}
 
dhtmlXTabBar.prototype._hideHover=function(tab){
 if((!this._lastHower)||(this._lastHower==tab)||(this._lastHower==this._lastActive))
 return;
 switch(this._tbst){
 case "win_text":
 this._lastHower._lChild.style.backgroundImage='url('+this._imgPath+this._mode+this._styles[this._cstyle][1]+')';
 break;
}
 this._lastHower=null;
}

 
dhtmlXTabBar.prototype._getTabById=function(tabId){
 return this.tabsId[tabId];
}

 
dhtmlXTabBar.prototype.setTabActive=function(tabId){
 var tab=this._getTabById(tabId);
 if(tab)this._setTabActive(tab);
}
 
dhtmlXTabBar.prototype._setTabActive=function(tab){
 if((this._onsel)&&(!this._onsel(tab.idd,this._lastActive?this._lastActive.idd:null)))return;
 if(this._lastActive)
 this._lastActive.className=this._lastActive.className.replace(/dhx_tab_element_active/g,"dhx_tab_element_inactive");
 tab.className=tab.className.replace(/dhx_tab_element_inactive/g,"dhx_tab_element_active");
 if((this._lastActive)&&(this._styles[this._cstyle][10]))
 this._lastActive.style.backgroundColor=this._styles[this._cstyle][10];
 if(this._styles[this._cstyle][11])
 tab.style.backgroundColor=this._styles[this._cstyle][11];

  if(this._vMode){
 switch(this._tbst){
 case "win_text":
 if(this._lastActive){
 this._lastActive._lChild.style.backgroundImage='url('+this._imgPath+this._mode+this._styles[this._cstyle][1]+')';
 this._lastActive.childNodes[0].childNodes[0].src=this._imgPath+this._mode+this._styles[this._cstyle][0];
 this._lastActive.childNodes[1].childNodes[0].src=this._imgPath+this._mode+this._styles[this._cstyle][2];
 this._lastActive.style.height=parseInt(this._lastActive.style.height)-this._styles[this._cstyle][9]+"px";
 this._lastActive._lChild.style.height=parseInt(this._lastActive._lChild.style.height)-this._styles[this._cstyle][9]+"px";
 this._lastActive.style[this._align=="right"?"marginBottom":"marginTop"]="0px"
 this._lastActive.style.width=this._height+1+"px";
 if(this._bMode)
 this._lastActive._lChild.style.width=this._height+1+"px";
}

 tab._lChild.style.backgroundImage='url('+this._imgPath+this._mode+this._styles[this._cstyle][4]+')';
 tab.childNodes[0].childNodes[0].src=this._imgPath+this._mode+this._styles[this._cstyle][3];
 tab.childNodes[1].childNodes[0].src=this._imgPath+this._mode+this._styles[this._cstyle][5];
 tab.style.height=parseInt(tab.style.height)+this._styles[this._cstyle][9]+"px";
 tab._lChild.style.height=parseInt(tab._lChild.style.height)+this._styles[this._cstyle][9]+"px";
 tab.style[this._align=="right"?"marginBottom":"marginTop"]="-3px"
 tab.style.width=this._height+3+"px";
 if(this._bMode)
 tab._lChild.style.width=this._height+3+"px";
 break;
}
}
 else
 {
 switch(this._tbst){
 case "win_text":
 if(this._lastActive){
 this._lastActive._lChild.style.backgroundImage='url('+this._imgPath+this._mode+this._styles[this._cstyle][1]+')';
 this._lastActive.childNodes[0].childNodes[0].src=this._imgPath+this._mode+this._styles[this._cstyle][0];
 this._lastActive.childNodes[1].childNodes[0].src=this._imgPath+this._mode+this._styles[this._cstyle][2];
 this._lastActive.style.width=parseInt(this._lastActive.style.width)-this._styles[this._cstyle][9]+"px";
 this._lastActive._lChild.style.width=parseInt(this._lastActive._lChild.style.width)-this._styles[this._cstyle][9]+"px";
 this._lastActive.style[this._align=="left"?"marginLeft":"marginRight"]="0px"
 this._lastActive.style.height=this._height+1+"px";
  if(this._bMode)
 this._lastActive._lChild.style.height=this._height+1+"px";
 }

 tab._lChild.style.backgroundImage='url('+this._imgPath+this._mode+this._styles[this._cstyle][4]+')';
 tab.childNodes[0].childNodes[0].src=this._imgPath+this._mode+this._styles[this._cstyle][3];
 tab.childNodes[1].childNodes[0].src=this._imgPath+this._mode+this._styles[this._cstyle][5];
 tab.style.width=parseInt(tab.style.width)+this._styles[this._cstyle][9]+"px";
 tab._lChild.style.width=parseInt(tab._lChild.style.width)+this._styles[this._cstyle][9]+"px";
 tab.style[this._align=="left"?"marginLeft":"marginRight"]="-3px"
 tab.style.height=this._height+3+"px";
  if(this._bMode)
 tab._lChild.style.height=this._height+3+"px";
  break;
}
}




 this._setContent(tab);


 this._lastActive=tab;
}





 
dhtmlXTabBar.prototype._createTab = function(text,size){
 var tab=document.createElement("DIV");
 tab.className='dhx_tab_element dhx_tab_element_inactive';
 var thml="";

 switch(this._tbst){
 case 'text':
 thml=text;
 break;
 case 'win_text':
  if(this._vMode)
{
 thml='<div style="position:absolute;'+(this._bMode?"right":"left")+':0px;top:0px;height:'+this._styles[this._cstyle][7]+'px;width:'+(this._height+3)+'px;"><img src="'+this._imgPath+this._mode+this._styles[this._cstyle][0]+((this._bMode&&(_isFF||_isOpera))?'" style="position:absolute;right:1px;"':'"')+'></div>';
 thml+='<div style="position:absolute;'+(this._bMode?"right":"left")+':0px;bottom:0px;height:'+this._styles[this._cstyle][8]+'px;width:'+(this._height+3)+'px;"><img src="'+this._imgPath+this._mode+this._styles[this._cstyle][2]+((this._bMode&&(_isFF||_isOpera))?'" style="position:absolute;right:1px;"':'"')+'></div>';
 thml+='<div style="position:absolute;background-repeat: repeat-y;background-image:url('+this._imgPath+this._mode+this._styles[this._cstyle][1]+');width:'+(this._height)+'px;left:0px;top:'+this._styles[this._cstyle][7]+'px;height:'+(parseInt(size||this._tabSize)-this._styles[this._cstyle][8]-this._styles[this._cstyle][7]+"px")+'">'+text+'</div>';
}
 else
 {
 thml='<div style="position:absolute;'+(this._bMode?"bottom":"top")+':0px;left:0px;width:'+this._styles[this._cstyle][7]+'px;height:'+(this._height+3)+'px;"><img src="'+this._imgPath+this._mode+this._styles[this._cstyle][0]+((this._bMode&&_isFF)?'" style="position:absolute;bottom:0px;"':'"')+'></div>';
 thml+='<div style="position:absolute;'+(this._bMode?"bottom":"top")+':0px;right:0px;width:'+this._styles[this._cstyle][8]+'px;height:'+(this._height+3)+'px;"><img src="'+this._imgPath+this._mode+this._styles[this._cstyle][2]+((this._bMode&&_isFF)?'" style="position:absolute;bottom:0px;left:0px;"':'"')+'></div>';
 thml+='<div style="position:absolute;background-repeat: repeat-x;background-image:url('+this._imgPath+this._mode+this._styles[this._cstyle][1]+');height:'+(this._height+(this._bMode?1:3))+'px;top:0px;left:'+this._styles[this._cstyle][7]+'px;width:'+(parseInt(size||this._tabSize)-this._styles[this._cstyle][8]-this._styles[this._cstyle][7]+"px")+';"><div style="padding-top:3px;">'+text+'<div></div>';
}
 if(!this._styles[this._cstyle][10])tab.style.backgroundColor='transparent';
 else tab.style.backgroundColor=this._styles[this._cstyle][10];
 break;
}
 tab.innerHTML=thml;
 tab._lChild=tab.childNodes[tab.childNodes.length-1];


  if(this._vMode)
{
 tab.style.height=size||this._tabSize;
 tab.style.width=this._height+1+"px";
}
 else
 {
 tab.style.width=size||this._tabSize;
 tab.style.height=this._height+1+"px";
}

 tab._offsetSize=parseInt(size||this._tabSize);
 return tab;
}

 
dhtmlXTabBar.prototype.clearAll = function(){
 this.tabsId=new Array();
 this.rows=new Array();
 this._lastActive=null;
 this._lastHower=null;
 this.entBox.innerHTML="";
 this._createSelf();
 this.setStyle(this._cstyle);
}



 
dhtmlXTabBar.prototype.setImagePath = function(path){
 this._imgPath=path;
}




 
dhtmlXTabBar.prototype.loadXMLString=function(xmlString,afterCall){
 this.XMLLoader=new dtmlXMLLoaderObject(this._parseXML,this);
 this.waitCall=afterCall||0;
 this.XMLLoader.loadXMLString(xmlString);
};
 
 dhtmlXTabBar.prototype.loadXML=function(file,afterCall){
 this.XMLLoader=new dtmlXMLLoaderObject(this._parseXML,this);
 this.waitCall=afterCall||0;
 this.XMLLoader.loadXML(file);
}


 dhtmlXTabBar.prototype._getXMLContent=function(node){
 var text="";
 for(var i=0;i<node.childNodes.length;i++)
{
 var z=node.childNodes[i];
 text+=(z.nodeValue===null?"":z.nodeValue);
}
 return text;
}
 
 dhtmlXTabBar.prototype._parseXML=function(that,a,b,c,obj){
 var selected="";
 if(!obj)obj=that.XMLLoader;

 var atop=obj.getXMLTopNode("tabbar");
 var arows = obj.doXPath("//row",atop);
  that._hrfmode=atop.getAttribute("hrefmode")||that._hrfmode;
   that._margin =atop.getAttribute("margin")||that._margin;
 that._align =atop.getAttribute("align")||that._align;
 that._offset =atop.getAttribute("offset")||that._offset;

 var acs=atop.getAttribute("tabstyle");
 if(acs)that.setStyle(acs);

 acs=atop.getAttribute("skinColors");
 if(acs)that.setSkinColors(acs.split(",")[0],acs.split(",")[1]);
  for(var i=0;i<arows.length;i++){
 var atabs = obj.doXPath("./tab",arows[i]);
 for(var j=0;j<atabs.length;j++){
 var width=atabs[j].getAttribute("width");
 var name=that._getXMLContent(atabs[j]);
 var id=atabs[j].getAttribute("id");
 that.addTab(id,name,width,"",i);
 if(atabs[j].getAttribute("selected"))selected=id;

  if(that._hrfmode)
 that.setContentHref(id,atabs[j].getAttribute("href"));
 else
   for(var k=0;k<atabs[j].childNodes.length;k++)
 if(atabs[j].childNodes[k].tagName=="content")
 that.setContentHTML(id,that._getXMLContent(atabs[j].childNodes[k]));
 

}
}
 if(selected)that.setTabActive(selected);
 if(that.dhx_xml_end)that.dhx_xml_end(that);
}

 
 dhtmlXTabBar.prototype.setOnLoadingEnd=function(func){
 if(typeof(func)=="function")
 this.dhx_xml_end=func;
 else
 this.dhx_xml_end=eval(func);
};

  
 dhtmlXTabBar.prototype.setHrefMode=function(mode){
 this._hrfmode=mode;
}
 
 dhtmlXTabBar.prototype.setContentHref=function(id,href){
 if(!this._hrefs)this._hrefs=new Array();
 this._hrefs[id]=href;

 switch(this._hrfmode){
 case "iframe":
 if(!this._glframe){
 var z=document.createElement("DIV");
 z.style.width='100%';
 z.style.height='100%';
 z.innerHTML="<iframe frameborder='0' width='100%' height='100%' src='about:blank'></iframe>";
 this._glframe=z.childNodes[0];
 this._conZone.appendChild(this._glframe);
}
 break;
 case "iframes":
 var z=document.createElement("DIV");
 z.style.width='100%';
 z.style.height='100%';
 z.style.display='none';
 z.innerHTML="<iframe frameborder='0' width='100%' height='100%' src='about:blank'></iframe>";
 z.childNodes[0].src=href;
 this.setContent(id,z);
 this._conZone.appendChild(z);
 break;
 case "ajax":
 var z=document.createElement("DIV");
 z.style.width='100%';
 z.style.height='100%';
 this.setContent(id,z);
 break;
}
}

 
 dhtmlXTabBar.prototype.tabWindow=function(tab_id){
 if((this._hrfmode=="iframes")||(this._hrfmode=="iframe"))
 return(this._content[tab_id]?this._content[tab_id].childNodes[0].contentWindow:null);
}

 dhtmlXTabBar.prototype._ajaxOnLoad=function(obj,a,b,c,loader){
 var z=loader.getXMLTopNode("content");
 var id=z.getAttribute("tab");
 obj._content[id].innerHTML= obj._getXMLContent(z);

  obj.adjustSize();
 }

 

 
 dhtmlXTabBar.prototype.setOnSelectHandler=function(func){
 if(typeof(func)=="function")
 this._onsel=func;
 else
 this._onsel=eval(func);
}
 
 dhtmlXTabBar.prototype.setContent=function(id,nodeId){
 if(typeof(nodeId)=="string")
 nodeId=document.getElementById(nodeId);

 if(!nodeId)return;

 this._content[id]=nodeId;
 if(nodeId.parentNode)nodeId.parentNode.removeChild(nodeId);
 nodeId.style.position="absolute";
 nodeId.style.visibility="hidden";
 nodeId.style.display="block";
 nodeId.style.top="0px";nodeId.style.top="0px";
 this._conZone.appendChild(nodeId);
 if((this._lastActive)&&(this._lastActive.idd==id))this._setContent(this._lastActive);
}
 
 dhtmlXTabBar.prototype._setContent=function(tab){
  if(this._hrfmode)
 switch(this._hrfmode){
 case "iframe":
 this._glframe.src=this._hrefs[tab.idd];
 return;
 break;
 case "iframes":
 if(this._lastIframe)
 this._lastIframe.style.visibility="hidden";
 this._content[tab.idd].style.visibility="";
 this._lastIframe=this._content[tab.idd];
 return;
 break;
 case "ajax":
 var z=this._content[tab.idd];
 if(!z._loaded){
 z.innerHTML="<div class='dhx_ajax_loader'><img src='"+this._imgPath+"loading.gif' />&nbsp;Loading...</div>";
(new dtmlXMLLoaderObject(this._ajaxOnLoad,this,true)).loadXML(this._hrefs[tab.idd]);
 z._loaded=true;
}
 break;
}
 

 if((this._lastActive)&&(this._content[this._lastActive.idd]))
 this._content[this._lastActive.idd].style.visibility='hidden';

 if(this._content[tab.idd])
 this._content[tab.idd].style.visibility='';

  this.adjustSize();
 }
 
 dhtmlXTabBar.prototype.setContentHTML=function(id,html){
 var z=document.createElement("DIV");
 z.style.width="100%";
 z.style.height="100%";
 z.style.overflow="auto";
 z.innerHTML=html;
 this.setContent(id,z);
}

 
 dhtmlXTabBar.prototype.setStyle=function(name){
 if(this._styles[name]){
 this._cstyle=name;
 this._conZone.style.backgroundColor=this._styles[this._cstyle][11];
}
}


  dhtmlXTabBar.prototype.allignToContainer=function(id,mode){
 if(id)
 this._conZone.style.display='none';

 if(typeof(id)!="object")
 id=document.getElementById(id);
 if(!id)return;

 this.entBox.style.position='absolute';
 document.body.appendChild(this.entBox);

 switch(mode){
 case "t":
 this.entBox.style.top=getAbsoluteTop(id)-this.entBox.offsetHeight+"px";
 this.entBox.style.left=getAbsoluteLeft(id)-this.entBox.offsetWidth+"px";
 break;
}
}

dhtmlXTabBar.prototype.setTabBarStyle = function(name){
 this._tbst=name;
}
 
 
 dhtmlXTabBar.prototype.enableContentZone=function(mode){
 this._conZone.style.display=convertStringToBoolean(mode)?"":'none';
}

 
 dhtmlXTabBar.prototype.setSkinColors=function(a_tab,p_tab){
 this._styles[this._cstyle][10]=p_tab;
 this._styles[this._cstyle][11]=a_tab;
 this._conZone.style.backgroundColor=a_tab;
}

 
dhtmlXTabBar.prototype.getActiveTab=function(){
 if(this._lastActive)return this._lastActive.idd;
 return null;
}
 
dhtmlXTabBar.prototype.goToNextTab=function(){
 if(this._lastActive){
 if(this._lastActive.nextSibling.idd){
 this._setTabActive(this._lastActive.nextSibling);
 return this._lastActive.nextSibling.idd;
}
 else
 if(this._lastActive.parentNode.nextSibling){
 var arow=this._lastActive.parentNode.nextSibling;
 this._setTabActive(arow.childNodes[0]);
 return arow.childNodes[0].idd;
}
}
 return null;
}
 
dhtmlXTabBar.prototype.goToPrevTab=function(){
 if(this._lastActive){
 if(this._lastActive.previousSibling){
 this._setTabActive(this._lastActive.previousSibling);
 return this._lastActive.idd;
}
 else
 if(this._lastActive.parentNode.previousSibling){
 var arow=this._lastActive.parentNode.previousSibling;
 this._setTabActive(arow.childNodes[arow.tabCount-1]);
 return this._lastActive.idd;
}
}
 return null;
}

  
dhtmlXTabBar.prototype.enableAutoSize=function(autoWidth,autoHeight){
 this._ahdj=convertStringToBoolean(autoHeight);
 this._awdj=convertStringToBoolean(autoWidth);
}

 
dhtmlXTabBar.prototype.setSize=function(width,height,contentZone){
 if(contentZone){
  if(!this._vMode)
 height+=20;
 else
  width+=20;
}

 this.height=height+"px";
 this.width=width+"px";

 this._lineA.style[this._vMode?"left":"top"]=(this._bMode?0:(this._height+2))+"px";
 this._lineA.style[this._vMode?"height":"width"]=this[this._vMode?"height":"width"];

  if(this._vMode){
 for(var i=0;i<this.rows.length;i++)
 this.rows[i].style.height=parseInt(this.height)+"px";

 this._conZone.style.height=height;
}
 else
 {
 this._conZone.style.width=this.width;
 for(var i=0;i<this.rows.length;i++)
 this.rows[i].style.width=parseInt(this.width)+"px";
}
 for(var i=0;i<this.rows.length;i++)
 this._redrawRow(this.rows[i]);
 this._setSizes();

}

dhtmlXTabBar.prototype.adjustSize=function(){
 var flag=false;var x=this._conZone.offsetWidth;var y=this._conZone.offsetHeight;
 if((this._ahdj)&&(this._conZone.scrollHeight>this._conZone.offsetHeight)){
 y=this._conZone.scrollHeight;
 flag=true;
}

 if((this._awdj)&&(this._conZone.scrollWidth>this._conZone.offsetWidth)){
 x=this._conZone.scrollWidth;
 flag=true;
}
 if(flag)this.setSize(x,y,true);
}
 

