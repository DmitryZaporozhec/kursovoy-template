<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>User</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script language="javascript" src="/js/jquery.js" type="text/javascript"></script>
    <script language="javascript" src="/js/user.js" type="text/javascript"></script>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<c:if test="${CURRENT_USER_ID!= null}">
    <jsp:include page="includes/header.jsp"/>
</c:if>
<form id="edit-user-form">
    <fieldset>
        <legend>Edit User ${user.firstName} ${user.lastName}</legend>

        <div class="alert alert-success" style="display: none;">
            <strong>Saved!</strong> User saved successfully!
        </div>

        <div class="alert alert-error" style="display: none;">
            <strong>Error!</strong> User hasn't saved!
        </div>


        <label>Login</label>
        <input id="login" type="text" value="${user.login}">
        <input id="userId" type="hidden" value="${user.id}">
        <label>First Name</label>
        <input id="name" type="text" value="${user.firstName}">
        <label>Last Name</label>
        <input id="lastName" type="text" value="${user.lastName}">
        <label>Age</label>
        <input id="age" type="number" value="${user.age}">
        <label>Password</label>
        <input id="password" type="password" value="${user.password}">
        <button type="submit" class="btn">Submit</button>
        <a class="btn btn-danger" href="/delete?userId=${user.id}">Delete</a>
    </fieldset>
</form>

</body>
</html>