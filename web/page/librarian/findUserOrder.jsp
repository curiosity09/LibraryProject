<%--
  Created by IntelliJ IDEA.
  User: Mikhail
  Date: 008 08 02 22
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User orders</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/bootstrap.css">
</head>
<body>
<%@include file="librarianHeader.jsp" %>
<div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-3 mt-5 p-3">
            <form method="post" action="${pageContext.request.contextPath}/controller"
                  enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="command" value="findUserOrder">
                <div class="mb-3">
                    <fmt:message key="th.username" var="user"/>
                    <label class="form-label">${user}</label>
                    <label>
                        <select name="username" class="form-select">
                                <c:forEach var="user" items="${requestScope.users}">
                                    <option>${user.username}</option>
                                </c:forEach></select>
                    </label>

                </div>
                <input type="hidden" name="offset" value="${param.get("offset")}">
<%--                <input type="hidden" name="limit" value="${param.get("limit")}">--%>
                <fmt:message key="button.submit" var="sub"/>
                <button type="submit" class="btn btn-dark">${sub}</button>
            </form>
        </div>
    </div>
</div>
<%@include file="librarianFooter.jsp" %>
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.js"></script>
</body>
</html>
