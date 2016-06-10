<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>Курсы</title>
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
            <h3>Список <c:if  test="${'Y' eq my}"> моих</c:if> курсов</h3>
            <table class="table">
                <thead>
                <tr>
                    <th>Название</th>
                    <th>Описание</th>
                    <c:if test="${not('Y'  eq my)}">
                        <th>Создатель</th>
                    </c:if>
                    <th>Дисциплина</th>
                    <th>Обновлено</th>
                    <th>Действия</th>
                </tr>
                </thead>
                <c:forEach var="course" items="${courses}">
                    <tr>
                        <td>${course.name} </td>
                        <td>${course.description} </td>
                        <c:if test="${not('Y'  eq my)}">
                            <td>${course.userDisplayName} </td>
                        </c:if>
                        <td>${course.disciplineName} </td>
                        <td><fmt:formatDate pattern="yyyy MM dd HH:mm"
                                            value="${course.createDate}"/></td>
                        <c:choose>
                            <c:when test="${'Y' eq my}">
                                <td>
                                    <a href="#"><i
                                            class="glyphicon glyphicon-picture"></i></a>
                                    <%--<a href="/course/delete-from-my/${course.id}"><i--%>
                                            <%--class="glyphicon glyphicon-remove"></i></a>--%>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    <a href="#"><i
                                            class="glyphicon glyphicon-picture"></i></a>
                                    <c:if test="${CURRENT_USER_TYPE eq 'ADMIN' or CURRENT_USER_TYPE eq  'LECTURER' or CURRENT_USER_TYPE eq  'TUTOR'}">
                                        <a href="/course/get?id=${course.id}"><i
                                                class="glyphicon glyphicon-edit"></i></a>
                                    </c:if>
                                    <c:if test="${CURRENT_USER_TYPE eq 'ADMIN'}">
                                        <a href="/course/delete?id=${course.id}"><i
                                                class="glyphicon glyphicon-remove"></i></a>
                                    </c:if>
                                    <%--<c:if test="${CURRENT_USER_TYPE eq 'STUDENT'}">--%>
                                        <%--<a href="/course/add-to-my/${course.id}"><i--%>
                                                <%--class="glyphicon glyphicon-check"></i></a>--%>
                                    <%--</c:if>--%>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>