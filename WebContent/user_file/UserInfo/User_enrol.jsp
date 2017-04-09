<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<html>
<head>
<base href="<%=basePath%>">
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
<link rel="shortcut icon" type="image/x-icon" />
<title>二手车交易网-网站</title>
<link type="text/css" href="css/common.css" rel="stylesheet" />
<link type="text/css" href="css/umain.css" rel="stylesheet" />
<link href="css/index.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="css/foot.css" rel="stylesheet" />
<link href="css/page.css" rel="stylesheet" type="text/css" />
</head>
<body>

	<jsp:include page="../top.jsp" flush="true" />
	<!-- 导航 -->
	<jsp:include page="../vnavi.jsp" flush="true" />
	<br />
	<br />

	<!--头部结束-->
	<div id="main1_x">
		<!--<div align="center"><a href="/vip" target="_blank"><img src="/resource/img/hf.gif" border="0"></a></div>-->
		<div id="w970" style="margin-left: 185px;">
			<div id="navi">
				<a href="">首页</a> <img src="img/next.png" height="10" width="15"> 会员中心
			</div>
			<div id="umain-nav" style="margin-bottom: 5px;">
				<table border="0" cellspacing="4" cellpadding="0">
					<tbody>
						<tr>
							<td><a target="_top"> <img name="fheadimg" id="fheadimg" title="资料" src="img/head0.gif">
							</a></td>
							<td valign="top">
								<ul>
									<li><a target="_top">${sessionScope.user.u_name
											}</a></li>
									<br />
									<li>￥<span id="red"><b class="font14">0</b> </span> <a target="_top">充值</a>
									</li>
									<li>￥<span id="red"><b class="font14">0</b> </span>
									</li>
									<li><a>退出</a></li>
								</ul>
							</td>
						</tr>
					</tbody>
				</table>

				<div id="bk-b01" style="font-size: 14px; color: #2366b3;" align="center">
					<div style="background-image: url(img/mut.gif); background-repeat: no-repeat; background-position: 40px;">
						<strong>账号信息</strong>
					</div>
				</div>
				<ul class="list-u-menu">
					<li id="personInfo"><a href="user.html">个人信息</a></li>
					<li id="renzheng"><a href="user/attest.html">实名认证</a></li>
					<li id="alter"><a href="query/User/u_id=${sessionScope.user.u_id }">资料修改</a></li>
					<li id="password"><a href="user/password.html">密码修改</a></li>
					<!--    <li><a href="/financial">余额提现</a></li>
     <li><a href="/addmoney">汇款充值</a></li>  -->
				</ul>
				<div id="bk-b01" style="font-size: 14px; color: #2366b3;" align="center">
					<div style="background-image: url(img/mut.gif); background-repeat: no-repeat; background-position: 40px;">
						<strong>我的车辆</strong>
					</div>
				</div>
				<ul class="list-u-menu">
					<li id="dengji"><a href="query2/RegistrationVehicle/u_id=${sessionScope.user.u_id }">登记车辆</a></li>
					<li id="guanzhu"><a href="query2/AttentionVehicle/u_id=${sessionScope.user.u_id }">我的订单.</a></li>
					<li id="chengjiao"><a href="query2/DealVehicle/u_id=${sessionScope.user.u_id }">成交车辆</a></li>
				</ul>
			</div>
			<div id="umain-right" style="margin-bottom: 5px; padding-top: 2px;">
				<img src="img/use_ico.gif">&nbsp;&nbsp;登记车辆
				<div id="dashed"></div>
				<div class="warning">小贴士：竞价之前,为了保证您的竞价顺畅，请尽量关闭消耗网络带宽和计算机性能的软件，建议您使用底部推荐的浏览器进行竞价</div>
				<div style="margin-top: 5px; margin-bottom: 5px; border-top: solid 1px #FFCD9B; border-bottom: solid 1px #FFCD9B;">


					<table width="755" border="1" cellpadding="1" cellspacing="1">

						<thead>
							<tr>
								<td width="9%"><div align="center">
										<strong>车辆名称</strong>
									</div></td>
								<td width="12%"><div align="center">
										<strong>车辆型号 </strong>
									</div></td>
								<td width="12%"><div align="center">
										<strong>车牌号</strong>
									</div></td>
								<!-- <td width="12%"><div align="center">
													<strong>会员联系方式</strong>
												</div></td> -->
								<td width="11%"><div align="center">
										<strong>联系方式</strong>
									</div></td>

							</tr>
						</thead>

						<tbody>
							<c:forEach var="sucInfor" varStatus="st" items="${requestScope.auctionInfo}">
								<tr>
									<td>
										<div align="center">${sucInfor.vname}</div>
									</td>
									<td><div align="center">${sucInfor.u_name}</div></td>
									<td><div align="center">${sucInfor.plateNo}</div></td>
									<%-- 												<td><div align="center">${sucInfor.tel}</div></td>
 --%>
									<td><div align="center">${sucInfor.u_tel}</div></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>
				<div align="center">
					<%-- <ul class="pagination">
						<li><a>共有${page.count}条记录</a></li>
						<li><a>第${page.page}页</a></li>
						<li><a href="query/RegistrationVehicle_${sessionScope.user.u_id }&skip=1">首页</a></li>
						<li><a href="query/RegistrationVehicle_${sessionScope.user.u_id }&skip=${page.page-1<=1?1:page.page- 1}"> 上一页 </a></li>
						<li><a href="query/RegistrationVehicle_${sessionScope.user.u_id }&skip=${page.page + 1 >=page.lastPage?page.lastPage:page.page + 1}">
								下一页</a></li>
						<li><a href="query/RegistrationVehicle_${sessionScope.user.u_id }&skip=${page.lastPage}"> 末页</a></li>
					</ul> --%>
				</div>
				<table width="755" border="0" cellpadding="0" cellspacing="0" style="margin-bottom: 5px;">
				</table>
			</div>
		</div>
	</div>

	<!-- 尾部开始 -->
	<jsp:include page="../foot.jsp" flush="true" />
	<!-- 尾部结束  -->
</body>
</html>
