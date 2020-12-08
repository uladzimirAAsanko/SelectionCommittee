<%--
  Created by IntelliJ IDEA.
  User: uladzimir
  Date: 8.12.20
  Time: 02:48
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Faculty page</title>
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
<section class="content">
    <div class="container-fluid">
        <div class="card mt-3">
            <div class="card-header">
                <h4>Faculty page</h4>
            </div>
            <div class="card-body">
                <div class="container emp-profile">
                    <div>
                        <c:if test="${userRole == 'ADMINISTRATOR'}">
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
                                        <form name="getAllExamsOfFaculty" method="post"
                                              action="${pageContext.request.contextPath}/controller" autocomplete="on">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <input class="profile-edit-btn btn btn-success" type="submit" value="get all exams">
                                                </div>
                                                <input type="hidden" name="command" value="getAllExamsFromFaculty">
                                            </div>
                                        </form>
                                        <form name="ADDEXAMTOFACULTY" method="post" action="${pageContext.request.contextPath}/controller"
                                              autocomplete="on">
                                            <div class="row">

                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <select class="form-control" name="typeOfExam">
                                                            <option disabled>Выберите героя</option>
                                                            <option selected value="0">Russian Language</option>
                                                            <option value="1">Belorussian Language</option>
                                                            <option value="2">Math</option>
                                                            <option value="3">Physics</option>
                                                            <option value="4">Chemistry</option>
                                                            <option value="5">Foreign Language</option>
                                                            <option value="6">History of world</option>
                                                            <option value="7">Biology</option>
                                                            <option value="8">Geography</option>
                                                            <option value="9">Social science</option>
                                                            <option value="10">History of Belarus</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <input type="number" class="form-control" required name="minimal" value="">
                                                </div>
                                                <div class="col-md-3">
                                                    <input class="profile-edit-btn btn btn-success" type="submit" value="add exam">
                                                </div>
                                                <input type="hidden" name="command" value="ADDEXAMTOFACULTY">
                                            </div>
                                        </form>
                                        <form name="deleteExamFromEnrollee" method="post"
                                              action="${pageContext.request.contextPath}/controller" autocomplete="on">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <select class="form-control" name="typeOfExam">
                                                            <option disabled>Выберите героя</option>
                                                            <option selected value="0">Russian Language</option>
                                                            <option value="1">Belorussian Language</option>
                                                            <option value="2">Math</option>
                                                            <option value="3">Physics</option>
                                                            <option value="4">Chemistry</option>
                                                            <option value="5">Foreign Language</option>
                                                            <option value="6">History of world</option>
                                                            <option value="7">Biology</option>
                                                            <option value="8">Geography</option>
                                                            <option value="9">Social science</option>
                                                            <option value="10">History of Belarus</option>
                                                        </select>
                                                    </div>

                                                </div>
                                                <div class="col-md-6">
                                                    <input class="profile-edit-btn btn btn-success" type="submit" value="remove exam">
                                                </div>
                                                <input type="hidden" name="command" value="deleteExamFromFaculty">
                                            </div>
                                        </form>
                                        <form name="addStatement" method="post"
                                              action="${pageContext.request.contextPath}/controller" autocomplete="on">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        Maximum students:
                                                        <input type="number" class="form-control" required name="maxStudents" value="">
                                                    </div>

                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label >Expired date:</label>
                                                        <input type="date" class="form-control" required name="expiredDate" value="2020-12-8"
                                                               min="2020-12-01" max="2021-12-31">
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <input class="profile-edit-btn btn btn-success" type="submit" value="add statement">
                                                </div>
                                                <input type="hidden" name="command" value="addStatement">
                                            </div>
                                        </form>
                                        <form name="deleteStatement" method="post"
                                              action="${pageContext.request.contextPath}/controller" autocomplete="on">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <input class="profile-edit-btn btn btn-success" type="submit" value="delete statement">
                                                </div>
                                                <input type="hidden" name="command" value="deleteStatement">
                                            </div>
                                        </form>
                                    </div>
                                </div>
                                <c:if test="${answer != null}">
                                    <div class="alert alert-primary" role="alert">
                                            ${answer}
                                    </div>
                                </c:if>
                            </div>
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
