<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/sys/sysTld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/sys/sysTld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/sys/sysTld/struts-logic.tld" prefix="logic"%>

<html>
  <script type="text/javascript" src="<%=request.getContextPath()%>/sys/sysJs/frameWorkUtility.js"></script>
	<head>
		<title>My JSP 'page1024' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/baseStyle.css">
		<title>资材采购申请单</title>
	</head>
	<body>
		<h5>
			资材采购申请单

		</h5>
		<html:form action="page1024.do" method="post" >
			<input type="hidden" value="runPageLoad" name="actionMethodId" />
			<table>
				<tr>
					<td>
						申请部门填写
					</td>
				</tr>
			</table>
			<div
				style="padding: 5px 0px 5px 5px; background-color: white; border; border-width: 1px; border-style: solid; border-color: #87C897;">
				<table style="font: normal 12px/ 25px simsun;">
					<tr>
						<td>
							填单日

						</td>
						<td>
							${page1024Form.writeTime}
						</td>
						<td>
							申请单号
						</td>
						<td>
							${page1024Form.stuffApplyNo}
						</td>
						<td>
							申请科室
						</td>
						<td>
							${page1024Form.depName}
						</td>
						<td>
							申请担当
						</td>
						<td>
							${page1024Form.userName}
						</td>
						<td>
							使用部门提交日

						</td>
						<td>
							${page1024Form.useDeptSubmitDate}
						</td>
					</tr>
					<tr>
						<td>
							申请类别
						</td>
						<td>
							<html:select property="applyType">
								<html:option value="000001">
									新品番申请

								</html:option>
								<html:option value="000002">
									产品更换
								</html:option>
								<html:option value="000003">
									供应商更换

								</html:option>
								<html:option value="000004">
									品番拆分
								</html:option>
							</html:select>
						</td>
						<td>
							原品番

						</td>
						<td>
							<html:text property="oldPartNO" />
						</td>
						<td>
							更换/拆分原因
						</td>
						<td colspan="6">
							<html:text property="changeReason" />
						</td>
					</tr>
					<tr>
						<td>
							品目
						</td>
						<td>
							<html:select property="partType">
								<html:option value="1">
									劳保品

								</html:option>
								<html:option value="2">
									化学品

								</html:option>
								<html:option value="3">
									工具
								</html:option>
								<html:option value="4">
									设备材

								</html:option>
								<html:option value="5">
									设备预备品

								</html:option>
								<html:option value="6">
									用度品

								</html:option>
							</html:select>
						</td>
						<td>
							品名
						</td>
						<td>
							<html:text property="partName" />
						</td>
						<td>
							品名(日文)
						</td>
						<td>
							<html:text property="partNameJa" />
						</td>
						<td>
							产地
						</td>
						<td>
							<html:text property="producingArea" />
						</td>
						<td>
							规格
						</td>
						<td>
							<html:text property="specification" />
						</td>
					</tr>
					<tr>
						<td>
							品牌
						</td>
						<td>
							<html:text property="brand" />
						</td>
						<td>
							用途

						</td>
						<td>
							<html:text property="use" />
						</td>
						<td>
							用途(日文)
						</td>
						<td>
							<html:text property="useJa" />
						</td>
						<td>
							建议到货周期
						</td>
						<td>
							<html:text property="suggestArriveCyc" />
						</td>
						<td>
							建议最小订购量
						</td>
						<td>
							<html:text property="suggestLeastQty" />
						</td>
					</tr>
					<tr>
						<td>
							现场订购数

						</td>
						<td>
							<html:text property="destineQty"/>
						</td>
						<td>
							适用车型
						</td>
						<td>
							<html:text property="carType"></html:text>
						</td>
						<td>
							建议供应商

						</td>
						<td>
							<html:select property="suggestSupplier">
								<html:option value=""></html:option>
								<html:option value="1">
									正式供应商1
								</html:option>
								<html:option value="2">
									正式供应商2
								</html:option>
								<html:option value="3">
									正式供应商3
								</html:option>
								<html:option value="4">
									正式供应商4
								</html:option>
								<html:option value="NG">
									不推荐

								</html:option>
							</html:select>
						</td>
						<td>
							费用代码
						</td>
						<td>
							<html:text property="chargeCode" />
						</td>
						<td>
							管理代码
						</td>
						<td>
							<html:text property="manageCode" />
						</td>
					</tr>
					<tr>
						<td>
							预计使用量

						</td>
						<td>
							<html:text property="intendingUseQty" value=""/>
						</td>
						<td>
							单位
						</td>
						<td>
							<html:select property="unit">
								<html:option value="1">
									个

								</html:option>
								<html:option value="2">
									件

								</html:option>
								<html:option value="3">
									台

								</html:option>
								<html:option value="4">
									KG
								</html:option>
								<html:option value="5">
									M2
								</html:option>
							</html:select>
						</td>
						<td>
							建议制造商
						</td>
						<td>
							<html:text property="suggestManufacturer" />
						</td>
						<td colspan="2">
							机器设备
						</td>
						<td>
						</td>
					</tr>
					<tr>
						<td>
							资材分类
						</td>
						<td>
							<html:select property="sortID">
								<html:option value=""></html:option>
								<html:option value="1">
									资材分类1
								</html:option>
								<html:option value="2">
									资材分类2
								</html:option>
								<html:option value="3">
									资材分类3
								</html:option>
								<html:option value="4">
									资材分类4
								</html:option>
								<html:option value="5">
									资材分类5
								</html:option>
							</html:select>
						</td>
						<td>
							紧急度
						</td>
						<td>
							<html:select property="exigency">
								<html:option value="000001">
									一般

								</html:option>
								<html:option value="000002">
									紧急

								</html:option>
							</html:select>
						</td>
						<td colspan="2"></td>
						<td>
							固定资产编码
						</td>
						<td>
							<html:text property="machineStuffNo" />
						</td>
						<td>
							名称
						</td>
						<td>
							<html:text property="machineStuffName" />
						</td>
					</tr>
					<tr>
						<td>
							优先度

						</td>
						<td>
							<html:select property="priority">
								<html:option value=""></html:option>
								<html:option value="000001">
									号口前纳入

								</html:option>
								<html:option value="000002">
									号口后6个月内纳入

								</html:option>
								<html:option value="000003">
									只编品番暂不订购
								</html:option>
							</html:select>
						</td>
						<td colspan="2">
							(新车型导入时填写)
						</td>
						<td colspan="2">
						</td>
						<td>
							制造商名称
						</td>
						<td>
							<html:text property="machineStuffManufacturer" />
						</td>
						<td>
							使用量

						</td>
						<td>
							<html:text property="machineStuffUseQty" />
						</td>
					</tr>
					<tr>
						<td colspan=2>
							MSDS/劳保质量检测报告

						</td>
						<td colspan=2>
						</td>
						<td>
							附件
						</td>
						<td colspan=1>
						</td>
						<td>
							备注
						</td>
						<td colspan="3"><html:text property="remark" />
						</td>
					</tr>
				</table>
			</div>
			<table align="center">
				<tr>
					<td>
						<input type="button" value="submit" name="submit1"
							onclick=
	doSubmit();
></input>
						<input type="button" value="reset" name="reset1" onclick=
	reset();
></input>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
	<script type="text/javascript" language="Javascript">
	function doSubmit() {
		var objFrm = document.forms[0];
			clearErrorColor();
			if(validatePage1024Form(objFrm)){
			objFrm.actionMethodId.value = 'submitApply';
			submitForm(objFrm,"<bean:message key='baseSample.doubleSubmit'/>");
			}
	}

	function reset() {
		document.forms[0].reset();
		clearErrorColor();
	}

</script>
	<html:javascript formName="page1024Form" />
		<jsp:include page="/sys/sysJsp/common/showAllTypeMessage.jsp"
		flush="true" />
</html>
