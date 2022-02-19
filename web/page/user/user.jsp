<%--
  Created by IntelliJ IDEA.
  User: Mikhail
  Date: 025 25 01 22
  Time: 21:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/bootstrap.css">
</head>
<body>
<%@include file="userHeader.jsp" %>
<fmt:message key="page.text" var="pageText"/>
<h2>${pageText}</h2>
<%@include file="userFooter.jsp" %>
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.js"></script>
</body>
</html>
