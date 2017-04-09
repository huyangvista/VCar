<%@page import="vive.VScreen"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title>二手车交易网</title>
<link type="text/css" href="css/jquery.css" rel="stylesheet" />
<link type="text/css" href="css/main.css" rel="stylesheet" />
<link href="css/font.css" rel="stylesheet" type="text/css">
<link href="css/first.css" rel="stylesheet" type="text/css">
<link href="css/index.css" rel="stylesheet" type="text/css" />
<link href="css/page.css" rel="stylesheet" type="text/css" />
<script src="js/select/error.js"></script>
<script src="js/select/AttestAndAttention.js"></script>



</head>

<body>
	<jsp:include page="../top.jsp" flush="true" />

	<input type="hidden" id="msg" value=${info[0] } />

	<!-- 导航 -->
	<jsp:include page="../vnavi.jsp" flush="true">
		<jsp:param name="vnavIndex" value="4" />
	</jsp:include>

	<br />
	<br />
	<br />
	<br />
	<br />
	<div style="margin-bottom: 5px; border-top: solid 0px #c2d5e3; border-bottom: solid 0px #c2d5e3; margin-left: 185px;">


		<%
			//处理筛选
			VScreen vscreen = new VScreen(request);
			vscreen.vscreen();
			vscreen.vrepe();
			vscreen.vrepeSell();
			vscreen.vdesc();
		%>

		<form action="ypgonggao.html" method="post">
			按条件进行查找 &nbsp&nbsp 按名称 <input type="text" name="vname" value="<%out.print(vscreen.vname == null ? "" : vscreen.vname);%>" /> &nbsp 按型号 <input
				type="text" name="vtype" value="<%out.print(vscreen.vtype == null ? "" : vscreen.vtype);%>" /> &nbsp 按价格 <input type="" text"" name="vmin"
				value="<%out.print(vscreen.vmin == null ? "" : vscreen.vmin);%>" /> - <input type="" text"" name="vmax"
				value="<%out.print(vscreen.vmax == null ? "" : vscreen.vmax);%>" /> &nbsp &nbsp <input type="submit"
				style="background: url(img/nav_li_hover.gif); width: 80px; height: 40px;" id="auction" value="开始筛选" />
		</form>
		<br />
		<form>
			<table border="0" width="970" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<td height="25" colspan="2" bgcolor="#f66"><strong class="fontCss"> 车辆基本信息</strong></td>
						<td width="210" height="25" bgcolor="#f66" align="center"><strong class="fontCss"> 登记时间 </strong></td>
						<td width="78" height="25" bgcolor="#f66" align="center"><strong class="fontCss"> 出售价格 </strong><b id="ss_pro__1" class="font14"> <span
								id="img_pro_1"> </span>
						</b></td>
						<td width="173" height="25" bgcolor="#f66" align="center"><strong class="fontCss"> 车辆来源</strong></td>
						<td width="50" height="25" bgcolor="#f66" align="center"><strong class="fontCss"> 关注</strong></td>
						<!-- 						<td width="245" height="25" bgcolor="#f66" align="center"><strong class="fontCss"> 操作</strong></td>
 -->
					</tr>
				</thead>

				<tbody>
					<c:forEach var="entry" items="${PrTaNoticeList}" varStatus="status">
						<tr>
							<td style="border-bottom: 1px #ccc solid; padding: 2px;" width="164"><a href="vihicle-${entry.v_id }.html" target="_blank"><img
									src="${entry.imageName}" style="display: block" border="0" height="88" width="160" /> </a></td>
							<td align="left" width="100"><a href="vihicle-${entry.v_id }.html" target="_blank"> <strong>${entry.vname}</strong>
							</a>
								<ul>
									<li>底价：<strong>${entry.bidSpri } 元</strong></li>
									<li>保证金：<strong>${entry.pledge} 元</strong></li>
									<li>车源地：<strong>${entry.source}</strong></li>
									<li>初次登记：<br /> <strong>${entry.regTime}</strong></li>
								</ul></td>
							<td align="center" width="210">
								<div style="font-weight: bold" class="timeCss">
									开始时间<br /> &nbsp;&nbsp;&nbsp;&nbsp;
									<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${entry.bidTime}" type="both" />
									<img src="img/clock0.gif" border="0" /> <br /> -- <br /> 结束时间<br />
									<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${entry.bidEndTime}" type="both" />
								</div>
							</td>
							<td align="center" width="78"><b id="ss_pro__1" class="font14"> <span id="img_pro_1"> </span> ￥ <span id="newprice_1">
										${entry.bidSpri} </span>
							</b></td>
							<td width="173">
								<div align="center">
									<%-- 									<img alt="" src="${entry.v_source }" height="50" width="160" />
 --%>
									<strong>${vlistUser[status.index].v_uid } </strong>

								</div>
							</td>
							<td align="center" valign="center" width="10">
								<div id="xpp">

									<font id="appCount${entry.v_id }" color="#F00">${entry.attCout}</font></a>
								</div>

							</td>

							<%-- <td align="center" valign="center" width="245">
								<div id="xpp">
									<a href="javascript:void(0)" rel="freezeUsers-24253" id="tooltip_1" title="${entry.attCout}人登记" style="color: black;"> <img alt="查看竞价会员"
										src="img/shopCar.png" /> 
										<font id="appCount${entry.v_id }" color="#F00">${entry.attCout}</font></a>
								</div>
								<div>
									<a href="javascript:setRequest('${sessionScope.user.u_id }','${entry.v_id}','state' , '0' , '${entry.attCout}')" class="indexchk"> <strong>
											<!-- 竞价登记 -->
									</strong>
									</a> 
									<a href="javascript:setRequest('${sessionScope.user.u_id }','${entry.v_id}','attention' , '1', '${entry.attCout}')" class="indexchk"> <strong>
											加入购物车</strong>
									</a>
								</div>
							</td> --%>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div align="center">
				<ul class="pagination">
					<%-- 					<li><a>共有${page.count}条记录</a></li>
 --%>
					<li><a>共有<%=vscreen.vcount() %>条记录</a></li>
					<li><a>第${page.page}页</a></li>
					<li><a href="ypgonggao/skip=1">首页</a></li>
					<li><a href="ypgonggao/skip=${page.page-1<=1?1:page.page- 1}"> 上一页 </a></li>
					<li><a href="ypgonggao/skip=${page.page + 1 >=page.lastPage?page.lastPage:page.page + 1}"> 下一页</a></li>
					<li><a href="ypgonggao/skip=${page.lastPage}"> 末页</a></li>
				</ul>
			</div>

		</form>
	</div>

	<jsp:include page="../foot.jsp" flush="true" />
	<!-- 尾部结束  -->
</body>

</html>