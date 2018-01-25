<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>W3</title>
</head>
<%@include file="/WEB-INF/fragments/header.jspf" %>
<body>
<br>
<br>

<table>
<tr>
<th>Exercise title</th>
<th>User name</th>
<th>Solution date</th>
<th></th>
</tr>
<c:forEach var="solution" items="${solutionsLimitedList}">
<tr>
<td>${solution.exercise.title}</td>
<td>${solution.user.username}</td>
<td>${solution.updated}</td>
<td>
<a href="SolutionDetails?id=${solution.id}">Show details</a>
</td>
</tr>
</c:forEach>
</table>

<br>
</body>
<%@include file="/WEB-INF/fragments/footer.jspf" %>
</html>