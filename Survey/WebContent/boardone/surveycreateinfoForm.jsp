<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="memberone.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
	String loginID = (String)session.getAttribute("loginID");
	memberDao dao = memberDao.getInstance();
	memberDto dto = dao.getMember(loginID);
	Date now = new Date();
	SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddhhmmss");
	SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
	String codeday = sf1.format(now);
	String today = sf2.format(now);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>설문조사 생성폼</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<script src="boardonescript.js" type="text/javascript"></script>
</head>
<body>
<% if(loginID != null && loginID.equals(dto.getId())){ %>
<section>
	<form method="post" action="surveycreateinfoProc.jsp" name="surveycreateForm">
		<table>
			<tr>
				<td>ID</td>
				<td><%=dto.getId()%><input type="hidden" name="id" value="<%=dto.getId()%>"></td>				
			</tr>
			<tr>
				<td>EMAIL</td>
				<td><%=dto.getEmail()%></td>
			</tr>
			<tr>
				<td>TAG(자동생성)</td>
				<td>
				<% 
					Random r = new Random();
				    int d = r.nextInt(9999);
				    codeday += String.valueOf(d);
			    %>
				<%=codeday%>
				<input type="hidden" name="tag" value="<%=codeday%>">
				</td>
			</tr>
			<tr>
				<td>설문시작날짜</td>
				<td>
					<input type="date" id="startdatere" name="startdatere" value="<%=today%>" required>
				</td>
			</tr>
			<tr>
				<td>설문종료날짜</td>
				<td>
					<input type="date" id="enddatere" name="enddatere" value="<%=today%>" required>
				</td>
			</tr>
			<tr>
				<td> 응답자수 제한 </td>
				<td> <input type="number" id="respondentlimit" name="respondentlimit" min="1" required></td>
			</tr>
			<tr>
				<td> 설문 참여시 얻을 포인트 </td>
				<td> <input type="number" id="point" name="point" min="0" required></td>
			</tr>
			<tr>
				<td> 문항수 </td>
				<td> <input type="number" id="surveynum" name="surveynum" min="1" required></td>
			</tr>
			<tr>
				<td> 설문대상자 </td>
				<td> 
					<input type="radio" name="target" value="1" checked="checked">회원<br>
					<input type="radio" name="target" value="2">회원+비회원<br>
					<input type="radio" name="target" disabled value="3">익명<br>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<b>※제출시 수정안됨, 삭제하고 다시 만들어야 가능</b><br>
					<input type="button" value="설문지 생성" onclick="surveyCheck()"/>
				</td>
			</tr>
		</table>
	</form>
</section>
	<% }else{ %>
	<script type="text/javascript">
		alert("정상적인 접근이 아닙니다. 비로그인 상태이거나 권한이 부족합니다.");
		location.href="../memberone/login.jsp";
	</script>
	<% } %>
</body>
</html>