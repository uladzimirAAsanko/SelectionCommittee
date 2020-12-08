<%--
  Created by IntelliJ IDEA.
  User: uladzimir
  Date: 8.12.20
  Time: 01:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Moderator</title>
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
<section class="content">
    <div class="container-fluid">
        <div class="card mt-3">
            <div class="card-header">
                <h4>Moderator tools</h4>
            </div>
            <div class="card-body">
                <div class="container emp-profile">
                    <div>
                        <div class="row">
                            <div class="col-md-8">
                                <div class="tab-content profile-tab" id="myTabContent">
                                    <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                                        <form action="${pageContext.request.contextPath}/controller" method="post" name="add admin code">
                                            <input type="hidden" name="command" value="addAdminCode">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    Admin Code:
                                                    <input type="text" name="adminCode" value="">
                                                </div>
                                                <div class="col-md-6"> Faculty ID:
                                                    <input type="text" name="facultyID" value="">
                                                </div>
                                                <div class="col-md-6">
                                                    <input class="profile-edit-btn btn btn-success" type="submit" value="add code">
                                                </div>
                                            </div>
                                        </form>
                                        <form action="${pageContext.request.contextPath}/controller" method="post" name="add faculty">
                                            <input type="hidden" name="command" value="addFaculty">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    Faculty name:
                                                    <input type="text" name="facultyName" value="">
                                                </div>
                                                <div class="col-md-6"> Faculty Site:
                                                    <input type="text" name="facultySite" value="">
                                                </div>
                                                <div class="col-md-6">
                                                    <input class="profile-edit-btn btn btn-success" type="submit" value="add faculty">
                                                </div>
                                            </div>
                                        </form>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <a class="profile-edit-btn btn btn-primary btn-block"
                                                   href="${pageContext.request.contextPath}/jsp/success_login.jsp">To
                                                    profile</a>
                                            </div>

                                            <div class="col-md-12 mt-1">
                                                <form name="log out" method="get" action="${pageContext.request.contextPath}/controller"
                                                      autocomplete="on">
                                                    <input type="hidden" name="command" value="logout">
                                                    <input type="submit" value="logout" class="btn btn-secondary btn-block btn-sm">
                                                </form>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <c:if test="${answer != null}">
                        <div class="alert alert-primary" role="alert">
                                ${answer}
                        </div>
                    </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>


</body>
</html>
