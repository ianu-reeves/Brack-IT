<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create a New Tournament</title>
</head>
<body>
    <a href="/login">Log In</a>
    <h3>Tournament Details</h3>
    <form:form action="/tournaments/process" modelAttribute="tournamentForm">
        <form:errors path="name" cssClass="errors" /><br>
        Tournament name: <form:input path="name" /><br><br>

<%--        <form:errors path="namesOfCompetitors" cssClass="errors" /><br>--%>
<%--        Please enter each competitor on a separate line: <form:textarea path="namesOfCompetitors" /><br><br>--%>

        <form:errors path="competitorCount" cssClass="errors" /><br>
        Number of competitors: <form:input path="competitorCount" /><br><br>
        <input type="submit" value="Create tournament">
    </form:form>
</body>
</html>
