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
    <title>Signin</title>
    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/signin.css">
    <script language="javascript" src="/js/jquery.js" type="text/javascript"></script>
    <script language="javascript" src="/js/login.js" type="text/javascript"></script>
</head>

<body>
<div class="container container-login">
    <form id="doLogin" class="form-signin">
        <h2>Вход</h2>
        <h4><span id="error" class="label label-danger"></span></h4>
        <label for="inputLogin" class="sr-only">Имя пользоваетля</label>
        <input type="text" id="inputLogin" class="form-control" placeholder="Имя пользоваетля" required autofocus>
        <label for="inputPassword" class="sr-only">Пароль</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="Пароль" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Вход</button>
        <a href="/selfRegistration" class="btn btn btn-inverse btn-block">Зарегистрироваться</a>
    </form>
</div>
</body>
</html>
