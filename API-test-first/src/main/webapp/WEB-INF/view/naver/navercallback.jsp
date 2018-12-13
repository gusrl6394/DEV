<%--
  Created by IntelliJ IDEA.
  User: dev
  Date: 2018-12-13
  Time: 오후 2:57
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="com.google.gson.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</head>
<body>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark sticky-top">
    <a class="navbar-brand" href="/">Home</a>
    <ul class="navbar-nav">
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/navermap"/>">네이버 Map</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/naverlogin"/>">네이버 로그인</a>
        </li>
    </ul>
</nav>

<div style="width: 100%; word-wrap: break-word;">
<%
    JsonParser jsonParser = new JsonParser();

    String clientId = "###";//애플리케이션 클라이언트 아이디값";
    String clientSecret = "###";//애플리케이션 클라이언트 시크릿값";
    String code = request.getParameter("code");
    String state = request.getParameter("state");
    String redirectURI = URLEncoder.encode("http://localhost:8080/navercallback", "UTF-8");
    String apiURL;
    apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
    apiURL += "client_id=" + clientId;
    apiURL += "&client_secret=" + clientSecret;
    apiURL += "&redirect_uri=" + redirectURI;
    apiURL += "&code=" + code;
    apiURL += "&state=" + state;
    String access_token = "";
    String refresh_token = "";
    System.out.println("apiURL="+apiURL);
    try {
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        BufferedReader br;
        System.out.print("responseCode="+responseCode);
        if(responseCode==200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {  // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String inputLine;
        StringBuffer res = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            res.append(inputLine);
        }
        br.close();
        if(responseCode==200) {
            out.println(res.toString());
            out.println("<br>");

            JsonObject item = (JsonObject) jsonParser.parse(res.toString());

            JsonElement accesstokenObject = item.get("access_token");
            access_token = accesstokenObject.toString();
            out.println("access_token => "+access_token);
            out.println("<br>");

            JsonElement refreshtokenObject = item.get("refresh_token");
            refresh_token = refreshtokenObject.toString();
            out.println("refresh_token => "+refresh_token);
            out.println("<br>");

            JsonElement token_typeObject = item.get("token_type");
            String token_type = token_typeObject.toString();
            out.println("token_type => "+token_type);
            out.println("<br>");

            JsonElement expires_inObject = item.get("expires_in");
            String expires_in = expires_inObject.toString();
            out.println("expires_in => "+expires_in);
            out.println("<br>");

            out.println("<br>");
            out.println("<br>");


        }
    } catch (Exception e) {
        System.out.println(e);
    }
%>
<%
    String token = access_token;// 네이버 로그인 접근 토큰;
    String header = "Bearer " + token; // Bearer 다음에 공백 추가
    try {
        String apiURL2 = "https://openapi.naver.com/v1/nid/me";
        URL url = new URL(apiURL2);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", header);
        int responseCode = con.getResponseCode();
        BufferedReader br;
        if(responseCode==200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {  // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String inputLine;
        StringBuffer resapi = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            resapi.append(inputLine);

        }
        br.close();
        System.out.println(resapi.toString());
        out.println(resapi.toString());

        JsonObject item = (JsonObject) jsonParser.parse(resapi.toString());

        JsonElement naver_profile = item.get("response");
        String naver_profilestr;
        naver_profilestr = naver_profile.toString();
        out.println("<br>");

        out.println("naver_profilestr => "+naver_profilestr);
        out.println("<br>");

        JsonObject profileObject = (JsonObject) naver_profile;
        JsonElement id = profileObject.get("id");
        out.println("id : " + id);
        out.println("<br>");

        JsonElement age = profileObject.get("age");
        out.println("age : " + age);
        out.println("<br>");

        JsonElement gender = profileObject.get("gender");
        out.println("gender : " + gender);
        out.println("<br>");

        JsonElement email = profileObject.get("email");
        out.println("email : " + email);
        out.println("<br>");

        JsonElement birthday = profileObject.get("birthday");
        out.println("birthday : " + birthday);
        out.println("<br>");

    } catch (Exception e) {
        System.out.println(e);
    }
%>
</div>
</body>
</html>
