<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
<script type="text/javascript">
	function vsubmit(t) {
		var close = confirm("确定要取消？");
		/* if (close) {
			//window.open("sssss.html");
		} else {
			//window.event;
		} */
		return close;
	}
</script>

</head>

<%
	int index = 0;
%>
<body>

	<jsp:include page="../top.jsp" flush="true" />
	<!-- 导航 -->
	<jsp:include page="../vnavi.jsp" flush="true" />
	<!--头部结束-->
	<br />
	<br />

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
				<img src="img/use_ico.gif">&nbsp;&nbsp;我的订单.
				<div id="dashed"></div>
				<div class="warning">小贴士：最多只能购物车25辆车，请及时清理您的我的订单.</div>
				<div style="margin-top: 5px; margin-bottom: 5px; border-top: solid 1px #FFCD9B; border-bottom: solid 1px #FFCD9B;">

					<c:choose>
						<c:when test="${requestScope.message != null}">
							<font size="5" color="red">${requestScope.message}</font>
						</c:when>
						<c:otherwise>
							<table width="800" border="0" cellpadding="0" cellspacing="0">

								<tbody>
									<tr>
										<td width="350" align="center">
											<div style="float: left; width: 40%">
												<strong>点击图片详情</strong>
											</div>
											<div style="float: right; width: 60%">
												<strong>商品.简介</strong>
											</div>
										</td>
										<td width="50" height="25" align="center"><strong>供货商</strong></td>
										<td width="150" height="25" align="center"><strong>联系方式</strong></td>
										<td width="100" height="25" align="center"><strong>当前价</strong></td>
										<!-- 										<td width="100" height="25" align="center"><strong>数量</strong></td>
 -->
										<td width="130" height="25" align="center"><strong>状态</strong></td>
									</tr>
									<c:forEach var="auctionInfo" varStatus="st" items="${requestScope.auctionInfo}">
										<tr style="border-bottom: solid 1px #c2d5e3;">
											<td>
												<div style="float: left; width: 42%">
													<a href="QueryServlet?view=VehicleVo&queryCondition=v_id&queryValue=${auctionInfo.v_id }" target="_blank"><img
														src="${auctionInfo.imagePath}" style="display: block" border="0" height="90px" width=130px; /></a>
													<!-- <td align="left" width="100"><a href="#" target="_blank"> -->
												</div>
												<div style="float: right; width: 58%">
													<a style="font-size: 8px"> <a href="QueryServlet?view=VehicleVo&queryCondition=v_id&queryValue=${auctionInfo.v_id }" target="_blank"><strong>${auctionInfo.vname}</strong></a>
													</a>
													<ul style="font-size: 10px">
														<li>底价：<strong>${auctionInfo.bidSpri}元</strong></li>
														<li>保证金：<strong>${auctionInfo.bidSpri * 0.15}元</strong></li>
														<li>车源地：<strong>${auctionInfo.source}</strong></li>
														<li>专利：<strong>${auctionInfo.regTime}</strong></li>
													</ul>
												</div>
											</td>
											<!-- Vive Add -->
											<td>
												<div align="center">
													<!--  <img alt="中国人保浙江省分公司" src="${auctionInfo.v_source }"
												height="50" width="130" />   -->
													<div align="center">${auctionInfo.u_name}</div>
												</div>
											</td>

											<td>
												<%-- <div style="font-weight: bold; font-size: 10px; text-align: center;" class="timeCss">
											<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${auctionInfo.bidTime}" type="both" />
											<img src="img/clock0.gif" border="0" /> <br /> -- <br />
											<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${auctionInfo.bidEndTime}" type="both" />

										</div> --%>
												<div align="center">${auctionInfo.u_tel}</div>

											</td>
											<td>
												<div align="center">${auctionInfo.bidSpri}元</div>
											</td>
											<!-- Vive num -->
											<%-- <td>


												<table>
													<tr>
														<td>
															<form action="cancelindent.html" method="post">
																<input type="hidden" name="vuser" value="${sessionScope.user.u_id}" /> <input type="hidden" name="v_id" value="<%out.print(index);%>" />
																<input type="hidden" name="num" style="width: 60px;" value="${vlist[st.index].version}" /> <input type="hidden" name="vbox" value="-" />
																<input type="submit" id="auction" value="   -  " />
															</form>
														</td>
														<td>
															<form action="cancelindent.html" method="post">
																<input type="hidden" name="vuser" value="${sessionScope.user.u_id}" /> <input type="hidden" name="v_id" value="<%out.print(index);%>" />
																<input type="text" name="num" style="width: 60px;" value="${vlist[st.index].version}" /> <input type="hidden" name="vbox" value="*" />
																<input type="submit" id="auction" value="OK" />

															</form>
														</td>

														<td>
															<form action="cancelindent.html" method="post">
																<input type="hidden" name="vuser" value="${sessionScope.user.u_id}" /> <input type="hidden" name="v_id" value="<%out.print(index);%>" />
																<input type="hidden" name="num" style="width: 60px;" value="${vlist[st.index].version}" /> <input type="hidden" name="vbox" value="+" />
																<input type="submit" id="auction" value="  +  " />
															</form>
														</td>
												</table>


											</td> --%>
											<td>
												<div align="center">
													<%
														Date date = new Date();
																	long dateTime = date.getTime();
													%>
													<c:set var="dateTime" value="<%=dateTime%>"></c:set>
													<c:choose>
														<c:when test="${dateTime >= auctionInfo.bidTime.getTime()}">
															<%-- 										Vive 			<a href="user_file/NetworkAuction/NetworkAuction.jsp?v_id=${auctionInfo.v_id}" id="auction">取消购买</a>
 --%>
															<!-- 													<a href="VCancle.html?vid=10" id="auction">取消购买</a>
 -->
															<!-- 													<a href="cancleindent?action=toServlet" id="auction">取消购买</a>
 -->
															<c:choose>
																<c:when test="${auctionInfo.vsState <= 0}">
       正在购物车
    </c:when>
																<c:when test="${auctionInfo.vsState <= 1}">
        正在等待系统确认
    </c:when>
																<c:otherwise>
        No comment sir...
    </c:otherwise>
															</c:choose>

															<form action="query2/AttentionVehicle/u_id=${sessionScope.user.u_id }" method="post" onsubmit="javascript:return vsubmit(this)">
																<input type="hidden" name="vuser" value="${sessionScope.user.u_id}" /> <input type="hidden" name="v_id"
																	value="<%out.print(index++);%>" /> <input type="hidden" name="vbox" value="/" /> <input type="submit" id="auction" value="取消购买" />
															</form>

														</c:when>
														<c:when test="${dateTime > auctionInfo.bidEndTime.getTime()}">
											暂不开售
										</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${auctionInfo.stopAuction == 1}">
													物品停止出售
													</c:when>
																<c:otherwise>
													物品即将开售
													</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>
												</div>
											</td>
										</tr>


									</c:forEach>

								</tbody>


							</table>
						</c:otherwise>
					</c:choose>

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