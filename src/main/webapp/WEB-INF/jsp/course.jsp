<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>User Managment System</title>
    <script language="javascript" src="/js/jquery.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script language="javascript" src="/js/course.js" type="text/javascript"></script>
    <script language="javascript" src="/js/bootstrap-dropdown.js" type="text/javascript"></script>
    <link href="/css/bootstrap-responsive.min.css" rel="stylesheet">
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
        <div class="col-sm-2">
            <c:if test="${CURRENT_USER_ID!= null}">
                <jsp:include page="includes/course-tree.jsp"/>
            </c:if>
        </div>
        <div class="col-sm-8">
            <form id="edit-course-form" class="clearfix">
                <fieldset>
                    <legend>Редактирование курса ${course.name}</legend>
                    <div class="alert alert-success" style="display: none;">
                        <strong>Saved!</strong> Сохранен!
                    </div>
                    <div class="alert alert-error" style="display: none;">
                        <strong>Ошибка!</strong> Не могу сохранить Курс!
                    </div>
                    <input id="id" type="hidden" value="${course.id}">

                    <div class="form-row">
                        <label for="name">Название</label>
                        <input id="name" type="text" required value="${course.name}">
                    </div>
                    <div class="form-row">
                        <label for="description">Описание</label>
                        <textarea id="description">${course.description}</textarea>
                    </div>
                    <div class="form-row">
                        <label for="discipline">Дисциплина</label>
                        <select name="discipline" id="discipline" <c:if
                                test="${course.disciplineId != 0}">
                            disabled="disabled"
                        </c:if>>
                            <c:forEach items="${disciplines}" var="item">
                                <option value="${item.disciplineId}" <c:if
                                        test="${course.disciplineId eq item.disciplineId}">
                                    selected="selected"
                                </c:if> >${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-row">
                        <label for="user">Создатель</label>
                        <input id="user" type="text" readonly="readonly" value="${course.userDisplayName}">
                        <input id="userId" type="hidden" value="${course.userId}">
                    </div>
                    <div class="form-row">
                        <label for="createDate">Дата последнего обновления</label>
                        <input id="createDate" type="text" readonly="readonly" value=" <fmt:formatDate pattern="yyyy MM dd HH:mm"
                                    value="${course.createDate}"/>">
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