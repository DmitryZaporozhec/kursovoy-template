<%@page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="menu" class="navbar">
    <ul class="nav">
        <li><a href="/index"><span class="glyphicon glyphicon-home"> Домой</span></a></li>
        <c:if test="${CURRENT_USER_TYPE eq 'ADMIN' or CURRENT_USER_TYPE eq  'LECTURER' or CURRENT_USER_TYPE eq  'TUTOR'}">
            <li role="presentation" class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true"
                   aria-expanded="false"><span class="glyphicon glyphicon-user">
                Пользователи</span><span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <c:if test="${CURRENT_USER_TYPE eq 'ADMIN'}">
                        <li><a href="/user">Создать</a></li>
                    </c:if>
                    <li><a href="/userList">Все пользователи</a></li>
                    <c:if test="${CURRENT_USER_TYPE eq 'ADMIN'}">
                        <li class="divider"></li>
                        <li><a href="#">Импортировать</a></li>
                    </c:if>
                </ul>
            </li>
        </c:if>
        <c:if test="${CURRENT_USER_TYPE eq 'ADMIN' or CURRENT_USER_TYPE eq  'LECTURER' or CURRENT_USER_TYPE eq  'TUTOR'}">
            <li role="presentation" class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true"
                   aria-expanded="false"><span class="glyphicon glyphicon glyphicon-th">
                Группы</span><span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <c:if test="${CURRENT_USER_TYPE eq 'ADMIN'}">
                        <li><a href="/group/get">Создать</a></li>
                    </c:if>
                    <li><a href="/group/list">Все группы</a></li>
                    <c:if test="${CURRENT_USER_TYPE eq 'ADMIN'}">
                        <li class="divider"></li>
                        <li><a href="#">Импортировать</a></li>
                    </c:if>
                </ul>
            </li>
        </c:if>
        <c:if test="${CURRENT_USER_TYPE eq 'ADMIN' or CURRENT_USER_TYPE eq  'LECTURER' or CURRENT_USER_TYPE eq  'TUTOR'}">
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
        </c:if>
        <li role="presentation" class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true"
               aria-expanded="false"><span class="glyphicon glyphicon-blackboard"> Курсы</span><span
                    class="caret"></span>
            </a>
            <ul class="dropdown-menu"><c:if
                    test="${CURRENT_USER_TYPE eq 'ADMIN' or CURRENT_USER_TYPE eq  'LECTURER' or CURRENT_USER_TYPE eq  'TUTOR'}">
                <li><a href="/course/get">Создать курс</a></li>
            </c:if>
                <li><a href="/course/list">Список курсов</a></li>

            </ul>
        </li>
        <li><a href="/user?userId=${CURRENT_USER_ID}"><span class="glyphicon glyphicon-briefcase"> Профиль</span></a>
        </li>
        <li><a href="/logout"><span class="glyphicon glyphicon-log-out"> Выход</span></a></li>

    </ul>
</div>