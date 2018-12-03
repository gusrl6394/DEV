<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "surveycreate.*"%>
<%@ page import = "java.sql.Timestamp" %>
<%
	request.setCharacterEncoding("UTF-8");
	System.out.println("----------------------");
	System.out.println("-------Proc");
%>
<jsp:useBean id="article" scope="page" class="surveycreate.SurveycreateDto">
	<jsp:setProperty name = "article" property="*"/>
</jsp:useBean>
<% 
	String type = request.getParameter("qcontenttype");
	if(type.equalsIgnoreCase("radio") || type.equalsIgnoreCase("checkbox")){
		String tempcontent="";
		int fornum = Integer.parseInt(request.getParameter("qcontentnum"));
		for(int i=0; i<fornum; i++){
			String colum = "qcontenttext"+(i+1);
			tempcontent+=request.getParameter(colum);
			if(i==fornum-1){
				continue;
			}
			tempcontent+="//";
		}
		article.setQcontent(tempcontent);
	}

	SurveycreateDao dao = SurveycreateDao.getInstance();
	boolean flag = dao.updateArticle(article);
	if(flag){
%>
<meta http-equiv="Refresh" content="0;url=surveyeditviewForm.jsp?tag=<%=article.getTag()%>">
<%
	System.out.println("----------------------");
	System.out.println("          Proc--------");
	}else{
%>
	<script>
	alert("업데이트 실패");
	history.go(-1);
	</script>
<%		
	}
	
%>