var userAgent = navigator.userAgent.toLowerCase();
var is_safari = userAgent.indexOf("safari")>=0;
var is_opera = userAgent.indexOf('opera') != -1 && opera.version();
var is_moz = (navigator.product == 'Gecko') && userAgent.substr(userAgent.indexOf('firefox') + 8, 3);
var is_chrome = userAgent.indexOf("chrome")>=0;
var is_ie = (userAgent.indexOf('msie') != -1 && !is_opera) && userAgent.substr(userAgent.indexOf('msie') + 5, 3);

function getOpener() {
   if(is_moz){
      return window.opener;
   } else if(is_chrome){
      return window.opener;
   } else {
      return parent.dialogArguments;
   }
}

  
 String.prototype.trim = function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
  }

function openWindow(URL, parent, x, y, width, height)
{
  if(is_ie){
     window.showModalDialog(URL,parent,"edge:raised;scroll:0;status:0;help:0;resizable:0;dialogWidth:"+width+"px;dialogHeight:"+height+"px;dialogTop:"+y+"px;dialogLeft:"+x+"px",true);
  }
  else {
	 var f = "height="+height+",width="+width+",status=0,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+y+",left="+x+",resizable=no,modal=yes,dependent=yes,dialog=yes,minimizable=no";
     window.open(URL, parent, f, true);
  }
}


function selectDate(formName, elementId, elementName){
    var x_selected =  document.getElementById(elementId);
    var url=contextPath+"/scripts/datepicker/chooseDate.jsp?formName="+formName+"&elementId="+elementId+"&elementName="+elementName;
   
    var x=100;
    var y=100;
    if(is_ie) {
	    x=document.body.scrollLeft+event.clientX-event.offsetX-100;
	    y=document.body.scrollTop+event.clientY-event.offsetY-50;
     }
     openWindow(url,self,x, y, 160, 165);
}

 

 function selectUsers(formName, elementId, elementName, deptId, roleId){
    var x_selected =  document.getElementById(elementId);
    var url=contextPath+"/mx/roleUserSelection?formName="+formName+"&elementId="+elementId+"&elementName="+elementName+"&deptId="+deptId+"&roleId="+roleId;
    if(x_selected != null && x_selected.value != ""){
	    url = url + "&selected="+x_selected.value+ "&x_selected="+x_selected.value;
    }
    var x=200;
    var y=200;
    if(is_ie) {
	    x=document.body.scrollLeft+event.clientX-event.offsetX-100;
	    y=document.body.scrollTop+event.clientY-event.offsetY+70;
     }
     openWindow(url,self,x, y, 458, 380);
 }

function selectDept(formName, elementId, elementName){
    var x_selected =  document.getElementById(elementId);
    var url=contextPath+"/mx/deptSelection?code=SYS_ORG&formName="+formName+"&elementId="+elementId+"&elementName="+elementName;
    if(x_selected != null && x_selected.value != ""){
	    url = url + "&selected="+x_selected.value+ "&x_selected="+x_selected.value;
    }
    var x=100;
    var y=100;
    if(is_ie) {
	    x=document.body.scrollLeft+event.clientX-event.offsetX-200;
	    y=document.body.scrollTop+event.clientY-event.offsetY-200;
     }
     openWindow(url,self,x, y, 585, 610);
}

function selectDept(formName, elementId, elementName, exclusive){
    var x_selected =  document.getElementById(elementId);
    var url=contextPath+"/mx/deptSelection?code=SYS_ORG&formName="+formName+"&elementId="+elementId+"&elementName="+elementName;
    if(x_selected != null && x_selected.value != ""){
	    url = url + "&selected="+x_selected.value+ "&x_selected="+x_selected.value;
    }
	if(exclusive != null){
		url = url + "&exclusive="+exclusive;
	}
    var x=100;
    var y=100;
    if(is_ie) {
	    x=document.body.scrollLeft+event.clientX-event.offsetX-200;
	    y=document.body.scrollTop+event.clientY-event.offsetY-200;
     }
     openWindow(url,self,x, y, 585, 610);
}

function selectTreeNode(treeCode, formName, elementId, elementName, exclusive){
    var x_selected =  document.getElementById(elementId);
    var url = contextPath+"/mx/treeSelection?code="+treeCode+"&formName="+formName+
		        "&elementId="+elementId+"&elementName="+elementName;
    if(x_selected != null && x_selected.value != ""){
	    url = url + "&selected="+x_selected.value+ "&x_selected="+x_selected.value;
    }
	if(exclusive != null){
		url = url + "&exclusive="+exclusive;
	}
    var x=100;
    var y=100;
    if(is_ie) {
	    x=document.body.scrollLeft+event.clientX-event.offsetX-200;
	    y=document.body.scrollTop+event.clientY-event.offsetY-200;
     }
     openWindow(url,self,x, y, 585, 610);
}

 
 	 
function clearSelected(elementId, elementName){
    if(document.getElementById(elementId) != null){
	    document.getElementById(elementId).value="" ;
    }
    if(document.getElementById(elementName) != null){
	    document.getElementById(elementName).value="" ;
    }
 }
 
function selectUser(formName, elementId, elementName){
	var x_selected =  document.getElementById(elementId);
    var url = contextPath+"/mx/mgt/identity/choose?elementId="+elementId+"&elementName="+elementName;
    if(x_selected != null && x_selected.value != ""){
	    url = url + "&selected="+x_selected.value+ "&x_selected="+x_selected.value;
    }
    var x=100;
    var y=100;
    if(is_ie) {
	    x=document.body.scrollLeft+event.clientX-event.offsetX-200;
	    y=document.body.scrollTop+event.clientY-event.offsetY-200;
     }
    openWindow(url,self,x, y, 625, 480);
}


function openMMWindow(url, width, height){
	var x=100;
    var y=100;
    if(is_ie) {
	    x=document.body.scrollLeft+event.clientX-event.offsetX-200;
	    y=document.body.scrollTop+event.clientY-event.offsetY-200;
     }
    openWindow(url, self, x, y, width, height);
}


function selectUser(elementId, elementName){
	var x_selected =  document.getElementById(elementId);
    var url = contextPath+"/mx/mgt/identity/chooseUser?elementId="+elementId+"&elementName="+elementName;
    if(x_selected != null && x_selected.value != ""){
	    url = url + "&selected="+x_selected.value+ "&x_selected="+x_selected.value;
    }
    var x=100;
    var y=100;
    if(is_ie) {
	    x=document.body.scrollLeft+event.clientX-event.offsetX-200;
	    y=document.body.scrollTop+event.clientY-event.offsetY-200;
     }
    openWindow(url,self,x, y, 625, 480);
}

function selectRole(elementId, elementName){
	var x_selected =  document.getElementById(elementId);
    var url = contextPath+"/mx/mgt/identity/chooseRole?elementId="+elementId+"&elementName="+elementName;
    if(x_selected != null && x_selected.value != ""){
	    url = url + "&selected="+x_selected.value+ "&x_selected="+x_selected.value;
    }
    var x=100;
    var y=100;
    if(is_ie) {
	    x=document.body.scrollLeft+event.clientX-event.offsetX-200;
	    y=document.body.scrollTop+event.clientY-event.offsetY-200;
     }
    openWindow(url,self,x, y, 585, 610);
}

function displayLayer(layerId){
	 var obj = document.getElementById(layerId);
	 if(obj.style.display == "block"){
	   obj.style.display = 'none';
	 }else{
	   if(obj.style.display == "none"){
		 obj.style.display = 'block';
		}
	 }
}

function displayLayer(layerId, x){
	 var obj = document.getElementById(layerId);
	 obj.style.display == x;
}

function disableForm(iForm){
	try{
	 for (var i=0; i<iForm.elements.length; i++) {
	  var elem = iForm.elements[i];
	  if (elem.getAttribute("type") != null) {
		if( elem.getAttribute("type") == "text" ||  elem.getAttribute("type") == "textarea" ){
			  elem.readOnly=true;
			  elem.disabled=true;
		 }
	  }
	 }
	 var x = document.getElementsByTagName("select");
	 for (var i=0;i<x.length;i++) {
		 x[i].disabled=true;
	 }
    }catch(exe){
	}
}

   function addElement(elementId, targetElementId) {
        var list = document.getElementById(elementId);
		
        for (i = 0; i < list.length; i++) {
            if (list.options[i].selected) {
                var value = list.options[i].value;
                var text = list.options[i].text;
                addToList(targetElementId, value, text);
				list.remove(i);
				i=i-1;
            }
        }

    }

    function addToList(targetElementId, value, text) {
        var list = document.getElementById(targetElementId);
        if (list.length > 0) {
            for (k = 0; k < list.length; k++) {
                if (list.options[k].value == value) {
                    return;
                }
            }
        }

        var len = list.options.length;
        list.length = len + 1;
        list.options[len].value = value;
        list.options[len].text = text;
    }

    function removeElement(elementId, targetElementId) {
        var list = document.getElementById(elementId);
		var slist = document.getElementById(targetElementId);
        if (list.length == 0 || list.selectedIndex < 0 || list.selectedIndex >= list.options.length)
            return;

        for (i = 0; i < list.length; i++) {
            if (list.options[i].selected) {
			    var value = list.options[i].value;
                var text = list.options[i].text;
                list.options[i] = null;
                i--;
				var len = slist.options.length;
				slist.length = len+1;
                slist.options[len].value = value;
                slist.options[len].text = text;				
            }
        }
    }

  function selectElement(elementId) {
    var list = document.getElementById(elementId);
    var len= list.length;
	var result = "";
	var names = "";
	for (var i=0;i<len;i++) {
      result = result + list.options[i].value;
	  names = names + list.options[i].text;
	  if(i < (len - 1)){
		  result = result + ",";
		  names = names + ",";
	   }
    }
    return result;
  }