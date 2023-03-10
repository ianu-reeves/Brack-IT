<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${event.tournament.name} | BrackIT</title>
</head>
    <body>
        <h2 class="error">${info}</h2>
        <h2>${event.tournament.name}</h2>
        Event begins at ${event.startTime}<br>
        <c:choose>
            <c:when test="${canApply}">
                <c:url var="link" value="/events/apply" />
                <c:set var="buttonText" value="Submit application" />
            </c:when>
            <c:when test="${hasApplied}">
                <c:url var="link" value="/events/unapply" />
                <c:set var="buttonText" value="Withdraw application" />
            </c:when>
        </c:choose>
        <form:form modelAttribute="applicationForm" method="POST" action="${link}">
            <form:hidden path="eventId" value="${event.id}" />
            <form:hidden path="userId" value="${user.id}" />
            <input type="submit" value="${buttonText}">
        </form:form>
    </body>
</html>
