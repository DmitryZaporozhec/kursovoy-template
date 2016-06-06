<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<div id="course-tree" class="navbar navbar-inverse">
    <ul class="nav">
        <li><a href="/course/get?id=${courseId}"><span class="glyphicon glyphicon-edit"> Описание курса</span></a>
        </li>
        <c:forEach var="i" items="${menu}">
        <li role="presentation" class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true"
               aria-expanded="false"><span class="glyphicon glyphicon-paperclip">
                Модуль ${i.displaOrder}</span><span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <c:forEach var="ia" items="${i.content}">
                    <li title="${ia.type}"><a href="/editor/get?type=LECTURE&parentId=${i.id}&id=${ia.id}"><span
                            class="glyphicon
                                <c:if test="${'LECTURE' eq ia.type}"> glyphicon-file</c:if>
                                 <c:if test="${'TASK' eq ia.type}"> glyphicon glyphicon-tasks</c:if>
                                <c:if test="${'LITERATURE' eq ia.type}"> glyphicon-book</c:if>
                               <c:if test="${'PRACTICE' eq ia.type}"> glyphicon-list-alt</c:if>
                                <c:if test="${'LABORATORY' eq ia.type}"> glyphicon glyphicon-export</c:if>
                               <c:if test="${'WEBINAR' eq ia.type}"> glyphicon-volume-up</c:if>

                                "> ${ia.contentName}</span></a></li>
                </c:forEach>
                <li class="divider"></li>

                <li><a href="/editor/get?type=LECTURE&parentId=${i.id}"><span
                        class="glyphicon glyphicon-plus"> Добавить лекцию</span></a>
                </li>
                <li><a href="/editor/get?type=TASK&parentId=${i.id}"><span
                        class="glyphicon glyphicon-plus"> Добавить задание</span></a>
                </li>
                <li><a href="/editor/get?type=LITERATURE&parentId=${i.id}"><span
                        class="glyphicon glyphicon-plus"> Добавить литературу</span></a>
                </li>
                <li><a href="/editor/get?type=PRACTICE&parentId=${i.id}"><span
                        class="glyphicon glyphicon-plus"> Добавить практику</span></a>
                </li>
                <li><a href="/editor/get?type=LABORATORY&parentId=${i.id}"><span
                        class="glyphicon glyphicon-plus"> Добавить лабораторную работу</span></a>
                </li>
                <li><a href="/editor/get?type=WEBINAR&parentId=${i.id}"><span
                        class="glyphicon glyphicon-plus"> Добавить вебинар</span></a>
                </li>
                <li class="divider"></li>
                <li><a href="/course/deleteModule?id=${i.id}"><span
                        class="glyphicon glyphicon-trash"> Удалить модуль</span></a>
                </li>
            </ul>
            </c:forEach>
        <li><a href="/course/addModule?parentId=${courseId}"><span
                class="glyphicon glyphicon-plus"> Добавить модуль</span></a>
        </li>
    </ul>
</div>