<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script language="javascript" src="/js/jquery.js" type="text/javascript"></script>
    <script language="javascript" src="/js/sms-auth.js" type="text/javascript"></script>
</head>

<body>
<div class="container container-login">
    <form id="doSmsAuth" class="form-sms-auth">
        <h2>Please send SMS code that comes to your phone</h2>
        <h4><span id="error" class="label label-important"></span></h4>
        <input id="login" type="hidden" value="${login}"/>
        <label for="inputLogin" class="sr-only">Login: ${login}</label>
        <input type="text" id="inputLogin" class="form-control" placeholder="SMS Code" required autofocus>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form>
</div>
</body>
</html>
