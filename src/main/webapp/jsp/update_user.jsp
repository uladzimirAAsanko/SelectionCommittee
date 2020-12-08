<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${location}"/>
<fmt:setBundle basename="prop.contentpage"/>
<html>

<head>
    <title>Update User</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous" />
    <script src="https://kit.fontawesome.com/6d90f39943.js"></script>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<header>
    <c:import url="${pageContext.request.contextPath}/jsp/fragment/header.jsp" />
</header>

<body>
<section class="content">
    <div class="container-fluid">
        <div class="card mt-3">
            <div class="card-header">
                <h4>${userLogin} <fmt:message key="update.info"/></h4>
            </div>
            <div class="card-body">
                <div class="container emp-profile">
                    <div>
                        <div class="row">
                            <div class="tab-content profile-tab" id="myTabContent">
                                <div class="col-md-8">
                                    <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                                        <div class="card p-3">
                                            <form class="mb-0" name="updateInfo" method="post"
                                                  action="${pageContext.request.contextPath}/controller" autocomplete="on">
                                                <input type="hidden" name="command" value="updateInfo">
                                                <div class="row">
                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <label><fmt:message key="update.firstName"/></label>
                                                            <input class="form-control" type="text" name="firstName" value=${userName}>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <label><fmt:message key="update.lastName"/></label>
                                                            <input class="form-control" type="text" name="lastName" value=${userLastName}>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <label><fmt:message key="update.fathersName"/></label>
                                                            <input class="form-control" type="text" name="fathersName" value=${userFathersName}>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-12">
                                                        <input class="profile-edit-btn btn btn-success btn-block" type="submit"
                                                               value="<fmt:message key="update.updateInfo"/>">
                                                    </div>
                                                    <input type="hidden" name="command" value="addExam">
                                                </div>
                                            </form>
                                        </div>
                                        <div class="card p-3 mt-2">
                                            <form class="mb-0" name="updatePassword" method="post" action="${pageContext.request.contextPath}/controller"
                                                  autocomplete="on">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="row">
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <label><fmt:message key="update.oldPassword"/></label>
                                                                    <input class="form-control" type="password" required name="oldPass" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <div class="form-group">
                                                            <label><fmt:message key="update.newPassword"/></label>
                                                            <input class="form-control" type="password" required name="newPass" value="">
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <div class="form-group">
                                                            <label><fmt:message key="update.passwordConfirm"/></label>
                                                            <input class="form-control" type="password" required name="passwordConfirm" value="">
                                                        </div>
                                                    </div>
                                                    <div class="col-md-12">
                                                        <input class="profile-edit-btn btn btn-success btn-block" type="submit"
                                                               value="<fmt:message key="update.updatePassword"/>">
                                                    </div>
                                                    <input type="hidden" name="command" value="updatePassword">
                                                </div>
                                            </form>
                                        </div>
                                        <div class="card p-3 mt-2 mb-2">
                                            <form class="mb-0" method="post" action="${pageContext.request.contextPath}/uploadFile" autocomplete="on"
                                                  enctype="multipart/form-data">
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="form-group">
                                                            <label><fmt:message key="update.selectPhoto"/></label>
                                                            <input type="file" name="file" />
                                                        </div>
                                                    </div>
                                                    <div class="col-md-12">
                                                        <input class="profile-edit-btn btn btn-success btn-block" type="submit" value="<fmt:message key="update.uploadPhoto"/>">
                                                    </div>
                                                </div>

                                            </form>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <a class="profile-edit-btn btn btn-primary btn-block"
                                                   href="${pageContext.request.contextPath}/jsp/success_login.jsp"><fmt:message key="exam.toProfile"/></a>
                                            </div>

                                            <div class="col-md-12 mt-1">
                                                <form class="mb-0" name="log out" method="get" action="${pageContext.request.contextPath}/controller"
                                                      autocomplete="on">
                                                    <input type="hidden" name="command" value="<fmt:message key="exam.logout"/>">
                                                    <input type="submit" value="logout" class="btn btn-secondary btn-block btn-sm">
                                                </form>
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
    </div>
</section>
<c:import url="${pageContext.request.contextPath}/jsp/fragment/footer.jsp"/>
</body>

</html>