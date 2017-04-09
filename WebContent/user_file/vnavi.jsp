<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 定义选项卡 -->

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>navigation</title>
</head>
<body>
<div class="menu_navcc">
    <div class="menu_nav clearfix">
        <!-- <ul class="nav_content" id="nav">
            <li class="current"><a href="index.html" title="首页"> <span> 首页 </span>
            </a></li>
            <li><a href="paimai.html" title="网络拍卖"> <span> 购物车 </span>
            </a></li>
            <li><a href="user.html" title="会员中心"> <span> 会员中心 </span>
            </a></li>
            <li><a href="vip.html" title="VIP大厅"> <span> 我要卖 </span>
            </a></li>
            <li><a href="ypgonggao.html" title="预拍公告"> <span> 正在交易</span>
            </a></li>
            <li><a href="Notice_front.html" title="帮助中心"> <span>帮助中心 </span>
            </a></li>
            <li><a href="company.html" title="公司简介"> <span> 公司简介 </span>
            </a></li>
        </ul>
        <div class="menu_nav_right"></div>
         -->


        <ul class="nav_content" id="nav">
            <%
                int vnavIndex = 0;
                String vsreq = request.getParameter("vnavIndex");
                if(vsreq != null)                vnavIndex = Integer.parseInt(vsreq);

                //String[] vnavText = { "首页", "购物车", "会员中心", "我要卖", "正在交易", "帮助中心", "公司简介" };
                //String[] vnavHref = { "index.html", "paimai.html", "user.html", "vip.html", "ypgonggao.html","Notice_front.html", "company.html" };
                String[] vnavText = {"首页", "交易大厅", "卖家中心", "会员中心", "帮助中心", "公司简介", "我的订单" /* , "" */};
                String[] vnavHref = {"index.html", "ypgonggao.html", "vip.html", "user.html", "Notice_front.html", "company.html", "vmylist.html" /* , "paimai.html" */};
                int[] viIndex = {0, 4, 3, 2, 5, 6, 1};
                for (int i = 0; i < vnavText.length; i++) {
                    String vsAdd = "";
                    if (vnavIndex == viIndex[i])
                        vsAdd = "class='current'";
                    out.print(String.format("<li %s ><a href='%s' title='%s'><span> %s </span></a></li> ", vsAdd,
                            vnavHref[i], vnavText[i], vnavText[i]));
                }
            %>
        </ul>


    </div>
</div>
</body>
</html>