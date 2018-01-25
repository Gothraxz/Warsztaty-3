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
<b>User details:</b>
<br><br>
User name: ${user.username}<br>
E-mail: ${user.email}<br>
Group: ${user.group.id}<br>
<br><br>
<b>Exercises: </b>
<br><br>

<c:if test="${empty userSolutions}">
User is not assigned to any exercise yet.
</c:if>

<c:if test="${not empty userSolutions}">

<table>
<tr>
<th>Exercise title</th>
<th>Solution date</th>
<th></th>
</tr>
<c:forEach var="solution" items="${userSolutions}">
<tr>
<td>${solution.exercise.title}</td>
<td>${solution.updated}</td>
<td>
<a href="UserSolutionDetails?id=${solution.id}">Show details</a>
</td>
</tr>
</c:forEach>
</table>

</c:if>


<br><b>
</body>
<%@include file="/WEB-INF/fragments/footer.jspf" %>
</html>