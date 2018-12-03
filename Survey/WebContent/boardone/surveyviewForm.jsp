<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.util.List" %>
<%@ page import ="java.lang.StringBuilder" %>
<%@ page import ="surveydate.*" %>
<%@ page import ="surveycreate.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>VIEW</title>
<%
String loginID = (String)session.getAttribute("loginID");
String tag = request.getParameter("tag");
Surveydatedao dao = Surveydatedao.getInstance();
int lastnumCheck = dao.taglastnumCheck(tag);
if(lastnumCheck==0){
%>
<script>
	alert("설문번호가 존재하지 않습니다.");
	location.href="list.jsp";
</script>
<%}%>
</head>
<body>
<section>
<%
	SurveycreateDao dbPro = SurveycreateDao.getInstance();
	List<SurveycreateDto> articleList = dbPro.getArticles(tag);
	StringBuilder innerstrb = new StringBuilder();
	innerstrb.append("<form name=\"viewForm\">");
	for(int i=0; i<lastnumCheck; i++){
		SurveycreateDto article = articleList.get(i);
		String type = article.getQcontenttype();
		String qcontentnec = article.getQcontentnec();
		if(qcontentnec != null && qcontentnec.equalsIgnoreCase("on")){
			switch(type){
				case "radio": innerstrb.append("<div class=\"text-nec\">");	break;
				case "checkbox": innerstrb.append("<div class=\"checkbox-nec\">");	break;
				case "text": innerstrb.append("<div class=\"text-nec\">");	break;
				case "textarea": innerstrb.append("<div class=\"textarea-nec\">");	break;
			}
		}else{
			switch(type){
				case "radio": innerstrb.append("<div class=\"text\">");	break;
				case "checkbox": innerstrb.append("<div class=\"checkbox\">");	break;
				case "text": innerstrb.append("<div class=\"text\">");	break;
				case "textarea": innerstrb.append("<div class=\"textarea\">");	break;
			}
		}
		innerstrb.append("<input type=\"hidden\" name=\""+(i+1)+"-"+"rnum\" value=\""+(i+1)+"\">");
		innerstrb.append("<div>질문) ");
		innerstrb.append(article.getQtitle());
		innerstrb.append("</div>");
		if(qcontentnec != null && qcontentnec.equalsIgnoreCase("on")){
			innerstrb.append("<div>[답변필수]</div>");
		}
		innerstrb.append("<br>");		
				
		if(type.equalsIgnoreCase("radio") || type.equalsIgnoreCase("checkbox")){
			innerstrb.append("<ol>");
			int qcontentnum = article.getQcontentnum(); // 답안갯수
			String[] qcontent = article.getQcontent().split("//");
			if(type.equalsIgnoreCase("radio")){
				for(int j=0; j<qcontent.length; j++){
					String temp;
					if(qcontentnec != null && qcontentnec.equalsIgnoreCase("on")) temp="<li><input type=\"radio\" id=\"rcontent\" name=\"rcontent\" value=\""+(j+1)+"\" required>";
					else temp="<li><input type=\"radio\" id=\""+(i+1)+"-"+(j+1)+"-"+"rcontent\" name=\""+(i+1)+"-"+"rcontent\" value=\""+(j+1)+"\">";					
					innerstrb.append(temp);
					temp = qcontent[j]+"</li><br>";
					innerstrb.append(temp);
				}
			}else if(type.equalsIgnoreCase("checkbox")){
				int qcontentmax = article.getQcontentmax();
				innerstrb.append("<div>중복 가능갯수 : "+qcontentmax+"<br><div>");
				for(int j=0; j<qcontent.length; j++){
					String temp="<li><input type=\"checkbox\" id=\""+(i+1)+"-"+(j+1)+"-"+"rcontent\" name=\""+(i+1)+"-"+"rcontent\" value=\""+(j+1)+"\">";					
					innerstrb.append(temp);
					temp = qcontent[j]+"</li><br>";
					innerstrb.append(temp);
				}
			}
			innerstrb.append("</ol>");
		}else if(type.equalsIgnoreCase("text")){
			String temp;
			if(qcontentnec != null && qcontentnec.equals("on"))	temp = "<input type=\"text\" id=\""+(i+1)+"-"+"rcontent\" name=\""+(i+1)+"-"+"rcontent\" required>";
			else	temp = "<input type=\"text\" id=\""+(i+1)+"-"+"rcontent\" name=\""+(i+1)+"-"+"rcontent\">";
			innerstrb.append(temp);
		}else if(type.equals("textarea")){
			String temp;
			if(qcontentnec != null && qcontentnec.equalsIgnoreCase("on")) temp = "<textarea id=\""+(i+1)+"-"+"rcontent\" name=\""+(i+1)+"-"+"rcontent\" required></textarea>";
			else temp ="<textarea id=\""+(i+1)+"-"+"rcontent\" name=\""+(i+1)+"-"+"rcontent\"></textarea>";
			innerstrb.append(temp);
		}
		innerstrb.append("</div>");
	}
	innerstrb.append("</form>");
	innerstrb.append("<br><br><button onclick=\"location.href=\'list.jsp'\">리스트로 돌아가기</button>");
	
%>
<article>
<%=innerstrb.toString()%>
</article>
</section>
</body>
</html>