<%--
  Created by IntelliJ IDEA.
  User: uladzimir
  Date: 30.10.20
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Auto logging</title>
</head>
<body>
<form name="getFaculties" method="get" action="${pageContext.request.contextPath}/controller" autocomplete="on">
    <input type="hidden"  name="command" value="AUTOLOGGING" >
    <input type="submit"  value="Show faculties" >
</form>
<b> Wait Please we try to auto login you</b>
</body>
</html>
