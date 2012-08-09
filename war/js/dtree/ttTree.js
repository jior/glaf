function getXMLHttpObject(){
  var C=null;
  try{
    C=new ActiveXObject("Msxml2.XMLHTTP");
  }catch(e){
    try{
      C=new ActiveXObject("Microsoft.XMLHTTP");
    }catch(sc){
      C=null;
    }
  }
  if(!C && typeof XMLHttpRequest!="undefined"){
    C=new XMLHttpRequest();
  }
  return C;
}
/**
 * 树节点对象
 */
function Node(id, parentId, name, url, title, btype){
  this.id = id;//节点id
  this.parentId = parentId;//父节点id
  this.parentNode = null;//父节点
  this.name = name;//节点名称
  this.title = title;//节点显示名称
  this.childNodes = new Array(0);  
  this.isBottom = false;//最下层节点
  this.rootBottom = false;//根是否为最底层
  this.isOpen = false;//是否打开子树
  this.btype = btype;  
  this.url = url;//节点url  
};


/**
 * 构造函数
 */
function ttTree(rootNode,suburl,config){
  this.nodes = new Array(0);//节点列表
  this.rootNode = rootNode;//根节点
  this.debugMode = false;//调试模式
  this.target = "_self";//弹出方式
  this.baseUrl = "";
  if(suburl)
  	this.subTreeUrl = suburl;
  else
	this.subTreeUrl = "/sys/tree.do?method=getSubTree&id=";
  for(var name in config) this[name] = config[name];	
  this.obj = "tree";//
  this.lastClick = rootNode.id;//上次点击
  this.icon = {
    root		: context + '/js/dtree/img/base.gif',
    folder		: context + '/js/dtree/img/folder.gif',
    folderOpen	: context + '/js/dtree/img/folderopen.gif',
    node		: context + '/js/dtree/img/page.gif',
    empty		: context + '/js/dtree/img/empty.gif',
    line		: context + '/js/dtree/img/line.gif',
    join		: context + '/js/dtree/img/join.gif',
    joinBottom	: context + '/js/dtree/img/joinbottom.gif',
    plus		: context + '/js/dtree/img/plus.gif',
    plusBottom	: context + '/js/dtree/img/plusbottom.gif',
    minus		: context + '/js/dtree/img/minus.gif',
    minusBottom	: context + '/js/dtree/img/minusbottom.gif',
    nlPlus		: context + '/js/dtree/img/nolines_plus.gif',
    nlMinus		: context + '/js/dtree/img/nolines_minus.gif',
    loading		: context + '/js/dtree/img/loading.gif'
  };
  this.indent = [];//记录每层节点是否为最底层
};

/**
 * 画出树结构
 */
ttTree.prototype.draw = function(id, hideroot, depth){
  var rootDiv = null;
  if(typeof(id) == 'object'){
    rootDiv = id;
  }else{
    rootDiv = document.getElementById(id);
  }
  rootDiv.className='ttTree';
  
  //根节点
  var nodeDiv = document.createElement("DIV");
  nodeDiv.id = "Node_" + this.rootNode.id;
  nodeDiv.name = 'Node_' + this.rootNode.id;
  nodeDiv.className = 'node';
  
  var img = document.createElement("IMG");//图标
  img.src = this.icon.folder;
  if(!hideroot) nodeDiv.appendChild(img);
  
  if(!this.filter){
	  var href = document.createElement("A");//链接
	  href.id= "link_"+this.rootNode.id;
	  href.className = 'node';
	  href.href = "javascript:" + this.obj + ".highLightNode('"+ this.rootNode.id +"');";
	  href.href +="goto('"+ this.baseUrl+this.rootNode.id +"',{id:"+this.rootNode.id+",name:'"+this.rootNode.name+"'});";
 	  href.innerText = this.rootNode.name;
	  href.title = this.rootNode.title;
	  nodeDiv.appendChild(href);
  } else {
	  var dSpan = document.createElement("span");
	  dSpan.style.cursor = "pointer";
	  dSpan.innerText = this.rootNode.name;
	  dSpan.data = {id:this.rootNode.id,name:this.rootNode.name,title:this.rootNode.title,btype:this.rootNode.btype,obj:this.obj};
	
	  dSpan.onclick = function(){ eval('freshLef(this)'); }
	  	
      nodeDiv.appendChild(dSpan);
  }
  rootDiv.appendChild(nodeDiv);
  
  var childNodeDiv = document.createElement("DIV");
  childNodeDiv.id = 'Child_' + this.rootNode.id;
  childNodeDiv.name = 'Child_' + this.rootNode.id;
  childNodeDiv.className = 'clip';
  childNodeDiv.style.display="block";
  rootDiv.appendChild(childNodeDiv);

  //展开根节点下所有子节点  
  
  this.expendNode(this.rootNode.parentId, this.rootNode.id, depth); 
}
/**
 * 画子节点
 */
ttTree.prototype.drawNodes = function(parentNodeDiv, node, depth){
  this.debug("depth:"+depth);  
  this.nodes[this.nodes.length] = node;
  if(node.childNodes.length==0){//没有子节点
    var nodeDiv = document.getElementById("Child_"+node.parentId);
	if(nodeDiv==null)return;
    nodeDiv.removeChild(parentNodeDiv);
    //更改当前节点图标为page
    this.changeNodeImg2(node);
    return;//退出方法
  }
  this.getIndent(node);
  this.debug("indent.length:"+this.indent.length);
  
  for(var i=0; i< node.childNodes.length; i++){//遍历子节点
    var childNode = node.childNodes[i];
    childNode.parentNode=node;    
    
    if(i==(node.childNodes.length-1)){//设为最底层
      childNode.isBottom = true;      
    }
    if(childNode.isBottom && childNode.parentId==this.rootNode.id){
      childNode.rootBottom=true;
    }else{
      childNode.rootBottom=node.rootBottom;
    }
    this.debug("isBottom:"+childNode.isBottom);
    this.debug("rootBottom:"+childNode.rootBottom);
    
    var nodeDiv = document.createElement("DIV");//创建子节点
    nodeDiv.id = "Node_" + childNode.id;
    nodeDiv.name = "Node_" + childNode.id;
    nodeDiv.className = "node";
    parentNodeDiv.appendChild(nodeDiv);//加入到父节点
        
    for(var n=this.indent.length-1; n>-1; n--){
      var img = document.createElement("IMG");//空格线
      img.src = (this.indent[n]==1)?this.icon.empty:this.icon.line;
      nodeDiv.appendChild(img);
    }
    
    var href = document.createElement("A")//链接
    href.className = "node";
    href.id= "a_"+childNode.id;
    href.href = "javascript:"+ this.obj +".expendNode("+ childNode.parentId +","+ childNode.id +","+ (depth+1) +");";
    var img = document.createElement("IMG");//第一个图标
    img.src = childNode.isBottom?this.icon.plusBottom:this.icon.plus;
    img.id = "img1_"+childNode.id;
    img.border = "0";
    href.appendChild(img);
    nodeDiv.appendChild(href);

    img = document.createElement("IMG");//第二个图标
    img.id = "img2_"+childNode.id;
    img.src = this.icon.folder;
    img.border = "0";
    nodeDiv.appendChild(img);

    if(!this.filter){
      href = document.createElement("A");//链接
      href.id= "link_"+childNode.id;
      href.className = "node";
      href.href = "javascript:" + this.obj + ".highLightNode('"+ childNode.id +"');";
      href.href +="goto('"+ this.baseUrl+childNode.id +"',{id:"+childNode.id+",name:'"+childNode.title+"'});";
	  href.target = this.target;
	  href.innerText = childNode.name;
      nodeDiv.appendChild(href);
  	} else {
		var dSpan = document.createElement("span");
	    dSpan.style.cursor = "pointer";
		dSpan.innerText = childNode.name;
	    dSpan.data = {id:childNode.id,name:childNode.name,title:childNode.title,btype:childNode.btype,obj:this.obj};
	  	dSpan.onclick = function(){eval('freshLeft(this)'); }
	  	
      	nodeDiv.appendChild(dSpan);
  	}
    //href.title = childNode.title;
    
    var childNodeDiv = document.createElement("DIV");
    childNodeDiv.id = "Child_" + childNode.id;
    childNodeDiv.name = "Child_" + childNode.id;
    childNodeDiv.className = "clip";
    childNodeDiv.style.display="block";
    parentNodeDiv.appendChild(childNodeDiv);//加入到父节点
  }
  this.indent=[];//清空
  var rootDiv = document.getElementById("ttTree");
  this.debug(rootDiv.outerHTML);
}
/**
 * 高亮度选择的节点
 */
ttTree.prototype.highLightNode = function(id){
  this.debug("click "+ id);
  
  //清除上次点击样式
  var href = document.getElementById("link_"+this.lastClick);
  href.className = "node";
  
  href = document.getElementById("link_"+id);
  if(href!=null){
    href.className = "nodeSel";
  }
  this.lastClick = id;
}
/**
 * 获取子菜单
 */
ttTree.prototype.getNodes = function(node, depth){  
  if(this.filter){
  	this.getXmlNodes(node,depth);
  	return;
  }
  var xmlhttp = getXMLHttpObject();
  if(xmlhttp){
    var childNodeDiv = document.getElementById("Child_"+node.id);
    var nodeDiv = document.getElementById("Node_"+node.id);
    //没有子节点或已经装载过子节点
    if(childNodeDiv==null || node.childNodes.length>0){
      return;
    }
    var thisObj = this;
    
    xmlhttp.onreadystatechange=function(){//激活事件            
      if(xmlhttp.readyState==4 && xmlhttp.status==200){//有返回
        if(node.childNodes.length>0){
          return;
        }
        var img = document.getElementById(node.id);
        nodeDiv.removeChild(img);//清除等待图标
        try{
          eval(xmlhttp.responseText);
        }catch(e){
          thisObj.debug(e);
          return;
        }
        thisObj.drawNodes(childNodeDiv, node, depth);
      }else{
      	//提示等待
      	var img = document.getElementById(node.id);
      	if(img==null){
      	  img = document.createElement("IMG");
      	  img.id = node.id;
      	  img.src = thisObj.icon.loading;
      	}
        nodeDiv.appendChild(img);
      }
    };
    xmlhttp.open("GET", context+ this.subTreeUrl + node.id , true);
    xmlhttp.send(null);
  }
}
ttTree.prototype.getXmlNodes = function(node, depth){
	var childNodeDiv = document.getElementById("Child_"+node.id);
    var nodeDiv = document.getElementById("Node_"+node.id);
    //没有子节点或已经装载过子节点
    if(childNodeDiv==null || node.childNodes.length>0){
      return;
    }
    this.filter.setFilter(this.filter.objName + "." + this.parentcode, node.id, "=");
    
    var tRs = this.filter.txSend();
    var tRows = tRs.selectNodes("//z:row");
    var tRow,tid,tname,tparentid,turl,ttitle;
    for(var i = 0; i < tRows.length; i++){
    	tRow = tRows[i];
    	tid = tRow.getAttribute("id");
    	tname = tRow.getAttribute("name");
    	tparentid = tRow.getAttribute("parentid");
    	turl = tRow.getAttribute("url");
    	ttitle = tRow.getAttribute("title");
    	tbtype = tRow.getAttribute("belong");
    	node.childNodes[node.childNodes.length] = new Node(tid,tparentid,tname,turl,ttitle,tbtype);
    }
    var img = document.getElementById(node.id);
    if(img)     nodeDiv.removeChild(img);//清除等待图标
    
     this.drawNodes(childNodeDiv, node, depth);
}
/**
 * 打开下级树
 */
ttTree.prototype.expendNode = function(parentId, nodeId, depth){
  var parentNode=null;
  var node = null;
  this.debug("this.nodes:"+this.nodes.length);
  for(var i=0; i<this.nodes.length; i++){
  	this.debug("node:"+this.nodes[i].id);
  	if(parentId == this.nodes[i].id){
  	  parentNode = this.nodes[i];
  	  break;
  	}
  }
  if(parentNode!=null){//不是根节点 
    this.debug("parentNode:"+parentNode.id);
    for(var i=0; i<parentNode.childNodes.length; i++){
  	  if(nodeId == parentNode.childNodes[i].id){
  	    node = parentNode.childNodes[i];
  	    break;
  	  }
    }
  }else{
    node = this.rootNode;
  }
  
  this.debug("node:"+node.id);
  this.debug("node.childNodes.length:"+node.childNodes.length);  
  	  
  if(node.childNodes.length>0){//已经加载过子节点
  	var nodeDiv = document.getElementById("Child_"+node.id);
  	this.debug("display:"+nodeDiv.style.display);
  	if(nodeDiv.style.display=='block'){
  	  node.isOpen=false;
  	  nodeDiv.style.display='none';//收起子节点
  	}else{
  	  node.isOpen=true;
  	  nodeDiv.style.display='block';//展开子节点
  	}
  	this.changeNodeImg(node);//更改图标
  }else{
  	node.isOpen=true;
  	this.changeNodeImg(node);//更改图标

    this.getNodes(node, depth);
  }  
}
/**
 * 
 */
ttTree.prototype.changeNodeImg = function(node){
  var img = document.getElementById("img1_"+node.id);//图标1
  if(img==null){
    return;
  }
  if(node.isBottom){//最底层
    if(node.isOpen){
      img.src=this.icon.minusBottom;
    }else{
      img.src=this.icon.plusBottom;
    }
  }else{
    if(node.isOpen){
      img.src=this.icon.minus;
    }else{
      img.src=this.icon.plus;
    }
  }
  
  img = document.getElementById("img2_"+node.id);//图标2
  if(node.isOpen){
    img.src=this.icon.folderOpen;
  }else{
    img.src=this.icon.folder;
  }
}
/**
 * 
 */
ttTree.prototype.changeNodeImg2 = function(node){
  var img = document.getElementById("img1_"+node.id);//图标1
  if(img==null){
    return;
  }
  
  if(node.isBottom){//最底层
    img.src=this.icon.joinBottom;
  }else{
    img.src=this.icon.join;    
  }
  
  img = document.getElementById("img2_"+node.id);//图标2
  img.src=this.icon.node;
  
  //清除链接
  var href = document.getElementById("a_"+node.id);
  href.href="#";
  href.style.cursor="hand";
}
/**
 * 关闭下级树
 */
ttTree.prototype.setDebugMode = function(flag){
  this.debugMode = flag;
}
/**
 * 弹出方式
 */
ttTree.prototype.setTarget = function(target){
  this.target = target;
}
/**
 * 设置url
 */
ttTree.prototype.setBaseUrl = function(url){
  this.baseUrl = url;
}
/**
 * 设置subTreeUrl
 */
ttTree.prototype.setSubTreeUrl = function(url){
  this.subTreeUrl = url;
}
ttTree.prototype.debug = function(msg){
  if(this.debugMode){
    var debuger = document.getElementById("debugbox");
    debuger.innerText = debuger.innerText + "\n" + msg;
  }
}
/**
 * 获取节点的嵌套组成（打印空格或竖线）
 */
ttTree.prototype.getIndent = function(node){
  if(node.id!=this.rootNode.id){//不是根节点
    if(node.isBottom){
      this.indent.push(1);
    }else{
      this.indent.push(0);
    }
    this.debug("indent[" + (this.indent.length-1) + "]:" + this.indent[this.indent.length-1]);
    
    //如果有parent则继续
  	var parentNode = null;
  	for(var i=0; i<this.nodes.length; i++){
  	  if(node.parentId == this.nodes[i].id){
  	    parentNode = this.nodes[i];
  	    break;
  	  }
    }
    if(parentNode!=null){//嵌套循环
      this.getIndent(parentNode);
    }
  }
}