<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String tag = request.getParameter("tag");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>설문조사 삭제 Form</title>
</head>
<body>
<section>
	<form method="post" action="surveydeleteProc.jsp" name="surveydeleteForm">
		<h3>정말 삭제하겠습니까?</h3>
		설무조사 태그 : <input type="text" name="tag" value="<%=tag%>" readonly><br>
		<input type="submit" name="delete" value="True">
		<input type="button" name="delete" value="No" onclick="location.href='list.jsp'">		
	</form>
	<br><button onclick="location.href='list.jsp'">리스트로 돌아가기</button>
</section>
</body>
</html>