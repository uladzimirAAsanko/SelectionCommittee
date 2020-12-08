<%--
  Created by IntelliJ IDEA.
  User: uladzimir
  Date: 10.11.20
  Time: 05:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${location}"/>
<fmt:setBundle basename="prop.contentpage"/>
<html>
<head>
    <title>Home</title>
</head>
<body>
<p>User Login = ${login}</p><br><br>
<p>first name = ${firstName}</p><br><br>
<p>last name = ${lastName}</p><br><br>
<p>last name = ${lastName}</p><br><br>
<p>email = ${email}</p><br><br>
<br><br>
<p>Faculty Name = ${facultyName}</p><br><br>
<p>Faculty Site = ${facultySite}</p><br><br>
<br><br>
<table>
    <c:forEach items="${allExams}" var="entry">
        exam = ${entry.key}, value = ${entry.value}<br>
    </c:forEach>
</table>
<br><br>
<form name="addExamToFaculty" method="post" action="${pageContext.request.contextPath}/controller" autocomplete="on">
    <input type="hidden"  name="command" value="addExamToFaculty" >
    typeOfExam
    <br>
    <input type="text" name="typeOfExam" value="">
    <br>
    minimal score
    <br>
    <input type="text" name="minimal" value="">
    <br>
    <input type="hidden"  name="command" value="addExamToFaculty" >
    <input type="submit"  value="add exam" >
</form><br><br>
<form name="deleteExamFromFaculty" method="post" action="${pageContext.request.contextPath}/controller" autocomplete="on">
    <input type="hidden"  name="command" value="deleteExamFromFaculty" >
    typeOfExam
    <br>
    <input type="text" name="typeOfExam" value="">
    <br>
    <input type="hidden"  name="command" value="delete exam" >
    <input type="submit"  value="delete exam" >
</form>
<br><br>
<form name="addStatement" method="post" action="${pageContext.request.contextPath}/controller" autocomplete="on">
    <input type="hidden"  name="command" value="addStatement" >
    max Students
    <br>
    <input type="text" name="maxStudents" value="">
    expiration Date
    <br>
    <input type="text" name="expiredDate" value="">
    <br>
    <input type="hidden"  name="command" value="add Statement" >
    <input type="submit"  value="add Statement" >
</form><br><br>
<form name="deleteStatement" method="post" action="${pageContext.request.contextPath}/controller" autocomplete="on">
    <input type="hidden"  name="command" value="deleteStatement" >
    <input type="hidden"  name="command" value="delete Statement" >
    <input type="submit"  value="delete Statement" >
</form><br><br>
answer = ${answer}
<br><br>
<form name="log out" method="get" action="${pageContext.request.contextPath}/controller" autocomplete="on">
    <input type="hidden"  name="command" value="logout" >
    <input type="submit"  value="logout" >
</form>
</body>
</html>
