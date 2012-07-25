var Element = {}; 
//�����ڵ����
Element.CreateNode = function(tag) { 
  return document.createElement(tag) 
} 
//�����ڵ����html����
Element.CreateHtmlNode = function(tag, text) { 
  var node = Element.CreateNode(tag);
  if (text) node.innerHTML = text; 
  return node; 
} 
//�����ڵ����text����
Element.CreateTextNode = function(tag, text) { 
  var node = Element.CreateNode(tag); 
  if (text) node.appendChild = document.createTextNode(text); 
  return node; 
} 
//�����м�¼
function RowAdd(tableObj, tdArray) {
  var tr = Element.CreateNode("tr"); 
  var td;
  
  for(var i=0;i<tdArray.length;i++){
    td = Element.CreateHtmlNode("td", tdArray[i]); 
    tr.appendChild(td);
  }  
  
  tableObj.tBodies[0].appendChild(tr); 
} 
//ɾ���м�¼  
function RowDelete(tableObj, tdIdx) { 
  var nodeList = tableObj.tBodies[0]; 
  if (nodeList.childNodes.length > 0)  { 
    nodeList.removeChild(nodeList.childNodes[tdIdx]); 
  }
} 