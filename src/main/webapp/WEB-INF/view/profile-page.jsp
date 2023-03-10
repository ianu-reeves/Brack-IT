<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: snack
  Date: 6/20/22
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${user.username}'s Profile | BrackIt</title>
</head>
<body>
<h2>${user.username}</h2><br><br>
<h3>Tournaments</h3>
<table>
    <tr>
        <th>Name</th>
        <th>Owner</th>
        <th>Date</th>
        <th>Game</th>
        <th>Character</th>
    </tr>
<c:forEach items="${tournaments}" var="tournament">
    <tr>
        <td><a href="/tournament/show/${tournament.id}">${tournament.name}</a></td>
        <td><a href="/user/${tournament.creator.username}">${tournament.creator.username}</a></td>
    </tr>
</c:forEach>
</table>
</body>
</html>
