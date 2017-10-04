<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <spring:url value="/resources/css/theme.css" var="themeCSS" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <title>Search</title>
</head>
<body>
<h1>Results</h1>
    <c:if test="${not empty list}">
        <table>
            <tr><td>File</td><td>Field</td><td>Value</td><td>Node count</td></tr>
            <c:forEach var="val" items="${list}" varStatus="status">
                <tr>
                    <td>${val.filename}</td><td>${val.field}</td><td>${val.value}</td><td>Nodes found = ${val.nodes.size()}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

</body>
</html>

