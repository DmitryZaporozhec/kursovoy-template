<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>Пользователь</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script language="javascript" src="/js/jquery.js" type="text/javascript"></script>
    <script language="javascript" src="/js/user.js" type="text/javascript"></script>
    <script language="javascript" src="/js/bootstrap-dropdown.js" type="text/javascript"></script>
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
            <strong>Saved!</strong> Сохранен!
        </div>

        <div class="alert alert-error" style="display: none;">
            <strong>Ошибка!</strong> Не могу сохранить пользователя!
        </div>

        <label>Логин</label>
        <input id="login" type="text" value="${user.login}">
        <input id="userId" type="hidden" value="${user.userId}">
        <label>Имя</label>
        <input id="name" type="text" value="${user.firstName}">
        <label>Фамилия</label>
        <input id="lastName" type="text" value="${user.lastName}">
        <label for="age">Возраст</label>
        <input name='age' id="age" type="number" value="${user.age}">
        <label for="userType">Тип пользователя</label>
        <select name="userType" id="userType">
            <c:forEach items="${userTypes}" var="item">
                <option value="${item}" <c:if test="${user.userType eq item}">
                    selected="selected"
                </c:if> >${item}</option>
            </c:forEach>
        </select>
        <label>Пароль</label>
        <input id="password" type="password" value="${user.password}">
        <div>
            <button type="submit" class="btn btn-primary">Сохранить</button>
            <a class="btn btn-danger" href="/delete?userId=${user.userId}">Удалить</a>
        </div>
    </fieldset>
</form>

</body>
</html>