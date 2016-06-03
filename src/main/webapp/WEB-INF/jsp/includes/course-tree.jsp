<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<div id="course-tree" class="navbar navbar-inverse">
    <ul class="nav">
        <li><a href="/course/get?id=${course.id}${content.courseId}"><span class="glyphicon glyphicon-edit"> Описание курса</span></a>
        </li>
        <c:forEach var="i" items="${menu}">
            <li><a href="/editor/get?type=LECTURE&parentId=${content.courseId}${course.id}&id=${i.id}"><span
                    class="glyphicon glyphicon-paperclip"> ${i.contentName}</span></a></li>
        </c:forEach>
        <li><a href="/editor/get?type=LECTURE&parentId=${content.courseId}${course.id}"><span
                class="glyphicon glyphicon-plus"> Добавить лекцию</span></a>
        </li>
    </ul>
</div>