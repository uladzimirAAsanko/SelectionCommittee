<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${location}"/>
<fmt:setBundle basename="prop.contentpage"/>
<html>
<head>
    <title>Login</title>
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

<div class="container login-container">
<div class="col-md-6 login-form-2">
    <h3><fmt:message key="login.title"/></h3>
    <form action="${pageContext.request.contextPath}/controller" name="authorization" method="post" >
        <input type="hidden" name="command" value="authorization">
        <div class="form-group">
            <input type="text" class="form-control" required placeholder="<fmt:message key="login.yourLogin"/>" value="" name="login" />
        </div>
        <div class="form-group">
            <input type="password" class="form-control" required placeholder="<fmt:message key="login.yourPassword"/>" name="password" value="" />
        </div>
        <div class="form-group">
            <input type="submit" class="btnSubmit" value="<fmt:message key="login.yourPassword"/>" />
        </div>
        <div class="form-group">
            <a class="ForgetPwd" href="${pageContext.request.contextPath}/jsp/change_password.jsp"><fmt:message key="login.fwd"/></a>
        </div>
    </form>
</div>
</div>
<c:import url="${pageContext.request.contextPath}/jsp/fragment/footer.jsp"/>
</body>
</html>
