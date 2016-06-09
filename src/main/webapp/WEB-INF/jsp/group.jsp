<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>Пользователь</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script language="javascript" src="/js/jquery.js" type="text/javascript"></script>
    <script language="javascript" src="/js/group.js" type="text/javascript"></script>
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
            <form id="edit-group-form">
                <fieldset>
                    <legend>Редактирование группы ${group.name}</legend>

                    <div class="alert alert-success" style="display: none;">
                        <strong>Saved!</strong> Сохранен!
                    </div>

                    <div class="alert alert-error" style="display: none;">
                        <strong>Ошибка!</strong> Не могу сохранить пользователя!
                    </div>
                    <div class="form-row">
                        <label for="name">Название</label>
                        <input id="name" type="text" value="${group.name}">
                    </div>

                    <input id="id" type="hidden" value="${group.id}">
                    <div class="form-row">
                        <label for="description">Описание</label>
                        <input id="description" type="text" value="${user.description}">
                    </div>
                    <div class="form-row control">
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                        <a class="btn btn-danger" href="/group/delete?id=${group.id}">Удалить</a>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>
</body>
</html>