<%@page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${!('Y' eq reader )}">
    <div id="menu" class="navbar">
        <ul class="nav">
            <li><a href="/index"><span class="glyphicon glyphicon-home"></span><span class="span-text">На главную</span></a></li>
            <c:if test="${CURRENT_USER_TYPE eq 'ADMIN' or CURRENT_USER_TYPE eq  'LECTURER' or CURRENT_USER_TYPE eq  'TUTOR'}">
                <li role="presentation" class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true"
                       aria-expanded="false"><span class="glyphicon glyphicon-user">
                </span><span class="span-text">Пользователи</span><span class="caret"></span>
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
                </span><span class="span-text">Группы</span><span class="caret"></span>
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
                       aria-expanded="false"><span class="glyphicon glyphicon-duplicate"></span><span class="span-text">Дисциплины</span>
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
                   aria-expanded="false"><span class="glyphicon glyphicon-blackboard"></span><span class="span-text">Курсы</span><span
                        class="caret"></span>
                </a>
                <ul class="dropdown-menu"><c:if
                        test="${CURRENT_USER_TYPE eq 'ADMIN' or CURRENT_USER_TYPE eq  'LECTURER' or CURRENT_USER_TYPE eq  'TUTOR'}">
                    <li><a href="/course/get">Создать курс</a></li>
                </c:if>
                    <li><a href="/course/list">Список курсов</a></li>
                    <c:if test="${CURRENT_USER_TYPE eq 'STUDENT'}">
                        <li><a href="/course/my/list">Мои курсы</a></li>
                    </c:if>

                </ul>
            </li>
            <li><a href="/user?userId=${CURRENT_USER_ID}"><span
                    class="glyphicon glyphicon-briefcase"> </span><span class="span-text">Профиль</span></a>
            </li>
            <li><a href="/logout"><span class="glyphicon glyphicon-log-out"> </span><span class="span-text">Выход</span></a></li>

        </ul>
    </div>
</c:if>