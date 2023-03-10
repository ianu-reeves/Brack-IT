<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form:form action="/authenticate" method="POST">
    <p>
        Username: <input type="text" name="username">
    </p>
    <p>
        Password: <input type="password" name="password">
    </p>

    <input type="submit" value="Log in">


</form:form>
</body>
</html>
