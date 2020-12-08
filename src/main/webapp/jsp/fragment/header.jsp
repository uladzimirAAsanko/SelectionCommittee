<%--
  Created by IntelliJ IDEA.
  User: uladzimir
  Date: 4.12.20
  Time: 19:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${location}"/>
<fmt:setBundle basename="prop.contentpage"/>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/jsp/welcome.jsp"><fmt:message key="header.Name"/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
            aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <a class="nav-item nav-link active" href="${pageContext.request.contextPath}/jsp/facultyPage.jsp"><fmt:message key="header.Fac"/><span class="sr-only">(current)</span></a>
        </div>
        <div class="navbar-nav ml-auto user-cabinet text-white">
            <c:if test="${userLogin == null}">
                <div class="navbar-nav">
                    <a class="nav-item nav-link active" href="${pageContext.request.contextPath}/jsp/auto_login.jsp"><fmt:message key="header.LogIn"/><span class="sr-only">(current)</span></a>
                    <a class="nav-item nav-link"href="${pageContext.request.contextPath}/jsp/registration.jsp"><fmt:message key="header.SingIn"/></a>
                </div>
            </c:if>
            <c:if test="${userLogin != null}">
                <a class="nav-item nav-link active" href="${pageContext.request.contextPath}/jsp/success_login.jsp"><span class="mr-1">${userLogin}</span></a>
                <i class="fas fa-user fa-lg"></i>
            </c:if>
        </div>
    </div>
</nav>