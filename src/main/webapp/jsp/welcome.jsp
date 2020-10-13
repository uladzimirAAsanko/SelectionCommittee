<%@ page import="java.util.StringTokenizer" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
Welcome to selection Committee.

<a href="${pageContext.request.contextPath}/jsp/login.jsp">Log in</a>
<a href="${pageContext.request.contextPath}/jsp/registration.jsp">Sign in</a>
<br>
<br><br>
<br>
<form name="getFaculties" method="post" action="${pageContext.request.contextPath}/controller" autocomplete="on">
    <input type="hidden"  name="command" value="getFaculties" >
    <input type="submit"  value="Show faculties" >
</form>
<table border="1px">
    ${faculty}
</table>
</body>