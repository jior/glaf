//ΪHTML��������TOOLTIP֧�֣�����eΪ��Ҫ��ӵ�TOOLTIP֧�ֵ���������Ϊdocument.body��Ϊ����ҳ��

//���֧�֣���ΪĳDIV���Ϊ��DIV�д���TOOLTIP���Ե�Ԫ���ṩ֧�֡�

function addTipSupport(e) {
  e.onmouseover = showTip;
  e.onmouseout = hideTip;
  e.onmousemove = showTip;
}
 

//��ÿؼ��ľ���λ�ã��������Ͻ�����
function getElePos(e){  
 var t=e.offsetTop; 
 var l=e.offsetLeft; 
 while(e=e.offsetParent){ 
 t+=e.offsetTop; 
 l+=e.offsetLeft; 
 } 
 
 return [l, t];
} 

//��ʾTOOLTIP

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

//����TOOLTIP

function hideTip() {
 altlayer.style.visibility = 'hidden';
}

