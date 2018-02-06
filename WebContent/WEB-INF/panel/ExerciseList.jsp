<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Exercise List</title>
</head>
<%@include file="/WEB-INF/fragments/header.jspf" %>
<body>
<br>

<table>
<tr>
<th>Id</th>
<th>Title</th>
<th>Description</th>
<th></th>
</tr>
<c:forEach var="exercise" items="${exercises}">
<tr>
<td>${exercise.id}</td>
<td>${exercise.title}</td>
<td>${exercise.description}</td>
<td>
<a href="ExerciseForm?id=${exercise.id}">Edit</a>
</td>
</tr>
</c:forEach>
</table>
<br><br>
<a href="ExerciseForm?id=0">Add new</a>
<br><br>
</body>
<%@include file="/WEB-INF/fragments/footer.jspf" %>
</html>