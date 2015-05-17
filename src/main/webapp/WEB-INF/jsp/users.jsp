<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>User Managment System</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="includes/header.jsp"/>
<table class="table">
    <thead>
    <tr>
        <th>Login</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Age</th>
        <th>Action</th>
    </tr>
    </thead>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.login} </td>
            <td>${user.firstName} </td>
            <td> ${user.lastName} </td>
            <td> ${user.age}</td>
            <td><a href="/user?userId=${user.userId}"><i class="icon-edit"></i></a>
                <a href="/delete?userId=${user.userId}"><i class="icon-trash"></i></a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>