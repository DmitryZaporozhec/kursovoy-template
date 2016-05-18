<%@page pageEncoding="UTF-8" %>
<div class="navbar navbar-inverse">
    <a class="brand" href="/index">${HEADER_USER_NAME}</a>
    <div class="navbar-inner">
        <ul class="nav">
            <li role="presentation" class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true"
                   aria-expanded="false">
                    Пользователи <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="/user">Создать</a></li>
                    <li><a href="/userList">Все пользователи</a></li>
                </ul>
            </li>
            <li role="presentation" class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true"
                   aria-expanded="false">
                    Дисциплины <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="/discipline/get">Создать дисциплину</a></li>
                    <li><a href="/discipline/list">Список дисциплин</a></li>
                </ul>
            </li>
            <li><a href="/logout">Выход</a></li>
        </ul>
    </div>
</div>