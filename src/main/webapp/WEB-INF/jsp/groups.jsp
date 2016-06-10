<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>Группы</title>
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
            <h3>Список групп</h3>
            <table class="table">
                <thead>
                <tr>
                    <th>Название</th>
                    <th>Описание</th>
                    <th>Действия</th>
                </tr>
                </thead>
                <c:forEach var="group" items="${groups}">
                    <tr>
                        <td>${group.name} </td>
                        <td>${group.description} </td>
                        <td>
                            <c:if test="${CURRENT_USER_TYPE eq 'ADMIN'}">
                                <a href="/group/get?id=${group.id}" title="Редактировать"><i class="glyphicon glyphicon-edit"></i></a>
                                <a href="/group/delete?id=${group.id}" title="Удалить"><i class="glyphicon glyphicon-remove"></i></a>
                            </c:if>
                            <c:if test="${CURRENT_USER_TYPE eq 'ADMIN' or CURRENT_USER_TYPE eq  'LECTURER' or CURRENT_USER_TYPE eq  'TUTOR'}">
                                 <a href="#" title="Добавить курс"><i class="glyphicon glyphicon-hand-right"></i></a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>