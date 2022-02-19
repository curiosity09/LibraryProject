<%--
  Created by IntelliJ IDEA.
  User: Mikhail
  Date: 025 25 01 22
  Time: 21:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/locale.jsp"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}resources/static/css/bootstrap.css">
</head>
<body>
<header class="p-3 bg-dark text-white">
    <div class="container">
        <div class="d-flex justify-content-lg-start">
            <a href="${pageContext.request.contextPath}/"
               class="d-flex">
                <img src="${pageContext.request.contextPath}/resources/images/icon.png" width="50" height="50"
                     role="img" aria-label="Bootstrap"
                     alt="Library">
            </a>
            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-end mb-md-0">
                <li class="justify-content-end p-1">
                    <a class="btn btn-group-sm btn-outline-light"
                       href="${pageContext.request.contextPath}/controller?command=changeLanguage&lang=en">EN</a>
                    <a class="btn btn-group-sm btn-outline-light"
                       href="${pageContext.request.contextPath}/controller?command=changeLanguage&lang=ru">RU</a>
                </li>
            </ul>
            <form action="${pageContext.request.contextPath}/register.jsp">
                <button type="submit" class="btn btn-warning">Sign Up</button>
            </form>
        </div>
    </div>
</header>
<div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-5 mt-5 p-3">
            <form method="post" action="${pageContext.request.contextPath}/controller"
                  enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="command" value="loginUser">
                <div class="mb-3">
                    <fmt:message key="th.username" var="user"/>
                    <label for="inputUsername" class="form-label">${user}</label>
                    <fmt:message key="placeholder.username" var="phUser"/>
                    <input name="username" type="text" class="form-control" id="inputUsername"
                           placeholder="${phUser}" required
                    pattern="^[A-za-z][A-Za-z0-9\._]{3,10}$">
                </div>
                <div class="mb-3">
                    <fmt:message key="label.pass" var="pass"/>
                    <label for="inputPassword" class="form-label">${pass}</label>
                    <fmt:message key="placeholder.pass" var="passPh"/>
                    <input name="password" type="password" class="form-control"
                        pattern="^[A-za-z0-9\._]{4,16}$" id="inputPassword" placeholder="${passPh}" required>
                </div>
                <fmt:message key="button.submit" var="sub"/>
                <button type="submit" class="btn btn-dark">${sub}</button>
                <p></p>
                <fmt:message key="alert.loginIf" var="alIf"/>
                <fmt:message key="alert.loginReg" var="alR"/>
                <div class="alert alert-dark" role="alert">
                    ${alIf} <a class="alert-link" href="${pageContext.request.contextPath}/register.jsp"> ${alR}</a>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}resources/static/js/bootstrap.js"></script>
</body>
</html>
