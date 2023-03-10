    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BrackIT | Create or Edit an Event</title>
</head>
<body>
    <div class="title">
        <a href="/tournaments/create">Create Tournament</a>
        <h2>Event Settings</h2><br>
    </div>
    <form:form action="/events/process" modelAttribute="eventForm">
        <div class="form-group">
            <div>
                <form:errors cssClass="error" path="zoneId" />
            </div>

            <label for="zone-id">Event Timezone: </label>
            <form:select id="zone-id" path="zoneId">
                <form:option value="" label="---SELECT---" />
                <form:options items="${timezones}" />
            </form:select><br>
            <small>The timezone where the event will take place. Not necessarily your own.</small>
        </div>

        <div class="form-group">
            <div>
                <form:errors cssClass="error" path="regions" />
            </div>
            <label for="region-select">Regions event is open to:</label><br>
<%--                <c:forEach items="${availableRegions}" var="region">--%>
<%--                    <form:checkbox path="regions" value="${region.shortName}" label="${region.fullName}" />--%>
<%--                </c:forEach>--%>
                <form:checkboxes id="region-select" path="regions" items="${availableRegions}" itemValue="shortName" itemLabel="fullName" delimiter="<br>" /><br>

        </div>

        <%-- EVENT START TIMESTAMP --%>
        <div class="form-group">
            <div class="error">
                <form:errors cssClass="error" path="startDate" />
            </div>
            <label for="event-date">Event date: </label>
            <form:input id="event-date" path="startDate" type="date" />
        </div>


        <div class="form-row">
            <div class="form-group">
                <div>
                    <form:errors cssClass="error" path="startHour" />
                    <form:errors cssClass="error" path="startMinute" />
                </div>
                <label for="start-hour">Event start time: </label>
                <form:select id="start-hour" path="startHour">
                    <c:forEach var="hour" begin="0" end="23">
                        <form:option value="${hour}">
                            <fmt:formatNumber value="${hour}" pattern="00" />
                        </form:option>
                    </c:forEach>
                </form:select>
                :
                <form:select id="start-minute" path="startMinute">
                    <c:forEach var="minute" begin="0" end="59" step="5">
                        <form:option value="${minute}">
                            <fmt:formatNumber value="${minute}" pattern="00" />
                        </form:option>
                    </c:forEach>
                </form:select>
            </div>
        </div>

        <%-- ENROLLMENT TIMESTAMP --%>
        <div class="form-group">
            <div>
                <form:errors cssClass="error" path="enrollmentDeadlineDate" />
            </div>
            <label for="enrollment-date">Enrollment deadline date: </label>
            <form:input id="enrollment-date" path="enrollmentDeadlineDate" type="date" />
        </div>

        <div class="form-row">
            <div class="form-group">
                <div>
                    <form:errors cssClass="error" path="enrollmentHour" />
                    <form:errors cssClass="error" path="enrollmentMinute" />
                </div>
                <label for="enrollment-hour">Enrollment deadline time: </label>
                <form:select id="enrollment-hour" path="enrollmentHour">
                    <c:forEach var="hour" begin="0" end="23">
                        <form:option value="${hour}">
                            <fmt:formatNumber value="${hour}" pattern="00" />
                        </form:option>
                    </c:forEach>
                </form:select>
                :
                <form:select id="enrollment-minute" path="enrollmentMinute">
                    <c:forEach var="minute" begin="0" end="59" step="5">
                        <form:option value="${minute}">
                            <fmt:formatNumber value="${minute}" pattern="00" />
                        </form:option>
                    </c:forEach>
                </form:select>
            </div>
        </div>
        <div class="form-group">
            <label for="event-type">Event type:</label>
            <form:select id="event-type" path="eventType">
                <form:options items="${eventTypes}" />
            </form:select>
        </div>

        <div class="title">
            <h2>Tournament Settings</h2><br>

            <div class="form-group">
                <div>
                    <form:errors cssClass="error" path="name" />
                </div>
                <label for="event-title">Name: </label>
                <form:input id="event-title" path="name" placeholder="Enter a name for the tournament" />
            </div>

            <div class="form-group">
                <div>
                    <form:errors cssClass="error" path="game" />
                </div>
                <label for="event-title">Game: </label>
                <form:input id="event-title" path="game" placeholder="Enter the name of the game that will be played" />
            </div>

            <div class="form-group">
                <div>
                    <form:errors cssClass="error" path="maxCapacity" />
                </div>
                <label for="max-capacity">Maximum number of participants: </label>
                <small>max 256</small>
                <form:input id="max-capacity" path="maxCapacity" />
            </div>

            <div class="form-group">
                <div>
                    <form:errors cssClass="error" path="firstTo" />
                </div>
                <label for="first-to">Number of sets a player must take to win the match: </label>
                <small>max 7</small>
                <form:input id="first-to" path="firstTo" />
            </div>
        </div>

        <input class="btn-approve" type="submit" value="Save and continue" />
    </form:form>
</body>
</html>
