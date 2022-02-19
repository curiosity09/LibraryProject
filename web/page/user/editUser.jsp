<%--
  Created by IntelliJ IDEA.
  User: Mikhail
  Date: 010 10 02 22
  Time: 13:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit user page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/bootstrap.css">
</head>
<body>
<%@include file="userHeader.jsp" %>
<div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-5 mt-5 p-3">
            <form method="post" action="${pageContext.request.contextPath}/controller"
                  enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="command" value="editUser">
                    <input name="username" type="hidden" class="form-control" value="${sessionScope.user.username}">
                <div class="mb-3">
                    <fmt:message key="label.pass" var="pass"/>
                    <label for="inputPassword" class="form-label">${pass}</label>
                    <fmt:message key="placeholder.pass" var="phPass"/>
                    <input name="password" type="password"
                          pattern="^[A-za-z0-9\._]{4,16}$" class="form-control" id="inputPassword"
                           placeholder="${phPass}" required>
                </div>
                <input name="role" type="hidden" value="user">
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
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.js"></script>
</body>
</html>
