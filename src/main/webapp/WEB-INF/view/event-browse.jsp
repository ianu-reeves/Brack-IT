<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Browse events | BrackIT</title>
</head>
    <body>
        <div>
            <form:form action="browse" method="GET">
                <label for="sort-method">Sort by:</label>
                <select id="sort-method" name="sort-method">
                    <c:forEach items="${sortMethods}" var="method">
                        <option value="${method.columnName}">${method.readableName}</option>
                    </c:forEach>
                </select>
                <label for="sort-order">Show results in </label>
                <select id="sort-order" name="sort-order">
                    <c:forEach items="${sortOrders}" var="order">
                        <option value="${order.abbreviation}">${order.fullName}</option>
                    </c:forEach>
                </select>
            </form:form>
        </div>
    </body>
</html>
