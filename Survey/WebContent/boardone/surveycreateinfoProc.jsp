<%@page import="surveydate.Surveydatedao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.sql.Timestamp" %>
<%@ page import = "java.util.Date" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<jsp:useBean id="surveydto" class="surveydate.Surveydatedto">
<jsp:setProperty name = "surveydto" property="*"/>
</jsp:useBean>
<%
	request.setCharacterEncoding("UTF-8");
	String startdate_s = request.getParameter("startdatere") + " "+"00:00:00.0";
	java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(startdate_s); // http://egloos.zum.com/uknowapps/v/1812725
	surveydto.setStartdate(t1);
	
	String enddate_s = request.getParameter("enddatere") + " "+"00:00:00.0";
	java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(enddate_s); // http://egloos.zum.com/uknowapps/v/1812725
	surveydto.setEnddate(t2);
	
	System.out.println(surveydto.getStartdate()); ///
	System.out.println(surveydto.getEnddate()); ///
	
	surveydto.setCreatedate(new Timestamp(System.currentTimeMillis()));
	surveydto.setEditdate(surveydto.getCreatedate());
	
	Surveydatedao dao = Surveydatedao.getInstance();
	boolean flag = dao.insertArticle(surveydto);
	
	
%>
<meta http-equiv="Refresh" content="0;url=list.jsp">