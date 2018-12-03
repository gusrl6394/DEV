<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="utf-8" session="false"%>
<!doctype html>
<html>
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet">
    <title>Hello, world!</title>
    <!-- JS -->
  </head>
  <body class="container">
    <div class="jumbotron">
        <h1>Hello, world!</h1>
        <p>The time on the server is ${serverTime}.</p>
    </div>
  </body>
  <meta http-equiv="Refresh" Content="1; URL=testhome">
</html>