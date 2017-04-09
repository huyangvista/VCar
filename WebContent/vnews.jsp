<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>二手车交易网</title>
</head>
<body>
	<jsp:include page="user_file/top.jsp" flush="true" />
<!-- 头 -->
<!-- 导航 -->
	<jsp:include page="user_file/vnavi.jsp" flush="true" >
	 <jsp:param name="vnavIndex" value="0"/>   
	 </jsp:include>

<!-- 尾-->
	<jsp:include page="user_file/foot.jsp" flush="true" />
</body>
</html>