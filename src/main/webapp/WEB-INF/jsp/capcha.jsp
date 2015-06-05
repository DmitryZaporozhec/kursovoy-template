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
        <h2>Please Enter the code from Image</h2>
        <input id="userId" type="hidden" value="${userId}"/>
        <input id="captcha" type="hidden" value="${captcha}"/>
        <img src="data:image/png;base64,${image}">
        <input type="text" id="inputCaptcha" class="form-control" placeholder="Code" required autofocus>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form>
</div>
</body>
</html>
