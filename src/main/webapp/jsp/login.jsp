<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/controller" name="loginForm" method="post" >
    <input type="hidden" name="command" value="authorization">
    Login:<br>
    <input type="text" name="login" value="">
    <br>
    Password:<br>
    <input type="password" name="password" value="">
    <br>
    ${errorLoginPassMessage}
    <br>
    <input type="submit" value="Log in">
</form>
</body>
</html>
