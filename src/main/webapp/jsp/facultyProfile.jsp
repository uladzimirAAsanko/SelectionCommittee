<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${location}"/>
<fmt:setBundle basename="prop.contentpage"/>
<html>
<head>
    <title>Title</title>
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
                    <h5> <fmt:message key="fp.name"/> ${name}  </h5>
                    <ul class="nav nav-tabs" id="myTab" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true"><fmt:message key="profile.userAbout"/> </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <c:if test="${statement != null}">
            <form name="getFaculties" method="post"
                  action="${pageContext.request.contextPath}/controller" autocomplete="on">
                <div class="row">
                    <div class="col-md-6">
                        <input class="profile-edit-btn btn btn-success" type="submit" value="signToFaculty">
                    </div>
                    <input type="hidden" name="command" value="signUserToStatement">
                    <input type="hidden"  name="nameOfFaculty" value="${name}" >
                </div>
            </form>
        </c:if>
        <div class="row">
            <div class="col-md-8">
                <div class="tab-content profile-tab" id="myTabContent">
                    <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                        <div class="row">
                            <div class="col-md-6">
                                <label><fmt:message key="fp.name"/> </label>
                            </div>
                            <div class="col-md-6">
                                <p>${name}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label><fmt:message key="fp.site"/> </label>
                            </div>
                            <div class="col-md-6">
                                <p>${site}</p>
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
</html>
