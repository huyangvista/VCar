<%@page import="cn.zlpc.dao.BaseDao"%>
<%@page import="cn.zlpc.po.SourceImg"%>
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
<head>
<base href="<%=basePath%>">
<title>车</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<!-- basic styles -->
 <link href="css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="css/font-awesome.min.css" /> 
<link rel="stylesheet" href="css/ace.min.css" />   
<link rel="stylesheet" href="css/ace-rtl.min.css" /> 
<link rel="stylesheet" href="css/ace-skins.min.css" />

<script src="js/ace-extra.min.js"></script>
<script src="js/jquery.min.js"></script>
<script src="js/Calendar4.js"></script>
<script type="text/javascript">
	/**
	 *	实现表单提交
	 */
	function validate(f) {

		if (f.plateNo.value == "") {
			alert("车牌号 不能为空");
			f.v_id.focus();
			return false;
		}
		if (f.vname.value == "") {
			alert("车辆名称不能为空");
			f.vname.focus();
			return false;
		}
		var mobile = f.tel.value;
		if (mobile == "") {
			alert("联系方式不能为空");
			f.tel.focus();
			return false;
		}
		if (mobile.length != 11) {
			alert("联系方式错误");
			f.tel.focus();
			return false;
		}
		if (f.pledge.value == "") {
			alert("保证金不能为空");
			f.pledge.focus();
			return false;
		}
	}
</script>

</head>
<body>
	<center>
		<h2>
			<font color="#aa0000"> 添加车辆信息</font>
		</h2>
	</center>

	<form id="vehicleAdd" action="SingleServlet?operate=Vehicle.add" method="post" onSubmit="return validate(this)" enctype="multipart/form-data">
		<table border="0" cellpadding="0" cellspacing="0">
			<tbody>
				<tr>
					<td height="45px;" width="200px">&nbsp;车牌号：</td>
					<td height="45px;" width="200px"><input type="text" class="input" name="plateNo" id="plateNo" /></td>
					<td height="45px;" width="200px">车辆名称：</td>
					<td height="45px;" width="200px"><input type="text" class="input" name="vname" id="vname" /></td>
					<td height="45px;" width="200px">停放地点：</td>
					<td height="45px;" width="200px"><input type="text" class="input" name="loc" /></td>
				</tr>
				<tr>
					<td height="45px;">车辆型号：</td>
					<td height="45px;"><input type="text" class="input" name="v_version" /></td>
					<td height="45px;">车辆档位：</td>
					<td height="45px;"><select name="gear">
							<option value="1" selected>自动档</option>
							<option value="0">手动档</option>
							<option value="2">手动自动联合档</option>
					</select></td>
					<td height="45px;">&nbsp;车架号：</td>
					<td height="45px;"><input type="text" class="input" name="vframe" /></td>
				</tr>
				<tr>
					<td height="45px;">&nbsp;发动机：</td>
					<td height="45px;"><input type="text" class="input" name="motor" /></td>
					<td height="45px;">&nbsp;变速箱：</td>
					<td height="45px;"><input type="text" class="input" name="gearBox" /></td>
					<td height="45px;">&nbsp;&nbsp;排量：</td>
					<td height="45px;"><input type="text" class="input" name="output" /></td>
				</tr>
				<tr>
					<td height="45px;">&nbsp;主气囊：</td>
					<td height="45px;"><input type="text" class="input" name="mairSac" /></td>
					<td height="45px;">&nbsp;副气囊：</td>
					<td height="45px;"><input type="text" class="input" name="sairSac" /></td>
					<td height="45px;">联系方式：</td>
					<td height="45px;"><input type="text" class="input" name="tel" /></td>
				</tr>
				<tr>
					<td height="45px;">车辆来源：</td>
					<td height="45px;"><input type="text" class="input" name="source" /></td>
					<td height="45px;">初级登记时间：</td>

					<td height="45px;"><input type="text" class="input" name="regTime" onclick="MyCalendar.SetDate(this)" /></td>
					<td height="45px;">价格：</td>
					<td height="45px;"><input type="text" class="input" name="pledge" /></td>
				</tr>
				<tr>
					<td height="45px;">是否过户：</td>
					<td height="45px;"><select name="ftransfer">
							<option value="1" selected>是</option>
							<option value="0">否</option>
					</select></td>
					<td height="45px;">是否拆解：</td>
					<td height="45px;"><select name="fdisass">
							<option value="1" selected>是</option>
							<option value="0">否</option>
					</select></td>
					<td height="45px;">是否有二次事故：</td>
					<td height="45px;"><select name="fagain">
							<option value="1" selected>是</option>
							<option value="0">否</option>
					</select></td>
				</tr>
				<tr>
					<td height="45px;">是否违章：</td>
					<td height="45px;"><select name="fbrule">
							<option value="1" selected>是</option>
							<option value="0">否</option>
					</select></td>
					<td height="45px;">是否抵押：</td>
					<td height="45px;"><select name="fmort" style="width: auto">
							<option value="1" selected>是</option>
							<option value="0">否</option>
					</select></td>
					<td height="45px;">车架是否受损：</td>
					<td height="45px;"><select name="fbroke">
							<option value="1" selected>是</option>
							<option value="0">否</option>
					</select></td>

				</tr>

				<tr>
					<%
						List<Object> list2 = new BaseDao().queryForSingle(SourceImg.class, 0, 0, null, null, null, false);
						if (list2.size() == 0) {
							SourceImg si = new SourceImg();
							si.setSimg_name("不存在，请添加！");
							list2.add(si);
						}
						request.setAttribute("list2", list2);
					%>
					<%-- <td height="45px;">来源(保险):</td>
					<td height="45px;">
					<select name="v_source">
							<c:forEach var="si" varStatus="st" items="${requestScope.list2}">
								<option value="${si.simg_path }">${si.simg_name }</option>
							</c:forEach>
					</select></td> --%>
					
					<td height="45px;" colspan="2">车辆图片上传：<input type="file" name="picture" id="picture" multiple />
					</td>
					<td height="45px;" colspan="2">注意事项:<textarea rows="2" cols="60" placeholder="单机此处填写" name="note"></textarea>
					</td>
					<td height="45px;">&nbsp;</td>
					<td height="45px;">&nbsp;</td>
				</tr>

				

				<tr>
					<td height="45px;">&nbsp;</td>
					<td height="45px;">&nbsp;</td>
					<td height="45px;">&nbsp;</td>
					<td height="45px;">&nbsp;</td>
					<td height="45px;">
						<button style="width: 100px; height: 30px;" class="btn btn-large btn-primary" type="submit">确定</button>
					</td>
					<td height="45px;"><button style="width: 100px; height: 30px;" class="btn btn-large btn-primary" type="reset">取消</button></td>

				</tr>
			</tbody>
		</table>
	</form>
	<br />

	<!-- /.row -->
</body>
</html>
