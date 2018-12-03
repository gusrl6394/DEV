<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "surveydate.*" %>
<%@ page import = "surveycreate.*" %>
<%
	String loginID = (String)session.getAttribute("loginID");
	int num = Integer.parseInt(request.getParameter("num"));
	String tag = request.getParameter("tag");
	try{		
		SurveycreateDao dao = SurveycreateDao.getInstance();
		SurveycreateDto article = dao.getArticle(num, tag);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>설문조사 내용 수정폼</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/writeFormstyle.css" rel="stylesheet" type="text/css">
<script src="boardonescript.js"></script>
</head>
<body>
<section>
<b>설문조사 내용 수정폼</b>
<article>
	<form method="post" name="writeForm" action="surveyupdateProc.jsp?tag=<%=tag%>">
		<table class="survey">
			<tr>
				<td>설문 TAG</td>
				<td><%=tag%><input type="hidden" id="tag" name="tag" value="<%=tag%>"></td>
			</tr>
			<tr>
				<td>질문번호</td>
				<td><input type="text" disabled value="<%=article.getNo()%>">
					<input type="hidden" id="no" name="no" value="<%=article.getNo()%>">
					<input type="hidden" id="num" name="num" value="<%=article.getNum()%>">					
					<input type="hidden" id="noref" name="noref" value="<%=article.getNoref()%>">
					<input type="hidden" id="nodepth" name="nodepth" value="<%=article.getNodepth()%>">
				</td>
			</tr>
			<tr>
				<td class ="attr">질문작성</td>
				<td>
					<input type="text" id="qtitle" name="qtitle" size=100 value="<%=article.getQtitle()%>" required/><br>
					<input type="checkbox" name="qcontentnec" <%if(article.getQcontentnec() != null && article.getQcontentnec().equals("on")) {%>checked="checked" <%}%>><b>질의에 대한 답안 체크 필수 여부</b>
				</td>
			</tr>
			<tr>
				<td rowspan="2" class ="attr">답안유형</td>
				<td>
					<% String type = article.getQcontenttype(); %>
					<input type = "radio" id="qcontenttyperadio" name = "qcontenttype" value = "radio" onclick="qcontentmaxCreate(this.form.qcontenttype.value)" <%if(type.equalsIgnoreCase("radio")){ %>checked<%}%> required>라디오선택
			        <input type = "radio" id="qcontenttypecheckbox" name = "qcontenttype" value = "checkbox" onclick="qcontentmaxCreate(this.form.qcontenttype.value)" <%if(type.equalsIgnoreCase("checkbox")){ %>checked<%}%> required>체크선택
			        <input type = "radio" id="qcontenttypetext" name = "qcontenttype" value = "text" onclick="qcontentmaxCreate(this.form.qcontenttype.value)" <%if(type.equalsIgnoreCase("text")){ %>checked<%}%> required>단답형
			        <input type = "radio" id="qcontenttypetextarea" name = "qcontenttype" value = "textarea" onclick="qcontentmaxCreate(this.form.qcontenttype.value)" <%if(type.equalsIgnoreCase("textarea")){ %>checked<%}%> required>주관형        
		        </td>		        
			</tr>
			<tr>
				<td>
		  	      <div id="answermax" class="answermax">
		  	 	     <!-- 라디오 버튼 및 체크박스시 답변갯수 생성 -->
		  	      	<% if(type.equalsIgnoreCase("radio")||type.equalsIgnoreCase("checkbox")){
		  	      		int qcontentnum = article.getQcontentnum();
		  	      	%>
		  	      		<b>답안 갯수 : </b><input type="number" id="qcontentnum" name="qcontentnum" value="<%=qcontentnum%>" min="1">		  	      		
		  	      	<%  } %>		  	      	
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
					<%  if(type.equalsIgnoreCase("radio") || type.equalsIgnoreCase("checkbox")){
							int qcontentnum = article.getQcontentnum();
							String[] qcontentarr = article.getQcontent().split("//");
					%>
					<% 		if(type.equalsIgnoreCase("radio")){
								for(int i=0; i<qcontentnum; i++){
					%>
									<%=(i+1)%>번) <input type="radio" id="qcontent" name="qcontent" disabled value="<%=(i+1)%>"/> : <input type="text" name="qcontenttext<%=(i+1)%>" size=60 value="<%=qcontentarr[i]%>"><br>
					<% 			
								}
								int qjumpanswer = article.getQjumpanswer();
								if(qjumpanswer != -1){
					%>
									<br>답안 ○번 선택시 다음 질문으로 넘어가기(옵션)<br>
									<input type="number" id="qjumpanswer" name="qjumpanswer" value="<%=article.getQjumpanswer()%>" min="0">번 답안시 
									<input type="number" id="qjump" name="qjump" value="<%=article.getQjump()%>" min="0">질문으로 넘어가기
					<%
								}else{
					%>
									<br>답안 ○번 선택시 다음 질문으로 넘어가기(옵션)<br>
									<input type="number" id="qjumpanswer" name="qjumpanswer" value="0" min="0">번 답안시 
									<input type="number" id="qjump" name="qjump" value="0" min="0">질문으로 넘어가기
					<%
								}
					%>																				
					<%		}else if(type.equalsIgnoreCase("checkbox")){
								int qcontentmax = article.getQcontentmax();
					%>
								- 중복답변 갯수 설정 : <input type="number" id="qcontentmax" name="qcontentmax" value="<%=qcontentmax%>" min="0"><br><br>
					<%		
								for(int i=0; i<qcontentnum; i++){
					%>
									<%=(i+1)%>번) <input type="checkbox" id="qcontent" name="qcontent" disabled value="<%=(i+1)%>"/> : <input type="text" name="qcontenttext<%=(i+1)%>" size=60 value="<%=qcontentarr[i]%>"><br>
					<%
								}
							}
						}else if(type.equalsIgnoreCase("text")){
					%>
							<input type="text" id="qcontent" name="qcontent" value=<%if(article.getQcontent() != null){%><%=article.getQcontent()%><%}else{%><%}%>><br>
					<%
						}else if(type.equalsIgnoreCase("textarea")){
					%>
							<textarea id="qcontent" id="qcontent" name="qcontent" rows="13" cols="50"><%if(article.getQcontent() != null){%><%=article.getQcontent()%><%}else{%><%}%></textarea>
					<%
						}
					%>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="attr">
				<input type="hidden" id="id" name="id" value="<%=loginID%>">
				<input type="submit" value="저장 "/>
				<input type="reset" value="다시 작성"/>
				<input type="button" value="목록" onclick="window.location='list.jsp'"/>
				</td>
			</tr>
		</table>		
	</form>
</article>
</section>
<%
	}catch(Exception e){
		e.printStackTrace();
	}
%>
</body>
</html>