<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	String loginID = (String)session.getAttribute("loginID"); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/mainstyle.css" rel="stylesheet" type="text/css">
</head>
<body>
<section>
	<article>
	<% if(loginID != null){ %>
	<table>
		<tr><td colspan="3"><%= loginID %>님 환영합니다.</td></tr><tr>
		<tr>
			<td class="infomenu"><a href="../boardone/surveycreateinfoForm.jsp">설문조사 폼생성</a></td>
			<td class="infomenu"><a href="../boardone/list.jsp">설문조사 리스트</a></td>			
			<td class="infomenu"><a href="modifyForm.jsp">정보수정</a></td>
			<!-- <td class="infomenu"><a href="deleteForm.jsp">회원탈퇴</a></td> -->
			<td class="infomenu"><a href="logoutForm.jsp">로그아웃</a></td>
		</tr>
	</table>
	<% }else{ %>
	<script type="text/javascript">
		alert("정상적인 접근이 아닙니다. 로그인 해 주세요.");
		location.href="login.jsp";
	</script>
	<% } %>
	</article>
</section>
</body>
</html>
<!-- 로그인부분과 설문조사 article부분 추가하기 -->