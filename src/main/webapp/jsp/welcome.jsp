<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${location}"/>
<fmt:setBundle basename="prop.contentpage"/>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous" />
    <script src="https://kit.fontawesome.com/6d90f39943.js"></script>
</head>
<header> <c:import url="${pageContext.request.contextPath}/jsp/fragment/header.jsp"/></header>
<body>
<fmt:message key="welcome.msg"/>


<script src="${pageContext.request.contextPath}/js/buttonBlock.js"></script>
<br>
<br><br>
<br>
<form name="getFaculties" method="post" action="${pageContext.request.contextPath}/controller" autocomplete="on">
    <input type="hidden"  name="command" value="getFaculties" >
    <input type="hidden"  name="currentSite" value="${pageContext.request.requestURL}" >
    <input type="submit"  value="Show faculties" >
</form>
<c:forEach items="${faculty}" var="exam">
    <div class="row">
        <div class="col-md-6">
            <a href="${pageContext.request.contextPath}/controller?command=getFacByName&faculty=${exam.key}">${exam.key}</a>
        </div>
        <div class="col-md-6">
            <p>${exam.value}</p>
        </div>
    </div>
</c:forEach>
<c:import url="${pageContext.request.contextPath}/jsp/fragment/footer.jsp"/>
</body>