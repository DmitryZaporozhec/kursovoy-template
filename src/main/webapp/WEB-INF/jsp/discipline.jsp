<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>User Managment System</title>
    <script language="javascript" src="/js/jquery.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script language="javascript" src="/js/discipline.js" type="text/javascript"></script>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <script language="javascript" src="/js/bootstrap-dropdown.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="includes/header.jsp"/>
<form id="edit-discipline-form">
    <fieldset>
        <legend>Редактирование дисциплины ${discipline.name}</legend>

        <div class="alert alert-success" style="display: none;">
            <strong>Saved!</strong> Сохранен!
        </div>

        <div class="alert alert-error" style="display: none;">
            <strong>Ошибка!</strong> Не могу сохранить дисциплину!
        </div>
        <input id="id" type="hidden" value="${discipline.disciplineId}">
        <label>Название</label>
        <input id="name" type="text" required value="${discipline.name}">
        <label>Описание</label>
        <textarea id="description">${discipline.description}</textarea>
        <div>
            <button type="submit" class="btn btn-primary">Сохранить</button>
            <a class="btn btn-danger" href="/discipline/delete?id=${discipline.disciplineId}">Удалить</a>
        </div>
    </fieldset>
</form>
</body>
</html>