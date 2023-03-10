<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h2>Register a new account</h2>
    <form:form action="/register/process" modelAttribute="registrationForm">
        <h4>Account information</h4>
        <h5>Fields marked (*) are required</h5>
        <form:errors path="firstName" cssClass="error" /><br>
        First name: <form:input path="firstName"/><br>
        <form:errors path="lastName" cssClass="error" /><br>
        Last name: <form:input path="lastName"/><br><br>
        <c:if test="${error != null}">
            ${error}
        </c:if><br>
        <form:errors path="username" cssClass="error" /><br>
        Username (*): <form:input path="username"/><br>
        <form:errors path="password" cssClass="error" /><br>
        Password (*): <form:password path="password"/><br>
        <form:errors path="matchingPassword" cssClass="error" /><br>
        Confirm password (*): <form:password path="matchingPassword"/><br>
        <form:errors path="email" cssClass="error" /><br>
        Email (*): <form:input path="email"/><br>
        <form:errors path="region" cssClass="error" /><br>
        Region (*):
        <form:select path="region">
            <form:options items="${regions}" itemValue="shortName" itemLabel="fullName" />
        </form:select><br>
        <form:errors path="zoneId" cssClass="error" />
        <form:select path="zoneId">
            <c:forEach items="${timezones}" var="timezone">
                <form:option value="${timezone.key}" label="${timezone.key} (GMT ${timezone.value})"/>
            </c:forEach>
        </form:select><br>

        <input type="submit" value="Register">
    </form:form>
</body>
</html>
