<%--
  Created by IntelliJ IDEA.
  User: uladzimir
  Date: 7.12.20
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${location}"/>
<fmt:setBundle basename="prop.contentpage"/>
<html>
<head>
    <title>Exam manager</title>
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
<script src="${pageContext.request.contextPath}/js/buttonBlock.js"></script>
<form name="autoLogin" method="get" action="${pageContext.request.contextPath}/controller" autocomplete="on" id=1>
    <input type="hidden" name="command" value="getAllUsersExam">
</form>
<c:if test="${exams == null}">
    <script>document.getElementById("1").submit()</script>
</c:if>
<section class="content">
    <div class="container-fluid">
        <div class="card mt-3">
            <div class="card-header">
                <h4>${userLogin}'s exams</h4>
            </div>
            <div class="card-body">
                <div class="container emp-profile">
                    <div>
                        <div class="row">
                            <div class="col-md-8">
                                <div class="tab-content profile-tab" id="myTabContent">
                                    <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                                        <c:forEach items="${exams}" var="exam">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <label>${exam.key}</label>
                                                </div>
                                                <div class="col-md-6">
                                                    <p>${exam.value}</p>
                                                </div>
                                            </div>
                                        </c:forEach>
                                        <form name="addExam" method="post" action="${pageContext.request.contextPath}/controller"
                                              autocomplete="on">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <select class="form-control" name="typeOfExam">
                                                            <option disabled>Выберите героя</option>
                                                            <option selected value="0"><fmt:message key="exam.Rus"/></option>
                                                            <option value="1"><fmt:message key="exam.Bel"/></option>
                                                            <option value="2"><fmt:message key="exam.Math"/></option>
                                                            <option value="3"><fmt:message key="exam.Physics"/></option>
                                                            <option value="4"><fmt:message key="exam.Chemistry"/></option>
                                                            <option value="5"><fmt:message key="exam.Foreign"/></option>
                                                            <option value="6"><fmt:message key="exam.HoW"/></option>
                                                            <option value="7"><fmt:message key="exam.Biology"/></option>
                                                            <option value="8"><fmt:message key="exam.Geography"/></option>
                                                            <option value="9"><fmt:message key="exam.SS"/></option>
                                                            <option value="10"><fmt:message key="exam.HoB"/></option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <input type="number" class="form-control" required name="<fmt:message key="exam.result"/>" value="">
                                                </div>
                                                <div class="col-md-3">
                                                    <input class="profile-edit-btn btn btn-success" type="submit" value="<fmt:message key="exam.addExam"/>">
                                                </div>
                                                <input type="hidden" name="command" value="addExam">
                                            </div>
                                        </form>
                                        <form name="deleteExamFromEnrollee" method="post"
                                              action="${pageContext.request.contextPath}/controller" autocomplete="on">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <select class="form-control" name="typeOfExam">
                                                            <option disabled>Выберите героя</option>
                                                            <option selected value="0"><fmt:message key="exam.Rus"/></option>
                                                            <option value="1"><fmt:message key="exam.Bel"/></option>
                                                            <option value="2"><fmt:message key="exam.Math"/></option>
                                                            <option value="3"><fmt:message key="exam.Physics"/></option>
                                                            <option value="4"><fmt:message key="exam.Chemistry"/></option>
                                                            <option value="5"><fmt:message key="exam.Foreign"/></option>
                                                            <option value="6"><fmt:message key="exam.HoW"/></option>
                                                            <option value="7"><fmt:message key="exam.Biology"/></option>
                                                            <option value="8"><fmt:message key="exam.Geography"/></option>
                                                            <option value="9"><fmt:message key="exam.SS"/></option>
                                                            <option value="10"><fmt:message key="exam.HoB"/></option>
                                                        </select>
                                                    </div>

                                                </div>
                                                <div class="col-md-6">
                                                    <input class="profile-edit-btn btn btn-success" type="submit" value="<fmt:message key="exam.removeExam"/>">
                                                </div>
                                                <input type="hidden" name="command" value="deleteExamFromEnrollee">
                                            </div>
                                        </form>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <a class="profile-edit-btn btn btn-primary btn-block"
                                                   href="${pageContext.request.contextPath}/jsp/success_login.jsp"><fmt:message key="exam.toProfile"/></a>
                                            </div>

                                            <div class="col-md-12 mt-1">
                                                <form name="log out" method="get" action="${pageContext.request.contextPath}/controller"
                                                      autocomplete="on">
                                                    <input type="hidden" name="command" value="logout">
                                                    <input type="submit" value="<fmt:message key="exam.logout"/>" class="btn btn-secondary btn-block btn-sm">
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
