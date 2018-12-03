<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "surveydate.*" %>
<!DOCTYPE html>
<html>
<head>
<%
	String loginID = (String)session.getAttribute("loginID");
	String tag = request.getParameter("tag");	
	int no=0;
	int noref=0;
	int nodepth=0;
	Surveydatedao dao = Surveydatedao.getInstance();
	int lastnumCheck = dao.taglastnumCheck(tag);
	int last = dao.taglastnum(tag);
	if(lastnumCheck>=last){
%>
<script>
	alert("설문조사 작성이 완료되었습니다.");
	location.href="list.jsp";
</script>
<%		
	}
	if(lastnumCheck==0){
		no=1;
		noref=1;
	}else{
		no=lastnumCheck+1;
		noref=no;
	}
%>
<meta charset="UTF-8">
<title>설문조사 내용 쓰기폼</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/writeFormstyle.css" rel="stylesheet" type="text/css">
<script src="boardonescript.js"></script>
</head>
<body>
<section>
<b>설문조사 폼 글쓰기</b>
<% if(loginID != null){ %>
<article>
	<form method="post" action="surveywriteProc.jsp" name="writeForm">
		<table class="survey">
			<tr>
				<td>설문 TAG</td>
				<td><%=tag%><input type="hidden" id="tag" name="tag" value="<%=tag%>"></td>
			</tr>
			<tr>
				<td>질문번호</td>
				<td><%=no%>
					<input type="hidden" id="no" name="no" value="<%=no%>">
					<input type="hidden" id="noref" name="noref" value="<%=noref%>">
					<input type="hidden" id="nodepth" name="nodepth" value="0">
				</td>
			</tr>
			<tr>
				<td class ="attr">질문작성</td>
				<td>
					<input type="text" id="qtitle" name="qtitle" size=100 required/><br>
					<input type="checkbox" id="qcontentnec" name="qcontentnec"><b>질의에 대한 답안 체크 필수 여부</b>
				</td>
			</tr>
			<tr>
				<td rowspan="2" class ="attr">답안유형</td>
				<td>
					<input type = "radio" id="qcontenttyperadio" name = "qcontenttype" value = "radio" onclick="qcontentmaxCreate(this.form.qcontenttype.value)" required>라디오선택
			        <input type = "radio" id="qcontenttypecheckbox" name = "qcontenttype" value = "checkbox" onclick="qcontentmaxCreate(this.form.qcontenttype.value)" required>체크선택
			        <input type = "radio" id="qcontenttypetext" name = "qcontenttype" value = "text" onclick="qcontentmaxCreate(this.form.qcontenttype.value)" required>단답형
			        <input type = "radio" id="qcontenttypetextarea" name = "qcontenttype" value = "textarea" onclick="qcontentmaxCreate(this.form.qcontenttype.value)" required>주관형        
		        </td>		        
			</tr>
			<tr>
				<td>
		  	      <div id="answermax" class="answermax">
		  	      	<!-- 라디오 버튼 및 체크박스시 답변갯수 생성 -->
		  	      </div>
		          <input type="button" value="답안 생성하기" onclick="createanswer(this.form.qcontenttype.value)"/>
		        </td>
			</tr>
			<tr>
				<td colspan="2">
					<b>답안작성</b>
				</td>
			</tr>
			<tr>
				<td colspan="2" id="content create">
					<!-- 컨텐츠 생성자리  -->
				</td>
			</tr>
			<tr>
				<td colspan="2" class="attr">
				<input type="hidden" id="id" name="id" value="<%=loginID%>">
				<input type="submit" value="저장 & 다음질문으로 넘어가기"/>
				<input type="reset" value="다시 작성"/>
				<input type="button" value="목록" onclick="window.location='list.jsp'"/>
				</td>
			</tr>
		</table>
	</form>
</article>
<% }else{ %>
<script type="text/javascript">
		alert("정상적인 접근이 아닙니다. 비로그인 상태이거나 권한이 부족합니다.");
		location.href="../memberone/main.jsp";
</script>
<% } %>
</section>
</body>
</html>