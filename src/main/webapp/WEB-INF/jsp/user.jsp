<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Пользователь</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script language="javascript" src="/js/jquery.js" type="text/javascript"></script>
    <script language="javascript" src="/js/user.js" type="text/javascript"></script>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<c:if test="${CURRENT_USER_ID!= null}">
    <jsp:include page="includes/header.jsp"/>
</c:if>
<form id="edit-user-form">
    <fieldset>
        <legend>Редактирование пользователя ${user.firstName} ${user.lastName}</legend>

        <div class="alert alert-success" style="display: none;">
            <strong>Сохранено!</strong> User saved successfully!
        </div>

        <div class="alert alert-error" style="display: none;">
            <strong>Ошибка!</strong> User hasn't saved!
        </div>


        <label>Логин</label>
        <input id="login" type="text" value="${user.login}">
        <input id="userId" type="hidden" value="${user.id}">
        <label>Имя</label>
        <input id="name" type="text" value="${user.firstName}">
        <label>Фамилия</label>
        <input id="lastName" type="text" value="${user.lastName}">
        <label>Email</label>
        <input id="email" type="text" value="${user.email}">
        <label>Возраст</label>
        <input id="age" type="number" value="${user.age}">
        <label>Пароль</label>
        <input id="password" type="password" value="${user.password}">

        <label>Ваш любимый цвет?</label>
        <input id="passPhrase" type="password" value="${user.passPhrase}">

        <div>
            <button type="submit" class="btn">Сохранить</button>
            <a class="btn btn-danger" href="/delete?userId=${user.id}">Удалить</a></div>
    </fieldset>
</form>

</body>
</html>