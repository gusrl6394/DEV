<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

 <style>
 	body{
 		background-color : blue;
 	}
 	td{
 		background-color : gray;
 	}
 	#main{
 		width : 1000px;
 		height : 500px;
 	}
 </style>
</head>
<body>

	<table>
		<tr id = "menu">
			<td>
				<a href = "home">홈으로</a>
					<div>
						<iframe id = 'lineless' src = "menuBar"  name = "menuBar">
						</iframe>
					</div>
			</td>
		</tr>
		<tr id = "down">
			<td id = "main">
			<!-- 
				<c:forEach var = "boar" items = "${boardlist}" varStatus = "Loop">
				</c:forEach>
				 -->
					<div>
						<iframe id = 'lineless' src = "mainView" name = "mainView" width = "1000px" height = 500px">
						</iframe>
					</div>
				
			</td>
			<td id = "hit">
				<div>
					<iframe id = 'lineless' src = "hitView" name = "hitView"  height = 500px>
					</iframe>
				</div>
			</td>
		</tr>
	
	
	
	

	</table>
	
</body>
</html>