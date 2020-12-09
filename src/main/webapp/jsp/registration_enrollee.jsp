<%--
  Created by IntelliJ IDEA.
  User: uladzimir
  Date: 3.11.20
  Time: 06:20
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/loginPage.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous" />
    <script src="https://kit.fontawesome.com/6d90f39943.js"></script>
    <title>change password</title>
</head>
<header> <c:import url="${pageContext.request.contextPath}/jsp/fragment/header.jsp"/></header>
<body>
<script src="${pageContext.request.contextPath}/js/buttonBlock.js"></script>
    <div class="container login-container">
        <div class="col-md-6 login-form-2">
            <h3>Set new password</h3>
            <form action="${pageContext.request.contextPath}/controller" method="post" name="registration">
                <input type="hidden" name="command" value="registrationenrollee">
                <div class="form-group">
                    <input type="number" class="form-control" required placeholder="Certificate"  name="certificate" value="" />
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Additional info"  name="additional" value="" />
                </div>
                <div class="form-group">
                    <input type="submit" class="btnSubmit" value="Confirm information" />
                </div>
                <c:if test="${answer != null}">
                    <div class="alert alert-primary" role="alert">
                            ${answer}
                    </div>
                </c:if>
            </form>
        </div>
    </div>
</body>
</html>
