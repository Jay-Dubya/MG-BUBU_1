<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Next - find shoe</title>
    <!-- Bootstrap core CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" />
    <!-- Custom styles for this template -->
    <!-- <link href="navbar.css" rel="stylesheet"> -->
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">
    <!-- Static navbar -->
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/next/"><b class="text-info">NEXT</b></a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li >
                        <a href="/next/">Home</a>
                    </li>
                    <li >
                        <a href="/next/addshoe" class="active">Add shoe</a>
                    </li>
                    <li class="active">
                        <a href="/next/findshoe">Find shoe</a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="active"></li>
                    <li></li>
                    <li></li>
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
        <!--/.container-fluid -->
    </nav>
    <!-- Main component for a primary marketing message or call to action -->
<form:form action="findshoe" method="get">
    <div class="jumbotron">
        <h1 class="text-success"><b>Shoe image management</b></h1>
        <p>Maintain images for use in production of NEXT shoe labels</p>
        <p><strong class="text-capitalize">Find shoe reference</strong></p>
        <p><form:input path="imagecode" /></p>
        <div class="radio-inline">
            <label><form:radiobutton path="search" value="reference"/>By reference</label>
        </div>
        <div class="radio-inline">
            <label><form:radiobutton path="search" value="image"/>By image name</label>
        </div>
        <p><button type="submit" class="btn btn-primary">Submit</button></p>
    </div>
</form:form>
    <div class="jumbotron">
        <h3>Search results for ${shoe.imagecode}</h3>
    <c:if test="${not empty list}">
        <table class="table table-striped">
            <tr><td>Code</td><td>Image</td></tr>
            <c:forEach var="value" items="${list}" varStatus="status">
                <tr>
                    <td>${value.imagecode}</td><td>${value.imagename}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
        <c:if test="${empty list and not empty shoe}">Cant find reference</c:if>
    </div>
</div>
<!-- /container -->
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<!-- <script src="assets/js/jquery.min.js"></script> -->
<!-- <script src="bootstrap/js/bootstrap.min.js"></script> -->
</body>
</html>

