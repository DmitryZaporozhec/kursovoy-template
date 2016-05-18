<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>User Managment System</title>
    <script language="javascript" src="/js/jquery.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <script language="javascript" src="/js/bootstrap-dropdown.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="includes/header.jsp"/>
<table class="table">
    <thead>
    <tr>
        <th>Название</th>
        <th>Описание</th>
        <th>Действия</th>
    </tr>
    </thead>
    <c:forEach var="discipline" items="${disciplines}">
        <tr>
            <td>${discipline.name} </td>
            <td>${discipline.description} </td>
            <td><a href="/discipline/get?id=${discipline.disciplineId}"><i class="icon-edit"></i></a>
                <a href="/discipline/delete?id=${discipline.disciplineId}"><i class="icon-trash"></i></a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>