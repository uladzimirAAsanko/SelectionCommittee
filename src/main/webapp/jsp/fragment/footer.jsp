<%--
  Created by IntelliJ IDEA.
  User: uladzimir
  Date: 8.12.20
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${location}"/>
<fmt:setBundle basename="prop.contentpage"/>
<footer class="footer fixed-bottom border">
    <div class="container p-1">
        <div class="row">
            <div class="col-6 text-muted">
                <span class="align-middle"><fmt:message key="footer.info"/></span>
            </div>
            <div class="col-6 text-right">
                <form name="switchLocation" method="post" action="${pageContext.request.contextPath}/controller" autocomplete="on">
                    <input type="hidden"  name="command" value="switchLocation" >
                    <input type="hidden"  name="currentSite" value="${pageContext.request.requestURL}">
                    <input class="btn btn-sm btn-secondary"  type="submit" value="${location}">
                </form>
            </div>
        </div>
    </div>
</footer>

<!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////// -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
