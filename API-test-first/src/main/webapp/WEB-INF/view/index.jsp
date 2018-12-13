<%--
  Created by IntelliJ IDEA.
  User: dev
  Date: 2018-12-13
  Time: 오후 4:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>INDEX</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</head>
<body style="height:1500px">
<div class="container-fluid">
    <br>
    <h3>Sticky Navbar</h3>
    <p>A sticky navigation bar stays fixed at the top of the page when you scroll past it.</p>
    <p>Scroll this page to see the effect. <strong>Note:</strong> sticky-top does not work in IE11 and earlier.</p>
</div>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark sticky-top">
    <a class="navbar-brand" href="/">Home</a>
    <ul class="navbar-nav">
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/navermap"/>">네이버 Map</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/naverlogin"/>">네이버 로그인</a>
        </li>
    </ul>
</nav>
<div class="container-fluid"><br>
    <p>오픈소스에 개인정보를 올리지 않기 위해 Client ID, Secert Key 등 ### 으로 모두 처리되어서 API가 동작이 되지 않습니다.</p>
</div>
</body>
</html>
