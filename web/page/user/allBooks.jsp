<%--
  Created by IntelliJ IDEA.
  User: Mikhail
  Date: 028 28 01 22
  Time: 21:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Books Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/bootstrap.css">
</head>
<body>
    <%@include file="userHeader.jsp" %>
<form method="post" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="addToCart">
<table class="table table-bordered table-striped">
    <fmt:message key="caption.book" var="captionText"/>
    <caption>${captionText}</caption>
    <thead class="table-dark">
    <tr>
        <fmt:message key="th.number" var="numTh"/>
        <th scope="col">${numTh}</th>
        <fmt:message key="th.nameBook" var="nameBook"/>
        <th scope="col">${nameBook}</th>
        <fmt:message key="th.authName" var="authName"/>
        <th scope="col">${authName}</th>
        <fmt:message key="th.genre" var="genre"/>
        <th scope="col">${genre}</th>
        <fmt:message key="th.section" var="section"/>
        <th scope="col">${section}</th>
        <fmt:message key="th.quantity" var="quantity"/>
        <th scope="col">${quantity}</th>
        <fmt:message key="th.year" var="year"/>
        <th scope="col">${year}</th>
        <fmt:message key="th.addCheck" var="addCheck"/>
        <th scope="col">${addCheck}</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="book" varStatus="status" items="${requestScope.books}">
        <tr>
            <td>${status.count}</td>
            <td>${book.name}</td>
            <td>${book.author.fullName}</td>
            <td>${book.genre.name}</td>
            <td>${book.section.name}</td>
            <td>${book.quantity}</td>
            <td>${book.publicationYear}</td>
            <td>
                <div class="form-check">
                <label for="bookAdd" class="form-check-label">Add</label>
                <input class="form-check-input" type="checkbox" id="bookAdd" name="bookId" value="${book.id}">
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
    <fmt:message key="button.addToCart" var="addToCart"/>
   <button type="submit" class="btn btn-dark">${addToCart}</button>
</form>
<nav aria-label="Page navigation example">
    <ul class="pagination">
        <c:forEach var="integer" varStatus="status" items="${requestScope.offsetList}">
            <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/controller?command=showAllBooks&offset=${integer.intValue()}">${status.count}</a></li>
        </c:forEach>
    </ul>
</nav>
<%@include file="userFooter.jsp" %>
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.js"></script>
</body>
</html>
