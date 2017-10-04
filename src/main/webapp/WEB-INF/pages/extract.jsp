<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Burberry Order Extraction</title>
</head>
<spring:url value="/resources/js/jquery-1.11.3.min.js" var="jquery"/>
<spring:url value="/resources/js/bootstrap.min.js" var="bootstrap"/>
<spring:url value="/resources/js/rsvp-3.1.0.min.js" var="rsvp"/>
<spring:url value="/resources/js/sha-256.min.js" var="sha"/>
<script src="${jquery}"></script>
<script src="${bootstrap}"></script>
<script src="${rsvp}"></script>
<script src="${sha}"></script>
<body>
<div align="center">
Burberry Data Extraction
<form:form action="runextract" method="get">
    <div class="container-fluid">
        <table class="table">
            <tr>
                <td>Input folder <form:input path="inputFolder"/></td>
            </tr>
            <tr>
                <td>Output folder <form:input path="outputFolder"/></td>
            </tr>
            <tr>
                <td>Field name : <form:input path="field1"/></td>
            </tr>
            <tr>
                <td>Value</td>
                <td>Replace</td>
            </tr>
            <tr>
                <td><form:input path="value1"/></td>
                <td><form:input path="newvalue1"/></td>
            </tr>
            <tr>
                <td><form:input path="value2"/></td>
                <td><form:input path="newvalue2"/></td>
            </tr>
            <tr>
                <td><form:input path="value3"/></td>
                <td><form:input path="newvalue3"/></td>
            </tr>
            <tr>
                <td><form:input path="value4"/></td>
                <td><form:input path="newvalue4"/></td>
            </tr>
            <tr>
                <td><form:input path="value5"/></td>
                <td><form:input path="newvalue5"/></td>
            </tr>
            <tr>
                <td><form:input path="value6"/></td>
                <td><form:input path="newvalue6"/></td>
            </tr>
            <tr>
                <td><form:input path="value7"/></td>
                <td><form:input path="newvalue7"/></td>
            </tr>
            <tr>
                <td><form:input path="value8"/></td>
                <td><form:input path="newvalue8"/></td>
            </tr>
            <tr>
                <td><form:input path="value9"/></td>
                <td><form:input path="newvalue9"/></td>
            </tr>
            <tr>
                <td><form:input path="value10"/></td>
                <td><form:input path="newvalue10"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Submit"/></td>
            </tr>
        </table>
    </div>
</form:form>
</div>
</body>
</html>
