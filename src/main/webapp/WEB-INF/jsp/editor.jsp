<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Редактор Курса</title>
    <!-- Include Font Awesome. -->
    <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet"
          type="text/css"/>

    <!-- Include Editor style. -->
    <link href="../css/froala_editor.min.css" rel="stylesheet" type="text/css"/>
    <link href="../css/froala_style.min.css" rel="stylesheet" type="text/css"/>

    <!-- Include Code Mirror style -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.3.0/codemirror.min.css">

    <!-- Include Editor Plugins style. -->
    <link rel="stylesheet" href="/css/plugins/char_counter.css">
    <link rel="stylesheet" href="/css/plugins/code_view.css">
    <link rel="stylesheet" href="/css/plugins/colors.css">
    <link rel="stylesheet" href="/css/plugins/emoticons.css">
    <link rel="stylesheet" href="/css/plugins/file.css">
    <link rel="stylesheet" href="/css/plugins/fullscreen.css">
    <link rel="stylesheet" href="/css/plugins/image.css">
    <link rel="stylesheet" href="/css/plugins/image_manager.css">
    <link rel="stylesheet" href="/css/plugins/line_breaker.css">
    <link rel="stylesheet" href="/css/plugins/quick_insert.css">
    <link rel="stylesheet" href="/css/plugins/table.css">
    <link rel="stylesheet" href="/css/plugins/video.css">


    <script language="javascript" src="/js/jquery.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script language="javascript" src="/js/bootstrap-dropdown.js" type="text/javascript"></script>
    <link href="/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <%--<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>--%>

    <!-- Include JS files. -->
    <script type="text/javascript" src="/js/froala_editor.min.js"></script>

    <!-- Include Code Mirror. -->
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.3.0/codemirror.min.js"></script>
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.3.0/mode/xml/xml.min.js"></script>

    <!-- Include Plugins. -->
    <script type="text/javascript" src="/js/plugins/align.min.js"></script>
    <script type="text/javascript" src="/js/plugins/char_counter.min.js"></script>
    <script type="text/javascript" src="/js/plugins/code_beautifier.min.js"></script>
    <script type="text/javascript" src="/js/plugins/code_view.min.js"></script>
    <script type="text/javascript" src="/js/plugins/colors.min.js"></script>
    <script type="text/javascript" src="/js/plugins/emoticons.min.js"></script>
    <script type="text/javascript" src="/js/plugins/entities.min.js"></script>
    <script type="text/javascript" src="/js/plugins/file.min.js"></script>
    <script type="text/javascript" src="/js/plugins/font_family.min.js"></script>
    <script type="text/javascript" src="/js/plugins/font_size.min.js"></script>
    <script type="text/javascript" src="/js/plugins/fullscreen.min.js"></script>
    <script type="text/javascript" src="/js/plugins/image.min.js"></script>
    <script type="text/javascript" src="/js/plugins/image_manager.min.js"></script>
    <script type="text/javascript" src="/js/plugins/inline_style.min.js"></script>
    <script type="text/javascript" src="/js/plugins/line_breaker.min.js"></script>
    <script type="text/javascript" src="/js/plugins/link.min.js"></script>
    <script type="text/javascript" src="/js/plugins/lists.min.js"></script>
    <script type="text/javascript" src="/js/plugins/paragraph_format.min.js"></script>
    <script type="text/javascript" src="/js/plugins/paragraph_style.min.js"></script>
    <script type="text/javascript" src="/js/plugins/quick_insert.min.js"></script>
    <script type="text/javascript" src="/js/plugins/quote.min.js"></script>
    <script type="text/javascript" src="/js/plugins/table.min.js"></script>
    <script type="text/javascript" src="/js/plugins/save.min.js"></script>
    <script type="text/javascript" src="/js/plugins/url.min.js"></script>
    <script type="text/javascript" src="/js/plugins/video.min.js"></script>

    <!-- Include Language file if we want to use it. -->
    <script type="text/javascript" src="/js/languages/ru.js"></script>
    <script type="text/javascript" src="/js/editor.js"></script>

</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-2">
            <c:if test="${CURRENT_USER_ID!= null}">
                <jsp:include page="includes/menu.jsp"/>
            </c:if>
        </div>
        <div class="col-sm-2">
            <c:if test="${CURRENT_USER_ID!= null}">
                <jsp:include page="includes/course-tree.jsp"/>
            </c:if>
        </div>
        <div class="col-sm-8">
            <input type="text" id="nameVal" value="${content.contentName}">
            <legend>Редактирование ${content.type} - ${content.contentName}</legend>
            <textarea id="edit" value="${content.body}"></textarea>
            <input type="hidden" id="id" value="${content.id}">
            <input type="hidden" id="courseId" value="${content.courseId}">
            <input type="hidden" id="type" value="${content.type}">
            <div class="control form-row">
                <button type="button" id="saveBTN" class="btn btn-primary">Сохранить</button>
                <a class="btn btn-danger" href="/discipline/delete?id=${discipline.disciplineId}">Удалить</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>