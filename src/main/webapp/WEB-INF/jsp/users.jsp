<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>Управление пользователями</title>
    <script language="javascript" src="/js/jquery.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script language="javascript" src="/js/bootstrap-dropdown.js" type="text/javascript"></script>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script>
        $(document).ready(function () {
            $('#groupSelector').on('change', function () {
                window.location.href = $('#groupSelector').val();
            });
        });
    </script>
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
                    <td colspan="4">
                        <label for="groupSelector">Поиск по группе</label>
                        <select id="groupSelector">
                            <option value="/userList">Все группы</option>
                            <c:forEach var="gr" items="${groups}">
                                <option value="/userList?groupId=${gr.id}"
                                        <c:if test="${gr.id eq currentGroup}">selected="selected"</c:if> >${gr.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>Логин</th>
                    <th>Имя</th>
                    <th>Фамилия</th>
                    <th>Тип Пользователя</th>
                    <th>Группа</th>
                    <c:if test="${CURRENT_USER_TYPE eq 'ADMIN'}">
                        <th>Действия</th>
                    </c:if>
                </tr>
                </thead>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.login} </td>
                        <td>${user.firstName} </td>
                        <td> ${user.lastName} </td>
                        <td><fmt:message key="com.edu.${user.userType.name()}"/></td>
                        <td> ${user.groupName}</td>
                        <c:if test="${CURRENT_USER_TYPE eq 'ADMIN'}">
                            <td><a href="/user?userId=${user.userId}"><i class="glyphicon glyphicon-edit"></i></a>
                                <a href="/delete?userId=${user.userId}"><i class="glyphicon glyphicon-remove"></i></a>

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