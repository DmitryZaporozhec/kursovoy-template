<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>User</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script language="javascript" src="/js/jquery.js" type="text/javascript"></script>
    <script language="javascript" src="/js/user.js" type="text/javascript"></script>
</head>
<body>
<input id="name" type="text" value="${user.firstName}">
<input id="lastName" type="text" value="${user.lastName}">
<input id="age" type="number" value="${user.age}">
<input id="submit" type="button" value="Submit">
</body>
</html>