<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!--[if IE 8 ]> <html class="ie ie8 lte_ie8" lang="zh-CN"> <![endif]-->
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--

用于遍历出售公告的一个相当于ajax的自动提交Servlet -->
<title>我的订单</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
<link rel="stylesheet" type="text/css" href="css/first.css" />
<link href="css/index.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="css/foot.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/tab.js"></script>
<script language="JavaScript">
	function isBrowser() {
		var result;
		var Sys = {};
		var ua = navigator.userAgent.toLowerCase();
		var s;
		(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : (s = ua
				.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : (s = ua
				.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : (s = ua
				.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua
				.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
		if (Sys.ie) {//Js判断为IE浏览器
			result = "ie";
		}

		if (Sys.firefox) {//Js判断为火狐(firefox)浏览器
			result = "firefox";
		}

		if (Sys.chrome) {//Js判断为谷歌chrome浏览器
			result = "chrome";
		}

		if (Sys.opera) {//Js判断为opera浏览器
			result = "opera";
		}
		if (Sys.safari) {//Js判断为苹果safari浏览器
			result = "safari";
		}
		return result;
	}

	var result = isBrowser();
	//Vive 注释的弹窗
	//alert(result);
	/*if(result.toString()=='chrome'&&result.toString()!='firefox'){
	 alert("您使用的不是360浏览器 ，火狐浏览器，谷歌浏览器 请下载以上浏览器 ");
	 window.location.href="download.jsp";
	 }*/
</script>
<SCRIPT type="text/javascript">
	var _c = _h = 0;
	$(document).ready(function() {
		$('#play  li').click(function() {
			var i = $(this).attr('alt') - 1;
			clearInterval(_h);
			_c = i;
			change(i);
		})
		$("#pic img").hover(function() {
			clearInterval(_h)
		}, function() {
			play()
		});
		play();
	})
	function play() {
		_h = setInterval("auto()", 3000);

	}
	function change(i) {
		$('#play li').css('background-color', '#000000').eq(i).css(
				'background-color', '#FF3000').blur();
		$("#pic img").fadeOut('slow').eq(i).fadeIn('slow');
	}
	function auto() {
		_c = _c > 6 ? 0 : _c + 1;

		change(_c);
	}
</SCRIPT>
<script>
	function checkForm2() {
		if (document.loginForm2.u_id.value == "") {
			alert("请输入账号！");
			return false;
		}
		if (document.loginForm2.psword.value == "") {
			alert("请输入密码！");
			return false;
		}
		return true;
	}
	function Method() {
		//执行action
		document.action = "ptns?view=haha";
		document.submit();
	}
</script>
<script src="js/select/error.js"></script>
<link href="css/page.css" rel="stylesheet" type="text/css" />
<script src="js/select/AttestAndAttention.js"></script>

<%
	int index = 0;
	int indexFinish = 0;
%>
</head>
<body>
	<!-- 主页 -->
	<jsp:include page="/user_file/top.jsp" flush="true" />
	<input type="hidden" id="msg" value=${info[0] } />

	<!-- 导航 -->
	<jsp:include page="/user_file/vnavi.jsp" flush="true">
		<jsp:param name="vnavIndex" value="1" />
	</jsp:include>


	<!-- 订单信息 -->
	<div style="height: auto !important; height: 10px; min-height: 10px;">
		<div style="margin-bottom: 5px; border-bottom: solid 1px #c2d5e3; margin-left: 180px; margin-right: 185px;">
			<%-- <c:choose>
				<c:when test="${requestScope.message != null}">
					<font size="5" color="red">${requestScope.message}</font>
				</c:when>
				<c:otherwise>
				<br />
				<br />
				<br />
				<br />
				<br />
					<font size="5" color="red">我的订单:</font>
				<br />
				<br />

					<table width="1000" border="0" cellpadding="0" cellspacing="0">

						<tbody>
							<tr>
								<td width="450" align="center">
									<div style="float: left; width: 42%">
										<strong>点击图片详情</strong>
									</div>
									<div style="float: right; width: 58%">
										<strong>商品.简介</strong>
									</div>
								</td>
								<td width="250" height="25" align="center"><strong>购买方</strong></td>
								<td width="250" height="25" align="center"><strong>联系方式</strong></td>
								<td width="200" height="25" align="center"><strong>当前价</strong></td>
								<td width="200" height="25" align="center"><strong>数量</strong></td>

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
												<li>生产地：<strong>${auctionInfo.source}</strong></li>
												<li>专利：<strong>${auctionInfo.regTime}</strong></li>
											</ul>
										</div>
									</td>
									<!-- Vive Add -->
									<td>
										<div align="center">

											<div align="center">${auctionInfo.u_name}</div>
											<div style="font-weight: bold; font-size: 10px; text-align: center;" class="timeCss"></div>
									</td>

									<td>
										<div align="center">${auctionInfo.u_tel}</div>
										</div>
									</td>
									<td>
										<div align="center">${auctionInfo.bidSpri}元</div>
									</td>
									<td>
										<div align="center">
											<strong> ${vlist[st.index].version}</strong>
										</div>
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
																									<a href="user_file/NetworkAuction/NetworkAuction.jsp?v_id=${auctionInfo.v_id}" id="auction">买家已经提交订单</a>

												<a id="auction">等待卖家发货</a>

												<form action="cancelindentFinish.html" method="post">
													<input type="hidden" name="vuser" value="${sessionScope.user.u_id}" /> <input type="hidden" name="v_id" value="<%out.print(index);%>" />
													<input type="hidden" name="vstate" value="-2" /> <input type="submit" id="auctionExit" value="我不想买了" />

												</form>
												<form action="cancelindentFinish.html" method="post">
													<input type="hidden" name="vuser" value="${sessionScope.user.u_id}" /> <input type="hidden" name="v_id" value="<%out.print(index++);%>" />
													<input type="hidden" name="vstate" value="0" /> <input type="submit" id="auction" value="处理完成" />

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


--%>

				<c:choose>
				<c:when test="${requestScope.message != null}">
					<font size="5" color="red">${requestScope.message}</font>
				</c:when>
				<c:otherwise>

				<font size="5" color="red">正在处理的订单:</font>
				<table width="1000" border="0" cellpadding="0" cellspacing="0">

					<tbody>
					<tr>
						<td width="450" align="center">
							<div style="float: left; width: 42%">
								<strong>点击图片详情</strong>
							</div>
							<div style="float: right; width: 58%">
								<strong>商品.简介</strong>
							</div>
						</td>
						<td width="250" height="25" align="center"><strong>购买方</strong></td>
						<td width="250" height="25" align="center"><strong>联系方式</strong></td>
						<td width="200" height="25" align="center"><strong>当前价</strong></td>
						<td width="200" height="25" align="center"><strong>数量</strong></td>

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

									<li>生产地：<strong>${auctionInfo.source}</strong></li>
										<%--
                                                                                        <li>时间：<strong>${vlist[st.index].vstime}</strong></li>
                                        --%>
								</ul>
							</div>
						</td>
						<!-- Vive Add -->
						<td>
							<div align="center">

								<div align="center">${auctionInfo.u_name}</div>
								<div style="font-weight: bold; font-size: 10px; text-align: center;" class="timeCss"></div>
						</td>

						<td>
							<div align="center">${auctionInfo.u_tel}</div>
		</div>
		</td>
		<td>
			<div align="center">${auctionInfo.bidSpri}元</div>
		</td>
		<td>
			<div align="center">
				<strong> ${auctionInfo.u_pledge}</strong>
			</div>
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
						<%-- 													<a href="user_file/NetworkAuction/NetworkAuction.jsp?v_id=${auctionInfo.v_id}" id="auction">买家已经提交订单</a>
                         --%>

						<c:choose>
							<c:when test="${vlist[st.index].v_vid <= 1}">
								<a id="auction">正在等待受理 </a>
							</c:when>
							<c:otherwise>
								<a id="auction">正在等待受理 </a>
							</c:otherwise>
						</c:choose>





						<%--<form action="cancelindentFinish.html" method="post">
							<input type="hidden" name="vuser" value="${sessionScope.user.u_id}" /> <input type="hidden" name="v_id"
																										  value="<%out.print(indexFinish++);%>" /> <input type="hidden" name="vstate" value="1" /> <input type="submit" id="auction"
																																																		  value="删除订单" />
						</form>--%>

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

			 <br /> <br /> <br />
			<c:choose>
				<c:when test="${requestScope.messageFinish != null}">
					<font size="5" color="red">${requestScope.messageFinish}</font>
				</c:when>
				<c:otherwise>

					<font size="5" color="red">已完成订单订单:</font>
					<table width="1000" border="0" cellpadding="0" cellspacing="0">

						<tbody>
							<tr>
								<td width="450" align="center">
									<div style="float: left; width: 42%">
										<strong>点击图片详情</strong>
									</div>
									<div style="float: right; width: 58%">
										<strong>商品.简介</strong>
									</div>
								</td>
								<td width="250" height="25" align="center"><strong>购买方</strong></td>
								<td width="250" height="25" align="center"><strong>联系方式</strong></td>
								<td width="200" height="25" align="center"><strong>当前价</strong></td>
								<td width="200" height="25" align="center"><strong>数量</strong></td>

								<td width="200" height="25" align="center"><strong>状态</strong></td>
							</tr>
							<c:forEach var="auctionInfo" varStatus="st" items="${requestScope.auctionInfoFinish}">
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

												<li>生产地：<strong>${auctionInfo.source}</strong></li>
<%--
												<li>时间：<strong>${vlist[st.index].vstime}</strong></li>
--%>
											</ul>
										</div>
									</td>
									<!-- Vive Add -->
									<td>
										<div align="center">

											<div align="center">${auctionInfo.u_name}</div>
											<div style="font-weight: bold; font-size: 10px; text-align: center;" class="timeCss"></div>
									</td>

									<td>
										<div align="center">${auctionInfo.u_tel}</div>
										</div>
									</td>
									<td>
										<div align="center">${auctionInfo.bidSpri}元</div>
									</td>
									<td>
										<div align="center">
											<strong> ${auctionInfo.u_pledge}</strong>
										</div>
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
<%-- 													<a href="user_file/NetworkAuction/NetworkAuction.jsp?v_id=${auctionInfo.v_id}" id="auction">买家已经提交订单</a>
 --%>

													<c:choose>
													<c:when test="${vlist[st.index].v_vid <= 1}">
														<a id="auction">订单已受理 </a>
													</c:when>
													<c:otherwise>
														<a id="auction">正在等待受理 </a>
													</c:otherwise>
													</c:choose>





													<%--<form action="cancelindentFinish.html" method="post">
														<input type="hidden" name="vuser" value="${sessionScope.user.u_id}" /> <input type="hidden" name="v_id"
															value="<%out.print(indexFinish++);%>" /> <input type="hidden" name="vstate" value="1" /> <input type="submit" id="auction"
															value="删除订单" />
													</form>
--%>
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



	<br />
	<!-- <hr size="80%"/> -->
	<br />
	<br />
	<br />
	<br />
	<br />



	<div style="text-align: center;">
		<!-- 添加信息 -->
		<%-- 		<jsp:include page="backstage/Vehicle/Vehicle_add2.jsp" flush="true" />
 --%>

		<!-- <form action="backstage/Vehicle/Vehicle_add.jsp" method="post">
			<input type="submit" id="auction" style="width: 400px; height: 80px; text-align: center; font-size: large;" value="发布二手车信息" />
		</form> -->
	</div>
	<br />
	<br />
	<br />
	<br />


	<!-- <frameset rows="45,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frameset rows="*" cols="174,*" framespacing="0" frameborder="no" border="0">
    <frame src="backstage/Vehicle/Vehicle_add.jsp" name="leftFrame" scrolling="no" noresize="noresize" id="leftFrame" title="leftFrame" />
  </frameset>
</frameset> -->




	<!-- 尾部开始 -->
	<jsp:include page="/user_file/foot.jsp" flush="true" />
	<!-- 尾部结束  -->
</body>
</html>
