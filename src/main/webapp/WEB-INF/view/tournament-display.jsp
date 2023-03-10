<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html style="min-height: 100%; background-image: linear-gradient(to bottom right, #5c818a, #0e0c21)">
<head>
    <title>Tournaments | ${tournament.tournament.name}</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div style="overflow: auto; width: 100%; height: 50%">
<svg width="${tournament.displayWidth}" height="${tournament.displayHeight}">

<!-- match display -->
<g>
<c:forEach items="${tournament.displayMatches}" var="match">
    <g transform="translate(${match.xOffset}, ${match.yOffset})">
        <g transform="translate(25, 0)">
            <rect class="match-background" height="50" width="200" rx="5"></rect>
            <path class="match-competitor-seed-bg" d="M 0 25 h 25 v 25 h -25 Z"></path>
            <path class="match-competitor-seed-bg" d="M 0 0 h 25 v 25 h -25 Z"></path>
            <text class="match-competitor-name" x="30" y="${tournament.TOTAL_HEIGHT/4}" text-anchor="start">
                ${match.match.competitor1.name}
            </text>
            <text class="match-competitor-name" x="30" y="${(tournament.TOTAL_HEIGHT/4)*3}" text-anchor="start">
                ${match.match.competitor2.name}
            </text>
        </g>
        <text class="match-number" x="15" y="${tournament.TOTAL_HEIGHT/2}" text-anchor="middle">
               ${match.match.matchNumber}
        </text>
    </g>
</c:forEach>
</g>

<!-- path display -->
<g>
<c:forEach items="${tournament.bracketPaths}" var="path">
    <g>
        <path class="bracket-path" d="${path}"></path>
    </g>
</c:forEach>
</g>
</svg>
</div>
</body>
</html>
