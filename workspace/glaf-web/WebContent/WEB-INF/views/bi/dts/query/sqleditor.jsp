<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.query.*"%>
<%@ page import="com.glaf.core.service.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.dts.domain.*"%>
<%@ page import="com.glaf.dts.query.*"%>
<%@ page import="com.glaf.dts.service.*"%>
<%
	 ITableDefinitionService tableDefinitionService = ContextFactory.getBean("tableDefinitionService");
	 TableDefinitionQuery query = new TableDefinitionQuery();
	 query.type(com.glaf.dts.util.Constants.DTS_TASK_TYPE);
	 List<TableDefinition> tables = tableDefinitionService.list(query);
%>
<!DOCTYPE html>
<html>
	<head>
	<title>ͼ�λ���ѯ</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/easyui/themes/default/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/easyui/themes/demo.css" type="text/css"></link>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/icons/styles.css" type="text/css"></link>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>		
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>			
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/map.js"></script>

<style>

*{
	font-size:12px;
}
body {
    font-family:helvetica,tahoma,verdana,sans-serif;
    padding:20px;
    font-size:13px;
    margin:0;
}
h2 {
    font-size:18px;
    color:#333;
    font-weight:bold;
    margin:0;
    margin-bottom:15px;
}
.demo-info{
	background:#FFFEE6;
	color:#8F5700;
	padding:12px;
}
.demo-tip{
	width:24px;
	height:16px;
	float:left;
}

.left {
	width: 120px;
	float: left;
}

.left table {
	background: #E0ECFF;
}

.left td {
	background: #eee;
}

.right {
	float: right;
	width: 570px;
}

.right table {
	background: #E0ECFF;
	width: 100%;
}

.right td {
	background: #fafafa;
	text-align: center;
	height: 200;
}

.right td.drop {
	background: #fafafa;
	width: 110px;
}

.right td.over {
	background: #FBEC88;
}

.item {
	text-align: center;
	border: 1px solid #499B33;
	background: #fafafa;
	width: 100px;
}

.assigned {
	border: 1px solid #BC2A4D;
}

.next table {
	background: #E0ECFF;
	width: 100%;
	text-align: center;
}

.next td {
	background: #fafafa;
	text-align: center;
	width: 150px;
}

.next td.drop {
	background: #fafafa;
	text-align: center;
	height: 20px;
}

.next td.over {
	background: #FBEC88;
}

#navlist
{
border-bottom: 1px solid #ccc;
margin: 0;
padding-bottom: 19px;
padding-left: 15px;
}

#navlist ul, #navlist li
{
display: inline;
list-style-type: none;
margin: 0;
padding: 0;
}

#navlist a:link, #navlist a:visited
{ 
background: #E8EBF0;
border: 1px solid #ccc;
color: #666;
float: left;
font-size: small;
font-weight: normal;
line-height: 14px;
margin-right: 8px;
padding: 2px 10px 2px 10px;
text-decoration: none;
}

#navlist a:link.current, #navlist a:visited.current
{
background: #fff;
border-bottom: 1px solid #fff;
color: #000;
}

#navlist a:hover { color: #f00; }

body.section-1 #navlist li#nav-1 a, 
body.section-2 #navlist li#nav-2 a,
body.section-3 #navlist li#nav-3 a,
body.section-4 #navlist li#nav-4 a
{
background: #fff;
border-bottom: 1px solid #fff;
color: #000;
}

#navlist #subnav-1,
#navlist #subnav-2,
#navlist #subnav-3,
#navlist #subnav-4
{
display: none;
width: 90%;
}

body.section-1 #navlist ul#subnav-1, 
body.section-2 #navlist ul#subnav-2,
body.section-3 #navlist ul#subnav-3,
body.section-4 #navlist ul#subnav-4
{
display: inline;
left: 15px;
position: absolute;
top: 95px;
}

body.section-1 #navlist ul#subnav-1 a, 
body.section-2 #navlist ul#subnav-2 a,
body.section-3 #navlist ul#subnav-3 a,
body.section-4 #navlist ul#subnav-4 a
{
background: #fff;
border: none;
border-left: 1px solid #ccc;
color: #999;
font-size: smaller;
font-weight: bold;
line-height: 10px;
margin-right: 4px;
padding: 2px 10px 2px 10px;
text-decoration: none;
}

#navlist ul a:hover { color: #f00 !important; }

#contents
{
background: #fff;
border: 1px solid #ccc;
border-top: none;
clear: both;
margin: 0px;
padding: 15px;
} 

.m{
cusor:handle;
}

</style>
<script>
function resetDrag(){
	$('.item').draggable({
		revert:true,
		proxy:'clone'
	});	
	
	$('.drop').droppable({
		onDragEnter:function(){
			$(this).addClass('over');
		},
		onDragLeave:function(){
			$(this).removeClass('over');
		},
		onDrop:function(e,source){
			$(this).removeClass('over');			
			if ($(source).hasClass('assigned')){
				$(this).append(source);
			} else {
				var c = $(source).clone().addClass('assigned');
				$(this).empty().append(c);
				c.draggable({
					revert:true
				});
			}
		}
	});	
}
$(document).ready(function(){
	initData();
	
})
window.onerror = killErrors;
function killErrors(){
	return true;
}
 
var userAgent = navigator.userAgent.toLowerCase();
var isSafari = userAgent.indexOf("Safari")>=0;
var is_opera = userAgent.indexOf('opera') != -1 && opera.version();
var is_moz = (navigator.product == 'Gecko') && userAgent.substr(userAgent.indexOf('firefox') + 8, 3);
var is_ie = (userAgent.indexOf('msie') != -1 && !is_opera) && userAgent.substr(userAgent.indexOf('msie') + 5, 3);

function getOpener() {
   if(is_moz){
      return window.opener;
   }
   else{
      return parent.dialogArguments;
   }
}

	   var map = new Map();	   
	   var map2 = new Map();
	   //���¼������div���϶�����
	   
	   function initData(){	 
		   jQuery.ajax({		
				  url: '<%=request.getContextPath()%>/rs/dts/table/list',				 	 
				  dataType: 'json',
				  type: "GET",
				  success: function(data){
				    // var str = '';	
				     for(i=0;i<data.tables.length;i++	){
				     //str += "<tr><td class='item'>"+data.tables[i].tableName+"</td></tr>";				    
				      map.put(data.tables[i].tableName, data.tables[i].columns);
                   	   }
                   	  // jQuery("#tables").html("<tbody>"+str+"</tbody>");
                   	  // alert(jQuery("#tables").html());
                   	   resetDrag();
				  }							  
				});
		}
	
		$(function(){	
			$('#selectedTable').droppable({		
				onDrop:function(e,source){			
					var name = $(source).find('p:eq(0)').html();							
					var tab=document.getElementById('selectedTable');	
					if(map2.get(name)==null){
						 var values = map.get(name);
						  for(j=0;values != null && j<values.length;j++){							   
							  $("#fieldSegments").append('<tr ><td><div class="item"><p>'+name+'.'+values[j].columnName+"</p></div></td></tr>");//����ѡ�ֶ�ѡ� �ֶ�
							  $("#Selfield").append('<tr><td><div class="item"><p>'+name+'.'+values[j].columnName+"</p></div></td></tr>");//��������ѡ� �ֶ�
							  $("#sortField").append('<tr><td><div class="item"><p>'+name+'.'+values[j].columnName+"</p></div></td></tr>");//��������ѡ� �ֶ�
							  $("#join").append('<tr><td><div class="item"><p>'+name+'.'+values[j].columnName+"</p></div></td></tr>");//��������ѡ� �ֶ�      
						  }
				      	map2.put(name, "1");				      
				      }
					 resetDrag();
				}
			});	
			
			$('.drop').droppable({		
			
				onDragEnter:function(){
					$(this).addClass('over');
				},
				onDragLeave:function(){
					$(this).removeClass('over');
				},
				
				onDrop:function(e,source){
					
					$(this).removeClass('over');					
					if ($(source).hasClass('assigned')){
						$(this).append(source);
					} else {
						var c = $(source).clone().addClass('assigned');
						$(this).empty().append(c);
						c.draggable({
							revert:true							
						});
						  map2.put(name, "2");		
					}	
					 												
				}
			});
		});
		
		//������		
	function addHang(obj){	
		var objMyTable = document.getElementById("selectedTable");
	����var index = objMyTable.rows.length;
	����var nextRow = objMyTable.insertRow(index);//Ҫ�������У��������Ǵӵ����ڶ��п�ʼ������
		var newCellCartonNo = nextRow.insertCell();
		var newCellCartonNo2 = nextRow.insertCell();
		newCellCartonNo.setAttribute("className","drop");
		newCellCartonNo.innerHTML = "&nbsp;";
		newCellCartonNo2.innerHTML = '<input type=button onclick="addHang();" style="width: 30px;" value="+"> <input type=button id="a"  onclick="delHang(this);" style="width: 30px;" value="-">';
		resetDrag();
 }	

		//ɾ����
		function delHang(ele){		
				var isVal=$(ele).parent().parent().find('td:eq(0)').text();	//�ж��ı���
				//alert(isVal.length);
				/*
					�����Ϊ�գ�ɾ���˱���ص��ֶ�������������б��е��ֶ�
				*/
				
				//isVal = isVal.replace(/^\s+|\s+$/g,""); 	
			//ȡ���ֶΣ�ѡ�ֶΣ�				
			if(isVal!=""){
				/*
					ֻ�ж���Ҫ������ѡ�ֶΣ��ֶΣ�������������ֵ���Ƿ����ֵ��
				*/
				var av=[];			
				var f=document.getElementById('td');	//�ֶΣ�ѡ�ֶΣ�
				for(var i=0;i<f.rows.length;i++)
				{
					var ff=f.rows[i].cells[1].innerText;//�ֶ�
					var fff=ff.substring(0,ff.indexOf('.'));
					if(isVal==fff){
						av=fff;
					}
				}
				
				var s=document.getElementById('tbody');	//����ֵ��������
				for(var i=0 ;i<s.rows.length;i++)
				{
					 var ss=s.rows[i].cells[2].innerText; //����ֵ   
					 var sss=ss.substring(0,ss.indexOf('.'));	
					 if(isVal==sss){					 
					 	av=sss;					 
					 }	
				}
				
			//�ж������������Ƿ���ֵ	
			var arr=[];	
			if(isVal==av){
				if(confirm("���������д������ݣ��˲������ᶪʧԭ������")){
					var fie=document.getElementById('td');	//�ֶ�				
					for(var i=0;i<fie.rows.length;i++)
					{				
						var fff=fie.rows[i].cells[1].innerText;//�ֶ�						
						var f2_=fff.substring(0,fff.lastIndexOf('.'));//��ȡ.ǰ��ģ�Ҳ���Ǳ���
						if(isVal==f2_){
							var s=fie.rows[i].cells[1];	
							$(s).html("");//����ֶΣ�ѡ�ֶΣ�
						}					
					}					
					
					//ȡ�ã�ѡ�ֶΣ�����ֶ��б�								
					var fieldSegments=document.getElementById("fieldSegments");											
					for(var i=0;i<fieldSegments.rows.length;i++){
						for(var j=0;j<fieldSegments.rows[i].cells.length;j++){
							var fieldData1=fieldSegments.rows[i].cells[j].innerText;
							var fieldData1_=fieldData1.substring(0,fieldData1.lastIndexOf('.'));//��ȡ.ǰ��ģ�Ҳ���Ǳ���
							if(isVal==fieldData1_){																		
								var ff = fieldSegments.rows[i];	
								arr.push(ff);
							}
						}												
					}
					map2.remove(fieldData1_,"1");  //��ѡ�ֶμ����ֶδ�mpa�Ƴ�
					
					//ȡ���ֶ�(����)
					var tiaojian=document.getElementById('tbody');  
					for(var i=0 ;i<tiaojian.rows.length;i++)
					{
						var tj=tiaojian.rows[i].cells[2].innerText; //����ֵ  
						var val=tiaojian.rows[i].cells[4].innerText; //ֵ   
						var tj_=tj.substring(0,tj.lastIndexOf('.'));//��ȡ.ǰ��ģ�Ҳ���Ǳ���
						var val_=val.substring(0,val.indexOf('.'));//��ȡ.ǰ��ģ�Ҳ���Ǳ���
						if(isVal==tj_){
							var t1=tiaojian.rows[i].cells[2];
							$(t1).html("");//�������ֵ��������
						}
						if(isVal==val_){
							var t2=tiaojian.rows[i].cells[4];
							$(t2).html("");//���ֵ��������
						}
					}
					//ȡ�ã�����������ֶ��б�
					var Selfield=document.getElementById("Selfield");	
					for(var i=0;i<Selfield.rows.length;i++){
						for(var j=0;j<Selfield.rows[i].cells.length;j++){
							var SelfieldData=Selfield.rows[i].cells[j].innerText;
							var SelfieldData_=SelfieldData.substring(0,SelfieldData.indexOf('.'));//��ȡ.ǰ��ģ�Ҳ���Ǳ���
							if(isVal==SelfieldData_){
								var sd=Selfield.rows[i];
								arr.push(sd);								
							}
						}						
					}
					map2.remove(SelfieldData_,"1");  //�����������ֶ����map���Ƴ�
					
					//ȡ���ֶΣ�����
					var paixu=document.getElementById('ty');
					for(var i=0 ;i<paixu.rows.length;i++)
					{
						var px=paixu.rows[i].cells[1].innerText; //�ֶ�
						var px_=px.substring(0,px.indexOf('.'));//��ȡ.ǰ��ģ�Ҳ���Ǳ���
						if(isVal==px_){
							var p=paixu.rows[i].cells[1];
							$(p).html("");
						}
					}
					//ȡ�ã���������ֶ��б�
					var sortField=document.getElementById('sortField');
					for(var i=0;i<sortField.rows.length;i++){
						for(var j=0;j<sortField.rows[i].cells.length;j++){
							var sofData=sortField.rows[i].cells[j].innerText;							
							var sofData_=sofData.substring(0,sofData.indexOf('.'));//��ȡ.ǰ��ģ�Ҳ���Ǳ���
							if(isVal==sofData_){
								var sof=sortField.rows[i];
								arr.push(sof);
							}
						}
					}
					map2.remove(sofData_,"1");  //����������ֶ����map���Ƴ�
					
					//ȡ���ֶΣ����ӣ�
					var  ziduan=document.getElementById('tby');
					for(var i=0 ;i<ziduan.rows.length;i++)
					{
						var yzd=ziduan.rows[i].cells[1].innerText; //Դ�ֶ�
						var mbzd=ziduan.rows[i].cells[3].innerText; //Ŀ���ֶ�
						var yzd_=yzd.substring(0,yzd.lastIndexOf('.'));//��ȡ.ǰ��ģ�Ҳ���Ǳ���
						var mbzd_=mbzd.substring(0,mbzd.indexOf('.'));//��ȡ.ǰ��ģ�Ҳ���Ǳ���
						if(isVal==yzd_){
							var y=ziduan.rows[i].cells[1];
							$(y).html("");//���Դ�ֶ�
						}
						if(isVal==mbzd_){
							var m=ziduan.rows[i].cells[3];
							$(m).html("");//���Ŀ���ֶ�
						}
					}
					//ȡ�ã����ӣ�����ֶ��б�
					var join=document.getElementById('join');
					for(var i=0;i<join.rows.length;i++){
						for(var j=0;j<join.rows[i].cells.length;j++){
							var joinData=join.rows[i].cells[j].innerText;
							var joinData_=joinData.substring(0,joinData.indexOf('.'));//��ȡ.ǰ��ģ�Ҳ���Ǳ���
							if(isVal==joinData_){
								var jd=join.rows[i];
								arr.push(jd);
							}
						}
					}
						map2.remove(joinData_,"1");  //�����Ӽ����ֶ����map���Ƴ�
				
						for(var i=0;i<arr.length;i++){
							$(arr[i]).remove();//ɾ�����������У���ߣ������ֶ�
						}
						
						if(jQuery("#selectedTable tr").length>1){	
							jQuery(arguments[0]).parent().parent().remove();//ȷ�ϲ�����ɾ������һ�С���
						} 
						
					 }else{
						return;
					}
				}else{//����������ֵ����ֻ���Ƴ�����������߼��ص��ֶ�
										
					//ȡ�ã�ѡ�ֶΣ�����ֶ��б�
					var fieldSegments=document.getElementById("fieldSegments");						
					for(var i=0;i<fieldSegments.rows.length;i++){
						for(var j=0;j<fieldSegments.rows[i].cells.length;j++){
							var fieldData1=fieldSegments.rows[i].cells[j].innerText;
							var fieldData1_=fieldData1.substring(0,fieldData1.indexOf('.'));//��ȡ.ǰ��ģ�Ҳ���Ǳ���
							if(isVal==fieldData1_){																				
								var fs=fieldSegments.rows[i];								
								arr.push(fs);
							}
						}									
					}
					map2.remove(fieldData1_,"1");  //��ѡ�ֶμ����ֶ����map���Ƴ�
					
					//ȡ�ã�����������ֶ��б�
					var Selfield=document.getElementById("Selfield");	
					for(var i=0;i<Selfield.rows.length;i++){
						for(var j=0;j<Selfield.rows[i].cells.length;j++){
							var SelfieldData=Selfield.rows[i].cells[j].innerText;
							var SelfieldData_=SelfieldData.substring(0,SelfieldData.indexOf('.'));//��ȡ.ǰ��ģ�Ҳ���Ǳ���
							if(isVal==SelfieldData_){
								var sd=Selfield.rows[i];
								arr.push(sd);  
							}
						}						
					}	
					map2.remove(SelfieldData_,"1");  //�����������ֶ����map���Ƴ�
					
					//ȡ�ã���������ֶ��б�
					var sortField=document.getElementById('sortField');
					for(var i=0;i<sortField.rows.length;i++){
						for(var j=0;j<sortField.rows[i].cells.length;j++){
							var sofData=sortField.rows[i].cells[j].innerText;							
							var sofData_=sofData.substring(0,sofData.indexOf('.'));//��ȡ.ǰ��ģ�Ҳ���Ǳ���
							if(isVal==sofData_){
								var sof=sortField.rows[i];
								arr.push(sof);
							}
						}
					}
					map2.remove(sofData_,"1");  //����������ֶ����map���Ƴ�
					
					//ȡ�ã����ӣ�����ֶ��б�
					var join=document.getElementById('join');
					for(var i=0;i<join.rows.length;i++){
						for(var j=0;j<join.rows[i].cells.length;j++){
							var joinData=join.rows[i].cells[j].innerText;
							var joinData_=joinData.substring(0,joinData.indexOf('.'));//��ȡ.ǰ��ģ�Ҳ���Ǳ���
							if(isVal==joinData_){
								var jd=join.rows[i];
								arr.push(jd);
							}
						}
					}
					map2.remove(joinData_,"1");  //�����Ӽ����ֶ����map���Ƴ�
						
					if(jQuery("#selectedTable tr").length>1){
						jQuery(arguments[0]).parent().parent().remove();
						for(var i=0;i<arr.length;i++){
							$(arr[i]).remove(); //ɾ�����У���ߣ������ֶ�
						}						
					}else{//���һ�в�ɾ���Ҽ��ص��ֶ�Ҳ����
						alert("�������һ��,����ɾ"); 
					}					
				}
								
			}else{	
					if(jQuery("#selectedTable tr").length>1){						
						jQuery(arguments[0]).parent().parent().remove();
					}else{
						alert("�������һ��,����ɾ");
					}
			}
		}
		
		
		 function addLine(){
����������	//�Զ�����
				var objMyTable = document.getElementById("tbody");
			����var index = objMyTable.rows.length;
			����var nextRow = objMyTable.insertRow(index);//Ҫ�������У��������Ǵӵ����ڶ��п�ʼ������
				var newCellCartonNo1 = nextRow.insertCell();
				var newCellCartonNo2 = nextRow.insertCell();
				var newCellCartonNo3 = nextRow.insertCell();
				var newCellCartonNo4 = nextRow.insertCell();
				var newCellCartonNo5 = nextRow.insertCell();
				var newCellCartonNo6 = nextRow.insertCell();
				
				newCellCartonNo1.style.display='none'; 
				newCellCartonNo3.setAttribute("className","drop");	
				newCellCartonNo5.setAttribute("className","drop");	
				newCellCartonNo1.innerHTML = '&nbsp;';
				newCellCartonNo2.innerHTML = '<select id="sele" style="width: 100px"><option>AND<option>OR</select>';
				newCellCartonNo3.innerHTML = "&nbsp;";
				newCellCartonNo4.innerHTML = '<select id="sel" style="width: 100px"><option>=<option>!=<option><<option><=<option>><option>>=<option>LIKE<option>NOT LIKE<option>IN<option>NOT IN<option>IS NULL<option>IS NOT NULL</select>';
				newCellCartonNo5.innerHTML = '<input type="text"  id="inputext" style="display: none;">';
				newCellCartonNo6.innerHTML = '<input type=button onclick="addLine();" style="width: 30px;" value="+"> <input type=button id="a"  onclick="delLine(this);" style="width: 30px;" value="-">';
				resetDrag();
				num();				
				
          }
          //ɾ����
		 function delLine(){
            if(jQuery("#tbody>tr").length>1){
				jQuery(arguments[0]).parent().parent().remove();
			}			
			num();
			resetDrag();
		}
		
		
		//�Զ�����
		function addline(){
			   var objMyTable = document.getElementById("tby");
			����var index = objMyTable.rows.length;
			����var nextRow = objMyTable.insertRow(index);//Ҫ�������У��������Ǵӵ����ڶ��п�ʼ������
				var newCellCartonNo1 = nextRow.insertCell();
				var newCellCartonNo2 = nextRow.insertCell();
				var newCellCartonNo3 = nextRow.insertCell();
				var newCellCartonNo4 = nextRow.insertCell();
				var newCellCartonNo5 = nextRow.insertCell();				
				
				newCellCartonNo1.style.display='none'; 
				newCellCartonNo2.setAttribute("className","drop");	
				newCellCartonNo4.setAttribute("className","drop");
				
				newCellCartonNo1.innerHTML = '&nbsp;';
				newCellCartonNo2.innerHTML = '&nbsp;';
				newCellCartonNo3.innerHTML = '<select id="joinClz" style="width: 100px"><option>=<option><<option><=<option>><option>>=</select>';
				newCellCartonNo4.innerHTML="&nbsp;";
				newCellCartonNo5.innerHTML = '<input type=button onclick="addline();" style="width: 30px;" value="+"> <input type=button  onclick="delline(this);" style="width: 30px;" value="-">';
				tby();
				resetDrag();		
		}
		//ɾ����
		function delline(){
			if(jQuery("#tby>tr").length>1){
				jQuery(arguments[0]).parent().parent().remove();
			}			
			tby();
			resetDrag();			
		}
		
		//�Զ�������
		function addine(){
			
			var objMyTable = document.getElementById("td");
		����var index = objMyTable.rows.length;
		����var nextRow = objMyTable.insertRow(index);//Ҫ�������У��������Ǵӵ����ڶ��п�ʼ������
			var newCellCartonNo1 = nextRow.insertCell();
			var newCellCartonNo2 = nextRow.insertCell();
			var newCellCartonNo3 = nextRow.insertCell();
			newCellCartonNo1.style.display='none';
			newCellCartonNo2.setAttribute("className","drop");
			newCellCartonNo2.innerHTML = "&nbsp;";
			newCellCartonNo3.innerHTML = '<input type=button onclick="addine();" style="width: 30px;" value="+"> <input type=button id="a"  onclick="deline(this);" style="width: 30px;" value="-">';
			td();
			resetDrag();
			
		}
		//ɾ����
		function deline(){
			if(jQuery("#td>tr").length>1){
				jQuery(arguments[0]).parent().parent().remove();
			}	
			td();
			resetDrag();
		}
		//�Զ�������
		function add(){			
			var objMyTable = document.getElementById("ty");
		����var index = objMyTable.rows.length;
		����var nextRow = objMyTable.insertRow(index);//Ҫ�������У��������Ǵӵ����ڶ��п�ʼ������
			var newCellCartonNo1 = nextRow.insertCell();
			var newCellCartonNo2 = nextRow.insertCell();
			var newCellCartonNo3 = nextRow.insertCell();
			var newCellCartonNo4 = nextRow.insertCell();
			newCellCartonNo1.style.display="none";
			newCellCartonNo2.setAttribute("className","drop");
			newCellCartonNo3.innerHTML = '<select id="sorts" style="width: 100px"><option>ASC<option>DESC</select>';
			newCellCartonNo4.innerHTML = '<input type=button onclick="add();" style="width: 30px;" value="+"> <input type=button  onclick="del(this);" style="width: 30px;" value="-">';
			ty();
			resetDrag();

		}
		//ɾ����
		function del(){
			if(jQuery("#ty>tr").length>1){
				jQuery(arguments[0]).parent().parent().remove();
			}	
			ty();
			resetDrag();
		}		
		
		function num(){
			//����
			jQuery("#tbody>tr").each(function(i){
				this.cells[0].innerHTML=i+1;
			});
		}
		function ty(){
			//����
			jQuery("#ty>tr").each(function(i){
				this.cells[0].innerHTML=i+1;
			});
		}
		function tby(){
			jQuery("#tby>tr").each(function(i){
				this.cells[0].innerHTML=i+1;
			});
		}
		function td(){
			jQuery("#td>tr").each(function(i){
				this.cells[0].innerHTML=i+1;
			});
		}
		//��ʾѡ��
		function optionTable(){
			$("#optionTable").show();
			$("#optionField,#whereSegment,#sort,#joint").hide();
			$("#1").addClass('current');
			$("#2,#3,#4,#5").removeClass('current');
		}
		//ѡ���ֶ�
		function optionField(){
			$("#optionField").show();
			$("#optionTable,#whereSegment,#sort,#joint").hide();
			$("#2").addClass('current');
			$("#1,#3,#4,#5").removeClass('current');
							
		}
		//ѡ������ 
		function condition(){
			$("#whereSegment").show();
			$("#optionTable,#optionField,#sort,#joint").hide();
			$("#3").addClass('current');
			$("#1,#2,#4,#5").removeClass('current');	
		}
		//����
		function sort(){
			$("#sort").show();
			$("#optionTable,#optionField,#whereSegment,#joint").hide();
			$("#4").addClass('current');
			$("#1,#2,#3,#5").removeClass('current');
			
		}
		//����
		function joint(){
			$("#joint").show();
			$("#optionTable,#optionField,#whereSegment,#sort").hide();
			$("#5").addClass('current');
			$("#1,#2,#3,#4").removeClass('current');			
		}
		
		
		function inputext(element)
		{		
			var oldhtml = element.innerHTML;   //���Ԫ��֮ǰ������			
			var newobj = document.createElement('input');   //����һ��inputԪ��
			newobj.type = 'text';   //ΪnewobjԪ���������			
			//����newobjʧȥ������¼�
			newobj.onblur = function(){
			element.innerHTML = this.value ? this.value : oldhtml;   
			}
			element.innerHTML = '';   //����Ԫ������Ϊ��
			element.appendChild(newobj);   //�����Ԫ��
			newobj.focus();   //��ý���
		}
		
		
		//�ύ����
	function submitData(){	
		
	//ȡ�ñ�

	var tab=document.getElementById('selectedTable');
	var sql = '';
	sql += '{"select":[';
	
	
	 
	//ȡ���ֶ�(ѡ�ֶ�)
	var fieldSegments=document.getElementById('td');
	for(var i=0 ;i<fieldSegments.rows.length;i++)
	{
		var fieldSegments1=fieldSegments.rows[i].cells[0].innerText;//���
		var fieldSegments2=fieldSegments.rows[i].cells[1].innerText;//�ֶ�
		var f2=fieldSegments2.substring(fieldSegments2.lastIndexOf('.')+1); //�ֶ���
		var f2_=fieldSegments2.substring(0,fieldSegments2.lastIndexOf('.'));//��ȡ.ǰ��ģ�Ҳ���Ǳ���

		if(f2.replace(/^\s+|\s+$/g,"").length > 0){	
			var first='{"columnName":"'+f2+'","tableAlias":"'+f2_+'","tableName":"'+f2_+'","ordinal":'+fieldSegments1+'}';
			sql+=first;
			if(i<fieldSegments.rows.length){
				sql+=',';
			}
		}		
	}
   if(sql.lastIndexOf(',')!=-1){
	   sql = sql.substring(0, sql.length-1);
   }
    sql+=']'; 
   sql+=', "from":[';

	for(var i=0 ;i<tab.rows.length;i++)
	{
  		for(var j=0;j<tab.rows[i].cells.length;j++){
  		    var tableName=tab.rows[i].cells[j].innerText;  
  			if(tableName.length > 1 && tableName != "  "){
				var from='{"tableAlias":"'+tableName+'","tableName":"'+tableName+'"}';
				sql+=from;
				if(j<tab.rows[i].cells.length-1){
					sql+=",";
				}
			}
  		}
	}

   if(sql.lastIndexOf(',')!=-1){
	   sql = sql.substring(0, sql.length - 1);
   }
	sql+=']';

	con_flag = 0;

	var temp_sql = "";
	var va=[];//���ӷ�
	var vaa=[];//������     

	//ȡ���ֶ�(����)
	var select=document.getElementById('tbody');	
	for(var i=0 ;i<select.rows.length;i++)
	{
   		var select1=select.rows[i].cells[0].innerText; //����   		
   		$('#tbody tr').each(function(ind,obj){
     		var select4=$(obj).find('#sel>option:selected').text(); //������     	
     		vaa.push(select4);	
		});
   		jQuery('#tbody tr').each(function(ind,obj){
     		var select2=jQuery(obj).find('#sele>option:selected').text(); //���ӷ�   
     		va.push(select2);     		 	
		}); 
   		var select3=select.rows[i].cells[2].innerText; //����ֵ   		
   		var s3=select3.substring(select3.lastIndexOf('.')+1); 
   		var select5=select.rows[i].cells[4].innerText;  //ֵ

    	var s5=select5.substring(select5.lastIndexOf('.')+1);
    	var sele=""; 
    	if($("#inputext").val()!= ""){      			
    		var inputData=$("#inputext").val();
    		inputData=sele;
    	}else{
    		sele=select5;
    	}  
		if(select3.length > 1 && sele.length > 0) {
			con_flag = con_flag +1;
			var where='{"connector":"'+va[i]+'","operator":"'+vaa[i]+'","field":"'+select3+'","value":"'+sele+'","ordinal":'+select1+'}';
			temp_sql+=where;
			if( i<select.rows.length-1){
				temp_sql+=',';
			}
		}
	}

   if(temp_sql.lastIndexOf(',')!=-1){
	   temp_sql = temp_sql.substring(0, temp_sql.length - 1);
   }

   if(con_flag > 0){
	   sql+=', "where":[';
       sql+=temp_sql;
	   sql+=']';
   }
	
	con_flag=0;
	temp_sql="";

	
	
	//ȡ���ֶ�(����)
	var sor=document.getElementById('ty');
	var sor1="";//����
	var sor2="";//�ֶ�	
	var arra=[];//����
	
	for(var i=0 ;i<sor.rows.length;i++)
	{
   		jQuery('#ty tr').each(function(ind,obj){
     			var sor3=jQuery(obj).find('#sorts>option:selected').text(); //����    
     			arra.push(sor3);     			
		}); 		
		if(arra[i]=="ASC"){
			arra[i]='true';
		}else{
			arra[i]='false';
		}
   		sor1=sor.rows[i].cells[0].innerText;    		
   		sor2=sor.rows[i].cells[1].innerText; 
		
   		var x_columnName=sor2.substring(sor2.lastIndexOf('.')+1); 
   		var x_table=sor2.substring(0,sor2.lastIndexOf('.'));	
		//alert(x_table);
   		if(x_table.length > 0 && x_columnName.length > 0){   		
			con_flag = con_flag+1;
			var sort='{"columnName":"'+x_columnName+'","tableAlias":"'+x_table+'","tableName":"'+x_table+'","asc":'+arra[i]+',"ordinal":'+sor1+'} '; 
			temp_sql+=sort;
			if(i<sor.rows.length-1){
				temp_sql+=',';
			}
   		}
   		
	}	
   if(temp_sql.lastIndexOf(',')!=-1){
	   temp_sql = temp_sql.substring(0, temp_sql.length - 1);
   }  

   if(con_flag > 0){
	   sql+=', "orderBy":[';
       sql+=temp_sql;
	   sql+=']';
   }
	
	con_flag=0;
	temp_sql="";
  

	//ȡ���ֶ�(����)
	var  tby=document.getElementById('tby');
	var tby1="";//����
	var tby2="";//Դ�ֶ�	
	var tby4="";//Ŀ���ֶ�
	var joinarray=[];
	for(var i=0 ;i<tby.rows.length;i++)
	{
		$('#tby tr').each(function(ind,obj){
     		var tby3=$(obj).find('#joinClz>option:selected').text(); //��������
     		joinarray.push(tby3);
		});
   		tby1=tby.rows[i].cells[0].innerText; 
   		tby2=tby.rows[i].cells[1].innerText; 
   		var t2=tby2.substring(tby2.lastIndexOf('.')+1);
   		tby4=tby.rows[i].cells[3].innerText; 
   		var t4=tby4.substring(tby4.lastIndexOf('.')+1);   
		if(tby2.length > 1 && tby4.length > 1) {
			var join='{"connector":"'+joinarray[i]+'","source":"'+tby2+'","target":"'+tby4+'","ordinal":'+tby1+'} ';	    
			temp_sql+=join;
			con_flag = con_flag+1;
			if(i < tby.rows.length -1){
				temp_sql+=',';
			}
		}
	}
   if(temp_sql.lastIndexOf(',')!=-1){
	   temp_sql = temp_sql.substring(0, temp_sql.length - 1);
   }

	if(con_flag > 0){
	   sql+=', "join":[';
       sql+=temp_sql;
	   sql+=']';
   }

	sql+='}';	

	//�ж��Ƿ������˱�
	var tab=document.getElementById('selectedTable');
	var tableName="";
	for(var i=0 ;i<tab.rows.length;i++)
	{
  		for(var j=0;j<tab.rows[i].cells.length;j++){
  		    tableName+=tab.rows[i].cells[j].innerText;  
  		}
   }
   if(tableName.replace(/^\s+|\s+$/g,"")==""){
   		alert("���κ�����,�޷�����SQL");
   		return;
   }

	//�ж��Ƿ����ֶΣ�ѡ�ֶΣ�
	var fs=document.getElementById('td');
	var fs2="";
	for(var i=0 ;i<fs.rows.length;i++)
	{
		fs2+=fs.rows[i].cells[1].innerText;//�ֶ�
	}
	if(fs2.replace(/^\s+|\s+$/g,"")==""){		
		alert("��SQL��䲻��ȷ,��ѡ���ֶ�");
		return;
	}
	//�ж������Ƿ�ƥ����ȫ
	var select=document.getElementById('tbody');	
	for(var i=0 ;i<select.rows.length;i++)
	{
		var select3=select.rows[i].cells[2].innerText; //����ֵ   		
   		var select5=select.rows[i].cells[4].innerText;  //ֵ
   		if(select3.replace(/^\s+|\s+$/g,"")=="" && select5.replace(/^\s+|\s+$/g,"")!=""){//����ֵΪ�գ�ֵ��Ϊ��
   			alert("����ƥ�䲻��ȷ,����ȷѡ������ֵ��Ӧ");
   			return;
   		}
   		if(select3.replace(/^\s+|\s+$/g,"")!="" && select5.replace(/^\s+|\s+$/g,"")==""){//����ֵ��Ϊ�գ�ֵ����
   			alert("����ƥ�䲻��ȷ,����ȷѡ�������ֵ");
   			return;
   		}
   		
   	}	
	//�ж������Ƿ�ƥ����ȫ
	var  tby=document.getElementById('tby');
	for(var i=0 ;i<tby.rows.length;i++)
	{
		var tby2=tby.rows[i].cells[1].innerText; //Դ�ֶ�
		var tby4=tby.rows[i].cells[3].innerText; //Ŀ���ֶ�
		if(tby2.replace(/^\s+|\s+$/g,"")=="" && tby4.replace(/^\s+|\s+$/g,"")!=""){//Դ�ֶ�Ϊ�գ�Ŀ���ֶβ�Ϊ��
			alert("����ƥ�䲻��ȷ,����ȷѡ��Դ�ֶζ�Ӧ");
			return;
		}
		if(tby2.replace(/^\s+|\s+$/g,"")!="" && tby4.replace(/^\s+|\s+$/g,"")==""){//Դ�ֶβ�Ϊ�գ�Ŀ���ֶ�Ϊ��
			alert("����ƥ�䲻��ȷ,����ȷѡ��Ŀ���ֶζ�Ӧ");
			return;
		}
	}
	
	document.getElementById("jsonResult").value=sql;
	alert(sql);
   	}

   function testSQL(){
       if( document.getElementById("jsonResult").value!=""){
		  alert(document.getElementById("jsonResult").value);
		  var x_params = jQuery("#iForm").formSerialize();
		  jQuery.ajax({
					type: "POST",
					url: '<%=request.getContextPath()%>/rs/dts/query/subject/toSql',
					data: x_params,
					dataType:  'json',
					error: function(data){
						alert('�������������');
					},
					success: function(data){
						if(data.sql != null){
							alert("�ɹ�");
							var parent_window = window.opener;								  
							document.getElementById("sqlx").value=data.sql;
							if(parent_window != null){
	                             var x_sql = parent_window.document.getElementById("sql");
								 x_sql.value = data.sql;
							}
							
						} else {
						 alert(data.message);
						}
					}
			});
	   }
	}
	</script>
	</head>
	<body>
		<div id="navcontainer">
			<ul id="navlist">
				<li>
					<a href="#" id="1" class="current" onClick="optionTable();">ѡ��</a>
				</li>
				<li>
					<a href="#" id="2" onClick="optionField();">ѡ�ֶ�</a>
				</li>
				<li>
					<a href="#" id="3" onClick="condition();">����</a>
				</li>
				<li>
					<a href="#" id="4" onClick="sort();">����</a>
				</li>
				<li>
					<a href="#" id="5" onClick="joint();">����</a>
				</li>
			</ul>
		</div>

		<div id="optionTable" style="width: 520px">
			<div id="tablesDiv" class="left" style="padding-top: 25px;">
				<table id="tables">
					<thead>
						<tr>
							<th>
								ѡ���
							</th>
						</tr>
						<%
					    for(TableDefinition tbl:tables){
					 %>
						<tr>
							<td>
								<div class="item">
									<p><%=tbl.getTableName() %></p>
								</div>
							</td>
						</tr>
						<%} %>
					</thead>
				</table>
			</div>

			<div style="padding-left: 18px; padding-top: 25px; width: 320px"
				class="right">
				<table border="0" cellspacing="1" cellpadding="1" id="selectedTable"  >
				    <%for(int kk=1;kk<=5;kk++){%>
					<tr  >
						<td class="drop" style="background: #fafafa;">
							&nbsp;
						</td>
						<td>
							<input type="button" onclick="addHang();" style="width: 30px;"
								value="+">
							<input type="button" id="a" onclick="delHang(this);"
								style="width: 30px;" value="-">
						</td>
					</tr>
				   <%}%>
					
				</table>
			</div>
		</div>

		<div style="padding-top: 20px; width: 520px; display: none;"
			id="optionField">
			<div id="selectSegment" class="left">
				<table id="fieldSegments" class="fieldSegments">
					<tr>
						<th>
							<br>ѡ���ֶ�
						</th>
					</tr>
				</table>
			</div>
			<div id="joinSegment" class="next"
				style="float: right; width: 320px;">
				<table>
					<tr>
						<th style="display: none;"></th>
						<th>
							�ֶ�
						</th>
						<th>
							����/ɾ��
						</th>
					</tr>
					<tbody id="td">

					  <%for(int kk=1;kk<=5;kk++){%>
						<tr>
							<td style="display: none;">
								<%=kk%>
							</td>
							<td class="drop" align="center"></td>
							<td align="center" style="width: 100px">
								<input type="button" value="+" style="width: 30px;"
									onclick="addine();">
								<input type="button" value="-" style="width: 30px;"
									onclick="deline(this);">
							</td>
						</tr>
					   <%}%>
						
					</tbody>
				</table>
			</div>
		</div>

		<div id="whereSegment"
			style="padding-top: 20; width: 870px; display: none;">
			<div class="left">
			    <br>
				<table id="Selfield">
					<th>
						<br>ѡ���ֶ�
					</th>
				</table>
			</div>

			<div style="float: right; padding-left: 10px; width: 670px"
				class="next">
				<br>
				<table class="table1">
					<tr>
						<th style="display: none;"></th>
						<th>
							���ӷ�
						</th>
						<th>
							����ֵ
						</th>
						<th>
							������
						</th>
						<th>
							ֵ
						</th>
						<th>
							��� / ɾ��
						</th>
					</tr>
					<tbody id="tbody">
					   <%for(int kk=1;kk<=5;kk++){%>
						<tr>
							<td style="display: none;">
								1
							</td>
							<td class="andor" style="width: 100px">
								<select id="sele" style="width: 100px">
									<option>
										AND
									<option>
										OR
								</select>
							</td>
							<td class="drop" style="width: 220px"></td>

							<td style="width: 100px">
								<select id="sel" style="width: 100px">
									<option>
										=
									<option>
										!=
									<option>
										&lt;
									<option>
										&lt;=
									<option>
										&gt;
									<option>
										&gt;=
									<option>
										LIKE
									<option>
										NOT LIKE
									<option>
										IN
									<option>
										NOT IN
									<option>
										IS NULL
									<option>
										IS NOT NULL
								</select>
							</td>
							<td class="drop" style="width: 220px"
								ondblclick="inputext(this);">
								<input type="text" id="inputext" style="display: none;">
							</td>
							<td style="width: 140px">
								<input type="button" value="+" style="width: 30px;"
									onclick="addLine();">
								<input type="button" value="-" style="width: 30px;"
									onclick="delLine(this);">
							</td>
						</tr>
						<%}%>

					</tbody>
				</table>
			</div>
		</div>

		<div style="padding-top: 20px; width: 640px; display: none;" id="sort">
			<div class="left">
				<table id="sortField">
					<th>
						<br>�����ֶ�
					</th>
				</table>
			</div>
			<div id="orderBySegment" class="next"
				style="float: right; padding-left: 1px; width: 440px">
				<table class="table1">
					<tr>
						<th style="display: none;"></th>
						<th>
							�ֶ�
						</th>
						<th>
							����
						</th>
						<th>
							���/ɾ��
						</th>
					</tr>
					<tbody id="ty">
					  <%for(int kk=1;kk<=5;kk++){%>
						<tr>
							<td style="display: none;">
								<%=kk%>
							</td>
							<td class="drop" align="center" width="180px"></td>
							<td align="center" style="width: 100px">
								<select id="sorts" style="width: 100px">
									<option>
										ASC
									<option>
										DESC
								</select>
							</td>
							<td style="width: 100px">
								<input type="button" value="+" style="width: 30px;"
									onclick="add();">
								<input type="button" value="-" style="width: 30px;"
									onclick="del(this);">
							</td>
						</tr>
					   <%}%>
					</tbody>
				</table>
			</div>
		</div>

		<div class="next"
			style="padding-top: 20; width: 780px; display: none;" id="joint">
			<br>
			<div class="left">
				<table id="join">
					<tr>
						<th>
							ѡ���ֶ�
						</th>
					</tr>
				</table>
			</div>
			<div class="next" style="float: right; padding-left: 1px; width: 540px">
			    <br>
				<table class="table1" width="570">
					<tr>
						<th style="display: none;"></th>
						<th>
							Դ�ֶ�
						</th>
						<th>
							��������
						</th>
						<th>
							Ŀ���ֶ�
						</th>
						<th>
							��� / ɾ��
						</th>
					</tr>
					<tbody id="tby">

					 <%for(int kk=1;kk<=5;kk++){%>
						<tr>
							<td style="display: none;">
								<%=kk%>
							</td>
							<td class="drop" width="180px"></td>
							<td class="select" align="center" style="width: 100px">
								<select id="joinClz" style="width: 100px">
									<option value="=">����</option>
								</select>
							</td>
							<td class="drop"></td>
							<td style="width: 100px">
								<input type="button" value="+" style="width: 30px;"
									onclick="addline();">
								<input type="button" value="-" style="width: 30px;"
									onClick="delline(this);">
							</td>
						</tr>
                       <%}%>
						
					</tbody>
				</table>
			</div>
		</div>
		<div style="padding-top: 40px; padding-left: 200px">
		    <br />
			<form id="iForm" name="iForm" method="post" action="">
			    <br />
				<textarea id="jsonResult" name="jsonResult" rows="8" cols="68"></textarea>
				<br />
				<textarea id="sqlx" name="sqlx" rows="8" cols="68" readonly></textarea>
				<br />
				<br />
				<br />
				<input type="button" value="����" class="sbt" onClick="submitData();">&nbsp;&nbsp;
				<input type="button" value="�ύ" class="sbt" onclick="testSQL();">&nbsp;&nbsp;
				<input type="button" value="�ر�" class="sbt" onclick="window.close();">
			</form>
		</div>
	</body>
</html>