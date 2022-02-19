<%--
  Created by IntelliJ IDEA.
  User: Yahor_Kaltsou
  Date: 1/26/2022
  Time: 9:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en_US'}" scope="session"/>
<fmt:setBundle basename="localization/localization" scope="session"/>

