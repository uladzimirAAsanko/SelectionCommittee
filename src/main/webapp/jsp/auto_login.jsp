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
<form name="autoLoffing" method="get" action="${pageContext.request.contextPath}/controller" autocomplete="on" id =1>
    <input type="hidden"  name="command" value="AUTOLOGGING" >
</form>
<script>document.getElementById("1").submit()</script>
<b> Wait Please we try to auto login you</b>
</body>
</html>
