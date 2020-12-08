<%--
  Created by IntelliJ IDEA.
  User: uladzimir
  Date: 13.10.20
  Time: 12:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>U ARE REGISTRATE SUCCESSFULLY</title>
</head>
<body>
<p> SUCCESS REGISTRATION))))))</p>

<form name="log out" method="get" action="${pageContext.request.contextPath}/controller" autocomplete="on">
    <input type="hidden"  name="command" value="logout" >
    <input type="submit"  value="logout" >
</form>
</body>
</html>
