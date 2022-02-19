<%--
  Created by IntelliJ IDEA.
  User: Mikhail
  Date: 002 02 02 22
  Time: 23:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Shopping cart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/bootstrap.css">
</head>
<body>
<%@include file="userHeader.jsp" %>
    <table class="table table-bordered table-striped">
        <fmt:message key="caption.order" var="caption"/>
        <caption>${caption}</caption>
        <thead class="table-dark">
        <tr>
            <fmt:message key="th.number" var="numTh"/>
            <th scope="col">${numTh}</th>
            <fmt:message key="th.nameBook" var="nameBook"/>
            <th scope="col">${nameBook}</th>
            <fmt:message key="th.authName" var="authName"/>
            <th scope="col">${authName}</th>
            <fmt:message key="th.year" var="year"/>
            <th scope="col">${year}</th>
            <fmt:message key="th.delete" var="delete"/>
            <th scope="col">${delete}</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="book" varStatus="status" items="${sessionScope.shoppingCart.shoppingList}">
            <tr>
                <td><input type="hidden" name="bookId" value="${book.id}">${status.count}</td>
                <td>${book.name}</td>
                <td>${book.author.fullName}</td>
                <td>${book.publicationYear}</td>
                <td>
                    <div>
                        <a href="${pageContext.request.contextPath}/controller?command=deleteBookFromOrder&bookId=${book.id}">
                            <input class="btn-dark" type="submit" value="Delete">
                        </a>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
<form method="post" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="addOrder">
<c:forEach var="book" varStatus="status" items="${sessionScope.shoppingCart.shoppingList}">
    <input type="hidden" name="bookId" value="${book.id}">
</c:forEach>
    <div class="form-check flex-grow-1 justify-content-center">
        <fmt:message key="check.lib" var="lib"/>
        <label class="form-check-label"><input class="form-check-input" type="radio" name="rentalPeriod" value="1"
                                               checked>${lib}</label><br>
        <fmt:message key="check.sub14" var="sub14"/>
        <label class="form-check-label"><input class="form-check-input" type="radio" name="rentalPeriod" value="14"
                                               checked>${sub14}</label><br>
        <fmt:message key="check.sub30" var="sub30"/>
        <label class="form-check-label"><input class="form-check-input" type="radio" name="rentalPeriod" value="30"
                                               checked>${sub30}</label><br>
    </div>
    <p></p>
    <fmt:message key="button.addOrd" var="addOrd"/>
    <button type="submit" class="btn btn-dark">${addOrd}</button>
</form>
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.js"></script>
<%@include file="userFooter.jsp" %>
</body>
</html>
