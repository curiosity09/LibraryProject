<%--
  Created by IntelliJ IDEA.
  User: Mikhail
  Date: 010 10 02 22
  Time: 18:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User info</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/bootstrap.css">
</head>
<body>
<%@include file="userHeader.jsp" %>
<table class="table table-bordered table-striped">
    <fmt:message key="caption.userInf" var="caption"/>
    <caption>${caption}</caption>
    <thead class="table-dark">
    <tr>
        <fmt:message key="th.username" var="username"/>
        <th scope="row">${username}</th>
        <fmt:message key="label.name" var="name"/>
        <th scope="row">${name}</th>
        <fmt:message key="label.surname" var="surname"/>
        <th scope="row">${surname}</th>
        <fmt:message key="label.phone" var="phone"/>
        <th scope="row">${phone}</th>
        <fmt:message key="label.email" var="email"/>
        <th scope="row">${email}</th>
    </tr>
    </thead>
    <tbody>
        <tr>
            <td>${sessionScope.user.username}</td>
            <td>${sessionScope.user.userData.name}</td>
            <td>${sessionScope.user.userData.surname}</td>
            <td>${sessionScope.user.userData.phoneNumber}</td>
            <td>${sessionScope.user.userData.emailAddress}</td>
        </tr>
    </tbody>
</table>
<%@include file="userFooter.jsp" %>
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.js"></script>
</body>
</html>
