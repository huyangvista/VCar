<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<title>二手车交易网</title>
<link type="text/css" href="css/common.css" rel="stylesheet" />
<link type="text/css" href="css/umain.css" rel="stylesheet" />
<link href="css/index.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="css/foot.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery.min.js"></script>
</head>
<body>
	<c:if test="${sessionScope.user == null}">
		<c:redirect url="login.html"></c:redirect>
	</c:if>
	<jsp:include page="../top.jsp" flush="true" />
	<!-- 导航 -->
	<jsp:include page="../vnavi.jsp" flush="true">
		<jsp:param name="vnavIndex" value="2" />
	</jsp:include>
	<!--头部结束-->
	<br />
	<br />

	<div id="main1_x">
		<!--<div align="center"><a href="/vip" target="_blank"><img src="/resource/img/hf.gif" border="0"></a></div>-->
		<div id="w970" style="margin-left:185px;">
			<div id="navi">
				<a href="">首页</a> <img src="img/next.png" height="10" width="15">
				会员中心
			</div>
			<div id="umain-nav" style="margin-bottom:5px;">
				<table border="0" cellspacing="4" cellpadding="0">
					<tbody>
						<tr>
							<td><a target="_top"> <img name="fheadimg"
									id="fheadimg" title="资料" src="img/head0.gif"> </a>
							</td>
							<td valign="top">
								<ul>
									<li><a target="_top">${sessionScope.user.u_name }</a>
									</li>
									<br />
									<li>￥<span id="red"><b class="font14">0</b> </span> <a
										target="_top">充值</a></li>
									<li>￥<span id="red"><b class="font14">0</b> </span></li>
									<li><a>退出</a></li>
								</ul>
							</td>
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
				<img src="img/use_ico.gif" />&nbsp;&nbsp;个人信息
				<div id="dashed"></div>
				<div id="error">
					<span>${sessionScope.user.u_name }</span>，您好！请先完善资料， <a
						href="QueryServlet?view=User&queryCondition=u_id&queryValue=${sessionScope.user.u_id }">立即完善</a>
				</div>
				<fieldset>
					<legend>
						<b>帐号信息</b>
					</legend>
					<div id="umain-user">
						<strong>账号：</strong>${sessionScope.user.u_id }(个人用户) &nbsp;&nbsp;
						<strong>昵称：</strong>${sessionScope.user.u_name } &nbsp;&nbsp; <strong>VIP：</strong>${sessionScope.user.r_rank
						== 1? "是" : "否" } &nbsp;&nbsp;
					</div>
				</fieldset>
				<fieldset style="padding:10px;">
					<legend>
						<b>短信开通状态</b>
					</legend>
					<div id="umain-user">
						<form id="form1" name="form1" method="post" action="changesms">
							<input name="sms" value="0" type="hidden" /> <strong>关闭</strong>
						</form>
					</div>
				</fieldset>
				<fieldset style="padding:10px;">
					<legend>
						<b>财务信息</b>
					</legend>
					<div id="umain-user">
						总冻结资金： <span style="color:red;font-size:14px;" id="balance"><b>0</b>
						</span> 元 &nbsp;&nbsp;电子钱包： <span style="color:red;font-size:14px;"
							id="balance"><b>0</b>
						</span> 元 &nbsp;&nbsp;
					</div>
				</fieldset>
			</div>
		</div>
	</div>


	<!-- 尾部开始 -->
	<jsp:include page="../foot.jsp" flush="true" />
	<!-- 尾部结束  -->
</body>
</html>
