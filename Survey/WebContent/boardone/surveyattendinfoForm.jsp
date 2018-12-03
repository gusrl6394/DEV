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
<style>
form > div{border-bottom:2px solid black;}
.balloon {position:relative; display:inline-block;}
.balloon span {display:inline-block; padding:10px; color:rgb(0, 0, 0); background:#00aeff; border-radius:20px;}
.balloon:after {content:''; position:absolute; width:0; height:0; border-style:solid;}
.balloon.msg:after {border-width:10px 15px; top:50%; margin-top:-10px;}
.balloon.msg:after {border-color:transparent #00aeff transparent transparent; left:-25px;}
</style>
<%
String loginID = (String)session.getAttribute("loginID");
if(loginID == null){
	loginID = "GUEST";
	session.setAttribute("loginID", "GUEST");
}
System.out.println("현재 loginID :::"+loginID);
String tag = request.getParameter("tag");
Surveydatedao dao = Surveydatedao.getInstance();
int target = dao.getsurveyattendtarget(tag);
System.out.println("현재 target :::"+target);
boolean flag=false;
if(target == 1){ // traget 1 - 회원전용 설문
	if(loginID.equals("GUEST")){
		flag = false;
	}else{
		flag = true;	
	}	
}else if(target == 2){
	flag = true;
}
if(!flag){
%>
<script>
alert("조사대상이 아닙니다.");
location.href="list.jsp";
</script>
<%
}
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
	innerstrb.append("<form method=\"post\" action=\"surveyattendinfoProc.jsp\" name=\"surveyattendinfoForm\">");
	innerstrb.append("<input type=\"hidden\" name=\"tag\" value=\""+tag+"\">");
	for(int i=0; i<lastnumCheck; i++){
		SurveycreateDto article = articleList.get(i);
		String type = article.getQcontenttype();
		String qcontentnec = article.getQcontentnec();
		if(qcontentnec != null && qcontentnec.equalsIgnoreCase("on")){
			switch(type){
				case "radio": innerstrb.append("<div class=\"radio-nec\" name=\""+(i+1)+"-"+"rnum\">");	break;
				case "checkbox": innerstrb.append("<div class=\"checkbox-nec\" name=\""+(i+1)+"-"+"rnum\">");	break;
				case "text": innerstrb.append("<div class=\"text-nec\" name=\""+(i+1)+"-"+"rnum\">");	break;
				case "textarea": innerstrb.append("<div class=\"textarea-nec\" name=\""+(i+1)+"-"+"rnum\">");	break;
			}
		}else{
			switch(type){
				case "radio": innerstrb.append("<div class=\"radio\" name=\""+(i+1)+"-"+"rnum\">");	break;
				case "checkbox": innerstrb.append("<div class=\"checkbox\" name=\""+(i+1)+"-"+"rnum\">");	break;
				case "text": innerstrb.append("<div class=\"text\" name=\""+(i+1)+"-"+"rnum\">");	break;
				case "textarea": innerstrb.append("<div class=\"textarea\" name=\""+(i+1)+"-"+"rnum\">");	break;
			}
		}
		/* innerstrb.append("<input type=\"hidden\" name=\""+(i+1)+"-"+"rnum\" value=\""+(i+1)+"\">"); */
		innerstrb.append("<div>질문) ");
		innerstrb.append(article.getQtitle());
		innerstrb.append("</div>");
		if(qcontentnec != null && qcontentnec.equalsIgnoreCase("on")){
			innerstrb.append("<div>[답변필수]</div>");
		}else{
			innerstrb.append("<div></div>");
		}
				
		if(type.equalsIgnoreCase("radio") || type.equalsIgnoreCase("checkbox")){
			innerstrb.append("<ol>");
			int qcontentnum = article.getQcontentnum(); // 답안갯수
			String[] qcontent = article.getQcontent().split("//");
			if(type.equalsIgnoreCase("radio")){
				for(int j=0; j<qcontent.length; j++){
					String temp;
					if(qcontentnec != null && qcontentnec.equalsIgnoreCase("on")) temp="<li><input type=\"radio\" id=\"rcontent\" name=\""+(i+1)+"-"+"rcontent\" value=\""+(j+1)+"\" required>";
					else temp="<li><input type=\"radio\" id=\""+(i+1)+"-"+(j+1)+"-"+"rcontent\" name=\""+(i+1)+"-"+"rcontent\" value=\""+(j+1)+"\">";					
					innerstrb.append(temp);
					temp = qcontent[j]+"</li>";
					innerstrb.append(temp);
				}
			}else if(type.equalsIgnoreCase("checkbox")){
				int qcontentmax = article.getQcontentmax();
				innerstrb.append("<div class=\"qcontentmax\">중복 가능갯수 : "+qcontentmax+"</div>");
				for(int j=0; j<qcontent.length; j++){
					String temp="<li><input type=\"checkbox\" id=\""+(i+1)+"-"+(j+1)+"-"+"rcontent\" name=\""+(i+1)+"-"+"rcontent\" value=\""+(j+1)+"\">";					
					innerstrb.append(temp);
					temp = qcontent[j]+"</li>";
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
	innerstrb.append("<br><br><button onclick=\"presubmit("+lastnumCheck+")\">제출</button>");
	innerstrb.append("<br><br><button onclick=\"location.href=\'list.jsp'\">리스트로 돌아가기</button>");
	innerstrb.append("");
	
%>
<article>
<%=innerstrb.toString()%>
</article>
</section>
</body>
<script>
function presubmit(lastnumCheck){
	console.log("총 문제수 : "+lastnumCheck);
	
	var elements = document.getElementsByClassName('checkbox-nec');
	var el = document.querySelectorAll("div.checkbox-nec input[type=checkbox]");
	var result=0;
	var flag=true;
	for(var i=0; i<elements.length; i++){
		for(var j=0; j<el.length; j++){
			if(el[j].checked){
				result++;
			}
		}
	}
	console.log("총 현재 체크된 숫자 : "+result);
	console.log("-----------------------");	
	/* checkbox-nec */
	var A = document.getElementsByClassName('checkbox-nec');
	for(var q=0; q<A.length; q++){
		var A2 = A[q].querySelectorAll("input[type=checkbox]");
		var B2 = A[q].querySelector("div[class=qcontentmax]");
		var C2 = A[q].firstChild.nextSibling;
		var res= 0;
		for(var q2=0; q2<A2.length; q2++){
			console.log(A2[q2]);
			if(A2[q2].checked){
				res++;
			}
		}
		console.log("체크된 숫자 : "+res);
		var B3;
		if(B2!=null){
			B3 = B2.textContent.substring(10);
			console.log("중복 가능갯수 : "+B3);
		}
		console.log("-----------------------");
		if(res>B3){
			console.log("res>B3");
			console.log(C2);
			C2.innerHTML = "[답변필수]&nbsp;&nbsp;&nbsp;<div class=\"balloon msg\"><span><b>중복 가능 갯수를 초과하였습니다.</b></span></div>"
			A[q].tabIndex = -1;
			A[q].focus();
			flag=false;
			/* return; */
		}else{
			console.log("else{} : "+A[q].getAttribute("tabIndex"));
			if(A[q].getAttribute("tabIndex") != null){				
				A[q].removeAttribute("tabIndex");
				C2.innerHTML = "[답변필수]";
				console.log("A[q].removeAttribute");
				flag=true;
			}
		}
	}
	/* checkbox */
	var B = document.getElementsByClassName('checkbox');
	for(var q=0; q<B.length; q++){
		var A2 = B[q].querySelectorAll("input[type=checkbox]");
		var B2 = B[q].querySelector("div[class=qcontentmax]");
		var C2 = B[q].firstChild.nextSibling;
		var res= 0;
		for(var q2=0; q2<A2.length; q2++){
			console.log(B2[q2]);
			if(A2[q2].checked){
				res++;
			}
		}
		console.log("체크된 숫자 : "+res);
		var B3;
		if(B2!=null){
			B3 = B2.textContent.substring(10);
			console.log("중복 가능갯수 : "+B3);
		}
		console.log("-----------------------");
		if(res>B3){
			console.log("res>B3");
			console.log(C2);
			C2.innerHTML = "<div class=\"balloon msg\"><span><b>중복 가능 갯수를 초과하였습니다.</b></span></div>"
			B[q].tabIndex = -1;
			B[q].focus();
			/* return; */
			flag=false;
		}else{
			console.log("else{} : "+B[q].getAttribute("tabIndex"));
			if(B[q].getAttribute("tabIndex") != null){				
				B[q].removeAttribute("tabIndex");
				C2.innerHTML = "";
				console.log("B[q].removeAttribute");
				flag=true;
			}
		}
	}
	/**/
	/* for(var i=0; i<lastnumCheck; i++){
		console.log(i+"-rcontent");
		var obj = document.getElementsByName(i+"-rcontent");
		console.log("obj : "+obj);
	} */
	if(flag){
		document.surveyattendinfoForm.submit();
	}else{
		return;
	}
}
</script>
</html>