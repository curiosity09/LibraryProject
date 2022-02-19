<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/bootstrap.css">
</head>
<body>
<%@include file="adminHeader.jsp" %>
<table class="table table-striped table-bordered">
    <fmt:message key="caption.allUser" var="allU"/>
    <caption>${allU}</caption>
    <thead class="table-dark">
    <tr>
        <th scope="col">ID</th>
        <fmt:message key="th.username" var="username"/>
        <th scope="col">${username}</th>
        <fmt:message key="th.role" var="role"/>
        <th scope="col">${role}</th>
        <fmt:message key="label.name" var="name"/>
        <th scope="col">${name}</th>
        <fmt:message key="label.surname" var="sur"/>
        <th scope="col">${sur}</th>
        <fmt:message key="label.phone" var="phone"/>
        <th scope="col">${phone}</th>
        <fmt:message key="label.email" var="email"/>
        <th scope="col">${email}</th>
        <fmt:message key="th.ban" var="ban"/>
        <th scope="col">${ban}</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${requestScope.users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.role}</td>
            <td>${user.userData.name}</td>
            <td>${user.userData.surname}</td>
            <td>${user.userData.phoneNumber}</td>
            <td>${user.userData.emailAddress}</td>
            <td>
                <c:if test="${user.banned==true}">
                    +
                </c:if>
                <c:if test="${user.banned==false}">
                    -
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
        <c:forEach var="integer" varStatus="status" items="${requestScope.offsetList}">
            <li class="page-item"><a class="page-link"
                                     href="${pageContext.request.contextPath}/controller?command=showAllUsers&limit=10&offset=${integer.intValue()}">${status.count}</a>
            </li>
        </c:forEach>
    </ul>
</nav>
<%@include file="adminFooter.jsp" %>
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.js"></script>
</body>
</html>
