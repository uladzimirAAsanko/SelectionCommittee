<%--
  Created by IntelliJ IDEA.
  User: uladzimir
  Date: 10.11.20
  Time: 02:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Success login</title>
</head>
<body>
    <p>User Login = ${login}</p><br><br>
    <p>first name = ${firstName}</p><br><br>
    <p>last name = ${lastName}</p><br><br>
    <p>last name = ${lastName}</p><br><br>
    <p>email = ${email}</p><br><br>
    <br><br>

    <form name="add admin code" method="post" action="${pageContext.request.contextPath}/controller" autocomplete="on">
        Admin code
        <br>
        <input type="text" name="adminCode" value="">
        <br><br>
        facultyId
        <br>
        <input type="text" name="facultyID" value="">
        <br>
        <br>
        <input type="hidden"  name="command" value="addAdminCode" >
        <input type="submit"  value="add Code" >
    </form><br><br><br><br>
    <p>${answer}</p>
</body>
</html>
