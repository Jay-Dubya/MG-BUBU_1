<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Navbar Template for Bootstrap</title>
        <!-- Bootstrap core CSS -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" />
        <!-- Custom styles for this template -->

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
                        <a class="navbar-brand" href="/burberry/"><b class="text-info">Burberry</b></a>
                    </div>
                    <div id="navbar" class="navbar-collapse collapse">
                        <ul class="nav navbar-nav">
                            <li >
                                <a href="/burberry/">Home</a>
                            </li>
                            <li class="active">
                                <a href="/burberry/addshoe" class="active">Add shoe</a>
                            </li>
                            <li>
                                <a href="/burberry/findshoe">Find shoe</a>
                            </li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li class="active"></li>
                        </ul>
                    </div>
                    <!--/.nav-collapse -->
                </div>
                <!--/.container-fluid -->
            </nav>
            <!-- Main component for a primary marketing message or call to action -->
            <form:form action="saveshoe" method="get">
            <div class="jumbotron">
                <h1 class="text-success"><b>Shoe image management</b></h1>
                <p>Maintain images for use in production of Burberry shoe labels</p>
                <p><strong class="text-capitalize">shoe reference</strong></p>
                <p><form:input path="imagecode" /></p>
                <p><strong class="text-capitalize">Shoe image file</strong></p>
                <p><form:input path="imagename" /></p>
                <p></p>
                <p></p>
                <button type="submit" class="btn btn-primary">Submit</button>
                <p></p>
            </div>
        </form:form>
            <h3>Added reference</h3>
            <table class="table table-striped">
                <tr><td>Code</td><td>Image</td></tr>
                    <tr>
                        <td>${shoe.imagecode}</td><td>${shoe.imagename}</td>
                    </tr>
            </table>
        </div>
    </body>
</html>
