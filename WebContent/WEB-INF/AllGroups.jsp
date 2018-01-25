<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<%@include file="/WEB-INF/fragments/header.jspf" %>
<body>
<br>

<table>
<tr>
<th>Id</th>
<th>Name</th>
<th></th>
</tr>
<c:forEach var="group" items="${allGroups}">
<tr>
<td>${group.id}</td>
<td>${group.name}</td>
<td>
<a href="GroupUsers?id=${group.id}">Show users</a>
</td>
</tr>
</c:forEach>
</table>

<br><br>
</body>
<%@include file="/WEB-INF/fragments/footer.jspf" %>
</html>