<%--
  Created by IntelliJ IDEA.
  User: Mikhail
  Date: 028 28 01 22
  Time: 21:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Order Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/bootstrap.css">
</head>
<body>
<%@include file="librarianHeader.jsp" %>
<table class="table table-striped table-bordered">
    <caption>Here you can find all orders</caption>
    <thead class="table-dark">
    <tr>
        <fmt:message key="th.number" var="numTh"/>
        <th scope="col">${numTh}</th>
        <fmt:message key="th.nameBook" var="nameBook"/>
        <th scope="col">${nameBook}</th>
        <fmt:message key="th.user" var="user"/>
        <th scope="col">${user}</th>
        <fmt:message key="th.rentTime" var="rentTime"/>
        <th scope="col">${rentTime}</th>
        <fmt:message key="th.rentPer" var="rentPer"/>
        <th scope="col">${rentPer}</th>
        <fmt:message key="th.status" var="stasut"/>
        <th scope="col">${stasut}</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="book" varStatus="status" items="${requestScope.orders}">
        <tr>
            <td>${status.count}</td>
            <td>
                <c:forEach var="i" begin="0" end="${book.book.size()-1}">
                    ${book.book.get(i).name}<br>
                </c:forEach>
            </td>
            <td>${book.user.username}</td>

            <td><fmt:parseDate value="${book.rentalTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedRentTime" type="both" />
                <fmt:formatDate pattern="dd MMM yyyy HH:mm" value="${parsedRentTime}" /></td>
            <td><fmt:parseDate value="${book.rentalPeriod}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedRentPer" type="both" />
                <fmt:formatDate pattern="dd MMM yyyy HH:mm" value="${parsedRentPer}" /></td>
            <td><c:if test="${book.rentalPeriod<=requestScope.dateTimeNow}">
                <fmt:message key="status.exp" var="exp"/>
                <p>${exp}</p>
            </c:if>
            <c:if test="${book.rentalPeriod>=requestScope.dateTimeNow}">
                <fmt:message key="status.act" var="act"/>
                <p>${act}</p>
            </c:if></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<nav aria-label="Page navigation example">
    <ul class="pagination">
        <c:forEach var="integer" varStatus="status" items="${requestScope.offsetList}">
            <li class="page-item"><a
                    class="page-link"
                    href="${pageContext.request.contextPath}/controller?command=showAllOrders&offset=${integer.intValue()}">${status.count}</a>
            </li>
        </c:forEach>
    </ul>
</nav>
<%--<%@include file="librarianFooter.jsp" %>--%>
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.js"></script>
</body>
</html>
