<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${location}"/>
<fmt:setBundle basename="prop.contentpage"/>
<html>
<head>
    <title>Success login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous" />
    <script src="https://kit.fontawesome.com/6d90f39943.js"></script>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" ></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<header> <c:import url="${pageContext.request.contextPath}/jsp/fragment/header.jsp"/></header>
<body>
<script src="${pageContext.request.contextPath}/js/buttonBlock.js"></script>
<form name="autoLogin" method="get" action="${pageContext.request.contextPath}/controller" autocomplete="on" id =1>
    <input type="hidden"  name="command" value="AUTOLOGGING" >
</form>
<c:if test="${email == null || userLogin==null}">
    <script>document.getElementById("1").submit()</script>
</c:if>
    <div class="container emp-profile">
        <div>
            <div class="row">
                <div class="col-md-4">
                    <div class="profile-img">
                        <img src=${avatar} alt="avatar" width="125" height="125"/>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="profile-head">
                        <h5> <fmt:message key="profile.userLogin"/> ${userLogin}  </h5>
                        <h6> <fmt:message key="profile.userRole"/> ${userRole} </h6>
                        <ul class="nav nav-tabs" id="myTab" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true"><fmt:message key="profile.userAbout"/> </a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="col-md-2">
                    <a class="profile-edit-btn" href="${pageContext.request.contextPath}/jsp/update_user.jsp"><fmt:message key="profile.updInfo"/> </a>
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <div class="tab-content profile-tab" id="myTabContent">
                        <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                            <div class="row">
                                <div class="col-md-6">
                                    <label><fmt:message key="profile.userLogin"/> </label>
                                </div>
                                <div class="col-md-6">
                                    <p>${userLogin}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label><fmt:message key="profile.userName"/> </label>
                                </div>
                                <div class="col-md-6">
                                    <p>${userName}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label><fmt:message key="profile.userLastName"/> </label>
                                </div>
                                <div class="col-md-6">
                                    <p>${userLastName}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label><fmt:message key="profile.userFathersLastName"/> </label>
                                </div>
                                <div class="col-md-6">
                                    <p>${userFathersName}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label><fmt:message key="profile.userEmail"/> </label>
                                </div>
                                <div class="col-md-6">
                                    <p>${email}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label><fmt:message key="profile.userRole"/> </label>
                                </div>
                                <div class="col-md-6">
                                    <p>${userRole}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <c:if test="${userRole == 'ENROLLEE'}">
                                        <a class="profile-edit-btn" href="${pageContext.request.contextPath}/jsp/examManagment.jsp"><fmt:message key="profile.userExamInfo"/> </a>
                                    </c:if>
                                    <c:if test="${userRole == 'MODERATOR'}">
                                        <a class="profile-edit-btn" href="${pageContext.request.contextPath}/jsp/moderatorManagment.jsp"><fmt:message key="profile.userModeratorTools"/> </a>
                                    </c:if>
                                </div>
                                <div class="col-md-6">
                                    <form name="log out" method="get" action="${pageContext.request.contextPath}/controller" autocomplete="on">
                                        <input type="hidden"  name="command" value="logout" >
                                        <input type="submit"  value="logout" >
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<c:import url="${pageContext.request.contextPath}/jsp/fragment/footer.jsp"/>
</body>