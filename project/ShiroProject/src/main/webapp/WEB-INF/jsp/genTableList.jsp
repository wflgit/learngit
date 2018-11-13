<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>GenTableList</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<table width="100%" border=1>
		<tr>
			<td>名称</td>
			<td>描述</td>
			<td>实体类名称</td>
			<td>商品描述</td>
			<td>关系父表</td>
		</tr>
		<c:forEach items="${genTableList }" var="genTable" varStatus="s">
			<tr>
				<td>${genTable.name }
				</td>
				<td>${genTable.comments }
				</td>
				<td>${genTable.className }
				</td>
				<td>${genTable.parentTable }</td>
			</tr>
		</c:forEach>

	</table>
</body>
</html>
