﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="vdll.utils.UploadFile" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
<link rel="shortcut icon" type="image/x-icon" />
<title>二手车交易网-网站</title>
<link type="text/css" href="css/common.css" rel="stylesheet" />
<link type="text/css" href="css/umain.css" rel="stylesheet" />
<script type="text/javascript" charset="utf-8">
	function showdiv() {
		var div = document.getElementById("divx");
		var close = document.getElementById("gb");
		div.style.display = "";
		close.style.display = "";

	}
	function closediv() {
		var div = document.getElementById("divx");
		var close = document.getElementById("gb");
		div.style.display = "none";
		close.style.display = "none";
	}
</script>

<link href="css/index.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="css/foot.css" rel="stylesheet" />
<script src="js/select/error.js"></script>
</head>
<body>
	<jsp:include page="../top.jsp" flush="true" />
	<input type="hidden" id="msg" value=${info[0] } />
	<!-- 导航 -->
	<jsp:include page="../vnavi.jsp" flush="true" />
	<br />
	<br />

	<!--头部结束-->
	<div id="main1_x">
		<div id="w970" style="margin-left:185px;">
			<div id="navi">
				<a href="">首页</a> <img src="img/next.png" height="10" width="15">
				会员中心
			</div>
			<div id="umain-nav" style="margin-bottom:5px;">
				<table border="0" cellspacing="4" cellpadding="0">
					<tbody>
						<tr>
							<td><a target="_top" > <img name="fheadimg"
									id="fheadimg" title="资料" src="img/head0.gif"> </a></td>
							<td valign="top">
								<ul>
									<li><a target="_top">${sessionScope.user.u_name
											}</a></li>
									<br />
									<li>￥<span id="red"><b class="font14">0</b>
									</span> <a target="_top">充值</a>
									</li>
									<li>￥<span id="red"><b class="font14">0</b>
									</span>
									</li>
									<li><a >退出</a>
									</li>
								</ul></td>
						</tr>
					</tbody>
				</table>
				<div id="bk-b01" style="font-size:14px;color:#2366b3;"
					align="center">
					<div
						style="background-image:url(img/mut.gif); background-repeat:no-repeat;background-position: 40px;">
						<strong>账号信息</strong>
					</div>
				</div>
				<ul class="list-u-menu">
					<li id="personInfo"><a href="user.html">个人信息</a></li>
					<li id="renzheng"><a href="user/attest.html">实名认证</a></li>
					<li id="alter"><a
						href="query/User/u_id=${sessionScope.user.u_id }">资料修改</a></li>
					<li id="password"><a href="user/password.html">密码修改</a></li>
					<!--    <li><a href="/financial">余额提现</a></li>
     <li><a href="/addmoney">汇款充值</a></li>  -->
				</ul>
				<div id="bk-b01" style="font-size:14px;color:#2366b3; "
					align="center">
					<div
						style="background-image:url(img/mut.gif); background-repeat:no-repeat;background-position: 40px;">
						<strong>我的车辆</strong>
					</div>
				</div>
				<ul class="list-u-menu">
					<li id="dengji"><a
						href="query2/RegistrationVehicle/u_id=${sessionScope.user.u_id }">登记车辆</a>
					</li>
					<li id="guanzhu"><a
						href="query2/AttentionVehicle/u_id=${sessionScope.user.u_id }">我的订单.</a>
					</li>
					<li id="chengjiao"><a
						href="query2/DealVehicle/u_id=${sessionScope.user.u_id }">成交车辆</a>
					</li>
				</ul>
			</div>
			<div id="umain-right" style="margin-bottom:5px;padding-top:2px;">
				<img src="img/use_ico.gif">&nbsp;&nbsp;资料修改
				<div id="dashed"></div>

				<form id="formPhone" method="post" name="creator"	enctype="multipart/form-data"	action="UploadHead" onSubmit="return IsEmail();">

					<input type="file" id="ff"   name="ff" value=""> <br>
					<input type="hidden" id="uid"  name="uid" value="${sessionScope.user.u_id }"> <br>

					<input type="button" value="上传头像">
				</form>

				<form method="post" name="creator"		action="UserServlet?operate=User.update" onSubmit="return IsEmail();">
					<table width="750" border="0" cellpadding="0" cellspacing="0">

						<tbody>
							<tr>
								<td width="146"></td>
								<td valign="bottom"><a href="javascript:void(0)"		   rel="headimg" id="clickme" title="">


									<img id="hdimg" src="img/head0.gif">

									<img width="100" height="100" id="hdimg" src="userhead/${sessionScope.user.u_id }.png">


									<input type="hidden" id="edithead"		name="head" value="0"> <br>
										${sessionScope.user.u_id } </a>
								</td>
							</tr>
							<input type="hidden" name="u_id" value=${sessionScope.user.u_id } />
							<tr>
								<td width="146" height="45"><div align="right">真实姓名：</div>
								</td>
								<td width="604"><input name="tname" type="text" id="gin2"
									maxlength="50" value="${list[0].tname }"><font
									color="red">*姓名必须为汉字</font></td>
							</tr>
							<tr>
								<td height="45"><div align="right">昵称：</div></td>
								<td><input  name="u_name" type="text"
									id="gin2" maxlength="50" value="${list[0].u_name }"><font
									color="red">*不可修改</font></td>
							</tr>
							<tr>
								<td height="45"><div align="right">身份证号：</div></td>
								<td><input name="cardID" type="text" id="gin2"
									maxlength="50" value="${list[0].cardID }"><font
									color="red">*请输入有效身份证(格式必须满足条件)</font></td>
							</tr>
							<tr>
								<td height="45"><div align="right">手机号：</div></td>
								<td><input name="tel" type="text" id="gin2" maxlength="50"
									value="${list[0].tel }"><font color="red">*请填写有效手机号码</font>
								</td>
							</tr>
							<tr>
								<td height="45"><div align="right">电子邮箱：</div></td>
								<td><input name="email" type="text" id="gin2"
									maxlength="50" value="${list[0].email }"><font
									color="red">*请填写正确有效的邮箱</font></td>
							</tr>
							<tr>
								<td height="45"><div align="right">地址：</div></td>
								<td><input name="u_address" type="text" id="gin2"
									maxlength="50" value="${list[0].u_address }"></td>
							</tr>

							<tr>
								<td height="45"></td>
								<td>修改后点击提交</td>
							</tr>
							<tr>
								<td height="45"></td>
								<td><input type="submit" name="Submit" value="提交"	   onclick="uploadPhone()"				class="button2"></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>

		<script>
			function uploadPhone() {

				document.getElementById("formPhone").submit();
			}
		</script>


		<!-- 尾部开始 -->
		<jsp:include page="../foot.jsp" flush="true" />
		<!-- 尾部结束  -->
</body>
</html>
