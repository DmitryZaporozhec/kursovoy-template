<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>Управление пользователями</title>
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
        <th>Age</th>
        <th>Количество неудачных входов</th>
        <th>Дата последнего удачного входа</th>
        <th>Статус</th>
        <th>Действия</th>
    </tr>
    </thead>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.login} </td>
            <td>${user.firstName} </td>
            <td> ${user.lastName} </td>
            <td> ${user.age}</td>
            <td> ${user.failLoginCount}</td>
            <td> ${user.lastLogin}</td>
            <td> ${user.status}</td>
            <td><a href="/user?userId=${user.id}"><i class="icon-edit"></i></a>
                <a href="/delete?userId=${user.id}"><i class="icon-trash"></i></a>
            </td>
        </tr>
        <c:if test="${not empty user.userIpHistoryList}">
        <tr>
            <td colspan="4">
            </td>
            <td colspan="4">
                <table class="table">
                    <thead>
                    <tr class="info">
                        <th colspan="3">История входов</th>
                    </tr>
                    <tr class="info">
                        <th>Ip Адресс</th>
                        <th>Дата</th>
                        <th>User Agent</th>
                        <th>Язык</th>
                        <th>Статус</th>
                    </tr>
                    </thead>
                    <c:forEach var="hisRec" items="${user.userIpHistoryList}">
                        <tr>
                            <td>${hisRec.ipAddress}</td>
                            <td>${hisRec.loginDate}</td>
                            <td>${hisRec.userAgent}</td>
                            <td>${hisRec.locale}</td>
                            <td>${hisRec.status}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
        </c:if>
    </c:forEach>
</table>
</body>
</html>