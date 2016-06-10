<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>User Managment System</title>
    <script language="javascript" src="/js/jquery.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script language="javascript" src="/js/discipline.js" type="text/javascript"></script>
    <script language="javascript" src="/js/bootstrap-dropdown.js" type="text/javascript"></script>
    <%--<link href="/css/bootstrap-responsive.min.css" rel="stylesheet">--%>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
<div class="container-fluid">
    <jsp:include page="includes/head.jsp"/>
    <div class="row">
        <div class="col-sm-2">
            <c:if test="${CURRENT_USER_ID!= null}">
                <jsp:include page="includes/menu.jsp"/>
            </c:if>
        </div>
        <div class="col-sm-10">
            <form id="edit-discipline-form" class="clearfix">
                <fieldset>
                    <legend>Редактирование дисциплины ${discipline.name}</legend>
                    <div class="alert alert-success" style="display: none;">
                        <strong>Saved!</strong> Сохранен!
                    </div>
                    <div class="alert alert-error" style="display: none;">
                        <strong>Ошибка!</strong> Не могу сохранить дисциплину!
                    </div>
                    <input id="id" type="hidden" value="${discipline.disciplineId}">

                    <div class="form-row">
                        <label for="name">Название</label>
                        <input id="name" type="text" required value="${discipline.name}">
                    </div>
                    <div class="form-row">
                        <label for="description">Описание</label>
                        <textarea id="description">${discipline.description}</textarea>
                    </div>
                    <div class="control form-row">
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                        <a class="btn btn-danger" href="/discipline/delete?id=${discipline.disciplineId}">Удалить</a>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>
</body>
</html>