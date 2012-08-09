var Element = {}; 
//创建节点对象
Element.CreateNode = function(tag) { 
  return document.createElement(tag) 
} 
//创建节点对象html内容
Element.CreateHtmlNode = function(tag, text) { 
  var node = Element.CreateNode(tag);
  if (text) node.innerHTML = text; 
  return node; 
} 
//创建节点对象text内容
Element.CreateTextNode = function(tag, text) { 
  var node = Element.CreateNode(tag); 
  if (text) node.appendChild = document.createTextNode(text); 
  return node; 
} 
//增加行记录
function RowAdd(tableObj, tdArray) {
  var tr = Element.CreateNode("tr"); 
  var td;
  
  for(var i=0;i<tdArray.length;i++){
    td = Element.CreateHtmlNode("td", tdArray[i]); 
    tr.appendChild(td);
  }  
  
  tableObj.tBodies[0].appendChild(tr); 
} 
//删除行记录  
function RowDelete(tableObj, tdIdx) { 
  var nodeList = tableObj.tBodies[0]; 
  if (nodeList.childNodes.length > 0)  { 
    nodeList.removeChild(nodeList.childNodes[tdIdx]); 
  }
} 