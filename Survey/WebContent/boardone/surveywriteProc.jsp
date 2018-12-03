<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "surveycreate.*"%>
<%@ page import = "surveydate.*" %>
<%@ page import = "java.sql.Timestamp" %>
<%
	request.setCharacterEncoding("UTF-8");
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
	dao.insertArticle(article);
	
	String tag = request.getParameter("tag");	
	Surveydatedao datedao = Surveydatedao.getInstance();
	int lastnumCheck = datedao.taglastnumCheck(tag);
	int last = datedao.taglastnum(tag);
	System.out.println(lastnumCheck);
	System.out.println(last);
	if(lastnumCheck<last){
		String temp = "surveywriteForm.jsp?tag="+tag;
		response.sendRedirect(temp);
	}else{
%>
	<script type="text/javascript">
		alert("설문조사 작성이 완료되었습니다.");
		location.href="list.jsp";
	</script>
<% 
	}
%>