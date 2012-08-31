<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" errorPage=""%>


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="StyleSheet" href="<%=request.getContextPath()%>/sys/sysJs/tree/dtree.css" type="text/css" />
  <script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/frameWorkUtility.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/tree/dtree.js"></script>
		<title>树</title>
	</head>
	<body style="background: #EDF9F0;"><br>
<div class="dtree">

	<p><a href="javascript: d.openAll();">打开所有</a> | <a href="javascript: d.closeAll();">关闭所有</a></p>

	<script type="text/javascript">
		<!--
		
		d = new dTree('d');

		d.add(0,-1,'这里是根节点');
		d.add(1,0,'Demo01','./Demo01.jsp','日历控件 画面锁定 上传下载','right');
		d.add(2,0,'Node 2','');
		d.add(3,1,'Node 1.1','');
		d.add(4,0,'Node 3','');
		d.add(5,3,'Node 1.1.1','');
		d.add(6,5,'Node 1.1.1.1','');
		d.add(7,0,'Node 4','');
		d.add(8,1,'Node 1.2','');
		d.add(9,0,'Demo02','','','','','../../sys/sysJs/tree/img/imgfolder.gif');
		d.add(10,9,'Node 2.1','','Pictures of Gullfoss and Geysir');
		d.add(11,9,'Node 2.2','');
		d.add(12,0,'Demo03','','','','../../sys/sysJs/tree/img/trash.gif');

		document.write(d);

		//-->
	</script>

</div>
	</body>
</html>

