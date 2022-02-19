
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="xlink" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/locale.jsp"/>
<header class="p-3 bg-dark text-white">
    <div class="container">
        <div class="d-flex flex-wrap align-items-start justify-content-center justify-content-lg-start">
            <a href="${pageContext.request.contextPath}/"
               class="d-flex align-items-start mb-2 mb-lg-0 text-white text-decoration-none">
                <img src="${pageContext.request.contextPath}/resources/images/icon.png"
                     class="bi me-2" width="50" height="50" role="img" aria-label="Bootstrap"
                     alt="Library">
            </a>
            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <fmt:message key="button.home" var="homeBut"/>
                <li><a href="${pageContext.request.contextPath}/page/admin/admin.jsp"
                       class="nav-link px-2 text-white">${homeBut}</a></li>
                <fmt:message key="button.allUser" var="allUser"/>
                <li><a href="${pageContext.request.contextPath}/controller?command=showAllUsers&limit=10&offset=0"
                       class="nav-link px-2 text-white">${allUser}</a></li>
                <fmt:message key="button.newEmployee" var="newEm"/>
                <li><a href="${pageContext.request.contextPath}/page/admin/createEmployee.jsp"
                       class="nav-link px-2 text-white">${newEm}</a></li>
                <fmt:message key="button.block" var="block"/>
                <li><a href="${pageContext.request.contextPath}/controller?command=blockUser"
                       class="nav-link px-2 text-white">${block}</a></li>
                <li>
                    <div>
                        <a class="btn btn-group-sm btn-outline-light"
                           href="${pageContext.request.contextPath}/controller?command=changeLanguage&lang=en">EN</a>
                        <a class="btn btn-group-sm btn-outline-light"
                           href="${pageContext.request.contextPath}/controller?command=changeLanguage&lang=ru">RU</a>
                    </div>
                </li>
            </ul>
            <form action="${pageContext.request.contextPath}/controller" method="get" enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="command" value="logoutUser">
                <div class="text-end">
                    <button type="submit" class="btn btn-outline-light me-2">Log out</button>
                </div>
            </form>
        </div>
    </div>
</header>
