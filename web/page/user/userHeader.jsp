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
                <li><a href="${pageContext.request.contextPath}/page/user/user.jsp"
                       class="nav-link px-2 text-white">${homeBut}</a></li>
                <fmt:message key="button.allBooks" var="allBookBut"/>
                <li><a href="${pageContext.request.contextPath}/controller?command=showAllBooks&offset=0"
                       class="nav-link px-2 text-white">${allBookBut}</a></li>
                <fmt:message key="button.cart" var="cartBur"/>
                <li><a href="${pageContext.request.contextPath}/page/user/shoppingCart.jsp"
                       class="nav-link px-2 text-white">${cartBur}</a></li>
                <fmt:message key="button.myOrder" var="myOrdBut"/>
                <li><a href="${pageContext.request.contextPath}/controller?command=showUserOrder&offset=0"
                       class="nav-link px-2 text-white">${myOrdBut}</a></li>
                <fmt:message key="button.editUserInfo" var="editUserBut"/>
                <li><a href="${pageContext.request.contextPath}/page/user/editUser.jsp"
                       class="nav-link px-2 text-white">${editUserBut}</a></li>
                <fmt:message key="button.aboutUser" var="aboutUsetBut"/>
                <li><a href="${pageContext.request.contextPath}/controller?command=showUserInfo"
                       class="nav-link px-2 text-white">${aboutUsetBut}</a></li>
                <li>
                    <div>
                        <a class="btn btn-group-sm btn-outline-light"
                           href="${pageContext.request.contextPath}/controller?command=changeLanguage&lang=en">EN</a>
                        <a class="btn btn-group-sm btn-outline-light"
                           href="${pageContext.request.contextPath}/controller?command=changeLanguage&lang=ru">RU</a>
                    </div>
                </li>
            </ul>
            <form action="${pageContext.request.contextPath}/controller" method="get"
                  enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="command" value="logoutUser">
                <div class="text-end">
                    <button type="submit" class="btn btn-outline-light me-2">Log out</button>
                </div>
            </form>
        </div>
    </div>
</header>
