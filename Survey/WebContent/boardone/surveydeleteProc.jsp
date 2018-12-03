<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="surveydate.*, surveycreate.*, surveyanswer.*" %>
<% request.setCharacterEncoding("UTF-8");
String tag = request.getParameter("tag");
SurveyanswerDao ansdao = SurveyanswerDao.getInstance();
ansdao.deleteArticle(tag);
SurveycreateDao credao = SurveycreateDao.getInstance();
credao.deleteArticle(tag);
Surveydatedao datedao = Surveydatedao.getInstance();
datedao.deleteArticle(tag);
%>
<script>
alert("삭제 완료되었습니다.");
</script>
<meta http-equiv="Refresh" content="0;url=list.jsp">