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
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-2">
            <jsp:include page="includes/menu.jsp"/>
        </div>
        <div class="col-md-10">
            <form id="edit-user-form">
                <fieldset>
                    <legend>Редактирование пользователя ${user.firstName} ${user.lastName}</legend>

                    <div class="alert alert-success" style="display: none;">
                        <strong>Saved!</strong> Сохранен!
                    </div>

                    <div class="alert alert-error" style="display: none;">
                        <strong>Ошибка!</strong> Не могу сохранить пользователя!
                    </div>
                    <div class="form-row">
                        <label for="login">Логин</label>
                        <input id="login" type="text" value="${user.login}">
                    </div>

                    <input id="userId" type="hidden" value="${user.userId}">
                    <div class="form-row">
                        <label for="name">Имя</label>
                        <input id="name" type="text" value="${user.firstName}">
                    </div>
                    <div class="form-row">
                        <label for="lastName">Фамилия</label>
                        <input id="lastName" type="text" value="${user.lastName}">
                    </div>
                    <div class="form-row">
                        <label for="userType">Тип пользователя</label>
                        <select name="userType" id="userType">
                            <c:forEach items="${userTypes}" var="item">
                                <option value="${item}" <c:if test="${user.userType eq item}">
                                    selected="selected"
                                </c:if> >${item}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-row">
                        <label for="password">Пароль</label>
                        <input id="password" type="password" value="${user.password}">
                    </div>
                    <div class="form-row control">
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                        <a class="btn btn-danger" href="/delete?userId=${user.userId}">Удалить</a>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>
</body>
</html>