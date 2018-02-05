<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Form</title>
</head>
<%@include file="/WEB-INF/fragments/header.jspf" %>
<body>
<br>

	<form action="UserAdmin" method="post">
		User name:<br>
		<input type="text" name="userName"><br>
		User email:<br>
		<input type="text" name="userEmail"><br>
		Group id:<br>
		<input type="text" name="groupId"><br>
		User password:<br>
		<input type="text" name="password"><br>
		<input type="submit" value="Submit">
	</form>

	<br><br>
</body>
<%@include file="/WEB-INF/fragments/footer.jspf" %>
</html>