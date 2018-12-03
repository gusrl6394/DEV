<!-- 해당 TAG가 해당 ID인지 서로 일치한지 확인필요.  -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "surveydate.*" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%
	String loginID = (String)session.getAttribute("loginID");
	if(loginID == null){
		loginID = "GUEST";
		session.setAttribute("loginID", "GUEST");
	}
	int count = 0;
	List<Surveydatedto> articleList = null;
	Surveydatedao dbPro = Surveydatedao.getInstance();
	count = dbPro.getArticleCount(); // 전체 글 수
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/liststyle.css" rel="stylesheet" type="text/css">
</head>
<body>
<section>
<article>
<b>설문목록(전체 글:<%= count %>)</b>
<table class="listwritebutton">
	<tr>
	<%if(loginID == null || loginID.equals("GUEST")){ %><td><a href="../memberone/login.jsp">로그인</a></td><% } %>
	<%if(loginID != null && !loginID.equals("GUEST")){ %><td><a href="surveycreateinfoForm.jsp">설문생성폼</a></td><% } %>
	<%if(loginID != null && !loginID.equals("GUEST")){ %><td><a href="../memberone/main.jsp">메인페이지</a></td><% } %>
	<%if(loginID != null && !loginID.equals("GUEST")){ %><td><a href="../memberone/logoutForm.jsp">로그아웃</a></td><% } %>
	</tr>
</table>
<%
	if(count == 0){
%>
<table class="listtable">
<tr>
	<td>게시판에 저장된 설문조사가 없습니다.</td>
</tr>
</table>
<% } else { %>
<table class="listtable">
	<tr>
		<th>순번</th>
		<th>설문생성날짜</th>
		<th>설문 TAG</th>
		<th>설문시작날짜</th>
		<th>설문종료날짜</th>
		<th>설문수정날짜</th>
		<th>응답자수</th>
		<th>응답자제한수</th>
		<th>설문참여시 획득 포인트</th>			
		<th>설문문항수</th>
		<th>설문대상자</th>
		<th>만든ID</th>
		<th>EDIT</th>
		<th>VIEW</th>
		<th>DELETE</th>
		<th>COMPLETE</th>
		<th>ATTEND</th>
	</tr>
	<%
	articleList = dbPro.getArticles();
	for(int i=0; i<count; i++){
		Surveydatedto article = articleList.get(i);
	%>
	<tr>
		<td><%=(i+1)%></td>
		<td><%=article.getCreatedate()%></td>
		<% if(loginID.equals(article.getId())){ %>
		<td><a href="surveywriteForm.jsp?tag=<%=article.getTag()%>"><%=article.getTag()%></a></td>
		<% }else{ %>
		<td><%=article.getTag()%></td>
		<% } %>
		<td><%=article.getStartdate()%></td>
		<td><%=article.getEnddate()%></td>
		<td><%=article.getEditdate()%></td>
		<td><%=article.getRespondent()%></td>
		<td><%=article.getRespondentlimit()%></td>
		<td><%=article.getPoint()%></td>				
		<td><%=article.getSurveynum()%></td>
		<td><%=article.getTarget()%></td>
		<td><%=article.getId()%></td>
		<% if(loginID.equals(article.getId())){ %>
		<td><a href="surveyeditviewForm.jsp?tag=<%=article.getTag()%>">EDIT</a></td>
		<td><a href="surveyviewForm.jsp?tag=<%=article.getTag()%>">VIEW</a></td>
		<td><a href="surveydeleteForm.jsp?tag=<%=article.getTag()%>">DELETE</a></td>
		<td><a href="#?tag=<%=article.getTag()%>">COMPLETE</a></td>
		<td><a href="surveyattendinfoForm.jsp?tag=<%=article.getTag()%>">ATTEND</a></td>
		<% }else{ %>
		<td>EDIT</td>
		<td><a href="surveyviewForm.jsp?tag=<%=article.getTag()%>">VIEW</a></td>
		<td>DELETE</td>
		<td>COMPLETE/td>
		<td><a href="surveyattendinfoForm.jsp?tag=<%=article.getTag()%>">ATTEND</a></td>
		<% } %>		
	</tr>	
	<% } %>
</table>
<% } %>
</article>
</section>
</body>
</html>