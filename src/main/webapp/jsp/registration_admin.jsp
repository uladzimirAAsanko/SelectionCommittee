<%--
  Created by IntelliJ IDEA.
  User: uladzimir
  Date: 10.11.20
  Time: 04:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/controller" method="post" name="registration">
    <input type="hidden" name="command" value="registration_admin">
    <br>
    Admin Code
    <br>
    <input type="text" name="code" value="">
    <br>
    <br>
    <input type="submit" value="Registration">

    <br>
</form>
</body>
</html>
