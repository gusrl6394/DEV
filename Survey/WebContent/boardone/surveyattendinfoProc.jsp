<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="surveydate.*, surveycreate.*, surveyanswer.*, java.util.ArrayList" %>
<%
request.setCharacterEncoding("UTF-8");
System.out.println("----------------------");
String loginID = (String)session.getAttribute("loginID");
System.out.println(loginID); ////////
String tag = request.getParameter("tag");
System.out.println(tag); /////////
Surveydatedao dao = Surveydatedao.getInstance();
int lastnumCheck = dao.taglastnumCheck(tag);
System.out.println(lastnumCheck); ////////

ArrayList<String[]> type;
ArrayList<String> insertdata= new ArrayList<String>();
SurveyanswerDao ansdao = SurveyanswerDao.getInstance();
type = ansdao.gettype(tag);
boolean state = false;

for(int i=1; i<=type.size(); i++){
	state = false;
	String[] temp = type.get(i-1);
	System.out.println("String[0], String[1] : "+temp[0]+","+temp[1]);
	
	System.out.println("현재 i:"+i);
	String recontent="";
	if(temp[0].equalsIgnoreCase("checkbox")){
		String[] value = request.getParameterValues(i+"-rcontent");
		if(temp[1] != null && temp[1].equals("on") && value == null){
			state=true;
			break;
		}
		int fornum = value.length;
		for(int j=0; j<fornum; j++){
			recontent+=value[j];
			if(j==fornum-1){
				continue;
			}
			recontent+="//";
		}
		System.out.println("checkbox:"+recontent);
	}else if(temp[0].equalsIgnoreCase("radio")){
		recontent = request.getParameter(i+"-rcontent");
		if(temp[1] != null && temp[1].equals("on") && recontent == null){
			state=true;
			break;
		}
		System.out.println("radio:"+recontent);
	}else if(temp[0].equalsIgnoreCase("text")){
		recontent = request.getParameter(i+"-rcontent");
		if(temp[1] != null && temp[1].equals("on") && recontent.equals("")){
			state=true;
			break;
		}
		System.out.println("text:"+recontent);
	}else if(temp[0].equalsIgnoreCase("textarea")){
		recontent = request.getParameter(i+"-rcontent");
		if(temp[1] != null && temp[1].equals("on") && recontent.equals("")){
			state=true;
			break;
		}
		System.out.println("textarea:"+recontent);
	}	
	insertdata.add(recontent);
	System.out.println(recontent);
}
System.out.println("----------------------");
%>
<% if(state){ %>
<script>
alert("필수답변 및 중복답변 갯수를 다시 확인하여 입력해주십시오.");
history.go(-1);
</script>
<% }else{
	for(int i=1; i<=type.size(); i++){
		SurveyanswerDto article = new SurveyanswerDto();
		article.setId(loginID);
		article.setNum(i);
		article.setRnum(i);
		article.setRcontent(insertdata.get(i-1));
		article.setTag(tag);
		ansdao.insertArticle(article);
	}
%>

<script>
alert("답변 정상 등록 완료");
</script>
<meta http-equiv="Refresh" content="0;url=list.jsp">
<% } %>