<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>User Managment System</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="includes/header.jsp"/>
<table class="table">
    <thead>
    <tr>
        <th>Логин</th>
        <th>Имя</th>
        <th>Фамилия</th>
        <th>Возвраст</th>
        <th>Тип Пользователя</th>
        <th>Действия</th>
    </tr>
    </thead>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.login} </td>
            <td>${user.firstName} </td>
            <td> ${user.lastName} </td>
            <td> ${user.age}</td>
            <td> ${user.userType.name()}</td>
            <td><a href="/user?userId=${user.userId}"><i class="icon-edit"></i></a>
                <a href="/delete?userId=${user.userId}"><i class="icon-trash"></i></a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>