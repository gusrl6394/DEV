<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="memberone.*" %>
<%@ page import="java.net.URLEncoder" %>
<%
	String id = request.getParameter("id");
	System.out.println("Loginid : " + id); ///////
	String pass = request.getParameter("pass");
	String Loginstate = request.getParameter("Loginstate");
	System.out.println("Loginstate : " + Loginstate); ////
	memberDao dao = memberDao.getInstance();
	int check = dao.loginCheck(id, pass);
%>
<%
	if(check == 1){ // 로그인 성공
		if(Loginstate!=null){
			Cookie cookie1 = new Cookie("checkstate", URLEncoder.encode(Loginstate, "UTF-8"));
			response.addCookie(cookie1);
			Cookie cookie2 = new Cookie("checkid", URLEncoder.encode(id, "UTF-8"));
			response.addCookie(cookie2);
		}else {
			Cookie cookie = new Cookie("checkstate", "");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			cookie = new Cookie("checkid", "");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
 		session.setAttribute("loginID", id);
		response.sendRedirect("main.jsp");
	}
	else if(check == 0){ // 비밀번호 틀림
%>
<script>
	alert("아이디 또는 비밀번호가 틀렸습니다.");
	history.go(-1);
</script>
<%
	}else{ // 아이디 없음
%>
<script>
	alert("아이디 또는 비밀번호가 틀렸습니다.");
	history.go(-1);
</script>
<%
	}
%>