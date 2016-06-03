<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>Пользователь</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script language="javascript" src="/js/jquery.js" type="text/javascript"></script>
    <script language="javascript" src="/js/bootstrap.js" type="text/javascript"></script>
    <script language="javascript" src="/js/user.js" type="text/javascript"></script>
    <script language="javascript" src="/js/tooltip.js" type="text/javascript"></script>
    <script language="javascript" src="/js/bootstrap-dropdown.js" type="text/javascript"></script>
    <link href="/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>


<div class="container-fluid">
    <div class="row">
        <div class="col-sm-2">
            <c:if test="${CURRENT_USER_ID!= null}">
                <jsp:include page="includes/menu.jsp"/>
            </c:if>
        </div>
        <div class="col-sm-10">
            <div class="">
                <h1>Добро пожаловать в систему ${HEADER_USER_NAME}</h1>
                <h4>Последние события:</h4>
                <c:forEach var="news" items="${newsList}">
                    <p>
                <span class="label label-info">
                    <fmt:formatDate pattern="yyyy MM dd HH:mm"
                                    value="${news.createDate}"/>  - ${news.caption}</span>  ${news.text}
                    </p>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

</body>
</html>