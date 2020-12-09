<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Registration</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous" />
    <script src="https://kit.fontawesome.com/6d90f39943.js"></script>
</head>
<header> <c:import url="${pageContext.request.contextPath}/jsp/fragment/header.jsp"/></header>
<body>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
<script src="${pageContext.request.contextPath}/js/buttonBlock.js"></script>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <header class="card-header">
                    <a href="${pageContext.request.contextPath}/jsp/auto_login.jsp" class="float-right btn btn-outline-primary mt-1">Log in</a>
                    <h4 class="card-title mt-2">Sign up</h4>
                </header>
                <article class="card-body">
                    <form action="${pageContext.request.contextPath}/controller" method="post" name="registration">
                        <input type="hidden" name="command" value="registration">
                        <div class="form-row">
                            <div class="col form-group">
                                <label>First name </label>
                                <input type="text" class="form-control" name="firstName" required placeholder="" value=${firstName}>
                            </div> <!-- form-group end.// -->
                            <div class="col form-group">
                                <label>Last name</label>
                                <input type="text" class="form-control" name="lastName" required placeholder=" " value=${lastName}>
                            </div> <!-- form-group end.// -->
                            <div class="col form-group">
                                <label>Fathers name</label>
                                <input type="text" class="form-control" name="fathersName" required placeholder=" " value=${fathersName}>
                            </div>
                        </div> <!-- form-row end.// -->
                        <div class="form-group">
                            <label>Email address</label>
                            <input type="email" class="form-control" required placeholder="" name="email" value=${email}>
                            <small class="form-text text-muted">We'll never share your email with anyone else.</small>
                        </div> <!-- form-group end.// -->
                        <div class="form-group">
                            <label>Login</label>
                            <input type="text" class="form-control" required placeholder="" name="login" value=${login}>
                            <small class="form-text text-muted">We'll never share your email with anyone else.</small>
                        </div> <!-- form-group end.// -->
                        <div class="form-group">
                            <label class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="role" value="1">
                                <span class="form-check-label"> Администратор </span>
                            </label>
                            <label class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="role" value="0" checked>
                                <span class="form-check-label"> Абитуриент</span>
                            </label>
                            <label class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="role" value="3">
                                <span class="form-check-label"> Гость </span>
                            </label>
                        </div> <!-- form-group end.// -->
                        <!-- form-row.// -->
                        <div class="form-group">
                            <label>Create password</label>
                            <input class="form-control" type="password" required name="password">
                        </div> <!-- form-group end.// -->
                        <div class="form-group">
                            <label>Create password</label>
                            <input class="form-control" type="password" required name="passwordConfirm">
                        </div> <!-- form-group end.// -->
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-block"> Register  </button>
                        </div> <!-- form-group// -->
                        <c:if test="${answer != null}">
                            <div class="alert alert-primary" role="alert">
                                    ${answer}
                            </div>
                        </c:if>
                        <small class="text-muted">By clicking the 'Register' button, you confirm that you accept our <br> Terms of use and Privacy Policy.</small>
                    </form>
                </article> <!-- card-body end .// -->
                <div class="border-top card-body text-center">Have an account? <a href="${pageContext.request.contextPath}/jsp/auto_login.jsp">Log In</a></div>
            </div> <!-- card.// -->
        </div> <!-- col.//-->

    </div> <!-- row.//-->


</div>
</body>