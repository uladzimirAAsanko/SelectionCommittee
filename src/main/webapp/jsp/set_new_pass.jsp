<%--
  Created by IntelliJ IDEA.
  User: uladzimir
  Date: 30.11.20
  Time: 11:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>change password</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/loginPage.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous" />
    <script src="https://kit.fontawesome.com/6d90f39943.js"></script>
</head>
<header> <c:import url="${pageContext.request.contextPath}/jsp/fragment/header.jsp"/></header>
<body>
<c:if test="${answer == null}">
<div class="container login-container">
    <div class="col-md-6 login-form-2">
        <h3>Login for Selection Comittee</h3>
        <form action="${pageContext.request.contextPath}/controller" method="post" name="setNewPass">
            <input type="hidden" name="command" value="setNewPass">
            <div class="form-group">
                <input type="password" class="form-control" required placeholder="Your new Password *"  name="password" value="" />
            </div>
            <div class="form-group">
                <input type="submit" class="btnSubmit" value="confirm password" />
            </div>
            <div class="form-group">
                <a class="ForgetPwd" href="${pageContext.request.contextPath}/jsp/change_password.jsp">ForgotPassword?</a>
            </div>
        </form>
    </div>
</div>
</c:if>
<c:if test="${answer != null}">
    <div class="alert alert-primary" role="alert">
    ${answer}
    </div>
</c:if>


<br>
<a href="${pageContext.request.contextPath}/jsp/welcome.jsp">On main page</a>
</body>
</html>
