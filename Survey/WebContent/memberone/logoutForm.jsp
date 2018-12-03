<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% session.invalidate(); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그아웃</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<style>
a{text-decoration: none;}
</style>
<meta charset="UTF-8" http-equiv="Refresh" content="5;url=login.jsp">
</head>
<body>
성공적으로 로그아웃 되었습니다.<br/><br/>
5초후 자동으로 메인페이지로 이동됩니다.<br/><br/>
<a href="login.jsp">메인 페이지로 이동</a>
</body>
</html>