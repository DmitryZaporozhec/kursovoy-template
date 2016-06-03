<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>${content.contentName}</title>
    <link href="/css/froala_style.min.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="fr-view">
    ${content.body}
</div>
</body>
</html>