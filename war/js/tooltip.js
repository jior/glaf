//为HTML容器增加TOOLTIP支持，参数e为你要添加的TOOLTIP支持的容器，如为document.body则为整个页面

//添加支持，如为某DIV则仅为该DIV中带有TOOLTIP属性的元素提供支持。

function addTipSupport(e) {
  e.onmouseover = showTip;
  e.onmouseout = hideTip;
  e.onmousemove = showTip;
}
 

//获得控件的绝对位置，返回左上角坐标
function getElePos(e){  
 var t=e.offsetTop; 
 var l=e.offsetLeft; 
 while(e=e.offsetParent){ 
 t+=e.offsetTop; 
 l+=e.offsetLeft; 
 } 
 
 return [l, t];
} 

//显示TOOLTIP

function showTip() { 
 getElePos(event.srcElement);
 if(event.srcElement.tooltip && event.srcElement.tooltip!='') {
  altlayer.style.visibility='visible';
  var cord = getElePos(event.srcElement);
  altlayer.style.left=cord[0]+event.offsetX + 20;
  altlayer.style.top=cord[1]+event.offsetY+25;
  altlayer.innerHTML=event.srcElement.tooltip;
  altlayer.style.zIndex = event.srcElement.style.zIndex + 1;
  }
  //else altlayer.style.visibility='hidden';
}

//隐藏TOOLTIP

function hideTip() {
 altlayer.style.visibility = 'hidden';
}

