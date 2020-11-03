<%--
  Created by IntelliJ IDEA.
  User: uladzimir
  Date: 3.11.20
  Time: 06:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/controller" method="post" name="registration">
    <input type="hidden" name="command" value="registrationenrollee">
    <br>
    Certificate
    <br>
    <input type="text" name="certificate" value="">
    <br>
    Additional info
    <br>
    <input type="text" name="additional" value="">
    <input type="submit" value="Registration">

    <br>
</form>
</body>
</html>
