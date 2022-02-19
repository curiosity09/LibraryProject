<%--
  Created by IntelliJ IDEA.
  User: Mikhail
  Date: 002 02 02 22
  Time: 12:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Block user page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/bootstrap.css">
</head>
<body>
<%@include file="adminHeader.jsp" %>
<div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-5 mt-5 p-3">
            <form method="post" action="${pageContext.request.contextPath}/controller"
                  enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="command" value="blockUser">
                <div class="mb-3">
                    <fmt:message key="label.selUser" var="sel"/>
                    <label class="form-label">${sel} </label>
                    <label>
                        <select name="username" class="form-select">
                            <c:forEach var="user" items="${requestScope.debtors}">
                                <option>${user.username}</option>
                            </c:forEach>
                        </select>
                    </label>

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
