<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>Пользователь</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script language="javascript" src="/js/jquery.js" type="text/javascript"></script>
    <script language="javascript" src="/js/user.js" type="text/javascript"></script>
    <script language="javascript" src="/js/tooltip.js" type="text/javascript"></script>
    <script language="javascript" src="/js/bootstrap-dropdown.js" type="text/javascript"></script>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<c:if test="${CURRENT_USER_ID!= null}">
    <jsp:include page="includes/header.jsp"/>
</c:if>

<div class="hero-unit">
    <div class="container">
        <h1>Добро пожаловать в систему ${HEADER_USER_NAME}</h1>
        <h4>Последние события:</h4>
        <c:forEach var="news" items="${newsList}">
            <p>
                <span class="label label-info">
                    <fmt:formatDate pattern="yyyy MM dd HH:mm"
                                    value="${news.createDate}"/>  - ${news.caption}</span>
                <br/>
                    ${news.text}
            </p>
        </c:forEach></div>
</div>

</body>
</html>