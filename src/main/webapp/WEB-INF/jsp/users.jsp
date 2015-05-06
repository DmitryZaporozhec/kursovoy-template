<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>Users</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<c:forEach var="user" items="${users}">
    <div class="userRow">
        <div class="left">First Name: ${user.firstName} </div>
        <div class="left">Last Name: ${user.lastName} </div>
        <div class="left">Age: ${user.age} </div>
        <div class="left"><a href="/user?userId=${user.userId}">Edit</a></div>
        <div class="clear"></div>
    </div>
</c:forEach>
</body>
</html>