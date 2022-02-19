<%--
  Created by IntelliJ IDEA.
  User: Mikhail
  Date: 002 02 02 22
  Time: 0:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add author page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/bootstrap.css">
</head>
<body>
<%@include file="librarianHeader.jsp" %>
<div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-5 mt-5 p-3">
            <form method="post" action="${pageContext.request.contextPath}/controller"
                  enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="command" value="addNewAuthor">
                <div class="mb-3">
                    <fmt:message key="th.authName" var="authName"/>
                    <label for="inputAuthor" class="form-label">${authName}</label>
                    <input name="authorFullName" type="text"
                    <fmt:message key="placeholder.author" var="phAuth"/>
                         pattern="^[A-ZА-Яа-яa-z\s]+$"  class="form-control" id="inputAuthor"
                           placeholder="${phAuth}" required>
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
