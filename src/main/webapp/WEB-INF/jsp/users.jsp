<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>Управление пользователями</title>
    <script language="javascript" src="/js/jquery.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script language="javascript" src="/js/bootstrap-dropdown.js" type="text/javascript"></script>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
<div class="container-fluid">
    <jsp:include page="includes/head.jsp"/>
    <div class="row">
        <div class="col-sm-2">
            <jsp:include page="includes/menu.jsp"/>
        </div>
        <div class="col-md-10">
            <h3>Список пользователей</h3>
            <table class="table">
                <thead>
                <tr>
                    <th>Логин</th>
                    <th>Имя</th>
                    <th>Фамилия</th>
                    <th>Тип Пользователя</th>
                    <th>Действия</th>
                </tr>
                </thead>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.login} </td>
                        <td>${user.firstName} </td>
                        <td> ${user.lastName} </td>
                        <td> ${user.userType.name()}</td>
                        <td><a href="/user?userId=${user.userId}"><i class="glyphicon glyphicon-edit"></i></a>
                            <a href="/delete?userId=${user.userId}"><i class="glyphicon glyphicon-remove"></i></a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>