<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
    <title>Users</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
    <c:forEach var="user" items="${users}">
    <div class="userRow">
        <div class="left">${user.firstName}</div>
        <div class="left">${user.lastName}</div>
        <div class="left">${user.age}</div>
        <div class="clear"></div>
    </div>
    </c:forEach>
</body>
</html>