<%--
  Created by IntelliJ IDEA.
  User: Mikhail
  Date: 008 08 02 22
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>User orders</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/bootstrap.css">
</head>
<body>
<%@include file="librarianHeader.jsp" %>
<form method="post" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="finishOrder">
<table class="table table-striped table-bordered">
    <fmt:message key="caption.userOrder" var="caption"/>
    <caption>${caption}</caption>
    <thead class="table-dark">
    <tr>
        <fmt:message key="th.number" var="numTh"/>
        <th scope="col">${numTh}</th>
        <fmt:message key="th.nameBook" var="nameBook"/>
        <th scope="col">${nameBook}</th>
        <fmt:message key="th.rentTime" var="rentTime"/>
        <th scope="col">${rentTime}</th>
        <fmt:message key="th.rentPer" var="rentPer"/>
        <th scope="col">${rentPer}</th>
        <fmt:message key="th.delete" var="del"/>
        <th scope="col">${del}</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="book" varStatus="status" items="${requestScope.usersOrder}">
        <tr>
            <td>${status.count} </td>
            <td>
                <c:forEach var="i" begin="0" end="${book.book.size()-1}">
                    <ul>
                        <li>
                            <p>
                                    ${book.book.get(i).name}<br>
                                    ${book.book.get(i).author.fullName}<br>
                                    ${book.book.get(i).publicationYear}
                            </p>
                        </li>
                    </ul>
                </c:forEach>
            </td>
            <td><fmt:parseDate value="${book.rentalTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedRentTime" type="both" />
                <fmt:formatDate pattern="dd MMM yyyy HH:mm" value="${parsedRentTime}" /></td>
            <td><fmt:parseDate value="${book.rentalPeriod}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedRentPer" type="both" />
                <fmt:formatDate pattern="dd MMM yyyy HH:mm" value="${parsedRentPer}" /></td>
            <td>
                <div class="form-check">
                    <label class="form-check-label" for="bookAdd">${del}</label>
                    <input type="hidden" name="username" value="${param.get("username")}">
                    <input type="hidden" name="offset" value="${param.get("offset")}">
                    <input class="form-check-input" type="checkbox" id="bookAdd" name="orderId" value="${book.id}">
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
    <fmt:message key="button.delete" var="delete"/>
    <button type="submit" class="btn btn-dark">${delete}</button>
</form>
<nav aria-label="Page navigation example">
    <ul class="pagination">
        <c:forEach var="integer" varStatus="status" items="${requestScope.offsetList}">
            <li class="page-item"><a class="page-link"
                                     href="${pageContext.request.contextPath}/controller?command=findUserOrder&username=${param.get("username")}&offset=${integer.intValue()}">${status.count}</a>
            </li>
        </c:forEach>
    </ul>
</nav>
<%--<%@include file="userFooter.jsp" %>--%>
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.js"></script>
</body>
</html>
