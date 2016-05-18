<%@page pageEncoding="UTF-8" %>
<div class="navbar navbar-inverse">
    <a class="brand" href="/user?userId=${CURRENT_USER_ID}">Привет, ${HEADER_USER_NAME}!</a>
    <div class="navbar-inner">
        <ul class="nav">
            <li><a href="/user">Новый пользователь</a></li>
            <li><a href="/userList">Пользователи</a></li>
            <li><a href="/logout">Выход</a></li>
        </ul>
    </div>
</div>