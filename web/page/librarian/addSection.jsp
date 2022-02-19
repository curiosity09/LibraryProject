
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add section page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/bootstrap.css">
</head>
<body>
<%@include file="librarianHeader.jsp" %>
<div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-5 mt-5 p-3">
            <form method="post" action="${pageContext.request.contextPath}/controller"
                  enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="command" value="addNewSection">
                <div class="mb-3">
                    <fmt:message key="th.section" var="section"/>
                    <label for="inputNameSection" class="form-label">${section}</label>
                    <fmt:message key="placeholder.section" var="phSection"/>
                    <input name="section" type="text"
                           pattern="^[A-ZА-Яа-яa-z\s]+$" class="form-control" id="inputNameSection"
                           placeholder="${phSection}" required>
                </div>
                <fmt:message key="button.submit" var="sub"/>
                <button type="submit" class="btn btn-dark">${sub}</button>
            </form>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.js"></script>
</body>
</html>
