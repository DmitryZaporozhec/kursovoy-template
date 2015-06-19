<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Компьютерный тест</title>
    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script language="javascript" src="/js/jquery.js" type="text/javascript"></script>
    <script language="javascript" src="/js/path-auth.js" type="text/javascript"></script>
</head>

<body>
<div class="container container-login">
    <form id="doSmsAuth" class="form-sms-auth">
        <h4>Введите ответ на секретный вопрос</h4>
        <h4>Какой Ваш любимый цвет?</h4>
        <h5><span id="error" class="label label-important"></span></h5>
        <input id="userId" type="hidden" value="${userId}"/>
        <input type="text" id="answer" class="form-control" placeholder="Ответ" required autofocus>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Отправить</button>
    </form>
</div>
</body>
</html>
