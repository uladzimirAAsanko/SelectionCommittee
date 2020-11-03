<%--
  Created by IntelliJ IDEA.
  User: uladzimir
  Date: 16.10.20
  Time: 01:39
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
<br>
result
<br>
<input type="text" name="result" value="">
<br>
typeOfExam
<br>
<input type="text" name="typeOfExam" value="">
<br><br>
<form name="addExam" method="post" action="${pageContext.request.contextPath}/controller" autocomplete="on">
    <input type="hidden"  name="command" value="addExam" >
    <input type="submit"  value="add exam" >
</form><br><br><br><br>
<p>${answer}</p>
</body>
</html>
