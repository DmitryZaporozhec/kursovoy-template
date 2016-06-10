<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>User Managment System</title>
    <script language="javascript" src="/js/jquery.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script language="javascript" src="/js/bootstrap-dropdown.js" type="text/javascript"></script>
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
            <h3>Список дисциплин</h3>
            <table class="table">
                <thead>
                <tr>
                    <th>Название</th>
                    <th>Описание</th>
                    <th>Действия</th>
                </tr>
                </thead>
                <c:forEach var="discipline" items="${disciplines}">
                    <tr>
                        <td>${discipline.name} </td>
                        <td>${discipline.description} </td>
                        <c:if test="${CURRENT_USER_TYPE eq 'ADMIN'}">
                            <td><a href="/discipline/get?id=${discipline.disciplineId}"><i
                                    class="glyphicon glyphicon-edit"></i></a>
                                <a href="/discipline/delete?id=${discipline.disciplineId}"><i
                                        class="glyphicon glyphicon-remove"></i></a>

                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>