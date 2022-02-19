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
    <title>Register page</title>
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
                <li class="justify-content-center p-1">
                        <a class="btn btn-group-sm btn-outline-light"
                           href="${pageContext.request.contextPath}/controller?command=changeLanguage&lang=en">EN</a>
                        <a class="btn btn-group-sm btn-outline-light"
                           href="${pageContext.request.contextPath}/controller?command=changeLanguage&lang=ru">RU</a>
                </li>
            </ul>
    <form action="${pageContext.request.contextPath}/index.jsp">
        <div class="text-end">
            <button type="submit" class="btn btn-outline-light me-2">Log In</button>
        </div>
    </form>
        </div>
    </div>
</header>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-5 p-3">
            <form method="post" action="${pageContext.request.contextPath}/controller" enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="command" value="registerUser">
                <div class="mb-3">
                    <fmt:message key="th.username" var="user"/>
                    <label for="inputUsername" class="form-label">${user}</label>
                    <fmt:message key="placeholder.username" var="userPh"/>
                    <input name="username" type="text" class="form-control"
                           pattern="^[A-za-z][A-Za-z0-9\._]{3,10}$" id="inputUsername" placeholder="${userPh}" required>
                </div>
                <div class="mb-3">
                    <fmt:message key="label.pass" var="pass"/>
                    <label for="inputPassword" class="form-label">${pass}</label>
                    <fmt:message key="placeholder.pass" var="passPh"/>
                    <input name="password" type="password" class="form-control"
                           pattern="^[A-za-z0-9\._]{4,16}$" id="inputPassword" placeholder="${passPh}" required>
                </div>
                <input type="hidden" name="role" value="user">
                <div class="mb-3">
                    <fmt:message key="label.name" var="name"/>
                    <label for="inputName" class="form-label">${name}</label>
                    <fmt:message key="placeholder.name" var="phName"/>
                    <input name="name" type="text" class="form-control"
                           pattern="^([A-Z]{1}[a-z]+|[А-Я]{1}[а-я]+)$" id="inputName"
                           placeholder="${phName}" value="${sessionScope.user.userData.name}">
                </div>
                <div class="mb-3">
                    <fmt:message key="label.surname" var="surname"/>
                    <label for="inputSurName" class="form-label">${surname}</label>
                    <fmt:message key="placeholder.surname" var="phSurname"/>
                    <input name="surname" type="text" class="form-control"
                           pattern="^([A-Z]{1}[a-z]+|[А-Я]{1}[а-я]+)$" id="inputSurName"
                           placeholder="${phSurname}" value="${sessionScope.user.userData.surname}">
                </div>
                <div class="mb-3">
                    <fmt:message key="label.phone" var="phone"/>
                    <label for="inputPhone" class="form-label">${phone}</label>
                    <fmt:message key="placeholder.phone" var="phPhone"/>
                    <input name="phone" type="text" class="form-control"
                           pattern="^(\+?375|80)(29|25|44|33)(\d{3})(\d{2})(\d{2})$" id="inputPhone"
                           placeholder="${phPhone}" value="${sessionScope.user.userData.phoneNumber}">
                </div>
                <div class="mb-3">
                    <fmt:message key="label.email" var="email"/>
                    <label for="inputEmail" class="form-label">${email}</label>
                    <fmt:message key="placeholder.email" var="phEmail"/>
                    <input name="email" type="email" class="form-control"
                           pattern="[A-za-z0-9\._]+@[a-z]+\.[a-z]{2,4}" id="inputEmail"
                           placeholder="${phEmail}" value="${sessionScope.user.userData.emailAddress}">
                </div>
                <fmt:message key="button.submit" var="submit"/>
                <button type="submit" class="btn btn-dark">${submit}</button>
            </form>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}resources/static/js/bootstrap.js"></script>
</body>
</html>