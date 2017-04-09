<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>二手车交易网</title>
</head>
<body>
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
											<p>${auctionInfo.bidTime}</p>
										</div>
									</td>

									<td>
										<div style="font-weight: bold; font-size: 10px; text-align: center;" class="timeCss">
											<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${auctionInfo.bidTime}" type="both" />
											<img src="img/clock0.gif" border="0" /> <br /> -- <br />
											<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${auctionInfo.bidEndTime}" type="both" />

										</div>
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
													<a href="user_file/NetworkAuction/NetworkAuction.jsp?v_id=${auctionInfo.v_id}" id="auction">我要购买</a>
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
	</div>


</body>
</html>