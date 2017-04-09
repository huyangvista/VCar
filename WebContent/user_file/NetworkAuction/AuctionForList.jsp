<%@ page language="java" import="java.util.*,cn.zlpc.vo.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<html>
<head>
<base href="<%=basePath%>">
<title>二手车交易网</title>
<link rel="stylesheet" type="text/css" href="css/first.css" />
<link href="css/index.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="css/foot.css" rel="stylesheet" />
<script type="text/javascript" src="<%=path%>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=path%>/dwr/interface/AjaxRefresh.js"></script>
<script type="text/javascript" src="<%=path%>/dwr/util.js"></script>
<script type="text/javascript">
<!--
	window.onload = function() {
		var auction = document.getElementById("auction");
		var browerFlag = getOS();
		if (browerFlag) {

		} else {
			alert("为了保证竞拍的成功，请不要使用IE浏览器");
			auction.href = "";
		}

	};
	function getOS() {/* 
																									 	var OsObject = ""; */
		if (isIE = navigator.userAgent.indexOf("MSIE") != -1) {

			return false;
		}
		return true;
	}

	-->
</script>
<%
	int index = 0;
%>
</head>
<!--
 <script language='javascript' defer>if(confirm('您还没有登录，没有此权限；立即登录吗？')){top.location.href='../index.html'/*tpa=http://www.cygpm.com/loginhui.aspx*/}else{top.location.href='../index.htm'/*tpa=http://www.cygpm.com/Default.aspx*/}</script>-->

<body>

	<jsp:include page="../top.jsp" flush="true" />
	<!-- 导航 -->
	<jsp:include page="../vnavi.jsp" flush="true">
		<jsp:param name="vnavIndex" value="1" />
	</jsp:include>



	<br />
	<br />
	<br />
	<br />
	<br />
	<div style="height: auto !important; height: 10px; min-height: 10px;">
		<div style="margin-bottom: 5px; border-bottom: solid 1px #c2d5e3; margin-left: 180px; margin-right: 185px;">
			<c:choose>
				<c:when test="${requestScope.message != null}">
					<font size="5" color="red">${requestScope.message}</font>
				</c:when>
				<c:otherwise>
					<table width="1000" border="0" cellpadding="0" cellspacing="0">

						<tbody>
							<tr>
								<td width="450" align="center">
									<div style="float: left; width: 42%">
										<strong>点击图片详情</strong>
									</div>
									<div style="float: right; width: 58%">
										<strong>车辆简介</strong>
									</div>
								</td>
								<td width="250" height="25" align="center"><strong>车主</strong></td>
								<td width="250" height="25" align="center"><strong>联系方式</strong></td>
								<td width="200" height="25" align="center"><strong>当前价</strong></td>
								<td width="200" height="25" align="center"><strong>状态</strong></td>
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
												<li>初次登记：<strong>${auctionInfo.regTime}</strong></li>
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
													<form action="cancelindent.html" method="post">
														<input type="hidden" name="vuser" value="${sessionScope.user.u_id}" /> <input type="hidden" name="v_id" value="<%out.print(index++);%>" />
														<input style="background: url(img/nav_li_hover.gif); width: 100px; height: 30px;" type="submit" id="auction" value="删除商品" />
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

		<!-- 提交到  VCancle indent <form action="cancelindent.html" method="post">
			<input type="text" name="vuser" value="0"> 
			<input  type="submit" id="auction" value="00000"/>
		</form> -->



		<div style="margin-bottom: 5px; border-bottom: solid 1px #c2d5e3; margin-left: 880px; margin-right: 185px;">

			<br>
			<p style="width: 200px; height: 50px; font-size: 20px;">合计 : ${requestScope.vlsum}</p>

			<form action="vindent.html" method="post">
				<%-- <input type="text" name="user" value="${requestScope.auctionInfo[0].v_id}"> --%>
				<c:forEach var="vitem" varStatus="st" items="${requestScope.auctionInfo}">
					<input type="hidden" name="vuser" value="${sessionScope.user.u_id}">
					<input type="hidden" name="v_id" value="${vitem.v_id}">
					<input type="hidden" name="vstate" value="1">
				</c:forEach>
				<input type="submit" style="background: url(img/banner4.jpg); width: 200px; height: 50px; font-size: 20px;" value="提 交">
			</form>
		</div>
	</div>

	<!-- 底部信息 -->
	<jsp:include page="../foot.jsp" flush="true" />
	<!-- 尾部结束  -->
</body>
</html>
