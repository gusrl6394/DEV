<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.net.URLDecoder" %> 
<%
	Map<String, Cookie> map = new HashMap<String, Cookie>();
	boolean checkon = false;
	String checkid="";
	if(request.getCookies() != null){
		Cookie[] cookies = request.getCookies();
		if(cookies != null){			
			for(int i=0; i<cookies.length; i++){
				map.put(cookies[i].getName(), cookies[i]);
			}
			// hashmap
			Cookie tmp = map.get("checkstate");
			if(tmp != null){
				checkon = tmp.getValue().equals("on");
				System.out.println("checkon : " + checkon); ///
			}
			if(checkon){
				tmp = map.get("checkid");
				if(tmp != null){
					checkid = tmp.getValue();
					System.out.println("checkid : " + checkid); ////
				}
			}
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/loginstyle.css" rel="stylesheet" type="text/css">
</head>
<body>
<section>
	<article class = "login">
		<form method="post" action="loginProc.jsp">
			<table class="loginTable">
				<tr>
					<th colspan="2">회원 로그인</th>
				</tr>
				<tr>
					<td><input type="text" class="id" name="id" placeholder="아이디 입력" <%if(checkon){ %>value=<%=checkid%><%}%>></td>
				</tr>
				<tr>
					<td><input type="password" class="pass" name="pass" placeholder="비밀번호 입력"/></td>
				</tr>
				<tr>
					<td><input type="checkbox" name="Loginstate" <%if(checkon){ %>checked="checked"<%}%>>아이디 기억하기</td>
				</tr>
				<tr>
					<td colspan="2" class="loginsubmit">
						<input type="submit" value="로그인"/>
						<input type="button" value="회원가입" onclick="javascript:window.location='regForm.jsp'"/>
						<br><input type="button" value="비회원인 상태로 리스트페이지로 이동" onclick="location.href='../boardone/list.jsp'">
					</td>
				</tr>
			</table>
		</form>
	</article>
</section>
</body>
</html>