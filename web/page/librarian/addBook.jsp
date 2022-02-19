<%--
  Created by IntelliJ IDEA.
  User: Mikhail
  Date: 029 29 01 22
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add book page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/bootstrap.css">
</head>
<body>
<%@include file="librarianHeader.jsp" %>
<div class="container">
    <div class="row justify-content-center mt-2">
        <div class="col-5 mt-2 p-3">
            <form method="post" action="${pageContext.request.contextPath}/controller"
                  enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="command" value="addNewBook">
                <div class="mb-3">
                    <fmt:message key="th.nameBook" var="book"/>
                    <label for="inputNameBook" class="form-label">${book}</label>
                    <fmt:message key="placeholder.bookName" var="phBook"/>
                    <input name="bookName" type="text"
                           pattern="^[A-ZА-Яа-яa-z0-9\s]+$" class="form-control" id="inputNameBook"
                           placeholder="${phBook}" required>
                </div>
                <div class="mb-3">
                    <fmt:message key="th.authName" var="auth"/>
                    <label>${auth}
                        <select name="authorFullName" class="form-select">
                            <c:forEach var="author" items="${requestScope.authors}">
                              <option>${author.fullName}</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <div class="mb-3">
                    <fmt:message key="th.genre" var="genre"/>
                    <label>${genre}
                        <select name="genre" class="form-select">
                            <c:forEach var="genre" items="${requestScope.genres}">
                                <option>${genre.name}</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <div class="mb-3">
                    <fmt:message key="th.section" var="section"/>
                    <label>${section}
                        <select name="section" class="form-select">
                            <c:forEach var="section" items="${requestScope.sections}">
                                <option>${section.name}</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <div class="mb-3">
                    <fmt:message key="th.quantity" var="quantity"/>
                    <label for="inputQuantity" class="form-label">${quantity}</label>
                    <fmt:message key="placeholder.quantity" var="phQuantity"/>
                    <input name="quantity" type="number" min="1" step="1" max="10" class="form-control" id="inputQuantity"
                           placeholder="${phQuantity}" required>
                </div>
                <div class="mb-3">
                    <fmt:message key="th.year" var="year"/>
                    <label for="inputYear" class="form-label">${year}</label>
                    <fmt:message key="placeholder.year" var="phYear"/>
                    <input name="year" type="number" min="1800" max="2030" class="form-control" id="inputYear"
                           placeholder="${phYear}" required>
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
