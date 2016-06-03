<%@page pageEncoding="UTF-8" %>
<div class="navbar">
    <ul class="nav">
        <li><a href="/index"><span class="glyphicon glyphicon-home"> Домой</span></a></li>
        <li role="presentation" class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true"
               aria-expanded="false"><span class="glyphicon glyphicon-user">
                Пользователи</span><span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <li><a href="/user">Создать</a></li>
                <li><a href="/userList">Все пользователи</a></li>
            </ul>
        </li>
        <li role="presentation" class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true"
               aria-expanded="false"><span class="glyphicon glyphicon-duplicate"> Дисциплины</span>
                <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <li><a href="/discipline/get">Создать дисциплину</a></li>
                <li><a href="/discipline/list">Список дисциплин</a></li>
            </ul>
        </li>
        <li role="presentation" class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true"
               aria-expanded="false"><span class="glyphicon glyphicon-blackboard"> Курсы</span><span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <li><a href="/course/get">Создать курс</a></li>
                <li><a href="/course/list">Список курсов</a></li>
            </ul>
        </li>
        <li><a href="/user?userId=${CURRENT_USER_ID}"><span class="glyphicon glyphicon-briefcase"> Профиль</span></a></li>
        <li><a href="/logout"><span class="glyphicon glyphicon-log-out"> Выход</span></a></li>

    </ul>
</div>